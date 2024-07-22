package com.nebula.service.service;

import com.nebula.service.domain.gen.TableColumsInfo;
import com.nebula.service.domain.gen.TableInfo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface GenService {

    List<TableInfo> getTableList();

    List<TableColumsInfo> getTableColumnsList(String tableName);

    void genCode(HttpServletResponse response,String tableName);
}
