<template>
  <div class="fs-body fs-body-index" :class="{'fs-status-blur':showPop}">
    <!-- 头部 Starts  -->
    <header class="fs-index-header">
        <div class="fs-index-card">
          <router-link to="/approval/list" class="fs-index-card-header">
            <div class="fs-title">待审核<em>{{todoCount}} <i class="fs-badge"></i></em>条
            <span class="fs-arrow-wrap">
                <i class="fs-iconfont fs-iconfont-right"></i>
            </span>
            </div>
          </router-link>
          <router-link to="/expense/detail" class="fs-index-card-content">
            <div class="fs-num">
              <em>{{fdMoney[0] | number }}</em><i>元</i>
            </div>
            <p class="fs-link">
              <span>未报费用</span>
              <span class="fs-arrow-wrap">
                <i class="fs-iconfont fs-iconfont-right"></i>
              </span></p>
          </router-link>
        </div>
    </header>
    <div class="fs-index-link">
      <router-link to="" class="fs-index-btn" >
          <div @click="showPop = true">记一笔</div>
      </router-link>
    </div>
    <!-- 头部 Ends -->
    <!-- 申请单&报销单 快捷 Starts -->
    <div class="fs-index-hotLink">
      <router-link to="/fee/list" class="fs-item">
        <div class="fs-txt-content">
          <div class="fs-icon-wrap"><i class="fs-iconfont fs-iconfont-shenqingdan"></i></div>
          <div class="fs-title">申请单</div>
        </div>
        <i class="fs-icon"></i>
      </router-link>
      <router-link to="/expense/list" class="fs-item">
       <div class="fs-txt-content">
         <div class="fs-icon-wrap">
            <i class="fs-iconfont fs-iconfont-baoxiaodan"></i>
         </div>
          <div class="fs-title" ref="title">报销单</div>
        </div>
      </router-link>
    </div>
    <!-- 申请单&报销单 快捷 Ends -->
    <!-- 第三方应用 Starts -->
    <!-- <card :header="{title:'第三方应用'}">
      <div slot="content" class="fs-panel-content">
        <ul class="fs-index-iconList">
          <li v-for="(item,index) in app" :key="index" @click.sync="linkThird(item.link)">
              <div class="fs-iconfont" :class="item.icon"></div>
              <p class="fs-title">{{item.title}}</p>
          </li>
        </ul>
      </div>
    </card>-->
    <!-- 第三方应用 Starts -->
    <!-- 手工记-弹出框 Starts -->
    <div v-transfer-dom class="blur-popup">
      <popup v-model="showPop" height="100%" class="fs-index-popup">
        <div class="fs-popup-wrap">
          <div class="fs-popup-list">
            <ul>
              <router-link tag="li" to="" @click.native="item.click" v-for="(item,index) in list" v-if="item.type!='photo'">
                
                <h3 class="fs-title"><i class="fs-iconfont"  :class="item.class"></i>{{item.title}}</h3>
                <p class="fs-txt">{{item.desc}}</p>
                
              </router-link>
              <li class="router-link-active"  v-for="(item,index) in list" v-if="item.type=='photo'">
              <el-upload
                ref="newUpload"
                action="getAction"
                :before-upload="beforeUpload"
                :on-success="onUploadSuccess"
                :auto-upload="true"
                :with-credentials="true"
                :show-file-list="false">
                <h3 class="fs-title"><i class="fs-iconfont " :class="item.class"></i>{{item.title}}</h3>

                <p class="fs-txt">{{item.desc}}</p>
                </el-upload>
              </li>
              
              
            </ul>
          </div>
          <div class="fs-btn-close" @click="showPop=false"><i class="fs-iconfont fs-iconfont-close"></i></div>
        </div>
      </popup>
    </div>
    <!-- 手工记-弹出框 Ends -->
  </div>
</template>

