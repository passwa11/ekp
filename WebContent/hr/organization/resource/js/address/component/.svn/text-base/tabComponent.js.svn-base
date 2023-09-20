/**
 * 标签组件
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var base = require('lui/base');
	var layout = require('lui/view/layout');
	var topic = require('lui/topic');
	var env = require('lui/util/env');
	var constant = require('lui/address/addressConstant');
	
	//选择页签组件
	var TabComponent =  base.Container.extend({
		initProps : function($super, cfg){
			this.parent = cfg.parent;
			this.addresscfg = this.parent.addresscfg;
			this.startup();
		},
		startup : function(){
			if (!this.layout) {
				this.layout = new layout.Template({
					src : require.resolve('../tmpl/address-tab.html#'),
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
	
	module.exports = TabComponent;
	
});