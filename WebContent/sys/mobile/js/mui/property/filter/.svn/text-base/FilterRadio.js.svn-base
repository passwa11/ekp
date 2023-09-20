define([ "dojo/_base/declare", "dojo/_base/lang", "dojo/dom-construct", "dojo/_base/array", "dojo/topic",
		"dojo/dom-style", "dojo/dom-class", "./FilterBase", "./FilterItem", "./FilterStaticMixin"], function(declare, lang,
		domConstruct, array, topic, domStyle, domClass, FilterBase, FilterItem, FilterStaticMixin) {
	return declare("mui.property.FilterRadio", [ FilterBase, FilterStaticMixin ], {

		buildRendering: function() {
			this.inherited(arguments);
		},
		
		renderItems: function() {
			var itemsType = this.getItemsType();

			if(itemsType == 'children') {
				var children = this.getChildren()
				array.forEach(children, function(item, index) {
					domConstruct.place(item.domNode, this.contentNode);
					// 初始化已选条件
					if(this.value && this.value.expr){
						if(this.value.expr == item.expr){
							domClass.add(item.domNode, this.selectedClass);
						}
					}else{
						if(lang.isString(this.value) && this.value === item.value){
							domClass.add(item.domNode, this.selectedClass);
						}
						if (lang.isArray(this.value) && this.value.indexOf(item.value) >= 0) {
							domClass.add(item.domNode, this.selectedClass);
						}
					}
					if(index >= 6) {
						domClass.add(item.domNode, this.overflowClass);
					}
				}, this);
			} else if(itemsType == 'options') {
				var options = this.options;
				array.forEach(options, function(option, index) {
					var item = new FilterItem(lang.mixin({ key: this.key }, option));
					domConstruct.place(item.domNode, this.contentNode);
					// 初始化已选条件
					if(this.value && this.value.expr){
						if(this.value.expr == item.expr){
							domClass.add(item.domNode, this.selectedClass);
						}
					}else{
						if(lang.isString(this.value) && this.value === item.value){
							domClass.add(item.domNode, this.selectedClass);
						}
						if (lang.isArray(this.value) && this.value.indexOf(item.value) >= 0) {
							domClass.add(item.domNode, this.selectedClass);
						}
					}
					if(index >= 6) {
						domClass.add(item.domNode, this.overflowClass);
					}
				}, this);
			} else {
				// TODO 渲染空数据提示
			}
		},

		startup: function() {
			this.inherited(arguments);
			this.buildStaticRadio();
		}

	});
});