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
					'sys.lbpmservice.audit.note.LbpmserviceDingNextAuditLabelItem',
					[ widgetBase, toggleMixin ],{

						baseClass : 'muiDingAuditLabelItemTitle',
						//处理人
						handlerName : '',
						
						//处理人头像
						handlerImg : '',
						
						startup : function() {
							this.inherited(arguments);
						},

						buildRendering : function() {
							this.icon = null;
							this.domNode = this.containerNode = this.srcNodeRef;
							this.dotNode = domConstruct.create('div',{
												className : 'muiDingAuditLabelItemDot'
											}, this.domNode);
							this.buildHandlerInfo();
							this.titleNode = domConstruct.create('span',{
								className : 'muiDingAuditLabelTitle',
								innerHTML : this.handlerName
							}, this.domNode);
							this.inherited(arguments);
						},
						
						buildHandlerInfo : function(){
							//处理人操作类型
							var handlerImg = "";
							if (this.handlerImg) {
								handlerImg = "<span class='auditDingNoteHandlerIcon'><img width='20px' height='20px' src='" + this.handlerImg + "' /></span>";
							} else {
								if (this.handlerName) {
									var canApplyName = this.handlerName;
									if (this.handlerName) {
										var handlerNameArr = this.handlerName.split(";");
										canApplyName = handlerNameArr[0];
									}
									handlerImg = iconUtil.createDingIcon(canApplyName);
								} else {
									handlerImg = '<span class="muiDingNoteSystem"></span>';
								}
							}
							domConstruct.place(handlerImg, this.dotNode, 'last');
						},

					});
		});