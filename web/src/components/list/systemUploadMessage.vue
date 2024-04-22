systemUploadMessage.vue<template>
  <a-list item-layout="horizontal" :data-source="dataLike">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-list-item-meta
            description=""
        >
          <template #title>
            <a v-if="item.videoReview===1" style="color: green" href="#">你的视频已通过审核&nbsp;&nbsp;&nbsp;{{item.videoName}}</a>
            <a v-if="item.videoReview===2" style="color: red" href="#">你的视频未通过审核&nbsp;&nbsp;&nbsp;{{item.videoName}}</a>
          </template>
        </a-list-item-meta>
      </a-list-item>
    </template>
  </a-list>
</template>
<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";

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
</script>

<style scoped>

</style>