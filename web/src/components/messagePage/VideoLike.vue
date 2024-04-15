<template>
  <a-card>
    <a-list :data-source="videoInfoValue" :grid="{ gutter: 16, column: 5 }">

      <template #renderItem="{ item }">
        <a-list-item>
          <a-card hoverable style="width: 240px;height: 300px"
          >
            <template #cover>
              <img  @click="() => onVideo(item.videoId,item.videoCover,item.userId)" class="cover" alt="example" :src=item.videoCover>
            </template>
            <a-card-meta :title="item.videoName">
              <template #description>
                <p class="p-summary">简介: {{ item.videoSummary }}</p>
              </template>

            </a-card-meta>
          </a-card>
        </a-list-item>
      </template>
    </a-list>
  </a-card>
</template>
<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import store from "@/store";
import router from "@/router";
const videoInfoValue=ref()
onMounted(async ()=>{
  await axios.post("/like/getUserLikeVideoInfo").then(resp=>{
    console.log("/like/getUserLikeVideoInfo===",resp.data)
    if (resp.code===200){

      videoInfoValue.value=resp.data
    }
  })
})
const onVideo = (_videoId, _cover, _userId) => {
  axios.post("/video/getVideoUrl", {id: _videoId}).then(resp => {
    if (resp.code === 200) {
      store.commit("setVideoInfo", {
        videoUrl: resp.data,
        cover: _cover,
        videoId: _videoId,
        userId: _userId,
      });
      router.push('/player')
    }

  })
}

</script>

