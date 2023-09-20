<template>
  <div class="expense-manual-edit">
    <group gutter="0">
      <x-input 
        title="法人名称"
        placeholder="请选择法人名称"
        :value="fdCompanyName"
        v-model="fdCompanyName"
        @click.native="companyPop = true"
        hidden
        class="is-link"
        :class="{
          'empty': fdCompanyName === ''
        }">
      </x-input>
      <!-- <popup-picker title="法人名称" v-model="fdCompanyId"  :data="companyList" :columns="1" @on-change="afterCompany" placeholder="请选择法人名称" show-name></popup-picker> -->
      <x-input title="发票号码" v-model="fdInvoiceNo" placeholder="请输入发票号码"></x-input>
      <x-input
        title="费用类型"
        placeholder="请选择费用类型"
        value="fdExpenseItemName"
        v-model="fdExpenseItemName"
        @click.native="itemPopUp = true"
        readonly
        :class="{
          'empty': fdExpenseItemName.length<1
        }"
        class="is-link">
      </x-input>
      <x-switch title="是否专票" :value-map="[false, true]" v-model="fdVatInvoice"></x-switch>
      <x-input title="发票代码"  v-model="fdInvoiceCode" placeholder="请输入发票代码"></x-input>
      <Datetime title="开票日期"  v-model="fdInvoiceDate" placeholder="请选择开票日期"  class="is-link"></Datetime>
      <x-input title="发票金额"  @on-change="afterTaxRate" v-model="fdInvoiceMoney" placeholder="请输入发票金额"></x-input>
      <popup-picker title="税率"  v-model="fdTaxRate"  :data="taxList" :columns="1" placeholder="请选择税率" @on-change="afterTaxRate" show-name></popup-picker>
      <x-input title="税额"  v-model="fdTax"  readonly></x-input>
      <x-input title="不含税金额"  v-model="fdNoTax"  readonly></x-input>
    </group>
     
    <!-- 费用类型选择 Start -->
   <div v-transfer-dom>
      <popup v-model="itemPopUp" class="itemPopUp">
        <popup-header
          @on-click-left="clearValue('itemPopUp','fdExpenseItem','keyword_item')"
          @on-click-right="selectItem"
          left-text="清空"
          right-text="完成">
        </popup-header>
        <search
          @on-submit="getFsBaseExpenseItem"
          v-model="keyword_item"
          @on-cancel="getFsBaseExpenseItem"
          @on-clear="getFsBaseExpenseItem"
          @on-change="getFsBaseExpenseItem"
          :auto-fixed="false">
        </search>
        <picker
          :data="itemList"
          v-model="fdExpenseItem"
          :columns="1">
        </picker>
      </popup>
    </div>
    <div v-transfer-dom>
      <popup v-model="companyPop" class="feeTypePopUp">
        <popup-header
          @on-click-left="clearValue('companyPop','fdCompany','keyword_company')"
          @on-click-right="selectCompany"
          left-text="清空"
          right-text="完成">
        </popup-header>
        <search
          @on-submit="getfdCompany"
          v-model="keyword_company"
          @on-cancel="getfdCompany"
          @on-clear="getfdCompany"
          @on-change="getfdCompany"
          :auto-fixed="false">
        </search>
        <picker
          :data="companyList"
          :columns="1"
          v-model="fdCompany">
        </picker>
      </popup>
    </div>

    <toast v-model="showWarn" type="warn">{{warnMessage}}</toast>

    <!-- 费用类型选择 End -->
    <div class="fs-bottom-bar">
      <div class="fs-col-24"><x-button type="primary" @click.native="setDetails">提交</x-button></div>
    </div>
  </div>
</template>

