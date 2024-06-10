package com.nebula.service.service;

import com.nebula.service.domain.system.SysDept;

import java.util.List;

public interface SysDeptService {

    List<SysDept> getRootDept(SysDept sysDept);

    SysDept getChild(SysDept superDept,SysDept queryParam);

    int createOrUpdateDeptInfo(SysDept sysDept);

    int changeDeptStatus(SysDept sysDept);

    String getDeptName(Integer id);
}
