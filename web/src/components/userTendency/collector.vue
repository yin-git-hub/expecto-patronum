<script setup>
import CollectionGroup from "@/components/collection/collectionGroup.vue";

defineComponent({
  name: "the-collection"
})
import {defineComponent, onMounted, ref} from 'vue'
import axios from "axios";
import store from "@/store";

onMounted(async () => {
  try {

    //查看是否收藏
    await axios.post('/collection/hasCollection',{
      videoId:videoId
    }).then(resp => {
      if(resp.data===true){
        collection_value.value = "已收藏"
        _disabled.value = true
      }
      if(resp.data===false){
        collection_value.value = "收藏"
        _disabled.value = false

      }
    })

  } catch (error) {
    console.error('请求失败:', error)
  }
})


const addCollection=()=>{
  axios.post('/collection/addCollection',{ videoId:videoId }).then(resp=>{
    if (resp.code===200){
      collection_value.value = "已收藏"
      _disabled.value = true
    }
  })
}

const deleteCollection=()=>{
  axios.post('/collection/deleteCollection/'+videoId,{ videoId:videoId }).then(resp=>{
    if(resp.code===200){

      collection_value.value = "收藏"
      _disabled.value = false
    }
  })
}

const collection_value = ref()
const _disabled = ref()
let videoId = store.state.videoInfo.videoId

</script>

<template>
  <a-space v-if="!_disabled" wrap>
    <a-button @click="addCollection" :disabled="_disabled" type="primary">
      {{ collection_value }}
    </a-button>
  </a-space>

  <a-dropdown v-else>
    <template #overlay>
      <a-menu @click="handleMenuClick">
        <a-menu-item key="1"><collection-group></collection-group></a-menu-item>
        <a-menu-item key="2"><a-button @click="deleteCollection" type="link">取消收藏</a-button></a-menu-item>
      </a-menu>
    </template>
    <a-button>
      {{ collection_value }}
      <DownOutlined />
    </a-button>
  </a-dropdown>
</template>

<style scoped>

</style>
