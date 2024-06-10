package com.nebula.framework.aspect;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.json.JSONUtil;
import com.nebula.common.annotation.Authorization;
import com.nebula.common.util.AdminUtil;
import com.nebula.framework.exception.PermissionException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
public class AuthorizationAspect {

    @Autowired
    StpInterface stpInterface;

    @Pointcut("@annotation(com.nebula.common.annotation.Authorization)")
    private void Authorization(){

    }

    @Before("Authorization()")
    public void before(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Authorization annotation = signature.getMethod().getAnnotation(Authorization.class);
        String methodAuth = annotation.value();
        List<String> permissionList = stpInterface.getPermissionList(AdminUtil.getLoginId(), null);
        System.out.println(JSONUtil.toJsonStr(permissionList));
        if (permissionList.contains(methodAuth)){
            return;
        }
        throw new PermissionException("当前访问没有权限");

    }
}
