define([
  "dojo/_base/declare",
  "dojo/topic",
  "dojo/dom-construct",
  "dojo/_base/array",
  "dojo/_base/lang",
  "mui/util",
  "mui/list/JsonStoreList"
], function(declare, topic, domConstruct, array, lang, util, JsonStoreList) {
  return declare("mui.category.CategoryList", [JsonStoreList], {
    //数据请求URL
    dataUrl: "",

    //父分类ID
    parentId: null,

    //选择类型要求
    selType: null,

    //当前值初始
    curIds: null,

    //当前值初始
    curNames: null,

    //单选|多选
    isMul: false,

    baseClass: "muiCateLists",

    //对外事件对应的唯一标示
    key: null,

    //搜索后不允许再往下查看子分类
    showMore: true,

    buildRendering: function() {
      this.url = util.urlResolver(this.dataUrl, this)

      this.inherited(arguments)
      this.domNode.className = this.baseClass
    },

    postCreate: function() {
      this.inherited(arguments)
      this.subscribe("/mui/category/changed", "_cateChange")
      //			this.subscribe("/mui/search/submit","_cateChange");
      this.subscribe("/mui/search/cancel/back","_cateReback");
      this.subscribe("/mui/category/selected", "_cateSelected")
      this.subscribe("/mui/cate/navTo", "_scrollToCate")
      this.subscribe("/mui/category/selChanged", "_setCurSel")
      this.subscribe("/mui/view/afterScroll", "_setNavInfo")
    },

    reload: function() {
      this.buildLoading()
      this.inherited(arguments)
    },

    startup: function() {
      this.inherited(arguments)
    },

    buildLoading: function() {
      if (this.tmpLoading == null) {
        array.forEach(this.getChildren(), function(child) {
          child.destroyRecursive()
        })
        this.tmpLoading = domConstruct.create(
          "li",
          {
            className: "muiCateLoading",
            innerHTML: '<i class="mui mui-loading mui-spin"></i>'
          },
          this.domNode
        )
      }
    },

    onComplete: function() {
      if (this.tmpLoading) {
        domConstruct.destroy(this.tmpLoading)
        this.tmpLoading = null
      }
      this.inherited(arguments)
      //#165145 如果页签切换了，第一次打开分类时重置条件
      if (window.cateFilterSetSelc && window.cateFilterSetSelc.openCount <= 1) {
        var els = document.getElementsByClassName('muiCateClearBtn');
        //#167322
        if (els && els.length > 0 && !els[0].className.contains("muiModelingCancelBtn")) {
          els[0].click();
        }
      }
    },

    _setCurSel: function(srcObj, evt) {
      if (srcObj.key == this.key) {
        if (evt) {
          this.curIds = evt.curIds
          this.curNames = evt.curNames
        }
      }
    },

    _setNavInfo: function(srcObj, evt) {
      if (srcObj.key == this.key) {
        var chs = this.getChildren()
        var selItem = null
        if (evt.to.y < 0) {
          var redraw = false
          for (var i = 0; i < chs.length; i++) {
            if (
              chs[i].header == "true" &&
              0 - evt.to.y >
                chs[i].domNode.offsetTop - chs[i].domNode.offsetHeight
            ) {
              topic.publish("/mui/category/navChange", this, {
                label: chs[i].getTitle()
              })
              redraw = true
            }
          }
          if (!redraw) topic.publish("/mui/category/navChange", this, null)
        } else {
          topic.publish("/mui/category/navChange", this, null)
        }
      }
    },

    //滚动到header
    _scrollToCate: function(srcObj, evt) {
      if (srcObj.key == this.key) {
        if (evt) {
          var chs = this.getChildren()
          var selItem = null
          for (var i = 0; i < chs.length; i++) {
            if (chs[i].header == "true" && chs[i].label == evt.label) {
              selItem = chs[i]
              break
            }
          }
          if (selItem) {
            topic.publish("/mui/view/scrollTo", this, {
              y: 0 - selItem.domNode.offsetTop
            })
          }
        }
      }
    },

    //搜索后返回
    _cateReback: function(srcObj, evt) {
      if (srcObj.key == this.key) {
        if (this._addressUrl) {
          this.url = this._addressUrl
        }

        //清空侧边导航栏数据
        topic.publish("/mui/category/clearNav", this)

        //TODO  loading
        this.showMore = true
        this.buildLoading()
        this.reload()
      }
    },

    //往下查看数据
    _cateChange: function(srcObj, evt) {
      if (srcObj.key == this.key) {
        if (evt && evt.url) {
          if (!this._addressUrl) {
            this._addressUrl = this.url
          }
          this.showMore = false
          this.url = evt.url
        } else {
          this.showMore = true
          this.parentId = evt.fdId
          this.url = util.urlResolver(util.formatUrl(this.dataUrl), this)
        }

        //清空侧边导航栏数据
        topic.publish("/mui/category/clearNav", this)

        this.buildLoading()
        this.reload()
      }
    },

    //选中
    _cateSelected: function(srcObj, evt) {
      if (srcObj.key == this.key) {
        if (!this.isMul) {
          this.curIds = evt.fdId
          this.curNames = evt.label
          topic.publish("/mui/category/clearSelected", this, {fdId: evt.fdId})
          //					array.forEach(this.getChildren(),lang.hitch(this,function(item){
          //						if(item.header!='true' && item.fdId != evt.fdId){
          //							topic.publish("/mui/category/cancelSelected",this,{fdId:item.fdId});
          //						}
          //					}));
          if (!this.confirm) {
            topic.publish("/mui/category/submit", this, {
              key: this.key,
              curIds: evt.fdId,
              curNames: evt.label
            })
          }
        }
      }
    }
  })
})
