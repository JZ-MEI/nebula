package com.nebula.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nebula.task.domain.SysJobLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysJobLogMapper extends BaseMapper<SysJobLog> {

    List<SysJobLog> getSysJobLogs(SysJobLog sysJobLog);
}
