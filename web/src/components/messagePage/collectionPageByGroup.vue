<template>
  <a-card>
    <a-list :data-source="videoList" :grid="{ gutter: 16, column: 5 }">

      <template #renderItem="{ item }">
        <a-list-item>
          <a-card hoverable style="width: 240px;height: 300px"
                  @click="() => onVideo(item.videoId,item.videoCover,item.userId)">
            <template #cover>
              <img class="cover" alt="example" :src=item.videoCover>
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

import {List} from "ant-design-vue";
import {defineComponent, ref, watchEffect} from 'vue';
import axios from "axios";
import store from "@/store";
import router from "@/router";
import {useRoute} from "vue-router";

const videoInfoValue = ref('')
const videoList=ref('')
const route = useRoute()

watchEffect(() => {
  // 在目标路由组件中获取查询参数 id


  try {
    let collectionGroupId = route.query.collectionGroupId
    axios.post(`/search/all/collection/group`, {
      "type": 'video',
      "pageIndex": 1,
      "pageSize": 10,
      "total": 0,
      "collectionGroupId": collectionGroupId,
      "valList": []
    }).then(response => {
      if (response.code === 200&&response.data!==null) {
        videoInfoValue.value = response.data
        videoList.value = videoInfoValue.value.valList
      } else {
        videoList.value = []
      }
      console.log('videoInfoValue.value.valList===>',videoInfoValue.value.valList)
      console.log('videoInfoValue.value===> ', videoInfoValue.value, collectionGroupId)
    });
  } catch (error) {
    console.error('请求失败:', error)
  }
});
const onVideo = (_id, _cover, _userId) => {
  axios.post("/video/getVideoUrl", {id: _id}).then(resp => {
    if (resp.code === 200) {
      store.commit("setVideoInfo", {
        videoUrl: resp.data,
        cover: _cover,
        videoId: _id,
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
