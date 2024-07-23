package com.nebula.service.service;

import com.nebula.service.domain.gen.TableColumsInfo;
import com.nebula.service.domain.gen.TableInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface GenService {

    List<TableInfo> getTableList();

    List<TableColumsInfo> getTableColumnsList(String tableName);

    Map<String,Object> genCode(TableInfo tableInfo);

    void downloadCode(HttpServletResponse response,TableInfo tableInfo) throws IOException;
}
