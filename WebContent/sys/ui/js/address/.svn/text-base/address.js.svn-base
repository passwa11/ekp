/**
 * 基于新UI地址本组件v1.0
 * TODO : 
 * 	1.自定义滚动条(解决非OSX系统下滚动条太难看问题)
 * 	2.常用联系人(暂无需求)
 *  3.搜索支持复制黏贴和组合键
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var base = require('lui/base');
	var layout = require('lui/view/layout');
	var topic = require('lui/topic');
	var env = require('lui/util/env');
	var dialog = require('lui/dialog');
	var lang = require('lang!sys-ui');
	var constant = require('lui/address/addressConstant');
	
	var AddressSelectedArea = require('lui/address/component/selectedAreaComponent');//已选区域组件
	var AddressButtons = require('lui/address/component/buttonComponent');//底部按钮栏组件
	var AddressPanel = require('lui/address/addressPanel');
	
	var cfgfunc = require('lui/address/addressCfg');
	
	//暴露一个全局变量
	if(!window.___landrayAddress___){
		window.___landrayAddress___ = {
			version : '1.0'	
		};
	}
	
	//地址本入口
	var Address = base.Container.extend({
		
		selectedPanel : null,
		
		initProps : function($super, cfg) {
			$super(cfg);
			this.mul = this.config.mulSelect;
			this.elem = $(this.config.elem);
			this.selectType = this.config.selectType;
			this.optionType = this.config.optionType;
			this.params = this.config;
			this.addresscfg = this.customCfg();
			if(!this.addresscfg){
				if(this.params.deptLimit){
					this.addresscfg = cfgfunc({
						includeType : 'address.tabs.org'
					});
				}else if ((this.selectType & ORG_TYPE_ROLE) == ORG_TYPE_ROLE){//是否显示角色线
					this.addresscfg = cfgfunc();
				}else{
					this.addresscfg = cfgfunc({
						exceptType : 'address.tabs.sysRole'
					});
				}
			}
			this.startup();
		},
		
		customCfg : function(){
			//for override
		},
		
		startup : function() {
			if (!this.layout) {
				this.layout = new layout.Template({
					src : require.resolve('./tmpl/address.html#'),
					parent : this
				});
				this.layout.startup();
				this.children.push(this.layout);
			}
			//选择页签组件构建
			if (!this.tab) {
				this.tab = new AddressTab({
					parent : this
				});
				this.children.push(this.tab);
			}
			//待选区域组件构建
			if (!this.selectArea){
				this.selectArea = new AddressSelectArea({
					parent : this
				});
				this.children.push(this.selectArea);
			}
			//已选区域组件构建
			if (!this.selectedBox){
				this.selectedBox = new AddressSelectedArea({
					parent : this
				});
				this.children.push(this.selectedBox);
			}
			//底部按钮栏组件构建
			if(!this.buttons){
				this.buttons = new AddressButtons({
					parent :this,
					settingPersonal : true //是否显示设置个人地址本
				});
				this.children.push(this.buttons);
			}
		},
		
		doLayout : function(obj){
			var self = this;
			this.elem.append(this.element);
			if (this.params.enableDingCss == "true") {
				this.elem.addClass("lui-ding-address-content");
			}
			this.element.append($(obj));
			this.tabdom = this.element.find('[data-lui-mark="address.tabs"]');
			this.selectAreadom = this.element.find('[data-lui-mark="address.selectarea"]');
			this.selectedBoxdom = this.element.find('[data-lui-mark="address.selectedbox"]');
			this.butoons =  this.element.find('[data-lui-mark="address.buttons"]');
			for (var i = 0; i < this.children.length; i++) {
				if (this.children[i] instanceof AddressTab) {
					this.children[i].setParentNode(this.tabdom);
				}
				if(this.children[i] instanceof AddressSelectArea){
					this.children[i].setParentNode(this.selectAreadom);
				}
				if(this.children[i] instanceof AddressSelectedArea){
					this.children[i].setParentNode(this.selectedBoxdom);
				}
				if(this.children[i] instanceof AddressButtons){
					this.children[i].setParentNode(this.butoons);
				}
				if (this.children[i].draw) {
					this.children[i].draw();
				}
			}
			setTimeout(function(){
				self.tab.selectTab(0);
				self.elem[0].focus();
			},100);//TODO BUG
			
			this.bindEvent();
			
			// 调整地址本高度，原始高度496
			var size = dialog.getSizeForAddress();
			if(size && size.height && (size.height - 60) > 496) {
				this.element.find('[data-lui-mark="address.content"]').css("height", (size.height - 60));
			}
		},
		
		bindEvent : function(){
			var self = this;
			//键盘回车事件:控制搜索
			//键盘上移、下移事件:控制已选区域的展开与收缩
			$(document).keydown(function(e){
				//处理Enter
				if(e.keyCode==13 && self.selectedPanel != null){
					self.selectedPanel.handleEnterKeyDown();
				}
				//处理上移事件
				if(e && e.keyCode == 38){
					self.selectArea.handleToggle('up');
				}
				//处理下移事件
				if(e && e.keyCode == 40){
					self.selectArea.handleToggle('down');
				}
			});
		}
		
	});
	
	//选择页签组件
	var AddressTab =  base.Container.extend({
		initProps : function($super, cfg){
			this.parent = cfg.parent;
			this.addresscfg = this.parent.addresscfg;
			this.startup();
		},
		startup : function(){
			if (!this.layout) {
				this.layout = new layout.Template({
					src : require.resolve('./tmpl/address-tab.html#'),
					parent : this
				});
				this.layout.startup();
				this.children.push(this.layout);
			}
		},
		doLayout : function(obj) {
			this.element.html($(obj));
			var tabs = this.element.find("[data-lui-tabid]");
			tabs.bind('click',function(e){
				var __tab  = $(this),
					__tabid = __tab.attr('data-lui-tabid');
				tabs.removeClass('active');
				__tab.addClass('active');
				topic.publish( constant.event['ADDRESS_TAB_SELECTED'] ,{
					id : __tabid
				});
			});
		},
		selectTab : function(index){
			var tabs = this.element.find("[data-lui-tabid]");
			if(tabs.eq(index)){
				var __tab  = tabs.eq(index),
					__tabid = __tab.attr('data-lui-tabid');
				tabs.removeClass('active');
				__tab.addClass('active');
				topic.publish( constant.event['ADDRESS_TAB_SELECTED'],{
					id : __tabid
				});
			}
		}
	});
	
	//待选区域组件
	var AddressSelectArea = base.Container.extend({
		initProps : function($super, cfg){
			this.parent = cfg.parent;
			this.addresscfg = this.parent.addresscfg;
			this.params = this.parent.params;
			this.optionType = this.parent.optionType;
			this.panels = {};
			this.startup();
		},
		startup:function(){
			if (!this.layout) {
				this.layout = new layout.Template({
					src : require.resolve('./tmpl/address-selectarea.html#'),
					parent : this
				});
				this.layout.startup();
				this.children.push(this.layout);
			}
			topic.subscribe( constant.event['ADDRESS_TAB_SELECTED'],this.selectChanged,this);
			topic.subscribe( constant.event['ADDRESS_SEARCH_FINISH'] ,this.handleToggleDown,this);
			topic.subscribe( constant.event['ADDRESS_NAV_SELECTED'],this.handleToggleDown,this);
		},
		doLayout:function(obj){
			this.element.html($(obj));
			var updown = this.element.find('[data-lui-mark="lui-address-btn-updown"]'),
				self = this;
			updown.click(function(){
				self.handleToggle();
			});
			updown.attr('title',lang['address.expand']);
		},
		selectChanged:function(evt){
			var panelId = evt.id;
			this.hiddenAllPanel();
			if(!this.panels[panelId]){
				var __cfg = this.searchCfg(panelId);
				if(this.instanceofAbstractAddressPanel(__cfg.panel)){
					loadingDialog = dialog.loading();
					var __panel = new __cfg.panel({
						ancestor : this.parent,
						parent : this,
						panelId : panelId
					});
					__panel.draw();
					this.panels[panelId] =__panel;
				}
			}else{	
				this.panels[panelId].show();
			}
			// 关闭loading遮罩层
			if(loadingDialog){
				loadingDialog.hide();
			}
			isHandleFinished = true;
			
			this.parent.selectedPanel = this.panels[panelId];
			this.handleToggle('down');
		},
		hiddenAllPanel : function(){
			for(var key in this.panels){
				this.panels[key].hide();
			}
		},
		searchCfg : function(id){
			var __cfg = null;
			for(var i=0;i<this.addresscfg.length;i++){
				__cfg = this.addresscfg[i];
				if(__cfg.id == id){
					return __cfg;
				}
			}
			return null;
		},
		instanceofAbstractAddressPanel:function(panel){
			if(panel){
				var tmp = panel.superclass;
				while(tmp){
					if(tmp == AddressPanel.AbstractAddressPanel){
						return true;
					}
					tmp = tmp.superclass;
				}
			}
			return false;
		},
		handleToggle : function(direction){
			var	selectAreadom = this.element.find('.lui_address_select_area'),//备选区域
				selectedBoxdom = this.parent.selectedBoxdom,//已选区域
				selectedPaneldom = this.parent.selectedPanel.element,//当前激活的备选区域面板
				updown = this.element.find('[data-lui-mark="lui-address-btn-updown"]'),
				selectedNav = selectedPaneldom.find('[data-lui-mark="lui-address-panel-nav"]');//当前激活的备选区域面板的头部
			if(direction){//已经处于想要切换的状态,不做切换
				var currentDirection = $(updown).is(".toggle") ? 'up' : 'down';
				if( currentDirection == direction){
					return;
				}
			}else{
				direction = $(updown).is(".toggle") ? 'down' : 'up';
			}
			
			// 调整地址本高度，原始高度322
			if(!this.selectAreaHeight) {
				this.selectAreaHeight = selectAreadom.height();
				if(this.selectAreaHeight < 322)
					this.selectAreaHeight = 322;
			}
			if (direction == 'down') {
				$(updown).removeClass("toggle");
				$(updown).attr('title',lang['address.expand']);
				selectAreadom.animate({
					height : this.selectAreaHeight + 'px'
				},100,function(){
					selectAreadom.css('overflow','');
					selectedBoxdom.removeClass('up');
				});
			}else{
				$(updown).addClass("toggle");
				$(updown).attr('title',lang['address.collpase']);
				selectAreadom.animate({
					height : selectedNav.outerHeight()
				},100,function(){
					selectAreadom.css('overflow','hidden');
					selectedBoxdom.addClass('up');
				});
				
				var size = dialog.getSizeForAddress();
				var _selectedBoxdomHeight;
				var _selectedbox = selectedBoxdom.find(".selectedbox-for-common");
				if(_selectedbox.length > 0) {
					_selectedBoxdomHeight = size.height - 200;
					_selectedbox.css("height", _selectedBoxdomHeight);
				}
				_selectedbox = selectedBoxdom.find(".selectedbox-for-search");
				if(_selectedbox.length > 0) {
					_selectedBoxdomHeight = size.height - 230;
					_selectedbox.css("height", _selectedBoxdomHeight);
				}
				selectedBoxdom.css("height", _selectedBoxdomHeight);
			}
		},
		handleToggleDown : function(){
			this.handleToggle('down');
		}
		
	});
	
	
	exports.Address = Address;
	
});