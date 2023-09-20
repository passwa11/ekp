<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="mobile.view">
	<template:replace name="head">
		<link rel="Stylesheet" href="<%=request.getContextPath() %>/sys/mobile/css/themes/default/view.css?s_cache=${MUI_Cache}"/>
		<style>
			.mblScrollableViewContainer {
				min-width:100%;
			}
			.muiNormal td {
				padding:4px 6px;
				border:1px solid #eee;
			}
		</style>
	</template:replace>
	<template:replace name="title">
		自定义数据
	</template:replace>
	<template:replace name="content">
		 <center>
				<c:if test="${'0'!=param.showButton}">
					<p class="txttitle">${dbEchartsCustomForm.docSubject}</p>
					<br/>		
				</c:if>
		  </center>
		<div class="lui-chartData-info-board" >
		   <div class="board-txt">${customTxt }</div>
		</div>
		
	</template:replace>
</template:include>
