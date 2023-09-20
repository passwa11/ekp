define([
    "dojo/_base/declare","dojo/dom-style","dojo/dom-class","mui/map","dojo/query",
    "dojo/topic","dojo/dom-construct","dojox/mobile/ScrollableView",
    "sys/attend/map/mobile/resource/js/qqMap/LocationDialog",
    "mui/device/adapter","sys/attend/map/mobile/resource/js/common/MapUtil",
	"mui/util","mui/i18n/i18n!sys-attend"
	], function(declare,domStyle,domClass,map,query,topic,domConstruct,ScrollableView,LocationDialog,adapter,MapUtil,util,Msg) {
	
	return declare("sys.attend.AttendQMapLocationDialog", [LocationDialog], {
		
		renderList : function(){
		},
		renderMap : function(){
			var self = this;
			this.map.setZoom(16);
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
				var latLng = new qq.maps.LatLng(result.lat,result.lng);
				var type = 3;//百度经纬度
				if(result.coordType==1){
					type = 1;//gps经纬度
				}
				if(result.coordType!=5){
					qq.maps.convertor.translate(latLng,type, function(res){
						self.attendLocationsConvert(res[0],function(){
							self._setPosition(res[0],{})
						});
					});
				}else{
					self.attendLocationsConvert(latLng,function(){
						self._setPosition(latLng,{})
					});
				}
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
			map.getQMapLocation({coordType:5,lat:fdLatLng.lat,lng:fdLatLng.lng,getPoi:0},function(result){
				var title = result.title;
				var address = result.address;
				var distance = attendLocation ? MapUtil.getDistance(MapUtil.formatCoord(fdLatLng,5),MapUtil.formatCoord(attendLocation.point,5)):NaN;
				var fdLocation = result.address;
				var inside = false;
				var iconUrl = "/sys/attend/mobile/resource/image/myora.png";
				if((self.fdType==1||self.fdType=="1")&& (self.fdLimit==null||self.fdLimit=="")){
					if(attendLocation && self.fdLocations && self.fdLocations.length>0){
						for(var arr in self.fdLocations){
							if(self.fdLocations[arr].distance && distance <= parseFloat(self.fdLocations[arr].distance)){
								iconUrl = "/sys/attend/mobile/resource/image/myblue.png";
								fdLocation = attendLocation ? attendLocation.address : result.address;
								inside = true;
								break;
							}
						}
					}
				}else{
					if(attendLocation && self.fdLocations && self.fdLocations.length>0 && self.fdLimit && distance <= parseFloat(self.fdLimit)){
						iconUrl = "/sys/attend/mobile/resource/image/myblue.png";
						fdLocation =attendLocation ? attendLocation.address : result.address;
						inside = true;
					}
				}
				var currentPoi = {
					name : result.title,
					point : fdLatLng,
					address : result.address,
					icon:util.formatUrl(iconUrl,true)
				};
				var marker = self.createMarker(currentPoi);
				if(attendLocation){
					var attendMarker = self.createMarker({
						point:attendLocation.point,
						label:'<span>' +Msg['mui.sign.zone']+'</span>'
					});
					self.showAttendLimit({point:attendLocation.point,radius:self.fdLimit});
				}
				self.map.setCenter(fdLatLng);
				
				topic.publish('sys/attend/relocation/complete',self,{
					fdLatLng:MapUtil.formatCoord(fdLatLng,5),
					address:address,//当前位置
					fdLocation:fdLocation,//考勤位置
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
				location = options.point instanceof qq.maps.LatLng ?  options.point : new qq.maps.LatLng(options.point.lat,options.point.lng);
			this.markerArray = this.markerArray || [];
			var marker = new qq.maps.Marker({
				map : this.map,
				position : location
			});
			if(options.icon){
				var anchor = new qq.maps.Point(22, 35),
		        size = new qq.maps.Size(45, 50),
		        origin = new qq.maps.Point(0, 0),
		        scaleSize = new qq.maps.Size(45, 50),
		        icon = new qq.maps.MarkerImage(options.icon,size,origin,anchor,scaleSize);
				marker.setIcon(icon);
			}
			if(options.label){
				var label = new qq.maps.Label({
	                position: options.point,
	                offset: new qq.maps.Size(-25, -60),
	                map: self.map,
	                content: options.label
	            });
			}
			marker.location = location;
			marker.name = options.name;
			marker.address = options.address;
			qq.maps.event.addListener(marker,'click',function(evt){
			});
			this.markerArray.push(marker);
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
					var distance = MapUtil.getDistance(MapUtil.formatCoord(currentPoint, 5), fdLocations[i].coord);
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
				var point = new qq.maps.LatLng(fdLatLng.lat, fdLatLng.lng);
				if (coordType == 5) {
					attendLocation.point = point;
					this.attendLocation = attendLocation;
					callback();
					return;
				}
				qq.maps.convertor.translate(point, 3, function (res) {
					attendLocation.point = res[0];
					self.attendLocation = attendLocation;
					callback();
				});
			} else {
				callback();
			}
		},
		
		showAttendLimit : function(options){
			if(this.circle){
				this.circle.setMap(null);
			}
			if(this.fdLimit){

			}else if(this.fdType=="1"||this.fdType==1){
				options.radius=this.curLimit;
			}
			var circle = this.circle = new qq.maps.Circle({
                map: this.map,
                center: options.point,
                radius: parseInt(options.radius) || 500,
                fillColor: new qq.maps.Color(0,15,255, 0.1),
                strokeWeight: 2
            });
			circle.setMap(this.map);
		}
		
	});
});