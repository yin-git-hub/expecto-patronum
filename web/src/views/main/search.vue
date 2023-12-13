<template>
  <div>

    <br/>
    <a-input-search
        v-model:value="value"
        placeholder="input search text"
        enter-button="Search"
        size="large"
        @search="onSearch"
    />
    <br/>
    <my-divider></my-divider>
  </div>

  <a-tabs v-model:activeKey="activeKey" @change="onTabChange">

    <a-tab-pane key="video" tab="视频">
      <video-info-list :user-list-data="videoInfoValue" :type="tagKey.value" :content="value.value"></video-info-list>

    </a-tab-pane>
    <a-tab-pane key="artical" tab="文章" force-render>
      <artical-list></artical-list>
    </a-tab-pane>
    <a-tab-pane key="user" tab="用户">
      <user-info-list :user-list-data="userInfoValue"></user-info-list>
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
import {defineComponent, ref, watch} from 'vue';
import MyDivider from "@/components/MyDivider.vue";
import ArticalList from "@/components/ArticalList.vue";
import UserInfoList from "@/components/UserInfoList.vue";
import axios from "axios";
import router from "@/router";
import VideoInfoList from "@/components/VideoInfoList.vue";

const urlValue = router.currentRoute._rawValue.query.text
const urlTagKey = router.currentRoute._rawValue.query.tagKey
const value = ref(urlValue || '')
const userInfoValue = ref('')
const videoInfoValue = ref('')
const tagKey = ref(urlTagKey || 'video')
const activeKey = tagKey
// 共查询出多少语句
const totalElements = ref()
//当前页总数据
const pageSize = ref(10);
//当前页
const current  = ref(1);
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
  console.log('page, pageSize===>',page, pageSize)

  const urlValue = router.currentRoute._rawValue.query.text
  const urlTagKey = router.currentRoute._rawValue.query.tagKey
  console.log('urlValue', urlValue)
  console.log('urlTagKey', urlTagKey)
  axios.post(`/search/all`, {
    "type": urlTagKey,
    "content": urlValue,
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
  tagKey.value = key
  router.push({
    query: {
      tagKey: tagKey.value,
      text: value.value,
    },
  })
}


const onSearch = searchValue => {
  // 将搜索框的值写到网址上
  router.push({
    query: {
      tagKey: tagKey.value,
      text: value.value,
    },
  })
  console.log(searchValue)
  axios.post(`/search/all`, {
    "type": tagKey.value,
    "content": value.value,
    "pageIndex": 1,
    "pageSize": 10,
    "total": 0,
    "valList": []
  })
      .then(response => {

        console.log('response===》', response)
        videoInfoValue.value = response.data

        totalElements.value = response.data.totalElements
      });

};

defineComponent({
  name: "search-view",
});
</script>
