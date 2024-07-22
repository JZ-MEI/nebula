package com.nebula.task.init;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.nebula.common.common.BaseResult;
import com.nebula.task.domain.SysJobLog;
import com.nebula.task.service.JobLogService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class ReflectiveMethodJob implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        Method method = (Method) context.getJobDetail().getJobDataMap().get("method");
        Object instance = context.getJobDetail().getJobDataMap().get("instance");
        String jobParams = (String) context.getJobDetail().getJobDataMap().get("jobParams");
        SysJobLog sysJobLog = new SysJobLog();
        sysJobLog.setJobHandler(method.getName());
        sysJobLog.setExecuteTime(new Date());
        sysJobLog.setJobId((Integer)context.getJobDetail().getJobDataMap().get("jobId"));
        try {
            BaseResult<?> result;
            // 从 JobDataMap 中获取方法和实例
            if (StrUtil.isNotBlank(jobParams)) {
                result = (BaseResult<?>) method.invoke(instance, jobParams);
            } else {
                // 调用方法
                result = (BaseResult<?>) method.invoke(instance);
            }
            sysJobLog.setDispatchResult(result.isSuccess() ? 1 : 0);
            sysJobLog.setExecuteResult(result.isSuccess() ? 1 : 0);
            sysJobLog.setExecuteErrorReason(result.isSuccess()?null:result.getMsg());
        } catch (Exception e) {
            sysJobLog.setDispatchResult(0);
            sysJobLog.setExecuteErrorReason(e.getMessage());
        }finally {
            JobLogService jobLogService = SpringUtil.getBean(JobLogService.class);
            jobLogService.insertJobLog(sysJobLog);
        }
    }
}