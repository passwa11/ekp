define(	["dojo/_base/declare", "dojo/dom-class",
				"dojo/dom-style", 
				"mui/util",
				"dojo/_base/lang",
				"dojo/dom-construct",
				"mui/person/PersonDetailMixin"], function(declare, domClass,
				domStyle, util, lang,domConstruct, PersonDetailMixin) {

			return declare("sys.fans.FansButtonMixin", [ PersonDetailMixin], {

						fdId : "",
				
						url : "/sys/fans/sys_fans_main/sysFansMain.do?method=dataFollow&q.type=!{type}&fdId=!{fdId}&fansModelName=!{fansModelName}&attentModelName=!{attentModelName}&rowsize=12",
						
						baseClass : "",
						
						muiIcon : "",
						
						muiLabel : "",
						
						fdMemberNum : "",
						
						startup : function() {
							if (this._started)
								return;
							this.inherited(arguments);
						},
						
						buildRendering : function() {
							this.inherited(arguments);
							this.buildMemberNum();
							domClass.add(this.domNode , this.baseClass);
							// 监听员工黄页的关注事件
							this.subscribe("/sys/zone/followAction", "_buildFollowNum");
						},

						buildMemberNum: function() {
							this.domNode.innerHTML = "<i class='mui " + this.muiIcon+"'></i>" +
								(this.muiLabel?this.muiLabel:"")
								+ (this.fdMemberNum ? this.fdMemberNum : "0") ;
						},
						
						onClick : function() {
							if (this.url) {
								this.detailUrl = util.urlResolver(this.url, {
									"fdId" : this.fdId,
									"fansModelName" : this.fansModelName,
									"attentModelName" : this.attentModelName,
									"type":this.type
								});
								this.openDeatailView();
							}
							this.inherited(arguments);
						},

						_buildFollowNum: function(buttonObj, data) {
							// 这里针对关注后粉丝数量的变化
							if(this.type == "fans") {
								if(typeof(this.fdMemberNum) == 'string') {
									this.fdMemberNum = parseInt(this.fdMemberNum);
								}
								if(data.relation == 1) {
									// 增加关注
									this.fdMemberNum += 1;
								} else if(data.relation == 0) {
									// 取消关注
									this.fdMemberNum -= 1;
								}
								this.buildMemberNum();
							}
						}

					});
		});