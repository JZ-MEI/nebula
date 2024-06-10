package com.nebula.service.service;

import com.nebula.service.domain.system.SysDictData;
import com.nebula.service.domain.system.SysDictType;

import java.util.List;
import java.util.Map;

public interface SysDictService {

    List<SysDictType> getSysDictType(SysDictType sysDictType);

    Map<String,List<SysDictData>> getInitDictData();

    List<SysDictData> getSysDictData(SysDictData sysDictData);

    int deleteDictTypeById(SysDictType sysDictType);

    int createOrEditDictType(SysDictType sysDictType);

    int createOrEditDictData(SysDictData sysDictData);

    int deleteDictDataById(SysDictData sysDictData);
}
