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
   <div v-if="showReply" class="comment-input-class">
     <a-list
         v-if="comments.length"
         :data-source="comments"
         :header="`${comments.length} ${comments.length > 1 ? 'replies' : 'reply'}`"
         item-layout="horizontal"
     >
       <template #renderItem="{ item }">
         <a-list-item>
           <a-comment
               :author="item.author"
               :avatar="item.avatar"
               :content="item.content"
               :datetime="item.datetime"
           />
         </a-list-item>
       </template>
     </a-list>
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
             Add Comment
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
  }, 1000);
};
</script>
