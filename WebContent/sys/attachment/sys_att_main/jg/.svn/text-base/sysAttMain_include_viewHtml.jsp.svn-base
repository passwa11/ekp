<%@page import="com.landray.kmss.sys.tag.model.SysTagAppConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="java.util.List,com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsWebOfficeUtil"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil"%>
<%@ page import="java.util.regex.Pattern"%>
<%@ page import="java.util.regex.Matcher"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil"%>
<%@page import="com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCenterUtil"%>
<%@page import="com.landray.kmss.sys.attachment.util.SysAttConfigUtil"%>
<%
	Boolean _isWpsCloudEnable = false;
	Boolean _isWpsCenterEnable = false;
	Boolean _isWpsPreviewEnable = false;
	Boolean _isDianJuEnable = false;
	Boolean _isFoxitEnable = false;
	String readOLConfig = SysAttConfigUtil.getReadOLConfig();
	if ("3".equals(readOLConfig)) {
		_isWpsCloudEnable = true;
	} else if ("5".equals(readOLConfig)) {
		_isWpsCenterEnable = true;
	} else if ("2".equals(readOLConfig)) {
		_isWpsPreviewEnable = true;
	} else if ("4".equals(readOLConfig)) {
		_isDianJuEnable = true;
	} else if ("6".equals(readOLConfig)) {
		_isFoxitEnable = true;
	}
	pageContext.setAttribute("_isWpsCloudEnable", _isWpsCloudEnable);
	pageContext.setAttribute("_isWpsCenterEnable", _isWpsCenterEnable);
	pageContext.setAttribute("_isWpsPreviewEnable", _isWpsPreviewEnable);
	pageContext.setAttribute("_isDianJuEnable", _isDianJuEnable);
	pageContext.setAttribute("_isFoxitEnable", _isFoxitEnable);
	pageContext.setAttribute("readOLConfig", readOLConfig);
	//是否是文档附件
	String _isAtt = request.getParameter("isAtt");
	//点聚阅读是否显示切换阅读模式按钮
	String _showChangeView = request.getParameter("showChangeView");
	if (StringUtil.isNotNull(_isAtt)) {
		pageContext.setAttribute("isAtt", _isAtt);
	}
	if (StringUtil.isNotNull(_showChangeView)) {
		pageContext.setAttribute("showChangeView", _showChangeView);
	}
%>

<%
	String formBeanName = request.getParameter("formBeanName");
//是否使用了wps预览转换服务
	String isUseWpsOnlineView = request.getParameter("isUseWpsOnlineView");
//是否使用了wps中台预览转换服务
	String isUseWpsCenterOnlineView = request.getParameter("isUseWpsCenterOnlineView");
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
	String fileName= null;
	try{
		String docSubject = (String)org.apache.commons.beanutils.PropertyUtils.getProperty(formBean,"docSubject");
		if(StringUtil.isNotNull(docSubject)){
			fileName = docSubject;
		}else{
			String fdName = (String)org.apache.commons.beanutils.PropertyUtils.getProperty(formBean,"fdName");
			if(StringUtil.isNotNull(fdName)){
				fileName = fdName;
			}
		}
		Pattern pattern = Pattern.compile("[\\s\\\\/:\\*\\?\\\"<>\\|]");
		Matcher matcher = pattern.matcher(fileName);
		fileName= matcher.replaceAll("");

		if(fileName.length() > 100 ){
			fileName = fileName.substring(0, 100);
		}

		pageContext.setAttribute("_fileName",fileName+".doc");
	}catch(Exception e){}

	Object originFormBean = pageContext.getAttribute("com.landray.kmss.web.taglib.FormBean");
	pageContext.setAttribute("com.landray.kmss.web.taglib.FormBean", formBean);
	if(formBean == null){
		formBean = com.landray.kmss.web.taglib.TagUtils.getInstance().lookup(pageContext,
				formBeanName, null);
		pageContext.setAttribute("_formBean", formBean);
	}
