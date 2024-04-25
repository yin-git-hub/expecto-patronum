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
      />
      <div class="operation-box">
        <a-button type="primary">发送消息</a-button>
      </div>
    </div>
  </div>
</template>
<script setup>
/* eslint-disable */
import {useRoute} from "vue-router";
import myMessageBar from "./myMessageBar.vue"
import otherMessageBar from "./otherMessageBar.vue"

const route = useRoute()
const userId = route.query.userId
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