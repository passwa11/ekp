seajs.use(['lui/jquery', 'lui/topic'], function($, topic) {
	LUI.ready(function() {
		// 获取“应用”hash参数，用于提供“应用”的初始化
		var appHashParamInfo = __getAppHashParamInfo();
		var app_title = appHashParamInfo["c_app_title"];
		var app_url = appHashParamInfo["c_app_url"];
		if(app_title && app_url){
			app_title = decodeURIComponent(app_title); 
			app_url = decodeURIComponent(app_url);
			// 获取渲染“应用”的DataView对象，并触发该对象的appNavChanged（应用切换）事件，目的为了通过该事件来进行当前应用标题修改
			var $dataViewDom = $(".lui_tlayout_header_app_popup").find("div.lui-component[data-lui-type='lui/base!DataView']");
			if($dataViewDom.length == 0) {
				return;
			}
			var dataViewId = $dataViewDom.attr("id")||$dataViewDom.attr("data-lui-cid");
            var dataView = LUI(dataViewId);
            if(dataView){
            	dataView.emit("appNavChanged",{
            		"channel": "switchAppNav",
            		"text": app_title,
            		"target": "_self",
            		"element": dataView.element
            	});
            	// 将应用数据设置至DataView对象中，提供给应用窗口辨识需要默认选中的应用项（详见sys/portal/render/navTree.js）
            	var default_app_info = {
        			"default_app_title" : app_title,
        			"default_app_url" : app_url
            	}
            	dataView._default_app_info = default_app_info;
            }
		}
	});
});

/**
* 获取应用Hash参数
*/
function __getAppHashParamInfo(){
	
	// 1、从浏览器地址栏中获取“应用”hash参数
	var app_title = getHashParamValue("c_app_title"); // 应用标题
	var app_url = getHashParamValue("c_app_url"); // 应用URL
	
	// 2、从父窗口临时变量中获取“应用”hash参数
	if(!app_title && !app_url){
		__initAppHashParamFromParentWindow();
		app_title = getHashParamValue("c_app_title");
		app_url = getHashParamValue("c_app_url");
	}
	
	// 3、从Cookie中获取“应用”hash参数
	if(!app_title && !app_url){
		app_title = LUI.getHashParamFromCookie("c_app_title");
		app_url = LUI.getHashParamFromCookie("c_app_url");
		if(app_title && app_url){
			__prependHashParamToURL({"c_app_title":app_title,"c_app_url":app_url});
		}
	}
	
	return {"c_app_title":app_title,"c_app_url":app_url};
}


/**
* 从父窗口获取hash参数，追加至浏览器地址栏URL的hash参数中
* （取值后将父窗口对象中的临时hash参数对象_child_window_hash_params清空，该变量值只允许使用一次）
*/
function __initAppHashParamFromParentWindow(){
	var parentWindowObj = window.opener;
	if(parentWindowObj){
		try {
			var currentAppHashParams = parentWindowObj._child_window_hash_params;
		} catch(e){}
		if(currentAppHashParams){
        	var hashParam = "";
			for(key in currentAppHashParams){
				var value = currentAppHashParams[key];
				if(key.indexOf("c_")==0){
                    var singleParam = key+"="+value;
					hashParam+=(hashParam==""?singleParam:"&"+singleParam);
				}
			}
			var locationHash = location.hash;
			if(locationHash){
				location.hash = locationHash+"&"+hashParam;
			}else{
				location.hash = hashParam;
			}
			parentWindowObj._child_window_hash_params = null;
		}
	}	
}


/**
* 往地址栏URL中追加自定义Hash参数
* @param url      URL地址
* @param customHashParams  自定义Hash参数
* @return
*/
function __prependHashParamToURL(customHashParams){
	var locationHash = window.location.hash;
	if(locationHash.indexOf("#")==0){
		locationHash = locationHash.substring(1);
	}
   	var hashParam = "";
	for(key in customHashParams){
		var value = customHashParams[key];
		if(key.indexOf("c_")==0 && locationHash.indexOf(key+"=")==-1){
            var singleParam = key+"="+value;
			hashParam+=(hashParam==""?singleParam:"&"+singleParam);
		}
	}
    if(hashParam!=""){
    	window.location.hash = locationHash?locationHash+"&"+hashParam:hashParam;
    }
}


/**
* 打开页面（个人设置、个人空间、我的订阅......）
* @return
*/
function __onOpenPage(url,target){
	if(LUI.pageMode()=='quick'){
		url = Com_SetUrlParameter(url,'j_aside','true');
		LUI.pageOpen(url,'_iframe');
	}else{
		window.open(url,target);
	}
}

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

/**
 * 登录
 * 用于匿名页眉 @author 吴进 by 20191112
 * @returns
 */
function __sys_login() {
	location.href=Com_Parameter.ContextPath+'login.jsp';
}

