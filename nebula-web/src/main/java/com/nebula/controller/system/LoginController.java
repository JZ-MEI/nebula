package com.nebula.controller.system;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.*;
import com.nebula.common.common.BaseResult;
import com.nebula.service.domain.monitor.SysLoginLog;
import com.nebula.service.domain.system.SysUserInfo;
import com.nebula.service.service.SysLoginLogService;
import com.nebula.service.service.SysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jws.Oneway;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    SysUserInfoService sysUserInfoService;
    @Autowired
    SysLoginLogService sysLoginLogService;

    @PostMapping("/login")
    public BaseResult<?> login(@RequestBody SysUserInfo sysUserInfo, HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgentUtil.parse(header);
        Browser browser = userAgent.getBrowser();
        Platform platform = userAgent.getPlatform();
        Engine engine = userAgent.getEngine();
        OS os = userAgent.getOs();
        Integer userId = sysUserInfoService.userLogin(sysUserInfo);
        StpUtil.login(userId, sysUserInfo.getRememberMe());
        SaSession session = StpUtil.getSessionByLoginId(userId);
        session.set("browser", browser.getName());
        session.set("platform", platform.getName());
        session.set("engine", engine.getName());
        session.set("os", os.getName());
        session.setToken(StpUtil.getTokenValue());
        String loginIp = ServletUtil.getClientIP(request);
        if (session.get("loginIp") != null&& !StrUtil.equals((String)session.get("loginIp"),loginIp)) {
            session.set("loginIp", session.get("loginIp") + "/" + loginIp);
        } else {
            session.set("loginIp", loginIp);
        }
        Date now = new Date();

        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setUserId(userId);
        sysLoginLog.setLoginTime(new Date());
        sysLoginLog.setLoginIp(loginIp);
        sysLoginLog.setLoginBrowser(browser.getName());
        sysLoginLog.setLoginPlatform(platform.getName());
        sysLoginLog.setLoginEngine(engine.getName());
        sysLoginLog.setLoginOs(os.getName());
        sysLoginLogService.insert(sysLoginLog);

        return BaseResult.success("登录成功");
    }

    @GetMapping("/getLoginState")
    public BaseResult<Boolean> getLoginState() {
        return BaseResult.success(StpUtil.isLogin());
    }

    @PostMapping("/logout")
    public BaseResult<?> logout() {
        StpUtil.logout();
        return BaseResult.success("退出成功");
    }

}
