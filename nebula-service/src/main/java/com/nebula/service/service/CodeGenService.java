package com.nebula.service.service;

import org.apache.velocity.VelocityContext;

public interface CodeGenService {

    String generateCode(String templateName, VelocityContext context);
}
