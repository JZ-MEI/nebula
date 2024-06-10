package com.nebula.service.service;

import com.nebula.task.domain.SysTask;

import java.util.List;

public interface SysTaskService {

    List<SysTask> getTaskList(SysTask sysTask);

    void modifyTaskOpen(SysTask sysTask) throws Exception;

    int createOrEditTask(SysTask sysTask) throws Exception;
}
