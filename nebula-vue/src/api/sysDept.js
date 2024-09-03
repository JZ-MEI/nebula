import {request} from "@/util/request.js";

export function getTableData(param){
    return request({
        url:"/sysDept/getDeptTableData",
        method:'get',
        params:param
    })
}

export function createOrUpdateDept(data){
    return request({
        url:"/sysDept/createOrEditDeptInfo",
        method:'post',
        data:data
    })
}

export function changeDeptStatus(data){
    return request({
        url:"/sysDept/changeDeptStatus",
        method:'post',
        data:data
    })
}

export function getDeptName(id){
    return request({
        url:"/sysDept/getDeptName/"+id,
        method:'get'
    })
}

export function getDeptUser(param){
    return request({
        url:"/sysDept/getDeptUser",
        method:'get',
        params:param
    })
}

export function addOrEditUserToDept(data){
    return request({
        url:"/sysDept/addOrEditUserToDept",
        method:"post",
        data:data
    })
}

export function removeUserFormDept(data){
    return request({
        url:"/sysDept/removeUserFormDept",
        method:"post",
        data:data
    })
}