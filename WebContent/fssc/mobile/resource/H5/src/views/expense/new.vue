<template>
  <div class="fs-expense-detail edit">
    <group gutter="0">
      <x-input v-model="fdId" v-if="false"></x-input>
      <x-input v-if="categoryInfo.fdSubjectType=='1'" v-model="docSubject" title="主题" placeholder="请输入主题"></x-input>
      <x-textarea
        :rows="2"
        :required="true"
        title="报销说明"
        v-model="fdDesc"
        placeholder="请输入报销说明">
      </x-textarea>

      <x-input
        title="所属公司"
        placeholder="请选择所属公司"
        :value="fdCompanyName"
        v-model="fdCompanyName"
        @click.native="companyPop = true"
        readonly
        class="is-link"
        :class="{
          'empty': fdCompanyName === ''
        }">
      </x-input>

      <x-input
        title="成本中心"
        placeholder="请选择成本中心"
        :value="fdCostCenterName"
        v-model="fdCostCenterName"
        @click.native="costPop = true"
        readonly
        class="is-link"
        :class="{
          'empty': fdCostCenterName === ''
        }">
      </x-input>

      <x-input v-if="categoryInfo.fdIsProject"
        title="项目"
        placeholder="请选择项目"
        :value="fdProjectName"
        v-model="fdProjectName"
        @click.native="projectPop = true"
        readonly
        class="is-link"
        :class="{
          'empty': fdProjectName === ''
        }">
      </x-input>

      <x-input v-if="categoryInfo.fdIsFee"
        title="关联申请"
        placeholder="请选择关联申请"
        :value="fdFeeMainNames"
        v-model="fdFeeMainNames"
        @click.native="feePop = true"
        readonly
        class="is-link"
        :class="{
          'empty': fdFeeMainNames === ''
        }">
      </x-input>

      
      <!-- <popup-picker title="成本中心" v-model="fdCostCenterIds" :data="costCenterList" :display-format="formatName" :columns="3"  placeholder="请选择成本中心"  show-name></popup-picker> -->
      <!-- <popup-picker v-if="categoryInfo.fdIsProject" title="项目" v-model="fdProjectId" :data="projectList"  :columns="1"  placeholder="请选择项目"  show-name></popup-picker> -->
      
       
       <!-- <popup-picker title="关联申请" v-if="categoryInfo.fdIsFee" v-model="fdFeeMainIds" :data="fdFeeMainList" :display-format="formatName" :columns="1"  placeholder="请选择"  show-name></popup-picker> -->
       
    </group>

    <div class="fs-travel-wrap">
      <group gutter="10">
        <cell title="附件">
          <el-upload
            :show-file-list="false"
            :before-upload="beforeUpload"
            :on-success="onUploadSuccess"
            :auto-upload="true"
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

    <!-- 收款账户 -->
    <div class="fs-expense-account-wrap">
      <div class="header">
        <div class="label">收款账户</div>
        <router-link class="right-wrap" to="" @click.native="addBank">
          <span class="fs-iconfont fs-iconfont-add"></span>新增账户
        </router-link>
      </div>
      <ul class="account-list">
        <li
          v-for="(account, index) in accountJson"
          :class="'type-' + account.type"
          :key='index'>
          <router-link to="" @click.native="editBank(account)">
            <div class="left">
              <span class="fs-iconfont fs-iconfont-zhongguoyinhang"></span>
              <span class="fs-iconfont fs-iconfont-nongyeyinhang"></span>
              <span class="fs-iconfont fs-iconfont-jiansheyinhang"></span>
              <span class="fs-iconfont fs-iconfont-tongqian"></span>
            </div>
            <div class="center">
              <p class="title">{{account.fdBank}}</p>
              <p class="name">收款金额：{{account.fdPayment}}</x-input></p>
            </div>
            <div class="del-btn" @click.stop="deleteBank(index)">删除</div>
            <div class="icon-wrap">
              <span class="fs-iconfont fs-iconfont-zhongguoyinhang"></span>
              <span class="fs-iconfont fs-iconfont-nongyeyinhang"></span>
              <span class="fs-iconfont fs-iconfont-jiansheyinhang"></span>
              <span class="fs-iconfont fs-iconfont-tongqian"></span>
            </div>
          </router-link>
        </li>
      </ul>
      <x-input v-model="accountJson" v-if="false"></x-input>
    </div>

    <!-- 新增行程 -->
    <div class="fs-expense-fee-wrap" v-if="categoryInfo.fdExpenseType=='2' && categoryInfo.fdIsTravelAlone==true"  >
      <div class="header">
        <div class="label">行程</div>
        <div class="right-wrap" @click="addTravel">
          <span class="fs-iconfont fs-iconfont-add"></span>新增行程
        </div>
      </div>
      <ul class="fee-list">
        <li v-for="(item, index) in fdTravelList" :key="index" :class="['fs-expense-card-'+item.type]">
          <div class="sub-header clearfix">
            <span class="title">{{item.fdSubject}}</span>
            <span class="del-btn" @click.sync="removeTravel(index)">移除</span>
          </div>
          <div class="card-wrap"  @click.sync="linkTravelInfo(item)">
            
            <div class="section-2">
              <p><span class="label">城市：</span>{{item.fdStartPlaceName}}<span class="fs-iconfont fs-iconfont-laihui"></span>{{item.fdArrivalPlaceName}}</p>
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
        <div class="right-wrap" @click="addDetail">
          <span class="fs-iconfont fs-iconfont-add"></span>新增费用
        </div>
      </div>
      <ul class="fee-list">
        <li v-for="(item, index) in fdDetailList" :key="index" :class="['fs-expense-card-'+item.type]">
          <div class="sub-header clearfix">
            <span class="title">{{'费用' + (index + 1)}}</span>
            <span class="del-btn" @click.sync="removeItem(index)">移除</span>
          </div>
          <div class="card-wrap"  @click.sync="linkDetailInfo(item)">
            <div class="section-1">
              <span class="fs-icon">
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
        <div class="right-wrap" @click="addInvoice">
          <span class="fs-iconfont fs-iconfont-add"></span>新增发票
        </div>
      </div>
      <ul class="fee-list">
        <li v-for="(item, index) in fdInvoiceList" :key="index" :class="['fs-expense-card-'+item.type]">
          <div class="sub-header clearfix">
            <span class="title">{{'发票' + (index + 1)}}</span>
            <span class="del-btn" @click.sync="removeInvoice(index)">移除</span>
          </div>
          <div class="card-wrap"  @click.sync="linkInvoiceInfo(item)">
            <div class="section-1">
              <span class="fs-icon">
                <i class="fs-iconfont" :class="item.type|typeClass"></i>
              </span>
              <div class="center">
                <div class="card-title">{{item.fdInvoiceNo}}</div>
                <div class="card-date">{{item.fdVatInvoice?'专票':'非专票'}}</div>
              </div>
              <div class="fee-wrap">
                <span class="symbol">¥</span>
                <span class="fee">{{item.fdInvoiceMoney}}</span>
              </div>
            </div>
            
          </div>
        </li>
      </ul>
    </div>

    

    <div class="expense-popup-wrap" :class="{active: showPop}">
      <div class="container clearfix">
        <div class="fs-row">
           <router-link to="" @click.native="manualAdd" class="fs-col-12">
              <span class="fs-iconfont fs-iconfont-shougongluru"></span>
              <span>手工录入</span>
            </router-link>
            <router-link to="" class="fs-col-12" @click.native="selectNote">
              <span class="fs-iconfont fs-iconfont-wenjianjia"></span>
              <span>选择费用</span>
            </router-link>
        </div>
        <div class="fs-divider"></div>
        <div class="fs-btn-group" @click="showPop=false">取消</div>
      </div>
      <div class="pop-mask" @click="showPop = false"></div>
    </div>
    <!-- 费用类型选择 End -->
    <div v-transfer-dom>
      <popup v-model="companyPop" class="feeTypePopUp">
        <popup-header
          @on-click-left="clearValue('companyPop','fdCompany','keyword_company')"
          @on-click-right="selectCompany"
          left-text="清空"
          right-text="完成">
        </popup-header>
        <search
          @on-submit="getfdCompany"
          v-model="keyword_company"
          @on-cancel="getfdCompany"
          @on-clear="getfdCompany"
          @on-change="getfdCompany"
          :auto-fixed="false">
        </search>
        <picker
          :data="companyList"
          :columns="1"
          v-model="fdCompany">
        </picker>
      </popup>
    </div>
    <div v-transfer-dom>
      <popup v-model="costPop" class="feeTypePopUp">
        <popup-header
          @on-click-left="clearValue('costPop','fdCostCenter','keyword_cost')"
          @on-click-right="selectCost"
          left-text="清空"
          right-text="完成">
        </popup-header>
        <search
          @on-submit="getFsBaseCostCenter"
          v-model="keyword_cost"
          @on-cancel="getFsBaseCostCenter"
          @on-clear="getFsBaseCostCenter"
          @on-change="getFsBaseCostCenter"
          :auto-fixed="false">
        </search>
        <picker
          :data="costCenterList"
          :columns="1"
          v-model="fdCostCenter">
        </picker>
      </popup>
    </div>
    <div v-transfer-dom>
      <popup v-model="projectPop" class="feeTypePopUp"  v-if="categoryInfo.fdIsProject">
        <popup-header
          @on-click-left="clearValue('projectPop','fdProject','keyword_project')"
          @on-click-right="selectProject"
          left-text="清空"
          right-text="完成">
        </popup-header>
        <search
          @on-submit="getFsBaseProject"
          v-model="keyword_project"
          @on-cancel="getFsBaseProject"
          @on-clear="getFsBaseProject"
          @on-change="getFsBaseProject"
          :auto-fixed="false">
        </search>
        <picker
          :data="projectList"
          :columns="1"
          v-model="fdProject">
        </picker>
      </popup>
    </div>
    <div v-transfer-dom>
      <popup v-model="feePop" class="feeTypePopUp"  v-if="categoryInfo.fdIsFee">
        <popup-header
          @on-click-left="clearValue('feePop','fdFeeMain','keyword_fee')"
          @on-click-right="selectFee"
          left-text="清空"
          right-text="完成">
        </popup-header>
        <search
          @on-submit="getFeeMain"
          v-model="keyword_fee"
          @on-cancel="getFeeMain"
          @on-clear="getFeeMain"
          @on-change="getFeeMain"
          :auto-fixed="false">
        </search>
        <picker
          :data="fdFeeMainList"
          :columns="1"
          v-model="fdFeeMain">
        </picker>
      </popup>
    </div>

    <!-- 组织架构人员弹窗 Start -->
    <fsOrganization
      :showPanel="showOrganization"
      :currentDept="currentDept"
      :multiple="true"
      :list="organizationList"
      @onClosePop="onClosePop"
      @onConfirmSelect="onSelectPerson"
      >
    </fsOrganization>
    <!-- 组织架构人员弹窗 End -->
    <div class="fs-footer-bar">
      <div class="fs-fl">
        <span class="fs-sum">
          合计：<em><i>￥</i>{{fdTotalAcount}}</em>
        </span>
      </div>
      <div class="fs-fr">
        <x-button type="primary" @click.native="saveExpense">提交</x-button>
      </div>
    </div>

    <toast v-model="showWarn" type="warn">{{warnMessage}}</toast>
    <loading :show="showLoading" :text="loadingMessage"></loading>
    <toast v-model="showShortTips" :time="2000">{{tipMessage}}</toast>
    <toast v-model="showCancel" type="cancel" :time="1000">{{cancelMessage}}</toast>
    <toast v-model="showText" type="text">{{textMessage}}</toast>

    <div v-transfer-dom>
      <popup v-model="showImage" height="100%" position="right" width="100%">
        <img :src="imgSrc" style="width:100%;height:100%;" v-on:click="showImage = false">
      </popup>
    </div>

    <!-- <div class="bottom-wrap">
    </div> -->
  </div>
