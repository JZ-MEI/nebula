package com.nebula.service.service.impl;

import com.nebula.framework.config.VelocityConfig;
import com.nebula.service.service.CodeGenService;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringWriter;

@Service
public class CodeGenServiceImpl implements CodeGenService {

    @Autowired
    VelocityEngine velocityEngine;

    /**
     * 生成代码
     * @param templateName 模板名称
     * @param context 正文内容
     * @return
     */
    @Override
    public String generateCode(String templateName, VelocityContext context) {
        return null;
    }
}
