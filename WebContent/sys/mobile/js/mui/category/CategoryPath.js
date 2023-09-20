define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dijit/_Contained",
  "dijit/_Container",
  "dojo/dom-construct",
  "./PathItem",
  "dojo/dom-style",
  "dojo/request",
  "dojo/topic",
  "dojo/_base/array",
  "mui/util",
  "mui/i18n/i18n!sys-mobile:mui.all.category"
], function(
  declare,
  WidgetBase,
  _Contained,
  _Container,
  domConstruct,
  PathItem,
  domStyle,
  request,
  topic,
  array,
  util,
  msg
) {
  var path = declare(
    "mui.category.CategoryPath",
    [_Contained, _Container, WidgetBase],
    {
      // 渲染模板
      itemRenderer: PathItem,
      //标示
      key: null,

      //当前分类id,可不填写,无此属性,则不需和后台交互
      curId: null,

      //必填属性
      curName: null,

      height: "inherit",

      baseClass: "muiCatePath",

      titleNode: msg['mui.all.category'],

      //获取详细信息地址
      detailUrl: null,

      buildRendering: function() {
        this.inherited(arguments)
        window.pathItem = null
        this.containerNode = domConstruct.create(
          "ul",
          {className: "muiCatePathContent"},
          this.domNode
        )
      },

      getPathContainer: function() {
        return this.containerNode
      },

      _createTitleNode: function() {
        var itemTag = domConstruct.create("div", { className: 'muiCatePathTitleItem' }, this.containerNode)
        var textNode = domConstruct.create(
          "div",
          {
            className: ""
          },
          itemTag
        )
        var labelNode = domConstruct.create(
          "span",
          {
            className: ""
          },
          textNode
        )
        labelNode.innerHTML = this.titleNode

        this.connect(itemTag, "onclick", function() {
        	// 生态组织
        	if(this.isEco && this.isEcoPath){
        		topic.publish("/mui/category/cancel", {
        			key: null
        		});
        		// 顶层隐藏公司卡片
        		var data = new Object();
        		data.isShow = false;
        		data.fdId = null;
        		topic.publish("/sys/org/eco/card/reload", data);
        	}
        	
          this._chgHeaderInfo(this, {})
          
          topic.publish("/mui/category/changed", this, {parentId: ""})
        })
        return itemTag
      },

      postCreate: function() {
        this.inherited(arguments)
        this.subscribe("/mui/category/changed", "_chgHeaderInfo")
      },

      startup: function() {
        if (this._started) {
          return
        }
        this.inherited(arguments)
        this._initPath()
      },
      
      _showHeader: function(srcObj) {
        if (srcObj.key == this.key) {
          domStyle.set(this.domNode, {display: "block"})
        }
      },

      _hideHeader: function(srcObj) {
        if (srcObj.key == this.key) {
          domStyle.set(this.domNode, {display: "none"})
        }
      },

      _refreshHeader: function() {},

      // 构建子项
      createListItem: function(item) {
        var item = new this.itemRenderer(this._createItemProperties(item))
        return item
      },

      // 格式化数据
      _createItemProperties: function(item) {
        return item
      },
      
      _initPath: function(){
    	  if (this.domNode.parentNode) {
              var h
              if (this.height === "inherit") {
                if (this.domNode.parentNode) {
                  h = this.domNode.parentNode.offsetHeight + "px"
                }
              } else if (this.height) {
                h = this.height
              }
              domStyle.set(this.domNode, {height: h, "line-height": h})
    	  }
    	  this._chgHeaderInfo(this, {fdId: this.curId})
          topic.publish("/mui/category/changed", this, {fdId: this.curId})
      },
      
      _createPath: function(items) {
        var evt = []
        var _self = this
        _self.getPathContainer().innerHTML = ""
        _self._createTitleNode()

        if (items.length == 0) {
          return
        }

        array.forEach(
          items,
          function(item) {
            _self._createPathItem(item, item.label)
          },
          this
        )

        window.pathItem = evt
        topic.publish("/mui/category/path", this, evt)
      },

      // 构建面包屑项目
      _createPathItem: function(item, label, container) {
        container = container || this.getPathContainer()
        var itemTag = domConstruct.create(
          "div",
          {
            className: "muiCatePathItem",
            value: item.fdId
          },
          container
        )
        var textNode = domConstruct.create(
          "div",
          {
            className: ""
          },
          itemTag
        )
        if (label) {
          var labelNode = domConstruct.create(
            "span",
            {
              className: ""
            },
            textNode
          )
          labelNode.innerHTML = label
        }

        this.connect(itemTag, "onclick", function() {
          var fdId = item.fdId
          if (fdId != this.curId) {
            this._chgHeaderInfo(this, {fdId: fdId})

            var index = -1
            array.forEach(
              this.items,
              function(item, idx) {
                if (item["fdId"] == fdId) {
                  index = idx
                  return false
                }
              },
              this
            )

            var obj = {fdId: fdId, parentId: ""}
            if (index >= 1) {
              obj.parentId = this.items[index - 1].fdId
            }
            topic.publish("/mui/category/changed", this, obj)
            
            // 顶层隐藏公司卡片
            if(this.isEco && this.isEcoPath) {
            	var data = new Object();
            	if(item.fdOrgType == 1){
            		data.isShow = false;
            		data.fdId = fdId;
            	}else {
            		data.isShow = true;
            		data.fdId = fdId;
            		data.label = item.label;
            		data.fdNo = item.fdNo;
            		data.fdAdmin = item.admin;
            	}
            	topic.publish("/sys/org/eco/card/reload", data);
            	var changeData = new Object();
            	changeData.fdId = item.fdId;
            	changeData.fdOrgType = item.fdOrgType;
            	changeData.fdInviteUrl = item.fdInviteUrl;
            	topic.publish("/sys/org/eco/btn/change", changeData);
            	topic.publish("/sys/org/eco/btn/setId", item.fdId);
            }
          }
        });
        
        return itemTag;
      },

      _chgUrlInfo: function(url, evt) {
        return url
      },

      _chgHeaderInfo: function(srcObj, evt) {
        if (srcObj.key == this.key) {
          if (evt) {
            if (evt.fdId) {
              domStyle.set(this.domNode, {display: "block"})
              if (
                this.curId != evt.fdId ||
                this.containerNode.innerHTML == ""
              ) {
                this.curId = evt.fdId
                var _url = util.urlResolver(this.detailUrl, this)
                _url = util.formatUrl(_url)
                _url = this._chgUrlInfo(_url, evt)
                var promise = request.post(_url, {
                  handleAs: "json"
                })
                var _self = this
                promise.then(
                  function(items) {
                    _self.items = items
                    _self._createPath(items)
                  },
                  function(data) {
                    //错误处理
                  }
                )
              }
            } else {
              this.curId = null
              this._createPath([])
            }
          }
        }
      }
    }
  )
  return path
})
