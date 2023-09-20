<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.attend.util.DateTimeFormatUtil,com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="java.util.List,com.landray.kmss.sys.attend.actions.SysAttendMainAction,com.landray.kmss.sys.attend.service.ISysAttendCategoryService,com.alibaba.fastjson.JSONArray,com.alibaba.fastjson.JSONObject" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	ISysAttendCategoryService cateService = (ISysAttendCategoryService) SpringBeanUtil.getBean("sysAttendCategoryService");
	JSONArray cateList = cateService.getAttendCategorys(request);
	if(!cateList.isEmpty()){
		JSONObject json = (JSONObject)cateList.get(0);
		String fdId = (String)json.get("fdId");
		String fdName = (String)json.get("fdName");
		List list = new SysAttendMainAction().test(request, response,fdId);
		if(!list.isEmpty()){
			request.setAttribute("_list", list);
			request.setAttribute("fdCategoryId", fdId);
			request.setAttribute("fdCategoryName", fdName);
		}
	}
	

	
%>
<template:include ref="default.edit">
	<template:replace name="title">
		<c:choose>
			<c:when test="${sysAttendMainForm.method_GET == 'add' }">
				<c:out value="${ lfn:message('operation.create') } - ${ lfn:message('sys-attend:module.sys.attend') }"></c:out>	
			</c:when>
			<c:otherwise>
				<c:out value="${ lfn:message('sys-attend:module.sys.attend') }"></c:out>
			</c:otherwise>
		</c:choose>
	</template:replace>
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float" count="3"> 
			
			<ui:button text="${ lfn:message('button.close') }" onclick="Com_CloseWindow();"></ui:button>	
		</ui:toolbar>
	</template:replace>
	<template:replace name="path">			
		<ui:menu layout="sys.ui.menu.nav"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home">
			</ui:menu-item>	
			<ui:menu-item text="${ lfn:message('sys-attend:module.sys.attend') }">
			</ui:menu-item>
		</ui:menu>
	</template:replace>	
	<template:replace name="content">
		<html:form action="/sys/attend/sys_attend_main/sysAttendMain.do?method=save">
			<html:hidden property="fdWorkType" value="0" />
			<html:hidden property="fdOutside" value="0" />
			<html:hidden property="fdStatus" value="1" />
			<html:hidden property="fdCategoryId" value="${fdCategoryId }" />
			<html:hidden property="fdWorkTimeId"  value="" />
			<html:hidden property="fdWorkType"  value="0" />
			<html:hidden property="fdType"  value="0" />
			
			<div class="lui_form_content_frame" style="padding-top:20px">
				<table class="tb_simple" width=100%>

					<tr>
						<td class="td_normal_title" width=15%>事项</td>
						<td >
							${fdCategoryName}
						</td>
						<td class="td_normal_title" width=15%>
							 签到地点
						</td>
						<td>
							<xform:text property="fdLocation" style="width:85%" value="广东省深圳市南山区科文路9-南门" />
						</td>
					</tr>
					
					<tr>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendMain.fdLng"/>
						</td>
						<td width="35%">
							<xform:text property="fdLng" style="width:85%" value="113.934771" />
						</td>
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendMain.fdLat"/>
						</td>
						<td width="35%">
							<xform:text property="fdLat" style="width:85%" value="22.544204" />
						</td>
					</tr>

					
					<tr>
						
						<td class="td_normal_title" width=15%>
							<bean:message bundle="sys-attend" key="sysAttendMain.fdDesc"/>
						</td>
						<td  colspan="3">
							<xform:textarea property="fdDesc" style="width:85%" value="签到性能测试" />
							<br></br>
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
								<c:param name="fdKey" value="attachment"/>
								<c:param name="fdAttType" value="pic"/>
							</c:import>
						</td>
					</tr>
					
					
					
				</table>
			</div>
			
			<center style="">
				<c:forEach var="item" items="${_list }">
					<div>班次时间:${item.signTime }</div>
				<div>
					<ui:button style="width:130px;" text="签到" onclick="dosubmit('${item.fdWorkTimeId }', '${item.fdWorkType }');"></ui:button>
				</div>
				</c:forEach>
				
			</center>
	
		<html:hidden property="fdId" />
		<html:hidden property="method_GET" />
		</html:form>
		<script>
			$KMSSValidation(document.forms['sysAttendMainForm']);
			function dosubmit(fdWorkTimeId,fdWorkType){
				var formObj = document.sysAttendMainForm;
				document.getElementsByName("fdWorkTimeId")[0].value=fdWorkTimeId;
				document.getElementsByName("fdWorkType")[0].value=fdWorkType;
				Com_Submit(formObj, 'save'); 
			}
		</script>
	</template:replace>
	<%--
	<template:replace name="nav">
		<div style="min-width:200px;"></div>
		<ui:accordionpanel style="min-width:200px;"> 
			<ui:content title="${ lfn:message('sys-doc:kmDoc.kmDocKnowledge.docInfo') }" toggle="false">
				<c:import url="/sys/evaluation/import/sysEvaluationMain_view_star.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="sysAttendMainForm" />
				</c:import>
				<ul class='lui_form_info'>
					<li><bean:message bundle="sys-attend" key="sysAttendMain.docCreator" />：
					<ui:person personId="${sysAttendMainForm.docCreatorId}" personName="${sysAttendMainForm.docCreatorName}"></ui:person></li>
					<li><bean:message bundle="sys-attend" key="sysAttendMain.docDept" />：${sysAttendMainForm.docDeptName}</li>
					<li><bean:message bundle="sys-attend" key="sysAttendMain.docStatus" />：<sunbor:enumsShow value="${sysAttendMainForm.docStatus}" enumsType="common_status" /></li>
					<li><bean:message bundle="sys-attend" key="sysAttendMain.docCreateTime" />：${sysAttendMainForm.docCreateTime }</li>				
				</ul>
			</ui:content>
		</ui:accordionpanel>
	</template:replace>
	--%>
</template:include>