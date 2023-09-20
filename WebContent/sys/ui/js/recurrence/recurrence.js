/**
 * 日程重复组件
 */
define(function(require, exports, module) {
	
	Com_IncludeFile('calendar.js');
	
	var base = require("lui/base");
	var lang = require('lang!sys-ui');
	var RecurrenceCustom = require('./recurrenceCustom').RecurrenceCustom;
	var DEFAULT_VALUE = "FREQ=DAILY;INTERVAL=1";

	var Recurrence = base.Component.extend({
		
		initProps : function($super,props){
			props = props || {};
			$super(props);
			//重复类型选择区域
			this.typeContainer = props.typeContainer;
			//自定义重复选择区
			this.customContainer = props.customContainer;
			// 是否重复
			this.isRecurrence = false; 
			//重复字段,为空在为fdRecurrenceStr
			this.property = props.property || 'fdRecurrenceStr';
			//重复字段值
			this.value = props.value || '';
			//是否强制开启
			this.isOn = props.isOn || '';
			//基准日期,按周循环的`重复时间`需要基于某一天进行计算
			this.baseDate = (typeof(props.baseDate) == 'string' ? new Date(props.baseDate) : props.baseDate) || new Date();
		},
		
		//根据`this.value`重新设置props
		resetProps : function(){
			if(!this.value)
				return;
			this.isRecurrence = true;
		},
		
		draw : function($super){
			if(this.isDrawed)
				return;
			this._drawBase();
			if(this.isOn != 'true'){
				this._drawTypeContainer();
			}
			if(this.isRecurrence){
				this._drawCustomContainer();
			}
			$super();
		},
		
		startup : function(){
			if (this.isStartup) {
				return;
			}
			this.on('recurrence.buildInType.change',this.onTypeChange,this);
			this.isStartup = true;
		},
		
		onTypeChange : function(isRecurrence){
			this.isRecurrence = isRecurrence;
			//清空自定义重复区
			this.$customContainer && this.$customContainer.hide();
			if(this.isRecurrence){
				this.clearValue();
				//如果新的选项是自定义,重新渲染自定义区域
				this._drawCustomContainer();
				this.$customContainer.show();
			}else{
				//重置重复字段的值,CUSTOM的setValue由recurrenceCustom触发（因为recurrenceCustom组件的渲染是异步的）
				this.setValue();
			}
		},
		
		_drawBase : function(){
			//重复类型选择区域渲染
			this.$typeContainer = $(this.typeContainer);
			if(!this.$typeContainer || this.$typeContainer.length == 0){
				this.$typeContainer = $('<div/>').appendTo(this.element);
			}
			//自定义重复选择区渲染
			this.$customContainer = $(this.customContainer);
			if(!this.$customContainer || this.$customContainer.length == 0){
				this.$customContainer = $('<div/>').after(this.$typeContainer);
			}
			//字段隐藏区
			this.$propertyInput = $('<input/>').attr('type','hidden').attr('name',this.property).appendTo(this.element);
			if(this.isOn == "true"){
				if(this.value == ""){
					this.value = DEFAULT_VALUE;
				}
			}
			if(this.value){
				this.$propertyInput.val(this.value);
				this.resetProps();
			}
		},
		
		_drawTypeContainer : function(){
			var self = this,
				$checkboxNode = $('<input type="checkbox"/>').attr('data-role','isRecurrence');
			if(this.isRecurrence){
				$checkboxNode.prop('checked',true);
			}
			$checkboxNode.on('click',function(){
				self.emit('recurrence.buildInType.change',$(this).prop('checked'));
			});
			$checkboxNode.appendTo(this.$typeContainer);
			$checkboxNode.after($('<span>'+ lang['ui.recurrence.isRecurrence'] +'</span>'));
			return $checkboxNode;
		},
		
		_drawCustomContainer : function(){
			if(!this.recurrenceCustom){
				this.recurrenceCustom = new RecurrenceCustom({
					parent : this,
					owner : this
				});
				this.recurrenceCustom.startup();
				this.recurrenceCustom.draw();
				this.recurrenceCustom.element.appendTo(this.$customContainer);
			}
		},
		
		clearValue : function(){
			this.value = '';
			this.$propertyInput.val(this.value);
		},
		
		setValue : function(){
			var isRecurrence = this.isRecurrence;
			if(!this.isRecurrence){
				this.value = '';
			}else{
				this.value = this.recurrenceCustom.getValue();
			}
			if(window.console){
				console.log('setValue:' + this.value);
			}
			this.$propertyInput.val(this.value);
		},
		
		getValue : function(format){
			return this.value;
		},
		
		setBaseDate : function(baseDate){
			this.baseDate = (typeof(baseDate) == 'string' ? new Date(baseDate) : baseDate) || new Date() ;
			this.setValue();
		}
		
	});
	
	exports.Recurrence = Recurrence;
	
});