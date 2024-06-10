<template>
    <div class="login-card">
        <div class="login-img"></div>
        <div class="login-form-div">
            <span class="login-title">星云管理系统</span>
            <div class="login-form">
                <lay-form :model="userInfo">
                    <lay-form-item prop="username">
                        <lay-input prefix-icon="layui-icon-username" v-model="userInfo.username"></lay-input>
                    </lay-form-item>
                    <lay-form-item prop="password">
                        <lay-input prefix-icon="layui-icon-password" v-model="userInfo.password" password
                                   type="password"></lay-input>
                    </lay-form-item>
                    <lay-form-item style="text-align: left">
                        <lay-checkbox name="like" v-model="userInfo.rememberMe"
                                      label="记住我" class="rememberMe"></lay-checkbox>
                    </lay-form-item>
                    <lay-form-item>
                        <lay-button type="normal" @click="userLogin" fluid>登录</lay-button>
                    </lay-form-item>
                </lay-form>
            </div>
        </div>
    </div>
</template>

<script>
import {onMounted, ref} from 'vue'
import {login} from "@/api/login.js";
import {useRouter} from "vue-router";
import {getRouterData} from "@/api/sysMenu.js";
import Layout from "@/layout/index.vue";
import {getLoginState} from "@/api/login.js";

export default {
    setup() {
        const userInfo = ref({
            username: '',
            password: '',
            rememberMe:false
        })

        const router = useRouter();
        const loadingId = ref();

        function userLogin() {
            loadingId.value = layer.load(0)
            login(userInfo.value).then((res) => {
                if (res.code === 200) {
                    getRouters()
                } else {
                    notify({
                        title: "登录失败",
                        content: res.msg,
                        icon: 2
                    })
                }
            });
        }

        function getRouters() {
            getRouterData().then(res => {
                let resData = res.data
                resData.forEach(item => {
                    router.addRoute({
                        path: '/',
                        component: Layout,
                        hidden: true,
                        children: [
                            {
                                path: item.routerPath,
                                component: () => import(/* @vite-ignore */ `../views${item.compPath}`)
                            }
                        ]
                    })
                });
                sessionStorage.setItem('routes', JSON.stringify(resData));
                layer.close(loadingId.value)
                router.push('/index')
            })
        }

        function skipLogin(){
            getLoginState().then(res=>{
                if (res.data){
                    router.push('/index')
                }
            })
        }
        onMounted(()=>{
            skipLogin()
        })

        return {
            userInfo,
            userLogin,
        }
    },
}
</script>

<style>
.login-card {
    margin: 20vh auto;
    width: 50vw; /* 可以根据需要进行调整 */
    height: 60vh;
    border-radius: 30px;
    overflow: hidden; /* 确保内容不会溢出 */
    display: flex;
    box-shadow: 0 0 90px #8c8b8b;
}

.login-img {
    height: 60vh;
    float: left;
    width: 35%;
    background-image: url("../assets/img/nebula.jpg");
    background-size: cover
}

.login-form-div {
    text-align: center;
    width: 65%;
    padding-top: 50px;
}

.login-title {
    margin-top: 30px;
    font-size: x-large;
}

.login-form {
    width: 60%;
    margin: 70px auto;
}

.layui-form-checked span, .layui-form-checked:hover span {
    background-color: #1e9fff;
}

.layui-form-checked i, .layui-form-checked:hover i {
    color: #1e9fff;
}
</style>