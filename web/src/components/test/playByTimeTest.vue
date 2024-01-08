<template>
  <div class="chat-container">
    <div
        class="chat-message"
        v-for="(msg, index) in visibleMessages"
        :key="index"
    >
      {{ msg.content }}
    </div>
  </div>
  <div>
    <a-input v-model:value="value" placeholder="Basic usage" /><a-button @click="addTime" >aq</a-button>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';

// 假设这是从外部传入的进度条时间（例如可以是父组件或 Vuex store中的数据）
const progressBarTime = ref(0);
const value = ref()
const addTime = ()=>{
  progressBarTime.value = value.value
}
const messages = ref([
  { time1: 8, content: "qqq" },
  { time1: 18, content: "qqq11" }
  // 更多消息...
]);

// 只显示当前进度条时间之前的消息
const visibleMessages = computed(() => {
  return messages.value.filter(msg => msg.time1 <= progressBarTime.value);
});

const scrollToBottom = () => {
  const container = document.querySelector('.chat-container');
  container.scrollTop = container.scrollHeight;
};

// 监听进度条时间的变化
watch(progressBarTime, (newValue, oldValue) => {
  if (newValue !== oldValue) {
    scrollToBottom();
  }
});

// 模拟进度条时间的变化
setTimeout(() => { progressBarTime.value = 10; }, 10000); // 10秒后设置进度条时间为10秒
setTimeout(() => { progressBarTime.value = 20; }, 20000); // 20秒后设置进度条时间为20秒
</script>

<style>
/* ...样式保持不变... */
</style>
