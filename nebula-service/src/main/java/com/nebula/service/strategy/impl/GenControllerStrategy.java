package com.nebula.service.strategy.impl;

import cn.hutool.core.util.StrUtil;
import com.nebula.common.annotation.Strategy;
import com.nebula.common.constants.CodeGenTypeConstants;
import com.nebula.service.strategy.CodeGenStrategy;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.Map;

@Strategy(CodeGenTypeConstants.CONTROLLER)
public class GenControllerStrategy implements CodeGenStrategy {
    @Autowired
    VelocityEngine velocityEngine;
    @Override
    public String genCode(Map<String, Object> params) {
        String templateName = "Controller.vm";
        VelocityContext context = new VelocityContext();
        context.put("className", params.get("className"));
        context.put("columns", params.get("tableColumnsList"));
        context.put("packageName",params.get("packageName"));
        context.put("classVariableName", StrUtil.lowerFirst((String) params.get("className")));
        Template template = velocityEngine.getTemplate("template/" + templateName);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }

    @Override
    public void genFile(String codeContext, String className, String path) {
        File file = new File(path+File.separator+className+"Controller.java");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(codeContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
