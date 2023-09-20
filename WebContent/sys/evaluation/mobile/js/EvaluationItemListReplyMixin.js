define(
		[ "dojo/_base/declare", "mui/util", "dojo/_base/array", "dojo/request",
				"dojo/_base/json", "dojo/query", "dojo/dom-construct","mui/i18n/i18n!sys-evaluation" ],
		function(declare, util, array, request, json, query, domConstruct,Msg) {

			return declare(
					"sys.evaluation.EvaluationItemListReplyMixin",
					null,
					{

						replyUrl : '/sys/evaluation/sys_evaluation_main/sysEvaluationReply.do?method=listReplyCount',

						moreClass : 'muiEvalReplyMoreMessage',

						// 回复信息相关
						loadedHandle : function(evt) {
							this.inherited(arguments);
							this.requestReply();
						},

						// 请求回复数
						requestReply : function() {
							if(!this.ids) 
								return;
							var self = this;
							request.post(util.formatUrl(this.replyUrl), {
								data : {
									ids : this.ids
								}
							}).response.then(function(data) {
								var datas = json.fromJson(data.text);
								self.moreNode(datas);
							});
						},

						// 构建回复节点
						moreNode : function(datas) {
							for (var i = 0; i < datas.length; i++) {
								var data = datas[i];
								var node = query("#reply_" + data.value);
								domConstruct.create('div', {
									className : this.moreClass,
									innerHTML : Msg["mui.sysEvaluation.mobile.reply.count"].replace('%count%',data.text)
								}, node[0]);
							}
						}

					});
		});