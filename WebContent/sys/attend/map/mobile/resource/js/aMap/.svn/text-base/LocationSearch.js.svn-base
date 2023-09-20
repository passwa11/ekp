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
			this.createAutocomplete();
			this.keydownHandle = this.connect(this.searchNode,'keydown','_onKeydown')
		},
		
		createAutocomplete : function(){
			if(!this.autoCompletet){
				AMap.service(['AMap.Autocomplete'],lang.hitch(this,function(){
					this.autoCompletet = new AMap.Autocomplete({
						input : this.searchNode
					});
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
			var defer = new Deferred();
			//清除地图上的覆盖物
			this.map.clearMap();
			this.autoCompletet.search(value,lang.hitch(this,function(code,result){
				if(result.tips && result.tips.length > 0){
					var tip = result.tips[0];
					topic.publish('/location/poi/dataChange', self,{ 
						pois : result.tips.slice(1), 
						location : tip
					});
				}else{
					topic.publish('/location/poi/dataChange', self,{pois:[],location:{}});
					return;
				}
			}));
		}
		
	});
	
	
	
});