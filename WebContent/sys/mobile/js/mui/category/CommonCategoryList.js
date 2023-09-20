define([
  "dojo/_base/declare",
  "dojo/dom-construct",
  "mui/util",
  "mui/list/JsonStoreList",
  "mui/category/CategoryAllSelectMixin"
], function(
  declare,
  domConstruct,
  util,
  JsonStoreList,
  CategoryAllSelectMixin
) {
  return declare(
    "mui.category.CommonCategoryList",
    [JsonStoreList, CategoryAllSelectMixin],
    {
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

      buildRendering: function() {
        this.url = util.urlResolver(this.dataUrl, this)
        this.inherited(arguments)
        this.domNode.className = this.baseClass
        this.subscribe("/mui/category/selChanged", "_setCurSel")
        this.buildLoading()
      },

      _setCurSel: function(srcObj, evt) {
        if (srcObj.key == this.key) {
          if (evt) {
            this.curIds = evt.curIds
            this.curNames = evt.curNames
          }
        }
      },

      startup: function() {
        this.inherited(arguments)
      },

      buildLoading: function() {
        if (this.tmpLoading == null) {
          domConstruct.empty(this.domNode)
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
      }
    }
  )
})
