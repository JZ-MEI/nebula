<template>
    <div class="view-container">
        <lay-form :model="queryParam" :pane="true" size="sm">
            <lay-row space="10">
                <lay-col md="6">
                    <lay-form-item label="用户名" prop="username">
                        <lay-input v-model="queryParam.username" placeholder="请输入用户名"></lay-input>
                    </lay-form-item>
                </lay-col>
                <lay-col md="6">
                    <lay-form-item label="用户昵称" prop="nickname">
                        <lay-input v-model="queryParam.nickname" placeholder="请输入用户昵称"></lay-input>
                    </lay-form-item>
                </lay-col>
                <lay-col md="6">
                    <lay-form-item label="用户电话" prop="userTelephone">
                        <lay-input v-model="queryParam.userTelephone" placeholder="请输入用户电话"></lay-input>
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
            <template v-slot:userSex="{row}">
                <dict-tag :options="dictValue.default_sex" :values="row.userSex"></dict-tag>
            </template>
            <template v-slot:userStatus="{row}">
                <lay-switch v-model="row.userStatus" :unswitch-value=1 unswitch-text="禁用" :onswitch-value=0
                            onswitch-text="启用" @change="switchChange(row)"></lay-switch>
            </template>
            <template v-slot:operation="{row}">
                <lay-button type="primary" size="xs" @click="handleEdit(row)">编辑</lay-button>
                <lay-button type="normal" size="xs" @click="modifyPassword(row)">修改密码</lay-button>
                <lay-button type="danger" size="xs" @click="handleDelete(row)">删除</lay-button>

            </template>
        </lay-table>
        <lay-layer v-model="isShow" title="新增用户" :btn="action">
            <div class="createForm view-container">
                <lay-form :model="createParam" :pane="true">
                    <lay-row space=20>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="用户名" prop="username">
                                <lay-input v-model="createParam.username"></lay-input>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="昵称" prop="nickname">
                                <lay-input v-model="createParam.nickname"></lay-input>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24" v-if="isCreate">
                            <lay-form-item label="密码" prop="password">
                                <lay-input v-model="createParam.password" type="password" password></lay-input>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="电话" prop="userTelephone">
                                <lay-input v-model="createParam.userTelephone"></lay-input>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="邮箱" prop="userEmail">
                                <lay-input v-model="createParam.userEmail"></lay-input>
                            </lay-form-item>
                        </lay-col>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="性别" prop="userSex">
                                <lay-radio v-for="item in dictValue.default_sex" v-model="createParam.userSex"
                                           :value="item.dictKey" :label="item.dictValue"></lay-radio>
                            </lay-form-item>
                        </lay-col>
                    </lay-row>
                </lay-form>
            </div>
        </lay-layer>
        <lay-layer v-model="showPwd" title="修改密码" :btn="pwdAction">
            <div class="createForm view-container">
                <lay-form :model="modifyPwdParam" :pane="true">
                    <lay-row space=20>
                        <lay-col md="24">
                            <lay-form-item label="新密码" prop="password">
                                <lay-input v-model="modifyPwdParam.password"></lay-input>
                            </lay-form-item>
                        </lay-col>
                    </lay-row>
                </lay-form>
            </div>
        </lay-layer>
    </div>
</template>
<script>
import {createOrEditUserInfo, getUserTableData, modifyPwd} from "@/api/sysUser.js";
import {onMounted} from "vue";
import {getDict} from "@/util/dict.js";
import DictTag from "@/component/DictTag.vue";
import {layer} from "@layui/layui-vue";

export default {
    components: {DictTag},
    dicts: ["default_sex"],
    setup() {
        const proxy = getCurrentInstance();
        const dictValue = ref({})

        const queryParam = ref({
            username: null,
            nickname: null,
            userTelephone: null,
            userEmail: null,
            pageNum: 1,
            pageSize: 10
        })

        const tableData = ref([])
        const rowsTotal = ref(0)

        const columns = ref([
            {title: "用户名", key: "username", align: "center"},
            {title: "昵称", key: "nickname"},
            {title: "性别", key: "userSex", align: "center", customSlot: "userSex"},
            {title: "电话", key: "userTelephone", align: "center"},
            {title: "邮箱", key: "userEmail", align: "center"},
            {title: "用户状态", key: "userStatus", align: "center", customSlot: "userStatus"},
            {title: "创建时间", key: "createTime", align: "center"},
            {title: "操作", align: 'center', customSlot: 'operation'}
        ])

        const isCreate = ref(true)
        const isShow = ref(false)
        const showPwd = ref(false)

        const createParam = ref({
            username: null,
            nickname: null,
            password: null,
            userSex: null,
            userTelephone: null,
            userEmail: null,
        })

        const modifyPwdParam = ref({
            id:null,
            password:null
        })
        const action = ref([
            {
                text: "确认",
                callback: () => {
                    createOrEditUserInfo(createParam.value).then(res => {
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

        const pwdAction = ref([
            {
                text: "确认",
                callback: () => {
                    modifyPwd(modifyPwdParam.value).then(res => {
                        if (res.code === 200) {
                            showPwd.value = false
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
                    showPwd.value = false
                    for (let key in createParam.value) {
                        createParam.value[key] = null;
                    }
                }
            }
        ])

        function handleQuery() {
            getUserTableData(queryParam.value).then(res => {
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
            isCreate.value = true
            isShow.value = true
        }

        function handleEdit(row) {
            createParam.value = {...row}
            isCreate.value = false
            isShow.value = true
        }

        function switchChange(row) {

            if (row.userStatus === 0) {
                row.userStatus = 1
            } else {
                row.userStatus = 0
            }
            createOrEditUserInfo(row).then(res => {
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
        }

        function handleDelete(row){
            const confirmText = `确定要删除用户【${row.nickname}】吗?`
            layer.confirm(confirmText,
                {
                    btn: [
                        {
                            text: '确定', callback: (id) => {
                                row.isDeleted = 1
                                createOrEditUserInfo(row).then(res => {
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
        function modifyPassword(row){
            modifyPwdParam.value.id=row.id
            showPwd.value = true
        }

        onMounted(() => {
            handleQuery()
            dictValue.value = getDict(proxy)
        })


        return {
            queryParam,
            tableData,
            rowsTotal,
            page,
            columns,
            isCreate,
            createParam,
            dictValue,
            isShow,
            action,
            modifyPwdParam,
            showPwd,
            pwdAction,
            resetParams,
            handleQuery,
            changePage,
            handleCreate,
            handleEdit,
            switchChange,
            handleDelete,
            modifyPassword
        }
    }
}
</script>