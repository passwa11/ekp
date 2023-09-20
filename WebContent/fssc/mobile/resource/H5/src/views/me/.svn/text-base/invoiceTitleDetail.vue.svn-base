<template>
  <div class="my-invoice-detail">
    <div class="container">
      <div class="detail-wrap">
        <table>
          <tr>
            <td>名称</td>
            <td>{{fdCompanyName}}</td>
          </tr>
          <tr>
            <td>税号</td>
            <td>{{fdTaxNo}}</td>
          </tr>
          <tr>
            <td>单位地址</td>
            <td>{{fdAdress}}</td>
          </tr>
          <tr>
            <td>电话号码</td>
            <td>{{fdPhone}}</td>
          </tr>
          <tr>
            <td>开户银行</td>
            <td>{{fdBank}}</td>
          </tr>
          <tr>
            <td>银行账户</td>
            <td>{{fdBankAccount}}</td>
          </tr>
        </table>
      </div>
      <div class="qr-wrap">
        <p class="prompt">开票时出示</p>
        <qrcode :value="qrmsg" type="img"></qrcode>
        <div class="btn-group">
          <div class="btn delete" @click.sync="delConfirm">删除</div>
          <router-link to="" @click.native="editDetail" class="btn edit">编辑</router-link>
        </div>
      </div>
    </div>
     <!-- 移除确认弹出框  -->
      <div v-transfer-dom>
        <confirm v-model="showConfirm"
        title="确定" @on-confirm="delDetail">
          <p style="text-align:center;">{{ '此操作将移除该发票抬头信息，是否继续' }}</p>
        </confirm>
      </div>
      <loading :show="showLoading" :text="loadingMessage"></loading>
  </div>
</template>

<script>
export default {
  name: 'myInvoiceDetail',
  data () {
    return {
      fdCompanyName:'',
      fdTaxNo:'',
      fdAdress:'',
      fdPhone:'',
      fdBank:'',
      fdBankAccount:'',
      showConfirm: false,
      showLoading:false,
      loadingMessage:'',
      qrmsg:''
    }
  },
  components: {
    
  },
  mounted(){
    if(this.$route.params.fdId){
      this.viewInvoiceTitle(this.$route.params.fdId);
    }
  },
  activated(){
    if(this.$route.params.fdId){
      this.viewInvoiceTitle(this.$route.params.fdId);
    }
  },
  methods:{
    viewInvoiceTitle(fdId){
      this.showLoading = true;
      let _url = serviceUrl+'viewInvoiceTitle&cookieId='+LtpaToken+'&fdId='+fdId;
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

            let msg = [];
            msg.push(this.fdCompanyName);
            msg.push(this.fdTaxNo);
            msg.push(this.fdAdress);
            msg.push(this.fdPhone);
            msg.push(this.fdBank);
            msg.push(this.fdBankAccount);
            this.qrmsg = msg.join(";");
            this.showLoading = false;
          }else{
            console.log(result.data.message);
            this.showLoading = false;
          }
        })
        .catch(err => {
          console.log('获取信息失败，请刷新重试')
          this.showLoading = false;
        })
    },
    
    editDetail(fdId){
      if(this.$route.params.fdId){
        this.$router.push({name:'myInvoiceEdit',params:{fdId:this.$route.params.fdId}});
      }
    },
      // 移除操作
    delConfirm() {
        // 提示删除confirm弹出框
        this.showConfirm = true;
    },
    delDetail(){
      this.showLoading = true;
      this.$api.post(serviceUrl+'deleteInvoiceTitle&cookieId='+LtpaToken+'&fdId='+this.$route.params.fdId,{}).then(resData=>{
        this.showLoading = false;
        if(resData.data.result=='success'){
          this.tipMessage = '操作成功'
          this.showShortTips  =true;
          this.$router.push({name:'myInvoiceList'});
        }else{
          this.cancelMessage = '操作失败'
          this.showCancel  =true;
        }
      }).catch(err=>{
        this.showLoading = false;
        console.log(err)
      });
    },
  },
}
</script>

<style lang="less">
@import "../../assets/css/variable.less";
.my-invoice-detail {
  background: @mainColor;
  min-height: 100%;
  padding: 25px 15px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  box-sizing: border-box;
  .container {
    background: #fff;
    border-radius: 3px;
    padding: 22px 15px;
    max-height: 100%;
    min-height: 560px;
    box-sizing: border-box;
  }
  .detail-wrap {
    border-bottom: 1px dashed @mainColor;
    position: relative;
    padding-bottom: 20px;
    table {
      tr {
        td {
          vertical-align: middle;
          line-height: 20px;
          padding: 8px 0;
          font-size: 16px;
          text-align: right;
          &:first-child {
            width: 90px;
            color: #999;
            text-align: left;
          }
        }
      }
    }
    &:before,
    &:after {
      content: '';
      width: 15px;
      height: 15px;
      border-radius: 50%;
      position: absolute;
      display: block;
      background: @mainColor;
      bottom: -7px;
      right: -24px;
    }
    &:after {
      left: -24px;
    }
  }
  .qr-wrap {
    text-align: center;
    .prompt {
      font-size: 16px;
      color: #999;
      margin: 20px 0 16px;
    }
    .qr {
      width: 150px;
      height: 150px;
      margin-bottom: 17px;
    }
    .btn {
      margin-top:10px;
      font-size: 16px;
      width: 100px;
      height: 32px;
      text-align: center;
      line-height: 32px;
      border-radius: 3px;
      display: inline-block;
      &.delete {
        color: #999;
        margin-right: 15px;
        border: 1px solid #999;
      }
      &.edit {
        color: @mainColor;
        border: 1px solid @mainColor;
      }
    }
  }
}
</style>
