<template>
    <div class="view-container">
        <lay-form :labelWidth="90">
            <lay-form-item label="关灯">
                <lay-switch v-model="theme" onswitch-value="dark" unswitch-value="light">
                    <template #onswitch-icon>
                        <lay-icon type="layui-icon-moon"/>
                    </template>
                    <template #unswitch-icon>
                        <lay-icon type="layui-icon-light"/>
                    </template>
                </lay-switch>
            </lay-form-item>
            <lay-form-item label="菜单颜色">
                <lay-radio-button v-model="menuStyle" value="light" label="浅色"
                                   size="sm"></lay-radio-button>
                <lay-radio-button v-model="menuStyle" value="dark" label="深色"
                                  size="sm"></lay-radio-button>
            </lay-form-item>
        </lay-form>
    </div>
    <lay-config-provider :theme="theme">
    </lay-config-provider>
</template>
<script>
import {watch} from "vue";

export default {
    name: "Drawer",
    emits: ["menu-style"],
    setup(props, {emit}) {
        const theme = ref('light')
        const menuStyle = ref('dark')


        watch(()=>menuStyle.value,(newStyle)=>{
            emit('menu-style',menuStyle.value)
        })

        return {
            theme,
            menuStyle
        }
    }
}

</script>