import {request} from "@/util/request.js";

export function getServerInfo(){
    return request({
        url: '/server/getServerInfo',
        method: 'get'
    })
}