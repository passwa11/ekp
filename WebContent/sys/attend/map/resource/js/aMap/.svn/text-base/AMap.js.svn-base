define( function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var topic = require('lui/topic');
	var BaseMap = require('../common/BaseMap');
	var mapUtil = require('../common/MapUtil');
	var lang = require('lang!sys-attend');
	var AMapWidget = BaseMap.extend({

		aMapJSUrl : 'https://webapi.amap.com/maps?v=1.4.15&key=2b0ff8afd4d82301c4cd3d277221e28b&plugin=AMap.AdvancedInfoWindow,AMap.Geocoder,AMap.Autocomplete,AMap.Geolocation',
		//保留地图的初始中心位置
		__mapCenter : null,
		render : function(callback){
			var context = this.context;
			//配置安全密钥
			if (context.mapKeyPcSecurityKey){
				// 兼容不需要安全密钥
				window._AMapSecurityConfig = {
					securityJsCode: context.mapKeyPcSecurityKey,
				}
			}
			var self = this;
			//地图组件只能使用web端jsapi的key
			this.aMapJSUrl = Com_SetUrlParameter(this.aMapJSUrl,'key',context.mapKeyPc);
			this.loadScript(this.aMapJSUrl,function(){
				//单例,如果已经渲染过一次不再渲染
				if(!self.rendered){
					self._initMainMap(context);//地图主区域
					self._initSearchbar(context);//搜索框
					self._initEvent(context);//事件
					self.rendered = true;
				}
				callback && callback();
			});
		},

		// 获取初始化位置
		getInitLocation: function(callback) {
			var self = this;
			//地图组件只能使用web端jsapi的key
			this.aMapJSUrl = Com_SetUrlParameter(this.aMapJSUrl,'key',this.context.mapKeyPc);
			this.loadScript(this.aMapJSUrl, function() {
				$('<div id="' + self.context.uuId + '"/>').prependTo(self.context.element);
				var map = new AMap.Map(self.context.uuId, {
					resizeEnable: true
				});
				map.plugin(['AMap.Geolocation'], function() {
					var geolocation = new AMap.Geolocation({
						// 是否使用高精度定位，默认：true
						enableHighAccuracy: true,
						// 设置定位超时时间，默认：无穷大
						timeout: 10000,
						// 定位按钮的停靠位置的偏移量，默认：Pixel(10, 20)
						buttonOffset: new AMap.Pixel(10, 20),
						//  定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
						zoomToAccuracy: true,
						//  定位按钮的排放位置,  RB表示右下
						buttonPosition: 'RB'
					});
					geolocation.getCurrentPosition(function(status, data) {
						if(status === 'complete') {
							callback && callback({
								"nameValue": data.formattedAddress,
								"coordinateValue": data.position.getLat() + ',' + data.position.getLng(),
								"detailValue": data.formattedAddress,
								"provinceValue": data.addressComponent ? (data.addressComponent.province || "") : "",
								"cityValue": data.addressComponent ? (data.addressComponent.city || "") : "",
								"districtValue": data.addressComponent ? (data.addressComponent.district || "") : ""
							});
						} else {
							console.error("定位失败！");
						}
					});
				});
			});
		},
		
		_initMainMap : function(context){
			var self = this,
				status = context.showStatus, //状态,edit、view、hidden、readOnly
				mainContent = context.mapMainContent,//外层容器
				uuId = context.uuId;
			this.mapContainer = $('<div id=\'amap_container\'/>').addClass('lui_map_container').addClass(status).prependTo(mainContent);
			this.map = new AMap.Map('amap_container', {
				resizeEnable: true,
				zoom:15
			});
			this.map.plugin(['AMap.ToolBar','AMap.Geolocation'],function(){
				var toolbar = new AMap.ToolBar();
				// 定位当前位置
				var geolocation = self.geolocation = new AMap.Geolocation({
					maximumAge : 3000,
					enableHighAccuracy:true
				});
				AMap.event.addListener(geolocation, 'complete', function(rs){
					if(!self.__stopGetLocation__){
						// 如果存在中心点则移动到中心点，不进行这步操作将导致地图以定位为中心 #155508
						if (self.__mapCenter == null){
							return;
						}
						self.map.setCenter(self.__mapCenter);
						// #155508,地图控件定位不能作为位置信息进行发布消息、创建marker和marker弹出框
						// var __options = {
						// 	name : rs.formattedAddress,
						// 	location : rs.position,
						// 	address : rs.formattedAddress,
						// };
						// var marker = self.createMarker(__options);
						// self.openInfoWindow(marker);
						// var evt = {
						// 	nameValue : rs.formattedAddress,
						// 	coordinateValue : rs.position.getLat() + ',' + rs.position.getLng(),
						// 	detailValue : rs.formattedAddress,
						// };
						// topic.channel(uuId).publish('sys.attend.map.data.change',evt);
					}
					self.__stopGetLocation__ = false;
				});
				self.map.addControl(toolbar);
				self.map.addControl(geolocation);
				geolocation.getCurrentPosition();
			});
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
				this.map.on('click',function(e){
					var lnglat = e.lnglat;
					var geocoder = new AMap.Geocoder({
			            radius: 1000,
			            extensions: "all"
			        });        
			        geocoder.getAddress([lnglat.lng,lnglat.lat], function(status, result) {
			            if (status === 'complete' && result.info === 'OK') {
			            	var title = result.regeocode.formattedAddress;
							var address = result.regeocode.formattedAddress;
							var surroundingPois = result.regeocode.pois;
							var point = new AMap.LngLat(lnglat.lng,lnglat.lat);
							var addressComponent = result.regeocode.addressComponent;
							
							if(surroundingPois && surroundingPois.length > 0){
								var poi = surroundingPois[0];
								title = poi.name;
								//address = poi.address;
								//point = new AMap.LngLat(poi.location.lng,poi.location.lat);
							}
			            	var __options = {
									name : title,
									location : point,
									address : address
								};
			            	self.map.clearMap();
							var marker = self.createMarker(__options);
							self.openInfoWindow(marker);
							
							var data = {
								nameValue : title,
		    	    			coordinateValue : "gcj02:"+(point.lat+ ','+point.lng),
		    	    			detailValue : address,
		    	    			province:addressComponent.province,
		    					city:addressComponent.city || addressComponent.province,
		    					district:addressComponent.district
	    	    			};
	    	    			topic.channel(uuId).publish('sys.attend.map.data.change',data);
			            }
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
				this.__stopSetCenter__ = true;
				var coordArr = evt.coordinateValue.replace('bd09:','').replace('gcj02:','').split(/[,;]/);
				var coordType = mapUtil.getCoordType(evt.coordinateValue);
				var lngLat = new AMap.LngLat(coordArr[1],coordArr[0]);
				if(coordType==3){
					AMap.convertFrom(lngLat,'baidu',function(status,result){
						if(status=='complete'){
							self._geocoder(result.locations[0],evt);
						}else{
							alert('地图加载失败');
						}
					});
				}else{
					this._geocoder(lngLat,evt);
				}
			}
		},
		
		_geocoder : function(lngLat,evt){
			var self = this,uuId = this.context.uuId;
			var nameValue = evt.nameValue;
			AMap.plugin(['AMap.Geocoder'],function(){
				var	geocoder = new AMap.Geocoder({});
				geocoder.getAddress(lngLat,function(code,result){
	    			self.getLocationRender(lngLat,{title:nameValue,address:evt.detailValue || result.regeocode.formattedAddress,province:evt.provinceValue,city:evt.cityValue,district:evt.districtValue});
				});
			});
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
				this.searchInput = $('<input id=\'amap_tipinput\'/>').attr('type','text').attr('placeholder',lang["sysAttend.mapDialog.search.txt"]).addClass('lui_map_searchinput').appendTo(this.searchBox);
				this.searchButton = $('<div/>').text(lang["sysAttend.mapDialog.search.btn"]).addClass('lui_map_searchbutton').appendTo(this.searchBox);
				//选中提示关键字触发
				if(!this.autoCompletet){
					AMap.service(['AMap.Autocomplete'],function(){
						var autoCompletet = self.autoCompletet = new AMap.Autocomplete({
							input : amap_tipinput
						});
						AMap.event.addListener(autoCompletet,'select',function(e){
							//直接使用poi.name 无法正确的定位，需要加上省市县信息才能正确定位 #157413
							self._search(e.poi.district + e.poi.name);
							setTimeout(function(){
								$(self.autoCompletet.Pb).hide();
							},300);
						});
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
		
		_search : function(value){
			var self = this,
				context = this.context,
				uuId = context.uuId,
				isDetailed = context.isDetailed;
			this.__stopGetLocation__ = true;
			var keyword = $.trim(value);
            if(keyword && keyword.indexOf(',')>-1){
            	//根据坐标反查
            	var coords = keyword.split(',');
            	var lngLat = new AMap.LngLat(coords[1],coords[0]);
            	this.getLocation(lngLat);
            	return;
            }
			this.autoCompletet.search(value,function(code,result){
				if(code=='no_data'){
					alert(lang['sysAttend.mapDialog.search.status1']);
					return;
				}
				if(code=='error'){
					alert(lang['sysAttend.mapDialog.search.status2']);
					return;
				}
				//清除地图上的覆盖物
				self.map.clearMap();
				var tips = result.tips;
				for(var i = 0 ;i < tips.length; i++){
					var item = tips[i];
					if(item.location){
						var marker = self.createMarker(item);
						self.openInfoWindow(marker);
						var evt = {
							nameValue : item.name,
	    	    			coordinateValue : "gcj02:"+(item.location.lat + ',' + item.location.lng),
	    	    			detailValue : item.address
    	    			};
						//获取省市信息
						var	geocoder = new AMap.Geocoder({});
						var lngLat = new AMap.LngLat(item.location.lng,item.location.lat);
						geocoder.getAddress(lngLat,function(code,result){
							if(code=='no_data'){
								alert(lang['sysAttend.mapDialog.search.status1']);
								return;
							}
							if(code=='error'){
								alert(lang['sysAttend.mapDialog.search.status2']);
								return;
							}
							var addressComponent = result.regeocode.addressComponent;
							evt.province=addressComponent.province;
							evt.city=addressComponent.city || addressComponent.province;
							evt.district=addressComponent.district;
							topic.channel(uuId).publish('sys.attend.map.data.change',evt);
						});
    	    			break;
					}
				}
			});
		},
		
		getLocation:function(lngLat){
			var self = this,uuId = this.context.uuId;
			AMap.plugin(['AMap.Geocoder'],function(){
				var	geocoder = new AMap.Geocoder({});
				geocoder.getAddress(lngLat,function(code,result){
					if(code=='no_data'){
						alert(lang['sysAttend.mapDialog.search.status1']);
						return;
					}
					if(code=='error'){
						alert(lang['sysAttend.mapDialog.search.status2']);
						return;
					}
					var addressComponent = result.regeocode.addressComponent;
	    			self.getLocationRender(lngLat,{title:result.regeocode.formattedAddress,address:result.regeocode.formattedAddress,province:addressComponent.province,city:addressComponent.city,district:addressComponent.district});
				});
			});
		},
		
		getLocationRender:function(lngLat,evt){
			var self = this,uuId = this.context.uuId;
			var title = evt.title;
			var address = evt.address;
			var __options = {
					name : title,
					location : lngLat,
					address : address
				};
				var marker = self.createMarker(__options);
				self.openInfoWindow(marker);
				var data = {
					nameValue : title,
	    			coordinateValue : "gcj02:"+(lngLat.lat+ ',' +lngLat.lng),
	    			detailValue : address,
	    			province:evt.province,
    				city:evt.city,
    				district:evt.district
    			};
    			topic.channel(uuId).publish('sys.attend.map.data.change',data);
		},
		
		createMarker : function(options){
			var self = this,
				context = this.context,
				uuId = context.uuId,
				isDetailed = context.isDetailed,
				location = options.location instanceof AMap.LngLat ?  options.location : new AMap.LngLat(options.location.lng,options.location.lat);
			var marker = new AMap.Marker({
				map : this.map,
				position : location
			}),
				infoWindow = this.createInfoWindow(options);
			marker.location = location;
			marker.name = options.name;
			marker.address = options.address;
			marker.infoWindow = infoWindow;
			marker.on('click',function(evt){
				var target = evt.target;
				if(target){
					self.openInfoWindow(target);
					var evt = {
						nameValue : target.name,
	    	    		coordinateValue : "gcj02:"+(target.location.lat + ',' + target.location.lng),
	    	    		detailValue : target.address
    	    		};
    	    		topic.channel(uuId).publish('sys.attend.map.data.change',evt);
				}
			});
			return marker;
		},
		
		createInfoWindow : function(options){
			var self = this,
				location = options.location instanceof AMap.LngLat ?  options.location : new AMap.LngLat(options.location.lng,options.location.lat);
			var content = $('<div/>'),
				title =  $('<div/>').addClass('lui_map_infowindow_title').html(options.name).appendTo(content),
				address = $('<div/>').addClass('lui_map_infowindow_address').html('地址：' + options.address).appendTo(content);
			return new AMap.AdvancedInfoWindow({
				placeSearch : false,
				content : content.html(),
				position : location,
				offset : new AMap.Pixel(0,-30)
			});
		},
		
		openInfoWindow : function(marker){
			var infoWindow = marker.infoWindow;
			infoWindow.open(this.map,marker.location);
			this.map.setZoomAndCenter(15 , marker.location);
			this.map.panBy(0,100);
			this.__mapCenter = this.map.getCenter();
		},
		
		openNavigationUri : function(evt){
			if(evt && evt.coordinateValue){
				var coordArr = evt.coordinateValue.replace('bd09:','').replace('gcj02:','').split(/,|;/);
				var coordType = mapUtil.getCoordType(evt.coordinateValue);
				var lngLat = new AMap.LngLat(coordArr[1],coordArr[0]);
				if(coordType==3){
					AMap.convertFrom(lngLat,'baidu',function(status,result){
						if(status=='complete'){
							self._openNavUri(result.locations[0],evt);
						}else{
							alert('地图加载失败');
						}
					});
				}else{
					this._openNavUri(lngLat,evt);
				}
			}
		},
		
		_openNavUri : function(lngLat,evt){
			var link = 'http://uri.amap.com/navigation?mode=car&policy=0&src=ekp&coordinate=gaode&callnative=0',
				coord = lngLat.lng + ',' + lngLat.lat,
				address = evt.nameValue;
			AMap.plugin(['AMap.Geolocation'],function(){
				var geoloc = new AMap.Geolocation({
					maximumAge : 3000,
					enableHighAccuracy : true,
				});
				geoloc.getCurrentPosition(function(status,result){
					if(status == 'complete'){ 
						var point = result.position,
							curCoord = point.getLng() + ',' + point.getLat(),
							curAddress = '我的位置';
							
						link = link + '&from=' + curCoord + ',' + curAddress 
									+ '&to=' + coord + ',' + address;
						
						window.open(link, '_blank');
					}
				})
			});
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