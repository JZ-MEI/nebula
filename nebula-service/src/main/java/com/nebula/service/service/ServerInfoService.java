package com.nebula.service.service;

import com.nebula.service.domain.monitor.ServerInfo;

import java.net.UnknownHostException;

public interface ServerInfoService {

    ServerInfo getServerInfo() throws UnknownHostException;
}
