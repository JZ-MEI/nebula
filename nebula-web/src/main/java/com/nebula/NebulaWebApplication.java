package com.nebula;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class NebulaWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(NebulaWebApplication.class, args);
        System.out.println("星云管理系统启动成功\n" +
                " __ __ __   ______    _______\n" +
                "/_//_//_/\\ /_____/\\ /_______/\\\n" +
                "\\ \\\\ \\\\ \\ \\\\    _\\/_\\   _   \\ \\\n" +
                " \\ \\\\ \\\\ \\ \\\\ \\/___/\\\\  \\_\\  \\/_\n" +
                "  \\ \\\\ \\\\ \\ \\\\  ___\\/_\\   _   \\ \\\n" +
                "   \\ \\\\ \\\\ \\ \\\\ \\____/\\\\  \\_\\  \\ \\\n" +
                "    \\_______\\/ \\_____\\/ \\_______\\/\n");
    }

}
