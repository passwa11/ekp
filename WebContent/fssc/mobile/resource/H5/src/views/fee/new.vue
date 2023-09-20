<template>
  <div class="fs-fee-detail edit">
    <group gutter="0">
    <div v-for="(item,index) in formData" v-if="item.position=='1'&&item.showStatus!='3'&&item.type!='5'" class="fs-cell-div">
      <Cell :title="item.title" v-if="(item.type=='1'||item.type=='2')&&item.showStatus=='2'" :value="formValue[item.name]"></Cell>

      <x-input show-clear="false" v-if="item.type=='1'&&item.showStatus=='1'" :placeholder="item.placeholder" @on-change="item.changeValue" v-model="formValue[item.name]" :value="formValue[item.name]" :title="item.title"></x-input>

      <Datetime v-if="item.type=='2'&&item.showStatus=='1'" :title="item.title" v-model="formValue[item.name]" :value="formValue[item.name]" :placeholder="item.placeholder" @on-change="item.changeValue"></Datetime>

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

    
        
    
    <div class="fs-travel-wrap" v-for="(item,index) in formData" v-if="item.type=='5'">
      <group gutter="0">
        <cell :title="item.title">
          <router-link to="" class="fs-link" @click.native="addTravel(item)">
            <span class="fs-iconfont fs-iconfont-add"></span>新增{{item.title}}
          </router-link>
        </cell>
        <div class="fssc-swipe-div" style="border-top: 1px solid #e2e2e2;" v-for="(item1,index1) in item.list">
          <swipeout-item :threshold=".5" underlay-color="#ccc">
            <div slot="right-menu" >
              <swipeout-button @click.native="removeDetail(index,index1)" background-color="#D23934">移除</swipeout-button>
            </div>
            <div slot="content" v-on:click="editDetail(item1,item)" class="swipe-content">
              <div class="fs-detail-left">{{item1.leftShow}}</div>
              <div class="fs-detail-right">
                <div class="fs-detail-right-text">{{item1.rightShow}}</div>
                <div class="fs-iconfont fs-iconfont-right"></div>
              </div>
            </div>
          </swipeout-item>
        </div>
        
        </cell>
      </group>
    </div>

    <div class="fs-travel-wrap" v-for="(item,index) in formData" v-if="item.type=='6'&&item.position=='1'">
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
    <fsOrganization v-for="(item,index) in formData" v-if="item.type=='4'&&item.position=='1'&&item.showStatus=='1'" 
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

    <div v-transfer-dom v-for="(item,index) in formData" v-if="item.position=='1'&&item.showStatus!='3'&&item.type=='3'">
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
      <x-button type="primary" @click.native="saveFee()">提交</x-button>
    </div>

    <div v-transfer-dom>
      <popup v-model="showImage" height="100%" position="right" width="100%">
        <img :src="imgSrc" style="width:100%;height:100%;" v-on:click="showImage = false">
      </popup>
    </div>

    

    
    
  </div>
  
    
</template>

