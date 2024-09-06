import { createRouter, createWebHistory } from 'vue-router';
import Layout from "@/layout/index.vue";
import Cookies from 'js-cookie';
import { getRouterData } from "@/api/sysMenu.js";

const staticRoutes = [
    {
        path: '/',
        component: () => import('@/views/login.vue')
    },
    {
        path: '/index',
        component: Layout,
        children: [
            {
                path: '',
                component: () => import('../views/index.vue')
            }
        ]
    }
];

const dynamicRoutes = [
    {
        path:"/",
        component:Layout,
        children:[
            {
                path: "/system/dict/detail/:dictId(\\d+)",
                name: 'dictDetail',
                component: () => import('../views/system/dict/detail'),
                meta: { menuTitle: '字典详情', keepAlive: false }
            },
            {
                path: "/system/dept/detail/:deptId(\\d+)",
                name: 'deptDetail',
                component: () => import('../views/system/dept/detail.vue'),
                meta: { menuTitle: '部门详情', keepAlive: false }
            },
            {
                path: "/system/role/detail/:roleId(\\d+)",
                name: 'roleDetail',
                component: () => import('../views/system/role/detail.vue'),
                meta: { menuTitle: '角色用户', keepAlive: false }
            },
            {
                path: "/personal/:page(\\w+)",
                name: 'personalHub',
                component: () => import('../views/system/user/personalHub.vue'),
                meta: { keepAlive: false, menuTitle: '个人中心' }
            },
            {
                path: "/monitor/task/detail/:taskId(\\d+)",
                name: 'execLog',
                component: () => import('../views/monitor/task/detail.vue'),
                meta: { menuTitle: '调度日志', keepAlive: false }
            }
        ]
    }
];

const modules = import.meta.glob('@/views/**/*.vue');
// 函数：根据后端数据生成动态路由
function createDynamicRoutes(routes) {
    routes.forEach(item => {
        const routeComponent = modules[`/src/views${item.compPath}`];
        router.addRoute({
            path: '/',
            component: Layout,
            children: [
                {
                    path: item.routerPath,
                    name: item.menuName,
                    id: item.id,
                    component: routeComponent,
                    meta: { menuTitle: item.menuTitle, keepAlive: item.isCache === 1 }
                }
            ]
        });
    });
}

const router = createRouter({
    history: createWebHistory(),
    routes: staticRoutes.concat(dynamicRoutes),
    scrollBehavior(to, from, savedPosition) {
        if (savedPosition) {
            return savedPosition;
        } else {
            return { top: 0 };
        }
    },
});

let isRouterInitialized = false;

router.beforeEach(async (to, from, next) => {
    if (Cookies.get("nebula")) {
        if (!isRouterInitialized && to.path !== '/') {
            try {
                const res = await getRouterData();
                createDynamicRoutes(res.data);
                isRouterInitialized = true;
                next({ ...to, replace: true }); // 确保动态路由加载后继续导航
            } catch (error) {
                console.error("Failed to fetch routes:", error);
                next(false);
            }
        } else if (to.path === '/') {
            next('/index'); // 重定向到首页
        } else {
            next();
        }
    } else {
        if (to.path !== '/') {
            next({ path: '/' });
        } else {
            next();
        }
    }
});

export default router;