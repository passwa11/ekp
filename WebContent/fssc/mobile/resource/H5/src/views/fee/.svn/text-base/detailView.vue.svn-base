<template>
  <div class="fs-fee-detail view">
    <group gutter="0">
      <Cell v-for="(item,index) in formData" v-if="item.position=='1'&&item.showStatus!='3'&&item.type!='5'" :title="item.title" :value="item.value"></Cell>
      
    </group>

    

    <div class="fs-travel-wrap" v-for="(item,index) in formData" v-if="item.position=='1'&&item.showStatus!='3'&&item.type=='5'">
      <group gutter="0">
        <cell :title="item.title">
        </cell>
        <cell :title="item1.leftShow" :key="index" :value="item1.rightShow" v-for="(item1,index) in item.list" is-link link="" @click.native="viewDetail(item1,item)">
        </cell>
      </group>
    </div>

      <div class="fs-travel-wrap">
      <group gutter="0">
        <cell title="附件">
        </cell>
        <cell :title="item.title" :key="index" v-for="(item,index) in attachments" is-link link="" @click.native="showAtt(item)">
        </cell>
      </group>
    </div> 

    <div>
    <iframe id="frame" width="100%" frameborder="0"  height="500px" src =""></iframe>
    </div>

    <div v-transfer-dom>
      <popup v-model="showImage" height="100%" position="right" width="100%">
        <img :src="imgSrc" style="width:100%;height:100%;" v-on:click="showImage = false">
      </popup>
    </div>

  </div>
</template>

<script>
import { fsUpload } from '@comp'
export default {
  name: "expenseNew",
  data() {
    return {
      docSubject:'',
      docContent:'',
      fdCompanyName:'',
      fdCostCenterName:'',
      fdPersonName:'',
      fdProjectName:'',
      fdMoney:'',
      attachments:[],
      travelDetails:[],
      showImage:false,
      imgSrc:'',
      formData:[]
    };
  },
  components:{
    fsUpload
  },
  activated(){
    if(this.$route.params.fdId){
      this.formData = [];
      this.getFormData();
    }
    
  },
  mounted(){
    //this.getFormData();
  },
  methods:{
    showAtt(item){
      this.imgSrc = domainPrefix+'fssc/mobile/fssc_mobile_note/fsscMobileNote.do?method=viewPic&fdId='+item.id;
      this.showImage = true;
    },
    initData(){
      if(this.$route.params.fdId){
        this.$api.post(serviceUrl+'getFeeMainById&cookieId='+LtpaToken+'&fdId='+this.$route.params.fdId,  {  })
        .then((resData) => {
          if(resData.data.result=='success'){
            let data = resData.data.data;
            for(let i in data){
              for(let k=0;k<this.formData.length;k++){
                if(this.formData[k].name==i){
                  if(this.formData[k].type=='5'){
                    let list = data[i];
                    for(let m=0;m<list.length;m++){
                      let detail = list[m];
                      detail.title = this.formData[k].title+(m+1);
                      this.formData[k].list.push(detail)
                    }
                  }else{
                    this.formData[k].value = data[i];
                  }
                }
              }
            }
            this.attachments = resData.data.data.attachments;
          }
        }).catch(function (error) {
          console.log(error);
        })
        let url = domainPrefix+"fssc/fee/fssc_fee_main/fsscFeeMain.do?method=viewLbpm&fdId=";
        //let url = "http://172.16.213.175:8086/ekp/fs/fee/fs_fee_mobile_data/fsFeeMobileData.do?method=viewFlow&fdId=";
        document.getElementsByTagName("iframe")[0].setAttribute("src",url+this.$route.params.fdId)
      }
    },
    getFormData(){
      this.$api.post(serviceUrl+'getFeeFormData&fdTemplateId='+this.$route.params.fdId,  { param: {} }).then((resData) => {
        let index = 0;
        for(let i=0;i<resData.data.data.length;i++){
          console.log("需要显示的字段："+resData.data.data[i].title)
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
            value:'',
            data:[],
            list:[],
            index:i,
          };
          
          this.formData.push(obj);
        }
        this.initData(); 
      }).catch( (error)=> {
        console.log(error);
      })
    },
    viewDetail(item1,item){
      this.$router.push(
        {
          "name":'FeeViewTravel',
          "params":{
            formData:JSON.stringify(this.formData),
            detail:item.name,
            data:JSON.stringify(item1)
          }
        }
      )
    }
  }
};
</script>

<style lang="less">
@import "../../assets/css/variable.less";
.fs-fee-detail.view {
  // background: #f5f5f5;
  .weui-cell__ft {
    min-height:22px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    color: #333;
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
      }
    }
  }
}
</style>
