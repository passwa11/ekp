define( function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var topic = require('lui/topic');
	var BaseMap = require('../common/BaseMap');
	var mapUtil = require('../common/MapUtil');
	var lang = require('lang!sys-attend');
	
	require('../../css/SearchControl.css');
	require('./SearchControl');
	
	var BaiduMap = BaseMap.extend({
		
		baiduJSUrl : 'https://api.map.baidu.com/api?v=2.0&ak=' + mapUtil.bMapKey + '&s=1',
		
		render : function(callback){
			var context = this.context;
			var self = this;
			this.baiduJSUrl = Com_SetUrlParameter(this.baiduJSUrl,'ak',context.mapKey);
			if(!window['_$isMapJsLoaded$_']){
				var _baiduJSUrl = Com_SetUrlParameter(this.baiduJSUrl,'callback',context.uuId + '_callback');
				window[context.uuId + '_callback'] = function(){
					self.__initMap(callback);
				};
				this.loadScript(_baiduJSUrl,function(){
				});
			}else{
				this.__initMap(callback);
			}
		},

		// 获取初始化位置
		getInitLocation: function(callback) {
			var self = this;
			self.baiduJSUrl = Com_SetUrlParameter(self.baiduJSUrl,'ak',self.context.mapKey);
			var _baiduJSUrl = Com_SetUrlParameter(self.baiduJSUrl,'callback',self.context.uuId + '_callback');
			window[self.context.uuId + '_callback'] = function() {
				var geolocation = new BMap.Geolocation();
				geolocation.getCurrentPosition(function(result) {
					if(this.getStatus() == BMAP_STATUS_SUCCESS) {
						callback && callback({
							"nameValue": result.address ? ((result.address.province || "") + (result.address.city || "") + (result.address.district || "")) : "",
							"coordinateValue": result.latitude + ',' + result.longitude,
							"detailValue": result.address ? ((result.address.province || "") + (result.address.city || "") + (result.address.district || "")) : "",
							"provinceValue": result.address ? (result.address.province || "") : "",
							"cityValue": result.address ? (result.address.city || "") : "",
							"districtValue": result.address ? (result.address.district || "") : ""
						});
					} else {
						if(window.console) {
							console.error("定位失败！");
						}
					}
				});
			};
			self.loadScript(_baiduJSUrl, function() {
			});
		},
		
		__initMap : function(callback){
			var context = this.context;
			//单例,如果已经渲染过一次不再渲染
			if(!this.rendered){
				this._initMainMap(context);//地图主区域
				this._initEvent(context);//事件
				this._initSearchbar(context);//搜索框
				this.rendered = true;
			}
			callback && callback();
		},
		
		destory : function(){
			var context = this.context;
			this._destoryEvent(context);
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
				this.map.addEventListener("click", function(evt){
					var geoc = new BMap.Geocoder(), 
					 	point = evt.point;
					geoc.getLocation(point,function(rs){
						var title = rs.addressComponents.district;
						var address = rs.address;
						var surroundingPois = rs.surroundingPois;
						var evt = {
    	    					nameValue : title || rs.addressComponents.city,
	    	    				coordinateValue : "bd09:"+(point.lat + ',' + point.lng),
	    	    				detailValue : address || rs.addressComponents.city,
	    	    				province:rs.addressComponents.province,
	    	    				city:rs.addressComponents.city,
	    	    				district:rs.addressComponents.district
    	    			};
						if(surroundingPois && surroundingPois.length > 0){
							var poi = surroundingPois[0];
							title = poi.title;
							address = poi.address;
							
							evt.nameValue = poi.title || poi.city;
							evt.coordinateValue = "bd09:"+(poi.point.lat + ',' + poi.point.lng);
							evt.detailValue = poi.address || poi.city;
						}
						//清除地图上所有覆盖物
						self.map.clearOverlays();
						var markerPoit = {
								point : point,
								title : title,
								address : address,
								isZoom : false
						};
						var marker = self.createMarker(markerPoit);
						self.openInfoWindow(marker);
    	    			topic.channel(uuId).publish('sys.attend.map.data.change',evt);
					});
				});
			}
		},
		
		_initDataEvent : function(evt){
			var self = this,
				context = this.context,
				status = context.showStatus,
				isDetailed = context.isDetailed,
				uuId = context.uuId;
			if(!evt.nameValue) {
				self.map.reset();
				if(status == 'edit' && self.searchControl.dom && self.searchControl.dom.searchText)
					self.searchControl.dom.searchText.value = '';
				//定位到当前位置
//				this.geolocationControl.location();
	    	}
			if(evt.nameValue && !evt.coordinateValue){
				self.map.reset();
				if(status == 'edit'){
					setTimeout(function(){//解决搜索下拉不出现
						self.searchControl.dom.searchText.value = evt.nameValue;
					},500);
				}
			}
			if(evt.coordinateValue && evt.nameValue) {
				self.__stopSetCenter__ = true;
				var coordArr = evt.coordinateValue.replace('bd09:','').replace('gcj02:','').split(/[,;]/);
				var coordType = mapUtil.getCoordType(evt.coordinateValue);
				var __point = new BMap.Point(coordArr[1], coordArr[0]);
				if(coordType==5){
					new BMap.Convertor().translate([__point],3,5,function(result){
						if(result.status==0){
							self._geocoder(result.points[0],evt);
						}else{
							alert('地图加载失败');
						}
					});
				}else{
					this._geocoder(__point,evt);
				}
			}
		},
		
		_geocoder : function(point,evt){
			var self = this,uuId = this.context.uuId;
			var nameValue = evt.nameValue;
			// 逆地址编码
	    	var geocoder = new BMap.Geocoder();
	    	geocoder.getLocation(point, function(result){
    			self.getLocationRender(point,{title:nameValue,address:evt.detailValue || result.address,province:evt.province||result.addressComponents.province,city:evt.city ||result.addressComponents.city,district:evt.district ||result.addressComponents.district});
    		});
		},
		
		getLocationRender:function(point,evt){
			var self = this,uuId = this.context.uuId;
			var title = evt.title;
			var address = evt.address;
			setTimeout(function(){//红点不定位到中央
    			var marker = self.createMarker({
					point : point,
					title : title,
					address : "地址：" + address
				});
	    		self.openInfoWindow(marker);
    		},200);
    		var data = {
					nameValue : title,
    				coordinateValue : 'bd09:' + (point.lat + ',' + point.lng),
    				detailValue : address,
    				province:evt.province,
    				city:evt.city,
    				district:evt.district
			};
			topic.channel(uuId).publish('sys.attend.map.data.change',data);
		},
		
		_initUnselectEvent : function(){
			var self = this;
			self.map.closeInfoWindow();
			self.searchControl.dom.searchText.value = '';
		},
		
		_initCloseEvent : function(){
			var self = this,
				status = this.context.showStatus;
			self.map.clearOverlays();
			if(status == 'edit'){
				self.searchControl && self.searchControl.searchAC && self.searchControl.searchAC.dispose();//销毁搜索下拉
			}
			self.destory();
		},
		
		_destoryEvent : function(context){
			var uuId = context.uuId;
			topic.channel(uuId).unsubscribe('sys.attend.map.data.init',this._initDataEvent);
			topic.channel(uuId).unsubscribe('sys.attend.map.dialog.unselect',this._initUnselectEvent);
			topic.channel(uuId).unsubscribe('sys.attend.map.dialog.close',this._initCloseEvent);
		},
		
		_initMainMap : function(context){
			var self = this,
				status = context.showStatus, //状态,edit、view、hidden、readOnly
				mainContent = context.mapMainContent, //外层容器
				uuId = context.uuId;
			this.mapContainer = $('<div class="lui_map_container"></div>').addClass(status).prependTo(mainContent);
			this.map = new BMap.Map(this.mapContainer[0],{enableMapClick: false});
			this.map.enableScrollWheelZoom();

			this.map.addControl(new BMap.NavigationControl());
			var localcity = new BMap.LocalCity();
			localcity.get(function(result){
				if(!self.__stopSetCenter__){
					self.map.centerAndZoom(result.center, 15);
				}
			});
		},
		
		_initSearchbar : function(context){
			var self = this,
				uuId = context.uuId,
				status = context.showStatus,
				isDetailed = context.isDetailed,
				mainContent = context.mapMainContent;
			
			if(status == 'edit'){
				this.searchBox = $('<div class="lui_map_searchbox" />').prependTo(mainContent);
				this.searchControl = new BMapLib.SearchControl({
		    	    container 			: this.searchBox[0],
		    	    map     			: this.map,
		    	    mapLocation			: this,
		    	    type    			: window.LOCAL_SEARCH,
		    	    enableAutoLocation 	: true,
		    	    isAutoComplete		: true,
		    	    i18n				:{searchTip:lang['sysAttend.mapDialog.search.txt'],searchBtn:lang["sysAttend.mapDialog.search.btn"]},
		    	    onMarkerSetdown		: function(pois) {
		    	    	self.__stopGetLocation__ = true;
		    	    	var k = 0;
		    	    	for(var i = 0; i < pois.length;i++){
		    	    		var poi = pois[i];
		    	    		if(!poi.point){
		    	    			continue;
		    	    		}
		    	    		k = Math.min(k,i);
		    	    		if(i==k){//第一个有效值
		    	    			var geocoder = new BMap.Geocoder();
		    	    			var thatpoi=poi;
			    		    	geocoder.getLocation(thatpoi.point, function(result){
			    		    		var evt = {
			    	    					nameValue : thatpoi.title || thatpoi.city,
				    	    				coordinateValue : 'bd09:'+(thatpoi.point.lat + ',' + thatpoi.point.lng),
				    	    				detailValue : thatpoi.address || thatpoi.city,
				    	    				province:result.addressComponents.province,
				    	    				city:result.addressComponents.city || result.addressComponents.province,
				    	    				district:result.addressComponents.district
			    	    			};
			    	    			topic.channel(uuId).publish('sys.attend.map.data.change',evt);
			    	    		});
		    	    		}
		    	    		
		    		    	poi.marker.addEventListener('click', function(e){
		    	    			var markerTitle = poi.title || poi.city;
		                		var markerAddress = poi.address || poi.city;
		                		var markerCoordinate ='bd09:'+( poi.point.lat + ',' + poi.point.lng);

		                		var option = {
		                			nameValue : markerTitle,
		                			coordinateValue : markerCoordinate,
		                			detailValue : markerAddress
		                		}
		                		var geocoder = new BMap.Geocoder();
			    		    	geocoder.getLocation(poi.point, function(result){
			    		    		option.province=result.addressComponents.province;
			    		    		option.city=result.addressComponents.city;
			    		    		option.district=result.addressComponents.district;
			    	    			topic.channel(uuId).publish('sys.attend.map.data.change',option);
			    	    		});
		    	    		});
		    	    	}
		    	    }
		    	});
			}
		},
		
		createMarker : function(options){
			var self = this;
			var point = options.point,
				marker = new BMap.Marker(point),
				infoWindow = this.createInfoWindow(options),
				isZoom = options.isZoom === false ? false : true;
			if(isZoom){
				this.map.centerAndZoom(point, 15);
			}
			this.map.addOverlay(marker);
			marker.point = options.point;
			marker.title = options.title;
			marker.address = options.address;
			marker.infoWindow = infoWindow;
			marker.addEventListener('click',function(evt){
				//evt.domEvent.stopPropagation();
				if (event.stopPropagation) { 
					event.stopPropagation();
				}else if(window.event){
					window.event.cancelBubble = true; 
				}
				var target = evt.target;
				if(target){
					self.openInfoWindow(target);
				}
			});
			return marker;
		},
		
		createInfoWindow : function(options) {
			var $content = $('<div class="lui_infowin_content" />').html(options.address);
		    var $title = $('<p class="lui_infowin_title" />').attr('title',options.title).html(options.title);
		    var opt = {
		    	title : $title[0] ,
		    }
		    return new BMap.InfoWindow($content[0],opt);
		},
		
		openInfoWindow : function(marker){
			marker.openInfoWindow(marker.infoWindow);
		},
		
		openNavigationUri : function(evt){
			if(evt && evt.coordinateValue){
				var coordArr = evt.coordinateValue.replace('bd09:','').replace('gcj02:','').split(/[,;]/);
				var coordType = mapUtil.getCoordType(evt.coordinateValue);
				var __point = new BMap.Point(coordArr[1], coordArr[0]);
				if(coordType==5){
					new BMap.Convertor().translate([__point],3,5,function(result){
						if(result.status==0){
							self._openNavUri(result.points[0],evt);
						}else{
							alert('地图加载失败');
						}
					});
				}else{
					this._openNavUri(__point,evt);
				}
			}
		},
		_openNavUri : function(lngLat,evt){
			var link = 'http://api.map.baidu.com/direction?mode=driving&output=html&src=ekp',
				coord = lngLat.lng + ',' + lngLat.lat,
				address = evt.nameValue;
			var geolocation = new BMap.Geolocation();
	    	geolocation.getCurrentPosition(function(result){
	    		if(this.getStatus() == BMAP_STATUS_SUCCESS){
	    			var curCoord = result.point.lat+','+result.point.lng,
	    				curAddress = '我的位置';
	    			var geoc = new BMap.Geocoder();
	    			geoc.getLocation(result.point,function(rs){
	    				var curCity = rs.addressComponents.city || rs.addressComponents.province;
	    				
	    				link = link + '&origin=latlng:' + curCoord + '|name:' + curAddress
	    							+ '&destination=latlng:' + coord + '|name:' + address + '&region=' + curCity;
	    				
	    				window.open(link, '_blank');
					});
	    		}else {
	    			console.error(this.getStatus());
	    		}
	    	},{
	    		enableHighAccuracy: true,
	    		maximumAge : 3000
	    	});
		},
		
	});
	
	module.exports = BaiduMap;
	
});