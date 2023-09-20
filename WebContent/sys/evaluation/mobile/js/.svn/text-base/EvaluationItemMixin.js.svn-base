define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
		"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
		"mui/rating/Rating", "mui/util",
		"sys/evaluation/mobile/js/EvaluationReplyListMixin",
		"dojo/html",
		"mui/form/editor/plugins/lingling/SysFaceConfig",
		], function(
		declare, domConstruct, domClass, domStyle, domAttr, ItemBase, Rating,
		util, EvaluationReplyListMixin, html,faceCfg) {
	var item = declare("sys.evaluation.EvaluationItemMixin", [ ItemBase,
			EvaluationReplyListMixin ], {
		tag : "li",
		// 简要信息
		summary : "",
		// 创建时间
		created : "",
		// 点评分数
		score : 0,
		// 标题
		label : "",
		// 创建者图像
		icon : "",
		tabIndex : "",

		buildRendering : function() {
			this._templated = !!this.templateString;
			if (!this._templated) {
				this.domNode = this.containerNode = this.srcNodeRef
						|| domConstruct.create(this.tag, {
							className : 'muiEvaluationItem'
						});
			}
			this.inherited(arguments);
			if (!this._templated)
				this.buildInternalRender();
		},

		buildInternalRender : function() {
			if (this.icon) {
				var imgDivNode = domConstruct.create("div", {
					className : "muiEvaluationIcon"
				}, this.containerNode);
				this.imgNode = domConstruct.create("div", {
					className : "muiEvaluationImg",
				}, imgDivNode);
				domStyle.set(this.imgNode, { 
					"background-image" : "url(" + util.formatUrl(this.icon) + ")" ,
					"background-size" : "cover"
				});
			}
			this.contentNode = domConstruct.create('div', {
				className : 'muiEvaluationContent'
			}, this.domNode, 'last');
			if (this.label) {
				this.labelNode = domConstruct.create("h2", {
					className : "muiEvaluationInfo"
				}, this.contentNode);
				domConstruct.create("span", {
					className : "muiEvaluationLabel muiAuthor",
					innerHTML : this.label
				}, this.labelNode);
				this.starNode = domConstruct.create("span", {
					className : "muiEvaluationScore"
				}, this.labelNode);
				var widget = new Rating({
					value : 5 - parseInt(this.score, 10)
				});
				this.starNode.appendChild(widget.domNode);
			}
			if (this.created) {
				this.createdNode = domConstruct.create("div", {
					className : "muiEvaluationCreated",
					innerHTML : '<i class="mui mui-time"></i>' + this.created
				}, this.contentNode);
			}
			if (this.summary) {
				this.summaryNode = domConstruct.create("p", {
					className : "muiEvaluationSummary",
					innerHTML : faceCfg.replaceFace(this.summary)
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
			
			this.additionNode = domConstruct.create("ul", {
				className : "muiEvaluationAdditionNode",
				"data-addition-node" : this.fdId
			}, this.domNode);
			
			this.inherited(arguments);
		},

		startup : function() {
			if (this._started) {
				return;
			}
			this.inherited(arguments);
		},

		_setLabelAttr : function(text) {
			if (text)
				this._set("label", text);
		}
	});
	return item;
});