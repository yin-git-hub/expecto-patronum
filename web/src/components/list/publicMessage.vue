

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
              <a-sub-menu key="msg1">
                <template #title>
                    系统消息
                </template>
                <a-menu-item-group key="sys1">
                  <template #icon>
                  </template>
                  <a-menu-item key="sys11">
                    <div class="group-item">
                      <router-link style="color: black" to="/systemLikeMessage">
                      <span>收到的赞</span>
                      </router-link>
                    </div>
                  </a-menu-item>
                </a-menu-item-group>
                <a-menu-item-group key="sys2">
                  <template #icon>
                  </template>
                  <a-menu-item key="sys22">
                    <div class="group-item">
                      <router-link style="color: black" to="/systemUploadMessage">
                      <span>视频消息</span>
                      </router-link>
                    </div>
                  </a-menu-item>
                </a-menu-item-group>
                <a-menu-item-group key="sys3">
                  <template #icon>
                  </template>
                  <a-menu-item key="sys33">
                    <div class="group-item">
                      <router-link style="color: black" to="/systemReportMessage">
                      <span>其他消息</span>
                      </router-link>
                    </div>
                  </a-menu-item>
                </a-menu-item-group>
              </a-sub-menu>
              <a-sub-menu key="msg2">
                <template  #title>
                    <span  @click="getChatMsgPerson"  >私信</span>
                </template>
                <a-menu-item-group v-for="person in chatMsgPerson" :key="person.userId">
                  <a-menu-item
                      @click="toChatPage(person.userId)"
                      :key="person.userId">
                    <div  class="group-item">
                      <span>{{person.nickname}}</span>
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
      </a-layout-footer>
    </a-layout>
  </div>
</template>
<script setup>
import axios from "axios";
import {onMounted, ref} from "vue";
import router from "@/router";

const chatMsgPerson = ref()
const getChatMsgPerson = ()=>{
  axios.post("/chat/getChatMsgPerson").then(resp=>{
    console.log('/chat/getChatMsgPerson===',resp.data)
    if (resp.code===200){
      chatMsgPerson.value = resp.data
    }
  })
}

const toChatPage = (userId) =>{
  // eslint-disable-next-line no-unused-vars


  router.push({ path: '/myMessage', query: { userId: userId }})

}

onMounted(async ()=>{
  await  axios.post("/chat/getChatMsgPerson").then(resp=>{
    console.log('/chat/getChatMsgPerson===',resp.data)
    if (resp.code===200){
      chatMsgPerson.value = resp.data
    }
  })
})
</script>
<style scoped>
.main-div{
  box-shadow: 0px 0px 1px;
  min-height: 1000px;
  max-width: 1200px;
  margin: 0 auto;
}
</style>