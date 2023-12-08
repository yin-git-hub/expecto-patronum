<template>
  <a-row class="login">
    <a-col :span="8" :offset="8" class="login-main">
      <h1 style="text-align: center"><CustomerServiceTwoTone />&nbsp;expecto patronum</h1>
      <a-form
          :model="loginForm"
          name="basic"
          autocomplete="off"
      >
        <a-form-item
            label=""
            name="mobile"
        >
          <a-input v-model:value="loginForm.phoneNum"  placeholder="请输入手机号"/>
        </a-form-item>

        <a-form-item
            label=""
            name="code"
        >
          <a-input v-model:value="loginForm.verifyCode" placeholder="请输入验证码">
            <template  #addonAfter>
              <a v-if="!loginForm.disabled" @click="sendCode">获取验证码</a>
              <a v-else>{{ loginForm.count }} 秒后重新发送</a>
            </template>
          </a-input>

        </a-form-item>

        <a-form-item>
          <a-button type="primary" block @click="login">登录</a-button>
        </a-form-item>

      </a-form>
    </a-col>
  </a-row>
</template>



<script>
import { defineComponent, reactive } from 'vue';
import axios from "axios";
import {notification} from "ant-design-vue";
import router from "@/router";



export default defineComponent({
  name: "login-view",
  setup() {
    const loginForm = reactive({
      disabled: false,
      total: 60,
      count: 0,

      phoneNum: '',
      verifyCode: '',
    });


    const timerHandler = () => {
      loginForm.count = loginForm.total
      loginForm.disabled = true

      const timer = setInterval(() => {
        if (loginForm.count > 1 && loginForm.count <= loginForm.total) {
          loginForm.count--
        } else {
          loginForm.disabled = false
          clearInterval(timer)
        }
      }, 1000)
    }

    const sendCode = () => {
      axios.post("/user/getVerifyCode", {
        phoneNum: loginForm.phoneNum
      }).then(response => {
        timerHandler();
        if (response.code===200) {
          notification.success({ description: '发送验证码成功！' });
          loginForm.code = response;
        } else {
          notification.error({ description: response.data });
        }
      });
    };

    const login = () => {
      // 发送登录请求到服务器
      axios.post("/user/verification", {
        phoneNum: loginForm.phoneNum,
        verifyCode: loginForm.verifyCode
      }).then(response => {
        console.log(response)
        if (response.code===200) {
          notification.success({ description: '登陆成功！' });
          // 登录成功后，跳转到控制台主页
          router.push("/welcome");
        } else {
          notification.error({ description: response.message });
        }
      });

    };

    return {
      loginForm,
      sendCode,
      login
    };
  },
});
</script>


<style>
/* 设置标题样式 */
.login-main h1 {
  font-size: 30px; /* 设置字体大小 */
  font-weight: bold; /* 设置字体粗细 */
}

/* 设置登录框的外部样式 */
.login-main {
  margin-top: 100px; /* 顶部外边距，垂直偏移 */
  padding: 30px 30px 20px; /* 内边距，上右下左 */
  border: 2px solid grey; /* 边框样式，2像素宽度，灰色 */
  border-radius: 10px; /* 圆角边框半径 */
  background-color: #fcfcfc; /* 背景颜色 */
}
</style>
