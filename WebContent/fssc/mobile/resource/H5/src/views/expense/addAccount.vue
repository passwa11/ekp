<template>
  <div class="fs-expense-add-account">
    <group gutter="0">
      <popup-picker title="支付方式" v-model="fdPayId" :data="fdPayList"  :columns="1"  placeholder="请选择支付方式"  show-name></popup-picker>
      <x-input title="收款金额" v-model="fdPayment" placeholder="请输入收款金额"></x-input>
      <x-input title="收款人账户名" v-model="fdAccountName" placeholder="请输入收款人账户名"></x-input>
      <x-input title="收款人账号" v-model="fdBankAccount" placeholder="请输入收款人账户"></x-input>
      <x-input title="收款人开户行" v-model="fdBank" placeholder="请输入收款人开户行"></x-input>
    </group>
    <div class="bottom-wrap">
      <x-button type="primary" @click.native="setAccounts">提交</x-button>
    </div>

    <toast v-model="showWarn" type="warn">{{warnMessage}}</toast>
    <loading :show="showLoading" :text="loadingMessage"></loading>
    <toast v-model="showShortTips" :time="2000">{{tipMessage}}</toast>
    <toast v-model="showCancel" type="cancel" :time="1000">{{cancelMessage}}</toast>
    <toast v-model="showText" type="text">{{textMessage}}</toast>


  </div>
</template>

<script>

export default {
  name: "ExpenseDetail",
  data() {
    return {
      fdPayId:[],
      fdPayList:[],
      fdPayment:'',
      fdAccountName:'',
      fdBankAccount:'',
      fdBank:'',
      warnMessage:'',
      showWarn:false,
      tipMessage:'',
      showShortTips:false,
      showCancel:false,
      cancelMessage:'',
      showLoading:false,
      loadingMessage:'',
      imgSrc:'',
      showImage:false,
      showText:false,
      textMessage:''
    }
  },
   //初始化函数
  mounted () {
    this.fdCompanyId = this.$route.params.fdCompanyId;
    this.getFsBasePay();
    this.initData();
  },
  activated(){
    this.fdCompanyId = this.$route.params.fdCompanyId;
    this.getFsBasePay();
    this.initData();
  },
  methods:{
    initData(){
      if(this.$route.params.accountJson){
        let data = this.$route.params.accountJson
        this.fdPayId = [data.fdPayId];
        this.fdPayment = data.fdPayment;
        this.fdAccountName = data.fdAccountName;
        this.fdBankAccount = data.fdBankAccount;
        this.fdBank = data.fdBank;
        this.fdId = data.fdId;
      }else{
        this.fdPayId = [];
        this.fdPayment = '';
        this.fdAccountName = '';
        this.fdBankAccount = '';
        this.fdBank = '';
        this.generateId();
      }
    },
    generateId(){
      this.$api.post(serviceUrl+'getGeneratorId&cookieId='+LtpaToken,  { param: {} })
          .then((resData) => {
            this.fdId = resData.data.fdId;
      }).catch(err=>{
        console.log(err)
      })
    },
    //获取付款方式
    getFsBasePay(){
      let param=new URLSearchParams();
      param.append('fdCompanyId',this.fdCompanyId);
       this.$api.post(serviceUrl+'getFsBasePay&cookieId='+LtpaToken, param)
        .then((resData) => {
          if(resData.data.result=='success'){
            this.fdPayList=resData.data.data;
          }else{
            console.log(resData.data.message);
          }
      }).catch( (error)=> {
       console.log(error);
      })
    },
    checkSubmit(data){
      if(!data.fdPayId){
        this.showWarn = true;
        this.warnMessage = '请填写支付方式'
        return false;
      }
      if(!data.fdPayment){
        this.showWarn = true;
        this.warnMessage = '请填写收款金额'
        return false;
      }
      if(!data.fdAccountName){
        this.showWarn = true;
        this.warnMessage = '请填写收款人账户名'
        return false;
      }
      if(!data.fdBankAccount){
        this.showWarn = true;
        this.warnMessage = '请填写收款人账号'
        return false;
      }
      if(!data.fdBank){
        this.showWarn = true;
        this.warnMessage = '请填写收款人开户行'
        return false;
      }
      return true;
    },
    //保存银行账号
    setAccounts() {
        
        let params = {
          fdPayId:this.fdPayId.length>0?this.fdPayId[0]:'',
          fdPayment:this.fdPayment,
          fdAccountName:this.fdAccountName,
          fdBankAccount:this.fdBankAccount,
          fdBank:this.fdBank,
          fdId:this.fdId
        }
        if(!this.checkSubmit(params)){
          return;
        }
        let type = '3';
        if(this.fdBank.indexOf('中国银行')>-1){
          type = '0';
        }
        if(this.fdBank.indexOf('农业银行')>-1){
          type = '1';
        }
        if(this.fdBank.indexOf('建设银行')>-1){
          type = '2';
        }
        params.type = type;
        let data = new URLSearchParams();
        this.$router.push({name:'expenseNew',params:{accountJson:params}});
    },
  }
}

</script>

<style lang="less">
.fs-expense-add-account {
  padding-bottom: 60px;
  .bottom-wrap {
    position: fixed;
    right: 0;
    left: 0;
    bottom: 0;
  }
}
</style>