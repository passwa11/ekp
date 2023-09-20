define([
    "dojo/_base/declare","dojo/dom-style","dojo/dom-class","mui/map","dojo/query","dojo/_base/lang",
    "dojo/topic","dojo/dom-construct","dojox/mobile/ScrollableView","dojo/dom-attr",
    "sys/attend/map/mobile/resource/js/aMap/LocationDialog",
    "mui/device/adapter","sys/attend/map/mobile/resource/js/common/MapUtil",
	"mui/util","mui/i18n/i18n!sys-attend"
	], function(declare,domStyle,domClass,map,query,lang,topic,domConstruct,ScrollableView,domAttr,LocationDialog,adapter,MapUtil,util,Msg) {
	
	return declare("sys.attend.AttendAMapLocationDialog", [LocationDialog], {
		
		renderList : function(){
		},
		renderMap : function(){
			var self = this;
			domClass.add(this.contentdom,'muiLocationContent');
		},
		renderToolbar:function(){
		},
		_initGeoControl:function(){},
		
		show : function(evt){
			var self = this;
			this.fdLocations = evt.fdLocations;
			this.fdLimit = evt.fdLimit;
			this.radius = evt.radius;
			this.fdType=evt.fdType;
			
			domStyle.set(this.domNode,'display','block');
			adapter.getCurrentCoord(function(result){
				var lngLat = new AMap.LngLat(result.lng,result.lat);
				var type = 'baidu';//百度经纬度
				if(result.coordType==1){
					type = 'gps';//gps经纬度
				}
				if(result.coordType!=5){
					AMap.convertFrom(lngLat,type,function(status,res){
						if(status=='complete'){
							self.attendLocationsConvert(res.locations[0],function(){
								self._setPosition(res.locations[0],{})
							});
						}else{
							self.showError();
						}
					});
				}else{
					self.attendLocationsConvert(lngLat,function(){
						self._setPosition(lngLat,{})
					});
				}
			},function(){
				self.showError();
			});

			var showStatus = evt.showStatus || this.showStatus;
			if(showStatus == 'edit'){
				domClass.add(this.domNode,'edit');
			}else{
				domClass.remove(this.domNode,'edit');
			}
		},
		onCurGeoClick : function(){
			topic.publish('sys/attend/relocation/start',this);
			this.inherited(arguments);
		},
		_setPosition : function(fdLatLng,evt){
			var self = this;
			//考勤点位置信息
			var attendLocation = this.attendLocation;
			var curLimit=this.curLimit;
			map.getAMapLocation({coordType:5,lat:fdLatLng.lat,lng:fdLatLng.lng,getPoi:0},function(result){
				var title = result.title;
				var address = result.address;
				var distance = attendLocation ?MapUtil.getDistance(MapUtil.formatCoord(fdLatLng,5),MapUtil.formatCoord(attendLocation.point,5)):NaN;
				var fdLocation = result.address;
				var inside = false;
				var iconUrl = "/sys/attend/mobile/resource/image/myora.png";
				if((self.fdType==1||self.fdType=="1")&& (self.fdLimit==null||self.fdLimit=="")){
					if(attendLocation && self.fdLocations && self.fdLocations.length>0){
						for(var arr in self.fdLocations){
							if(self.fdLocations[arr].distance && distance <= parseFloat(self.fdLocations[arr].distance)){
								iconUrl = "/sys/attend/mobile/resource/image/myblue.png";
								fdLocation = attendLocation.address;
								inside = true;
								break;
							}
						}
					}
				}else{
					if(attendLocation && self.fdLocations && self.fdLocations.length>0 && self.fdLimit && distance <= parseFloat(self.fdLimit)){
						iconUrl = "/sys/attend/mobile/resource/image/myblue.png";
						fdLocation = attendLocation.address;
						inside = true;
					}
				}
				var markerPoit = {
					point : fdLatLng,
					title : title,
					address : address,
					icon:util.formatUrl(iconUrl,true)
				};
				if(attendLocation){
					var attendMarker = self.createMarker({
						point:attendLocation.point,
						label :Msg['mui.sign.zone'],
						labelClass:'muiMyLocationLabel'
					});
					self.showAttendLimit({point:attendLocation.point,radius:self.fdLimit});
				}else{
					self.map.setZoom(17);
				}
				var marker = self.createMarker(markerPoit);
				self.map.panTo(fdLatLng);
				
				topic.publish('sys/attend/relocation/complete',self,{
					fdLatLng:MapUtil.formatCoord(fdLatLng,5),
					address:address,//当前位置
					fdLocation: fdLocation,//考勤位置
					distance:distance,
					inside : inside
				});
			},function(){
				self.showError();
			});
		},
		createMarker : function(options){
			var zoomLevel=16;
			if(this.fdLimit){
				zoomLevel = this.fdLimit<=200 ? 17:16;
			}
			var self = this,
				location = options.point instanceof AMap.LngLat ?  options.point : new AMap.LngLat(options.point.lng,options.point.lat);
			var marker = new AMap.Marker({
				map : this.map,
				position : location
			});
			if(options.icon){
				var icon = new AMap.Icon({
					size:new AMap.Size(45,50),
					image:options.icon,
					imageSize:new AMap.Size(45,50)
				});
				marker.setIcon(icon);
				marker.setOffset(new AMap.Pixel(-25,-35));
			}
			if(options.label){
				marker.setLabel({
			        offset: new AMap.Pixel(-15, -25),
			        content: "<div class='" + options.labelClass + "'>" + options.label + "</div>"
			    });
			}
			marker.location = location;
			marker.name = options.name;
			marker.address = options.address;
			marker.on('click',function(evt){
			});
			return marker;
		},
		//坐标转换及获取当前最近的考勤点/签到点
		attendLocationsConvert : function(currentPoint,callback){
			var self = this;
			var fdLocations = this.fdLocations;
			var attendLocation = null;
			if(!this.fdLocations || this.fdLocations.length ==0){
				callback && callback();
				return;
			}
			for(var i =0;i<fdLocations.length;i++){
				var coord = fdLocations[i].coord;
				if(coord){
					var address = fdLocations[i].address;
					var curLimit = fdLocations[i].distance;
					var distance = MapUtil.getDistance(MapUtil.formatCoord(currentPoint,5),fdLocations[i].coord);
					if(attendLocation){
						if(attendLocation.distance > distance){
							this.curLimit=curLimit;
							attendLocation = {
								point:coord,
								address :address,
								distance : distance
							};
						}
					}else{
						this.curLimit=curLimit;
						attendLocation = {
							point:coord,
							address :address,
							distance : distance
						};
					}
				}
			}
			if(attendLocation) {
				var coordType = MapUtil.getCoordType(attendLocation.point);
				var fdLatLng = MapUtil.getCoord(attendLocation.point);
				var point = new AMap.LngLat(fdLatLng.lng, fdLatLng.lat);
				if (coordType == 5) {
					attendLocation.point = point;
					this.attendLocation = attendLocation;
					callback();
					return;
				}
				AMap.convertFrom(point, 'baidu', function (status, res) {
					if (status == 'complete') {
						attendLocation.point = res.locations[0];
						self.attendLocation = attendLocation;
						callback();
					} else {
						self.showError();
					}
				});
			}else{
				callback();
			}
		},
		
		showAttendLimit : function(options){
			if(this.circle){
				this.map.remove(this.circle);
			    //this.map.setFitView();
			}
			var zoomLevel=17;//地图放大的倍数
			if(this.fdLimit){
				zoomLevel = this.fdLimit<=200 ? 17:16;
			}else if(this.fdType=="1"||this.fdType==1){
				zoomLevel = this.curLimit<=200 ? 17:16;
				options.radius=this.curLimit;
			}
			var circle = this.circle = new AMap.Circle({
		        center: options.point,
		        radius: parseInt(options.radius), //半径
		        strokeOpacity: 1,
		        strokeWeight: 1,
		        strokeOpacity: 0.2,
		        fillOpacity: 0.3,
		        fillColor: '#1791fc',
		        zIndex: 50,
		    })

		    circle.setMap(this.map);
		    // 缩放地图到合适的视野级别
		    //this.map.setFitView([ circle ]);
			this.map.setZoom(zoomLevel);
			 
		},
		createInfoWindow : function(options){
		}
		
	});
});