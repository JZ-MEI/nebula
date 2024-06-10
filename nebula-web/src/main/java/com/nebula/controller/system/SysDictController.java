package com.nebula.controller.system;

import com.nebula.common.common.BaseResult;
import com.nebula.common.entity.TableDataInfo;
import com.nebula.common.util.BaseController;
import com.nebula.common.util.PageUtil;
import com.nebula.service.domain.system.SysDictData;
import com.nebula.service.domain.system.SysDictType;
import com.nebula.service.service.SysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/sysDict")
public class SysDictController extends BaseController {
    @Autowired
    SysDictService sysDictService;

    @GetMapping("/getDictTableData")
    public BaseResult<TableDataInfo<SysDictType>> getDictTableData(SysDictType sysDictType) {
        PageUtil.startPage(sysDictType);
        List<SysDictType> dictTypeList = sysDictService.getSysDictType(sysDictType);
        return BaseResult.success(PageUtil.getTableData(dictTypeList));
    }

    @GetMapping("/getInitDictData")
    public BaseResult<Map<String, List<SysDictData>>> getDictData() {
        return BaseResult.success(sysDictService.getInitDictData());
    }

    @GetMapping("/getDictData")
    public BaseResult<TableDataInfo<SysDictData>> getDictData(SysDictData sysDictData){
        PageUtil.startPage(sysDictData);
        List<SysDictData> dictDataList = sysDictService.getSysDictData(sysDictData);
        return BaseResult.success(PageUtil.getTableData(dictDataList));
    }

    @PostMapping("/deleteDictType")
    public BaseResult<?> deleteDictType(@RequestBody SysDictType sysDictType){
        return toResult(sysDictService.deleteDictTypeById(sysDictType));
    }

    @PostMapping("createOrEditDictType")
    public BaseResult<?> createOrEditDictType(@RequestBody SysDictType sysDictType){
        return toResult(sysDictService.createOrEditDictType(sysDictType));
    }
    @PostMapping("createOrEditDictData")
    public BaseResult<?> createOrEditDictData(@RequestBody SysDictData sysDictData){
        return toResult(sysDictService.createOrEditDictData(sysDictData));
    }

    @PostMapping("/deleteDictData")
    public BaseResult<?> deleteDictData(@RequestBody SysDictData sysDictData){
        return toResult(sysDictService.deleteDictDataById(sysDictData));
    }
}
