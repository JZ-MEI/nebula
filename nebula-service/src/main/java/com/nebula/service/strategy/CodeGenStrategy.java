package com.nebula.service.strategy;

import cn.hutool.json.JSONObject;

import java.io.File;
import java.util.Map;

public interface CodeGenStrategy {

    String genCode(Map<String,Object> params);

    void genFile(String codeContext,String className,String path);
}
