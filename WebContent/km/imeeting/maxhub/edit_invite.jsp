<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@ page import="com.landray.kmss.util.IDGenerator"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
 String fdId = request.getParameter("fdId");

 request.setAttribute("fdId", fdId);


%>
<template:include ref="maxhub.edit">

	<template:replace name="head">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/km/imeeting/maxhub/resource/css/edit.css?s_cache=${MUI_Cache}">
	</template:replace>

	<template:replace name="content">
		<form action="${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=mhuInvite" method="post" 
			id="saveForm" name="saveForm" style="height: 100%; box-sizing: border-box; padding: 2rem;">
			<section class="mhui-main-content">
				<!-- 二维码 start -->
				<div class="mhui-row" style="height: auto;">
					<div class="mhui-col-xs-12" style="text-align: center">
						<div id="qrcode" class="qrcode"></div>
						<div class="qrcode-tips">扫一扫加入会议</div>
					</div>
			    </div>
				<!-- 二维码 end -->			 
				  
				<!-- 列表 start -->
			    <div class="mhui-row" style="height: auto;">
					<div class="mhui-col-xs-12">
						<div id="attenPersons" data-dojo-type="mhui/list/ItemListBase"
							data-dojo-mixins="km/imeeting/maxhub/resource/js/list/AddressItemListMixin"
							data-dojo-props="url:'${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=mhuGetAttendPersons&fdId=${fdId}&key=mhuKmImeetingInvite'">
						</div>
					</div>
			    </div>
			    <input type="hidden" name="fdId" id="fdId" value="${fdId}"/>
			    <input type="hidden" name="key" id="key"  value="mhuKmImeetingInvite"/>
		    	<input type="hidden" name="attendIds" id="attendIds" />
			    <!-- 列表 end -->
			</section>
		</form>
		<script type="text/javascript">
		var fdId = "${fdId}";
		</script>
		<script type="text/javascript" src="${LUI_ContextPath}/km/imeeting/maxhub/resource/js/edit_invite.js"></script>
	</template:replace>	
</template:include>