package com.nebula.task.service;

import com.nebula.task.domain.SysTask;

import java.util.List;

public interface TaskService {
    List<SysTask> getTaskList(SysTask sysTask);
}
