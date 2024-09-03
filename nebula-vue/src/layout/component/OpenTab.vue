<template>
    <!--    <lay-tab type="brief" v-model="currentTab" :allow-close="allowClose" @change="change" @close="close"-->
    <!--             :beforeClose="beforeClose" class="openTab">-->
    <!--        <lay-tab-item v-for="tab in openTab" :title="tab.menuName" :id="tab.id" :closable="tab.closable" :key="tab.id">-->

    <!--        </lay-tab-item>-->
    <div>
        <div class="tag-row">
            <lay-tag class="page-tag" v-for="(tab,index) in openTab" :type="currentTab===tab.id?'primary':''"
                     :closable="tab.closable" @close="close(tab.id)" @click="changeTab(tab)">
                {{ tab.menuTitle }}
            </lay-tag>
        </div>
        <div style="height: 95%;overflow-x: hidden">
            <router-view v-slot="{ Component }">
                <keep-alive v-if="keepAlive">
                    <component :is="Component"></component>
                </keep-alive>
                <component v-else :is="Component"></component>
            </router-view>
        </div>
    </div>
    <!--    </lay-tab>-->
</template>

<script>
import {useRoute, useRouter} from "vue-router";
import {watch} from "vue";
import {getPageBreadcrumb} from "@/api/sysMenu.js";


export default {
    name: "OpenTab",
    props: {
        openTab: Object,
        currentTabProp: [String, Number]
    },
    emits: ["close-tab", "breadcrumb", "before-close"],
    setup(props, {emit}) {

        const allowClose = ref(true)
        const router = useRouter()
        const currentTab = ref(props.currentTabProp)

        function change(id) {
            let filter = props.openTab.filter(item => item.id === props.currentTabProp);
            let indexOf = props.openTab.indexOf(filter[0]);
            router.push(props.openTab[indexOf - 1].routerPath)
        }

        function close(id) {
            emit("close-tab", id)
        }

        function beforeClose(id) {
            emit("before-close", id)
        }

        function changeTab(tab){
            currentTab.value=tab.id
            router.push(tab.routerPath)
        }

        watch(() => props.currentTabProp, (newValue) => {
            currentTab.value = newValue;
        });
        const route = useRoute()

        const keepAlive = route.meta.keepAlive

        return {
            allowClose,
            change,
            close,
            currentTab,
            beforeClose,
            changeTab,
            keepAlive
        }
    }
}
</script>
<style>
.openTab {
    display: block;
    height: 100%;
    margin: 0 !important;
}

.tag-row {
    padding: 0 15px;
    height: 40px;
    line-height: 40px;
    background-color: #f3f3f3;
    cursor: pointer;
}

.page-tag {
    margin-left: 5px;
    margin-right: 5px;
}
</style>