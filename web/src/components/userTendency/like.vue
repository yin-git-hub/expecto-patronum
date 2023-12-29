<template>
  <a @mousemove="updateTooltip" @mouseleave="hideTooltip">
    <img style="width: 32px;height: 32px" :src="imagePath" @click="changeImage">
    <div v-if="showTooltip" :style="tooltipStyle" class="tooltip">{{ noticInfo }}</div>
  </a>
</template>

<script setup>

import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";
import store from "@/store";
// 定义图片路径
const likeImage = require('../../assets/like.png');
const like2Image = require('../../assets/like2.png');
const imagePath = ref(likeImage);
const noticInfo = ref("点赞")
defineComponent({
  name: "the-like"
})

onMounted(async () => {
  try {
    await axios.post('/like/getLikeStatus',{videoId:store.state.videoInfo.videoId}).then(resp=>{
      if (resp.data.videoId === store.state.videoInfo.videoId) {
        imagePath.value = like2Image
        noticInfo.value = "已点赞"
      }
    })

  } catch (error) {
    console.error('请求失败:', error)
  }
})
const changeImage = () => {
  if (noticInfo.value==="点赞"){
    axios.post("/like/addLike",{videoId:store.state.videoInfo.videoId}).then(resp=>{
      if(resp.code===200){
        imagePath.value = imagePath.value === likeImage ? like2Image : likeImage;
        noticInfo.value = noticInfo.value === "点赞" ? "已点赞":"点赞";
      }
    })
  }else {
    axios.post("/like/cancelLike",{videoId:store.state.videoInfo.videoId}).then(resp=>{
      if(resp.code===200){
        imagePath.value = imagePath.value === likeImage ? like2Image : likeImage;
        noticInfo.value = noticInfo.value === "点赞" ? "已点赞":"点赞";
      }
    })

  }

};

// Tooltip 相关
const showTooltip = ref(false);
const tooltipStyle = ref({});

const updateTooltip = (event) => {
  showTooltip.value = true;
  tooltipStyle.value = {
    position: 'fixed',
    left: `${event.clientX + 10}px`, // 横向位置
    top: `${event.clientY + 10}px` // 纵向位置
  };
};

const hideTooltip = () => {
  showTooltip.value = false;
};
</script>

<style scoped>
.tooltip {
  background-color: black;
  color: white;
  padding: 5px 10px;
  border-radius: 4px;
  /* 其他样式 */
}
</style>
