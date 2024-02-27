<script>
import axios from "axios";

export default {
  name: 'SignupForm',
  data() {
    return {
      data: {
        name: '',
        email: '',
        address: '',
        password: '',
        role: '',
      }
    };
    },
  methods: {
    submitForm() {
      const params = {
        name: this.name,
        email: this.email,
        address: this.address,
        password: this.password,
        role: this.role
      };
      console.log("---axios Post 시작----");
      axios
        .post(`http://localhost:8082/member/new`, params)
          .then((response) => console.log(response))
      },
  },
};
</script>

<template>
  <div class="container">
    <form @submit.prevent="submitForm">
      <div>
        <label for="email">이메일</label>
        <input type="text" id="email" v-model="email" />
      </div>
      <div>
        <label for="name">이름</label>
        <input type="text" id="name" v-model="name" />
      </div>
      <div>
        <label for="password">비밀번호</label>
        <input type="password" id="password" v-model="password" />
      </div>
      <div>
        <label for="position">주소</label>
        <input type="text" id="address" v-model="address" />
      </div>
      <div>
        <label for="teamName">권한</label>
        <input type="text" id="role" v-model="role" />
      </div>
      <button type="submit">회원가입</button>
    </form>
  </div>
</template>

<style>
.boxTest {
  width: 200px;
  height: 50px;
  text-align: center;
  border: 1px solid #000;
}
</style>
