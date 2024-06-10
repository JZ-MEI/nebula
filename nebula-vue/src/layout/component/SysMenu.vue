<template>
    <lay-menu :selected-key="selectedKey" @change-selected-Key="changeSelectedKey" @change-open-keys="changeOpenKeys"
              :openKeys="openKeys" :tree="true" class="sideMenu" :collapse="collapse" :theme="menuStyle.style">
        <menu-item :sys-menu="menuData" @current-tab="openTab" @breadcrumb="getBreadCrumb"/>
    </lay-menu>
</template>
<script>

import MenuItem from "@/layout/component/MenuItem.vue";
import {useRoute, useRouter} from "vue-router";
import {getPageBreadcrumb} from "@/api/sysMenu.js";
import {onMounted} from "vue";

export default {
    name: "SysMenu",
    props: {
        menuData: Object,
        collapse:Boolean,
        menuStyle:Object
    },
    components:{
        MenuItem
    },
    emits:["current-tab","breadcrumb"],
    setup(props,{emit}) {
        const openKeys = ref()
        const selectedKey = ref(0)

        const changeSelectedKey = (val) => {

            selectedKey.value = val;
        }

        const changeOpenKeys = (val) => {
            openKeys.value = val;
        }

        const route = useRoute()
        function openTab(currentItem){
            emit("current-tab",currentItem)
        }

        function getBreadCrumb(breadcrumb){
            emit("breadcrumb",breadcrumb)
        }


        return {
            openKeys,
            selectedKey,
            changeOpenKeys,
            changeSelectedKey,
            openTab,
            getBreadCrumb
        }
    }
}
</script>
<style>
.sideMenu{
    height: calc(100vh - 45px);
    border-radius: unset !important;
}

.layui-nav-tree{
    padding-top: 0 !important;
}
</style>