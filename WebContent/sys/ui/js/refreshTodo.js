
define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	var todoFlag="";
	var autoRefreshTime=0; 
	var todoRefreshTime=null;
	/**
	 * 第一个参数是回调函数，
	 * 第二个参数是第一次是否执行第一个参数
	 */
	exports.initRefreshTodo = function(callBack,initCallBack) {
		//获取刷新待办的心跳时间 
		LUI.$.getJSON(Com_Parameter.ContextPath + "data/sys-notify/sysNotifyPortlet/getNotifyAutoRefreshTime?"+Math.random(),function(json){
			if(json!=null){ 
				if(json.data && json.data.refreshTime && json.data.refreshTime > 0){
					//如果Redis开启了自动更新则使用redis自动更新的定时器时间来执行验证是否更新
					autoRefreshTime =json.data.refreshTime*1000; 
					if(json.data.flag){									
						todoFlag =json.data.flag;
					}
					todoRefreshTime=0;
					checkTodoChange(callBack);
				}
				if(initCallBack  instanceof Function){ 
					initCallBack(todoRefreshTime);
				}
			}
		});
	}
	
	var checkTodoChange = function(callBack){
		var url=Com_Parameter.ContextPath + "data/sys-notify/sysNotifyPortlet/checkTodoChange?todoFlag="+todoFlag+"&"+Math.random(); 
		LUI.$.getJSON(url,function(check){
			if(check && check.data){
				if(check.data.flag){					
					todoFlag =check.data.flag; 
				}
				if(check.data.oper=='load'){ 
					if(callBack instanceof Function){ 
						callBack();
					} 
				}
			} 
			if(autoRefreshTime > 0){
				//重新设置定时器执行
				window.setTimeout(function(){
					checkTodoChange(callBack);
				},autoRefreshTime); 
			} 
		});  
	};
});