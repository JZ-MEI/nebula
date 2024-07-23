package com.nebula.common.enums;

public enum DataTypeEmun {
    INT("int","Integer"),
    TINYINT("tinyint","Integer"),
    SMALLINT("smallint","Integer"),
    MEDIUMINT("mediumint","Integer"),
    INTEGER("integer","Integer"),
    BIGINT("bigint","Long"),
    FLOAT("float","Float"),
    DOUBLE("double","Double"),
    REAL("real","Double"),
    DECIMAL("decimal","BigDecimal","java.math.BigDecimal"),
    CHAR("char","String"),
    VARCHAR("varchar","String"),
    TEXT("text","String"),
    LONGTEXT("longtext","String"),
    DATE("date","Date","java.util.Date"),
    TIME("time","Date","java.util.Date"),
    DATETIME("datetime","Date","java.util.Date"),
    TIMESTAMP("timestamp","Date","java.util.Date"),
    BIT("bit","Boolean")
    ;
    public final String databaseType;
    public final String javaType;
    public String importPackage;

    DataTypeEmun(String databaseType, String javaType, String importPackage) {
        this.databaseType = databaseType;
        this.javaType = javaType;
        this.importPackage = importPackage;
    }

    DataTypeEmun(String databaseType, String javaType) {
        this.databaseType = databaseType;
        this.javaType = javaType;
    }

    public static String findJavaTypeByDatabaseType(String databaseType) {
        for (DataTypeEmun dataType : DataTypeEmun.values()) {
            if (dataType.databaseType.equals(databaseType)) {
                return dataType.javaType;
            }
        }
        return null;
    }
}
