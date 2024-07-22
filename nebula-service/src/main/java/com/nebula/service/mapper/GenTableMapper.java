package com.nebula.service.mapper;

import com.nebula.service.domain.gen.TableColumsInfo;
import com.nebula.service.domain.gen.TableInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GenTableMapper {

    List<TableInfo> getTableName(@Param("tableSchema")String tableSchema);

    List<TableColumsInfo> getTableColumnsList(@Param("tableSchema")String tableSchema,@Param("tableName")String tableName);
}
