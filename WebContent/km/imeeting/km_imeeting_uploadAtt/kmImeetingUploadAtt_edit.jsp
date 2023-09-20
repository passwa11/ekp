<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.view" sidebar="no">
	<%--页签名--%>
	<template:replace name="title">
		<c:out value="${lfn:message('km-imeeting:kmImeeting.tree.uploadAtt') } - ${ lfn:message('km-imeeting:module.km.imeeting') }"></c:out>
	</template:replace>
	
	<template:replace name="toolbar">
		<ui:toolbar id="toolbar" layout="sys.ui.toolbar.float"> 
			<%--提交--%>
			<c:if test="${canUpload==true}">
				<ui:button text="${lfn:message('button.submit')}"  
					onclick="Com_Submit(document.kmImeetingMainForm, 'saveUpdateAtt');" order="2">
				</ui:button>
			</c:if>
			<%--关闭--%>
			<ui:button text="${lfn:message('button.close')}" order="5" onclick="Com_CloseWindow();">
			</ui:button>
		</ui:toolbar>
	</template:replace>
	
	<template:replace name="path">
		<ui:menu layout="sys.ui.menu.nav"  id="categoryId"> 
			<ui:menu-item text="${ lfn:message('home.home') }" icon="lui_icon_s_home"></ui:menu-item>
			<ui:menu-item text="${ lfn:message('km-imeeting:module.km.imeeting') }"></ui:menu-item>
			<ui:menu-item text="${lfn:message('km-imeeting:kmImeeting.tree.uploadAtt') }" target="_self"></ui:menu-item>
		</ui:menu>
	</template:replace>
	
	
	<template:replace name="content"> 
		<style>
			body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,form,iieldset,input,textarea,p,blockquote,th,td{ 
				margin:0; 
				padding:0;
				list-style: none;
			}
		</style>
		<html:form action="/km/imeeting/km_imeeting_main/kmImeetingMain.do">
			<html:hidden property="fdId" />
			<div class="lui_form_content_frame" >
				<%--上会材料--%>
				<p class="lui_form_subject">
					<bean:message bundle="km-imeeting" key="kmImeeting.tree.uploadAtt" />
				</p>
				<table class="tb_normal" width=100%>
					<tr>
						<%--会议名称--%>
						<td class="td_normal_title" width=15%>
							<bean:message  bundle="km-imeeting" key="kmImeetingMain.fdName"/>
						</td>
						<td width=85% colspan="3"> 
							<c:out value="${kmImeetingMainForm.fdName}"/>
						</td>
						<%--已召开会议无权限再提交上会材料--%>
						<c:if test="${canUpload==false}">
							<script>
								seajs.use(['lui/dialog'],function(dialog){
									dialog.alert('${lfn:message("km-imeeting:kmImeetingMain.cannot.upload.tip")}',function(){
										//window.close();
									});
								});							
							</script>
						</c:if>
					</tr>
					<%-- 所属场所 --%>
					<c:import url="/sys/authorization/sys_auth_area/sysAuthArea_field.jsp" charEncoding="UTF-8">
	                     <c:param name="id" value="${kmImeetingMainForm.authAreaId}"/>
	                </c:import>
					<tr>
						<%--召开时间--%>
						<td class="td_normal_title" width=15%>
							<bean:message  bundle="km-imeeting" key="kmImeetingMain.fdDate"/>
						</td>
						<td width=85% colspan="3"> 
							<xform:datetime property="fdHoldDate" dateTimeType="datetime"></xform:datetime>~
							<xform:datetime property="fdFinishDate" dateTimeType="datetime"></xform:datetime>
						</td>
					</tr>
					<tr>
						<%--地点--%>
						<td class="td_normal_title" width=15%>
							<bean:message  bundle="km-imeeting" key="kmImeetingMain.fdPlace"/>
						</td>
						<td width=85% colspan="3"> 
							<c:out value="${kmImeetingMainForm.fdPlaceName}"/>
							<c:if test="${not empty kmImeetingMainForm.fdOtherPlace }">
								<c:out value="${kmImeetingMainForm.fdOtherPlace}"/>
							</c:if>
						</td>
					</tr>
					<tr>
						<%--主持人--%>
						<td class="td_normal_title" width=15%>
							<bean:message  bundle="km-imeeting" key="kmImeetingMain.fdHost"/>
						</td>
						<td width=85% colspan="3"> 
							<c:out value="${kmImeetingMainForm.fdHostName}"/>
							<c:if test="${not empty kmImeetingMainForm.fdOtherHostPerson }">
								<c:out value="${kmImeetingMainForm.fdOtherHostPerson}"/>
							</c:if>
						</td>
					</tr>
					<tr>
						<%--明细--%>
						<td colspan="4">
							<script>Com_IncludeFile("doclist.js");</script>
							<table class="tb_normal" width=100% id="TABLE_DocList" align="center">
								<tr align="center">
									<%--序号--%> 
									<td class="td_normal_title" style="width: 3%">
										<bean:message key="page.serial"/>
									</td>
									<%--会议议题--%> 
									<td class="td_normal_title" style="width: 11%">
										<bean:message bundle="km-imeeting" key="kmImeetingAgenda.docSubject"/>
									</td>
									<%--汇报人--%> 
									<td class="td_normal_title" style="width: 5%">
										<bean:message bundle="km-imeeting" key="kmImeetingAgenda.docReporter"/>
									</td>
									<%--汇报时间（分钟）--%> 
									<td class="td_normal_title" style="width: 7%">
										<bean:message bundle="km-imeeting" key="kmImeetingAgenda.docReporterTime"/>
									</td>
									<%--上会所需材料--%> 
									<td class="td_normal_title" style="width: 12%">
										<bean:message bundle="km-imeeting" key="kmImeetingAgenda.attachmentName"/>
									</td>
									<%--材料负责人--%> 
									<td class="td_normal_title" style="width: 7%">
										<bean:message bundle="km-imeeting" key="kmImeetingAgenda.docRespons"/>
									</td>
									<%--上会所需材料--%> 
									<td class="td_normal_title" style="width: 38%">
										<bean:message bundle="km-imeeting" key="kmImeetingAgenda.attachment.submit"/>
									</td>
								</tr>
								<c:forEach items="${kmImeetingMainForm.kmImeetingAgendaForms}"  var="kmImeetingAgendaItem" varStatus="vstatus">
									<c:if test="${kmImeetingAgendaItem.docResponsId==currentUser.fdId or isAdmin==true}">
									<tr KMSS_IsContentRow="1" align="center">
										<td width="3%">
											${vstatus.index+1}
											<html:hidden property="kmImeetingAgendaForms[${vstatus.index}].fdId"/>
										</td>
										<td width=12%>
											<xform:text property="kmImeetingAgendaForms[${vstatus.index}].docSubject" />
										</td>
										<td width=6%>
											<xform:text property="kmImeetingAgendaForms[${vstatus.index}].docReporterName" />
										</td>
										<td width=5%>
											<xform:text property="kmImeetingAgendaForms[${vstatus.index}].docReporterTime" />
											<c:if test="${not empty kmImeetingMainForm.kmImeetingAgendaForms[vstatus.index].docReporterTime }">
												<bean:message key="date.interval.minute"/>
											</c:if>
										</td>
										<td width=12%>
											<xform:text property="kmImeetingAgendaForms[${vstatus.index}].attachmentName" />
										</td>
										<td width=6%>
											<xform:text property="kmImeetingAgendaForms[${vstatus.index}].docResponsName" />
										</td>
										<%--上会所需材料--%> 
										<td style="width: 38%" align="left">
											<c:if test="${canUpload==true}">
												<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
													<c:param name="fdKey" value="ImeetingUploadAtt_${kmImeetingAgendaItem.fdId }" />
													<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
													<c:param name="fdModelId" value="${JsParam.fdId }" />
													<c:param name="uploadAfterSelect" value="true" />
												</c:import>
											</c:if>
											<c:if test="${canUpload==false}">
												<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
													<c:param name="fdKey" value="ImeetingUploadAtt_${kmImeetingAgendaItem.fdId }" />
													<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingMain" />
													<c:param name="fdModelId" value="${JsParam.fdId }" />
												</c:import>
											</c:if>
										</td>
									</tr>
									</c:if>
								</c:forEach>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</html:form>
	</template:replace>
</template:include>