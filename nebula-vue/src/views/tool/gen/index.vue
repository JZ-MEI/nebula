<template>
    <div class="view-container">
        <lay-row :space="15">
            <lay-col :md="5">
                <lay-quote>表信息</lay-quote>
                <lay-table
                    :resize="true"
                    :height="'100%'"
                    :columns="columns"
                    :data-source="tableData"
                    @row-double="doubleClick"
                />
            </lay-col>
            <lay-col :md="19">
                <lay-quote>表字段信息</lay-quote>
                <lay-table
                    :resize="true"
                    :height="'100%'"
                    :columns="rowColumns"
                    :data-source="rowTableData"
                    emptyDescription="双击表名后查看"
                    :default-toolbar="true"
                >
                    <template v-slot:toolbar>
                        <lay-button type="primary" size="xs" @click="openPackage">生成</lay-button>
                    </template>

                    <template v-slot:isNullable="{row}">
                        <dict-tag :options="dictValue.field_nullable" :values="row.isNullable"></dict-tag>
                    </template>
                    <template v-slot:columnKey="{row}">
                        <dict-tag :options="dictValue.filed_key" :values="row.columnKey"></dict-tag>
                    </template>
                </lay-table>
            </lay-col>
        </lay-row>
        <lay-layer v-model="packageNameLayer" :btn="action">
            <div class="view-container">
                <lay-form label-width="40px">
                    <lay-form-item label="包名">
                        <lay-input v-model="genCodeParam.packageName" placeholder="请输入包名"/>
                    </lay-form-item>
                </lay-form>
            </div>
        </lay-layer>
        <lay-layer v-model="codeLayer" :area="['80vw', '80vh']">
            <lay-row :space="15" style="height: calc(80vh - 57px);margin: 0">
                <lay-col :md="6" style="margin: 0;height: 100%;padding: 0">
                    <div style="height: 30px;padding: 8px 15px">
                        <lay-button type="primary" size="xs" @click="downloadCode">下载</lay-button>
                    </div>
                    <div style="height: calc(100% - 30px)">
                        <lay-scroll height="100%" style="background-color: #fff" thumbColor="#c9c9c9">
                            <div class="file-list">
                                <ul>
                                    <li v-for="(item,index) in fileNameList" :key="index"
                                        :class="{ 'file-name': currentFile!== item, 'file-name-select': currentFile === item }"
                                        @click="changeFile(item)">
                                        {{ item }}
                                    </li>
                                </ul>
                            </div>
                        </lay-scroll>
                    </div>
                </lay-col>
                <lay-col :md="18" style="margin: 0;height: 100%;padding: 0">
                    <code-preview :value="codeValue" :language="currentFile==='mapperXml'?'xml':'java'"
                                  theme="vs-dark"></code-preview>
                </lay-col>
            </lay-row>
        </lay-layer>
    </div>
</template>
<script>
import {onMounted} from "vue";
import { genDomain, getTableColumns, getTableName} from "@/api/gen.js";
import CodePreview from "@/component/CodePreview.vue";
import {layer} from "@layui/layui-vue";
import DownloadUtil from "@/util/downloadUtil.js";
import {getDict} from "@/util/dict.js";
import DictTag from "@/component/DictTag.vue";

export default {
    dicts: ["field_nullable","filed_key"],
    components: {DictTag, CodePreview},
    setup() {
        const proxy = getCurrentInstance();
        const dictValue = ref({})
        const columns = [
            {title: "表名", key: "tableName", align: 'center'},
            {title: "注释", key: "tableComment", align: 'center'},
        ]
        const tableData = ref([])

        function getTableList() {
            getTableName().then(res => {
                tableData.value = res.data
            })
        }

        const rowColumns = [
            {title: "表名", key: "tableName", align: 'center'},
            {title: "列名", key: "columnName", align: 'center'},
            {title: "注释", key: "columnComment", align: 'center'},
            {title: "可空", key: "isNullable", align: 'center',customSlot:'isNullable'},
            {title: "默认值", key: "columnDefault", align: 'center'},
            {title: "列类型", key: "columnType", align: 'center'},
            {title: "键", key: "columnKey", align: 'center',customSlot: 'columnKey'},
        ]

        const rowTableData = ref([])

        const currentTable = ref("");

        const currentFile = ref("")

        function doubleClick(row) {
            currentTable.value = row.tableName
            let param = {
                tableName: row.tableName
            }
            getTableColumns(param).then(res => {
                rowTableData.value = res.data
            })
        }

        const genCodeParam = ref({
            tableName: '',
            packageName: ''
        })

        const codeValue = ref("hello world")

        const packageNameLayer = ref(false)

        function openPackage() {
            if (currentTable.value) {
                packageNameLayer.value = true
                genCodeParam.value.tableName = currentTable.value
            } else {
                layer.msg("请选择表后生成", {icon: 2, time: 1000})
            }
        }

        const action = ref([
            {
                text: "确认",
                callback: () => {
                    if (genCodeParam.value.packageName) {
                        genCode()
                        packageNameLayer.value = false
                    } else {
                        layer.msg("请输入包名", {icon: 2, time: 1000})
                    }
                }
            },
            {
                text: "取消",
                callback: () => {
                    packageNameLayer.value = false
                }
            }
        ])
        const genCodeResult = ref({})
        const codeLayer = ref(false)

        const fileNameList = ref([])

        function genCode() {
            genDomain(genCodeParam.value).then(res => {
                genCodeResult.value = res.data
                fileNameList.value = Object.keys(genCodeResult.value);
                codeLayer.value = true
                currentFile.value = fileNameList.value[0]
                codeValue.value = genCodeResult.value[currentFile.value]
            });
        }

        function changeFile(item) {
            currentFile.value = item
            codeValue.value = genCodeResult.value[item]
        }

        function downloadCode(){
            DownloadUtil.download("/gen/downloadCode",'code.zip',genCodeParam.value)
        }

        onMounted(() => {
            getTableList()
            dictValue.value = getDict(proxy)

        })

        return {
            columns,
            tableData,
            doubleClick,
            rowColumns,
            rowTableData,
            genCode,
            genCodeParam,
            packageNameLayer,
            openPackage,
            action,
            codeLayer,
            genCodeResult,
            fileNameList,
            currentFile,
            changeFile,
            codeValue,
            downloadCode,
            dictValue
        }
    }
}
</script>
<style>
.file-list {
    padding: 15px;
}

.file-name {
    padding: 5px;
    cursor: pointer;
}

.file-name:hover {
    background-color: #eeeeee;
}

.file-name-select {
    padding: 5px;
    cursor: pointer;
    background-color: rgba(22, 183, 119, 0.2);
}
</style>