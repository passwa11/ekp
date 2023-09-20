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
	var item = declare("mui.list.item.TemplateItemMixin", [ItemBase,openProxyMixin], {
		
		tag:"div",
		
		baseClass:"muiKmReviewTemplateItem",
		
		//图片预览
		iconClass:"muiReuseIcon",
		
		fdDesc:"",
		
		href: "/km/review/km_review_main/kmReviewMain.do?method=add",
		
		buildRendering:function(){
			this.inherited(arguments);
			this.contentNode = domConstruct.create(this.tag, { className : 'card' });
			if (this.baseClass) {
				domClass.add(this.domNode,this.baseClass);
			}
			this.buildInternalRender();
			domConstruct.place(this.contentNode,this.containerNode);
		},
		
		buildInternalRender : function() {
			
			var wrapNode = domConstruct.create("div",{className:""},this.contentNode);
			
			//标题
			var titleNode = domConstruct.create("p",{className:"muiFontSizeM muiFontColorInfo", innerHTML:this.label},wrapNode);
			
			//分类
			var desc = domConstruct.create("span",{innerHTML:this.docCagetory || "&nbsp;"},wrapNode);
			
			//操作
			var optNode = domConstruct.create("div",{className:"btn muiFontSizeS", innerHTML:msg["mui.kmreviewTemplate.launch"]},this.contentNode);
			
			// 绑定点击事件
			if(this.href){
				this.href += "&fdTemplateId=" + this.fdId;
				this.proxyClick(optNode, this.href, '_blank');
			}

		},
		
		startup:function(){
			this.inherited(arguments);
		},
		
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		}
	});
	return item;
});