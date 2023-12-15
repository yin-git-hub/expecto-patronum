<template>
  <vue-danmaku v-model:danmus="danmus" ref="danmakuRef" style="height:100px; width:300px;"></vue-danmaku>
  <button @click="sendDanmu"> 发送弹幕</button>
</template>
<script setup>
import vueDanmaku from 'vue3-danmaku'
import {ref} from "vue";

const danmus = ref(['danmu1', 'danmu2', 'danmu3', '…'])
const danmakuRef = ref(null)
const sendDanmu = () => {
  const index = danmakuRef.value.push('new danmu')
  console.log(index)
// 打印出插入后的下标
} </script>
