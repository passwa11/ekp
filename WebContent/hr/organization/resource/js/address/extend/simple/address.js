define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var Address = require('lui/address/address').Address;
	
	var cfgfunc = require('lui/address/extend/simple/addressCfg');
	
	var AddressSimple = Address.extend({
		
		customCfg : function() {
			return cfgfunc();
		}
		
	});
	
	exports.Address = AddressSimple;
	
});