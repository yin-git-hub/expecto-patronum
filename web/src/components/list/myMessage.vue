<!--eslit-diable-->
<template>
  <div class="container">
    <div class="chat-box">
      <myMessageBar
          :avatar-url="'https://hbimg.huabanimg.com/dec89391890c8cae3fc21af5e8d9a4211a922aa18a7b-SN9pHl_fw658'"
          :msg-text="'999999'"></myMessageBar>
      <otherMessageBar :avatar-url="'https://hbimg.b0.upaiyun.com/bf839d0677e8c59be15bd4663e592e3c26ab987a1162a-sL1LQL_fw658'"></otherMessageBar>
    </div>

    <div class="edit-box">
      <a-textarea
          placeholder="请输入消息"
          :auto-size="{ minRows: 3, maxRows: 3 }"
          v-model:value="chatContext"
      />
      <div class="operation-box">
        <a-button @click="sendMessage" type="primary">发送消息</a-button>
      </div>
    </div>
  </div>
</template>
<script setup>
/* eslint-disable */
import {useRoute} from "vue-router";
import myMessageBar from "./myMessageBar.vue"
import otherMessageBar from "./otherMessageBar.vue"
import store from "@/store";
import {onMounted, ref} from "vue";
import axios from "axios";
const chatContext = ref()
const route = useRoute()
const _userId = route.query.userId
const _token = store.state.userInfo.token
// const ws = new WebSocket("ws://localhost:7330/water-sty/scrolling/" + _token + "/" + store.state.videoInfo.videoId);
const chat = new WebSocket("ws://localhost:7330/water-sty/chat/"+_token+"/"+_userId);

chat.onopen = function () {
  console.log('WebSocket connection established');
};

chat.onmessage = function (message) {
  // data.value = JSON.parse(message.data);
};

console.log('route.query.chatMessage===',route.query.chatMessage);
const sendMessage = () => {
  console.log('chatContext.value===',chatContext.value)
  chat.send(chatContext.value);
}

</script>
<style scoped>

.container {
  height: 80vh;
  width: 100%;
  position: relative;
  border: 1px solid black;
}

.edit-box {
  width: 100%;
  height: 15vh;
  border-top: 1px solid black;
  position: absolute;
  bottom: 0;
  padding: 5px 1vw;
}

.operation-box {
  width: 100%;
  height: 6vh;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.chat-box {
  padding: 10px;
}

</style>