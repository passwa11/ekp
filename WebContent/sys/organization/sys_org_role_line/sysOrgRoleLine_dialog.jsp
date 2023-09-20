<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="x-ua-compatible" content="IE=5"/>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="Pragma" content="No-Cache">
		<script>
			function returnMessge(id, name, isExternal, type){
				returnValue = {text:name, value:id, nodeType:type, isExternal:isExternal};
				if(window.$dialog!=null){
					$dialog.hide({"rtnVal":returnValue});
				}else{
					window.close();
				}
			}
		</script>
	</head>
	<frameset frameborder=0 border=0>
  		<frame src="sysOrgRoleLine.do?${HtmlParam.query}">
	</frameset>
</html>