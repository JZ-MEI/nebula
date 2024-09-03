package com.nebula.controller.gen;

import com.nebula.common.common.BaseResult;
import com.nebula.common.util.BaseController;
import com.nebula.service.domain.gen.TableColumsInfo;
import com.nebula.service.domain.gen.TableInfo;
import com.nebula.service.service.GenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("gen")
public class GenController extends BaseController {

    @Autowired
    GenService genService;

    /**
     * 获取所有的表名
     * @return 表名的List
     */
    @GetMapping("getTableName")
    public BaseResult<List<TableInfo>> getTableName(){
        List<TableInfo> tableList = genService.getTableList();
        return BaseResult.success(tableList);
    }

    /**
     * 根据表名获取表字段
     * @param tableInfo 表信息
     * @return 表字段列表
     */
    @GetMapping("getTableColumns")
    public BaseResult<List<TableColumsInfo>> getTableColumnsList(TableInfo tableInfo){
        List<TableColumsInfo> tableColumnsList = genService.getTableColumnsList(tableInfo.getTableName());
        return BaseResult.success(tableColumnsList);
    }

    @PostMapping("genCode")
    public BaseResult<Map<String,Object>> genCode(@RequestBody TableInfo tableInfo){

        Map<String, Object> result = genService.genCode(tableInfo);
        return BaseResult.success(result);

//        genService.genCode(response,tableInfo.getTableName());
    }

    @GetMapping("downloadCode")
    public void downloadCode(HttpServletResponse response,TableInfo tableInfo) throws IOException {
        genService.downloadCode(response,tableInfo);
    }
}
