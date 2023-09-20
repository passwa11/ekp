define([ "dojo/_base/declare", "third/ctrip/xform/resource/mobile/js/CommonRequireValidate","third/ctrip/xform/resource/mobile/js/TravelPeopleValidate",
	"third/ctrip/xform/resource/mobile/js/DateValidate"], 
		function(declare,CommonRequireValidate,TravelPeopleValidate,DateValidate) {
	var claz = declare("third.ctrip.xform.resource.mobile.js.HotelService", null, {
		
		validateTasks : [],
		
		relationInfoDom : null,
		
		constructor : function(relationInfoDom){
			this.relationInfoDom = relationInfoDom;
			//入店时间必填
			this.validateTasks.push(new CommonRequireValidate("hotelEntryDate_relationId"));
			//离店时间必填
			this.validateTasks.push(new CommonRequireValidate("hotelBeginDate_relationId"));
			// 入店城市必填
			this.validateTasks.push(new CommonRequireValidate("hotelToCity_relationId"));
			// 出行人必填
			this.validateTasks.push(new TravelPeopleValidate("hotelPassengerList_relationId","hotelRetinueList_relationId"));
			// 入店时间必须早于最晚入店时间
			this.validateTasks.push(new DateValidate("hotelEntryDate_relationId","hotelLatestEntryDate_relationId"));
			// 离店时间必须早于最晚离店时间
			this.validateTasks.push(new DateValidate("hotelBeginDate_relationId","hotelEndDate_relationId"));
			// 入店时间必须比离店时间要早
			this.validateTasks.push(new DateValidate("hotelEntryDate_relationId","hotelBeginDate_relationId"));
		},
		
		validate : function(){
			var rs = {'status':'01','msg':''};
			for(var i = 0;i < this.validateTasks.length;i++){
				rs = this.validateTasks[i].validate(this.relationInfoDom);
				if(rs.status == '00'){
					break;
				}
			}
			return rs;
		}
		
	});
	return claz;
});