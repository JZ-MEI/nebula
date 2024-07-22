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
                        <lay-button type="primary" size="xs" @click="genCode">生成</lay-button>
                    </template>
                </lay-table>
            </lay-col>
        </lay-row>
    </div>
</template>
<script>
import {onMounted} from "vue";
import {genDomain, getTableColumns, getTableName} from "@/api/gen.js";

export default {
    setup() {

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
            {title: "可空", key: "isNullable", align: 'center'},
            {title: "默认值", key: "columnDefault", align: 'center'},
            {title: "列类型", key: "columnType", align: 'center'},
            {title: "键", key: "columnKey", align: 'center'},
        ]

        const rowTableData = ref([])

        const currentTable = ref("");
        function doubleClick(row) {
            currentTable.value = row.tableName
            let param = {
                tableName: row.tableName
            }
            getTableColumns(param).then(res => {
                rowTableData.value = res.data
            })
        }
        function genCode(){
            let param={
                tableName:currentTable.value
            }
            genDomain(param);
        }

        onMounted(() => {
            getTableList()
        })

        return {
            columns,
            tableData,
            doubleClick,
            rowColumns,
            rowTableData,
            genCode
        }
    }
}
</script>