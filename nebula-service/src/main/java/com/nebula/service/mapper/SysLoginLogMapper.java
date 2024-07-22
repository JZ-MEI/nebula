package com.nebula.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nebula.service.domain.monitor.SysLoginLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {

    List<SysLoginLog> getLoginLogList(SysLoginLog sysLoginLog);
}
