define([ "dojo/_base/declare", "dojo/dom-construct",
		 "dojox/mobile/_ItemBase", "mui/util",
		 "mui/i18n/i18n!sys-evaluation:*",
		 "dojo/html"], function(
		declare, domConstruct, ItemBase, 
		util, msg, html) {
	var item = declare("sys.evaluation.EvaluationItemMixin", [ ItemBase ], {
		
		tag : "li",
		
		fdEvaluationIntervalTime : "",
		
		fdId : "",
		
		fdEvaluationContent : "",

		buildRendering : function() {
			this._templated = !!this.templateString;
			if (!this._templated) {
				this.domNode = this.containerNode = this.srcNodeRef
						|| domConstruct.create(this.tag, {
							className : 'muiEvaluationAdditionItem'
						});
			}
			this.inherited(arguments);
			if (!this._templated)
				this.buildInternalRender();
		},

		buildInternalRender : function() {
			this.contentNode = domConstruct.create('div', {
				className : 'muiEvaluationAdditionContent'
			}, this.domNode, 'last');
			
			if(this.fdEvaluationIntervalTime) {
				this.timeNode = domConstruct.create("div", {
					className : "muiEvaluationCreated",
					innerHTML : msg["sysEvaluationMain.addition"] + "&nbsp" +
								"<i class='mui mui-time'></i>" +
								this.fdEvaluationIntervalTime
				}, this.contentNode);
			}
			
			if (this.fdEvaluationContent) {
				this.summaryNode = domConstruct.create("p", {
					className : "muiEvaluationSummary",
					innerHTML : this.fdEvaluationContent.replace(/&lt;br&gt;/g, '<br>').replace(/&amp;nbsp;/g, ' ')
				}, this.contentNode);
			}
			
			//附件节点
			this.attachmentDiv = domConstruct.create("div", {className:'muiPostAttchment'}, this.contentNode);
			
			var self = this;
			
			var url = "/sys/attachment/mobile/import/view.jsp?fdKey=eval_" + this.fdId + "&fdModelName=com.landray.kmss.sys.evaluation.model.SysEvaluationMain&fdModelId=" + this.fdId;
			
			require(["dojo/text!" + util.formatUrl(url)], function(tmplStr){
				domConstruct.empty(self.attachmentDiv);
				var dhs = new html._ContentSetter({
					node:self.attachmentDiv,
					parseContent : true,
					cleanContent : true
				});
				dhs.set(tmplStr);
				dhs.tearDown();
			});
			
			this.inherited(arguments);
		},

		startup : function() {
			if (this._started) {
				return;
			}
			this.inherited(arguments);
		}
	});
	return item;
});