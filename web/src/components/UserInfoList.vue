<template>
  <a-card>
    <a-list :data-source="userListData.valList">


      <template #renderItem="{ item }">
        <a-card-grid style="width: 20%; text-align: center">

          <a-list-item>
            <a-card hoverable style="width: 240px">
              <template #cover>
                <img alt="example" src="https://os.alipayobjects.com/rmsportal/QBnOOoLaAfKPirc.png" />
              </template>
              <a-card-meta :title="item.username">
                <template #description>
                  <p>Phone: {{ item.phoneNum }}</p>
                  <p>Age: {{ item.age }}</p>
                  <p>Gender: {{ item.gender }}</p>
                </template>
              </a-card-meta>
            </a-card>
          </a-list-item>

        </a-card-grid>

      </template>
    </a-list>

  </a-card>


</template>
<script>
import {defineComponent } from "vue";
import {List} from "ant-design-vue";

export default defineComponent({

  props: {
    userListData: List
  },
  setup: function (){

  }
});
</script>
