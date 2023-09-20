define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var env = require('lui/util/env');
	
	//百度地图默认key
	exports.bMapKey = 'cnG6G1wW70lQ36H693uVOyOXiwvMaph3';
	
	exports.getCurrentMap = function(callback,error){
		var url = "/sys/attend/map/sysAttendMapConfig.do?method=getCurrentMap";
		$.ajax({
			url: env.fn.formatUrl(url),
			timeout : 10000, //超时时间设置，单位毫秒
			type: 'GET',
			dataType: 'json',
			error: function(err){
				error && error();
			},
			success: function(result){
				callback && callback(result);
			},
			complete : function(XMLHttpRequest,status){
			　　　　if(status=='timeout'){//超时,status还有success,error等值的情况
			　　　　		alert("请求超时！");
			　　　　}
			}

		});	
	};
	//coordType:1 GPS坐标,2 sogou经纬度,3 baidu经纬度,4 mapbar经纬度,5 腾讯、高德坐标
	exports.getCoordType = function(coord){
		if(coord.indexOf('bd09:') > -1){
			return 3;
		}
		if(coord.indexOf('gcj02:') > -1){
			return 5;
		}
		return 3;
	};
	
});