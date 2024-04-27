<!--eslit-diable-->
<template>
  <div class="msg-container">
    <!--    消息-->
    <span class="message-box">
      {{ msgText }}
    </span>
    <!--    头像-->
    <img :src="avatarUrl" class="avatar-box"/>
  </div>
</template>
<script setup>
/* eslint-disable */
import {useRoute} from "vue-router";

const route = useRoute()
const userId = route.query.userId

defineProps({
  avatarUrl: {
    type: String
  },
  msgText: {
    type: String,
    default: "666"
  },
  id: {
    type: Number
  }
})

</script>
<style scoped>

.msg-container {
  display: flex;
  justify-content: flex-end;
  align-items: flex-start;

}

.avatar-box {
  display: inline-block;
  width: 3.5rem;
  height: 3.5rem;
  border-radius: 100%;
  box-shadow: 0 0 5px black;
  margin: 0 15px;
}

.message-box {
  background-color: lightgreen;
  min-width: 30%;
  max-width: 60%;
  word-break: break-word;
  display: inline-block;
  padding: 12px;
  margin-top: 30px;
  border-radius: 10px;
}

</style>