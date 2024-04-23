systemUploadMessage.vue<template>
  <a-list item-layout="horizontal" :data-source="dataLike">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-list-item-meta
            description=""
        >
          <template #title>
            <a @click="toAuthorWorks(item.userId)" style="color: blue" >{{item.nickname}}</a>&nbsp;
              赞了你的视频 &nbsp;&nbsp;&nbsp;<a @click="onVideo(item.videoId,null,null)" href="#">{{item.videoName}}</a>
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
  await axios.post("/message/getVideoLikeMsg").then(resp=>{
    if (resp.code === 200) {
      console.log("getVideoLikeMsg===",resp.code)
      if (resp.data !== null) {
        dataLike.value=resp.data;
      }
      console.log("getVideoLikeMsg===",dataLike.value)

    }
  })
})

const toAuthorWorks=(_userId)=>{
  console.log(_userId)
  router.push({path:"/toAuthorWorks",query:{userId:_userId}})
}

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
</script>

<style scoped>

</style>