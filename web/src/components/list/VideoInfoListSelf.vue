<template>
  <a-card>
    <a-list :data-source="videoInfoValue.valList" :grid="{ gutter: 16, column: 5 }">

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
            <a-button @click="delVideo(item.videoId)">删除</a-button>
          </a-card>
        </a-list-item>
      </template>
    </a-list>
  </a-card>
</template>
<script setup>

import {List, message} from "ant-design-vue";
import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";
import store from "@/store";
import router from "@/router";
const videoInfoValue = ref('')

onMounted(async () => {
  try {
    await axios.post(`/search/all/self`, {
      "type":'video',
      "pageIndex": 1,
      "pageSize": 10,
      "total": 0,
      "valList": []
    }).then(response => {
      videoInfoValue.value = response.data
      console.log('videoInfoValue.value===> ',videoInfoValue.value)
    });

  } catch (error) {
    console.error('请求失败:', error)
  }
})

const delVideo=(_videoId)=>{
  axios.post("/video/delVideo/"+_videoId).then(resp=>{
    if (resp.code === 200) {
      message.success('删除成功');
      window.location.reload()
    }
  })
}
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

defineComponent({
  props: {
    userListData: List,
    type: String,
    content: String
  }
});
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
