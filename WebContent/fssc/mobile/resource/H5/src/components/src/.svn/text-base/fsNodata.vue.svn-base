<template>
    <!-- 没有数据的缺省图 -->
    <div class="fs-no-data">
        <div class="fs-icon">
            <img src="../../assets/images/noData.png" alt="">
            <p class="fs-title">
                <slot>暂无数据</slot>
            </p>
        </div>
    </div>
</template>


<script>
export default {
  name: "fsNodata"
};
</script>

<style lang="less">
@import "../../assets/css/variable.less";
.fs-no-data {
  display: flex;
  align-items: center;
  flex-direction: column;
  width: 100%;
  color: @mainColor;
  text-align: center;
  .fs-icon {
    img {
      width: 140px;
      height: 140px;
    }
    i {
      font-size: 70px;
      margin-bottom: 30px;
    }
  }
  .fs-title {
    font-size: 17px;
    margin-top: 14px;
    color: #aeaeae;
    line-height: 18px;
  }
}
</style>

