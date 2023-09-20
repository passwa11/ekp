<%@ page import="com.landray.kmss.sys.attachment.model.SysAttMain" %>
<%@ page import="com.landray.kmss.sys.attachment.util.SysAttConfigUtil" %>
<%@ page import="com.landray.kmss.sys.attachment.util.JgWebOffice" %>
<%@ page import="com.landray.kmss.util.UserUtil" %>
<%@ page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.collections.CollectionUtils" %>
<%@ page import="com.landray.kmss.sys.attachment.integrate.dianju.interfaces.ISysAttachmentDianJuProvider" %>
<%@ page import="org.apache.commons.lang3.StringUtils" %>
<%@ page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsoaassistUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
	//版式文件不显示切换阅读
	String isPdf = "false";
	//是否是附件
	String isAtt = "false";
	//打印
	boolean canPrint = false;
	//下载
	String showDownload = "true";
	//切换阅读模式
	String showChangeView = "false";
	String url = null;
	Object previewUrl = request.getAttribute("previewUrl");
	Object attMain = null;
	SysAttMain sysAttMain = null;
	List attMainList = (List)pageContext.getAttribute("sysAttMains");
	if (CollectionUtils.isNotEmpty(attMainList)) {
		attMain = attMainList.get(0);
	} else if (CollectionUtils.isNotEmpty((List)request.getAttribute("djSysAttMains"))) {
		attMain = ((List)request.getAttribute("djSysAttMains")).get(0);
	} else {
		attMain = request.getAttribute("attMain");
	}
	if (attMain != null) {
		sysAttMain = (SysAttMain) attMain;
		if (JgWebOffice.isPDF(sysAttMain.getFdFileName()) || JgWebOffice.isOFD(sysAttMain.getFdFileName())) {
			isPdf = "true";
		}
	}
	pageContext.setAttribute("isPdf", isPdf);
	if (previewUrl == null || "".equals(previewUrl)) {
		if (sysAttMain != null) {
			ISysAttachmentDianJuProvider sysAttachmentDianJuProvider = (ISysAttachmentDianJuProvider) SpringBeanUtil.getBean("sysAttachmentDianJuProvider");
			url = sysAttachmentDianJuProvider.getPreviewUrl(sysAttMain,UserUtil.getKMSSUser().getUserName());
		}
	}else{
		url = (String) previewUrl;
	}
	if (sysAttMain != null) {
		pageContext.setAttribute("fdId", sysAttMain.getFdId());
		if (!"editonline".equals(sysAttMain.getFdKey())) {
			isAtt = "true";
			canPrint = UserUtil.checkAuthentication("/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=" + sysAttMain.getFdId(), "GET");
		}
	}
	url += "&showSeal=false&showDownload=false&showPrint="+canPrint;
	pageContext.setAttribute("previewUrl", url);
	pageContext.setAttribute("onlineToolType",SysAttConfigUtil.getOnlineToolType());
	pageContext.setAttribute("isAtt", isAtt);
	pageContext.setAttribute("showDownload",showDownload);
	pageContext.setAttribute("showChangeView",showChangeView);
	showDownload = request.getParameter("showDownload");
	if (StringUtils.isNotEmpty(showDownload)) {
		pageContext.setAttribute("showDownload",showDownload);
	}
	showChangeView = request.getParameter("showChangeView");
	if (StringUtils.isNotEmpty(showChangeView)) {
		pageContext.setAttribute("showChangeView",showChangeView);
	}
	isAtt = request.getParameter("isAtt");
	if (StringUtils.isNotEmpty(isAtt)) {
		pageContext.setAttribute("isAtt", isAtt);
	}
	//内嵌加载项
	pageContext.setAttribute("wpsoAassistEmbed",SysAttWpsoaassistUtil.isWPSOAassistEmbed());
