<template>
    <!-- 滴滴打车相关页面 -->
    <div class="fs-third-taxi-wrap">
        <!-- 打车1 确认行程 -->
        <div class="taxi-step" v-for="(item,index) in list" :key="index" :class="{'active':nowStep === index }" @click="show(index)">
            <img :src="item.img" :alt="item.title">
        </div>
    </div>
</template>

<script>
export default {
  data() {
    return {
      nowStep: 0,
      list: [
        {
          img: require("../../../assets/images/third/taxi-01.jpg"),
          title: "确认行程"
        },
        {
          img: require("../../../assets/images/third/taxi-02.jpg"),
          title: "行程中"
        }
      ]
    };
  },
  activated(){
    this.nowStep = 0
  },
  methods: {
    show(index) {
      if (index === 0) {
        this.nowStep = 1
      } else {
          this.nowStep = 0
      }
    }
  }
};
</script>

<style lang="less">
.fs-third-taxi-wrap {
  height: 100%;
  position: relative;
  .taxi-step {
    position: absolute;
    top: 0;
    bottom: 0;
    width: 100%;
    display: none;
    &.active {
      display: block;
    }
  }
  img {
    width: 100%;
    height: 100%;
  }
}
</style>


