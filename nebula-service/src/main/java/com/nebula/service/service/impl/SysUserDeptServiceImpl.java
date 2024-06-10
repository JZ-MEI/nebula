package com.nebula.service.service.impl;

import com.nebula.service.domain.system.SysUserDept;
import com.nebula.service.domain.system.SysUserInfo;
import com.nebula.service.mapper.SysUserDeptMapper;
import com.nebula.service.service.SysUserDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysUserDeptServiceImpl implements SysUserDeptService {

    @Autowired
    SysUserDeptMapper sysUserDeptMapper;

    @Override
    public List<SysUserInfo> getDeptUser(SysUserDept sysUserDept) {
        return sysUserDeptMapper.getDeptUser(sysUserDept);
    }

    @Override
    public int addOrEditUserToDept(SysUserDept sysUserDept) {
        if (sysUserDept.getId() != null) {
            sysUserDept.setUpdateTime(new Date());
            return sysUserDeptMapper.updateDeptUser(sysUserDept);
        }
        sysUserDept.setCreateTime(new Date());
        return sysUserDeptMapper.insert(sysUserDept);
    }

    @Override
    public int removeUserFormDept(SysUserDept sysUserDept) {
        return sysUserDeptMapper.deleteById(sysUserDept.getId());
    }
}
