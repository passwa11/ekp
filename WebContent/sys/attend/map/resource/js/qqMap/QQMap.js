define( function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var topic = require('lui/topic');
	var BaseMap = require('../common/BaseMap');
	var mapUtil = require('../common/MapUtil');
	var lang = require('lang!sys-attend');
	
	var AMapWidget = BaseMap.extend({
		
		qqMapJSUrl : 'https://map.qq.com/api/js?v=2.exp&key=I7RBZ-7WR3D-V6V4R-HQGPA-BJYVT-WIBLL&libraries=place,geometry,autocomplete,convertor',
		// 用于获取初始位置
		qqLocationJsUrl: 'https://apis.map.qq.com/ws/location/v1/ip?key=I7RBZ-7WR3D-V6V4R-HQGPA-BJYVT-WIBLL',
		
		render : function(callback){
			var context = this.context;
			var self = this;
			this.qqMapJSUrl = Com_SetUrlParameter(this.qqMapJSUrl,'key',context.mapKey);
			if(!window['_$isMapJsLoaded$_']){
				var _qqMapJSUrl = Com_SetUrlParameter(this.qqMapJSUrl,'callback',context.uuId + '_callback');
				window[context.uuId + '_callback'] = function(){
					self.__initMap(callback);
				};
				this.loadScript(_qqMapJSUrl,function(){
				});
			}else{
				this.__initMap(callback);
			}
		},

		// 获取初始化位置
		getInitLocation: function(callback) {
			var self = this;
			self.qqLocationJsUrl = Com_SetUrlParameter(self.qqLocationJsUrl,'key',self.context.mapKey);
			$.ajax({
				type:'get',
				url: self.qqLocationJsUrl,
				data:{
					output:'jsonp',
				},
				dataType: 'jsonp',
				success:function (res) {
					if(res.status == 0) {
						callback && callback({
							"nameValue": res.result.ad_info ? ((res.result.ad_info.province || "") + (res.result.ad_info.city || "") + (res.result.ad_info.district || "")) : "",
							"coordinateValue": res.result.location.lat + ',' + res.result.location.lng,
							"detailValue": res.result.ad_info ? ((res.result.ad_info.province || "") + (res.result.ad_info.city || "") + (res.result.ad_info.district || "")) : "",
							"provinceValue": res.result.ad_info ? (res.result.ad_info.province || "") : "",
							"cityValue": res.result.ad_info ? (res.result.ad_info.city || "") : "",
							"districtValue": res.result.ad_info ? (res.result.ad_info.district || "") : ""
						});
					} else {
						if(window.console) {
							console.error("定位失败！");
						}
					}
				}
			});
		},
		
		__initMap : function(callback){
			var context = this.context;
			//单例,如果已经渲染过一次不再渲染
			if(!this.rendered){
				this._initMainMap(context);//地图主区域
				this._initSearchbar(context);//搜索框
				this._initEvent(context);//事件
				this.rendered = true;
			}
			callback && callback();
		},
		
		_initMainMap : function(context){
			var self = this,
				status = context.showStatus, //状态,edit、view、hidden、readOnly
				mainContent = context.mapMainContent,//外层容器
				uuId = context.uuId;
			this.mapContainer = $('<div/>').addClass('lui_map_container').addClass(status).prependTo(mainContent);
			var center = new qq.maps.LatLng(39.916527,116.397128);
			this.map = new qq.maps.Map(this.mapContainer[0],{
				center: center,
			    zoom: 15
			});
			//获取城市列表接口设置中心点
		    citylocation = new qq.maps.CityService({
		        complete : function(result){
		        	self.map.setCenter(result.detail.latLng);
		        	self.cityName = result.detail.name;
		        },
		        error : function(){
		        	alert('地理位置定位失败');
		        }
		    });
		    //调用searchLocalCity();方法    根据用户IP查询城市信息。
		    citylocation.searchLocalCity();
		},
		
		_initEvent : function(context){
			var self = this,
				status = context.showStatus,
				uuId = context.uuId,
				isDetailed = context.isDetailed;
			//数据初始化事件
			topic.channel(uuId).subscribe('sys.attend.map.data.init',this._initDataEvent,this);
			//取消选定事件
			topic.channel(uuId).subscribe('sys.attend.map.dialog.unselect',this._initUnselectEvent,this);
			//关闭窗口事件
			topic.channel(uuId).subscribe('sys.attend.map.dialog.close',this._initCloseEvent,this);
			//地图导航
			topic.channel(uuId).subscribe('sys.attend.map.uri.navigation.open',this.openNavigationUri,this);
			if(status == 'edit'){
				qq.maps.event.addListener(this.map, 'tilesloaded', function(evt) {
					//地图加载完成重新回写
					self.searchInput.attr('placeholder',lang["sysAttend.mapDialog.search.txt"]);
				});
				qq.maps.event.addListener(this.map, 'click', function(evt) {
					var geocoder = new qq.maps.Geocoder();
					geocoder.getAddress(evt.latLng);
					geocoder.setComplete(function(result) {
	                    //map.setCenter(result.detail.location);
						var detail = result.detail;
						var title = detail.addressComponents.streetNumber || detail.addressComponents.district;
						var address = detail.address;
						var location = detail.location;
						var surroundingPois = detail.nearPois;

						var evt = {
    	    					nameValue : title,
	    	    				coordinateValue : "gcj02:"+(location.lat + ',' + location.lng),
	    	    				detailValue : address,
	    	    				province:detail.addressComponents.province,
	    	    				city:detail.addressComponents.city,
	    	    				district:detail.addressComponents.district
    	    			};
						if(surroundingPois && surroundingPois.length > 0){
							var poi = surroundingPois[0];
							title = poi.name;
							address = poi.address;
							
							evt.nameValue = poi.name;
							evt.coordinateValue = "gcj02:"+(location.lat + ',' + location.lng);
							evt.detailValue = poi.address;
						}
						
						var markerPoit = {
							name : title,
							latLng : location,
							address : address
						};
						self.clearOverlays();
						var marker = self.createMarker(markerPoit);
						marker.infoWin.open();
    	    			topic.channel(uuId).publish('sys.attend.map.data.change',evt);
	                });
	                //若服务请求失败，则运行以下函数
	                geocoder.setError(function() {	
	                });
				});
			}
		},
		
		_initDataEvent : function(evt){
			var self = this,
				context = this.context,
				uuId = context.uuId,
				status = context.showStatus;
			if(!evt.nameValue){
				if(status == 'edit'){
					this.searchInput.val(evt.nameValue);
				}
				
			}
			if(evt.nameValue && !evt.coordinateValue){
				if(status == 'edit'){
					this.searchInput.val(evt.nameValue);
				}
			}
			if(evt.coordinateValue && evt.nameValue) {
				var coordArr = evt.coordinateValue.replace('bd09:','').replace('gcj02:','').split(/[,;]/);
				var coordType = mapUtil.getCoordType(evt.coordinateValue);
				var latLng = new qq.maps.LatLng(coordArr[0],coordArr[1]);
				if(coordType==3){
					qq.maps.convertor.translate(latLng,3,function(res){
						self._geocoder(res[0],evt);
					});
				}else{
					this._geocoder(latLng,evt);
				}
			}
		},
		
		_geocoder : function(latLng,evt){
			var self = this,uuId = this.context.uuId;
			var nameValue = evt.nameValue;
			var geocoder = new qq.maps.Geocoder();
			geocoder.getAddress(latLng);
			geocoder.setComplete(function(result) {
    			self.getLocationRender(latLng,{title:nameValue,address:result.detail.address,province:evt.provinceValue,city:evt.cityValue,district:evt.districtValue});
            });
            //若服务请求失败，则运行以下函数
            geocoder.setError(function() {
            	alert(lang['sysAttend.mapDialog.search.status3']);
            });
		},
		getLocation:function(latLng){
			var self = this,uuId = this.context.uuId;
			var geocoder = new qq.maps.Geocoder();
			geocoder.getAddress(latLng);
			geocoder.setComplete(function(result) {
    			self.getLocationRender(latLng,{title:result.detail.address,address:result.detail.address,province:result.detail.addressComponents.province,city:result.detail.addressComponents.city,district:result.detail.addressComponents.district});
            });
            //若服务请求失败，则运行以下函数
            geocoder.setError(function() {
            	alert(lang['sysAttend.mapDialog.search.status3']);
            });
		},
		
		getLocationRender:function(latLng,evt){
			var self = this,uuId = this.context.uuId;
			setTimeout(function(){
				 self.map.panTo(latLng);
			},350);
			var title = evt.title;
			var address = evt.address;
			
			var markerPoit = {name : title,latLng : latLng,address : address};
			self.clearOverlays();
			var marker = self.createMarker(markerPoit);
			marker.infoWin.open();
			var option = {
				nameValue : title,coordinateValue : "gcj02:"+(latLng.lat + ',' + latLng.lng),detailValue : address,
				province:evt.province,
				city:evt.city,
				district:evt.district
			};
			topic.channel(uuId).publish('sys.attend.map.data.change',option);
		},
		
		_initUnselectEvent : function(evt){
			this.searchInput.val('');
		},
		
		_initCloseEvent : function(evt){
			var status = this.context.showStatus;
			if(status == 'edit'){
				this.searchInput.val('');
			}
			this.destory();
		},
		
		_initSearchbar : function(context){
			var self = this,
				uuId = context.uuId,
				status = context.showStatus,
				isDetailed = context.isDetailed,
				mainContent = context.mapMainContent;
			if(status == 'edit'){
				this.searchBox = $('<div class="lui_map_searchbox" />').prependTo(mainContent);
				this.searchInput = $('<input/>').attr('type','text').attr('placeholder',lang["sysAttend.mapDialog.search.txt"]).addClass('lui_map_searchinput').appendTo(this.searchBox);
				this.searchButton = $('<div/>').text(lang['sysAttend.mapDialog.search.btn']).addClass('lui_map_searchbutton').appendTo(this.searchBox);
				//选中提示关键字触发
				if(!this.autoCompletet){
					var autoCompletet = self.autoCompletet = new qq.maps.place.Autocomplete(self.searchInput[0]);
					self.searchService = new qq.maps.SearchService({
				        map : self.map
				    });
				    //添加监听事件
				    qq.maps.event.addListener(autoCompletet, "confirm", function(res){
				    	if (autoCompletet.place && autoCompletet.place.address){
							self._search(res.value,autoCompletet.place.address);
						}else{
							self._search(res.value);
						}
				    });
				}
				//点击搜索按钮触发
				this.searchButton.on('click',function(){
					var value = self.searchInput.val();
					self._search(value);
				});
				//回车触发
				$(document).keydown(function(e){
					if(e.keyCode==13){
						var value = self.searchInput.val();
						self._search(value);
					}
				});
			}
		},
		
		_search : function(value,location){
			var self = this,
				context = this.context,
				uuId = context.uuId,
				isDetailed = context.isDetailed;
			this.__stopGetLocation__ = true;
			this.searchService.setError(function(result){
				console.log('搜索失败');
			});
			//默认pois数量
			this.searchService.setPageIndex(0);
			if (location && location.indexOf("-")>-1){
				//判断选择联想框中选择的是哪个城市的数据
				this.searchService.setLocation(location.split("-")[1]);
			}else {
				this.searchService.setLocation(self.cityName);
			}
			this.searchService.setPageCapacity(5);
			this.searchService.setComplete(function(result){
				setTimeout(function(){
					self.searchService.clear();
				},1);
				if(result.type !="POI_LIST"){
					alert('请输入正确的地址');
					return;
				}
				var pois = result.detail.pois;
				if(pois && pois.length>0){
					var latlngBounds = new qq.maps.LatLngBounds();
					for(var i = 0; i < pois.length;i++){
						//扩展边界范围，用来包含搜索到的Poi点
                        latlngBounds.extend(pois[i].latLng);
                        var markerInfo = self.createMarker(pois[i]);
                        
						if(i==0){
							markerInfo.infoWin.open();
							var evt = {
									nameValue : pois[i].name,
			    	    			coordinateValue : "gcj02:"+(pois[i].latLng.lat + ',' + pois[i].latLng.lng),
			    	    			detailValue : pois[0].address || ''
		    	    			};
							var geocoder = new qq.maps.Geocoder();
							geocoder.getAddress(pois[i].latLng);
							geocoder.setComplete(function(result) {
								evt.province=result.detail.addressComponents.province;
								evt.city=result.detail.addressComponents.city;
								evt.district=result.detail.addressComponents.district;
								topic.channel(uuId).publish('sys.attend.map.data.change',evt);
				            });
				            //若服务请求失败，则运行以下函数
				            geocoder.setError(function() {
				            	alert(lang['sysAttend.mapDialog.search.status3']);
				            });
		    	    		break;
						}
						
					}
					//调整地图视野
                    self.map.fitBounds(latlngBounds);
				}
			});
			this.clearOverlays();
			var keyword = $.trim(value);
            if(keyword && keyword.indexOf(',')>-1){
            	//根据坐标反查
            	var coords = keyword.split(',');
            	var latLng = new qq.maps.LatLng(coords[0],coords[1]);
            	this.getLocation(latLng);
            	return;
            }
			this.searchService.search(value);
		},
		clearOverlays : function() {
			var overlays = this.markers;
			var infoWindows = this.infoWindows;
			if(!overlays){
				overlays = [];
			}
			if(!infoWindows){
				infoWindows = [];
			}
            var overlay;
            while (overlay = overlays.pop()) {
                overlay.setMap(null);
            }
            var infoWin;
            while (infoWin = infoWindows.pop()) {
            	infoWin.close();
            }
        },
		createMarker : function(options){
			var self = this,
				context = this.context,
				uuId = context.uuId,
				isDetailed = context.isDetailed,
				location = options.latLng instanceof qq.maps.LatLng ?  options.latLng : new qq.maps.LatLng(options.latLng.lat,options.latLng.lng);
			var marker = new qq.maps.Marker({
				map : this.map,
				position : location
			}),
				infoWindow = this.createInfoWindow(options);
			
			if(!this.markers){
				this.markers = [];
			}
			this.markers.push(marker);
			
			if(!this.infoWindows){
				this.infoWindows = [];
			}
			this.infoWindows.push(infoWindow);
			
			qq.maps.event.addListener(marker, 'click', function(evt) {
				infoWindow.open();
				var evt = {
					nameValue : options.name,
    	    		coordinateValue : "gcj02:"+(location.lat + ',' + location.lng),
    	    		detailValue : options.address
	    		};
	    		topic.channel(uuId).publish('sys.attend.map.data.change',evt);
			});
			return {'marker':marker,'infoWin':infoWindow};
		},
		
		createInfoWindow : function(options){
			var self = this,
				location = options.latLng instanceof qq.maps.LatLng ?  options.latLng : new qq.maps.LatLng(options.latLng.lat,options.latLng.lng);
			var content = $('<div/>'),
				title =  $('<div/>').addClass('lui_map_infowindow_title').html(options.name).appendTo(content),
				address = $('<div/>').addClass('lui_map_infowindow_address').html('地址：' + options.address).appendTo(content);
			return new qq.maps.InfoWindow({
				map : self.map,
				content : content.html(),
				position : location
			});
		},
		
		//不开放
		_openNavigationUri : function(evt){
			if(evt && evt.coordinateValue){
				var coordArr = evt.coordinateValue.replace('bd09:','').replace('gcj02:','').split(/[,;]/);
				var coordType = mapUtil.getCoordType(evt.coordinateValue);
				var latLng = new qq.maps.LatLng(coordArr[0],coordArr[1]);
				if(coordType==3){
					qq.maps.convertor.translate(latLng,3,function(res){
						self._openNavUri(res[0],evt);
					});
				}else{
					this._openNavUri(latLng,evt);
				}
			}
		},
		_openNavUri : function(lngLat,evt){
			var link = 'http://apis.map.qq.com/uri/v1/routeplan?type=drive&from=&fromcoord=&to=&tocoord=&policy=0&referer=ekp',
				coord = lngLat.lat + ',' + lngLat.lng,
				address = evt.nameValue;
		},
		
		destory : function(){
			var context = this.context;
			this._destoryEvent(context);
		},
		
		_destoryEvent : function(){
			var uuId = this.context.uuId;
			topic.channel(uuId).unsubscribe('sys.attend.map.data.init',this._initDataEvent);
			topic.channel(uuId).unsubscribe('sys.attend.map.dialog.unselect',this._initUnselectEvent);
			topic.channel(uuId).unsubscribe('sys.attend.map.dialog.close',this._initCloseEvent);
		}
	
	});
	
	module.exports = AMapWidget;
	
});