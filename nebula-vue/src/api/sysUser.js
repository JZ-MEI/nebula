import {request} from "@/util/request.js";

export function getUserTableData(param){
    return request({
        url:"/sysUser/getUserTableData",
        method:'get',
        params:param
    })
}

export function createOrEditUserInfo(data){
    return request({
        url:"/sysUser/createOrEditUserInfo",
        method:'post',
        data:data
    })
}

export function modifyPwd(data){
    return request({
        url:"/sysUser/modifyPwd",
        method:"post",
        data:data
    })
}

export function getNormalUser(){
    return request({
        url: "/sysUser/getNormalUser",
        method:'get'
    })
}

export function getPersonalInfo(){
    return request({
        url:"/sysUser/getPersonalInfo",
        method:'get'
    })
}

export function modifyPersonalInfo(data){
    return request({
        url:"/sysUser/modifyPersonalInfo",
        method:'post',
        data:data
    })
}

export function modifyPassword(data){
    return request({
        url:"/sysUser/modifyPassword",
        method:'post',
        data:data
    })
}