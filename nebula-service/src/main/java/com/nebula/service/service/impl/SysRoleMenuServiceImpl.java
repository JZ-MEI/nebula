package com.nebula.service.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.nebula.service.domain.system.SysRoleMenu;
import com.nebula.service.mapper.SysRoleMenuMapper;
import com.nebula.service.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Autowired
    SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<Integer> getRoleMenuList(SysRoleMenu sysRoleMenu) {
        List<SysRoleMenu> roleMenuList = sysRoleMenuMapper.getRoleMenuList(sysRoleMenu);
        return roleMenuList.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
    }

    @Override
    public int authorization(SysRoleMenu sysRoleMenu) {

        List<SysRoleMenu> roleMenuList = sysRoleMenuMapper.getRoleMenuList(sysRoleMenu);
        List<Integer> newMenuIdList = sysRoleMenu.getNewMenuIdList();
        List<Integer> needToDelete = roleMenuList.stream()
                .filter(i -> !newMenuIdList.contains(i.getMenuId()))
                .map(SysRoleMenu::getId)
                .collect(Collectors.toList());

        List<Integer> needToInsert = newMenuIdList.stream()
                .filter(i->!roleMenuList.stream()
                        .map(SysRoleMenu::getMenuId)
                        .collect(Collectors.toList())
                        .contains(i))
                .collect(Collectors.toList());

        List<SysRoleMenu> newMenuList = needToInsert.stream().map(i -> {
            SysRoleMenu newRoleMenu = new SysRoleMenu();
            newRoleMenu.setCreateTime(new Date());
            newRoleMenu.setRoleId(sysRoleMenu.getRoleId());
            newRoleMenu.setMenuId(i);
            return newRoleMenu;
        }).collect(Collectors.toList());
        if (CollUtil.isEmpty(needToDelete)&&CollUtil.isEmpty(needToInsert)){
            return 1;
        }
        int effectRows = 0;
        if (CollUtil.isNotEmpty(needToDelete)) {
            effectRows = sysRoleMenuMapper.deleteBatchIds(needToDelete);
        }
        for (SysRoleMenu insertEntity:newMenuList){
            effectRows += sysRoleMenuMapper.insert(insertEntity);
        }
        return effectRows;
    }
}