/**
 * 地址本待选区域内部面板,目前提供1个基类面板和6个拓展面板
 * 1.AbstractAddressPanel:基类面板
 * 2.RecentAddressPanel:常用联系人面板
 * 3.OrgAddressPanel:组织架构面板
 * 4.GroupAddressPanel:群组面板
 * 5.SysRoleAddressPanel:角色面板
 * 6.SearchAddressPanel:高级搜索面板
 * 7.ListAddressPanel:备选列表面板
 */
define(function(require, exports, module) {
	
	var base = require("lui/base");
	var layout = require('lui/view/layout');
	var toolbar = require('lui/toolbar');
	var dialog = require('lui/dialog');
	var lang = require('lang!');
	var uilang = require('lang!sys-ui');
	var orglang = require('lang!sys-organization');
	var topic = require('lui/topic');
	var constant = require('lui/address/addressConstant');
	
	var selectOptionComponent = require('lui/address/component/selectOptionComponent');
	var navComponent = require('lui/address/component/navComponent');
	var treeviewComponent = require('hr/organization/resource/js/address/component/treeviewComponent');
	
	var AbstractAddressPanel = base.Container.extend({
		
		canSearch : true,//是否开启导航栏搜索功能，navComponent中使用..
		
		canSelect : false,//是否开启待选列表筛选功能，selectOptionComponent中使用..
		
		file : './tmpl/address-panel-common.html#',
		
		initProps : function($super, cfg) {
			this.parent = cfg.parent;
			this.ancestor = cfg.ancestor;
			this.params = this.parent.params;
			this.panelId = cfg.panelId;
			this.startup();
		},
		
		startup:function(){
			if (!this.layout && this.file) {
				if(this._getExt(this.file) == 'script'){
					this.layout = new layout.Javascript({
						src : require.resolve(this.file),
						parent : this
					});
				}else{
					this.layout = new layout.Template({
						src : require.resolve(this.file),
						parent : this
					});
				}
				this.layout.startup();
				this.children.push(this.layout);
			}
		},
		
		doLayout:function(obj){
			this.element.html($(obj))
			if(this.parent && this.parent.element){
				var panel = this.parent.element.find("[data-lui-panelid='"+this.panelId+"']");
				panel.html(this.element);
			}
			this.afterLayout();
		},
		
		afterLayout:function(){
			// 调整地址本高度
			var size = dialog.getSizeForAddress();
			if(size && size.height && (size.height - 290) > 290) {
				this.selectAreaHeight = size.height - 290 + 17;
			} else {
				this.selectAreaHeight = 290;
			}
			this.element.find('.lui-address-panel-contentArea').css("height", this.selectAreaHeight);
			
			//构建导航栏
			this.navDom = this.element.find("[data-lui-mark='lui-address-panel-nav']");
			if(this.navDom.length > 0){
				//导航栏组件
				var options = {
					element : this.navDom,
					parent : this,
					refName : this.refName,
					canSearch : this.canSearch,
					realTimeSearch : this.params.realTimeSearch
				};
				if(this.navSelectType){
					options.navSelectType = this.navSelectType;
				}
				this.navComponent = new navComponent(options);
				this.navComponent.startup();
				this.navComponent.draw();
				//this.generateNav(this.navDom[0]);
			}
			//构建待选列表
			this.rightDom = this.element.find("[data-lui-mark='lui-address-panel-right']");
			if(this.rightDom.length > 0){
				if(this.selectAreaHeight)
					this.rightDom.css("height", this.selectAreaHeight);
				//待选列表组件
				this.selectOptionComponent = new selectOptionComponent({
					element : this.rightDom,
					parent : this,
					ancestor : this.ancestor,
					refName : this.refName,
					searchSize : this.params.searchSize,
					canSelect : this.canSelect
				});
				this.selectOptionComponent.startup();
				this.selectOptionComponent.draw();
				//this.generateSelectOpt(this.rightDom[0]);
			}
			//构建左侧树
			this.leftDom = this.element.find("[data-lui-mark='lui-address-panel-left']");
			if(this.leftDom.length > 0 && this.treeviewType ){
				if(this.selectAreaHeight)
					this.leftDom.css("height", this.selectAreaHeight - 7);
				this.treeviewComponent = new this.treeviewType({
					element : this.leftDom,
					refName : this.refName,
					parent : this
				});
				this.treeviewComponent.startup();
				this.treeviewComponent.draw();
				//this.generateTree(this.leftDom[0]);
			}
		},
		
		show : function(){
			this.element.show();
		},
		
		hide : function(){
			this.element.hide();
		},
		
		handleEnterKeyDown : function(){
			if(this.navComponent && this.navComponent.handleEnterKeyDown){
				this.navComponent.handleEnterKeyDown();
			}
		},
		
		//返回后缀名
		_getExt : function(file){
			var ext = file.substring( file.lastIndexOf('.') ).toLowerCase();
			if(ext.indexOf('js') > -1){
				return 'srcipt';
			}
			if(ext.indexOf('html') > -1 || ext.indexOf('jsp')){
				return 'html';
			}
			return 'html';
		}
		
	});
	
	
	var RecentAddressPanel = AbstractAddressPanel.extend({
		
		refName : 'recent',
		
		file : './tmpl/address-panel-recent.html#',
		
		afterLayout:function(){
			var self = this;
			//构建导航栏
			this.navDom = this.element.find("[data-lui-mark='lui-address-panel-nav']");
			if(this.navDom.length > 0){
				//导航栏组件
				this.navComponent = new navComponent({
					element : this.navDom,
					parent : this,
					refName : this.refName,
					canSearch : this.canSearch,
					realTimeSearch : this.params.realTimeSearch
				});
				this.navComponent.startup();
				this.navComponent.draw();
				//this.generateNav(this.navDom[0]);
			}
			
			// 调整地址本高度
			var size = dialog.getSizeForAddress();
			if(size && size.height && (size.height - 290) > 290) {
				this.selectAreaHeight = size.height - 290 + 17;
			} else {
				this.selectAreaHeight = 290;
			}
			this.element.find('.lui-address-panel-contentArea').css("height", this.selectAreaHeight);

			//构建最近人员列表
			this.centerDom = this.element.find("[data-lui-mark='lui-address-panel-center']");
			if(this.centerDom.length > 0){
				//标题
				this.centerTitleDom = $('<div class="lui-address-contentArea-dt" />').html(orglang['table.sysOrganizationRecentContact']).appendTo(this.centerDom);
				
				//清除最近联系人
				this.clearRecentDom = $('<div class="lui-address-clearRecent" />').text(uilang['address.recent.clearAll']).appendTo(this.centerDom);
				this.clearRecentDom.on('click',function(){
					var delUrl = Com_Parameter.ContextPath + 'hr/organization/hr_organization_recent_contact/hrOrganizationRecentContact.do?method=delContacts';
					$.post(delUrl,null,function(data){
						topic.channel(self.refName).publish( constant.event['ADDRESS_DATASOURCE_CHANGED'],{
							data : new KMSSData(),
							sourceComponent : self //发出数据改变事件的对象
						});
					},'json');
				});
				
				this.selectCount = 10; // 查询数据，默认10个
				if(this.selectAreaHeight) {
					var _count = parseInt((this.selectAreaHeight - 280) / 42) * 2;
					this.selectCount += _count;
				}
				
				//待选列表组件
				this.selectOptionLeftComponent = new selectOptionComponent({
					element : this.centerDom,
					parent : this,
					ancestor : this.ancestor,
					refName : this.refName
				});
				this.selectOptionLeftComponent.startup();
				this.selectOptionLeftComponent.draw();
				
				var kmssData = new KMSSData(),
					params =this.params,
					selectType = params.selectType;
				
				kmssData.AddHashMap({
					orgType : selectType,
					selectCount : this.selectCount
				});
				kmssData.SendToBean('hrDialogRecentContactList', function(rtnData){
					topic.channel(self.refName).publish( constant.event['ADDRESS_DATASOURCE_CHANGED'],{
						data : rtnData,
						sourceComponent : self //发出数据改变事件的对象
					});
				});
			}
			
			topic.channel(this.refName).subscribe( constant.event['ADDRESS_SEARCH_CANCEL'] ,this.handleSeacrchCancel,this);
			topic.subscribe(constant.event['ADDRESS_SEARCH_FINISH'],this.handleSearchFinish,this);
		},
		
		handleSeacrchCancel : function(){
			var kmssData = new KMSSData(),
				params =this.params,
				selectType = params.selectType,
				self = this;
			kmssData.AddHashMap({
				orgType : selectType,
				selectCount : this.selectCount
			});
			kmssData.SendToBean('hrDialogRecentContactList', function(rtnData){
				topic.channel(self.refName).publish(constant.event['ADDRESS_DATASOURCE_CHANGED'],{
					data : rtnData,
					sourceComponent : self //发出数据改变事件的对象
				});
			});
			this.centerTitleDom.html(orglang['table.sysOrganizationRecentContact']);
			this.clearRecentDom.show();
		},
		
		handleSearchFinish : function(args){
			if(args && args.navComponent && args.navComponent.refName == this.refName){
				if(args.value){
					this.centerTitleDom.html(uilang['address.toSelect']);
					this.clearRecentDom.hide();
				}else{
					this.centerTitleDom.html(orglang['table.sysOrganizationRecentContact']);
					this.clearRecentDom.show();
				}
			}
			
//			var contentArea = this.element.find('.lui-address-panel-contentArea');
//			if(contentArea.length > 0 && !contentArea.hasClass("overflow-y")) {
//				contentArea.css("overflow-y", "auto");
//			}
		}
		
	});
	
	var OrgAddressPanel = AbstractAddressPanel.extend({
		
		refName : 'org',
		
		canSelect : true,
		
		treeviewType : treeviewComponent.OrgTreeViewComponent,
		
		afterLayout : function($super){
			$super();
		},
		
		handleNodePath : function(args){
			var navsdata = args.navsdata;
			if(navsdata != null && navsdata.length > 0){
				var navdata = navsdata[navsdata.length - 1];//当前节点
				this.navComponent.startWith  = navdata.value;
				this.navComponent.__startWith = navdata.value;
			}
		}
		
	});
	
	var GroupAddressPanel = AbstractAddressPanel.extend({
		
		refName : 'group',
		
		navSelectType : ORG_TYPE_GROUP, // 只搜索群组
		
		treeviewType : treeviewComponent.GroupTreeViewComponent
	});
	
	var SysRoleAddressPanel = AbstractAddressPanel.extend({
		
		canSearch : false,
		
		refName : 'sysRole',
		treeviewType : treeviewComponent.SysRoleTreeViewComponent
		
	});
	
	var SearchAddressPanel = AbstractAddressPanel.extend({
		
		file : './tmpl/address-panel-search.jsp#',
		
		refName : 'search',
		
		accurate : false,//是否精确搜索
		
		afterLayout:function(){
			this.searchbar = this.element.find("[data-lui-mark='lui-address-searchbar']");
			this.buttonSearch = this.buildButton({
				text:lang['button.search']
			});
			this.rebuildSearchType();
			this.bindEvent();
			this.list = this.element.find("[data-lui-mark='lui-address-search-list']");
			if(this.list.length > 0){
				
				//待选列表组件
				this.selectOptionComponent = new selectOptionComponent({
					element : this.list,
					parent : this,
					ancestor : this.ancestor,
					refName : this.refName
				});
				this.selectOptionComponent.startup();
				this.selectOptionComponent.draw();
				
				//this.generateSelectOpt(this.list[0]);
			}
			if(this.params.optionType == 'mul'){
				this.searchbar.attr('title',orglang['sysOrg.address.search.mul.help']);
			}else{
				this.searchbar.attr('title',orglang['sysOrg.address.search.sgl.help']);
			}
			
			// 调整地址本高度
			var size = dialog.getSizeForAddress();
			if(size && size.height && (size.height - 318) > 245) {
				this.selectAreaHeight = size.height - 318;
			} else {
				this.selectAreaHeight = 245;
			}
			this.element.find('.lui-address-search-list').css("height", this.selectAreaHeight);
		},
		buildButton : function(cfg){
			var button = toolbar.buildButton({
				text : cfg.text,
				styleClass : cfg.style
			});
			this.searchbar.append(button.element.css({
				margin : '0 10px',
				display : 'inline-block'
			}));
			button.draw();
			return button;
		},
		rebuildSearchType : function(){
			var selectType = this.params.selectType,
				length = $('[data-lui-mark="lui-address-search-type"]').size();
			if((selectType & ORG_TYPE_ORG) != ORG_TYPE_ORG){
				var type = $('[data-lui-mark="lui-address-search-type"][data-lui-value="1"]'),
					parent = type.parent();
				if(parent){
					parent.remove();
				}
				length-- ;
			}
			if((selectType & ORG_TYPE_DEPT) != ORG_TYPE_DEPT){
				var type = $('[data-lui-mark="lui-address-search-type"][data-lui-value="2"]'),
					parent = type.parent();
				if(parent){
					parent.remove();
				}
				length-- ;
			}
			if((selectType & ORG_TYPE_POST) != ORG_TYPE_POST){
				var type = $('[data-lui-mark="lui-address-search-type"][data-lui-value="4"]'),
					parent = type.parent();
				if(parent)
					parent.remove();
				length-- ;
			}
			if((selectType & ORG_TYPE_PERSON) != ORG_TYPE_PERSON){
				var type = $('[data-lui-mark="lui-address-search-type"][data-lui-value="8"]'),
					parent = type.parent();
				if(parent)
					parent.remove();
				length-- ;
			}
			if((selectType & ORG_TYPE_GROUP) != ORG_TYPE_GROUP){
				var type = $('[data-lui-mark="lui-address-search-type"][data-lui-value="16"]'),
					parent = type.parent();
				if(parent)
					parent.remove();
				length-- ;
			}
			if((selectType & ORG_TYPE_ROLE) != ORG_TYPE_ROLE){
				var type = $('[data-lui-mark="lui-address-search-type"][data-lui-value="32"]'),
					parent = type.parent();
				if(parent)
					parent.remove();
				length-- ;
			}
			//如果只有一种类型，出现过滤选项没有意义，所以多个选项才出现
			if(length <= 1){
				$('[data-lui-mark="lui-address-search-type"]').parent().remove();
			}
			//如果模型一(机构、部门、岗位、人员)没有则移除全选框
			$('.lui-address-searchbar-li.group1:last').addClass('lastchild');
			if($('.lui-address-searchbar-li.group1').length <= 1){
				$('.lui-address-searchbar-li.group1').remove();
			}
		},
		bindEvent : function(){
			var self = this;
			//搜索类型点击事件
			self.element.find(".lui-address-searchbar-li").click(function(){
				loadingDialog = dialog.loading();
				var checkbox = $(this).find('.checkbox');
				//复选框禁用
				if(checkbox.hasClass('disable')){
					return;
				}
				if (checkbox.is(".checked")) {
					checkbox.removeClass("checked");
				}else{
					checkbox.addClass("checked");
				}
				
				//TODO 好像有点怪,还没想到要用什么设计模式替换
				handleSelectAll(this);//如果选中的是全选按钮,做额外处理
				handleAccurate(this);//如果选中的是精确搜索按钮,做额外处理
				
				var filterOption = $('[data-lui-mark="lui-address-search-type"]'),
					selectType;
				for(var i=0; i<filterOption.length; i++){
					if(filterOption.eq(i).hasClass('checked'))
						selectType = selectType | filterOption.eq(i).attr('data-lui-value');
				}
				//self.params.rightSelectType = selectType;
				//联动刷新其他复选框是否需要禁用
				filterOption.removeClass('disable');
				$('[data-lui-mark="lui-address-search-selectall"]').removeClass('disable');
				if(selectType){
					if(selectType <= ORG_TYPE_ALLORG){//模型一:机构、部门、人员、岗位
						filterOption.filter('[data-lui-value="16"],[data-lui-value="32"]').addClass('disable');
					}else if(selectType == ORG_TYPE_GROUP){//模型二:群组
						filterOption.not('[data-lui-value="16"]').addClass('disable');
						$('[data-lui-mark="lui-address-search-selectall"]').addClass('disable');
					}else if(selectType == ORG_TYPE_ROLE){//模型三:角色线
						filterOption.not('[data-lui-value="32"]').addClass('disable');
						$('[data-lui-mark="lui-address-search-selectall"]').addClass('disable');
					}
				}
				
				handleRefeshSelectAll();
				// 关闭loading遮罩层
				if(loadingDialog){
					loadingDialog.hide();
				}
			});
			
			//精确搜索点击事件
			function handleAccurate(_this){
				if($(_this).hasClass('last')){
					var checkbox = $(_this).find('[data-lui-mark="lui-address-search-accurate"]');
					if (checkbox.is(".checked")) {
						self.accurate = true;
					}else{
						self.accurate = false;
					}
				}
			}
			
			//全选点击事件(该全选只是针对机构、部门、岗位、部门进行全选)
			function handleSelectAll (_this){
				if($(_this).hasClass('selectall')){
					var checkbox = $(_this).find('[data-lui-mark="lui-address-search-selectall"]'),
						filterOption = $('[data-lui-mark="lui-address-search-type"]'),
						group1Option = filterOption.not('[data-lui-value="16"],[data-lui-value="32"]');
					if (checkbox.is(".checked")) {
						group1Option.addClass("checked");
					}else{
						group1Option.removeClass("checked");
					}
				}
			}
			
			//刷新全选按钮
			function handleRefeshSelectAll(){
				var filterOption = $('[data-lui-mark="lui-address-search-type"]'),
					group1Option = filterOption.not('[data-lui-value="16"],[data-lui-value="32"]');
					group1OptionCheck = group1Option.filter('.checked'),
					selectAll =  $('[data-lui-mark="lui-address-search-selectall"]');
				if(group1Option.length == group1OptionCheck.length){
					selectAll.addClass('checked');
				}else{
					selectAll.removeClass('checked');
				}
			}
			
			//搜索按钮点击事件
			this.buttonSearch.onClick=function(){
				self.search(self);
			};
			//是否开启实时搜索
			if(!(this.params.realTimeSearch == 'false')){
				self.element.find('[data-lui-mark="lui-address-searchbar-input"]').on('keyup',function(){
					if($(this).val()){
						//如果存在搜索定时器,先清除原来的搜索定时器任务,然后重新起一个新的搜索定时器任务
						if(self.searchTimer){
							clearTimeout(self.searchTimer);
						}
						self.searchTimer = setTimeout(function(){
							self.search(self,true);
							self.searchTimer = null;
						},500);
					}
				});
			}
			//placeholder
			if(LUI && LUI.placeholder){
				 LUI.placeholder(self.element);
			}
		},
		
		search : function(self,noTip){
			var input = $('[data-lui-mark="lui-address-searchbar-input"]');
			if(!input.val()){
				if(!noTip){
					dialog.failure(lang['dialog.requiredKeyword'],null,null,'suggest',null,{
						autoCloseTimeout : 0.5
					});
				}
				return ;
			}
			$(".address-loading").addClass("address-loading-search");
			//获取过滤选项，构造查询的BeanUrl
			var filterOption = $('[data-lui-mark="lui-address-search-type"]');
			var selectType;
			for(var i=0; i<filterOption.length; i++){
				if(filterOption.eq(i).hasClass('checked'))
					selectType = selectType | filterOption.eq(i).attr('data-lui-value');
			}
			var searchBeanUrl = self.params.searchBeanURL;
			var kmssData = new KMSSData();
			kmssData.AddHashMap({
				key : input.val(),
				accurate: this.accurate
			});
			if(selectType!=null){
				kmssData.AddHashMap({
					orgType : selectType
				});
			}else{
				var ___orgType = self.params.selectType & ORG_TYPE_ALLORG;
				if(self.params.selectType == ORG_TYPE_GROUP 
						|| self.params.selectType == ORG_TYPE_ROLE 
						|| self.params.selectType == (ORG_TYPE_GROUP | ORG_TYPE_ROLE) ){
					___orgType = self.params.selectType;
				}
				if(self.params.selectType & ORG_FLAG_AVAILABLEYES)
					___orgType = ___orgType | ORG_FLAG_AVAILABLEYES;
				if(self.params.selectType & ORG_FLAG_AVAILABLENO)
					___orgType = ___orgType | ORG_FLAG_AVAILABLENO;
				if(self.params.selectType & ORG_FLAG_BUSINESSYES)
					___orgType = ___orgType | ORG_FLAG_BUSINESSYES;
				if(self.params.selectType & ORG_FLAG_BUSINESSNO)
					___orgType = ___orgType | ORG_FLAG_BUSINESSNO;
				kmssData.AddHashMap({
					orgType : ___orgType
				});
			}
			kmssData.SendToBean(searchBeanUrl, function(rtnData){
				if(!self.accurate){
					if(rtnData && rtnData.data && rtnData.data.length > self.params.searchSize ){
						rtnData.data = rtnData.data.slice(0,self.params.searchSize);
					}
					topic.channel(self.refName).publish( constant.event['ADDRESS_DATASOURCE_CHANGED'],{
						data : rtnData,
						sourceComponent : self //发出数据改变事件的对象
					});
				}else{//精确搜索
					var resultArr = rtnData.GetHashMapArray(),
						notFoundString = '',
						list = [];
					for(var i=0; i<resultArr.length; i++){
						var result = resultArr[i];
						if(result.id==null){
							notFoundString+="; "+result.key;
							continue;
						}
						list.push(result);
					}
					topic.channel(self.refName).publish( constant.event['ADDRESS_DATASOURCE_CHANGED'],{
						data : rtnData,
						sourceComponent : self //发出数据改变事件的对象
					});
					if(!self.ancestor || !self.ancestor.mul) { // 如果是多选，不需要自动选中
						if(list.length > 0){
							topic.publish( constant.event['ADDRESS_ELEMENT_SELECT'],list);
						}
					}
				}
			});
			topic.publish( constant.event['ADDRESS_SEARCH_FINISH'] );
		},
		
		handleEnterKeyDown : function(){
			this.search(this);
		}
		
	});
	
	
	var ListAddressPanel = AbstractAddressPanel.extend({
			
		file : './tmpl/address-panel-list.html#',
		
		refName : 'list',
		
		afterLayout: function(obj){
			var self = this;
			//构建导航栏
			this.navDom = this.element.find("[data-lui-mark='lui-address-panel-nav']");
			if(this.navDom.length > 0){
				//导航栏组件
				this.navComponent = new navComponent({
					element : this.navDom,
					parent : this,
					refName : this.refName,
					canSearch : this.canSearch,
					searchfunc : function(value,context){
						var searchBeanURL = self.params.searchBeanURL;
						searchBeanURL = searchBeanURL.replace(/!\{keyword\}/g, encodeURIComponent(value));
						var kmssdata = new KMSSData().AddXMLData(searchBeanURL);
						kmssdata.Format("id:name:info");
						self.getDataByIds(kmssdata.data,function(rtnData){
							topic.channel(self.refName).publish( constant.event['ADDRESS_DATASOURCE_CHANGED'],{
								data : rtnData,
								sourceComponent : self //发出数据改变事件的对象
							});
						});
					},
					realTimeSearch : this.params.realTimeSearch
				});
				this.navComponent.startup();
				this.navComponent.draw();
			}
			//构建备选列表
			this.centerDom = this.element.find("[data-lui-mark='lui-address-panel-center']");
			if(this.centerDom.length > 0){
				//待选列表组件
				this.selectOptionLeftComponent = new selectOptionComponent({
					element : this.centerDom,
					parent : this,
					ancestor : this.ancestor,
					refName : this.refName
				});
				this.selectOptionLeftComponent.startup();
				this.selectOptionLeftComponent.draw();
				
				this.centerDom.prepend($('<div class="lui-address-contentArea-dt">'+ this.params.title +'</div>'));
				
				var params =this.params,
					optionData = params.optionData,
					self = this;
				if(optionData!=null){
					optionData = optionData.Format("id:name:info");
					this.getDataByIds(optionData.data,function(rtnData){
						topic.channel(self.refName).publish( constant.event['ADDRESS_DATASOURCE_CHANGED'],{
							data : rtnData,
							sourceComponent : self //发出数据改变事件的对象
						});
					});
				}else{
					topic.channel(self.refName).publish( constant.event['ADDRESS_DATASOURCE_CHANGED'],{
						data : new KMSSData(),
						sourceComponent : self //发出数据改变事件的对象
					});
				}
			}
			
			topic.channel(this.refName).subscribe( constant.event['ADDRESS_SEARCH_CANCEL'] ,this.handleSeacrchCancel,this);
			
			// 调整地址本高度
			var size = dialog.getSizeForAddress();
			if(size && size.height && (size.height - 310) > 310) {
				this.selectAreaHeight = size.height - 310 + 80;
				this.element.find('.lui-address-panel-contentArea').css("overflow-y", "auto");
			} else {
				this.selectAreaHeight = 310;
			}
			this.element.find('.lui-address-panel-contentArea').css("height", this.selectAreaHeight);
		},
		
		getDataByIds : function(array,cb){
			var ids = '';
			for(var i =0;i <array.length;i++){
				if(array[i].id){
					ids += array[i].id + ';';
				}
			}
			if(cb){
				if(ids.length > 0){
					ids = ids.substring(0,ids.length - 1);
					var kmssData = new KMSSData();
					kmssData.AddHashMap({
						ids : ids
					});
					kmssData.SendToBean('hrorganizationDialogSearch', function(rtnData){
						cb(rtnData);
					});
				}else{
					cb(new KMSSData());
				}
			}
		},
		
		handleSeacrchCancel: function(){
			var params =this.params,
				optionData = params.optionData,
				self = this;
			if(optionData!=null){
				optionData = optionData.Format("id:name:info");
				this.getDataByIds(optionData.data,function(rtnData){
					topic.channel(self.refName).publish( constant.event['ADDRESS_DATASOURCE_CHANGED'],{
						data : rtnData,
						sourceComponent : self //发出数据改变事件的对象
					});
				});
			}else{
				topic.channel(self.refName).publish( constant.event['ADDRESS_DATASOURCE_CHANGED'],{
					data : [],
					sourceComponent : self //发出数据改变事件的对象
				});
			}
		}
		
	});
	
	exports.AbstractAddressPanel = AbstractAddressPanel;
	exports.RecentAddressPanel = RecentAddressPanel;
	exports.OrgAddressPanel = OrgAddressPanel;
	exports.GroupAddressPanel = GroupAddressPanel;
	exports.SysRoleAddressPanel = SysRoleAddressPanel;
	exports.SearchAddressPanel = SearchAddressPanel;
	exports.ListAddressPanel = ListAddressPanel;
	
});