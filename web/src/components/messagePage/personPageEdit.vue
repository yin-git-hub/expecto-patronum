<template>
  <div class="container">
    <a-form :model="formState" :label-col="labelCol" :wrapper-col="wrapperCol">
    <div class="avatar-container">
      <!-- 这里插入头像和其他相关内容 -->
      <div class="image-avatar">
        <a-form-item label="原有头像">
          <a-avatar  :size="64" :src="formState.avatarValue" />
        </a-form-item>
        <a-form-item label="修改头像">
          <the-user-picture-upload></the-user-picture-upload>
        </a-form-item>
      </div>
      <div class="username">
        <a-form-item label="昵称">
          <a-input v-model:value="formState.nickname" />
        </a-form-item>
      </div>
      <div class="signature">
        <a-form-item label="个性签名">
          <a-input v-model:value="formState.signature" />
        </a-form-item>
      </div>
    </div>
    </a-form>
    <a-button @click="upload" type="primary" style="margin-left: 650px">
        提交
    </a-button>
  </div>
</template>

<script setup>
import {defineComponent, onMounted, reactive, ref} from 'vue';
import axios from "axios";
import TheUserPictureUpload from "@/components/upload/pictureUser.vue";
import {message} from "ant-design-vue";
import store from "@/store";


onMounted(async () => {
  try {
    await axios.post('/user/getUserInfo').then(resp => {
      if(resp.code===200){
        console.log('resp.data===>',resp.data)
        formState.nickname = resp.data.nickname
        formState.signature = resp.data.signature
        formState.avatarValue = resp.data.image

      }
    })
  } catch (error) {
    console.error('请求失败:', error)
  }
})

const upload = ()=>{
  axios.post('/user/userInfoPersonal',{
    signature:formState.signature,
    nickname:formState.nickname
  }).then(resp=>{
    if (resp.code===200){
      message.success('更新成功',5)
      store.commit("setUserInfo",{
        ...store.state.userInfo,
        nickname:formState.nickname,
        signature:formState.signature,

      })

    }
  })
}

const formState = reactive({
  nickname: '',
  signature:'',
  region: undefined,
  date1: undefined,
  delivery: false,
  type: [],
  resource: '',
  desc: '',
});

const labelCol=ref({
  span: 4,
})
const wrapperCol=ref({
  span: 14,
})
defineComponent({
});
</script>

<style scoped>

</style>
