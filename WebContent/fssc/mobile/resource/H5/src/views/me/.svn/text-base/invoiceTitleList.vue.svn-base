<template>
  <div class="my-invoice-list">
    <ul class="invoice-list" >
      <li v-for="(item, index) in invoiceInfo"
        :key="index"
        @click.sync="viewDetail(item.fdId)">
        <router-link to="">
          <p class="title">{{item.companyName}}</p>
        </router-link>
      </li>
    </ul>
    <router-link to="/me/invoice/title/edit" class="add-invoice-btn vux-1px">
      <span class="fs-iconfont fs-iconfont-add"></span>添加发票抬头
    </router-link>
  </div>
</template>

<script>

export default {
  name: 'myInvoiceList',
  data () {
    return {
      invoiceInfo:[]
    }
  },
  components: {

  },
  mounted(){
    this.getInvoiceTitle();
  },
  activated(){
    this.getInvoiceTitle();
  },
  methods:{
    getInvoiceTitle(){
      let _url = serviceUrl+'getInvoiceTitle&cookieId='+LtpaToken;
      let _param = { param: {} }
      this.$api.post(_url, _param)
        .then(result => {
          if(result.data.result=='success'){
            this.invoiceInfo=result.data.data;
          }else{
            console.log(result.data.message);
          }
        })
        .catch(err => {
          console.log('获取信息失败，请刷新重试')
        })
    },
    viewDetail(fdId){
      this.$router.push({name:'myInvoiceDetail',params:{fdId:fdId}});
    },
  },
}
</script>

<style lang="less">
@import "../../assets/css/variable.less";
@import '~vux/src/styles/1px.less';
.my-invoice-list {
  padding-bottom: 10px;
  .invoice-list {
    &>li {
      margin: 14px 15px 0;
      height: 68px;
      border-radius: 5px;
      overflow: hidden;
      &>a {
        background: #fff;
        display: block;
        height: 100%;
        border-left: 4px solid @mainColor;
      }
    }
    .title {
      font-size: 16px;
      color: #333;
      padding: 16px 0 0 8px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .type {
      font-size: 12px;
      color: #999;
      padding: 4px 0 0 8px;
    }
  }
  .add-invoice-btn {
    display:block;
    font-size: 12px;
    color: #999;
    text-align: center;
    padding: 14px;
    margin: 14px 15px 0;
    .fs-iconfont {
      font-weight: bold;
      font-size: 13px;
      margin-right: 4px;
    }
    &:before {
      border: 1px solid #c6c6c6;
      color: #c6c6c6;
      border-radius: 5px;
    }
  }
}
</style>
