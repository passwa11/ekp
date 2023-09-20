define(['dojo/_base/declare', 'dojo/dom-construct'], function(declare, domConstruct) {
	var item = declare("mui.list.item.TextItem.introduceMixin", null, {

		/**
		 * 本mixin应该被移动端所有展现样式mixin所继承（如左图右文，右图左文，简单列表展现等）
		 * 用于对数据进行转换封装，并对数据源格式进行统一
		 */
		introduceTagClass : '',

		buildTag : function (domNode) {

			this.inherited(arguments);
			
			if(this.isIntroduce && domNode) {
				
				this.formatIntroduceData();
				
				domConstruct.create('span', {
					className : this.tagClass + this.introduceTagClass,
					innerHTML : this.introduceTagText
				}, domNode);
				
			}
			
		},
		
		formatIntroduceData : function () {
			
			if(this.isIntroduce) {
			
				this.introduceTagText = '精华';
				
				this.introduceTagClass = ' muiIntroduceTag';
				
			} 
		}
	});
	return item;
});