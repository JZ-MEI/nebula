package com.nebula.framework.aspect;


import com.nebula.common.util.AdminUtil;
import com.nebula.framework.context.SqlLogContext;
import com.nebula.framework.context.UserContext;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SqlLogAspect {

    @Before("@annotation(com.nebula.common.annotation.SqlLog)")
    public void setUserId() {
        // 获取当前登录用户的ID
        Integer loginId = AdminUtil.getLoginId();
        UserContext.setUserId(loginId);
        SqlLogContext.startLog();
    }

    @After("@annotation(com.nebula.common.annotation.SqlLog)")
    public void clearUserId() {
        // 清除ThreadLocal中的用户ID
        UserContext.clear();
        SqlLogContext.endLog();
    }
}
