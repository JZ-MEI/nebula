package com.nebula.service.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.*;
import cn.hutool.system.oshi.CpuInfo;
import cn.hutool.system.oshi.OshiUtil;
import com.nebula.service.domain.monitor.DiskInfo;
import com.nebula.service.domain.monitor.ServerInfo;
import com.nebula.service.service.ServerInfoService;
import org.springframework.stereotype.Service;
import oshi.hardware.*;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ServerInfoServiceImpl implements ServerInfoService {


    @Override
    public ServerInfo getServerInfo() throws UnknownHostException {
        ServerInfo serverInfo = new ServerInfo();

        // CPU信息
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        serverInfo.setCpuNum(cpuInfo.getCpuNum());
        serverInfo.setCpuFree(formatPercentNum(cpuInfo.getFree()));
        serverInfo.setSysUsageRate(formatPercentNum(cpuInfo.getSys()));
        serverInfo.setUserUsageRate(formatPercentNum(cpuInfo.getUser()));


        // 内存信息
        GlobalMemory memory = OshiUtil.getMemory();
        serverInfo.setTotalMemory(formatMemory(memory.getTotal()));
        serverInfo.setUsedMemory(formatMemory(memory.getTotal()-memory.getAvailable()));
        serverInfo.setAvailableMemory(formatMemory(memory.getAvailable()));
        BigDecimal divide = Convert.toBigDecimal(memory.getTotal()).subtract(Convert.toBigDecimal(memory.getAvailable())).divide(Convert.toBigDecimal(memory.getTotal()), 4, BigDecimal.ROUND_HALF_DOWN);

        serverInfo.setMemoryUsageRate(formatPercentNum(divide.multiply(Convert.toBigDecimal(100)).divide(BigDecimal.ONE,2, BigDecimal.ROUND_HALF_DOWN)));
        RuntimeInfo runtimeInfo = SystemUtil.getRuntimeInfo();
        serverInfo.setJvmTotalMemory(formatMemory(runtimeInfo.getMaxMemory()));
        serverInfo.setJvmUsedMemory(formatMemory(runtimeInfo.getTotalMemory()));
        serverInfo.setJvmAvailableMemory(formatMemory(runtimeInfo.getFreeMemory()));
        BigDecimal jvmDivide = Convert.toBigDecimal(runtimeInfo.getTotalMemory()).divide(Convert.toBigDecimal(runtimeInfo.getMaxMemory()), 4, BigDecimal.ROUND_HALF_DOWN);
        serverInfo.setJvmUsageRate(formatPercentNum(jvmDivide.multiply(Convert.toBigDecimal(100)).divide(BigDecimal.ONE,2,BigDecimal.ROUND_HALF_DOWN)));
        // 服务器信息
        OsInfo osInfo = SystemUtil.getOsInfo();
        serverInfo.setServerName(InetAddress.getLocalHost().getHostName());
        serverInfo.setOsName(osInfo.getName());
        serverInfo.setOsArch(osInfo.getArch());
        serverInfo.setIpAddress(SystemUtil.getHostInfo().getAddress());

        // JVM信息
        JavaInfo javaInfo = SystemUtil.getJavaInfo();
        JavaRuntimeInfo javaRuntimeInfo = SystemUtil.getJavaRuntimeInfo();
        JvmInfo jvmInfo = SystemUtil.getJvmInfo();
        serverInfo.setJavaVersion(javaInfo.getVersion());
        serverInfo.setJavaHome(javaRuntimeInfo.getHomeDir());
        serverInfo.setJavaName(jvmInfo.getName());

        // 运行信息
        Date startTime = new Date(ManagementFactory.getRuntimeMXBean().getStartTime());
        String runtime = DateUtil.formatBetween(startTime, new Date(), BetweenFormatter.Level.SECOND);
        UserInfo userInfo = SystemUtil.getUserInfo();
        serverInfo.setStartTime(DateUtil.format(startTime, "yyyy-MM-dd HH:mm:ss"));
        serverInfo.setCurrentRuntime(runtime);
        serverInfo.setCurrentDir(userInfo.getCurrentDir());

        // 磁盘信息

        List<HWDiskStore> diskStores = OshiUtil.getDiskStores();
        List<DiskInfo> diskInfoList = new ArrayList<>();
        for (HWDiskStore hwDiskStore:diskStores){
            DiskInfo currentDiskInfo = new DiskInfo();
            currentDiskInfo.setDiskSize(formatMemory(hwDiskStore.getSize()));
            currentDiskInfo.setDiskName(hwDiskStore.getModel());
            currentDiskInfo.setDiskMountPoint(hwDiskStore.getPartitions().stream().map(HWPartition::getMountPoint).collect(Collectors.joining(StrUtil.COMMA)));
            diskInfoList.add(currentDiskInfo);
        }
        serverInfo.setDiskInfoList(diskInfoList);

        return serverInfo;
    }


    private String formatPercentNum(Number number){
        String percentTemplate = "{}%";
        return StrUtil.format(percentTemplate,number);
    }

    private String formatMemory(Number number){
        if (number.longValue()<1024){
            return StrUtil.format("{}Byte",number);
        }else if (number.longValue()>= 1024 && number.longValue()<1048576){
            return StrUtil.format("{}KB",number.longValue()/1024);
        } else if (number.longValue() >= 1048576 && number.longValue() < 1073741824) {
            return StrUtil.format("{}MB",number.longValue()/1048576);
        } else {
            return StrUtil.format("{}GB",number.longValue()/1073741824);
        }
    }
}
