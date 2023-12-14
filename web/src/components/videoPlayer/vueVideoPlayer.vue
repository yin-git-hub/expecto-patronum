<template>
  <div>
    <vue3VideoPlay
        v-bind="options"
        :poster="coverUrl"
    />
    <vue-danmaku v-model:danmus="danmus" style="height:100px; width:900px;"></vue-danmaku>
    <template  >
      <span>{{ danmu }}</span>
    </template>
  </div>
</template>

<script setup >
import {reactive, ref} from "vue";

import vueDanmaku from "vue3-danmaku";
import store from "@/store";

const danmus = ref(['danmu1', 'danmu2', 'danmu3'])
const videoUrl  = ref(store.state.videoInfo.videoUrl||"")
const coverUrl = ref(store.state.videoInfo.cover||"")
const options = reactive({
  width: "800px", //播放器宽度
  height: "450px", //播放器高度
  color: "#409eff", //主题色
  title: "", //视频名称
  src: videoUrl.value, //视频源
  muted: false, //静音
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
