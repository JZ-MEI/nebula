package com.nebula.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nebula.service.domain.system.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {
    List<SysDept> getSysDeptList(SysDept sysDept);

    int changeDeptStatus(SysDept sysDept);

    String getDeptNameById(@Param("id")Integer id);
}
