package com.nebula.service.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import com.nebula.common.util.AdminUtil;
import com.nebula.service.config.StpUserRoleConfig;
import com.nebula.service.domain.system.SysMenu;
import com.nebula.service.mapper.SysMenuMapper;
import com.nebula.service.service.SysMenuService;
import com.nebula.service.service.SysRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    SysMenuMapper sysMenuMapper;
    @Autowired
    SysRoleUserService sysRoleUserService;

    private static final Integer SUPER_PARENT_ID = 0;

    @Override
    public List<SysMenu> getSuperMenuList(SysMenu sysMenu) {
        sysMenu.setParentId(SUPER_PARENT_ID);
        return sysMenuMapper.getMenuTableData(sysMenu);
    }
    @Override
    public List<SysMenu> getPageBreadcrumb(String routerPath) {
        return sysMenuMapper.getPageBreadcrumb(routerPath);
    }

    @Override
    public List<SysMenu> getRouterData() {
        return sysMenuMapper.getRouterData();
    }

    @Override
    public int createMenu(SysMenu sysMenu) {
        return sysMenuMapper.insert(sysMenu);
    }

    @Override
    public int updateMenu(SysMenu sysMenu) {
        return sysMenuMapper.updateById(sysMenu);
    }

    @Override
    public int deleteById(SysMenu sysMenu) {
        sysMenu.setIsDeleted(1);
        return sysMenuMapper.updateById(sysMenu);
    }

    @Override
    public SysMenu getChild(SysMenu superMenu, SysMenu queryParam) {
        List<SysMenu> childMenus = sysMenuMapper.getMenuTableData(queryParam);
        if (CollUtil.isNotEmpty(childMenus)) {
            List<SysMenu> childMenu = childMenus.stream()
                    .peek(i -> {
                        queryParam.setParentId(i.getId());
                        getChild(i,queryParam);
                    }).collect(Collectors.toList());
            if (CollUtil.isNotEmpty(childMenu)) {
                superMenu.setSysMenuList(childMenu);
            } else {
                superMenu.setSysMenuList(null);
            }
        }
        return superMenu;
    }

    @Override
    public SysMenu getMenuInfo(SysMenu sysMenu) {
        return sysMenuMapper.getMenuInfo(sysMenu);
    }


    @Override
    public List<String> getAllMenuPermission() {
        return sysMenuMapper.getAllMenuPermission();
    }
}
