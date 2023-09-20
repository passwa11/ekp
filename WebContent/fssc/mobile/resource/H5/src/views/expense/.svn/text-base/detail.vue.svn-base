<template>
    <div class="fs-expense-detail-wrap" :class="{'fs-expense-editStatus':isEdit||isSelect}">
        <!-- 手工记快捷入口 -->
        <div v-show="!isSelect" class="fs-shortcut"  @click="showPop = true">
            <i class="fs-iconfont fs-iconfont-jiahao"></i>
        </div>
        <!-- 头部选项卡 -->
        <div class="fs-tab-head">
            <span v-for="(item,index) in filterOrder" class="fs-tab-item" :class="{'fs-active':index===nowIndex}" :key="index" @click="nowIndex=index;setOrder(item.keyword)">{{item.title}}</span>
            <div v-show="!isSelect" class="fs-link" @click="cancelSelect">{{isEdit?'取消':'选择'}}</div>
            <x-input v-model="orderby" value="" v-if="false"></x-input>
        </div>
        <!-- 卡片 -->
        <!-- 按时间排序 有数据的的情况展现 -->
        <div class="fs-expense-content-wrap" v-show="true">
            <!-- 本月 Starts -->
            <div class="fs-expense-title">
                <span class="fs-title">合计</span>
                <span class="fs-sum">￥<em>{{total}}</em></span>
                 <x-input v-model="fdNoteIds" v-if="false"></x-input>
            </div>
            <div class="fs-expense-content">
                <div class="fs-expense-card" v-for="(item,index) in expenseCard" :class="['fs-expense-card-'+item.type,item.state?'fs-status-selected':'']" :key="index">
                    <!-- 查看状态 Starts -->
                    <template v-if="!isEdit&&!isSelect">
                      <div @click.sync="editNote(item)" class="fs-expense-card-header">
                          <div class="fs-fl">
                              <div class="fs-icon">
                                  <i class="fs-iconfont" :class="item.type|typeClass"></i>
                              </div>
                              <div class="fs-txt">
                                  <p class="fs-type">{{item.title }}</p>
                                  <p class="fs-date">{{item.date}}</p>
                                  <x-input v-model="fdNoteId" v-if="false" :value="item.id"></x-input>
                              </div>
                          </div>
                          <div class="fs-fr">￥<em>{{item.price}}</em></div>
                      </div>
                      <div class="fs-expense-card-footer">
                          <p><em>城市：</em><span>{{item.city}}</span></p>
                          <p><em>事由：</em><span>{{item.reason}}</span></p>
                      </div>
                    </template>
                    <!-- 查看状态 Ends -->
                    <!-- 编辑状态 Starts -->
                    <div class="fs-expense-card-edit" v-else @click="choose(item)">
                      <div class="fs-expense-checkbox-box">
                        <i class="fs-com-check"></i>
                        <input type="text" v-model="item.state" @click="item.state=!item.state">
                        <!-- <input class="fs-com-check" type="checkbox" v-model="item.state" @click="item.state=!item.state"> -->
                      </div>
                      <div class="fs-expense-card-header">
                          <div class="fs-fl">
                              <div class="fs-icon">
                                  <i class="fs-iconfont" :class="item.type|typeClass"></i>
                              </div>
                              <div class="fs-txt">
                                  <p class="fs-type">{{item.title }}</p>
                                  <p class="fs-date">{{item.date}}</p>
                                  <x-input v-model="fdNoteId" v-if="false" :value="item.id"></x-input>
                              </div>
                          </div>
                          <div class="fs-fr">￥<em>{{item.price}}</em></div>
                      </div>
                      <div class="fs-expense-card-footer">
                          <p><em>城市：</em><span>{{item.city}}</span></p>
                          <p><em>事由：</em><span>{{item.reason}}</span></p>
                      </div>
                    </div>
                    <!-- 编辑状态 Ends -->
                </div>
            </div>
            <!-- 本月 Ends -->
        </div>
        <!-- 暂无数据 Starts -->
        <div class="fs-no-data" v-show="false">
            <div class="fs-icon">
                <img src="../../assets/images/noData.png" alt="">
                <p class="fs-title">
                    <slot>暂无数据</slot>
                </p>
            </div>
        </div>
        <!-- 暂无数据 Ends -->
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

        <!-- 底部操作条 Starts -->
        <div class="fs-footer-bar">
          <div class="fs-fl">
            <label><input type="checkbox" v-model="checkAllFlag" @click="toggleCheckAll"><i class="fs-com-check"></i>全选</label>
            <div class="fs-link-button" v-show="!isSelect" @click="deleteCard">移除</div>
          </div>
          <div class="fs-fr">
             <span class="fs-sum">
              合计：<em><i>￥</i>{{totalPrice}}</em>
            </span>
            <!-- 创建报销单 -->
            <x-button v-if="!isSelect" type="primary" link="" @click.native="showCate = true" :disabled="checkedCount===0">创建报销单</x-button>
            <!-- 选择费用 -->
             <x-button v-if="isSelect" class="fs-btn-comfirm" type="primary" link="" @click.native="selectFromExpense(false)" :disabled="checkedCount===0">确定</x-button>
          </div>
        </div>
        <!-- 底部操作条 Ends -->
        <!-- 移除确认弹出框  -->
        <div v-transfer-dom>
          <confirm v-model="showConfirm"
          title="确定" @on-confirm="deleteFee">
            <p style="text-align:center;">{{ '此操作将移除该费用，是否继续' }}</p>
          </confirm>
        </div>
        <!-- 还没有选中弹出框  -->
        <div v-transfer-dom>
          <confirm v-model="showConfirm_noData"
          title="操作提示">
            <p style="text-align:center;">{{ '您没有选中需要操作的数据' }}</p>
          </confirm>
        </div>
        <div v-transfer-dom>
          <popup v-model="showCate" class="projectPopUp">
            <popup-header
              @on-click-left="showCate = false"
              @on-click-right="selectFromExpense(true)"
              left-text="取消"
              right-text="完成"
              title="选择模板">
            </popup-header>
            <picker :data="cateData" v-model="currentCateId">
            </picker>
          </popup>
        </div>
        <loading :show="showLoading" :text="loadingMessage"></loading>
        <toast v-model="showShortTips" :time="2000">{{tipMessage}}</toast>
        <toast v-model="showCancel" type="cancel" :time="1000">{{cancelMessage}}</toast>
    </div>
