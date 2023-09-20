/**
 * 导航栏组件( 机构 > 部门 > 子部门 )
 * TODO : 抽取高级搜索和导航栏搜索中的公用代码
 */
define(function(require, exports, module) {
	
	var base = require('lui/base');
	var topic = require('lui/topic');
	var $ = require('lui/jquery');
	var layout = require('lui/view/layout');
	var dialog = require('lui/dialog');
	var orglang = require('lang!sys-organization');
	var lang = require('lang!');
	var constant = require('lui/address/addressConstant');
	
	var NavComponent = base.Component.extend({
		
		orgTypeFilter : -1,
		
		canSearch : true,
		
		navUlDom : null,
		
		initProps : function($super, _config) {
			$super(_config);
			this.refName = _config.refName;
			this.navSelectType = _config.navSelectType;//导航栏右侧搜索组件的搜索类型
			// #55619 当选择的组织架构类型和导航的组织架构类型冲突时，不进行搜索（开始）
			// 原来代码：this.canSearch = _config.canSearch;
			var params = this.parent.params;
			if(this.navSelectType && (this.navSelectType & params.selectType) == 0) {
				this.canSearch = false;
			} else {
				this.canSearch = _config.canSearch;
			}
			// （结束）
			this.searchfunc = _config.searchfunc;
			this.realTimeSearch = _config.realTimeSearch;
		}, 
		
		draw: function($super){
			this.generateNav();
			$super();
		},
		
		generateNav : function(){
			this.navUlDom = $('<ul class="lui-address-panel-nav-ul"/>');
			this.navUlDom.appendTo(this.element);
			if(this.canSearch){
				this._generateSearch();
			}
			topic.channel(this.refName).subscribe( constant.event['ADDRESS_ORGTYPEFILTER_CHANGE'],this.handleOrgTypeFilterChange,this);
			topic.channel(this.refName).subscribe( constant.event['ADDRESS_DATASOURCE_CHANGED'],this.refreshNav,this);
		},
		
		handleOrgTypeFilterChange: function(args){
			this.orgTypeFilter = args.orgTypeFilter;
		},
		
		refreshNav : function(args){
			var searchWidth = 0
			$('.lui-address-nav-search').each(function (i, el) {
					if (el.clientWidth) searchWidth = el.clientWidth
				})
			var windowsWidth = document.body.clientWidth,
				navsdata = args.navsdata || [],
				ulWidth = windowsWidth - searchWidth,//UL可接受的长度
				realWidth = 0,//UL内部实际的长度
				self = this;
			if(navsdata.length == 0){
				return;
			}
			this.navUlDom.html('');
			
			var clickfn = function(id){
				return function(){
					topic.channel(self.refName).publish( constant.event['ADDRESS_NAV_SELECTED'],{
						node : id
					});
					topic.publish( constant.event['ADDRESS_NAV_SELECTED'],{node : id});//默认频道也塞个,方便全局订阅
				}
			};
			
			for(var i = 0;i < navsdata.length;i++){
				var li = $('<li class="lui-address-panel-nav-li" />')
						.text(navsdata[i].text)
						.attr('title',navsdata[i].text)
						.click( clickfn(navsdata[i].id) );
				if(navsdata[i].disable){
					li.addClass('disable');
				}
				this.navUlDom.append(li);
				realWidth += li.width();
				if(i != navsdata.length - 1){
					var divide = $('<li class="divide" />');
					this.navUlDom.append(divide);
					realWidth += divide.width() + 10;
				}
			}
			if( realWidth + 10 > ulWidth ){
				var first = this.navUlDom.find('.divide').eq(0),
					leave = $('<li class="lui-address-panel-nav-li leave" />').text('…………'),
					leaveDivide = $('<li class="divide" />');
				first.after(leave);
				leave.after(leaveDivide);
				realWidth = realWidth + leave.width() + divide.width() + 10;
				while(realWidth + 10 > ulWidth){
					var li = leaveDivide.next(),
						devide = li.next();
					realWidth  = realWidth - li.width();
					li.remove();
					if(devide.length > 0){
						realWidth  = realWidth - devide.width() - 10;
						devide.remove();
					}
				}
			}
		},
		
		_toggleRangeSearch : function(evt) {
			var self = evt.data,
				rangeSearchDiv = self.element.find('[data-lui-mark="lui-nav-range-search-div"]'),
				rangeSearchQuery = self.element.find('[data-lui-mark="lui-nav-range-search-query"]'),
				rangeSearchCancel = self.element.find('[data-lui-mark="lui-nav-range-search-cancel"]'),
				fdNameInput = self.element.find('[data-lui-mark="lui-nav-range-search-fdName"]'),
				fdDeptNameInput = self.element.find('[data-lui-mark="lui-nav-range-search-fdDeptName"]'),
				fdHasChildInput = self.element.find('[data-lui-mark="lui-nav-range-search-fdHasChild"]'),
				input = self.element.find('[data-lui-mark="lui-nav-search-input"]'),
				params = self.parent.params,
				searchBeanUrl = params.searchBeanURL,
				deptLimit = params.deptLimit;
			var inputVal = input.val();
			if(inputVal!=null&&inputVal!=""){
				fdNameInput.val(inputVal);
			}
			if(rangeSearchDiv.is(':hidden')){
				rangeSearchDiv.slideDown();
				fdNameInput.keyup(function(){
					if(!$(this).val()&&!fdDeptNameInput.val()){
						topic.channel(self.refName).publish( constant.event['ADDRESS_SEARCH_CANCEL']);
					}
				});
				fdDeptNameInput.keyup(function(){
					if(!$(this).val()&&!fdNameInput.val()){
						topic.channel(self.refName).publish( constant.event['ADDRESS_SEARCH_CANCEL']);
					}
				});
				fdHasChildInput.keyup(function(){
					if(!$(this).val()&&!fdNameInput.val()){
						topic.channel(self.refName).publish( constant.event['ADDRESS_SEARCH_CANCEL']);
					}
				});
				rangeSearchQuery.click(function(){
					var fdName = fdNameInput.val();
					var fdDeptName = fdDeptNameInput.val();
					var fdHasChild = fdHasChildInput.get(0).checked;
					var kmssData = new KMSSData();
					if(!self.navSelectType){
						self.navSelectType = params.selectType & ORG_TYPE_ALLORG;
						if(params.selectType & ORG_FLAG_AVAILABLEYES)
							self.navSelectType = self.navSelectType | ORG_FLAG_AVAILABLEYES;
						if(params.selectType & ORG_FLAG_AVAILABLENO)
							self.navSelectType = self.navSelectType | ORG_FLAG_AVAILABLENO;
						if(params.selectType & ORG_FLAG_BUSINESSYES)
							self.navSelectType = self.navSelectType | ORG_FLAG_BUSINESSYES;
						if(params.selectType & ORG_FLAG_BUSINESSNO)
							self.navSelectType = self.navSelectType | ORG_FLAG_BUSINESSNO;
					}
					var orgType = self.navSelectType;
					if(self.orgTypeFilter && self.orgTypeFilter!= -1){
						orgType = orgType & self.orgTypeFilter;
					}
					kmssData.AddHashMap({
						fdName : fdName,
						fdDeptName : fdDeptName,
						fdHasChild : fdHasChild,
						orgType : orgType,
						range : 'true'
					});
					var searchBean = searchBeanUrl, index = searchBean.indexOf("s_bean=");
					if (index > -1) {
						searchBean = searchBean.substr(index + 7);
					}
					if(deptLimit){
						searchBean += "&deptLimit=" + deptLimit;
					}
					kmssData.SendToBean(searchBean, function(rtnData){
						if(rtnData && rtnData.data && rtnData.data.length > params.searchSize ){
							rtnData.data = rtnData.data.slice(0,params.searchSize);
						}
						topic.channel(self.refName).publish( constant.event['ADDRESS_DATASOURCE_CHANGED'],{
							data : rtnData,
							sourceComponent : self //发出数据改变事件的对象
						});
					});
					if(!fdName&&!fdDeptName){
						topic.channel(self.refName).publish( constant.event['ADDRESS_SEARCH_CANCEL']);
					}else{
						topic.publish( constant.event['ADDRESS_SEARCH_FINISH'],{
							navComponent : self,
							value : fdName||fdDeptName
						});
					}
					rangeSearchDiv.slideUp();
				});
				rangeSearchCancel.bind('click',function(){
					fdNameInput.val('');
					fdDeptNameInput.val('');
					topic.channel(self.refName).publish( constant.event['ADDRESS_SEARCH_CANCEL']);
					rangeSearchDiv.slideUp();
				});
			}else{
				rangeSearchDiv.slideUp();
			}
		},
		
		_generateSearch : function(){
			var self = this,
				params = self.parent.params,
				deptLimit = params.deptLimit;//部门限定参数
			this.navLayout =  new layout.Template({
				src : require.resolve('../tmpl/address-nav-search.jsp#'),
				parent : this
			});
			this.navLayout.startup();
			this.navLayout.get(this,function(obj){
				$(obj).appendTo(self.element);
				var search = self.element.find('[data-lui-mark="lui-nav-search-btn"]'),
					input = self.element.find('[data-lui-mark="lui-nav-search-input"]'),
					cacel = self.element.find('[data-lui-mark="lui-nav-search-cancel"]'),
					rangeSearch = self.element.find('[data-lui-mark="lui-nav-range-search-btn"]');
				search.click(self,self.search);//搜索按钮事件绑定
				// 存在部门限定参数时不显示搜索范围
				if(!deptLimit){
					rangeSearch.click(self,self._toggleRangeSearch);
				}else{
					rangeSearch.hide();
				}
				//TODO keyup事件不支持黏贴和组合键,考虑换成input+propertychange(for IE8)事件,不过暂时有点瑕疵,待研究....
				input.on('input propertychange',function(evt){//搜索框事件绑定
					if($(this).val()){
						cacel.css('display','inline');
					}else{
						cacel.trigger('click');
					}
					//实时搜索
					if(!(self.realTimeSearch == 'false') && $(this).val() ){
						//如果存在搜索定时器,先清除原来的搜索任务,然后重新起一个新的搜索任务
						if(self.searchTimer){
							clearTimeout(self.searchTimer);
						}
						self.searchTimer = setTimeout(function(){
							self.search({
								data:self,
								noTip : true
							});
							self.searchTimer = null;
						},500);
					}
				});
				cacel.click(function(){//取消搜索按钮事件绑定
					topic.channel(self.refName).publish( constant.event['ADDRESS_SEARCH_CANCEL']);
					$(this).hide();
					if(input.val()){
						input.val('');
					}
				});
				//placeholder
				if(LUI && LUI.placeholder){
					 LUI.placeholder(self.element);
				}
			});
		},
		
		search : function(evt){
			var self = evt.data,
				input = self.element.find('[data-lui-mark="lui-nav-search-input"]'),
				params = self.parent.params,
				searchBeanUrl = params.searchBeanURL,
				deptLimit = params.deptLimit;
			if(!input.val()){
				if(!evt.noTip){
					dialog.failure(lang['dialog.requiredKeyword'],null,null,'suggest',null,{
						autoCloseTimeout : 0.5
					});
				}
				return ;
			}
			loadingDialog = dialog.loading();
			if(self.searchfunc){
				self.searchfunc(input.val(),this);
				topic.publish( constant.event['ADDRESS_SEARCH_FINISH'],{
					navComponent : self,
					value : input.val()
				});
				// 关闭loading遮罩层
				if(loadingDialog){
					loadingDialog.hide();
				}
				return;
			}
			var kmssData = new KMSSData();
			if(!self.navSelectType){
				self.navSelectType = params.selectType & ORG_TYPE_ALLORG;
				if(params.selectType == ORG_TYPE_GROUP 
						|| params.selectType == ORG_TYPE_ROLE 
						|| params.selectType == (ORG_TYPE_GROUP | ORG_TYPE_ROLE) ){
					self.navSelectType = params.selectType;
				}
				if(params.selectType & ORG_FLAG_AVAILABLEYES)
					self.navSelectType = self.navSelectType | ORG_FLAG_AVAILABLEYES;
				if(params.selectType & ORG_FLAG_AVAILABLENO)
					self.navSelectType = self.navSelectType | ORG_FLAG_AVAILABLENO;
				if(params.selectType & ORG_FLAG_BUSINESSYES)
					self.navSelectType = self.navSelectType | ORG_FLAG_BUSINESSYES;
				if(params.selectType & ORG_FLAG_BUSINESSNO)
					self.navSelectType = self.navSelectType | ORG_FLAG_BUSINESSNO;
			}
			var startWith = '';
			if(self.startWith){
				startWith = self.startWith;
			}else if( params.startWith){
				startWith = params.startWith ;
			}
			var orgType = self.navSelectType;
			if(self.orgTypeFilter && self.orgTypeFilter!= -1){
				orgType = orgType & self.orgTypeFilter;
			}
			
			kmssData.AddHashMap({
				key : input.val(),
				startWith : startWith,
				orgType : orgType
			});
			var searchBean = searchBeanUrl, index = searchBean.indexOf("s_bean=");
			if (index > -1) {
				searchBean = searchBean.substr(index + 7);
			}
			if(deptLimit){
				searchBean += "&deptLimit=" + deptLimit;
			}
			kmssData.SendToBean(searchBean, function(rtnData){
				if(rtnData && rtnData.data && rtnData.data.length > params.searchSize ){
					rtnData.data = rtnData.data.slice(0,params.searchSize);
				}
				topic.channel(self.refName).publish( constant.event['ADDRESS_DATASOURCE_CHANGED'],{
					data : rtnData,
					sourceComponent : self //发出数据改变事件的对象
				});
			});
			topic.publish( constant.event['ADDRESS_SEARCH_FINISH'],{
				navComponent : self,
				value : input.val()
			});
			// 关闭loading遮罩层
			if(loadingDialog){
				loadingDialog.hide();
			}
		},
		
		handleEnterKeyDown : function(){
			this.search({
				data : this
			});
		},
		
		reload : function(){
			this.search({
				data : this
			});
		}
		
	});
	
	module.exports = NavComponent;
	
});