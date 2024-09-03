<template>
    <div class="view-container">
        <lay-form :model="queryParam" :pane="true" size="sm">
            <lay-row space="10">
                <lay-col md="6">
                    <lay-form-item label="字典名称" prop="dictName">
                        <lay-input v-model="queryParam.dictName" placeholder="字典名称"></lay-input>
                    </lay-form-item>
                </lay-col>
                <lay-col md="6">
                    <lay-form-item label="字典类型" prop="dictType">
                        <lay-input v-model="queryParam.dictType" placeholder="字典类型"></lay-input>
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
                   :default-toolbar="true" @change="changePage" :page="page">
            <template v-slot:toolbar>
                <lay-button type="primary" @click="handleCreate" size="sm">新增</lay-button>
            </template>
            <template v-slot:dictType="{row}">
                <router-link :to=genDetailUrl(row) class="table-link">
                    {{row.dictType}}
                </router-link>
            </template>
            <template v-slot:operation="{row}">
                <lay-button type="primary" size="xs" @click="handleEdit(row)">编辑</lay-button>
                <lay-button type="danger" size="xs" @click="handleDelete(row)">删除</lay-button>
            </template>
        </lay-table>
        <lay-layer v-model="isShow" title="新增字典" :btn="action">
            <div class="createForm view-container">
                <lay-form :model="createParam" :pane="true">
                    <lay-row space=20>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="字典名称" prop="dictName">
                                <lay-input v-model="createParam.dictName"></lay-input>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="字典类型" prop="dictType">
                                <lay-input v-model="createParam.dictType"></lay-input>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="24">
                            <lay-form-item label="备注" prop = "remark">
                                <lay-input v-model="createParam.remark"></lay-input>
                            </lay-form-item>
                        </lay-col>
                    </lay-row>
                </lay-form>
            </div>
        </lay-layer>
    </div>
</template>
<script>

import {createOrEditDictType, deleteDictType, getDictTableData} from "@/api/sysDict.js";
import {onMounted} from "vue";
import {layer} from "@layui/layui-vue";

export default {
    name:'sysDict',
    setup() {
        const queryParam = ref({
            dictName: null,
            dictType: null,
            pageNum: 1,
            pageSize: 10,
        })

        const createParam = ref({
            dictName:null,
            dictType:null,
            remark:null,
        })
        const isShow = ref(false)

        const columns = ref([
            {title: "字典名称", key: "dictName", align: "center"},
            {title: "字典类型", key: "dictType", align: "center",customSlot: 'dictType'},
            {title: "创建时间", key: "createTime", align: "center"},
            {title: "备注", key: "remark", align: "center"},
            {title: "操作", align: 'center', customSlot: 'operation'}
        ])
        const tableData = ref([])
        const rowsTotal = ref(0)

        const genDetailUrl = (row)=>{
            return "/system/dict/detail/"+row.id;
        }

        const page = reactive({
            current: queryParam.value.pageNum,
            limit: queryParam.value.pageSize,
            total: rowsTotal,
            layout: ["prev", "page", "next", "limits", "count"],
        });

        function resetParams() {
            for (let key in queryParam.value) {
                queryParam.value[key] = null;
            }
        }
        function handleQuery() {
            getDictTableData(queryParam.value).then(res=>{
                tableData.value = res.data.rows
                rowsTotal.value = res.data.total
            })
        }

        function changePage(item) {
            queryParam.value.pageNum = item.current
            handleQuery()
        }

        function handleCreate() {
            isShow.value = true
        }

        function handleEdit(row){
            handleCreate()
            createParam.value = {...row}
        }

        const action = ref([
            {
                text: "确认",
                callback: () => {
                    createOrEditDictType(createParam.value).then(res => {
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
                        createParam.value[key] = null;
                    }
                }
            }
        ])

        function handleDelete(row){
            const confirmText = `确定要删除字典类型为【${row.dictType}】及其所有字典值吗?`
            layer.confirm(confirmText,
                {
                    btn: [
                        {
                            text: '确定', callback: (id) => {
                                let param = {id: row.id}
                                deleteDictType(param).then(res => {
                                    if (res.code === 200) {
                                        layer.notify({
                                            title: "成功",
                                            content: res.msg,
                                            icon: 1
                                        })
                                    } else {
                                        layer.notify({
                                            title: "失败",
                                            content: res.msg,
                                            icon: 2
                                        })
                                    }
                                    handleQuery()
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
            tableData,
            page,
            changePage,
            rowsTotal,
            createParam,
            isShow,
            action,
            resetParams,
            handleQuery,
            genDetailUrl,
            handleEdit,
            handleDelete,
            handleCreate
        }
    }
}
</script>