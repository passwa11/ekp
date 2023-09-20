<template>
    <div class="fs-approval-list">
      <tab
        active-color='#4285f4'
        :line-width=2
        v-model="activeTab"
        @on-item-click="changeHeight()" >
        <tab-item @on-item-click="changeHeight">报销</tab-item>
        <tab-item @on-item-click="changeHeight">申请</tab-item>
      </tab>
      <swiper v-model="activeTab" height="200px" :show-dots="false" ref="approvalSwiper">
        <swiper-item>
          <fsFeeCard :cardData="listData[0]" @click="viewItem"></fsFeeCard>
        </swiper-item>
        <swiper-item>
          <fsFeeCard :cardData="listData[1]" @click="viewItem"></fsFeeCard>
        </swiper-item>
      </swiper>
    </div>
</template>

<script>
import { fsFeeCard } from '@comp/'
export default {
  name: 'approvalList',
  data () {
    return {
      activeTab: 0,
      listData: [],
      linkName:['expenseDetailView','feeDetailView']
    }
  },
  components: {
    fsFeeCard
  },
  mounted(){
    this.getApproval();
  },
  activated(){
    this.getApproval();
  },
  methods: {
    getFeeMainList(){
      this.$api.post(serviceUrl+'getFeeMainList&cookieId='+LtpaToken,  { param: {} })
        .then((resData) => {
          this.listData=resData.data.data;
          // console.log('打印纸',this.listData)
      }).catch(function (error) {
       console.log(error);
      })
    },
    changeHeight () {
      this.$nextTick(() => {
        let minHeight = document.body.offsetHeight
        let tabHeight = this.listData[this.activeTab].length * 112
        let h = Math.max(minHeight, tabHeight)
        this.$refs.approvalSwiper.xheight = h + 'px'
      })
    },
    getApproval(){
       this.$api.post(serviceUrl + "getApproval&cookieId=" + LtpaToken, {param: {}}).then(resData => {
          if (resData.data.result == "success") {
            this.listData = resData.data.data;
            this.changeHeight();
            console.log('1023',this.listData)
          } else {
            console.log(resData.data.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    viewItem(item){
      this.$router.push({name:this.linkName[this.activeTab],params:{fdId:item.id}});
    }
  }
}
</script>

<style lang="less">
@import "../../assets/css/variable.less";
.fs-approval-list {
  padding-bottom: 50px;
  .fs-new-btn {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    display: block;
    padding: 15px 10px;
    color: #fff;
    background: @mainColor;
    text-align: center;
    font-size: 18px;
  }
  .vux-tab {
    &:before,
    &:after {
      content: '';
      position: absolute;
      left: 0;
      right: 0;
      bottom: 0;
      height: 1px;
      background: #eee;
      transform: scaleY(0.5);
    }
    &:before {
      top: 0;
    }
    .vux-tab-item {
      background: none;
      font-size: 16px;
      color: #333;
    }
  }
}
</style>