<script>
import kk from 'kkjs'
import dd from 'dingtalk-jsapi'
import axios from 'axios'
export default {
  name: "index",
  data() {
    return {
      action:serviceUrl+'saveAtt&cookieId='+LtpaToken,
      app: [
        { title: "打车", icon: "fs-iconfont-dache", link: "/third/taxi" },
        { title: "酒店", icon: "fs-iconfont-jiudian", link: "http://h5.m.taobao.com/trip/btrip-hotel-search/search/index.html?corpId=ding259143288b70948b" },
        { title: "机票", icon: "fs-iconfont-jipiao", link: "https://h5.m.taobao.com/trip/btrip-search/search/index.html?corpId=ding259143288b70948b" },
        { title: "更多", icon: "fs-iconfont-more", link: "" }
      ],
      colorList: ["#3CAE69", "#febe4d", "#4285F4", "#F27474", "#3CAE69"],
      popupList: [
        {
          type:'manual',
          title: "手工录入",
          icon: "fs-iconfont-shougongluru",
          desc: "手工录入发票信息",
          link: '/',
          click:this.byHand,
          color: "#3CAE69"
        },
        {
          type:'photo',
          title: "拍照识别",
          icon: "fs-iconfont-paizhaoshibie",
          desc: "通过手机拍照获取发票信息",
          link: "/",
          click:this.getPicture,
          color: "#febe4d"
        },
        {
          type:'qrcode',
          title: "发票扫描",
          icon: "fs-iconfont-fapiaosaomiao",
          desc: "通过纸质二维码扫描,获取发票信息",
          link: "/",
          click:this.scanQrCode,
          color: "#4285F4"
        },
        {
          type:'weixin',
          title: "微信导入",
          icon: "fs-iconfont-daorufapiao",
          desc: "通过微信卡包导入电子票",
          link: "/",
          color: "#F27474"
        },
        {
          type:'alipay',
          title: "第三方导入",
          icon: "fs-iconfont-disanfangmingxi",
          desc: "通过支付宝卡包导入电子票",
          link: "/",
          color: "#3CAE69"
        }
      ],
      list:[],
      showPop: false,
      todoCount:0,
      fdMoney:[0],
      LtpaToken:''

    }
  },
  filters: {
    //保留2位小数点过滤器 不四舍五入
    number(value) {
      var toFixedNum = Number(value).toFixed(3);
      var realVal = toFixedNum.substring(0, toFixedNum.toString().length - 1);
      return realVal;
    }
  },
  //初始化函数
  mounted () {
    this.initData();
    this.getNoteCreateList();
    console.log(this)
    //this.$vux.loading.show({text:'加载中'})
    
  },
  created (){
    this.$nextTick(()=>{
        //this.$countNumber(0, this,this.fdMoney)
    })
  },
  watch:{
    '$route' (to,from){
      this.initData();
      this.addBackEvent();
    }
  },
  methods:{
    addBackEvent(){
      if(backEventBinded)return;
      backEventBinded = true;
      document.addEventListener('backbutton', (e)=> {
        if(this.$route.query.fromPage){
          this.$router.push({name:this.$route.query.fromPage});
          e.preventDefault(); 
          return;
        }
        for(let i in routerMap){
          if(i == this.$route.name){
            this.$router.push({name:routerMap[i]});
            e.preventDefault(); 
            return;
          }
        }
      });
      dd.ready(()=>{
        dd.biz.navigation.setLeft({
          control:true,
          text:'',
          onSuccess:()=>{
            if(this.$route.query.fromPage){
              this.$router.push({name:this.$route.query.fromPage});
              return;
            }
            for(let i in routerMap){
              if(i == this.$route.name){
                this.$router.push({name:routerMap[i]});
                return;
              }
            }
            
          },
          onFail:()=>{
          
          }
        })
      });
    },
    //获取随手记入口
    getNoteCreateList(){
      this.$api.post(serviceUrl+'getNoteCreateList',{}).then((resData) => {
        this.list = [];
        if(resData.data.result=='success'){
           let data = resData.data.data;
           for(var i=0;i<this.popupList.length;i++){
            for(let k=0;k<data.length;k++){
              if(data[k].type==this.popupList[i].type){
                this.list.push(this.popupList[i]);
              }
            }
           }
        }else{
          alert(resData.data.message)
        }
      })
    },
    beforeUpload(file){
      this.$vux.loading.show({'text':'上传中...'});
      this.showPop = false;
      let fd = new FormData();
      fd.append('file',file);//传文件
      axios.post(this.getAction(),fd,{headers: {'Content-Type': 'multipart/form-data'}}).then((res)=>{
        this.$refs.newUpload[0].clearFiles()
        this.$vux.loading.hide();
        this.$vux.loading.show({'text':'识别中...'})
        this.getInvoiceInfoFromRayky(res.data.fdId)
      }).catch((err)=>{
        this.$refs.newUpload.clearFiles()
        this.$vux.loading.hide();
        this.$vux.toast.show({'text':'上传失败'});
        console.log(err)
      })
      return false;
    },
    onUploadSuccess(resp,file,list){
      //this.$vux.loading.hide();
      //this.$vux.loading.show({'text':'识别中...'})
      //this.getInvoiceInfoFromRayky(resp.fdId)
    },
    getAction(file){
      return this.action+LtpaToken+"&fdModelName=com.landray.kmss.fssc.mobile.model.FsscMobileNote&filename=pic_"+new Date().getTime()+'.jpg';
    },
    initData(){
      if(kk.isKK()){
        kk.ready(()=>{
          kk.app.getCookie({
            url: serviceUrl
          },( res )=>{
            //LtpaToken = encodeURIComponent(encodeURIComponent(this.getCookie(res.cookie,"LtpaToken")));
            //console.log('getCookie coolieStr: ' + res.cookie );
            this.getTodoCount();
            this.getNotExpenseAcount();
            //JSESSIONID=DD24DC2A1174E096DD9FD912C98D775A
          });
        })
      }else{
        //LtpaToken = encodeURIComponent(encodeURIComponent('AAECAzVDOTU4N0I2NUM5QzFGMzZ3YW5nYkiP5fFonYu/8ZVzd23n8MmFWN8g'));
        //
        //AAECAzVDOTJGMzM4NUM5MzlCRjh0ZXN03ymT/a5leMR1p/YoJUrYwRxMiqc=
        this.getTodoCount();
        this.getNotExpenseAcount();
      }
    },
    getCookie(cookie,name) {
      var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
      if (arr = cookie.match(reg))
        return (arr[2]);
      else
        return null;
    },
     getTodoCount(){
      let _url = serviceUrl+'getTodoCount&cookieId='+LtpaToken;
      let _param = { param: {} }
      this.$api.post(_url, _param)
        .then(result => {
          this.todoCount = result.data.count;
        })
        .catch(err => {
          console.log('获取信息失败，请刷新重试')
        })
    },

    getNotExpenseAcount(){
      let _url = serviceUrl+'getNotExpenseAcount&cookieId='+LtpaToken;
      let _param = { param: {} }
      this.$api.post(_url, _param)
        .then(result => {
          if(result.data.result=='success'){
            this.$set(this.fdMoney, 0, result.data.fdMoney)
            this.$vux.loading.hide();
          }else{
            console.log(result.data.message);
          }
        })
        .catch(err => {
          console.log('获取信息失败，请刷新重试')
        })
    },
     linkThird(url){
      if(url.indexOf("http")>-1){
        window.open(url);
      }else if(url.indexOf('method') > 1){
        window.open('http://219.134.186.38:8888/ekp'+url+'&cookieId='+LtpaToken,'_self');
      }
      else {
        this.$router.push({path:url});
      }
    },
    byHand(){
      this.$router.push({name:'NewManual',params:{flag:'isnew'}});
    },
    scanQrCodeInKK(){
      kk.scaner.scanTDCode((res)=>{
        let code = res.code.split(",");
        let params = {
          fplx:code[1],
          fdInvoiceNo:code[3],
          fdInvoiceCode:code[2],
          fdInvoiceMoney:code[4],
          fdInvoiceDate:code[5],
          fdCheckCode:code[6].substring(code[6].length-6)
        }
        let data = new URLSearchParams();
        data.append("fdInvoiceNo",JSON.stringify(params));
        this.$api.post(serviceUrl+'addInvoiceInfoByQrCode&cookieId='+LtpaToken,data).then((resData) => {
          if(resData.data.result=='success'){
            this.$router.push({name:'NewManual',params:{invoiceData:resData.data.data,flag:'isnew'}});
          }else{
            alert(resData.data.message)
          }
        })
      });
    },
    scanQrCode(){
      if(kk.isKK()){
      	this.scanQrCodeInKK();
      }else{
      	this.scanQrCodeInDD();
      }
    },
    scanQrCodeInDD(){
      dd.biz.util.scan({
        type: 'qrCode' , // type 为 all、qrCode、barCode，默认是all。
        onSuccess: (data1)=> {
          //alert(JSON.stringify(data))
          let code = data1.text.split(",");
          let params = {
            fplx:code[1],
            fdInvoiceNo:code[3],
            fdInvoiceCode:code[2],
            fdInvoiceMoney:code[4],
            fdInvoiceDate:code[5],
            fdCheckCode:code[6].substring(code[6].length-6)
          }
          let data = new URLSearchParams();
          data.append("fdInvoiceNo",JSON.stringify(params));
          this.showPop = false;
          this.$vux.loading.show("解析中");
          this.$api.post(serviceUrl+'addInvoiceInfoByQrCode&cookieId='+LtpaToken,data).then((resData) => {
            this.$vux.loading.hide();
            if(resData.data.result=='success'){
              this.$router.push({name:'NewManual',params:{invoiceData:resData.data.data,flag:'isnew'}});
            }else{
              this.$vux.toast.show({text:resData.data.message,type:'cancel',time:1000});
            }
          })
        },
        onFail : (err)=> {
          alert(JSON.stringify(err))
        }
      })
    },
    getPicture(){
      let filename = 'pic_'+new Date().getTime()+".jpg"
      let path = 'sdcard://'+filename;
      kk.media.getPicture({
        sourceType:'camera',
        destinationType: 'file',
        encodingType: 'jpg',
        savePath:path,
        quality:50
      }, (res)=>{
        this.showPop=false;
        this.$vux.loading.show({'text':'上传中'})
        this.$api.post(serviceUrl+'getGeneratorId&cookieId='+LtpaToken,  {})
          .then((resData) => {
            //this.$router.push({name:'NewManual',params:{path:path,filename:filename,fdId:resData.data.fdId,flag:'isnew'}});
            this.uploadPicture(path,filename,resData.data.fdId)
          }).catch(function (error) {
          console.log(error);
        })

      })
    },
    uploadPicture(path,filename,id,index,attachment){
      var proxy = new kk.proxy.Upload({
        url:serviceUrl+'saveAtt&fdModelName=com.landray.kmss.fssc.mobile.model.FsscMobileNote&cookieId='+LtpaToken+'&filename='+filename+'&fdId='+id,
        path:path
      },(res)=>{
        //上传成功，开始识别
        if(res.progress==100){
          this.getInvoiceInfoFromRayky(id);
          this.$vux.loading.show({'text':'识别中'})
        }else{
          this.$vux.loading.show({'text':'上传中:'+res.progress+'%'})
        }
      },(code,message)=>{
        alert("code:"+code+",message："+message);
        this.$vux.loading.hide()
      })
      // 开始上传
      proxy.start();
    },
    getInvoiceInfoFromRayky(id){
      //alert('开始识别并保存');
      this.$api.post(serviceUrl+'saveInvoiceInfoFormRayky&city='+this.fdStartPlace+'&fdId='+id,  {})
        .then((resData) => { 
          //识别成功
          if(resData.data.result=='success'){
            this.$vux.loading.hide()
            if(resData.data.message){
              this.$vux.toast.show({type:'success',text:resData.data.message,time:1000});
            }else{
              this.$vux.toast.show({type:'success',text:'识别成功',time:1000});
            }
            this.getNotExpenseAcount();
          }else{
            this.$vux.toast.show({type:'cancel',text:'识别失败：'+resData.data.message,time:1000});
            this.$vux.loading.hide()
          }
        }).catch( (error) => {
          this.$vux.toast.show({type:'cancel',text:'识别失败：'+JSON.stringify(error),time:1000});
          this.$vux.loading.hide()
        console.log(error);
      })
    },
    saveMultiInvoiceInfo(item){
      this.$api.post(serviceUrl+'createMultiInvoiceInfo&cookieId='+LtpaToken+'&params='+encodeURIComponent(JSON.stringify(item)),  {})
        .then((resData) => { 
          if(resData.data.result=='success'){
            this.$vux.loading.hide()
            this.$vux.toast.show({'text':'识别成功'})
            this.getNotExpenseAcount();
          }else{
            this.$vux.loading.hide()
            alert('发票查验失败：'+resData.data.message)
          }
          //保存成功回调
          
          
        }).catch(function (error) {
          alert('识别异常：'+JSON.stringify(error));
          this.$vux.loading.hide()
        console.log(error);
      })
    }

  },
};
</script>

