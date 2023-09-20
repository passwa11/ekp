<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmSmissiveMain" list="${queryPage.list }">
		<list:data-column property="fdId">
		</list:data-column>
		<list:data-column col="docSubject"  title="${ lfn:message('km-smissive:kmSmissiveMain.docSubject') }" style="text-align:left;min-width:100px" escape="false">
		 <a class="com_subject textEllipsis" title="${kmSmissiveMain.docSubject}" data-href="${LUI_ContextPath}/km/smissive/km_smissive_main/kmSmissiveMain.do?method=view&fdId=${kmSmissiveMain.fdId}" 
		 target="_blank" onclick="Com_OpenNewWindow(this)">
		   <c:out value="${kmSmissiveMain.docSubject}"/>
		 </a>
		</list:data-column>
		<list:data-column  property="fdFileNo" headerClass="width100" styleClass="width100" title="${ lfn:message('km-smissive:kmSmissiveMain.fdFileNo') }">
		</list:data-column> 
		<list:data-column  col="docAuthor.fdName" headerClass="width60" styleClass="width60" title="${ lfn:message('km-smissive:kmSmissiveMain.docAuthorId') }" escape="false">
		   <ui:person personId="${kmSmissiveMain.docAuthor.fdId}" personName="${kmSmissiveMain.docAuthor.fdName}"></ui:person> 
		</list:data-column>
		<list:data-column  property="fdMainDept.fdName" headerClass="width90" styleClass="width90" title="${ lfn:message('km-smissive:kmSmissiveMain.fdMainDeptId') }">
		</list:data-column>
		<list:data-column  col="docPublishTime"  headerClass="width80" styleClass="width80" title="${ lfn:message('km-smissive:kmSmissiveMain.docPublishTime') }">
		    <kmss:showDate value="${kmSmissiveMain.docPublishTime}" type="date"/>
		</list:data-column>
		<list:data-column  col="docStatus" headerClass="width60" styleClass="width60" escape="false" title="${ lfn:message('km-smissive:kmSmissiveMain.docStatus') }">
			<c:choose>
	            <c:when test="${kmSmissiveMain.docStatus == '20'}">
	              <div style="color:#4cbe45">
	               ${ lfn:message('status.examine')}
	              </div>
	            </c:when>
	            <c:otherwise>
	                <sunbor:enumsShow value="${kmSmissiveMain.docStatus}" enumsType="common_status" />
	            </c:otherwise>
          </c:choose>
		</list:data-column>
	</list:data-columns>
</list:data>
