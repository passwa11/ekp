<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.zone.util.SysZoneConfigUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script>
/* 导航信息的链接 */
function __navLinkUrl(fdUrl, serverPath, key){
	if(fdUrl.indexOf("http://") == 0 || fdUrl.indexOf("https://") == 0){
		return fdUrl;
	}
	var currentKey  = "<%=SysZoneConfigUtil.getCurrentServerGroupKey()%>";
	if(currentKey == key || !serverPath ){
		serverPath = "${ LUI_ContextPath }";
	}
	fdUrl = serverPath + fdUrl;
	var param = "LUIID=iframe_body&userId=${sysOrgPerson.fdId}&userSex=${sysOrgPerson.fdSex}&isSelf=${isSelfNoPower}&zone_TA=${zone_TA}";
	var index = fdUrl.indexOf("?");
	if(index >= 0){
		if(index == fdUrl.length - 1){
			fdUrl += param; 
		}else{
			fdUrl += "&" + param;
		}
	}else if(index < 0){
		fdUrl += "?" + param; 
	}
	
	return fdUrl;
}




	$(function() {
		//var row_show = "${row_show}";
		var navLinkSize = "${navLinkSize}";
		var px = (parseInt(150)*(parseInt(navLinkSize) + 1))+'px';
		$(".nav_link_body").width(px);
		if(navLinkSize>7){
			$(".left_row").show();
			$(".right_row").show();
			
		}
	});

	


function  left_drag_nav(){
	var frame=$(".nav_link_frame");
	var xl = frame.scrollLeft();
	var scro = xl - 300;
	frame.animate({scrollLeft: scro}, 200);
} ;

function  right_drag_nav(){
	var frame=$(".nav_link_frame");
	var xl = frame.scrollLeft();
	var scro = xl + 300;
	frame.animate({scrollLeft: scro}, 200);
} 


</script>