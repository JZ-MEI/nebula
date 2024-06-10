<template>
    <template v-for="item in sysMenu" :key="item.id">
        <lay-menu-item v-if="!item.sysMenuList" :id="item.id">
            <template #icon>
                <lay-icon :type="item.menuIcon"></lay-icon>
            </template>
            <template #title>
                <span @click="openItem(item)">
                {{ item.menuName }}
                </span>
            </template>
        </lay-menu-item>
        <lay-sub-menu :id="item.id" v-else>
            <template #icon>
                <lay-icon :type="item.menuIcon"></lay-icon>
            </template>
            <template #title>
                {{ item.menuName }}
            </template>
            <menu-item :sys-menu="item.sysMenuList" @current-tab="openTab"/>
        </lay-sub-menu>
    </template>
</template>
<script>
import {useRouter} from "vue-router";
import {getPageBreadcrumb} from "@/api/sysMenu.js";

export default {
    name: "MenuItem",
    props: {
        sysMenu: Object
    },
    emits: ['current-tab',"breadcrumb"],
    setup(props, {emit}) {
        const router = useRouter()

        function openItem(item) {
            emit("current-tab", item)
            router.push(item.routerPath)
            if (item.routerPath === "/index"){
                emit("breadcrumb",[{menuName:'主页',routerPath:'/index'}])
            }else {
                let param = {routerPath: item.routerPath}
                getPageBreadcrumb(param).then(res => {
                    emit("breadcrumb", res.data)
                })
            }
        }
        function openTab(item){
            emit("current-tab",item)
        }

        return {
            openItem,
            openTab
        }
    }
}
</script>