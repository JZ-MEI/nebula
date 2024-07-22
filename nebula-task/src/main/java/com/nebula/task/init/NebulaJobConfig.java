package com.nebula.task.init;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;

import com.nebula.task.annotation.NebulaJob;
import com.nebula.task.domain.SysTask;
import com.nebula.task.domain.TaskDomain;
import com.nebula.task.service.TaskService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Component
public class NebulaJobConfig {
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskScheduler taskScheduler;

    private List<Method> jobHandler = new ArrayList<>();


    @PostConstruct
    public void init() throws Exception {
        // 遍历Spring上下文中所有的Bean
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        List<Method> methodList = new ArrayList<>();
        for (String beanName : beanNames) {
            Object bean = applicationContext.getBean(beanName);
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(NebulaJob.class)) {
                    methodList.add(method);
                }
            }
        }
        List<TaskDomain> dbJobInfo = getDbJobInfo(methodList);
        taskScheduler.initTask(dbJobInfo);
    }

    private List<TaskDomain> getDbJobInfo(List<Method> methodList) {
        List<String> jobList = methodList.stream().map(i -> AnnotationUtil.getAnnotation(i, NebulaJob.class).jobName()).collect(Collectors.toList());
        SysTask sysTask = new SysTask();
        sysTask.setJobList(jobList);
        sysTask.setIsOpen(1);
        List<SysTask> taskList = taskService.getTaskList(sysTask);

        List<TaskDomain> sysJobHandlerList = new ArrayList<>();
        for (SysTask task : taskList) {
            TaskDomain taskDomain = new TaskDomain();
            BeanUtil.copyProperties(task, taskDomain);
            Method method = methodList.stream().filter(i -> {
                boolean equals = StrUtil.equals(AnnotationUtil.getAnnotation(i, NebulaJob.class).jobName(), task.getJobHandler());
                return equals;
            }).findFirst().get();
            taskDomain.setMethod(method);
            sysJobHandlerList.add(taskDomain);
        }
        return sysJobHandlerList;

    }

}
