define([ "dojo/_base/declare", "dojo/_base/array", "dijit/_WidgetBase", "dojo/dom-construct",
		"dijit/_Contained", "dijit/_Container", "dojo/topic", "dojo/on", "dojo/dom-class"], 
		function(declare, array, _WidgetBase, domConstruct, Contained, Container, topic, on, domClass) {
	var claz = declare("mui.property.FilterBase", [ _WidgetBase, Contained,
			Container ], {
		
		EVENT_VALUES: '/mui/property/filter/values',
		
		SET_EVENT: '/mui/property/filter/value/set',
		GET_EVENT: '/mui/property/filter/value/get',
		
		_props: {},
		
		baseClass: 'filterItem',
		expandClass: 'expand',
		selectedClass: 'selected',
		overflowClass: 'overflow',
		
		key: "__default__",
		
		// 标题
		subject: null,
		// 键值
		name: null,

		// 多选
		multi: false,
		// 当前值
		value: [],
		// 可选项
		options: [],
		
		expand: false,
		expandLimit: 6,
		
		buildRendering: function() {
			this.inherited(arguments);
			
			var _props = this._props || {};
			for(var key in _props) {
				this[key] = _props[key];
			}
			
			this.titleNode = domConstruct.create('h4', {
				className: 'filterItemTitle',
				innerHTML: this.subject
			}, this.domNode, 'first');
			this.contentNode = domConstruct.create('ul', null, this.domNode, 'last');
			
			this.subscribe(this.EVENT_VALUES, '_onChange');
		},
		
		getValue: function(cb) {
			var ctx = this;
			topic.publish(this.GET_EVENT , this, {
				name: this.name,
				cb: cb
			});
		},
		
		getItemsType: function() {
			var children = this.getChildren()
			var options = this.options;
			if(children && children.length > 0) {
				return 'children';
			} else if(options && options.length > 0) {
				return 'options';
			} else {
				return '';
			}
		},
		
		getItemsCount: function() {
			var itemsType = this.getItemsType();
			if(itemsType == 'children') {
				return this.getChildren().length;
			} else if(itemsType == 'options') {
				return this.options.length;
			} else {
				return 0;
			}
			
		},

		startup: function() {
			this.inherited(arguments);

			if(!this.staticDataUrl)
				this.buildExpand();
		},
		
		buildExpand : function (){
			// 获取当前值
			var ctx = this;
			this.getValue(function(value) {
				ctx.value = value || [];
			})

			// 动态判断是否支持展开收缩
			if(this.getItemsCount() > this.expandLimit) {
				domConstruct.create('div', {
					className: 'filterItemExpander',
					innerHTML: '<i class="fontmuis muis-to-left"/>'
				}, this.titleNode, 'last');
				
				if(ctx.expand){
					domClass.add(ctx.domNode, ctx.expandClass);
				};
				on(this.titleNode, "click", function() {
					if(ctx.expand) {
						domClass.remove(ctx.domNode, ctx.expandClass);
						ctx.expand = false;
					} else {
						domClass.add(ctx.domNode, ctx.expandClass);
						ctx.expand = true;
					}
				});
			}
		},
		
		_onChange: function(obj, evt) {
			if(!evt || !evt.values) {
				return
			}
			this.value = [].concat(evt.values[this.name]) || [];
		}
		
	});
	return claz;
});