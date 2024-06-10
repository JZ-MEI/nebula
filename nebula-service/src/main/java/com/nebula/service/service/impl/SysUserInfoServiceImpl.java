package com.nebula.service.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nebula.common.constants.UserInfoConstants;
import com.nebula.common.util.AdminUtil;
import com.nebula.framework.exception.LoginException;
import com.nebula.service.domain.system.SysUserInfo;
import com.nebula.service.mapper.SysUserInfoMapper;
import com.nebula.service.service.SysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class SysUserInfoServiceImpl implements SysUserInfoService{
    @Autowired
    SysUserInfoMapper sysUserInfoMapper;

    @Override
    public Integer userLogin(SysUserInfo sysUserInfo) {
        SysUserInfo userInfo = sysUserInfoMapper.getUserInfoByUsername(sysUserInfo.getUsername());
        if (BeanUtil.isEmpty(userInfo)||NumberUtil.equals(userInfo.getIsDeleted(), UserInfoConstants.USER_IS_DELETED)){
            throw new LoginException(StrUtil.format("当前登录用户名为{}的用户不存在，请检查用户名",sysUserInfo.getUsername()));
        }
        if (NumberUtil.equals(userInfo.getUserStatus(), UserInfoConstants.USER_IS_LIMIT)){
            throw new LoginException(StrUtil.format("当前用户{}已停用，请联系管理员",sysUserInfo.getUsername()));
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matches = bCryptPasswordEncoder.matches(sysUserInfo.getPassword(), userInfo.getPassword());
        if (!matches){
            throw new LoginException(StrUtil.format("当前用户{}登录密码错误，请重试",sysUserInfo.getUsername()));
        }
        return userInfo.getId();
    }


    @Override
    public List<SysUserInfo> getSysUserInfo(SysUserInfo sysUserInfo) {
        return sysUserInfoMapper.getSysUserInfo(sysUserInfo);
    }

    @Override
    public int createOrEditUserInfo(SysUserInfo sysUserInfo) {
        if (sysUserInfo.getId()!=null){
            sysUserInfo.setUpdateTime(new Date());
            sysUserInfo.setUpdateBy(AdminUtil.getLoginId());
            return sysUserInfoMapper.updateById(sysUserInfo);
        }
        sysUserInfo.setCreateTime(new Date());
        sysUserInfo.setCreateBy(AdminUtil.getLoginId());
        sysUserInfo.setUserStatus(0);

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePwd = bCryptPasswordEncoder.encode(sysUserInfo.getPassword());
        sysUserInfo.setPassword(encodePwd);
        return sysUserInfoMapper.insert(sysUserInfo);
    }

    @Override
    public int modifyPwd(SysUserInfo sysUserInfo) {
        sysUserInfo.setUpdateTime(new Date());
        sysUserInfo.setUpdateBy(AdminUtil.getLoginId());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePwd = bCryptPasswordEncoder.encode(sysUserInfo.getPassword());
        sysUserInfo.setPassword(encodePwd);
        return sysUserInfoMapper.updateById(sysUserInfo);
    }

    @Override
    public List<SysUserInfo> getNormalUser() {
        SysUserInfo sysUserInfo = new SysUserInfo();
        sysUserInfo.setUserStatus(0);
        sysUserInfo.setIsDeleted(0);
        return sysUserInfoMapper.getSysUserInfo(sysUserInfo);
    }

    @Override
    public SysUserInfo getUserById(Integer id) {
        return sysUserInfoMapper.selectById(id);
    }

    @Override
    public SysUserInfo getPersonalInfo() {
        Integer loginId = AdminUtil.getLoginId();
        return sysUserInfoMapper.selectById(loginId);
    }

    @Override
    public int modifyPersonalInfo(SysUserInfo sysUserInfo) {
        sysUserInfo.setId(AdminUtil.getLoginId());
        sysUserInfo.setUpdateTime(new Date());
        sysUserInfo.setUpdateBy(AdminUtil.getLoginId());
        return sysUserInfoMapper.updateById(sysUserInfo);
    }

    @Override
    public int modifyPassword(String data) {
        JSONObject params = JSONUtil.parseObj(data);
        String currentPwd = params.getStr("currentPwd");
        Integer loginId = AdminUtil.getLoginId();
        SysUserInfo sysUserInfo = sysUserInfoMapper.selectById(loginId);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        boolean matches = bCryptPasswordEncoder.matches(currentPwd, sysUserInfo.getPassword());
        if (!matches){
            throw new LoginException("当前密码错误，请重新输入");
        }
        String newPwd = params.getStr("newPwd");
        String encode = bCryptPasswordEncoder.encode(newPwd);
        SysUserInfo userInfo = new SysUserInfo();
        userInfo.setId(loginId);
        userInfo.setPassword(encode);
        userInfo.setUpdateBy(loginId);
        userInfo.setUpdateTime(new Date());
        return sysUserInfoMapper.updateById(userInfo);
    }
}
