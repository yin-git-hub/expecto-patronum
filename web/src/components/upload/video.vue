<template>
  <a-upload-dragger
      v-model:fileList="fileList"
      name="file"
      :multiple="true"
      action="actionUrl"
      :chunk-size="chunkSize"
      :custom-request="customRequest"
      @change="handleChange"
  >
    <p class="ant-upload-drag-icon">
      <inbox-outlined></inbox-outlined>
    </p>
    <p class="ant-upload-text">Click or drag file to this area to upload</p>
    <p class="ant-upload-hint">
      Support for a single or bulk upload. Strictly prohibit from uploading company data or other
      band files
    </p>
  </a-upload-dragger>

</template>
<script>
import {InboxOutlined} from '@ant-design/icons-vue';
import {message} from 'ant-design-vue';
import {defineComponent, ref} from 'vue';
import axios from "axios";
import store from "@/store";

export default defineComponent({
  name: "the-video-upload",
  components: {
    InboxOutlined,
  },
  setup() {
    const actionUrl = "/video/upload"
    const fileVideo = ref()
    const chunkSize = ref(5 * 1024 * 1024)
    const fileName = ref('')
    const fileSize = ref('')
    const fileType = ref('')
    const videoMD5 = ref('')
    const videoInfo = ''
    const handleChange = info => {
      const status = info.file.status;
      fileName.value = info.file.name
      fileSize.value = info.file.size
      fileType.value = info.file.type
      if (status !== 'uploading') {
        console.log(1111)
        console.log("aaaa")

      }
      if (status === 'done') {
        console.log(2222)
        console.log('bbbb')
        message.success(`${info.file.name} file uploaded successfully.`);
      } else if (status === 'error') {
        message.error(`${info.file.name} file upload failed.`);
      }
    };

    const customRequest = options => {
      // 获取文件对象
      fileVideo.value = options.file;

      console.log('options===>', fileVideo.value)
      // 获取文件名
      const filename = fileVideo.value.name;
      // 获取文件总大小
      const totalSize = fileVideo.value.size;
      // 计算分片总数
      const total = Math.ceil(totalSize / chunkSize.value);
      // 定义一个数组，用来存储分片的索引
      const chunks = [];
      // 定义一个变量，用来记录已上传的分片数
      let uploaded = 0;

      calcFileMD5(fileVideo.value).then(res => {
        videoMD5.value = res
        //父传子
        const videoInfo = {
          "filename": filename,
          "totalSize": totalSize,
          "md5": videoMD5.value,
          "total":total,
        }

        store.commit("setVideoInfo",videoInfo)

        // 遍历所有分片，将分片的索引添加到数组中
        for (let i = 0; i < total; i++) {
          chunks.push(i);
        }
        // 从数组中取出一个分片的索引，开始发送
        const firstIndex = chunks.shift();
        sendChunk(firstIndex);
        console.log('md5值:', res)
      });

      const sendChunk = index => {
        // 创建一个表单对象，用来存储分片数据
        const formData = new FormData();
        // 获取分片的起始位置
        const start = index * chunkSize.value;
        // 获取分片的结束位置
        const end = Math.min(start + chunkSize.value, totalSize);
        // 获取分片的内容
        const chunk = fileVideo.value.slice(start, end);
        // 将分片的文件名、大小、总数、索引、内容添加到表单中
        formData.append("filename", filename);
        formData.append("totalSize", totalSize);
        formData.append("total", total);
        formData.append("index", index);
        formData.append("file", chunk);
        formData.append("md5", videoMD5.value)
        // 创建一个 axios 实例，用来发送请求
        const axiosInstance = axios.create();
        // 发送请求
        axiosInstance
            .post(actionUrl, formData)
            .then(response => {
              // 请求成功，更新上传进度
              uploaded++;
              const percent = (uploaded / total) * 100;
              options.onProgress({percent});
              // 判断是否还有未上传的分片
              if (chunks.length > 0) {
                // 从数组中取出一个分片的索引，继续发送
                const nextIndex = chunks.shift();
                sendChunk(nextIndex);
              } else {
                // 所有分片都已上传，调用成功的回调函数
                options.onSuccess(response.data);
              }
            })
            .catch(error => {
              // 请求失败，调用失败的回调函数
              options.onError(error);
            });
      };

    };

    //导入计算文件MD5
    const calcFileMD5 = (file) =>{
      let CryptoJS = require("crypto-js");
      return new Promise(resolve => {
        const fileReader = new FileReader();
        fileReader.onloadend = ev => {
          resolve(
              CryptoJS.MD5(CryptoJS.enc.Latin1.parse(ev.target.result)).toString(
                  CryptoJS.enc.Hex
              )
          );
        };
        fileReader.readAsBinaryString(file);
      });
    }
    return {
      handleChange,
      customRequest,
      actionUrl,
      chunkSize,
      videoInfo,
      fileList: ref([]),
    };
  },
});
</script>
