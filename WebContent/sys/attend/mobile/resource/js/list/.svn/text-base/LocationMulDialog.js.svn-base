define([
    "dojo/_base/declare","dojo/dom-style","dojo/dom-class",
    "dojo/topic","dojo/dom-construct",
    "sys/attend/map/mobile/resource/js/baiduMap/LocationDialog",
	"mui/util","sys/attend/mobile/resource/js/list/AttendSwapLocInfo"
	], function(declare,domStyle,domClass,topic,domConstruct,LocationDialog,util,AttendSwapLocInfo) {
	
	return declare("sys.attend.mobile.resource.js.list.LocationMulDialog", [LocationDialog], {
		
		buildRendering : function(){
			this.inherited(arguments);
			this.init();
			this.renderMap();
			this.renderList();
			domClass.add(this.domNode,'muiLocationDialog');
		},
		
		startup : function(){
			this.inherited(arguments);
			this.subscribe('sys/attend/location/markerpanto','panTo');
		},
		
		renderMap : function(){
			var self = this;
			this.map.enableScrollWheelZoom();
			domClass.add(this.contentdom,'muiLocationContent');
		},
		
		renderList : function(){
			if(this.locationInfo)
				return;
			var locationInfo = this.locationInfo = new AttendSwapLocInfo({
				map : this.map,
				height : '5rem'
			});
			locationInfo.startup();
			domConstruct.place(locationInfo.domNode,this.listdom,'last');
		},
		
		createInfoWindow : function(options){
			var self = this;
			var dom = domConstruct.create('div',{className : 'muiLocationInfoWindow' }),
				title = domConstruct.create('div',{ className:'muiLocationInfoWindowName', innerHTML: options.title },dom);
			return new BMap.InfoWindow(dom);
		},
		
		show : function(evt){
			var self = this;
			domStyle.set(this.domNode,'display','block');
			topic.publish('sys/attend/locationDialog/show',this,evt);
			
			if(evt && evt.datas && evt.datas.length>0){
				var _datas = evt.datas;
				for(var i=0; i<_datas.length; i++) {
					(function(index, data) {
						var __point = new BMap.Point(data.fdLng, data.fdLat);
						var options = {
							point : __point,
							title : data.fdLocation || ''
						};
						self.createMarker(options);
					})(i,_datas[i])
				}
				this.panTo({lng:_datas[0].fdLng, lat:_datas[0].fdLat});
			}
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