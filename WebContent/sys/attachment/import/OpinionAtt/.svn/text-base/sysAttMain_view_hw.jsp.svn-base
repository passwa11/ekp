<%@page import="com.landray.kmss.common.actions.RequestContext"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@page import="com.landray.kmss.sys.mobile.util.MobileUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="com.landray.kmss.sys.attachment.util.SysAttPicUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="/WEB-INF/kmss-bean.tld" prefix="bean"%>
<%@page import="java.util.List,
				java.util.ArrayList,
				com.landray.kmss.util.StringUtil,
				com.landray.kmss.util.ResourceUtil,
				com.landray.kmss.util.SpringBeanUtil,
				com.landray.kmss.util.UserUtil"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@page import="com.landray.kmss.sys.attachment.dao.ISysAttMainCoreInnerDao"%>
<c:set var="_fdKey" value="${param.fdKey}"/>
<c:set var="_fdAttType" value="${param.fdAttType}"/>
<c:set var="_fdItemMixin" value="${param.fdItemMixin}"/>
<c:set var="_fdExtendClass" value="${param.fdExtendClass}"/>

<%
	String formBeanName = request.getParameter("formBeanName");
	String attKey = request.getParameter("fdKey");
	Object formBean = null;
	if(formBeanName != null && formBeanName.trim().length()!= 0){
		formBean = pageContext.getAttribute(formBeanName);
		if(formBean == null)
			formBean = request.getAttribute(formBeanName);
		if(formBean == null)
			formBean = session.getAttribute(formBeanName);
	}
	//pageContext.setAttribute("_downLoadNoRight",new PdaRowsPerPageConfig().getFdAttDownload());
	pageContext.setAttribute("_formBean", formBean);
%>
<c:set var="attForms" value="${_formBean.attachmentForms[param.fdKey]}" />
<c:set var="sysAttMains" value="${attForms.attachments}" />
<%
	//以下代码用于附件不通过form读取的方式
	List sysAttMains = (List)pageContext.getAttribute("sysAttMains");
	if(sysAttMains==null || sysAttMains.isEmpty()){
		try{
			String _modelName = request.getParameter("fdModelName");
			String _modelId = request.getParameter("fdModelId");
			if(StringUtil.isNotNull(_modelName) 
					&& StringUtil.isNotNull(_modelId)){
				String cacheKey = _modelName + "_" + _modelId;
				List cacheAtts = (List)request.getAttribute(cacheKey);
				if(cacheAtts!=null && !cacheAtts.isEmpty()){
					sysAttMains = cacheAtts;
				}else{
					String caheFlag = (String)request.getAttribute(cacheKey+"_flag");
					if(!"1".equals(caheFlag)){
						ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService)SpringBeanUtil.getBean("sysAttMainService");
						sysAttMains = ((ISysAttMainCoreInnerDao) sysAttMainService
								.getBaseDao()).findAttListByModel(_modelName,_modelId);
						request.setAttribute(cacheKey,sysAttMains);
						request.setAttribute(cacheKey+"_flag","1");
					}else{
						sysAttMains = new ArrayList();
					}
				}
				pageContext.setAttribute("sysAttMains",sysAttMains);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
%>


<c:if test="${sysAttMains!=null && fn:length(sysAttMains)>0}">
<tr>	
	<td>
	<link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/sys/lbpmservice/mobile/audit_note_ext/log/log_style.css?s_cache=${MUI_Cache}"></link>
	<div class="muiAuditLog ${_fdExtendClass}">
		<c:if test="${_fdAttType=='pic' }">
			<div class="auditHandlerImgBox">
				<c:forEach var="sysAttMain" items="${sysAttMains}" varStatus="vsStatus">
					<%
					
						SysAttMain sysAttMain = (SysAttMain)pageContext.getAttribute("sysAttMain");
						String downLoadUrl = "/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=" + sysAttMain.getFdId();
						boolean fromKKApp = MobileUtil.isFromKKApp(new RequestContext(request));
						if(fromKKApp){
							downLoadUrl = ResourceUtil.getKmssConfigString("kmss.urlPrefix") + downLoadUrl;
						}else{
							downLoadUrl = request.getContextPath() + downLoadUrl;
						}
						request.setAttribute("downLoadUrl", downLoadUrl);
					%>
					<c:if test="${sysAttMain.fdAttType=='pic' and sysAttMain.fdKey==param.fdKey}">
					 	<img class="muiAuditImg" border="0" width="100" src='${downLoadUrl}'/>
					</c:if>
				</c:forEach>
			</div>
			
			<%@ include file="/sys/lbpmservice/mobile/audit_note_ext/log/sysLbpmProcess_log_pc.jsp"%>
			
		</c:if>
	</div>
	</td>
</tr>	
</c:if>
