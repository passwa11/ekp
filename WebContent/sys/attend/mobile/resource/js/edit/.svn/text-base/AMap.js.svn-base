define(['dojo/_base/lang','dojo/topic','dojo/dom-construct','mui/util','dojo/query','sys/attend/map/mobile/resource/js/aMap/AMap','sys/attend/map/mobile/resource/js/common/MapUtil','./AttendAMapLocationDialog'],
		function(lang,topic,domConstruct,util,query,baseMap,MapUtil,AttendAMapLocationDialog){
	return lang.mixin(baseMap,{
		
		_openLocationDialog : function(evt){
			var self = this;
			if(!window['muiAttendAMapLocationDialog']){
				
				var dialog = window['muiAttendAMapLocationDialog'] = new AttendAMapLocationDialog({
					showStatus : 'view',
					isShowList : false,
					isShowSearch:false
				});
				
				dialog.startup();
				domConstruct.place(dialog.domNode,query('#map')[0],'last');
			}
			evt.showStatus = 'view';
			window['muiAttendAMapLocationDialog'].show(evt);
		}
	
	});
});