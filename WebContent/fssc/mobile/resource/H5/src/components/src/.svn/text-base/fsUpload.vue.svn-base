<template>
  <div class="fs-travel-wrap" :class="{ view: view }">
    <div class="header clearfix">
      <div class="left">附件</div>
    </div>
    <div class="body">
      <el-upload
        :action="action"
        :auto-upload="true"
        :disabled="view"
        :before-upload="beforeUpload"
        :file-list="fileList"
        multiple>
        <div class="up-btn"><span class="fs-iconfont fs-iconfont-fujian"></span>点击上传</div>
      </el-upload>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      percent: 13
    };
  },
  props: {
    view: Boolean,
    fileList: Array,
    action:String
  },
  methods:{
    beforeUpload(file,e,f,g,h){
      this.$emit('beforeUpload',file)
    }
  }

};
</script>

<style lang="less">
@import "../../assets/css/variable.less";
.fs-upload-wrap {
  position: relative;
  .header {
    padding: 13px 15px 0;
    .left {
      color: #666;
      font-size: 16px;
      float: left;
    }
  }
  .body {
    padding: 0 15px 13px;
    .el-upload {
      position: absolute;
      top: 0;
      right: 15px;
      height: 50px;
      line-height: 45px;
      color: @mainColor;
      font-size: 14px;
      .fs-iconfont {
        font-size: 16px;
        padding-right: 3px;
        position: relative;
        bottom: -1px;
      }
    }
  }
  &.view {
    .el-upload--text {
      display: none;
    }
  }
}
</style>


