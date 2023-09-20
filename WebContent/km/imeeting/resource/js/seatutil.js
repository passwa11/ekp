define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	
	function resolveData(data) {
		if(!data || data.length < 1) {
			return [];
		}
		
		var t = {};
		$.each(data || [], function(_, d) {
			var _d = t[d.x +"_"+ d.y];
			if(_d){
				if(_d.x == d.x && _d.y == d.y ) {
					if(d.clazz.type == _d.clazz.type){
						t[d.x +"_"+ d.y] = null;
					}else{
						t[d.x +"_"+ d.y] = d;
					}
				}
			}else {
				t[d.x +"_"+ d.y] = d;
			}
		});
		
		var res = [];
		var index = 1;
		for(var k in t) {
			var _t = t[k];
			
			if(_t) {
				if(_t.clazz.type == "1" ||　_t.clazz.type == "0"){
					_t['number'] = index;
					index++;
				}
				res.push(t[k]);
			}
		}
		return res;
	}
	
	exports.resolveData = resolveData;
	
});