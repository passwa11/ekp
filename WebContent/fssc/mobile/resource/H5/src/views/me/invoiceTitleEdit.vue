<template>
  <div class="my-invoice-edit">
    <group gutter="0">
      <x-input v-if="false" value="" x-model="fdId"></x-input>
      <x-input title="名称" v-model="fdCompanyName" placeholder="请输入名称"  class="fs-cell-required"></x-input>
      <x-input title="税号" v-model="fdTaxNo" placeholder="请输入税号"  class="fs-cell-required"></x-input>
      <x-textarea
        :rows="2"
        v-model="fdAdress"
        title="单位地址"
        placeholder="请输入单位地址">
      </x-textarea>
      <x-input title="电话号码" placeholder="请输入电话号码"  v-model="fdPhone"></x-input>
      <x-input title="开户银行" placeholder="请输入开户银行"  v-model="fdBank"></x-input>
      <x-input title="银行账户" placeholder="请输入银行账户"  v-model="fdBankAccount"></x-input>
    </group>

    <div class="bottom-wrap">
      <x-button type="primary" @click.native="saveInvoiceTitleInfo">保存</x-button>
    </div>
    <toast v-model="showWarn" type="warn">{{warnMessage}}</toast>
  </div>
</template>

<script>

export default {
  name: 'myInvoiceEdit',
  data () {
    return {
      fdId:'',
      fdCompanyName:'',
      fdTaxNo:'',
      fdAdress:'',
      fdPhone:'',
      fdBank:'',
      fdBankAccount:'',
      expand: true,
      showWarn:false,
      warnMessage:''
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
      if(this.$route.params.fdId){
        let _url = serviceUrl+'viewInvoiceTitle&cookieId='+LtpaToken+'&fdId='+this.$route.params.fdId;
      let _param = { param: {} }
      this.$api.post(_url, _param)
        .then(result => {
          if(result.data.result=='success'){
            this.fdCompanyName=result.data.data.fdCompanyName;
            this.fdTaxNo=result.data.data.fdTaxNo;
            this.fdAdress=result.data.data.fdAdress;
            this.fdPhone=result.data.data.fdPhone;
            this.fdBank=result.data.data.fdBank;
            this.fdBankAccount=result.data.data.fdBankAccount;
            this.fdId=result.data.data.fdId;
          }else{
            console.log(result.data.message);
          }
        })
        .catch(err => {
          console.log('获取信息失败，请刷新重试')
        })
      }else{
        this.fdCompanyName='';
        this.fdTaxNo = '';
        this.fdAdress = '';
        this.fdPhone = '';
        this.fdBank='';
        this.fdBankAccount='';
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
    //保存发票抬头
    saveInvoiceTitleInfo() {
        let params = {
          fdCompanyName:encodeURI(this.fdCompanyName),
          fdTaxNo:this.fdTaxNo,
          fdAdress:encodeURI(this.fdAdress),
          fdPhone:this.fdPhone,
          fdBank:encodeURI(this.fdBank),
          fdBankAccount:this.fdBankAccount,
          fdId:this.fdId
        }
        if(!this.checkSubmit(params)){
          return;
        }
        let data = new URLSearchParams();
        data.append('params', JSON.stringify(params));
      	this.$api.post(serviceUrl+'saveInvoiceTitle&cookieId='+LtpaToken,
          data
      	).then((resData) => {
       	 if(resData.data.result=='success'){
         	 this.$router.push({name:'myInvoiceList'});
        	}
          	}).catch(function (error) {
      	 console.log(error);
      	})
    },
     checkSubmit(data){
        if(!data.fdCompanyName){
          this.showWarn = true;
          this.warnMessage = '请填写名称'
          return false;
        }
        if(!data.fdTaxNo){
          this.showWarn = true;
          this.warnMessage = '请填写税号'
          return false;
        }
        return true;
      },
  }
}
</script>

<style lang="less">
@import "../../assets/css/variable.less";
.my-invoice-edit {
  // background: #f5f5f5;
  padding-bottom: 60px;
  .title-cell {
    .weui-cell__ft {
      color: #333;
      font-size: 16px;
    }
  }
  .weui-cell__ft {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .weui-label {
    min-width: 90px;
  }
  .bottom-wrap {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
  }
}
</style>
