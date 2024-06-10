package com.nebula.service.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.nebula.common.util.AdminUtil;
import com.nebula.task.annotation.NebulaJob;
import com.nebula.service.mapper.SysTaskMapper;
import com.nebula.service.service.SysTaskService;
import com.nebula.task.domain.SysTask;
import com.nebula.task.domain.TaskDomain;
import com.nebula.task.init.ReflectiveMethodJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysTaskServiceImpl implements SysTaskService {

    @Autowired
    SysTaskMapper sysTaskMapper;
    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public List<SysTask> getTaskList(SysTask sysTask) {
        return sysTaskMapper.getSysTaskList(sysTask);
    }

    @Override
    public void modifyTaskOpen(SysTask sysTask) throws Exception {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        SysTask task = sysTaskMapper.selectById(sysTask.getId());
        if (NumberUtil.equals(task.getIsOpen(), 1)) {
            pauseTask(task);
            sysTask.setIsOpen(NumberUtil.equals(0, task.getIsOpen()) ? 1 : 0);
            sysTaskMapper.updateById(sysTask);
            return;
        }

        String jobKey = Convert.toStr(sysTask.getId());
        boolean isExist = scheduler.checkExists(JobKey.jobKey(jobKey));
        if (isExist) {
            scheduler.resumeJob(JobKey.jobKey(jobKey));
            return;
        }
        sysTask.setIsOpen(NumberUtil.equals(0, task.getIsOpen()) ? 1 : 0);
        sysTaskMapper.updateById(sysTask);
        registerTask(task, scheduler);
    }

    @Override
    public int createOrEditTask(SysTask sysTask) throws Exception {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        if (sysTask.getId() == null) {
            sysTask.setCreateTime(new Date());
            sysTask.setCreateBy(AdminUtil.getLoginId());
            int insert = sysTaskMapper.insert(sysTask);
            if (NumberUtil.equals(sysTask.getIsOpen(), 1)) {
                registerTask(sysTask, scheduler);
            }
            return insert;
        }
        sysTask.setUpdateBy(AdminUtil.getLoginId());
        sysTask.setUpdateTime(new Date());
        SysTask existTask = sysTaskMapper.selectById(sysTask.getId());
        if (sysTask.getIsDeleted() == 1){
            scheduler.deleteJob(JobKey.jobKey(Convert.toStr(sysTask.getId())));
            return sysTaskMapper.updateById(sysTask);
        }
        if (StrUtil.equals(sysTask.getJobHandler(), existTask.getJobHandler())
                && StrUtil.equals(sysTask.getCron(), existTask.getCron())
                && NumberUtil.equals(sysTask.getIsOpen(), existTask.getIsOpen())
                && StrUtil.equals(sysTask.getJobParams(), existTask.getJobParams())) {
            return sysTaskMapper.updateById(sysTask);
        }
        scheduler.deleteJob(JobKey.jobKey(Convert.toStr(sysTask.getId())));
        registerTask(sysTask, scheduler);
        if (!NumberUtil.equals(sysTask.getIsOpen(), existTask.getIsOpen())) {
            modifyTaskOpen(sysTask);
        }
        return sysTaskMapper.updateById(sysTask);
    }

    private void pauseTask(SysTask sysTask) throws Exception {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.pauseJob(JobKey.jobKey(sysTask.getId()));
    }

    private void registerTask(SysTask sysTask, Scheduler scheduler) throws Exception {
        Method selectMethod = getSelectMethod(sysTask);
        if (!ObjectUtil.isNotNull(selectMethod)) {
            throw new RuntimeException(StrUtil.format("未找到{}对应的任务,请检查任务配置", sysTask.getJobName()));
        }
        TaskDomain taskDomain = BeanUtil.copyProperties(sysTask, TaskDomain.class);
        taskDomain.setMethod(selectMethod);

        Class<?> methodClass = taskDomain.getMethod().getDeclaringClass();
        Method method = taskDomain.getMethod();
        Object instance = methodClass.getDeclaredConstructor().newInstance();


        Map<String, Object> map = new HashMap<>();
        map.put("method", method);
        map.put("instance", instance);
        if (StrUtil.isNotBlank(taskDomain.getJobParams())) {
            map.put("jobParams", taskDomain.getJobParams());
        }
        JobDataMap jobDataMap = new JobDataMap(map);
        // 创建 JobDetail，传递方法和实例
        JobDetail jobDetail = JobBuilder.newJob(ReflectiveMethodJob.class)
                .withIdentity(Convert.toStr(taskDomain.getId()))
                .usingJobData(jobDataMap)
                .build();

        // 创建 Trigger，根据 cron 表达式配置调度
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(Convert.toStr(taskDomain.getId()))
                .withSchedule(CronScheduleBuilder.cronSchedule(taskDomain.getCron()))
                .build();

        // 调度任务
        scheduler.scheduleJob(jobDetail, trigger);
    }

    private void resumeTask(SysTask sysTask, Scheduler scheduler) throws Exception {
        String jobKey = Convert.toStr(sysTask.getId());
        boolean isExist = scheduler.checkExists(JobKey.jobKey(jobKey));
        if (isExist) {

        }
    }

    private Method getSelectMethod(SysTask sysTask) {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            Object bean = applicationContext.getBean(beanName);
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(NebulaJob.class) && StrUtil.equals(method.getAnnotation(NebulaJob.class).jobName(), sysTask.getJobHandler())) {
                    return method;
                }
            }
        }
        return null;
    }

}
