<template>
  <div>
    <video-player class="video-player vjs-custom-skin"
                  ref="videoPlayer"
                  :options="playerOptions"
    ></video-player>
  </div>
</template>

<script>
export default {
  data() {
    return {
      playerOptions: {
        // 视频 url
        sources: [{
          src: 'http://localhost:7330/water-sty/video/play/minio-demo/709d1d31dc47636e4f5ccbfd07601c19.mp4',
          type: 'video/mp4'
        }],
        // 其他设置项
        // 自动播放
        autoplay: false,
        // 播放/暂停、音量、进度条等控件
        controls: true,
        // 是否静音
        muted: false,
        // 是否循环
        loop: true,
        // 倍速
        playbackRates: [0.5, 1, 1.5, 2, 10],
        // 宽高比，可以传入一个字符串（形如’16:9’）
        aspectRatio: '16:9',
        // 开始页面
        poster: 'http://localhost:7330/water-sty/picture/video-picture-bucket/picture:709d1d31dc47636e4f5ccbfd07601c19.png',
      }
    }
  },

}
</script>
