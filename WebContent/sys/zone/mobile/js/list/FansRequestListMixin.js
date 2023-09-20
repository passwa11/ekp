define(
		[ "dojo/_base/declare", "mui/util", "dojo/_base/array", "dojo/request",
				"dojo/_base/json", "dojo/topic"],
		function(declare, util, array, request, json, topic) {
			/**
			 * 统一查询列表所有人员的关注情况，减少请求
			 * 
			 * */
			return declare(
					"sys.zone.FansRequestListMixin",
					null,
					{

						followStatusUrl : '/sys/fans/sys_fans_main/sysFansMain.do?method=getRelationType',

						moreClass : 'muiEvalReplyMoreMessage',

						buildRendering : function() {
							this.inherited(arguments);
							this.subscribe('/mui/list/loaded', 'status');
						},

						ids : null,

						//状态信息相关
						status : function(evt) {
							if (!evt)
								return;
							var datas = evt.listDatas;
							this.buildIds(datas);
							this.requestStatus();
						},

						// 请求回复数
						requestStatus : function() {
							var self = this;
							request.post(util.formatUrl(this.followStatusUrl), {
								data : {
									personIdsStr : this.ids
								}
							}).response.then(function(data) {
								var datas = json.fromJson(data.text);
								if(datas.relation) {
									var obj = {}, relas = datas.relation;
									for(var i = 0; i < relas.length; i ++) {
										obj[relas[i][0]] = relas[i][1];
									}
									topic.publish("/sys/zone/followstatus", obj);
								}
							});
						},


						// 构建查询id
						buildIds : function(datas) {
							this.ids = '';
							array.forEach(datas, function(data, index) {
								if(data.type && data.type == 8) {
									var id = data.fdId;
									this.ids += (this.ids == '' ? id : ',' + id);
								}
							}, this);
						}
					});
		});