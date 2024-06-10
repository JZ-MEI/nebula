package com.nebula.task.service.impl;

import com.nebula.task.domain.SysTask;
import com.nebula.task.mapper.TaskMapper;
import com.nebula.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskMapper taskMapper;
    @Override
    public List<SysTask> getTaskList(SysTask sysTask) {
        return taskMapper.getSysTaskList(sysTask);
    }
}
