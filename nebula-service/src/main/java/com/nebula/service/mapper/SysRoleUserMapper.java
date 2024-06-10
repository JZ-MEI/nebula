package com.nebula.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nebula.service.domain.system.SysRoleUser;
import com.nebula.service.domain.system.SysUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleUserMapper extends BaseMapper<SysRoleUser> {

    List<SysUserInfo> getRoleUser(SysRoleUser sysRoleUser);

    List<String> getUserRoleList(@Param("loginId")Integer loginId);
}
