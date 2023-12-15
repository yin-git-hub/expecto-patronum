<template>
  <vue-danmaku class="danmu-class"  ref="danmakuRef"
  ></vue-danmaku>

  <br />
  <a-input-search
      v-model:value="mandamus"
      placeholder="input search text"
      enter-button="Search"
      size="large"
      @search="sendMessage"
  />
  <br />

  {{ data }}
</template>

<script setup>
import vueDanmaku from "vue3-danmaku";
import store from "@/store";
import {ref, watch} from "vue";

const mandamus = ref([])
const danmakuRef = ref(null)
const _token = store.state.userInfo.token
const ws = new WebSocket("ws://localhost:7330/water-sty/scrolling/" + _token + "/" + store.state.videoInfo.videoId);
const data = ref('')



// 使用 watch 监听 store某个值 的变化
watch(() => store.state.videoInfo.isPlayer, (newValue, oldValue) => {
  console.log('Value changed from', oldValue, 'to', newValue);
  const isPlayer = store.state.videoInfo.isPlayer
  console.log('isPlayer = store.state.videoInfo.isPlayer===>',isPlayer)

    if(isPlayer==="pause"){
      danmakuRef.value.pause()
    }
    if(isPlayer==="play"){
      danmakuRef.value.play()
    }

});


ws.onopen = function () {
  console.log('WebSocket connection established');
};

ws.onmessage = function (message) {
  data.value =  JSON.parse(message.data);

  if (data.value.scrollingContext != null) {
    console.log('data===>', data)
  }
  console.log(message.data)

};

const sendMessage = () => {

  const scrollingContext = mandamus.value;


  const jsonData = {
    currentCount: 0,
    videoId: store.state.videoInfo.videoId,
    relativeTime: store.state.videoInfo.videoCurrentTime,
    scrollingContext: scrollingContext
  };

  const jsonStr = JSON.stringify(jsonData);

  ws.send(jsonStr);
  danmakuRef.value.add(scrollingContext)
  mandamus.value = ''
}
</script>

<style scoped>
.danmu-class {
  height: 100px;
  width: 900px;
}
</style>

