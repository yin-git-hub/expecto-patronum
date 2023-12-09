<template>
  <a-form :model="formState" :label-col="labelCol" :wrapper-col="wrapperCol">
    <a-form-item label="标题：">
      <a-input v-model:value="store.state.videoInfo.filename"/>
    </a-form-item>
    <a-form-item label="封面：">
      <the-picture-upload></the-picture-upload>
    </a-form-item>

    <a-form-item label="简介：">
      <a-textarea v-model:value="formState.summaryValue" showCount :maxlength="100"/>
    </a-form-item>
    <a-space style="position: relative; bottom: 0;margin-left: 60%;">
      <a-button type="primary" @click="uploadinfo">上传</a-button>
    </a-space>

  </a-form>

</template>
<script>
import {defineComponent, reactive } from 'vue';
import ThePictureUpload from "@/components/upload/picture.vue";
import store from "@/store";
import video from "./video.vue";
import axios from "axios";
import {notification} from "ant-design-vue";


export default defineComponent({
  name: "the-video-info-input",
  computed: {
    store() {
      return store
    },
    video() {
      return video
    },

  },
  components: {ThePictureUpload},
  setup() {
    const formState = reactive({
      title: '',
      summaryValue: '',
      size: store.state.videoInfo.totalSize,
      md5: store.state.videoInfo.md5,
    });

    const uploadinfo = () => {
      const map = {
        "videoSummary": formState.summaryValue,
        "videoName": store.state.videoInfo.filename,
        "videoMd5": store.state.videoInfo.md5
      }
      console.log(map)

      axios.post("/video/updateVideoInfo", map)
          .then(resp => {
            console.log(resp)
            notification.success({description: '上传成功'});
          })

    }


    return {
      labelCol: {
        span: 4,
      },
      wrapperCol: {
        span: 14,
      },
      formState,
      uploadinfo,
    };
  },
});
</script>
