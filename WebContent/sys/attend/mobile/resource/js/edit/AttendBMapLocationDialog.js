define([
    "dojo/_base/declare","dojo/dom-style","dojo/dom-class","mui/map","dojo/query",
    "dojo/topic","dojo/dom-construct","dojox/mobile/ScrollableView",
    "sys/attend/map/mobile/resource/js/baiduMap/LocationDialog",
    "mui/device/adapter","sys/attend/map/mobile/resource/js/common/MapUtil",
	"mui/util","mui/coordtransform","mui/i18n/i18n!sys-attend"
	], function(declare,domStyle,domClass,map,query,topic,domConstruct,ScrollableView,LocationDialog,adapter,MapUtil,util,coordtransform,Msg) {
	
	return declare("sys.attend.AttendBMapLocationDialog", [LocationDialog], {
		
		renderList : function(){
		},
		renderMap : function(){
			var self = this;
			this.map.enableScrollWheelZoom();
			domClass.add(this.contentdom,'muiLocationContent');
		},
		renderToolbar:function(){
		},
		_initGeoControl:function(){},
		
		show : function(evt){
			var self = this;
			this.fdLocations = evt.fdLocations;
			this.fdLimit = evt.fdLimit;
			this.radius = evt.radius;//圆半径
			this.fdType=evt.fdType;
			
			domStyle.set(this.domNode,'display','block');
			adapter.getCurrentCoord(function(result){
				var from = 3;//gcj02坐标
				if(result.coordType==1){
					from = 1;//wgs84坐标
				}
				var ___point = new BMap.Point(result.lng,result.lat);
				if(result.coordType!=3){
					new BMap.Convertor().translate([___point],from,5,function(res){
						if(res.status==0){
							self.attendLocationsConvert(res.points[0],function(){
								self._setPosition(res.points[0],{})
							});
						}else{
							self.showError();
						}
					});
				}else{
					self.attendLocationsConvert(___point,function(){
						self._setPosition(___point,{})
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
			//最近的考勤点位置信息
			var attendLocation = this.attendLocation;
			var curLimit=this.curLimit;
			map.getBMapLocation({coordType:3,lat:fdLatLng.lat,lng:fdLatLng.lng,getPoi:0},function(result){
				var title = result.title;
				var address = result.address;
				var distance = attendLocation ? self.map.getDistance(attendLocation.point,fdLatLng): NaN;
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
				self.map.clearOverlays();
				var marker = self.createMarker(markerPoit);
				if(attendLocation){
					var attendMarker = self.createMarker({
						point:attendLocation.point,
						label:'<span>'+ Msg['mui.sign.zone']+'</span>'
					});
					self.showAttendLimit({point:attendLocation.point,radius:self.fdLimit});
				}else{
					self.map.centerAndZoom(fdLatLng,17);
				}
				self.map.panTo(fdLatLng);
				
				topic.publish('sys/attend/relocation/complete',self,{
					fdLatLng:MapUtil.formatCoord(fdLatLng,3),
					address:address,//当前位置
					fdLocation: fdLocation,//考勤位置
					distance: distance,
					inside: inside,
					curLimit:curLimit,
				});
			},function(){
				self.showError();
			});
		},
		createMarker : function(options){
			var self = this;
			var point = options.point,
				marker = new BMap.Marker(point),
				infoWindow = this.createInfoWindow(options);
			this.map.addOverlay(marker);
//			this.defer(function(){
//				this.map.centerAndZoom(point,zoomLevel);
//			},300);
			if(options.icon){
				var icon = new BMap.Icon(options.icon,new BMap.Size(45,50),{
					anchor:new BMap.Size(25, 45)
				});
				icon.setImageSize(new BMap.Size(45,50));
				marker.setIcon(icon);
			}
			if(options.label){
				var label = new BMap.Label(options.label,{offset:new BMap.Size(-5,-20)});
				marker.setLabel(label);
			}
			
			marker.point = options.point;
			marker.title = options.title;
			marker.address = options.address;
			marker.infoWindow = infoWindow;
			marker.addEventListener('click',function(evt){
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
				if(coord) {
					var address = fdLocations[i].address;
					var curLimit = fdLocations[i].distance;
					var distance = MapUtil.getDistance(MapUtil.formatCoord(currentPoint, 3), fdLocations[i].coord);
					if (attendLocation) {
						if (attendLocation.distance > distance) {
							this.curLimit = curLimit;
							attendLocation = {
								point: coord,
								address: address,
								distance: distance
							};
						}
					} else {
						this.curLimit = curLimit;
						attendLocation = {
							point: coord,
							address: address,
							distance: distance
						};
					}
				}
			}
			if(attendLocation) {
				var coordType = MapUtil.getCoordType(attendLocation.point);
				var fdLatLng = MapUtil.getCoord(attendLocation.point);
				var point = new BMap.Point(fdLatLng.lng, fdLatLng.lat);
				if (coordType == 3) {
					attendLocation.point = point;
					this.attendLocation = attendLocation;
					callback();
					return;
				}
				new BMap.Convertor().translate([point], 3, 5, function (res) {
					if (res.status == 0) {
						attendLocation.point = res.points[0];
						self.attendLocation = attendLocation;
						callback();
					} else {
						self.showError();
					}
				});
			} else {
				callback();
			}
		},
		
		showAttendLimit : function(options){
			var zoomLevel=17;//地图放大的倍数
			if(this.fdLimit){
				zoomLevel = this.fdLimit<=200 ? 17:16;
			}else if(this.fdType=="1"||this.fdType==1){
				zoomLevel = this.curLimit<=200 ? 17:16;
				options.radius=this.curLimit;
			}
			var circle = new BMap.Circle(options.point,options.radius,{
				strokeColor:'#1791fc',
				fillColor:'#1791fc',
				strokeWeight:2,
				strokeOpacity:0.6,
				fillOpacity:0.3,
				strokeStyle:'solid'
			});
			this.map.addOverlay(circle);
			this.map.centerAndZoom(options.point,zoomLevel);
		},
		createInfoWindow : function(options){
		}
		
	});
});