<template>
  <a-upload
      v-model:file-list="fileList"
      name="avatar"
      list-type="picture-card"
      class="avatar-uploader"
      :show-upload-list="false"
      action="actionUrl"
      :before-upload="beforeUpload"
      :custom-request="customRequest"
      @change="handleChange"
  >
   <img v-if="imageUrl" :src="imageUrl" alt="avatar" style="width: 100px; height: 100px;" />
    <div v-else>
      <loading-outlined v-if="loading"></loading-outlined>
      <plus-outlined v-else></plus-outlined>
      <div class="ant-upload-text">Upload</div>
    </div>
  </a-upload>
</template>
<script>
import { PlusOutlined, LoadingOutlined } from '@ant-design/icons-vue';
import { message } from 'ant-design-vue';
import { defineComponent, ref } from 'vue';
import axios from "axios";
function getBase64(img, callback) {
  const reader = new FileReader();
  reader.addEventListener('load', () => callback(reader.result));
  reader.readAsDataURL(img);
}
export default defineComponent({
  name:"the-user-picture-upload",
  components: {
    LoadingOutlined,
    PlusOutlined,
  },
  setup() {
    const actionUrl = '/user/picture/upload'
    const fileList = ref([]);
    const loading = ref(false);
    const imageUrl = ref('');
    const handleChange = info => {
      if (info.file.status === 'uploading') {
        loading.value = true;
        return;
      }
      if (info.file.status === 'done') {
        // Get this url from response in real world.
        getBase64(info.file.originFileObj, base64Url => {
          imageUrl.value = base64Url;
          loading.value = false;
        });
      }
      if (info.file.status === 'error') {
        loading.value = false;
        message.error('upload error');
      }
    };
    const beforeUpload = file => {

      const isLt2M = file.size / 1024 / 1024 < 10;
      if (!isLt2M) {
        message.error('Image must smaller than 10MB!');
      }
      return   isLt2M;
    };
    const customRequest = file=>{
      console.log('file===>',file.file.name)
      // 后缀 suffix
      const suffix =  file.file.name.toString().split('.')[1]
      const formData = new FormData();
      formData.append("picture",file.file)
      formData.append("suffix",suffix)

      axios.post(actionUrl,formData).then(resp=>{
        console.log(resp)
        file.onSuccess(resp.data)
      })
    }
    return {
      fileList,
      loading,
      imageUrl,
      actionUrl,
      handleChange,
      customRequest,
      beforeUpload,
    };
  },
});
</script>
<style>
.avatar-uploader > .ant-upload {
  width: 128px;
  height: 128px;
}
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>
