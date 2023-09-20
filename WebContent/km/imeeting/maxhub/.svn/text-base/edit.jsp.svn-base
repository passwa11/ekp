<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="java.util.Map"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.km.imeeting.service.IKmImeetingBookService"%>
<%@ page import="com.landray.kmss.util.IDGenerator"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<template:include ref="maxhub.edit">
<%
	String fdId=IDGenerator.generateID(); 
	request.setAttribute("fdId", fdId);
	
	String userName=UserUtil.getUser().getFdName();
	request.setAttribute("userName", userName);
	
	request.setAttribute("meetingName", request.getParameter("meetingName"));
	request.setAttribute("fdHoldDuration", "0.5");
	
	String bookId = request.getParameter("bookId");
	request.setAttribute("bookId", bookId);
	if(StringUtil.isNotNull(bookId)){
		IKmImeetingBookService service = (IKmImeetingBookService)SpringBeanUtil.getBean("kmImeetingBookService");
		Map map = service.getDataById(bookId);
		if(map.size()>0){
			String bookName=map.get("fdName").toString();
			String bookHoldDuration=map.get("fdHoldDuration").toString();
			String fdHoldDate=map.get("fdHoldDate").toString();
			String fdFinishDate = map.get("fdFinishDate").toString();
			
			request.setAttribute("meetingName", bookName);
			request.setAttribute("fdHoldDuration", bookHoldDuration);
			request.setAttribute("fdHoldDate", fdHoldDate);
			request.setAttribute("fdFinishDate", fdFinishDate);
		}
	}
	request.setAttribute("placeId", request.getParameter("placeId"));
	
%>
	<template:replace name="head">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/km/imeeting/maxhub/resource/css/edit.css?s_cache=${MUI_Cache}">
	</template:replace>

	<template:replace name="content">
	
		<form action="${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=mhuSave" method="post" id="saveForm" name="saveForm">
			<!-- 主体内容区 Starts -->
			<section class="mhui-main-content">
			
				<div class="mhui-row">
					<div data-dojo-type="mhui/nav/StepNav"
						data-dojo-props="data:[{text:'基本信息'},{text:'邀请人员'}],canHandleNavClick:false"></div>
				</div>
				
				<div class="mhui-row" id="editBase" style="padding: 0px 18rem;">
					<table class="mhui-table" data-dojo-type="mhui/form/Form" id="newForm">
						<tr>
							<td class="mhuiFormTitle">会议名称：</td>
							<td>
								<div data-dojo-type="mhui/form/Input" 
									data-dojo-props="name:'title',isRequired:true,
										value:'${meetingName }',
										placeholder:'请输入会议名称'"></div>
							</td>
						</tr>
						<tr>
							<td class="mhuiFormTitle">历时：</td>
							<td>
								<div id="duration"
									data-dojo-type="mhui/form/Selector" 
									data-dojo-mixins="km/imeeting/maxhub/resource/js/SelectorMixin"
									data-dojo-props="name:'duration',
										options:[
											<c:forEach begin="0" end="15" varStatus="varstatus">
												{value: '${ varstatus.count / 2 }', unit: '<bean:message key="date.interval.hour"/>'},
											</c:forEach>
											<c:if test="${bookId != ''}">
											{value: '${fdHoldDuration}', unit: '<bean:message key="date.interval.hour"/>'},
											</c:if>
										],
										<c:if test="${bookId != ''}">
										editable: false,
										</c:if>
										<c:if test="${bookId == ''}">
										lazy:true,
										</c:if>
										/*lazy:true,*/
										value:'${fdHoldDuration}',
										placeholder:'请选择时长',
										isRequired:true"></div>
									
							</td>
						</tr>
						<tr>
							<td class="mhuiFormTitle">主持人：</td>
							<td>
								<div data-dojo-type="mhui/form/Input" 
									data-dojo-props="name:'hostPerson',value:'${userName}',editable:false,isRequired:true"></div>
							</td>
						</tr>
					</table>
			    </div>
			    
			    <div class="mhui-row mhui-hidden" style="text-align: center;" id="editQrcode">
		   			<div class="qrcode-tips" style="font-size: 2.2rem;">扫一扫加入会议</div>
					<div id="qrcode" class="qrcode"></div>
					<div>
						<span class="qrcode-nums" id="attendNums">总人数：0</span>
					</div>
				</div>
				
				<div class="mhui-row mhui-hidden">
					<div id="attenPersons" data-dojo-type="mhui/list/ItemListBase"
						data-dojo-mixins="km/imeeting/maxhub/resource/js/list/AddressItemListMixin"
						data-dojo-props="url:'${LUI_ContextPath}/km/imeeting/km_imeeting_main/kmImeetingMain.do?method=mhuGetAttendPersons&fdId=${fdId}&key=mhuKmImeeting'">
					</div>
				</div>
						    
			    <input type="hidden" name="fdId" id="fdId" value="${fdId}"/>
			    <input type="hidden" name="attendIds" id="attendIds" />
			    <input type="hidden" name="placeId" id="placeId" value="${placeId}"/>
			    <input type="hidden" name="bookId" id="bookId" value="${bookId}"/>
			    <input type="hidden" name="fdHoldDate" id="fdHoldDate" value="${fdHoldDate}"/>
			    <input type="hidden" name="fdFinishDate" id="fdFinishDate" value="${fdFinishDate}"/>
			</section>
			
		</form>
		
		<script type="text/javascript">
			window.fdId = "${fdId}";
			window.placeId = "${placeId}";
			window.bookId = "${bookId}";
			window.fdHoldDate = "${fdHoldDate}";
			window.fdFinishDate = "${fdFinishDate}"
		</script>
		
		<div class="mhuiDialogToolbar" id="baseButton">
		
			<div class="mhuiDialogBtn" onclick="dialogCancle()" data-step="0">取消</div>
			<div class="mhuiDialogBtn mhuiDialogPrimaryBtn" onclick="next()" data-step="0">邀请人员</div>
			<div class="mhuiDialogBtn mhui-hidden" onclick="prev()" data-step="1">上一步</div>
			<div class="mhuiDialogBtn mhuiDialogPrimaryBtn mhui-hidden" onclick="submit()" data-step="1">开始会议</div>
			
		</div>
		
		<script type="text/javascript" src="${LUI_ContextPath}/km/imeeting/maxhub/resource/js/edit.js"></script>
		
	</template:replace>	
	
</template:include>
