define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/_base/lang",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/i18n/i18n!km-review:mui.kmReviewMain.docCreatorName",
   	"mui/i18n/i18n!km-review:mui.kmReviewMain.docInitiateTime",
   	"mui/i18n/i18n!km-review:mui.kmReviewMain.docRate",
   	"mui/openProxyMixin"
	], function(declare, domConstruct,domClass , domStyle , lang,domAttr , ItemBase , util,msg1,msg2,msg3,openProxyMixin) {
	var item = declare("mui.list.item.ProcessItemMixin", [ItemBase,openProxyMixin], {
		tag:"li",
		
		baseClass:"muiKmReviewItem",
		
		//流程简要信息
		summary:"",
		
		//创建时间
		created:"",
		
		//创建者
		creator:"",
		
		//创建人图像
		icon:"",
		
		//状态
		status:"",
		
		buildRendering:function(){
			this.inherited(arguments);
			this.contentNode = domConstruct.create(this.tag, { className : 'muiKmReviewListItem' });
			this.buildInternalRender();
			domConstruct.place(this.contentNode,this.containerNode);
		},
		
		buildInternalRender : function() {
			
			// 标题
			var titleNode = domConstruct.create("div",{className:"muiKmReviewListTitle muiFontSizeM muiFontColorInfo", innerHTML:this.label},this.contentNode);
			
			// 内容载体DOM
			var processContainer = domConstruct.create("div",{className:"muiKmReviewListProcessContainer muiFontSizeS muiFontColorMuted"},this.contentNode);
			
			// 申请人
			var applyPersonNode = domConstruct.create("div",{className:"muiKmReviewListApplyPerson"},processContainer);
			var personHeadIconNode = domConstruct.create("div",{className:"muiKmReviewListPersonHeadIcon"},applyPersonNode);
			domConstruct.create('img',{src :util.formatUrl(this.icon),className : 'muiKmReviewListPersonHeadImg'},personHeadIconNode);
			var personInfoRow = domConstruct.create("div",{className:"muiKmReviewListInfoRow"},applyPersonNode);
			domConstruct.create("span",{},personInfoRow).innerText =msg1['mui.kmReviewMain.docCreatorName']+":";
			domConstruct.create("span",{},personInfoRow).innerText = this.creator;
		
			// 发起时间
			var applyDateNode = domConstruct.create("div",{className:"muiKmReviewListApplyDate"},processContainer);
			var dateInfoRow = domConstruct.create("div",{className:"muiKmReviewListInfoRow"},applyDateNode);
			domConstruct.create("span",{},dateInfoRow).innerText = msg2['mui.kmReviewMain.docInitiateTime']+":";
			domConstruct.create("span",{},dateInfoRow).innerText = this.created;
			
			// 流程进度
			var progressNode = domConstruct.create("div",{className:"muiKmReviewListProgress"},processContainer);
			var progressInfoRow = domConstruct.create("div",{className:"muiKmReviewListInfoRow"},progressNode);
			domConstruct.create("span",{},progressInfoRow).innerText =  msg3['mui.kmReviewMain.docRate']+":";
			domConstruct.create("span",{},progressInfoRow).innerText = this.summary;
			
			// 状态标签
			if(this.status){
			  var statusTagNode = domConstruct.create("div",{className:"muiKmReviewListStatusTag muiFontSizeS"},this.contentNode);
			  statusTagNode.innerHTML = this.status;
			}
			
			// 绑定点击事件
			if(this.href){
				this.proxyClick(this.contentNode, this.href, '_blank');
			}

		},
		
		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},
	
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		}
	});
	return item;
});