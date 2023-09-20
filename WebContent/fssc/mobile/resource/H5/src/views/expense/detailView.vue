<template>
  <div class="fs-expense-detail view">
    <!-- 查看物流入口 Starts -->
    <router-link v-show="showExpress" class="fs-head-bar fs-express-enter" :to="{path:'/expense/express',query: {id: this.$route.params.fdId}}">
        <div class="fs-fl">
          <i class="fs-icon fs-icon-express">
            <!-- <img src="../../assets/images/icon/icon-express.png" alt=""> -->
          </i>
          <span>点击立即查看快递动态</span>
        </div>
        <div class="fs-fr"><i class="fs-iconfont fs-iconfont-right"></i></div>
    </router-link>
    <!-- 查看物流入口 Ends -->
    <group gutter="0">
      <Cell title="主题" :value="docSubject"></Cell>
      <Cell title="报销说明" :value="docContent"></Cell>
      <Cell title="所属公司" :value="fdCompanyName"></Cell>
      <Cell title="所属部门" :value="fdCostCenterName"></Cell>
      <Cell title="所属项目" v-if="categoryInfo.fdIsProject" :value="fdProjectName"></Cell>
      <Cell title="关联申请" v-if="categoryInfo.fdIsFee" :value="fdFeeMainName"></Cell>
    </group>

     <div class="fs-travel-wrap">
      <group gutter="0">
        <cell title="附件">
        </cell>
        <cell :title="item.title" :key="index" v-for="(item,index) in attachments" is-link link="" @click.native="showAtt(item)">
        </cell>
      </group>
    </div>

    <!-- 收款账户 -->
    <div class="fs-expense-account-wrap">
      <div class="header">
        <div class="label">收款账户</div>
      </div>
      <ul class="account-list">
        <li
          :class="'type-' + account.type"
          v-for="(account, index) in accountList"
          :key='index'>
          <router-link to="" @click.native="viewAccount(account)">
            <div class="left">
              <span class="fs-iconfont fs-iconfont-zhongguoyinhang"></span>
              <span class="fs-iconfont fs-iconfont-nongyeyinhang"></span>
              <span class="fs-iconfont fs-iconfont-jiansheyinhang"></span>
              <span class="fs-iconfont fs-iconfont-tongqian"></span>
            </div>
            <div class="center">
              <p class="title">{{account.title}}</p>
              <p class="name">收款金额：{{account.name}}</p>
            </div>
            <div class="icon-wrap">
              <span class="fs-iconfont fs-iconfont-zhongguoyinhang"></span>
              <span class="fs-iconfont fs-iconfont-nongyeyinhang"></span>
              <span class="fs-iconfont fs-iconfont-jiansheyinhang"></span>
              <span class="fs-iconfont fs-iconfont-tongqian"></span>
            </div>
          </router-link>
        </li>
      </ul>
    </div>

    <!-- 新增行程 -->
    <div class="fs-expense-fee-wrap" v-if="categoryInfo.fdExpenseType=='2' && categoryInfo.fdIsTravelAlone==true">
      <div class="header">
        <div class="label">行程</div>
      </div>
      <ul class="fee-list">
        <li v-for="(item, index) in fdTravelList" :key="index" :class="['fs-expense-card-'+item.type]">
          <div class="sub-header clearfix">
            <span class="title">{{item.fdSubject}}</span>
          </div>
          <div class="card-wrap"  @click.sync="linkTravelInfo(item)">
            
            <div class="section-2">
              <p><span class="label">城市：</span>{{item.fdStartPlace}}<span class="fs-iconfont fs-iconfont-laihui"></span>{{item.fdArrivalPlace}}</p>
              <p><span class="label">发生日期：</span>{{item.fdBeginDate}}<span class="label">人员：</span>{{item.fdPersonListNames}}</p>
            </div>
          </div>
        </li>
      </ul>
    </div>

    <!-- 新增费用 -->
    <div class="fs-expense-fee-wrap">
      <div class="header">
        <div class="label">费用</div>
      </div>
      <ul class="fee-list">
        <li v-for="(item, index) in fdDetailList" :key="index" :class="['fs-expense-card-'+item.type]">
          <div class="sub-header clearfix">
            <span class="title">{{'费用' + (index + 1)}}</span>
          </div>
          <div class="card-wrap"  @click.sync="linkDetailInfo(item)">
            <div class="section-1">
              <span>
                <i class="fs-iconfont" :class="item.type|typeClass"></i>
              </span>
              <div class="center">
                <div class="card-title">{{item.fdExpenseItemName}}</div>
                <div class="card-date">使用人：{{item.fdRealUserName}}</div>
              </div>
              <div class="fee-wrap">
                <span class="symbol">¥</span>
                <span class="fee">{{item.fdMoney}}</span>
              </div>
            </div>
            <div class="section-2">
              
              <p><span class="label">事由：</span>{{item.fdDesc}}</p>
            </div>
          </div>
        </li>
      </ul>
    </div>

    <!-- 新增发票 -->
    <div class="fs-expense-fee-wrap">
      <div class="header">
        <div class="label">发票</div>
      </div>
      <ul class="fee-list">
        <li v-for="(item, index) in fdInvoiceList" :key="index" :class="['fs-expense-card-'+item.type]">
          <div class="sub-header clearfix">
            <span class="title">{{'发票' + (index + 1)}}</span>
          </div>
          <div class="card-wrap"  @click.sync="linkInvoiceInfo(item)">
            <div class="section-1">
              <span>
                <i class="fs-iconfont" :class="item.type|typeClass"></i>
              </span>
              <div class="center">
                <div class="card-title">{{item.fdInvoiceNo}}</div>
                <div class="card-date">{{item.fdVatInvoice?'专票':'非专票'}}</div>
              </div>
              <div class="fee-wrap" v-if="item.fdVatInvoice">
                <span class="symbol">¥</span>
                <span class="fee">{{item.fdInvoiceMoney}}</span>
              </div>
            </div>
            
          </div>
        </li>
      </ul>
    </div>
    <div>
     <iframe id="frame" width="100%" frameborder="0"  height="500px" src =""></iframe>
    </div>
    <div v-transfer-dom>
      <popup v-model="showImage" height="100%" position="right" width="100%">
        <img :src="imgSrc" style="width:100%;height:100%;" v-on:click="showImage = false">
      </popup>
    </div>
   
  </div>
