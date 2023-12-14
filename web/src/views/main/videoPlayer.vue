<script setup>
import {defineComponent } from 'vue'
import VueVideoPlayer from "@/components/videoPlayer/vueVideoPlayer.vue";
import ScrollingWebScoket from "@/components/videoPlayer/scrollingWebScoket.vue";



 defineComponent({
  name: "videoPlayer"
})
</script>

<template>
<vue-video-player></vue-video-player>
  <br>
  <scrolling-web-scoket  ></scrolling-web-scoket>
</template>

<style scoped>

</style>
