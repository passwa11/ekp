define(
		[ "dojo/_base/declare", "mui/form/editor/EditorUtil",
				"dojo/dom-construct", "mui/dialog/OperaTip", "dojo/dom-class",
				"mui/util", "dojo/request", "dojo/_base/json",
				"dojo/_base/array",
				"sys/evaluation/mobile/js/EvaluationReplyItemMixin",
				"dojo/dom-style",
				"sys/evaluation/mobile/js/EvaluationReplyPostMixin",
				"mui/i18n/i18n!sys-evaluation"],
		function(declare, EditorUtil, domConstruct, OperaTip, domClass, util,
				request, json, array, EvaluationReplyItemMixin, domStyle,
				EvaluationReplyPostMixin,Msg) {
			var item = declare(
					"sys.evaluation.EvaluationReplyListMixin",
					[ EvaluationReplyPostMixin ],
					{

						replyGetUrl : "/sys/evaluation/sys_evaluation_main/sysEvaluationReply.do?method=listReplyInfo&fdEvaluationId=!{fdId}",

						startup : function() {
							this.inherited(arguments);
							this.bindMoreClick();
						},

						// 回复事件
						onReplyClick : function() {
							var self = this, parent = self.getParent();
							this._onReplyClick({
								name : 'replyContent',
								data : {
									fdEvaluationId : self.fdId,
									mainModelId : parent.fdModelId,
									mainModelName : parent.fdModelName,
									evalMark : 0
								},
								placeholder : Msg['mui.sysEvaluation.mobile.replyContent']
							});
						},

						// 绑定更多点击事件
						bindMoreClick : function() {
							this.connect(this.domNode, 'onclick', 'moreClick');
						},

						// 更多点击事件
						moreClick : function(evt) {
							var target = evt.target;
							if (target
									&& domClass.contains(target,
											"muiEvalReplyMoreMessage")) {
								if (!this.isReplyed) {
									this.isReplyed = true;
									this.requestReply();
									domStyle.set(target, {
										display : 'none'
									});
								}
							}
						},

						// 请求回复列表信息
						requestReply : function() {
							var self = this;
							request.get(util.formatUrl(util.urlResolver(
									this.replyGetUrl, {
										"fdId" : this.fdId
									})), null).response.then(function(data) {
								var datas = json.fromJson(data.text);
								var replys = datas.replyInfo;
								array.forEach(replys, function(reply) {
									domConstruct.place(
											this.createItem(reply).domNode,
											this.replyContentNode);
								}, self);
							});
						},

						// 创建回复列表
						createItem : function(data) {
							var item = new EvaluationReplyItemMixin(data);
							item.startup();
							return item;
						},

						buildInternalRender : function() {
							this.replyNode = domConstruct.create("div", {
								id : "reply_" + this.fdId,
								className : "muiEvalReplyMore"
							}, this.containerNode);

							this.replyContentNode = domConstruct.create("div",
									{
										className : "muiEvalReplyMoreContent"
									}, this.replyNode);

							// 操作按钮
							this.operationNode = domConstruct
									.create(
											'div',
											{
												className : 'muiEvalReplyOpt',
												innerHTML : '<div class="muiEvalReplyOperation"><span class="l"></span><span class="f"></span><i class="mui mui-more"></i></div>'
											}, this.labelNode);
							this.connect(this.operationNode, 'click',
									'onOperationClick');
						},

						// 操作按钮点击
						onOperationClick : function(evt) {
							if (this.operaTip && this.operaTip.isShow)
								return;
							this.operas = [];
							var self = this;
							this.operaTip = OperaTip.tip({
								refNode : this.operationNode,
								operas : [ {
									'icon' : 'mui-msg',
									'text' : Msg['mui.sysEvaluation.mobile.btnReply'],
									'func' : function() {
										self.onReplyClick();
									}
								} ]
							});
						}

					});
			return item;
		});