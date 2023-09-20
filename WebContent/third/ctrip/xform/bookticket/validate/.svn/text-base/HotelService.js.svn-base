/**
 * 
 */
(function(){
	function HotelService(){
		
		this.validateTasks = [];
		
		this._init = HotelService_Init;
		this.validate = HotelService_Validate;
		
		this._init();
	}
	
	function HotelService_Init(){
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
	}
	
	function HotelService_Validate(domNode){
		var rs = {'status':'01','msg':''};
		for(var i = 0;i < this.validateTasks.length;i++){
			rs = this.validateTasks[i].validate(domNode);
			if(rs.status == '00'){
				break;
			}
		}
		return rs;
	}
	
	return window.HotelService = HotelService;
})()