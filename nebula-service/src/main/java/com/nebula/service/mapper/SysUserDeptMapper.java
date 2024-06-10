package com.nebula.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nebula.service.domain.system.SysUserDept;
import com.nebula.service.domain.system.SysUserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserDeptMapper extends BaseMapper<SysUserDept> {


    List<SysUserInfo> getDeptUser(SysUserDept sysUserDept);

    int updateDeptUser(SysUserDept sysUserDept);
}
