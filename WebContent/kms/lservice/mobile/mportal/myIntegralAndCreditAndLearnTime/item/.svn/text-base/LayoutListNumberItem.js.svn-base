define([
	"dojo/_base/declare",
	"dojo/dom-construct",
	"dojo/dom-class",
	"dojo/dom-style",
	"dojo/_base/lang",
	"dijit/_WidgetBase",
	"dojo/_base/array",
	"dojo/request",
	"dojo/on",
	"mui/util",
	"mui/device/adapter",
	], function(declare, domConstruct, domClass, domStyle, lang, _WidgetBase, array, request, on, util, adapter) {
	
	return  declare("kms.lservice.myIntegralAndCreditAndLearnTime.layoutListNodeItem",[_WidgetBase], {
		
		imgUrl: "",
		
		text: "",
		
		count: "",
		
		type: "",
		
		baseClass: "kmsLserviceNumberNode_item",
	
			
		buildRendering : function() {
			this.inherited(arguments);	
			domStyle.set(this.domNode,{				
				"width": (1/3 * 100) + "%",			
			});
			this.buildNode();
		},		
		
		
		buildNode: function(){

			var self = this;
			on(this.domNode,"click",function(e){
				self.onClick(e);
			});
			
			var count = this.count;
			var countStyle = "font-size:2.2rem;line-height:2.2rem;margin-bottom:0.5rem;"
				
			if(this.count > 999){				
				countStyle = "font-size:1.8rem;line-height:1.8rem;margin-bottom:0.5rem;"
			}		
			
			if(this.count > 9999) count = "9999<span style='color: #979DB1;'>+</span>";			

			//左侧图标
			var leftNode = domConstruct.create("img",{
				style: "width:4.2rem;height:4.2rem;",
				src: this.imgUrl,
			},this.domNode);
			
			//右侧文字数量
			var rightNode = domConstruct.create("div",{	
				className: "kmsLserviceNumberNode_item_right_countContainer",
				//style: "display:flex;flex-direction: column;align-items: center;margin-left:0.5rem;",
			},this.domNode);
			//右侧数量
			domConstruct.create("div",{	
				//style: countStyle,
				className: "kmsLserviceNumberNode_item_right_count",
				innerHTML: count,
			},rightNode);
			//右侧文字
			domConstruct.create("div",{	
				style: "font-size:1.2rem;line-height:1.2rem;",
				innerHTML: this.text,
			},rightNode);
			
		},
		
		onClick: function(e){
			if(this.url){
				adapter.open(this.url);
			}
		},		
		
	});
});