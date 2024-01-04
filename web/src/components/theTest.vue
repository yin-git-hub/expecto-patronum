<template>
<!--  npm install vue-cropperjs   -->
  <div>
    <!-- 图片上传 -->
    <input type="file" @change="onFileChange" accept="image/*" />

    <!-- 图片裁剪 -->
    <div v-if="imageSrc">
      <vue-cropper
          ref="cropper"
          :src="imageSrc"
          :aspect-ratio="9 / 6"
          :guides="true"
          style="width: 100%;"
      />
      <button @click="cropImage">裁剪图片</button>
    </div>

    <!-- 上传按钮 -->
    <button v-if="croppedImage" @click="uploadImage">上传图片</button>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import VueCropper from 'vue-cropperjs';
import 'cropperjs/dist/cropper.css';
import axios from "axios";

const imageSrc = ref(null);
const croppedImage = ref(null);
const cropper = ref(null);

const onFileChange = (e) => {
  const files = e.target.files || e.dataTransfer.files;
  if (!files.length) return;

  const reader = new FileReader();
  reader.onload = (e) => {
    imageSrc.value = e.target.result;
  };
  reader.readAsDataURL(files[0]);
};

const cropImage = () => {
  if (cropper.value) {
    // 获取裁剪后的图片
    croppedImage.value = cropper.value.getCroppedCanvas().toDataURL();
  }
};

const uploadImage = () => {
  // 上传逻辑
  // 创建一个 FormData 对象来包含文件数据
  let formData = new FormData();
  // 将裁剪后的图片添加到 FormData 对象
  // 由于 croppedImage.value 是 base64 编码的字符串，需要先转换为 Blob

  formData.append('file',  croppedImage.value);
  // formData.append('file', dataURLtoBlob(croppedImage.value));

  // 发送 POST 请求到服务器
  axios.post('/q/w', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });

  // TODO: 将 croppedImage.value 发送到服务器
};
</script>

<style scoped>
/* 你可以在这里添加一些自定义样式 */
</style>
