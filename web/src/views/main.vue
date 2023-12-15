<script setup>
import {defineComponent, onMounted} from 'vue'
import TheHeaderView from "@/components/the-header.vue";
import axios from "axios";

onMounted(async () => {
  try {
    await axios.post('/user/refresh-token').then(resp => {
      console.log(resp)


      // 在这里可以对后端返回的数据进行处理，更新页面状态或执行其他操作
    }) // 向后端发送GET请求获取数据

  } catch (error) {
    console.error('请求失败:', error)
  }
})
defineComponent({
  name: "the-main"
})
</script>

<template>
  <the-header-view></the-header-view>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <router-view></router-view>
    </a-layout-content>
  </a-layout>


</template>

<style scoped>


</style>
