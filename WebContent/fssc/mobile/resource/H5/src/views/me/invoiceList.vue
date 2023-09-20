<template>
    <div class="fs-invoice-list-page">
        <!-- 搜索头部 -->
       <fs-search @watchSearch="onSearch"></fs-search>
        <div class="fs-invoice-list-wrap">
            <Group title="未报销">
                <Cell v-if="noExpense.length >0" v-for="(item,index) in noExpense" :key=index>
                    <div class="fs-fl" slot="title">
                        <p class="fs-title">{{item.fdFeeType}}</p>
                        <p class="fs-info">{{item.fdSalesName}}</p>
                    </div>
                    <div class="fs-fr">
                        <div class="fs-num">￥<em>{{item.fdAmountTax}}</em></div>
                    </div>
                </Cell>
                <div style="text-align:center;height:50px;line-height:50px;" v-if="noExpense.length ==0">无数据</div>
            </Group>
            <Group title="已报销">
                <Cell v-if="isExpense.length >0" v-for="(item,index) in isExpense" :key=index>
                    <div class="fs-fl" slot="title">
                        <p class="fs-title">{{item.fdFeeType}}</p>
                        <p class="fs-info">{{item.fdSalesName}}</p>
                    </div>
                    <div class="fs-fr">
                        <div class="fs-num">￥<em>{{item.fdAmountTax}}</em></div>
                    </div>
                </Cell>
                <div style="text-align:center;height:50px;line-height:50px;" v-if="isExpense.length ==0">无数据</div>
            </Group>
        </div>
        <!-- 发票信息 -->
    </div>
</template>
<script>
import { fsSearch } from '@comp/'
export default {
  data() {
    return {
        isExpense:[],
        noExpense:[],
    };
  },
  components: {
    fsSearch
  },
  mounted(){
    this.getInvoiceByPerson('');
  },
  activated(){
    this.getInvoiceByPerson('');
  },
   methods:{
    getInvoiceByPerson(keyword){
      let _url = serviceUrl+'getInvoiceByPerson&cookieId='+LtpaToken;
      if(keyword){
           _url+='&keyword='+encodeURI(keyword);
      }
      let _param = { param: {} }
      this.$api.post(_url, _param)
        .then(result => {
          if(result.data.result=='success'){
            this.isExpense=result.data.isExpense;
            this.noExpense=result.data.noExpense;
          }else{
            console.log(result.data.message);
          }
        })
        .catch(err => {
          console.log('获取信息失败，请刷新重试')
        })
    },
    //搜索方法
    onSearch(keyword){
        this.getInvoiceByPerson(keyword);
    },
  },
};
</script>

<style lang="less">
@import "../../assets/css/variable.less";
.fs-invoice-list-page{
    padding-top:44px;
}
.fs-invoice-list-wrap{
  padding-top: 15px;
    &>div .weui-cells__title {
        color: #333 !important;
    }
    .fs-fl{
        .fs-title{
            color: #333;
            font-size:16px;
            margin-bottom:5px;
        }
        .fs-info{
            font-size:14px;
            color:#666;
        }
    }
    .fs-fr{
        .fs-num{
            color:#333;
            font-size:14px;
            em{
                font-size: 21px;
            }
        }
    }
}
</style>
