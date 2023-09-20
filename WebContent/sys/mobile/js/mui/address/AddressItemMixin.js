define(	["dojo/_base/declare", "dojo/_base/array", "dojo/dom-class", "mui/iconUtils" , "mui/category/CategoryItemMixin" ,"dojo/dom-construct", "mui/util",
       	"dijit/registry","mui/i18n/i18n!sys-mobile", "dojo/request", "dojo/topic", "mui/dialog/Tip"],
		function(declare, array, domClass, iconUtils, CategoryItemMixin, domConstruct, util, registry, Msg, request, topic, Tip) {
			var item = declare("mui.address.AddressItemMixin", [ CategoryItemMixin ], {
				
				buildRendering:function(){
					this.inherited(arguments);
				},
				
				startup : function() {					
					this.inherited(arguments);
					this.addColorToKeyWords();
				},
				
				_buildItemBase: function(){
					this.inherited(arguments);
					if (this.header != "true") {
						// 重写infoNode
						var infoNode = domConstruct.create('div', {
							'className' : 'muiCateInfo '
						}, this.cateContainer);
						domConstruct.place(infoNode, this.infoNode, 'after');
						domConstruct.destroy(this.infoNode);
						this.infoNode = infoNode;
						// 姓名
						this.titleNode = domConstruct.create('div', {
							className : 'muiCatemuiCateName'
						}, this.infoNode);
						this.labelNode = domConstruct.create('span',{
							className: 'muiAddressLabelName',
							innerHTML :  util.formatText(this.label)
						},this.titleNode);
					}
					// 人员增加岗位、部门全路径等信息
					if( this.type != null && (this.type || window.ORG_TYPE_PERSON) == window.ORG_TYPE_PERSON){
						domClass.add(this.infoNode, 'muiAddressPersonInfo');
						// 职级名称
						if(this.staffingLevel){
							domConstruct.create('span',{
								className: 'muiAddressStaffingLevelName',
								innerHTML :  util.formatText(this.staffingLevel)
							},this.titleNode);
						}
						// 负责人
						if(this.authElementAdmin){
							domConstruct.create('span',{
								className: 'muiAddressAuthElementAdminName',
								innerHTML :  util.formatText(this.authElementAdmin)
							},this.titleNode);
						}
						// 岗位
						if(this.posts && this.posts.length > 0){
							for(var i=0; i<this.posts.length; i++) {
								domConstruct.create('span',{
									className: 'muiAddressPostName',
									innerHTML :  util.formatText(this.posts[i])
								},this.titleNode);
							}
						}
						if(this.leaderType){
							domClass.add(this.infoNode, 'muiAddressLeaderPerson');
							var leaderNode = domConstruct.create('span',{
								className: 'muiAddressLeaderLabel'
							},this.titleNode);
							if((this.leaderType & window.ORG_TYPE_ORG) == window.ORG_TYPE_ORG){
								domClass.add(leaderNode, 'muiAddressLeaderOrg');
								domConstruct.create('img',{
									className: 'muiAddressLeaderImg',
									src: util.formatUrl('/sys/mobile/css/themes/default/images/address-leader-org.png')
								}, leaderNode);
							}
							if((this.leaderType & window.ORG_TYPE_DEPT) == window.ORG_TYPE_DEPT){
								domClass.add(leaderNode, 'muiAddressLeaderDept');
								domConstruct.create('img',{
									className: 'muiAddressLeaderImg',
									src: util.formatUrl('/sys/mobile/css/themes/default/images/address-leader-dept.png')
								}, leaderNode);
							}
							domConstruct.create('span',{
								className: 'muiAddressLeaderLabel',
								innerHTML :  Msg['mui.mobile.address.leader.label']
							}, leaderNode);
						}
					}else if (this.header != "true") {
						domClass.add(this.infoNode, 'muiAddressNonePersonInfo');
					}
					// 部门全路径
					if(this.parentNames){
						domClass.add(this.infoNode,'muiAddressWithParentNames');
						domConstruct.create('div',{
							className: 'muiAddressParentNames',
							innerHTML: this._formatParentNames(util.formatText(this.parentNames))
						},this.infoNode);
					}
					// 群组分类不显示图标
					if(this.type != null && (this.type || window.ORG_TYPE_GROUP_CATE) == window.ORG_TYPE_GROUP_CATE){
						if(this.iconNode){
							domConstruct.destroy(this.iconNode);
						}
						domClass.add(this.domNode, 'muiAddressGroupCateItem');
					}
					this.iconNode && this.connect(this.iconNode, "click", "_selectCate")
			        this.infoNode && this.connect(this.infoNode, "click", "_selectCate")
				},
				
				_formatParentNames: function(parentNames){
					if(!parentNames){
						return parentNames;
					}
					var formatParentNames = '';
					var parents = parentNames.split('_');
					var length = parents.length;
					var startIndex = length <= 3 ? 0 : length - 3;
					for(var i = startIndex; i < length; i++){
						var name = parents[i];
						if(name.length > 8){
							name = name.substring(0,8) + '...';
						}
						formatParentNames += name + '_';
					}
					formatParentNames = formatParentNames.replace(/_$/,'');
					return formatParentNames;
				},
				
				//获取分组标题信息
				getTitle:function(){
					if( this.label=='1' ){
						return Msg['mui.address.org'];
					}
					if( this.label=='2' ){
						return Msg['mui.address.dept'];
					}
					if(this.label=='4'){
						return Msg['mui.address.post'];
					}
					if( this.label=='8' ){
						return Msg['mui.address.person'];
					}
					if( this.label=='16' ){
						return Msg['mui.address.group'];
					}
					return this.label;
				},
				
				//是否显示往下一级
				showMore : function(){
					// 机构、部门
					if((this.type | window.ORG_TYPE_ORGORDEPT) ==  window.ORG_TYPE_ORGORDEPT){
						if(this.scope == "22" || this.scope == "33"|| this.scope == "44"){
							return false;
						}
						return true;
					}
					// 群组分类
					if((this.type | window.ORG_TYPE_GROUP_CATE) ==  window.ORG_TYPE_GROUP_CATE){
						if(this.scope == "22" || this.scope == "33"|| this.scope == "44"){
							return false;
						}
						return true;
					}
					// 群组
					if((this.type | window.ORG_TYPE_GROUP) ==  window.ORG_TYPE_GROUP){
						if(this.scope == "22" || this.scope == "33"|| this.scope == "44"){
							return false;
						}
						return true;
					}
					return false;
				},
				
				//是否显示选择框
				showSelect:function(){
					// 指定查看指定人员则不能选择部门
					if (this.type == '2' && this.isAllDept && this.isAllDept == 'false') {
						return false;
					}
					var pWeiget = this.getParent();
					//支持选择无效的人员	
					if(pWeiget && pWeiget.selType == '512' && this.type == '8'){
						return true; 
					}
					if(pWeiget && ((pWeiget.selType & this.type) ==  this.type)){
						if(this.isAllDept) {
							if(this.isAllDept == 'true') {
								return true;
							} else {
								return false;
							}
						} else {
							return true; 
						}
					}
					
					return false;
				},
				
				_toggleSelect : function(select) {
					this.inherited(arguments);
					if (select) {
						topic.publish('/mui/category/setSelected', this, this);
					} else {
						topic.publish('/mui/category/cancelSelected', this, this);
					}
				},
				
				_setSelectedTrigger: function() {
					topic.publish("/mui/category/selected", this, {
						label: this.label,
						fdId: this.fdId,
						icon: this.icon,
						type: this.type,
						staffingLevel : this.staffingLevel,
						parentNames: this.parentNames,
						isExternal: this.isExternal
					})
					topic.publish("/mui/category/cate_selected", this, {
						label: this.label,
						fdId: this.fdId,
						icon: this.icon,
						type: this.type,
						staffingLevel: this.staffingLevel,
						parentNames: this.parentNames,
						isExternal: this.isExternal
					})
			    },
				
				//是否选中
				isSelected:function(){
					var pWeiget = this.getParent();
					if(pWeiget && pWeiget.curIds){
						var arrs = pWeiget.curIds.split(";");
						if (array.indexOf(arrs,this.fdId)>-1)
							return true;
					}
					return false;
				},
				
				buildIcon:function(iconNode){
					if(this.icon){
						//iconUtils.setIcon(util.formatUrl(this.icon), null,this._headerIcon, null, iconNode);
						domConstruct.create("span", {style:{background:'url(' + util.formatUrl(this.icon) +') center center no-repeat',backgroundSize:'cover',display:'inline-block'}}, iconNode);
					}else{
						if(dojoConfig.dingXForm && dojoConfig.dingXForm === "true" && this.type == window.ORG_TYPE_PERSON){
							iconNode.innerHTML = iconUtils.createDingIcon(this.label,"div");
						}else{
							// 目前只有岗位和部门有区分内外头像
							if (this.isExternal && this.isExternal == 'true') {
								if((this.type | window.ORG_TYPE_ORG) ==  window.ORG_TYPE_ORG){
									this.icon = "address-org-external";
									this.isPngIcon = true;
								}
								if((this.type | window.ORG_TYPE_DEPT) ==  window.ORG_TYPE_DEPT){
									this.icon = "address-dept-external"; 
									this.isPngIcon = true;
								}
								if((this.type | window.ORG_TYPE_POST) ==  window.ORG_TYPE_POST){
									this.icon = "address-post-external"; 
									this.isPngIcon = true;
								}
							} else {
								if((this.type | window.ORG_TYPE_ORG) ==  window.ORG_TYPE_ORG){
									this.icon = "address-org";
									this.isPngIcon = true;
								}
								if((this.type | window.ORG_TYPE_DEPT) ==  window.ORG_TYPE_DEPT){
									this.icon = "address-dept"; 
									this.isPngIcon = true;
								}
								if((this.type | window.ORG_TYPE_POST) ==  window.ORG_TYPE_POST){
									this.icon = "address-post"; 
									this.isPngIcon = true;
								}
							}
							if((this.type | window.ORG_TYPE_GROUP) ==  window.ORG_TYPE_GROUP){
								this.icon = "address-group"; 
								this.isPngIcon = true;
							}
							if(this.isPngIcon){
								this.buildPNGIcon(this.icon, iconNode);
							}else{
								iconUtils.setIcon(this.icon, null,
										this._headerIcon, null, iconNode);
							}
						}
					}
				},
				
				buildPNGIcon: function(pngIcon, container){
					domConstruct.create('img', {
						src: util.formatUrl('/sys/mobile/css/themes/default/images/' + pngIcon + '.png')
					}, container);
				},
				
				addColorToKeyWords:function(){
					if(!this.labelNode){
						return;
					}
					var searchBarNode = this.findSearchBar();
					if(!searchBarNode){
						return;
					}
					var keyWords = searchBarNode.searchNode.value;
					if (keyWords){
						var arr = keyWords.replace(/'/g, "''").replace(/,/g,";").replace(/，/g,";").replace(/；/g,";").split(";");
						var hashKey = [];
						var htmlTemp = this.labelNode.innerHTML;
						array.forEach(arr, function(key) {
							if(hashKey.indexOf(key) < 0){
								var replaceWord = ("<em>"+ key+"</em>");
								htmlTemp = htmlTemp.replace(key,replaceWord);
								hashKey.push(key);
							}
					  }, this);
					  if (htmlTemp){
						  this.labelNode.innerHTML = htmlTemp; 
					  }
					}
				},
				
				findSearchBar: function(){
					var searchBarID = '_address_search_' + this.key;
					var searchBar = registry.byId(searchBarID);
					return searchBar
				},
				
				//重写此方法，展开下级之前请求是否存在数据
				_openCate:function(evt){
					
					evt && evt.stopPropagation();
					
					var self = this;
					
					this.set("entered", true);
					
					this.defer(function(){
						this.set("entered", false);
						
						//选择机构或者部门的情况下执行判断
						if(this.selType == 1 || this.selType == 2 || this.selType == 3) {
							var url = util.urlResolver(util.formatUrl(this.dataUrl), {
								parentId: this.fdId || '',
								selType: this.selType || '',
								deptLimit: this.deptLimit || ''
							});
							request.post(url, {
								handleAs: 'json'
							}).then(function(res) {
								if((res || []).length < 1) {
									self.moreArea && domConstruct.destroy(self.moreArea);
									
									Tip.fail({
										text: '当前没有下级部门'
									});
									
								} else {
									topic.publish("/mui/view/scrollTo",this,{y:(0)});
									topic.publish("/mui/category/changed", self, {
										'fdId': self.fdId,
										'label': self.label,
										'type': self.type
									});
								}
							}, function(err) {
								Tip.fail({
									text: '请求获取数据失败'
								});
							});
						} else {
							topic.publish("/mui/view/scrollTo",this,{y:(0)});
							topic.publish("/mui/category/changed", self, {
								'fdId': self.fdId,
								'label': self.label,
								'type': self.type
							});
						}
						
						
					}, 200);
					return;
				}
			});
			return item;
		});