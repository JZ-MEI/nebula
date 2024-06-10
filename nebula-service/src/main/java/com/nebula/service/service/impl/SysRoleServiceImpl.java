package com.nebula.service.service.impl;

import com.nebula.common.util.AdminUtil;
import com.nebula.service.domain.system.SysRole;
import com.nebula.service.mapper.SysRoleMapper;
import com.nebula.service.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    SysRoleMapper sysRoleMapper;



    @Override
    public List<SysRole> getRoleList(SysRole sysRole) {
        return sysRoleMapper.getRoleList(sysRole);
    }

    @Override
    public int createOrUpdateSysRole(SysRole sysRole) {
        if (sysRole.getId()!=null){
            sysRole.setUpdateTime(new Date());
            sysRole.setUpdateBy(AdminUtil.getLoginId());
            return sysRoleMapper.updateById(sysRole);
        }
        sysRole.setCreateTime(new Date());
        sysRole.setCreateBy(AdminUtil.getLoginId());
        return sysRoleMapper.insert(sysRole);
    }


}
