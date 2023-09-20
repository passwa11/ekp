<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<c:set var="orgForm" value="${requestScope[param.formName]}"/>
<c:forEach items="${props}" var="prop" varStatus="status">
	<c:if test="${prop.fdStatus == 'true'}">
		<tr>
			<td>
				<xform:text property="${prop.fdFieldName}" 
							subject="${prop.fdName}"
							value="${orgForm.dynamicMap[prop.fdFieldName]}" 
							mobile="true" align="right"></xform:text>
			</td>
		</tr>
	</c:if>
</c:forEach>