<template>
    <div class="view-container">
        <lay-form :model="queryParam" :pane="true" size="sm">
            <lay-row space="10">
                <lay-col md="6">
                    <lay-form-item label="菜单名称" prop="menuName">
                        <lay-input v-model="queryParam.menuName" placeholder="请选择菜单名称"></lay-input>
                    </lay-form-item>
                </lay-col>
                <lay-col md="6">
                    <lay-form-item label="菜单类型" prop="menuType">
                        <lay-select v-model="queryParam.menuType" class="width-control" placeholder="请选择菜单类型"
                                    :allow-clear="true">
                            <lay-select-option v-for="item in dictValue.menu_type" :value="item.dictKey"
                                               :label="item.dictValue"></lay-select-option>
                        </lay-select>
                    </lay-form-item>
                </lay-col>
                <lay-col md="6">
                    <lay-form-item label="是否显示" prop="isVisiable">
                        <lay-select v-model="queryParam.isVisiable" class="width-control" placeholder="请选择是否显示"
                                    :allow-clear="true">
                            <lay-select-option v-for="item in dictValue.yes_or_no" :value="item.dictKey"
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
        <lay-table :columns="columns" children-column-name="sysMenuList" :data-source="tableData"
                   :default-toolbar="true" :default-expand-all="false" @change="changePage" :page="page">
            <template v-slot:toolbar>
                <lay-button type="primary" @click="handleCreate" size="sm">新增</lay-button>
            </template>
            <template v-slot:menuIcon="{ row }">
                <lay-icon :type="row.menuIcon"></lay-icon>
            </template>
            <template v-slot:operation="{row}">
                <lay-button type="primary" size="xs" @click="handleEdit(row)">编辑</lay-button>
                <lay-button type="danger" size="xs" @click="handleDelete(row)">删除</lay-button>
            </template>
            <template v-slot:dictType="{row}">
                <dict-tag :options="dictValue.menu_type" :values="row.menuType"></dict-tag>
            </template>
            <template v-slot:isCache="{row}">
                <dict-tag :options="dictValue.yes_or_no" :values="row.isCache"></dict-tag>
            </template>
            <template v-slot:isThird="{row}">
                <dict-tag :options="dictValue.yes_or_no" :values="row.isThird"></dict-tag>
            </template>
            <template v-slot:isVisiable="{row}">
                <dict-tag :options="dictValue.yes_or_no" :values="row.isVisiable"></dict-tag>
            </template>

        </lay-table>
        <lay-layer v-model="isShow" title="新增菜单" :btn="action">
            <div class="createForm view-container">
                <lay-form :model="createParam" :pane="true">
                    <lay-row space=20>
                        <lay-col md="24">
                            <lay-form-item label="父级菜单" prop="parentId">
                                <lay-tree-select v-model="createParam.parentId" :data="tableData"
                                                 :replaceFields="replaceFields" class="width-control"
                                                 allow-clear></lay-tree-select>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="菜单名称" prop="menuName">
                                <lay-input v-model="createParam.menuName"></lay-input>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="菜单图标" prop="menuIcon">
                                <lay-icon-picker v-model="createParam.menuIcon" page allow-clear
                                                 class="width-control"></lay-icon-picker>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="菜单类型" prop="menuType">
                                <lay-select v-model="createParam.menuType" placeholder="请选择" class="width-control"
                                            allow-clear>
                                    <lay-select-option v-for="item in dictValue.menu_type" :value="item.dictKey"
                                                       :label="item.dictValue"></lay-select-option>
                                </lay-select>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="权限标识" prop="permission">
                                <lay-input v-model="createParam.permission"></lay-input>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="路由地址" prop="routerPath">
                                <lay-input v-model="createParam.routerPath"></lay-input>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="组件地址" prop="compPath">
                                <lay-input v-model="createParam.compPath"></lay-input>
                            </lay-form-item>
                        </lay-col>

                        <lay-col md="12" sm="24">
                            <lay-form-item label="是否缓存" prop="isCache">
                                <lay-select v-model="createParam.isCache" placeholder="请选择" class="width-control"
                                            allow-clear>
                                    <lay-select-option v-for="item in dictValue.yes_or_no" :value="item.dictKey"
                                                       :label="item.dictValue"></lay-select-option>
                                </lay-select>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="是否外链" prop="isThird">
                                <lay-select v-model="createParam.isThird" placeholder="请选择" class="width-control"
                                            allow-clear>
                                    <lay-select-option v-for="item in dictValue.yes_or_no"  :value="item.dictKey"
                                                       :label="item.dictValue"></lay-select-option>
                                </lay-select>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="是否可见" prop="isVisiable">
                                <lay-select v-model="createParam.isVisiable" placeholder="请选择" class="width-control"
                                            allow-clear>
                                    <lay-select-option v-for="item in dictValue.yes_or_no" :value="item.dictKey"
                                                       :label="item.dictValue"></lay-select-option>
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
import {getMenuTableData, createOrEditMenu, deleteById} from "@/api/sysMenu.js";
import {getDict} from "@/util/dict.js";
import {layer} from "@layui/layui-vue";
import DictTag from "@/component/DictTag.vue";


