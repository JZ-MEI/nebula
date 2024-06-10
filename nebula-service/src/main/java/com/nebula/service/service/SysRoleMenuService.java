package com.nebula.service.service;

import com.nebula.service.domain.system.SysRoleMenu;

import java.util.List;

public interface SysRoleMenuService {

    List<Integer> getRoleMenuList(SysRoleMenu sysRoleMenu);

    int authorization(SysRoleMenu sysRoleMenu);
}
