package com.nebula.service.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.nebula.service.domain.monitor.OnlineUser;
import com.nebula.service.domain.system.SysUserInfo;
import com.nebula.service.service.OnlineUserService;
import com.nebula.service.service.SysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OnlineUserServiceImpl implements OnlineUserService {

    @Autowired
    SysUserInfoService sysUserInfoService;

    @Override
    public List<OnlineUser> getOnlineUserList(OnlineUser onlineUser) {
        int start = (onlineUser.getPageNum()-1) * onlineUser.getPageSize();
        List<String> sessionIdList = StpUtil.searchSessionId(StrUtil.nullToDefault(onlineUser.getToken(),""), start, onlineUser.getPageSize(), false);

        List<OnlineUser> onlineUserList = new ArrayList<>();
        for (String sessionId:sessionIdList){
            SaSession session = StpUtil.getSessionBySessionId(sessionId);
            OnlineUser currentSessionUser = new OnlineUser();
            SysUserInfo currentUser = sysUserInfoService.getUserById((Integer) session.getLoginId());

            currentSessionUser.setNickname(currentUser.getNickname());
            currentSessionUser.setLoginTime(new Date(session.getCreateTime()));
            currentSessionUser.setBrowser((String)session.get("browser"));
            currentSessionUser.setPlatform((String)session.get("platform"));
            currentSessionUser.setEngine((String)session.get("engine"));
            currentSessionUser.setOs((String)session.get("os"));
            currentSessionUser.setLoginDevices(session.getTokenSignList().size());
            currentSessionUser.setToken(session.getToken());
            currentSessionUser.setLoginIp((String) session.get("loginIp"));
            currentSessionUser.setLoginId((Integer)session.getLoginId());
            onlineUserList.add(currentSessionUser);
        }
        return onlineUserList;
    }
}
