define(
		[ "dojo/_base/declare", "dojo/topic", "dojo/query", "dojo/dom-style",
				"dojo/_base/array", "mui/util", "dojox/mobile/viewRegistry",
				"dijit/registry", "dojox/mobile/_css3" ],
		function(declare, topic, query, domStyle, array, util, viewRegistry,
				registry, css3) {
			var claz = declare(
					"sys.lbpmservice.mobile.lbpm_audit_note.lbpm_process_status.js.ProcessStatusScrollableViewMixin",
					null,
					{
						// 逻辑代码
						startup : function() {
							this.inherited(arguments);
							this.subscribe(
											"/sys/lbpmservice/mobile/lbpm_audit_note/lbpm_process_status/toTop",
											'handleToTopTopic');
						},
						handleToTopTopic : function() {
							// 刷新列表后，置顶列表
							var div = query(".mblScrollableViewContainer",
									this.domNode)[0];
							domStyle.set(div, css3.name('transform'),
									'translate3d(0, 0, 0)');
						}
					});
			return claz;
		});