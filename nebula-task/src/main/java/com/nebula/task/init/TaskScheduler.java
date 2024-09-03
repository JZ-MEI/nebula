package com.nebula.task.init;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.nebula.task.domain.TaskDomain;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class TaskScheduler {
    public void initTask(List<TaskDomain> taskList) throws Exception {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        for (TaskDomain task : taskList) {

            Class<?> methodClass = task.getMethod().getDeclaringClass();
            Method method = task.getMethod();
            Object instance = methodClass.getDeclaredConstructor().newInstance();

            Map<String,Object> map = new HashMap<>();
            map.put("method", method);
            map.put("instance", instance);
            map.put("jobId",task.getId());
            if (StrUtil.isNotBlank(task.getJobParams())){
                map.put("jobParams",task.getJobParams());
            }
            JobDataMap jobDataMap = new JobDataMap(map);
            // 创建 JobDetail，传递方法和实例
            JobDetail jobDetail = JobBuilder.newJob(ReflectiveMethodJob.class)
                    .withIdentity(Convert.toStr(task.getId()))
                    .usingJobData(jobDataMap)
                    .build();

            // 创建 Trigger，根据 cron 表达式配置调度
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(Convert.toStr(task.getId()))
                    .withSchedule(CronScheduleBuilder.cronSchedule(task.getCron()))
                    .build();

            // 调度任务
            scheduler.scheduleJob(jobDetail, trigger);
        }
    }

}
