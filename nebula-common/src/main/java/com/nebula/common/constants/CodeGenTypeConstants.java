package com.nebula.common.constants;

import java.util.Arrays;
import java.util.List;

public class CodeGenTypeConstants {
    public static final String DOMAIN = "Domain";
    public static final String SERVICE = "Service";
    public static final String SERVICE_IMPL = "ServiceImpl";
    public static final String CONTROLLER = "Controller";
    public static final String MAPPER_INTERFACE = "MapperInterface";
    public static final String MAPPER_XML = "MapperXml";

    public static final List<String> TYPE_LIST =
            Arrays.asList(DOMAIN, SERVICE, SERVICE_IMPL, CONTROLLER, MAPPER_INTERFACE, MAPPER_XML);

}
