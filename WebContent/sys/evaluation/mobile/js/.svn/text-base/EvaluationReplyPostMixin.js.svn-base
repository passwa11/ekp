define(
		[ "dojo/_base/declare", "mui/form/editor/EditorUtil", "mui/util",
				"dojo/_base/json", "dojo/topic", "dijit/registry",
				"dojo/_base/lang" ],
		function(declare, EditorUtil, util, json, topic, registry, lang) {
			var item = declare(
					"sys.evaluation.EvaluationReplyPostMixin",
					null,
					{

						replyPostUrl : '/sys/evaluation/sys_evaluation_main/sysEvaluationReply.do?method=save',

						// 回复事件
						_onReplyClick : function(datas) {
							var self = this, parent = self.getParent();
							var forumValidates = [];
							var data = {
								plugin : [ 'face',"dnyling","lingling"],
								limitNum : 1000
							};
							lang.mixin(data, datas);
							EditorUtil
									.popup(
											self.replyPostUrl,
											data,
											function(data) {
												if (data.status == 200) {
													topic.publish(
															'/mui/list/toTop',
															this, {
																time : 0
															});
													this
															.defer(
																	function() {
																		topic
																				.publish(
																						"/mui/list/onPull",
																						registry
																								.byId('eval_scollView'));
																	}, 100);
												}
											});
						}

					});
			return item;
		});