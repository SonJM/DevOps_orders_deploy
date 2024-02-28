<template>
    <OrderListComponent
    :isAdmin="true"
    :apiUrl="`/member/${id}/orders`"
    />
</template>

<script>
import OrderListComponent from '@/components/OrderListComponent.vue';
export default {
    props:["id"],
    components:{
        OrderListComponent
    }
}
</script>