export const orderRoutes = [
    {
        path: '/orders',
        name: 'OrderList',
        component: ()=>import("@/views/OrderList.vue"),
    },
    {
        path: '/ordercart',
        name: 'OrderCart',
        component: ()=>import("@/views/OrderCart.vue"),
    }
]