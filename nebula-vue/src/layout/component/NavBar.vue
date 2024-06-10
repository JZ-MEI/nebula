<template>
    <div class="navBar">
        <lay-icon type="layui-icon-spread-left" v-if="isOpenMenu" class="menuControl" size="20px"
                  @click="menuControl"></lay-icon>
        <lay-icon type="layui-icon-shrink-right" v-else class="menuControl" size="20px" @click="menuControl"></lay-icon>
        <lay-icon type="layui-icon-more-vertical" size="24px" style="float: right;" @click="openDraw"></lay-icon>
        <lay-dropdown trigger="hover" updateAtScroll placement="bottom" style="line-height: 45px">
            <lay-avatar radius class="avatar" size="lg" :src="avatar"></lay-avatar>
            <template #content>
                <lay-dropdown-menu>
                    <lay-dropdown-menu-item @click="toPersonalHub">
                        <template #prefix>
                            <lay-icon type="layui-icon-username"></lay-icon>
                        </template>
                        <template #default>个人中心</template>
                    </lay-dropdown-menu-item>
                    <lay-dropdown-menu-item @click="toModifyPwd">
                        <template #prefix>
                            <lay-icon type="layui-icon-password"></lay-icon>
                        </template>
                        <template #default>修改密码</template>
                    </lay-dropdown-menu-item>
                    <lay-line></lay-line>
                    <lay-dropdown-menu-item @click="userLogout">
                        <template #prefix>
                            <lay-icon type="layui-icon-logout"></lay-icon>
                        </template>
                        <template #default>退出登陆</template>
                    </lay-dropdown-menu-item>
                </lay-dropdown-menu>
            </template>
        </lay-dropdown>
        <lay-layer v-model="showDraw" type="drawer" title="主题设置">
            <div class="view-container">
                <lay-form :labelWidth="90">
                    <lay-form-item label="关灯">
                        <lay-switch v-model="theme" onswitch-value="dark" unswitch-value="light">
                            <template #onswitch-icon>
                                <lay-icon type="layui-icon-moon"/>
                            </template>
                            <template #unswitch-icon>
                                <lay-icon type="layui-icon-light"/>
                            </template>
                        </lay-switch>
                    </lay-form-item>
                    <lay-form-item label="菜单颜色">
                        <lay-radio-button v-model="menuStyleScheme" value="light" label="浅色"
                                          size="sm"></lay-radio-button>
                        <lay-radio-button v-model="menuStyleScheme" value="dark" label="深色"
                                          size="sm"></lay-radio-button>
                    </lay-form-item>
                    <lay-form-item label="圆角度">
                        <lay-input-number v-model="borderRadius" size="sm"></lay-input-number>
                    </lay-form-item>
                    <lay-form-item label="主色调">
                        <lay-color-picker v-model="themeVariable['--global-primary-color']"></lay-color-picker>
                    </lay-form-item>
                    <lay-form-item label="次色调">
                        <lay-color-picker v-model="themeVariable['--global-checked-color']"></lay-color-picker>
                    </lay-form-item>
                    <lay-form-item label="通用色">
                        <lay-color-picker v-model="themeVariable['--global-normal-color']"></lay-color-picker>
                    </lay-form-item>
                    <lay-form-item label="警告色">
                        <lay-color-picker v-model="themeVariable['--global-warm-color']"></lay-color-picker>
                    </lay-form-item>
                    <lay-form-item label="危险色">
                        <lay-color-picker v-model="themeVariable['--global-danger-color']"></lay-color-picker>
                    </lay-form-item>
                    <lay-form-item style="width: 70%;margin: 0 auto">
                        <lay-button type="primary" fluid @click="resetScheme">重置主题方案</lay-button>
                    </lay-form-item>
                </lay-form>
                <lay-config-provider :theme="theme" :themeVariable="themeVariable">
                </lay-config-provider>
            </div>
        </lay-layer>

    </div>
</template>
<script>

import {getPersonalInfo} from "@/api/sysUser.js";
import {onMounted, watch} from "vue";
import {logout} from "@/api/login.js";
import {useRouter} from "vue-router";

export default {
    name: "NavBar",
    emits: ['change-menu-show', 'menu-style'],
    props: {
        isOpenMenu: Boolean,
        breadcrumbItem: Object
    },
    setup(props, {emit}) {
        function menuControl() {
            emit('change-menu-show', !props.isOpenMenu);
        }

        const avatar = ref('');

        const showDraw = ref(false)

        function getAvatar() {
            getPersonalInfo().then(res => {
                avatar.value = res.data.avatar;
            })
        }

        function openDraw() {
            showDraw.value = true
        }

        const currentRouter = useRouter();

        function userLogout() {
            logout().then(res => {
                if (res.code === 200) {
                    layer.notifiy({
                        title: "退出成功",
                        icon: 1
                    })
                    currentRouter.push("/")
                }
            })
        }

        function toPersonalHub() {
            currentRouter.push("/personal/info")
        }

        function toModifyPwd() {
            currentRouter.push("/personal/pwd")
        }

        const theme = ref('light')
        const menuStyleScheme = ref('dark')

        const menuStyle = ref({
            style: "dark",
            bgColor: "#393D49",
            titleColor: "#F0F8FF"
        })

        function modifyStyle(currentStyle) {
            if (currentStyle === 'light') {
                menuStyle.value.style = 'light'
                menuStyle.value.bgColor = '#fff'
                menuStyle.value.titleColor = '#393D49'
            } else {
                menuStyle.value.style = 'dark'
                menuStyle.value.bgColor = '#393d49'
                menuStyle.value.titleColor = '#F0F8FF'
            }
            emit('menu-style', menuStyle.value)
        }

        const borderRadius = ref(2)

        const themeVariable = ref({
            "--global-border-radius": "2px",
            "--global-primary-color": "#16BAAA",
            "--global-checked-color": "#5fb878",
            "--global-normal-color": "#1e9fff",
            "--global-warm-color": "#ffb800",
            "--global-danger-color": "#ff5722"
        })

        function resetScheme(){
            borderRadius.value = 2
            themeVariable.value = {
                "--global-border-radius": "2px",
                "--global-primary-color": "#16BAAA",
                "--global-checked-color": "#5fb878",
                "--global-normal-color": "#1e9fff",
                "--global-warm-color": "#ffb800",
                "--global-danger-color": "#ff5722"
            }
        }

        onMounted(() => {
            getAvatar()
            emit('menu-style', menuStyle.value)
        })

        watch(() => menuStyleScheme.value, (newValue) => {
            modifyStyle(newValue)
        })

        watch(() => borderRadius.value, (newValue) => {
            themeVariable.value['--global-border-radius'] = newValue + 'px'
        })


        return {
            avatar,
            showDraw,
            menuControl,
            openDraw,
            userLogout,
            toPersonalHub,
            toModifyPwd,
            modifyStyle,
            menuStyle,
            theme,
            menuStyleScheme,
            themeVariable,
            borderRadius,
            resetScheme
        }
    }

}

</script>
<style>
.navBar {
    height: 45px;
    padding-left: 30px;
    padding-right: 20px;
    line-height: 45px;
}

.menuControl {
    margin-right: 20px;
}

.avatar {
    float: right;
    height: 35px !important;
    width: 35px !important;
    margin-top: 5px;
    margin-right: 15px;
}


</style>