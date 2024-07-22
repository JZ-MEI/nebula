<template>
    <div>
        <div style="border: 1px dashed #16b777;padding:5px 5px;margin-bottom: 5px" @click="changeAttr(index)">
            <lay-row :space="15">
                <lay-col :md="22">
                    <lay-form-item :label="dynamicComponent.label" class="form-item" :size="size"
                                   :label-width="labelWidth+'px'" :label-position="labelPosition">
                        <component :is="dynamicComponent.component" v-bind="dynamicComponent.props" :size="size"
                                   v-if="!mutiComponent.includes(dynamicComponent.component)&&dynamicComponent.component!=='lay-button'"></component>
                        <div v-else-if="mutiComponent.includes(dynamicComponent.component)">
                            <component v-for='item in dynamicComponent.props.options' :is="dynamicComponent.component"
                                       v-model="dynamicComponent.props.modelValue"
                                       :disable="dynamicComponent.props.disabled"
                                       :label="item.label" :value="item.value" :size="size"
                            ></component>
                        </div>
                        <div v-else>
                            <component :is="dynamicComponent.component" v-bind="dynamicComponent.props" :size="size">
                                <span style="color: #fff">{{ dynamicComponent.props.text }}</span>
                            </component>
                        </div>
                    </lay-form-item>
                </lay-col>
                <lay-col :md="2">
                    <div class="delete-btn" @click="deleteItem(index)">
                        <lay-icon type="layui-icon-delete" style="font-size: 20px;"/>
                    </div>
                </lay-col>
            </lay-row>
        </div>
    </div>
</template>

<script>
import {computed, h} from "vue";

export default {
    props: {
        component: Object,
        attrs: Object,
        id: Number,
        index: Number,
        size: String,
        labelWidth: String,
        labelPosition: String
    },
    emits: ['change-attr', 'delete-item'],
    setup(props, {emit}) {
        const dynamicComponent = computed(() => {
            return jsonToVNode(props.component);
        });

        const jsonToVNode = (component) => {
            const {tag, label, ...restProps} = component;
            // 将传递给组件的属性和样式分离出来
            const props = {
                ...restProps,
                style: {
                    ...restProps.style
                }
            };

            return {
                component: tag,
                label,
                props,
            };
        };

        function changeAttr(index) {
            emit('change-attr', index)
        }

        function deleteItem(index) {
            emit('delete-item', index)
        }

        const mutiComponent = [
            'lay-radio',
            'lay-radio-button',
            'lay-checkbox'
        ]

        return {
            dynamicComponent,
            changeAttr,
            mutiComponent,
            deleteItem
        };
    }
};
</script>
<style>
.canvas-item .form-item {
    margin-bottom: 0 !important;
}

.layui-col-space15 {
    margin: 0;
}

.delete-btn {
    background-color: #ff5722;
    border: 1px solid #fff;
    color: #fff;
    width: 30px;
    height: 30px;
    line-height: 30px;
    text-align: center;
    margin-top: 7px;
    cursor: pointer;
    border-radius: 30px;
    transition: background-color 0.3s ease, color 0.3s ease, border 0.3s ease;
}

.delete-btn:hover {
    border-radius: 30px;
    background-color: #ffeded;
    border: 1px solid #ff5722;
    color: #ff5722;
}
</style>