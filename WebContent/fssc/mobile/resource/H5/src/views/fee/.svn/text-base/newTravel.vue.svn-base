<template>
  <div class="fs-fee-travel-new">

    <group gutter="0">
    <div v-for="(item,index) in formData" v-if="item.showStatus!='3'&&item.type!='5'" class="fs-cell-div">
      <Cell :title="item.title" v-if="(item.type=='1'||item.type=='2')&&item.showStatus=='2'" :value="formValue[item.name]"></Cell>

      <x-input show-clear="false" v-if="item.type=='1'&&item.showStatus=='1'" :placeholder="item.placeholder" @on-change="item.changeValue" v-model="formValue[item.name]" :value="formValue[item.name]" :title="item.title"></x-input>

      <Datetime v-if="item.type=='2'" :title="item.title" v-model="formValue[item.name]" :value="formValue[item.name]" :placeholder="item.placeholder" @on-change="item.changeValue"></Datetime>

      <Cell :title="item.title" v-if="(item.type=='3'||item.type=='4')&&item.showStatus=='2'" :value="item.showValue"></Cell>

      <x-input 
        v-if="item.type=='3'&&item.showStatus=='1'"
        :title="item.title"
        :placeholder="item.placeholder"
        :value="item.showValue"
        v-model="item.showValue"
        @click.native="item.show = true"
        readonly
        class="is-link"
        :class="{
          'empty': item.showValue === ''
        }">
      </x-input>

      <x-input v-if="item.type=='4'&&item.showStatus=='1'" 
        v-model="formValue[item.name+'_name']"
        :title="item.title"
        :value="formValue[item.name+'_name']"
        :placeholder="item.placeholder"
        readonly
        @click.native="item.show=true"
        :class="{
          'empty': !formValue[item.name]
        }"
        class="is-link">
      </x-input>
      </div>
    </group>


    <div v-transfer-dom v-for="(item,index) in formData" v-if="item.showStatus!='3'&&item.type=='3'">
      <popup v-model="item.show" class="feeTypePopUp">
        <popup-header
          @on-click-left="item.clearValue"
          @on-click-right="item.changeValue"
          left-text="清空"
          right-text="完成">
        </popup-header>
        <search
          @on-submit="getDataSource(item.dataSource,item.baseOn,index)"
          v-model="item.keyword"
          @on-cancel="getDataSource(item.dataSource,item.baseOn,index)"
          @on-clear="getDataSource(item.dataSource,item.baseOn,index)"
          @on-change="getDataSource(item.dataSource,item.baseOn,index)"
          :auto-fixed="false">
        </search>
        <picker
          :data="item.data"
          :columns="1"
          v-model="item.currentValue">
        </picker>
      </popup>
    </div>
    

    <div class="bottom-wrap">
      <x-button type="primary" @click.native="save">保存</x-button>
    </div>

    <!-- 组织架构人员弹窗 Start -->
    <fsOrganization v-for="(item,index) in formData" v-if="item.type=='4'" 
        :showPanel="item.show"
        :currentDeptName="formValue[item.name+'_curName']"
        :currentDeptId="formValue[item.name+'_curId']"
        :multiple="item.multi"
        :list="item.data"
        :fieldName="item.name"
        :type="item.orgType"
        @onClosePop="item.onClosePop"
        @onConfirmSelect="item.onConfirmSelect"
        @toParent="item.toParent"
        @toChild="item.toChild"
        @searchOrg="item.searchOrg"
        @searchCancel="item.searchCancel">
    </fsOrganization>
    <!-- 组织架构人员弹窗 End -->

    <toast v-model="showWarn" type="warn">{{warnMessage}}</toast>
    <loading :show="showLoading" :text="loadingMessage"></loading>
    <toast v-model="showShortTips" :time="2000">{{tipMessage}}</toast>
    <toast v-model="showCancel" type="cancel" :time="1000">{{cancelMessage}}</toast>

  </div>
</template>

