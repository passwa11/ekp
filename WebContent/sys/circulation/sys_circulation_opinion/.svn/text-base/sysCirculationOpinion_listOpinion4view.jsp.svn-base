<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List,java.util.ArrayList,com.landray.kmss.util.StringUtil,com.landray.kmss.util.ResourceUtil,com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@page import="com.landray.kmss.sys.attachment.dao.ISysAttMainCoreInnerDao"%>
<%@page import="com.landray.kmss.sys.circulation.model.SysCirculationOpinion"%>
<%@page import="com.landray.kmss.sys.circulation.util.CirculationUtil"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<template:include ref="default.simple" sidebar="no">
	<template:replace name="body">
	<link rel="Stylesheet" href="${LUI_ContextPath}/sys/circulation/resource/css/circulate.css?s_cache=${MUI_Cache}" />
	<script type="text/javascript">
	seajs.use(['theme!form']);
	Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
	Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
	Com_IncludeFile("upload.css","${LUI_ContextPath}/sys/attachment/view/img/","css",true);
	Com_IncludeFile("dnd.css","${LUI_ContextPath}/sys/attachment/view/img/","css",true);
	</script>
	<script language="JavaScript">
		//传阅意见后会发回刷新事件，在这个事件中再查找上一级页面刷新待办
		function refreshNotify(){
			try{
				if(window.parent.opener!=null) {
					try {
						if (window.parent.opener.LUI) {
							window.parent.opener.LUI.fire({ type: "topic", name: "successReloadPage" });
							return;
						}
					} catch(e) {}
					if (window.parent.LUI) {
						LUI.fire({ type: "topic", name: "successReloadPage" }, window.parent.opener);
					}
					var hrefUrl= window.parent.opener.location.href;
					var localUrl = location.href;
					if(hrefUrl.indexOf("/sys/notify/")>-1 && localUrl.indexOf("/sys/notify/")==-1){
						window.parent.opener.location.reload();
					}
				}
			}catch(e){}
		}
		Com_AddEventListener(window,"load",refreshNotify);
	</script>

	<div class="opinionContainer">
		<div class="label_title"> 
			<div class="title"><bean:message bundle="sys-circulation" key="sysCirculationMain.list" /></div>
		</div>
		<c:choose>
			<c:when test="${fn:length(queryList)>0}">
				<ul class="opinion-list">
					<c:forEach items="${queryList}" var="sysCirculationOpinion" varStatus="vstatus">
						<li>
							<div class="opinionInfo">
								<span>${sysCirculationOpinion.fdBelongPerson.fdName}</span>
								<span>${sysCirculationOpinion.fdBelongPerson.fdParent.deptLevelNames}</span>
								<span><kmss:showDate value="${sysCirculationOpinion.fdWriteTime}" type="datetime"></kmss:showDate></span></div>
							<div class="opinionContent">${sysCirculationOpinion.docContent}</div>
							<div>
								<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
									<c:param name="fdKey" value="attachment" />
									<c:param name="formBeanName" value="sysCirculationOpinionForm" />
									<c:param name="fdModelId" value="${sysCirculationOpinion.fdId }" />
									<c:param name="fdModelName" value="com.landray.kmss.sys.circulation.model.SysCirculationOpinion" />
									<c:param name="fdForceDisabledOpt" value="edit;print;copy" />
									<c:param name="isShowDownloadCount" value="false" />
								</c:import>
							</div>
						</li>
					</c:forEach>
				</ul>
			</c:when>
			<c:otherwise>
				<div class="prompt_container" style="text-align: left;">
					<bean:message key="return.noRecord" />
				</div>
			</c:otherwise>
		</c:choose>
	</div>
	</template:replace>
</template:include>
