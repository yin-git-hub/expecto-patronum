<template>
  <div>
    <!--    comment  input  -->
    <a-divider></a-divider>
    <div  class="comment-input-class-show" >
      <div style="font-weight: bold; font-size: 16px;">
        评论
      </div>
      <a-comment>
        <template #avatar>
          <a-avatar
              :src=store.state.userInfo.image
          />
        </template>
        <template #content>
          <a-form-item>
            <a-textarea :rows="1" v-model:value="value" />
          </a-form-item>
          <a-form-item>
            <a-button html-type="submit" :loading="submitting" type="primary" @click="handleSubmit">
              发送
            </a-button>
          </a-form-item>
        </template>
      </a-comment>
    </div>
  </div>
  <div>
    <a-comment  v-for="comment in commentList" :key="comment.id">
      <template #actions>
        <span @click="toggleReply">回复</span>
        <span @click="deleteComment(comment.id)">删除</span>
        <span @click="reportComment(comment.id)">举报</span>
      </template>
      <template #author>
        <a>{{comment.userInfo.nickname}}</a>
      </template>
      <template #avatar>
        <a-avatar :src=comment.userInfo.image alt="Han Solo" />
      </template>
      <template #content>
        <p>
          {{ comment.content }}
        </p>
      </template>
    </a-comment>
    <!--    comment  input  -->
    <div v-if="showReply" class="comment-input-class" @focusout="handleFocusOut">

      <a-comment>
        <template #avatar>
          <a-avatar
              :src=store.state.userInfo.image
          />
        </template>
        <template #content>
          <a-form-item>
            <a-textarea :rows="4" v-model:value="value" />
          </a-form-item>
          <a-form-item>
            <a-button html-type="submit" :loading="submitting" type="primary" @click="handleSubmit">
              发送
            </a-button>
          </a-form-item>
        </template>
      </a-comment>
    </div>
  </div>
</template>


<script setup>
import {onMounted, ref} from 'vue';
import moment from 'moment';
import store from "@/store";
import axios from "axios";
import {message} from "ant-design-vue";
const showReply = ref(false);
const commentList = ref();
const comments = ref([]);
const submitting = ref(false);
const value = ref('');
const toggleReply = () => {
  showReply.value = !showReply.value;
  console.log(showReply.value)
};
onMounted(async () => {
  try {
    //查看是否收藏
    await axios.post('/comment/getFirstLevelComments',{
      videoId: store.state.videoInfo.videoId
    }).then(resp=>{
      if (resp.code === 200) {
        commentList.value = resp.data
      }
    })

  } catch (error) {
    console.error('请求失败:', error)
  }
})
const reportComment=(commentId)=>{
  axios.post('/comment/reportComment/'+commentId).then(resp=>{
    if (resp.code === 200) {
      message.success("举报成功")
    }else if(resp.code===50001) {
      message.warn(resp.message)
    }else {
      message.error("操作失败")
    }
  })
}
// 删除评论
const deleteComment = (commentId) => {
  try {
    axios.post('/comment/deleteComment/'+commentId).then(resp=>{
      if (resp.code === 200) {
        const index = commentList.value.findIndex(comment => comment.id === commentId);
        if (index !== -1) {
          commentList.value.splice(index, 1);
        }
      }
    })
    // 处理响应...
  } catch (error) {
    console.error('Error deleting comment', error);
  }
};


const handleSubmit = () => {

  axios.post('/comment/addComment',{
    videoId:store.state.videoInfo.videoId,
    userId:store.state.userInfo.userId,
    replyToUserId: '-1',
    content: value.value,
  }).then(resp=>{
    if (resp.code === 200) {
      axios.post('/comment/getFirstLevelComments',{
        videoId: store.state.videoInfo.videoId
      }).then(resp=>{
        if (resp.code === 200) {
          commentList.value = resp.data
        }
      })
    }
  })
  if (!value.value) {
    showReply.value = false;
    return;
  }
  submitting.value = true;
  setTimeout(() => {
    submitting.value = false;
    comments.value = [
      {
        author: 'Han Solo',
        avatar: 'https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png',
        content: value.value,
        datetime: moment().fromNow(),
      },
      ...comments.value,
    ];
    value.value = '';
    showReply.value = false;
  }, 0);


};

const handleFocusOut = (event) => {
  // 检查焦点是否还在评论输入区域内
  if (!event.currentTarget.contains(event.relatedTarget)) {
    showReply.value = false;
  }
};
</script>
