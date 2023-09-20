define(function(require, exports, module) {
	
	var base = require('lui/base');
	var layout = require('lui/view/layout');
	var strategies = require('./strategy').strategies;
	
	var AbstractRType = base.Container.extend({
		
		rType : null,
		
		initProps : function($super,props){
			props = props || {};
			$super(props);
			this.strategies = [];
			this.owner = props.owner;
			this.params = {};
		},
		
		addStrategy : function(name,options){
			var Strategy = strategies[name];
			this.strategies.push(new Strategy({
				owner : this.owner,
				tmpParams : options || {}
			}));
		},
		
		startup : function(){
			if (this.isStartup) {
				return;
			}
			this.isStartup = true;
			var $table = $('<table width="100%" class="tb_simple" />');
			var $container = $('<tbody/>').appendTo($table);
			var all = [];
			for(var i = 0; i < this.strategies.length; i++){
				var strategy = this.strategies[i];
				all.push(strategy.element());
			}
			this.promiseAll(all).then($.proxy(function(nodes){
				for(var i = 0; i < nodes.length; i++){
					$container.append(nodes[i]);
				}
				$table.appendTo(this.element);
				setTimeout($.proxy(function(){
					this.afterRender();
					this.emit('recurrence.rType.layout.done');
				},this),1)
				
			},this));
		},
		
		promiseAll : function(array){
			var defer = $.Deferred(),
				result = [],
				completeCount = 0;
			for(var i = 0; i < array.length; i++){
				array[i].then((function(index){
					return function(node){
						completeCount++;
						result[index] = node;
						if(completeCount == array.length){
							defer.resolve(result);
						}
					}
				})(i));
			}
			return defer.promise();
		},
		
		afterRender : function(){
			var self = this;
			this.isRender = true;
			this.setValue();
			$('input,select',this.element).on('change',function(){
				self.owner.setValue();
			});
			//back hack
			$('.endTypeSelectDateContainer',this.element).on('click',function(){
				var name = $(this).attr('data-input-name');
				window.selectDate(name,null,function(){
					self.owner.setValue();
				})
			});
			this.owner.setValue();
		},
		
		setValue : function(){
			var value = this.owner.value,
				typeReg = new RegExp('FREQ=' + this.rType);
			if(!typeReg.test(value)){
				return;
			}
			for(var i = 0; i < this.strategies.length; i++){
				var strategy = this.strategies[i];
				strategy.setValue(value);
			}
		},
		
		//获取重复信息
		getValue : function(){
			var recurrenceObj = {};
			for(var i = 0; i < this.strategies.length; i++){
				var strategy = this.strategies[i];
				recurrenceObj = $.extend(recurrenceObj,strategy.getValue());
			}
			return this.formatValue(recurrenceObj);
		},
		
		formatValue : function(recurrenceObj){
			var recurrenceArray = [];
			for(var key in recurrenceObj){
				recurrenceArray.push(key.toLocaleUpperCase() + '=' + recurrenceObj[key].toLocaleUpperCase());
			}
			return recurrenceArray.join(';');
		},
		
		//重置组件
		reset : function(){
			for(var i = 0; i < this.strategies.length; i++){
				var strategy = this.strategies[i];
				strategy.reset();
			}
			this.emit('recurrence.rType.reset');
		}
		
	});
	
	module.exports = AbstractRType;
	
});