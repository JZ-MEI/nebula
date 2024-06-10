package com.nebula.service.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.nebula.common.util.AdminUtil;
import com.nebula.service.domain.system.SysDept;
import com.nebula.service.mapper.SysDeptMapper;
import com.nebula.service.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    SysDeptMapper sysDeptMapper;
    @Override
    public List<SysDept> getRootDept(SysDept sysDept) {
        sysDept.setParentId(0);
        sysDept.setIsDelete(0);
        return sysDeptMapper.getSysDeptList(sysDept);
    }

    @Override
    public SysDept getChild(SysDept superDept,SysDept queryParam){
        List<SysDept> childDeptList = sysDeptMapper.getSysDeptList(queryParam);
        childDeptList = childDeptList.stream().peek(i->{
            queryParam.setParentId(i.getId());
            getChild(i,queryParam);
        }).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(childDeptList)){
            superDept.setChildList(childDeptList);
        }else{
            superDept.setChildList(null);
        }
        return superDept;
    }

    @Override
    public int createOrUpdateDeptInfo(SysDept sysDept) {
        if (sysDept.getId()!=null){
            sysDept.setUpdateTime(new Date());
            sysDept.setUpdateBy(AdminUtil.getLoginId());
            return sysDeptMapper.updateById(sysDept);
        }
        sysDept.setCreateTime(new Date());
        sysDept.setCreateBy(AdminUtil.getLoginId());
        sysDept.setDeptStatus(0);

        return sysDeptMapper.insert(sysDept);
    }

    @Override
    public int changeDeptStatus(SysDept sysDept) {
        sysDept.setUpdateTime(new Date());
        sysDept.setUpdateBy(AdminUtil.getLoginId());
        return sysDeptMapper.changeDeptStatus(sysDept);
    }

    @Override
    public String getDeptName(Integer id) {
        return sysDeptMapper.getDeptNameById(id);
    }
}
