/**
 * 基于新UI的组织架构列表组件v1.0
 */
define(function(require, exports, module) {
	
	var base = require('lui/base');
	var layout = require('lui/view/layout');
	var topic = require('lui/topic');
	var env = require('lui/util/env');
	var dialog = require('lui/dialog');
	var lang = require('lang!sys-ui');
	var constant = require('lui/address/addressConstant');
	
	var AddressSelectedArea = require('lui/address/component/selectedAreaComponent');//已选区域组件
	var AddressButtons = require('lui/address/component/buttonComponent');//底部按钮栏组件
	
	//暴露一个全局变量
	if(!window.___landrayAddressList___){
		window.___landrayAddressList___ = {
			version : '1.0'	
		};
	}
	
	//备选列表入口
	var AddressList = base.Container.extend({
		
		initProps : function($super, cfg) {
			$super(cfg);
			this.mul = this.config.mulSelect;
			this.elem = $(this.config.elem);
			this.params = this.config;
			this.optionType = this.config.optionType;
			this.addresscfg = require('lui/address/addressListCfg')();
			this.startup();
		},
		
		startup : function(){
			if (!this.layout) {
				this.layout = new layout.Template({
					src : require.resolve('./tmpl/addressList.html#'),
					parent : this
				});
				this.layout.startup();
				this.children.push(this.layout);
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
					parent :this
				});
				this.children.push(this.buttons);
			}
		},
		doLayout : function(obj){
			var self = this;
			this.elem.append(this.element);
			this.element.append($(obj));
			this.selectAreadom = this.element.find('[data-lui-mark="address.selectarea"]');
			this.selectedBoxdom = this.element.find('[data-lui-mark="address.selectedbox"]');
			this.butoons =  this.element.find('[data-lui-mark="address.buttons"]');
			for (var i = 0; i < this.children.length; i++) {
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
			//键盘回车事件:控制搜索
			//键盘上移、下移事件:控制已选区域的展开与收缩
			$(document).keydown(function(e){
				//处理Enter
				if(e.keyCode == 13 && (!!self.selectedPanel || !!self.selectedBox)){
					self.selectArea.panel.handleEnterKeyDown();
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
			
			// 调整地址本高度，原始高度452
			var size = dialog.getSizeForAddress();
			if(size && size.height && (size.height - 60) > 452) {
				this.element.find('[data-lui-mark="address.content"]').css("height", (size.height - 60));
			} else {
				this.element.find('[data-lui-mark="address.content"]').css("height", 452);
			}
		}
		
	});
	
	//待选区域组件
	var AddressSelectArea = base.Container.extend({
		initProps : function($super, cfg){
			this.parent = cfg.parent;
			this.addresscfg = this.parent.addresscfg;
			this.optionType = this.parent.optionType;
			this.params = this.parent.params;
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
			topic.subscribe( constant.event['ADDRESS_SEARCH_FINISH'] ,this.handleToggleDown,this);
		},
		doLayout: function(obj){
			this.element.html($(obj));
			//处理备选列表面板...
			for(var i=0;i<this.addresscfg.length;i++){
				__cfg = this.addresscfg[i];
				var __panel = new __cfg.panel({
					ancestor : this.parent,
					parent : this,
					panelId : __cfg.id
				});
				__panel.draw();
				this.panel =__panel;
			}
			//处理展开折叠..
			var updown = this.element.find('[data-lui-mark="lui-address-btn-updown"]'),
				self = this;
			updown.click(function(){
				self.handleToggle();
			});
			updown.attr('title',lang['address.expand']);
			this.handleToggle('down');
		},
		handleToggle : function(direction){
			var	selectAreadom = this.element.find('.lui_address_select_area'),//备选区域
				selectedBoxdom = this.parent.selectedBoxdom,//已选区域
				selectedPaneldom = this.panel.element,//待选区域面板
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
			selectedBoxdom.css("height", this.selectAreaHeight);
			if (direction == 'down') {
				$(updown).removeClass("toggle");
				$(updown).attr('title',lang['address.expand']);
				selectAreadom.animate({
					height : this.selectAreaHeight + 'px'
				},400,function(){
					selectAreadom.css('overflow','');
					selectedBoxdom.removeClass('up');
				});
			}else{
				$(updown).addClass("toggle");
				$(updown).attr('title',lang['address.collpase']);
				selectAreadom.animate({
					height : selectedNav.outerHeight()
				},400,function(){
					selectAreadom.css('overflow','hidden');
					selectedBoxdom.addClass('up');
				});
			}
		},
		handleToggleDown : function(){
			this.handleToggle('down');
		}
	});
	
	
	exports.AddressList = AddressList;
	
});