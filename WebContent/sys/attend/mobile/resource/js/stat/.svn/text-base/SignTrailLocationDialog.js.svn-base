define([
    "dojo/_base/declare","dojo/dom-style","dojo/dom-class",
    "dojo/topic","dojo/dom-construct","dojox/mobile/ScrollableView",
    "sys/attend/map/mobile/resource/js/baiduMap/LocationDialog",
    "sys/attend/mobile/resource/js/stat/SignTrailPersonView",
	"mui/util","mui/coordtransform",'sys/attend/mobile/resource/js/stat/SignTrailLocationMarkerMixin'
	], function(declare,domStyle,domClass,topic,domConstruct,ScrollableView,LocationDialog,SignTrailPersonView,util,coordtransform,SignTrailLocationMarkerMixin) {
	
	return declare("sys.attend.mobile.resource.js.list.SignTrailLocationDialog", [LocationDialog,SignTrailLocationMarkerMixin], {
		
		renderList : function(){
			this.signTrailPersionView = new SignTrailPersonView({
				map : this.map,
				height : '5rem'
			});
			this.signTrailPersionView.startup();
			var scrolView = new ScrollableView();
			domConstruct.place(scrolView.domNode,this.listdom,'last');
			domConstruct.place(this.signTrailPersionView.domNode,scrolView.domNode,'last');
			scrolView.startup();
			domClass.add(this.listdom,'muiLocationListDom muiSignTrailLocationList');
			domClass.add(scrolView.domNode,'muiSignTrailScrollableView');
		},
		renderToolbar:function(){
		},
		_initGeoControl:function(){},
		
		show : function(evt){
			var self = this;
			domStyle.set(this.domNode,'display','block');
		
			topic.publish('sys/attend/locationDialog/show',this,evt);
			
			this.map.clearOverlays();
			
			if(evt && evt.datas && evt.datas.length>0){
				var _datas = evt.datas;
				for(var i=0; i<_datas.length; i++) {
					(function(index, data) {
						var __point = new BMap.Point(data.fdLng, data.fdLat);
						if(data.coordType==5){
							var coordArr = coordtransform.gcj02tobd09(data.fdLng, data.fdLat);
							__point = new BMap.Point(coordArr[0],coordArr[1]);
						}
						var options = {
							point : __point,
							title : index + 1
						};
						setTimeout(function(){
							self.createMarker(options);
						},350);
						
					})(i,_datas[i])
				}
				var options = {lng:_datas[0].fdLng, lat:_datas[0].fdLat};
				if(_datas[0].coordType==5){
					var coordArr = coordtransform.gcj02tobd09(options.lng, options.lat);
					__point = new BMap.Point(coordArr[0],coordArr[1]);
				}
				this.panTo(options);
			}
			
			//class
			var showStatus = evt.showStatus || this.showStatus;
			if(showStatus == 'edit'){
				domClass.add(this.domNode,'edit');
			}else{
				domClass.remove(this.domNode,'edit');
			}
		},
		panTo :function(evt){
			if(evt && evt.lng && evt.lat){
				var __point = new BMap.Point(evt.lng, evt.lat);
				this.map.panTo(__point);
			}
		}
		
	});
});