import {request} from "@/util/request.js";

export function getTableData(param){
    return request({
        url:"/loginLog/getTableData",
        method:'get',
        params:param
    })
}