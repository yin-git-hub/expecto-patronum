<template>
  <div class="class-video">
    <vue3VideoPlay
        v-bind="options"
        :poster="coverUrl"
        @timeupdate="onTimeupdate"
        @play="onPlay"
        @pause="onPause"
    />
  </div>
  <div  class="class-scrolling">
    <vue-danmaku class="danmu-class" ref="danmakuRef"
    ></vue-danmaku>

  </div>

  <div style="width: 800px;align-items: center; display: flex; margin-top: 10px" class="calss-inputDanmu">

    <span style="white-space: nowrap;">{{ data }}&nbsp;人正在观看</span>&nbsp;&nbsp;

    <a-input-search
        v-model:value="mandamus"
        placeholder="input search text"
        enter-button="发送弹幕"
        size="large"

        autoplay=false
        @search="sendMessage"></a-input-search>

  </div>


</template>

<script setup >
import {reactive, ref} from "vue";


import store from "@/store";

const videoUrl  = ref(store.state.videoInfo.videoUrl||"")
const coverUrl = ref(store.state.videoInfo.cover||"")
const videoCurrentTime = ref('')
const mandamus = ref()
const onTimeupdate = (ev) => {
  videoCurrentTime.value = ev.target.currentTime
  const videoInfo = store.state.videoInfo;
  store.commit("setVideoInfo",{
    ...videoInfo,
    videoCurrentTime:videoCurrentTime.value
  })
}

const onPlay = (ev) => {
  const videoInfo = store.state.videoInfo;
  console.log("播放===>",ev.type);
  const _videoInfo = {
    ...videoInfo,
    isPlayer:ev.type
  }
  store.commit("setVideoInfo",_videoInfo)

};
const onPause = (ev) => {
  console.log( "暂停===>",ev);
  const videoInfo = store.state.videoInfo;
  const _videoInfo = {
    ...videoInfo,
    isPlayer:ev.type
  }
  store.commit("setVideoInfo",_videoInfo)

};

const options = reactive({
  width: "800px", //播放器宽度
  height: "450px", //播放器高度
  color: "#409eff", //主题色
  title: "ffff", //视频名称
  src: videoUrl.value, //视频源
  muted: true, //静音
  webFullScreen: false,
  speedRate: ["0.75", "1.0", "1.25", "1.5", "2.0"], //播放倍速
  autoPlay: false, //自动播放
  loop: false, //循环播放
  mirror: false, //镜像画面
  ligthOff: false, //关灯模式
  volume: 0.3, //默认音量大小
  control: true, //是否显示控制
  controlBtns: [
    "audioTrack",
    "quality",
    "speedRate",
    "volume",
    "setting",
    "pip",
    "pageFullScreen",
    "fullScreen",
  ], //显示所有按钮,
});


import vueDanmaku from "vue3-danmaku";
import {onMounted,   watch} from "vue";
import axios from "axios";

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
.class-video, .class-scrolling {
  top: 150px;
  left: 400px;
}
.danmu-class{
  height: 450px;
  width: 800px;
  z-index: 99999;
}
.class-scrolling {
  position: absolute; /* 绝对定位 */
  width: 100%;
  height: 100%;
  pointer-events: none; /* 允许点击穿透，便于操作底下的视频播放器 */

}

</style>
