<template>
  <div class="manual-new-wrap">
    <group gutter="0">
       <x-input v-model="fdId" v-if="false"></x-input>
      <x-textarea  :required="true" :rows="2" title="消费事由" placeholder="请输入消费事由" v-model="fdDesc">
      </x-textarea>
      <popup-picker title="成本中心" v-model="currentDeptIds" :data="costCenterList"  :columns="3" @on-change="afterCostCenter" placeholder="请选择成本中心" ref="pickerCostCenter" show-name></popup-picker>
      <x-input v-model="fdCompanyId" v-if="false"></x-input>

      <x-input
        title="消费城市"
        placeholder="请选择消费城市"
        :value="fdEndAreaName"
        v-model="fdEndAreaName"
        @click.native="areaPop = true"
        readonly
        class="is-link"
        :class="{
          'empty': fdEndAreaName === ''
        }">
      </x-input>

      <!-- <popup-picker  title="消费城市" v-model="fdEndArea" @on-change="setEndPlace" :data="placeList" :columns="2" :display-format="formatName" placeholder="请选择消费城市" show-name></popup-picker> -->
      <Datetime    title="消费时间" v-model="fdHappenDate" placeholder="请输入消费时间"></Datetime>
      <!--<popup-picker title="费用类型" v-model="currentType" :data="itemList" :columns="1" placeholder="请选择" show-name></popup-picker>-->
      <x-input
        title="费用类型"
        placeholder="请选择费用类型"
        :value="fdExpenseItemName"
        v-model="fdExpenseItemName"
        @click.native="itemPopUp = true"
        readonly
        class="is-link"
        :class="{
          'empty': fdExpenseItemName === ''
        }">
      </x-input>
      <x-input v-model="fdExpenseItemId" v-if="false"></x-input>
      <x-input  title="总费用" placeholder="请输入金额" v-model="total"></x-input>
    </group>
    

    <div class="fs-travel-wrap">
      <group gutter="0">
        <cell title="发票明细">
          <router-link to="" class="fs-link" @click.native="scanQrCodeInDD">
            <span class="fs-iconfont fs-iconfont-add"></span>添加明细
          </router-link>
        </cell>
        <div class="fssc-swipe-div" style="border-top: 1px solid #e2e2e2;" v-for="(item,index) in invoices">
          <swipeout-item :threshold=".5" underlay-color="#ccc">
            <div slot="right-menu" >
              <swipeout-button @click.native="removeInvoice(index)" background-color="#D23934">移除</swipeout-button>
            </div>
            <div slot="content" v-on:click="showInvoice(item,index)" class="swipe-content">
              {{item.title}}(<i v-if="item.state=='0'" style="color:red">已验真</i><i style="color:red" v-else>异常</i>)
            </div>
          </swipeout-item>
        </div>
        <!-- <cell :title="item.title" :key="index" v-for="(item,index) in invoices" :value="item.value" :link="{path:'/invoice/detail',query:{id:item.id}}" is-link>
          <span slot="title">
            {{item.title}}
            (<i v-if="item.state=='0'" style="color:red">已验真</i><i style="color:red" v-else>异常</i>)
          </span>
        </cell> -->
      </group>
    </div>

    <div class="fs-travel-wrap">
      <group gutter="0">
        <cell title="附件">
          <el-upload
            :action="action"
            :auto-upload="true"
            :file-list="fileList"
            :show-file-list="false"
            :before-upload="beforeUpload"
            :with-credentials="true"
            multiple>
            <span class="fs-iconfont fs-iconfont-fujian"></span>点击上传
          </el-upload>
        </cell>
        <div class="fssc-swipe-div" style="border-top: 1px solid #e2e2e2;" v-for="(item,index) in attachments">
          <swipeout-item :threshold=".5" underlay-color="#ccc">
            <div slot="right-menu" >
              <swipeout-button @click.native="removeAttr(index)" background-color="#D23934">移除</swipeout-button>
            </div>
            <div slot="content" v-on:click="showAtt(item,index)" class="swipe-content">
              {{item.title}}
            </div>
          </swipeout-item>
        </div>
      </group>
    </div>

    <div v-transfer-dom>
      <popup v-model="showPop">
        <div class="manual-pop-wrap">
          <ul class="clearfix">
            <li class="fs-col-8" v-for="(item,index) in popupIcon" :key="index" @click.sync="item.click">
              <span  class="fs-iconfont-wrap"><i class="fs-iconfont" :class="item.icon"></i></span>
              <p class="fs-title">{{item.title}}</p>
            </li>
          </ul>
        </div>
      </popup>
    </div>
    <!-- 费用类型选择 Start -->
    <div v-transfer-dom>
      <popup v-model="itemPopUp" class="feeTypePopUp">
        <popup-header
          @on-click-left="clearValue('itemPopUp','fdExpenseItem','keyword_item')"
          @on-click-right="selectItem"
          left-text="清空"
          right-text="完成">
        </popup-header>
        <search
          @on-submit="getFsBaseExpenseItem"
          v-model="keyword_item"
          @on-cancel="getFsBaseExpenseItem"
          @on-clear="getFsBaseExpenseItem"
          @on-change="getFsBaseExpenseItem"
          :auto-fixed="false">
        </search>
        <picker
          :data="itemList"
          v-model="fdExpenseItem"
          :columns="1">
        </picker>
      </popup>
    </div>
    <div v-transfer-dom>
      <popup v-model="areaPop" class="feeTypePopUp">
        <popup-header
          @on-click-left="clearValue('areaPop','fdEndArea','keyword_area')"
          @on-click-right="selectArea"
          left-text="清空"
          right-text="完成">
        </popup-header>
        <search
          @on-submit="getCityData"
          v-model="keyword_area"
          @on-cancel="getCityData"
          @on-clear="getCityData"
          @on-change="getCityData"
          :auto-fixed="false">
        </search>
        <picker
          :data="placeList"
          v-model="fdEndArea"
          :columns="1">
        </picker>
      </popup>
    </div>
    <!-- 费用类型选择 End -->

    <div v-transfer-dom>
      <popup v-model="showImage" height="100%" position="right" width="100%">
        <img :src="imgSrc" style="width:100%;height:100%;" v-on:click="showImage = false">
      </popup>
    </div>

    <toast v-model="showText" type="text">{{textMessage}}</toast>

    <div class="bottom-wrap">
      <x-button type="primary" @click.native="saveExpenseNote">提交</x-button>
    </div>
  </div>
