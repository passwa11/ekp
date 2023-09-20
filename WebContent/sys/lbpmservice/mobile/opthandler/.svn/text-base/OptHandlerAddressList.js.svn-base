define( [ 
	"dojo/_base/declare", 
    "mui/address/AddressList",
    "dojo/_base/lang", 
    "mui/util",
	], function(declare, AddressList, lang, util) {
	return declare("mui.sys.lbpmservice.OptHandlerAddressList", [ AddressList ], {

		
		//将处理人单独构建，不放在URL后请求
		optHandlerIds:"",
		
		buildQuery:function(){
			var params = this.inherited(arguments);
			return lang.mixin(params , {
				exceptValue : this.exceptValue,
				optHandlerIds:this.optHandlerIds
			});
		}
		
	});
});