%>
<style>
	.dianjuBtn{
		float: right;
		margin-right: 15px;
		margin-top: 5px;
		margin-bottom: 5px;
	}
	.dianjuBtnDiv{
		display: flex;
		align-items: center;
	}
	.dianjuDownloadIcon{
		background: url(${KMSS_Parameter_ContextPath}/sys/attachment/sys_att_main/dianju/img/download.png) no-repeat center;
		background-size: contain;
		display: inline-block;
		width: 14px;
		height: 14px;
	}
	.dianjuChangeViewIcon{
		background: url(${KMSS_Parameter_ContextPath}/sys/attachment/sys_att_main/dianju/img/changeView.png) no-repeat center;
		background-size: contain;
		display: inline-block;
		width: 14px;
		height: 14px;
		margin-right: 2px;
	}
	.dianjuBtnFont {
		color: #0a8cd2;
	}
</style>
<%--<script>Com_IncludeFile("jquery.js");</script>--%>
<script type="text/javascript" src="<%=request.getContextPath() %>/resource/js/common.js"></script>
<script>
	function setIframeHeight(iframe) {
		var h = document.documentElement.scrollHeight ||
				document.documentElement.offsetHeight || window.outerHeight;
		if (!h || h < 300) {
			h = 360;
		} else {
			h -= 40;
		}
		iframe.height = h;

	}
</script>
<c:if test="${isPdf=='false'}">
	<c:if test="${isAtt=='true' or showChangeView=='true'}">
		<%--		切换阅读模式 0 金格, 3 wps加载项--%>
		<div class="dianjuBtn">
			<div class="dianjuBtnDiv">
				<span class="dianjuChangeViewIcon"></span>
				<c:if test="${onlineToolType == '0'}">
					<a class="dianjuBtnFont" href="${LUI_ContextPath }/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${fdId }&viewType=jg">${lfn:message('sys-attachment:viewer_hint_2')}</a>
				</c:if>
				<c:if test="${onlineToolType == '3'}">
					<script type="text/javascript" src="${LUI_ContextPath }/sys/attachment/sys_att_main/wps/oaassist/js/wps_utils.js?s_cache=${LUI_Cache }"></script>
					<c:choose>
						<c:when test="${wpsoAassistEmbed == 'true'}">
							<a class="dianjuBtnFont" href="${LUI_ContextPath }/sys/attachment/sys_att_main/sysAttMain.do?method=viewByWpsoAassistEmbed&fdId=${fdId}">${lfn:message('sys-attachment:viewer_hint_2')}</a>
						</c:when>
						<c:otherwise>
							<a class="dianjuBtnFont" href="javascript:void(0);" onclick="openByWpsOaassist('${fdId}');">${lfn:message('sys-attachment:viewer_hint_2')}</a>
						</c:otherwise>
					</c:choose>
				</c:if>
			</div>
		</div>
	</c:if>
</c:if>
<c:if test="${isAtt=='true' and showDownload=='true'}">
	<div class="dianjuBtn">
		<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${fdId}" requestMethod="GET">
			<div class="dianjuBtnDiv">
				<span class="dianjuDownloadIcon"></span>
				<a href="javascript:void(0);" class="dianjuBtnFont" onclick="Com_OpenWindow('${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${fdId}&downloadType=manual');">
					<bean:message bundle="sys-attachment" key="sysAttMain.button.download" />
				</a>
			</div>
		</kmss:auth>
<%--			<kmss:auth requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=${fdId}" requestMethod="GET">--%>
<%--				<a href="javascript:void(0);" class="" onclick="Com_OpenWindow('${KMSS_Parameter_ContextPath}sys/attachment/sys_att_main/sysAttMain.do?method=print&fdId=${fdId }','_blank')">--%>
<%--					<kmss:message key="lbpmNode.subform.print_form" bundle="sys-lbpmservice" />--%>
<%--				</a>--%>
<%--			</kmss:auth>--%>
	</div>
</c:if>
<iframe id="dianju_iframe" src="${previewUrl}" width="100%" frameborder="0" scrolling="no" onload="setIframeHeight(this)"></iframe>

