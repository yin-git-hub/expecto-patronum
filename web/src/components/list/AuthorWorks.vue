<template>


  <div class="image-avatar">
            <span style="float: left"><a-form-item>
              <a-avatar :size="64" :src="authorInfo.image"/>
            </a-form-item>
            </span>
    <span style="float: left; margin-left: 30px">
              <a-form-item label="昵称">
              {{ authorInfo.nickname }}
              </a-form-item>
              <a-form-item style="margin-top: -20px" label="个性签名">
              {{ authorInfo.signature }}
            </a-form-item>
            </span>
    <span style="float: right;position: relative">
       <the-following></the-following>
       <a-button @click="toChatPage(authorInfo.userId)" style="position: absolute;top: 40px;left: 0px;width: 66px;margin-right: 40px">私信</a-button>
    </span>
  </div>
  <a-divider></a-divider>

  <a-list item-layout="horizontal" :data-source="data">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-list-item-meta
            :description="item.videoSummary"
        >
          <template #title>
            <a @click="onVideo(item.videoId, item.videoCover, item.userId)">{{ item.videoName }}</a>
          </template>
          <template #avatar>
            <a-avatar :src="item.videoCover"/>
          </template>
        </a-list-item-meta>
      </a-list-item>
    </template>
  </a-list>
</template>

<script setup>
import {onMounted, ref} from 'vue';
import axios from "axios";
import {useRoute} from "vue-router";
import store from "@/store";
import router from "@/router";
import TheFollowing from "@/components/userTendency/following.vue";

let route = useRoute()
const authorInfo = ref({
  image: '',
  nickname: '',
  signature: '',
});
onMounted(async () => {

  try {
    await axios.post('/user/getAuthorWorks/' + route.query.userId).then(resp => {
      if (resp.code === 200) {
        console.log('getAuthorWorks.data===>', resp.data)
        data.value = resp.data

      }
    })
    await axios.post('/user/getUserInfoByUserId/' + route.query.userId).then(resp => {
      if (resp.code === 200) {
        console.log('getUserInfoByUserId.data===>', resp.data.image)
        authorInfo.value = resp.data
        store.commit("setUserId",authorInfo.value.userId)
      }
    })
  } catch (error) {
    console.error('请求失败:', error)
  }
})

const toChatPage = (_userId=>{
  console.log(_userId)
  router.push({ path: '/myMessage', query: { userId: _userId }})
})

const data = ref([])
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

<style scoped>

</style>
