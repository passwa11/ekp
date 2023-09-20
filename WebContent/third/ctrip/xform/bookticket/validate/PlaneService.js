/**
 * 
 */
(function(){
	function PlaneService(){
		
		this.validateTasks = [];
		
		this._init = PlaneService_Init;
		this.validate = PlaneService_Validate;
		
		this._init();
	}
	
	function PlaneService_Init(){
		// 出发日期必填
		this.validateTasks.push(new CommonRequireValidate("beginDate_relationId"));
		// 出发城市必填
		this.validateTasks.push(new CommonRequireValidate("fromCity_relationId"));
		// 到达城市必填
		this.validateTasks.push(new CommonRequireValidate("toCity_relationId"));
		// 出行人必填
		this.validateTasks.push(new TravelPeopleValidate("passengerList_relationId","retinueList_relationId"));
		// 出发日期不能晚于到达日期
		this.validateTasks.push(new DateValidate("beginDate_relationId","endDate_relationId"));
		// 离店日期不能晚于最晚离店时间
		//this.validateTasks.push(new DateValidate("hotelBeginDate_relationId","hotelEndDate_relationId"));
	}
	
	function PlaneService_Validate(domNode){
		var rs = {'status':'01','msg':''};
		for(var i = 0;i < this.validateTasks.length;i++){
			rs = this.validateTasks[i].validate(domNode);
			if(rs.status == '00'){
				break;
			}
		}
		return rs;
	}
	
	return window.PlaneService = PlaneService;
})()