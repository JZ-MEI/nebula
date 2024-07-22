package com.nebula.controller.monitor;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.nebula.common.common.BaseResult;
import com.nebula.common.entity.PageDomain;
import com.nebula.common.entity.TableDataInfo;
import com.nebula.common.util.BaseController;
import com.nebula.common.util.ExcelReader;
import com.nebula.common.util.PageUtil;
import com.nebula.service.domain.monitor.SysLoginLog;
import com.nebula.service.mapper.SysLoginLogMapper;
import com.nebula.service.service.SysLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/loginLog")
public class SysLoginLogController extends BaseController {

    @Autowired
    SysLoginLogService sysLoginLogService;

    @GetMapping("getTableData")
    public BaseResult<TableDataInfo<SysLoginLog>> getTableData(SysLoginLog sysLoginLog){
        PageUtil.startPage(sysLoginLog);
        List<SysLoginLog> loginLogList = sysLoginLogService.getLoginLogList(sysLoginLog);
        return BaseResult.success(PageUtil.getTableData(loginLogList));
    }
}
