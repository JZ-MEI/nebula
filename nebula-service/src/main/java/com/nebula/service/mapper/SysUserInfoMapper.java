package com.nebula.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nebula.service.domain.system.SysUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserInfoMapper extends BaseMapper<SysUserInfo> {
    SysUserInfo getUserInfoByUsername(@Param("username")String username);

    List<SysUserInfo> getSysUserInfo(SysUserInfo sysUserInfo);

}
