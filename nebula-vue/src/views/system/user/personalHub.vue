<template>
    <div class="view-container">
        <lay-panel shadow="hover">
            <lay-row :space="10" v-if="currentPage==='info'">
                <lay-col :md="6">
                    <div class="avatar-show">
                        <div class="avatar-title">当前头像</div>
                        <lay-avatar :src="userInfo.avatar" size="lg" class="user-avatar" radius></lay-avatar>
                        <br>
                        <!--                        <lay-button type="primary" @click="changeAvatar">更换头像</lay-button>-->
                        <lay-upload @cutdone="getCutDone" acceptMime="images"
                                    :cut="true" :cutOptions="cutOptions" :auto="false" text="更换头像">
                        </lay-upload>
                    </div>
                </lay-col>
                <lay-col :md="18">
                    <div class="form-container">
                        <lay-form :model="userInfo" :pane="true">
                            <lay-form-item label="用户名" prop="username">
                                <lay-input v-model="userInfo.username" disabled></lay-input>
                            </lay-form-item>
                            <lay-form-item label="昵称" prop="nickname">
                                <lay-input v-model="userInfo.nickname"></lay-input>
                            </lay-form-item>
                            <lay-form-item label="邮箱" prop="email">
                                <lay-input v-model="userInfo.userEmail"></lay-input>
                            </lay-form-item>
                            <lay-form-item label="手机号" prop="telephone">
                                <lay-input v-model="userInfo.userTelephone"></lay-input>
                            </lay-form-item>
                            <lay-form-item label="性别" prop="userSex">
                                <lay-select v-model="userInfo.userSex" class="width-control">
                                    <lay-select-option v-for="item in dictValue.default_sex" :value="item.dictKey"
                                                       :label="item.dictValue"></lay-select-option>
                                </lay-select>
                            </lay-form-item>
                            <lay-form-item style="text-align: center">
                                <lay-button type="primary" @click="modifyPersonal">保存修改</lay-button>
                            </lay-form-item>
                        </lay-form>
                    </div>
                </lay-col>
            </lay-row>
            <div v-else-if="currentPage==='pwd'" class="pwd-div view-container">
                <lay-form :model="pwdInfo" :pane="true"  :rules="rules" ref="modifyPwd">
                    <lay-form-item label="当前密码" prop="currentPwd" >
                        <lay-input v-model="pwdInfo.currentPwd" type="password" password></lay-input>
                    </lay-form-item>
                    <lay-form-item label="新密码" prop="newPwd">
                        <lay-input v-model="pwdInfo.newPwd" type="password" password ></lay-input>
                    </lay-form-item>
                    <lay-form-item label="确认密码" prop="confirmPwd">
                        <lay-input v-model="pwdInfo.confirmPwd" type="password" password></lay-input>
                    </lay-form-item>
                    <lay-form-item style="text-align: center">
                        <lay-button type="primary" @click="handleModifyPwd">保存修改</lay-button>
                    </lay-form-item>
                </lay-form>
            </div>
        </lay-panel>
    </div>
</template>
<script>
import {getPersonalInfo, modifyPassword, modifyPersonalInfo} from "@/api/sysUser.js";
import {onMounted} from "vue";
import {getDict} from "@/util/dict.js";
import {useRoute} from "vue-router";
import {layer} from "@layui/layui-vue";

export default {
    dicts: ["default_sex"],
    setup() {
        const userInfo = ref({
            username: "",
            nickname: "",
            userEmail: "",
            userTelephone: "",
            userSex: ""
        })

        const pwdInfo = ref({
            currentPwd: "",
            newPwd: "",
            confirmPwd: ""
        })
        const proxy = getCurrentInstance();
        const dictValue = ref({})

        const route = useRoute()
        const currentPage = ref(route.params.page)
        const cutOptions = {
            copperOption: {
                center: false,
                aspectRatio: 1,
            }
        }
        const rules = ref({
            currentPwd : {
                validator(rule, value, callback){
                    if (!value){
                        callback(new Error("原密码不能为空"))
                    }else{
                        return true
                    }
                }
            },
            newPwd : {
                validator(rule,value,callback){
                    if (!value){
                        callback(new Error("新密码不能为空"))
                    }else{
                        return true
                    }
                }
            },
            confirmPwd : {
                validator(rule,value,callback){
                    if (!value){
                        callback(new Error("确认密码不能为空"))
                    }else if (value !== pwdInfo.value.newPwd){
                        callback(new Error("两次密码不一致"))
                    }else{
                        return true
                    }
                }
            }
        })

        const modifyPwd = ref()

        function getUserInfo() {
            getPersonalInfo().then(res => {
                userInfo.value = res.data
            })
        }



        function getCutDone(result){
            let param = {
                avatar:result.cutResult
            }
            modifyPersonalInfo(param).then(res=>{
                if (res.code===200){
                    layer.notifiy({
                        title: "保存成功",
                        icon: 1
                    })
                    getUserInfo();
                }
            })
        }

        function modifyPersonal(){
            modifyPersonalInfo(userInfo.value).then(res=>{
                if (res.code===200){
                    layer.notifiy({
                        title: "保存成功",
                        icon: 1
                    })
                    getUserInfo();
                }
            })
        }

        function handleModifyPwd() {
            modifyPwd.value.validate((isValidate, model, errors) => {
                if (isValidate){
                  modifyPassword(pwdInfo.value).then(res=>{
                    if(res.code===200){
                      layer.notifiy({
                        title: "保存成功",
                        icon: 1
                      })
                    }
                  })
                }
            });
        }

        onMounted(() => {
            getUserInfo()
            dictValue.value = getDict(proxy)
        })

        watch(() => route.params.page, (newPage) => {
            currentPage.value = newPage
        })
        return {
            userInfo,
            pwdInfo,
            dictValue,
            currentPage,
            modifyPersonal,
            handleModifyPwd,
            getCutDone,
            cutOptions,
            rules,
            modifyPwd
        }
    }
}
</script>


<style>
.avatar-show {
    height: 50vh;
    text-align: center;
    border-right: #dddddd 1px dashed;
}

.user-avatar {
    height: 25vh !important;
    width: 25vh !important;
    margin-top: 5vh;
    margin-bottom: 5vh
}

.avatar-title {
    margin-top: 5vh;
    font-size: xx-large;
    font-weight: bolder;
}

.form-container {
    margin-left: 5vh;
    margin-top: 5vh;
}

.pwd-div {
    width: 70%;
    margin: 0 auto
}
</style>