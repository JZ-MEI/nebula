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
                    <lay-form-item label="调度结果" prop="dispatchResult">
                        <lay-select v-model="queryParam.dispatchResult" placeholder="请选择调度结果" :show-search="true"
                                    :allow-clear="true" class="width-control">
                            <lay-select-option v-for="item in dictValue.success_or_not" :value="item.dictKey"
                                               :label="item.dictValue"></lay-select-option>
                        </lay-select>
                    </lay-form-item>
                </lay-col>
                <lay-col md="6">
                    <lay-form-item label="执行结果" prop="executeResult">
                        <lay-select v-model="queryParam.executeResult" placeholder="请选择执行结果" :show-search="true"
                                    :allow-clear="true" class="width-control">
                            <lay-select-option v-for="item in dictValue.success_or_not" :value="item.dictKey"
                                               :label="item.dictValue"></lay-select-option>
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
            <template v-slot:dispatchResult="{row}">
                <dict-tag :options="dictValue.success_or_not" :values="row.dispatchResult"></dict-tag>
            </template>
            <template v-slot:executeResult="{row}">
                <dict-tag :options="dictValue.success_or_not" :values="row.executeResult"></dict-tag>
            </template>
        </lay-table>
    </div>
</template>
<script>
import {onMounted} from 'vue'
import {getDict} from "@/util/dict.js";
import {getDetailTableData} from "@/api/sysTask.js";
import {useRoute} from "vue-router";
import DictTag from "@/component/DictTag.vue";

export default {
    components: {DictTag},
    dicts: ["success_or_not"],
    setup(){
        const route = useRoute()
        const queryParam = ref({
            startTime:"",
            endTime:"",
            dispatchResult:"",
            executeResult:"",
            jobId:route.params.taskId,
            pageNum:1,
            pageSize:10
        })

        const proxy = getCurrentInstance();
        const dictValue = ref({})
        const rowsTotal = ref(0)
        const tableData = ref([])
        const page = reactive({
            current: queryParam.value.pageNum,
            limit: queryParam.value.pageSize,
            total: rowsTotal,
            layout: ["prev", "page", "next", "limits", "count"],
        });

        const columns = [
            {title: "调度时间", key: "executeTime", align: 'center',width: '250'},
            {title: "执行器", key: "jobHandler", align: 'center',width: '150'},
            {title: "调度结果", key: "dispatchResult", align: 'center',customSlot:'dispatchResult',width: '100'},
            {title: "执行结果", key: "executeResult", align: 'center',customSlot:'executeResult',width: '100'},
            {title: "调度错误信息", key: "dispatchErrorReason", align: 'center',ellipsisTooltip: true},
            {title: "执行错误信息", key: "executeErrorReason", align: 'center',ellipsisTooltip: true},
        ]
        function handleQuery() {
            getDetailTableData(queryParam.value).then(res => {
                tableData.value = res.data.rows
                rowsTotal.value = res.data.total
            })
        }
        function changePage(item) {
            queryParam.value.pageNum = item.current
            handleQuery()
        }
        function resetParams() {
            for (let key in queryParam.value) {
                if (key !== "pageNum" && key !== "pageSize" && key !== 'jobId') {
                    queryParam.value[key] = null;
                }
            }
        }
        onMounted(()=>{
            dictValue.value = getDict(proxy)
            handleQuery()
        })

        return {
            queryParam,
            dictValue,
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