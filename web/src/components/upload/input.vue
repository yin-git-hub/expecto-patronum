<template>
  <a-form :model="formState" :label-col="labelCol" :wrapper-col="wrapperCol">
    <a-form-item label="标题：">
      <a-input v-model:value="store.state.videoInfo.filename"/>
    </a-form-item>
    <a-form-item label="标签：">
      <a-dropdown>
        <a-select
            ref="select"
            v-model:value="value1"
            style="width: 120px"
            @focus="focus"
            @change="handleChange"
        >
          <a-select-option v-for="item in labelsData" :key="item.id">{{item.labelName}}</a-select-option>
        </a-select>
      </a-dropdown>
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
import {defineComponent, reactive, ref} from 'vue';
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
  components: {ThePictureUpload },


  setup() {
    const labelsData=ref("");



    const focus = () => {
      axios.post("/label/getVideoLabel").then(resp=>{
        if (resp.code===200){
          labelsData.value = resp.data;
          console.log("labelsData.value===",labelsData.value)
        }
      })
    };
    const handleChange = value => {
      formState.worksLabelId = value
      console.log(`selected ${value}`);
    };

    const formState = reactive({
      title: '',
      summaryValue: '',
      worksLabelId:'',
      size: store.state.videoInfo.totalSize,
      md5: store.state.videoInfo.md5,
    });

    const uploadinfo = () => {
      const map = {
        "videoSummary": formState.summaryValue,
        "worksLabelId": formState.worksLabelId,
        "videoName": store.state.videoInfo.filename,
        "videoMd5": store.state.videoInfo.md5,

      }
      console.log(map)

      axios.post("/video/updateVideoInfo", map)
          .then(resp => {
            console.log(resp)
            if (resp.code === 200) {
              notification.success({description: '上传成功'});
            }else {
              notification.warn({description: resp.message})
            }
          })

    }


    return {
      labelsData,
      handleChange,focus,
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
