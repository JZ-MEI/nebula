package com.nebula.service.service;

import com.nebula.service.domain.system.SysRoleUser;
import com.nebula.service.domain.system.SysUserInfo;

import java.util.List;

public interface SysRoleUserService {

    List<SysUserInfo> getRoleUser(SysRoleUser sysRoleUser);

    int createOrDeleteRoleUser(SysRoleUser sysRoleUser);

    List<String> getUserRoleList(Integer loginId);
}
