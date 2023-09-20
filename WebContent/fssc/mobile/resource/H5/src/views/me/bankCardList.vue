<template>
  <div class="fs-bank-card-list">
    <ul class="card-list">
      <li :class="'type-' + card.type" v-for="(card, index) in cardList" :key="index">
        <router-link to="/me/bankCards">
          <div class="left">
            <span class="fs-iconfont fs-iconfont-zhongguoyinhang"></span>
            <span class="fs-iconfont fs-iconfont-nongyeyinhang"></span>
            <span class="fs-iconfont fs-iconfont-jiansheyinhang"></span>
            <span class="fs-iconfont fs-iconfont-tongqian"></span>
          </div>
          <div class="right">
            <p class="title">{{card.accountName}}</p>
            <p class="name">账户名：{{card.fdName}}</p>
            <p class="num">{{card.fdPayeeAccount}}</p>
            <span class="fs-iconfont fs-iconfont-zhongguoyinhang"></span>
            <span class="fs-iconfont fs-iconfont-nongyeyinhang"></span>
            <span class="fs-iconfont fs-iconfont-jiansheyinhang"></span>
            <span class="fs-iconfont fs-iconfont-tongqian"></span>
          </div>
        </router-link>
      </li>
    </ul>
    <div @click.sync="addAcount" class="add-card-btn vux-1px">
      <span class="fs-iconfont fs-iconfont-add"></span>新增银行卡
    </div>
  </div>
</template>

<script>

export default {
  name: 'bankCardList',
  data () {
    return {
      cardList: []
    }
  },
  components: {

  },
   //初始化函数
  mounted () { 
    this.getAccountInfo();
  },
  activated(){
  	this.getAccountInfo();
  },
  methods:{
    getAccountInfo(){
      let _url = serviceUrl+'getAccountInfo&cookieId='+LtpaToken;
      let _param = { param: {} }
      this.$api.post(_url, _param)
        .then(result => {
          if(result.data.result=='success'){
            this.cardList=result.data.data;
          }else{
            console.log(result.data.message);
          }
        })
        .catch(err => {
          console.log('获取信息失败，请刷新重试')
        })
    },
    addAcount(){
    	this.$router.push({name:'bankCardEdit'});
    },
  },
}
</script>

<style lang="less">
@import "../../assets/css/variable.less";
@import '~vux/src/styles/1px.less';
.fs-bank-card-list {
  padding-bottom: 10px;
  .card-list {
    &>.type-0>a {
      background-image: linear-gradient(-180deg, #EE7A7A 0%, #E54B5E 100%);
      .fs-iconfont-zhongguoyinhang {
        color: #B92127;
        display: block;
      }
    }
    &>.type-1>a {
      background-image: linear-gradient(-180deg, #069C86 0%, #018C77 100%);
      .fs-iconfont-nongyeyinhang {
        color: #028E79;
        display: block;
      }
    }
    &>.type-2>a {
      background-image: linear-gradient(-180deg, #0E7DCC 0%, #0069B4 100%);
      .fs-iconfont-jiansheyinhang {
        color: #0069B4;
        display: block;
      }
    }
    &>.type-3>a {
      background-image: linear-gradient(-180deg, #F3B36D 0%, #D57C13 100%);
      .fs-iconfont-tongqian {
        color: #DB8825;
        display: block;
      }
    }
    &>li {
      margin: 15px 15px 0;
      border-radius: 5px;
      overflow: hidden;
      &>a {
        display: block;
        height: 110px;
        position: relative;
      }
      .fs-iconfont {
        display: none;
      }
      .left {
        position: absolute;
        top: 18px;
        left: 18px;
        span {
          background-color: #fff;
          border-radius: 50%;
          width: 42px;
          height: 42px;
          line-height: 42px;
          text-align: center;
          font-size: 30px;
        }
      }
      .right {
        padding: 16px 20px 0 70px;
        color: #fff;
        .title {
          font-size: 16px;
          margin-top: 5px;
        }
        .name {
          font-size: 12px;
          opacity: 0.7;
          margin-top: 4px;
        }
        .num {
          font-size: 18px;
          margin-top: 10px;
        }
        .fs-iconfont {
          font-size: 145px;
          color: #fff;
          opacity: 0.1;
          position: absolute;
          right: -4px;
          top: -25px;
        }
      }
    }
  }
  .add-card-btn {
    font-size: 12px;
    color: @mainColor;
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
      border-radius: 10px;
      .border-2x;
      .border-1w
    }
  }
}
</style>
