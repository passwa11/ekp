define([ "dojo/_base/declare","dojo/_base/lang","mui/search/SearchBar","dojo/touch",
		"dojo/dom-attr","dojo/dom-class","dojo/dom-style","dojo/dom-construct","dojo/topic","dojo/Deferred",
		"dojox/mobile/ScrollableView",'./LocationMarkerMixin'],
		function(declare,lang,SearchBar,touch,domAttr,domClass,domStyle ,domConstruct,topic,Deferred,ScrollableView,LocationMarkerMixin){
	
	
	return declare("sys.attend.locationSearch",[ SearchBar,LocationMarkerMixin ],{
		
		searchListNode : null,
		
		map : null,

		buildRendering : function(){
			this.inherited(arguments);
		},
		
		startup : function(){
			this.subscribe('sys/attend/locationDialog/show','handleDialogShow');
			this.subscribe('sys/attend/locationDialog/hide','handleDialogHide');
		},
		
		handleDialogShow : function(obj, evt){
			if(obj != this.__parent__)
				return;
			//this.createAutocomplete();
			this.keydownHandle = this.connect(this.searchNode,'keydown','_onKeydown')
		},
		
		createAutocomplete : function(){
			var self = this;
			if(!this.autoCompletet){
				this.autoCompletet = new qq.maps.place.Autocomplete(this.searchNode,{
					//offset : new qq.maps.Size(10000,10000)
				});
				qq.maps.event.addListener(this.autoCompletet,'confirm',lang.hitch(this,function(evt){
					 var searchService = new qq.maps.SearchService({
						 map : self.map,
						 complete : function(results){
							 
						 }
					 });
					 searchService.search(evt.value);
				}));
			}
		},
		
		handleDialogHide : function(obj){
			if(obj != this.__parent__)
				return;
			this.disconnect(this.keydownHandle);
			this.searchNode.value = '';
		},
		
		_onKeydown : function(e){
			var keyCode = e.keyCode;
			if(e.keyCode == 13 && e.target && e.target.value){
				var value = e.target.value;
				this.search(value);
				this.searchNode.blur();
			}
		},
		
		search : function(value){
			var self = this;
			var citylocation = new qq.maps.CityService({
		        complete : function(result){
		        	var city = result.detail.name;
		        	var searchService = new qq.maps.SearchService({
						 complete : function(results){
							 var pois = results.detail.pois;
							 if(!pois || pois.length == 0)
								 return;
							 var currentPoi = null;
							 var surroundingPois = [];
							 for(var i = 0;i < pois.length;i++){
								 var markerPoit = {
										 point : new qq.maps.LatLng(pois[i].latLng.lat,pois[i].latLng.lng),
										 location : new qq.maps.LatLng(pois[i].latLng.lat,pois[i].latLng.lng),
										 title : pois[i].name,
										 name : pois[i].name,
										 address : pois[i].address
								 };
								 if(i == 0){
									currentPoi = markerPoit;
								 }else{
									 surroundingPois.push(markerPoit);
								 }
							 }
							 topic.publish('/location/poi/dataChange', self,{pois:surroundingPois,location:currentPoi});
						 }
					 });
		        	searchService.setLocation(city);
		        	searchService.search(value);
		        }
		    });
		    citylocation.searchLocalCity();
			//清除地图上的覆盖物
			this.clearOverlays();
		}
		
	});
	
	
	
});