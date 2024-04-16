

<template>

    <a-button
        style="float: right;margin-right: 460px"
        type="primary" @click="showModal">举报</a-button>
    <a-modal v-model:visible="visible" title="举报留言" @ok="handleOk">


      <a-textarea v-model:value="reportContent" placeholder="请输入举报内容" allow-clear />


    </a-modal>

</template>
<script setup>
import {ref} from "vue";
import axios from "axios";
import store from "@/store";
import {message} from "ant-design-vue";

const visible = ref(false);
const reportContent=ref();
const showModal = () => {
  visible.value = true;
};
const handleOk = () => {
  console.log("reportContent===",reportContent.value)
  axios.post("/report/addVideoReport",{
    videoId:store.state.videoInfo.videoId,
    reportMessage:reportContent.value,
  }).then(resp=>{
    console.log("handleOkreportContent===",resp)
    if (resp.code === 200) {
      message.success("举报成功")
    }else if(resp.code===50001){
      message.warn(resp.message)
    }else {
      message.error("操作失败")
    }
  })
  visible.value = false;
};
</script>
<style scoped>

</style>