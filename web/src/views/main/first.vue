<template>


  <a-tabs v-model:activeKey="activeKey" @change="onTabChange">

    <a-tab-pane v-for="item in labels" :key="item.id" :tab="item.labelName">
      <video-info-list :user-list-data="videoInfoValue" >
      </video-info-list>
    </a-tab-pane>

  </a-tabs>
  <div>
    <a-pagination
        show-size-changer
        v-model:current="current1"
        v-model:pageSize="pageSize"
        :total="totalElements"
        @showSizeChange="onShowSizeChange"
        @change="onChange"
    />
    <br />
  </div>

</template>
<script setup>
import {defineComponent, onMounted, ref, watch} from 'vue';
import axios from "axios";
import router from "@/router";
import VideoInfoList from "@/components/list/VideoInfoList.vue";


const urlworksLabelId = router.currentRoute._rawValue.query.worksLabelId

const videoInfoValue = ref('')
const worksLabelId = ref(urlworksLabelId || '1')
const activeKey = worksLabelId
// 共查询出多少语句
const totalElements = ref()
//当前页总数据
const pageSize = ref(10);
//当前页
const current  = ref(1);
const labels  = ref();

onMounted(async() =>{
  try {
    await axios.post("/label/getVideoLabel").then(resp=>{
      if (resp.code===200){
        labels.value = resp.data
        console.log("labelsData.value===",resp.data)
      }
    })
  }catch (e){
    console.log(e)
  }
})
const onShowSizeChange = (current, pageSize) => {
  console.log('current, pageSize===>',current, pageSize);
};
watch(pageSize, () => {
  console.log('pageSize', pageSize.value);
});
watch(current , () => {
  console.log('current', current.value);
});

const onChange = (page, pageSize) => {
  // const worksLabelId = router.currentRoute._rawValue.query.worksLabelId

  console.log('worksLabelId,page, pageSize===>',worksLabelId,page, pageSize)

  axios.post(`/video/getVideoInfo`, {
    "worksLabelId": worksLabelId,
    "content": "",
    "pageIndex": page,
    "pageSize": pageSize || 10,
    "total": 0,
    "valList": []
  }).then(resp => {
    videoInfoValue.value = resp.data
    totalElements.value = resp.data.totalElements
    console.log('resp===>',resp.data)
    console.log('totalElements.value===>',totalElements.value)
  })
}

const onTabChange = key => {
  worksLabelId.value = key
  router.push({
    query: {
      worksLabelId: worksLabelId.value,
    },
  })
  axios.post(`/search/getVideoInfo`, {
    "worksLabelId": worksLabelId.value,
    "pageIndex": 1,
    "pageSize":  10,
    "total": 0,
    "valList": []
  }).then(resp => {
    videoInfoValue.value = resp.data
    totalElements.value = resp.data.totalElements
    console.log('resp===>',resp.data)
    console.log('totalElements.value===>',totalElements.value)
  })
}



defineComponent({
  name: "the-first-page",
});
</script>
