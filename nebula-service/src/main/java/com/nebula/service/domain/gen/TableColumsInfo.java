package com.nebula.service.domain.gen;

import com.nebula.common.entity.PageDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TableColumsInfo extends PageDomain {
    private String tableName;
    private String columnName;
    private String isNullable;
    private String columnDefault;
    private String dataType;
    private String columnType;
    private String columnKey;
    private String columnComment;
    private String extra;
    private String camelColumnName;
}
