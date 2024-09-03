<template>
    <div class="view-container">
        <lay-form :model="queryParam" :pane="true" size="sm">
            <lay-row space="10">
                <lay-col md="6">
                    <lay-form-item label="部门名称" prop="deptName">
                        <lay-input v-model="queryParam.deptName" placeholder="请输入部门名称"></lay-input>
                    </lay-form-item>
                </lay-col>
                <lay-col md="6">
                    <lay-form-item label="部门状态" prop="deptStatus">
                        <lay-select v-model="queryParam.deptStatus" class="width-control" placeholder="请选择部门状态"
                                    :allow-clear="true">
                            <lay-select-option :value=0 label="正常"></lay-select-option>
                            <lay-select-option :value=1 label="停用"></lay-select-option>
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
        <lay-table :columns="columns" children-column-name="childList" :data-source="tableData"
                   :default-toolbar="true" :default-expand-all="false" @change="changePage" :page="page">
            <template v-slot:toolbar>
                <lay-button type="primary" @click="handleCreate" size="sm">新增</lay-button>
            </template>
            <template v-slot:deptName="{row}">
                <router-link :to=genDetailUrl(row) class="table-link">
                    {{row.deptName}}
                </router-link>
            </template>
            <template v-slot:deptStatus="{row}">
                <lay-switch v-model="row.deptStatus" :unswitch-value=1 unswitch-text="停用" :onswitch-value=0
                            onswitch-text="正常" @change="switchChange(row)"></lay-switch>
            </template>
            <template v-slot:operation="{row}">
                <lay-button type="primary" size="xs" @click="handleEdit(row)">编辑</lay-button>
                <lay-button type="danger" size="xs" @click="handleDelete(row)">删除</lay-button>
            </template>
        </lay-table>
        <lay-layer v-model="isShow" title="新增部门" :btn="action">
            <div class="createForm view-container">
                <lay-form :model="createParam" :pane="true">
                    <lay-row space=20>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="父级菜单" prop="parentId">
                                <lay-tree-select v-model="createParam.parentId" :data="tableData"
                                                 :replaceFields="replaceFields" class="width-control"
                                                 allow-clear></lay-tree-select>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="部门名称" prop="deptName">
                                <lay-input v-model="createParam.deptName"></lay-input>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="部门领导" prop="leader">
                                <lay-select v-model="createParam.leader" :show-search="true" class="width-control"
                                            allow-clear>
                                    <lay-select-option v-for="item in userInfo" :value="item.id"
                                                       :label="item.nickname"></lay-select-option>
                                </lay-select>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="排序" prop="orderNo">
                                <lay-input-number v-model="createParam.orderNo" :min="0"
                                                  class="width-control"></lay-input-number>
                            </lay-form-item>
                        </lay-col>
                    </lay-row>
                </lay-form>
            </div>
        </lay-layer>
    </div>
</template>

<script>
import {onMounted} from "vue";
import {changeDeptStatus, createOrUpdateDept, getTableData} from "@/api/sysDept.js";
import {layer} from "@layui/layui-vue";
import {getNormalUser} from "@/api/sysUser.js";

export default {
    name:'sysDept',
    setup() {
        const queryParam = ref({
            deptName: null,
            deptStatus: null,
            pageNum: 1,
            pageSize: 10
        })

        const columns = [
            {title: "部门名称", key: "deptName",customSlot: "deptName"},
            {title: "管理人", key: "leaderName", align: 'center'},
            {title: "排序", key: "orderNo", align: 'center'},
            {title: "部门状态", key: "deptStatus", align: 'center', customSlot: 'deptStatus'},
            {title: "创建时间", key: "createTime", align: 'center'},
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

        const isShow = ref(false)
        const createParam = ref({
            deptName: null,
            parentId: null,
            leader: null,
            orderNo: 1,
        })
        const replaceFields = ref({
            title: "deptName",
            children: "childList"

        })

        const userInfo =ref([])

        const action = ref([
            {
                text: "确认",
                callback: () => {
                    createOrUpdateDept(createParam.value).then(res => {
                        if (res.code === 200) {
                            isShow.value = false
                            handleQuery()
                            layer.notify({
                                title: "操作成功",
                                icon: 1
                            })
                        } else {
                            layer.notify({
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
                        if (key==="orderNo"){
                            createParam.value[key] = 1
                        }else{
                            createParam.value[key] = null;
                        }
                    }
                }
            }
        ])

        const genDetailUrl = (row)=>{
            return "/system/dept/detail/"+row.id;
        }
        function changePage(item) {
            queryParam.value.pageNum = item.current
            handleQuery()
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

        function switchChange(row) {
            if (row.deptStatus === 0) {
                row.deptStatus = 1
            } else {
                row.deptStatus = 0
            }
            const changeParam = {
                id: row.id,
                deptStatus: row.deptStatus
            }
            changeDeptStatus(changeParam).then(res => {
                if (res.code === 200) {
                    handleQuery()
                    layer.notify({
                        title: "操作成功",
                        icon: 1
                    })
                } else {
                    layer.notify({
                        title: "创建失败",
                        content: res.msg,
                        icon: 2
                    })
                }
            })
        }

        function handleCreate(){
            isShow.value = true
        }

        function getUserInfo(){
            getNormalUser().then(res=>{
                if (res.code===200) userInfo.value = res.data
            })
        }

        function handleEdit(row){
            createParam.value = {...row}
            handleCreate()
        }

        function handleDelete(row){
            const confirmText = `确定要删除部门【${row.deptName}】及其下属部门吗?`
            layer.confirm(confirmText,
                {
                    btn: [
                        {
                            text: '确定', callback: (id) => {
                                const changeParam = {
                                    id: row.id,
                                    isDelete: 1
                                }
                                changeDeptStatus(changeParam).then(res => {
                                    if (res.code === 200) {
                                        handleQuery()
                                        layer.notify({
                                            title: "操作成功",
                                            icon: 1
                                        })
                                    } else {
                                        layer.notify({
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



        onMounted(() => {
            getUserInfo()
            handleQuery()
        })

        return {
            queryParam,
            columns,
            page,
            tableData,
            isShow,
            createParam,
            replaceFields,
            userInfo,
            action,
            genDetailUrl,
            handleQuery,
            changePage,
            resetParams,
            switchChange,
            handleCreate,
            handleEdit,
            handleDelete
        }
    }
}
</script>