<template>
  <div class="expense-manual-edit">
    <group gutter="0">
      <x-input  title="行程" v-model="fdSubject" readonly  ></x-input>
      <Datetime title="起始日期" v-model="fdBeginDate" placeholder="请选择起始日期"  class="is-link" @on-change="getTravelDays"></Datetime>
      <Datetime title="结束日期" v-model="fdEndDate" placeholder="请选择结束日期" class="is-link" @on-change="getTravelDays"></Datetime>
      <x-input title="天数" v-model="fdTravelDays" readonly placeholder="自动计算"></x-input>
      <x-input
        title="人员"
        v-model="fdPersonListNames"
        :value="fdPersonListNames"
        placeholder="请选择人员"
        readonly
        @click.native="showOrganization = true"
        :class="{
          'empty': !fdPersonListNames
        }"
        class="is-link">
      </x-input>
    
      <x-input
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

      <x-input
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

      <x-input
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

      <!-- 组织架构人员弹窗 End -->
      <!-- <popup-picker title="出发城市" v-model="fdStartPlace"  :data="placeList" :columns="2" placeholder="请选择出发城市" show-name></popup-picker> -->
      <!-- <popup-picker title="到达城市" v-model="fdArrivalPlace" :data="placeList" :columns="2" placeholder="请选择到达城市" show-name></popup-picker> -->

      <!-- <popup-picker title="交通工具" v-model="fdBerthId" :columns="1" :data="vehicleList" placeholder="请选择交通工具" show-name></popup-picker> -->
    </group>
<!-- 组织架构人员弹窗 Start -->
    <fsOrganization
      :showPanel="showOrganization"
      :currentDeptName="currentDeptName"
      :currentDeptId="currentDeptId"
      :multiple="true"
      :list="organizationList"
      type="person"
      @onClosePop="onClosePop"
      @onConfirmSelect="onSelectPerson"
      @toParent="getParentOrg"
      @toChild="getChildOrg"
      @searchOrg="searchOrg"
      @searchCancel="searchCancel"
      >
    </fsOrganization>
    <!-- 费用类型选择 Start -->
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
    <!-- 费用类型选择 End -->
    <toast v-model="showWarn" type="warn">{{warnMessage}}</toast>

    <!-- 费用类型选择 End -->
    <div class="fs-bottom-bar">
      <div class="fs-col-24"><x-button type="primary" @click.native="setDetails">提交</x-button></div>
    </div>
  </div>
</template>

