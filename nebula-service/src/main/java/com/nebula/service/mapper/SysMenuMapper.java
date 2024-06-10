package com.nebula.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nebula.service.domain.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    List<SysMenu> getMenuList();

    List<SysMenu> getPageBreadcrumb(@Param("routerPath")String routerPath);

    List<SysMenu> getRouterData();

    List<SysMenu> getMenuTableData(SysMenu sysMenu);

    SysMenu getMenuInfo(SysMenu sysMenu);
}
