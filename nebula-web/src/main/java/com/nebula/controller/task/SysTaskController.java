package com.nebula.controller.task;

import com.nebula.common.common.BaseResult;
import com.nebula.common.entity.TableDataInfo;
import com.nebula.common.util.BaseController;
import com.nebula.common.util.PageUtil;
import com.nebula.service.service.SysTaskService;
import com.nebula.task.domain.SysTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("task")
public class SysTaskController extends BaseController {

    @Autowired
    SysTaskService sysTaskService;

    @GetMapping("getTableData")
    public BaseResult<TableDataInfo<SysTask>> getTableData(SysTask sysTask) {
        PageUtil.startPage(sysTask);
        List<SysTask> taskList = sysTaskService.getTaskList(sysTask);
        return BaseResult.success(PageUtil.getTableData(taskList));
    }

    @PostMapping("modifyTaskOpen")
    public BaseResult<?> modifyTaskOpen(@RequestBody SysTask sysTask) throws Exception {
        sysTaskService.modifyTaskOpen(sysTask);
        return BaseResult.success();
    }

    @PostMapping("createOrEditTask")
    public BaseResult<?> createOrEditTask(@RequestBody SysTask sysTask) throws Exception {
        return toResult(sysTaskService.createOrEditTask(sysTask));
    }
}