</template>

<script>
import { fsUpload } from '@comp'
export default {
  name: 'expenseView',
  data () {
    return {
      showPop: false,
      showExpress: true,//是否显示查看物流
      attachments:[],
      accountList: [],
      fdDetailList: [],
      docSubject:'',
      docContent:'',
      fdCompanyName:'',
      fdCostCenterName:'',
      fdProjectName:'',
      fdFeeMainName:'',
      fdMoney:'',
      feeList:[],
      imgSrc:'',
      showImage:false,
      fdTravelList:[],
      fdInvoiceList:[],
      fdTemplateId:'',
      categoryInfo:{}
    }
  },
  components:{
    fsUpload
  },
   activated(){
    this.initData();
  },
  mounted(){
    this.initData();
  },
   methods:{
    initData(){
      if(this.$route.params.fdId){
        this.$api.post(serviceUrl+'getExpenseMainById&cookieId='+LtpaToken+'&fdId='+this.$route.params.fdId,  {  })
        .then((resData) => {
          if(resData.data.result=='success'){
            let data = resData.data.data;
            this.docSubject = data.docSubject;
            this.docContent = data.docContent;
            this.fdCompanyName = data.fdCompanyName;
            this.fdCostCenterName = data.fdCostCenterName;
            this.fdProjectName = data.fdProjectName;
            this.fdFeeMainName = data.fdFeeMainName;
            this.fdMoney = data.fdMoney;
            this.attachments = data.attachments;
            this.accountList=data.accountData;
            this.fdDetailList = data.details;
            this.fdInvoiceList = data.fdInvoiceList;
            this.fdTravelList = data.fdTravelList;
            this.categoryInfo = data.categoryInfo;
          }else{
            console.log(resData.data.message);
          }
        }).catch(function (error) {
          console.log(error);
        })
        //let url = "http://219.134.186.38:8888/ekp/fs/expense/fs_expense_mobile_data/fsExpenseMobileData.do?method=viewFlow&fdId=";
        let url = domainPrefix+"fssc/expense/fssc_expense_main/fsscExpenseMain.do?method=viewLbpm&fdId=";
        document.getElementsByTagName("iframe")[0].setAttribute("src",url+this.$route.params.fdId+'&cookieId='+LtpaToken)
      }
    },
    showAtt(item,index){
      this.showImage = true;
      this.imgSrc = domainPrefix+'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=viewPic&fdId='+item.id;
    },
    linkDetailInfo(item){
      this.$router.push({name: 'expenseManualView',params:{dataJson:item,categoryInfo:this.categoryInfo}});
    },
    linkTravelInfo(item){
      this.$router.push({name: 'expenseViewTravel',params:{dataJson:item,categoryInfo:this.categoryInfo}});
    },
    linkInvoiceInfo(item){
      this.$router.push({name: 'expenseViewInvoice',params:{dataJson:item}});
    },
     viewAccount(item){
      this.$router.push({
        name:'expenseViewAccount',
        params:{accountJson:item,fdCompanyId:this.fdCompanyId}
      })
     }
  }
}
</script>

