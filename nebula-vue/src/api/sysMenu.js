import {request} from "@/util/request.js";

export function getMenuList(param){
    return request({
        url:"/sysMenu/getMenuList",
        method:"get",
        params:param
    })
}

export function getPageBreadcrumb(param){
    return request({
        url:"/sysMenu/getPageBreadcrumb",
        method: "get",
        params:param
    })
}

export function getRouterData(){
    return request({
        url:"/sysMenu/getRouterData",
        method:'get'
    })
}

export function getMenuTableData(param){
    return request({
        url:"/sysMenu/getMenuTableData",
        method:'get',
        params:param
    })
}

export function createOrEditMenu(createData){
    return request({
        url:"/sysMenu/createOrEditMenu",
        method:"post",
        data:createData
    })
}

export function deleteById(data){
    return request({
        url:"/sysMenu/deleteById",
        method:"post",
        data:data
    })
}
export function getMenuInfo(param){
    return request({
        url:'/sysMenu/getMenuInfo',
        method:'get',
        params:param
    })
}