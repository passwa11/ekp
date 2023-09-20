define([ "dojo/_base/declare", "dojo/_base/lang", "dojo/_base/array", "dojo/request",
         "dojo/dom-construct", "dojo/dom-class", "mui/util", "./FilterItem"], 
		function(declare, lang, array, request, domConstruct, domClass, util, FilterItem) {
	return declare("mui.property.FilterStaticMixin", null, {
		
		buildRendering: function() {
			this.inherited(arguments);
			
		},

		/**
		 * 静态数据源url
		 *
		 * 返回数据格式：
		 * {
		 *   options: [
		 *   	{
		 *     		name : '草稿',
		 *      	value : '10'
		 *   	},
		 *   	{
		 *     		name : '发布',
		 *      	value : '30'
		 *   	}
		 *   ]
		 * }
		 */
		staticDataUrl : null,
		
		buildStaticRadio : function (){
			
			if(this.staticDataUrl) {
				this.renderStatic();
			} else {
				this.renderItems();
			}
			
		},
		
		renderStatic : function() {
			
			var self = this;
			
			var url = util.formatUrl(this.staticDataUrl);
			
			request.get(url, {
				handleAs : 'json',
				headers : {'accept': 'application/json'}
			}).response.then(function(result) {
				var data = result.data;
				if(data && data != '' && data.options)
					self.drawStatic(data.options);
			});
			
		},
		
		drawStatic : function(options) {
            
			this.options = options;
			
			// 支持展开
			this.buildExpand();

			array.forEach(options, function(option, index) {
				var item = new FilterItem(lang.mixin({ 
					key: this.key 
				}, option));
				
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
			
		}
		
	});
});