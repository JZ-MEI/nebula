import {request} from "@/util/request.js";

export function getApiList(){
    return request({
        url:"/api/getApiList",
        method:'get'
    })
}