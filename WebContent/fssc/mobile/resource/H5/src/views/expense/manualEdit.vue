<template>
  <div class="expense-manual-edit">
    <group gutter="0">
      <popup-picker v-if="categoryInfo.fdExpenseType=='2'&& categoryInfo.fdIsTravelAlone==true " title="所属行程" @on-change="changeExpenseItem" v-model="fdTravel" :data="travelList" :columns="1" placeholder="请选择所属行程" show-name></popup-picker>
      <x-input
        title="报销人员"
        v-model="fdRealUserName"
        placeholder="请选择报销人员"
        readonly
        @click.native="showOrganization = true"
        :class="{
          'empty': !fdRealUserName
        }"
        class="is-link">
      </x-input>
      <x-input v-if="categoryInfo.fdDept==true" 
        title="承担部门"
        v-model="fdDeptName"
        :value="fdDeptName"
        placeholder="请选择承担部门"
        readonly
        @click.native="showDept = true"
        :class="{
          'empty': !fdDeptName
        }"
        class="is-link">
      </x-input>
      <x-input v-model="fdRealUserId" v-if="false"></x-input>
      <x-input
        title="费用类型"
        placeholder="请选择费用类型"
        :value="fdExpenseItemName"
        v-model="fdExpenseItemName"
        @click.native="itemPopUp = true"
        readonly
        :class="{
          'empty': fdExpenseItemName.length<1
        }"
        class="is-link">
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

      <x-input
        title="WBS"
        v-if="categoryInfo.fdWbs"
        placeholder="请选择WBS"
        :value="fdWbsName"
        v-model="fdWbsName"
        @click.native="wbsPop = true"
        readonly
        :class="{
          'empty': fdWbsName.length<1
        }"
        class="is-link">
      </x-input>

      <x-input
        title="内部订单" 
        v-if="categoryInfo.fdInnerOrder"
        placeholder="请选择内部订单"
        :value="fdInnerOrderName"
        v-model="fdInnerOrderName"
        @click.native="orderPop = true"
        readonly
        :class="{
          'empty': fdInnerOrderName.length<1
        }"
        class="is-link">
      </x-input>

      <!-- <popup-picker title="成本中心" v-if="categoryInfo.fdAllocType=='2'" v-model="currentDeptIds" :data="costCenterList" :display-format="formatName" :columns="3" placeholder="请选择成本中心" ref="pickerCostCenter" show-name></popup-picker> -->
      <Datetime v-if="categoryInfo.fdBeginDate==false"  
 	  		 title="发生日期"  v-model="fdHappenDate" placeholder="请选择发生日期" 
 	  		 class="is-link" @on-change="getTravelDays">
 	    </Datetime>

      <Datetime  v-if="categoryInfo.fdBeginDate==true"  
           title="起始日期"
           v-model="fdBeginDate" placeholder="请选择起始日期"  
           class="is-link" @on-change="getTravelDays">
       </Datetime>
       
      <Datetime    v-if="categoryInfo.fdBeginDate==true"  
         title="结束日期"
         v-model="fdHappenDate" placeholder="请选择结束日期"
         class="is-link" @on-change="getTravelDays">
      </Datetime>
        
      <x-input v-if="categoryInfo.fdBeginDate==true"  
         title="天数"
         v-model="fdTravelDays" readonly placeholder="自动计算">
      </x-input>
      
      
       <x-input v-if="categoryInfo.fdExpenseType=='2' && categoryInfo.fdIsTravelAlone==false"  
        title="出发城市"
        placeholder="请选择出发城市"
        :value="fdStartPlaceName"
        v-model="fdStartPlaceName"
        @click.native="startPlacePop = true"
        readonly
        class="is-link"
        :class="{
          'empty': fdStartPlaceName === ''
        }">
      </x-input>

      <x-input v-if="categoryInfo.fdExpenseType=='2' && categoryInfo.fdIsTravelAlone==false"  
        title="到达城市"
        placeholder="请选择到达城市"
        :value="fdArrivalPlaceName"
        v-model="fdArrivalPlaceName"
        @click.native="endPlacePop = true"
        readonly
        class="is-link"
        :class="{
          'empty': fdArrivalPlaceName === ''
        }">
      </x-input>
      
      <x-input v-if="categoryInfo.fdIsTravelAlone==false && categoryInfo.fdBerthId==true"  
        title="交通工具"
        placeholder="请选择交通工具"
        :value="fdBerthName"
        v-model="fdBerthName"
        @click.native="berthPop = true"
        readonly
        class="is-link"
        :class="{
          'empty': fdBerthName === ''
        }">
      </x-input>
 	 
      <!-- <x-input
        title="报销币种"
        placeholder="请选择报销币种"
        :value="fdCurrencyName"
        v-model="fdCurrencyName"
        @click.native="currencyPop = true"
        readonly
        class="is-link"
        :class="{
          'empty': fdCurrencyName === ''
        }">
      </x-input> -->

      <!-- <popup-picker title="报销币种" v-model="fdCurrencyId" :data="currencyList" :columns="3" placeholder="请选择币种" show-name></popup-picker> -->
      <x-input title="招待人数" v-model="fdPersonNumber" v-if="categoryInfo.fdPersonNumber" placeholder="请输入招待人数">
      </x-input>
      <x-input title="申报金额" placeholder="申请金额" @on-change="changeMoney" v-model="fdMoney"></x-input>
      <x-switch title="是否抵扣" :value-map="[false, true]" v-if="categoryInfo.fdInputTax" v-model="fdIsDeduct" @on-change="changeMoney"></x-switch>
      <x-input title="进项税额" v-model="fdInputTax" v-show="fdIsDeduct" readonly>
      </x-input>
      <x-input title="用途摘要" v-model="fdDesc" placeholder="请输入用途"></x-input>
      
    </group>
     <!-- 组织架构人员弹窗 Start -->
    <fsOrganization
      :showPanel="showOrganization"
      :currentDeptName="currentDeptName"
      :currentDeptId="currentDeptId"
      :multiple="false"
      :list="organizationList"
      type="person"
      @onClosePop="showOrganization = false"
      @onConfirmSelect="onSelectPerson"
      @toParent="getParentPerson"
      @toChild="getChildPerson"
      @searchOrg="searchPerson"
      @searchCancel="searchPersonCancel"
      >
    </fsOrganization>

    <fsOrganization
      :showPanel="showDept"
      :currentDeptName="deptName"
      :currentDeptId="deptId"
      :multiple="false"
      :list="deptList"
      type="dept"
      @onClosePop="showDept = false"
      @onConfirmSelect="onSelectDept"
      @toParent="getParentDept"
      @toChild="getChildDept"
      @searchOrg="searchDept"
      @searchCancel="searchDeptCancel"
      >
    </fsOrganization>
    <!-- 组织架构人员弹窗 End -->
    <!-- 费用类型选择 Start -->
    <div v-transfer-dom>
      <popup v-model="itemPopUp" class="itemPopUp">
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
      <popup v-model="wbsPop" v-if="categoryInfo.fdWbs" class="feeTypePopUp">
        <popup-header
          @on-click-left="clearValue('wbsPop','fdWbs','keyword_wbs')"
          @on-click-right="selectWbs"
          left-text="清空"
          right-text="完成">
        </popup-header>
        <search
          @on-submit="getWbsList"
          v-model="keyword_wbs"
          @on-cancel="getWbsList"
          @on-clear="getWbsList"
          @on-change="getWbsList"
          :auto-fixed="false">
        </search>
        <picker
          :data="wbsList"
          :columns="1"
          v-model="fdWbs">
        </picker>
      </popup>
    </div>
    <div v-transfer-dom>
      <popup v-model="orderPop" v-if="categoryInfo.fdInnerOrder" class="feeTypePopUp">
        <popup-header
          @on-click-left="clearValue('orderPop','fdInnerOrder','keyword_order')"
          @on-click-right="selectOrder"
          left-text="清空"
          right-text="完成">
        </popup-header>
        <search
          @on-submit="getOrderList"
          v-model="keyword_order"
          @on-cancel="getOrderList"
          @on-clear="getOrderList"
          @on-change="getOrderList"
          :auto-fixed="false">
        </search>
        <picker
          :data="orderList"
          :columns="1"
          v-model="fdInnerOrder">
        </picker>
      </popup>
    </div>
    <div v-transfer-dom>
      <popup v-model="currencyPop" class="feeTypePopUp">
        <popup-header
          @on-click-left="clearValue('currencyPop','fdCurrency','keyword_currency')"
          @on-click-right="selectCurrency"
          left-text="清空"
          right-text="完成">
        </popup-header>
        <search
          @on-submit="getCurrencyData"
          v-model="keyword_currency"
          @on-cancel="getCurrencyData"
          @on-clear="getCurrencyData"
          @on-change="getCurrencyData"
          :auto-fixed="false">
        </search>
        <picker
          :data="currencyList"
          :columns="1"
          v-model="fdCurrency">
        </picker>
      </popup>
    </div>

 <div v-transfer-dom>
      <popup v-model="endPlacePop" class="feeTypePopUp">
        <popup-header
          @on-click-left="clearValue('endPlacePop','fdArrivalPlace','keyword_city')"
          @on-click-right="selectEndPlace"
          left-text="清空"
          right-text="完成">
        </popup-header>
        <search
          @on-submit="getCityData"
          v-model="keyword_city"
          @on-cancel="getCityData"
          @on-clear="getCityData"
          @on-change="getCityData"
          :auto-fixed="false">
        </search>
        <picker
          :data="placeList"
          :columns="1"
          v-model="fdArrivalPlace">
        </picker>
      </popup>
    </div>
    <div v-transfer-dom>
      <popup v-model="berthPop" class="feeTypePopUp">
        <popup-header
          @on-click-left="clearValue('berthPop','fdBerth','keyword_vehicle')"
          @on-click-right="selectBerth"
          left-text="清空"
          right-text="完成">
        </popup-header>
        <search
          @on-submit="getVehicleData"
          v-model="keyword_vehicle"
          @on-cancel="getVehicleData"
          @on-clear="getVehicleData"
          @on-change="getVehicleData"
          :auto-fixed="false">
        </search>
        <picker
          :data="vehicleList"
          :columns="1"
          v-model="fdBerth">
        </picker>
      </popup>
    </div>
    
   <div v-transfer-dom>
      <popup v-model="startPlacePop" class="feeTypePopUp">
        <popup-header
          @on-click-left="clearValue('startPlacePop','fdStartPlace','keyword_city')"
          @on-click-right="selectStartPlace"
          left-text="清空"
          right-text="完成">
        </popup-header>
        <search
          @on-submit="getCityData"
          v-model="keyword_city"
          @on-cancel="getCityData"
          @on-clear="getCityData"
          @on-change="getCityData"
          :auto-fixed="false">
        </search>
        <picker
          :data="placeList"
          :columns="1"
          v-model="fdStartPlace">
        </picker>
      </popup>
    </div>
    <toast v-model="showWarn" type="warn">{{warnMessage}}</toast>

    <!-- 费用类型选择 End -->
    <div class="fs-bottom-bar">
      <div class="fs-col-24"><x-button type="primary" @click.native="setDetails">提交</x-button></div>
    </div>
  </div>
