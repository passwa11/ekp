define(['dojo/_base/lang','dojo/topic','dojo/dom-construct','mui/util','dojo/query','sys/attend/map/mobile/resource/js/qqMap/QQMap','sys/attend/map/mobile/resource/js/common/MapUtil','./AttendQMapLocationDialog'],
		function(lang,topic,domConstruct,util,query,baseMap,MapUtil,AttendQMapLocationDialog){
	return lang.mixin(baseMap,{
		
		_openLocationDialog : function(evt){
			var self = this;
			if(!window['muiAttendQMapLocationDialog']){
				
				var dialog = window['muiAttendQMapLocationDialog'] = new AttendQMapLocationDialog({
					showStatus : 'view',
					isShowList : false,
					isShowSearch:false
				});
				
				dialog.startup();
				domConstruct.place(dialog.domNode,query('#map')[0],'last');
			}
			evt.showStatus = 'view';
			window['muiAttendQMapLocationDialog'].show(evt);
		}
	
	});
});