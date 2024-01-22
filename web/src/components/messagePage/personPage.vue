<template>
  <div class="container">
    <a-form :model="formState" :label-col="labelCol" :wrapper-col="wrapperCol">
      <div class="avatar-container">
        <!-- 这里插入头像和其他相关内容 -->
        <div class="image-avatar">
          <a-form-item label="头像">
            <a-avatar :size="64" :src="formState.avatarValue"/>
          </a-form-item>
        </div>
        <div class="username">
          <a-form-item label="昵称">
            {{formState.nickname}}
          </a-form-item>
        </div>
        <div class="signature">
          <a-form-item label="个性签名">
            {{formState.signature}}
          </a-form-item>
        </div>
      </div>
    </a-form>
    <a type="link" style="margin-left: 800px">
      <router-link to="/personal-edit">
        修改
      </router-link>
    </a>
  </div>

</template>

<script setup>
import {defineComponent, onMounted, reactive, ref} from 'vue';
import axios from "axios";
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
const formState = reactive({
  nickname:'',
  signature:'',
  avatarValue:'',
  name: '',
  region: undefined,
  date1: undefined,
  delivery: false,
  type: [],
  resource: '',
  desc: '',
});

const labelCol = ref({
  span: 4,
})
const wrapperCol = ref({
  span: 14,
})
defineComponent({});
</script>

<style scoped>

</style>
