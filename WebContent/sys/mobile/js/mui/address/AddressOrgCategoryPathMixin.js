/**
 * 组织架构面包屑Mixin
 */
define([ 
	"dojo/_base/declare",
	"dojo/_base/lang",
	"dojo/topic",
	"dojo/request",	
	"dojo/dom-style",
	"mui/util"
	],function(declare, lang, topic, request, domStyle, util) {
	
	return declare("mui.address.AddressOrgCategroyPathMixin", null, {
		
		_initPath: function(){
			var self = this
			if (this.domNode.parentNode) {
				var h
	            if(this.height === "inherit") {
	            	if(this.domNode.parentNode) {
	            		h = this.domNode.parentNode.offsetHeight + "px"
	            	}
	            }else if(this.height) {
	            	h = this.height
	            }
				domStyle.set(this.domNode, {height: h, "line-height": h})
			}
			this._fetchParentId(function(parentId){
				self._chgHeaderInfo(self, {fdId: parentId})
		        topic.publish("/mui/category/changed", self, {fdId: parentId})
			}, this.parentId)
		},
		
		_fetchParentId: function(callback, parentId){
			if(this.isExt != '' && this.isExt != undefined && this.isExt == 'true'){// 生态组织
				callback && callback('')
			} else if(this.isEco != '' && this.isEco != undefined && this.isEco == 'true'){// 生态组织
				callback && callback('')
			} else if(parentId) {// 生态组织
				callback && callback(parentId)
			} else {
				var url = util.formatUrl('/sys/organization/mobile/address.do?method=parentId');
				var promise = request.post(url, { handleAs: "json" });
				promise.then(lang.hitch(this,function(result){
					if(result && result.parentId){
						callback && callback(result.parentId)
					}else{
						callback && callback('')
					}
				}),function(){
					callback && callback('')
				});
			}
		}
	});

})