import {getUserRoleList} from "@/api/sysRole.js";
export default {
    async mounted(el, binding) {
        const { value } = binding;
        const res = await getUserRoleList();
        const permissions = res.data; // 从你的权限系统中获取权限列表

        if (!permissions.includes(value)) {
            el.parentNode && el.parentNode.removeChild(el);
        }
    }

};