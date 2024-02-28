import {createRouter, createWebHistory} from 'vue-router';
// export default인 경우에는 {} 필요없고, 여러개의 요소가 있을 경우에는 {} 필요.
import {memberRoutes} from "./memberRouter.js";
import { orderRoutes } from './orderRouter.js';
import { itemRoutes } from './itemRouter.js';

const routes = [
    {
        // url경로 지정
        path: '/',
        // 이 라우터의 이름
        name: 'home',
        component: ()=>import('@/views/ItemList.vue'),
    },
    {
        path: '/login',
        name: 'Login',
        component: ()=>import('@/views/LoginComponent.vue'),
    },
    {
        path: '/basic',
        name: 'BasicComponent',
        component: ()=>import('@/components/BasicComponent.vue'),
    },
    // ...은 스프레드 연산자로 불린다.
    // 주로, 배열요소를 다른 배열요소에 합할때 사용
    ...memberRoutes,
    ...orderRoutes,
    ...itemRoutes
]

const router = createRouter({
    // vue router는 내부적으로 두가지 방식의 히스토리 관리를 제공
    // 1) createWebHistory, createHashHistory
    history: createWebHistory(),
    routes
});

export default router;