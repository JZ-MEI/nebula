import {getInitDict} from "@/api/sysDict.js";


// export default {
//     install(app, options) {
//         // 在应用实例上混入数据
//         app.mixin({
//             data() {
//                 return {
//                     dict: {}
//                 }
//             },
//             methods: {
//                 getDictValue() {
//                     if (this.$options.dicts !== undefined) {
//                         let dicts = this.$options.dicts
//                         dicts.forEach(item => {
//                             // this.dict.push({[item]: this.initDictData[item]})
//                             this.dict = this.initDictData
//                         })
//                     }
//                 },
//                 initDict() {
//                     getInitDict().then(res => {
//                         this.initDictData = res.data
//                         // this.getDictValue()
//                     })
//                 }
//             },
//             created() {
//                 if (this.$options.dicts) {
//                     this.initDict()
//                 }
//             },
//         });
//     }
// }

export function getDict(proxy){
    const dictValue = {};
    const dicts = proxy.type.dicts
    let dictDataString = localStorage.getItem("dict");
    if (dictDataString !== null) {
        const dictData = JSON.parse(dictDataString);
        dicts.forEach(key => {
            if (dictData.hasOwnProperty(key)) {
                dictValue[key] = dictData[key];
            }
        });
    }
    return dictValue
}


export function initDict() {
    getInitDict().then(res => {
        localStorage.setItem("dict",JSON.stringify(res.data))
    })
}