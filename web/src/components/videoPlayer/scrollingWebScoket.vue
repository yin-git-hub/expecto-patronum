<template>
  <vue-danmaku class="danmu-class" ref="danmakuRef"
  ></vue-danmaku>

  <br/>
  <a-input-search
      v-model:value="mandamus"
      placeholder="input search text"
      enter-button="Search"
      size="large"

      autoplay=false
      @search="sendMessage"></a-input-search>
  <br/>

  {{ data }}
</template>

<script setup>
import vueDanmaku from "vue3-danmaku";
import store from "@/store";
import {onMounted, ref, watch} from "vue";
import axios from "axios";

const mandamus = ref()
const danmakuRef = ref(null)
const _token = store.state.userInfo.token
const ws = new WebSocket("ws://localhost:7330/water-sty/scrolling/" + _token + "/" + store.state.videoInfo.videoId);
const data = ref('')
const mandamuList = ref([])


// 使用 watch 监听 store某个值 的变化
watch(() => store.state.videoInfo.isPlayer, () => {

  const isPlayer = store.state.videoInfo.isPlayer

  if (isPlayer === "play") {
    danmakuRef.value.play()
  } else {
    danmakuRef.value.pause()
  }

});
let visited = [];
watch(() => store.state.videoInfo.videoCurrentTime, () => {

  let videoCurrentTime = store.state.videoInfo.videoCurrentTime
  let list = mandamuList.value;
  let videoCurrentTimeArea = Math.floor(videoCurrentTime)

// 使用for循环遍历list
  for (let item of list) {
    let danmuTime = item.relativeTime
    if (danmuTime >= videoCurrentTimeArea && danmuTime < videoCurrentTimeArea + 1) {
      // 获取当前元素在列表中的索引
      let index = item.id;
      if (!visited.includes(index)) {

        visited.push(index);
        danmakuRef.value.add(item.scrollingContext);

      }
    }

  }
  // danmakuRef.value.add()
});

onMounted(async () => {
  try {
    danmakuRef.value.pause()
    await axios.post('/video/getScrolling', {
      id: store.state.videoInfo.videoId
    }).then(resp => {
      if (resp.code === 200) {
        mandamuList.value = resp.data
      }
      console.log(resp)
    })
  } catch (e) {
    console.log(e)
  }

})


ws.onopen = function () {
  console.log('WebSocket connection established');
};

ws.onmessage = function (message) {
  data.value = JSON.parse(message.data);


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
  height: 450px;
  width: 800px;
  z-index: 99999;
}
</style>

