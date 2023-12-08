<script setup>
import {defineComponent} from 'vue'
import TheHeaderView from "@/components/the-header.vue";

 defineComponent({
  name: "the-main"
})
</script>

<template>
  <the-header-view></the-header-view>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
    >
      <router-view></router-view>
    </a-layout-content>
  </a-layout>


</template>

<style scoped>

</style>
