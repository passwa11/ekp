<template>
  <div class="fs-organization">
    <div 
      class="popup-panel"
      :class="{
        'active': showPanel,
        'check': multiple,
        'radio': !multiple
      }">
      <div class="container">
        <div class="header">
          <div class="left" @click="toParent">
            <span class="fs-iconfont fs-iconfont-left"></span>
            上一级
          </div>
          <div class="center">{{currentDeptName}}</div>
          <div class="right" @click="closePop">取消</div>
        </div>
        <div class="body">
          <div class="search-wrap" style="height:28px;margin-bottom:30px;">
            <search :auto-fixed="false" @on-submit="searchOrg" v-model="keyword" @on-cancel="searchCancel"></search>
          </div>
          <ul
            class="list"
            v-if="cate.list.length > 0"
            v-for="(cate, index) in organList"
            :key="index">
            <li class="cate">
              <div class="cate-header">部门</div>
              <!--部门-->
              <ul class="inner-list">
                <li class="item" v-for="(item, index) in cate.list" :key="index">
                  <label v-if="item.type=='dept'">
                      <input v-if="multiple&&type=='dept'" type="checkbox" v-model="item.selected">
                      <input v-if="multiple==false&&type=='dept'" type="radio" name="organ" v-model="item.selected">
                      <div class="select-wrap" :class="{
                        'selected': item.selected
                      }">
                        <div @click="choose(item)" class="radio" v-if="multiple==false&&type=='dept'"></div>
                        <div @click="choose(item)" v-if="multiple&&type=='dept'" class="check">
                          <span class="fs-iconfont fs-iconfont-dagou"></span>
                        </div>
                      </div>
                      <div class="content-wrap" @click="toChild(item)">
                        <div class="dept-wrap">
                          <!-- <img :src="item.avatar"> -->
                          <!-- <img src="" alt=""> -->
                        </div>
                        <p class="name">
                          <span class="name-text">{{item.name}}</span>
                          <span class="arr_right fs-iconfont fs-iconfont-right"></span>
                        </p>

                      </div>
                      
                  </label>
                </li>
              </ul>
              <div class="cate-header" v-if="type=='person'">员工</div>
              <!--员工-->
              <ul class="inner-list" v-if="type=='person'">
                <li class="item" v-for="(item, index) in cate.list" :key="index">
                  <label @click="choose(item)" v-if="item.type=='person'">
                      <input v-if="multiple&&type=='person'" type="checkbox" v-model="item.selected">
                      <input v-else type="radio" name="organ" v-model="item.selected">
                      <div class="select-wrap" :class="{
                        'selected': item.selected
                      }">
                        <div class="radio" v-if="multiple==false&&type=='person'"></div>
                        <div v-if="multiple&&type=='person'" class="check">
                          <span class="fs-iconfont fs-iconfont-dagou"></span>
                        </div>
                      </div>
                      <div class="content-wrap">
                        <div class="avatar-wrap">
                          <!-- <img :src="item.avatar"> -->
                          <!-- <img src="" alt=""> -->
                        </div>
                        <p class="name">
                          {{item.name}}
                          
                        </p>

                      </div>
                      
                  </label>
                </li>
              </ul>
            </li>
          </ul>
        </div>
        <div class="footer">
          <div class="container">
            <div class="list-wrap">
              <ul
                class="list clearfix"
                :style="{
                  'width': selectedList.length * 54 + 20 + 'px'
                }">
                <li v-for="(item, index) in selectedList" :key="index">
                  <div class="avatar-wrap">
                    <!-- <img :src="item.avatar"> -->
                  </div>
                  <p class="name">{{item.name}}</p>
                </li>
              </ul>
            </div>
            <x-button type="primary" @click.native="confirm">确定({{selectedList.length}})</x-button>
            <!-- <div class="btn">确定({{selectedList.length}})</div> -->
          </div>
        </div>
      </div>
    </div>
    <div
      class="popMask"
      :class="{
        'active': showPanel
      }"
      @click="closePop">
    </div>
    
  </div>
