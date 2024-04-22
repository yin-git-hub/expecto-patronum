<template>
  <a-list item-layout="horizontal" :data-source="data">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-list-item-meta
            description="Ant<a href='#'>ddd </a>Design, a design language for background applications, is refined by Ant UED Team"
        >
          <template #title>
            <a href="https://www.antdv.com/">{{ item.title }}</a>
          </template>
        </a-list-item-meta>
      </a-list-item>
    </template>
  </a-list>
</template>
<script setup>
import {onMounted} from "vue";
import axios from "axios";

const data = [
  {
    title: 'Ant Design Title 1',
  },
  {
    title: 'Ant Design Title 2',
  },
  {
    title: 'Ant Design Title 3',
  },
  {
    title: 'Ant Design Title 4',
  },
];
onMounted(async () =>{
  await axios.post("/message/getVideoLikeMsg").then(resp=>{
    console.log("getVideoLikeMsg===",resp)
  })
  await axios.post("/message/getVideoUploadMsg").then(resp=>{
    console.log("getVideoUploadMsg===",resp)
  })
  await axios.post("/message/getVideoReportMsg").then(resp=>{
    console.log("getVideoReportMsg===",resp)

  })
})
</script>

<style scoped>

</style>