<script>
import { fsUpload,fsOrganization,slide } from '@comp'
import Toast from 'vux'
import kk from 'kkjs'
import axios from 'axios'
export default {
  name: "expenseNew",
  data() {
    return {
      action:'saveAtt',
      fileList:[],
      expand: true,
      
      fdId:'',
      
      attachments:[],
      showConfirm:false,
      fdMoney:'',
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
      feeCategoryId:'',
      formData:[],
      formValue:{},
      formMap:{}
    };
  },
  mounted(){
    if(this.$route.query.flag=='list'){
      this.clearData();
      this.feeCategoryId = this.$route.query.id
      this.formValue['fdTemplateId'] = this.feeCategoryId
      this.getFormData();
    }
    
  },
  created(){

  },
  activated(){

    
    // if(this.$route.query.flag=='list'){
    //   this.clearData();
    //   this.feeCategoryId = this.$route.query.id
    //   this.formValue['fdTemplateId'] = this.feeCategoryId
    //   this.getFormData();
    // }
    
    
  },
  components:{
    fsUpload,
    fsOrganization
  },
  watch:{
    '$route' (to,from){
      if(to.name=='feeNew'&&this.$route.query.flag=='list'){
        this.clearData();
        this.feeCategoryId = this.$route.query.id
      }
      if(to.name!='feeNew'){
        return;
      }
      if(this.$route.query.flag=='list'){
        this.clearData();
        this.feeCategoryId = this.$route.query.id
        this.formValue['fdTemplateId'] = this.feeCategoryId
        this.getFormData();
      }
      if(this.$route.query.data){
        if(typeof this.$route.query.data=='string'){
          return;
        }
        let contains = false;
        let ii = 0;
        for(let i =0;i<this.formData.length;i++){
          if(this.formData[i].name==this.$route.query.detail){
            ii = i;
            break;
          }
        }
        for(let i=0;i<this.formData[ii].list.length;i++){
          if(this.$route.query.data.fdId==this.formData[ii].list[i].fdId){
            this.formData[ii].list.splice(i,1,this.$route.query.data);
            contains = true;
          }
        }
        if(!contains){
          this.formData[ii].list.push(this.$route.query.data);
        }
        //如果有金额合计的，需要计算
        for(let i of this.formData){
          if(i.init=='8'){
            this.getSumValue(i.index)
          }
        }
      }
    }
  },
  methods:{
    getSumValue(i){
      let baseOn = this.formData[i].baseOn.split(".");
      let value = 0;
      for(let k of this.formData){
        if(k.name==baseOn[0]){
          for(let m of k.list){
            if(!isNaN(m[this.formData[i].baseOn])){
              value += m[this.formData[i].baseOn]*1;
            }
          }
        }
      }
      this.formValue[this.formData[i].name] = value;
    },
    removeDetail(index,index1){
      this.formData[index].list.splice(index1,1);
      //如果有金额合计的，需要计算
      for(let i of this.formData){
        if(i.init=='8'){
          this.getSumValue(i.index)
        }
      }
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
    clearData(){
      this.fdId='';
      this.attachments=[];
      this.formValue={};
      this.formData=[];
      this.formMap={};
      this.getGeneratorId();
    },
    beforeUpload(file){
      let key = '';
      for(let i of this.formData){
        if(i.type=='6'&&i.position=='1'){
          key = i.name;
        }
      }
      this.$vux.loading.show({'text':'附件上传中...'});
      this.showPop = false;
      let fd = new FormData();
      fd.append('file',file);//传文件
      axios.post(serviceUrl+"saveAtt&filename="+file.name+"&fdModelName=com.landray.kmss.fssc.fee.model.FsscFeeMain&fdModelId="+this.fdId+'&key='+key,fd,{headers: {'Content-Type': 'multipart/form-data'}}).then((res)=>{
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
    getFormData(){
      this.$api.post(serviceUrl+'getFeeFormData&fdTemplateId='+this.feeCategoryId,  { param: {} }).then((resData) => {
        for(let i=0;i<resData.data.data.length;i++){
          this.formMap[resData.data.data[i].text] = resData.data.data[i].name
          let obj = {
            name:resData.data.data[i].name,
            show:false,
            title:resData.data.data[i].title,
            type:resData.data.data[i].type,
            position:resData.data.data[i].position,
            init:resData.data.data[i].init,
            baseOn:resData.data.data[i].baseOn,
            placeholder:'请填写'+resData.data.data[i].title,
            dataSource:resData.data.data[i].dataSource,
            showStatus:resData.data.data[i].showStatus,
            required:resData.data.data[i].required,
            orgType:resData.data.data[i].orgType,
            multi:resData.data.data[i].multi,
            leftShow:resData.data.data[i].leftShow,
            rightShow:resData.data.data[i].rightShow,
            keyword:'',
            currentValue:[],
            showValue:'',
            value:'',
            data:[],
            list:[],
            index:i,
            changeValue:()=>{
              if(this.formData[i].type=='3'){
                this.formData[i].show = false;
                this.formData[i].keyword = '';
                this.getDataSource(this.formData[i].dataSource,this.formData[i].baseOn,i);
                for(let k of this.formData[i].data){
                  if(k.value==this.formData[i].currentValue){
                    this.formData[i].showValue = k.name;
                  }
                }
                this.formValue[resData.data.data[i].name] = this.formData[i].currentValue;
              }
              for(let k of this.formData){
                if((k.type=='3'||k.type=='4')&&k.baseOn.indexOf(this.formData[i].name)>-1&&k.position=='1'){
                  this.getDataSource(k.dataSource,k.baseOn,k.index)
                  //当前部门
                  if(k.init=='2'){
                    this.setDefaultDept(k.index);
                  }
                  //默认公司
                  if(k.init=='3'){
                    this.setDefaultCompany(k.index);
                  }
                }
              }
            },
            clearValue:()=>{
              this.formData[i].show = false;
              this.formData[i].keyword = ''
              this.formData[i].showValue = '';
              this.formData[i].currentValue = [];
            }
          };

          if(obj.type=='4'){
            this.formValue[obj.show] = false;
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
            this.$set(this.formValue,obj.name,obj.type=='1'||obj.type=='2' ?"":[]);
            //如果字段类型为对象，需要获取数据源
            if(obj.type=='3'&&obj.position=='1'){
              this.getDataSource(obj.dataSource,obj.baseOn,i);
            }
            //如果字段类型为组织架构，需要获取数据源
            if(obj.type=='4'&&obj.position=='1'){
              this.getOrganizationList(obj.name);
            }
            //当前用户
            if(obj.init=='1'&&!obj.baseOn&&obj.position=='1'){
              this.setDefaultUser(i);
            }
          }
          
          
          
        }
      }).catch( (error)=> {
        console.log(error);
      })
    },
    setDefaultUser(i){
      this.$api.post(serviceUrl+'getDefaultOrg',  { param: {} }).then((resData) => {
        this.formValue[this.formData[i].name+'_name']=resData.data.data[0].name;
        this.formValue[this.formData[i].name+'_id']=resData.data.data[0].id;
        this.formValue[this.formData[i].name+'_curName']=resData.data.data[0].name;
        this.formValue[this.formData[i].name+'_curId']=resData.data.data[0].id;
        this.formValue[this.formData[i].name] = resData.data.data[0].id;
        this.formData[i].showValue = resData.data.data[0].name;
        for(let k of this.formData){
          if(k.baseOn.indexOf(this.formData[i].name)>-1){
            //当前部门
            if(k.init=='2'){
              this.setDefaultDept(k.index);
            }
            //默认公司
            if(k.init=='3'){
              this.setDefaultCompany(k.index);
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
      }).catch( (error)=> {
        console.log(error);
      })
    },
    setDefaultCompany(i){
      let personId = this.formValue[this.formData[i].baseOn+'_id'];
      this.getDataSource(this.formData[i].dataSource,this.formData[i].baseOn,i);
      this.$api.post(serviceUrl+'getDefaultCompany&personId='+personId,  { param: {} }).then((resData) => {
        this.$set(this.formValue,this.formData[i].name,[resData.data.data[0].id]);
        this.formData[i].currentValue = [resData.data.data[0].id];
        this.formData[i].showValue = resData.data.data[0].name;
        for(let k of this.formData){
          if(k.baseOn.indexOf(this.formData[i].name)>-1){
            if(k.type=='3'){
              this.getDataSource(k.dataSource,k.baseOn,k.index)
            }
            //默认成本中心
            if(k.init=='4'&&k.position=='1'){
              this.setDefaultCost(k.index);
            }
            //默认币种
            if(k.init=='5'&&k.position=='1'){
              this.setDefaultCurrency(k.index);
            }
          }
        }
      }).catch( (error)=> {
        console.log(error);
      })
    },
    setDefaultCost(i){
      if(this.formData[i].position!='1'){
        return;
      }
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
    getDataSource(dataSource,baseOn,i){
      dataSource = this.resolveUrl(dataSource)+'&keyword='+this.formData[i].keyword;
      console.log("开始获取数据源，后台接口为："+dataSource)
      this.$api.post(domainPrefix+dataSource,  { param: {} }).then((resData) => {
        this.formData[i].data=resData.data.data;
        console.log("获取数据源"+this.formData[i].title+'成功')
      }).catch( (error)=> {
        console.log(error);
      })
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
    showAtt(item,index){
      if(item.value.indexOf('上传中')>-1){
        this.showText = true;
        this.textMessage  = '照片正在上传，请稍候再操作'
        return;
      }
      this.showImage = true;
      this.imgSrc = domainPrefix+'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=viewPic&cookieId='+LtpaToken+'&fdId='+item.value;
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
       this.$api.post(serviceUrl+'getOrganizationList&cookieId='+LtpaToken+'&currentDeptId='+currentDeptId+'&type='+type+'&child='+child+'&keyword='+keyword, {})
       .then((resData) => {
          if(resData.data.result=='success'){
            for(var i of this.formData){
              if(i.name==name){
                i.data = resData.data.data;
              }
            }
            this.formValue[name+'_curName']=resData.data.currentDeptName;
            this.formValue[name+'_curId']=resData.data.currentDeptId;
          }else{
            this.$vux.toast.show({type:'warn',text:resData.data.message,time:1500})
          }
      }).catch(function (error) {
        this.showWarn = true;
        this.warnMessage = error.message
      })
    },
    addTravel(item){
      if(item.baseOn&&(!this.formValue[item.baseOn]||this.formValue[item.baseOn].length==0)){
        this.showWarn = true;
        for(let i of this.formData){
          if(i.name==item.baseOn){
            this.$vux.toast.show({'text':i.placeholder,time:2000,type:'warn'});
          }
        }
        return;
      }
      let index = this.formData[item.index].list.length+1;
      this.$router.push({
        name:'feeNewTravel',
        query:{
          formValue:JSON.stringify(this.formValue),
          formMap:JSON.stringify(this.formMap),
          cateId:this.feeCategoryId,
          baseOn:item.baseOn,
          detail:item.name,
          leftShow:item.leftShow,
          rightShow:item.rightShow
        }
      })
    },
    editDetail(item,detail){
      this.$router.push({
        name:'feeNewTravel',
        query:{
          formValue:JSON.stringify(this.formValue),
          formMap:JSON.stringify(this.formMap),
          cateId:this.feeCategoryId,
          baseOn:detail.baseOn,
          data:JSON.stringify(item),
          detail:detail.name,
          leftShow:detail.leftShow,
          rightShow:detail.rightShow
        }
      })
    },
    getPicture(){
      
      kk.media.getPicture({
        sourceType: 'album',
        destinationType: 'file',
        encodingType: 'jpg',
        count: 1,
        chooseOrignPic: false // 选取原图
      },(res)=>{
          let filename = 'pic_'+new Date().getTime()+".jpg";
          this.$api.post(serviceUrl+'getGeneratorId&cookieId='+LtpaToken,  { param: {} })
            .then((resData) => {
            this.attachments.push({
              title:filename,
              value:'上传中：0%',
              id:resData.data.fdId
            });
            this.uploadPicture(res[0].imageURI,filename,resData.data.fdId,this.attachments.length-1);
          }).catch(function (error) {
            console.log(error);
          })
      });
    },
    uploadPicture(path,filename,id,index){
      var proxy = new kk.proxy.Upload({
        url:serviceUrl+'saveAtt&fdModelName=com.landray.kmss.fssc.fee.model.FsscFeeMain&cookieId='+LtpaToken+'&fdModelId='+this.fdId+'&filename='+filename+'&fdId='+id,
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
    saveFee(){
      let param = {
        fdId:this.fdId,
        fdTemplateId:this.feeCategoryId
      }
      for(let item of this.formData){
        if(item.position=='1'){

          if(item.type=='1'||item.type=='2'){
            if(item.required&&!this.formValue[item.name]){
              this.$vux.toast.show({type:'warn',text:item.placeholder,time:2000})
              return;
            }
            param[item.name] = this.formValue[item.name];
          }else if(item.type=='3'){
            if(item.required&&!this.formValue[item.name]||this.formValue[item.name].length==0){
              this.$vux.toast.show({type:'warn',text:item.placeholder,time:2000})
              return;
            }
            param[item.name] = this.formValue[item.name];
            for(let i of item.data){
              if(i.value==param[item.name]){
                param[item.name+'.name'] = i.name;
              }
            }
          }else if(item.type=='4'){
            if(item.required&&!this.formValue[item.name+'_id']){
              this.$vux.toast.show({type:'warn',text:item.placeholder,time:2000})
              return;
            }
            param[item.name+'.name'] = this.formValue[item.name+'_name'];
            param[item.name] = this.formValue[item.name+'_id'];
          }else{
            if(item.required&&item.list.length==0){
              this.$vux.toast.show({type:'warn',text:'请至少填写一行'+item.title,time:2000})
              return;
            }
            param[item.name] = item.list;
          }
        }
      }
      param.fdId = this.fdId;
      this.$vux.loading.show()
      this.$api.post(serviceUrl+'checkMobileBudget&params='+encodeURIComponent(JSON.stringify(param)), {})
       .then((resData) => {
          if(!resData.data.data.pass){
            this.$vux.loading.hide()
            this.$vux.toast.show({type:'cancel',text:resData.data.data.message})
            return;
          }
          let params = resData.data.data.data
          this.$api.post(serviceUrl+'saveFeeMain&param='+encodeURIComponent(JSON.stringify(params)), {})
           .then((resData) => {
              this.$vux.loading.hide()
              if(resData.data.result=='success'){
                this.$vux.toast.show({type:'success',text:'操作成功',time:2000})
                this.$router.push({path:'/fee/list'})
              }else{
                this.$vux.toast.show({type:'cancel',text:'操作失败',time:2000})
                console.log(resData.data.message)
              }
          }).catch( (error)=> {
            this.$vux.loading.hide()
            console.log(error);
          })
      }).catch( (error)=> {
        this.$vux.loading.hide()
        console.log(error);
      })
    }
  }
};
</script>

<style lang="less">
@import "../../assets/css/variable.less";
.fs-detail-left{
  width:60%;
  float: left;
  overflow: hidden;
  text-overflow:ellipsis;
  white-space: nowrap;
}
.fs-detail-right{
  width:30%;
  float: right;
  overflow: hidden;
  text-overflow:ellipsis;
  white-space: nowrap;
  margin-right: 11px;
  text-align: right;
}
.fs-detail-right .fs-iconfont{
  float: right;
  color:#ccc;
}
.fs-detail-right-text{
  float: left;
  color:#888;
  overflow: hidden;
  text-overflow:ellipsis;
  white-space: nowrap;
  width:80%;
}
.swipe-content{
  height:52px;
  font-size: 14px;
  margin-left: 15px;
  line-height: 52px;
}
.fs-swipe-div{border-top: 1px solid #e2e2e2;}
.fs-cell-div{border-bottom: 1px solid #e2e2e2;}
.fs-fee-detail {
  // background: @splitColor;
  padding-bottom: 60px;
  .vux-x-input.disabled .weui-input {
    -webkit-text-fill-color: #333;
  }
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
      color: #ea4335;
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
  .fs-link{
    color: @mainColor;
    .fs-iconfont {
      font-weight: bold;
      font-size: 14px;
      font-weight: bold;
      padding-right: 3px;
    }
  }
  // 添加行程
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
  .travel-list {
    .vux-no-group-title {
      margin-top: 0;
    }
    .weui-cell {
      background: #fcfcfc;
      font-size: 13px;
    }
    .vux-label {
      font-size: 13px;
      color: #666;
    }
    .weui-cell__ft {
      color: #333;
    }
    .travel-item::before,
    .item-header::before {
      content: "";
      z-index: 2;
      height: 1px;
      position: absolute;
      left: 0;
      right: 0;
      top: 0;
      background: #e7e7e7;
      transform: scaleY(0.5);
    }
    .item-header::before {
      bottom: 0;
      top: auto;
    }
    .travel-item {
      position: relative;
      max-height: 50px;
      overflow: hidden;
      transition: all 0.3s ease;
      &.active {
        max-height: 450px;
        transition: all 0.3s ease;
        .item-header .fs-iconfont {
          transform: rotate(90deg);
          transition: all 0.3s ease;
        }
      }
      .item-header {
        font-size: 13px;
        color: #666;
        position: relative;
        background: #fff;
        padding: 17px 15px;
        .fs-iconfont {
          transition: all 0.3s ease;
          transform: rotate(0deg);
          margin-left: 3px;
          font-size: 13px;
          width: 15px;
          height: 15px;
          text-align: center;
          line-height: 15px;
          display: inline-block;
        }
        .del-btn {
          color: #ee5555;
          position: absolute;
          right: 15px;
          top: 50%;
          padding: 5px;
          margin-top: -10px;
        }
      }
    }
  }
}
</style>
