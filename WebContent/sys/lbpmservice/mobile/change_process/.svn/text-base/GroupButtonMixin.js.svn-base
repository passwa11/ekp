define([
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dojo/query",
    "dojo/_base/array",
	"dojo/dom-class",
	"dojo/dom-construct",
	"dojo/dom-style",
	"mui/form/Address"
	], function(declare, lang, query, array, domClass, domConstruct, domStyle, Address) {
	
	return declare("sys.lbpmservice.mobile.GroupButtonMixin", null, {
		
		postCreate: function() {
			this.text = this.text || '地址簿';
			this.inherited(arguments);
		},
		
		startup : function(){
			this.inherited(arguments);
			this.disconnect(this.domNodeOnClickEvent);
			this.subscribe("/mui/form/valueChanged", "_addressValueChange");
		},

		/**
		 * Address控件内容变更
		 * 避免用户清除Address控件组织架构后重新点击选择组织架构，此时Address控件未被更新的情况
		 * 当前情况只针对于使用双（多）Address控件，一个展示，一个隐藏
		 * @param srcObj
		 * @param ctx
		 * @private
		 */
		_addressValueChange: function (srcObj, ctx){
			if (srcObj &&
				srcObj === this &&
				srcObj instanceof Address &&
				!srcObj._overTime &&
				(srcObj.handlerIds != null || srcObj.optHandlerIds != null)
			) {
				if(srcObj.getParent &&
					srcObj.getParent() != null &&
					srcObj.getParent().childrenMap != null){
					var childrenMap = srcObj.getParent().childrenMap;
					for(var key in childrenMap){
						if(key !== srcObj.id){
							childrenMap[key].curIds = srcObj.curIds;
							childrenMap[key].curNames = srcObj.curNames;
						}
					}
				}
			}
		}
		
	});
});