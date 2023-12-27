<script setup>
defineComponent({
  name: "the-following"
})
import {defineComponent, onMounted, ref} from 'vue'
import axios from "axios";
import store from "@/store";

onMounted(async () => {
  try {

    //查看是否关注
    await axios.post('/following/hasFollowing',{
      upId:userId
    }).then(resp => {
      console.log('resp===>',resp)
      if(resp.data===true){
        following_value.value = "已关注"
        _disabled.value = true
      }
      if(resp.data===false){
        following_value.value = "关注"
        _disabled.value = false

      }
    })

  } catch (error) {
    console.error('请求失败:', error)
  }
})


const addFollowing=()=>{
  axios.post('/following/addFollowing',{ upId:userId }).then(resp=>{
    if (resp.code===200){
      following_value.value = "已关注"
      _disabled.value = true
    }
  })
}

const following_value = ref()
const _disabled = ref()
let userId = store.state.videoInfo.userId

</script>

<template>
  <a-space wrap>
    <a-button  @click="addFollowing" :disabled=_disabled type="primary">{{following_value}}</a-button>
  </a-space>
</template>

<style scoped>

</style>
