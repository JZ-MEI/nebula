package com.nebula.service.service.impl;

import com.nebula.common.annotation.SqlLog;
import com.nebula.common.util.AdminUtil;
import com.nebula.service.domain.system.SysDictData;
import com.nebula.service.domain.system.SysDictType;
import com.nebula.service.mapper.SysDictDataMapper;
import com.nebula.service.mapper.SysDictTypeMapper;
import com.nebula.service.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysDictServiceImpl implements SysDictService {

    @Autowired
    SysDictTypeMapper sysDictTypeMapper;
    @Autowired
    SysDictDataMapper sysDictDataMapper;

    @Override
    public List<SysDictType> getSysDictType(SysDictType sysDictType) {
        return sysDictTypeMapper.getSysDictType(sysDictType);
    }

    @Override
    public Map<String, List<SysDictData>> getInitDictData() {
        Map<String, List<SysDictData>> resultMap = new HashMap<>();
        List<SysDictType> sysDictTypeList = sysDictTypeMapper.getSysDictType(new SysDictType());
        for (SysDictType sysDictType : sysDictTypeList) {
            SysDictData queryParam = new SysDictData();
            queryParam.setTypeId(sysDictType.getId());
            List<SysDictData> sysDictData = sysDictDataMapper.getSysDictData(queryParam);
            resultMap.put(sysDictType.getDictType(), sysDictData);
        }

        return resultMap;
    }

    @Override
    public List<SysDictData> getSysDictData(SysDictData sysDictData) {
        return sysDictDataMapper.getSysDictData(sysDictData);
    }

    @Override
    public int deleteDictTypeById(SysDictType sysDictType) {
        sysDictType.setIsDeleted(1);
        sysDictTypeMapper.updateById(sysDictType);
        sysDictDataMapper.deleteDictDataByTypeId(sysDictType.getId());
        return 1;
    }

    @SqlLog
    @Override
    public int createOrEditDictType(SysDictType sysDictType) {
        if (sysDictType.getId()!=null){
            sysDictType.setUpdateTime(new Date());
            sysDictType.setUpdateBy(AdminUtil.getLoginId());
            return sysDictTypeMapper.updateById(sysDictType);
        }
        sysDictType.setCreateTime(new Date());
        sysDictType.setCreateBy(AdminUtil.getLoginId());
        return sysDictTypeMapper.insert(sysDictType);
    }

    @Override
    public int createOrEditDictData(SysDictData sysDictData) {
        if (sysDictData.getId()!=null){
            sysDictData.setUpdateTime(new Date());
            sysDictData.setUpdateBy(AdminUtil.getLoginId());
            return sysDictDataMapper.updateById(sysDictData);
        }
        sysDictData.setCreateTime(new Date());
        sysDictData.setCreateBy(AdminUtil.getLoginId());
        return sysDictDataMapper.insert(sysDictData);
    }

    @Override
    public int deleteDictDataById(SysDictData sysDictData) {
        return sysDictDataMapper.deleteById(sysDictData);
    }
}
