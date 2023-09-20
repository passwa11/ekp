
var thisEvent="";
var isFirefox=navigator.userAgent.indexOf("Firefox")>-1;
/**
 * 全选
 * @param id
 * @return
 */
function allSelect(){
	if(arguments.callee.caller!=null&&arguments.callee.caller.arguments.length>0){
		thisEvent=arguments.callee.caller.arguments[0];
	}else{
		if(!isFirefox){
			thisEvent=window.event; 
		}else{
			thisEvent=arguments[0];
		}
	}
	if(method!="view"){
		var srcElement =  thisEvent.srcElement ? thisEvent.srcElement : thisEvent.target;
		var id = srcElement.id;
		var name;
		$("#"+id+" input[type='checkbox']").each(function(){
			this.checked = true;
			name = this.name;
		});	
		srcElement.onclick=cancelAllSelect;
		if(isFirefox){
			srcElement.textContent= "取消";
		}else{
			srcElement.innerText = "取消";
		}
		select__valueChange(name);
	}
}

/**
 * 取消全选
 * @param id
 * @return
 */
function cancelAllSelect(){
	 if(arguments.callee.caller!=null&&arguments.callee.caller.arguments.length>0){
			thisEvent=arguments.callee.caller.arguments[0];
		}else{
			if(!isFirefox){
				thisEvent=window.event;
			}else{
				thisEvent=arguments[0];
			}
			
		}
	var srcElement =  thisEvent.srcElement ? thisEvent.srcElement : thisEvent.target;
	var id = srcElement.id;
	var name;
	$("#"+id+" input[type='checkbox']").each(function(){
		this.checked = false;
		name = this.name;
	});	
	srcElement.onclick=allSelect;
	if(isFirefox){
		srcElement.textContent= "全选";
	}else{
		srcElement.innerText = "全选";
	}
	select__valueChange(name);
}

function select__valueChange(name){
	if(name){
		__cbClick(name.substring(1),'null',false,null);
	}
}

/**
 * 取消选择
 * @param id
 * @return
 */
function cancelSelect(id){
	if(method!="view"){
		$("#" + id + " input[type='radio']").each(function() {
			this.checked = false;
		});
		// 清空隐藏域
		$("#" + id + " div input[type='radio']").attr('value', '')[0].checked = true;
	}
}
