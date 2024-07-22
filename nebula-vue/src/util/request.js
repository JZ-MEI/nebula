// request.js
import axios from 'axios';
import {layer} from "@layui/layui-vue";

const instance = axios.create({
    // baseURL: 'http://localhost:8080', // 你的后端服务地址
    timeout: 10000, // 请求超时时间
});

// 添加请求拦截器
instance.interceptors.request.use(
    (config) => {
        // 在发送请求之前做些什么，例如添加 token 到 headers
        // 对于 POST, PUT, PATCH 请求，设置 Content-Type 为 application/json
        if (['post', 'put', 'patch'].includes(config.method.toLowerCase())) {
            config.headers['Content-Type'] = 'application/json';
        }
        return config;
    },
    (error) => {
        // 对请求错误做些什么
        return Promise.reject(error);
    }
);

// 添加响应拦截器
// ...（保持不变）

// 封装通用请求方法
export async function request({ url, method = 'get', data = null, params = null }) {
    try {
        const options = {};
        url = import.meta.env.VITE_APP_BASE_API+`${url}`

        // 只有当 data 存在时才添加到 options 中
        if (data) {
            options.data = data;
        }

        // 当 method 不是 post/put/patch 时，并且 params 存在，才添加到 options 中
        if (params && !['post', 'put', 'patch'].includes(method.toLowerCase())) {
            options.params = params;
        }

        const response = await instance({
            url,
            method,
            ...options
        });
        if(response.data.code !== 200){
            layer.notifiy({
                title: "操作失败",
                content: response.data.msg,
                icon: 2
            })
        }
        return response.data; // 直接返回 response.data 而不是整个 response 对象
    } catch (error) {
        console.error(error);
        throw error;
    }
}