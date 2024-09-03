import {request} from "@/util/request.js";

export function getDictTableData(param){
    return request({
        url:"/sysDict/getDictTableData",
        method:'get',
        params:param
    })
}

export function getInitDict(){
    return request({
        url:"/sysDict/getInitDictData",
        method:'get'
    })
}

export function getDictData(param){
    return request({
        url:"/sysDict/getDictData",
        method:'get',
        params:param
    })
}

export function deleteDictType(data){
    return request({
        url:"/sysDict/deleteDictType",
        method:'post',
        data:data

    })
}

export function createOrEditDictType(data){
    return request({
        url:'/sysDict/createOrEditDictType',
        method:'post',
        data:data
    })
}

export function createOrEditDictData(data){
    return request({
        url:"/sysDict/createOrEditDictData",
        method:'post',
        data:data
    })
}

export function deleteDictData(data){
    return request({
        url:"/sysDict/deleteDictData",
        method:'post',
        data:data
    })
}