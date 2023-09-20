<template>
  <div class="fs-expense-list">
    <fsFeeCard :cardData="listData" @click="viewItem"></fsFeeCard>
    <router-link class="fs-new-btn" @click.native="showCate = true" to="">新建</router-link>
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
  name: 'expenseList',
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
    this.getExpenseMainList();
    this.getExpenseCateList()
    kk.app.on('back',()=>{
      this.$router.push({name:'index'})
      return false;
    })
  },
  activated(){
    this.getExpenseMainList();
    this.getExpenseCateList()
    kk.app.on('back',()=>{
      this.$router.push({name:'index'})
      return false;
    })
  },
  methods:{
    getExpenseMainList(){
      this.$api.post(serviceUrl+'getExpenseMainList&cookieId='+LtpaToken,  { param: {} })
        .then((resData) => {
          if(resData.data.result=='success'){
            this.listData=resData.data.data;
          }else{
            console.log(resData.data.message);
          }
      }).catch(function (error) {
       console.log(error);
      })
    },
    viewItem(item){
      this.$router.push({name:'expenseDetailView',params:{fdId:item.id}});
    },
    getExpenseCateList(){
      this.$vux.loading.show({'text':'正在加载可用模板'})
      this.$api.post(serviceUrl+'getExpenseCateList',  { param: {} })
        .then((resData) => {
          this.cateData=resData.data.data;
          this.$vux.loading.hide()
      }).catch(function (error) {
       console.log(error);
      })
    },
    toAdd(){
      if(!this.currentCateId||this.currentCateId.length==0){
        this.$vux.toast.show({text: '请选择模板'})
        return;
      }
      this.$router.push({name:'expenseNew',query:{create:true,fdTemplateId:this.currentCateId[0]}});
      this.showCate = false;
    }
  },

}
</script>

<style lang="less">
@import "../../assets/css/variable.less";
.fs-expense-list {
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
