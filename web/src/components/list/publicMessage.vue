<script setup>

</script>

<template>
  <div class="main-div">
    <a-layout>
      <a-layout-content>
        <a-layout style="padding: 24px 0; background: #fff">
          <a-layout-sider width="200" style="background: #fff">
            <a-menu
                mode="inline"
                style="height: 100%"
            >
              <a-menu-item key="1">
                <router-link to="/systemMessage">
                  系统消息
                </router-link>
              </a-menu-item>
              <a-sub-menu key="videoList1">
                <template #title>
                  <router-link style="color: black" to="/myMessage">
                    私信
                  </router-link>
                </template>
                <a-menu-item-group key="v1">
                  <template #icon>
                  </template>
                  <a-menu-item
                      key="vv1">
                    <div class="group-item">
                      <span>未审核</span>
                    </div>
                  </a-menu-item>
                </a-menu-item-group>
              </a-sub-menu>

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

  </div>
</template>

<style scoped>
.main-div{
  box-shadow: 0px 0px 1px;
  min-height: 1000px;
  max-width: 1200px;
  margin: 0 auto;
}
</style>