<template>
    <div class="view-container">
        <lay-row :space="10">
            <lay-col :md="5">
                <lay-scroll height="calc(100vh - 100px)" thumbColor="#c9c9c9">
                    <lay-row>
                        <lay-col :md="24">
                            <span class="component-group-title">输入型组件</span>
                        </lay-col>
                        <draggable
                            v-model="inputComponents"
                            :sort="false"
                            :clone="clone"
                            itemKey="id"
                            group="group"
                        >
                            <template v-slot:item="{ element, index }">
                                <lay-col :md="12">
                                    <div class="component-element">{{ element.label }}</div>
                                </lay-col>
                            </template>
                        </draggable>
                    </lay-row>
                    <lay-row>
                        <lay-col :md="24">
                            <span class="component-group-title">选择型组件</span>
                        </lay-col>
                        <draggable
                            v-model="selectComponents"
                            :sort="false"
                            :clone="clone"
                            itemKey="id"
                            group="group"
                        >
                            <template #item="{ element, index }">
                                <lay-col :md="12">
                                    <div class="component-element">{{ element.label }}</div>
                                </lay-col>
                            </template>
                        </draggable>
                    </lay-row>
                    <lay-row>
                        <lay-col :md="24">
                            <span class="component-group-title">操作型组件</span>
                        </lay-col>
                        <draggable
                            v-model="layoutComponents"
                            :sort="false"
                            :clone="clone"
                            itemKey="id"
                            group="group"
                        >
                            <template #item="{ element, index }">
                                <lay-col :md="12">
                                    <div class="component-element">{{ element.label }}</div>
                                </lay-col>
                            </template>
                        </draggable>
                    </lay-row>
                    <!--                </div>-->
                </lay-scroll>
            </lay-col>
            <lay-col :md="14">
                <div class="current-canvas">
                    <div class="canvas-header">
                        <div class="canvas-title">当前画布</div>
                        <div class="canvas-operation">
                            <span class="canvas-operation-item" @click="exportForm">导出</span>
                            <span class="canvas-operation-item" @click="viewCode">查看代码</span>
                        </div>
                        <div style="clear: both"></div>
                    </div>
                    <lay-scroll height="calc(100vh - 158px)" thumbColor="#c9c9c9">
                        <div class="canvas-container">
                            <lay-form :pane="formAttr.pane">
                                <draggable v-model="drawList" itemKey="id" group="group">
                                    <template v-slot:item="{ element, index }">
                                        <draggable-item @change-attr="changeAttr" :key="index" :component="element"
                                                        :attrs="itemAttr"
                                                        style="margin-bottom: 0" :index="index" :size="formAttr.size"
                                                        :label-width="formAttr.labelWidth"
                                                        :label-position="formAttr.labelPosition"
                                                        @delete-item="deleteComponent"/>
                                    </template>
                                </draggable>
                            </lay-form>
                        </div>
                    </lay-scroll>
                </div>
            </lay-col>
            <lay-col :md="5">
                <lay-tab type="brief" v-model="currentTab">
                    <lay-tab-item title="组件属性" id="1">
                        <lay-scroll height="calc(100vh - 180px)" thumbColor="#c9c9c9">
                            <lay-form v-if="itemAttr" :model="itemAttr" class="form-attr" label-width="70px">
                                <div v-for="(value, key) in itemAttr" :key="key">
                                    <lay-form-item :label="getLabel(key)"
                                                   v-if="key !=='id'&&key !=='password'&&key!=='options'">
                                        <component :is="getComponentType(key,value)" v-model="itemAttr[key]"/>
                                    </lay-form-item>
                                    <lay-form-item :label="getLabel(key)" v-else-if="key==='options'">
                                        <div v-for="(item, index) in value" :key="index">
                                            <lay-row :space="15">
                                                <lay-col :md="10">
                                                    <lay-input v-model="item.label"></lay-input>
                                                </lay-col>
                                                <lay-col :md="10">
                                                    <lay-input v-model="item.value"></lay-input>
                                                </lay-col>
                                                <lay-col :md="4">
                                                    <div style="line-height: 36px">
                                                        <lay-icon type="layui-icon-reduce-circle" color="#FF5722"
                                                                  style="cursor:pointer;"
                                                                  @click="deleteOption(index)"></lay-icon>
                                                    </div>
                                                </lay-col>
                                            </lay-row>
                                        </div>
                                        <lay-icon type="layui-icon-add-circle" color="#16b777"
                                                  style="cursor:pointer;" @click="addOption"></lay-icon>
                                    </lay-form-item>
                                </div>
                            </lay-form>
                        </lay-scroll>
                    </lay-tab-item>
                    <lay-tab-item title="表单属性" id="2">
                        <lay-form :model="formAttr" class="form-attr">
                            <lay-form-item label="表单名">
                                <lay-input v-model="formAttr.model"/>
                            </lay-form-item>
                            <lay-form-item label="是否必填">
                                <lay-switch v-model="formAttr.required"></lay-switch>
                            </lay-form-item>
                            <lay-form-item label="尺寸">
                                <lay-select v-model="formAttr.size" placeholder="请选择">
                                    <lay-select-option value="xs" label="超小"></lay-select-option>
                                    <lay-select-option value="sm" label="小"></lay-select-option>
                                    <lay-select-option value="md" label="中"></lay-select-option>
                                    <lay-select-option value="lg" label="大"></lay-select-option>
                                </lay-select>
                            </lay-form-item>
                            <lay-form-item label="方框风格">
                                <lay-switch v-model="formAttr.pane"></lay-switch>
                            </lay-form-item>
                            <lay-form-item label="标签位置">
                                <lay-select v-model="formAttr.labelPosition" placeholder="请选择">
                                    <lay-select-option value="top" label="上"></lay-select-option>
                                    <lay-select-option value="left" label="左"></lay-select-option>
                                    <lay-select-option value="right" label="右"></lay-select-option>
                                </lay-select>
                            </lay-form-item>
                            <lay-form-item label="标签宽度">
                                <lay-input v-model="formAttr.labelWidth">
                                    <template #append="{ disabled }">px</template>
                                </lay-input>
                            </lay-form-item>
                        </lay-form>
                    </lay-tab-item>
                </lay-tab>
            </lay-col>
        </lay-row>
        <lay-layer v-model="showCodePreview" type="drawer" area="1000px" title="代码预览">
            <div style="height: calc(100vh - 55px);width: 100%">
                <code-preview :value="codeContent" language="html" :theme="codeTheme"></code-preview>
            </div>
        </lay-layer>
    </div>
