import {request} from "@/util/request.js";

export function getRoleList(param){
    return request({
        url:"/sysRole/getRoleList",
        method:"get",
        params:param
    })
}

export function createOrUpdateSysRole(data){
    return request({
        url:'/sysRole/createOrUpdateSysRole',
        method:"post",
        data:data
    })
}

export function getRoleMenuList(param){
    return request({
        url:"/sysRole/getRoleMenuList",
        method:"get",
        params:param
    })
}

export function commitAuth(data){
    return request({
        url:"/sysRole/authorization",
        method:"post",
        data:data
    })
}

export function getRoleUserTableData(param){
    return request({
        url:"/sysRole/getRoleUserTableData",
        method:"get",
        params:param
    })
}

export function createOrDeleteRoleUser(data){
    return request({
        url:'/sysRole/createOrDeleteRoleUser',
        method:"post",
        data:data
    })
}

export function getUserRoleList(){
    return request({
        url:'/sysRole/getUserRoleList',
        method:'get'
    })
}