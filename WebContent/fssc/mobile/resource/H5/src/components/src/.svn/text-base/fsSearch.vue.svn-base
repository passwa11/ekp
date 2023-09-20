<template>
     <div class="fs-com-search-bar">
            <search position="absolute" placeholder="搜索"
            @on-submit="onSubmit"
            @on-cancel="onCancel"
            @on-clear="onClear"
            v-model="keyword"></search>
        </div>
</template>

<script>
import { Search } from "vux";
export default {
  data() {
    return {
      keyword:'',
    };
  },
  components: {
    Search
  },
  methods:{
    onSubmit(){
      this.$emit('watchSearch',this.keyword);    //触发$emit绑定的watchChild方法
    },
    onCancel(){
      this.$emit('watchSearch','');    //触发$emit绑定的watchChild方法
    },
    onClear(){
      this.$emit('watchSearch','');
    }
  },
};
</script>

<style lang="less">
/* 搜索公共样式 */
.fs-com-search-bar {
  position: fixed;
  top: 0;
  right: 0;
  left: 0;
  height: 44px;
  z-index: 3;
  padding-right: 0px;
  background: #fff;
}
.fs-com-search-bar .weui-search-bar {
  background-color: #fff;
}
.fs-com-search-bar .weui-search-bar__label {
  text-align: left;
  line-height: 26px;
  background-color: #f5f5f5;
}
.fs-com-search-bar .weui-search-bar__label .weui-icon-search {
  margin-left: 10px;
}
.fs-com-search-bar .weui-search-bar__cancel-btn {
  color: #4285f4;
}
.fs-com-search-bar .weui-search-bar:before,
.fs-com-search-bar .weui-search-bar:after {
  display: none;
}

.fs-search-box {
  position: relative;
}
.fs-search-box input {
  border: none;
}
.fs-com-search-bar .fs-filter {
  position: absolute;
  right: 0;
  top: 0;
  line-height: 44px;
  padding-right: 10px;
  font-size: 12px;
  span {
    font-size: 14px;
    color: #999;
    padding: 0 4px;
  }
}
.fs-com-search-bar .fs-filter .fs-iconfont {
  color: #999;
}
</style>


