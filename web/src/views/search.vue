<template>
  <div>

    <br />
    <a-input-search
        v-model:value="value"
        placeholder="input search text"
        enter-button="Search"
        size="large"
        @search="onSearch"
    />
    <br />
    <my-divider></my-divider>
  </div>

  <a-tabs v-model:activeKey="activeKey" @change="onTabChange">
    <a-tab-pane key="1" tab="视频">   <a-empty /> </a-tab-pane>
    <a-tab-pane key="2" tab="文章" force-render>
      <artical-list></artical-list>
    </a-tab-pane>
    <a-tab-pane key="3" tab="用户">
      <user-info-list :user-list-data="userInfoValue"></user-info-list>
        </a-tab-pane>
  </a-tabs>
</template>
<script>
import {defineComponent, ref } from 'vue';
import MyDivider from "@/components/MyDivider.vue";
import ArticalList from "@/components/ArticalList.vue";
import {  useRouter} from "vue-router";
import UserInfoList from "@/components/UserInfoList.vue";
import axios from "axios";
import {notification} from "ant-design-vue";



 export default defineComponent({
  name: "search-view",


  components: {UserInfoList, ArticalList, MyDivider},

   setup: function () {
     const router = useRouter();

     const userInfoValue = ref("")


     const value = ref( router.currentRoute.value.query.text||'')
     const activeKey = ref(router.currentRoute.value.params.select||1)

      const pageIndex = 1;
      const pageSize = 20;
      const area = 1;

     const onSearch = searchValue => {

       router.push({
         query:{
           text: value.value,
         },
       })
       axios.get(`/video/getVideoList/${pageIndex}/${pageSize}/${area}`,{})
       .then(response => {

         console.log(response)

         if (response.code===200) {
           userInfoValue.value = response.data;
           notification.success({ description: '发送验证码成功！' });

         } else {
           notification.error({ description: response.data });
         }
       });

       console.log('use value', searchValue);
       console.log('or use this.value', value.value);
     };

     const onTabChange = tabKey =>{

       router.push({
         path: "/search/".concat(tabKey),
         query:{
           text: value.value,
         },
       })

     }
     return {
       value ,
       onSearch,
       activeKey,
       onTabChange,
       userInfoValue,
     };

   },
});
</script>
