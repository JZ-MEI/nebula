package com.nebula.task;

import com.nebula.task.annotation.NebulaJob;
import org.springframework.stereotype.Component;

@Component
public class test {

    @NebulaJob(jobName = "alias1")
    public void alias1(){
        System.out.println("任务1执行");
    }

    @NebulaJob(jobName = "alias2")
    public void alias2(String params){
        System.out.println(params);
    }
}
