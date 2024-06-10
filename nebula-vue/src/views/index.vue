<template>
    <div class="view-container">
        test
    </div>
</template>

<script>
import { ref } from 'vue'
import {useRouter} from "vue-router";
import Layout from "@/layout/index.vue";
import router from "@/router/index.js";

export default {
    setup() {

        const openKeys2 = ref(["7"])
        const selectedKey = ref("5")
        const changeSelectedKey = (val) => {
            selectedKey.value = val;
        }

        const changeOpenKeys = (val) => {
            openKeys2.value = val;
        }
        const router = useRouter()



        return {
            openKeys2,
            selectedKey,
            changeOpenKeys,
            changeSelectedKey
        }
    }
}
</script>