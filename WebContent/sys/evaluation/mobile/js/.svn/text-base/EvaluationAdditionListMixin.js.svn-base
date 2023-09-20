define(
		[ "dojo/_base/declare", "mui/util", "dojo/_base/array", "dojo/request",
				"dojo/_base/json", "dojo/query", 
				"dojo/dom-construct","mui/i18n/i18n!sys-evaluation",
				"sys/evaluation/mobile/js/EvaluationAdditionItem"],
		function(declare, util, array, request, json, query, domConstruct, Msg,EvaluationAdditionItem) {

			return declare(
					"sys.evaluation.EvaluationAdditionListMixin",
					null,
					{

						additionUrl : '/sys/evaluation/sys_evaluation_main/sysEvaluationMain.do?method=getEvaluationByParentIds',


						loadedHandle : function(evt) {
							this.inherited(arguments);
							this.requestAddition();
						},

						// 追加评论
						requestAddition : function() {
							if(!this.idList || this.idList.length <= 0) 
								return;
							var self = this;
							request.post(util.formatUrl(this.additionUrl), {
								handleAs : "json",
								data : {
									ids : this.idList,
									fdModelName : this.fdModelName,
									fdModelId : this.fdModelName
								}
							}).response.then(function(result) {
								self.buildAddition(result.data);
							});
						},
						
						buildAddition : function(data) {
							var tid = this.id,  d;
							for(var key in data) {
								var node = query("#" + tid + " [data-addition-node='" + key + "']")[0];
								for(var i = 0 ; i < data[key].length; i++) {
									var item = this._createAdditionItem(data[key][i]);
									node.appendChild(item.domNode);
								}
							}
						},
						
						_createAdditionItem : function(data) {
							var item = new EvaluationAdditionItem(data);
							item.startup();
							return item;
						},

					});
		});