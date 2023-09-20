/**
 * 自定义重复信息组件
 */
define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var base = require('lui/base');
	var lang = require('lang!sys-ui');
	var layout = require('lui/view/layout');
	
	var RecurrenceCustom = base.Container.extend({
		
		initProps : function($super,props){
			props = props || {};
			$super(props);
			this.owner = props.owner;
			this.curRType = this.getCurRTypeByValue(this.owner.value);
			this.rTypeComponents = {};//缓存自定义重复类型组件
		},
		
		getCurRTypeByValue : function(value){
			var curRType = 'DAILY';
			var reg = /FREQ=((DAILY|WEEKLY|MONTHLY|YEARLY));/;
			if(reg.test(value)){
				curRType = RegExp.$1;
			}
			return curRType;
		},
		
		startup : function(){
			if (this.isStartup) {
				return;
			}
			this.isStartup = true;
			if (!this.layout) {
				this.layout = new layout.Template({
					src : require.resolve('./tmpl/recurrenceCustom.jsp#'),
					parent : this
				})
				this.layout.startup();
				this.children.push(this.layout);
			}
			this.on('recurrence.type.change',this.onTypeChange,this);
		},
		doLayout : function(obj){
			var self = this;
			this.element.append($(obj));
			var $selectNode = this.$selectNode = $('.rTypeSelect',this.element),
				$contentNode = this.$contentNode = $('.rTypeContent',this.element);
			$selectNode && $selectNode.on('change',function(){
				var value = $(this).val();
				self.emit('recurrence.type.change',value);
			});
			$selectNode.val(self.curRType);
			this.__render();
		},
		onTypeChange : function(value){
			this.curRType = value;
			this.__render();
		},
		__render : function(){
			var self = this,
				curRType = this.curRType,
				curComponent = this.rTypeComponents[curRType],
				defer = $.Deferred();
			if(!curComponent){
				var componentURI = 'lui/recurrence/rType/' + curRType.toLocaleLowerCase();
				seajs.use([componentURI],function(Component){
					curComponent = new Component({
						parent : self,
						owner : self.parent
					});
					self.rTypeComponents[curRType] = curComponent;
					curComponent.startup();
					curComponent.draw();
					defer.resolve();
				});
			}else{
				defer.resolve();
			}
			defer.then($.proxy(function(){
				var anotherDefer = $.Deferred();
				if(curComponent.isRender){
					curComponent.reset();
					anotherDefer.resolve();
					setTimeout($.proxy(function(){
						 this.owner.setValue(); 
					},this),1);
				}else{
					curComponent.on('recurrence.rType.layout.done',function(){
						anotherDefer.resolve();
					});
				}
				anotherDefer.then($.proxy(function(){
					this.$contentNode.children().detach();
					this.$contentNode.append(curComponent.element);
				},this));
			},this));
		},
		
		getValue : function(format){
			var curRType = this.curRType,
				curComponent = this.rTypeComponents[curRType];
			if(curComponent){
				if(window.console){
					//console.log('customValue:' + ['FREQ='+curRType ,curComponent.getValue()].join(';'));
				}
				return ['FREQ='+curRType ,curComponent.getValue()].join(';');
			}
			return '';
		}
		
	});
	
	exports.RecurrenceCustom = RecurrenceCustom;
	
	
});