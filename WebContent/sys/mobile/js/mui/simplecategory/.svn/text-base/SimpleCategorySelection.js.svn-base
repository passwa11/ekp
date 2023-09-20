define([
  "dojo/_base/declare",
  "mui/category/CategorySelection",
  "dojo/dom-construct",
  "dojo/dom-class",
  "dojo/request",
  "mui/util",
  "dojo/_base/lang",
  "mui/i18n/i18n!sys-mobile:mui.button.reset",
  "mui/i18n/i18n!sys-mobile:mui.mobile"
], function(
  declare,
  CategorySelection,
  domConstruct,
  domClass,
  request,
  util,
  lang,
  msg,
  info
) {
  var selection = declare(
    "mui.simplecategory.SimpleCategorySelection",
    [CategorySelection],
    {
      modelName: null,

      // 只显示末尾三级
      max: 3,

      // 是否必填
      required: false,

      pathUrl:
        "/sys/category/mobile/sysSimpleCategory.do?method=pathList&cateId=!{curId}&modelName=!{modelName}&fdTmepKey=!{fdTmepKey}",

      //获取详细信息地址
      detailUrl:
        "/sys/category/mobile/sysSimpleCategory.do?method=detailList&cateId=!{curIds}&modelName=!{modelName}&fdTmepKey=!{fdTmepKey}",

      buildIcon: function() {
        return null
      },

      _buildSelItem: function() {
        var item = this.inherited(arguments)
        domClass.add(item, "muiCategorySItem")
        return item
      },

      startup: function() {
        this.inherited(arguments)
        if (!this.isMul) {
          domClass.add(this.leftArea, "muiCateSecBtnDis")
          //          if(this.required) {
          //        	    this.subHandle = this.connect(this.buttonNode, "click", "_subSelItem")
          //          }
          if (!this.required) {
            this.clearButtonNode.innerHTML = msg['mui.button.reset']
          }
        }
      },

      _resizeSelection: function() {
        if (!this.isMul) {
          var items = this.cateSelArr

          var html = ""
          if (items.length > 0) {
            domClass.remove(this.buttonNode, "muiCateSecBtnDis")
            // 确定事件
            if (this.subHandle == null) {
              this.subHandle = this.connect(
                this.buttonNode,
                "click",
                "_subSelItem"
              )
            }

            html = items[0].label
            this.clearButtonNode.className = "muiCateClearBtn"
            // 清空事件
            if (this.clearHandle == null) {
              this.clearHandle = this.connect(
                this.clearButtonNode,
                "click",
                lang.hitch(this, function() {
                  this._clearSelItem(this)
                  this.clearButtonNode.className =
                    "muiCateClearBtn muiCateSecBtnDis"
                  if (this.clearHandle) {
                    this.disconnect(this.clearHandle)
                    this.clearHandle = null
                  }
                })
              )
            }
          } else {
            this.clearButtonNode.className = "muiCateClearBtn muiCateSecBtnDis"
            if (this.clearHandle) {
              this.disconnect(this.clearHandle)
              this.clearHandle = null
            }

            if (this.required) {
              domClass.add(this.buttonNode, "muiCateSecBtnDis")
              if (this.subHandle) {
                this.disconnect(this.subHandle)
                this.subHandle = null
              }
            } else {
              if (this.subHandle == null) {
                this.subHandle = this.connect(
                  this.buttonNode,
                  "click",
                  "_subSelItem"
                )
              }
            }
          }

          if (html) {
            html = info['mui.mobile.sysMobile.simpleCategory.selected'] + "：" + html
          }
          this.leftArea.innerHTML = html

          return
        }

        this.inherited(arguments)
      },

      // 构建描述
      buildDesc: function(node, item) {
        var url = util.urlResolver(this.pathUrl, {
          modelName: this.modelName,
          curId: item.fdId
        })
        url = util.formatUrl(url)
        var promise = request.post(url, {
          handleAs: "json"
        })
        promise.then(
          lang.hitch(this, function(items) {
            var descNode = domConstruct.create(
              "div",
              {
                className: "muiCateSecItemDesc"
              },
              node
            )

            var len = items.length
            var index = 0
            for (var i = len - 1; i >= 0; i--) {
              if (index >= this.max) {
                break
              }
              domConstruct.create(
                "div",
                {innerHTML: items[i].label},
                descNode,
                "first"
              )
              index++
            }
          })
        )
      }
    }
  )
  return selection
})
