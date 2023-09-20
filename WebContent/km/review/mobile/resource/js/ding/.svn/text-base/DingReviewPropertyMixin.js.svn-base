/**
 * 增加标题栏
 */
define([
	"dojo/_base/declare",
	"dojo/_base/lang",
	"mui/util",
	"dojo/dom-construct",
	"dojo/dom-class",
	"dojo/query",
	"dojo/touch",
	"dojo/dom-attr",
   	"mui/i18n/i18n!km-review:mui.kmReviewMain.status",
], function(declare,lang,util,domConstruct,domClass,query,touch,domAttr,msg1) {
  return declare("km.review.DingReviewPropertyMixin", null, {
    
	  showFilter: function() {
		  var dialogDiv = query('.filterlayer_content',this.dialogDiv);
		  var toggleNode = domConstruct.create("div",{className:"mui-review-head"},dialogDiv[0],'first');
		  
		  var iconNode = domConstruct.create("i",{className:"mui-icon-toggle-down"},toggleNode);
		  domConstruct.create("span",{className:"",innerHTML:'筛选'},toggleNode);
		  this.connect(iconNode, touch.press, 'hide');
		  this.inherited(arguments);
		  
		  setTimeout(function() {
				var datetimeInputs = query('.muiPropertyDatetime .muiSelInput',dialogDiv[0]);
				if(datetimeInputs && datetimeInputs.length>0){
					for(var i=0;i<datetimeInputs.length;i++){
						domAttr.set(datetimeInputs[i], "placeholder", '请输入时间段');
					}
				}
			}, 60);
	  }
	  
  })
})
