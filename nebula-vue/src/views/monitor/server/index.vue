<template>
    <div class="view-container">
        <lay-row space="30">
            <lay-col :md="12">
                <lay-card>
                    <template v-slot:title>
                        <div class="itemKey card-header">CPU信息</div>
                    </template>
                    <description :row-num="1" :data="serverInfo.cpuInfo"/>
                </lay-card>
            </lay-col>
            <lay-col :md="12">
                <lay-card>
                    <template v-slot:title>
                        <div class="itemKey card-header">内存信息</div>
                    </template>
                    <description :row-num="2" :data="serverInfo.memoryInfo" label-width="40%"/>
                </lay-card>
            </lay-col>
        </lay-row>
        <lay-row>
            <lay-col :md="24">
                <lay-card>
                    <template v-slot:title>
                        <div class="itemKey card-header">服务器信息</div>
                    </template>
                    <description :row-num="2" :data="serverInfo.osInfo"/>
                </lay-card>
            </lay-col>
        </lay-row>
        <br>
        <lay-row>
            <lay-col :md="24">
                <lay-card>
                    <template v-slot:title>
                        <div class="itemKey card-header">JVM信息</div>
                    </template>
                    <description :row-num="2" :data="serverInfo.runtimeInfo"/>
                </lay-card>
            </lay-col>
        </lay-row>
        <br>
        <lay-row>
            <lay-col :md="24">
                <lay-card>
                    <template v-slot:title>
                        <div class="itemKey card-header">磁盘信息</div>
                    </template>
                    <description v-for="item in serverInfo.diskInfo" :row-num="1" :data="item"/>
                </lay-card>
            </lay-col>
        </lay-row>
    </div>
</template>
<script>
import {onMounted} from "vue";
import {getServerInfo} from "@/api/server.js";
import Description from "@/component/Description.vue";

export default {
    name: 'server',
    components: {Description},
    setup() {

        const serverInfo = ref({});
        const handleQuery = () => {
            getServerInfo().then(res => {
                serverInfo.value = res.data
            })
        }
        const cpuInfo = ref([])
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