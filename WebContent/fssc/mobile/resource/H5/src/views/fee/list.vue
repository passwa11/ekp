<template>
  <div class="fs-fee-list">
    <fsFeeCard :cardData="listData" @click="viewItem"></fsFeeCard>
    <!-- <fsNodata>暂无数据2</fsNodata> -->
    <router-link class="fs-new-btn" @click.native="addFee" to="">新建</router-link>
    <div v-transfer-dom>
      <popup v-model="showCate" class="projectPopUp">
        <popup-header
          @on-click-left="showCate = false"
          @on-click-right="toAdd"
          left-text="取消"
          right-text="完成"
          title="选择模板">
        </popup-header>
        <picker :data="cateData" v-model="currentCateId">
        </picker>
      </popup>
    </div>
  </div>
</template>

<script>
import { fsFeeCard } from '@comp/'
import kk from 'kkjs'

export default {
  name: 'feeList',
  data () {
    return {
      listData: [],
      cateData:[],
      currentCateId:[],
      showCate:false
    }
  },
  components: {
    fsFeeCard
  },
  mounted(){
    this.getFeeMainList();
    this.getFeeCateList();
    kk.app.on('back',()=>{
      this.$router.push({name:'index'})
      return false;
    })
  },
  activated(){
    this.getFeeMainList();
    this.getFeeCateList();
    kk.app.on('back',()=>{
      this.$router.push({name:'index'})
      return false;
    })
  },
  methods:{
    getFeeMainList(){
      this.$vux.loading.show({'text':'正在加载列表数据'})
      this.$api.post(serviceUrl+'getFeeMainList&cookieId='+LtpaToken,  { param: {} })
        .then((resData) => {
          this.listData=resData.data.data;
          this.$vux.loading.hide()
      }).catch(function (error) {
       console.log(error);
      })
    },
    getFeeCateList(){
      this.$vux.loading.show({'text':'正在加载可用模板'})
      this.$api.post(serviceUrl+'getFeeCateList&cookieId='+LtpaToken,  { param: {} })
        .then((resData) => {
          this.cateData=resData.data.data;
          this.$vux.loading.hide()
      }).catch(function (error) {
       console.log(error);
      })
    },
    viewItem(item){
      this.$router.push({name:'feeDetailView',params:{fdId:item.id}});
    },
    addFee(){
      this.showCate = true;
    },
    toAdd(){
      if(!this.currentCateId||this.currentCateId.length==0){
        this.$vux.toast.show({text: '请选择模板'})
        return;
      }
      this.$router.push({name:'feeNew',query:{flag:'list',id:this.currentCateId[0]}});
      this.showCate = false;
    }
  }
}
</script>

<style lang="less">
@import "../../assets/css/variable.less";
.fs-fee-list {
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
}
</style>
