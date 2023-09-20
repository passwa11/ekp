<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<c:set var="orgForm" value="${requestScope[param.formName]}"/>
<c:forEach items="${props}" var="prop" varStatus="status">
	<c:choose>
		<c:when test="${status.index % 2 == 0}">
		<tr>
		</c:when>
		<c:when test="${status.index != 0 && status.index % 2 == 0}">
		</tr>
		</c:when>
	</c:choose>
		<td width=15% class="td_normal_title">
			${prop.fdName}
		</td>
		<c:choose>
			<c:when test="${status.index % 2 == 0 && status.index == fn:length(props) - 1}">
			<td width=85% colspan="3">
			</c:when>
			<c:otherwise>
			<td width=35%>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${prop.fdDisplayType == 'text'}">
				<c:set var="scaleLength" value=""/>
				<c:if test="${'java.lang.Double' eq prop.fdFieldType}">
				<c:set var="scaleLength" value="scaleLength(${prop.fdScale})"/>
				</c:if>
				<xform:text property="${prop.fdFieldName}" value="${orgForm.dynamicMap[prop.fdFieldName]}" validators="${'true' eq prop.fdRequired ? 'required' : ''} maxLength(${prop.fdFieldLength}) ${scaleLength} ${'java.lang.Integer' eq prop.fdFieldType ? 'digits' : ''} ${'java.lang.Double' eq prop.fdFieldType ? 'number' : ''}" subject="${prop.fdName}" style="width:80%"></xform:text>
			</c:when>
			<c:when test="${prop.fdDisplayType == 'textarea'}">
				<xform:textarea property="${prop.fdFieldName}" value="${orgForm.dynamicMap[prop.fdFieldName]}" validators="${'true' eq prop.fdRequired ? 'required' : ''} maxLength(${prop.fdFieldLength})" subject="${prop.fdName}" style="width:96%"></xform:textarea>
			</c:when>
			<c:when test="${prop.fdDisplayType == 'radio'}">
				<xform:radio property="${prop.fdFieldName}" value="${orgForm.dynamicMap[prop.fdFieldName]}" validators="${'true' eq prop.fdRequired ? 'required' : ''}" subject="${prop.fdName}" showStatus="edit">
					<c:forEach items="${prop.fdFieldEnums}" var="_enum">
					<xform:simpleDataSource value="${_enum.fdValue}">${_enum.fdName}</xform:simpleDataSource>
					</c:forEach>
				</xform:radio>
			</c:when>
			<c:when test="${prop.fdDisplayType == 'checkbox'}">
				<xform:checkbox property="${prop.fdFieldName}" value="${orgForm.dynamicMap[prop.fdFieldName]}" validators="${'true' eq prop.fdRequired ? 'required' : ''}" subject="${prop.fdName}" showStatus="edit">
					<c:forEach items="${prop.fdFieldEnums}" var="_enum">
					<xform:simpleDataSource value="${_enum.fdValue}">${_enum.fdName}</xform:simpleDataSource>
					</c:forEach>
				</xform:checkbox>
			</c:when>
			<c:when test="${prop.fdDisplayType == 'select'}">
				<xform:select property="${prop.fdFieldName}" value="${orgForm.dynamicMap[prop.fdFieldName]}" validators="${'true' eq prop.fdRequired ? 'required' : ''}" subject="${prop.fdName}" showStatus="edit">
					<c:forEach items="${prop.fdFieldEnums}" var="_enum">
					<xform:simpleDataSource value="${_enum.fdValue}">${_enum.fdName}</xform:simpleDataSource>
					</c:forEach>
				</xform:select>
			</c:when>
			<c:when test="${prop.fdFieldType == 'java.util.Date'}">
				<xform:datetime property="${prop.fdFieldName}" value="${orgForm.dynamicMap[prop.fdFieldName]}" validators="${'true' eq prop.fdRequired ? 'required' : ''}" subject="${prop.fdName}" showStatus="edit" dateTimeType="${prop.fdDisplayType}"></xform:datetime>
			</c:when>
		</c:choose>
		<c:if test="${'true' eq prop.fdRequired}"><span class="txtstrong">*</span></c:if>
		</td>
	<c:if test="${status.index == fn:length(props) - 1}">
	</tr>
	</c:if>
</c:forEach>
