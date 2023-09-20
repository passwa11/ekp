<template>
  <div class="expense-manual-edit">
    <group gutter="0">
       <x-input v-model="fdId" v-if="false"></x-input>
      <x-input  title="法人名称" v-model="fdCompanyName" hidden  ></x-input>
      <x-input title="发票号码"  v-model="fdInvoiceNo"  readonly ></x-input>
      <x-input title="费用类型"  v-model="fdExpenseItemName"  readonly ></x-input>
      
      <x-switch v-if="fdVatInvoice" title="是否专票" :value-map="[false, true]" v-model="fdVatInvoice" readonly></x-switch>
      <x-input  title="发票代码" v-model="fdInvoiceCode" readonly ></x-input>
      <x-input  title="开始日期" v-model="fdInvoiceDate" readonly ></x-input>
      <x-input  title="发票金额" v-model="fdInvoiceMoney" readonly></x-input>
      <x-input  title="税率" v-model="fdTaxValue" readonly></x-input>
      <x-input  title="税额" placeholder="申请金额" readonly v-model="fdTax"></x-input>
      <x-input  title="不含税额" v-model="fdNoTax" readonly></x-input>
      
    </group>
     

  </div>
</template>

<script>
import { fsUpload, fsOrganization } from '@comp'

export default {
  name: 'expenseInvoiceEdit',
  data () {
    return {
      fdCompanyName: '',
      fdInvoiceNo:'',
      fdExpenseItemName:'',
      fdVatInvoice:'',
      fdInvoiceCode:'',
      fdInvoiceDate:'',
      fdInvoiceMoney: '',  
      fdTax: '',
      fdTaxValue:'',
      fdNoTax:''
      
    }
  },
  components:{
    fsOrganization
  },
  mounted(){
    this.initDetail();
  },
  //初始化函数
  activated () {
    this.initDetail();
  },
  methods:{
   initDetail(){
        let dataJson = this.$route.params.dataJson;
        this.fdCompanyName=dataJson.fdCompanyName;
        this.fdInvoiceNo=dataJson.fdInvoiceNo;
        this.fdExpenseItemName=dataJson.fdExpenseItemName;
        this.fdVatInvoice=dataJson.fdVatInvoice;
        this.fdInvoiceCode=dataJson.fdInvoiceCode;
        this.fdInvoiceDate=dataJson.fdInvoiceDate;
        this.fdInvoiceMoney=dataJson.fdInvoiceMoney;
        this.fdTax=dataJson.fdTax;
        this.fdNoTax=dataJson.fdNoTax;
        this.fdTaxValue=dataJson.fdTaxValue;
        
      
    }
  }
}

</script>

<style lang="less">
.expense-manual-edit {
  padding-bottom: 60px;
}
</style>
