define(
		[		"dojo/_base/declare",
				"dojo/dom-construct",
				"dojo/dom-style",
				"dijit/_WidgetBase",
				"mui/util",
				"dojo/query",
				"dojo/_base/lang",
				"mui/iconUtils",
				dojoConfig.baseUrl
						+ "sys/lbpmservice/mobile/lbpm_audit_note/js/_LbpmserviceAuditLabelItemToggleMixin.js" ],
		function(declare, domConstruct, domStyle, widgetBase, util, query,
				lang, iconUtil, toggleMixin) {

			return declare(
					'sys.lbpmservice.audit.note.LbpmserviceDingAuditLabelItem',
					[ widgetBase, toggleMixin ],{

						baseClass : 'muiDingAuditLabelItemTitle',
						fdExecutionId : '',
						//处理人
						fdHandlerName : '',
						
						//处理时间
						fdCreateTime : '',

						store : '',
						
						nodeFlag : 'normal',//组件自定义节点类型，开始start  流程中节点normal  结束end
						
						_setStoreAttr : function(store) {
							for ( var key in store) {
								if (typeof (this[key]) != 'undefined')
									this.set(key, store[key]);
							}
						},
						
						startup : function() {
							this.inherited(arguments);
						},

						buildRendering : function() {
							this.icon = null;
							var className = this.store.status ? this.store.status : "";
							this.domNode = this.containerNode = this.srcNodeRef;
							this.dotNode = domConstruct.create('div',{
												className : 'muiDingAuditLabelItemDot ' + className
											}, this.domNode);
							this.buildHandlerInfo();
							var nodeName = "";
							if (!this.store.hasOuterBorder) {
								nodeName = this.store.fdFactNodeName;
							}
							var titleInnerHtml = nodeName;
							if (!this.store.hasOuterBorder) {
								titleInnerHtml += "(" + this.store.fdHandlerName + ")";
							} else {
								titleInnerHtml += this.store.fdHandlerName;
							}
							this.titleNode = domConstruct.create('span',{
								className : 'muiDingAuditLabelTitle',
								innerHTML : titleInnerHtml
							}, this.domNode);
							if (this.store.fdCreateTime) {
								domConstruct.create('span',{
									className : 'muiDingAuditNoteCreateTime',
									innerHTML : this.store.fdCreateTime
								}, this.domNode);
							}
							this.inherited(arguments);
						},
						
						buildHandlerInfo : function(){
							var auditNote = this.store;
							var historyHandlers = auditNote.historyHandlers;
							//处理人操作类型
							var handlerImg = "";
							if (auditNote.fdDingImg) {
								handlerImg = "<span class='auditDingNoteHandlerIcon'><img width='20px' height='20px' src='" + auditNote.fdDingImg + "' /></span>";
							} else {
								if (auditNote.fdHandlerImage) {
									handlerImg = iconUtil.createDingIcon(auditNote._fdHandlerName || auditNote.fdHandlerName);
								} else {
									handlerImg = '<span class="muiDingNoteSystem"></span>';
								}
							}
							domConstruct.place(handlerImg, this.dotNode, 'last');
						},

					});
		});