<script>
import { fsUpload, fsOrganization } from '@comp'
import dd from 'dingtalk-jsapi'
export default {
  name: 'expenseAddTravel',
  data () {
    return {
      fdSubject:'',
      fdBeginDate:'',
      fdEndDate:'',
      fdPersonListNames:'',
      fdPersonListIds:'',
      showOrganization:false,
      currentDeptName:'',
      currentDeptId:'',
      organizationList:[],
      fdStartPlace:[],
      fdStartPlaceId:'',
      fdArrivalPlace:[],
      fdArrivalPlaceId:'',
      fdVehicleName:[],
      fdVehicleId:[],
      vehicleList:[],
      fdBerthName:[],
      fdBerthId:'',
      fdBerth:[],
      berthList:[],
      warnMessage:'',
      showWarn:false,
      fdTravelDays:'',
      placeList:[],
      startPlacePop:false,
      keyword_city:'',
      fdStartPlaceName:'',
      fdArrivalPlaceName:'',
      endPlacePop:false,
      fdBerthName:'',
      berthPop:false,
      keyword_vehicle:''
    }
  },
   components:{
    fsOrganization
  },
  mounted(){
    this.fdCompanyId = this.$route.params.fdCompanyId;
    this.getOrganizationList();
    this.getCityData();
    //this.getVehicleData();
    this.initDetail();
    if(!this.fdId){
      this.getGeneratorId();
    }
    
  },
  //初始化函数
  activated () {
    this.fdCompanyId = this.$route.params.fdCompanyId;
    this.getOrganizationList();
    this.getCityData();
    this.getVehicleData();
    this.initDetail();
    if(!this.fdId){
      this.getGeneratorId();
    }
    this.setDefaultPlace();
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
    setDefaultPlace(){
      let geo = new BMap.Geolocation();
      let _ = this;
      geo.getCurrentPosition(function(r){
        if(this.getStatus()==BMAP_STATUS_SUCCESS){
          for(let i of _.placeList){
            if(i.name.indexOf(r.address.city)>-1||i.name.indexOf(r.address.province)>-1){
              if(_.fdStartPlaceId.length==0){
                _.fdStartPlace = [i.value];
                _.fdStartPlaceName = i.name;
                _.fdStartPlaceId = i.value
              }
              if(_.fdArrivalPlaceId.length==0){
                _.fdArrivalPlace = [i.value];
                _.fdArrivalPlaceName = i.name;
                _.fdArrivalPlaceId = i.value;
                break;
              }
            }
          }
        }
      })
    },
    getGeneratorId(){
      this.$api.post(serviceUrl+'getGeneratorId&cookieId='+LtpaToken,  { param: {} })
        .then((resData) => {
          this.fdId=resData.data.fdId;
      }).catch(function (error) {
        console.log(error);
      })
    },
     // 关闭组织架构弹出框
    onClosePop(){
      this.showOrganization=false
    },
    // 选择同行人员
    onSelectPerson(arr){
      this.fdPersonListNames= '';
      this.fdPersonListIds = '';
      for(var i=0;i<arr.length;i++){
        this.fdPersonListNames+=arr[i].name+';'
        this.fdPersonListIds+=arr[i].id+';'
      }
      if(this.fdPersonListNames.length>0){
        this.fdPersonListNames = this.fdPersonListNames.substring(0,this.fdPersonListNames.length-1);
        this.fdPersonListIds = this.fdPersonListIds.substring(0,this.fdPersonListIds.length-1);
      }
      this.showOrganization=false;
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
      }).catch( (error)=> {
        this.showWarn = true;
        this.warnMessage = error.message
      })
    },
    changeVehicle(value){
      for(let i of this.vehicleList){
        if(i.value==value){
          this.fdVehicleName = i.name;
          break;
        }
      }
    },
    formatName(value, name) {
      return name.split(' ')[name.split(' ').length - 1]
    },
    //获取城市列表
    getCityData() {
      let params = new URLSearchParams();
      params.append('fdCompanyId', this.fdCompanyId);
      params.append('flag','note');
      this.$api.post(serviceUrl+'getCityData&keyword='+this.keyword_city, params)
       .then((resData) => {
            if(resData.data.result=='success'){
              this.placeList=resData.data.data;
            }else{
              console.log(resData.data.message);
            }
        }).catch( (error)=> {
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
      getDefaultUser(){
        if(this.fdRealUserId)return;
        this.$api.post(serviceUrl+'getDefaultOrg', {})
         .then((resData) => {
            this.fdPersonListIds = resData.data.data[0].id
            this.fdPersonListNames = resData.data.data[0].name
            for(let i of this.organizationList){
              if(i.value==this.fdPersonListIds){
                i.selected = true;
                break;
              }
            }
         }).catch(err=>{

         });
        //let info = {id:'',userName:'王兵'}
        
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
     initDetail(){
      
      if(!this.$route.params.dataJson){
        
        this.fdSubject = this.$route.params.fdSubject;
        this.fdBeginDate = '';
        this.currentDeptIds = [];
        this.fdStartPlace = [];
        this.fdArrivalPlace = [];
        this.fdArrivalPlaceName = '';
        this.fdArrivalPlaceId = '';
        this.fdStartPlaceId = '';
        this.fdStartPlaceName = '';
        this.fdBeginDate = this.formatDate(new Date());
        this.fdEndDate = this.fdBeginDate;
        this.fdTravelDays = 1;
        this.fdBerthId = '';
        this.fdBerthName = '';
        this.fdBerth = [];
        this.fdPersonListNames=[],
        this.fdPersonListIds=[],
        this.fdId = '';
        this.getDefaultUser();
      }
      if(this.$route.params.dataJson){
        let dataJson = this.$route.params.dataJson;
        this.fdId=dataJson['fdId'];
        this.fdSubject = dataJson['fdSubject']
        this.fdPersonListIds=dataJson['fdPersonListIds'];
        this.fdPersonListNames=dataJson['fdPersonListNames'];
        if(dataJson['fdStartPlaceId']){
          this.fdStartPlaceId = dataJson.fdStartPlaceId
          this.fdStartPlaceName = dataJson.fdStartPlaceName;
          this.fdStartPlace = [dataJson.fdStartPlaceId]
        }
        if(dataJson['fdArrivalPlaceId']){
          this.fdArrivalPlace = [dataJson['fdArrivalPlaceId']]
          this.fdArrivalPlaceId = dataJson.fdArrivalPlaceId;
          this.fdArrivalPlaceName = dataJson.fdArrivalPlaceName;
        }
        this.fdBeginDate=dataJson['fdBeginDate'];
        this.fdEndDate=dataJson['fdEndDate'];
        this.fdTravelDays=dataJson['fdTravelDays'];
        if(dataJson.fdBerthId){
          this.fdBerthId = dataJson.fdBerthId;
          this.fdBerth = [dataJson.fdBerthId];
          this.fdBerthName = dataJson.fdBerthName;
        }
      }
      
    },
    checkSubmit(data){
      
      if(!data.fdBeginDate){
        this.showWarn = true;
        this.warnMessage = '请填写起始日期'
        return false;
      }
      if(!data.fdEndDate){
        this.showWarn = true;
        this.warnMessage = '请填写结束日期'
        return false;
      }
      if(data.fdEndDate<data.fdBeginDate){
        this.showWarn = true;
        this.warnMessage = '起始日期不能小于结束日期'
        return false;
      }
      if(!data.fdPersonListIds){
        this.showWarn = true;
        this.warnMessage = '请填写人员'
        return false;
      }
      if(!data.fdStartPlaceId){
        this.showWarn = true;
        this.warnMessage = '请填写出发城市'
        return false;
      }
      if(!data.fdArrivalPlaceId){
        this.showWarn = true;
        this.warnMessage = '请填写到达城市'
        return false;
      }
      if(!data.fdBerthId){
        this.$vux.toast.show({type:'warn',text:'请填写交通工具',time:2000})
        return false;
      }
      
      return true;
    },
   //拼接明细数据
   setDetails(){
      let dataJson= {
        fdId:this.fdId,
        fdSubject:this.fdSubject,
        fdPersonListIds:this.fdPersonListIds,
        fdPersonListNames:this.fdPersonListNames,
        fdBeginDate:this.fdBeginDate,
        fdEndDate:this.fdEndDate,
        fdTravelDays:this.fdTravelDays,
        fdStartPlaceId:this.fdStartPlaceId,
        fdStartPlaceName:this.fdStartPlaceName,
        fdArrivalPlaceName:this.fdArrivalPlaceName,
        fdArrivalPlaceId:this.fdArrivalPlaceId,
        fdBerthId:this.fdBerthId,
        fdBerthName :this.fdBerthName
      }
      
      if(!this.checkSubmit(dataJson)){
        return;
      }
      this.$router.push({name:'expenseNew',params:{fdTravelList:dataJson}});
   },
   getTravelDays(){
      if(!this.fdBeginDate||!this.fdEndDate){
        return;
      }
      let begin = new Date(this.fdBeginDate.replace(/\-/g,"/"));
      let end = new Date(this.fdEndDate.replace(/\-/g,"/"));
      this.fdTravelDays = (end.getTime()-begin.getTime())/1000/60/60/24+1;
    },
    getParentOrg(currentDeptId){
      this.getOrganizationList(currentDeptId.currentDeptId);
    },
    getChildOrg(item){
      this.getOrganizationList(item.currentDeptId,item.child);
    },
    searchOrg(keyword){
      this.getOrganizationList(null,null,keyword.keyword);
    },
    searchCancel(){
      this.getOrganizationList();
    }
  }
}

</script>

<style lang="less">
.expense-manual-edit {
  padding-bottom: 60px;
}
</style>
