<template>
    <div class="view-container">
        <lay-form :model="queryParam" :pane="true" size="sm">
            <lay-row space="10">
                <lay-col md="6">
                    <lay-form-item label="用户凭证" prop="token">
                        <lay-input v-model="queryParam.token" placeholder="用户token(模糊查询)"></lay-input>
                    </lay-form-item>
                </lay-col>
                <lay-col md="6">
                    <lay-form-item>
                        <lay-button type="primary" @click="handleQuery">搜索</lay-button>
                        <lay-button type="default" @click="resetParams">重置</lay-button>
                    </lay-form-item>
                </lay-col>
            </lay-row>
        </lay-form>
        <lay-table :columns="columns" :data-source="tableData"
                   :default-toolbar="true" :default-expand-all="false" @change="changePage" :page="page">
            <template v-slot:operation="{row}">
                <lay-button type="normal" size="xs" @click="forceOffline(row)">强制下线</lay-button>
            </template>
        </lay-table>
    </div>
</template>

<script>
import {onMounted} from "vue";
import {getOnlineUser, offline} from "@/api/onlineUser.js";
import {layer} from "@layui/layui-vue";

export default {
    name:'onlineUser',
    setup() {
        const queryParam = ref({
            token: null,
            pageSize: 10,
            pageNum: 1
        })
        const rowsTotal = ref(0)
        const tableData = ref([])

        const page = reactive({
            current: queryParam.value.pageNum,
            limit: queryParam.value.pageSize,
            total: rowsTotal,
            layout: ["prev", "page", "next", "limits", "count"],
        });

        const columns=[
            {title: "token", key: "token", align: 'center'},
            {title: "用户姓名", key: "nickname", align: 'center'},
            {title: "最近登陆时间", key: "loginTime", align: 'center'},
            {title: "浏览器", key: "browser", align: 'center'},
            {title: "登陆平台", key: "platform", align: 'center'},
            {title: "浏览器引擎", key: "engine", align: 'center'},
            {title: "操作系统", key: "os", align: 'center'},
            {title: "登陆设备数", key: "loginDevices", align: 'center'},
            {title: "IP地址", key: "loginIp", align: 'center'},
            {title: "操作", align: 'center', customSlot: 'operation'}
        ]

        function changePage(item) {
            queryParam.value.pageNum = item.current
            handleQuery()
        }

        function handleQuery() {
            getOnlineUser(queryParam.value).then(res => {
                rowsTotal.value = res.data.total
                tableData.value = res.data.rows
            })
        }

        function resetParams() {
            for (let key in queryParam.value) {
                if (key !== "pageNum" && key !== "pageSize") {
                    queryParam.value[key] = null;
                }
            }
        }

        function forceOffline(row) {
            offline(row.loginId).then(res=>{
                if (res.code===200){
                    handleQuery()
                    layer.notifiy({
                        title: "操作成功",
                        icon: 1
                    })
                }
            })
        }

        onMounted(() => {
            handleQuery()
        })

        return {
            queryParam,
            rowsTotal,
            tableData,
            page,
            columns,
            changePage,
            handleQuery,
            resetParams,
            forceOffline
        }
    }
}
</script>