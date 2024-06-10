package com.nebula.task.domain;

import lombok.Data;

import java.lang.reflect.Method;

@Data
public class TaskDomain {
    private Integer id;
    private String jobName;
    private String jobHandler;

    private String cron;

    private Method method;

    private String jobParams;
}
