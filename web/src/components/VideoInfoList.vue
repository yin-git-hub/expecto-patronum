<template>
  <a-card>
    <a-list :data-source="userListData.valList" :grid="{ gutter: 16, column: 5 }">

      <template #renderItem="{ item }">
        <a-list-item>
          <a-card hoverable style="width: 240px;height: 300px">
            <template  #cover>
              <img class="cover" alt="example" :src=item.videoCover />
            </template>
            <a-card-meta :title="item.videoName">
              <template #description>
                <p class="p-summary">简介: {{ item.videoSummary}}</p>
              </template>
            </a-card-meta>
          </a-card>
        </a-list-item>
      </template>
    </a-list>
  </a-card>
<!--  <a-pagination v-model:current="current" :total="500" show-less-items @change="onChange"  />-->
  <div>
    <a-pagination v-model:current="current" :total="userListData.totalElements"  @change="onChange" >
      <template #itemRender="
 /* eslint-disable-next-line vue/no-unused-vars */
{page, type, originalElement }">
        <a v-if="type === 'prev'">Previous</a>
        <a v-else-if="type === 'next'">Next</a>
        <renderVNode v-else :vnode="originalElement"></renderVNode>
      </template>
    </a-pagination>
  </div>

</template>
<script>

import {List} from "ant-design-vue";
import { defineComponent, ref } from 'vue';
import axios from "axios";
import router from "@/router";

function renderVNode(_, { attrs: { vnode } }) {
  return vnode;
}
export default defineComponent({
  props: {
    userListData: List,
    type: String,
    content: String
  },
  components: {
    renderVNode,
  },
  setup: function (props){
    // eslint-disable-next-line no-undef
    const urlValue=router.currentRoute._rawValue.query.text
    const urlTagKey=router.currentRoute._rawValue.query.tagKey
    const current = ref(1)
    const total = ref(props.userListData.totalElements||1)
    console.log('props.userListData.totalElements===>',props.userListData.totalElements);
    const onChange=(page, pageSize)=>{

      console.log('urlValue',urlValue)
      console.log('urlTagKey',urlTagKey)
      console.log('page',page)
      console.log('current',current.value)
      axios.post(`/search/all`, {
        "type": urlTagKey,
        "content": urlValue ,
        "pageIndex":  current.value,
        "pageSize": pageSize||10,
        "total": 0,
        "valList": []
      }).then(resp=>{
        console.log('resp.data===>',resp.data)
        // eslint-disable-next-line vue/no-mutating-props
        props.userListData.valList = resp.data.valList
        total.value = resp.data.totalPages
        console.log(props)
      })
    }
    return {
      onChange,
      current,
      total,
    };
  }
});
</script>
<style scoped>
.cover {
  width: 50%; /* 相对于父元素的宽度缩放为50% */
  height: 50% ; /* 根据宽度比例自动调整高度 */
  margin: 0 auto;
}

.p-summary {
  width: 200px; /* 定义容器的宽度 */
  white-space: nowrap; /* 不换行 */
  overflow: hidden; /* 隐藏溢出的文本 */
  text-overflow: ellipsis; /* 显示省略号 */
}
</style>
