/**
 * “同部门成员”列表Mixin
 */
define([ 
	"dojo/_base/declare", 
	"./AddressItemListMixin"
	], function(declare, AddressItemListMixin) {
	return declare("mui.address.AddressDeptMemberListMixin", [ AddressItemListMixin], {
		
		// 数据请求URL
		dataUrl : '/sys/organization/mobile/address.do?method=deptMemberList',

		//搜索请求地址
		searchUrl : "/sys/organization/mobile/address.do?method=deptMemberList&keyword=!{keyword}"
		
			
	});
});