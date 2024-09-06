<template>
    <div class="noScroll">
        <div class="menuDiv">
            <top-logo :router-path="logoRouterPath" :collapse="collapse" :bg-color="menuStyle.bgColor"
                      :title-color="menuStyle.titleColor"/>
            <sys-menu :menu-data="menuData" :collapse="collapse" @current-tab="pushCurrentTab" :menu-style="menuStyle"/>
        </div>
        <div class="container">
            <nav-bar @change-menu-show="changeMenuShow" :is-open-menu="collapse" :breadcrumb-item="breadcrumbData"
                     @menu-style="modifyStyle"/>
            <div style="height: 100%">
                <open-tab :open-tab="openTab" :current-tab-prop="currentOpenTab" @close-tab="closeTab"
                          @before-close="beforeClose"/>
            </div>
        </div>
    </div>
</template>
<script>
import SysMenu from "@/layout/component/SysMenu.vue";
import NavBar from "@/layout/component/NavBar.vue";
import OpenTab from "@/layout/component/OpenTab.vue";
import TopLogo from "@/layout/component/TopLogo.vue";
import {ref, onMounted, watch} from 'vue';
import {getLoginState} from "@/api/login.js";
import {getMenuInfo, getMenuList, getPageBreadcrumb} from "@/api/sysMenu.js";
import {useRoute, useRouter} from "vue-router";
import Cookies from "js-cookie";

export default {
    components: {
        SysMenu,
        NavBar,
        OpenTab,
        TopLogo
    },
    setup() {
        const collapse = ref(false);
        const menuData = ref([{
            menuTitle: "首页",
            id: 0,
            routerPath: "/index",
            menuIcon: "layui-icon-home",
            isThird:0
        }])
        const breadcrumbData = ref()
        const currentOpenTab = ref(0)
        const openTab = ref([
            {
                menuTitle: "首页",
                id: 0,
                routerPath: "/index",
                closable: false
            }
        ])
        const logoRouterPath = "/index"

        function loadMenuList() {
            getMenuList().then(res => {
                res.data.forEach(item => {
                    menuData.value.push(item)
                })
            })
        }

        const router = useRouter();
        const route = useRoute();

        function verifyLoginState() {
            getLoginState().then(res => {
                if (res.data) {
                    loadMenuList()
                    loadBreadcrumb()
                } else {
                    layer.notify({
                      title: "当前登录已过期，请重新登录",
                      icon: 2
                    })
                    Cookies.remove('nebula')
                    router.push('/')
                }
            })
        }

        function changeMenuShow(isShow) {
            collapse.value = isShow;
        }

        function loadBreadcrumb() {
            if (route.path === '/index') {
                breadcrumbData.value = [{menuTitle: '主页', routerPath: '/index'}]
            } else {
                let param = {routerPath: route.path}
                getPageBreadcrumb(param).then(res => {
                    breadcrumbData.value = res.data
                })
            }
        }


        function pushCurrentTab(currentTab) {
            if (openTab.value.find(item => item.id === currentTab.id)) {
                currentOpenTab.value = currentTab.id
            } else {
                const newTab = {id: currentTab.id, menuTitle: currentTab.menuTitle, routerPath: currentTab.routerPath,closable:true}
                openTab.value.push(newTab)
                currentOpenTab.value = currentTab.id
            }
        }

        function initCurrentTab() {
            if (route.path === '/index') {
                return;
            }
            if (route.path !== '/index' || route.path !== '/') {
                let param = {
                    menuTitle: route.meta.menuTitle,
                    routerPath: route.path
                }
                getMenuInfo(param).then(res => {
                    if (res.data) {
                        if (openTab.value.find(item => item.id === res.data.id)) {
                            currentOpenTab.value = res.data.id
                        } else {
                            const newTab = {
                                id: res.data.id,
                                menuTitle: res.data.menuTitle,
                                routerPath: res.data.routerPath,
                                closable:true
                            }
                            openTab.value.push(newTab)
                            currentOpenTab.value = res.data.id
                        }
                    } else {
                        if (openTab.value.find(item => item.id === route.path)) {
                            currentOpenTab.value = route.path
                        } else {
                            const newTab = {id: route.path, menuTitle: route.meta.menuTitle, routerPath: route.path,closable:true}
                            openTab.value.push(newTab)
                            currentOpenTab.value = route.path
                        }
                    }
                })
            }
        }

        function closeTab(id) {
            let filter = openTab.value.filter(tab=>tab.id===id)[0]
            let number = openTab.value.indexOf(filter);
            if (currentOpenTab.value===id) {
                currentOpenTab.value = openTab.value[number - 1].id
                router.push(openTab.value[number - 1].routerPath)
            }
            openTab.value = openTab.value.filter(tab => tab.id !== id);
            // openTab.value.splice(number)
            // openTab.value = openTab.value.filter(tab => tab.id !== id);
        }

        function beforeClose(id) {
            let parse = JSON.parse(JSON.stringify(openTab.value));
            let filter = parse.filter(tab => tab.id === id);
            let number = parse.indexOf(filter[0]);
            currentOpenTab.value = parse[number - 1].id
        }

        const menuStyle = ref({})

        function modifyStyle(style) {
            menuStyle.value = {...style}
        }

        onMounted(() => {
            verifyLoginState()
            initCurrentTab()
        })
        watch(() => route.path, (newValue, oldValue) => {
            initCurrentTab()
        })

        return {
            menuData,
            collapse,
            currentOpenTab,
            changeMenuShow,
            breadcrumbData,
            openTab,
            logoRouterPath,
            pushCurrentTab,
            closeTab,
            beforeClose,
            modifyStyle,
            menuStyle
        }


    },
    created() {
    }

}
</script>
<style>
html {
    overflow: hidden
}

.menuDiv {
    flex-grow: 0;
    flex-basis: auto;
}

.container {
    flex-grow: 1;
    flex-basis: auto;
    height: 100vh;
}

.noScroll {
    display: flex;
}

</style>