</template>

<script>
import draggable from 'vuedraggable'
import {inputComponents, selectComponents, layoutComponents} from '@/util/generator/components.js'
import {labelMapping} from "@/util/generator/labelDict.js";
import {ref, watch} from 'vue'
import DraggableItem from "@/component/DraggableItem.vue";
import CodePreview from "@/component/CodePreview.vue";

export default {
    components: {CodePreview, draggable, DraggableItem},
    setup() {
        const currentTab = ref("1")
        const drawList = ref([{
            label: '单行文本',
            tag: 'lay-input',
            placeholder: '请输入',
            disabled: false,
            modelValue: 'inputVal',
            'allow-clear': false,
            "prefix-icon": '',
            "suffix-icon": '',
            maxlength: null,
        }])
        const numValue = ref(0)

        const clone = (original) => {
            return {...original, id: Date.now()}  // 为克隆的对象添加唯一id
        }
        const formData = ref({})

        const currentItem = ref(0)

        const formAttr = ref({
            model: 'formData',
            required: false,
            size: 'md',
            pane: false,
            labelPosition: 'right',
            labelWidth: '95'
        })

        const itemAttr = ref({})


        function changeAttr(index) {
            currentItem.value = index;
            itemAttr.value = {...drawList.value[index]}
        }

        const getComponentType = (key, value) => {
            if (typeof value === 'boolean') {
                return 'lay-switch';
            } else if (typeof value === 'string') {
                return 'lay-input';
            }
            // 你可以根据需要扩展更多类型
            return 'lay-input';
        }

        function getLabel(key) {
            return labelMapping[key] || key;
        }

        watch(itemAttr, (newVal) => {
            drawList.value[currentItem.value] = {...newVal}
        }, {deep: true})

        function exportForm() {
            let formHtml = generateFormHtml(drawList.value, formAttr.value);
            downloadHtml(formHtml, "form.vue");
        }

        const codeContent = ref("")

        function viewCode() {
            codeContent.value = getCodeContent()
            showCodePreview.value = true
        }

        function getCodeContent() {
            return generateFormHtml(drawList.value, formAttr.value);
        }

        // ${generateProps(formAttributes)}
        function generateFormHtml(components, formAttributes) {
            let formContent = components.map(component => {
                const {label, tag, ...props} = component;
                return `
            <lay-form-item label="${label}" size="${formAttributes.size}" labelPosition="${formAttributes.labelPosition}" labelWidth="${formAttributes.labelWidth}px">
                <${tag} ${generateProps(props)}></${tag}>
            </lay-form-item>`;
            }).join("");
            const formWrapper = `<lay-form model="${formAttributes.model}" :pane="${formAttributes.pane}">
            ${formContent}
        </lay-form>`;
            const model = components.map(component => {
                if (component['modelValue']) {
                    return `${component["modelValue"]}:null`
                } else if (component['v-model']) {
                    return `${component["v-model"]}:null`
                }
            })
            const pageContent = `
<template>
    <div class="view-container">
        ${formWrapper}
    </div>
</template>
<script>
export default {
    setup() {
        const ${formAttributes.model} = ref({
            ${model}
        })
    }
}
&lt;/script>`;
            return pageContent.replace('&lt;', '<')
        }

        function generateProps(props) {
            return Object.keys(props).map(key => {
                if (key === 'id') {
                    return
                }
                if (key === 'options') {
                    return `${key}="${JSON.stringify(props[key])}"`
                }
                if (typeof props[key] === 'boolean') {
                    return props[key] ? key : '';
                }
                return `${key}="${props[key]}"`;
            }).join(" ");
        }

        function downloadHtml(content, filename) {
            const blob = new Blob([content], {type: "text/html"});
            const url = URL.createObjectURL(blob);
            const a = document.createElement("a");
            a.href = url;
            a.download = filename;
            a.click();
            URL.revokeObjectURL(url);
        }

        const showCodePreview = ref(false)

        const codeTheme = ref('vs-dark')

        function deleteComponent(index) {
            drawList.value.splice(index, 1);
        }

        function deleteOption(index) {
            this.itemAttr.options.splice(index, 1);
        }

        function addOption() {
            itemAttr.value.options.push({label: '', value: ''})
        }

        return {
            drawList,
            currentTab,
            inputComponents,
            selectComponents,
            layoutComponents,
            clone,
            formAttr,
            currentItem,
            changeAttr,
            itemAttr,
            getComponentType,
            getLabel,
            numValue,
            exportForm,
            showCodePreview,
            viewCode,
            codeContent,
            codeTheme,
            deleteComponent,
            deleteOption,
            addOption
        }
    }
}
</script>

<style>

.component-element {
    border: 1px dashed rgba(22, 183, 119, 0.1);
    margin: 3px;
    padding: 10px 5px;
    background-color: rgba(22, 183, 119, 0.1);
    text-align: center;
    cursor: pointer;
}

.component-group-title {
    font-size: large;
    font-weight: bold;
}

.current-canvas {
    border: 1px dashed #c9c9c9;
    height: 100%;
}

.canvas-title {
    padding: 10px;
    width: 30%;
    float: left;
}

.canvas-container {
    padding: 10px;
}

.canvas-operation {
    text-align: right;
    width: 50%;
    padding: 10px;
    float: right;
}

.canvas-header {
    width: 100%;
    border-bottom: 1px solid #c9c9c9;
    margin-bottom: 10px;
}

.canvas-operation-item {
    cursor: pointer;
    color: #16b777;
    padding: 0 10px;
    text-decoration: underline;
}
</style>