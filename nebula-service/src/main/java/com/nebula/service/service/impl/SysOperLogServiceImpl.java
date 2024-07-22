package com.nebula.service.service.impl;

import com.nebula.service.domain.monitor.SysOperLog;
import com.nebula.service.mapper.SysOperLogMapper;
import com.nebula.service.service.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysOperLogServiceImpl implements SysOperLogService {

    @Autowired
    SysOperLogMapper sysOperLogMapper;

    @Async
    @Override
    public void insertLog(SysOperLog sysOperLog) {
       sysOperLogMapper.insert(sysOperLog);
    }


    @Override
    public List<SysOperLog> getSysOperLogData(SysOperLog sysOperLog) {
        return sysOperLogMapper.getSysOperLog(sysOperLog);
    }
}
