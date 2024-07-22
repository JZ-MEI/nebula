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
                    <lay-form-item label="操作类型" prop="commandType">
                        <lay-select v-model="queryParam.commandType" placeholder="请选择操作类型" :show-search="true"
                                    :allow-clear="true" class="width-control">
                            <lay-select-option value="UPDATE" label="更新"></lay-select-option>
                            <lay-select-option value="INSERT" label="新增"></lay-select-option>
                            <lay-select-option value="DELETE" label="删除"></lay-select-option>
                        </lay-select>
                    </lay-form-item>
                </lay-col>
                <lay-col md="6">
                    <lay-form-item label="SQL ID" prop="sqlId">
                        <lay-input v-model="queryParam.sqlId" placeholder="请输入SQL ID"
                                   class="width-control"></lay-input>
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
import {getTableData} from "@/api/sysOperLog.js";

export default {
    name:'sysOper',
    setup() {
        const queryParam = ref({
            startTime: "",
            endTime: "",
            userId: "",
            commandType: "",
            sqlId: "",
            pageNum: 1,
            pageSize: 10,

        })

        const columns=[
            {title: "操作时间", key: "operTime", align: 'center',width: '250'},
            {title: "操作人", key: "nickname", align: 'center',width: '150'},
            {title: "操作类型", key: "commandType", align: 'center',width: '100'},
            {title: "SQL ID", key: "sqlId", align: 'center',ellipsisTooltip: true},
            {title: "变更参数", key: "sqlParam", align: 'center',ellipsisTooltip: true}
        ]

        const userList = ref([])

        const rowsTotal = ref(0)
        const tableData = ref([])

        const page = reactive({
            current: queryParam.value.pageNum,
            limit: queryParam.value.pageSize,
            total: rowsTotal,
            layout: ["prev", "page", "next", "limits", "count"],
        });

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
            changePage,
            handleQuery,
            resetParams
        }
    }
}
</script>