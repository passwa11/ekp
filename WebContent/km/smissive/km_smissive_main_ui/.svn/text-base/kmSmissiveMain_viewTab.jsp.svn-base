<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.km.smissive.util.KmSmissiveConfigUtil"%>
<%@page import="com.landray.kmss.km.smissive.forms.KmSmissiveMainForm"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@page import="com.landray.kmss.util.UserUtil" %>
<%@ page import="java.util.List"%>
<%@ page import="com.landray.kmss.sys.attachment.util.SysAttConfigUtil" %>
<template:replace name="content">
	<link rel="stylesheet" href="${ LUI_ContextPath}/sys/attachment/view/img/upload.css" />
	<!-- 软删除配置 -->
	<c:import url="/sys/recycle/import/redirect.jsp">
		<c:param name="formBeanName" value="kmSmissiveMainForm"></c:param>
	</c:import>
	<script>
		Com_IncludeFile("jquery.js");
	</script>
	<script>
		seajs.use(['sys/ui/js/dialog'], function(dialog) {
			window.dialog=dialog;
		});
		/* 软删除配置 */
		function Delete(delUrl){
			Com_Delete_Get(delUrl, 'com.landray.kmss.km.smissive.model.KmSmissiveMain');
			return;
		}
		//解决当在新窗口打开主文档时控件显示不全问题，这里打开时即为最大化修改by张文添
		function max_window(){
			window.moveTo(0, 0);
			window.resizeTo(window.screen.width, window.screen.height);
		}
		function fn_dialog(url){
			url = "<c:url value="/resource/jsp/frame.jsp?url=" />" + encodeURIComponent(url);
			var winStyle = "resizable=1,scrollbars=1,width=850,height=600,dependent=yes,alwaysRaised=1";
			window.open(url, "_blank", winStyle);
		}
		//max_window();

		//解决非ie下控件高度问题
		$(document).ready(function(){
			var obj1 = document.getElementById("JGWebOffice_editOnline");
			var obj2 = document.getElementById("JGWebOffice_mainOnline");
			if(obj1){
				obj1.setAttribute("height", "550px");
			}
			if(obj2){
				obj2.setAttribute("height", "550px");
			}
		});
	</script>
	<script type="text/javascript">
		<c:if test="${_isWpsWebOffice eq 'true'}">
			Com_IncludeFile("wps_utils.js",Com_Parameter.ContextPath + "sys/attachment/sys_att_main/wps/oaassist/js/","js",true);
		</c:if>
		function openWpsFile(fdId){
			var wpsParam = {};
			wpsParam['wpsExtAppModel'] = "kmSmissive";
			openWpsOAAssit(fdId,wpsParam);
		};
		function editWpsFile(fdId){
			var wpsParam = {};
			wpsParam['wpsExtAppModel'] = "kmSmissive";
			wpsParam['newFlag'] = "false";
			wpsParam['bookMarks'] = '{"docSubject":"${kmSmissiveMainForm.docSubject}","docAuthorName":"${kmSmissiveMainForm.docAuthorName}","fdUrgency":"${kmSmissiveMainForm.fdUrgencyName}","fdTemplateName":"${kmSmissiveMainForm.fdTemplateName}","docCreateTime":"${kmSmissiveMainForm.docCreateTime}","fdSecret":"${kmSmissiveMainForm.fdSecretName}","fdFileNo":"${kmSmissiveMainForm.fdFileNo}","fdMainDeptName":"${kmSmissiveMainForm.fdMainDeptName}","fdSendDeptNames":"${kmSmissiveMainForm.fdSendDeptNames}","fdCopyDeptNames":"${kmSmissiveMainForm.fdCopyDeptNames}","fdIssuerName":"${kmSmissiveMainForm.fdIssuerName}","docCreatorName":"${kmSmissiveMainForm.docCreatorName}"}';

			editWpsOAAssit(fdId,wpsParam);
		}

	</script>
	<c:if test="${kmSmissiveMainForm.fdNeedContent=='1' or empty kmSmissiveMainForm.fdNeedContent}">
		<%
			//以下代码用于附件不通过form读取的方式
			List sysAttMains = (List)pageContext.getAttribute("sysAttMainContent");
			if(sysAttMains==null || sysAttMains.isEmpty()){
				try{
					String _modelId = request.getParameter("kmSmissiveMainForm_fdId");
					if(StringUtil.isNotNull(_modelId)){
						ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService)SpringBeanUtil.getBean("sysAttMainService");
						sysAttMains = sysAttMainService.findByModelKey("com.landray.kmss.km.smissive.model.KmSmissiveMain",_modelId,"mainOnline");
					}
					if(sysAttMains!=null && !sysAttMains.isEmpty()){
						pageContext.setAttribute("sysAttMainContent",sysAttMains);
						SysAttMain sysAttmain = (SysAttMain)sysAttMains.get(0);
						pageContext.setAttribute("sysAttMainFdId",sysAttmain.getFdId());
						boolean canEdit = UserUtil.checkAuthentication(
								"/sys/attachment/sys_att_main/sysAttMain.do?method=edit&fdId="
										+ sysAttmain.getFdId(),
								"get");
						pageContext.setAttribute("sysAttMainCanEdit",canEdit);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		%>
		<c:forEach items="${sysAttMainContent}" var="sysAttMain"	varStatus="vstatus">
			<c:set var="attachmentId" value="${sysAttMain.fdId}"/>
			<c:set var="fdAttMainId" value="${sysAttMain.fdId}" scope="request"/>
		</c:forEach>
	</c:if>
	<%
		Boolean _isJGEnabled = false, _isWpsWebOffice = false, _isWpsCloudEnable = false, _isWpsCenterEnable = false,
				_isJGView = false, _isWpsWebOfficeView = false;
		boolean existViewPath = JgWebOffice.isExistViewPath(request);
		String type = SysAttConfigUtil.getOnlineToolType();
		String readOLConfig = SysAttConfigUtil.getReadOLConfig();
		if ("0".equals(type)) {//金格
			_isJGEnabled = true;
		} else if ("3".equals(type)) {//加载项
			_isWpsWebOffice = true;
		} else if ("1".equals(type)) {//云文档
			_isWpsCloudEnable = true;
		} else if ("4".equals(type)) {//中台
			_isWpsCenterEnable = true;
		}
		if ("0".equals(type) && "1".equals(readOLConfig) && !existViewPath) {
			//金格+aspose，文件没有转换完成时，使用金格
			_isJGView = true;
		}
		if ("3".equals(type) && "1".equals(readOLConfig) && !existViewPath) {
			//wps加载项+aspose，文件没有转换完成时，使用加载项
			_isWpsWebOfficeView = true;
		}

		pageContext.setAttribute("_isJGEnabled", _isJGEnabled);
		pageContext.setAttribute("_isWpsCloudEnable", _isWpsCloudEnable);
		pageContext.setAttribute("_isWpsWebOffice", _isWpsWebOffice);
		pageContext.setAttribute("_isWpsCenterEnable", _isWpsCenterEnable);
		pageContext.setAttribute("_isJGView", _isJGView);
		pageContext.setAttribute("_isWpsWebOfficeView", _isWpsWebOfficeView);
		pageContext.setAttribute("readOLConfig", readOLConfig);
	%>
	<p class="txttitle">
		<c:out value="${kmSmissiveMainForm.fdTitle}" />
	</p>
	<c:if test="${not empty kmSmissiveMainForm.attachmentForms['mainOnline'].attachments or not empty kmSmissiveMainForm.attachmentForms['editOnline'].attachments}">
		<div class="lui_form_content_frame" style="padding-top: 10px">
			<table class="tb_normal" width="100%">
				<c:if
						test="${kmSmissiveMainForm.fdNeedContent=='1' or empty kmSmissiveMainForm.fdNeedContent}">
					<tr>
					<td colspan="4">
					<c:if test="${pageScope._isJGEnabled == 'true'}">
						<!--   提示信息 -->
						<%
							if (com.landray.kmss.sys.attachment.util.JgWebOffice.isJGEnabled()
									&& JgWebOffice.isExistFile(request)) {
						%>
						<c:if test="${isShowImg&&kmSmissiveMainForm.docStatus!='20'}">
							<tr>
								<td class="td_normal_title" width=15%>
									<bean:message bundle="km-smissive" key="kmSmissiveMain.prompt.title" />
								</td>
								<td colspan="3">
									<font style="text-align: center"><bean:message bundle="km-smissive" key="kmSmissiveMain.prompt" /></font>
								</td>
							</tr>
						</c:if>
						<%
							}
						%>
						<c:if test="${pageScope.readOLConfig != '6' && pageScope.readOLConfig != '5' && pageScope.readOLConfig != '4' && pageScope.readOLConfig != '2' && pageScope.readOLConfig != '1' || _isJGView == true}">
						  <div id="missiveButtonDiv" style="text-align: right; padding-bottom: 5px">
							<%
								if (JgWebOffice.isJGEnabled() && Boolean.parseBoolean(KmSmissiveConfigUtil
										.isShowImg(((KmSmissiveMainForm) request.getAttribute("kmSmissiveMainForm"))))) {
							%>
							<c:choose>
								<c:when test="${isIE}">
									<kmss:auth requestURL="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=view&fdId=${param.fdId}" requestMethod="GET">
										<a href="javascript:void(0);" class="attswich"
										   onclick="Com_OpenWindow('kmSmissiveMain.do?method=view&fdId=${param.fdId}&isShowImg=${isShowImg}','_self');">
											<bean:message bundle="km-smissive" key="smissive.button.change.view" />
										</a>
									</kmss:auth>
								</c:when>
								<c:otherwise>
									<%
										if (JgWebOffice.isJGMULEnabled()) {
									%>
									<kmss:auth requestURL="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=view&fdId=${param.fdId}" requestMethod="GET">
										<a href="javascript:void(0);" class="attswich"
										   onclick="Com_OpenWindow('kmSmissiveMain.do?method=view&fdId=${param.fdId}&isShowImg=${isShowImg}','_self');">
											<bean:message bundle="km-smissive" key="smissive.button.change.view" />
										</a>
									</kmss:auth>
									<%
										}
									%>
								</c:otherwise>
							</c:choose>
							<%
								}
							%>
						</div>
						</c:if>
						
					</c:if>
					<c:choose>
						<c:when test="${editStatus == true}">
							<c:choose>
								<c:when test="${pageScope._isWpsCloudEnable == 'true'}">
									<c:import url="/sys/attachment/sys_att_main/wps/cloud/sysAttMain_edit.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="mainOnline" />
										<c:param name="load" value="true" />
										<c:param name="bindSubmit" value="false"/>
										<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
										<c:param name="formBeanName" value="kmSmissiveMainForm" />
										<c:param name="fdTemplateModelId" value="${kmSmissiveMainForm.fdTemplateId}" />
										<c:param name="fdTemplateModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
										<c:param name="fdTemplateKey" value="mainOnline" />
										<c:param name="fdTempKey" value="${kmSmissiveMainForm.fdTemplateId}" />
									</c:import>
								</c:when>
								<c:when test="${pageScope._isWpsCenterEnable == 'true'}">
									<c:import url="/sys/attachment/sys_att_main/wps/center/sysAttMain_edit.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="mainOnline" />
										<c:param name="load" value="true" />
										<c:param name="bindSubmit" value="false"/>
										<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
										<c:param name="formBeanName" value="kmSmissiveMainForm" />
										<c:param name="fdTemplateModelId" value="${kmSmissiveMainForm.fdTemplateId}" />
										<c:param name="fdTemplateModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
										<c:param name="fdTemplateKey" value="mainOnline" />
										<c:param name="fdTempKey" value="${kmSmissiveMainForm.fdTemplateId}" />
									</c:import>
								</c:when>
								<c:when test="${pageScope._isWpsWebOffice == 'true' and pageScope.readOLConfig == '6'}">
									<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_include_viewHtml.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="mainOnline" />
										<c:param name="fdAttType" value="office" />
										<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
										<c:param name="formBeanName" value="kmSmissiveMainForm" />
										<c:param name="buttonDiv" value="missiveButtonDiv" />
										<c:param name="isExpand" value="true" />
										<c:param name="showToolBar" value="false" />
									</c:import>
								</c:when>
								<c:when test="${pageScope._isWpsWebOffice == 'true'}">
									<c:import url="/sys/attachment/sys_att_main/wps/oaassist/sysAttMain_edit.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="mainOnline" />
										<c:param name="fdMulti" value="false" />
										<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
										<c:param name="formBeanName" value="kmSmissiveMainForm" />
										<c:param name="fdTemplateModelId" value="${param.fdTemplateId}" />
										<c:param name="fdTemplateModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
										<c:param name="fdTemplateKey" value="mainContent" />
										<c:param name="templateBeanName" value="kmSmissiveTemplateForm" />
										<c:param name="showDelete" value="false" />
										<c:param name="wpsExtAppModel" value="kmSmissive" />
										<c:param name="redhead" value="${redhead}" />
										<c:param name="nodevalue" value="${nodevalue}" />
										<c:param name="wpsExtAppModel" value="kmSmissive" />
										<c:param  name="signtrue"  value="${signtrue}"/>
										<c:param name="canDownload" value="${canDownload}" />
										<c:param name="newFlag" value="false" />
										<c:param  name="hideReplace"  value="true"/>
										<c:param  name="canEdit"  value="true"/>
										<c:param name="bookMarks" value='{"docSubject":"${kmSmissiveMainForm.docSubject}","docAuthorName":"${kmSmissiveMainForm.docAuthorName}","fdUrgency":"${kmSmissiveMainForm.fdUrgencyName}","fdTemplateName":"${kmSmissiveMainForm.fdTemplateName}","docCreateTime":"${kmSmissiveMainForm.docCreateTime}","fdSecret":"${kmSmissiveMainForm.fdSecretName}","fdFileNo":"${kmSmissiveMainForm.fdFileNo}","fdMainDeptName":"${kmSmissiveMainForm.fdMainDeptName}","fdSendDeptNames":"${kmSmissiveMainForm.fdSendDeptNames}","fdCopyDeptNames":"${kmSmissiveMainForm.fdCopyDeptNames}","fdIssuerName":"${kmSmissiveMainForm.fdIssuerName}","docCreatorName":"${kmSmissiveMainForm.docCreatorName}"}'/>
									</c:import>
								</c:when>
								<c:when test="${pageScope._isJGEnabled == 'true' and pageScope.readOLConfig == '6'}">
									<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_include_viewHtml.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="mainOnline" />
										<c:param name="fdAttType" value="office" />
										<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
										<c:param name="formBeanName" value="kmSmissiveMainForm" />
										<c:param name="buttonDiv" value="missiveButtonDiv" />
										<c:param name="isExpand" value="true" />
										<c:param name="showToolBar" value="false" />
									</c:import>
								</c:when>
								<c:when test="${pageScope._isJGEnabled == 'true'}">
									<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_edit.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="mainOnline" />
										<c:param name="fdAttType" value="office" />
										<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
										<c:param name="formBeanName" value="kmSmissiveMainForm" />
										<c:param name="buttonDiv" value="missiveButtonDiv" />
										<c:param name="isReadOnly" value="${isReadOnly}" />
										<c:param name="isToImg" value="false" />
									</c:import>
								</c:when>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${pageScope._isJGView}">
									<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_include_viewHtml.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="mainOnline" />
										<c:param name="fdAttType" value="office" />
										<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
										<c:param name="formBeanName" value="kmSmissiveMainForm" />
										<c:param name="contentFlag" value="true" />
										<c:param name="isShowImg" value="${isShowImg}" />
										<c:param name="buttonDiv" value="missiveButtonDiv" />
										<c:param name="isExpand" value="true" />
										<c:param name="showAllPage" value="${showAllPage}" />
										<c:param name="bookMarks" value="docSubject:${kmSmissiveMainForm.docSubject},docAuthorName:${kmSmissiveMainForm.docAuthorName},fdUrgency:${kmSmissiveMainForm.fdUrgencyName},fdTemplateName:${kmSmissiveMainForm.fdTemplateName},docCreateTime:${kmSmissiveMainForm.docCreateTime},fdSecret:${kmSmissiveMainForm.fdSecretName},fdFileNo:${kmSmissiveMainForm.fdFileNo},fdMainDeptName:${kmSmissiveMainForm.fdMainDeptName},fdSendDeptNames:${kmSmissiveMainForm.fdSendDeptNames},fdCopyDeptNames:${kmSmissiveMainForm.fdCopyDeptNames},fdIssuerName:${kmSmissiveMainForm.fdIssuerName},docCreatorName:${kmSmissiveMainForm.docCreatorName}" />
									</c:import>
								</c:when>
								<c:when test="${pageScope._isWpsWebOfficeView}">
									<c:import url="/sys/attachment/sys_att_main/wps/oaassist/sysAttMain_view.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="mainOnline" />
										<c:param name="fdMulti" value="false" />
										<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
										<c:param name="formBeanName" value="kmSmissiveMainForm" />
										<c:param name="fdTemplateModelId" value="${param.fdTemplateId}" />
										<c:param name="fdTemplateModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
										<c:param name="fdTemplateKey" value="mainContent" />
										<c:param name="templateBeanName" value="kmSmissiveTemplateForm" />
										<c:param name="showDelete" value="false" />
										<c:param name="wpsExtAppModel" value="kmSmissive" />
										<c:param name="redhead" value="${redhead}" />
										<c:param name="nodevalue" value="${nodevalue}" />
										<c:param name="wpsExtAppModel" value="kmSmissive" />
										<c:param  name="signtrue"  value="${signtrue}"/>
										<c:param name="canDownload" value="${canDownload}" />
										<c:param name="newFlag" value="false" />
										<c:param  name="hideReplace"  value="true"/>
										<c:param  name="canEdit"  value="false"/>
										<c:param name="bookMarks" value='{"docSubject":"${kmSmissiveMainForm.docSubject}","docAuthorName":"${kmSmissiveMainForm.docAuthorName}","fdUrgency":"${kmSmissiveMainForm.fdUrgencyName}","fdTemplateName":"${kmSmissiveMainForm.fdTemplateName}","docCreateTime":"${kmSmissiveMainForm.docCreateTime}","fdSecret":"${kmSmissiveMainForm.fdSecretName}","fdFileNo":"${kmSmissiveMainForm.fdFileNo}","fdMainDeptName":"${kmSmissiveMainForm.fdMainDeptName}","fdSendDeptNames":"${kmSmissiveMainForm.fdSendDeptNames}","fdCopyDeptNames":"${kmSmissiveMainForm.fdCopyDeptNames}","fdIssuerName":"${kmSmissiveMainForm.fdIssuerName}","docCreatorName":"${kmSmissiveMainForm.docCreatorName}"}'/>
									</c:import>
								</c:when>
								<c:when test="${pageScope.readOLConfig == '1' }">
									<div id="missiveButtonDiv" style="text-align:right;padding-bottom:5px">&nbsp;
										<kmss:authShow roles="ROLE_KMSMISSIVE_DOWNLOADCONTENT">
											<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${attachmentId}" requestMethod="GET">
												<a href="javascript:void(0);" class="attdownloadcontent"
												   onclick="Com_OpenWindow('${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${attachmentId}');">
													正文下载 </a>&nbsp;
											</kmss:auth>
										</kmss:authShow>
										<kmss:authShow roles="ROLE_KMSMISSIVE_PRINTCONTENT">
											<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=${attachmentId}" requestMethod="GET">
												<a href="javascript:void(0);" class="attprint"
												   onclick="printContext('${attachmentId}')">
													打印正文
												</a>&nbsp;
											</kmss:auth>
										</kmss:authShow>
										<c:choose>
											<c:when test="${sysAttMainCanEdit == true}">
												<a href="javascript:void(0);" class="attbook" onclick="Com_OpenWindow(Com_Parameter.ContextPath+'km/smissive/bookMarks.jsp','_blank');">
													<bean:message key="kmSmissiveMain.bookMarks.title" bundle="km-smissive"/>
												</a>
												<c:if test="${editStatus == true}">
													<a href="javascript:void(0);" class="attbook" onclick="editWpsFile('${sysAttMainFdId}')">
														<bean:message key="kmSmissive.editDocContent" bundle="km-smissive"/>
													</a>
												</c:if>
											</c:when>
											<c:otherwise>
												<a href="javascript:void(0);" class="attbook" onclick="openWpsFile('${sysAttMainFdId}')">
													<bean:message key="kmSmissive.viewDocContent" bundle="km-smissive"/>
												</a>
											</c:otherwise>
										</c:choose>
									</div>
									<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_include_viewHtml.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="mainOnline" />
										<c:param name="fdAttType" value="office" />
										<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
										<c:param name="formBeanName" value="kmSmissiveMainForm" />
										<c:param name="buttonDiv" value="missiveButtonDiv" />
										<c:param name="isExpand" value="true" />
										<c:param name="showToolBar" value="false" />
									</c:import>
								</c:when>
								<c:when test="${pageScope.readOLConfig == '2' }">
									<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_include_viewHtml.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="mainOnline" />
										<c:param name="fdAttType" value="office" />
										<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
										<c:param name="formBeanName" value="kmSmissiveMainForm" />
										<c:param name="contentFlag" value="true" />
										<c:param name="isShowImg" value="${isShowImg}" />
										<c:param name="buttonDiv" value="missiveButtonDiv" />
										<c:param name="isExpand" value="true" />
										<c:param name="showAllPage" value="${showAllPage}" />
										<c:param name="bookMarks" value="docSubject:${kmSmissiveMainForm.docSubject},docAuthorName:${kmSmissiveMainForm.docAuthorName},fdUrgency:${kmSmissiveMainForm.fdUrgencyName},fdTemplateName:${kmSmissiveMainForm.fdTemplateName},docCreateTime:${kmSmissiveMainForm.docCreateTime},fdSecret:${kmSmissiveMainForm.fdSecretName},fdFileNo:${kmSmissiveMainForm.fdFileNo},fdMainDeptName:${kmSmissiveMainForm.fdMainDeptName},fdSendDeptNames:${kmSmissiveMainForm.fdSendDeptNames},fdCopyDeptNames:${kmSmissiveMainForm.fdCopyDeptNames},fdIssuerName:${kmSmissiveMainForm.fdIssuerName},docCreatorName:${kmSmissiveMainForm.docCreatorName}" />
									</c:import>
								</c:when>
								<c:when test="${pageScope.readOLConfig == '3' }">
									<c:import url="/sys/attachment/sys_att_main/wps/cloud/sysAttMain_view.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="mainOnline" />
										<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
										<c:param name="formBeanName" value="kmSmissiveMainForm" />
									</c:import>
								</c:when>
								<c:when test="${pageScope.readOLConfig == '4' }">
									<%
										//以下代码用于附件不通过form读取的方式
										List sysAttMains = (List)pageContext.getAttribute("sysAttMainContent");
										if (sysAttMains == null || sysAttMains.isEmpty()) {
											try {
												String _modelId = request.getParameter("kmSmissiveMainForm_fdId");
												if (StringUtil.isNotNull(_modelId)) {
													ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
													sysAttMains = sysAttMainService.findByModelKey("com.landray.kmss.km.smissive.model.KmSmissiveMain", _modelId, "mainOnline");
												}
												if (sysAttMains != null && !sysAttMains.isEmpty()) {
													request.setAttribute("djSysAttMains", sysAttMains);
												}
											} catch (Exception e) {

											}
										} else {
											request.setAttribute("djSysAttMains",sysAttMains);
										}
									%>
									<c:import url="/sys/attachment/sys_att_main/dianju/sysAttMain_view_preview.jsp" charEncoding="UTF-8">
										<c:param name="showDownload" value="false" />
										<c:param name="isAtt" value="false" />
										<c:param name="showChangeView" value="true" />
									</c:import>
								</c:when>
								<c:when test="${pageScope.readOLConfig == '5' }">
									<c:import url="/sys/attachment/sys_att_main/wps/center/sysAttMain_view.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="mainOnline" />
										<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
										<c:param name="formBeanName" value="kmSmissiveMainForm" />
									</c:import>
								</c:when>
								<c:otherwise>
									<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_include_viewHtml.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="mainOnline" />
										<c:param name="fdAttType" value="office" />
										<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
										<c:param name="formBeanName" value="kmSmissiveMainForm" />
										<c:param name="contentFlag" value="true" />
										<c:param name="isShowImg" value="${isShowImg}" />
										<c:param name="buttonDiv" value="missiveButtonDiv" />
										<c:param name="isExpand" value="true" />
										<c:param name="showAllPage" value="${showAllPage}" />
										<c:param name="bookMarks" value="docSubject:${kmSmissiveMainForm.docSubject},docAuthorName:${kmSmissiveMainForm.docAuthorName},fdUrgency:${kmSmissiveMainForm.fdUrgencyName},fdTemplateName:${kmSmissiveMainForm.fdTemplateName},docCreateTime:${kmSmissiveMainForm.docCreateTime},fdSecret:${kmSmissiveMainForm.fdSecretName},fdFileNo:${kmSmissiveMainForm.fdFileNo},fdMainDeptName:${kmSmissiveMainForm.fdMainDeptName},fdSendDeptNames:${kmSmissiveMainForm.fdSendDeptNames},fdCopyDeptNames:${kmSmissiveMainForm.fdCopyDeptNames},fdIssuerName:${kmSmissiveMainForm.fdIssuerName},docCreatorName:${kmSmissiveMainForm.docCreatorName}" />
									</c:import>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					</td>
					</tr>
				</c:if>
				<c:if test="${kmSmissiveMainForm.fdNeedContent=='0'}">
					<c:if
							test="${not empty kmSmissiveMainForm.attachmentForms['editOnline'].attachments}">
						<tr>
							<td colspan="4">
								<c:choose>
									<c:when
											test="${fn:length(kmSmissiveMainForm.attachmentForms['editOnline'].attachments)>1}">
										<div class="lui_form_spacing"></div>
										<div>
											<div class="lui_form_subhead">
												<img
														src="${KMSS_Parameter_ContextPath}sys/attachment/view/img/attachment.png">
												<bean:message key="kmSmissiveMain.mainonline"
															  bundle="km-smissive" />
											</div>
											<c:import
													url="/sys/attachment/sys_att_main/sysAttMain_view.jsp"
													charEncoding="UTF-8">
												<c:param name="formBeanName" value="kmSmissiveMainForm" />
												<c:param name="fdKey" value="editOnline" />
												<c:param name="fdModelId" value="${param.fdId}" />
												<c:param name="fdModelName"
														 value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
											</c:import>
										</div>
									</c:when>
									<c:when test="${pageScope._isWpsCloudEnable == 'true' and pageScope.readOLConfig == '3'}">
										<c:if test="${editStatus == true}">
											<c:import url="/sys/attachment/sys_att_main/wps/cloud/sysAttMain_edit.jsp" charEncoding="UTF-8">
												<c:param name="fdKey" value="editOnline" />
												<c:param name="load" value="true" />
												<c:param name="bindSubmit" value="false"/>
												<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
												<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
												<c:param name="formBeanName" value="kmSmissiveMainForm" />
												<c:param name="fdTemplateModelId" value="${kmSmissiveMainForm.fdTemplateId}" />
												<c:param name="fdTemplateModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
												<c:param name="fdTemplateKey" value="mainOnline" />
												<c:param name="fdTempKey" value="${kmSmissiveMainForm.fdTemplateId}" />
											</c:import>
										</c:if>
										<c:if test="${editStatus != true}">
											<c:import url="/sys/attachment/sys_att_main/wps/cloud/sysAttMain_view.jsp" charEncoding="UTF-8">
												<c:param name="fdKey" value="editOnline" />
												<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
												<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
												<c:param name="formBeanName" value="kmSmissiveMainForm" />
											</c:import>
										</c:if>
									</c:when>
									<c:when test="${pageScope._isWpsCenterEnable == 'true'}">
										<c:if test="${editStatus == true}">
											<c:import url="/sys/attachment/sys_att_main/wps/center/sysAttMain_edit.jsp" charEncoding="UTF-8">
												<c:param name="fdKey" value="editOnline" />
												<c:param name="load" value="true" />
												<c:param name="bindSubmit" value="false"/>
												<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
												<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
												<c:param name="formBeanName" value="kmSmissiveMainForm" />
												<c:param name="fdTemplateModelId" value="${kmSmissiveMainForm.fdTemplateId}" />
												<c:param name="fdTemplateModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveTemplate" />
												<c:param name="fdTemplateKey" value="mainOnline" />
												<c:param name="fdTempKey" value="${kmSmissiveMainForm.fdTemplateId}" />
											</c:import>
										</c:if>
										<c:if test="${editStatus != true}">
											<c:import url="/sys/attachment/sys_att_main/wps/center/sysAttMain_view.jsp" charEncoding="UTF-8">
												<c:param name="fdKey" value="editOnline" />
												<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
												<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
												<c:param name="formBeanName" value="kmSmissiveMainForm" />
											</c:import>
										</c:if>
									</c:when>
									<c:when test="${pageScope.readOLConfig == '4'}">
										<%
											//以下代码用于附件不通过form读取的方式
											List sysAttMains = (List)pageContext.getAttribute("sysAttMainContent");
											if (sysAttMains == null || sysAttMains.isEmpty()) {
												try {
													String _modelId = request.getParameter("kmSmissiveMainForm_fdId");
													if (StringUtil.isNotNull(_modelId)) {
														ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService");
														sysAttMains = sysAttMainService.findByModelKey("com.landray.kmss.km.smissive.model.KmSmissiveMain", _modelId, "editOnline");
													}
													if (sysAttMains != null && !sysAttMains.isEmpty()) {
														request.setAttribute("djSysAttMains", sysAttMains);
													}
												} catch (Exception e) {

												}
											} else {
												request.setAttribute("djSysAttMains",sysAttMains);
											}
										%>
										<c:import url="/sys/attachment/sys_att_main/dianju/sysAttMain_view_preview.jsp" charEncoding="UTF-8">
											<c:param name="showDownload" value="false" />
											<c:param name="isAtt" value="false" />
											<c:param name="showChangeView" value="true" />
										</c:import>
									</c:when>
									<c:when test="${pageScope.readOLConfig == '6'}">
										<c:set var="attForms" value="${kmSmissiveMainForm.attachmentForms['editOnline']}" />
										<c:forEach items="${attForms.attachments}" var="sysAttMainx" varStatus="vstatus">
											<c:set var="fdAttMainId" value="${sysAttMainx.fdId}" scope="request" />
										</c:forEach>
										<c:set var="sysAttMain" value="${kmSmissiveMainForm.attachmentForms['editOnline'].attachments[0]}" />
										<%
											SysAttMain sysAttMain = (SysAttMain) pageContext.getAttribute("sysAttMain");
											request.setAttribute("attId", sysAttMain.getFdId());
										%>
										<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_include_viewHtml.jsp" charEncoding="UTF-8">
											<c:param name="fdKey" value="editOnline" />
											<c:param name="fdAttType" value="office" />
											<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
											<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
											<c:param name="formBeanName" value="kmSmissiveMainForm" />
											<c:param name="contentFlag" value="true" />
											<c:param name="isShowImg" value="${isShowImg}" />
											<c:param name="buttonDiv" value="missiveButtonDiv" />
											<c:param name="isExpand" value="true" />
											<c:param name="showAllPage" value="${showAllPage}" />
											<c:param name="bookMarks"
													 value="docSubject:${kmSmissiveMainForm.docSubject},docAuthorName:${kmSmissiveMainForm.docAuthorName},fdUrgency:${kmSmissiveMainForm.fdUrgencyName},fdTemplateName:${kmSmissiveMainForm.fdTemplateName},docCreateTime:${kmSmissiveMainForm.docCreateTime},fdSecret:${kmSmissiveMainForm.fdSecretName},fdFileNo:${kmSmissiveMainForm.fdFileNo},fdMainDeptName:${kmSmissiveMainForm.fdMainDeptName},fdSendDeptNames:${kmSmissiveMainForm.fdSendDeptNames},fdCopyDeptNames:${kmSmissiveMainForm.fdCopyDeptNames},fdIssuerName:${kmSmissiveMainForm.fdIssuerName},docCreatorName:${kmSmissiveMainForm.docCreatorName}" />
										</c:import>
									</c:when>
									<c:otherwise>
										<c:set var="attForms" value="${kmSmissiveMainForm.attachmentForms['editOnline']}" />
										<c:forEach items="${attForms.attachments}" var="sysAttMainx" varStatus="vstatus">
											<c:set var="fdAttMainId" value="${sysAttMainx.fdId}" scope="request" />
										</c:forEach>
										<c:set var="sysAttMain" value="${kmSmissiveMainForm.attachmentForms['editOnline'].attachments[0]}" />
										<%
											SysAttMain sysAttMain = (SysAttMain) pageContext.getAttribute("sysAttMain");
											request.setAttribute("attId", sysAttMain.getFdId());
											String suffix = sysAttMain.getFdFileName()
													.substring(sysAttMain.getFdFileName().lastIndexOf(".") + 1);
											if ("doc".equals(suffix) || "docx".equals(suffix)) {
										%>

										<%
											if (com.landray.kmss.sys.attachment.util.JgWebOffice.isJGEnabled()
													&& JgWebOffice.isExistViewPath(request)) {
										%>
										<c:if test="${isShowImg and editStatus != true}">
											<table class="tb_normal" width="100%">
												<tr>
													<td class="td_normal_title" width=15%>
														<bean:message bundle="km-smissive" key="kmSmissiveMain.prompt.title" />
													</td>
													<td colspan="3">
														<font style="text-align: center"><bean:message bundle="km-smissive" key="kmSmissiveMain.prompt" /></font>
													</td>
												</tr>
											</table>
										</c:if>
										<%
											}
										%>
										<div id="missiveButtonDiv"
											 style="text-align: right; padding-bottom: 5px">
											&nbsp;
											<%
												if (JgWebOffice.isJGEnabled() && Boolean.parseBoolean(
														KmSmissiveConfigUtil.isShowImg(((KmSmissiveMainForm) request
																.getAttribute("kmSmissiveMainForm"))))) {
											%>
											<c:choose>
												<c:when test="${isIE}">
													<kmss:auth
															requestURL="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=view&fdId=${param.fdId}"
															requestMethod="GET">
														<a href="javascript:void(0);" class="attswich"
														   onclick="Com_OpenWindow('kmSmissiveMain.do?method=view&fdId=${param.fdId}&isShowImg=${isShowImg}','_self');">
															<bean:message bundle="km-smissive" key="smissive.button.change.view" />
														</a>
													</kmss:auth>
												</c:when>
												<c:otherwise>
													<%
														if (JgWebOffice.isJGMULEnabled()) {
													%>
													<kmss:auth
															requestURL="/km/smissive/km_smissive_main/kmSmissiveMain.do?method=view&fdId=${param.fdId}"
															requestMethod="GET">
														<a href="javascript:void(0);" class="attswich"
														   onclick="Com_OpenWindow('kmSmissiveMain.do?method=view&fdId=${param.fdId}&isShowImg=${isShowImg}','_self');">
															<bean:message bundle="km-smissive" key="smissive.button.change.view" />
														</a>
													</kmss:auth>
													<%
														}
													%>
												</c:otherwise>
											</c:choose>
											<%
												}
											%>
										</div>
										<c:if test="${editStatus == true}">
											<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_edit.jsp" charEncoding="UTF-8">
												<c:param name="fdKey" value="editOnline" />
												<c:param name="fdAttType" value="office" />
												<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
												<c:param name="fdModelName"
														 value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
												<c:param name="formBeanName" value="kmSmissiveMainForm" />
												<c:param name="buttonDiv" value="missiveButtonDiv" />
												<c:param name="isReadOnly" value="${isReadOnly}" />
												<c:param name="isToImg" value="false" />
											</c:import>
										</c:if>
										<c:if test="${editStatus != true}">
											<c:choose>
												<c:when test="${pageScope.readOLConfig == '5'}">
													<c:import url="/sys/attachment/sys_att_main/wps/center/sysAttMain_view.jsp" charEncoding="UTF-8">
														<c:param name="fdKey" value="editOnline" />
														<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
														<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
														<c:param name="formBeanName" value="kmSmissiveMainForm" />
													</c:import>
												</c:when>
												<c:otherwise>
													<c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_include_viewHtml.jsp" charEncoding="UTF-8">
														<c:param name="fdKey" value="editOnline" />
														<c:param name="fdAttType" value="office" />
														<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
														<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
														<c:param name="formBeanName" value="kmSmissiveMainForm" />
														<c:param name="contentFlag" value="true" />
														<c:param name="isShowImg" value="${isShowImg}" />
														<c:param name="buttonDiv" value="missiveButtonDiv" />
														<c:param name="isExpand" value="true" />
														<c:param name="showAllPage" value="${showAllPage}" />
														<c:param name="bookMarks"
																 value="docSubject:${kmSmissiveMainForm.docSubject},docAuthorName:${kmSmissiveMainForm.docAuthorName},fdUrgency:${kmSmissiveMainForm.fdUrgencyName},fdTemplateName:${kmSmissiveMainForm.fdTemplateName},docCreateTime:${kmSmissiveMainForm.docCreateTime},fdSecret:${kmSmissiveMainForm.fdSecretName},fdFileNo:${kmSmissiveMainForm.fdFileNo},fdMainDeptName:${kmSmissiveMainForm.fdMainDeptName},fdSendDeptNames:${kmSmissiveMainForm.fdSendDeptNames},fdCopyDeptNames:${kmSmissiveMainForm.fdCopyDeptNames},fdIssuerName:${kmSmissiveMainForm.fdIssuerName},docCreatorName:${kmSmissiveMainForm.docCreatorName}" />
													</c:import>
												</c:otherwise>
											</c:choose>
										</c:if>
										<%
										} else if ("pdf".equals(suffix)
												&& (JgWebOffice.isJGPDFEnabled() || JgWebOffice.isExistViewPath(request))) {
										%>
										<div id="downloadDiv" style="text-align: right; padding-bottom: 5px">&nbsp;
											<kmss:authShow roles="ROLE_KMSMISSIVE_PRINTCONTENT">
												<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=${attId}" requestMethod="GET">
													<a href="javascript:void(0);" class="attprint"
													   onclick="Com_OpenWindow('${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=${attId }','_blank')">
														打印正文
													</a>&nbsp;
												</kmss:auth>
											</kmss:authShow>
											<kmss:authShow roles="ROLE_KMSMISSIVE_DOWNLOADCONTENT">
												<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${attId}" requestMethod="GET">
													<a href="javascript:void(0);" class="attdownloadcontent"
													   onclick="Com_OpenWindow('${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${attId}');">
														正文下载 </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												</kmss:auth>
											</kmss:authShow>
										</div>
										<c:choose>
											<c:when test="${empty showAllPage or showAllPage}">
												<iframe width="100%" style="min-height: 565px"
														src='<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${attId}&showAllPage=true&newOpen=true&inner=yes"/>'
														frameborder="0"> </iframe>
											</c:when>
											<c:otherwise>
												<iframe width="100%" style="min-height: 565px"
														src='<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${attId}&toolPosition=top&newOpen=true&inner=yes"/>'
														frameborder="0"> </iframe>
											</c:otherwise>
										</c:choose>
										<%
										} else {
										%>
										<div class="lui_form_spacing"></div>
										<div>
											<div class="lui_form_subhead">
												<img
														src="${KMSS_Parameter_ContextPath}sys/attachment/view/img/attachment.png">
												<bean:message key="kmSmissiveMain.mainonline"
															  bundle="km-smissive" />
											</div>
											<c:import
													url="/sys/attachment/sys_att_main/sysAttMain_view.jsp"
													charEncoding="UTF-8">
												<c:param name="formBeanName" value="kmSmissiveMainForm" />
												<c:param name="fdKey" value="editOnline" />
												<c:param name="fdModelId" value="${param.fdId}" />
												<c:param name="fdModelName"
														 value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
											</c:import>
										</div>
										<%
											}
										%>
									</c:otherwise>
								</c:choose></td>
						</tr>
					</c:if>
				</c:if>
			</table>
		</div>
	</c:if>
	<c:if test="${not empty kmSmissiveMainForm.attachmentForms['mainAtt'].attachments}">
		<div class="lui_form_content_frame" style="padding-top: 10px">
			<c:if
					test="${not empty kmSmissiveMainForm.attachmentForms['mainAtt'].attachments}">
				<div class="lui_form_spacing"></div>
				<div>
					<div class="lui_form_subhead">
						<img
								src="${KMSS_Parameter_ContextPath}sys/attachment/view/img/attachment.png">
							${ lfn:message('sys-doc:sysDocBaseInfo.docAttachments') }(${fn:length(kmSmissiveMainForm.attachmentForms['mainAtt'].attachments)})
					</div>
					<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp"
							  charEncoding="UTF-8">
						<c:param name="fdKey" value="mainAtt" />
						<c:param name="formBeanName" value="kmSmissiveMainForm" />
					</c:import>
				</div>
			</c:if>
		</div>
	</c:if>
	<c:set var="collapsed" value="false"></c:set>
	<c:if test="${kmSmissiveMainForm.docStatusFirstDigit>='3'}">
		<c:set var="collapsed" value="true"></c:set>
	</c:if>
	<c:choose>
		<c:when test="${param.approveModel eq 'right'}">
			<ui:tabpanel suckTop="true" layout="sys.ui.tabpanel.sucktop" var-count="6" var-average='false' var-useMaxWidth='true'
						 var-supportExpand="true" var-expand="true">
				<%@ include file="/km/smissive/km_smissive_main_ui/kmSmissiveMain_viewContent.jsp"%>
			</ui:tabpanel>
		</c:when>
		<c:otherwise>
			<ui:tabpage expand="false" >
				<%@ include file="/km/smissive/km_smissive_main_ui/kmSmissiveMain_viewContent.jsp"%>
			</ui:tabpage>
		</c:otherwise>
	</c:choose>
	<%@ include file="/km/smissive/cookieUtil_script.jsp"%>
	<script>
		//文件编号
		function generateFileNum(){
			if("${fdNoId}" !=""){
				//文件编号的时候，审批人不一定有编辑正文的权限，先接触文档保护
				if(Attachment_ObjectInfo['mainOnline'] && Attachment_ObjectInfo['mainOnline'].ocxObj){
					Attachment_ObjectInfo['mainOnline'].ocxObj.EditType = "1";
				}
				var docNum = document.getElementsByName("fdFileNo")[0];
				path=Com_GetCurDnsHost()+Com_Parameter.ContextPath+'km/smissive/km_smissive_main_ui/kmSmissiveNum.jsp?fdId=${kmSmissiveMainForm.fdId}&fdNumberId=${fdNoId}';
				dialog.iframe(path,"文件编号",function(rtn){
					if(rtn!="undefined"&&rtn!=null){
						docNum.value = rtn;
						document.getElementById("docnum").innerHTML = rtn;
						//填充控件中的文号书签
						if(Attachment_ObjectInfo['mainOnline'] && typeof(Attachment_ObjectInfo['mainOnline'].setBookmark) == 'function'){
							Attachment_ObjectInfo['mainOnline'].setBookmark('fdFileNo',document.getElementsByName("fdFileNo")[0].value);
							if("${isReadOnly}"=="true" && Attachment_ObjectInfo['mainOnline'].ocxObj){
								if(!Attachment_ObjectInfo['mainOnline'].canCopy){
									Attachment_ObjectInfo['mainOnline'].ocxObj.CopyType = "1";
									Attachment_ObjectInfo['mainOnline'].ocxObj.EditType = "0,1";
								}else{
									Attachment_ObjectInfo['mainOnline'].ocxObj.CopyType = "1";
									Attachment_ObjectInfo['mainOnline'].ocxObj.EditType = "4,1";
								}
							}
						}
						if(Attachment_ObjectInfo['editOnline'] && typeof(Attachment_ObjectInfo['editOnline'].setBookmark) == 'function'){
							Attachment_ObjectInfo['editOnline'].setBookmark('fdFileNo',document.getElementsByName("fdFileNo")[0].value);
							if("${isReadOnly}"=="true" && Attachment_ObjectInfo['editOnline'].ocxObj){
								if(!Attachment_ObjectInfo['editOnline'].canCopy){
									Attachment_ObjectInfo['editOnline'].ocxObj.CopyType = "1";
									Attachment_ObjectInfo['editOnline'].ocxObj.EditType = "0,1";
								}else{
									Attachment_ObjectInfo['editOnline'].ocxObj.CopyType = "1";
									Attachment_ObjectInfo['editOnline'].ocxObj.EditType = "4,1";
								}
							}
						}
					}
				},{width:400,height:200});
			}
		}

		<c:if  test="${kmSmissiveMainForm.sysWfBusinessForm.fdNodeAdditionalInfo.modifyDocNum =='true'}">
		Com_Parameter.event["submit"].push(function(){
			//操作类型为通过类型 ，才写回编号
			if(lbpm.globals.getCurrentOperation().operation['isPassType'] == true){
				var docNum = document.getElementsByName("fdFileNo")[0];
				var isRepeat=true;
				var results;
				if(""!=docNum.value){
					var url="${KMSS_Parameter_ContextPath}km/smissive/km_smissive_main/kmSmissiveMain.do?method=saveDocNum";
					$.ajax({
						type:"post",
						url:url,
						data:{fdDocNum:docNum.value,fdId:"${kmSmissiveMainForm.fdId}"},
						async:false,    //用同步方式
						dataType:"json",
						success:function(res){
							results = res;
							//如果编号被占用，则删除cookie和 数据库保存的编号
							if("${fdNoId}" !=""){
								var docBufferNum = getTempNumberFromDb("${fdNoId}");
								if(results['isRepeat']=="true"){
									if(docBufferNum && docNum.value == docBufferNum){
										delTempNumFromDb("${fdNoId}",decodeURI(docBufferNum));
									}
									alert('<bean:message key="kmSmissiveMain.message.error.fdDocNum.repeat" bundle="km-smissive" />');
									isRepeat = false;
								}else{
									if(docBufferNum){
										delTempNumFromDb("${fdNoId}",decodeURI(docBufferNum));
									}
								}
							}
						}
					});
					if(results['flag']=="false"&&results['isRepeat']!="true"){
						alert("更新文档编号不成功");
						return false
					}else{
						return isRepeat;
					}
				}
				return isRepeat;
			}else{
				return true;
			}
		});
		</c:if>
	</script>
	<script language="javascript" for="window" event="onload">
		//Doc_SetCurrentLabel("Label_Tabel",2);
		//var obj = document.getElementsByName("mainOnline_print");	//公司产品JS库有问题，其中生成的按钮没有ID属性，导致ie6不能够用该语句查找
		//obj[0].style.display = "none";
		var tt = document.getElementsByTagName("INPUT");

		for(var i=0;i<tt.length;i++){
			if(tt[i].name == "mainOnline_print"){
				tt[i].style.display = "none";
			}
			if(tt[i].name == "mainOnline_printPreview"){
				tt[i].style.display = "none";
			}
			if(tt[i].name == "mainOnline_download"){
				tt[i].style.display = "none";
			}
		}


	</script>
	<script type="text/javascript">
		<c:if test="${_isWpsWebOffice eq 'true'}">
			Com_IncludeFile("wps_utils.js",Com_Parameter.ContextPath + "sys/attachment/sys_att_main/wps/oaassist/js/","js",true);
		</c:if>
		function printContext(fdId){
			var wpsParam = {};
			wpsParam['wpsExtAppModel'] = "Smissive";
			openWpsOAAssit(fdId,wpsParam);
		}
	</script>
</template:replace>
<c:choose>
	<c:when test="${param.approveModel eq 'right'}">
		<template:replace name="barRight">
			<c:choose>
				<c:when test="${kmSmissiveMainForm.docStatus>='30' || kmSmissiveMainForm.docStatus=='00'}">
					<ui:accordionpanel>
						<!-- 基本信息-->
						<c:import url="/km/smissive/km_smissive_main_ui/kmSmissiveMain_viewBaseInfo.jsp" charEncoding="UTF-8">
						</c:import>
						<!-- 关联配置 -->
						<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmSmissiveMainForm" />
							<c:param name="approveType" value="right" />
						</c:import>
					</ui:accordionpanel>
				</c:when>
				<c:otherwise>
					<ui:tabpanel layout="sys.ui.tabpanel.vertical.icon" id="barRightPanel" >
						<c:if test="${kmSmissiveMainForm.docStatus != '10'}">
							<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
								<c:param name="formName" value="kmSmissiveMainForm" />
								<c:param name="fdKey" value="smissiveDoc" />
								<c:param name="showHistoryOpers" value="true" />
								<c:param name="isExpand" value="true" />
								<c:param name="approveType" value="right" />
								<c:param name="approvePosition" value="right" />
							</c:import>
						</c:if>
						<!-- 审批记录 -->
						<c:import url="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_list_content.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmSmissiveMainForm" />
							<c:param name="fdModelId" value="${kmSmissiveMainForm.fdId}" />
							<c:param name="fdModelName" value="com.landray.kmss.km.smissive.model.KmSmissiveMain" />
						</c:import>
						<!-- 关联配置 -->
						<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="kmSmissiveMainForm" />
							<c:param name="approveType" value="right" />
							<c:param name="needTitle" value="true" />
						</c:import>
						<c:import url="/km/smissive/km_smissive_main_ui/kmSmissiveMain_viewBaseInfo.jsp" charEncoding="UTF-8">
						</c:import>
					</ui:tabpanel>
				</c:otherwise>
			</c:choose>
		</template:replace>
	</c:when>
	<c:otherwise>
		<template:replace name="nav">
			<c:import url="/sys/relation/import/sysRelationMain_view.jsp" charEncoding="UTF-8">
				<c:param name="formName" value="kmSmissiveMainForm" />
			</c:import>
		</template:replace>
	</c:otherwise>
</c:choose>

