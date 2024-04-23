systemUploadMessage.vue<template>
  <a-list item-layout="horizontal" :data-source="dataLike">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-list-item-meta
            description=""
        >
          <template #title>
            <a v-if="item.videoReview===1" @click="onVideo(item.videoId,null,null)" style="color: green" href="#">你的视频已通过审核&nbsp;&nbsp;&nbsp;{{item.videoName}}</a>
            <a v-if="item.videoReview===2" @click="toCreate" style="color: red" href="#">你的视频未通过审核&nbsp;&nbsp;&nbsp;{{item.videoName}}</a>
          </template>
        </a-list-item-meta>
      </a-list-item>
    </template>
  </a-list>
</template>
<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import router from "@/router";
import store from "@/store";

const dataLike=ref()
onMounted(async () =>{
  await axios.post("/message/getVideoUploadMsg").then(resp=>{
    if (resp.code === 200) {
      console.log("getVideoLikeMsg===",resp.code)
      if (resp.data !== null) {
        dataLike.value=resp.data;
      }
      console.log("getVideoLikeMsg===",dataLike.value)

    }
  })
})

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

const toCreate = ()=>{
  router.push('/create')
}
</script>

<style scoped>

</style>