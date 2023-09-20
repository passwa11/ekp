<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<%@ page import="com.landray.kmss.sys.lbpmext.authorize.model.LbpmAuthorize,
                 com.landray.kmss.sys.organization.model.SysOrgElement,
                 java.util.*,com.landray.kmss.util.*"%>
<list:data>
	<list:data-columns var="lbpmAuthorize" list="${queryPage.list}" varIndex="status" mobile="true">
		<list:data-column property="fdId">
		</list:data-column >
		<%--授权人--%>
		<list:data-column col="authorizer" title="${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizer') }" escape="false">
			  <c:out value="${lbpmAuthorize.fdAuthorizer.fdName}"/>
		</list:data-column>
		<%--授权人头像--%>
		<list:data-column col="fdAuthorizerIcon" escape="false">
			 <person:headimageUrl personId="${lbpmAuthorize.fdAuthorizer.fdId}" size="m" />
		</list:data-column>
		<%--被授权人--%>
		<list:data-column col="authorizedPerson" title="${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizer') }" escape="false">
			 <c:choose>
				<c:when test="${lbpmAuthorize.fdAuthorizeType == 1}">
					<kmss:joinListProperty properties="fdName" value="${lbpmAuthorize.fdAuthorizedReaders}" />
				</c:when>
				<c:otherwise>
					<c:out value="${lbpmAuthorize.fdAuthorizedPerson.fdName}" />
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<%--被授权人--%>
		<list:data-column col="fdAuthorizedPersonIcon" escape="false">
			 <person:headimageUrl personId="${lbpmAuthorize.fdAuthorizedPerson.fdId}" size="m" />
		</list:data-column>
		<%-- 录入时间--%>
	 	<list:data-column col="fdCreateTime" title="${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdCreateTime') }">
	        <kmss:showDate value="${lbpmAuthorize.fdCreateTime}" type="datetime"></kmss:showDate>
      	</list:data-column>		
		<%-- 开始时间--%>
	 	<list:data-column col="fdStartTime" title="${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdStartTime') }">
	        <kmss:showDate value="${lbpmAuthorize.fdStartTime}" type="datetime"></kmss:showDate>
      	</list:data-column>
      	<%-- 结束时间--%>
	 	<list:data-column col="fdEndTime" title="${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdEndTime') }">
	        <kmss:showDate value="${lbpmAuthorize.fdEndTime}" type="datetime"></kmss:showDate>
      	</list:data-column>
      	<%-- 授权类型值--%>	
		<list:data-column col="fdAuthorizeTypeV" title="${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizeType') }" escape="false">
		       <c:out value="${lbpmAuthorize.fdAuthorizeType}"/>
		</list:data-column>
      	<%-- 授权类型--%>	
		<list:data-column col="fdAuthorizeType" title="${ lfn:message('sys-lbpmext-authorize:lbpmAuthorize.fdAuthorizeType') }" escape="false">
		       <sunbor:enumsShow  enumsType="lbpmAuthorize_authorizeType" value="${lbpmAuthorize.fdAuthorizeType}"/>
		</list:data-column>
		<%--链接--%>
		<list:data-column col="href" escape="false">
		     /sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=view&fdId=${lbpmAuthorize.fdId}
		</list:data-column>
		<list:data-column col="overDay" escape="false">
			<%
				if(pageContext.getAttribute("lbpmAuthorize")!=null){
					LbpmAuthorize lbpmAuthorize=(LbpmAuthorize)pageContext.getAttribute("lbpmAuthorize");
					String a= "";
					if(lbpmAuthorize.getFdAuthorizeType()==1){
						a=ResourceUtil.getString("lbpmAuthorize.fdEndTime.infinitive","sys-lbpmext-authorize");
					}else{
					long d2 = lbpmAuthorize.getFdEndTime().getTime();
					long d1 = lbpmAuthorize.getFdStartTime().getTime();
					long l = d2-d1;
					long day=l/(24*60*60*1000);
					long hour=(l/(60*60*1000)-day*24);
					long min=((l/(60*1000))-day*24*60-hour*60);
					if(day>0){
						a+=day+ResourceUtil.getString("date.interval.day");
					}else{
						if(hour>0){
							a+=hour+ResourceUtil.getString("date.interval.hour");
						}
						if(min>0){
							a+=min+ResourceUtil.getString("date.interval.minute");
						}
					}
				 }
				  out.print(a);
			  }
			%>
		</list:data-column>
		<list:data-column col="status" escape="false">
			<%
				if(pageContext.getAttribute("lbpmAuthorize")!=null){
					LbpmAuthorize lbpmAuthorize=(LbpmAuthorize)pageContext.getAttribute("lbpmAuthorize");
					Date nowT = new Date();
					if(nowT.getTime()-lbpmAuthorize.getFdStartTime().getTime()>0&&lbpmAuthorize.getFdEndTime().getTime()-nowT.getTime()>0){
						out.print("true");
					}
				}
			%>
		</list:data-column>
	</list:data-columns>
	<list:data-paging page="${queryPage}">
	</list:data-paging>
</list:data>