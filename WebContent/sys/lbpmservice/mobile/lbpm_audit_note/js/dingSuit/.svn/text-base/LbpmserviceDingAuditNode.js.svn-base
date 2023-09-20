define(
["dojo/_base/declare",
"dojo/dom-construct",
"dojo/dom-style",
"dojo/dom-class",
"dijit/_WidgetBase",
"dojo/dom-geometry",
"mui/util",
"dojo/query",
"dojo/_base/lang",
"mui/iconUtils",
dojoConfig.baseUrl
		+ "sys/lbpmservice/mobile/lbpm_audit_note/js/_LbpmserviceAuditLabelItemToggleMixin.js" ],
function(declare, domConstruct, domStyle, domClass, widgetBase, domGeometry, util, query,
		lang, iconUtil, toggleMixin) {
	/** 节点 */
	return declare(
			'sys.lbpmservice.ding.audit.note.node',
			[ widgetBase, toggleMixin ],{

				baseClass : 'muiDingAuditNodeItem',
				fdExecutionId : '',
				// 节点名称
				fdFactNodeName : '',

				store : '',
				
				expandItems:true,
				
				nodeFlag : 'normal',//组件自定义节点类型，开始start  流程中节点normal  结束end
				
				_setStoreAttr : function(store) {
					for ( var key in store) {
						if (typeof (this[key]) != 'undefined')
							this.set(key, store[key]);
					}
				},

				startup : function() {
					this.inherited(arguments);
					this.subscribe('/mui/lbpmservice/branch_toggle',
							lang.hitch(this, this.branch_toggle));
				},

				buildRendering : function() {
					this.icon = null;
					this.domNode = this.containerNode = this.srcNodeRef;
					if (this.store.pass === "30") {
						this.baseClass += " pass";
					} 
					if (this.store.pass === "31") {
						this.baseClass += " reject";
					}
					//并行,会审信息
					this.dotNode = domConstruct.create('div',{
										className : 'muiDingAuditLabelItem muiDingAuditNormal'
									}, this.domNode,"first");
					//节点状态
					var itemDotClassName = 'muiDingAuditLabelItemDot muiLbpmProcessSystem';
					if (this.store.fdProcessType == "2") {
						itemDotClassName += ' muiLbpmProcessJoint';
					} 
					if (this.store.fdProcessType == "1") {
						itemDotClassName += ' muiLbpmProcessParallel';
					}
					this.itemDot = domConstruct.create('div',{
						className : itemDotClassName,
						innerHTML : "<i></i>"
					}, this.dotNode);
					//标题
					var handlersLength = this.store.historyHandlers.length + this.store.currentHandlers.length;
					var isPass = false;
					if (this.store.currentHandlers.length == 0) {
						isPass = true;
					}
					var nodeTypeText = "";
					var nodeDesc = "";
					if (this.store.fdProcessType == "1") {
						nodeTypeText = this.store.fdFactNodeName + "(并行)";
						nodeDesc = handlersLength + "人任意1人通过审批即可";
						if (this.store.pass == "30") {
							nodeDesc += "(已通过)";
						} else if (this.store.pass == "31") {
							nodeDesc += "(已驳回)";
						} else {
							nodeDesc += "(审批中)";
						}
					} else if (this.store.fdProcessType == "2") {
						nodeTypeText = this.store.fdFactNodeName + "(会审)";
						nodeDesc = "需" + handlersLength + "人全部审批通过";
						if (this.store.pass == "30") {
							nodeDesc += "(已通过)";
						} else if (this.store.pass == "31") {
							nodeDesc += "(已驳回)";
						} else {
							nodeDesc += "(" + this.store.historyHandlers.length + "人已审批通过" + ")";
						}
					}
					this.itemHead = domConstruct.create('div',{
						className : 'muiDingAuditLabelItemHead',
						innerHTML : '<div>' + nodeTypeText + '</div>'
					}, this.dotNode);
					
					this.nodeInfo = domConstruct.create('div',{
						className : 'muiDingAuditLabelNodeInfo',
						innerHTML: nodeDesc
					}, this.dotNode);
					//描述
					this.handler = domConstruct.create('div',{
						className : 'muiDingAuditNoteHandler',
					}, this.dotNode);
					// 构建“展开”更多按钮DOM
					var auditNodeBtn = domConstruct.create("div", { className: "muiDingAuditNodeBtn"}, this.itemHead);
					var expandButton = domConstruct.create("span", { className: "muiDingAuditNodeExpandBtn"}, auditNodeBtn);
					// 构建“收起”更多按钮DOM（默认隐藏）
					var collapseButton = domConstruct.create("span", { className: "muiDingAuditNodeCollapseBtn"}, auditNodeBtn);
					//处理人头像
					this.builderHandlers();
					var self = this;
					// 绑定“展开”更多按钮点击事件
				    this.connect(expandButton, "click", function(){
			    		domClass.add(self.domNode,"expand");
			    		domClass.remove(self.domNode,"collapse");
			    		domStyle.set(expandButton,"display","none");
			    		domStyle.set(collapseButton,"display","inline");
				    });
				    // 绑定“收起”更多按钮点击事件
				    this.connect(collapseButton, "click", function(){
				    	domClass.add(self.domNode,"collapse");
				    	domClass.remove(self.domNode,"expand");
				    	domStyle.set(expandButton,"display","inline");
			    		domStyle.set(collapseButton,"display","none");
				    });
					this.inherited(arguments);
					
				},
				
				builderHandlers : function() {
					var auditNotes = this.store;
					var historyHandlers = auditNotes.historyHandlers;
					var currentHandlers = auditNotes.currentHandlers;
					if (historyHandlers) {
						for (var i = 0; i < historyHandlers.length; i++) {
							var handler = historyHandlers[i];
							this.buildHandlerInfo(handler,false);
						}
					}
					if (currentHandlers) {
						for (var i = 0; i < currentHandlers.length; i++) {
							var handler = currentHandlers[i];
							this.buildHandlerInfo(handler,true);
						}
					}
				},
				
				buildHandlerInfo : function(handler,current){
					var className = current ? "current" : "history";
					var handlerSpan = domConstruct.create('span',{
						className : 'muiDingAuditNodeHandler ' + className
					}, this.handler);
					var handlerImg = "";
					if (handler.fdDingImg) {
						handlerImg = "<img src='" + handler.fdDingImg + "' />";
					} else {
						handlerImg = iconUtil.createDingIcon(handler.fdName);
					}
					handlerImg += "<label>" + handler.fdName + "</label>";
					domConstruct.place(handlerImg, handlerSpan, 'last');
				},
			});
});