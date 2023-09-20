<template>
  <div class="fs-invoice-detail">
    <div style="margin-top:3px;">

    </div>
    <group gutter="0">
      <x-input title="发票代码" v-model="fdInvoiceCode" placeholder="" value="" :readonly="editForm"></x-input>
      <x-input title="发票号码" v-model="fdInvoiceNo" placeholder="" value="" :readonly="editForm"></x-input>
      <x-input title="发票校验码" v-model="fdCheckCode" placeholder="" value="" :readonly="editForm"></x-input>
      <Datetime title="发票日期" v-model="fdInvoiceDate" placeholder="" value="" :readonly="editForm"></Datetime>
      <cell title="验证信息" v-if="fdCheckStatus=='0'" value="未验真"></cell>
      <cell title="验证信息" v-if="fdCheckStatus=='1'" value="已验真"></cell>
      <x-input title="税额" v-model="fdTotalTax" placeholder="" value="" :readonly="editForm"></x-input>
      <x-input title="价税合计" v-model="fdAmountTax" placeholder="" value="" :readonly="editForm"></x-input>
      <x-input title="购方名称" v-model="fdPurchaserName" placeholder="" value="" :readonly="editForm"></x-input>
      <x-input title="购方税号" v-model="fdPurchaserTaxNo" placeholder="" value="" :readonly="editForm"></x-input>
      <x-input title="销方名称" v-model="fdSalesName" placeholder="" value="" :readonly="editForm"></x-input>
    </group>
    <div class="fs-bottom-bar">
      <div class="fs-col-12" v-if="editForm === true"><x-button type="primary" @click.native="editForm = false">编辑</x-button></div>
      <div class="fs-col-12" v-else><x-button type="primary" @click.native="saveInvoiceInfo">保存</x-button></div>
      <div class="fs-col-12"><x-button type="">删除</x-button></div>
    </div>
  </div>
</template>

<script>

export default {
  name: "invoiceDetail",
  data() {
    return {
      editForm: true,
      fdInvoiceCode:'',
      fdInvoiceNo:'',
      fdCheckCode:'',
      fdInvoiceDate:'',
      fdCheckStatus:'',
      fdTotalTax:'',
      fdAmountTax:'',
      fdPurchaserName:'',
      fdPurchaserTaxNo:'',
      fdSalesName:''
    };
  },

  //初始化函数
  mounted () {
    if(this.$route.query.id){
      this.getInvoiceInfo(this.$route.query.id);
    }
},
  activated (){
   this.editForm=true;
   if(this.$route.query.id){
      this.fdInvoiceCode='',
      this.fdInvoiceNo='',
      this.fdCheckCode='',
      this.fdInvoiceDate='',
      this.fdCheckStatus='',
      this.fdTotalTax='',
      this.fdAmountTax='',
      this.fdPurchaserName='',
      this.fdPurchaserTaxNo='',
      this.fdSalesName=''
      this.getInvoiceInfo(this.$route.query.id);
    }
  },
  methods:{
    saveInvoiceInfo() {
      let params = new URLSearchParams();
      params.append('params', encodeURI(JSON.stringify(this.buildData())));
      this.$api.post(serviceUrl+'saveInvoiceInfo&cookieId='+LtpaToken,
          params
      ).then((resData) => {
        if(resData.data.result=='success'){
          this.$router.push({name:'NewManual',query:{flag:'invoice'}});
        }
          }).catch(function (error) {
       console.log(error);
      })
    },
     buildData(){
      let data = {};
      data.InvoiceCode = this.fdInvoiceCode;
      data.InvoiceNumber = this.fdInvoiceNo;
      data.fdCheckCode=this.fdCheckCode;
      data.BillingDate = this.fdInvoiceDate;
      data.TotalTax = this.fdTotalTax;
      data.TotalAmount = this.fdAmountTax;
      data.PurchaserName = this.fdPurchaserName;
      data.PurchaserTaxNo = this.fdPurchaserTaxNo;
      data.SalesTaxName=this.fdSalesName;
      return data;
    },
    getInvoiceInfo(id){ 
      let params = new URLSearchParams();
      params.append('fdId', id);
      this.$api.post(
         serviceUrl+'getInvoiceInfo&cookieId='+LtpaToken,
         params
      ).then((resData) => {
        console.log(resData)
        if(resData.data.result=='success'){
          this.fdInvoiceCode=resData.data.data.fdInvoiceCode;
          this.fdInvoiceNo=resData.data.data.fdInvoiceNumber;
          this.fdCheckCode=resData.data.data.fdCheckCode;
          this.fdInvoiceDate=resData.data.data.fdInvoiceDate;
          this.fdCheckStatus=resData.data.data.fdCheckStatus
          this.fdTotalTax=resData.data.data.fdTotalTax;
          this.fdAmountTax=resData.data.data.fdJshj;
          this.fdPurchaserName=resData.data.data.fdPurchaserName;
          this.fdPurchaserTaxNo=resData.data.data.fdPurchaserTaxNo;
          this.fdSalesName=resData.data.data.fdSalesName;
        }else{
          console.log(resData.data.message)
        }
      }).catch(function (error) {
        console.log(error);
      })
    },
  },
};
</script>

<style lang="less">
.fs-invoice-img{
  height:139px;
}
.fs-invoice-detail {
  padding-bottom: 60px;
  .vux-x-input.disabled .weui-input {
    -webkit-text-fill-color: #333;
  }
}
</style>
