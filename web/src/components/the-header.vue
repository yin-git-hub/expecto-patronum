<template>
  <a-layout-header style="background: white" class="header">
    <div class="logo">
      <router-link to="/welcome" style="color: black; font-size: 18px">
        expecto-patronum
      </router-link>
    </div>
    <div style="float: right; color: black;">
      <span @mouseover="onAllMessage" id="all-message">
        <router-link  style="margin-right: 20px; color: black"  to="/publicMessage">
          消息
        </router-link>
      </span>
      您好：{{ userInfo.nickname }}
      <router-link to="/login" style="color: black;">
        退出登录
      </router-link>
    </div>
    <a-menu
        v-model:selectedKeys="selectedKeys"
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
        style="background: white"
    >
      <a-menu-item key="/welcome">
        <router-link  style="color: black" to="/welcome">
            欢迎
        </router-link>
      </a-menu-item>
      <a-menu-item key="/first-page">
        <router-link  style="color: black"  to="/first">
          首页
        </router-link>
      </a-menu-item>
      <a-menu-item key="/search">
        <router-link  style="color: black"  to="/search/s">
          搜索
        </router-link>
      </a-menu-item>
      <a-menu-item key="/create">
        <router-link  style="color: black"  to="/create">
          创作
        </router-link>
      </a-menu-item>
      <a-menu-item key="/myPage">
        <router-link  style="color: black"  to="/myPage">
          我的空间
        </router-link>
      </a-menu-item>
    </a-menu>
  </a-layout-header>

</template>

<script>
import {defineComponent, ref, watch} from 'vue';
import store from "@/store";
import router from '@/router'

export default defineComponent({
  name: "the-header-view",
  setup() {
    let userInfo = store.state.userInfo;
    const selectedKeys = ref([]);
    const msgFlag = ref("888")
    const onAllMessage = ()=>{
      console.log("111")
    }
    watch(() => router.currentRoute.value.path, (newValue) => {
      console.log('watch', newValue);
      selectedKeys.value = [];
      selectedKeys.value.push(newValue);
    }, {immediate: true});
    return {
      msgFlag,
      userInfo,
      onAllMessage,
      selectedKeys
    };
  },
});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.msgSummary{
  float: right;
}
.msgSummaryButton{
  display: block;
}
.logo {
  float: left;
  height: 31px;
  width: 150px;
  color: black;
  font-size: 20px;
}

</style>
