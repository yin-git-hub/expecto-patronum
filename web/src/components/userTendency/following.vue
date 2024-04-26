<script setup>
import FollowingGroup from "@/components/following/followingGroup.vue";

defineComponent({
  name: "the-following"
})
import {defineComponent, onMounted, ref} from 'vue'
import axios from "axios";
import store from "@/store";

const following_value = ref()
const _disabled = ref()
let userId = store.state.videoInfo.userId

onMounted(async () => {
  try {
    let userId = store.state.videoInfo.userId
    let followUserId = store.state.userId
    if (userId===null||userId===""){
      userId = followUserId
    }
    console.log('userId===',userId)
    console.log('followUserId===',followUserId)
    //查看是否关注
    await axios.post('/following/hasFollowing',{
      upId:userId||store.state.userId
    }).then(resp => {
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

const deleteFollowing=()=>{
  axios.post('/following/deleteFollowing/'+userId,{ upId:userId }).then(resp=>{
    if(resp.code===200){

      following_value.value = "关注"
      _disabled.value = false
    }
  })
}

</script>

<template>
  <a-space v-if="!_disabled" wrap>
    <a-button @click="addFollowing" :disabled="_disabled" type="primary">
      {{ following_value }}
    </a-button>
  </a-space>

  <a-dropdown v-else>
    <template #overlay>
      <a-menu @click="handleMenuClick">
        <a-menu-item key="1"><following-group></following-group></a-menu-item>
        <a-menu-item key="2"><a-button @click="deleteFollowing" type="link">取消关注</a-button></a-menu-item>
      </a-menu>
    </template>
    <a-button>
      {{ following_value }}
      <DownOutlined />
    </a-button>
  </a-dropdown>
</template>

<style scoped>

</style>
