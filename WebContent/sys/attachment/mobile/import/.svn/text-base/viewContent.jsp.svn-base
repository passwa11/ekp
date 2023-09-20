<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttViewerUtil"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsWebOfficeUtil"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCenterUtil"%>
<%@page import="java.util.List,com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="java.util.regex.Pattern"%>
<%@ page import="java.util.regex.Matcher"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@ page import="com.landray.kmss.sys.attachment.util.SysAttConfigUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<c:if test="${empty tiny }">
	<mui:cache-file name="mui-attachment.js" cacheType="md5"/>
</c:if>
<%
String formBeanName = request.getParameter("formName");
//新闻管理正文移动端不可下载 #103107 #105409
boolean newsFlag = false;
if("sysNewsMainForm".equals(formBeanName) || "kmDocKnowledgeForm".equals(formBeanName) || "kmImeetingSummaryForm".equals(formBeanName)){
	newsFlag = true;
}
pageContext.setAttribute("newsFlag", newsFlag);

Object formBean = null;
if(formBeanName != null && formBeanName.trim().length() != 0){
	formBean = pageContext.getAttribute(formBeanName);
	if(formBean == null){
		formBean = request.getAttribute(formBeanName);
	}
	if(formBean == null){
		formBean = session.getAttribute(formBeanName);
	}
	pageContext.setAttribute("_formBean", formBean);
}else{
	formBeanName = "com.landray.kmss.web.taglib.FormBean";
}
//得到文档状态，用于控制留痕按钮在发布状态中不显示
String docStatus = null;
try{
docStatus = (String)org.apache.commons.beanutils.PropertyUtils.getProperty(formBean,"docStatus");
pageContext.setAttribute("_docStatus", docStatus);
}catch(Exception e){}
//得到文档标题,下载时取文档标题
String fileName = null;
try{
	fileName = (String)org.apache.commons.beanutils.PropertyUtils.getProperty(formBean,"docSubject"); 
}catch(Exception e){
	try {
		fileName = (String)org.apache.commons.beanutils.PropertyUtils.getProperty(formBean,"fdName");
	} catch(Exception _e) {}
}

if(StringUtil.isNotNull(fileName)){
	Pattern pattern = Pattern.compile("[\\s\\\\/:\\*\\?\\\"<>\\|]");
	Matcher matcher = pattern.matcher(fileName);
	fileName = matcher.replaceAll("");
}
   
//标题加上".doc"作为文件名
//pageContext.setAttribute("_fileName", fileName+".doc");


Object originFormBean = pageContext.getAttribute("com.landray.kmss.web.taglib.FormBean");
pageContext.setAttribute("com.landray.kmss.web.taglib.FormBean", formBean);
if(formBean == null){
	formBean = com.landray.kmss.web.taglib.TagUtils.getInstance().lookup(pageContext,
			formBeanName, null);
	pageContext.setAttribute("_formBean", formBean);
}
%>
<c:set var="_fdKey" value="${param.fdKey}" />

<c:if test="${param.formName!=null && param.formName!=''}">
	<c:set var="_formBean" value="${requestScope[param.formName]}" />
	<c:set var="attForms" value="${_formBean.attachmentForms[_fdKey]}" />
</c:if>

<c:set var="_fdModelName" value="${param.fdModelName}" />
<c:if test="${_fdModelName==null || _fdModelName == ''}">
	<c:if test="${_formBean!=null}">
		<c:set var="_fdModelName" value="${_formBean.modelClass.name}" />
	</c:if>
</c:if>

<c:set var="_fdModelId" value="${param.fdModelId}" />
<c:if test="${_fdModelId==null || _fdModelId == ''}">
	<c:if test="${_formBean!=null}">
		<c:set var="_fdModelId" value="${_formBean.fdId}" />
	</c:if>
</c:if>

<c:set var="_fdMulti" value="false" />
<c:if test="${param.fdMulti!=null}">
	<c:set var="_fdMulti" value="${param.fdMulti}" />
</c:if>

<%-- fdAttType: byte/pic--%>
<c:set var="_fdAttType" value="byte" />
<c:if test="${param.fdAttType!=null}">
	<c:set var="_fdAttType" value="${param.fdAttType}" />
