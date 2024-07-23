import axios from "axios";
import {saveAs} from 'file-saver'

const DownloadUtil = {
    download(url,fileName,params){
        axios({
            method: 'get',
            url: import.meta.env.VITE_APP_BASE_API+url,
            responseType: 'blob',
            params:params
        }).then(res=>{
            const blob = new Blob([res.data]);
            const name = fileName
            saveAs(blob, name)
        })
    }
}

export default DownloadUtil;