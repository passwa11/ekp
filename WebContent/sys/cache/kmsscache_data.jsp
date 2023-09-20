<%@ page language="java" contentType="text/json; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:data>
	<list:data-columns var="cache" list="${queryPage.list}" varIndex="status" custom="false">
		<list:data-column col="index">
			${status+1}
		</list:data-column>
		<list:data-column col="name" style="width: 20%" title="${lfn:message('sys-cache:sysCache.data.name')}">
			${cache.name}
		</list:data-column>
		<list:data-column col="keys" style="width: 70%" title="${lfn:message('sys-cache:sysCache.data.key')}" escape="false">
			<textarea rows="5"  style="width:98%; min-height:50px" readonly="readonly" >${cache.keys}</textarea>
		</list:data-column>
		<list:data-column col="oper" style="width: 10%" title="${lfn:message('sys-cache:sysCache.data.operation')}" escape="false">
			<span onclick="cleanCache('${cache.name}','${scope}','true');" style="cursor: pointer; text-decoration: underline;">${lfn:message('sys-cache:sysCache.clean')}</span>
			</br></br>
			<span onclick="dialogRemoveKeys('${cache.name}','${cache.keys}','${scope}');" style="cursor: pointer; text-decoration: underline;">${lfn:message('sys-cache:sysCache.removeKeys')}</span>
		</list:data-column>
	</list:data-columns>
	<%-- 分页信息生成 --%>
	<list:data-paging page="${queryPage}" />
</list:data>
