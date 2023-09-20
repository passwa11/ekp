<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.attachment.util.AttImageUtils"%>
<%@page import="com.landray.kmss.sys.attachment.util.JgWebOffice"%>
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
<style>
.tab_img{ width:100%; zoom:1;}
.tab_img:after{ display:block; clear:both; visibility:hidden; line-height:0px; content:"clear";}
</style>
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
	<p class="tab_img">
	<c:forEach items="${sysAttMains}" var="sysAttMain" varStatus="vsStatus">
		<c:if test="${sysAttMain.fdKey==param.fdKey}">
				<img style="float: right"  height="60" src='<c:url value="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${sysAttMain.fdId}"/>' ></img>
		</c:if>
	</c:forEach>
	</p>
	</td>
</tr>	
</c:if>

