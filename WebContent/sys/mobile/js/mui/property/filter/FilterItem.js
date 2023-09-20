define([ "dojo/_base/declare", "dijit/_WidgetBase", "dojo/dom-construct",
		"dojo/topic", "dojo/dom-class", 'dojo/_base/array', 'mui/ChannelMixin' ], function(declare, _WidgetBase,
		domConstruct, topic, domClass, array, ChannelMixin) {
	var claz = declare("mui.property.FilterItem", [ _WidgetBase, ChannelMixin ], {

		EVENT_VALUES: '/mui/property/filter/values',
		
		SET_EVENT: '/mui/property/filter/value/set',
		GET_EVENT: '/mui/property/filter/value/get',
		
		name: null,
		// 值
		value: null,
		// 表达式；默认情况下query字符串为name=value，如果expr存在则使用此值作用query字符串
		expr: null,

		tagName: 'li',
		
		selectedClass: 'selected',
		
		resolveValue: null,
		
		compareValue: null,

		buildRendering: function() {
			this.inherited(arguments);
			if(!this.key && this.getParent()){
				this.key = this.getParent().key;
			}
			this.domNode = domConstruct.create(this.tagName, {});
			this.subscribe(this.EVENT_VALUES, '_onChange')
			this.buildItem();
		},

		startup: function() {
			this.inherited(arguments);
			var ctx = this;
			topic.publish('/mui/property/filter/values/get', this, {
				cb: function(values) {
					ctx._onChange(ctx, {
						values: values
					});
				}
			});
		},

		buildItem: function() {
			this.itemNode = domConstruct.create('a', {
				href: 'javascript:;',
				innerHTML: this.name
			}, this.domNode);
			this.connect(this.domNode, 'click', '_onClick');
		},

		unSelected: function() {
			domClass.remove(this.domNode, this.selectedClass);
		},

		selected: function() {
			domClass.add(this.domNode, this.selectedClass);
		},
		
		_onChange: function(obj, evt) {
			if(!this.isSameChannel(obj.key)){
				return;
			}
			
			if(!evt || !evt.values) {
				return
			}
			var parent = this.getParent();
			var value = evt.values[parent.name] || [];
			
			var selected = false;
			if(this.compareValue) {
				selected = this.compareValue(this.value, value);
			} else {
				if(value && value.expr){
					selected = value.expr == this.expr
				}else{
					if (typeof(value) == 'string') {
						selected = (value == this.value);
					} else {
						selected = array.indexOf(value, this.value) >= 0;
					}
				}
			}
			if(selected) {
				this.selected();
			} else {
				this.unSelected();
			}
			
		},
		
		_onClick: function(evt) {
			var ctx = this;
			var parent = this.getParent();
			
			var name = parent.name;
			var multi = parent.multi;
			var value = parent.value || [];
			
			var target = evt.currentTarget;
			var isSelected = domClass.contains(target, this.selectedClass);
			
			var newValue;
			if(this.expr){
				// 存在expr表达式时newValue为对象,传递expr属性（TODO:带表达式的筛选项暂不支持多选）
				newValue = { expr: this.expr };
			}else{
				// 不存在expr表达式时为数组,传递选项值
				newValue = [];
				if(this.resolveValue) {
					newValue = this.resolveValue(isSelected, ctx.value);
				} else {
					var resValue = isSelected ? [] : [ctx.value];
					var newValue = multi ? array.filter(value, function(v) {
						return v !== ctx.value;
					}).concat(resValue) : resValue;
				}
			}
			topic.publish(this.SET_EVENT, this, {
				name: name,
				value: newValue
			});

		}

	});
	return claz;
});