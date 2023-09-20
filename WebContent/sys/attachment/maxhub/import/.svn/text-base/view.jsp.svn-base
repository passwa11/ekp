<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@ page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@ page import="com.landray.kmss.util.StringUtil"%>
<%@ page import="com.landray.kmss.sys.attachment.model.SysAttMain"%>
<%@ page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<%@ page import="com.landray.kmss.sys.attachment.dao.ISysAttMainCoreInnerDao"%>
<%@ page import="com.landray.kmss.sys.attachment.util.SysAttViewerUtil"%>
<%@ page import="com.landray.kmss.third.pda.model.PdaRowsPerPageConfig"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/mobile/mui.tld" prefix="mui"%>
<mui:cache-file name="maxhub-attachment.js" cacheType="md5"/>
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
	pageContext.setAttribute("_downLoadNoRight",new PdaRowsPerPageConfig().getFdAttDownload());
	if(sysAttMains == null || sysAttMains.isEmpty()){
		try{
			String _modelName = request.getParameter("fdModelName");
			String _modelId = request.getParameter("fdModelId");
			String _key = request.getParameter("fdKey");
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
						sysAttMains = ((ISysAttMainCoreInnerDao) sysAttMainService.getBaseDao()).findAttListByModel(_modelName,_modelId);
						request.setAttribute(cacheKey,sysAttMains);
						request.setAttribute(cacheKey+"_flag","1");
					}else{
						sysAttMains = new ArrayList();
					}
				}
				//附件个数
				int attCount  = 0;
				for(int i = 0;i < sysAttMains.size();i++){
					SysAttMain s = (SysAttMain)sysAttMains.get(i);
					if(_key.equals(s.getFdKey())){
						attCount++;
					}
				}
				pageContext.setAttribute("attCount",attCount);
				pageContext.setAttribute("sysAttMains",sysAttMains);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}else{
		//附件个数
		pageContext.setAttribute("attCount",sysAttMains.size());
	}
%>

<div class="mhuiAttachments mhuiAttachments${_fdViewType}">
	<div id="attachmentObject_${_fdKey}"  
		data-dojo-type="sys/attachment/maxhub/js/AttachmentList"
		data-dojo-props="fdKey:'${_fdKey}',fdModelName:'${_fdModelName}',fdModelId:'${_fdModelId}',viewType:'${_fdViewType}',editMode:'view'">
		<c:if test="${attCount > 0}">
			<c:forEach var="sysAttMain" items="${sysAttMains}" varStatus="vsStatus">
				<c:if test="${sysAttMain.fdKey==_fdKey}">
					<%
						SysAttMain sysAtt = (SysAttMain) pageContext.getAttribute("sysAttMain");
						String escapeFileName = StringEscapeUtils.escapeJavaScript(sysAtt.getFdFileName());
						pageContext.setAttribute("escapeFileName", escapeFileName);
					%>
					<c:set value="false" var="canDownload"></c:set>
					<c:set var="downLoadUrl" value="/sys/attachment/sys_att_main/sysAttMain.do?method=viewDownload&fdId=${sysAttMain.fdId}" />
					<%--下载权限  --%>
					<kmss:auth
						requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${sysAttMain.fdId}"
						requestMethod="GET">
						<c:set var="downLoadUrl"
							value="/sys/attachment/sys_att_main/sysAttMain.do?method=download&fdId=${sysAttMain.fdId}" />
						<c:set value="true" var="canDownload"></c:set>
					</kmss:auth>
					<c:set value="true" var="canRead"></c:set>
					<%--查看权限  --%>
					<kmss:auth
						requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=read&fdId=${sysAttMain.fdId}"
						requestMethod="GET">
						<c:set value="true" var="canRead"></c:set>
					</kmss:auth>
					<c:set  var="canEdit" value="false"></c:set>
					<%--编辑权限  --%>
					<kmss:auth	
						requestURL="/sys/attachment/sys_att_main/sysAttMain.do?method=edit&v=true&fdId=${sysAttMain.fdId}"
						requestMethod="GET">
						<c:set  var="canEdit" value="true"></c:set>
					</kmss:auth>
					<c:if test="${canDownload != true }">
						<c:if test="${_downLoadNoRight==true || _downLoadNoRight=='true'}">
							<c:set var="downLoadUrl" value="/third/pda/attdownload.jsp?fdId=${sysAttMain.fdId}&open=1" />
							<c:set value="true" var="canDownload"></c:set>
						</c:if>
					</c:if>
					<%
						SysAttMain sysAttMain = (SysAttMain) pageContext.getAttribute("sysAttMain");
					    Boolean hasViewer = Boolean.FALSE;
					    String viewPicHref = "/third/pda/attdownload.jsp?open=1";
						if (SysAttViewerUtil.isConverted(sysAttMain)
								&& sysAttMain.getFdContentType().indexOf("video") < 0
								|| sysAttMain.getFdContentType().indexOf("mp4") >= 0||
								sysAttMain.getFdContentType().indexOf("audio") >= 0) {
							hasViewer = Boolean.TRUE;
						}
						pageContext.setAttribute("viewPicHref", viewPicHref);
						pageContext.setAttribute("hasViewer", hasViewer);
					%>
					<div
						data-dojo-type="sys/attachment/maxhub/js/AttachmentViewListItem"
						data-dojo-props="name:'${escapeFileName}',
							size:'${sysAttMain.fdSize}',
							href:'${downLoadUrl}',
							thumb:'/sys/attachment/sys_att_main/sysAttMain.do?method=view&picthumb=small&fdId=${sysAttMain.fdId}',
							viewPicHref : '${viewPicHref }',
							fdId:'${sysAttMain.fdId}',
							type:'${sysAttMain.fdContentType}',
							hasViewer:${hasViewer},
							canEdit:${canEdit},
							canDownload:${canDownload},
							canRead:${canRead}">
					</div>
				</c:if>
			</c:forEach>
		</c:if>
	</div>		
</div>