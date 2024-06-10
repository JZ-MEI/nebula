import { createApp,ref } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'
import Layui from '@layui/layui-vue'
import '@layui/layui-vue/lib/index.css'

import {initDict} from "@/util/dict.js";

const app = createApp(App)
app.use(router)
app.use(Layui)
app.use(initDict)

app.config.globalProperties.$ref = ref;
app.mount('#app')
