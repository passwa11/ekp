<template>
  <div class="fs-fee-travel-edit">
    <group gutter="0">
      <x-input title="名称" placeholder="行程1" disabled></x-input>
      <x-input title="实际使用人" placeholder="周明明" disabled class="is-link"></x-input>
      <x-input title="费用类型" placeholder="差旅费" disabled class="is-link"></x-input>
      <x-input title="成本中心" placeholder="用户体验中心" disabled class="is-link"></x-input>
      <x-input title="开始时间" placeholder="2018年9月30日 16:00" disabled class="is-link"></x-input>
      <x-input title="结束时间" placeholder="2018年9月30日 16:00" disabled class="is-link"></x-input>
      <x-input title="出发地" placeholder="深圳" disabled class="is-link"></x-input>
      <x-input title="到达地" placeholder="深圳" disabled class="is-link"></x-input>
      <x-input title="交通工具" placeholder="火车" disabled class="is-link"></x-input>
      <x-input title="申请金额" placeholder="¥540.00" disabled></x-input>
      <x-input title="币种" placeholder="人民币" disabled class="is-link"></x-input>
      <x-input title="折本位币金额" placeholder="0" disabled></x-input>
      <x-textarea
        :rows="2"
        title="备注"
        disabled
        value="好的">
      </x-textarea>
    </group>

    <div class="fs-bottom-bar">
      <div class="fs-col-24"><x-button type="primary">保存</x-button></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "expenseNewTravel",
  data() {
    return {}
  },
  methods: {
  }
};
</script>

<style lang="less">
@import "../../assets/css/variable.less";
.fs-fee-travel-edit {
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
    box-sizing: border-box;
  }
}
</style>
