define([ "dojo/_base/declare", "third/ctrip/xform/resource/mobile/js/CommonRequireValidate","third/ctrip/xform/resource/mobile/js/TravelPeopleValidate",
			"third/ctrip/xform/resource/mobile/js/DateValidate"], 
		function(declare, CommonRequireValidate,TravelPeopleValidate) {
	var claz = declare("third.ctrip.xform.resource.mobile.js.PlaneService", null, {
		
		validateTasks : [],
		
		relationInfoDom : null,
		
		constructor : function(relationInfoDom){
			this.relationInfoDom = relationInfoDom;
			// 添加校验
			// 出发日期必填
			this.validateTasks.push(new CommonRequireValidate("beginDate_relationId"));
			// 出发城市必填
			this.validateTasks.push(new CommonRequireValidate("fromCity_relationId"));
			// 到达城市必填
			this.validateTasks.push(new CommonRequireValidate("toCity_relationId"));
			// 出行人必填
			this.validateTasks.push(new TravelPeopleValidate("passengerList_relationId","retinueList_relationId"));
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