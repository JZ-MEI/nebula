package com.nebula.controller.monitor;

import com.nebula.common.common.BaseResult;
import com.nebula.common.util.BaseController;
import com.nebula.service.domain.monitor.ServerInfo;
import com.nebula.service.service.ServerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

@RestController
@RequestMapping("server")
public class ServerInfoController extends BaseController {

    @Autowired
    ServerInfoService serverInfoService;


    @GetMapping("getServerInfo")
    public BaseResult<ServerInfo> getServerInfo() throws UnknownHostException {
        ServerInfo serverInfo = serverInfoService.getServerInfo();
        return BaseResult.success(serverInfo);
    }
}
