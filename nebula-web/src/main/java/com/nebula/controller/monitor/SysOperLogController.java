package com.nebula.controller.monitor;

import com.nebula.common.common.BaseResult;
import com.nebula.common.entity.TableDataInfo;
import com.nebula.common.util.BaseController;
import com.nebula.common.util.PageUtil;
import com.nebula.service.domain.monitor.SysOperLog;
import com.nebula.service.service.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sysOperLog")
public class SysOperLogController extends BaseController {

    @Autowired
    SysOperLogService sysOperLogService;

    @GetMapping("getTableData")
    public BaseResult<TableDataInfo<SysOperLog>> getTableData(SysOperLog sysOperLog){
        PageUtil.startPage(sysOperLog);
        List<SysOperLog> sysOperLogData = sysOperLogService.getSysOperLogData(sysOperLog);
        return BaseResult.success(PageUtil.getTableData(sysOperLogData));
    }
}
