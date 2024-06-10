import {request} from "@/util/request.js";

export function getOnlineUser(param){
    return request({
        url:"online/getOnlineUser",
        method:'get',
        params:param
    })
}

export function offline(data){
    return request({
        url:"online/forceOffline",
        method:'post',
        data:data
    })
}