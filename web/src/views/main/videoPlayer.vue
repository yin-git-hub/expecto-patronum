<script setup>
import {defineComponent} from 'vue'
import VueVideoPlayer from "@/components/videoPlayer/vueVideoPlayer.vue";
import ScrollingWebScoket from "@/components/videoPlayer/scrollingWebScoket.vue";

import TheFollowing from "@/components/userTendency/following.vue";
import TheLike from "@/components/userTendency/like.vue";
import TheCollection from "@/components/userTendency/collector.vue";


defineComponent({
  name: "videoPlayer"
})
</script>

<template>
  <vue-video-player></vue-video-player>
  <br>
  <scrolling-web-scoket></scrolling-web-scoket>
  <br>
  <the-following></the-following>
  <br>
  <the-like></the-like>
  <br>
  <the-collection></the-collection>
</template>

<style scoped>

</style>
