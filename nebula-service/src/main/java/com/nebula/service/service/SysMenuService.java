package com.nebula.service.service;

import com.nebula.service.domain.system.SysMenu;

import java.util.List;

public interface SysMenuService {

    List<SysMenu> getSuperMenuList(SysMenu sysMenu);

    List<SysMenu> getPageBreadcrumb(String routerPath);

    List<SysMenu> getRouterData();

    SysMenu getChild(SysMenu sysMenu,SysMenu queryParam);

    int createMenu(SysMenu sysMenu);

    int updateMenu(SysMenu sysMenu);

    int deleteById(SysMenu sysMenu);

    SysMenu getMenuInfo(SysMenu sysMenu);

    List<String> getAllMenuPermission();
}
