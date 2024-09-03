<template xmlns:lay-row="http://www.w3.org/1999/html">
    <div class="view-container">
        <lay-form :model="queryParam" :pane="true" size="sm">
            <lay-row space="10">
                <lay-col md="6">
                    <lay-form-item label="用户名称" prop="nickname">
                        <lay-input v-model="queryParam.nickname" placeholder="请输入用户名称"></lay-input>
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
                <lay-button type="primary" @click="handleCreate" size="sm">添加</lay-button>
            </template>
            <template v-slot:operation="{row}">
                <lay-button type="danger" size="xs" @click="handleDelete(row)">删除</lay-button>
            </template>
        </lay-table>
        <lay-layer v-model="isShow" title="添加用户" :btn="action">
            <div class="createForm view-container">
                <lay-form :model="userParam" :pane="true">
                    <lay-row space=20>
                        <lay-col md="6">
                            <lay-form-item label="用户名称" prop="nickname">
                                <lay-input v-model="userParam.nickname"></lay-input>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="6">
                            <lay-form-item>
                                <lay-button type="primary" @click="queryUser">搜索</lay-button>
                            </lay-form-item>
                        </lay-col>
                    </lay-row>
                </lay-form>
                <lay-form :model="createParam" :pane="true">
                    <lay-row space = 30>
                        <lay-col md="4" v-for="item in userData">
                        <lay-radio-button v-model="createParam.userId" :value="item.id"
                                          :label="item.nickname"></lay-radio-button>
                        </lay-col>
                    </lay-row>
                </lay-form>
                <lay-page  v-model="userParam.pageNum" :limit="userParam.pageSize" :total="rowsTotal" @change="userPage"></lay-page>
            </div>
        </lay-layer>
    </div>
</template>
<script>
import {createOrDeleteRoleUser, createOrUpdateSysRole, getRoleUserTableData} from "@/api/sysRole.js";
import {useRoute} from "vue-router";
import {onMounted} from "vue";
import {getUserTableData} from "@/api/sysUser.js";
import {layer} from "@layui/layui-vue";

export default {
    name:'roleDetail',
    setup() {

        const route = useRoute()
        const roleId = route.params.roleId
        const queryParam = ref({
            nickname: null,
            roleId: roleId,
            pageNum: 1,
            pageSize: 10
        })
        const rowsTotal = ref(0)
        const tableData = ref([])
        const columns = ref([
            {title: "昵称", key: "nickname",align: "center"},
            {title: "性别", key: "userSex", align: "center"},
            {title: "电话", key: "userTelephone", align: "center"},
            {title: "邮箱", key: "userEmail", align: "center"},
            {title: "创建时间", key: "createTime", align: "center"},
            {title: "操作", align: 'center', customSlot: 'operation'}
        ])

        const isShow = ref(false)

        const page = reactive({
            current: queryParam.value.pageNum,
            limit: queryParam.value.pageSize,
            total: rowsTotal,
            layout: ["prev", "page", "next", "limits", "count"],
        });

        const createParam = ref({
            roleId: roleId,
            userId: null
        })

        const userData = ref([])
        const userTotal = ref(0)
        const userParam = ref({
            nickname: null,
            pageNum: 1,
            pageSize: 30
        })

        const action = ref([
            {
                text: "确认",
                callback: () => {
                    createOrDeleteRoleUser(createParam.value).then(res => {
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
                    createParam.value["userId"]=null
                }
            }
        ])
        function changePage(item) {
            queryParam.value.pageNum = item.current
            handleQuery()
        }

        function handleQuery() {
            getRoleUserTableData(queryParam.value).then(res => {
                tableData.value = res.data.rows
                rowsTotal.value = res.data.total
            })
        }

        function resetParams() {
            for (let key in queryParam.value) {
                switch (key) {
                    case "pageNum":
                    case "pageSize":
                        continue;
                    case "roleId":
                        queryParam.value[key] = roleId;
                        continue;
                    default:
                        queryParam.value[key] = null
                }
            }
        }


        function handleCreate() {
            queryUser()
            isShow.value = true
        }

        function queryUser() {
            getUserTableData(userParam.value).then(res => {
                userData.value = res.data.rows
                userTotal.value = res.data.total
            })
        }
        function userPage(item) {
            userParam.value.pageNum = item.current
            queryUser()
        }
        function handleDelete(row) {
            const confirmText = `确定要删除当前角色下的用户【${row.nickname}】吗?`
            layer.confirm(confirmText,
                {
                    btn: [
                        {
                            text: '确定', callback: (id) => {
                                const changeParam = {
                                    id: row.id,
                                }
                                createOrDeleteRoleUser(changeParam).then(res => {
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
            handleQuery()
        })

        return {
            queryParam,
            rowsTotal,
            tableData,
            columns,
            page,
            isShow,
            createParam,
            userData,
            userTotal,
            userParam,
            action,
            changePage,
            handleQuery,
            resetParams,
            handleCreate,
            queryUser,
            userPage,
            handleDelete
        }
    }
}
</script>