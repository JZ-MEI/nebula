import {request} from "../util/request.js";

export function login(data){
    return request({
        url: '/user/login', // 注意这里没有 '/api' 前缀，因为它将在 request.js 中被添加
        method: 'post',
        data: data
    });
}

export function getLoginState(){
    return request({
        url:"/user/getLoginState",
        method:"get"
    })
}


export function logout(){
    return request({
        url:"/user/logout",
        method:'post'
    })
}