package com.nebula.service.service;

import com.nebula.service.domain.system.SysUserDept;
import com.nebula.service.domain.system.SysUserInfo;

import java.util.List;

public interface SysUserDeptService {


    List<SysUserInfo> getDeptUser(SysUserDept sysUserDept);

    int addOrEditUserToDept(SysUserDept sysUserDept);

    int removeUserFormDept(SysUserDept sysUserDept);
}
