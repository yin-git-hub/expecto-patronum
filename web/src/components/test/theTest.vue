<template>
  <vue-danmaku style="width: 500px;height: 500px" ref="danmaku" v-model:danmus="danmus" loop :channels="5">
    <template v-slot:dm="{ index, danmu }">
      <span>{{ index }}{{ danmu.name }}：{{ danmu.text }}</span>
    </template>
  </vue-danmaku>
</template>

<script setup>


import {ref} from "vue";
const danmus = ref([
    { avatar: '', name: 'a', text: 'aaa' },
  { avatar: '', name: 'b', text: 'bbb' }])

</script>
