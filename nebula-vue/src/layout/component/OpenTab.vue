<template>
    <lay-tab type="card" v-model="currentTab" :allow-close="allowClose" @change="change" @close="close"
             :beforeClose="beforeClose" class="openTab">
        <lay-tab-item v-for="tab in openTab" :title="tab.menuName" :id="tab.id" :closable="tab.closable">
            <div style="overflow-y: auto;height: 90%">
                <router-view/>
            </div>
        </lay-tab-item>
    </lay-tab>
</template>

<script>
import {useRouter} from "vue-router";
import {onMounted} from "vue";
import {getPageBreadcrumb} from "@/api/sysMenu.js";

export default {
    name: "OpenTab",
    props: {
        openTab: Object,
        currentTabProp: String
    },
    emits: ["close-tab", "breadcrumb", "before-close"],
    setup(props, {emit}) {

        const allowClose = ref(true)
        const router = useRouter()
        const currentTab = ref(props.currentTabProp)

        function change(id) {
            let filter = props.openTab.filter(item=>item.id === props.currentTabProp);
            let indexOf = props.openTab.indexOf(filter[0]);
            router.push(props.openTab[indexOf-1].routerPath)
        }

        function close(id) {
            emit("close-tab", id)
        }

        function beforeClose(id) {
            emit("before-close", id)
        }

        watch(() => props.currentTabProp, (newValue) => {
            currentTab.value = newValue;
        });


        return {
            allowClose,
            change,
            close,
            currentTab,
            beforeClose,
        }
    }
}
</script>
<style>
.openTab {
    display: block;
    height: 100%;
    margin:0 !important;
}
</style>