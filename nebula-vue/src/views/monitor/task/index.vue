<template>
    <div class="view-container">
        <lay-form :model="queryParam" :pane="true" size="sm">
            <lay-row space="10">
                <lay-col md="6">
                    <lay-form-item label="任务名称" prop="jobName">
                        <lay-input v-model="queryParam.jobName" placeholder="任务名称"></lay-input>
                    </lay-form-item>
                </lay-col>
                <lay-col md="6">
                    <lay-form-item label="任务执行器" prop="jobHandler">
                        <lay-input v-model="queryParam.jobHandler" placeholder="任务执行器"></lay-input>
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
            <template v-slot:toolbar>
                <lay-button type="primary" @click="handleCreate" size="sm">新增</lay-button>
            </template>
            <template v-slot:jobHandler="{row}">
                <router-link :to=genDetailUrl(row) class="table-link">
                    {{row.jobHandler}}
                </router-link>
            </template>
            <template v-slot:isOpen="{row}">
                <lay-switch v-model="row.isOpen" :unswitch-value=0 unswitch-text="停用" :onswitch-value=1
                            onswitch-text="正常" @change="switchChange(row)"></lay-switch>
            </template>
            <template v-slot:operation="{row}">
                <lay-button type="primary" size="xs" @click="handleEdit(row)">编辑</lay-button>
                <lay-button type="danger" size="xs" @click="handleDelete(row)">删除</lay-button>
            </template>
        </lay-table>
        <lay-layer v-model="isShow" title="新增任务" :btn="action">
            <div class="createForm view-container">
                <lay-form :model="createParam" :pane="true">
                    <lay-row space=20>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="任务名称" prop="jobName">
                                <lay-input v-model="createParam.jobName"></lay-input>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="执行器" prop="jobHandler">
                                <lay-input v-model="createParam.jobHandler"></lay-input>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="CRON表达式" prop="cron">
                                <lay-input v-model="createParam.cron"></lay-input>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="任务状态" prop="isOpen">
                                <lay-select v-model="createParam.isOpen" :show-search="true" class="width-control"
                                            allow-clear>
                                    <lay-select-option :value="1" label="启用"></lay-select-option>
                                    <lay-select-option :value="0" label="停用"></lay-select-option>
                                </lay-select>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="24" sm="24">
                            <lay-form-item label="任务参数" prop="jobParams">
                                <lay-textarea placeholder="请输入任务参数" v-model="createParam.jobParams">
                                </lay-textarea>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="24" sm="24">
                            <lay-form-item label="任务描述" prop="jobDesc">
                                <lay-textarea placeholder="请输入任务描述" v-model="createParam.jobDesc">
                                </lay-textarea>
                            </lay-form-item>
                        </lay-col>
                    </lay-row>
                </lay-form>
            </div>
        </lay-layer>
    </div>
</template>
<script>
import {createOrEditTask, getTaskTableData, modifyTaskOpen} from "@/api/sysTask.js";
import {onMounted} from "vue";
import {layer} from "@layui/layui-vue";
import {changeDeptStatus, createOrUpdateDept} from "@/api/sysDept.js";

export default {
    name:'sysJob',
    setup() {
        const queryParam = ref({
            jobName: null,
            jobHandler: null,
            pageNum: 1,
            pageSize: 10
        })

        const columns = [
            {title: "任务名称", key: "jobName",align: 'center'},
            {title: "执行器", key: "jobHandler", align: 'center',customSlot: 'jobHandler'},
            {title: "CRON", key: "cron", align: 'center'},
            {title: "运行参数",key: "jobParams",align: 'center'},
            {title: "运行状态", key: "isOpen", align: 'center', customSlot: 'isOpen'},
            {title: "创建时间", key: "createTime", align: 'center'},
            {title: "任务描述", key: "jobDesc",ellipsisTooltip:true,ellipsisTooltipTheme:'dark'},
            {title: "操作", align: 'center', customSlot: 'operation'}
        ]

        const rowsTotal = ref(0)
        const tableData = ref([])

        const page = reactive({
            current: queryParam.value.pageNum,
            limit: queryParam.value.pageSize,
            total: rowsTotal,
            layout: ["prev", "page", "next", "limits", "count"],
        });

        const createParam = ref({
            jobName:null,
            jobHandler:null,
            cron:null,
            jobParams:null,
            isOpen:1,
            jobDesc:null
        })
        const isShow = ref(false)

        const action = ref([
            {
                text: "确认",
                callback: () => {
                    createOrEditTask(createParam.value).then(res => {
                        if (res.code === 200) {
                            isShow.value = false
                            handleQuery()
                            layer.notifiy({
                                title: "操作成功",
                                icon: 1
                            })
                        } else {
                            layer.notifiy({
                                title: "创建失败",
                                content: res.msg,
                                icon: 2
                            })
                        }
                    })
                }
            },
            {
                text: "取消",
                callback: () => {
                    isShow.value = false
                    for (let key in createParam.value) {
                        createParam.value[key] = null;
                    }
                }
            }
        ])

        function changePage(item) {
            queryParam.value.pageNum = item.current
            handleQuery()
        }

        const genDetailUrl = (row)=>{
            return "/monitor/task/detail/"+row.id;
        }

        function handleQuery(){
            getTaskTableData(queryParam.value).then(res=>{
                tableData.value = res.data.rows
                rowsTotal.value = res.data.total
            })
        }

        function resetParams(){
            for (let key in queryParam.value) {
                if (key !== "pageNum" && key !== "pageSize") {
                    queryParam.value[key] = null;
                }
            }
        }

        function switchChange(row){
            let param = {
                id:row.id
            }
            modifyTaskOpen(param).then(res=>{
                if (res.code===200){
                    handleQuery()
                    layer.notifiy({
                        title: "操作成功",
                        icon: 1
                    })
                }
            })
        }

        function handleCreate(){
            isShow.value = true;
        }

        function handleEdit(row){
            createParam.value = {...row}
            isShow.value = true
        }

        function handleDelete(row){
            const confirmText = `确定要删除任务【${row.jobName}】吗?`
            layer.confirm(confirmText,
                {
                    btn: [
                        {
                            text: '确定', callback: (id) => {
                                const changeParam = {
                                    id: row.id,
                                    isDeleted: 1
                                }
                                createOrEditTask(changeParam).then(res => {
                                    if (res.code === 200) {
                                        handleQuery()
                                        layer.notifiy({
                                            title: "操作成功",
                                            icon: 1
                                        })
                                    } else {
                                        layer.notifiy({
                                            title: "创建失败",
                                            content: res.msg,
                                            icon: 2
                                        })
                                    }
                                })
                                layer.close(id);
                            }
                        },
                        {
                            text: '取消', callback: (id) => {
                                layer.close(id);
                            }
                        }
                    ]
                }
            );
        }

        onMounted(()=>{
            handleQuery()
        })

        return {
            queryParam,
            columns,
            rowsTotal,
            tableData,
            page,
            createParam,
            isShow,
            action,
            changePage,
            handleQuery,
            resetParams,
            switchChange,
            handleCreate,
            handleEdit,
            handleDelete,
            genDetailUrl
        }
    }
}
</script>