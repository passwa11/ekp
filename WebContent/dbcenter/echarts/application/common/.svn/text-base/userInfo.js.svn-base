/**
 * 获取对象信息
 */
(function(){
	function UserInfo(){
		this.getItems = UserInfo_GetItems;
	}
	
	function UserInfo_GetItems(type,modelName){
		if(!modelName || modelName == ''){
			modelName = "com.landray.kmss.sys.organization.model.SysOrgPerson";
		}
		var items = [];
		$.ajax({
			type : "get",
			async : false,//是否异步
			url : Com_Parameter.ContextPath + "dbcenter/echarts/application/dbEchartsApplication.do?method=getModelInfo&modelName=" + modelName,
			dataType : "json",
			success : function(rn) {
				for(var i = 0;i < rn.length;i++){
					var item = rn[i];
					// 同类型或者对象类型的
					if((item.fieldType.toLowerCase() == type.toLowerCase()) || item.fieldType.indexOf("com.landray.kmss") > -1){
						items.push(item);
					}
				}
			}
		});
		return items;
	}
	
	window.userInfo = new UserInfo();
})()
