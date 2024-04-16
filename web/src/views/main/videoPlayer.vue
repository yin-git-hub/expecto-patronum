<script setup>
import {defineComponent} from 'vue'
import VueVideoPlayer from "@/components/videoPlayer/vueVideoPlayer.vue";
import TheFollowing from "@/components/userTendency/following.vue";
import TheLike from "@/components/userTendency/like.vue";
import TheCollection from "@/components/userTendency/collector.vue";
import TheComment from "@/components/comment/TheComment.vue";
import VideoReport from "@/components/comment/videoReport.vue";


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
    <the-following></the-following>
    <span>
    <video-report></video-report>
    </span>

    &nbsp;
    <br>
    <the-comment></the-comment>
  </div>

</template>

<style scoped>

</style>

