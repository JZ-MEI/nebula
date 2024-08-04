<template>
    <div class="view-container">
        <lay-form :model="queryParam" :pane="true" size="sm">
            <lay-row space="10">
                <lay-col md="6">
                    <lay-form-item label="字典名称" prop="dictName">
                        <lay-input v-model="queryParam.dictKey" placeholder="字典键"></lay-input>
                    </lay-form-item>
                </lay-col>
                <lay-col md="6">
                    <lay-form-item label="字典类型" prop="dictType">
                        <lay-input v-model="queryParam.dictValue" placeholder="字典值"></lay-input>
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
            <template v-slot:useTag="{row}">
                <dict-tag :options="dictValue.yes_or_no" :values="row.useTag"></dict-tag>
            </template>
            <template v-slot:tagType="{row}">
                <lay-tag :type="row.tagType" v-if="row.tagType" variant="light">{{ row.dictValue }}</lay-tag>
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
                            <lay-form-item label="字典键" prop="dictKey">
                                <lay-input v-model="createParam.dictKey"></lay-input>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="字典值" prop="dictValue">
                                <lay-input v-model="createParam.dictValue"></lay-input>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="使用标签" prop="useTag">
                                <lay-select v-model="createParam.useTag" placeholder="请选择" class="width-control"
                                            allow-clear>
                                    <lay-select-option v-for="item in dictValue.yes_or_no" :value="item.dictKey"
                                                       :label="item.dictValue"></lay-select-option>
                                </lay-select>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="8" sm="20" v-if="createParam.useTag==='1'">
                            <lay-form-item label="标签类型" prop="tagType">
                                <lay-select v-model="createParam.tagType" placeholder="请选择" class="width-control">
                                    <lay-select-option value="primary" label="primary"></lay-select-option>
                                    <lay-select-option value="normal" label="normal"></lay-select-option>
                                    <lay-select-option value="warm" label="warm"></lay-select-option>
                                    <lay-select-option value="danger" label="danger"></lay-select-option>
                                </lay-select>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="4" sm="4" v-if="createParam.useTag==='1'">
                            <div style="line-height: 38px">
                                <lay-tag :type="createParam.tagType" variant="light">{{ createParam.dictValue }}</lay-tag>
                            </div>
                        </lay-col>
                        <lay-col md="24">
                            <lay-form-item label="备注" prop="remark">
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
import {useRoute} from "vue-router";
import {createOrEditDictData, deleteDictData, getDictData} from "@/api/sysDict.js";
import {onMounted} from "vue";
import DictTag from "@/component/DictTag.vue";
import {getDict} from "@/util/dict.js";
import {layer} from "@layui/layui-vue";

export default {
    name:'dictDetail',
    dicts: ["yes_or_no"],
    components: {DictTag},
    setup() {
        const route = useRoute()
        const proxy = getCurrentInstance();
        const dictValue = ref({})
        const isShow = ref(false)

        const queryParam = ref({
            dictKey: null,
            dictValue: null,
            typeId: route.params.dictId,
            pageNum: 1,
            pageSize: 10
        })

        const createParam = ref({
            typeId: route.params.dictId,
            dictKey: null,
            dictValue: null,
            useTag: '0',
            tagType: null,
            remark: null,
        })

        const tableData = ref([])
        const rowsTotal = ref(0)
        const columns = ref([
            {title: "创建时间", key: "createTime", align: "center"},
            {title: "字典键", key: "dictKey", align: "center"},
            {title: "字典值", key: "dictValue", align: "center"},
            {title: "使用标签", key: "useTag", align: "center", customSlot: 'useTag'},
            {title: "标签样式", key: "tagType", align: 'center', customSlot: 'tagType'},
            {title: "备注", key: "remark", align: 'center'},
            {title: "操作", customSlot: 'operation'}
        ])

        const action = ref([
            {
                text: "确认",
                callback: () => {
                    createOrEditDictData(createParam.value).then(res => {
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
                        switch (key) {
                            case "typeId":
                                createParam.value[key] = route.params.dictId;
                                continue;
                            case "useTag":
                                createParam.value[key] = 0;
                                continue;
                            default:
                                createParam.value[key] = null;
                        }
                    }
                }
            }
        ])

        // , customSlot: 'operation'

        function handleQuery() {
            getDictData(queryParam.value).then(res => {
                tableData.value = res.data.rows
                rowsTotal.value = res.data.total
            })
        }

        function resetParams() {
            for (let key in queryParam.value) {
                queryParam.value[key] = null;
            }
        }

        const page = reactive({
            current: queryParam.value.pageNum,
            limit: queryParam.value.pageSize,
            total: rowsTotal,
            layout: ["prev", "page", "next", "limits", "count"],
        });

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

        function handleDelete(row){
            const confirmText = `确定要删除字典值为【${row.dictValue}】的数据吗?`
            layer.confirm(confirmText,
                {
                    btn: [
                        {
                            text: '确定', callback: (id) => {
                                let param = {id: row.id}
                                deleteDictData(param).then(res => {
                                    if (res.code === 200) {
                                        layer.notifiy({
                                            title: "成功",
                                            content: res.msg,
                                            icon: 1
                                        })
                                    } else {
                                        layer.notifiy({
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

        onMounted(() => {
            handleQuery()
            dictValue.value = getDict(proxy)
        })

        return {
            queryParam,
            tableData,
            columns,
            dictValue,
            page,
            isShow,
            createParam,
            action,
            changePage,
            handleQuery,
            resetParams,
            handleCreate,
            handleEdit,
            handleDelete
        }
    }
}
</script>