<template>
  <a-layout>
    <a-layout-content >

      <a-layout style="padding: 24px 0; background: #fff">
        <a-layout-sider width="200" style="background: #fff">
          <a-menu
              mode="inline"
              style="height: 100%"
          >
            <a-menu-item key="1">
              <router-link to="/personal">
                个人信息
              </router-link>
            </a-menu-item>
            <a-menu-item key="2">
              <router-link to="/getSelfVideo">
                我的作品
              </router-link>
            </a-menu-item>
            <a-menu-item key="3">
              <router-link to="/getSelfVideo">
                我的关注
              </router-link>
            </a-menu-item>
            <a-menu-item key="4">
              <router-link to="/getSelfVideo">
                我的收藏
              </router-link>
            </a-menu-item>

          </a-menu>
        </a-layout-sider>
        <a-layout-content :style="{ padding: '0 24px', minHeight: '280px' }">
          <router-view></router-view>
        </a-layout-content>
      </a-layout>
    </a-layout-content>
    <a-layout-footer style="background: white;text-align: center">
      Ant Design ©2018 Created by Ant UED
    </a-layout-footer>
  </a-layout>
</template>
<script setup>
import { defineComponent  } from 'vue';
defineComponent({

});
</script>
<style scoped>
#components-layout-demo-top-side .logo {
  float: left;
  width: 120px;
  height: 31px;
  margin: 16px 24px 16px 0;
  background: white;
}

.ant-row-rtl #components-layout-demo-top-side .logo {
  float: right;
  margin: 16px 0 16px 24px;
}

.site-layout-background {
  background: white;
}
</style>