<script>
import { fsUpload,fsOrganization } from '@comp'
export default {
  name: 'expenseNewTravel',
  data () {
    return {
      expand: true,
      showOrganization:false,
      currentDept:'',
      organizationList: [],
      fdPersonName:'',
      fdPersonId:'',
      fdId:'',
      title:'',
      
      warnMessage:'',
      showWarn:false,
      warnMessage:'',
      showWarn:false,
      tipMessage:'',
      showShortTips:false,
      showCancel:false,
      cancelMessage:'',
      showLoading:false,
      loadingMessage:'',
      formData:[],
      formValue:{},
      formMap:{},
      baseOn:'',
      cateId:'',
      detail:'',
      dataLoaded:false
    }
  },
  mounted(){
    //this.initData();
  },
  created(){
    
  },
  activated(){
    this.initData();
    this.getFormData();
  },
  components:{
    fsUpload,
    fsOrganization
  },
  methods:{

    initData(){
      this.dataLoaded = false
        this.formValue={};
        this.formMap={};
        this.formData=[];
        this.title = this.$route.query.title;
        this.fdId = '';
        this.baseOn = this.$route.query.baseOn
        this.cateId = this.$route.query.cateId
        this.detail = this.$route.query.detail
        let mainFormValue = JSON.parse(this.$route.query.formValue)
        this.formValue = this.$extend(mainFormValue,{});
        this.formMap = JSON.parse(this.$route.query.formMap)
        //编辑状态加载字段值
        if(this.$route.query.data){
          let data = JSON.parse(this.$route.query.data);
          this.formValue = this.$extend(data,this.formValue);
          this.fdId = data.fdId;
        }else{
          this.getGeneratorId();
        }
    },
    getFormData(){
      this.$api.post(serviceUrl+'getFeeFormData&fdTemplateId='+this.cateId,  { param: {} }).then((resData) => {
        let index = 0;
        for(let i=0;i<resData.data.data.length;i++){
          
          if(resData.data.data[i].name.indexOf(this.detail)==-1||resData.data.data[i].name==this.detail){
            continue;
          }
          let ii = index;
          this.formMap[resData.data.data[i].text] = resData.data.data[i].name
          console.log("需要显示的字段："+resData.data.data[i].title)
          let obj = {
            name:resData.data.data[i].name,
            show:false,
            title:resData.data.data[i].title,
            type:resData.data.data[i].type,
            baseOn:resData.data.data[i].baseOn,
            placeholder:'请填写'+resData.data.data[i].title,
            dataSource:resData.data.data[i].dataSource,
            showStatus:resData.data.data[i].showStatus,
            value:'',
            required:resData.data.data[i].required,
            init:resData.data.data[i].init,
            orgType:resData.data.data[i].orgType,
            multi:resData.data.data[i].multi,
            keyword:'',
            currentValue:[],
            showValue:'',
            data:[],
            index:index,
            changeValue:(val)=>{
              if(this.formData[ii].type=='3'){
                this.formData[ii].show = false;
                this.formData[ii].keyword = ''
                this.getDataSource(this.formData[ii].dataSource,this.formData[ii].baseOn,ii);
                for(let k of this.formData[ii].data){
                  if(k.value==this.formData[ii].currentValue){
                    this.formData[ii].showValue = k.name;
                  }
                }
                this.formValue[this.formData[ii].name] = this.formData[ii].currentValue;
              }
              for(let k of this.formData){
                if((k.type=='3'||k.type=='4')&&k.baseOn.indexOf(this.formData[ii].name)>-1){
                  this.getDataSource(k.dataSource,k.baseOn,k.index)
                  if(k.init=='4'){
                    this.setDefaultCost(k.index);
                  }
                  if(k.init=='5'){
                    this.setDefaultCurrency(k.index);
                  }
                  //当前部门
                  if(k.init=='2'){
                    this.setDefaultDept(k.index);
                  }
                }
                //天数
                if(k.init=='7'){
                  this.getDays(k);
                }
              }
            },
            clearValue:()=>{
              this.formData[ii].show = false;
              this.formData[ii].keyword = ''
              this.formData[ii].showValue = '';
              this.formData[ii].currentValue = [];
            }
          };
          if(obj.type=='4'){
            this.formValue[obj.name+'_curId'] = '';
            this.formValue[obj.name+'_curName'] = '';
            obj.showPanel = ()=>{
              obj.show = true;
            }
            obj.onClosePop = ()=>{obj.show = false}
            obj.onConfirmSelect = (arr)=>{
              if(!obj.multi){
                this.formValue[obj.name+'_name'] = arr.item.name;
                this.formValue[obj.name+'_id'] = arr.item.id;
                this.formValue[obj.name] = arr.item.id;
              }else{
                let ids = [],names = [];
                for(let m of arr){
                  ids.push(m.id);
                  names.push(m.name)
                }
                this.formValue[obj.name+'_name'] = names.join(';')
                this.formValue[obj.name+'_id'] = ids.join(';')
                this.formValue[obj.name] = ids.join(';')
              }
              obj.changeValue();
              obj.show = false
            }
            obj.toParent = (item)=>{
              this.getOrganizationList(item.name,item.currentDeptId);
            }
            obj.toChild = (item)=>{
              this.getOrganizationList(item.name,item.currentDeptId,item.child);
            }
            obj.searchOrg = (item)=>{
              this.getOrganizationList(item.name,null,null,item.keyword);
            }
            obj.searchCancel=(name)=>{
              this.getOrganizationList(name);
            }
          }
          this.formData.push(obj);
          if(obj.showStatus!='3'){
            this.formValue[obj.name] = this.formValue[obj.name]||'';
            //如果字段类型为对象，需要获取数据源
            if(obj.type=='3'){
              this.getDataSource(obj.dataSource,obj.baseOn,index);
            }
            //如果字段类型为组织架构，需要获取数据源
            if(obj.type=='4'){
              this.getOrganizationList(obj.name);
            }
            //当前用户
            if(obj.init=='1'&&!obj.baseOn){
              this.setDefaultUser(index);
            }
            if(obj.init=='4'){
              this.setDefaultCost(index);
            }
            if(obj.init=='5'){
              this.setDefaultCurrency(index);
            }
            if(obj.init==10&&this.formValue[obj.name].length==0){
              this.formValue[obj.name] = this.formatDate(new Date());
            }
            if(obj.init==7){
              this.getDays(obj)
            }
          }
          index++;
        }
         this.dataLoaded = true;
      }).catch( (error)=> {
        console.log(error);
      })
    },
    $extend(_from,_to){
      if(!_from||!_to){
        return;
      }
      for(let i in _from){
        if(!_to[i]||_to[i].length==0){
          _to[i] = _from[i];
        }
      }
      return _to;
    },
    setDefaultUser(i){
      if(this.formValue[this.formData[i].name].length>0)return;
      this.$api.post(serviceUrl+'getDefaultOrg',  { param: {} }).then((resData) => {
        this.formValue[this.formData[i].name+'_name']=resData.data.data[0].name;
        this.formValue[this.formData[i].name+'_id']=resData.data.data[0].id;
        this.formValue[this.formData[i].name+'_curName']=resData.data.data[0].name;
        this.formValue[this.formData[i].name+'_curId']=resData.data.data[0].id;
        this.formValue[this.formData[i].name] = resData.data.data[0].id;
        this.formData[i].showValue = resData.data.data[0].name;
        for(let k of this.formData[i].data[0].list){
          if(k.id==resData.data.data[0].id){
            k.selected = true;
          }
        }
        for(let k of this.formData){
          if(k.baseOn.indexOf(this.formData[i].name)>-1){
            //当前部门
            if(k.init=='2'){
              this.setDefaultDept(k.index);
            }
            //默认公司
            if(k.init=='4'){
              this.setDefaultCost(k.index);
            }
          }
        }
      }).catch( (error)=> {
        console.log(error);
      })
    },
    setDefaultDept(i){
      let personId = this.formValue[this.formData[i].baseOn+'_id'];
      this.$api.post(serviceUrl+'getDefaultOrg&personId='+personId,  { param: {} }).then((resData) => {
        this.formValue[this.formData[i].name+'_name']=resData.data.data[0].name;
        this.formValue[this.formData[i].name+'_id']=resData.data.data[0].id;
        this.formValue[this.formData[i].name+'_curName']=resData.data.data[0].name;
        this.formValue[this.formData[i].name+'_curId']=resData.data.data[0].id;
        this.formValue[this.formData[i].name] = resData.data.data[0].id;
        this.formData[i].showValue = resData.data.data[0].name;
        this.formData[i].show = true;
        this.formData[i].show = false;
        for(let k of this.formData[i].data[0].list){
          if(k.id==resData.data.data[0].id){
            k.selected = true;
          }
        }
      }).catch( (error)=> {
        console.log(error);
      })
    },
    setDefaultCost(i){
      if(this.formValue[this.formData[i].name].length>0)return;
      this.getDataSource(this.formData[i].dataSource,this.formData[i].baseOn,i);
      let baseOn = this.formData[i].baseOn.split(";");
      let personId='',fdCompanyId='';
      for(let k of baseOn){
        if(this.formValue[k+'_id']!=null){
          personId = this.formValue[k+'_id']
        }else{
          fdCompanyId = this.formValue[k];
        }
      }
      this.$api.post(serviceUrl+'getDefaultCost&personId='+personId+'&fdCompanyId='+fdCompanyId,{ param: {} }).then((resData) => {
        if(resData.data.data.length>0){
          this.formData[i].currentValue = [resData.data.data[0].id];
          this.formData[i].showValue = resData.data.data[0].name;
          this.$set(this.formValue,this.formData[i].name,[resData.data.data[0].id]);
        }
      }).catch( (error)=> {
        console.log(error);
      })
    },
    setDefaultCurrency(i){
      if(this.formValue[this.formData[i].name].length>0)return;
      this.getDataSource(this.formData[i].dataSource,this.formData[i].baseOn,i);
      let fdCompanyId=this.formValue[this.formData[i].baseOn];
          
      this.$api.post(serviceUrl+'getDefaultCurrency&fdCompanyId='+fdCompanyId,{ param: {} }).then((resData) => {
        if(resData.data.data.length>0){
          this.formData[i].currentValue = [resData.data.data[0].id];
          this.formData[i].showValue = resData.data.data[0].name;
          this.$set(this.formValue,this.formData[i].name,[resData.data.data[0].id]);
        }
      }).catch( (error)=> {
        console.log(error);
      })
    },
    setDefaultPlace(i){
      let geo = new BMap.Geolocation();
      let _ = this;
      geo.getCurrentPosition(function(r){
        if(this.getStatus()==BMAP_STATUS_SUCCESS){
          console.log(r)
          for(let k of _.formData[i].data){
            if(k.name.indexOf(r.address.city)>-1||k.name.indexOf(r.address.province)>-1){
              _.formValue[_.formData[i].name] = k.value;
              _.formData[i].currentValue = [k.value];
              _.formData[i].showValue = k.name;
            }
          }
        }
      })
    },
    getDataSource(dataSource,baseOn,i){
      dataSource = this.resolveUrl(dataSource)+'&keyword='+this.formData[i].keyword;
      console.log("开始获取数据源，后台接口为："+dataSource)
      this.$api.post(domainPrefix+dataSource,  { param: {} }).then((resData) => {
        this.formData[i].data=resData.data.data;
        if(this.formData[i].init=='11'&&this.formValue[this.formData[i].name].length==0){
          this.setDefaultPlace(i);
        }
        if(this.formValue[this.formData[i].name]){
          this.formData[i].currentValue = this.formValue[this.formData[i].name];
          for(let m of this.formData[i].data){
            if(m.value==this.formValue[this.formData[i].name]){
              this.formData[i].showValue = m.name
            }
          }
        }
        console.log("获取数据源"+this.formData[i].title+'成功')
      }).catch( (error)=> {
        console.log(error);
      })
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
    resolveUrl(url){
      if(!url)return url;
      let __url = url.split("\?")[0]+'?type=mobile';
      let params = url.split("\?")[1].split("\&");
      for(let i of params){
        let t = i.split("\=");
        if(t[1].indexOf("$")>-1){
          __url+='&'+t[0]+'='+this.formValue[this.formMap[t[1]]];
        }else if(t[1].indexOf("!")>-1){
          __url+='&'+t[0]+'='+this.formValue[t[1].replace(/\!\{(.+)\}/ig,'$1')];
        }else{
          __url+='&'+t[0]+'='+t[1];
        } 
      }
      return __url;
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
    //获取组织架构列表
    getOrganizationList(name,currentDeptId,child,keyword){
       currentDeptId = currentDeptId||'';
       child = child||'';
       keyword = keyword||'';
       let type = '';
       for(let i of this.formData){
        if(i.name==name){
          type = i.orgType;
        }
       }
       this.$api.post(serviceUrl+'getOrganizationList&cookieId='+LtpaToken+'&currentDeptId='+currentDeptId+'&type='+type
        +'&child='+child+'&keyword='+keyword, {})
       .then((resData) => {
          if(resData.data.result=='success'){
            for(var i of this.formData){
              if(i.name==name){
                i.data = resData.data.data;
                if(!this.formValue[i.name+'_name']&&this.formValue[name]){
                  let names = [];
                  for(let m of i.data[0].list){
                    if(this.formValue[name].indexOf(m.id)>-1){
                      names.push(m.name);
                    }
                  }
                  if(names.length>0){
                    this.formValue[i.name+'_name'] = names.join(';')
                    
                  }

                }
              }
            }
            this.formValue[name+'_curName']=resData.data.currentDeptName;
            this.formValue[name+'_curId']=resData.data.currentDeptId;
            
            
            
          }else{
            this.$vux.toast.show({type:'warn',text:resData.data.message,time:1000})
          }
      }).catch(function (error) {
        this.showWarn = true;
        this.warnMessage = error.message
      })
    },
    
    //获取城市列表
    getCityData() {
      let params = new URLSearchParams();
      params.append('fdCompanyId', this.fdCompanyId);
      params.append('flag','note');
      this.$api.post(serviceUrl+'getCityData&cookieId='+LtpaToken, params)
       .then((resData) => {
        this.placeList=resData.data.data;
      }).catch(function (error) {
       console.log(error);
      })
    },
    //获取城市列表
    getVehicleData() {
      let params = new URLSearchParams();
      params.append('fdCompanyId', this.fdCompanyId);
      this.$api.post(serviceUrl+'getVehicleData&cookieId='+LtpaToken, params)
       .then((resData) => {
        this.placeList=resData.data.data;
      }).catch(function (error) {
       console.log(error);
      })
    },
    //获取币种列表
    getCurrencyData() {
      let params = new URLSearchParams();
      params.append('fdCompanyId', this.fdCompanyId);
      this.$api.post(serviceUrl+'getCurrencyData&cookieId='+LtpaToken, params)
       .then((resData) => {
        
        this.currencyList=resData.data.data;
      }).catch(function (error) {
       console.log(error);
      })
    },
    afterCurrencySelect(value){
      for(var i=0;i<this.currencyList.length;i++){
        if(this.currencyList[i].value==value){
          this.fdRate = this.currencyList[i].rate;
          this.fdStandardMoney = this.fdApplyMoney*this.fdRate;
        }
      }
    },
    checkSubmit(data){
      for(let i in data){
        for(let k of this.formData){
          if(k.name==i){
            if(k.required&&data[i]==''){
              this.$vux.toast.show({text:k.placeholder,type:'warn',time:2000});
              return false;
            }
          }
        }
      }
      for(let i of this.formData){
        if(i.init=='7'){
          if(data[i.name]<1){
            this.$vux.toast.show({text:i.title+'不能小于1',type:'warn',time:2000});
            return false;
          }
        }
      }
      return true;
    },
    save(){
      let data = {}
      let left = [],right = [];
      for(let i of this.formData){
        if(i.type=='3'){
          data[i.name] = this.formValue[i.name]
          for(let k of i.data){
            if(k.value==data[i.name]){
              data[i.name+'.name'] = k.name;
            }
          }
          if(this.$route.query.leftShow.indexOf(i.name)>-1){
            left.push(data[i.name+'.name']);
          }
          if(this.$route.query.rightShow.indexOf(i.name)>-1){
            right.push(data[i.name+'.name']);
          }
        }else if(i.type=='4'){
          data[i.name+'.name'] = this.formValue[i.name+'_name'];
          data[i.name] = this.formValue[i.name]
          if(this.$route.query.leftShow.indexOf(i.name)>-1){
           left.push(data[i.name+'.name'])
          }
          if(this.$route.query.rightShow.indexOf(i.name)>-1){
            right.push(data[i.name+'.name'])
          }
        }else{
          data[i.name] = this.formValue[i.name]
          if(this.$route.query.leftShow.indexOf(i.name)>-1){
            left.push(data[i.name])
          }
          if(this.$route.query.rightShow.indexOf(i.name)>-1){
            right.push(data[i.name]);
          }
        }
      }
      data.leftShow = left.join(';')
      data.rightShow = right.join(';')
      data.fdId=this.fdId;
      if(!this.checkSubmit(data)){
        return false;
      }
      this.$router.push({name:'feeNew',query:{data:data,detail:this.detail,flag:'detail'}});
    },
    getDays(item){
      let baseOn = item.baseOn.split(";");
      if(baseOn.length<2){
        return;
      }
      let start = this.formValue[baseOn[0]];
      let end = this.formValue[baseOn[1]];
      if(!start||!end){
        return;
      }
      start = new Date(start.replace(/\-/g,"/"));
      end = new Date(end.replace(/\-/g,"/"));
      this.formValue[item.name]  = (end.getTime()-start.getTime())/1000/60/60/24+1;
    }
  }
}
</script>

<style lang="less">
@import "../../assets/css/variable.less";
.fs-fee-travel-new {
  // background: #f5f5f5;
  padding-bottom: 60px;
  .title-cell {
    .weui-cell__ft {
      color: #333;
      font-size: 16px;
    }
  }
  .weui-cell__ft {
    height:22px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .weui-label {
    min-width: 90px;
  }
  .bottom-wrap {
    position: fixed;
    bottom: 0;
    left: 0;
    right: 0;
  }
}
</style>