<style lang="less">
@import "../../assets/css/variable.less";
.fs-body-index {
  padding-bottom: 70px;
}
body .blur-popup .vux-popup-dialog {
  background: transparent !important;
}
// 背景模糊
.fs-status-blur{
  filter:blur(10px)
}
.fs-badge {
  display: inline-block;
  width: 5px;
  height: 5px;
  border-radius: 50%;
  background: #f63535;
}

// 头部
.fs-index-header {
  background-image: linear-gradient(-1deg, #d7d7d7 2%, #ffffff 100%);
  height: 173.5px;
  padding: 0 15px;
  .fs-arrow-wrap{
    display:inline-block;
    transform: scale(0.8);
    margin-left:-6px;

  }
  .fs-index-card {
    height: 200px;
    background-color:@mainColor;
    background-image:url(../../assets/images/fs-index-card-bg.png);
    background-size:cover;
    background-repeat:no-repeat;
    border-radius: 5px;
    &-header {
      display:block;
      height: 40px;
      line-height: 40px;
      text-align: right;
      padding-right: 10px;
      border-bottom:1px dashed #fff;
      position: relative;
      &:before,&:after{
        content:"";
        width:15px;
        height:15px;
        border-radius: 50%;
        position: absolute;
        bottom:-7px;
        right:-9px;
        background-color:#f6f6f6;
       }
      &:after{
        left: -9px;
        right: auto;
      }
      .fs-iconfont {
        font-size: 12px;
      }
      .fs-title{
        color: #fff;
        em {
          font-style: normal;
          position: relative;
          margin: 0 2px;
          font-size:16px;
          .fs-badge {
            position: absolute;
            top: 1px;
            right: 2px;
          }
        }
      }
    }
    &-content {
      display:block;
      text-align: center;
      padding-top: 35px;
      padding-bottom:32px;
      .fs-iconfont {
        font-size: 12px;
      }
      .fs-num {
        color: #fff;
        display: block;
        line-height:50px;
        height:45px;
        font-size:0;
        i {
          font-style: normal;
          font-size:18px;
        }
        em {
          font-style: normal;
          font-size: 35px;
        }
      }
    }
    .fs-link {
      display: block;
      font-size: 14px;
      line-height: 20px;
      text-align: center;
      color: #fff;
      letter-spacing: 1px;
      .fs-icon {
        color: #f00;
      }
    }
  }
}

// 记一笔
.fs-index-link {
  height: 70px;
  background: #fff;
  text-align: center;
  .fs-index-btn {
    margin-top: 11px;
    display: inline-block;
    height: 48px;
    line-height: 48px;
    background: #fa7b3f;
    border-radius: 100px;
    font-size: 16px;
    color: #fff;
    width: 80%;
  }
}

// 快捷
.fs-index-hotLink {
  display: flex;
  height: 115px;
  background: #fff;
  margin-top: 10px;
  padding-top: 10px;
  padding-bottom: 10px;
  align-items: center;
  .fs-icon-wrap {
    width: 70px;
    height: 70px;
    line-height: 70px;
    margin: 0 auto 7px;
    text-align: center;
    background: #ecf2fd;
    border-radius: 100px;
    .fs-iconfont {
      font-size: 36px;
      // color: #4285f4;
      background: linear-gradient(-180deg, #689df6 0%, #4285f4 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
    }
  }
  .fs-title {
    font-size: 15px;
    line-height: 21px;
  }
  .fs-item {
    flex: 1;
    text-align: center;
    &:first-child {
      border-right: 1px solid @borderColor;
      .fs-iconfont{
        margin-left:9px;
        font-size:38px;
      }
    }
  }
  .fs-txt-content {
    .fs-title {
    }
    .fs-summery {
    }
  }
}

// 第三方应用 panel
.fs-index-iconList {
  display: flex;
  padding-top: 26px;
  padding-bottom: 17px;
  text-align: center;
  li {
    // display: inline-block;
    // margin: 0 30px;
    flex: 1;
    .fs-iconfont {
      font-size: 25px;
      color: #4285f4;
      margin-bottom:2px;
      &.fs-iconfont-dache{
        // font-size:28px;
      }
      &.fs-iconfont-jiudian {
        color: #F5A623;
      }
      &.fs-iconfont-jipiao {
        color: #3cae69;
      }
    }
    .fs-title{
      font-size:14px;
        line-height:20px;
    }
  }
}

// 记一笔弹出框
.fs-popup-wrap {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.fs-popup-list li{
  margin: 0 auto 15px;
}
.fs-popup-list {
  padding-top: 10px;
  li {
    height: 75px;
    margin-bottom: 18px;
    text-align: left;
    width:76%;
    min-width: 270px;
    margin: 0 auto 15px;
    border-left: 4px solid;
    border-radius: 8px;
    border-top-left-radius: 5px;
    border-bottom-left-radius: 5px;
    padding-left: 30px;
    background-color: #fff;
    box-sizing: border-box;
    &:nth-child(1) {
      color: #3cae69;
      .fs-iconfont {
        color: #3cae69;
      }
    }
    &:nth-child(2) {
      color: #febe4d;
      .fs-iconfont {
        color: #febe4d;
      }
    }
    &:nth-child(3) {
      color: #4285f4;
      .fs-iconfont {
        color: #4285f4;
      }
    }
    &:nth-child(4) {
      color: #f27474;
      .fs-iconfont {
        color: #f27474;
      }
    }
    &:nth-child(5) {
      color: #3cae69;
      .fs-iconfont {
        color: #3cae69;
      }
    }
    .fs-iconfont {
      margin-right: 5px;
      font-size: 20px;
    }
    .fs-title {
      font-size: 18px;
      padding: 16px 0 10px;
      line-height: 20px;
      height: 20px;
      color: #333;
    }
    .fs-txt {
      font-size: 12px;
      color: #666;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
  }
}
.fs-popup-wrap .fs-btn-close {
  display: block;
  width: 45px;
  height: 45px;
  line-height: 45px;
  text-align: center;
  color: #fff;
  border-radius: 50%;
  background: #4285f4;
  box-shadow: 0 3px 5px 0 #82c0e4;
  font-size: 14px;
  margin: 6px auto;
}
// 弹出的遮罩层
body .fs-popup-mask {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background: transparent;
  filter: blur(3px);
}

// 修改Vux控件
body .weui-panel__hd:after {
  border-color: #eaeaea;
  left: 0;
}
body .weui-panel__hd{
  font-size: 14px;
  padding-top: 12px;
  padding-bottom:12px;
  color:#333;
}
// vux底部tab控件
body .weui-tabbar__label{
  line-height:14px;
  margin-top:5px;
}

// 底部模糊背景
.fs-body-index.fs-status-blur+.weui-tabbar{
  filter: blur(10px);
}
.el-upload{
  text-align: left !important;
}
</style>

