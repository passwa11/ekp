define( [ "dojo/_base/declare","dojo/_base/lang", "dojo/topic","dojo/dom-construct"],
		function(declare,lang, topic, domConstruct){
	
	return declare("sys.attend.LocationMarkerMixin",[],{
		
		startup : function(){
			this.inherited(arguments);
			this.bindMarkerEvent();
		},
		
		bindMarkerEvent : function(){
			var self = this;
			if(this.showStatus == 'edit' && this.isShowSearch && !this.poiRadius){
				this.map.addEventListener("touchstart", function(evt){
					var geoc = new BMap.Geocoder(), 
					 	point = evt.point;
					geoc.getLocation(point,function(rs){
						if(!rs){
							return;
						}
						var surroundingPois = rs.surroundingPois;
						if(surroundingPois && surroundingPois.length > 0){
							//清除地图上所有覆盖物
							self.map.clearOverlays();
							var poi = surroundingPois[0];
							var markerPoit = {
									point : point,
									title : poi.title,
									address : poi.address
							};
							var marker = self.createMarker(markerPoit);
							topic.publish('/location/poi/dataChange', self ,{pois:rs.surroundingPois,location:markerPoit});
						}
					},{poiRadius:500,numPois:50});
				});
			}
		},
		
		createMarker : function(options){
			var self = this;
			var point = options.point,
				marker = new BMap.Marker(point),
				infoWindow = this.createInfoWindow(options);
			this.map.centerAndZoom(point,15);
			this.map.addOverlay(marker);
			this.defer(function(){
				this.map.centerAndZoom(point,15);
			},300);
			marker.point = options.point;
			marker.title = options.title;
			marker.address = options.address;
			marker.infoWindow = infoWindow;
			marker.addEventListener('click',function(evt){
//				evt.domEvent.stopPropagation();
//				var target = evt.target;
//				if(target){
//					self.openInfoWindow(target)
//				}
			});
			return marker;
		},
		
		createInfoWindow : function(options){
			var self = this;
			var dom = domConstruct.create('div',{className : 'muiLocationInfoWindow' }),
				title = domConstruct.create('div',{ className:'muiLocationInfoWindowName', innerHTML: options.title },dom),
				address = domConstruct.create('div',{ className : 'muiLocationInfoWindowAddress' ,innerHTML: '地址：' + options.address},dom);
			return new BMap.InfoWindow(dom);
		},
		
		openInfoWindow : function(marker){
			marker.openInfoWindow(marker.infoWindow);
		}
	});
	
});