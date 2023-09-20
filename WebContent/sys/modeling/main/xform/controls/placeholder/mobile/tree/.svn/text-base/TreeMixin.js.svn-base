define(["dojo/_base/declare","mui/syscategory/SysCategoryMixin"], function(declare,SysCategoryMixin) {
  window.SYS_CATEGORY_TYPE_CATEGORY = 0 //"CATEGORY" 类别

  window.SYS_CATEGORY_TYPE_TEMPLATE = 1 //"TEMPLATE" 模板

  return declare("sys.modeling.main.xform.controls.placeholder.mobile.tree.TreeMixin", [SysCategoryMixin], {
	  
	  isMul: false,
	  
	  jsURL:"/sys/modeling/main/xform/controls/placeholder/mobile/tree/TreeSgl.js!extendPara=key:!{key}&s_time=" + new Date().getTime() + "&dataUrl=" + (this.dataUrl || "") + "&detailUrl=" + this.detailUrl + "&titleNode=" + this.titleNode + "&modelId="+this.modelId,
		 
	  _setIsMulAttr: function(mul) {
		  this._set("isMul", mul)
		  if (this.isMul) {
			  this.jsURL =
				  "/sys/modeling/main/xform/controls/placeholder/mobile/tree/TreeMul.js!extendPara=key:!{key}&s_time=" + new Date().getTime() + "&dataUrl=" + (this.dataUrl || "") + "&detailUrl=" + this.detailUrl+ "&titleNode=" + this.titleNode+ "&modelId="+this.modelId;
		  }else{
			  this.jsURL =
				  "/sys/modeling/main/xform/controls/placeholder/mobile/tree/TreeSgl.js!extendPara=key:!{key}&s_time=" + new Date().getTime() + "&dataUrl=" + (this.dataUrl || "") + "&detailUrl=" + this.detailUrl+ "&titleNode=" + this.titleNode+ "&modelId="+this.modelId;
		  }
	  }
  	})
})
