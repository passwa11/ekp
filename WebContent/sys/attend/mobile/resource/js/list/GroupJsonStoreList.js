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
	
	return declare("sys.attend.mobile.resource.js.list.GroupJsonStoreList", [CalendarJsonStoreList], {
		
		formatData:function(datas){
			var data=[];
			if(!datas.data){
				return data;
			}
			var calendars=datas.data.calendars;
			for(var key in calendars){
				data=data.concat(calendars[key]);
			}
			return data;
		},
		
		quicksort:function(datas,left,right){
			this.inherited(arguments);
		},
		
		hasCalendar:function(args){
			args.startDate.setHours(0,0,0,0);
			args.endDate.setHours(0,0,0,0);
			var result={},
				tmpDate=args.startDate,
				__caches=this.__caches;
			while(dateClz.compare(tmpDate,args.endDate) <= 0){
				var key=cutil.formatDate(tmpDate);
				if(__caches[key] && __caches[key].length >0 ){
					var datas = __caches[key];
					if(datas.length > 0 && datas[0].fdType==2){
						result[key] = '1';//蓝点
					}else{
						for(var i = 0 ;i < datas.length;i++){
							if((datas[i].fdSignedStatus==0 || datas[i].fdSignedStatus==2 ||datas[i].fdSignedStatus==3
									|| datas[i].fdOsdReviewType==1 && datas[i].fdSignedStatus==1 && datas[i].fdSignedOutside==true)
										&& datas[i].fdState!=2){
								result[key] = '0';//红点
								break;
							}
						}
						if(!result[key]){
							result[key] = '1';
							for(var i = 0 ;i < datas.length;i++){
								if(datas[i].fdSignedOutside==true){
									result[key] = '2';//橙点
									break;
								}
							}
						}
					}
					
				}
				tmpDate=dateClz.add(tmpDate,"day",1);
			}
			return result;
		}
		
	});
});