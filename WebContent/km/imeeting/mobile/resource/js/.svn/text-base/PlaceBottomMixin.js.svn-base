define(['dojo/_base/declare', 'dojo/topic'], 
		function(declare, topic) {
	
	return declare('km.imeeting.PlaceBottomMixin', null, {
		
		handleCreate: function(){
			
			if(this.start[0] || this.finish[0]){
				
				this._setTime4Create();
				
				topic.publish('/km/imeeting/create', this, {
					fdTime: this.fdTime,
					fdStartTime: this.fdStartTime,
					fdFinishTime: this.fdFinishTime,
					resId: this.placeId
				});
				
				//this.meetingUrl += "&fdTime="+this.fdTime+"&fdStratTime="+this.fdStartTime+"&fdFinishTime="+this.fdFinishTime+"&resId="+this.placeId;
			}else{
				alert("请选择会议时间段");
			}
		}
		
	});
});