</template>

<script>
import kk from 'kkjs'
import dd from 'dingtalk-jsapi'
import axios from 'axios'
export default {
  name: "ExpenseDetail",
  data() {
    return {
      action:serviceUrl+'saveAtt&cookieId='+LtpaToken,
      nowIndex: 0,
      filterOrder: [
      {
        title:"按时间排序",
        keyword:'dateTime'
      }, 
      {
        title:"按类型排序",
        keyword:'expenseType'
      }
      ],
      isEdit: false,
      isSelect: false,
      showConfirm: false,
      showConfirm_noData: false,
      expenseCard: [],
      fdNoteId: "",
      fdNoteIds: '',
      tipMessage:'',
      showShortTips:false,
      showCancel:false,
      cancelMessage:'',
      showLoading:false,
      loadingMessage:'',
      total:0.00,
      showPop:false,//是否显示遮罩-手工记
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
      orderby:'up',
      currentCateId:[],
      cateData:[],
      showCate:false,
      fdStartPlace:'',
      fdTemplateId:'',
      fdCompanyId:''
    };
  },
  // filters: {
  //   typeClass: function(value) {
  //     if (value === "taxi") {
  //       return "fs-iconfont-dache"
  //     } else if (value === "hotel") {
  //       return "fs-iconfont-jiudian"
  //     } else if (value === "airplane") {
  //       return "fs-iconfont-jipiao"
  //     } else {
  //       return "fs-iconfont-qianbi1"
  //     }
  //   }
  // },
  mounted() {
    // 通过路由参数来判断状态
    let type = this.$route.query.type;
    this.isSelect = type === "select";
    this.isEdit = false;
    this.getExpenseNote();
    kk.app.on('back',()=>{
      if(!this.isSelect){
        this.$router.push({name:'index'})
        return false;
      }
    })
  },
  activated(){
    this.fdTemplateId = this.$route.query.fdTemplateId||'';
    this.fdCompanyId = this.$route.query.fdCompanyId||'';
    // 通过路由参数来判断状态
    let type = this.$route.query.type;
    this.isSelect = type === "select";
    this.isEdit = false;
    this.getExpenseCateList();
    this.getNoteCreateList();
    this.getExpenseNote();
    kk.app.on('back',()=>{
      if(!this.isSelect){
        this.$router.push({name:'index'})
        return false;
      }
    })
  },
  methods: {
    getExpenseCateList(){
      this.$vux.loading.show({'text':'正在加载可用模板'})
      this.$api.post(serviceUrl+'getExpenseCateList',  { param: {} })
        .then((resData) => {
          this.cateData=resData.data.data;
          this.$vux.loading.hide()
      }).catch(function (error) {
       console.log(error);
      })
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
    beforeUpload(file){
      this.$vux.loading.show({'text':'上传中...'});
      this.showPop = false;
      let fd = new FormData();
      fd.append('file',file);//传文件
      axios.post(this.getAction(),fd,{headers: {'Content-Type': 'multipart/form-data'}}).then((res)=>{
        this.$refs.newUpload[0].clearFiles();
        this.$vux.loading.hide();
        this.$vux.loading.show({'text':'识别中...'})
        this.getInvoiceInfoFromRayky(res.data.fdId)
      }).catch((err)=>{
        this.$refs.newUpload[0].clearFiles()
        this.$vux.loading.hide();
        this.$vux.toast.show({'text':'上传失败'});
        console.log(err)
      })
      return false;
    },
    onUploadSuccess(resp,file,list){
      // this.$vux.loading.hide();
      // this.$vux.loading.show({'text':'识别中...'})
      // this.getInvoiceInfoFromRayky(resp.fdId)
    },
    getAction(file){
      return this.action+LtpaToken+"&fdModelName=com.landray.kmss.fs.expense.model.FsExpenseNote&filename=pic_"+new Date().getTime()+'.jpg';
    },
    selectFromExpense(create){
      let ids = [];
      for(let i=0;i<this.expenseCard.length;i++){
        if(this.expenseCard[i].state){
          ids.push(this.expenseCard[i].id);
        }
      }
      this.$api.post(serviceUrl+'getNoteByIds&cookieId='+LtpaToken+'&ids='+ids.join(';'),{}).then(res=>{
        this.$router.push(
          {
            name:'expenseNew',
            query:{
              fdTemplateId:this.currentCateId[0]
            },
            params:{
              dataJsonArray:res.data.data,
              create:create,
              fdTemplateId:this.currentCateId[0]
            }
          }
        );
      }).catch(err=>{

      });
    },
    linkThird(url){
      if(url.indexOf('method') > 1){
        window.open('http://219.134.186.38:8888/ekp'+url+'&cookieId='+LtpaToken,'_self');
      }
      else {
        this.$router.push({path:url});
      }
    },
    // 编辑状态切换
    switchStatus() {
      isEdit = !isEdit;
    },
    // 切换全选反选
    toggleCheckAll() {
      let flag = !this.checkAllFlag;
      this.expenseCard.forEach(item => {
        item.state = flag;
      });
    },
    // 移除操作
    deleteCard() {
      if (this.checkedCount < 1) {
        // 没有选中项
        this.showConfirm_noData = true;
      } else {
        // 提示删除confirm弹出框
        this.showConfirm = true;
      }
    },
    setOrder(keyword){
      let ids = this.$route.query.ids;
      let param=new URLSearchParams();
      param.append('keyword',keyword);
      param.append('orderby',this.orderby);
      this.$api
        .post(serviceUrl + "getExpenseNote&fdTemplateId=" + this.fdTemplateId+'&ids='+ids+'&fdCompanyId='+this.fdCompanyId, param)
        .then(resData => {
          if (resData.data.result == "success") {
            this.expenseCard = resData.data.data;
            let sum=0.00;
              for(var i= 0 ;i< this.expenseCard.length; i++) {
                sum = Number(sum*1.00+this.expenseCard[i].price*1.00).toFixed(2);
              }
              this.total=sum;
              this.orderby=this.orderby=='up'?'down':'up';
            console.log('获取随手记费用的列表',this.expenseCard)
          } else {
            console.log(resData.data.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    // 获取随手记列表
    getExpenseNote() {
      let ids = this.$route.query.ids;
      this.$api
        .post(serviceUrl + "getExpenseNote&fdTemplateId=" + this.fdTemplateId+'&ids='+ids+'&fdCompanyId='+this.fdCompanyId, {
          param: {}
        })
        .then(resData => {
          if (resData.data.result == "success") {
            this.expenseCard = resData.data.data;
            let sum=0.00;
              for(var i= 0 ;i< this.expenseCard.length; i++) {
                sum = Number(sum*1.00+this.expenseCard[i].price*1.00).toFixed(2);
              }
              this.total=sum;
            console.log('获取随手记费用的列表',this.expenseCard)
          } else {
            console.log(resData.data.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    // 取消选择
    cancelSelect() {
      // 从编辑状态取消，需要清空选择
      if (this.isEdit) {
        if (this.expenseCard.length > 0) {
          this.expenseCard.forEach(item => {
            if (item.state) {
              item.state = false;
            }
          });
        }
      }
      this.isEdit = !this.isEdit;
    },
    editNote(item){
      console.log(item)
      this.$router.push({name:'NewManual',params:{fdNoteId:item.id}});
    },
    // 选择事件
    choose(item){
      item.state = !item.state
      console.log(item.state)
    },
    deleteFee(){
      this.showLoading = true;
      this.$api.post(serviceUrl+'deleteExpenseNote&cookieId='+LtpaToken+'&ids='+this.fdNoteIds,{}).then(resData=>{
        this.showLoading = false;
        if(resData.data.result=='success'){
          this.tipMessage = '操作成功'
          this.showShortTips  =true;
          this.getExpenseNote();
        }else{
          this.cancelMessage = '操作失败'
          this.showCancel  =true;
        }
      }).catch(err=>{
        this.showLoading = false;
        console.log(err)
      });
    },
     byHand(){
      this.$router.push({name:'NewManual',params:{flag:'isnew'}});
    },
    scanQrCode(){
      if(kk.isKK()){
      	this.scanQrCodeInKK();
      }else{
      	this.scanQrCodeInDD();
      }
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
            this.uploadPicture(path,filename,resData.data.fdId)
            //this.$router.push({name:'NewManual',params:{path:path,filename:filename,fdId:resData.data.fdId,flag:'isnew'}});
          }).catch(function (error) {
          console.log(error);
        })

      })
    },
    uploadPicture(path,filename,id,index,attachment){
      var proxy = new kk.proxy.Upload({
        url:serviceUrl+'saveAtt&fdModelName=com.landray.kmss.fs.expense.model.FsExpenseNote&cookieId='+LtpaToken+'&filename='+filename+'&fdId='+id,
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
            this.getExpenseNote();
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
            this.getExpenseNote();
          }else{
            this.$vux.loading.hide()
            alert('保存随手记失败：'+resData.data.message)
          }
          //保存成功回调
          
          
        }).catch(function (error) {
          alert('识别异常：'+JSON.stringify(error));
          this.$vux.loading.hide()
        console.log(error);
      })
    }
  
  },
  computed: {
    // 总价
    totalPrice() {
      let money = 0;
      this.fdNoteIds = "";
      if (this.expenseCard.length > 0) {
        this.expenseCard.forEach(item => {
          if (item.state) {
            money += parseFloat(item.price);
            //拼接选中id
            this.fdNoteIds += item.id + ";";
          }
        });
      }
      return money.toFixed(2);
    },
    checkedCount() {
      let i = 0;
      if (this.expenseCard.length > 0) {
        this.expenseCard.forEach(item => {
          if (item.state) {
            i++;
          }
        });
      }
      return i;
    },
    checkAllFlag() {
      if (this.expenseCard.length > 0) {
        return this.checkedCount === this.expenseCard.length;
      } else {
        this.fdNoteIds = "";
        return false;
      }
    },
    
  }
};
</script>

<style lang="less">
@import "../../assets/css/variable.less";
// 手工记入口
.fs-shortcut{
  position:fixed;
  bottom: 60px;
  right: 15px;
  width: 50px;
  height: 50px;
  line-height: 50px;
  text-align: center;
  background-color: @mainColor;
  border-radius: 50%;
  z-index: 4;
  font-size: 20px;
  color:#fff;
  .fs-iconfont{
    font-size: 36px;
  }
}
.fs-expense-detail-wrap {
  padding-top: 40px;
  padding-bottom: 130px;
  em {
    font-style: normal;
  }
  font-size: 14px;

  // 头部菜单卡片
  .fs-tab-head {
    position: fixed;
    left:0;
    right:0;
    top:0;
    z-index: 2;
    height: 42px;
    line-height: 42px;
    padding: 0 10px;
    overflow: hidden;
    background: #fff;
    border-bottom: 1px solid #eee;
    .fs-tab-item {
      display: inline-block;
      margin-right: 15px;
      color: #999;
      &.fs-active {
        color: @mainColor;
      }
    }
    .fs-link {
      float: right;
      padding-left: 14px;
      color: #666;
      &:before {
        content: "";
        display: inline-block;
        position: relative;
        top: 4px;
        padding: 9px 0;
        margin-right: 10px;
        border-left: 1px solid @borderColor;
      }
    }
  }
  // 列表内容
  .fs-expense-content-wrap {
    position: relative;
    z-index: 1;
    .fs-expense-title {
      height: 37px;
      line-height: 37px;
      padding: 0 10px;
      display: flex;
      justify-content: space-between;
      .fs-title {
        font-size: 14px;
        color: #333;
      }
      .fs-sum {
        color: #999;
        font-size: 13px;
        em {
          font-size: 14px;
        }
      }
    }
    .fs-expense-content {
      .fs-expense-card {
        background: #fff;
        margin-bottom: 5px;
        // border-top: 1px solid #eee;
        // border-bottom: 1px solid #eee;
        position: relative;
        label{
          display: block;
        }
        &.fs-status-selected {
          background-color: rgba(66, 133, 244, 0.2);
        }
        .fs-icon {
          border: 1px solid @mainColor;
          color: @mainColor;
        }
        &-taxi {
          .fs-icon {
            border-color: @mainColor;
            color: @mainColor;
          }
          .fs-type {
            color: @mainColor;
          }
        }
        &-hotel {
          .fs-icon {
            border-color: #F5A623;
            color: #F5A623;
          }
          .fs-type {
            color: #F5A623;
          }
        }
        &-airplane {
          .fs-icon {
            border-color: #3cae69;
            color: #3cae69;
          }
          .fs-type {
            color: #3cae69;
          }
        }
        // 复选框
        .fs-expense-checkbox-box {
          display: none;
          position: absolute;
          left: 5px;
          top: 15px;
          border: 10px solid transparent;
        }
        &-header {
          border-bottom: 1px solid @borderColor;
          padding: 12px;
          overflow: hidden;
          .fs-date {
            color: #999;
          }
          .fs-fl {
            float: left;
            .fs-icon {
              display: inline-block;
              width: 39px;
              height: 39px;
              line-height: 39px;
              border-radius: 50%;
              text-align: center;
              .fs-iconfont {
                font-size: 22px;
              }
            }
            .fs-txt {
              display: inline-block;
              vertical-align: top;
              .fs-type {
                font-size: 18px;
                line-height: 25px;
              }
              .fs-date {
                font-size: 12px;
                color: #999;
              }
            }
          }
          .fs-fr {
            float: right;
            em {
              font-size: 21px;
            }
          }
        }
        &-footer {
          padding: 10px;
          p {
            font-size: 13px;
            color: #2a2a2a;
            line-height: 20px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            em {
              font-style: normal;
              color: #333;
            }
          }
        }
      }
    }
  }

  // 底部操作条
  .fs-footer-bar {
    position: fixed;
    bottom: 0;
    width: 100%;
    height: 50px;
    line-height: 50px;
    border-top: 1px solid #ddd;
    background-color: #f7f7f7;
    left: 0;
    display: none;
    z-index: 2;
    justify-content: space-between;
    .fs-fl {
      padding-left: 10px;
      label {
        color: #999;
        border: 5px solid transparent;
        .fs-com-check{
          position: relative;
          top: 2px;
          margin-right:3px;
        }
        input {
          display:none;
          margin-right: 5px;
          vertical-align: middle;
          position: relative;
          top: -1px;
        }
        margin-right: 15px;
      }
      .fs-link-button {
        display: inline-block;
        color: #ee5555;
      }
    }
    .fs-fr {
      // float: right;
      .weui-btn {
        font-size: 14px;
        display: inline-block;
        width: auto;
        min-width: 100px;
        &.fs-btn-comfirm {
          letter-spacing: 4px;
        }
      }
      .fs-sum {
        // float: left;
        margin-right: 10px;
        white-space: nowrap;
        font-size: 13px;
        em {
          color: @mainColor;
          font-size: 16px;
        }
      }
    }
  }
}
// 编辑状态
.fs-expense-card-edit {
  // padding-bottom: 60px;
  input[type="text"]{
    display: none;
  }
}
.fs-expense-editStatus{
  // padding-bottom:60px;
}
.fs-expense-editStatus .fs-expense-card {
  padding-left: 45px;
}
.fs-expense-detail-wrap.fs-expense-editStatus
  .fs-expense-content-wrap
  .fs-expense-content
  .fs-expense-card
  .fs-expense-checkbox-box {
  display: block;
}
.fs-expense-detail-wrap.fs-expense-editStatus .fs-footer-bar {
  display: flex;
}
// 卡片

// 公共复选框样式
.fs-com-check,input.fs-com-check {
  display: inline-block;
  width: 15px;
  height: 15px;
  background-image: url(../../assets/images/icon/icon-check.png);
  background-position: center center;
  background-repeat: no-repeat;
  background-size: 15px 15px;
  background-color: transparent;
  display: inline-block;
  outline: none;
  -webkit-appearance: none;
  border-radius: 0;
  border-color:#666;
}
.fs-status-selected .fs-com-check,
input:checked +.fs-com-check{
  background-image: url(../../assets/images/icon/icon-check-cur.png);
}
</style>


