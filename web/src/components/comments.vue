<template>
  <a-comment>
    <template #actions>
      <span key="comment-nested-reply-to">Reply to</span>
    </template>
    <template #author>
      <a>Han Solo111</a>
    </template>
    <template #avatar>
      <a-avatar
          src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png"
          alt="Han Solo"
      />
    </template>
    <template #content>
      <p>
        1111We supply a series of design principles, practical patterns and high quality design
        resources (Sketch and Axure).
      </p>
    </template>
  </a-comment>
</template>

<script>
import {defineComponent } from 'vue';

export default defineComponent({
  name: "the-comments",
  setup() {

    return {
    };
  },
});
</script>
