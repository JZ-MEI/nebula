package com.nebula.service.service.impl;

import com.nebula.service.domain.monitor.SysLoginLog;
import com.nebula.service.mapper.SysLoginLogMapper;
import com.nebula.service.service.SysLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysLoginLogServiceImpl implements SysLoginLogService {
    @Autowired
    SysLoginLogMapper sysLoginLogMapper;

    @Override
    public int insert(SysLoginLog sysLoginLog) {
        return sysLoginLogMapper.insert(sysLoginLog);
    }

    @Override
    public List<SysLoginLog> getLoginLogList(SysLoginLog sysLoginLog) {
        return sysLoginLogMapper.getLoginLogList(sysLoginLog);
    }
}
