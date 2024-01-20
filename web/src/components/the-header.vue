<template>
  <a-layout-header style="color: white" class="header">
    <div class="logo">
      <router-link to="/welcome" style="color: white; font-size: 18px">
        expecto-patronum
      </router-link>
    </div>
    <div style="float: right; color: white;">
      您好：{{ userInfo.nickname }}
      <router-link to="/login" style="color: white;">
        退出登录
      </router-link>
    </div>
    <a-menu
        v-model:selectedKeys="selectedKeys"
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="/welcome">
        <router-link to="/welcome">
            欢迎
        </router-link>
      </a-menu-item>
      <a-menu-item key="/search">
        <router-link to="/search/s">
          搜索
        </router-link>
      </a-menu-item>
      <a-menu-item key="/create">
        <router-link to="/create">
          创作
        </router-link>
      </a-menu-item>
      <a-menu-item key="/myPage">
        <router-link to="/myPage">
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

    watch(() => router.currentRoute.value.path, (newValue) => {
      console.log('watch', newValue);
      selectedKeys.value = [];
      selectedKeys.value.push(newValue);
    }, {immediate: true});
    return {
      userInfo,
      selectedKeys
    };
  },
});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.logo {
  float: left;
  height: 31px;
  width: 150px;
  color: white;
  font-size: 20px;
}

</style>
