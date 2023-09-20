<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" bodyClass="lui_seat_body">
	<%-- 样式 --%>
	<template:replace name="head">
		<link href="${LUI_ContextPath}/km/imeeting/resource/css/seat.css?s_cache=${LUI_Cache}" rel="stylesheet">
		<script>
			function confirmDelete(msg){
				var del = confirm('<bean:message key="page.comfirmDelete"/>');
				return del;
			}
		</script>
	</template:replace>
	
	<%-- 按钮栏--%>
	<template:replace name="toolbar">
	
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="6"> 
			<ui:button text="${lfn:message('button.edit')}" onclick="Com_OpenWindow('kmImeetingSeatTemplate.do?method=edit&fdId=${JsParam.fdId}','_self');" order="1" ></ui:button>
			<ui:button text="${lfn:message('button.delete')}" onclick="if(!confirmDelete())return;Com_OpenWindow('kmImeetingSeatTemplate.do?method=delete&fdId=${JsParam.fdId}','_self');" order="2" ></ui:button>
			<ui:button text="${ lfn:message('button.close') }" order="3"  onclick="Com_CloseWindow()">
			</ui:button>
		</ui:toolbar>  
	
	</template:replace>
	
	<%--内容区--%>
	<template:replace name="content">
		  <div class="lui_seat_setting_wrap">
	    	<div class="lui_seat_setting_aside">
	    		<div class="lui_seat_setting_aside_inner">
	      			<div class="lui_seat_setting_aside_item">
	        			<h3 class="lui_seat_setting_aside_item_title">模板名称</h3>
	        			<table>
	        				<tr>
	        					<td>
	        						<xform:text property="fdName" required="true" showStatus="view" subject="模板名称"></xform:text>
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
		<%@include file="/km/imeeting/km_imeeting_seat_template/kmImeetingSeatTemplate_view_js.jsp"%>
	</template:replace>
</template:include>