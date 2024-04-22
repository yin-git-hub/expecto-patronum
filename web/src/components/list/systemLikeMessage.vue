systemUploadMessage.vue<template>
  <a-list item-layout="horizontal" :data-source="dataLike">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-list-item-meta
            description=""
        >
          <template #title>
            <a href="#"><span style="color: blue">{{item.nickname}}</span>&nbsp;赞了你的视频 &nbsp;&nbsp;&nbsp;{{item.videoName}}</a>
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
</script>

<style scoped>

</style>