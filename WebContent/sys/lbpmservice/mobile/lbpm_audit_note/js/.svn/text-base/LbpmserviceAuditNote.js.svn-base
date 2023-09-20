define([ "dojo/_base/declare", "dijit/_WidgetBase", "mui/util",
		"dijit/_Contained", "dijit/_Container", "dojo/html",
		"dojo/dom-construct", "dojo/query", "dojo/request"], function(declare,
		widgetBase, util, Contained, Container, html, domConstruct, query,
		request) {

	return declare('sys.lbpmservice.audit.note', [ widgetBase, Contained,
			Container ], {

		url : '/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote.do?method=listNote4Mobile',
		
		fdModelId : '',
		
		fdModelName : '',
		
		formBeanName : '',
		
		docStatus:'30',
		
		lazy : false,

		rscriptType : /^$|\/(?:java|ecma)script/i,

		buildRendering : function() {
			this.domNode = this.containerNode = this.srcNodeRef;
			this.inherited(arguments);
			this.loading = domConstruct.create('span',{innerHTML:'<i class="mui mui-loading mui-spin"></i>记录加载中..'},this.domNode);
		},

		getText : function(callBack) {
			var self = this;
			var formData = {};
			formData['fdModelId'] = this.fdModelId;
			formData['fdModelName'] = this.fdModelName;
			formData['formBeanName'] = this.formBeanName;
			formData['docStatus'] = this.docStatus;
			request.post(util.formatUrl(this.url), {
				data : formData
			}).response.then(function(response) {
				if (response.status == 200) {
					callBack.call(self, response.data);
				}else{
					self.loading.innerHTML = "流程记录加载失败";
				}
			});
		},

		reload : function() {
			this.doLoad();
		},

		doLoad : function() {
			if (this.loaded)
				return;
			var self = this;
			this.getText(function(text) {
				var dhs = new html._ContentSetter({
					parseContent : true,
					onEnd : function() {
						var scripts = query('script', this.node);
						scripts.forEach(function(node, index) {
							if (self.rscriptType.test(node.type || "")) {
								if (node.src) {
	
								} else {
									var __text = node.text || node.textContent || node.innerHTML || "";
									__text = __text.replace(new RegExp('\u2028',"gm"),'');
									window["eval"].call(window,__text);
								}
							}
						});
						this.inherited("onEnd", arguments);
					}
				});
				self.container = domConstruct.create('div', {
					className : 'muiLbpmserviceAuditContainer'
				}, self.domNode, 'last');
				dhs.node = self.container;
				dhs.set(text);
				dhs.parseDeferred.then(function(results) {
					domConstruct.destroy(self.loading);
				});
				dhs.tearDown();
				self.loaded = true;
			});
		},

		startup : function() {
			this.inherited(arguments);
			if (!this.lazy)
				this.doLoad();
			
		}
	});
});