<template>


  <div class="image-avatar">
            <span style="float: left"><a-form-item >
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
  </div>
  <a-divider></a-divider>

</template>

<script setup>
import {onMounted, ref} from 'vue';
import axios from "axios";
import {useRoute} from "vue-router";

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
      }
    })
    await axios.post('/user/getUserInfoByUserId/' + route.query.userId).then(resp => {
      if (resp.code === 200) {
        console.log('getUserInfoByUserId.data===>', resp.data.image)
        authorInfo.value = resp.data
      }
    })
  } catch (error) {
    console.error('请求失败:', error)
  }
})


</script>

<style scoped>

</style>
