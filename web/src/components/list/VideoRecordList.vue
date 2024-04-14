<template>
  <a-list  item-layout="horizontal" :data-source="data">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-list-item-meta
            :description= "item.videoSummary"
        >
          <template #title>
            <a @click="() => onVideo(item.videoId,item.videoCover,item.userId)">{{ item.videoName }}</a>
          </template>
          <template #avatar>
            <a-avatar :src="item.videoCover" />
          </template>
        </a-list-item-meta>
      </a-list-item>
    </template>
  </a-list>
</template>
<script setup>

import {onMounted, ref} from "vue";
import axios from "axios";
import store from "@/store";
import router from "@/router";


const data = ref("")

const onVideo = (_id,_cover,_userId)=>{
  axios.post("/video/getVideoUrl",{id:_id}).then(resp=>{
    if (resp.code===200){
      store.commit("setVideoInfo", {
        videoUrl:resp.data,
        cover:_cover,
        videoId:_id,
        userId:_userId,
      });
      router.push('/player')
    }

  })
}

onMounted(async ()=>{
  await axios.post("/video/getVideoRecord").then(resp=>{
    console.log("video.resp.data===",resp.data)
    data.value = resp.data
  })
})

</script>
<style scoped>
.cover {
  width: 50%; /* 相对于父元素的宽度缩放为50% */
  height: 50%; /* 根据宽度比例自动调整高度 */
  margin: 0 auto;
}

.p-summary {
  width: 200px; /* 定义容器的宽度 */
  white-space: nowrap; /* 不换行 */
  overflow: hidden; /* 隐藏溢出的文本 */
  text-overflow: ellipsis; /* 显示省略号 */
}
</style>
