package com.nebula.service.service.impl;

import cn.hutool.core.util.StrUtil;
import com.nebula.service.domain.system.SysRoleUser;
import com.nebula.service.domain.system.SysUserInfo;
import com.nebula.service.mapper.SysRoleUserMapper;
import com.nebula.service.service.SysMenuService;
import com.nebula.service.service.SysRoleUserService;
import com.nebula.service.service.SysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysRoleUserServiceImpl implements SysRoleUserService {
    @Autowired
    SysRoleUserMapper sysRoleUserMapper;
    @Autowired
    SysUserInfoService sysUserInfoService;
    @Autowired
    SysMenuService sysMenuService;

    @Override
    public List<SysUserInfo> getRoleUser(SysRoleUser sysRoleUser) {
        return sysRoleUserMapper.getRoleUser(sysRoleUser);
    }

    @Override
    public int createOrDeleteRoleUser(SysRoleUser sysRoleUser) {
        if (sysRoleUser.getId() != null) {
            return sysRoleUserMapper.deleteById(sysRoleUser.getId());
        }
        sysRoleUser.setCreateTime(new Date());
        return sysRoleUserMapper.insert(sysRoleUser);
    }

    @Override
    public List<String> getUserRoleList(Integer loginId) {
        SysUserInfo userInfo = sysUserInfoService.getUserById(loginId);
        if (StrUtil.equals(userInfo.getUsername(),"admin")){
            return sysMenuService.getAllMenuPermission();
        }
        return sysRoleUserMapper.getUserRoleList(loginId);
    }
}
