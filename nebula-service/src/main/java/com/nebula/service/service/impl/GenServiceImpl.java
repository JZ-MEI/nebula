package com.nebula.service.service.impl;

import cn.hutool.core.util.StrUtil;
import com.nebula.service.domain.gen.TableColumsInfo;
import com.nebula.service.domain.gen.TableInfo;
import com.nebula.service.mapper.GenTableMapper;
import com.nebula.service.service.GenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class GenServiceImpl implements GenService {

    @Value("${spring.datasource.url}")
    private String url;

    @Autowired
    GenTableMapper genTableMapper;

    @Override
    public List<TableInfo> getTableList() {
        String tableSchema = getTableSchema();
        return genTableMapper.getTableName(tableSchema);
    }

    @Override
    public List<TableColumsInfo> getTableColumnsList(String tableName) {
        String tableSchema = getTableSchema();
        return genTableMapper.getTableColumnsList(tableSchema,tableName);
    }

    @Override
    public void genCode(HttpServletResponse response, String tableName) {
        String tableSchema = getTableSchema();
        List<TableColumsInfo> tableColumnsList = genTableMapper.getTableColumnsList(tableSchema, tableName);
        String camelTableName = StrUtil.upperFirst(StrUtil.toCamelCase(tableName));

        System.out.println(camelTableName);
    }

    private String getTableSchema(){
        int startIndex = url.lastIndexOf("/");
        int endIndex = url.indexOf("?");
        return url.substring(startIndex+1, endIndex);
    }
}
