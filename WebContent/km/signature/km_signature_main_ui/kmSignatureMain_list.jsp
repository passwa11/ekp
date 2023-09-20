<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.lang.String"%>
<%@page import="com.landray.kmss.km.signature.util.BlobUtil"%>
<%@page import="com.landray.kmss.km.signature.model.KmSignatureMain"%>
<%@page import="com.landray.kmss.sys.organization.model.SysOrgElement"%>
<%@page import="com.landray.kmss.km.signature.service.IKmSignatureMainService"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%
IKmSignatureMainService kmSignatureMainService= (IKmSignatureMainService)SpringBeanUtil.getBean("kmSignatureMainService");
%>
<list:data>
	<list:data-columns var="kmSignatureMain" list="${queryPage.list }" varIndex="status">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		<%-- 签章名称 --%>
		<list:data-column col="fdMarkName" escape="false" title="${ lfn:message('km-signature:signature.markname') }" style="text-align:left;min-width:150px">
			<c:out value="${kmSignatureMain.fdMarkName }"></c:out>
		</list:data-column>
		
		<%-- 签章保存时间 --%>
		<list:data-column headerClass="width120" property="fdMarkDate" escape="false" title="${ lfn:message('km-signature:signature.docCreateTime') }">
		</list:data-column>
		
		
		<%-- 签章类型 --%>
		<list:data-column headerClass="width60" col="fdDocType" escape="false" title="${ lfn:message('km-signature:signature.docType') }">
			<c:if test="${kmSignatureMain.fdDocType == 1}">
				<c:out value="${ lfn:message('km-signature:signature.fdDocType.handWrite') }"></c:out>
			</c:if>
			<c:if test="${kmSignatureMain.fdDocType == 2}">
				<c:out value="${ lfn:message('km-signature:signature.fdDocType.companySignature') }"></c:out>
			</c:if>
		</list:data-column>
		<%-- 签章名称 --%>
		<list:data-column col="docSubject" escape="false" title="${ lfn:message('km-signature:signature.markname') }" style="text-align:left;min-width:200px">
			<c:out value="${kmSignatureMain.fdMarkName}"></c:out>
		</list:data-column>
		
		<%-- 调用sys/ui/extend/grid-table-render.html中可识别字段实现图文展现（docCategory.fdName、docAuthor.fdName） --%>
		
		<%-- 用户名称
		<list:data-column headerClass="width100" col="docAuthor.fdName" escape="false" title="${ lfn:message('km-signature:signature.username') }">
			${kmSignatureMain.fdUserName}
		</list:data-column>
		 --%>
		<%-- 签章图片（图文展现） --%>
		<list:data-column col="fdImageUrl" title="imageLink">
			<c:if test="${loadImg == true}">
			
			<%  Object basedocObj = pageContext.getAttribute("kmSignatureMain");
			   if(basedocObj != null) { 
				   KmSignatureMain kmSignatureMain = (KmSignatureMain)basedocObj;
					String id = kmSignatureMainService.getCardPicIdsBySignatureId(kmSignatureMain.getFdId());
					if(StringUtil.isNotNull(id)){
						out.print("/sys/attachment/sys_att_main/sysAttMain.do?method=view&picthumb=small&fdId="+id );
					}else{
						
					}
				}
	        %>
			</c:if>
		</list:data-column>
		<c:if test="${fdDocType == 1}">
			<list:data-column headerClass="width80" col="fdIsDefault" escape="false" title="${ lfn:message('km-signature:signature.defaultSign') }">
				<c:if test="${kmSignatureMain.fdIsDefault}">
					<img src='<c:url value="/sys/profile/resource/images/profile_list_status_y.png"/>'>
				</c:if>
			</list:data-column>
			<list:data-column headerClass="width40" col="fdIsFreeSign" escape="false" title="${ lfn:message('km-signature:signature.fdIsFreeSign') }">
				<c:if test="${kmSignatureMain.fdIsFreeSign == false}">
					<c:out value="${ lfn:message('km-signature:signature.docInForce.no') }"></c:out>
				</c:if>
				<c:if test="${kmSignatureMain.fdIsFreeSign == true}">
					<c:out value="${ lfn:message('km-signature:signature.docInForce.yes') }"></c:out>
				</c:if>
			</list:data-column>
		</c:if>
		<%-- 签章类型（图文展现） --%>
		<list:data-column headerClass="width40" col="docCategory.fdName" escape="false" title="${ lfn:message('km-signature:signature.docType') }">
			<c:if test="${kmSignatureMain.fdDocType == 1}">
				<c:out value="${ lfn:message('km-signature:signature.fdDocType.handWrite') }"></c:out>
			</c:if>
			<c:if test="${kmSignatureMain.fdDocType == 2}">
				<c:out value="${ lfn:message('km-signature:signature.fdDocType.companySignature') }"></c:out>
			</c:if>
		</list:data-column>
		<%-- 授权用户（图文展现） --%>
		<list:data-column headerClass="width100" col="docAuthor.fdName" escape="false" title="${ lfn:message('km-signature:signature.users') }">
			<%
				Object basedocObj = pageContext.getAttribute("kmSignatureMain");
				String fdUsersNames = "";
				if(basedocObj != null) {
					KmSignatureMain kmSignatureMain = (KmSignatureMain) basedocObj;
					List<SysOrgElement> fdUsers = kmSignatureMain.getFdUsers();
					if(fdUsers != null){
						for(int i=0; i<fdUsers.size(); i++){
							if(i == 0){
								fdUsersNames += fdUsers.get(i).getFdName();
							}else{
								fdUsersNames += ";"+fdUsers.get(i).getFdName();
							}
						}
					}
				}
			%>
			<c:out value="<%=fdUsersNames%>"></c:out>
		</list:data-column>
		<%-- 创建者 --%>
		<list:data-column headerClass="width120" col="docCreator.fdName" title="${ lfn:message('km-signature:signature.docCreator') }" escape="false">
		  <ui:person personId="${kmSignatureMain.fdId}" personName="${kmSignatureMain.docCreator.fdName}"></ui:person>
		</list:data-column>
		<%-- 创建时间 --%>
		<list:data-column headerClass="width120" property="docCreateTime" escape="false" title="${ lfn:message('km-signature:signature.docCreateTime') }">
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }"
		pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }">
	</list:data-paging>
</list:data>