<!--成功弹窗-->
<template>
  <a-alert
      class="a-alert-class"
      v-if="props.isAlertVisible"
      message="Success Tips"
      type="success"
      show-icon
  />

</template>

<script setup>
// eslint-disable-next-line no-undef
const props=defineProps({
  isAlertVisible:Boolean
});
</script>
<style scoped>
.a-alert-class {
  position: fixed; /* 固定定位 */
  top: 20%; /* 屏幕顶部的20%处，可根据需要调整 */
  left: 50%; /* 水平居中 */
  transform: translateX(-50%); /* 向左移动自身宽度的50%，以确保完全居中 */
  width: 50%; /* 可根据需要调整宽度 */
  z-index: 9999;
}
</style>
