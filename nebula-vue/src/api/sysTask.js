import {request} from "@/util/request.js";

export function getTaskTableData(param){
    return request({
        url:'/task/getTableData',
        method:'get',
        params:param
    })
}

export function modifyTaskOpen(data){
    return request({
        url:"/task/modifyTaskOpen",
        method:'post',
        data:data
    })
}

export function createOrEditTask(data){
    return request({
        url:"/task/createOrEditTask",
        method:'post',
        data:data
    })
}