%>
<c:set var="attForms" value="${_formBean.attachmentForms[param.fdKey]}" />
<c:set var="attachmentId" value=""/>
<c:set var="fdFileName" value="${_fileName}"/>
<c:set var="sysAttMains" value="${attForms.attachments}" />
<%
	//以下代码用于附件不通过form读取的方式
	List sysAttMains = (List)pageContext.getAttribute("sysAttMains");
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
<c:forEach items="${sysAttMains}" var="sysAttMain"	varStatus="vstatus">
	<c:set var="attachmentId" value="${sysAttMain.fdId}"/>
	<c:if test="${empty _fileName}">
		<c:set var="fdFileName" value="${sysAttMain.fdFileName}"/>
	</c:if>
	<c:if test="${! empty _fileName}">
		<%
			// 上面的逻辑中，附件的名称被设置为："文档标题" + ".doc"，但如果上传的附件不是doc文件，那么在预览时就会出现问题
			// 这里在加载附件时，判断如果上传的文件非doc格式，需要重新设置正确的格式
			String curFileName = ((com.landray.kmss.sys.attachment.model.SysAttMain) pageContext.getAttribute("sysAttMain")).getFdFileName();
			if (!curFileName.endsWith(".doc")) {
				String fdFileName = (String) pageContext.getAttribute("_fileName");
				fdFileName = fdFileName.substring(0, fdFileName.lastIndexOf(".")) + curFileName.substring(curFileName.lastIndexOf("."));
				pageContext.setAttribute("fdFileName", fdFileName);
			}
		%>
	</c:if>
	<c:set var="fdAttMainId" value="${sysAttMain.fdId}" scope="request"/>
