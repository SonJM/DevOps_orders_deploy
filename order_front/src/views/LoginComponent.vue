<template>
    <div class="container">
        <div class="page-header text-center" style="margin-top: 20px;">
            <h1>로그인</h1>
        </div>
        <div id="update-form" class="mt-4">
        <!-- submit은 기본적으로 form제출 시 브라우저가 페이지를 새로고침하므로 해당동작을 막기위해 prevent사용 -->
            <form @submit.prevent="doLogin">
                <div class="form-group">
                    <label for="email">email: </label>
                    <input class="form-control" v-model="email" type="email">
                </div>
                <div class="form-group">
                    <label for="password">비번: </label>
                    <input class="form-control" v-model="password" type="password">
                </div>
                <button class="btn btn-primary" type="submit" value="로그인">로그인</button>
            </form>
        </div>
    </div>
</template>
<script>
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';

export default {
    data(){
        return{
            email: "",
            password: ""
        }
    },
    methods: {
        async doLogin(){
            // 2가지 예외 가능성:
            // 1)200번대 상태값이면서 token이 비어있는 경우
            // 2)200번대 상태값이 아닌경우
            try{
                const loginData = {email: this.email, password: this.password }
                console.log(process.env.VUE_APP_API_BASE_URL)
                const response =  await axios.post(`${process.env.VUE_APP_API_BASE_URL}/doLogin`, loginData);
                const token = response.data.result.token;
                if (token) {
                    console.log(token);
                    const decoded = jwtDecode(token);
                    console.log(decoded);
                    localStorage.setItem("role", decoded.role);
                    localStorage.setItem("token", token);
                    // created 함수는 reload될 때 1번만 실행되는 hook함수
                    // 그런데, router.push를 통한 화면전환은 reload를 실행시키지 않으므로, created함수 호출이 되지 않음
                    // this.$router.push("/");
                    window.location.href = "/";
                }else{
                    console.log("200 OK but not token");
                    alert("Login Failed")
                }
            }catch(error){
                const error_message = error.response.data.error_message;
                if(error_message){
                    console.log(error_message);
                    alert(error_message);
                }else{
                    console.log(error);
                    alert("Login Failed")
                }
            }
        }
    }
}
</script>
<style lang="scss" scoped>
</style>