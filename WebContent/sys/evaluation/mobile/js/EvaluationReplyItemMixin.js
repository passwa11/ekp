define(
		[ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
				"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
				"mui/util", "mui/form/editor/EditorUtil", "dojo/topic",
				"dijit/registry",
				"sys/evaluation/mobile/js/EvaluationReplyPostMixin",
				"sys/evaluation/mobile/js/_EvaluationReplyLinkItem","mui/form/editor/plugins/lingling/SysFaceConfig","mui/i18n/i18n!sys-evaluation" ],
		function(declare, domConstruct, domClass, domStyle, domAttr, ItemBase,
				util, EditorUtil, topic, registry, EvaluationReplyPostMixin,
				_EvaluationReplyLinkItem,faceCfg,Msg) {
			var item = declare(
					"sys.evaluation.EvaluationReplyItemMixin",
					[ ItemBase, EvaluationReplyPostMixin,
							_EvaluationReplyLinkItem ],
					{

						"class" : "muiEvalReplyLi",

						tabIndex : "",
						// 回复内容
						replyContent : "",
						// 回复id
						replyId : "",
						// 回复时间
						replyTime : "",
						// 回复者id
						replyerId : "",
						// 回复者名字
						replyerName : "",
						// 回复回复者名字
						parentReplyerName : "",
						// 回复回复id
						parentReplyerId : "",
						// 当前用户id
						currentUserId : "",
						buildRendering : function() {
							this.inherited(arguments);
							this.contentNode = domConstruct.create('div', {
								className : "muiEvalReplyContent"
							}, this.domNode);

							var parentName = this.parentReplyerName ? (Msg["mui.sysEvaluation.mobile.btnReply"]
									+ "<span class='muiAuthor'>"
									+ this.parentReplyerName + "：</span>")
									: "：";
							this.replyNode = domConstruct
									.create(
											'div',
											{
												innerHTML : "<span class='muiAuthor'>"
														+ this.replyerName
														+ "</span>"
														+ parentName
														+ faceCfg.replaceFace(this.replyContent)
														+ "<div class='muiEvaluationReplyCreated'>"
														+ this.replyTime
														+ "</span>"
											}, this.contentNode);
						},

						_onClick : function(evt) {
							if (this.currentUserId == this.replyerId)
								return false;
							this.inherited(arguments);
							this.replyClick(evt);
						},

						replyClick : function(evt) {
							var self = this;
							var parent = this.getParent(), parents = parent
									.getParent();
							this._onReplyClick({
								name : 'replyContent',
								data : {
									fdEvaluationId : parent.fdId,
									mainModelId : parents.fdModelId,
									mainModelName : parents.fdModelName,
									evalMark : 1,
									replyId : self.replyId
								},
								placeholder : Msg["mui.sysEvaluation.mobile.btnReply"] + this.replyerName + "："
							});
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