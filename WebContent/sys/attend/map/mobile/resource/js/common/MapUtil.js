define(
		[ "dojo/_base/declare", "dojo/_base/window","mui/coordtransform" ],
		function(declare, win,coordtransform) {
			var claz = declare(
					"map.mui.MapUtil",
					null,
					{
						//百度地图默认key
						bMapKey:'cnG6G1wW70lQ36H693uVOyOXiwvMaph3',
						
//////////////////////////////////////baidu map start/////////////////////////////////
						_OD : function (a, b, c) {
						    while (a > c) a -= c - b;
						    while (a < b) a += c - b;
						    return a;
						},
						_SD : function (a, b, c) {
						    b != null && (a = Math.max(a, b));
						    c != null && (a = Math.min(a, c));
						    return a;
						},
						//坐标如:gcj02:22.540191;114.019549
						getDistance : function (latLng1,latLng2) {
							var type1 = this.getCoordType(latLng1);
							var type2 = this.getCoordType(latLng2);
							var coordArr1 = latLng1.replace('bd09:','').replace('gcj02:','').split(/[,;]/);
							var coordArr2 = latLng2.replace('bd09:','').replace('gcj02:','').split(/[,;]/);
							
							var point1 = {lat:coordArr1[0],lng:coordArr1[1]};
							var point2 = {lat:coordArr2[0],lng:coordArr2[1]};
							if(type1!=type2){
								if(type1==5){
									var tempCoord = coordtransform.gcj02tobd09(point1.lng,point1.lat);
									point1 = {lat:tempCoord[1],lng:tempCoord[0]};
								}
								if(type2==5){
									var tempCoord = coordtransform.gcj02tobd09(point2.lng,point2.lat);
									point2 = {lat:tempCoord[1],lng:tempCoord[0]};
								}
							}
							
						    var a = Math.PI * this._OD(point1.lng, -180, 180) / 180;
						    var b = Math.PI * this._OD(point2.lng, -180, 180) / 180;
						    var c = Math.PI * this._SD(point1.lat, -74, 74) / 180;
						    var d = Math.PI * this._SD(point2.lat, -74, 74) / 180;
						    return 6370996.81 * Math.acos(Math.sin(c) * Math.sin(d) + Math.cos(c) * Math.cos(d) * Math.cos(b-a));
						},
						getCoordType : function(coord){
							if(coord.indexOf('bd09:') > -1){
								return 3;
							}
							if(coord.indexOf('gcj02:') > -1){
								return 5;
							}
							return 3;
						},
						formatCoord : function(point,coordType){
							var prefix = "bd09:";
							if(coordType==5){
								prefix = "gcj02:";
							}
							var currentLatLng = prefix +(point.lat+"," + point.lng);
							return currentLatLng;
						},
						getCoord : function(coord){
							var latLng = coord.replace('bd09:','').replace('gcj02:','').split(/[,;]/);
							return {
								lat:latLng[0],
								lng:latLng[1]
							}
						}
//////////////////////////////////////baidu map end/////////////////////////////////						
					});
			return new claz();
		});