<template>
  <div>
    <ul class="fs-fee-card" v-show="cardData.length>0">
      <li
        v-for="(item, index) in cardData"
        :key="index"
        :class="item.clazz"
        @click.sync="onClick(item)">
        <router-link class="item-wrap" to="">
          <div class="row-1">
            <div class="left">
              <p class="title">{{item.title}}</p>
              <!-- <p class="date">{{item.date}}</p> -->
            </div>
            <div class="right">
              <span class="tag" :class="item.clazz">{{item.status}}</span>
            </div>
          </div>
          <div class="row-2">
            <table>
              <tr>
                <td class="left">
                  <!-- <p>公司：<span class="val">{{item.company}}</span></p> -->
                  <p><span class="val">{{item.dept}}</span><span class="date">{{item.date}}</span></p>
                </td>
                <td class="right">
                  <p class="count">¥<em>{{item.count}}</em></p>
                </td>
              </tr>
            </table>
          </div>
        </router-link>
      </li>
    </ul>
    <!-- 暂无数据 Starts -->
    <div class="fs-no-data" v-show="cardData.length==0">
        <div class="fs-icon">
            <img src="../../assets/images/noData.png" alt="">
            <p class="fs-title">
                <slot>暂无数据</slot>
            </p>
        </div>
    </div>
    <!-- 暂无数据 Ends -->
    <!-- <fsNodata></fsNodata> -->
  </div>
</template>

<script>
import { fsNodata } from '@comp/'
export default {
  name: 'fsFeeCard',
  props: {
    cardData: {
      default:[],
      require: true,
      type: Array
    }
  },
  methods:{
    onClick(item){
      // 状态 clazz status10 -- 未提交； status20 -- 审批中； status30 -- 已发布；
      this.$emit("click",item)
    }
  },
  components: {
    fsNodata
  }
}
</script>

<style lang="less">
@import "../../assets/css/variable.less";
.fs-fee-card {
  li,
  .status00,
  .status20,
  .status30 {
    &.item-wrap .row-1,
    &.item-wrap .row-2,
    &.item-wrap .row-1 p.title,
    &.item-wrap .row-2 table .right p,
    &.item-wrap .row-2 table .left p .val {
      color: #999;
    }
    &.item-wrap .row-2 table .right p.count{
      font-size: 14px;
      em{
        font-size: 21px;
      }
    }
    .status10{
      color: #EF741C;
    }
    .status20 {
        color: #4285f4;
    }
    .status30 {
        color: #34A853;
    }
    .item-wrap {
      margin-bottom: 10px;
      background: #fff;
      position: relative;
      display: block;
      &:after {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        height: 1px;
        transform: scaleY(0.5);
        background: @borderColor;
      }
      .row-1 {
        position: relative;
        // height: 62px;
        padding:18px 15px;
        box-sizing: border-box;
        color: #333;
        &:after {
          content: '';
          position: absolute;
          bottom: 0;
          left: 0;
          right: 0;
          height: 1px;
          // transform: scaleY(0.5);
          background: @borderColor;
          .border-2x
        }
        .left {
          padding-right: 80px;
        }
        .right {
          position: absolute;
          right: 15px;
          top: 18px;
          color: #999;
          font-size: 18px;
        }
        p {
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
          &.title {
            color: @stressTxtColor;
            // margin-bottom: 4px;
            font-size: 18px;
          }
          &.date {
            font-size: 12px;
            line-height: 15px;
            color: #999;
          }
        }
      }
      .row-2 {
        box-sizing: border-box;
        position: relative;
        // height: 62px;
        padding: 10px 15px 10px 15px;
        color: #333;
        table {
          width: 100%;
          .left {
            max-width: 230px;
            p {
              width: 100%;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
              font-size: 13px;
              line-height: 15px;
              color: #999;
              // margin-bottom: 5px;
              .val{
                margin-right: 10px;
                display: inline-block;
                vertical-align: middle;
                max-width: 80px;
                overflow: hidden;
                white-space: nowrap;
                text-overflow: ellipsis;
              }
              .val:empty{
                margin-right: 0;
              }
              .date {
                display: inline-block;
                vertical-align: middle;
              }
            }
          }
          .right {
            text-align: right;
            p {
              color: @stressTxtColor;
              max-width: 100px;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
              font-size: 14px;
              float: right;
              em{
                font-size: 21px;
              }
            }
          }
        }
      }
    }
  }
}
</style>
