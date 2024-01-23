<template>
  <a-list item-layout="horizontal" :data-source="data">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-list-item-meta
            :description="item.signature"
        >
          <template #title>
            <a href="https://www.antdv.com/">{{ item.nickname }}</a>
          </template>
          <template #avatar>
            <a-avatar :src="item.image" />
          </template>
        </a-list-item-meta>
      </a-list-item>
    </template>
  </a-list>
</template>
<script setup>
import {defineComponent, onMounted, ref} from 'vue';
import axios from "axios";

onMounted(async ()=>{
  try {
    axios.get('/following/getFollowings').then(resp=>{
      if(resp.code===200){
        data.value = resp.data
      }
    })
  }catch (e){
    console.log(e)
  }

})
const data = ref([])
 defineComponent({

});
</script>