</c:if>

<%-- fdViewType: normal/simple--%>
<c:set var="_fdViewType" value="normal"></c:set>
<c:if test="${param.fdViewType!=null}">
	<c:set var="_fdViewType" value="${param.fdViewType}" />
</c:if>
<c:if test="${attForms!=null}">
 <c:set var="sysAttMains" value="${attForms.attachments}" />
</c:if>
<%
		//以下代码用于附件不通过form读取的方式
		List sysAttMains = (List)pageContext.getAttribute("sysAttMains");
		pageContext.setAttribute("_downLoadNoRight",new com.landray.kmss.third.pda.model.PdaRowsPerPageConfig().getFdAttDownload());
		if(sysAttMains==null || sysAttMains.isEmpty()){
			try{
				String _modelName = request.getParameter("fdModelName");
				String _modelId = request.getParameter("fdModelId");
				String _key = request.getParameter("fdKey");
				if(StringUtil.isNotNull(_modelName) 
						&& StringUtil.isNotNull(_modelId) 
						&& StringUtil.isNotNull(_key)){
					ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService)SpringBeanUtil.getBean("sysAttMainService");
					sysAttMains = sysAttMainService.findByModelKey(_modelName,_modelId,_key);
				}
				if(sysAttMains!=null && !sysAttMains.isEmpty()){
					pageContext.setAttribute("sysAttMains",sysAttMains);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
%>
<%
	pageContext.setAttribute("wpsWebOfficeEnable", SysAttWpsWebOfficeUtil.isEnable());
	pageContext.setAttribute("wpsCloudOfficeEnable", SysAttWpsCloudUtil.isEnable());
	pageContext.setAttribute("wpsCloudOfficeEnableEdit", SysAttWpsCloudUtil.isEnable(true));
	pageContext.setAttribute("wpsCloudOfficeEnableMobileEdit", SysAttWpsCloudUtil.isEnableMobile(true));
	pageContext.setAttribute("wpsPreviewIsLinux", SysAttWpsCloudUtil.checkWpsPreviewIsLinux());
	pageContext.setAttribute("wpsPreviewIsWindows", SysAttWpsCloudUtil.checkWpsPreviewIsWindows());
	pageContext.setAttribute("wpsCenterWebOfficeEnable", SysAttWpsCenterUtil.isWPSCenterEnableMobile(true));
	pageContext.setAttribute("readOLConfig", SysAttConfigUtil.getReadOLConfig());
%>
<c:if test="${fn:length(sysAttMains)>0}">
	<div class="muiAttachments muiAttachments${_fdViewType}">
		<div data-dojo-type="sys/attachment/mobile/js/AttachmentList" style="padding-left:0px"
			data-dojo-props="fdKey:'${_fdKey}',fdModelName:'${_fdModelName}',fdModelId:'${_fdModelId}',viewType:'${_fdViewType}',editMode:'view'">
			<c:forEach var="sysAttMain" items="${sysAttMains}" varStatus="vsStatus">
				<c:set value="false" var="canDownload"></c:set>
				<!-- 下载权限 -->
				<kmss:auth
					requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${sysAttMain.fdId}"
					requestMethod="GET">
					<c:set value="true" var="canDownload"></c:set>
				</kmss:auth>
				<c:if test="${_docStatus=='30'}">
				<% if(!com.landray.kmss.util.UserUtil.getKMSSUser().isAdmin()){%>
					<c:if test="${not empty param.contentFlag}">
						<c:set value="false" var="canDownload"></c:set>
						<c:set value="false" var="downloadContentFlag"></c:set>
						<c:set value="false" var="readDownloadFlag"></c:set>
						<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=downloadContent&fdModelName=${_fdModelName}&fdId=${sysAttMain.fdId}" requestMethod="GET">
							<c:set value="true" var="downloadContentFlag"></c:set>
						</kmss:auth>
						<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=readDownload&fdModelName=${_fdModelName}&fdId=${sysAttMain.fdId}" requestMethod="GET">
							<c:set value="true" var="readDownloadFlag"></c:set>
						</kmss:auth>
						<c:if test="${'true' eq downloadContentFlag and 'true' eq readDownloadFlag}">
							<c:set value="true" var="canDownload"></c:set>
						</c:if>
					</c:if>
				<%} %>	
				</c:if>

				<c:set value="true" var="canRead"></c:set>
				<!-- 查看权限 -->
				<kmss:auth
					requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=read&fdId=${sysAttMain.fdId}"
					requestMethod="GET">
					<c:set value="true" var="canRead"></c:set>
				</kmss:auth>
				
				<c:set  var="canEdit" value="false"></c:set>
				<%--编辑权限  --%>
				<c:if test="${param.editContent == 'true'}">
					<c:set  var="canEdit" value="true"></c:set>
				</c:if>
				
				<c:if test="${canDownload != true }">
					<c:if test="${_downLoadNoRight==true || _downLoadNoRight=='true'}">
						<c:set var="downLoadUrl" value="/third/pda/attdownload.jsp?fdId=${sysAttMain.fdId}&open=1" />
						<c:set value="true" var="canDownload"></c:set>
					</c:if>
				</c:if>
				
				<c:set var="_customSubject" value=""></c:set>
				<c:if test="${param.customSubject!=null}">
					<c:set var="_customSubject" value="${param.customSubject}"/>
				</c:if>
				<c:if test="${sysAttMain.fdAttType!='pic'}">
					<c:set var="downLoadUrl"
						value="/sys/attachment/sys_att_main/sysAttMain.do?method=readDownload&fdId=${sysAttMain.fdId}" />
					<c:if test="${_downLoadNoRight==true || _downLoadNoRight=='true'}">
						<c:set var="downLoadUrl"
							value="/third/pda/attdownload.jsp?fdId=${sysAttMain.fdId}" />
					</c:if>
					<%
						SysAttMain sysAttMain = (SysAttMain) pageContext
											.getAttribute("sysAttMain");
						String fileExt = sysAttMain.getFdFileName().substring(sysAttMain.getFdFileName().lastIndexOf(".")+1);
						if(StringUtil.isNotNull(fileExt)){
							fileName = StringEscapeUtils.escapeJavaScript(fileName+"."+fileExt);
						}else{
							fileName = StringEscapeUtils.escapeJavaScript(fileName+".doc");
						}
						pageContext.setAttribute("_fileName", fileName);
						String path = SysAttViewerUtil.getViewerPath(
								sysAttMain, request);
						Boolean hasViewer = Boolean.FALSE;
						if (StringUtil.isNotNull(path))
							hasViewer = Boolean.TRUE;
						pageContext.setAttribute("hasViewer", hasViewer);
					%>
					<div
						data-dojo-type="sys/attachment/mobile/js/AttachmentViewContentListItem"
						data-dojo-props="name:'${_fileName}',
							customSubject:'${_customSubject }',
							size:'${sysAttMain.fdSize}',
							href:'${downLoadUrl}',
							thumb:'/sys/attachment/sys_att_main/sysAttMain.do?method=view&picthumb=small&fdId=${sysAttMain.fdId}',
							fdId:'${sysAttMain.fdId}',
							type:'${sysAttMain.fdContentType}',
							hasViewer:${hasViewer },
							canEdit:${canEdit},
							canDownload:${canDownload},
							canRead:${canRead},
							newsFlag:${newsFlag},
							wpsCloudOfficeEnable:${wpsCloudOfficeEnable},
							wpsCenterWebOfficeEnable:${wpsCenterWebOfficeEnable},
							wpsCloudOfficeEnableMobileEdit:${wpsCloudOfficeEnableMobileEdit},
							wpsWebOfficeEnable:${wpsWebOfficeEnable},
							wpsCloudOfficeEnableEdit:${wpsCloudOfficeEnableEdit},
							wpsPreviewIsLinux:${wpsPreviewIsLinux},
							wpsPreviewIsWindows:${wpsPreviewIsWindows},
							readOLConfig:${readOLConfig}">
					</div>
				</c:if>
			</c:forEach>
		</div>
	</div>
</c:if>
