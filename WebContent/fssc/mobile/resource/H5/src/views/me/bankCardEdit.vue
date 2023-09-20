<template>
  <div class="fs-base-add-account">
    <group gutter="0">
      <x-input  v-model="fdId" v-if="false"></x-input>
      <x-input title="账户名" v-model="fdAccountPayeeName" placeholder="请输入账户名"></x-input>
      <x-input title="开户行" v-model="fdPayeeBank" placeholder="请输入开户行"></x-input>
      <x-input title="开户行账号" v-model="fdPayeeAccount" placeholder="请输入开户行账号"></x-input>
    </group>
    <div class="bottom-wrap">
      <x-button type="primary" @click.native="setAccount">提交</x-button>
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
  name: "BankCardEdit",
  data() {
    return {
      fdId:'',
      fdAccountPayeeName:'',
      fdPayeeBank:'',
      fdPayeeAccount:'',
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
    this.initData();
  },
  activated(){
    this.initData();
  },
  methods:{
    initData(){
      if(this.$route.params.accountJson){
        let data = this.$route.params.accountJson
        this.fdAccountPayeeName = data.fdAccountPayeeName;
        this.fdPayeeBank = data.fdPayeeBank;
        this.fdPayeeAccount = data.fdPayeeAccount;
        this.fdId = data.fdId;
      }else{
        this.fdAccountPayeeName = '';
        this.fdPayeeBank = '';
        this.fdPayeeAccount = '';
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
    checkSubmit(data){
    if(!data.fdAccountPayeeName){
      this.showWarn = true;
      this.warnMessage = '请填写账户名'
      return false;
    }
    if(!data.fdPayeeBank){
      this.showWarn = true;
      this.warnMessage = '请填写开户行'
      return false;
    }
    if(!data.fdPayeeAccount){
      this.showWarn = true;
      this.warnMessage = '请填写开户行账号'
      return false;
    }
    return true;
  },
    //保存银行账号
    setAccount() {
        let params = {
          fdAccountPayeeName:encodeURI(this.fdAccountPayeeName),
          fdPayeeBank:encodeURI(this.fdPayeeBank),
          fdPayeeAccount:this.fdPayeeAccount,
          fdId:this.fdId
        }
        if(!this.checkSubmit(params)){
          return;
        }
        let data = new URLSearchParams();
        data.append('params', JSON.stringify(params));
      	this.$api.post(serviceUrl+'saveAccount&cookieId='+LtpaToken,
          data
      	).then((resData) => {
       	 if(resData.data.result=='success'){
         	 this.$router.push({name:'bankCardList'});
        	}
          	}).catch(function (error) {
      	 console.log(error);
      	})
    },
  }
}

</script>

<style lang="less">
.fs-base-add-account {
  padding-bottom: 60px;
  .bottom-wrap {
    position: fixed;
    right: 0;
    left: 0;
    bottom: 0;
  }
}
</style>