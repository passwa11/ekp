define([ "dojo/_base/declare","dojo/topic", "./CompositeNavItem", ], function(declare,topic,CompositeNavItem) {
	var NavItem = declare('mui.portal.compositeNavBarMixin', null, {
		
		itemRenderer:CompositeNavItem,
		
		// 格式化门户数据那些恶心格式
		_createItemProperties : function(item) {
			return {
				value : item[0],
				text : item[1],
				type : item[2],
				pageUrl : item[3],
				pageUrlOpenType: item[4],
				tabIndex : item.tabIndex,
				forceRefresh : this.forceRefresh
			}
		},
	});

	return NavItem;
});