</template>

<script>
import { fsUpload, fsOrganization } from '@comp'
import kk from 'kkjs'
import axios from 'axios'
export default {
  name: 'expenseNew',
  data () {
    return {
      fdInvoiceList:[],
      fdTravelList:[],
      fdDetailList:[],
      accountJson:[],
      docSubject:'',
      fdFeeMainIds:'',
      fdFeeMainList:[],
      fdTotalAcount:'',
      showPop: false,
      showOrganization: false,  //组织架构选择器
      fdDesc:'',
      projectPopUp: false,  // 项目选择弹窗
      project: '',  // 当前的费用类型
      fdCompanyId:'',
      companyList:[],
      fdCostCenterId:'',
      costCenterList:[],
      fdProjectId:'',
      fdProjectName:'',
      projectList:[],
      keyword:'',
      accountList: [],
      currentDept: '',
      organizationList: [],
      attachments:[],
      warnMessage:'',
      showWarn:false,
      tipMessage:'',
      showShortTips:false,
      showCancel:false,
      cancelMessage:'',
      showLoading:false,
      loadingMessage:'',
      imgSrc:'',
      showImage:false,
      showText:false,
      textMessage:'',
      fdTemplateId:'',
      categoryInfo:{},
      attachmentUrl:serviceUrl+"saveAtt",
      fdCompanyName:'',
      companyPop:false,
      fdCompany:[],
      keyword_company:'',
      costPop:false,
      keyword_cost:'',
      fdCostCenter:[],
      fdCostCenterName:'',
      fdProject : [],
      projectPop : false,
      keyword_project : '',
      fdFeeMain:[],
      feePop:false,
      keyword_fee:'',
      fdFeeMainNames:''

    }
  },
  components:{
    fsUpload,
    fsOrganization
  },
  //初始化函数
  mounted () {
    this.getGeneratorId();
    this.getfdCompany();
    this.getFeeMain();
    this.createFsExpenseDetail();
    this.getOrganizationList();
    this.getTotalMoney();
    
  },
  activated(){
    if(this.$route.params.create||this.$route.query.create){
      this.clearData();
      this.fdTemplateId = this.$route.query.fdTemplateId
    }
    
    this.getCategoryInfo();
    this.getGeneratorId();
    this.getfdCompany();
    this.getFeeMain();
    this.createFsExpenseDetail();
    this.getOrganizationList();
    this.showPop = false;
    if(this.$route.params.dataJson){
      let data=this.$route.params.dataJson;
      let contains = false;
      for(let i=0;i<this.fdDetailList.length;i++) {
        if(this.fdDetailList[i]['fdId']==data.fdId){
          this.fdDetailList.splice(i,1,data);
          contains = true;
          break;
        }
      }
      if(!contains){
        this.fdDetailList.push(data);
      }
      this.getTotalMoney();
    }

    if(this.$route.params.accountJson){
      let data=this.$route.params.accountJson;
      let contains = false;
      for(let i=0;i<this.accountJson.length;i++) {
        if(this.accountJson[i]['fdId']==data.fdId){
          this.accountJson.splice(i,1,data);
          contains = true;
          break;
        }
      }
      if(!contains){
        this.accountJson.push(data);
      }
    }
    //行程明细
    if(this.$route.params.fdTravelList){
      let contains = false;
      let data=this.$route.params.fdTravelList;
      for(let i=0;i<this.fdTravelList.length;i++){
        if(this.fdTravelList[i].fdId==data.fdId){
          this.fdTravelList.splice(i,1,data);
          contains = true;
          break;
        }
      }
      if(!contains){
        this.fdTravelList.push(data);
      }
    }
    //发票明细
    if(this.$route.params.fdInvoiceList){
      let contains = false;
      let data=this.$route.params.fdInvoiceList;
      for(let i=0;i<this.fdInvoiceList.length;i++){
        if(this.fdInvoiceList[i].fdId==data.fdId){
          this.fdInvoiceList.splice(i,1,data);
          contains = true;
          break;
        }
      }
      if(!contains){
        this.fdInvoiceList.push(data);
      }
    }

    //随手记转报销
    if(this.$route.params.dataJsonArray){
      let data=this.$route.params.dataJsonArray.note;
      let newData = [];
      for(let k=0;k<data.length;k++){
        let contains = false;
        for(let i=0;i<this.fdDetailList.length;i++) {
          if(this.fdDetailList[i]['fdNoteId']==data[k].fdNoteId&&data[k].fdNoteId){
            this.fdDetailList.splice(i,1,data[k]);
            contains = true;
            break;
          }
        }
        if(!contains){
          newData.push(data[k]);
        }
      }
      if(newData.length>0){
        for(let k=0;k<newData.length;k++){
          this.fdDetailList.push(newData[k])
        }
      }
      let invoice = this.$route.params.dataJsonArray.invoice;
      let newInvoice = [];
      for(let k=0;k<invoice.length;k++){
        let contains = false;
        for(let i=0;i<this.fdInvoiceList.length;i++) {
          if(this.fdInvoiceList[i]['fdInvoiceCode']==invoice[k].fdInvoiceCode&&this.fdInvoiceList[i]['fdInvoiceNumber']==invoice[k].fdInvoiceNumber){
            this.fdInvoiceList.splice(i,1,invoice[k]);
            contains = true;
            break;
          }
        }
        if(!contains){
          newInvoice.push(invoice[k]);
        }
      }
      if(newInvoice.length>0){
        for(let k=0;k<newInvoice.length;k++){
          this.fdInvoiceList.push(newInvoice[k])
        }
      }
      let attachments = this.$route.params.dataJsonArray.attachments;
      let newAtts = [];
      for(let k=0;k<attachments.length;k++){
        let contains = false;
        for(let i=0;i<this.attachments.length;i++) {
          if(this.attachments[i]['id']==attachments[k].id){
            this.attachments.splice(i,1,attachments[k]);
            contains = true;
            break;
          }
        }
        if(!contains){
          newAtts.push(attachments[k]);
        }
      }
      if(newAtts.length>0){
        for(let k=0;k<newAtts.length;k++){
          this.attachments.push(newAtts[k])
        }
      }
      this.getTotalMoney();
      
    }
    
  },

  methods:{
    //校验转报销的费用中是否含有未配置的费用类型
    checkExpenseItem(){
      let params = new URLSearchParams();
      params.append('fdCompanyId', this.fdCompanyId);
      params.append('flag','expense');
      params.append('categoryId',this.fdTemplateId);
      
      this.$api.post(serviceUrl+'getFsscBaseExpenseItem&flag=expense&keyword=', params)
        .then((resData) => {
        if(resData.data.result=='success'){
          let str = [];
          for(let i of resData.data.data){
            str.push(i.value);
          }
          str = str.join(";");
          for(let i of this.fdDetailList){
            if(str.indexOf(i.fdExpenseItemId)==-1){
              i.fdExpenseItemId = '';
              i.fdExpenseItemName = '';
            }
          }
        }else{
          console.log(resData.data.message);
        }
      }).catch(function (error) {
        console.log(error);
      })
    },
    clearValue(pop,name,key){
      this[pop] = false;
      this[key] = '';
      this[name] = [];
      this[name+'Id'] = '';
      this[name+'Name'] = '';
      this[name+'Ids'] = '';
      this[name+'Names'] = '';
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
    selectCost(){
      this.keyword_cost = '';
      this.costPop = false;
      this.getFsBaseCostCenter();
      for(let i of this.costCenterList){
        if(i.value==this.fdCostCenter[0]){
          this.fdCostCenterName = i.name;
          this.fdCostCenterId = i.value;
          return;
        }
      }
    },
    selectProject(){
      this.keyword_project = '';
      this.projectPop = false;
      this.getFsBaseProject();
      for(let i of this.projectList){
        if(i.value==this.fdProject[0]){
          this.fdProjectName = i.name;
          this.fdProjectId = i.value;
          return;
        }
      }
    },
    selectFee(){
      this.keyword_fee = '';
      this.feePop = false;
      this.getFeeMain();
      for(let i of this.fdFeeMainList){
        if(i.value==this.fdFeeMain[0]){
          this.fdFeeMainNames = i.name;
          this.fdFeeMainIds = i.value;
          return;
        }
      }
    },
    clearData(){
      this.fdTotalAcount = 0.00;
      this.fdId = '';
      this.fdCompanyId = '';
      this.fdCompany  = [];
      this.fdCompanyName = '';
      this.fdCostCenterId = '';
      this.fdCostCenter  =[];
      this.fdCostCenterName  ='';
      this.docSubject = '';
      this.fdDesc = '';
      this.fdProjectId = '';
      this.fdProjectName = '';
      this.fdProject = [];
      this.attachments = [];
      this.fdDetailList = [];
      this.accountJson = [];
      this.fdFeeMainNames = '';
      this.fdFeeMainIds = '';
      this.fdFeeMain = [],
      this.fdTravelList = [];
      this.fdInvoiceList = [];
      this.setDefaultCompany();
    },
    beforeUpload(file){
      this.$vux.loading.show({'text':'附件上传中...'});
      this.showPop = false;
      let fd = new FormData();
      fd.append('file',file);//传文件
      axios.post(this.attachmentUrl+"&filename="+file.name+"&fdModelName=com.landray.kmss.fssc.expense.model.FsscExpenseMain&fdModelId="+this.fdId,fd,{headers: {'Content-Type': 'multipart/form-data'}}).then((res)=>{
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
    onUploadSuccess(resp,file,list){
      this.$vux.loading.hide();
      this.$vux.loading.show({'text':'识别中...'})
      this.getInvoiceInfoFromRayky(resp.fdId)
    },
    getCategoryInfo(){
      this.$api.post(serviceUrl+'getExpenseCategoryInfo&fdTemplateId='+this.fdTemplateId,  { param: {} }).then((resData) => {
        this.categoryInfo = resData.data.data
      }).catch( (error)=> {
        console.log(error);
      })
    },
    setDefaultCompany(){
      this.$api.post(serviceUrl+'getDefaultCompany&personId=',  { param: {} }).then((resData) => {
        this.fdCompanyId = resData.data.data[0].id
        this.fdCompany = [resData.data.data[0].id]
        this.fdCompanyName = resData.data.data[0].name
        this.getFsBaseCostCenter();
        this.setDefaultCost();
        this.getFsBaseProject();
        this.checkExpenseItem();
        if(this.accountJson.length==0){
          this.getDefaultAccounts();
        }
      }).catch( (error)=> {
        console.log(error);
      })
    },
    setDefaultCost(i){
      this.$api.post(serviceUrl+'getDefaultCost&fdCompanyId='+this.fdCompanyId,{ param: {} }).then((resData) => {
        if(resData.data.data.length>0){
          this.fdCostCenterId = resData.data.data[0].id
          this.fdCostCenter = [resData.data.data[0].id];
          this.fdCostCenterName = resData.data.data[0].name
        }
      }).catch( (error)=> {
        console.log(error);
      })
    },
    removeItem(index){
      this.fdDetailList.splice(index,1);
      this.getTotalMoney();
    },
    removeTravel(index){
      this.fdTravelList.splice(index,1);
    },
    removeInvoice(index){
      this.fdInvoiceList.splice(index,1);
    },
    deleteBank(index){
      this.accountJson.splice(index,1);
    },
    selectNote(){
      if(!this.fdCompanyId){
        this.showWarn = true;
        this.warnMessage = '请先选择公司'
        return;
      }
      let ids = [];
      for(let i=0;i<this.fdDetailList.length;i++){
        ids.push(this.fdDetailList[i].fdNoteId);
      }
      this.$router.push({name:'expenseDetail',query:{type:'select',ids:ids.join(";"),fromPage:'expenseNew',fdTemplateId:this.fdTemplateId,fdCompanyId:this.fdCompanyId}})
    },
    editBank(bank){
      this.$router.push({name:'expenseAddAccount',params:{accountJson:bank,fdCompanyId:this.fdCompanyId}})
    },
    addBank(){
      if(this.fdCompanyId.length==0){
        this.showWarn = true;
        this.warnMessage = '请填写公司'
        return;
      }
      this.$router.push({name:'expenseAddAccount',params:{fdCompanyId:this.fdCompanyId}})
    },
    linkTravelInfo(bank){
      this.$router.push({name:'expenseAddTravel',params:{dataJson:bank,fdCompanyId:this.fdCompanyId}})
    },
    linkInvoiceInfo(bank){
      this.$router.push({name:'expenseAddInvoice',params:{dataJson:bank,fdCompanyId:this.fdCompanyId}})
    },
    linkDetailInfo(item){
      let travelList = [];
      for(let i=0;i<this.fdTravelList.length;i++){
        travelList.push({name:'行程'+(i+1),value:'行程'+(i+1)})
      }
      this.$router.push({name: 'expenseManualEdit',params:{ dataJson:item,fdCompanyId:this.fdCompanyId,fdTemplateId:this.fdTemplateId,travelList:travelList}});
    },
    addTravel(){
      if(this.fdCompanyId.length==0){
        this.showWarn = true;
        this.warnMessage = '请填写公司'
        //return;
      }
      this.$router.push({name:'expenseAddTravel',params:{fdCompanyId:this.fdCompanyId,fdSubject:'行程'+(this.fdTravelList.length+1)}})
    },
    getTotalMoney(){
      let money = 0;
      for(let i=0;i<this.fdDetailList.length;i++) {
        money += this.fdDetailList[i].fdMoney*1;
      }
      money = money?money.toFixed(2):'0.00';
      this.fdTotalAcount = money;
      if(this.accountJson.length>=1){
        this.accountJson[0].fdPayment = money;
      }
    },
    manualAdd(){
      let travelList = [];
      for(let i=0;i<this.fdTravelList.length;i++){
        travelList.push({name:'行程'+(i+1),value:'行程'+(i+1)})
      }
      this.$router.push({name:'expenseManualEdit',params:{fdCompanyId:this.fdCompanyId,fdTemplateId:this.fdTemplateId,travelList:travelList}})
      this.showPop = false;
    },
    addDetail(){
      
      if(this.fdCompanyId.length==0){
        this.showWarn = true;
        this.warnMessage = '请填写公司'
        return;
      }
      this.showPop = true;
    },
    addInvoice(){
      this.$router.push({name:'expenseAddInvoice',params:{fdTemplateId:this.fdTemplateId,fdCompanyId:this.fdCompanyId}})
    },
    showAtt(item,index){
      this.showImage = true;
      this.imgSrc = domainPrefix+'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=viewPic&fdId='+item.value;
    },
    getPicture(){
      
      kk.media.getPicture({
        sourceType: 'album',
        destinationType: 'file',
        encodingType: 'jpg',
        count: 6,
        chooseOrignPic: false // 选取原图
      },(res)=>{
        for(let item of res){
          let filename = 'pic_'+new Date().getTime()+".jpg";
          this.$api.post(serviceUrl+'getGeneratorId&cookieId='+LtpaToken,  { param: {} })
            .then((resData) => {
            this.attachments.push({
              title:filename,
              value:'上传中：0%',
              id:resData.data.fdId
            });
            this.uploadPicture(item.imageURI,filename,resData.data.fdId,this.attachments.length-1);
          }).catch(function (error) {
            console.log(error);
          })
        }
      });
    },
    uploadPicture(path,filename,id,index){
      console.log("attId:"+id+",modelId:"+this.fdId)
      var proxy = new kk.proxy.Upload({
        url:serviceUrl+'saveAtt&fdModelName=com.landray.kmss.fs.expense.model.FsExpenseMain&cookieId='+LtpaToken+'&fdModelId='+this.fdId+'&filename='+filename+'&fdId='+id,
        path:path
      },(res)=>{
        if(res.progress==100){
          this.attachments[index].value = '上传完成';
        }else{
          this.attachments[index].value = '上传中：'+res.progress+'%';
        }
      },(code,message)=>{
        alert("code:"+code+",message："+message);
      })
      // 开始上传
      proxy.start();
    },
    // 关闭组织架构弹出框
    onClosePop(){
      this.showOrganization=false
    },
    // 选择同行人员
    onSelectPerson(arr){
      this.showOrganization=false;
      console.log('选择了同行的人员',arr)
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
    //获取记账公司
    getfdCompany(){
       this.$api.post(serviceUrl+'getFsCompany&keyword='+this.keyword_company, { param: {} })
        .then((resData) => {
          if(resData.data.result=='success'){
            this.companyList=resData.data.data;
            
          }else{
            console.log(resData.data.message);
          }
          }).catch(function (error) {
       console.log(error);
      })
    },
    //选择完公司
    selectCompany(){
      this.companyPop = false;
      this.keyword_company = '';
      this.getfdCompany();
      for(let i of this.companyList){
        if(i.value==this.fdCompany[0]){
          this.fdCompanyName =i.name;
          this.fdCompanyId = i.value;
        }
      }
      this.checkExpenseItem();
      this.getFsBaseCostCenter();
      this.getFsBaseProject();
      if(this.accountJson.length==0){
        this.getDefaultAccounts();
      }
        
    },
    //获取成本中心
     getFsBaseCostCenter(){
      let params = new URLSearchParams();
      if(this.fdCompanyId){
        params.append('fdCompanyId', this.fdCompanyId);
      }
      this.$api.post(serviceUrl+'getFsscBaseCostCenter&keyword='+this.keyword_cost, params)
        .then((resData) => {
            if(resData.data.result=='success'){
              this.costCenterList=resData.data.data;
            }else{
              console.log(resData.data.message);
            }
          }).catch(function (error) {
       console.log(error);
      })
    },

    formatName(value, name) {
      return name.split(' ')[name.split(' ').length - 1]
    },

    //获取项目
     getFsBaseProject() {
      let params = new URLSearchParams();
      params.append('fdCompanyId', this.fdCompanyId);
       this.$api.post(serviceUrl+'getFsProject&keyword='+this.keyword_project, params)
       .then((resData) => {
            if(resData.data.result=='success'){
              this.projectList=resData.data.data;
            }else{
              console.log(resData.data.message);
            }
          }).catch(function (error) {
       console.log(error);
      })
   },
    //获取事前申请列表
    getFeeMain() {
      let params = new URLSearchParams();
       this.$api.post(serviceUrl+'getFeeMain&fdTemplateId='+this.fdTemplateId+"&keyword="+this.keyword_fee, params)
       .then((resData) => {
              if(resData.data.result=='success'){
                this.fdFeeMainList=resData.data.data;
              }else{
                console.log(resData.data.message);
              }
          }).catch(function (error) {
       console.log(error);
      })
   },
   //根据随手记列表选择的id补充费用明细列表
  createFsExpenseDetail() {
       let params = new URLSearchParams();
       params.append('ids', this.$route.query.ids);
       this.$api.post(serviceUrl+'createFsExpenseDetail&cookieId='+LtpaToken, params)
       .then((resData) => {
              if(resData.data.result=='success'){
                this.$store.commit("setDetailJson", resData.data.data);
                console.log('选择费用后的返回值',this.fdDetailList)
              }else{
                console.log(resData.data.message);
              }
          }).catch(function (error) {
       console.log(error);
      })
   },
   //获取默认银行账户信息
   getDefaultAccounts(){
    let params = new URLSearchParams();
       params.append('loginName', 'wangb');
       this.$api.post(serviceUrl+'getDefaultAccounts&fdCompanyId='+this.fdCompanyId, params)
       .then((resData) => {
          if(resData.data.result=='success'&&this.accountJson.length==0){
            let rtnData=resData.data.data;
            let param = {
              fdPayId:rtnData[0].fdPayId,
              fdAccountName:rtnData[0].fdAccountName,
              fdBankAccount:rtnData[0].fdBankAccount,
              fdBank:rtnData[0].name,
              fdAccountName:rtnData[0].name,
              type:rtnData[0].type,
              fdPayment:'',
              fdId:rtnData[0].fdId
            }
            this.accountJson.push(param)
            //如果有费用明细，自动计算总额
            if(this.fdDetailList.length>0){
              let money = 0;
              for(let i of this.fdDetailList){
                money+=i.fdMoney*1;
              }
              if(!isNaN(money)){
                this.accountJson[0].fdPayment = money.toFixed(2);
              }
            }
          }else{
            console.log(resData.data.message);
          }
      }).catch(function (error) {
       console.log(error);
      })
   },
    //获取组织架构列表
   getOrganizationList(){
       let params = new URLSearchParams();
       params.append('loginName', 'wangb');
       this.$api.post(serviceUrl+'getOrganizationList&cookieId='+LtpaToken, params)
       .then((resData) => {
              if(resData.data.result=='success'){
                this.organizationList=resData.data.data;
                this.currentDept=resData.data.currentDept;
              }else{
                console.log(resData.data.message);
              }
          }).catch(function (error) {
       console.log(error);
      })
   },
   
   checkDetailSubmit(){
      for(let i=0;i<this.fdDetailList.length;i++){
        let data = this.fdDetailList[i];
        if(!data.fdTravel&&this.categoryInfo.fdExpenseType=='2'&& this.categoryInfo.fdIsTravelAlone==true){
          this.showWarn = true;
          this.warnMessage = '费用明细行'+(i+1)+'未填写行程'
          return false;
        }
        if(!data.fdRealUserId){
          this.showWarn = true;
          this.warnMessage = '费用明细行'+(i+1)+'未填写报销人员'
          return false;
        }
        if(!data.fdExpenseItemId){
          this.showWarn = true;
          this.warnMessage = '费用明细行'+(i+1)+'未填写费用类型'
          return false;
        }
        if(!data.fdCostCenterId){
          this.showWarn = true;
          this.warnMessage = '费用明细行'+(i+1)+'未填写成本中心'
          return false;
        }
        if(!data.fdMoney){
          this.showWarn = true;
          this.warnMessage = '费用明细行'+(i+1)+'未填写申请金额'
          return false;
        }
      }
      if(this.categoryInfo.fdExpenseType=='2'){
        for(let i=0;i<this.fdTravelList.length;i++){
          let data = this.fdTravelList[i];
          if(!data.fdBeginDate){
            this.showWarn = true;
            this.warnMessage = '行程'+(i+1)+'未填写开始时间'
            return false;
          }
          if(!data.fdEndDate){
            this.showWarn = true;
            this.warnMessage = '行程'+(i+1)+'未填写结束时间'
            return false;
          }
          if(!data.fdStartPlaceName){
            this.showWarn = true;
            this.warnMessage = '行程'+(i+1)+'未填写出发城市'
            return false;
          }
          if(!data.fdArrivalPlaceName){
            this.showWarn = true;
            this.warnMessage = '行程'+(i+1)+'未填写到达城市'
            return false;
          }
        }
      }
      
      for(let i=0;i<this.fdInvoiceList.length;i++){
        let data = this.fdInvoiceList[i];
        if(!data.fdCompanyId){
          this.showWarn = true;
          this.warnMessage = '发票明细行'+(i+1)+'未填写法人名称'
          return false;
        }
        if(!data.fdExpenseItemId){
          this.showWarn = true;
          this.warnMessage = '发票明细行'+(i+1)+'未填写费用类型'
          return false;
        }
        if(!data.fdInvoiceNo){
          this.showWarn = true;
          this.warnMessage = '发票明细行'+(i+1)+'未填写发票号码'
          return false;
        }
        if(data.fdVatInvoice){
          if(!data.fdInvoiceCode){
            this.showWarn = true;
            this.warnMessage = '发票明细行'+(i+1)+'未填写发票代码'
            return false;
          }
          if(!data.fdInvoiceDate){
            this.showWarn = true;
            this.warnMessage = '发票明细行'+(i+1)+'未填写开票日期'
            return false;
          }
          if(!data.fdInvoiceMoney){
            this.showWarn = true;
            this.warnMessage = '发票明细行'+(i+1)+'未填写发票金额'
            return false;
          }
          if(!data.fdTaxRate){
            this.showWarn = true;
            this.warnMessage = '发票明细行'+(i+1)+'未填写税率'
            return false;
          }
        }
      }
      
      return true;
    },
  checkSubmit(data){
    if(!data.docSubject&&this.categoryInfo.fdSubjectType=='1'){
      this.showWarn = true;
      this.warnMessage = '请填写主题'
      return false;
    }
    if(!data.fdDesc){
      this.showWarn = true;
      this.warnMessage = '请填写报销说明'
      return false;
    }
    if(!data.fdCompanyId){
      this.showWarn = true;
      this.warnMessage = '请填写公司'
      return false;
    }
    if(!data.fdCostCenterId){
      this.showWarn = true;
      this.warnMessage = '请填写成本中心'
      return false;
    }
    if(data.fdTotalAcount==null||data.fdTotalAcount*1==0){
      this.showWarn = true;
      this.warnMessage = '请至少填写一行费用明细'
      return false;
    }
    return true;
  },
  checkAmount(){
    let money = 0;
    for(let i=0;i<this.accountJson.length;i++){
      money += this.accountJson[i].fdPayment*1;
    }
    if(this.fdTotalAcount*1!==money){
      this.showWarn = true;
      this.warnMessage = '费用合计必须等于收款金额合计'
      return false;
    }
    return true;
  },
  saveExpense() {
      let data = this.buildData();
      if(!this.checkSubmit(data)||!this.checkDetailSubmit()||!this.checkAmount()){
        return;
      }
      this.$vux.loading.show();
      this.$api.post(serviceUrl+'checkExpenseBudget&params='+encodeURI(JSON.stringify(data)),{}).then((resData) => {
        if(!resData.data.data.pass){
          this.$vux.loading.hide();
          this.$vux.toast.show({type:'cancel',text:resData.data.data.message,time:2000})
          return;
        }
        data = resData.data.data.data
        this.$api.post(serviceUrl+'createExpense&params='+encodeURI(JSON.stringify(data)),{}).then((resData) => {
          this.$vux.loading.hide();
          if(resData.data.result=='success'){
            this.$vux.toast.show({type:'success',text:'操作成功',time:1000})
            this.$router.push({name:'expenseList'});
          }else{
            this.$vux.toast.show({type:'warn',text:'操作失败,'+resData.data.message,time:1000})
            console.log(resData.data.message);
          }
        }).catch(function (error) {
          console.log(error);
        })
      }).catch(function (error) {
        console.log(error);
      })
    },
     buildData(){
      let data = {};
      data.fdId= this.fdId;
      data.docSubject=this.docSubject;
      data.fdDesc=this.fdDesc;
      data.fdCompanyId=this.fdCompanyId;
      data.fdCostCenterId=this.fdCostCenterId;
      data.docCategoryId=this.fdTemplateId;
      data.fdProjectId=this.fdProjectId;
      data.fdProjectName=this.fdProjectName;
      data.fdFeeMainId=this.fdFeeMainIds;
      data.fdFeeMainName=this.fdFeeMainNames;
      data.fdTotalAcount=this.fdTotalAcount;
      data.detailJson=this.fdDetailList;
      data.accountJson=this.accountJson;
      data.fdAttNum = this.attachments.length;
      data.attachments = this.attachments;
      data.fdTravelList = this.fdTravelList;
      data.fdInvoiceList = this.fdInvoiceList;
      return data;
    },
    
  },
}
</script>

<style lang="less">
@import "../../assets/css/variable.less";
.fs-expense-detail {
  // background: @splitColor;
  padding-bottom: 60px;
  // .vux-x-input.disabled .weui-input {
  //   -webkit-text-fill-color: #333;
  // }
  .weui-cell__ft {
    min-height:22px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
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
    .projectPopUp {
    position: relative;
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
        color: @stressTxtColor;
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
        &.fs-expense-card{
          .fs-icon {
              border-color: #4285f4;
              color: #4285f4;
            }
            .center .card-title {
              color: #4285f4;
            }
          &-taxi .section-1 {
            .fs-icon {
              border-color: #4285f4;
              color: #4285f4;
            }
            .center .card-title {
              color: #4285f4;
            }
          }
          &-hotel .section-1 {
            .fs-icon {
              border-color: #F5A623;
              color: #F5A623;
            }
            .center .card-title {
              color: #F5A623;
            }
          }
          &-airplane .section-1 {
            .fs-icon {
              border-color: #3cae69;
              color: #3cae69;
            }
            .center .card-title {
              color: #3cae69;
            }
          }
        }
        .section-1 {
          position: relative;
          .fs-icon{
            width: 39px;
            height: 39px;
            line-height: 39px;
            background: #fff;
            border: 1px solid @mainColor;
            color: @mainColor;
            // color: #fff;
            border-radius: 50%;
            display: block;
            line-height: 40px;
            text-align: center;
            font-size: 22px;
            position: absolute;
            top: 0;
            left: 0;
            .fs-iconfont{
              font-size: 22px;
            }
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
              top: -2px;
              font-size: 14px;
            }
            .fee {
              font-size: 21px;
              color: @stressTxtColor;
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
          .del-btn {
            font-size: 12px;
            color: #fff;
            border: 1px solid #fff;
            position: absolute;
            top: 15px;
            right: 15px;
            border-radius: 3px;
            padding: 2px 4px;
            opacity: 0.9;
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
  // 弹窗
  .expense-popup-wrap {
    .container {
      background: #fff;
      position: fixed;
      top: 50%;
      left: 50%;
      width: 276px;
      height: 220px;
      margin-left: -138px;
      margin-top: -110px;
      z-index: 3;
      border-radius: 8px;
      border: 1px solid #D3DCE6;
      box-sizing: border-box;
      padding: 0 15px;
      display: none;
    }
    .fs-row{ overflow: hidden;}
    .fs-btn-group{
      text-align:center;
      line-height:44px;
    }
    .fs-divider {
      height: 1px;
      background: #e5e5e5;
      position: absolute;
      bottom: 44px;
      left: 0;
      right: 0;
    }
    .pop-mask {
      background: rgba(74,74,74,0.40);
      position: fixed;
      left: 0;
      top: 0;
      width: 0%;
      bottom: 0;
      z-index: 2;
      opacity: 0;
    }
    .fs-col-12 {
      text-align: center;
      padding-bottom: 27px;
      box-sizing: border-box;
      color: #333;
      display: block;
      @btnHeight: 70px;
      .fs-iconfont {
        width: @btnHeight;
        height: @btnHeight;
        line-height: @btnHeight;
        display: block;
        background: rgba(66,133,244,0.10);
        text-align: center;
        font-size: 34px;
        color: @mainColor;
        border-radius: 50%;
        margin: 50px auto 10px;
      }
    }
    &.active {
      .container {
        display: block;
      }
      .pop-mask {
        width: 100%;
        opacity: 1;
        transition: opacity 0.3s ease;
      }
    }
  }

  // 底部弹出框
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
    // display: none;
    z-index: 2;
    justify-content: space-between;
    .fs-fl {
      float: left;
      width: 50%;
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
      width: 40%;
      float: right;
      text-align: right;
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
          color: #4285f4;
          font-size: 16px;
        }
      }
    }
  }
}
.router-link-active{
  color:#4285f4;
}
.del-btn{z-index: 1;}
</style>
