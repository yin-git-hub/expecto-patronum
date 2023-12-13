<template>
  <div>
    <a-pagination
        v-model:current="current1"
        v-model:pageSize="pageSize"
        show-size-changer
        :total="500"
        @showSizeChange="onShowSizeChange"
    />
    <br />
  </div>
</template>
<script setup>
import { ref, watch } from 'vue';
const pageSize = ref(20);
const current1 = ref(3);
const onShowSizeChange = (current, pageSize) => {
  console.log(current, pageSize);
};
watch(pageSize, () => {
  console.log('pageSize', pageSize.value);
});
watch(current1, () => {
  console.log('current', current1.value);
});

</script>
