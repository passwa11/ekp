define(function(require, exports, module) {

    /**
    * 根据消息类型获取相应的（j_path）访问路径 
    * @param dataTypes 消息类型(可能是字符串，如："todo" 也可能是数组，如["todo","toview","suspend"])
    * @return
    */
	function getJPath(dataTypes) {
		
		// 定义访问路径（默认为处理类的待处理）
		var path = "/process&dataType=todo";  
		if(dataTypes){
			var dataType = dataTypes.split(";")[0];
			
			// 处理类（待处理、已处理）
	        if(dataType=="todo"||dataType=="tododone"){
	        	path = "/process&dataType="+dataType;
	        }
	        
	        // 阅读类（待处理、已处理）
	        if(dataType=="toview"||dataType=="toviewdone"){
	        	path = "/read&dataType="+dataType;
	        }
	        
	        // 系统通知类（待处理、已处理）
	        if(dataType=="systoview"||dataType=="systoviewdone"){
	        	path = "/system&dataType="+dataType;
	        }	        
		}

        return path;
	}
	
	
	/**
    * 门户组件 根据消息类型获取相应的（j_path）访问路径 
    * @param dataTypes 消息类型(可能是字符串，如："todo" 也可能是数组，如["todo","toview","suspend"])
    * @return
    */
	function getPortletJPath(dataTypes) {
		console.log("getPortletJPath");
		// 定义访问路径（默认为处理类的待处理）
		var path = "/process&dataType=todo";  
		if(dataTypes){
			var dataType = dataTypes.split(";")[0];
			
			// 处理类（待处理、已处理）
	        if(dataType=="todo"||dataType=="tododone"){
	        	if(dataTypes.indexOf('suspend')==-1){
	        		path = "/process&dataType="+dataType+"&cri.list_todo.q=fdType:1";
	        	}
	        } 
	        
	        if(dataType=="suspend"){
	        	path = "/process&dataType="+dataType+"&cri.list_todo.q=fdType:3";
	        }
	        
	        // 阅读类（待处理、已处理）
	        if(dataType=="toview"||dataType=="toviewdone"){
	        	path = "/read&dataType="+dataType;
	        }
	        
	        // 系统通知类（待处理、已处理）
	        if(dataType=="systoview"||dataType=="systoviewdone"){
	        	path = "/system&dataType="+dataType;
	        }	        
		}

        return path;
	}


	exports.getJPath = getJPath;
	
	exports.getPortletJPath = getPortletJPath;

});