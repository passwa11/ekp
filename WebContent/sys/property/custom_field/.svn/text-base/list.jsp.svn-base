<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.sys.property.custom.DynamicAttributeField"%>
<%@ page import="com.landray.kmss.util.ResourceUtil" %>
<list:data>
	<!-- 表格数据 -->
	<list:data-columns var="field" list="${queryPage.list }">
		<list:data-column col="fdId">
			${field.fieldName}
		</list:data-column>
		<list:data-column headerClass="width30" property="order" title="${ lfn:message('sys-property:custom.field.order') }" />
		<list:data-column headerClass="width100" property="columnName" title="${ lfn:message('sys-property:custom.field.columnName') }" />
		<list:data-column headerClass="width100" col="fieldName" title="${ lfn:message('sys-property:custom.field.fieldName') }" >
			<c:out value="${fn:escapeXml(field.fieldName)}"></c:out>
		</list:data-column>
		<list:data-column headerClass="width80" col="fieldType" title="${ lfn:message('sys-property:custom.field.fieldType') }">
			<c:choose>
				<c:when test="${'java.lang.String' eq field.fieldType}">
					${ lfn:message('sys-property:custom.field.fieldType.string') }
				</c:when>
				<c:when test="${'java.lang.Integer' eq field.fieldType}">
					${ lfn:message('sys-property:custom.field.fieldType.integer') }
				</c:when>
				<c:when test="${'java.lang.Double' eq field.fieldType}">
					${ lfn:message('sys-property:custom.field.fieldType.double') }
				</c:when>
				<c:when test="${'java.util.Date' eq field.fieldType}">
					${ lfn:message('sys-property:custom.field.fieldType.date') }
				</c:when>
				<c:when test="${'com.landray.kmss.sys.organization.model.SysOrgElement' eq field.fieldType}">
					${ lfn:message('sys-property:custom.field.fieldType.sys.org') }
				</c:when>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width60" col="required" title="${ lfn:message('sys-property:custom.field.required') }" >
			<sunbor:enumsShow value="${field.required}" enumsType="common_yesno" />
		</list:data-column>
		<list:data-column headerClass="width80" col="fieldLength" title="${ lfn:message('sys-property:custom.field.fieldLength') }">
			<c:choose>
				<c:when test="${'java.util.Date' eq field.fieldType}">
					-
				</c:when>
				<c:otherwise>
					${field.fieldLength}
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width140" col="fieldTexts" title="${ lfn:message('sys-property:custom.field.fieldTexts') }" escape="false">
			${lfn:escapeHtml(field.fieldTextsForView)}
		</list:data-column>
		<list:data-column headerClass="width80" col="displayType" title="${ lfn:message('sys-property:custom.field.displayType') }">
			<c:choose>
				<c:when test="${'text' eq field.displayType}">
					${ lfn:message('sys-property:custom.field.displayType.text') }
				</c:when>
				<c:when test="${'textarea' eq field.displayType}">
					${ lfn:message('sys-property:custom.field.displayType.textarea') }
				</c:when>
				<c:when test="${'radio' eq field.displayType}">
					${ lfn:message('sys-property:custom.field.displayType.radio') }
				</c:when>
				<c:when test="${'checkbox' eq field.displayType}">
					${ lfn:message('sys-property:custom.field.displayType.checkbox') }
				</c:when>
				<c:when test="${'select' eq field.displayType}">
					${ lfn:message('sys-property:custom.field.displayType.select') }
				</c:when>
				<c:when test="${'datetime' eq field.displayType}">
					${ lfn:message('sys-property:custom.field.displayType.datetime') }
				</c:when>
				<c:when test="${'date' eq field.displayType}">
					${ lfn:message('sys-property:custom.field.displayType.date') }
				</c:when>
				<c:when test="${'time' eq field.displayType}">
					${ lfn:message('sys-property:custom.field.displayType.time') }
				</c:when>
			</c:choose>
			<%
				if(pageContext.getAttribute("field")!=null){
					Object obj = pageContext.getAttribute("field");
					if(obj instanceof DynamicAttributeField){
						DynamicAttributeField field = (DynamicAttributeField)obj;
						String fieldType = field.getFieldType();
						if("com.landray.kmss.sys.organization.model.SysOrgElement".equals(fieldType)){
							String displayType = field.getDisplayType();
							String[] displays = displayType.split(";");
							String orgType = "";
							for(int i = 0;i<displays.length;i++){
								if("ORG_TYPE_PERSON".equals(displays[i])){
									orgType = orgType + ResourceUtil.getString("sys-property:custom.field.displayType.person")+";";
								}else if("ORG_TYPE_ORGORDEPT".equals(displays[i])){
									orgType = orgType + ResourceUtil.getString("sys-property:custom.field.displayType.orgOrDept")+";";
								}else if("ORG_TYPE_POST".equals(displays[i])){
									orgType = orgType + ResourceUtil.getString("sys-property:custom.field.displayType.post")+";";
								}
							}
							out.print(orgType);
						}
					}
				}
			%>
		</list:data-column>
		<list:data-column headerClass="width80" col="status" title="${ lfn:message('sys-property:custom.field.status') }">
			<c:choose>
				<c:when test="${'true' eq field.status}">
					${ lfn:message('sys-property:custom.field.status.true') }
				</c:when>
				<c:otherwise>
					${ lfn:message('sys-property:custom.field.status.false') }
				</c:otherwise>
			</c:choose>
		</list:data-column>
		<list:data-column headerClass="width140" col="fieldEnums" title="${ lfn:message('sys-property:custom.field.fieldEnums') }" escape="false">
			${lfn:escapeHtml(field.fieldEnumsForView)}
		</list:data-column>
		
		<!-- 其它操作 -->
		<list:data-column headerClass="width80" col="operations" title="${ lfn:message('list.operation') }" escape="false">
			<!--操作按钮 开始-->
			<div class="conf_show_more_w">
				<div class="conf_btn_edit">
					<!-- 编辑 -->
					<a class="btn_txt" href="javascript:edit('${field.fieldName}')">${lfn:message('button.edit')}</a>
					<c:choose>
						<c:when test="${'true' eq field.status}">
							<!-- 禁用 -->
							<a class="btn_txt" href="javascript:changeStatus('false','${field.fieldName}')">${ lfn:message('sys-property:custom.field.status.false') }</a>
						</c:when>
						<c:otherwise>
							<!-- 启用 -->
							<a class="btn_txt" href="javascript:changeStatus('true','${field.fieldName}')">${ lfn:message('sys-property:custom.field.status.true') }</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<!--操作按钮 结束-->
		</list:data-column>
	</list:data-columns>

	<!-- 分页 -->
	<list:data-paging currentPage="${queryPage.pageno }" pageSize="${queryPage.rowsize }" totalSize="${queryPage.totalrows }" />
</list:data>