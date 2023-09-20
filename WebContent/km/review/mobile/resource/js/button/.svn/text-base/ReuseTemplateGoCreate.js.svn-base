define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/openProxyMixin",
   	"mui/i18n/i18n!km-review"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util,openProxyMixin,msg) {
	var item = declare("mui.list.item.ReuseTemplateGoCreate", [ItemBase,openProxyMixin], {
		
		tag:"div",
		
		baseClass:"",
		
		//Title
		fdName:"",
		
		fdDesc:"",
		
		//图片预览
		iconClass:"",
		
		href: "",
		
		__createUrl: '',
		
		buildRendering:function(){
			this.inherited(arguments);
			if (this.baseClass) {
				domClass.add(this.domNode,this.baseClass);
			}
			this.buildInternalRender();
		},
		
		buildInternalRender : function() {
			
			this.buildImg();
			
			var contentRightNode = domConstruct.create("div",{className:"center"},this.domNode);
			
			//标题
			var titleNode = domConstruct.create("p",{className:"muiFontSizeM", innerHTML:this.fdName},contentRightNode);
			
			//描述
			var desc = domConstruct.create("span",{className:"muiFontSizeMS", innerHTML:this.fdDesc || ""},contentRightNode);
			
			//操作
			var optNode = domConstruct.create("div",{className:"right", innerHTML:msg["mui.kmreviewTemplate.create"]},this.domNode);
			
			// 绑定点击事件
			if(this.href){
				this.href += "&product=EKP&createUrl=" + encodeURIComponent(this.__createUrl);
				this.proxyClick(optNode, this.href, '_blank');
			}
		},
		
		buildImg : function() {
			var iconWrapNode = domConstruct.create('img', {
				src : "../mobile/resource/images/reuse_bg.png"
			}, this.domNode);
		},
		
		startup:function(){
			this.inherited(arguments);
		}
	});
	return item;
});