<template>
    <div class="view-container">
        <lay-form :model="queryParam" :pane="true" size="sm">
            <lay-row space="10">
                <lay-col md="6">
                    <lay-form-item label="用户名称" prop="nickname">
                        <lay-select v-model="queryParam.userId" :show-search="true" class="width-control"
                                    allow-clear>
                            <lay-select-option v-for="item in userInfo" :value="item.id"
                                               :label="item.nickname"></lay-select-option>
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
                   :default-toolbar="true" @change="changePage" :page="page">
            <template v-slot:toolbar>
                <lay-button type="primary" @click="handleCreate" size="sm">添加用户</lay-button>
            </template>
            <template v-slot:operation="{row}">
                <lay-button type="primary" size="xs" @click="handleEdit(row)">编辑</lay-button>
                <lay-button type="danger" size="xs" @click="handleDelete(row)">删除</lay-button>
            </template>
        </lay-table>
        <lay-layer v-model="isShow" title="添加用户" :btn="action">
            <div class="createForm view-container">
                <lay-form :model="createParam" :pane="true">
                    <lay-row space=20>
                        <lay-col md="12" sm="24">
                            <lay-form-item label="用户" prop="leader">
                                <lay-select v-model="createParam.userId" :show-search="true" class="width-control"
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
                        <lay-col md="24" sm="24">
                            <lay-form-item label="岗位" prop="deptPost">
                                <lay-input v-model="createParam.deptPost"></lay-input>
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
import {getNormalUser} from "@/api/sysUser.js";
import {onMounted} from "vue";
import {
    addOrEditUserToDept,
    createOrUpdateDept,
    getDeptUser,
    getDeptName,
    changeDeptStatus,
    removeUserFormDept
} from "@/api/sysDept.js";
import DictTag from "@/component/DictTag.vue";
import {layer} from "@layui/layui-vue";

export default {
    name:'deptDetail',
    components: {DictTag},
    setup() {
        const route = useRoute()
        const deptId = route.params.deptId
        const deptName = ref()
        const queryParam = ref({
            userId: null,
            deptId: deptId,
            pageNum: 1,
            pageSize: 10
        })
        const isShow = ref(false)
        const userInfo = ref([])

        const rowsTotal = ref(0)
        const tableData = ref([])

        const columns = [
            {title: "岗位", key: "deptPost", align: 'center'},
            {title: "姓名", key: "nickname", align: 'center'},
            {title: "电话", key: "userTelephone", align: 'center'},
            {title: "邮箱", key: "userEmail", align: 'center'},
            {title: "添加时间", key: "createTime", align: 'center'},
            {title: "排序", key: "orderNo", align: 'center'},
            {title: "操作", align: 'center', customSlot: 'operation'}
        ]
        const createParam = ref({
            userId: null,
            deptId: deptId,
            orderNo: 1,
            deptPost: null,
        })

        const action = ref([
            {
                text: "确认",
                callback: () => {
                    addOrEditUserToDept(createParam.value).then(res => {
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
                        switch (key) {
                            case "orderNo":
                                createParam.value[key] = 1;
                                continue;
                            case "deptId":
                                createParam.value[key] = deptId;
                                continue;
                            default:
                                createParam.value[key] = null;
                        }
                    }
                }
            }
        ])

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

        function loadDeptName(id){
            getDeptName(id).then(res=>{
                deptName.value = res.data
            })
        }
        function getUserInfo() {
            getNormalUser().then(res => {
                if (res.code === 200) userInfo.value = res.data
            })
        }

        function handleQuery() {
            getDeptUser(queryParam.value).then(res => {
                tableData.value = res.data.rows
                rowsTotal.value = res.data.total
            })
        }

        function resetParams() {
            for (let key in queryParam.value) {
                if (key !== "pageNum" && key !== "pageSize" && key !== "deptId") {
                    queryParam.value[key] = null;
                }
            }
        }

        function handleCreate() {
            isShow.value = true
        }

        function handleEdit(row){
            createParam.value = {...row}
            isShow.value = true
        }

        function handleDelete(row){
            const confirmText = `确定要移除部门【${deptName.value}】下的用户【${row.nickname}】吗?`
            layer.confirm(confirmText,
                {
                    btn: [
                        {
                            text: '确定', callback: (id) => {
                                const removeParam = {
                                    id: row.id,
                                }
                                removeUserFormDept(removeParam).then(res => {
                                    if (res.code === 200) {
                                        handleQuery()
                                        layer.notify({
                                            title: "操作成功",
                                            icon: 1
                                        })
                                    } else {
                                        layer.notify({
                                            title: "操作失败",
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
            loadDeptName(route.params.deptId)
        })
        return {
            deptId,
            queryParam,
            userInfo,
            rowsTotal,
            tableData,
            columns,
            action,
            page,
            isShow,
            createParam,
            handleEdit,
            changePage,
            handleQuery,
            resetParams,
            handleCreate,
            handleDelete
        }
    }
}
</script>