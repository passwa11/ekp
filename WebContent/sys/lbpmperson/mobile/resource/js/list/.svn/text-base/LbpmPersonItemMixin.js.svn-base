define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/_base/lang",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/i18n/i18n!sys-lbpmperson:mui",
   	"mui/openProxyMixin"
	], function(declare, domConstruct,domClass , domStyle , lang,domAttr , ItemBase , util,msg,openProxyMixin) {
	var item = declare("sys.lbpmperson.mobile.list.LbpmPersonItemMixin", [ItemBase,openProxyMixin], {
		tag:"li",
		
		baseClass:"muiLbpmPersonItem",
		
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
		
		//所属模块
		module:"",
		
		buildRendering:function(){
			this.inherited(arguments);
			this.contentNode = domConstruct.create(this.tag, { className : 'muiLbpmPersonListItem' });
			this.buildInternalRender();
			domConstruct.place(this.contentNode,this.containerNode);
		},
		
		buildInternalRender : function() {
			
			// 标题
			var titleNode = domConstruct.create("div",{className:"muiLbpmPersonListTitle muiFontSizeM muiFontColorInfo", innerHTML:this.label},this.contentNode);
			
			// 内容载体DOM
			var processContainer = domConstruct.create("div",{className:"muiLbpmPersonListProcessContainer muiFontSizeS muiFontColorMuted"},this.contentNode);
			
			// 申请人
			var applyPersonNode = domConstruct.create("div",{className:"muiLbpmPersonListApplyPerson"},processContainer);
			var personHeadIconNode = domConstruct.create("div",{className:"muiLbpmPersonListPersonHeadIcon"},applyPersonNode);
			domConstruct.create('img',{src :util.formatUrl(this.icon),className : 'muiLbpmPersonListPersonHeadImg'},personHeadIconNode);
			var personInfoRow = domConstruct.create("div",{className:"muiLbpmPersonListInfoRow"},applyPersonNode);
			domConstruct.create("span",{},personInfoRow).innerText =msg['mui.lbpmperson.person.creator']+":";
			domConstruct.create("span",{},personInfoRow).innerText = this.creator;
		
			// 发起时间
			var applyDateNode = domConstruct.create("div",{className:"muiLbpmPersonListApplyDate"},processContainer);
			var dateInfoRow = domConstruct.create("div",{className:"muiLbpmPersonListInfoRow"},applyDateNode);
			domConstruct.create("span",{},dateInfoRow).innerText = msg['mui.lbpmperson.docInitiateTime']+":";
			domConstruct.create("span",{},dateInfoRow).innerText = this.created;
			
			// 流程进度
			var progressNode = domConstruct.create("div",{className:"muiLbpmPersonListProgress"},processContainer);
			var progressInfoRow = domConstruct.create("div",{className:"muiLbpmPersonListInfoRow"},progressNode);
			domConstruct.create("span",{},progressInfoRow).innerText =  msg['mui.lbpmperson.docRate']+":";
			domConstruct.create("span",{},progressInfoRow).innerText = this.summary;
			
			//所属模块
			var moduleNode = domConstruct.create("div",{className:"muiLbpmPersonListModule"},processContainer);
			var moduleInfoRow = domConstruct.create("div",{className:"muiLbpmPersonListInfoRow"},moduleNode);
			domConstruct.create("span",{},moduleInfoRow).innerText =  msg['mui.lbpmperson.flow.order.module']+":";
			domConstruct.create("span",{},moduleInfoRow).innerText = this.module;
			
			// 状态标签
			if(this.status){
			  var statusTagNode = domConstruct.create("div",{className:"muiLbpmPersonListStatusTag muiFontSizeS"},this.contentNode);
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