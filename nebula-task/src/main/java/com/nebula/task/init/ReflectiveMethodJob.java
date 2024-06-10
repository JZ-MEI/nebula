package com.nebula.task.init;

import cn.hutool.core.util.StrUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.lang.reflect.Method;

public class ReflectiveMethodJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            // 从 JobDataMap 中获取方法和实例
            Method method = (Method) context.getJobDetail().getJobDataMap().get("method");
            Object instance = context.getJobDetail().getJobDataMap().get("instance");
            String jobParams = (String) context.getJobDetail().getJobDataMap().get("jobParams");
            if (StrUtil.isNotBlank(jobParams)) {
                method.invoke(instance, jobParams);
            } else {
                // 调用方法
                method.invoke(instance);
            }
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }
    }
}