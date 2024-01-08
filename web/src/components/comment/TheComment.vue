<template>
  <div>
    <a-comment>
      <template #actions>
        <span @click="toggleReply">Reply to</span>
        <span @click="deleteComment">Delete</span>
      </template>
      <template #author>
        <a>Han Solo</a>
      </template>
      <template #avatar>
        <a-avatar src="https://joeschmoe.io/api/v1/random" alt="Han Solo" />
      </template>
      <template #content>
        <p>
          We supply a series of design principles, practical patterns and high quality design
          resources (Sketch and Axure).
        </p>
      </template>
    </a-comment>
    <!--    comment  input  -->
    <div v-if="showReply" class="comment-input-class" @focusout="handleFocusOut">

      <a-comment>
        <template #avatar>
          <a-avatar
              src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
              alt="Han Solo"
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
import { ref } from 'vue';
import moment from 'moment';
const showReply = ref(false);

const toggleReply = () => {
  showReply.value = !showReply.value;
  console.log(showReply.value)
};

// 删除评论
const deleteComment = async () => {
  try {
    console.log('Comment deleted');
    // 处理响应...
  } catch (error) {
    console.error('Error deleting comment', error);
  }
};


const comments = ref([]);
const submitting = ref(false);
const value = ref('');
const handleSubmit = () => {

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
  }, 1000);

};

const handleFocusOut = (event) => {
  // 检查焦点是否还在评论输入区域内
  if (!event.currentTarget.contains(event.relatedTarget)) {
    showReply.value = false;
  }
};
</script>
