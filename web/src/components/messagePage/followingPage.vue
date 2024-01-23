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
        <div>
          <a-button   type="link"
                      @click="showModal(item.userId)">设置分组</a-button>
          <a-modal  v-model:visible="visible" title="关注分组" @ok="handleOk(item.userId)">
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

          </a-modal>

        </div>
        <a-button @click="deleteFollowing(item.userId)" type="link">取消关注</a-button>
      </a-list-item>
    </template>
  </a-list>

</template>
<script setup>
import {defineComponent, onMounted, ref } from 'vue';

import axios from "axios";
const visible = ref(false)

const data = ref([])
import store from "@/store";
const plainOptions = ref([]);
const optionValue=ref();
const checkedList=ref([]);
const _resp = {}

const showModal = (upId) => {
  axios.post('/following/getChoosedGroups',{
    upId:upId
  }).then(resp=>{
    if(resp.code===200){
      _resp.value.map(v=>{
        if(resp.data.includes(v.id)){
          checkedList.value.push(v.groupName)
        }
      })

    }
  })
  visible.value = true;
};

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

const handleOk = upId => {
  axios.post('/following/addFollowingToGroup',{
    upId:upId,
    groupIds:store.state.followingGroupList
  })

  visible.value = false;

};
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
const deleteFollowing=(upId)=>{
  axios.post('/following/deleteFollowing/'+upId).then(resp=>{
    if(resp.code===200){
      window.location.reload();
    }
  })
}

defineComponent({

});
</script>
<style scoped>
.vertical-checkbox-group   {
  /*  上下排列*/
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
</style>
