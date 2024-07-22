package com.nebula.task.service;

import com.nebula.task.domain.SysJobLog;
import com.nebula.task.mapper.SysJobLogMapper;

import java.util.List;

public interface JobLogService {

    int insertJobLog(SysJobLog sysJobLog);

    List<SysJobLog> getSysJobLog(SysJobLog sysJobLog);
}
