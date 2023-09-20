<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/list_top.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<link rel="stylesheet" href="<c:url value="/eop/basedata/resource/css/importResult.css"/>" />
<script type="text/javascript">
	Com_IncludeFile("jquery.js");
</script>
<center>
<div style="display: none" id="resultDiv">${rtnObj}</div>
<script>
	Com_AddEventListener(window,'load',function(){
		window.parent.dia.hide($("#resultDiv").text());
	});
</script>
</center>
<%@ include file="/resource/jsp/list_down.jsp"%>
