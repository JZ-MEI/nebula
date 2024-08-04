package com.nebula.task.task;

import com.nebula.task.annotation.NebulaJob;
import com.nebula.common.common.BaseResult;
import org.springframework.stereotype.Component;

@Component
public class ExampleTask {

    @NebulaJob(jobName = "alias1")
    public BaseResult<?> alias1(){
        System.out.println("任务1执行");
        return BaseResult.success();
    }

    @NebulaJob(jobName = "alias2")
    public BaseResult<?> alias2(String params){
        System.out.println(params);
        return BaseResult.success();
    }
}
