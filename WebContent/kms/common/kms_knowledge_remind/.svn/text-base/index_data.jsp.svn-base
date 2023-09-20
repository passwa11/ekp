<%@ page language="java" contentType="text/json; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="kmsKnowledgeRemindInfo" list="${queryPage.list}" varIndex="status">
		<list:data-column property="fdId" >
		</list:data-column>
		<list:data-column col="index">
		  ${status+1}
		</list:data-column>
		
		<list:data-column col="moduleKey"  escape="false"   title="${ lfn:message('kms-common:kmsKnowledgeRemindInfo.moduleKey') }" >
			<a class="btn_txt" href="javascript:openModuleInfo('${kmsKnowledgeRemindInfo.moduleKey}','${kmsKnowledgeRemindInfo.noteCategory}')" style="color:#4285f4" 
			    alt="<bean:message bundle='kms-common' key='kmsKnowledgeRemind.detail'/>"
			    title="<bean:message bundle='kms-common' key='kmsKnowledgeRemind.detail'/>">
				<c:out value="${kmsKnowledgeRemindInfo.moduleName}"></c:out> 
			</a>
		</list:data-column>
		<list:data-column col="docSubject"   escape="false"  title="${ lfn:message('kms-common:kmsKnowledgeRemindInfo.docSubject') }">
		    <a class="btn_txt" href="javascript:openDocInfo('${kmsKnowledgeRemindInfo.docId}','${kmsKnowledgeRemindInfo.docType}')" style="color:#4285f4">
			     <c:out value="${kmsKnowledgeRemindInfo.docSubject}"></c:out> 
			</a>
		</list:data-column>
		<list:data-column col="docCreateTime"    title="${ lfn:message('kms-common:kmsKnowledgeRemindInfo.docCreateTime') }">
			<kmss:showDate value="${kmsKnowledgeRemindInfo.docCreateTime }" ></kmss:showDate>
		</list:data-column>
		<list:data-column col="docExpireTime"   title="${ lfn:message('kms-common:kmsKnowledgeRemindInfo.docExpireTime') }">
			<kmss:showDate value="${kmsKnowledgeRemindInfo.docExpireTime }" ></kmss:showDate>
		</list:data-column>
		<list:data-column col="docDescription"   title="${ lfn:message('kms-common:kmsKnowledgeRemindInfo.docDescription') }">
			<c:out value="${kmsKnowledgeRemindInfo.docDescription}"></c:out>
		</list:data-column>
		<list:data-column col="docRemindTime"   title="${ lfn:message('kms-common:kmsKnowledgeRemindInfo.docRemindTime') }">
			<kmss:showDate value="${kmsKnowledgeRemindInfo.docRemindTime }" ></kmss:showDate>
		</list:data-column>
	</list:data-columns>
	
	<list:data-paging page="${queryPage}" />
</list:data>
