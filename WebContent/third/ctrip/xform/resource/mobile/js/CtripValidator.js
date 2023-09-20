define([ "dojo/_base/declare", "third/ctrip/xform/resource/mobile/js/PlaneService","third/ctrip/xform/resource/mobile/js/HotelService"], 
		function(declare, PlaneService, HotelService) {
	var claz = declare("third.ctrip.xform.resource.mobile.js.CtripValidator", null, {
		
		bookTypeWgt : null, // 预订类型的控件
		
		relationInfoDom : null, // 记录关联信息的组件
		
		validateService : {}, // 校验业务：飞机、酒店
		
		owner : null, // 兼容校验框架用
		
		constructor : function(bookTypeWgt, relationInfoDom){
			this.bookTypeWgt = bookTypeWgt;
			this.relationInfoDom = relationInfoDom;
			// 添加校验
			this.validateService["plane"] = new PlaneService(relationInfoDom);
			this.validateService["hotel"] = new HotelService(relationInfoDom);
		},
		
		doOwnValidate : function(){
			var self = this;
			var rs = {'status':'01','msg':''};
			var types = self.bookTypeWgt.value.split(";");
			if(types.length == 0){
				alert("携程控件要求  \"预订类型\" 必填!");
				return false;
			}
			for(var i = 0;i < types.length;i++){
				if(self.validateService.hasOwnProperty(types[i])){
					rs = self.validateService[types[i]].validate();
					if(rs.status == '00'){
						break;
					}
				}
			}
			if(rs.status == '00'){
				alert(rs.msg);
				return false;
			}
			return true;
		},
		
		setParamValues : function(){
			// 兼容校验框架用
		},
		
		setOwner:function(ownerArgu){
			// 兼容校验框架用
			this.owner = ownerArgu || this;
		}
		
	});
	return claz;
});