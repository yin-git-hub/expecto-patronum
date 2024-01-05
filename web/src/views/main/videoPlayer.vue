<script setup>
import {defineComponent} from 'vue'
import VueVideoPlayer from "@/components/videoPlayer/vueVideoPlayer.vue";
import TheFollowing from "@/components/userTendency/following.vue";
import TheLike from "@/components/userTendency/like.vue";
import TheCollection from "@/components/userTendency/collector.vue";


defineComponent({
  name: "videoPlayer"
})
</script>

<template>
  <div class="video-container">
    <vue-video-player></vue-video-player>
    <br>
    <the-like></the-like>&nbsp;
    <the-collection></the-collection>&nbsp;
    <the-following></the-following>&nbsp;
  </div>

</template>

<style scoped>

</style>

