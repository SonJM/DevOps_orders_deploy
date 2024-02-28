<template>
    <h1>HOME 화면</h1>
</template>

<script>
import {mapGetters} from 'vuex';
export default{
    data(){
        return{
            // ... 연산자를 통해 getter를 통해 반환되는 객체의 속성들을 현재 객체 내에 풀어서(spread)넣기 위함
            ...mapGetters(['getTotalQuantity'])
        }
    },
    components: {
    },
    created(){

    },
    methods: {

    }
}
</script>