package com.nebula.service.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.convert.Convert;
import com.nebula.service.service.SysRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StpUserRoleConfig implements StpInterface {

    @Autowired
    SysRoleUserService sysRoleUserService;
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<String> userRoleList = sysRoleUserService.getUserRoleList(Convert.toInt(loginId));
        return userRoleList;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return null;
    }
}