<script>
import { fsUpload, fsOrganization } from '@comp'
import kk from 'kkjs'
export default {
  name: 'expenseInvoiceEdit',
  data () {
    return {
      fdInvoiceDate:'',
      fdInvoiceCode: '',
      fdCompanyId:[],
      companyList:[],
      fdExpenseItemId:'',
      itemPopUp: false,  // 项目选择弹窗
      fdExpenseItemId:'',
      fdExpenseItemName:'',
      itemList:[],
      fdExpenseItem:[],
      keyword:'',
      fdInvoiceMoney:'',
      fdTaxRate:[],
      fdTaxValue:'',
      fdInvoiceNo:'',
      fdTax:'',
      fdNoTax:'',
      fdSalesName:'',
      currencyList:[],
      taxList:[],
      fdId:'',
      fdVatInvoice:false,
      isReadOnly:true,
      warnMessage:'',
      showWarn:false,
      fdTemplateId:'',
      fdCompanyName:'',
      fdCompany:[],
      companyPop:false,
      keyword_company:'',
      keyword_item:''
    }
  },
   components:{
    fsOrganization
  },
  mounted(){
   // this.getfdCompany();
    this.fdCompanyId = this.$route.params.fdCompanyId;
    this.initDetail();
    this.getFsBaseExpenseItem();
    this.getTaxRate();
    if(!this.fdId){
      this.getGeneratorId();
    }
    
  },
  //初始化函数
  activated () {
   // this.getfdCompany();
    this.fdTemplateId = this.$route.params.fdTemplateId;
    this.fdCompanyId = this.$route.params.fdCompanyId;
    this.getFsBaseExpenseItem();
    this.getTaxRate();
    this.initDetail();
    if(!this.fdId){
      this.getGeneratorId();
    }
  },
  methods:{
    clearValue(pop,name,key){
      this[pop] = false;
      this[key] = '';
      this[name] = [];
      this[name+'Id'] = '';
      this[name+'Name'] = '';
    },
    selectItem() {
      this.keyword_item = '';
      this.itemPopUp = false;
      this.getFsBaseExpenseItem();
      for(let i=0;i<this.itemList.length;i++){
        if(this.itemList[i].value==this.fdExpenseItem[0]){
          this.fdExpenseItemName = this.itemList[i].name;
          this.fdExpenseItemId = this.itemList[i].value;
          return;
        }
      }
    },
    //选择完公司
    selectCompany(){
      this.companyPop = false;
      this.keyword_company = '';
      this.getfdCompany();
      for(let i of this.companyList){
        if(i.value==this.fdCompany[0]){
          this.fdCompanyName =i.name;
          this.fdCompanyId = i.value;
        }
      }
       
      this.getFsBaseExpenseItem();
      this.getTaxRate();
      
        
    },
    //获取记账公司
    getfdCompany(){
       this.$api.post(serviceUrl+'getFsCompany&cookieId='+LtpaToken, { param: {} })
        .then((resData) => {
          if(resData.data.result=='success'){
            this.companyList=resData.data.data;
            
          }else{
            console.log(resData.data.message);
          }
          }).catch(function (error) {
       console.log(error);
      })
    },
    //选择完公司
    afterCompany(){
      this.getFsBaseExpenseItem();
      this.getTaxRate();
    },
     //获取随手记随机ID
    getGeneratorId(){
      this.$api.post(serviceUrl+'getGeneratorId&cookieId='+LtpaToken,  { param: {} })
        .then((resData) => {
              this.fdId=resData.data.fdId;
          }).catch(function (error) {
       console.log(error);
      })
    },
    //获取费用类型
    getFsBaseExpenseItem(keyword) {
      let params = new URLSearchParams();
      params.append('fdCompanyId', this.fdCompanyId);
      params.append('flag','note');
      if(keyword){
        keyword=encodeURI(keyword);
        params.append('keyword',keyword);
      }
       this.$api.post(serviceUrl+'getFsscBaseExpenseItem&cookieId='+LtpaToken, params)
       .then((resData) => {
          if(resData.data.result=='success'){
            this.itemList=resData.data.data;
          }else{
            console.log(resData.data.message);
          }
      }).catch(function (error) {
       console.log(error);
      })
    },
    formatName(value, name) {
      return name.split(' ')[name.split(' ').length - 1]
    },
      
   //获取税率
   getTaxRate(){
     let params = new URLSearchParams();
      params.append('fdCompanyId', this.fdCompanyId);
      this.$api.post(serviceUrl+'getFsBaseTaxrate&cookieId='+LtpaToken, params)
       .then((resData) => {
          if(resData.data.result=='success'){
            this.taxList=resData.data.data;
          }else{
            console.log(resData.data.message);
          }
        }).catch(function (error) {
       console.log(error);
      })
   },
   afterTaxRate(){
      this.fdTaxValue = 0;
      for(let tax of this.taxList){
        if(tax.value==this.fdTaxRate[0]){
          this.fdTaxValue = tax.fdTaxRate;
        }
      }
      if(this.fdTaxValue==null){
        return;
      }
      this.fdTax=(this.fdInvoiceMoney/(1+this.fdTaxValue/100.00)*this.fdTaxValue/100.00).toFixed(2);
      this.fdNoTax=(this.fdInvoiceMoney-this.fdTax).toFixed(2);
      
   },
   initDetail(){
      console.log(this.$route.params)
      if(!this.$route.params.dataJson){
        this.fdInvoiceCode = '';
        //this.fdCompanyId = '';
        this.fdCompany = [];
        this.fdCompanyName = '';
        this.fdExpenseItemName = '';
        this.fdExpenseItemId = '';
        this.fdExpenseItem = [];
        this.fdRate = '';
        this.fdMoney = '';
        this.fdInvoiceMoney = '';
        this.fdInvoiceNo = '';
        this.fdTaxRate = [];
        this.fdTax = '';
        this.fdNoTax = '';
        this.fdTaxValue = '';
        this.fdId = '';
        this.fdVatInvoice = false;
        this.fdInvoiceDate = '';
      }
      if(this.$route.params.dataJson){
        let dataJson = this.$route.params.dataJson;
        if(dataJson.fdCompanyId){
         // this.fdCompany = [dataJson['fdCompanyId']];
          //this.fdCompanyId = dataJson['fdCompanyId']
          //this.fdCompanyName = dataJson['fdCompanyName']
         // this.selectCompany();
        }
        
        this.fdId=dataJson['fdId']||'';
        if(dataJson.fdExpenseItemId){
          this.fdExpenseItemName=dataJson['fdExpenseItemName'];
          this.fdExpenseItemId=dataJson['fdExpenseItemId'];
          this.fdExpenseItem=[dataJson['fdExpenseItemId']];
        }else{
          this.fdExpenseItemName='';
          this.fdExpenseItemId='';
          this.fdExpenseItem=[];
        }
        
        this.fdInvoiceMoney=dataJson['fdInvoiceMoney'];
        this.fdInvoiceNo=dataJson['fdInvoiceNo']||'';
        this.fdTax=dataJson['fdTax'];
        this.fdNoTax=dataJson['fdNoTax'];
        this.fdInvoiceCode = dataJson['fdInvoiceCode']||'';
        this.fdVatInvoice = dataJson['fdVatInvoice']||false;
        this.fdInvoiceDate = dataJson['fdInvoiceDate']||''
        if(dataJson.fdTaxRate){
          this.fdTaxRate = [dataJson.fdTaxRate];
        }else{
          this.fdTaxRate =  [];
        }
      }
      
    },
    checkSubmit(data){
      if(!data.fdExpenseItemId){
        this.showWarn = true;
        this.warnMessage = '请填写费用类型'
        return false;
      }
      if(!data.fdInvoiceNo){
        this.showWarn = true;
        this.warnMessage = '请填写发票号码'
        return false;
      }
      
      if(data.fdVatInvoice){
        if(!data.fdInvoiceMoney){
          this.showWarn = true;
          this.warnMessage = '请填写发票金额'
          return false;
        }
        if(!data.fdTaxRate){
          this.showWarn = true;
          this.warnMessage = '请填写税率'
          return false;
        }
        if(!data.fdInvoiceDate){
          this.showWarn = true;
          this.warnMessage = '请填写开票日期'
          return false;
        }
      }
      return true;
    },
   //拼接明细数据
   setDetails(){
      let dataJson= {
        fdId:this.fdId,
        fdCompanyId:this.fdCompanyId,
        fdCompanyName:this.fdCompanyName,
        fdInvoiceMoney:this.fdInvoiceMoney,
        fdExpenseItemId:this.fdExpenseItemId,
        fdExpenseItemName:this.fdExpenseItemName,
        fdVatInvoice:this.fdVatInvoice,
        fdInvoiceNo:this.fdInvoiceNo,
        fdTax:this.fdTax,
        fdNoTax:this.fdNoTax,
        fdTaxValue:this.fdTaxValue,
        fdTaxRate:this.fdTaxRate.length>0?this.fdTaxRate[0]:'',
        fdTax:this.fdTax,
        fdNoTaxMoney:this.fdNoTax,
        fdInvoiceDate:this.fdInvoiceDate,
        fdInvoiceCode:this.fdInvoiceCode
      }
      if(!this.checkSubmit(dataJson)){
        return;
      }
      this.$router.push({name:'expenseNew',params:{fdInvoiceList:dataJson}});
   }
   
  }
}

</script>

<style lang="less">
.expense-manual-edit {
  padding-bottom: 60px;
}
</style>
