define(['dojo/_base/declare', 'dojo/dom-construct'], function(declare, domConstruct) {
	var item = declare("mui.list.item.TextItem.topMixin", null, {

		/**
		 * 本mixin应该被移动端所有展现样式mixin所继承（如左图右文，右图左文，简单列表展现等）
		 * 用于对数据进行转换封装，并对数据源格式进行统一
		 */
		topTagClass : '',
		
		buildTag : function (domNode) {

			this.inherited(arguments);

			if(this.isTop && domNode) {
				
				this.formatTopData();
				
				domConstruct.create('span', {
					className : this.tagClass + this.topTagClass,
					innerHTML : this.topTagText
				}, domNode);
				
			}
			
		},
		
		formatTopData : function () {
			
			if(this.isTop) {
				
				this.topTagText = '置顶';
				
				this.topTagClass = ' muiTopTag';

			} 
		}
	});
	return item;
});