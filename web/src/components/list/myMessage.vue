<template>
  myMessage
  {{userId}}
</template>
<script setup>
import {useRoute} from "vue-router";

const route = useRoute()
const userId = route.query.userId
</script>
<style scoped>

</style>