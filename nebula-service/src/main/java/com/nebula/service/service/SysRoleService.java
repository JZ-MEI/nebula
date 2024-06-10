package com.nebula.service.service;

import com.nebula.service.domain.system.SysRole;

import java.util.List;

public interface SysRoleService {

    List<SysRole> getRoleList(SysRole sysRole);

    int createOrUpdateSysRole(SysRole sysRole);
}
