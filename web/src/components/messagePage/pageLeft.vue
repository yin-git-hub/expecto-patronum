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
            <a-sub-menu key="sub1" @titleClick="titleClickFollowing">
              <template #title> <router-link to="/personal-following">
                我的关注
                <span>
              </span>
              </router-link></template>
              <a-menu-item-group key="g1">
                <template #icon>
                  <QqOutlined />
                </template>
                <a-menu-item
                    @click="groupClickFollowing(item.id)"
                    v-for="item in fList"
                    :key="item.id">
                  <div class="group-item">
                    <span>{{ item.groupName }}</span>

                    <a-popconfirm
                        title="Are you sure delete this task?"
                        ok-text="Yes"
                        cancel-text="No"
                        @confirm="confirmFollowing(item.id)"
                        @cancel="cancel"
                    >
                      <a-button type="link" class="delete-button">删除</a-button>
                    </a-popconfirm>
                  </div>
                </a-menu-item>
              </a-menu-item-group>
            </a-sub-menu>
            <a-sub-menu key="sub2" @titleClick="titleClickCollection">
              <template #title>
                我的收藏
                <span>
              </span>
              </template>
              <a-menu-item-group key="g1">
                <template #icon>
                  <QqOutlined />
                </template>
                <a-menu-item
                    @click="groupClickCollection(item.id)"
                    v-for="item in gList"
                    :key="item.id">
                  <div class="group-item">
                    <span>{{ item.groupName }}</span>
                    <a-popconfirm
                        title="Are you sure delete this task?"
                        ok-text="Yes"
                        cancel-text="No"
                        @confirm="confirmCollection(item.id)"
                        @cancel="cancel"
                    >
                      <a-button type="link" class="delete-button">删除</a-button>
                    </a-popconfirm>
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
</template>
<script setup>
import {defineComponent, onMounted, ref} from 'vue';
import router from "@/router";
import axios from "axios";
import {message} from "ant-design-vue";
const fList = ref([])
const gList = ref([])
const followingCount = ref()
onMounted(async ()=>{
  axios.post('/following/getFollowingCount').then(resp=>{
    if (resp.code===200){
      followingCount.value = resp.data
    }
  })
})
const confirmFollowing = id => {
  axios.post('/following/deleteFollowingGroup/'+id.slice(1,5)).then(resp=>{
    if(resp.code===200){
      message.success('删除成功');
      window.location.reload()
    }
  })
};
const confirmCollection = id => {
  axios.post('/collection/deleteCollectionGroup/'+id.slice(1,5)).then(resp=>{
    if(resp.code===200){
      message.success('删除成功');
      window.location.reload()
    }
  })
};

const cancel = e => {
  console.log(e);
};
const titleClickFollowing = ()=>{
  axios.post('/following/getFollowingGroup').then(resp=>{
    if (resp.code === 200) {
      fList.value = resp.data
      fList.value = fList.value.map(item => ({ ...item, id: 'f'+item.id }));
    }
  })
  router.push('/personal-following')
}

const titleClickCollection = ()=>{
  axios.post('/collection/getCollectionGroup').then(resp=>{
    if (resp.code === 200) {
      gList.value = resp.data
      gList.value = gList.value.map(item => ({ ...item, id: 'g'+item.id }));
    }
  })
  router.push('/personal-collection')
}

const groupClickFollowing=(_id)=>{
  router.push({ path: '/personal-following-group', query: { groupId: _id.slice(1, 5) } });
}

const groupClickCollection=(_id)=>{
  router.push({ path: '/personal-collection-group', query: { groupId: _id.slice(1, 5) } });
}

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
.group-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.delete-button {
  margin-left: auto;  /* 将按钮推到最右边 */
}
</style>
