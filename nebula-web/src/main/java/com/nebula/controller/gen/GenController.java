package com.nebula.controller.gen;

import com.nebula.common.common.BaseResult;
import com.nebula.common.util.BaseController;
import com.nebula.service.domain.gen.TableColumsInfo;
import com.nebula.service.domain.gen.TableInfo;
import com.nebula.service.domain.system.SysUserInfo;
import com.nebula.service.service.GenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("gen")
public class GenController extends BaseController {

    @Autowired
    GenService genService;
    @GetMapping("getTableName")
    public BaseResult<List<TableInfo>> getTableName(){
        List<TableInfo> tableList = genService.getTableList();
        return BaseResult.success(tableList);
    }

    @GetMapping("getTableColumns")
    public BaseResult<List<TableColumsInfo>> getTableColumnsList(TableInfo tableInfo){
        List<TableColumsInfo> tableColumnsList = genService.getTableColumnsList(tableInfo.getTableName());
        return BaseResult.success(tableColumnsList);
    }

    @PostMapping("genCode")
    public void genCode(HttpServletResponse response, @RequestBody TableInfo tableInfo){
        genService.genCode(response,tableInfo.getTableName());
    }
}
