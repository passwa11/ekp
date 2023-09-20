define(	["dojo/_base/declare", "dojo/_base/lang", "dojo/request", "mui/util",
				"dojo/dom-class", "mui/dialog/Tip", 
				'dojo/dom-attr', "dojo/dom-construct"
				,'dojo/topic','mui/i18n/i18n!sys-zone:sysZonePerson.4m'], function(declare, lang,
				req, util, domClass, Tip, domAttr, domConstruct, topic,msg) {

			return declare("sys.zone.mobile.js._FollowButtonMixin", null, {
				
				followStatusUrl:"/sys/fans/sys_fans_main/sysFansMain.do?method=loadRlation",
				
				addCareUrl : "/sys/fans/sys_fans_main/sysFansMain.do?method=addFollow",

				/*******关注需要的字段****/ 
				userId : "",
				
				attentModelName : "",
				
				fansModelName : "",
				
				isFollowPerson : "",
				/*******关注需要的字段****/
				
				/*
				 * 是否延迟加载关注状态，这里是列表关注按钮和查看关注按钮都用同一个，列表的关注状态是不直接初始化，而是统一等列表人员加载完再初始化以减少请求
				 * 查看页面则直接加载，用此属性区分，默认是列表形式
				 */
				isDelayStatus : true,

				
				startup : function() {
					if (this._started) return;
					this.inherited(arguments);
					
					if(this.isDelayStatus){
						// 如果需要延时构建关注按钮组件，则订阅事件监听等待外部通知后再进行构建关注组件DOM （适用于列表展现:基于性能考虑，一般等到列表基础信息渲染完之后再来构建关注组件DOM）
						this.subscribe("/sys/zone/followstatus", "_followStatusChange"); 
					}else{
						// 如果不需要延时构建关注按钮，则根据查看的人员ID，去查询对方与当前登录人的关注状态后直接构建关注组件DOM
						req(util.formatUrl(this.followStatusUrl), {
							handleAs : "json",
							method : 'post',
							data : {
								"userId" : this.userId,
								"attentModelName" : this.attentModelName,
								"fansModelName" : this.fansModelName,
								"isFollowPerson" : this.isFollowPerson
							}
						}).then(lang.hitch(this, function(data) {
							this._toggleLabel(data);
						}));
					}

				},
				
				_followStatusChange : function(relationInfos) {
					if(!relationInfos) {
						return;
					}
					var _tmpdata = {
							"attentModelName" : this.attentModelName,
							"fansModelName" : this.fansModelName,
							"isFollowPerson" : this.isFollowPerson	
					};
					/*
					 * 这里evt[this.userId]的取值只有1和2，与pc端的列表一样，1表示已经关注，2表示互相关注
					 * 没有值表示还没关注列表的，这里单方面被关注没必要知道
					 */
					if(!relationInfos[this.userId]) {
						//没有值的情况
						_tmpdata.relation = "0";
					} else {
						if(relationInfos[this.userId] == 2) {
							_tmpdata.relation = "3";
						} else if(relationInfos[this.userId] == 1) {
							_tmpdata.relation = "1";
						}
					}
					this._toggleLabel(_tmpdata);
				},
				
				followAction : function() {
					
					this.set('locked', true);
					var type = domAttr.get(this.domNode, "data-action-type");
					req(util.formatUrl(this.addCareUrl), {
						handleAs : "json",
						method : 'post',
						data : {
							"fdPersonId" : this.userId,
							"attentModelName" : this.attentModelName,
							"fansModelName" : this.fansModelName,
							"isFollowPerson" : this.isFollowPerson,
							"isFollowed" : type
						}
					}).then(lang.hitch(this, function(data) {
						if(data.result == "success") {
							topic.publish("/sys/zone/followAction", this, data);
							this._toggleLabel(data, true);
						} else {
							Tip.fail({"text" : "操作失败"});
						}
					}), function(error) {
						Tip.fail({"text" : "操作失败"});
					});
				},
				
				
				onClick: function() {
					if(this.locked)
						return;
					if(this.contactInfo) {
						topic.publish("/sys/zone/list/contact", this, {
									contactInfo : this.contactInfo,
									currentIcon : this.currentIcon,
									currentText : this.currentText,
									currentActionType: domAttr.get(this.domNode,"data-action-type"),
									followAction : this.followAction
						});
					} else {
						this.followAction();
					}
				},
				
				_toggleLabel : function(data, isTip) {
					
					var tipText = ""
					if(data.relation == "2" || data.relation == "0") { /* relation:2 表示对方关注了我 、relation:0 表示双方都未关注对方  */
						domAttr.set(this.domNode, "data-action-type", "unfollowed");
						tipText = msg['sysZonePerson.4m.cancelSuccess'];
					}else if(data.relation=="3" || data.relation == "1") { /* relation:3 表示双方相互关注了对方 、relation:1 表示我关注了对方  */
						domAttr.set(this.domNode, "data-action-type", "followed");
						tipText = msg['sysZonePerson.4m.followSuccess'];
					}
					// 弹出提示Tip层告知对方操作成功
					if(isTip)
						Tip.tip({
							icon : 'mui mui-success',
							time: 1000,
							text : tipText
						});
                    data["userId"] = this.userId;
					// 通知按钮视图构建mixin去绘制按钮DOM
					topic.publish("/sys/zone/followStatusChange", this, data);

					this.set("locked", false);
				}

			});
			
			
});
			