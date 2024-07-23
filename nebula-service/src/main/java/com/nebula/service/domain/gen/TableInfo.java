package com.nebula.service.domain.gen;

import com.nebula.common.entity.PageDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class TableInfo extends PageDomain {
    private String tableName;
    private String tableComment;
    private String packageName;
    private Map<String,Object> code;
}
