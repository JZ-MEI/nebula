package com.nebula.task.service.impl;

import com.nebula.task.domain.SysJobLog;
import com.nebula.task.mapper.SysJobLogMapper;
import com.nebula.task.service.JobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobLogServiceImpl implements JobLogService {
    @Autowired
    SysJobLogMapper sysJobLogMapper;
    @Override
    public int insertJobLog(SysJobLog sysJobLog) {
        return sysJobLogMapper.insert(sysJobLog);
    }


    @Override
    public List<SysJobLog> getSysJobLog(SysJobLog sysJobLog) {
         return sysJobLogMapper.getSysJobLogs(sysJobLog);
    }
}
