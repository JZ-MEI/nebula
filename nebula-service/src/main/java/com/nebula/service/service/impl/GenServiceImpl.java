package com.nebula.service.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.nebula.common.constants.CodeGenTypeConstants;
import com.nebula.common.enums.DataTypeEmun;
import com.nebula.common.util.DownloadUtil;
import com.nebula.service.domain.gen.TableColumsInfo;
import com.nebula.service.domain.gen.TableInfo;
import com.nebula.service.mapper.GenTableMapper;
import com.nebula.service.service.GenService;
import com.nebula.service.strategy.CodeGenStrategy;
import com.nebula.service.strategy.helper.CodeGenStrategyHelper;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GenServiceImpl implements GenService {

    @Value("${spring.datasource.url}")
    private String url;

    @Autowired
    GenTableMapper genTableMapper;
    @Autowired
    VelocityEngine velocityEngine;
    @Autowired
    ResourceLoader resourceLoader;

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
    public Map<String,Object> genCode(TableInfo tableInfo) {
        String tableName = tableInfo.getTableName();
        String packageName = tableInfo.getPackageName();
        String tableSchema = getTableSchema();
        List<TableColumsInfo> tableColumnsList = genTableMapper.getTableColumnsList(tableSchema, tableName);
        tableColumnsList = tableColumnsList.stream().peek(i->{
            i.setColumnType(DataTypeEmun.findJavaTypeByDatabaseType(i.getDataType()));
            i.setCamelColumnName(StrUtil.toCamelCase(i.getColumnName()));
        }).collect(Collectors.toList());
        String className = StrUtil.upperFirst(StrUtil.toCamelCase(tableName));
        List<String> typeList = CodeGenTypeConstants.TYPE_LIST;
        Map<String,Object> result = new HashMap<>();
        for (String type:typeList) {
            CodeGenStrategy strategy = CodeGenStrategyHelper.getStrategy(type);
            Map<String, Object> params = new HashMap<>();
            params.put("className", className);
            params.put("tableColumnsList", tableColumnsList);
            params.put("packageName", packageName);
            params.put("tableName",tableName);
            String s = strategy.genCode(params);
            result.put(type,s);
        }
        return result;
    }

    @Override
    public void downloadCode(HttpServletResponse response, TableInfo tableInfo) throws IOException {
        String className = StrUtil.upperFirst(StrUtil.toCamelCase(tableInfo.getTableName()));
        Map<String, Object> genResult = genCode(tableInfo);
        Set<String> mapKey = genResult.keySet();
        String path = resourceLoader.getResource("classpath:").getURI().getPath();
        path += "generatedFiles"; // 在resource目录下创建一个新的文件夹
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        for (String key:mapKey){
            CodeGenStrategy strategy = CodeGenStrategyHelper.getStrategy(key);
            strategy.genFile((String) genResult.get(key), className, path);
        }
        File zip = ZipUtil.zip(path);
        DownloadUtil.downloadFile(zip,response,"code.zip");
        FileUtil.del(directory);
        FileUtil.del(zip);
    }

    private String getTableSchema(){
        int startIndex = url.lastIndexOf("/");
        int endIndex = url.indexOf("?");
        return url.substring(startIndex+1, endIndex);
    }
}
