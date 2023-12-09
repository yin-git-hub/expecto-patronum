<script setup>
import { defineComponent, ref } from 'vue';
import TheVideoUpload from "@/components/upload/video.vue";
import TheVideoInfoInput from "@/components/upload/input.vue";

const isInfoInputVisible = ref(true);

defineComponent({
  name: "the-create",
});
</script>

<template>
  <the-video-upload></the-video-upload>
  <br>
  <!-- Use v-if to conditionally render or hide the element -->
  <the-video-info-input v-if="isInfoInputVisible"></the-video-info-input>
</template>

<style scoped>
/* Add your scoped styles here if needed */
</style>
