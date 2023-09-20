<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.edit" bodyClass="lui_seat_body">

	<%-- 样式 --%>
	<template:replace name="head">
		<link href="${LUI_ContextPath}/km/imeeting/resource/css/seat.css?s_cache=${LUI_Cache}" rel="stylesheet">
	</template:replace>
	
	<%-- 按钮栏--%>
	<template:replace name="toolbar">
	
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="6"> 
			<!-- 添加行-->
			<ui:button text="${lfn:message('km-imeeting:py.addRow')}" onclick="addRow()" order="1" ></ui:button>
			<!-- 添加列-->
			<ui:button text="${lfn:message('km-imeeting:py.addCol')}" onclick="addCol()" order="1" ></ui:button>
			<ui:button text="清空全部" order="1" onclick="window.clearAllCustomData()">
			</ui:button>
			<c:if test="${kmImeetingSeatTemplateForm.method_GET=='edit'}">
				<ui:button text="${lfn:message('button.update') }" order="2" onclick="window.submit('update')">
				</ui:button>
			</c:if>
			<c:if test="${kmImeetingSeatTemplateForm.method_GET=='add'}">
				<ui:button text="${lfn:message('button.save') }" order="2" onclick="window.submit('save')">
				</ui:button>
			</c:if>
			<ui:button text="${ lfn:message('button.close') }" order="3"  onclick="Com_CloseWindow()">
			</ui:button>
		</ui:toolbar>  
	
	</template:replace>
	
	<%--内容区--%>
	<template:replace name="content">
		<html:form action="/km/imeeting/km_imeeting_seat_template/kmImeetingSeatTemplate.do">
			<html:hidden property="method_GET"/>
			<html:hidden property="fdId"/>
			<html:hidden property="fdSeatDetail"/>
			<html:hidden property="fdSeatCount" value="0"/>
			<html:hidden property="fdCols"/>
			<html:hidden property="fdRows"/>
			
			<!-- 坐席设置 Starts -->
		  	<div class="lui_seat_setting_wrap">
		    	<div class="lui_seat_setting_aside">
		    		<div class="lui_seat_setting_aside_inner">
		      			<div class="lui_seat_setting_aside_item">
		        			<h3 class="lui_seat_setting_aside_item_title">模板名称</h3>
		        			<table>
		        				<tr>
		        					<td>
		        						<xform:text property="fdName" required="true" showStatus="edit" subject="模板名称"></xform:text>
		        					</td>
		        				</tr>
		        			</table>
				        	<div class="lui_seat_setting_seat_wrapper">
				        		<span class="lui_seat_setting_seat">座位数：</span>
								<c:choose>
									<c:when test="${kmImeetingSeatTemplateForm.fdSeatCount != null }">
										<span id="seatCount" class="lui_seat_setting_seat_number">${kmImeetingSeatTemplateForm.fdSeatCount}</span>
									</c:when>
									<c:otherwise>
										<span id="seatCount" class="lui_seat_setting_seat_number">0</span>
									</c:otherwise>
								</c:choose>
				        	</div>
		      			</div>
				      	<div class="lui_seat_setting_aside_item">
				          	<h3 class="lui_seat_setting_aside_item_title">会议室元素</h3>
				          	<div id="seatElement">
				          	</div>
				      	</div>
		    		</div>
		    	</div>
		    
		    	<div id="seat" class="lui_seat_setting_content">
		    	</div>
		  	</div>
		</html:form>
		
		<%@include file="/km/imeeting/km_imeeting_seat_template/kmImeetingSeatTemplate_edit_js.jsp"%>
		
	</template:replace>
</template:include>