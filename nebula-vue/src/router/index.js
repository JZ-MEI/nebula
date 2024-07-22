import {createRouter, createWebHistory} from 'vue-router'
import Layout from "@/layout/index.vue";
import Cookies from 'js-cookie';
import {getRouterData} from "@/api/sysMenu.js";

const staticRoutes = [
    {
        path: '/',
        component: () => import('@/views/login.vue')
    },
    {
        path: '/',
        component: Layout,
        children: [
            {
                path: '/index',
                component: () => import('../views/index.vue')
            }
        ]
    }
];
const dynamicRoutes = [
    {
        path: "/",
        component: Layout,
        children: [
            {
                path: '/system/dict/detail/:dictId(\\d+)',
                name:'dictDetail',
                component: () => import('../views/system/dict/detail.vue'),
                meta:{menuTitle:'字典详情',keepAlive: false}
            }
        ]
    },
    {
        path: "/",
        component: Layout,
        children: [
            {
                path: '/system/dept/detail/:deptId(\\d+)',
                name:'deptDetail',
                component: () => import('../views/system/dept/detail.vue'),
                meta:{menuTitle:'部门详情',keepAlive: false}
            }
        ]
    },
    {
        path: "/",
        component: Layout,
        children: [
            {
                path:'/system/role/detail/:roleId(\\d+)',
                name:'roleDetail',
                component:()=>import('../views/system/role/detail.vue'),
                meta:{menuTitle:'角色用户',keepAlive: false}
            }
        ]
    },
    {
        path: "/",
        component: Layout,
        children: [
            {
                path:'/personal/:page(\\w+)',
                name:'personalHub',
                component:()=>import('../views/system/user/personalHub.vue'),
                meta:{keepAlive:false,menuTitle: '个人中心'}
            }
        ]
    },
    {
        path:"/",
        component: Layout,
        children: [
            {
                path:'/monitor/task/detail/:taskId(\\d+)',
                name:'execLog',
                component:()=>import('../views/monitor/task/detail.vue'),
                meta:{menuTitle: '调度日志',keepAlive: false}
            }
        ]
    }
]

const router = createRouter({
    history: createWebHistory(),
    routes: await getAllRouter(),
    scrollBehavior(to, from, savedPosition) {
        if (savedPosition) {
            return savedPosition
        } else {
            return {top: 0}
        }
    },
});

async function getAllRouter() {
    const finalRoutes = [...staticRoutes]
    if (Cookies.get("nebula")) {
        finalRoutes.push(...dynamicRoutes)
        const res = await getRouterData();
        const accessRouter = res.data;

        accessRouter.forEach(item => {
            finalRoutes.push({
                path: '/',
                component: Layout,
                children: [
                    {
                        path: item.routerPath,
                        name: item.menuName,
                        id:item.id,
                        component: () => import(/* @vite-ignore */ `../views${item.compPath}`),
                        meta:{menuTitle:item.menuTitle,keepAlive:item.isCache===1}
                    }
                ]
            })
        });
    }
    return finalRoutes
}

// ,
// {
//     path:'/personal',
//         component: ()=>import('../views/system/user/personalHub.vue')
// }

export default router;