define(["dojo/_base/declare","mui/syscategory/SysCategoryMixin"], function(declare,SysCategoryMixin) {
  window.SYS_CATEGORY_TYPE_CATEGORY = 0 //"CATEGORY" 类别

  window.SYS_CATEGORY_TYPE_TEMPLATE = 1 //"TEMPLATE" 模板

  var SysOnlyCategoryMixin = declare("km.review.mobile.resource.js.view.SysOnlyCategoryMixin", [SysCategoryMixin], {
    
	//数据请求URL
	_dataUrl:
        "/sys/category/mobile/sysCategory.do?method=cateList2&isLoadChildren=1&fdTempKey=!{fdTempKey}&categoryId=!{parentId}&canSelect=!{canSelect}&getTemplate=!{getTemplate}&modelName=!{modelName}&authType=!{authType}&extendPara=key:!{key}",
	
  })
  return SysOnlyCategoryMixin
})
