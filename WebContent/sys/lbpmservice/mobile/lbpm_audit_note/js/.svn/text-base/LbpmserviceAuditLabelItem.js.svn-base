define(
		[		"dojo/_base/declare",
				"dojo/dom-construct",
				"dojo/dom-style",
				"dijit/_WidgetBase",
				"mui/util",
				"dojo/query",
				"dojo/_base/lang",
				dojoConfig.baseUrl
						+ "sys/lbpmservice/mobile/lbpm_audit_note/js/_LbpmserviceAuditLabelItemToggleMixin.js" ],
		function(declare, domConstruct, domStyle, widgetBase, util, query,
				lang, toggleMixin) {

			return declare(
					'sys.lbpmservice.audit.note.LbpmserviceAuditLabelItem',
					[ widgetBase, toggleMixin ],{

						baseClass : 'muiLbpmserviceAuditLabelItem',
						fdExecutionId : '',
						// 节点名称
						fdFactNodeName : '',

						store : '',
						
						nodeFlag : 'normal',//组件自定义节点类型，开始start  流程中节点normal  结束end
						
						_setStoreAttr : function(store) {
							for ( var key in store) {
								if (typeof (this[key]) != 'undefined')
									this.set(key, store[key]);
							}
						},

						_setFdFactNodeNameAttr : function(fdFactNodeName) {
							this.fdFactNodeName = fdFactNodeName;
							if (!this.titleNode)
								this.titleNode = domConstruct.create('div',{
													className : 'muiLbpmserviceAuditLabelItemTitle',
													innerHTML : Com_HtmlEscape(fdFactNodeName)
												}, this.domNode);
						},

						startup : function() {
							this.inherited(arguments);
							this.subscribe('/mui/lbpmservice/branch_toggle',
									lang.hitch(this, this.branch_toggle));
						},

						buildRendering : function() {
							if('start' == this.nodeFlag){
								this.icon = "mui mui-play";	//开始图标
								this.baseClass = 'muiLbpmserviceAuditLabelItem muiLbpmserviceAuditStart';
							}else if('end' == this.nodeFlag){
								this.icon = "mui mui-stop";	//结束图标
								this.baseClass = 'muiLbpmserviceAuditLabelItem muiLbpmserviceAuditEnd';
							}else{
								this.icon = null;
								this.baseClass = 'muiLbpmserviceAuditLabelItem muiLbpmserviceAuditNormal';
							}
							this.domNode = this.containerNode = this.srcNodeRef;
							this.dotNode = domConstruct.create('div',{
												className : 'muiLbpmserviceAuditLabelItemDot',
												innerHTML : '<i class="'+(this.icon?this.icon:'')+'"></i>'
											}, this.domNode);
							this.inherited(arguments);
						},

						branch_toggle : function(obj, evt) {
							if (!evt)
								return;
							if (evt.fdExecutionId == this.fdExecutionId) {
								this[evt.methodPrex + 'Item'](evt);
							}
						},

						showItem : function(evt) {
							domStyle.set(this.domNode, {
								'height' : 'auto'
							});

							this.defer(function() {
								domStyle.set(this.domNode, {
									'display' : 'block'
								});
							}, 1);
						},

						hideItem : function() {
							domStyle.set(this.domNode, {
								'height' : 0
							});

							this.defer(function() {
								domStyle.set(this.domNode, {
									'display' : 'none'
								});
							}, 1);
						}

					});
		});