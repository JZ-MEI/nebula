package com.nebula.service.service;

import com.nebula.service.domain.system.SysUserInfo;

import java.util.List;

public interface SysUserInfoService {

    Integer userLogin(SysUserInfo sysUserInfo);

    List<SysUserInfo> getSysUserInfo(SysUserInfo sysUserInfo);

    int createOrEditUserInfo(SysUserInfo sysUserInfo);

    int modifyPwd(SysUserInfo sysUserInfo);

    List<SysUserInfo> getNormalUser();

    SysUserInfo getUserById(Integer id);

    SysUserInfo getPersonalInfo();

    int modifyPersonalInfo(SysUserInfo sysUserInfo);

    int modifyPassword(String data);

}
