<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:data>
	<list:data-columns var="dbEchartsTable" list="${queryPage.list }">
		<list:data-column property="fdId" />
		<list:data-column  col="docSubject" title="${ lfn:message('dbcenter-echarts:dbEchartsTable.docSubject') }" style="text-align:center;min-width:180px" escape="false">
			<span class="com_subject"><c:out value="${dbEchartsTable.docSubject}" /></span>
		</list:data-column>	
		<list:data-column  headerClass="width120" col="createMode" title="${ lfn:message('dbcenter-echarts:dbEchartsTable.createMode') }">
		   <c:choose>
			     <c:when test="${dbEchartsTable.fdType eq '11'}">
			        ${ lfn:message('dbcenter-echarts:dbEchartsTable.programMode') }
			     </c:when>
			     <c:when test="${dbEchartsTable.fdType eq '01'}">
			        ${ lfn:message('dbcenter-echarts:dbEchartsTable.configMode') }
			     </c:when>
			     <c:otherwise></c:otherwise>
		  </c:choose>
		</list:data-column>		
		<list:data-column  headerClass="width120" property="docCreateTime" title="${ lfn:message('dbcenter-echarts:dbEchartsTable.docCreateTime') }">
		</list:data-column>
		<list:data-column  headerClass="width100" property="docCreator.fdName" title="${ lfn:message('dbcenter-echarts:dbEchartsTable.docCreator') }">
		</list:data-column>
		<list:data-column headerClass="width120" property="dbEchartsTemplate.fdName" title="${ lfn:message('dbcenter-echarts:dbEchartsTemplate.fdName') }">
		</list:data-column>
		<list:data-column headerClass="width120" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<kmss:auth requestURL="/dbcenter/echarts/db_echarts_table/dbEchartsTable.do?method=edit&fdId=${dbEchartsTable.fdId}" requestMethod="GET">
						<a class="btn_txt" href="javascript:edit('${dbEchartsTable.fdId}')">${lfn:message('button.edit')}</a>
					</kmss:auth>
					<kmss:auth requestURL="/dbcenter/echarts/db_echarts_table/dbEchartsTable.do?method=delete&fdId=${dbEchartsTable.fdId}">
					    <a class="btn_txt" href="javascript:deleteDoc('${dbEchartsTable.fdId}')">${lfn:message('button.delete')}</a>
					</kmss:auth>					
				</div>
			</div>
		</list:data-column>
	</list:data-columns>
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>
