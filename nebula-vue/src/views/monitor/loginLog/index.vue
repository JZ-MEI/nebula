<template>
    <div class="view-container">
        <lay-form :model="queryParam" :pane="true" size="sm">
            <lay-row space="10">
                <lay-col md="6">
                    <lay-form-item label="开始时间" prop="startTime">
                        <lay-date-picker type="datetime" v-model="queryParam.startTime" placeholder="开始时间"
                                         class="width-control"></lay-date-picker>
                    </lay-form-item>
                </lay-col>
                <lay-col md="6">
                    <lay-form-item label="结束时间" prop="endTime">
                        <lay-date-picker type="datetime" v-model="queryParam.endTime" placeholder="结束时间"
                                         class="width-control"></lay-date-picker>
                    </lay-form-item>
                </lay-col>
                <lay-col md="6">
                    <lay-form-item label="操作人" prop="userId">
                        <lay-select v-model="queryParam.userId" placeholder="请选择操作人" :show-search="true"
                                    :allow-clear="true" class="width-control">
                            <lay-select-option v-for="item in userList" :value="item.id"
                                               :label="item.nickname"></lay-select-option>
                        </lay-select>
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
        </lay-table>
    </div>
</template>
<script>
import {getNormalUser} from "@/api/sysUser.js";
import {onMounted} from "vue";
import {getTableData} from "@/api/loginLog.js";

export default {
    name:'sysLogin',
    setup(){
        const queryParam = ref({
            startTime:"",
            endTime:"",
            userId:"",
            pageNum:1,
            pageSize:10
        })

        const userList = ref([])

        const rowsTotal = ref(0)
        const tableData = ref([])

        const page = reactive({
            current: queryParam.value.pageNum,
            limit: queryParam.value.pageSize,
            total: rowsTotal,
            layout: ["prev", "page", "next", "limits", "count"],
        });

        const columns = [
            {title: "登录时间", key: "loginTime", align: 'center',width: '250'},
            {title: "登录账号", key: "nickname", align: 'center',width: '150'},
            {title: "登录IP", key: "loginIp", align: 'center',width: '100'},
            {title: "浏览器", key: "loginBrowser", align: 'center',ellipsisTooltip: true},
            {title: "浏览器引擎", key: "loginEngine", align: 'center',ellipsisTooltip: true},
            {title: "系统平台", key: "loginPlatform", align: 'center',ellipsisTooltip: true},
            {title: "操作系统", key: "loginOs", align: 'center',ellipsisTooltip: true}
        ]

        function getUserList() {
            getNormalUser().then(res => {
                userList.value = res.data
            })
        }
        function handleQuery() {
            getTableData(queryParam.value).then(res => {
                tableData.value = res.data.rows
                rowsTotal.value = res.data.total
            })
        }
        function resetParams() {
            for (let key in queryParam.value) {
                if (key !== "pageNum" && key !== "pageSize") {
                    queryParam.value[key] = null;
                }
            }
        }
        function changePage(item) {
            queryParam.value.pageNum = item.current
            handleQuery()
        }

        onMounted(() => {
            getUserList()
            handleQuery()
        })

        return {
            queryParam,
            userList,
            rowsTotal,
            tableData,
            page,
            columns,
            handleQuery,
            resetParams,
            changePage
        }
    }
}
</script>