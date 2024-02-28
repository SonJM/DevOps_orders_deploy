<template>
    <div class="container">
        <div class="page-header text-center" style="margin-top: 20px;"><h1>회원 정보</h1></div>
        <table class="table">
        <thead>
            <tr>
                <td>이름</td>
                <td>{{userInfo.name}}</td>
            </tr>
            <tr>
                <td>email</td>
                <td>{{userInfo.email}}</td>
            </tr>
            <tr>
                <td>도시</td>
                <td>{{userInfo.city}}</td>
            </tr>
            <tr>
                <td>상세주소</td>
                <td>{{userInfo.street}}</td>
            </tr>
            <tr>
                <td>우편정보</td>
                <td>{{userInfo.zipcode}}</td>
            </tr>
        </thead>
        <tbody>
            
        </tbody>
        </table>
    </div>
    <OrderListComponent
    :isAdmin="false"
    apiUrl="/member/myorders"
    />
</template>

<script>
import axios from 'axios';
import OrderListComponent from '@/components/OrderListComponent.vue';
export default {
    components:{
        OrderListComponent
    },
    data(){
        return {
            userInfo: [],
        }
    },
    async created() {
    try {
        const token = localStorage.getItem("token");
        const headers = { Authorization: `Bearer ${token}` };
        const response = await axios.get(`${process.env.VUE_APP_API_BASE_URL}/member/myInfo`, {headers});
        this.userInfo = response.data;
    } catch (error) {
        console.log(error.response);
    }
  },
}
</script>