</template>

<script>
export default {
  name: "fsOrganization",
  data() {
    return {
      keyword:''
    };
  },
  props: {
    showPanel: {
      type: Boolean,
      default: false
    },
    currentDeptName: {
      type: String,
      default: ""
    },
    currentDeptId: {
      type: String,
      default: ""
    },
    list: {
      type: Array,
      default: []
    },
    multiple: {
      type: Boolean,
      default: true
    },
    fieldName:{
      type:String,
      default:''
    },
    type:{
      type : String,
      default:''
    }
  },
  methods: {
    // 点击取消按钮
    closePop() {
      // 向父组件发起事件 关闭弹出框
      this.$emit("onClosePop");
    },
    // 点击确定按钮
    confirm(){
      this.$emit('onConfirmSelect',this.selectedList)
    },
    // 单选的选择时间
    choose(item){
      if(!this.multiple&&this.type==item.type){
        // console.log('点击了选择')
        this.$emit('onConfirmSelect',{item:item,name:this.fieldName})
      }
    },
    toParent(){
      this.$emit('toParent',{currentDeptId:this.currentDeptId,name:this.fieldName})
    },
    toChild(item){
      this.$emit('toChild',{currentDeptId:item.id,child:true,name:this.fieldName})
    },
    searchOrg(){
      this.$emit('searchOrg',{keyword:this.keyword,name:this.fieldName});
    },
    searchCancel(){
      this.$emit('searchCancel',this.fieldName);
    }
  },
  computed: {
    organList() {
      return this.list
    },
    selectedList() {
      let i = 0,arr=[];
      if (this.organList.length > 0) {
        this.organList.forEach(item => {
          item.list.forEach(childItem => {
            if (childItem.selected) {
              // i++;
              arr.push(childItem)
            }
          });
        });
      }
      return arr;
    }
  }
};
</script>

