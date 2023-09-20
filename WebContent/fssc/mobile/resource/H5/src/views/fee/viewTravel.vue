<template>
  <div class="fs-fee-travel-view">
    <group gutter="0">
      <Cell v-for="(item,index) in formData" v-if="item.showStatus!='3'&&item.type!='5'" :title="item.title" :value="item.value"></Cell>
      <Cell title="预算状态" v-model="fdBudgetStatus"></Cell>
      <Cell title="标准状态" v-model="fdStandardStatus"></Cell>
    </group>
  </div>
</template>

<script>
export default {
  name: "expenseNewTravel",
  data() {
    return {
      formData:[],
      fdBudgetStatus:'',
      fdStandardStatus:''
    }
  },
  activated(){
    this.initData();
  },
  methods: {
    initData() {
      if(this.$route.params.data){
        
        this.formData = [];
        let data = JSON.parse(this.$route.params.data)
        this.fdBudgetStatus = data.fdBudgetStatus;
        this.fdStandardStatus = data.fdStandardStatus;
        let formData = JSON.parse(this.$route.params.formData)
        for(let i of formData){
          if(i.name.indexOf(this.$route.params.detail)>-1&&i.type!='5'){
            i.value = data[i.name.split('.')[1]];
            this.formData.push(i)
          }
        }
      }
    }
  }
};
</script>

<style lang="less">
@import "../../assets/css/variable.less";
.fs-fee-travel-view {
  // background: #f5f5f5;
  padding-bottom: 60px;
  .vux-label {
    min-width: 90px;
  }
  .vux-x-input.disabled .weui-input {
    -webkit-text-fill-color: #333;
  }
  .bottom-wrap {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
  }
  .is-link .weui-input {
    padding-right: 16px;
    box-sizing: border-box;
  }
}
</style>
