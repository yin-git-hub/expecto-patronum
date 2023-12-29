<template>
  <div>
    <a-button   type="link"
              @click="showModal">设置分组</a-button>
    <a-modal  v-model:visible="visible" title="关注分组" @ok="handleOk">
      <p><check-box/></p>
    </a-modal>

  </div>
</template>
<script>
import { defineComponent, ref } from 'vue';
import CheckBox from "@/components/following/checkBox.vue";
import store from "@/store";
import axios from "axios";
export default defineComponent({
  components: {  CheckBox},
  setup() {
    const visible = ref(false);
    const isAlertVisible = ref(false)
    const showModal = () => {
      visible.value = true;
    };
    const handleOk = e => {
      console.log('e===>',e);
      axios.post('/following/addFollowingToGroup',{
        upId:store.state.videoInfo.userId,
        groupIds:store.state.followingGroupList
      })

      visible.value = false;

    };
    return {
      visible,
      showModal,
      handleOk,
      isAlertVisible,
    };
  },
});
</script>

<style scoped>
.a-alert-class{
  position: fixed; /* 固定定位 */
  top: 20%; /* 屏幕顶部的20%处，可根据需要调整 */
  left: 50%; /* 水平居中 */
  transform: translateX(-50%); /* 向左移动自身宽度的50%，以确保完全居中 */
  width: 50%; /* 可根据需要调整宽度 */
  z-index: 1000; /* 确保在其他元素之上 */
}
</style>
