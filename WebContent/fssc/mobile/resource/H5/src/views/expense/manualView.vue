<template>
  <div class="expense-manual-edit">
    <group gutter="0">
      <x-input title="所属行程" v-if="categoryInfo.fdExpenseType=='2' && categoryInfo.fdIsTravelAlone==true " v-model="fdTravel" readonly  ></x-input>
      <x-input title="报销人员" v-model="fdRealUserName" readonly  ></x-input>
      <x-input title="承担部门" v-if="categoryInfo.fdDept==true " v-model="fdDeptName" readonly  ></x-input>
      <x-input title="费用类型"  v-model="fdExpenseItemName"  readonly ></x-input>
      <x-input title="成本中心" v-model="fdCostCenterName"  readonly ></x-input>
      
      <x-input title="起始日期" v-if="categoryInfo.fdBeginDate==true " v-model="fdBeginDate"  readonly ></x-input>
      <x-input title="结束日期" v-if="categoryInfo.fdBeginDate==true " v-model="fdHappenDate"  readonly ></x-input>
      <x-input title="天数"  v-if="categoryInfo.fdBeginDate==true " v-model="fdTravelDays"  readonly ></x-input>
      <x-input title="出发城市"  v-if="categoryInfo.fdExpenseType=='2' && categoryInfo.fdIsTravelAlone==false"  v-model="fdStartPlace"  readonly ></x-input>
      <x-input title="到达城市" v-if="categoryInfo.fdExpenseType=='2' && categoryInfo.fdIsTravelAlone==false " v-model="fdArrivalPlace"  readonly ></x-input>
      <x-input title="交通工具" v-if="categoryInfo.fdIsTravelAlone==false && categoryInfo.fdBerthId==true " v-model="fdBerthName"  readonly ></x-input>
        
      <x-input title="WBS号" v-if="categoryInfo.fdWbs" v-model="fdWbsName"  readonly ></x-input>
      <x-input title="内部订单" v-if="categoryInfo.fdInnerOrder" v-model="fdInnerOrderName"  readonly ></x-input>
      <x-input title="发生日期"  v-if="categoryInfo.fdBeginDate==false" v-model="fdHappenDate"  readonly ></x-input>
      <!-- <x-input title="报销币种"  v-model="fdCurrencyName"  readonly ></x-input> -->
      <x-input title="招待人数" v-if="categoryInfo.fdPersonNumber" v-model="fdPersonNumber"  readonly ></x-input>
      <x-input title="申报金额" placeholder="申请金额" readonly v-model="fdMoney"></x-input>
      <x-input title="进项税额" v-if="fdIsDeduct" v-model="fdInputTax"  readonly ></x-input>
      <x-input title="用途摘要" v-model="fdDesc" readonly></x-input>
      <x-input title="预算状态" v-model="fdBudgetStatus"  readonly ></x-input>
      <x-input title="标准状态" v-model="fdStandardStatus" readonly></x-input>
      
    </group>
     

  </div>
</template>

<script>

export default {
  name: 'expenseManualEdit',
  data () {
    return {
	  fdBerthName:'',
	  fdArrivalPlace:'',
	  fdStartPlace:'',
	  fdTravelDays:'',
      fdBeginDate:'',
      fdRealUserName: '',
      fdExpenseItemName:'',
      fdCostCenterName:'',
      fdHappenDate:'',
      fdMoney:'',
      fdWbsName:'',
      fdDesc:'',
      fdInnerOrderName:'',
      fdInputTax:'',
      fdCurrencyName:'',
      fdIsDeduct:false,
      categoryInfo:{},
      fdtravel:'',
      fdPersonNumber:'',
      fdDeptName:'',
      fdBudgetStatus:'',
      fdStandardStatus:''

    }
  },
  components:{
    
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
        this.categoryInfo = this.$route.params.categoryInfo;
        let dataJson = this.$route.params.dataJson;
        this.fdRealUserName=dataJson.fdRealUserName;
        this.fdExpenseItemName=dataJson.fdExpenseItemName;
        this.fdCostCenterName=dataJson.fdCostCenterName;
        this.fdHappenDate=dataJson.fdHappenDate;
        this.fdWbsName=dataJson.fdWbsName;
        this.fdInnerOrderName=dataJson.fdInnerOrderName;
        this.fdInputTax=dataJson.fdInputTax;
        this.fdCurrencyName=dataJson.fdCurrencyName;
        this.fdMoney=dataJson.fdMoney;
        this.fdIsDeduct=dataJson.fdIsDeduct;
        this.fdDesc=dataJson.fdDesc;
        this.fdTravel = dataJson.fdTravel;
        this.fdPersonNumber = dataJson.fdPersonNumber;
        this.fdDeptName = dataJson.fdDeptName;
        this.fdBudgetStatus = dataJson.fdBudgetStatus;
        this.fdStandardStatus = dataJson.fdStandardStatus;
        this.fdBerthName =dataJson.fdBerthName;
	    this.fdArrivalPlace = dataJson.fdArrivalPlace;
	    this.fdStartPlace = dataJson.fdStartPlace;
	    this.fdTravelDays = dataJson.fdTravelDays;
        this.fdBeginDate = dataJson.fdBeginDate
    }
  }
}

</script>

<style lang="less">
.expense-manual-edit {
  padding-bottom: 60px;
}
</style>
