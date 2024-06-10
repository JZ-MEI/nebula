package com.nebula.controller.system;

import com.nebula.common.common.BaseResult;
import com.nebula.common.entity.TableDataInfo;
import com.nebula.common.util.BaseController;
import com.nebula.common.util.PageUtil;
import com.nebula.service.domain.system.SysRole;
import com.nebula.service.domain.system.SysRoleMenu;
import com.nebula.service.domain.system.SysRoleUser;
import com.nebula.service.domain.system.SysUserInfo;
import com.nebula.service.service.SysRoleMenuService;
import com.nebula.service.service.SysRoleService;
import com.nebula.service.service.SysRoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sysRole")
public class SysRoleController extends BaseController {

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysRoleMenuService sysRoleMenuService;

    @Autowired
    SysRoleUserService sysRoleUserService;

    @GetMapping("getRoleList")
    public BaseResult<TableDataInfo<SysRole>> getRoleList(SysRole sysRole) {
        PageUtil.startPage(sysRole);
        List<SysRole> roleList = sysRoleService.getRoleList(sysRole);
        return BaseResult.success(PageUtil.getTableData(roleList));
    }

    @PostMapping("createOrUpdateSysRole")
    public BaseResult<?> createOrUpdateSysRole(@RequestBody SysRole sysRole){
        return toResult(sysRoleService.createOrUpdateSysRole(sysRole));
    }

    @GetMapping("getRoleMenuList")
    public BaseResult<List<Integer>> getRoleMenuList(SysRoleMenu sysRoleMenu){
        return BaseResult.success(sysRoleMenuService.getRoleMenuList(sysRoleMenu));
    }
    @PostMapping("authorization")
    public BaseResult<?> authorization(@RequestBody SysRoleMenu sysRoleMenu){
        return toResult(sysRoleMenuService.authorization(sysRoleMenu));
    }

    @GetMapping("getRoleUserTableData")
    public BaseResult<TableDataInfo<SysUserInfo>> getRoleUserTableData(SysRoleUser sysRoleUser){
        PageUtil.startPage(sysRoleUser);
        List<SysUserInfo> roleUser = sysRoleUserService.getRoleUser(sysRoleUser);
        return BaseResult.success(PageUtil.getTableData(roleUser));
    }

    @PostMapping("createOrDeleteRoleUser")
    public BaseResult<?> createOrDeleteRoleUser(@RequestBody SysRoleUser sysRoleUser){
        return toResult(sysRoleUserService.createOrDeleteRoleUser(sysRoleUser));
    }
}
