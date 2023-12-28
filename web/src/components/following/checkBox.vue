<template>

  <a-checkbox-group
      v-model:value="checkedList"
      :options="plainOptions"
      class="vertical-checkbox-group"
      @change="change"
  />
  <p><a-divider/>
    <a-input-search
      v-model:value="optionValue"
      placeholder="input search text"
      enter-button="添加分组"
      size="large"
      @search="onSearch"
  /></p>
</template>
<script setup>
import {onMounted,  ref } from 'vue';
import axios from "axios";
import store from "@/store";
const plainOptions = ref([]);
const optionValue=ref();
const checkedList=ref([]);
const _resp = {}

const change=(e)=>{
  const temp=[]
  _resp.value.map(item => {
    if(e.includes(item.groupName)){
      temp.push(
       item.id  )
    }
  });

  store.commit("setFollowingGroupList",temp)
}
onMounted(async () => {
  try {

    //查看是否关注
    await axios.post('/following/getFollowingGroup').then(resp=>{
      if(resp.code===200){
        _resp.value = resp.data
         resp.data.map(item => {
           plainOptions.value.push( item.groupName )
         });
      }
    })

  } catch (error) {
    console.error('请求失败:', error)
  }
})
onMounted(async () => {
  try {

    //选中已有的选项
    await axios.post('/following/getChoosedGroups',{
      upId:store.state.videoInfo.userId
    }).then(resp=>{
      if(resp.code===200){
        _resp.value.map(v=>{
          if(resp.data.includes(v.id)){
            checkedList.value.push(v.groupName)
            console.log('checkedList1===>',checkedList.value)
          }
        })

      }
    })

  } catch (error) {
    console.error('请求失败:', error)
  }
})

// 添加分组
const onSearch=()=>{
  axios.post('/following/addFollowingGroup',{
    groupName:optionValue.value
  }).then(resp=>{
    if (resp.code === 200) {
      plainOptions.value.push(optionValue.value)
      optionValue.value=''
      // 刷新分组
      axios.post('/following/getFollowingGroup').then(resp=>{
        if(resp.code===200){
          plainOptions.value = []
          _resp.value = resp.data
          resp.data.map(item => {
            plainOptions.value.push( item.groupName )
          });
        }
      })
    }
  })

}

</script>
<style scoped>
.vertical-checkbox-group   {
/*  上下排列*/
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
</style>
