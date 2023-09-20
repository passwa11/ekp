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
	
	return  declare("kms.lservice.myBookmarkAndNoteAndEvaluationList.layoutListNodeItem",[_WidgetBase], {
		
		imgUrl: "",
		
		text: "",
		
		count: "",
		
		unit: "",
		
		type: "",
		
		typeUrl: {
			"bookmark": util.formatUrl("/sys/bookmark/mobile/#path=0",true),
			"note": util.formatUrl("/kms/common/mobile/myNote/index.jsp",true),
			"evaluation": util.formatUrl("/sys/evaluation/mobile/myEvaluation/index.jsp",true),
		},
			
		buildRendering : function() {
			this.inherited(arguments);	
			domStyle.set(this.domNode,{
				"display": "flex",
				"flex": 1,
				"height": "4.2rem",
				"background-color": "#fff"				
			});
			this.buildNode();
		},		
		
		
		buildNode: function(){
			var self = this;
			on(this.domNode,"click",function(e){
				self.onClick(e);
			});
			
			//左侧
			var leftNode = domConstruct.create("div",{
				style: "display: flex;align-items: center;",
			},this.domNode);
				
			//左侧图标
			domConstruct.create("img",{
				style: "width:2.1rem;height:2.1rem;",
				src: this.imgUrl,
			},leftNode);
			//左侧文字
			domConstruct.create("div",{
				style: "margin-left:1rem;font-size:1.2rem;line-height:1.2rem;",
				innerHTML: this.text,
			},leftNode);

			//右侧
			var rightNode = domConstruct.create("div",{	
				style: "display:flex;flex: 1;justify-content: flex-end;align-items: center;",
			},this.domNode);

			//右侧图标
			var arrowRightUrl = util.formatUrl("/kms/lservice/mobile/style/img/arrow-right.svg",true);	
			domConstruct.create("img",{
				style: "margin-left:0.5rem;width:2rrem;height:2rem;",
				src: arrowRightUrl,
			},rightNode);	
		},
		
		onClick: function(e){			
			var url = this.typeUrl[this.type];
			if(url){
				adapter.open(url);
			}
		},
		
		
	});
});