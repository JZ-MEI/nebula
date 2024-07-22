package com.nebula.service.interceptor;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nebula.framework.context.SqlLogContext;
import com.nebula.framework.context.UserContext;
import com.nebula.service.domain.monitor.SysOperLog;
import com.nebula.service.service.SysOperLogService;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Properties;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
@Component
public class SqlLogInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (!SqlLogContext.isLog()){
            return invocation.proceed();
        }
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        String sqlId = mappedStatement.getId();
        String sqlCommandType = mappedStatement.getSqlCommandType().toString();
        Integer userId = UserContext.getUserId();
        Object parameter = invocation.getArgs().length > 1 ? invocation.getArgs()[1] : null;
        JSONObject paramJson = JSONUtil.parseObj(parameter);
        String sqlParam = JSONUtil.toJsonStr(paramJson);

        SysOperLog sysOperLog = new SysOperLog();
        sysOperLog.setSqlId(sqlId);
        sysOperLog.setUserId(userId);
        sysOperLog.setOperTime(new Date());
        sysOperLog.setCommandType(sqlCommandType);
        sysOperLog.setSqlParam(sqlParam);
        SysOperLogService bean = SpringUtil.getBean(SysOperLogService.class);
        bean.insertLog(sysOperLog);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }
}