</c:forEach>
<%@ page import="com.landray.kmss.sys.attachment.util.*" %>
<c:choose>
	<c:when test="${empty param.isShowImg or param.isShowImg}">
		<%
			//取fdAttMainId的值判断附件是否已经转换
			if(JgWebOffice.isExistViewPath(request) && !"true".equals(isUseWpsOnlineView)  && !"true".equals(isUseWpsCenterOnlineView)){
		%>
		<%
			boolean isExpand = "true".equals(request.getParameter("isExpand"));
			if(isExpand){
		%>
		<c:choose>
			<c:when test="${_isWpsCloudEnable or  param.showAllPage eq 'false'}">
				<c:set var="iframeH" value="556px"></c:set>
			</c:when>
			<c:when test="${_isWpsCenterEnable or  param.showAllPage eq 'false'}">
				<c:set var="iframeH" value="556px"></c:set>
			</c:when>
			<c:when test="${_isWpsPreviewEnable or  param.showAllPage eq 'false'}">
				<c:set var="iframeH" value="556px"></c:set>
			</c:when>
			<c:when test="${_isDianJuEnable or  param.showAllPage eq 'false'}">
				<c:set var="iframeH" value="556px"></c:set>
			</c:when>
			<c:when test="${_isFoxitEnable or  param.showAllPage eq 'false'}">
				<c:set var="iframeH" value="556px"></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="iframeH" value="100%"></c:set>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${empty param.showAllPage or param.showAllPage}">
				<iframe id="IFrame_Content" width="100%" height="${iframeH}" src='<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${attachmentId}&viewer=htmlviewer&showAllPage=true&dj_ifr=true&isAtt=${isAtt}&showChangeView=${showChangeView}"/>'
						frameborder="0" scrolling="no">
				</iframe>
			</c:when>
			<c:otherwise>
				<iframe id="IFrame_Content" width="100%" height="${iframeH}" src='<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${attachmentId}&viewer=htmlviewer&toolPosition=top&newOpen=true&inner=yes&dj_ifr=true&isAtt=${isAtt}&showChangeView=${showChangeView}"/>'
						frameborder="0" scrolling="auto">
				</iframe>
			</c:otherwise>
		</c:choose>
		<%}else{ %>
		<c:choose>
			<c:when test="${empty param.showAllPage or param.showAllPage}">
				<ui:event event="show">
					document.getElementById('IFrame_Content').src = ("<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${attachmentId}&viewer=htmlviewer&showAllPage=true&dj_ifr=true&isAtt=${isAtt}&showChangeView=${showChangeView}"/>");
				</ui:event>
				<iframe id="IFrame_Content" width="100%" height="100%"
						frameborder="0" scrolling="no">
				</iframe>
			</c:when>
			<c:otherwise>
				<ui:event event="show">
					document.getElementById('IFrame_Content').src = ("<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${attachmentId}&viewer=htmlviewer&toolPosition=top&newOpen=true&inner=yes&dj_ifr=true&isAtt=${isAtt}&showChangeView=${showChangeView}"/>");
				</ui:event>
				<iframe id="IFrame_Content" width="100%" height="600px"
						frameborder="0" scrolling="auto">
				</iframe>
			</c:otherwise>
		</c:choose>
		<%} %>
		<%}else{%>
		<%if("2".equals((String)pageContext.getAttribute("readOLConfig"))){ %>
			<%
				if("true".equals(request.getParameter("isExpand"))){
			%>
				<c:choose>
					<c:when test="${_isWpsCloudEnable or  param.showAllPage eq 'false'}">
						<c:set var="iframeH" value="556px"></c:set>
					</c:when>
					<c:when test="${_isWpsCenterEnable or  param.showAllPage eq 'false'}">
						<c:set var="iframeH" value="556px"></c:set>
					</c:when>
					<c:when test="${_isWpsPreviewEnable or  param.showAllPage eq 'false'}">
						<c:set var="iframeH" value="556px"></c:set>
					</c:when>
					<c:when test="${_isDianJuEnable or  param.showAllPage eq 'false'}">
						<c:set var="iframeH" value="556px"></c:set>
					</c:when>
					<c:when test="${_isFoxitEnable or  param.showAllPage eq 'false'}">
						<c:set var="iframeH" value="556px"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="iframeH" value="100%"></c:set>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${empty param.showAllPage or param.showAllPage}">
						<iframe id="IFrame_Content" width="100%" height="${iframeH}" src='<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${attachmentId}&dj_ifr=true&isAtt=${isAtt}&showChangeView=${showChangeView}"/>'
								frameborder="0" scrolling="no">
						</iframe>
					</c:when>
					<c:otherwise>
						<iframe id="IFrame_Content" width="100%" height="${iframeH}" src='<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${attachmentId}&dj_ifr=true&isAtt=${isAtt}&showChangeView=${showChangeView}"/>'
								frameborder="0" scrolling="auto">
						</iframe>
					</c:otherwise>
				</c:choose>
			<%}else{%>
				<c:choose>
					<c:when test="${empty param.showAllPage or param.showAllPage}">
						<ui:event event="show">
							document.getElementById('IFrame_Content').src = ("<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${attachmentId}&dj_ifr=true&isAtt=${isAtt}&showChangeView=${showChangeView}"/>");
						</ui:event>
						<iframe id="IFrame_Content" width="100%" height="100%"
								frameborder="0" scrolling="no">
						</iframe>
					</c:when>
					<c:otherwise>
						<ui:event event="show">
							document.getElementById('IFrame_Content').src = ("<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${attachmentId}&dj_ifr=true&isAtt=${isAtt}&showChangeView=${showChangeView}"/>");
						</ui:event>
						<iframe id="IFrame_Content" width="100%" height="600px"
								frameborder="0" scrolling="auto">
						</iframe>
					</c:otherwise>
				</c:choose>
			<%}%>
		<%}else if("3".equals((String)pageContext.getAttribute("readOLConfig"))){ %>
<%--			<c:import url="/sys/attachment/sys_att_main/wps/cloud/sysAttMain_viewinclude.jsp" charEncoding="UTF-8"></c:import>--%>
		<%@ include file="/sys/attachment/sys_att_main/wps/cloud/sysAttMain_viewinclude.jsp"%>
		<%}else if("5".equals((String)pageContext.getAttribute("readOLConfig"))){%>
<%--			<c:import url="/sys/attachment/sys_att_main/wps/center/sysAttMain_viewinclude.jsp" charEncoding="UTF-8"></c:import>--%>
		<%@ include file="/sys/attachment/sys_att_main/wps/center/sysAttMain_viewinclude.jsp"%>
		<%}else if("4".equals((String)pageContext.getAttribute("readOLConfig"))){%>
		<%@ include file="/sys/attachment/sys_att_main/dianju/sysAttMain_view_preview.jsp"%>
		<%}else if("6".equals((String)pageContext.getAttribute("readOLConfig"))){%>
<%--			<c:import url="/sys/attachment/sys_att_main/foxit/sysAttMain_view_preview.jsp" charEncoding="UTF-8"></c:import>--%>
		<%@ include file="/sys/attachment/sys_att_main/foxit/sysAttMain_view_preview.jsp"%>
		<%}else{%>
<%--			<c:import url="sysAttMain_viewinclude.jsp" charEncoding="UTF-8"></c:import>--%>
		<%@ include file="sysAttMain_viewinclude.jsp"%>
		<%}%>
		<%} %>
	</c:when>
	<c:otherwise>
		<%if("2".equals((String)pageContext.getAttribute("readOLConfig"))){ %>
			<%if("true".equals(request.getParameter("isExpand"))){%>
				<c:set var="iframeH" value="100%"></c:set>
				<c:choose>
					<c:when test="${empty param.showAllPage or param.showAllPage}">
						<iframe id="IFrame_Content" width="100%" height="${iframeH}" src='<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${attachmentId}&dj_ifr=true&isAtt=${isAtt}&showChangeView=${showChangeView}"/>'
								frameborder="0" scrolling="no">
						</iframe>
					</c:when>
					<c:otherwise>
						<iframe id="IFrame_Content" width="100%" height="${iframeH}" src='<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${attachmentId}&dj_ifr=true&isAtt=${isAtt}&showChangeView=${showChangeView}"/>'
								frameborder="0" scrolling="auto">
						</iframe>
					</c:otherwise>
				</c:choose>
			<%}else{%>
				<c:choose>
					<c:when test="${empty param.showAllPage or param.showAllPage}">
						<ui:event event="show">
							document.getElementById('IFrame_Content').src = ("<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${attachmentId}&dj_ifr=true&isAtt=${isAtt}&showChangeView=${showChangeView}"/>");
						</ui:event>
						<iframe id="IFrame_Content" width="100%" height="100%"
								frameborder="0" scrolling="no">
						</iframe>
					</c:when>
					<c:otherwise>
						<ui:event event="show">
							document.getElementById('IFrame_Content').src = ("<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=view&fdId=${attachmentId}&dj_ifr=true&isAtt=${isAtt}&showChangeView=${showChangeView}"/>");
						</ui:event>
						<iframe id="IFrame_Content" width="100%" height="600px"
								frameborder="0" scrolling="auto">
						</iframe>
					</c:otherwise>
				</c:choose>
			<%}%>
		<%}else if("3".equals((String)pageContext.getAttribute("readOLConfig"))){ %>
<%--			<c:import url="/sys/attachment/sys_att_main/wps/cloud/sysAttMain_viewinclude.jsp" charEncoding="UTF-8"></c:import>--%>
		<%@ include file="/sys/attachment/sys_att_main/wps/cloud/sysAttMain_viewinclude.jsp"%>
		<%}else if("5".equals((String)pageContext.getAttribute("readOLConfig"))){%>
<%--			<c:import url="/sys/attachment/sys_att_main/wps/center/sysAttMain_viewinclude.jsp" charEncoding="UTF-8"></c:import>--%>
		<%@ include file="/sys/attachment/sys_att_main/wps/center/sysAttMain_viewinclude.jsp"%>
		<%}else if("4".equals((String)pageContext.getAttribute("readOLConfig"))){%>
		<%@ include file="/sys/attachment/sys_att_main/dianju/sysAttMain_view_preview.jsp"%>
		<%}else if("6".equals((String)pageContext.getAttribute("readOLConfig"))){%>
<%--			<c:import url="/sys/attachment/sys_att_main/foxit/sysAttMain_view_preview.jsp" charEncoding="UTF-8"></c:import>--%>
		<%@ include file="/sys/attachment/sys_att_main/foxit/sysAttMain_view_preview.jsp"%>
		<%}else{%>
<%--			<c:import url="sysAttMain_viewinclude.jsp" charEncoding="UTF-8"></c:import>--%>
		<%@ include file="sysAttMain_viewinclude.jsp"%>
		<%}%>
	</c:otherwise>
</c:choose>