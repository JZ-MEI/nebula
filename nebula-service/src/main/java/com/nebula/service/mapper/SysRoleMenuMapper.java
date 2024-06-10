package com.nebula.service.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nebula.service.domain.system.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    List<SysRoleMenu> getRoleMenuList(SysRoleMenu sysRoleMenu);
}
