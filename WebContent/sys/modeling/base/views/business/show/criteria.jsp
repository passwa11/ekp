<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.modeling.base.result.context.ListviewConditionEntry"%>
<%@ page import="com.landray.kmss.sys.modeling.base.result.util.ListviewConditionUtil"%>
<%@ page import="com.landray.kmss.util.EnumerationTypeUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.sunbor.web.tag.enums.ValueLabel" %>

<script>
	Com_IncludeFile('calendar.js');
	Com_IncludeFile("listview_ui_criteria.js", "${LUI_ContextPath}/sys/modeling/base/resources/js/", 'js', true);
</script>
<c:if test="${fn:length(conditions) > 0 }">
	<list:criteria id="criteria1">
	<c:forEach items="${conditions}" var="conditionEntry" varStatus="vStatus">
		<c:set var="propertyName" value="${conditionEntry.property.name}" />
		<c:set var="expend" value="${conditionEntry.expend}" />
		<c:set var="muti" value="${conditionEntry.muti}" />
		<c:if test="${not empty conditionEntry.defaultValues}">
			<c:set var="defaultValue" value="${conditionEntry.defaultValues}" />
		</c:if>
		<c:if test="${empty conditionEntry.defaultValues}">
			<c:set var="defaultValue" value="" />
		</c:if>
		<%-- 标题 --%>
		<c:if test="${not empty conditionEntry.messageKey}">
			<c:set var="titleText" value="${lfn:message(conditionEntry.messageKey)}" />
		</c:if>
		<c:if test="${empty conditionEntry.messageKey}">
			<c:set var="titleText" value="${conditionEntry.label}" />
		</c:if>
		<c:choose>
			<%-- 枚举 --%>
			<c:when test="${(conditionEntry.type=='number' or conditionEntry.type=='string' or conditionEntry.type=='enum') and (not empty conditionEntry.property.enumValues || not empty conditionEntry.property.enumType)}">
				<%
					ListviewConditionEntry conditionEntry = (ListviewConditionEntry) pageContext.getAttribute("conditionEntry");
					if (conditionEntry.getProperty().getEnumValues() == null) {
						StringBuilder values = new StringBuilder();
						List<ValueLabel> enums = EnumerationTypeUtil.getColumnEnumsByType(conditionEntry.getProperty().getEnumType(), request.getLocale());
						for (ValueLabel en : enums) {
							values.append(en.getLabel()).append("|").append(en.getValue()).append(";");
						}
						conditionEntry.getProperty().setEnumValues(values.toString());
					}
					ListviewConditionUtil.setPropertyEnumValues(conditionEntry.getProperty(), pageContext);
				%>
				<list:cri-criterion title="${titleText }" key="${propertyName}" expand="${expend }" multi="${muti }" >
					<list:box-select>
						<list:item-select cfg-defaultValue ="${defaultValue}" cfg-conditionBusinessType="${conditionEntry.businessType}">
							<ui:source type="Static">
								[
								<c:forEach var="item" items="${avalables }">
									{text:'${item.label }', value:'${item.value }'}
									,
								</c:forEach>
								]
							</ui:source>
						</list:item-select>
					</list:box-select>
				</list:cri-criterion>
			</c:when>
			<%-- 字符串 --%>
			<c:when test="${conditionEntry.type=='string'}">
				<list:cri-ref key="${propertyName}" ref="criterion.sys.string" title="${titleText }" expand="${expend }" multi="${muti }" defaultValue="${defaultValue}" conditionType="${conditionEntry.type}"/>
			</c:when>

			<%-- 数字 --%>
			<c:when test="${conditionEntry.type=='number'}">
				<list:cri-ref title="${titleText }" key="${propertyName}" ref="criterion.sys.num" expand="${expend }" multi="${muti }" defaultValue="${defaultValue}" conditionType="${conditionEntry.type}"/>
			</c:when>

			<%-- 日期 --%>
			<c:when test="${conditionEntry.type=='Date' || conditionEntry.type=='DateTime'}">
				<list:cri-ref title="${titleText }" key="${propertyName}" ref="criterion.sys.calendar" expand="${expend }" multi="${muti }" defaultValue="${defaultValue}" conditionType="${conditionEntry.type}"/>
			</c:when>

			<%-- 时间  //TODO 显示的是日期的样式，待改进 --%>
			<c:when test="${conditionEntry.type=='Time'}">
				<list:cri-ref title="${titleText }" key="${propertyName}" ref="criterion.sys.calendar" expand="${expend }" multi="${muti }" defaultValue="${defaultValue}" conditionType="${conditionEntry.type}">
					<list:varParams type="CriterionTimeDatas"></list:varParams>
				</list:cri-ref>
			</c:when>

			<%-- 人员 --%>
			<c:when test="${conditionEntry.type=='person'}">
				<list:cri-ref ref="criterion.sys.person" key="${propertyName}" title="${titleText }" expand="${expend }" multi="${muti }" defaultValue="${defaultValue}" conditionType="${conditionEntry.type}"/>
			</c:when>

			<%-- 部门 --%>
			<c:when test="${conditionEntry.type=='dept'}">
				<list:cri-ref ref="modeling.criterion.sys.dept" key="${propertyName}" title="${titleText }" expand="${expend }" multi="${muti }" defaultValue="${defaultValue}" conditionType="${conditionEntry.type}"/>
			</c:when>

			<%-- 岗位 --%>
			<c:when test="${conditionEntry.type=='post'}">
				<list:cri-ref ref="criterion.sys.postperson.availableAll" key="${propertyName}" title="${titleText }" expand="${expend }" multi="${muti }" defaultValue="${defaultValue}" conditionType="${conditionEntry.type}"/>
			</c:when>

			<%--//TODO 关联 --%>
			<c:when test="${conditionEntry.type=='foreign'}">
			</c:when>
		</c:choose>
	</c:forEach>
	</list:criteria>
</c:if>