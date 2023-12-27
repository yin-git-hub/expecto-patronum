<template>
  <div>
    <vue3VideoPlay
        v-bind="options"
        :poster="coverUrl"
        @timeupdate="onTimeupdate"
        @play="onPlay"
        @pause="onPause"
    />

  </div>
</template>

<script setup >
import {reactive, ref} from "vue";


import store from "@/store";

const videoUrl  = ref(store.state.videoInfo.videoUrl||"")
const coverUrl = ref(store.state.videoInfo.cover||"")
const videoCurrentTime = ref('')
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
  title: "", //视频名称
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
</script>

<style scoped></style>