<style lang="less">
.fs-organization {
  .popup-panel {
    background: #fff;
    position: fixed;
    width: 80%;
    height: 100%;
    right: -100%;
    top: 0;
    transition: all 0.3s ease;
    z-index: 3;
    &.active {
      right: 0;
      transition: all 0.3s ease;
    }
  }
  .container {
    position: relative;
    height: 100%;
  }
  .popMask {
    background: rgba(0, 0, 0, 0.5);
    position: fixed;
    left: 0;
    top: 0;
    width: 0;
    height: 0;
    z-index: 2;
    opacity: 0;
    transition: opacity 0.3s ease;
    &.active {
      opacity: 1;
      height: 100%;
      width: 100%;
      transition: opacity 0.3s ease;
    }
  }
  .header {
    height: 40px;
    line-height: 40px;
    position: absolute;
    top: 0;
    right: 0;
    width: 100%;
    background: #4285f4;
    color: #fff;
    text-align: center;
    z-index: 3;
    .left {
      position: absolute;
      top: 0;
      left: 0;
      padding: 0 10px;
      &:active {
        color: rgba(255, 255, 255, 0.6);
      }
    }
    .center {
      padding: 0 75px;
      font-size: 16px;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }
    .right {
      position: absolute;
      top: 0;
      right: 0;
      padding: 0 10px;
      &:active {
        color: rgba(255, 255, 255, 0.6);
      }
    }
  }
  .body {
    height: 100%;
    overflow-y: scroll;
    padding: 40px 0 60px;
    box-sizing: border-box;
    .list {
      .cate {
        &-header {
          font-size: 14px;
          color: #7f7f7f;
          background: #eaeaea;
          padding: 2px 10px;
          min-height: 20px;
          box-sizing: border-box;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
        .inner-list {
          .item {
            position: relative;
            label {
              display: block;
            }
            input[type="checkbox"],
            input[type="radio"] {
              display: none;
            }
            .select-wrap {
              padding: 0 14px;
              text-align: center;
              position: absolute;
              left: 0;
              top: 50%;
              margin-top: -7px;
            }
            .content-wrap {
              line-height: 50px;
              margin: 0 10px 0 45px;
              position: relative;
              .name {
                font-size: 14px;
                color: #333;
                padding-left: 40px;
                overflow: hidden;
                text-overflow: ellipsis;
                white-space: nowrap;
                .name-text{
                  text-overflow: ellipsis;
                  width:80%;
                  display: inline-block;
                }
              }
            }
            &:after {
              content: "";
              height: 1px;
              position: absolute;
              right: 0;
              left: 0;
              bottom: 0;
              transform: scaleY(0.5);
              background: #e5e5e5;
            }
            &:last-child:after {
              height: 0;
            }
          }
        }
      }
    }
  }
  .footer {
    background: #fff;
    position: absolute;
    left: 0;
    right: 0;
    bottom: 0;
    height: 60px;
    border-top: 1px solid #eee;
    .container {
      position: relative;
      padding-right: 82px;
    }
    &::-webkit-scrollbar {
      width: 0 !important;
    }
    .list-wrap {
      overflow-x: scroll;
    }
    .list {
      height: 60px;
      box-sizing: border-box;
      padding: 0 10px;
      li {
        float: left;
        width: 50px;
        padding: 0 2px;
        height: 100%;
        text-align: center;
        .avatar-wrap {
          position: relative;
          top: 0;
          margin: 5px auto 4px;
        }
        p {
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
          font-size: 10px;
        }
      }
    }
    .weui-btn {
      position: absolute;
      right: 0;
      bottom: 0;
      height: 60px;
      text-align: center;
      width: 82px;
      color: #fff;
      background: #4285f4;
      font-size: 16px;
      line-height: 60px;
      &:active {
        color: rgba(255, 255, 255, 0.6);
      }
    }
  }
  .avatar-wrap {
    width: 32px;
    height: 32px;
    position: absolute;
    left: 0;
    top: 50%;
    margin-top: -16px;
    background: url("../../assets/images/default_avatar.png");
    background-size: 100%;
    border-radius: 50%;
    overflow: hidden;
    img {
      line-height: 40px;
      max-width: 100%;
      max-height: 100%;
      text-align: center;
    }
  }
  .dept-wrap{
    width: 32px;
    height: 32px;
    position: absolute;
    left: 0;
    top: 50%;
    margin-top: -16px;
    background: url("../../assets/images/icon/icon-organ.png");
    background-size: 100%;
    border-radius: 50%;
    overflow: hidden;
    img {
      line-height: 40px;
      max-width: 100%;
      max-height: 100%;
      text-align: center;
    }
  }
  .select-wrap > div {
    display: none;
    &.check {
      .fs-iconfont {
        color: #fff;
        box-sizing: border-box;
        width: 14px;
        height: 14px;
        line-height: 14px;
        display: block;
        border: 1px solid #dfe1e0;
        &:before {
          opacity: 0;
          font-size: 12px;
          position: relative;
          top: -1px;
        }
      }
    }
    &.radio {
      width: 14px;
      height: 14px;
      background: #fff;
      border: 1px solid #dfe1e0;
      box-sizing: border-box;
      border-radius: 50%;
    }
  }
  .select-wrap.selected > .check {
    .fs-iconfont {
      background: #4285f4;
      border: 0;
      &:before {
        opacity: 1;
      }
    }
  }
  .select-wrap.selected > .radio {
    border: 5px solid #4285f4;
  }
  .check .select-wrap > .check {
    display: block;
  }
  .radio .select-wrap > .radio {
    display: block;
  }
  // 单选状态，不需要底部按钮
  .radio .footer{
    display: none;
  }
  .arr_right{float:right;}
  .dept-wrap{
    display: block;
    width: 36px;
    height: 36px;
    border-radius: 50%;
    background-color: #4285f4;
    background-repeat: no-repeat;
    background-image: url(../../assets/images/icon/icon-organ.png);
    background-position: center;
    background-size: 22px;
    float:left;
  }
}
</style>

