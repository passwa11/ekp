define( [ "dojo/_base/declare","dojo/_base/lang", "dojo/topic","dojo/dom-construct"],
		function(declare,lang, topic, domConstruct){
	
	return declare("sys.attend.aMap.LocationMarkerMixin",[],{
		
		startup : function(){
			this.inherited(arguments);
		},
		
		createMarker : function(options){
			var self = this,
				location = options.location instanceof qq.maps.LatLng ?  options.location : new qq.maps.LatLng(options.location.lat,options.location.lng);
			this.markerArray = this.markerArray || [];
			var marker = new qq.maps.Marker({
				map : this.map,
				position : location
			});
			var infoWindow = this.createInfoWindow(options);
			infoWindow.setPosition(marker);
			marker.location = location;
			marker.name = options.name;
			marker.address = options.address;
			marker.infoWindow = infoWindow;
			qq.maps.event.addListener(marker,'click',function(evt){
//				var target = evt.target;
//				if(target){
//					self.openInfoWindow(target)
//				}
			});
			this.markerArray.push(marker);
			return marker;
		},
		
		createInfoWindow : function(options){
			var self = this,
				location = options.location instanceof qq.maps.LatLng ?  options.location : new qq.maps.LatLng(options.location.lat,options.location.lng);
			var dom = domConstruct.create('div',{className : 'muiLocationInfoWindow' }),
				title = domConstruct.create('div',{ className:'muiLocationInfoWindowName', innerHTML: options.name },dom),
				address = domConstruct.create('div',{ className : 'muiLocationInfoWindowAddress' ,innerHTML: '地址：' + options.address},dom);
			var infoWindow = new qq.maps.InfoWindow({
				map : self.map,
				content : dom.outerHTML
			});
			this.infoWindowArray  = this.infoWindowArray || [];
			this.infoWindowArray.push(infoWindow);
			return infoWindow;
		},
		
		openInfoWindow : function(marker){
			var infoWindow = marker.infoWindow;
			infoWindow.open();
		},
		
		clearOverlays : function(){
			if (this.markerArray && this.markerArray.length > 0) {
		        for (var i = 0;i < this.markerArray.length;i++) {
		            this.markerArray[i].setMap(null);
		        }
		        this.markerArray.length = 0;
		    }
		}
		
	});
	
});