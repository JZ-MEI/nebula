package com.nebula.service.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
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
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ServerInfoServiceImpl implements ServerInfoService {


    @Override
    public ServerInfo getServerInfo() throws UnknownHostException {
        ServerInfo serverInfo = new ServerInfo();


        // CPU信息
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        Map<String,Object> cpuMap = new LinkedHashMap<>();
        cpuMap.put("核心数",cpuInfo.getCpuNum());
        cpuMap.put("空闲率",formatPercentNum(cpuInfo.getFree()));
        cpuMap.put("系统使用率",formatPercentNum(cpuInfo.getSys()));
        cpuMap.put("用户使用率",formatPercentNum(cpuInfo.getUser()));
        List<JSONObject> cpuList = new ArrayList<>();
        for (Map.Entry<String,Object> entry:cpuMap.entrySet()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("key",entry.getKey());
            jsonObject.set("value",entry.getValue());
            cpuList.add(jsonObject);
        }
        serverInfo.setCpuInfo(cpuList);


        // 内存信息
        GlobalMemory memory = OshiUtil.getMemory();
        Map<String,Object> memoryMap = new LinkedHashMap<>();
        BigDecimal divide = Convert.toBigDecimal(memory.getTotal()).subtract(Convert.toBigDecimal(memory.getAvailable())).divide(Convert.toBigDecimal(memory.getTotal()), 4, RoundingMode.HALF_DOWN);
        RuntimeInfo runtimeInfo = SystemUtil.getRuntimeInfo();
        BigDecimal jvmDivide = Convert.toBigDecimal(runtimeInfo.getTotalMemory()).divide(Convert.toBigDecimal(runtimeInfo.getMaxMemory()), 4, BigDecimal.ROUND_HALF_DOWN);
        memoryMap.put("系统总内存",formatMemory(memory.getTotal()));
        memoryMap.put("系统已用内存",formatMemory(memory.getTotal()-memory.getAvailable()));
        memoryMap.put("系统可用内存",formatMemory(memory.getAvailable()));
        memoryMap.put("系统内存使用率",formatPercentNum(divide.multiply(Convert.toBigDecimal(100)).divide(BigDecimal.ONE,2, RoundingMode.HALF_DOWN)));
        memoryMap.put("JVM总内存",formatMemory(runtimeInfo.getMaxMemory()));
        memoryMap.put("JVM已用内存",formatMemory(runtimeInfo.getTotalMemory()));
        memoryMap.put("JVM可用内存",formatMemory(runtimeInfo.getFreeMemory()));
        memoryMap.put("JVM内存使用率",formatPercentNum(jvmDivide.multiply(Convert.toBigDecimal(100)).divide(BigDecimal.ONE,2,BigDecimal.ROUND_HALF_DOWN)));
        List<JSONObject> memoryList = new ArrayList<>();
        for (Map.Entry<String,Object> entry:memoryMap.entrySet()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("key",entry.getKey());
            jsonObject.set("value",entry.getValue());
            memoryList.add(jsonObject);
        }
        serverInfo.setMemoryInfo(memoryList);


        // 服务器信息
        OsInfo osInfo = SystemUtil.getOsInfo();
        Map<String,Object> osMap = new LinkedHashMap<>();
        osMap.put("服务器名称",InetAddress.getLocalHost().getHostName());
        osMap.put("操作系统",osInfo.getName());
        osMap.put("IP地址",SystemUtil.getHostInfo().getAddress());
        osMap.put("系统架构",osInfo.getArch());
        List<JSONObject> osList = new ArrayList<>();
        for (Map.Entry<String,Object> entry:osMap.entrySet()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("key",entry.getKey());
            jsonObject.set("value",entry.getValue());
            osList.add(jsonObject);
        }
        serverInfo.setOsInfo(osList);

        // JVM信息
        // 运行信息
        JavaInfo javaInfo = SystemUtil.getJavaInfo();
        Map<String,Object> runtimeMap = new LinkedHashMap<>();
        JavaRuntimeInfo javaRuntimeInfo = SystemUtil.getJavaRuntimeInfo();
        JvmInfo jvmInfo = SystemUtil.getJvmInfo();
        Date startTime = new Date(ManagementFactory.getRuntimeMXBean().getStartTime());
        String runtime = DateUtil.formatBetween(startTime, new Date(), BetweenFormatter.Level.SECOND);
        UserInfo userInfo = SystemUtil.getUserInfo();

        runtimeMap.put("Java版本",javaInfo.getVersion());
        runtimeMap.put("安装路径",javaRuntimeInfo.getHomeDir());
        runtimeMap.put("JVM名称",jvmInfo.getName());
        runtimeMap.put("项目路径",userInfo.getCurrentDir());
        runtimeMap.put("启动时间",DateUtil.format(startTime, "yyyy-MM-dd HH:mm:ss"));
        runtimeMap.put("运行时长",runtime);
        List<JSONObject> runtimeList = new ArrayList<>();
        for (Map.Entry<String,Object> entry:runtimeMap.entrySet()){
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("key",entry.getKey());
            jsonObject.set("value",entry.getValue());
            runtimeList.add(jsonObject);
        }
        serverInfo.setRuntimeInfo(runtimeList);
//        // 磁盘信息
//
        List<HWDiskStore> diskStores = OshiUtil.getDiskStores();
//        List<DiskInfo> diskInfoList = new ArrayList<>();
        List<List<JSONObject>> diskList = new ArrayList<>();
        for (HWDiskStore hwDiskStore:diskStores){
//            DiskInfo currentDiskInfo = new DiskInfo();
            Map<String,Object> diskMap = new LinkedHashMap<>();
            diskMap.put("磁盘名称",hwDiskStore.getModel());
            diskMap.put("磁盘容量",formatMemory(hwDiskStore.getSize()));
            diskMap.put("盘符",hwDiskStore.getPartitions().stream().map(HWPartition::getMountPoint).collect(Collectors.joining(StrUtil.COMMA)));
            List<JSONObject> oneDiskList = new ArrayList<>();
            for (Map.Entry<String,Object> entry:diskMap.entrySet()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("key",entry.getKey());
                jsonObject.set("value",entry.getValue());
                oneDiskList.add(jsonObject);
            }
            diskList.add(oneDiskList);
        }
        serverInfo.setDiskInfo(diskList);

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
