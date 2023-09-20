define([ "dojo/_base/declare","mui/search/SearchBar","dojo/touch","dojo/dom-class","dojo/dom-style","dojo/dom-construct","dojo/topic",
		"./LocationMarkerMixin"],
		function(declare,SearchBar,touch,domClass,domStyle ,domConstruct,topic,LocationMarkerMixin){
	
	return declare("sys.attend.locationSearch",[ SearchBar, LocationMarkerMixin ],{
		
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
			this.createAutocomplete();
			this.connect(this.searchNode,'keydown','_onKeydown')
		},
		
		handleDialogHide : function(obj){
			if(obj != this.__parent__)
				return;
			this.destoryAutocomplete();
			this.searchNode.value = '';
		},
		
		createAutocomplete : function(){
			var  self = this;
			var autocomplete = this.autocomplete =  new BMap.Autocomplete({
				'input' : this.searchNode,
				'location' : this.map
			});
			autocomplete.addEventListener("onconfirm",function(e){
				var value =  e.item.value;
				self.search(value);
			});
		},
		
		destoryAutocomplete : function(){
			if(this.autocomplete){
				this.autocomplete.dispose();
			}
		},
		
		search : function(value){
			var self = this;
			//清除地图上所有覆盖物
			this.map.clearOverlays();
			var searchMax = 1;
			var keyword = value;
			if(value instanceof Object){
				keyword = value.province +  value.city +  value.district +  value.street +  value.business;
				searchMax = 10;
			}
			function onSearchComplete(){
				//获取第一个智能搜索的结果
				var count = Math.min(local.getResults().getCurrentNumPois(),searchMax),
					geoc = new BMap.Geocoder(); 
				if(count==0){
					topic.publish('/location/poi/dataChange', self,{pois:[],location:{}});
					return;
				}
				for(var i = 0; i < count; i++){
					var poi = local.getResults().getPoi(i),
						point = poi.point;
					geoc.getLocation(point,(function(index, _point, _poi){
						//debugger;
						return function(rs){
							var markerPoit = {
									point : _point,
									title : _poi.title,
									address : rs.address
							};
							var marker = self.createMarker(markerPoit);
							if(index == 0){
								//self.openInfoWindow(marker);
								topic.publish('/location/poi/dataChange', self,{pois:rs.surroundingPois,location:markerPoit});
							}
								
							//marker.openInfoWindow(marker.infoWindow);
						}
						
					})(i,point,poi));
				}
			}
			var local = new BMap.LocalSearch(this.map, {
				  onSearchComplete: onSearchComplete
				});
			local.search(keyword);
		},
		
		_onKeydown : function(e){
			var keyCode = e.keyCode;
			if(e.keyCode == 13 && e.target && e.target.value){
				var value = e.target.value;
				this.search(value);
				this.searchNode.blur();
			}
		}
		
	});
	
});