define( [ "dojo/_base/declare","dojo/_base/lang", "dojo/topic","dojo/dom-construct"],
		function(declare,lang, topic, domConstruct){
	
	return declare("sys.attend.aMap.LocationMarkerMixin",[],{
		
		startup : function(){
			this.inherited(arguments);
			this.initEvent();
		},
		
		initEvent:function(){
			var self = this;
			if(this.showStatus == 'edit' && this.isShowSearch && !this.poiRadius){
				this.map.on('touchstart',function(e){
					var lnglat = e.lnglat;
					AMap.plugin(['AMap.Geocoder'],function(){
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
				            	
								var __options = {
						              name: title,
						              location: point,
						              address: address
						            };
						          var pois = []
					              var nearPois = surroundingPois;
					              if (nearPois && nearPois.length > 0) {
					                for (var i = 0; i < nearPois.length; i++) {
					                  var latLng = new AMap.LngLat(
					                    nearPois[i].location.lng,
					                    nearPois[i].location.lat
					                  )
					                  var poi = {
					                    name: nearPois[i].name,
					                    title: nearPois[i].name,
					                    location: latLng,
					                    point: latLng,
					                    address: nearPois[i].address
					                  };
					                  if(i==0){
					                	  __options = poi;
					                  }
					                  pois.push(poi);
					                }
						           }
					              self.map.clearMap();
					              self.map.panTo(__options.location);
						          self.createMarker(__options);
						            topic.publish("/location/poi/dataChange", self, {
						              pois: pois,
						              location: __options
						            });
				            }
				        });
					});
					
				});
			}
		},
		
		createMarker : function(options){
			var self = this,
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
					self.openInfoWindow(target)
				}
			});
			return marker;
		},
		
		createInfoWindow : function(options){
			var self = this,
				location = options.location instanceof AMap.LngLat ?  options.location : new AMap.LngLat(options.location.lng,options.location.lat);
			var dom = domConstruct.create('div',{className : 'muiLocationInfoWindow' }),
				title = domConstruct.create('div',{ className:'muiLocationInfoWindowName', innerHTML: options.name },dom),
				address = domConstruct.create('div',{ className : 'muiLocationInfoWindowAddress' ,innerHTML: '地址：' + options.address},dom);
			return new AMap.AdvancedInfoWindow({
				placeSearch : false,
				content : dom.outerHTML,
				position : location,
				offset : new AMap.Pixel(0,-30)
			});
		},
		
		openInfoWindow : function(marker){
			var infoWindow = marker.infoWindow;
			infoWindow.open(this.map,marker.location);
		}
	});
	
});