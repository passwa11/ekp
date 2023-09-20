define( [ "dojo/_base/declare","dojo/_base/lang", "dojo/topic","dojo/dom-construct",'dojo/on','dojo/touch'],
		function(declare,lang, topic, domConstruct,on, touch){
	
	return declare("sys.attend.SignTrailLocationMarkerMixin", [], {
		
		createMarker : function(options){
			var SerialMarker = function(point, text){
				this._point = point;
				this._text = text;
			};
			SerialMarker.prototype = new BMap.Overlay();
			SerialMarker.prototype.initialize = function(map){
				this._map = map;
				var self = this;
				
				var markerDom = domConstruct.toDom('<span class="muiSignMarker"><i>'+ this._text +'</i></span>');
				
				on(markerDom, touch.press, function(){
					if(self._text) {
						topic.publish('/signTrail/stat/list/scroll', { index:  parseInt(self._text) -1});
					}
				});
				
			    map.getPanes().markerPane.appendChild(markerDom);
			    this.markerDom = markerDom;
			    return markerDom;
			};
			SerialMarker.prototype.draw = function(){
				var map = this._map;
			    var position = map.pointToOverlayPixel(this._point);
			    this.markerDom.style.left = position.x + "px";    
			    this.markerDom.style.top = position.y + "px";
			};
			
			var point = options.point,
				text = options.title,
				marker = new SerialMarker(point, text);
			this.map.addOverlay(marker);
			this.defer(function(){
				this.map.centerAndZoom(point,15);
			},300);
			marker.addEventListener('click',function(evt){
			});
			return marker;
		},
		
		createInfoWindow : function(options){
		},
		
		openInfoWindow : function(marker){
		}
		
	});
	
});