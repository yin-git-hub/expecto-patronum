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
<!--    <scrolling-web-scoket  class="overlay"></scrolling-web-scoket>-->
    <br>
    <the-following></the-following>
    <br>
    <the-like></the-like>
    <br>
    <the-collection></the-collection>
  </div>

</template>

<style scoped>

</style>