<style lang="less">
@import "../../assets/css/variable.less";
.fs-expense-detail.view {
  // background: #f5f5f5;
  .weui-cell__ft {
    min-height:22px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    color: #333;
  }
  .vux-cell-primary {
    min-width: 90px;
  }
  .cell-wrap {
    position: relative;
    .require-spot {
      color: #EA4335;
      position: absolute;
      top: 10px;
    }
  }
  .bottom-wrap {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
  }
  // 添加费用
  .fs-expense-account-wrap,
  .fs-expense-fee-wrap {
    margin-top: 10px;
    .header {
      background: #fff;
      position: relative;
      padding: 16px 15px;
      .label {
        font-size: 16px;
        color: #666;
      }
      .right-wrap {
        position: absolute;
        right: 15px;
        top: 0;
        line-height: 50px;
        color: @mainColor;
        font-size: 14px;
        .fs-iconfont {
          font-size: 14px;
          font-weight: bold;
          padding-right: 3px;
        }
      }
    }
    .fee-list {
      position: relative;
      &::after {
        content: '';
        height: 1px;
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        background: #e7e7e7;
        transform: scaleY(0.5);
      }
      &>li {
        background-color: #fff;
        padding-bottom: 16px;
        position: relative;
        &:last-child::after {
          height: 0;
        }
        &:after {
          content: '';
          height: 1px;
          position: absolute;
          bottom: 0;
          left: 12px;
          right: 12px;
          background: #eee;
          transform: scaleY(0.5);
        }
        .sub-header {
          font-size: 13px;
          padding: 12px 15px;
          .title {
            color: #666;
            float: left;
          }
          .del-btn {
            color: #EE5555;
            float: right;
          }
        }
        .card-wrap {
          background: #f6f6f6;
          border: 1px solid #eee;
          border-radius: 2px;
          margin: 0 10px;
          padding: 14px;
        }
        .section-1 {
          position: relative;
          .fs-iconfont {
            width: 40px;
            height: 40px;
            border: 2px solid @mainColor;
            border-color: @mainColor;
            background-color:#fff;
            color: @mainColor;
            // color: #fff;
            border-radius: 50%;
            display: block;
            line-height: 40px;
            text-align: center;
            font-size: 26px;
            position: absolute;
            top: 0;
            left: 0;
            &:before {
              position: relative;
              top: -2px;
            }
          }
          .center {
            min-height: 50px;
            padding: 0 0 0 54px;
            .card-title {
              font-size: 18px;
              color: @mainColor;
              margin-bottom: 4px;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
            }
            .card-date {
              font-size: 12px;
              color: #999;
            }
          }
          .fee-wrap {
            position: absolute;
            top: 6px;
            right: 0;
            .symbol {
              position: relative;
              // top: -2px;
              font-size: 14px;
            }
            .fee {
              font-size: 21px;
              color: #333;
              // font-weight: bold;
            }
          }
          &:after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            background: #eee;
            height: 1px;
            transform: scaleY(0.5);
          }
        }
        .section-2 {
          padding-top: 10px;
          font-size: 13px;
          color: #666;
          line-height: 18px;
          &>p:first-child {
            margin-bottom: 4px;
          }
          .label {
            color: #333;
          }
          .fs-iconfont {
            font-size: 14px;
            margin: 0 4px;
          }
        }
      }
    }
  }
  // 收款账户
  .fs-expense-account-wrap {
    margin-top: 10px;
    .account-list {
      background: #fff;
      position: relative;
      padding-bottom: 10px;
      &>.type-0>a {
        background-image: linear-gradient(-180deg, #EE7A7A 0%, #E54B5E 100%);
        .left .fs-iconfont-zhongguoyinhang,
        .icon-wrap .fs-iconfont-zhongguoyinhang {
          color: #B92127;
          display: block;
        }
      }
      &>.type-1>a {
        background-image: linear-gradient(-180deg, #069C86 0%, #018C77 100%);
        .left .fs-iconfont-nongyeyinhang,
        .icon-wrap .fs-iconfont-nongyeyinhang {
          color: #028E79;
          display: block;
        }
      }
      &>.type-2>a {
        background-image: linear-gradient(-180deg, #0E7DCC 0%, #0069B4 100%);
        .left .fs-iconfont-jiansheyinhang,
        .icon-wrap .fs-iconfont-jiansheyinhang {
          color: #0069B4;
          display: block;
        }
      }
      &>.type-3>a {
        background-image: linear-gradient(-180deg, #F3B36D 0%, #D57C13 100%);
        .left .fs-iconfont-tongqian,
        .icon-wrap .fs-iconfont-tongqian {
          color: #DB8825;
          display: block;
        }
      }
      &>li {
        padding: 9px 15px 0;
        box-sizing: border-box;
        &>a {
          display: block;
          height: 70px;
          border-radius: 5px;
          position: relative;
          overflow: hidden;
          @circelSize: 38px;
          .left {
            position: absolute;
            top: 50%;
            margin-top: -@circelSize/2;
            left: 14px;
            .fs-iconfont {
              display: none;
              width: @circelSize;
              height: @circelSize;
              line-height: @circelSize;
              background: #fff;
              border-radius: 50%;
              text-align: center;
              font-size: 28px;
            }
          }
          .center {
            color: #fff;
            padding: 18px 60px 0 60px;
            .title {
              font-size: 16px;
              margin-bottom: 3px;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
            }
            .name {
              font-size: 12px;
              opacity: 0.8;
              overflow: hidden;
              text-overflow: ellipsis;
              white-space: nowrap;
            }
          }
          .icon-wrap {
            position: absolute;
            right: 6px;
            top: 50%;
            margin-top: -70px;
            .fs-iconfont {
              color: #fff !important;
              display: none;
              opacity: 0.1;
              font-size: 120px;
            }
          }
        }
      }
      &:before {
        content: '';
        height: 1px;
        position: absolute;
        left: 0;
        right: 0;
        top: 0;
        transform: scaleY(0.5);
        background: #eee;
      }
    }
  }
}
.fs-expense-detail.hasTopBar{
  padding-top:45px;
}

// 查看物流的入口
.fs-express-enter{
  height:40px;
  line-height:40px;
  background-color:@mainColor;
  color:#fff;
  padding: 0 15px;
  margin-bottom: 10px;
  // position: fixed;
  // top:0;
  // right:0;
  // left:0;
  // z-index: 2;
  display: flex;
  align-items: center;
  justify-content: space-between;
  .fs-fl{
    .fs-icon{
      display:inline-block;
      width:28px;
      height:28px;
      background:url('../../assets/images/icon/icon-express.png') no-repeat left top;
      background-size:cover;
      vertical-align: middle;
    }
  }
  .fs-fr{
    .fs-iconfont{
      font-size: 14px;
    }
  }
}
</style>
