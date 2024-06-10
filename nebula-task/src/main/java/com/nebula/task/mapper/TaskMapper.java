package com.nebula.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nebula.task.domain.SysTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TaskMapper extends BaseMapper<SysTask> {

    List<SysTask> getSysTaskList(SysTask sysTask);
}
