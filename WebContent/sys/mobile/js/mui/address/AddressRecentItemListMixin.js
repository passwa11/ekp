define([ 
	"dojo/_base/declare", 
	"./AddressRecentItemMixin" 
	], function(declare, AddressRecentItemMixin) {
	return declare("mui.address.AddressRecentItemListMixin", null, {
		// 数据请求URL
		dataUrl : '/sys/organization/sys_org_element/sysOrgElement.do?method=getRecentContact&orgType=!{selType}&selectCount=10',

		itemRenderer : AddressRecentItemMixin
	});
});