<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDictModel"%>
<%@page import="com.landray.kmss.sys.config.dict.SysDataDict"%>
<%@page import="com.landray.kmss.common.service.IBaseService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="com.landray.kmss.common.model.IBaseModel"%>
<%@page import="com.landray.kmss.sys.relation.model.SysRelationStaticNew"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.constant.SysDocConstant"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ taglib uri="/WEB-INF/kmss.tld" prefix="kmss"%>

<%
	String discardStatus = ResourceUtil.getString("status.discard");
	String draftStatus = ResourceUtil.getString("status.draft");
	String examineStatus = ResourceUtil.getString("status.examine");
	String refuseStatus = ResourceUtil.getString("status.refuse");
	String publishStatus = ResourceUtil.getString("status.publish");
	String expireStatus = ResourceUtil.getString("status.expire");
	//规范制度文档失效
	String kmInstitutionExpireStatus = ResourceUtil.getString("kmInstitution.status.expire","sys-relation");
	Map<String,String> statusMap = new HashMap<String,String>();
	statusMap.put(SysDocConstant.DOC_STATUS_DISCARD,discardStatus);
	statusMap.put(SysDocConstant.DOC_STATUS_DRAFT,draftStatus);
	statusMap.put(SysDocConstant.DOC_STATUS_EXAMINE,examineStatus);
	statusMap.put(SysDocConstant.DOC_STATUS_REFUSE,refuseStatus);
	statusMap.put(SysDocConstant.DOC_STATUS_PUBLISH,publishStatus);
	statusMap.put(SysDocConstant.DOC_STATUS_EXPIRE,expireStatus);
	//规范制度文档失效
	statusMap.put("50",kmInstitutionExpireStatus);
%>

<list:data>
	<list:data-columns var="item" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		
		<list:data-column col="fdSourceDocSubject" title="${lfn:message('sys-relation:relationOverView.source.doc2')}" escape="false">
			<%
				SysRelationStaticNew item = (SysRelationStaticNew)pageContext.getAttribute("item");
				if(item != null){
					String fdSourceId = item.getFdSourceId();
					String fdSourceModelName = item.getFdSourceModelName();
					String fdSourceDocSubject = item.getFdSourceDocSubject();
					
					String url = "";
					String showDocSubject = "";
					if(StringUtil.isNotNull(fdSourceId) && StringUtil.isNotNull(fdSourceModelName)){
						SysDataDict sysDataDict = SysDataDict.getInstance();
						SysDictModel docModel1 = sysDataDict.getModel(fdSourceModelName);
						if(docModel1==null){
							showDocSubject = "模块已删除";
						}else{
							String sourceUrl = sysDataDict.getModel(fdSourceModelName).getUrl();
							url = request.getContextPath() + 
											sourceUrl.substring(0,sourceUrl.indexOf("?")+1) + 
												StringUtil.setQueryParameter(sourceUrl.substring(
														sourceUrl.indexOf("?")+1),"fdId",fdSourceId);
							showDocSubject = "<a class='com_subject' href='" + 
														url + "' target='_blank'>" + 
														fdSourceDocSubject + "</a>";
						}
					}else{
						showDocSubject = fdSourceDocSubject;
					}
					out.print(showDocSubject);
				}
			%>
		</list:data-column>
		
		<list:data-column col="fdRelatedDocSubject" title="${lfn:message('sys-relation:relationOverView.related.doc')}" escape="false">
			<%
				SysRelationStaticNew item = (SysRelationStaticNew)pageContext.getAttribute("item");
				if(item != null){
					String fdRelatedId = item.getFdRelatedId();
					String fdRelatedModelName = item.getFdRelatedModelName();
					String docSubject = "";
					String fdRelatedUrl = item.getFdRelatedUrl();
					String docPublishing = ResourceUtil.getString("relationOverView.doc.publishing","sys-relation");
					String recycle = ResourceUtil.getString("relationOverView.doc.recycle","sys-relation");
					String hasDeleted = ResourceUtil.getString("relationOverView.doc.deleted","sys-relation");
					String showStatus = docPublishing;
					
					Boolean isDeleted = false;
					docSubject = item.getFdRelatedName();
					if(StringUtil.isNull(fdRelatedId) || StringUtil.isNull(fdRelatedModelName)){
						showStatus = "";
					}else{
						SysDictModel docModel = SysDataDict.getInstance().getModel(fdRelatedModelName);
						IBaseService docService = (IBaseService) SpringBeanUtil.getBean(docModel.getServiceBean());
						IBaseModel baseModel = (IBaseModel)docService.findByPrimaryKey(fdRelatedId,null, true);
						if(baseModel != null){
							if(PropertyUtils.isReadable(baseModel, "docStatus")){
								String docStatus = (String)PropertyUtils
														.getProperty(baseModel,"docStatus");
								showStatus = "["+statusMap.get(docStatus)+"]";
							}
						}else{
							fdRelatedUrl = "";
							showStatus = hasDeleted;
							isDeleted = true;
						}
					}
					String showDocSubject = "";
					if(isDeleted){
						showDocSubject = "<span>" + showStatus + 
												"<span class='com_subject'>" + 
													docSubject + "</span></span>";
					}else{
						if(StringUtil.isNotNull(fdRelatedUrl) 
								&& "/".equals(fdRelatedUrl.substring(0,1))){
							fdRelatedUrl = request.getContextPath() + fdRelatedUrl;
						}
						showDocSubject = "<a class='com_subject' href='" + fdRelatedUrl + 
											"' target='_blank'>" + 
												showStatus + docSubject + "</a>";
					}
					out.print(showDocSubject);
				}
			%>
		</list:data-column>
		
		<list:data-column col="fdRelatedType" title="${lfn:message('sys-relation:relationOverView.relationship')}" escape="false">
			<c:if test="${item.fdRelatedType == 0}">
				<bean:message bundle="sys-relation" key="sysRelationMain.helptips15" />
			</c:if>
			<c:if test="${item.fdRelatedType != 0}">
				<bean:message bundle="sys-relation" key="sysRelationMain.helptips15" />
			</c:if>			
		</list:data-column>
	</list:data-columns>

	<list:data-paging page="${queryPage }">
	</list:data-paging>
</list:data>