import {defineConfig,loadEnv} from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from "unplugin-auto-import/vite";
import {LayuiVueResolver} from "unplugin-vue-components/dist/resolvers.js";
import Components from "unplugin-vue-components/dist/vite.js";
import path from "path";


const getEnv = (mode, target) => {
    return loadEnv(mode, process.cwd())[target]
}

// https://vitejs.dev/config/
export default ({mode})=>
    defineConfig({
    plugins: [vue(),
        // htmlPlugin,
        AutoImport({
            imports: ["vue", "vue-router"],
            resolvers: [LayuiVueResolver()],

        }),
        Components({
            resolvers: [LayuiVueResolver()],
        }),
    ],
    server: {
        host: '0.0.0.0',
        port: getEnv(mode,'VITE_PORT'),
        open: true,
        proxy: {
            // 选项写法
            [getEnv(mode,"VITE_APP_BASE_API")]: {
                target: 'http://localhost:8080',
                changeOrigin: true,
                rewrite:(path)=> path.replace( [getEnv(mode,"VITE_APP_BASE_API")],'')
            },
            // 或者使用字符串简写
            // '/api': 'http://localhost:8080'
        }
    },
    resolve: {
        alias: {
            '@': path.resolve(__dirname, 'src')
        }
    },

})
