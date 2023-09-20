define(['dojo/_base/lang','dojo/topic','dojo/dom-construct','mui/util','dojo/query','sys/attend/map/mobile/resource/js/baiduMap/BaiduMap','sys/attend/map/mobile/resource/js/common/MapUtil','./AttendBMapLocationDialog'],
		function(lang,topic,domConstruct,util,query,baseMap,MapUtil,AttendBMapLocationDialog){
	return lang.mixin(baseMap,{
		_openLocationDialog : function(evt){
			var self = this;
			if(!window['muiAttendBMapLocationDialog']){
				
				var dialog = window['muiAttendBMapLocationDialog'] = new AttendBMapLocationDialog({
					showStatus : 'view',
					isShowList : false,
					isShowSearch:false
				});
				
				dialog.startup();
				domConstruct.place(dialog.domNode,query('#map')[0],'last');
			}
			evt.showStatus = 'view';
			window['muiAttendBMapLocationDialog'].show(evt);
		}
	});
});