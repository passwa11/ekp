define( [ "dojo/_base/declare","dojo/dom-style", "dojo/dom-construct", "dojo/dom-class", "dojo/topic",
          "mui/util", "dojo/touch", "dojox/mobile/_css3","dojox/mobile/TransitionEvent","dijit/registry",'dojo/query'  ],
        function(declare, domStyle, domConstruct, domClass, topic, util, touch, css3,TransitionEvent,registry,query) {
	return declare("notify.search.SearchBarDialogMixin", null , {
			//模块标识
			modelName : "",
			
			searchCancelEvt : "/mui/search/cancel",

			searchShowEvt : "/mui/searchbar/show",
			
			show:function(evt){
				var opts = {
						moveTo:'cardSearchView'
					};
				new TransitionEvent(evt.target, opts).dispatch();
				document.getElementById("keyWord").value='';
				topic.publish("/mui/list/adjustDestination",this,{y:0});
				topic.publish("/mui/search/showsearch");
				this.setSearchType();
			},
			
			hideSearchBar : function(srcObj) {
			},
			setSearchType:function(){
				var navBar =registry.byId('_navBar');
				var currentItem = navBar.selectedItem;
				var dataType = "todo";
				var fdType = util.getUrlParameter(currentItem.url,'fdType');
				var oprType = util.getUrlParameter(currentItem.url,'oprType');
				var _dataType = util.getUrlParameter(currentItem.url,'dataType');
				if(fdType=='2' && oprType=='doing' || _dataType=='toview'){
					dataType = 'toview';
				}
				if(_dataType=='toviewdone'){
					dataType = 'toviewdone';
				}
				if(_dataType=='tododone' || oprType=='done'){
					dataType = 'tododone';
				}
				var selObj = registry.byNode(query('.muiNotifySearchType')[0]);
				selObj.setValue(dataType);
			}
	});
});