export default {
    dicts: ["menu_type", "yes_or_no"],
    components: {DictTag},
    setup() {
        const isShow = ref(false)
        const checkValue = 1
        let createParam = ref({
            parentId: null,
            menuName: null,
            menuIcon: "layui-icon-home",
            menuType: null,
            permission: null,
            routerPath: null,
            compPath: null,
            orderNo: 1,
            isCache: 1,
            isThird: 0,
            isVisiable: 1
        })


        const rowsTotal = ref(0)
        const replaceFields = ref({
            title: "menuName",
            children: "sysMenuList"

        })

        const queryParam = ref({
            menuName: null,
            menuType: null,
            isVisiable: null,
            pageNum: 1,
            pageSize: 10,
        })
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

        const columns = [
            {title: "菜单名称", key: "menuName"},
            {title: "菜单图标", key: "menuIcon", customSlot: "menuIcon", align: 'center'},
            {title: "菜单类型", key: "menuType", align: 'center', customSlot: 'dictType'},
            {title: "权限标识", key: "permission", align: 'center'},
            {title: "路由地址", key: "routerPath", align: 'center'},
            {title: "组件地址", key: "compPath", align: 'center'},
            {title: "排序", key: "orderNo", align: 'center'},
            {title: "是否缓存", key: "isCache", align: 'center', customSlot: 'isCache'},
            {title: "是否外链", key: "isThird", align: 'center', customSlot: 'isThird'},
            {title: "是否可见", key: "isVisiable", align: 'center', customSlot: 'isVisiable'},
            {title: "操作", align: 'center', customSlot: 'operation'}
        ]
        const tableData = ref([])



        function resetParams() {
            for (let key in queryParam.value) {
                queryParam.value[key] = null;
            }
        }

        const proxy = getCurrentInstance();
        const dictValue = ref({})

        function handleQuery() {
            getMenuTableData(queryParam.value).then(res => {
                tableData.value = res.data.rows
                rowsTotal.value = res.data.total
            })
        }

        function handleCreate() {
            isShow.value = true
        }

        const action = ref([
            {
                text: "确认",
                callback: () => {
                    createOrEditMenu(createParam.value).then(res => {
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
                            case "menuIcon":
                                createParam.value[key] = "layui-icon-home";
                                continue;
                            case "orderNo":
                                createParam.value[key] = 1;
                                continue;
                            default:
                                createParam.value[key] = null;
                        }
                    }
                }
            }
        ])

        function handleEdit(row) {
            handleCreate()

            createParam.value = {...row}
        }

        function handleDelete(row) {
            const confirmText = row.sysMenuList ? `确定要删除${row.menuName}及其下属子菜单吗?` : `确定要删除${row.menuName}吗?`
            layer.confirm(confirmText,
                {
                    btn: [
                        {
                            text: '确定', callback: (id) => {
                                let param = {id: row.id}
                                deleteById(param).then(res => {
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
            // getDict(proxy)
        })

        return {
            rowsTotal,
            page,
            isShow,
            columns,
            tableData,
            queryParam,
            resetParams,
            handleCreate,
            handleQuery,
            createParam,
            replaceFields,
            action,
            dictValue,
            proxy,
            checkValue,
            changePage,
            handleEdit,
            handleDelete
        }
    }
}
</script>