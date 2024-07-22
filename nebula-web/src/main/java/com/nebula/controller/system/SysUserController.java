package com.nebula.controller.system;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.nebula.common.common.BaseResult;
import com.nebula.common.entity.TableDataInfo;
import com.nebula.common.util.AdminUtil;
import com.nebula.common.util.BaseController;
import com.nebula.common.util.PageUtil;
import com.nebula.service.domain.system.SysUserInfo;
import com.nebula.service.service.SysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sysUser")
public class SysUserController extends BaseController {

    @Autowired
    SysUserInfoService sysUserInfoService;

    @GetMapping("getUserTableData")
    public BaseResult<TableDataInfo<SysUserInfo>> getUserTableData(SysUserInfo sysUserInfo) {
        PageUtil.startPage(sysUserInfo);
        List<SysUserInfo> userInfoList = sysUserInfoService.getSysUserInfo(sysUserInfo);
        return BaseResult.success(PageUtil.getTableData(userInfoList));

    }

    @SaCheckPermission("user.add")
    @PostMapping("createOrEditUserInfo")
    public BaseResult<?> createOrEditUserInfo(@RequestBody SysUserInfo sysUserInfo) {
        return toResult(sysUserInfoService.createOrEditUserInfo(sysUserInfo));
    }


    @PostMapping("modifyPwd")
    public BaseResult<?> modifyPwd(@RequestBody SysUserInfo sysUserInfo) {
        return toResult(sysUserInfoService.modifyPwd(sysUserInfo));
    }

    @GetMapping("getNormalUser")
    public BaseResult<List<SysUserInfo>> getNormalUser(){
        return BaseResult.success(sysUserInfoService.getNormalUser());
    }

    @GetMapping("getPersonalInfo")
    public BaseResult<SysUserInfo> getUserAvatar(){
        SysUserInfo personalInfo = sysUserInfoService.getPersonalInfo();
        personalInfo.setPassword(null);
        return BaseResult.success(personalInfo);
    }

    @PostMapping("modifyPersonalInfo")
    public BaseResult<?> modifyAvatar(@RequestBody SysUserInfo sysUserInfo){
        return toResult(sysUserInfoService.modifyPersonalInfo(sysUserInfo));
    }

    @PostMapping("modifyPassword")
    public BaseResult<?> modifyPassword(@RequestBody String data){
        return toResult(sysUserInfoService.modifyPassword(data));
    }

}
