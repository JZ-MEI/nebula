<template>
  <lay-row class="description-row">
    <lay-col :md="colSpace" v-for="item in data">
      <div class="description-item">
        <div class="description-key">{{ item.key }}</div>
        <div class="description-value">{{ item.value}}</div>
      </div>
    </lay-col>
  </lay-row>
</template>
<script>
import {onMounted} from "vue";

export default {
  name: "Description",
  props: {
    rowNum: {
      type: Number,
      validator: function (value) {
        return [1, 2, 3, 4,
           6].includes(value);
      }
    },
    data:Array,
    labelWidth:String
  },
  setup(props, {emits}) {
    const colSpace = ref(24);

    const descLabelWidth = ref(props.labelWidth)
    function calcColSpace() {
      colSpace.value = 24 / (props.rowNum);
    }

    onMounted(() => {
      calcColSpace()
      if (!props.labelWidth){
        descLabelWidth.value = '30%'
      }
    })
    return {
      colSpace,
      descLabelWidth
    }
  }
}
</script>
<style>
.description-row {
  border-top: 1px solid #b7b7b7;
  border-left: 1px solid #b7b7b7;
}

.description-item {
  border-bottom: 1px solid #b7b7b7;
  border-right: 1px solid #b7b7b7
}
.description-key {
  width: v-bind(descLabelWidth);
  border-right: 1px solid #b7b7b7;
  background-color: rgba(22, 183, 119, 0.1);
  display: inline-block;
  padding: 3px 15px;
  text-align: center;
}
.description-value {
  padding: 3px 15px;
  display: inline-block;
}
</style>