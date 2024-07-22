package com.nebula.service.service;

import com.nebula.service.domain.monitor.SysLoginLog;

import java.util.List;

public interface SysLoginLogService {

    int insert(SysLoginLog sysLoginLog);

    List<SysLoginLog> getLoginLogList(SysLoginLog sysLoginLog);
}
