package com.nebula.service.domain.monitor;

import lombok.Data;

import java.util.List;

@Data
public class ServerInfo {

    private Integer cpuNum;

    private String cpuFree;

    private String sysUsageRate;

    private String userUsageRate;

    private String totalMemory;

    private String usedMemory;

    private String availableMemory;

    private String memoryUsageRate;

    private String jvmTotalMemory;

    private String jvmUsedMemory;

    private String jvmAvailableMemory;

    private String jvmUsageRate;

    private String serverName;

    private String osName;

    private String osArch;

    private String ipAddress;

    private String javaVersion;

    private String javaHome;

    private String javaName;

    private String startTime;

    private String currentRuntime;

    private String currentDir;

    private List<DiskInfo> diskInfoList;
}
