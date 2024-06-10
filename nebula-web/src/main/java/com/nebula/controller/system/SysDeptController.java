package com.nebula.controller.system;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.nebula.common.annotation.Authorization;
import com.nebula.common.common.BaseResult;
import com.nebula.common.entity.TableDataInfo;
import com.nebula.common.util.BaseController;
import com.nebula.common.util.PageUtil;
import com.nebula.service.domain.system.SysDept;
import com.nebula.service.domain.system.SysUserDept;
import com.nebula.service.domain.system.SysUserInfo;
import com.nebula.service.service.SysDeptService;
import com.nebula.service.service.SysUserDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("sysDept")
public class SysDeptController extends BaseController {

    @Autowired
    SysDeptService sysDeptService;
    @Autowired
    SysUserDeptService sysUserDeptService;

    @GetMapping("getDeptTableData")
    public BaseResult<TableDataInfo<SysDept>> getDeptTableData(SysDept sysDept){
        PageUtil.startPage(sysDept);
        List<SysDept> rootDeptList = sysDeptService.getRootDept(sysDept);
        SysDept queryParam = BeanUtil.copyProperties(sysDept, SysDept.class, "pageNum", "pageSize");
        rootDeptList = rootDeptList.stream().map(i -> {
            queryParam.setParentId(i.getId());
            return sysDeptService.getChild(i, queryParam);
        }).collect(Collectors.toList());
        return BaseResult.success(PageUtil.getTableData(rootDeptList));
    }

    @Authorization("test2")
    @PostMapping("createOrEditDeptInfo")
    public BaseResult<?> createOrEditDeptInfo(@RequestBody SysDept sysDept){
        return toResult(sysDeptService.createOrUpdateDeptInfo(sysDept));
    }

    @PostMapping("changeDeptStatus")
    public BaseResult<?> changeDeptStatus(@RequestBody SysDept sysDept){
        return toResult(sysDeptService.changeDeptStatus(sysDept));
    }

    @GetMapping("getDeptName/{id}")
    public BaseResult<String> getDeptName(@PathVariable("id")Integer id){
        return BaseResult.success(StrUtil.EMPTY,sysDeptService.getDeptName(id));
    }

    @GetMapping("getDeptUser")
    public BaseResult<TableDataInfo<SysUserInfo>> getDeptUser(SysUserDept sysUserDept){
        PageUtil.startPage(sysUserDept);
        List<SysUserInfo> deptUser = sysUserDeptService.getDeptUser(sysUserDept);
        return BaseResult.success(PageUtil.getTableData(deptUser));
    }

    @PostMapping("addOrEditUserToDept")
    public BaseResult<?> addOrEditUserToDept(@RequestBody SysUserDept sysUserDept){
        return toResult(sysUserDeptService.addOrEditUserToDept(sysUserDept));
    }

    @PostMapping("removeUserFormDept")
    public BaseResult<?> removeUserFormDept(@RequestBody SysUserDept sysUserDept){
        return toResult(sysUserDeptService.removeUserFormDept(sysUserDept));
    }
}
