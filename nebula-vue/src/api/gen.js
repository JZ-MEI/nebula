import {request} from "@/util/request.js";

export function getTableName(){
    return request({
        url:'/gen/getTableName',
        method:"get"
    })
}

export function getTableColumns(param){
    return request({
        url:'/gen/getTableColumns',
        method:'get',
        params:param
    })
}

export function genDomain(data){
    return request({
        url:'/gen/genCode',
        method:"post",
        data:data
    })
}

export function downloadGenCode(data){
    return request({
        url:'/gen/downloadCode',
        method:"get",
        data:data
    })
}