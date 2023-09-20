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
	
	return declare("sys.unit.UnitAllCatePathMixin", null, {
		
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
		}
		
	});

})