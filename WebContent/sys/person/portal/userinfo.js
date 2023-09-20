/**
* 退出登录
* @return
*/  
function __sys_logout(){
	seajs.use(['lui/jquery','lui/dialog'],function($,dialog){
		dialog.confirm(theaderMsg["home.logout.confirm"],function(value){
			if(value){
				location.href=Com_Parameter.ContextPath+"logout.jsp";
			}				
		});
	});
}

/**
* 根据地址栏?号后面的参数名称获取参数值
* @param name 参数名称
* @return
*/
function getQueryString(name){
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return unescape(r[2]); return null;
}

/**
* 根据地址栏#号后面的Hash参数名称获取参数值
* @param name 参数名称
* @return
*/ 
function getHashParamValue(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var p = window.location.hash.substring(1);
	if(p==""){
	  return null;
	}
    var r = p.match(reg);
    if (r != null){
	  return decodeURIComponent(r[2]);
	}else{
	  return null;
	} 
}

/**
* 跳转至指定场所下的页面 
* @param targetPortalInfo 目标场所默认可访问的门户页面信息对象
* @return
*/ 
function toAreaPage(targetPortalInfo){
	// 兼容IE8，给字符串对象添加startsWith方法
	if (typeof String.prototype.startsWith !== 'function') {
	    String.prototype.startsWith = function(prefix) {
	        return this.slice(0, prefix.length) === prefix;
	    };
	}
	// 兼容IE8，给字符串对象添加endsWith方法
	if (typeof String.prototype.endsWith !== 'function') {
	    String.prototype.endsWith = function(suffix) {
	        return this.indexOf(suffix, this.length - suffix.length) !== -1;
	    };
	}	
	
	var targetPortalIsQuick = targetPortalInfo.portalIsQuick; // 目标场所默认可访问的门户页面是否为极速模

	if(LUI.pageMode()=="quick"){ // ①、当前页面为 “ 极速模式 ”
		
		var j_start = getHashParamValue("j_start");
	    
		// [极速模式] 下判断当前是否在门户页
		if( j_start==null || j_start.startsWith("/sys/portal/page.jsp") ){
			
			var requestUrl = Com_Parameter.ContextPath+"sys/portal/page.jsp";
			window.location.href = requestUrl;
			
		}else{
			
			if(targetPortalIsQuick){ // 目标场所门户为极速模式
				var requestUrl = Com_Parameter.ContextPath+"sys/portal/page.jsp"+window.location.hash;
				window.location.href = requestUrl;
				if(window.location.href.endsWith(requestUrl)){
					window.location.reload();
				}
			}else{ // 目标场所门户为普通模式
				var requestUrl = Com_Parameter.ContextPath+( j_start.startsWith("/") ? j_start.substring(1) : "" );
				window.location.href = requestUrl;
			}

		}
							
	}else{ // ②、当前页面为 “ 普通模式 ”
		
		// [普通模式] 下判断当前是否在门户页
		if(location.pathname.endsWith("/sys/portal/page.jsp")){
			var requestUrl = Com_Parameter.ContextPath+"sys/portal/page.jsp";
			window.location.href = requestUrl;	
		}else{
			var modulePath = window.location.href.substring((window.location.origin+Com_Parameter.ContextPath).length-1);
			var requestUrl = Com_Parameter.ContextPath+"sys/portal/page.jsp";
			var hashParamStr = "#j_start="+encodeURIComponent(modulePath)+"&j_target=_iframe";
			requestUrl+=hashParamStr;
			window.location.href = requestUrl;
		}
		
	}
}

/**
* 切换场所
* @return
*/  
function __switchArea(){
	var url = '/sys/person/portal/areaTree.jsp?service='+encodeURIComponent('sysAuthAreaSwitchService&id=!{value}');
	seajs.use(['lui/dialog','lui/jquery'],function(dialog,$){
		dialog.iframe(url,theaderMsg["dialog.locale.winTitle"],function(rtnData){
			if(rtnData && rtnData.value){
				var authAreaId = rtnData.value; // 场所ID
				var authAreaName = rtnData.text; // 场所名称
				$.get(Com_Parameter.ContextPath+"sys/authorization/sys_auth_area/sysAuthArea.do?method=switchArea&areaId="+authAreaId,function(data){
					// 判断是否需要切换到场所对应的门户
					if( data && data.switchToAreaPortal==true ){
						if(data.targetPortalInfo){
							toAreaPage(data.targetPortalInfo);
						}else{
							var tipText =  authAreaName+" "+theaderMsg["authArea.not.found.portalPage.tip"];
							alert(tipText); // 场所下未找到可访问的门户页面 
						}
					}else{
						location.reload();
					}
				});
			}
		},{width:500,height:500});
	});
}