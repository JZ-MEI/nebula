import {request} from "@/util/request.js";

export function getTableData(param){
    return request({
        url:"/sysOperLog/getTableData",
        method:'get',
        params:param
    })
}