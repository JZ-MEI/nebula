package com.nebula.service.service;

import com.nebula.service.domain.monitor.SysOperLog;

import java.util.List;

public interface SysOperLogService {

    void insertLog(SysOperLog sysOperLog);

    List<SysOperLog> getSysOperLogData(SysOperLog sysOperLog);
}
