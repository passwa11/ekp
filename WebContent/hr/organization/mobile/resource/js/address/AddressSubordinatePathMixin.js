define([ 
	"dojo/_base/declare"
	],function(declare) {
	return declare("mui.address.AddressSubordinatePathMixin", [ ],{
		
		// 获取详细信息地址
		detailUrl : '/sys/organization/mobile/address.do?method=subordinatePath&fdId=!{curId}',
	
	});
});