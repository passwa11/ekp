<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<c:set var="orgForm" value="${requestScope[param.formName]}"/>
<c:if test="${not empty props}">
	<div class=propertyGap>${HtmlParam.infoLable}</div>
	<table class="muiSimple" cellpadding="0" cellspacing="0">
		<c:forEach items="${props}" var="prop" varStatus="status">
			<c:if test="${prop.fdStatus == 'true'}">
				<tr>
					<td class="muiTitle">${prop.fdName}</td>
					<c:choose>
						<c:when test="${prop.fdDisplayType == 'text'}">
							<td>
								<c:set var="scaleLength" value=""/>
								<c:if test="${'java.lang.Double' eq prop.fdFieldType}">
								<c:set var="scaleLength" value="scaleLength(${prop.fdScale})"/>
								</c:if>
								<xform:text property="${prop.fdFieldName}" 
											value="${orgForm.dynamicMap[prop.fdFieldName]}" 
											mobile="true" align="right" required="${prop.fdRequired}"
											validators="maxLength(${prop.fdFieldLength}) ${scaleLength} ${'java.lang.Integer' eq prop.fdFieldType ? 'digits' : ''} ${'java.lang.Double' eq prop.fdFieldType ? 'number' : ''}"></xform:text>
							</td>
						</c:when>
						<c:when test="${prop.fdDisplayType == 'textarea'}">
							<td>
								<xform:textarea property="${prop.fdFieldName}" 
												value="${orgForm.dynamicMap[prop.fdFieldName]}" 
												mobile="true" align="right" required="${prop.fdRequired}"
												validators="maxLength(${prop.fdFieldLength})"></xform:textarea>
							</td>
						</c:when>
						<c:when test="${prop.fdDisplayType == 'radio'}">
							<td>
								<xform:radio property="${prop.fdFieldName}" 
											 value="${orgForm.dynamicMap[prop.fdFieldName]}"
											 alignment="H" mobile="true" 
											 required="${prop.fdRequired}"
											 mobileRenderType="normal"
											 validators="maxLength(${prop.fdFieldLength})">
									<c:forEach items="${prop.fdFieldEnums}" var="_enum">
										<xform:simpleDataSource value="${_enum.fdValue}">${_enum.fdName}</xform:simpleDataSource>
									</c:forEach>
								</xform:radio>
							</td>
						</c:when>
						<c:when test="${prop.fdDisplayType == 'checkbox'}">
							<td>
								<xform:checkbox property="${prop.fdFieldName}" 
												value="${orgForm.dynamicMap[prop.fdFieldName]}" 
												required="${prop.fdRequired}"
												mobile="true">
									<c:forEach items="${prop.fdFieldEnums}" var="_enum">
										<xform:simpleDataSource value="${_enum.fdValue}">${_enum.fdName}</xform:simpleDataSource>
									</c:forEach>
								</xform:checkbox>
							</td>
						</c:when>
						<c:when test="${prop.fdDisplayType == 'select'}">
							<td>
								<xform:select property="${prop.fdFieldName}" 
											  value="${orgForm.dynamicMap[prop.fdFieldName]}" 
											  required="${prop.fdRequired}"
											  subject="${prop.fdName}" mobile="true"
											  validators="maxLength(${prop.fdFieldLength})">
									<c:forEach items="${prop.fdFieldEnums}" var="_enum">
										<xform:simpleDataSource value="${_enum.fdValue}">${_enum.fdName}</xform:simpleDataSource>
									</c:forEach>
								</xform:select>
							</td>
						</c:when>
						<c:when test="${prop.fdFieldType == 'java.util.Date'}">
							<td>
								<xform:datetime property="${prop.fdFieldName}" 
												value="${orgForm.dynamicMap[prop.fdFieldName]}" 
												required="${prop.fdRequired}"
												dateTimeType="${prop.fdDisplayType}" mobile="true"></xform:datetime>
							</td>
						</c:when>
					</c:choose>
				</tr>
			</c:if>
		</c:forEach>
	</table>
</c:if>
