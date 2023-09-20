/**
 * 待选区域组件
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var base = require('lui/base');
	var layout = require('lui/view/layout');
	var topic = require('lui/topic');
	var dialog = require('lui/dialog');
	var lang = require('lang!sys-ui');
	var constant = require('lui/address/addressConstant');
	
	var AddressPanel = require('lui/address/addressPanel');
	
	var SelectAreaComponent = base.Container.extend({
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
					src : require.resolve('../tmpl/address-selectarea.html#'),
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
	
	module.exports = SelectAreaComponent;
	
});