</template>

<script>
import { ChinaAddressV4Data } from 'vux'
import fsUpload from '@/components/src/fsUpload'
import kk from 'kkjs'
import dd from 'dingtalk-jsapi'
import axios from 'axios'
export default {
  name: "manualNew",
  components: {
    fsUpload
  },
  data() {
    return {
      action:'saveAtt',
      action:serviceUrl+'',
      fdId:'',
      showPop: false,
      feeTypePopUp: false,  // 费用类型选择弹窗
      fdHappenDate:"",
      total: "",
      fdDesc:"",
      fdCompanyId:"",
      currentDeptNames: [],
      currentDeptIds:[],
      feeType: '',  // 当前的费用类型
      fdExpenseItemId:'',
      fdExpenseItem:[],
      currentType: [],  // 费用类型选择器的当前类型
      addressData: ChinaAddressV4Data,
      itemList: [],
      costCenterList: [],
      placeList:[],
      fdEndArea:[],
      invoices: [],
      attachments:[],
      popupIcon: [
        // {
        //   icon: "fs-iconfont-xuanzetupian",
        //   title: "选择图片",
        //   click:this.selectPicture
        // },
        {
          icon: "fs-iconfont-fapiaosaomiao",
          title: "发票扫描",
          click:this.scanQrCodeInDD
        },
        // {
        //   icon: "fs-iconfont-paizhaoshibie",
        //   title: "拍照识别",
        //   click:this.getPicture
        // },
        {
          icon: "fs-iconfont-daorufapiao",
          title: "导入发票"
        },
        {
          icon: "fs-iconfont-disanfangmingxi",
          title: "第三方明细",
          click:this.exportThirdInvoice
        }
      ],
      keyword:'',
      fdExpenseItemName:'',
      imgSrc:'',
      showImage:false,
      showText:false,
      textMessage:'',
      fdEndAreaId:'',
      fdEndAreaName:'',
      areaPop:false,
      keyword_area:'',
      keyword_item:'',
      itemPopUp:false
    };
  },
  activated (){
    if(this.$route.params.flag=='isnew'){
      this.clearData();
    }
    this.getFsBaseCostCenter();
    this.getDefaultInvoice();
    if(this.$route.params.fdNoteId){
      this.getNoteData(this.$route.params.fdNoteId);
    }else{
      this.getGeneratorId();
    }
    
  },
   //初始化函数
  mounted () { 
  },
  methods:{
    clearValue(pop,name,key){
      this[pop] = false;
      this[key] = '';
      this[name] = [];
      this[name+'Id'] = '';
      this[name+'Name'] = '';
    },
    showInvoice(item){
      this.$router.push({path:'/invoice/detail',query:{id:item.id}})
    },
    removeAttr(index){
      this.$vux.loading.show();
      this.$api.post(serviceUrl+'deleteAtt&fdId='+this.attachments[index].value,{ param: {} }).then((resData) => {
        this.$vux.loading.hide();
        if(resData.data.result=='success'){
          this.attachments.splice(index,1)
        }else{
          this.$vux.toast.show({'text':resData.data.message,type:'cancel',time:1000})
        }
      }).catch( (error)=> {
        this.$vux.loading.hide();
        this.$vux.toast.show({'text':'操作异常:'+JSON.stringify(error),type:'cancel',time:1000})
        console.log(error);
      })
      
    },
    removeInvoice(index){
      this.$vux.loading.show();
      this.$api.post(serviceUrl+'deleteInvoice&fdId='+this.invoices[index].id,{ param: {} }).then((resData) => {
        this.$vux.loading.hide();
        if(resData.data.result=='success'){
          let money = this.invoices[index].value;
          if(!isNaN(money)){
            this.total -= money*1;
          }
          this.invoices.splice(index,1)

        }else{
          this.$vux.toast.show({'text':resData.data.message,type:'cancel',time:1000})
        }
      }).catch( (error)=> {
        this.$vux.loading.hide();
        this.$vux.toast.show({'text':'操作异常:'+JSON.stringify(error),type:'cancel',time:1000})
        console.log(error);
      })
    },
    selectItem() {
      this.keyword_item = '';
      this.itemPopUp = false;
      this.getFsBaseExpenseItem();
      for(let i=0;i<this.itemList.length;i++){
        if(this.itemList[i].value==this.fdExpenseItem[0]){
          this.fdExpenseItemName = this.itemList[i].name;
          this.fdExpenseItemId = this.itemList[i].value;
          return;
        }
      }
    },
    selectArea(){
      this.keyword_area= '';
      this.areaPop = false;
      this.getCityData();
      for(let i of this.placeList){
        if(i.value==this.fdEndArea[0]){
          this.fdEndAreaName = i.name;
          this.fdEndAreaId = i.value;
          return;
        }
      }
    },
    setDefaultPlace(){
      let geo = new BMap.Geolocation();
      let _ = this;
      geo.getCurrentPosition(function(r){
        if(this.getStatus()==BMAP_STATUS_SUCCESS){
          for(let i of _.placeList){
            if(i.name.indexOf(r.address.city)>-1||i.name.indexOf(r.address.province)>-1){
              _.fdEndAreaName = i.name;
              _.fdEndArea =[i.value];
              _.fdEndareaId = i.value;
            }
          }
        }
      })
     //获取随手记随机ID
    },
    beforeUpload(file){
      this.$vux.loading.show({'text':'附件上传中...'});
      this.showPop = false;
      let fd = new FormData();
      fd.append('file',file);//传文件
      axios.post(serviceUrl+"saveAtt&filename="+file.name+"&fdModelName=com.landray.kmss.fssc.mobile.model.FsscMobileNote&fdModelId="+this.fdId,fd,{headers: {'Content-Type': 'multipart/form-data'}}).then((res)=>{
        let filename = file.name.length>20?(file.name.substring(0,10)+"..."+file.name.substring(file.name.length-7)):file.name;
        this.attachments.push({value:res.data.fdId,title:filename})
        this.$vux.loading.hide();
        this.$vux.toast.show({'text':'上传完成',type:'success',time:1000})

      }).catch((err)=>{
        this.$vux.loading.hide();
        this.$vux.toast.show({'text':'上传失败'});
        console.log(err)
      })
      return false;
    },
    //从首页进来的话先清空数据
    clearData(){
      this.fdId='';
      this.total='';
      this.fdDesc='';
      this.currentDeptIds=[];
      this.fdEndArea=[];
      this.fdHappenDate=this.formatDate(new Date());
      this.fdExpenseItemId='';
      this.fdExpenseItemName='';
      this.invoices=[];
      this.attachments=[];
      this.getDefaultCost();

    },
    formatDate(date){
      let year = date.getFullYear();
      let month = date.getMonth()+1;
      let day = date.getDate();
      if(month<10){
        month = "0"+month;
      }
      if(day<10){
        day = "0"+day;
      }
      return year+"-"+month+"-"+day;
    },
    getDefaultCost(){
      this.$api.post(serviceUrl+'getDefaultCost',{ param: {} }).then((resData) => {
        if(resData.data.data.length>0){
          this.fdCompanyId = resData.data.data[0].fdCompanyId;
          this.currentDeptIds = [this.fdCompanyId,resData.data.data[0].id]
        }
      }).catch( (error)=> {
        console.log(error);
      })
    },
    setEndPlace(val){
      for(let i of this.placeList){
        if(i.value==val[0]){
          this.fdEndPlace = i.name;
          return;
        }
      }
    },
    showAtt(item,index){
      console.log(item)
      if(item.value!=null&&item.value.indexOf('上传中')>-1){
        this.showText = true;
        this.textMessage  = '照片正在上传，请稍候再操作'
        return;
      }
      this.showImage = true;
      this.imgSrc = domainPrefix+'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=viewPic&cookieId='+LtpaToken+'&fdId='+item.value;
    },
    getNoteData(id){
      this.$api.post(serviceUrl+'getNoteData&cookieId='+LtpaToken+'&fdId='+id,  { param: {} })
        .then((resData) => {
        this.fdId=id;
        let data = resData.data.data[0];
        this.fdHappenDate = data.fdHappenDate;
        this.fdDesc = data.fdDesc;
        this.currentDeptIds = [data.fdCompanyId,data.fdCostCenterId];
        let area=[];
        this.fdCompanyId = data.fdCompanyId;
        this.fdEndArea=[data.fdEndArea];
        this.fdEndAreaId = data.fdEndArea;
        this.fdEndAreaName = data.fdEndPlace
        if(data.fdExpenseItemId){
          this.fdExpenseItemId = data.fdExpenseItemId;
          this.fdExpenseItem = [data.fdExpenseItemId]
          this.fdExpenseItemName = data.fdExpenseItemName;
        }else{
          this.fdExpenseItemId = ''
          this.fdExpenseItem = []
          this.fdExpenseItemName = ''
        }
        
        this.total = data.total;
        this.invoices = data.invoices;
        this.attachments = data.attachments;
        this.getCityData();
        this.getFsBaseExpenseItem();
      }).catch(function (error) {
        console.log(error);
      })
    },
    //获取随手记随机ID
    getGeneratorId(){
      if(this.fdId){
        return;
      }
      this.$api.post(serviceUrl+'getGeneratorId&cookieId='+LtpaToken,  { param: {} })
        .then((resData) => {
              this.fdId=resData.data.fdId;
          }).catch(function (error) {
       console.log(error);
      })
    },
    //获取成本中心
     getFsBaseCostCenter(){
      this.$api.post(serviceUrl+'getCostCenterForNote&cookieId='+LtpaToken, { param: {} })
        .then((resData) => {
              this.costCenterList=resData.data.data;
          }).catch(function (error) {
       console.log(error);
      })
    },
    formatName(value, name) {
      return name.split(' ')[name.split(' ').length - 1]
    },
    selectFeeType() {
       this.fdExpenseItemId= this.currentType[0];
      this.fdExpenseItemName=this.$refs.itemPicker&&this.$refs.itemPicker.getNameValues();
      this.feeTypePopUp = false;
    },
    afterCostCenter(value){
      if(value.length>0){
        this.fdCompanyId=value[0];
        this.getCityData();
        this.getFsBaseExpenseItem();
      }
    },
     //获取费用类型
    getFsBaseExpenseItem() {
      let params = new URLSearchParams();
      params.append('fdCompanyId', this.fdCompanyId);
      params.append('flag','note');
     
       this.$api.post(serviceUrl+'getFsscBaseExpenseItem&keyword='+this.keyword_item, params)
       .then((resData) => {
              this.itemList=resData.data.data;
          }).catch(function (error) {
       console.log(error);
      })
   },
   //获取城市列表
    getCityData() {
      let params = new URLSearchParams();
      params.append('fdCompanyId', this.fdCompanyId);
      params.append('flag','note');
      this.$api.post(serviceUrl+'getCityData&keyword='+this.keyword_area, params)
       .then((resData) => {
          this.placeList=resData.data.data;
          if(!this.fdEndAreaId){
            this.setDefaultPlace();
          }
      }).catch(function (error) {
       console.log(error);
      })
   },
  addInvoiceDetail(index){
    //对应的顺序不能变，后续加在后面加即可，中间插需调整index对应的函数
    if(index=='4'){
      this.exportThirdInvoice();
    }
   },
   //导入发票，目前随机生成发票数据
   exportThirdInvoice(){
      let params = new URLSearchParams();
      params.append('flag','note');
      params.append('fdModelId',this.fdId);
       this.$api.post(serviceUrl+'exportThirdInvoice&cookieId='+LtpaToken,params)
       .then((resData) => {
        if(resData.data.result=='success'){
            this.invoices=resData.data.data;
            this.showPop=false;
        }
          }).catch(function (error) {
       console.log(error);
      })
   },
  getDefaultInvoice(){
    //扫码
    if(this.$route.params.invoiceData){
      this.invoices.push(this.$route.params.invoiceData)
      this.total = this.$route.params.invoiceData.value;
    }
  },
  uploadPicture(path,filename,id,index,attachment){
    var proxy = new kk.proxy.Upload({
      url:serviceUrl+'saveAtt&fdModelName=com.landray.kmss.fssc.mobile.model.FsscMobileNote&cookieId='+LtpaToken+'&fdModelId='+this.fdId+'&filename='+filename+'&fdId='+id,
      path:path
    },(res)=>{
      if(res.progress==100){
        if(attachment==null){
          this.attachments[index].value = '识别中';
          this.getInvoiceFromAtt(id,index);
        }else{
          this.attachments[index].value = '上传成功';
        }
        
      }else{
        this.attachments[index].value = '上传中：'+res.progress+'%';
      }
    },(code,message)=>{
      alert("code:"+code+",message："+message);
    })
    // 开始上传
    proxy.start();
  },
  getInvoiceFromAtt(id,index){
    this.$api.post(serviceUrl+'getInvoiceFromAtt&fdId='+id+'&cookieId='+LtpaToken,{}).then((resData)=>{
      if(resData.data.result=='success'){
        this.attachments[index].value = '识别完成';
        this.invoices.push(resData.data.data)
      }else if(resData.data.result=='failure'){
        this.attachments[index].value = '识别失败';
      }else{
        setTimeout(()=>{
          this.getInvoiceFromAtt(id,index);
        },5000);
      }
    })
  },
  saveExpenseNote() {
      let data = this.buildData();
      if(!data){
        return;
      }
      this.$api.post(serviceUrl+'createExpenseNote&params='+encodeURI(JSON.stringify(data)),
          {}
      ).then((resData) => {
        if(resData.data.result=='success'){
          this.$router.push({name:'expenseDetail'});
        }
          }).catch(function (error) {
       console.log(error);
      })
    },
     buildData(){
      let data = {};
      data.fdDesc = this.fdDesc;
      if(this.currentDeptIds.length>0){
        data.fdCostCenterId = this.currentDeptIds[this.currentDeptIds.length-1].split(';')[0];
      }
      
      data.fdCompanyId = this.fdCompanyId;
      data.fdEndPlace = this.fdEndAreaName;
      data.fdEndArea = this.fdEndArea[0];
      data.fdHappenDate=this.fdHappenDate;
      data.fdExpenseItemId=this.fdExpenseItemId;
      data.fdAcount=this.total;
      data.fdId=this.fdId;
      data.invoiceList=this.invoices;
      data.attachments = this.attachments;
      if(!data.fdDesc){
        this.$vux.toast.show({type:'warn',text:'请输入消费事由',time:2000});
        return;
      }
      if(!data.fdCostCenterId){
        this.$vux.toast.show({type:'warn',text:'请选择成本中心',time:2000});
        return;
      }
      if(!data.fdEndPlace){
        this.$vux.toast.show({type:'warn',text:'请选择消费城市',time:2000});
        return;
      }
      if(!data.fdHappenDate){
        this.$vux.toast.show({type:'warn',text:'请输入消费时间',time:2000});
        return;
      }
      if(!data.fdExpenseItemId){
        this.$vux.toast.show({type:'warn',text:'请选择费用类型',time:2000});
        return;
      }
      if(!data.fdAcount){
        this.$vux.toast.show({type:'warn',text:'请输入消费金额',time:2000});
        return;
      }
      return data;
    },
    selectPicture(){
      let filename = 'pic_'+new Date().getTime()+".jpg";
      kk.media.getPicture({
        sourceType: 'album',
        destinationType: 'file',
        encodingType: 'jpg',
        count: 1,
        chooseOrignPic: false // 选取原图
      },(res)=>{
        this.showPop = false;
        
        this.$api.post(serviceUrl+'getGeneratorId&cookieId='+LtpaToken,  { param: {} })
          .then((resData) => {
          this.attachments.push({
            title:filename,
            value:'上传中：0%',
            id:resData.data.fdId
          });
          this.uploadPicture(res[0].imageURI,filename,resData.data.fdId,this.attachments.length-1);
        });
      });
    },
    selectPictureAtt(attachment){
      let filename = 'pic_'+new Date().getTime()+".jpg";
      kk.media.getPicture({
        sourceType: 'album',
        destinationType: 'file',
        encodingType: 'jpg',
        count: 1,
        chooseOrignPic: false // 选取原图
      },(res)=>{
        this.showPop = false;
        this.$api.post(serviceUrl+'getGeneratorId&cookieId='+LtpaToken,  { param: {} })
          .then((resData) => {
          this.attachments.push({
            title:filename,
            value:'上传中：0%',
            id:resData.data.fdId
          });
          this.uploadPicture(res[0].imageURI,filename,resData.data.fdId,this.attachments.length-1,true);
        });
      });
    },
    scanQrCode(){
      kk.scaner.scanTDCode((res)=>{
        this.showPop = false;
        let code = res.code.split(",");
        let params = {
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
            this.invoices.push(resData.data.data);
          }else{
            alert(resData.data.message)
          }
        })
      });
    },
    scanQrCodeInDD(){
      dd.biz.util.scan({
        type: 'qrCode' , // type 为 all、qrCode、barCode，默认是all。
        onSuccess: (data1)=> {
          let numbers = [];
          for(let i of this.invoices){
            numbers.push(i.title.replace(/^.+\((\d+)\)\(.+\)$/,'$1'));
          }
          let code = data1.text.split(",");
          // let code = '01,01,4403183130,04077808,2038.83,20190122,,B539,'.split(",")
          let params = {
            fplx:code[1],
            fdInvoiceNo:code[3],
            fdInvoiceCode:code[2],
            fdInvoiceMoney:code[4],
            fdInvoiceDate:code[5],
            fdCheckCode:code[6].substring(code[6].length-6)
          }
          if(numbers.join(';').indexOf(params.fdInvoiceNo)>-1){
            this.$vux.toast.show({text:'发票已添加',type:'warn',time:1000});
            return;
          }
          let data = new URLSearchParams();
          data.append("fdInvoiceNo",JSON.stringify(params));
          this.$vux.loading.show("解析中")
          this.$api.post(serviceUrl+'addInvoiceInfoByQrCode&cookieId='+LtpaToken,data).then((resData) => {
            this.$vux.loading.hide();
            if(resData.data.result=='success'){
              this.invoices.push(resData.data.data);
              this.total = (this.total*1+resData.data.data.value*1).toFixed(2);
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
        quality:80
      }, (res)=>{
        this.showPop = false;
        
        this.$api.post(serviceUrl+'getGeneratorId&cookieId='+LtpaToken,  {})
          .then((resData) => {
          this.attachments.push({
            title:filename,
            value:'上传中：0%',
            id:resData.data.fdId
          });
          this.uploadPicture(path,filename,resData.data.fdId,this.attachments.length-1);
        }).catch(function (error) {
          console.log(error);
        })

      })
    },
  },
};
</script>

<style lang="less">
@import "../../assets/css/variable.less";
.manual-new-wrap {
  // background: #f5f5f5;
  padding-bottom: 50px;
  .vux-x-input.disabled.is-link .weui-input {
    padding-right: 15px;
    box-sizing: border-box;
    -webkit-text-fill-color: #333;
  }
  .vux-x-input.disabled.is-link.empty .weui-input {
    -webkit-text-fill-color: #999;
    padding-right: 0;
  }
  .cell-wrap {
    position: relative;
    .require-spot {
      color: #ea4335;
      position: absolute;
      top: 10px;
    }
  }
  .feeTypePopUp {
    position: relative;
  }
  .add-btn {
    color: @mainColor;
    text-align: center;
    float: right;
    height: 20px;
    line-height: 20px;
    .fs-add {
      font-size: 18px;
      font-weight: bold;
    }
    &:before {
      position: absolute;
      left: 0;
      top: 0;
      right: 0;
      height: 1px;
      border-top: 1px solid #d9d9d9;
      color: #d9d9d9;
      -webkit-transform-origin: 0 0;
      transform-origin: 0 0;
      -webkit-transform: scaleY(0.5);
      transform: scaleY(0.5);
      left: 0;
    }
  }
  .bottom-wrap {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
    .submit {
      padding: 16px 10px;
      font-size: 16px;
      color: #fff;
      background: @mainColor;
      text-align: center;
      &:active {
        background: @mainColor + 66;
      }
    }
  }
}
.manual-pop-wrap {
  background-color: #f8f9f8;
  padding-top:20px;
  padding-bottom:5px;
  ul {
    overflow: hidden;
  }
  li {
    &:nth-child(1) .fs-iconfont {
      color: #3cae69;
    }
    &:nth-child(2) .fs-iconfont {
      color: #4285f4;
    }
    &:nth-child(3) .fs-iconfont {
      color: #ffbe4d;
    }

    &:nth-child(4) .fs-iconfont {
      color: #f27474;
    }
    &:nth-child(5) .fs-iconfont {
      color: #3cae69;
    }
    text-align: center;
    padding: 0px 0 15px;
    .fs-iconfont-wrap {
      display:inline-block;
      width: 50px;
      height: 50px;
      line-height:50px;
      margin-bottom:5px;
      border-radius: 50%;
      background-color: #fff;
      box-shadow: 0 0 2px 0 rgba(200, 198, 198, 0.5);
      .fs-iconfont{
        font-size:24px;
      }
    }
    .fs-title {
      font-size: 13px;
      color: #333;
      text-align: center;
      line-height: 18px;
    }
  }
}

// 表单 title修改
.weui-cells__title {
  .fs-title {
    float: left;
    line-height: 20px;
  }
  overflow: hidden;
}
.fs-travel-wrap {
    margin: 10px 0;
    .vux-label{
      font-size:13px;
    }
    .weui-cell:before{
      left:10px!important;
    }
    .weui-cell:nth-child(2):before{
      left:0!important;
    }
    .weui-cell:nth-child(1) {
      .vux-label{
        font-size:16px;
      }
    }
    .list-header {
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
  }
  .router-link-active{
    color:#4285f4;
  }
</style>
