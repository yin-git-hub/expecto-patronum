<!--eslit-diable-->
<template>
  <div class="container">
    <div class="chat-box">
      <div style="display:flex; justify-content: center; margin-bottom: 5px" v-html="reState"></div>
      <div v-for="msg in chatWindowContext"
           :key="msg.id">
        <!--        对方为发送消息  我为接收消息-->
        <myMessageBar
            v-if="_userId===msg.sendUserId"
            :avatar-url="msg.sendUserImage"
            :msg-text="msg.message"
        ></myMessageBar>
        <!--        我方为发送消息  对方为接收消息-->
        <otherMessageBar
            v-if="_userId!==msg.sendUserId"
            :avatar-url="msg.sendUserImage"
            :msg-text="msg.message"
        ></otherMessageBar>
      </div>
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
import {onMounted, onUpdated, ref, watch} from "vue";
import axios from "axios";

const chatContext = ref()
const chatWindowContext = ref()
const route = useRoute()
// 获取和谁聊天的id
const _userId = ref()
const _token = store.state.userInfo.token
const chat = new WebSocket("ws://localhost:7330/water-sty/webSocket/chat/" + _token + "/" + route.query.userId);
// 对方状态
const reState = ref()
const reUserInfo = ref()

chat.onopen = function () {
  console.log('WebSocket connection established');
};



chat.onmessage = function (message) {
  console.log(typeof message.data,typeof "false")
  if (message.data.toString()==="false"||message.data.toString()==="true"){
    if (message.data == "false") {
      reState.value = "对方状态：<span style='color: #999999'>掉线</span>"
    }
    if (message.data == "true") {
      reState.value = "对方状态：<span style='color: green'>在线</span>"
    }
  }else {
    let arr = new Array()
    arr.sendUserId = reUserInfo.value.userId
    arr.message = message.data
    arr.sendUserImage = reUserInfo.value.image
    chatWindowContext.value.push(arr)

    // 第一种方案
    let chatBox = document.querySelector('.chat-box')
    setTimeout(() => {
      chatBox.scrollTop = chatBox.scrollHeight;
    }, 1)
  }
};

const sendMessage = () => {


  chat.send( chatContext.value);
  let arr = new Array()
  arr.sendUserId = store.state.userInfo.userId
  arr.message = chatContext.value
  arr.sendUserImage = store.state.userInfo.image
  chatWindowContext.value.push(arr)


  // 第一种方案
  let chatBox = document.querySelector('.chat-box')
  setTimeout(() => {
    chatBox.scrollTop = chatBox.scrollHeight;
  }, 1)

}
onMounted(() => {
  // route.query.userId 为 对方 的id
  axios.post('/chat/getChatMsg/' + route.query.userId).then(resp => {
    console.log('_chatMessage===', resp.data)
    _userId.value = parseInt(route.query.userId)
    chatWindowContext.value = resp.data;
  })
  // route.query.userId 为 对方 的id
  axios.post('/user/getUserInfoByUserId/'+route.query.userId).then(resp=>{
    if (resp.code===200) {
      reUserInfo.value = resp.data
    }
  })
})
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
  height: 450px;
  overflow: hidden;
  overflow-y: scroll;

}

</style>