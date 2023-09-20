define(["dojo/_base/declare"], function(declare) {
  window.SYS_CATEGORY_TYPE_CATEGORY = 0 //"CATEGORY" 类别

  window.SYS_CATEGORY_TYPE_TEMPLATE = 1 //"TEMPLATE" 模板

  var sysCategory = declare("mui.syscategory.SysCategoryMixin", null, {
    type: window.SYS_CATEGORY_TYPE_TEMPLATE,

    //模块名
    modelName: null,

    mainModelName: null,

    showCommonCate: true,
    
    confirm: !this.isMul,

    //是否取模板, 值:0 否  , 1 是
    getTemplate: 1,

    //0显示显示子机构分类,只1显示父机构分类,2只父机构分类和子机构分类
    showType: "0",
    
    //对节点的验证权限,0显示所有(00可以选中所有,01只能选中有维护权限的,02只能选中有使用权限的，03根据全局权限配置决定是否需要权限校验)
//    authType: "02",

    isMul: false,
    
    // 单个分类信息请求URL
	detailUrl: '/sys/category/mobile/sysCategory.do?method=detailList&cateId=!{currentId}&modelName=!{modelName}&fdTmepKey=!{fdTmepKey}',
    
    jsURL:
      "/sys/mobile/js/mui/syscategory/syscategory_sgl.js!modelName=!{modelName}&mainModelName=!{mainModelName}&fdTmepKey=!{fdTmepKey}&authType=!{authType}&confirm=!{confirm}&required=!{required}&showCommonCate=!{showCommonCate}&extendPara=key:!{key}&s_time=" +
      new Date().getTime(),

    _setIsMulAttr: function(mul) {
      this._set("isMul", mul)
      if (this.isMul) {
        this.jsURL =
          "/sys/mobile/js/mui/syscategory/syscategory_mul.js!modelName=!{modelName}&fdTmepKey=!{fdTmepKey}&mainModelName=!{mainModelName}&authType=!{authType}&showCommonCate=!{showCommonCate}&extendPara=key:!{key}&s_time=" +
          new Date().getTime()
      } else {
        this.jsURL =
          "/sys/mobile/js/mui/syscategory/syscategory_sgl.js!modelName=!{modelName}&fdTmepKey=!{fdTmepKey}&mainModelName=!{mainModelName}&authType=!{authType}&confirm=!{confirm}&required=!{required}&showCommonCate=!{showCommonCate}&extendPara=key:!{key}&s_time=" +
          new Date().getTime()
      }
    }
  })
  return sysCategory
})
