define(["dojo/_base/declare"], function(declare) {
  window.SIMPLE_CATEGORY_TYPE_CATEGORY = 0 //"CATEGORY" 类别

  var simpleCategory = declare("mui.simplecategory.SimpleCategoryMixin", null, {
    type: window.SIMPLE_CATEGORY_TYPE_CATEGORY,

    parentId: "",
    
    confirm: !this.isMul,

    //模块名
    modelName: null,

    //对节点的验证权限,0显示所有(00可以选中所有,01只能选中有维护权限的,02只能选中有使用权限的，03根据全局权限配置决定是否需要进行数据权限过滤)
    // authType: "02",

    isMul: false,
    
    //url参数 格式 {extProps:'param1:value1;param2:value2'}
    ___urlParam: "",
    
    // 单个分类信息请求URL
	detailUrl: '/sys/category/mobile/sysSimpleCategory.do?method=detailList&cateId=!{currentId}&modelName=!{modelName}',

    jsURL:
      "/sys/mobile/js/mui/simplecategory/simplecategory_sgl.js!modelName=!{modelName}&extendPara=key:!{key}&showFavoriteCate=!{showFavoriteCate}&authType=!{authType}&confirm=!{confirm}&required=!{required}",

    _setIsMulAttr: function(mul) {
      this._set("isMul", mul)

      if (this.isMul) {
        this.jsURL =
          "/sys/mobile/js/mui/simplecategory/simplecategory_mul.js!modelName=!{modelName}&extendPara=key:!{key}&showFavoriteCate=!{showFavoriteCate}&authType=!{authType}"
      } else {
        this.jsURL =
          "/sys/mobile/js/mui/simplecategory/simplecategory_sgl.js!modelName=!{modelName}&extendPara=key:!{key}&showFavoriteCate=!{showFavoriteCate}&authType=!{authType}&confirm=!{confirm}&required=!{required}"
      }
    },

    _set___urlParamAttr: function(___urlParam) {
      if (___urlParam) {
        ___urlParam = ___urlParam.replace(/\'/g, "\\'").replace(/\"/g, '\\"')
      }
      this._set("___urlParam", ___urlParam)
    },
    
    postCreate: function() {
      this.inherited(arguments)
      this.type = window.SIMPLE_CATEGORY_TYPE_CATEGORY
     }
  })
  return simpleCategory
})
