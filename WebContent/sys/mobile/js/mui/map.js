define(
		[ "dojo/_base/declare", "dojo/_base/window", "dojo/on", "dojo/query","dojo/request/script","mui/coordtransform" ],
		function(declare, win, on, query, script,coordtransform) {
			var claz = declare(
					"mui.map",
					null,
					{
						getMapLocation : function(options,callback,error){
							var mapType = dojoConfig.map.mapType || 'bmap';
							if(mapType=='bmap'){
								this.getBMapLocation(options,callback,error);
							}else if(mapType=='qmap'){
								this.getQMapLocation(options,callback,error);
							}else if(mapType=='amap'){
								this.getAMapLocation(options,callback,error);
							}
						},
						getQMapLocation : function(options,callback,error){
							var key = dojoConfig.map.qMapKey;
							var coordType = options.coordType;//coordType:1 GPS坐标,2 sogou经纬度,3 baidu经纬度,4 mapbar经纬度,5 腾讯、高德坐标
							var coord = options.lat+',' + options.lng;
							var poiRadius = options.poiRadius || 500;
							var poiPageSize = options.poiPageSize || 50;
							var getPoi = options.getPoi==0 ? 0:1;
							var callbackName = "jsonpcallback_" + new Date().getTime();
							
							var apiURL = "https://apis.map.qq.com/ws/geocoder/v1/?callback={jsonpcallback}&output=jsonp&coord_type={coordType}&poi_options=radius={radius};page_size={poiPageSize};page_index=1&get_poi={getPoi}&key={key}&location=" + coord;
							apiURL = apiURL.replace('{coordType}',coordType).replace('{key}',key).replace('{jsonpcallback}',callbackName)
											.replace('{radius}',poiRadius).replace('{poiPageSize}',poiPageSize).replace('{getPoi}',getPoi);
							var option = { handleAs:'json',jsonp:callbackName,timeout:10000 };
							window[callbackName] = function(rtn){
								if(rtn.status==0){
									var _result =rtn.result;
									var datas = {};
									datas.coordType='5';
									var title = _result.address_component.street_number || _result.address_component.street || _result.address_component.district;
									datas.title = title;
									datas.point={lat:_result.location.lat,lng:_result.location.lng};
									datas.address = _result.formatted_addresses.recommend || _result.address;
									
									datas.addressComponent = _result.address_component;
									var _pois = _result.pois;
									if(_pois && _pois.length>0){
										var newPois = [];
										for(var i = 0;i < _pois.length;i++){
											var record = {
												title:_pois[i].title,
												address:_pois[i].address,
												point:{lat:_pois[i].location.lat,lng:_pois[i].location.lng},
												distance:_pois[i]._distance
											};
											newPois.push(record);
										}
										datas.pois = newPois;
									}
									callback && callback(datas);
									//http://apis.map.qq.com/ws/geocoder/v1/?location=22.541246,113.939962&coord_type=1&poi_options=radius=500;page_size=50;page_index=1&get_poi=1&key=I7RBZ-7WR3D-V6V4R-HQGPA-BJYVT-WIBLL
								}else{
									error && error(rtn);
								}
							}
							script.get(apiURL ,option);
						},
						
						getBMapLocation : function(options,callback,error){
							var key = dojoConfig.map.bMapKey || 'cnG6G1wW70lQ36H693uVOyOXiwvMaph3';
							var coordType = options.coordType;//coordType:1 GPS坐标,2 sogou经纬度,3 baidu经纬度,4 mapbar经纬度,5 腾讯、高德坐标
							if(coordType==1){
								coordType = 'wgs84ll';
							}else if(coordType==3){
								coordType = 'bd09ll';
							}else if(coordType==5){
								coordType = 'gcj02ll';
							}
							var coord = options.lat +',' +options.lng;
							var poiRadius = options.poiRadius || 500;
							var poiPageSize = options.poiPageSize || 50;
							var getPoi = options.getPoi==0 ? 0:1;
							var callbackName = "jsonpcallback_" + new Date().getTime();
							//地图版本
							var isNew  = dojoConfig.map.bMapVer=="1" ? true:false;
							
							var apiURL = this.getBMapApiUrl(key,coord,coordType,poiRadius,poiPageSize,getPoi,callbackName,isNew);
							var option = { handleAs:'json',jsonp:callbackName,timeout:10000 };
							window[callbackName] = function(rtn){
								console.log('获取地理坐标:jsonpcallback:' + rtn.status);
								if(rtn.status==0){
									var _result =rtn.result;
									var datas = {};
									datas.coordType='3';
									var street = _result.addressComponent.street || '';
									var street_number = _result.addressComponent.street_number || '';
									var title = (street + street_number) || _result.addressComponent.district;
									datas.title = title;
									datas.point={lat:_result.location.lat,lng:_result.location.lng};
									datas.address = _result.formatted_address;
									datas.addressComponent=_result.addressComponent;
									var _pois = _result.pois;
									if(_pois && _pois.length>0){
										var newPois = [];
										for(var i = 0;i < _pois.length;i++){
											var record = {
												title:_pois[i].name,
												address:_pois[i].addr,
												point:{lat:_pois[i].point.y,lng:_pois[i].point.x},
												distance:_pois[i].distance
											};
											newPois.push(record);
										}
										datas.pois = newPois;
									}
									callback && callback(datas);
								}else{
									error && error(rtn);
								}
							}
							script.get(apiURL ,option);
						},
						
						getBMapApiUrl : function(key,coord,coordType,poiRadius,poiPageSize,getPoi,callbackName,isNew){
							var apiURL = "https://api.map.baidu.com/reverse_geocoding/v3/?callback={jsonpcallback}&output=json&coordtype={coordType}&extensions_poi={getPoi}&radius={radius}&page_size={poiPageSize}&ak={key}&location=" + coord;
							if(!isNew){
								apiURL = "https://api.map.baidu.com/geocoder/v2/?callback={jsonpcallback}&output=json&coordtype={coordType}&pois={getPoi}&radius={radius}&page_size={poiPageSize}&ak={key}&location=" + coord;
							}
							apiURL = apiURL.replace('{coordType}',coordType).replace('{key}',key).replace('{jsonpcallback}',callbackName)
										   .replace('{radius}',poiRadius).replace('{poiPageSize}',poiPageSize).replace('{getPoi}',getPoi);
							return apiURL;
						},
						
						getAMapLocation : function(options,callback,error){
							var key = dojoConfig.map.aMapKey;
							var coordType = options.coordType;//coordType:1 GPS坐标,2 sogou经纬度,3 baidu经纬度,4 mapbar经纬度,5 腾讯、高德坐标
							if(coordType==1){
								coordType = 'gps';
							}else if(coordType==3){
								coordType = 'baidu';
							}else if(coordType==5){
								coordType = 'autonavi';
							}
							var coord = options.lng +',' +options.lat;
							var poiRadius = options.poiRadius || 500;
							var poiPageSize = options.poiPageSize || 50;
							var getPoi = options.getPoi==0 ? 'base':'all';
							var callbackName = "jsonpcallback_" + new Date().getTime();
							
							var apiURL = "https://restapi.amap.com/v3/geocode/regeo?key={key}&location={location}&coordsys={coordType}&radius={radius}&callback={jsonpcallback}&output=json&extensions={getPoi}&roadlevel=0";
							apiURL = apiURL.replace('{coordType}',coordType).replace('{key}',key).replace('{jsonpcallback}',callbackName).replace('{location}',coord)
											.replace('{radius}',poiRadius).replace('{getPoi}',getPoi);
							var option = { handleAs:'json',jsonp:callbackName,timeout:10000 };
							window[callbackName] = function(rtn){
								if(rtn.status==1){
									var regeocode = rtn.regeocode;
									var addressComponent = regeocode.addressComponent;
									var datas = {};
									datas.coordType='5';
									var _location = addressComponent.streetNumber && addressComponent.streetNumber.location;
									var _coords = _location && _location.split(',');
									if(_coords && _coords.length>1){
										datas.point={lat:_coords[1],lng:_coords[0]};
									}else{
										if(options.coordType==1){
											_coords = coordtransform.wgs84togcj02(options.lng,options.lat);
											datas.coordType='5';
											datas.point={lat:_coords[1],lng:_coords[0]};
										}else if(options.coordType==3){
											datas.coordType='3';
											datas.point={lat:options.lat,lng:options.lng};
										}else if(options.coordType==5){
											datas.point={lat:options.lat,lng:options.lng};
										}
									}
									var component = regeocode.addressComponent;
									var street = component.streetNumber && component.streetNumber.street || '';
									var street_number = component.streetNumber && component.streetNumber.number || '';
									var title = (street + street_number) || component.district;
									datas.title = title;
									//debugger;
									datas.address = regeocode.formatted_address;
									//直辖市
									var city = addressComponent.city;
									if(city instanceof Array){
										addressComponent.city = addressComponent.province;
									}
									datas.addressComponent = addressComponent;
									var _pois = regeocode.pois;
									if(_pois && _pois.length>0){
										var newPois = [];
										for(var i = 0;i < _pois.length;i++){
											var locationArr = _pois[i].location.split(',');
											var record = {
												title:_pois[i].name,
												address:_pois[i].address,
												point:{lat:locationArr[1],lng:locationArr[0]},
												distance:_pois[i].distance
											};
											newPois.push(record);
										}
										datas.pois = newPois;
									}
									callback && callback(datas);
								}else{
									error && error(rtn);
								}
							}
							script.get(apiURL ,option);
						}
					});
			return new claz();
		});