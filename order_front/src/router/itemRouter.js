export const itemRoutes = [
    {
        path: '/items',
        name: 'ItemList',
        component: ()=>import("@/views/ItemList.vue")
    },
    {
        path: '/items/manage',
        name: 'ItemListManage',
        component: ()=>import("@/views/ItemListManage.vue"),
    },
    {
        path: '/item/create',
        name: 'ItemCreate',
        component: ()=>import("@/views/ItemCreate.vue"),
    }
]