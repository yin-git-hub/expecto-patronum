<template>
  <a-form :model="formState" :label-col="labelCol" :wrapper-col="wrapperCol">
    <a-form-item label="标题：">
      <a-input v-model:value="store.state.videoInfo.filename" />
    </a-form-item>
    <a-form-item label="封面：">
      <the-picture-upload></the-picture-upload>
    </a-form-item>

    <a-form-item label="简介：">
      <a-textarea v-model:value="formState.summaryValue" showCount :maxlength="100" />
    </a-form-item>

  </a-form>

</template>
<script>
import {defineComponent, reactive, toRaw} from 'vue';
import ThePictureUpload from "@/components/upload/picture.vue";
import store from "@/store";
import video from "./video.vue";


export default defineComponent({
  name:"the-video-info-input",
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
    const videoInfo = store.state.videoInfo

    console.log('store===>',videoInfo)
    const formState = reactive({
      title:videoInfo.filename,
      summaryValue:'',
      size:videoInfo.size,
      md5:videoInfo.md5,
    });
    const onSubmit = () => {
      console.log('submit!', toRaw(formState));
    };
    return {
      labelCol: {
        span: 4,
      },
      wrapperCol: {
        span: 14,
      },
      formState,
      onSubmit,
    };
  },
});
</script>
