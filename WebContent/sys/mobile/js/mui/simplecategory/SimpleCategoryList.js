define([
  "dojo/_base/declare",
  "mui/category/CategoryList",
  "dojo/_base/lang",
  "dojo/_base/json",
  "mui/category/CategoryAllSelectMixin"
], function(declare, CategoryList, lang, json, CategoryAllSelectMixin) {
  return declare(
    "mui.simplecategory.SimpleCategoryList",
    [CategoryList, CategoryAllSelectMixin],
    {
      modelName: null,

      authCateIds: null,

      ___urlParam: "",

      //数据请求URL
      dataUrl:
        "/sys/category/mobile/sysSimpleCategory.do?method=cateList&categoryId=!{parentId}&getTemplate=!{selType}&modelName=!{modelName}&authType=!{authType}&extProps=!{___urlParam}",

      buildRendering: function() {
        this.inherited(arguments)

        if (this.getParent().getParent()) {
          this.authCateIds = this.getParent().getParent().authCateIds
        }
      },

      buildQuery: function() {
        var p = null
        if (this.___urlParam) {
          try {
            p = json.fromJson(this.___urlParam)
          } catch (e) {}
        }
        var params = this.inherited(arguments)
        return lang.mixin(
          params,
          {
            authCateIds: this.authCateIds
          },
          p
        )
      }
    }
  )
})
