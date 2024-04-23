<template>
  <a-list item-layout="horizontal" :data-source="data">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-list-item-meta
            description=""
        >
          <template #title>
            <span v-if="item.reportStatus===2" href="#"> 你举报的视频<span style="color: red">已确认违规</span>&nbsp;&nbsp;&nbsp;<span style="color: skyblue">{{ item.videoName }}</span></span>
            <a @click="onVideo(item.videoId,null,null)" v-if="item.reportStatus===3" href="#"> 你举报的视频<span  style="color: green">没有问题</span>&nbsp;感谢关注&nbsp;&nbsp;<span style="color: skyblue">{{ item.videoName }}</span></a>
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

const data = ref()
onMounted(async () =>{

  await axios.post("/message/getVideoReportMsg").then(resp=>{
    console.log("getVideoReportMsg===",resp)
    data.value = resp.data

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
</script>

<style scoped>

</style>