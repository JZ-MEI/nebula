<template>
    <div class="view-container">
        <lay-form :model="queryParam" :pane="true" size="sm">
            <lay-row space="10">
                <lay-col md="6">
                    <lay-form-item label="角色名称" prop="roleName">
                        <lay-input v-model="queryParam.roleName" placeholder="请输入角色名称"></lay-input>
                    </lay-form-item>
                </lay-col>
                <lay-col md="6">
                    <lay-form-item label="角色字符" prop="roleKey">
                        <lay-input v-model="queryParam.roleKey" placeholder="请输入角色字符"></lay-input>
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
                <lay-button type="primary" @click="handleCreate" size="sm" v-permission="'role.add'">新增</lay-button>
            </template>
            <template v-slot:roleName="{row}">
                <router-link :to=genDetailUrl(row) class="table-link">
                    {{row.roleName}}
                </router-link>
            </template>
            <template v-slot:roleStatus="{row}">
                <lay-switch v-model="row.roleStatus" :unswitch-value=1 unswitch-text="停用" :onswitch-value=0
                            onswitch-text="正常" @change="switchChange(row)"></lay-switch>
            </template>
            <template v-slot:operation="{row}">
                <lay-button type="normal" size="xs" @click="authorization(row)">授权管理</lay-button>
                <lay-button type="primary" size="xs" @click="handleEdit(row)">编辑</lay-button>
                <lay-button type="danger" size="xs" @click="handleDelete(row)">删除</lay-button>
            </template>
        </lay-table>
        <lay-layer v-model="isShow" title="新增角色" :btn="action">
            <div class="createForm view-container">
                <lay-form :model="createParam" :pane="true">
                    <lay-row space=20>
                        <lay-col md="24" sm="24">
                            <lay-form-item label="角色名称" prop="roleName">
                                <lay-input v-model="createParam.roleName"></lay-input>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="角色字符" prop="roleKey">
                                <lay-input v-model="createParam.roleKey"></lay-input>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="排序" prop="orderNo">
                                <lay-input-number v-model="createParam.orderNo" :min="0"
                                                  class="width-control"></lay-input-number>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="24" sm="24">
                            <lay-form-item label="备注" prop="remark">
                                <lay-input v-model="createParam.remark"></lay-input>
                            </lay-form-item>
                        </lay-col>
                    </lay-row>
                </lay-form>
            </div>
        </lay-layer>
        <lay-layer v-model="showAuth" title="授权管理" :btn="authAction">
            <div class="createForm view-container treeForm">
                <lay-tree
                    :data="menuData"
                    :showCheckbox="true"
                    v-model:checkedKeys="checkedKeys"
                    :replaceFields="replaceFields"
                    :default-expand-all = "true"
                >
                </lay-tree>
            </div>
        </lay-layer>
    </div>
</template>
<script>

import {commitAuth, createOrUpdateSysRole, getRoleList, getRoleMenuList} from "@/api/sysRole.js";
import {onMounted} from "vue";
import {layer} from "@layui/layui-vue";
import {getMenuList} from "@/api/sysMenu.js";

export default {
    name:'sysRole',
    setup() {
        const queryParam = ref({
            roleName: null,
            roleKey: null,
            pageNum: 1,
            pageSize: 10
        })

        const rowsTotal = ref(0)
        const tableData = ref([])

        const page = reactive({
            current: queryParam.value.pageNum,
            limit: queryParam.value.pageSize,
            total: rowsTotal,
            layout: ["prev", "page", "next", "limits", "count"],
        });

        const columns = [
            {title: "角色名称", key: "roleName",customSlot: "roleName",align: "center"},
            {title: "角色字符", key: "roleKey", align: 'center'},
            {title: "排序", key: "orderNo", align: 'center'},
            {title: "角色状态", key: "roleStatus", align: 'center', customSlot: "roleStatus"},
            {title: "创建时间", key: "createTime", align: 'center'},
            {title: "操作", align: 'center', customSlot: 'operation'}
        ]

        const isShow = ref(false)

        const createParam = ref({
            roleName: null,
            roleKey: null,
            orderNo: 1,
            remark: null
        })

        const action = ref([
            {
                text: "确认",
                callback: () => {
                    createOrUpdateSysRole(createParam.value).then(res => {
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
                        if (key === "orderNo") {
                            createParam.value[key] = 1
                        } else {
                            createParam.value[key] = null;
                        }
                    }
                }
            }
        ])

        const showAuth = ref(false)
        const currentRoleId = ref()
        const replaceFields = ref({
            id: 'id',
            title: "menuTitle",
            children: "sysMenuList"

        })

        const checkedKeys = ref([])

        const menuData = ref([])

        const authAction = ref([
            {
                text: "确认",
                callback: () => {
                    let param = {
                        roleId: currentRoleId.value,
                        newMenuIdList: checkedKeys.value
                    }
                    commitAuth(param).then(res=>{
                        if (res.code === 200) {
                            showAuth.value = false
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
                    showAuth.value = false
                }
            }
        ])

        const genDetailUrl = (row)=>{
            return "/system/role/detail/"+row.id;
        }


        function handleQuery() {
            getRoleList(queryParam.value).then(res => {
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

        function handleCreate() {
            isShow.value = true
        }

        function handleEdit(row) {
            createParam.value = {...row}
            isShow.value = true
        }

        function switchChange(row) {
            if (row.roleStatus === 0) {
                row.roleStatus = 1
            } else {
                row.roleStatus = 0
            }
            const changeParam = {
                id: row.id,
                roleStatus: row.roleStatus
            }
            createOrUpdateSysRole(changeParam).then(res => {
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

        function handleDelete(row) {
            const confirmText = `确定要删除角色【${row.roleName}】吗?`
            layer.confirm(confirmText,
                {
                    btn: [
                        {
                            text: '确定', callback: (id) => {
                                const changeParam = {
                                    id: row.id,
                                    isDeleted: 1
                                }
                                createOrUpdateSysRole(changeParam).then(res => {
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

        function authorization(row) {
            getMenuData();
            const roleMenuParam={
                roleId:row.id
            }
            getRoleMenuList(roleMenuParam).then(res=>{
                checkedKeys.value = res.data
                showAuth.value = true
            })
            currentRoleId.value = row.id

        }

        function getMenuData() {
            let typeList = [1,2,3]
            let param = {
                typeList: typeList.join(',')
            }
            getMenuList(param).then(res => {
                menuData.value = res.data
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
            isShow,
            createParam,
            action,
            showAuth,
            replaceFields,
            checkedKeys,
            menuData,
            genDetailUrl,
            authAction,
            handleQuery,
            resetParams,
            changePage,
            handleCreate,
            handleEdit,
            switchChange,
            handleDelete,
            authorization
        }
    }
}
</script>