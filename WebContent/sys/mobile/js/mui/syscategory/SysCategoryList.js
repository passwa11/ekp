define([
  "dojo/_base/declare",
  "dojo/_base/lang",
  "mui/category/CategoryList",
  "mui/category/CategoryAllSelectMixin",
  "mui/util"
], function(declare, lang, CategoryList, CategoryAllSelectMixin,util) {
  return declare(
    "mui.syscategory.SysCategoryList",
    [CategoryList, CategoryAllSelectMixin],
    {
      modelName: null,

      //有权限查看的类别id
      authCateIds: null,

      buildRendering: function() {
        this.inherited(arguments)
        if (this.getParent().getParent()) {
          this.authCateIds = this.getParent().getParent().authCateIds
        }
      },

      //数据请求URL
      dataUrl:
        "/sys/category/mobile/sysCategory.do?method=cateList&fdTempKey=!{fdTempKey}&categoryId=!{parentId}&canSelect=!{canSelect}&getTemplate=!{getTemplate}&modelName=!{modelName}&authType=!{authType}&extendPara=key:!{key}",
        
      doLoad: function(handle, append) {
    	  if (this._dataUrl && this._dataUrl != "undefined") {
          	this.url = util.urlResolver(util.formatUrl(this._dataUrl), this)
          }
    	  this.inherited(arguments)
      },

      buildQuery: function() {
        var params = this.inherited(arguments)
        return lang.mixin(params, {
          authCateIds: this.authCateIds
        })
      }
    }
  )
})
