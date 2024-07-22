package com.nebula.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nebula.service.domain.monitor.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {

    List<SysOperLog> getSysOperLog(SysOperLog sysOperLog);
}
