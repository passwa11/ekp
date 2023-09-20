<template>
  <div class="expense-manual-edit">
    <group gutter="0">
      <x-input title="起始日期" v-model="fdBeginDate"  readonly ></x-input>
      <x-input title="结束日期" v-model="fdEndDate" readonly ></x-input>
      <x-input title="天数" v-model="fdTravelDays" readonly ></x-input>
      <x-input  title="人员" v-model="fdPersonListNames" readonly  ></x-input>
      <x-input title="出发城市" v-model="fdStartPlace" readonly></x-input>
      <x-input title="到达城市" v-model="fdArrivalPlace" readonly></x-input>
      <x-input title="交通工具" v-model="fdBerthName" readonly></x-input>
    </group>
     

  </div>
</template>

<script>
import { fsUpload, fsOrganization } from '@comp'

export default {
  name: 'expenseManualEdit',
  data () {
    return {
      
      fdBeginDate:'',
      fdEndDate:'',
      fdStartPlace:'',
      fdArrivalPlace: '',  //组织架构选择器
      fdPersonListNames: '',
      fdBerthName:'',
      fdTravelDays:''
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
        this.fdPersonListNames=dataJson.fdPersonListNames;
        
        this.fdBeginDate=dataJson.fdBeginDate;
        this.fdEndDate=dataJson.fdEndDate;
        this.fdStartPlace=dataJson.fdStartPlace;
        this.fdArrivalPlace=dataJson.fdArrivalPlace;
        this.fdBerthName=dataJson.fdBerthName;
        this.fdTravelDays = dataJson.fdTravelDays
      
    }
  }
}

</script>

<style lang="less">
.expense-manual-edit {
  padding-bottom: 60px;
}
</style>
