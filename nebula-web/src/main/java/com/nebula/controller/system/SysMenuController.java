package com.nebula.controller.system;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.nebula.common.common.BaseResult;
import com.nebula.common.constants.MenuTypeConstants;
import com.nebula.common.entity.TableDataInfo;
import com.nebula.common.util.AdminUtil;
import com.nebula.common.util.BaseController;
import com.nebula.common.util.PageUtil;
import com.nebula.service.domain.system.SysMenu;
import com.nebula.service.service.SysMenuService;
import com.nebula.service.service.SysRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("sysMenu")
public class SysMenuController extends BaseController {

    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    SysRoleUserService sysRoleUserService;


    @GetMapping("getMenuList")
    public BaseResult<List<SysMenu>> getMenuList(SysMenu sysMenu) {
        List<String> userRoleList = sysRoleUserService.getUserRoleList(AdminUtil.getLoginId());
        sysMenu.setPermissionList(userRoleList);
        if (CollUtil.isEmpty(sysMenu.getTypeList())) {
            sysMenu.setTypeList(Arrays.asList(MenuTypeConstants.DIRECTORY, MenuTypeConstants.MENU));
        }
        List<SysMenu> superMenuList = sysMenuService.getSuperMenuList(sysMenu);
        superMenuList = superMenuList.stream().map(i -> {
            sysMenu.setParentId(i.getId());
            return sysMenuService.getChild(i,sysMenu);
        }).collect(Collectors.toList());
        return BaseResult.success(superMenuList);
    }

    @GetMapping("getPageBreadcrumb")
    public BaseResult<List<SysMenu>> getPageBreadcrumb(String routerPath) {
        return BaseResult.success(sysMenuService.getPageBreadcrumb(routerPath));
    }

    @GetMapping("getRouterData")
    public BaseResult<List<SysMenu>> getRouterData() {
        return BaseResult.success(sysMenuService.getRouterData());
    }

    @GetMapping("getMenuTableData")
    public BaseResult<TableDataInfo<SysMenu>> getMenuTableData(SysMenu sysMenu){
        List<String> userRoleList = sysRoleUserService.getUserRoleList(AdminUtil.getLoginId());
        sysMenu.setPermissionList(userRoleList);
        PageUtil.startPage(sysMenu);
        List<SysMenu> superMenuList = sysMenuService.getSuperMenuList(sysMenu);
        SysMenu queryParam = BeanUtil.copyProperties(sysMenu,SysMenu.class,"pageNum","pageSize");
        superMenuList = superMenuList.stream().map(i -> {
            queryParam.setParentId(i.getId());
            return sysMenuService.getChild(i,queryParam);
        }).collect(Collectors.toList());
        return BaseResult.success(PageUtil.getTableData(superMenuList));
    }

    @PostMapping("createOrEditMenu")
    public BaseResult<?> createOrEditMenu(@RequestBody SysMenu sysMenu) {
        if (sysMenu.getId()!=null){
            sysMenu.setUpdateTime(new Date());
            sysMenu.setUpdateBy(AdminUtil.getLoginId());
            return toResult(sysMenuService.updateMenu(sysMenu));
        }
        sysMenu.setCreateTime(new Date());
        sysMenu.setCreateBy(AdminUtil.getLoginId());
        return toResult(sysMenuService.createMenu(sysMenu));
    }

    @PostMapping("/deleteById")
    public BaseResult<?> deleteById(@RequestBody SysMenu sysMenu){
        return toResult(sysMenuService.deleteById(sysMenu));
    }

    @GetMapping("getMenuInfo")
    public BaseResult<SysMenu> getMenuInfo(SysMenu sysMenu){
        return BaseResult.success(sysMenuService.getMenuInfo(sysMenu));
    }
}
