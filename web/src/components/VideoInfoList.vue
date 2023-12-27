<template>
  <a-card>
    <a-list :data-source="userListData.valList" :grid="{ gutter: 16, column: 5 }">

      <template #renderItem="{ item }">
        <a-list-item >
          <a-card hoverable style="width: 240px;height: 300px" @click="() => onVideo(item.videoId,item.videoCover,item.userId)">
            <template  #cover>
              <img  class="cover" alt="example" :src=item.videoCover />
            </template>
            <a-card-meta :title="item.videoName">
              <template #description>
                <p class="p-summary">简介: {{ item.videoSummary}}</p>
              </template>
            </a-card-meta>
          </a-card>
        </a-list-item>
      </template>
    </a-list>
  </a-card>
</template>
<script>

import {List} from "ant-design-vue";
import { defineComponent  } from 'vue';
import axios from "axios";
import store from "@/store";
import router from "@/router";


export default defineComponent({
  props: {
    userListData: List,
    type: String,
    content: String
  },
  setup(){
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
          console.log('resp.data,_cover===>',resp,_cover)
        }

      })
    }
    return{
      onVideo,
    }
  }
});
</script>
<style scoped>
.cover {
  width: 50%; /* 相对于父元素的宽度缩放为50% */
  height: 50% ; /* 根据宽度比例自动调整高度 */
  margin: 0 auto;
}

.p-summary {
  width: 200px; /* 定义容器的宽度 */
  white-space: nowrap; /* 不换行 */
  overflow: hidden; /* 隐藏溢出的文本 */
  text-overflow: ellipsis; /* 显示省略号 */
}
</style>
