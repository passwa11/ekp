define([
    "dojo/_base/declare",
    "dojo/topic",
    'dojo/when',
    "mui/calendar/CalendarJsonStoreList",
	"dojo/date",
	"mui/util",
	'dojo/_base/lang',
	"dojo/request",
	"mui/calendar/CalendarUtil"
	], function(declare,topic,when,CalendarJsonStoreList,dateClz,util,lang,request,cutil) {
	
	return declare("sys.modeling.main.calendar.GroupJsonStoreList", [CalendarJsonStoreList], {
		
		formatData:function(datas){
			var data=[];
			if(!datas.data){
				return data;
			}
			var calendars=datas.data;
			for(var key in calendars){
				data=data.concat(calendars[key]);
			}
			return data;
		},
		
		quicksort:function(datas,left,right){
			this.inherited(arguments);
		}
		
	});
});