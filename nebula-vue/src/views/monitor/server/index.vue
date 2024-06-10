<template>
    <div class="view-container">
        <lay-row space="30">
            <lay-col :md="12">
                <lay-card>
                    <template v-slot:title>
                        <div class="attributeTitle card-header">CPU信息</div>
                    </template>
                    <lay-row style="text-align: center;">
                        <lay-col :md="12">
                            <span class="attributeTitle">核心数</span>
                        </lay-col>
                        <lay-col :md="12">
                            <span class="attributeTitle">{{ serverInfo.cpuNum }}</span>
                        </lay-col>
                        <lay-line></lay-line>
                        <lay-col :md="12">
                            <span class="attributeTitle">用户使用率</span>
                        </lay-col>
                        <lay-col :md="12">
                            <span class="attributeTitle">{{ serverInfo.userUsageRate }}</span>
                        </lay-col>
                        <lay-line></lay-line>
                        <lay-col :md="12">
                            <span class="attributeTitle">系统使用率</span>
                        </lay-col>
                        <lay-col :md="12">
                            <span class="attributeTitle">{{ serverInfo.sysUsageRate }}</span>
                        </lay-col>
                        <lay-line></lay-line>
                        <lay-col :md="12">
                            <span class="attributeTitle">空闲率</span>
                        </lay-col>
                        <lay-col :md="12">
                            <span class="attributeTitle">{{ serverInfo.cpuFree }}</span>
                        </lay-col>
                    </lay-row>
                </lay-card>
            </lay-col>
            <lay-col :md="12">
                <lay-card>
                    <template v-slot:title>
                        <div class="attributeTitle card-header">内存信息</div>
                    </template>
                    <lay-row style="text-align: center;">
                        <lay-col :md="8">
                            <span class="attributeTitle">系统/JVM总内存</span>
                        </lay-col>
                        <lay-col :md="8">
                            <span class="attributeTitle">{{ serverInfo.totalMemory }}</span>
                        </lay-col>
                        <lay-col :md="8">
                            <span class="attributeTitle">{{ serverInfo.jvmTotalMemory }}</span>
                        </lay-col>
                        <lay-line></lay-line>
                        <lay-col :md="8">
                            <span class="attributeTitle">系统/JVM已用内存</span>
                        </lay-col>
                        <lay-col :md="8">
                            <span class="attributeTitle">{{ serverInfo.usedMemory }}</span>
                        </lay-col>
                        <lay-col :md="8">
                            <span class="attributeTitle">{{ serverInfo.jvmUsedMemory }}</span>
                        </lay-col>
                        <lay-line></lay-line>
                        <lay-col :md="8">
                            <span class="attributeTitle">系统/JVM可用内存</span>
                        </lay-col>
                        <lay-col :md="8">
                            <span class="attributeTitle">{{ serverInfo.availableMemory }}</span>
                        </lay-col>
                        <lay-col :md="8">
                            <span class="attributeTitle">{{ serverInfo.jvmAvailableMemory }}</span>
                        </lay-col>
                        <lay-line></lay-line>
                        <lay-col :md="8">
                            <span class="attributeTitle">系统/JVM使用率</span>
                        </lay-col>
                        <lay-col :md="8">
                            <span class="attributeTitle">{{ serverInfo.memoryUsageRate }}</span>
                        </lay-col>
                        <lay-col :md="8">
                            <span class="attributeTitle">{{ serverInfo.jvmUsageRate }}</span>
                        </lay-col>
                    </lay-row>
                </lay-card>
            </lay-col>
        </lay-row>
        <lay-row>
            <lay-col :md="24">
                <lay-card>
                    <template v-slot:title>
                        <div class="attributeTitle card-header">服务器信息</div>
                    </template>
                    <lay-row style="text-align: center;">
                        <lay-col :md="6">
                            <span class="attributeTitle">服务器名称</span>
                        </lay-col>
                        <lay-col :md="6">
                            <span class="attributeTitle">{{ serverInfo.serverName }}</span>
                        </lay-col>
                        <lay-col :md="6">
                            <span class="attributeTitle">操作系统</span>
                        </lay-col>
                        <lay-col :md="6">
                            <span class="attributeTitle">{{ serverInfo.osName }}</span>
                        </lay-col>
                        <lay-line/>
                        <lay-col :md="6">
                            <span class="attributeTitle">IP地址</span>
                        </lay-col>
                        <lay-col :md="6">
                            <span class="attributeTitle">{{ serverInfo.ipAddress }}</span>
                        </lay-col>
                        <lay-col :md="6">
                            <span class="attributeTitle">系统架构</span>
                        </lay-col>
                        <lay-col :md="6">
                            <span class="attributeTitle">{{ serverInfo.osArch }}</span>
                        </lay-col>
                    </lay-row>
                </lay-card>
            </lay-col>
        </lay-row>
        <br>
        <lay-row>
            <lay-col :md="24">
                <lay-card>
                    <template v-slot:title>
                        <div class="attributeTitle card-header">JVM信息</div>
                    </template>
                    <lay-row style="text-align: center;">
                        <lay-col :md="6">
                            <span class="attributeTitle">JVM名称</span>
                        </lay-col>
                        <lay-col :md="6">
                            <span class="attributeTitle">{{ serverInfo.javaName }}</span>
                        </lay-col>
                        <lay-col :md="6">
                            <span class="attributeTitle">Java版本</span>
                        </lay-col>
                        <lay-col :md="6">
                            <span class="attributeTitle">{{ serverInfo.javaVersion }}</span>
                        </lay-col>
                        <lay-line/>
                        <lay-col :md="6">
                            <span class="attributeTitle">安装路径</span>
                        </lay-col>
                        <lay-col :md="6">
                            <span class="attributeTitle">{{ serverInfo.javaHome }}</span>
                        </lay-col>
                        <lay-col :md="6">
                            <span class="attributeTitle">项目路径</span>
                        </lay-col>
                        <lay-col :md="6">
                            <span class="attributeTitle">{{ serverInfo.currentDir }}</span>
                        </lay-col>
                        <lay-line/>
                        <lay-col :md="6">
                            <span class="attributeTitle">启动时间</span>
                        </lay-col>
                        <lay-col :md="6">
                            <span class="attributeTitle">{{ serverInfo.startTime }}</span>
                        </lay-col>
                        <lay-col :md="6">
                            <span class="attributeTitle">运行时长</span>
                        </lay-col>
                        <lay-col :md="6">
                            <span class="attributeTitle">{{ serverInfo.currentRuntime }}</span>
                        </lay-col>
                    </lay-row>
                </lay-card>
            </lay-col>
        </lay-row>
        <br>
        <lay-row>
            <lay-col :md="24">
                <lay-card>
                    <template v-slot:title>
                        <div class="attributeTitle card-header">磁盘信息</div>
                    </template>
                    <lay-row style="text-align: center">
                        <div v-for="(item, index) in serverInfo.diskInfoList" :key="index">
                            <lay-col :md="8">
                                <span class="attributeTitle">{{ item.diskName }}</span>
                            </lay-col>
                            <lay-col :md="8">
                                <span class="attributeTitle">{{ item.diskSize }}</span>
                            </lay-col>
                            <lay-col :md="8">
                                <span class="attributeTitle">{{ item.diskMountPoint }}</span>
                            </lay-col>
                            <lay-line v-if="index !==serverInfo.diskInfoList.length-1"></lay-line>
                        </div>
                    </lay-row>
                </lay-card>
            </lay-col>
        </lay-row>
    </div>
</template>

<script>
import {onMounted} from "vue";
import {getServerInfo} from "@/api/server.js";

export default {
    setup() {

        const serverInfo = ref({});
        const handleQuery = () => {
            getServerInfo().then(res => {
                serverInfo.value = res.data
            })
        }
        onMounted(() => {
            handleQuery()
        })

        return {
            serverInfo
        }
    }
}

</script>

<style>
.attributeTitle {
    font-size: 1.1rem;
    line-height: 3rem;
}

.layui-card {
    border: 1px solid #dddddd;
    padding-top: 0;
    padding-bottom: 0;
}

.layui-card .layui-card-header {
    border-bottom: 1px solid #dddddd;
}

.card-header {
    font-size: 1.2rem;
    font-weight: bold;
}



</style>