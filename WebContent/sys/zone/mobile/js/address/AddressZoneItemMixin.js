define(
		[ "dojo/_base/declare", "dojo/dom-class",
				"dojo/_base/lang", "dojox/mobile/Button",
				"sys/zone/mobile/js/_FollowButtonMixin",
				"sys/zone/mobile/js/_FollowButtonListViewMixin",
				"mui/category/CategoryItemMixin", "mui/util",
				"dojo/dom-construct", "dojo/dom-attr" ,"dojo/topic",'dojox/mobile/viewRegistry',
				'mui/i18n/i18n!sys-mobile:mui.address',
				'mui/i18n/i18n!sys-zone:sysZonePerson.4m',
				"sys/zone/mobile/js/AddressSessionStorage",
				"mui/history/listener"],
		function(declare, domClass, lang, button,
				_FollowButtonMixin, _FollowButtonListViewMixin, CategoryItemMixin, util, domConstruct, 
				domAttr, topic,viewRegistry,Msg, msgPerson, AddressSessionStorage, listener) {
			var item = declare(
					"sys.zone.mobile.js.address.AddressZoneItemMixin",
					[ CategoryItemMixin ],{
				
				extendContact : [],
				
				personUrl : "/sys/zone/index.do?userid=!{personId}",
				
				postCreate:function() {
					this.inherited(arguments);
				},
				
				//获取分组标题信息
				getTitle:function(){
					if( this.label=='2' ) {
						return Msg['mui.address.org']; // 组织
					}
					if(this.label=='4') {
						return Msg['mui.address.post']; // 岗位
					}
					if(this.label=='8') {
						return msgPerson['sysZonePerson.4m.address.person']; // 员工
					}
					return this.label;
				},
				
				//是否显示往下一级
				showMore : function(){
					if((this.type | window.ORG_TYPE_ORGORDEPT) ==  window.ORG_TYPE_ORGORDEPT){
						return true;
					}
					return false;
				},
				
				//是否显示选择框
				showSelect:function(){
					var pWeiget = this.getParent();
					if(pWeiget && ((pWeiget.selType & this.type) ==  this.type)){
						return true;
					}
					return false;
				},
				
				//是否选中
				isSelected:function(){
					var pWeiget = this.getParent();
					if(pWeiget && pWeiget.curIds && (pWeiget.curIds.indexOf(this.fdId)>-1)){
						return true;
					}
					return false;
				},
				
				buildIcon:function(iconNode){
					if(this.icon){
						domConstruct.create("div", {className:"mui_zone_list_item_icon_person", style:{background:'url(' + util.formatUrl(this.icon) +') center center no-repeat',backgroundSize:'cover'}}, iconNode);
					}else{
						var orgClass = "fontmuis muis-department";
						var deptClass = "fontmuis muis-group";
						var stationClass = "fontmuis muis-station";
						
						if(this.isExternal && this.isExternal == "true"){
							var orgIconNode = domConstruct.create("div", {className:"mui_zone_list_item_icon_org external"}, iconNode); 
						}else{
							var orgIconNode = domConstruct.create("div", {className:"mui_zone_list_item_icon_org"}, iconNode);
						}
						
						var iconClass = ""; 
						if((this.type | window.ORG_TYPE_ORG) ==  window.ORG_TYPE_ORG){ /* 机构图标  */
							iconClass = orgClass;
						}
						if((this.type | window.ORG_TYPE_DEPT) ==  window.ORG_TYPE_DEPT){ /* 部门图标  */
							iconClass = deptClass;
						}
						if((this.type | window.ORG_TYPE_POST) ==  window.ORG_TYPE_POST){ /* 岗位图标  */
							iconClass = stationClass;
						}
						domConstruct.create("i", {className:iconClass}, orgIconNode);
					}
				},
				
				startup : function() {
					this.inherited(arguments);
					//构建关注（更多）按钮
					if(this.btnArea) {
						var followBtn = new declare( [button, _FollowButtonMixin, _FollowButtonListViewMixin])( {
									"userId" : this.fdId,
									"attentModelName" : "com.landray.kmss.sys.zone.model.SysZonePersonInfo",
									"fansModelName" : "com.landray.kmss.sys.zone.model.SysZonePersonInfo",
									"isFollowPerson" : "true",
									"srcNodeRef" : domConstruct.create("span"),
									"contactInfo" :  {
										email : this.email,
										tel : this.phone,
										personId : this.fdId
									}
								});
						this.btnArea.appendChild(followBtn.domNode);
						followBtn.startup();
					}
				},
				
				_isPerson :function() {
					return this.type && 
						(this.type | window.ORG_TYPE_PERSON) ==  window.ORG_TYPE_PERSON;
				},
				
				_isSelf : function() {
					return this.fdId && (this.fdId == window.currrtPersonId);
				},
				
				buildRendering : function() {
					
					// 分隔线
					if(this["showDividerLine"]==true){
						
						this.domNode = this.containerNode = this.srcNodeRef = domConstruct.create("div", {className:"mui_zone_list_item_divider_line"});
						
					}else{
						
						if(this._isPerson()){
							this.domNode = this.containerNode = this.srcNodeRef = domConstruct.create("div", {className:"mui_zone_list_item mui_zone_person_cateInfo"});	
						}else{
							this.domNode = this.containerNode = this.srcNodeRef = domConstruct.create("li", {className:"mui_zone_list_item"});	
						}		
						this.contentNode = domConstruct.create("div", {className:"mui_zone_list_item_content"}, this.domNode);
						
					}
					
					this.inherited(arguments);
				},
				
				_buildItemBase : function() {
					
					if(this.header != 'true' ) {
						this.zoneItemContainer = domConstruct.create("div", {className:"mui_zone_list_item_container"}, this.contentNode);
						
						// 图标（部门图标、岗位图标、人员图标）
						this.iconNode = domConstruct.create("div", {className:"mui_zone_list_item_icon"}, this.zoneItemContainer);
						this.buildIcon(this.iconNode);
						
						this.infoNode = domConstruct.create("div", {className:"mui_zone_list_item_info"}, this.zoneItemContainer);
						
						// 名称（部门名称、岗位名称、人员姓名）
						if(this._isPerson()){
							this.titleNode = domConstruct.create("div", {className: "mui_zone_list_item_name muiFontSizeM muiFontColorInfo mui_zone_list_item_person_name", innerHTML:this.label}, this.infoNode);
							this.followIconNode = domConstruct.create("div", {className: "mui_zone_list_item_follow_icon"}, this.infoNode);
							this.subscribe("/sys/zone/followStatusChange",function(buttonObj,data){
								if(data.userId == this.fdId){
									if(data.relation == "1"){ /* relation:1 表示我关注了对方  */
										this.followIconNode.innerHTML="<i class='fontmuis muis-follow'></i>";
									}else if(data.relation == "3"){ /* relation:3表示双方相互关注了对方  */
										this.followIconNode.innerHTML="<i class='fontmuis muis-mutual-follow'></i>";
									}else{
										this.followIconNode.innerHTML="";
									}	
								}
							});
						}else{
							this.titleNode = domConstruct.create("div", {className: "mui_zone_list_item_name muiFontSizeM muiFontColorInfo", innerHTML:this.label}, this.infoNode);
						}
						
						// 人员所属岗位
						if(this.post) {
							var postNode = domConstruct.create("div", {className:"mui_zone_list_item_post muiFontSizeS muiFontColorMuted"}, this.infoNode);
							domConstruct.create("span",{innerHTML:msgPerson["sysZonePerson.4m.post"]+":"},postNode);
							domConstruct.create("span",{innerHTML:this.post},postNode);
						}
						
						// 人员手机号码
						if(this.phone) {
							var phoneNode = domConstruct.create("div", {className:"mui_zone_list_item_phone muiFontSizeS muiFontColorMuted"}, this.infoNode);
							domConstruct.create("span",{innerHTML:msgPerson["sysZonePerson.4m.mobilePhone"]+":"},phoneNode);
							domConstruct.create("span",{innerHTML:this.phone},phoneNode);
						}
						
						if(this._isPerson()) {
							var href = util.urlResolver(this.personUrl, {"personId": this.fdId});
							this.href = util.formatUrl(href);
						}
						
						this.connect(this.iconNode,"click","_selectCate");
						this.connect(this.infoNode,"click","_selectCate");
						
						// 更多按钮图标
						this.moreArea = domConstruct.create("div",{className:"mui_zone_list_item_more"},this.zoneItemContainer);
						domConstruct.create("i",{className:"fontmuis muis-to-right muiFontColorMuted"},this.moreArea);
						
						// 人员操作按钮图标
						if(this._isPerson() && !this._isSelf()) {
							this.btnArea = domConstruct.create("div",{className:"mui_zone_list_item_btn"},this.zoneItemContainer);
						}
						
					} else { // 搜索结果展示的头部标题(机构、岗位、员工)
						this.titleNode = domConstruct.create("div", { "className":"mui_zone_list_item_header muiFontSizeS",'innerHTML':this.getTitle() }, this.contentNode);
					}  
					

				},
				_selectCate : function(evt) {
			    
					this.set("entered", true);
					this.defer(function(){
						this.set("entered", false);
					},200);
					this.inherited(arguments);
					var self = this;
					AddressSessionStorage.removeStorage("zoneListData");
					if(this.href) {
						var storageData = {
								curDepId : this.getParent().parentId ? this.getParent().parentId : "",
							    curPersonId : this.fdId ? this.fdId : ""
						};
						
						AddressSessionStorage.setStorage("zoneListData", storageData);
						//getPos
						this.defer(function(){
							window.open(self.href, "_self");
						}, 250);
					}
				}
				
				
			});
			return item;
		});