</template>

<script>
import { fsUpload, fsOrganization } from '@comp'
import kk from 'kkjs'
export default {
  name: 'expenseManualEdit',
  data () {
    return {
      berthPop:false,
      fdStartPlace:[],
      fdStartPlaceId:'',
      fdArrivalPlace:[],
      fdArrivalPlaceId:'',
      fdVehicleId:[],
      vehicleList:[],
      fdBerth:[],
      fdBerthId:'',
      berthList:[],
      endPlacePop:false,
      startPlacePop:false,
      fdBerthName:[],
      fdBerthName:'',
      fdArrivalPlaceName:'',
      fdStartPlaceName:'',
      fdDeptName:'',
      showDept:false,
      person: '',
      fdCompanyId:'',
      fdCostCenterId:'',
      fdCostCenter:[],
      fdCostCenterName:'',
      costPop:false,
      keyword_cost:'',
      fdBeginDate:'',
      fdHappenDate:'',
      fdTravelDays:'',
      showOrganization: false,  //组织架构选择器
      currentDeptId: '',
      currentDeptName:'',
      currentDeptIds:[],
      organizationList: [],
      fdRealUserId:'',
      fdRealUserName:'',
      itemPopUp: false,  // 项目选择弹窗
      fdExpenseItemId:'',
      fdExpenseItemName:'',
      itemList:[],
      fdExpenseItem:[],
      keyword_item:'',
      costCenterList: [],
      placeList:[],
      fdStartArea:[],
      fdEndArea:[],
      fdVehicle:'',
      fdMoney:'',
      fdCurrency:[],
      fdExchageRate:'',
      fdLocalMoney:'',
      fdDesc:'',
      fdTaxRate:[],
      fdTaxValue:'',
      fdInvoiceNo:'',
      fdTotalTax:'',
      fdNoTax:'',
      fdSalesName:'',
      currencyList:[],
      taxList:[],
      fdId:'',
      fdVatInvoice:false,
      isReadOnly:true,
      warnMessage:'',
      showWarn:false,
      fdTemplateId:'',
      fdCurrencyId:'',
      currencyList:[],
      fdDeptName:'',
      deptList:[],
      deptName:[],
      deptId:[],
      fdTravel:[],
      travelList:[],
      categoryInfo:{},
      fdInnerOrderId:'',
      fdInnerOrder:[],
      fdInnerOrderName:'',
      orderList:[],
      fdWbsId:'',
      fdWbs:[],
      fdWbsName:'',
      wbsList:[],
      fdPersonNumber:'',
      fdInputTax:'',
      fdIsDeduct:false,
      currencyPop:false,
      wbsPop:false,
      orderPop:false,
      fdCurrencyName:'',
      fdCurrency:[],
      keyword_currency:'',
      keyword_order:'',
      keyword_wbs:'',
      fdDeptId:''
    }
  },
   components:{
    fsOrganization
  },
  mounted(){
    this.getGeneratorId();
    this.fdCompanyId = this.$route.params.fdCompanyId;
    this.fdTemplateId = this.$route.params.fdTemplateId;
    this.getOrganizationList();
    this.getCategoryInfo();
    this.getDeptList();
    this.getFsBaseExpenseItem();
    this.getFsBaseCostCenter();
    this.initDetail();
    this.getCityData();
    if(!this.fdId){
      this.getGeneratorId();
    }
    
  },
  //初始化函数
  activated () {
    this.fdCompanyId = this.$route.params.fdCompanyId;
    this.fdTemplateId = this.$route.params.fdTemplateId;
    this.getCategoryInfo();
    this.getOrganizationList();
    this.getDeptList();
    this.getFsBaseExpenseItem();
    this.getFsBaseCostCenter();
    this.getCurrencyData();
    this.initDetail();
    this.getVehicleData();
    this.travelList = this.$route.params.travelList
    console.log(this.categoryInfo)
    
    if(!this.fdId){
      this.getGeneratorId();
    }
  },
  methods:{
    clearValue(pop,name,key){
      this[pop] = false;
      this[key] = '';
      this[name] = [];
      this[name+'Id'] = '';
      this[name+'Name'] = '';
    },
    selectBerth(){
      this.berthPop = false;
      this.keyword_vehicle = '';
      this.getVehicleData();
      for(let i of this.vehicleList){
        if(i.value==this.fdBerth[0]){
          this.fdBerthName = i.name;
          this.fdBerthId = i.value;
          return;
        }
      }
    },
    selectCost(){
      this.keyword_cost = '';
      this.costPop = false;
      this.getFsBaseCostCenter()
      for(let i of this.costCenterList){
        if(i.value==this.fdCostCenter[0]){
          this.fdCostCenterName = i.name;
          this.fdCostCenterId = i.value;
          return;
        }
      }
    },
    selectWbs(){
      this.keyword_wbs = '';
      this.wbsPop = false;
      this.getWbsList()
      for(let i of this.wbsList){
        if(i.value==this.fdWbs[0]){
          this.fdWbsName = i.name;
          this.fdWbsId = i.value;
          return;
        }
      }
    },
    selectOrder(){
      this.keyword_order = '';
      this.orderPop = false;
      this.getOrderList()
      for(let i of this.orderList){
        if(i.value==this.fdInnerOrder[0]){
          this.fdInnerOrderName = i.name;
          this.fdInnerOrderId = i.value;
          return;
        }
      }
    },
    selectCurrency(){
      this.keyword_currency = '';
      this.currencyPop = false;
      this.getCurrencyData()
      for(let i of this.currencyList){
        if(i.value==this.fdCurrency[0]){
          this.fdCurrencyName = i.name;
          this.fdCurrencyId = i.value;
          return;
        }
      }
    },
     selectStartPlace(){
      this.keyword_city = '';
      this.startPlacePop = false;
      this.getCityData()
      for(let i of this.placeList){
        if(i.value==this.fdStartPlace[0]){
          this.fdStartPlaceName = i.name;
          this.fdStartPlaceId = i.value;
          return;
        }
      }
    },
    selectEndPlace(){
      this.keyword_city = '';
      this.endPlacePop = false;
      this.getCityData();
      for(let i of this.placeList){
        if(i.value==this.fdArrivalPlace[0]){
          this.fdArrivalPlaceName = i.name;
          this.fdArrivalPlaceId = i.value;
          return;
        }
      }
    },
    //获取随手记随机ID
    getGeneratorId(){
      this.$api.post(serviceUrl+'getGeneratorId&cookieId='+LtpaToken,  { param: {} })
        .then((resData) => {
              this.fdId=resData.data.fdId;
          }).catch(function (error) {
       console.log(error);
      })
    },
    getCategoryInfo(){
      this.$api.post(serviceUrl+'getExpenseCategoryInfo&fdTemplateId='+this.fdTemplateId,  { param: {} }).then((resData) => {
        this.categoryInfo = resData.data.data
          console.log(this.categoryInfo);
        if(this.categoryInfo.fdWbs){
          this.getWbsList();
        }
        if(this.categoryInfo.fdInnerOrder){
          this.getOrderList();
        }
      }).catch( (error)=> {
        console.log(error);
      })
    },
    changeMoney(){
      if(!this.fdExpenseItemId||!this.categoryInfo.fdInputTax){
        return
      }
      if(!this.fdIsDeduct){
        this.fdInputTax=0;
        return;
      }
      this.$api.post(serviceUrl+'getInputTax&fdCompanyId='+this.fdCompanyId+"&fdExpenseItemId="+this.fdExpenseItemId, {})
       .then((resData) => {
          if(resData.data.result=='success'){
            let rate = resData.data.data.rate;
            this.fdInputTax = (this.fdMoney*rate).toFixed(2);
          }else{
            this.showWarn = true;
            this.warnMessage = resData.data.message
          }
      }).catch(function (error) {
        this.showWarn = true;
        this.warnMessage = error.message
      })
    },
    getOrderList(){
      this.$api.post(serviceUrl+'getOrderList&fdCompanyId='+this.fdCompanyId+'&keyword='+this.keyword_order, {})
       .then((resData) => {
          if(resData.data.result=='success'){
            this.orderList =resData.data.data;
          }else{
            this.showWarn = true;
            this.warnMessage = resData.data.message
          }
      }).catch(function (error) {
        this.showWarn = true;
        this.warnMessage = error.message
      })
    },
    getWbsList(){
      this.$api.post(serviceUrl+'getWbsList&fdCompanyId='+this.fdCompanyId+'&keyword='+this.keyword_wbs, {})
       .then((resData) => {
          if(resData.data.result=='success'){
            this.wbsList =resData.data.data;
          }else{
            this.showWarn = true;
            this.warnMessage = resData.data.message
          }
      }).catch(function (error) {
        this.showWarn = true;
        this.warnMessage = error.message
      })
    },
    // 实际使用人相关
    onSelectPerson(arr){
      this.showOrganization=false;
      this.fdRealUserId=arr.item.id;
      this.fdRealUserName=arr.item.name;
      console.log('选择了同行的人员',arr);
    },
    getParentPerson(currentDeptId){
      this.getOrganizationList(currentDeptId.currentDeptId);
    },
    getChildPerson(item){
      this.getOrganizationList(item.currentDeptId,item.child);
    },
    searchPerson(keyword){
      this.getOrganizationList(null,null,keyword.keyword);
    },
    searchPersonCancel(){
      this.getOrganizationList();
    },
    // 部门相关
    onSelectDept(arr){
      this.showDept=false;
      this.fdDeptId=arr.item.id;
      this.fdDeptName=arr.item.name;
      console.log('选择了部门',arr);
    },
    getParentDept(currentDeptId){
      this.getDeptList(currentDeptId.currentDeptId);
    },
    getChildDept(item){
      this.getDeptList(item.currentDeptId,item.child);
    },
    searchDept(keyword){
      this.getDeptList(null,null,keyword.keyword);
    },
    searchDeptCancel(){
      this.getDeptList();
    },

    changeExpenseItem(value){
      this.changeMoney()
      for(let i of this.itemList){
        if(i.value==value){
          this.fdExpenseItemName = i.name
          break;
        }
      }
    },
    //获取组织架构列表
    getOrganizationList(currentDeptId,child,keyword){
       let params = new URLSearchParams();
       currentDeptId = currentDeptId?currentDeptId:'';
       child = child?child:'';
       keyword = keyword?keyword:'';
       params.append('loginName', 'wangb');
       this.$api.post(serviceUrl+'getOrganizationList&cookieId='+LtpaToken+'&currentDeptId='+currentDeptId+'&type=person'+'&child='+child+'&keyword='+keyword, params)
       .then((resData) => {
          if(resData.data.result=='success'){
            this.organizationList=resData.data.data;
            this.currentDeptName=resData.data.currentDeptName;
            this.currentDeptId=resData.data.currentDeptId;
          }else{
            this.showWarn = true;
            this.warnMessage = resData.data.message
          }
      }).catch(function (error) {
        this.showWarn = true;
        this.warnMessage = error.message
      })
    },
    //获取部门列表
    getDeptList(currentDeptId,child,keyword){
       let params = new URLSearchParams();
       currentDeptId = currentDeptId?currentDeptId:'';
       child = child?child:'';
       keyword = keyword?keyword:'';
       params.append('loginName', 'wangb');
       this.$api.post(serviceUrl+'getOrganizationList&cookieId='+LtpaToken+'&currentDeptId='+currentDeptId+'&type=dept'+'&child='+child+'&keyword='+keyword, params)
       .then((resData) => {
          if(resData.data.result=='success'){
            this.deptList=resData.data.data;
            this.deptName=resData.data.currentDeptName;
            this.deptId=resData.data.currentDeptId;
          }else{
            this.showWarn = true;
            this.warnMessage = resData.data.message
          }
      }).catch(function (error) {
        this.showWarn = true;
        this.warnMessage = error.message
      })
    },
   //获取费用类型
  getFsBaseExpenseItem(keyword) {
      let params = new URLSearchParams();
      params.append('fdCompanyId', this.fdCompanyId);
      params.append('flag','expense');
      params.append('categoryId',this.fdTemplateId);
      if(keyword){
        keyword=encodeURI(keyword);
        params.append('keyword',keyword);
      }
      this.$api.post(serviceUrl+'getFsscBaseExpenseItem&flag=expense&keyword='+this.keyword_item, params)
        .then((resData) => {
        if(resData.data.result=='success'){
          console.log(resData.data)
          this.itemList=resData.data.data;
        }else{
          console.log(resData.data.message);
        }
      }).catch(function (error) {
       console.log(error);
      })
   },
   selectItem() {
      this.keyword_item = '';
      this.itemPopUp = false;
      this.getFsBaseExpenseItem();
      for(let i of this.itemList){
        if(i.value==this.fdExpenseItem[0]){
          this.fdExpenseItemName = i.name;
          this.fdExpenseItemId = i.value;
          return;
        }
      }
    },
     //获取成本中心
     getFsBaseCostCenter(){
      let params=new URLSearchParams();
      params.append('fdCompanyId',this.fdCompanyId);
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
      //获取城市列表
    getCityData() {
      let params = new URLSearchParams();
      params.append('fdCompanyId', this.fdCompanyId);
      params.append('flag','note');
      this.$api.post(serviceUrl+'getCityData&cookieId='+LtpaToken, params)
       .then((resData) => {
            if(resData.data.result=='success'){
                  this.placeList=resData.data.data;
                }else{
                    console.log(resData.data.message);
                  }
          }).catch(function (error) {
       console.log(error);
      })
   },
    //获取城市列表
  getVehicleData() {
    let params = new URLSearchParams();
    params.append('fdCompanyId', this.fdCompanyId);
    this.$api.post(serviceUrl+'getVehicleData&keyword='+this.keyword_vehicle, params)
     .then((resData) => {
        if(resData.data.result=='success'){
          this.vehicleList=resData.data.data;
        }else{
          console.log(resData.data.message);
        }
      }).catch(function (error) {
        console.log(error);
      })
    },
    //获取城市列表
    getBerthData() {
      let params = new URLSearchParams();
      params.append('fdCompanyId', this.fdCompanyId);
      params.append('fdVehicleId', this.fdVehicleId);
      this.$api.post(serviceUrl+'getBerthData&cookieId='+LtpaToken, params)
        .then((resData) => {
            if(resData.data.result=='success'){
              this.berthList=resData.data.data;
            }else{
              console.log(resData.data.message);
            }
        }).catch(function (error) {
          console.log(error);
        })
      },
   //获取币种税率
   getCurrencyData(){
    let params = new URLSearchParams();
      params.append('fdCompanyId', this.fdCompanyId);
      this.$api.post(serviceUrl+'getCurrencyData&keyword='+this.keyword_currency, params)
       .then((resData) => {
            if(resData.data.result=='success'){
                this.currencyList=resData.data.data;
              }else{
                  console.log(resData.data.message);
                }
          }).catch(function (error) {
       console.log(error);
      })
   },
   //获取税率
   getTaxRate(){
     let params = new URLSearchParams();
      params.append('fdCompanyId', this.fdCompanyId);
      this.$api.post(serviceUrl+'getFsBaseTaxrate&cookieId='+LtpaToken, params)
       .then((resData) => {
            if(resData.data.result=='success'){
                this.taxList=resData.data.data;
              }else{
                  console.log(resData.data.message);
                }
          }).catch(function (error) {
       console.log(error);
      })
   },
   afterCurrency(value){
    if(value.length>0){
      let currencyData=[];
      let list=this.currencyList;
      for(let item of list) {
         if(item.value==value[0]){
            currencyData=item;
            break;
          }
      }
      this.fdExchageRate=currencyData.rate;
      this.fdLocalMoney=this.fdExchageRate*this.fdMoney;
    }
   },
   afterTaxRate(){
    if(this.fdVatInvoice){
        this.fdTaxValue = 0;
        for(let tax of this.taxList){
          if(tax.value==this.fdTaxRate[0]){
            this.fdTaxValue = tax.fdTaxRate;
          }
        }
        this.fdTotalTax=(this.fdMoney/(1+this.fdTaxValue/100.00)*this.fdTaxValue/100.00).toFixed(2);
        this.fdNoTax=(this.fdMoney-this.fdTotalTax).toFixed(2);
      }
   },
   getDefaultUser(){
      if(this.fdRealUserId)return;
      this.$api.post(serviceUrl+'getDefaultOrg', {})
       .then((resData) => {
          this.fdRealUserId = resData.data.data[0].id
          this.fdRealUserName = resData.data.data[0].name
          this.setDefaultDept();
          this.setDefaultCost();
       }).catch(err=>{

       });
      //let info = {id:'',userName:'王兵'}
      
   },
   setDefaultDept(i){
      if(this.fdDeptId.length>0)return
      let personId = this.fdRealUserId;
      this.$api.post(serviceUrl+'getDefaultOrg&personId='+personId,  { param: {} }).then((resData) => {
        this.fdDeptId = resData.data.data[0].id
        this.fdDeptName = resData.data.data[0].name
      }).catch( (error)=> {
        console.log(error);
      })
    },
    setDefaultCost(i){
      if(this.fdCostCenterId)return;
      this.$api.post(serviceUrl+'getDefaultCost&personId='+this.fdRealUserId+'&fdCompanyId='+this.fdCompanyId,{ param: {} }).then((resData) => {
        if(resData.data.data.length>0){
          this.fdCostCenterId = resData.data.data[0].id
          this.fdCostCenter = [resData.data.data[0].id]
          this.fdCostCenterName = resData.data.data[0].name
        }
      }).catch( (error)=> {
        console.log(error);
      })
    },
   setDefaultCurrency(i){
      if(this.fdCurrencyId)return;
      this.$api.post(serviceUrl+'getDefaultCurrency&fdCompanyId='+this.fdCompanyId,{ param: {} }).then((resData) => {
        if(resData.data.data.length>0){
          this.fdCurrency = [resData.data.data[0].id];
          this.fdCurrencyId = resData.data.data[0].id;
          this.fdCurrencyName = resData.data.data[0].name;
        }
      }).catch( (error)=> {
        console.log(error);
      })
    },
   initDetail(){
      
      if(!this.$route.params.dataJson){
        this.fdDeptName = '';
        this.fdDeptId = '';
        this.fdNoteId = '';
        this.fdWbsId = [];
        this.fdInnerOrderId=[];
        this.fdInputTax = '';
        this.fdPersonNumber = '';
        this.fdExpenseItemName = '';
        this.fdCostCenterName =  '';
        this.fdCostCenter = [];
        this.fdCostCenterId = '';
        this.fdExpenseItemId = '';
        this.fdExpenseItem = [];
        this.currentDeptIds = [];
        this.fdRealUserId = '';
        this.fdRealUserName = '';
        this.fdDesc = '';
        this.fdRate = '';
        this.fdMoney = '';
        this.fdCurrencyId = '';
        this.fdCurrency = [];
        this.fdCurrencyName = '';
        this.fdId = '';
        this.fdHappenDate = this.formatDate(new Date());
        this.setDefaultCurrency();
        this.getDefaultUser();
        this.fdBeginDate = '';
        this.fdBeginDate = this.formatDate(new Date());
        this.fdTravelDays = 1;
        this.fdBerthName = '';
      }
      if(this.$route.params.dataJson){
        let dataJson = this.$route.params.dataJson;
        this.fdId=dataJson['fdId'];
        
        this.fdNoteId = dataJson.fdNoteId;
        this.fdHappenDate=dataJson['fdHappenDate'];
        this.fdRate = dataJson.fdRate;
        this.fdDesc = dataJson.fdDesc;
        this.fdRealUserId=dataJson['fdRealUserId'];
        this.fdRealUserName=dataJson['fdRealUserName'];
        this.fdBeginDate=dataJson['fdBeginDate'];
        this.fdHappenDate=dataJson['fdHappenDate'];
        this.fdTravelDays=dataJson['fdTravelDays'];
        
        if(dataJson.fdExpenseItemId){
          this.fdExpenseItemId=dataJson['fdExpenseItemId'];
          this.fdExpenseItem = [dataJson['fdExpenseItemId']]
          this.fdExpenseItemName=dataJson['fdExpenseItemName'];
        }else{
          this.fdExpenseItemName='';
        }
        
        if(dataJson.fdCostCenterId){
          this.fdCostCenter=[dataJson.fdCostCenterId];
          this.fdCostCenterId=dataJson.fdCostCenterId;
          this.fdCostCenterName=dataJson.fdCostCenterName;
        }
        this.fdDeptId = dataJson.fdDeptId;
        this.fdDeptName = dataJson.fdDeptName;
        
        
        if(dataJson.fdCurrencyId){
          this.fdCurrency = [dataJson.fdCurrencyId];
          this.fdCurrencyId = dataJson.fdCurrencyId;
          this.fdCurrencyName = dataJson.fdCurrencyName;
        }
        if(dataJson.fdBerthId){
          this.fdBerthId = dataJson.fdBerthId;
          this.fdBerth = [dataJson.fdBerthId];
          this.fdBerthName = dataJson.fdBerthName;
        }
        this.fdDesc=dataJson['fdDesc'];
        this.fdMoney=dataJson['fdMoney'];
      }
      
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
    getTravelDays(){
      if(!this.fdBeginDate||!this.fdHappenDate){
        return;
      }
      let begin = new Date(this.fdBeginDate.replace(/\-/g,"/"));
      let end = new Date(this.fdHappenDate.replace(/\-/g,"/"));
      this.fdTravelDays = (end.getTime()-begin.getTime())/1000/60/60/24+1;
      console.log(fdTravelDays);
    },
    checkSubmit(data){
      if(!data.fdTravel&&this.categoryInfo.fdExpenseType=='2'&& this.categoryInfo.fdIsTravelAlone==true){
        this.showWarn = true;
        this.warnMessage = '请填写所属行程'
        return false;
      }
      if(!data.fdRealUserId){
        this.showWarn = true;
        this.warnMessage = '请填写实际使用人'
        return false;
      }
      if(!data.fdExpenseItemId){
        this.showWarn = true;
        this.warnMessage = '请填写费用类型'
        return false;
      }
      if(!data.fdCostCenterId&&this.categoryInfo.fdAllocType=='2'){
        this.showWarn = true;
        this.warnMessage = '请填写成本中心'
        return false;
      }
      if(!data.fdHappenDate){
        this.showWarn = true;
        this.warnMessage = '请填写发生日期'
        return false;
      }
      if(!data.fdMoney){
        this.showWarn = true;
        this.warnMessage = '请填写金额'
        return false;
      }
      if(!data.fdBeginDate&& this.categoryInfo.fdBeginDate==true){
        this.showWarn = true;
        this.warnMessage = '请填写开始日期'
        return false;
      }
      if(!data.fdStartPlaceId &&this.categoryInfo.fdIsTravelAlone==false){
       this.showWarn = true;
        this.warnMessage = '请填写出发城市'
        return false;
      }
       if(!data.fdArrivalPlaceId&&this.categoryInfo.fdIsTravelAlone==false){
       this.showWarn = true;
        this.warnMessage = '请填写到达城市'
        return false;
      }
      if(!data.fdBerthId&&this.categoryInfo.fdBerthId==true&&this.categoryInfo.fdIsTravelAlone==false){
       this.showWarn = true;
        this.warnMessage = '请填写交通工具'
        return false;
      }
      
      return true;
    },
   //拼接明细数据
   setDetails(){
      let dataJson= {
        fdId:this.fdId,
        fdRealUserId:this.fdRealUserId,
        fdRealUserName:this.fdRealUserName,
        fdExpenseItemId:this.fdExpenseItemId,
        fdExpenseItemName:this.fdExpenseItemName,
        fdCostCenterId:this.fdCostCenterId,
        fdCostCenterName:this.fdCostCenterName,
        fdMoney:this.fdMoney,
        fdDesc:this.fdDesc,
        fdNoteId : this.fdNoteId,
        fdHappenDate:this.fdHappenDate,
        fdCurrencyId:this.fdCurrencyId,
        fdCurrencyName:this.fdCurrencyName,
        fdDeptId:this.fdDeptId,
        fdDeptName:this.fdDeptName,
        fdTravel:this.fdTravel.length>0?this.fdTravel[0]:"",
        fdWbsId:this.fdWbsId,
        fdInnerOrderId:this.fdInnerOrderId,
        fdIsDeduct:this.fdIsDeduct,
        fdInputTax:this.fdInputTax,
        fdPersonNumber:this.fdPersonNumber,
        fdBeginDate:this.fdBeginDate,
        fdArrivalPlaceId:this.fdArrivalPlaceId,
        fdStartPlaceId:this.fdStartPlaceId,
        fdTravelDays:this.fdTravelDays,
        fdBerthName :this.fdBerthName,
        fdBerthId:this.fdBerthId,
      }
      if(!this.checkSubmit(dataJson)){
        return;
      }
      this.$router.push({name:'expenseNew',params:{dataJson:dataJson}});
    }
  }
}

</script>

<style lang="less">
.expense-manual-edit {
  padding-bottom: 60px;
}
.vux-search-box,.vux-search-bar{
  height:58px !import;
}
</style>
