export const memberRoutes = [
    {
        path: '/members',
        name: 'MemberList',
        component: ()=>import('@/views/MemberList.vue'),
    },
    {
        path: '/member/new',
        name: "MemberCreate",
        component: ()=>import("@/views/MemberCreate.vue"),
    },
    {
        path: '/member/:id/orders',
        name: 'MemberOrders',
        component: ()=>import("@/views/MemberOrders.vue"),
        props: true,
    },
    {
        path: '/mypage',
        name: 'MyPage',
        component: ()=>import('@/views/MyPage.vue'),
    },
];