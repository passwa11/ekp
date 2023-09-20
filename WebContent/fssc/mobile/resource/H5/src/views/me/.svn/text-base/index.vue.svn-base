<template>
  <div class="fs-me">
    <div class="user-msg-wrap">
      <div class="left">
        <img src="@/assets/images/avatar.png" />
      </div>
      <div class="center">
        <p class="name">{{name}}</p>
        <p class="desc">{{memo}}</p>
      </div>
      <div class="right">
        <span class="arrow"></span>
      </div>
    </div>
    <group>
      <!-- <Cell
        title="银行卡"
        link="/me/bankCards"
        is-link>
        <span slot="icon" class="fs-iconfont fs-iconfont-yinhangqia"></span>
      </Cell>
      <Cell
        title="发票抬头"
        link="/me/invoice/title/list"
        is-link>
        <span slot="icon" class="fs-iconfont fs-iconfont-fapiaotaitou"></span>
      </Cell> -->
      <Cell
        title="我的发票"
        link="/me/invoice/list"
        is-link>
        <span slot="icon" class="fs-iconfont fs-iconfont-wodefapiao"></span>
      </Cell>
    </group>
    <group>
      <Cell
        title="帮助中心"
        link="/me/help"
        is-link>
        <span slot="icon" class="fs-iconfont fs-iconfont-bangzhuzhongxin"></span>
      </Cell>
      <Cell
        title="退出系统"
        link=""
        @click.native="showConfirm=true"
        is-link>
        <span slot="icon" class="fs-iconfont fs-iconfont-close"></span>
      </Cell>
    </group>
    <div v-transfer-dom>
      <confirm v-model="showConfirm"
      title="确定" @on-confirm="logout">
        <p style="text-align:center;">{{ '您确定要退出系统吗？' }}</p>
      </confirm>
    </div>
  </div>
</template>

<script>

export default {
  name: 'me',
  data () {
    return {
      name:'',
      memo:'',
      showConfirm:false
    }
  },
   //初始化函数
  mounted () { 
    this.getPersonInfo();
  },
  methods:{

    logout(){
      window.location.href = domainPrefix+'logout.jsp?logoutUrl='+domainPrefix+'/fssc/mobile/resource/H5/dist/index.jsp'
    },
    getPersonInfo(){
      let _url = serviceUrl+'getPersonInfo&cookieId='+LtpaToken;
      let _param = { param: {} }
      this.$api.post(_url, _param)
        .then(result => {
          if(result.data.result=='success'){
            this.name=result.data.name;
            this.memo=result.data.memo;
          }else{
            console.log(result.data.message);
          }
        })
        .catch(err => {
          console.log('获取信息失败，请刷新重试')
        })
    },
  },
}
</script>

<style lang="less">
@import "../../assets/css/variable.less";
.fs-me {
  padding-bottom: 70px;
  .weui-cells .fs-iconfont {
    color: @mainColor;
    font-size: 19px;
    margin-right: 10px;
    transform: scale(1.1);
    display: block;
  }
  .user-msg-wrap {
    border-top: 20px solid @splitColor;
    padding: 14px 15px;
    position: relative;
    background: #fff;
    min-height: 65px;
    &:after {
      content: '';
      display: inline-block;
      height: 8px !important;
      width: 8px !important;
      border-width: 1px 1px 0 0 !important;
      border-color: #C8C8CD;
      border-style: solid;
      -webkit-transform: matrix(0.71, 0.71, -0.71, 0.71, 0, 0);
      transform: matrix(0.71, 0.71, -0.71, 0.71, 0, 0);
      position: relative;
      top: -2px;
      position: absolute;
      top: 50%;
      margin-top: -4px;
      right: 15px;
    }
    .left {
      position: absolute;
      width: 60px;
      height: 60px;
      overflow: hidden;
      border-radius: 3px;
      img {
        width: 100%;
        height: 100%;
      }
    }
    .center {
      padding: 0 10px 0 74px;
      .name {
        font-size: 17px;
        margin-top: 6px;
        color: #333;
      }
      .desc {
        color: #999;
        font-size: 14px;
        margin-top: 4px;
        line-height: 18px;
      }
    }
    .right {
      position: absolute;
    }
  }
  .vux-label {
    color: #333;
  }

}
</style>
