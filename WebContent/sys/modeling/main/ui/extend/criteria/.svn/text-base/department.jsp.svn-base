<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<list:cri-criterion
	title="${lfn:message('sys-ui:ui.criteria.dept') }" expand="false"
	key="${criterionAttrs['key']}">
	<list:box-title>
		<div class="criterion-title-popup-div"><ui:menu
			layout="sys.ui.menu.nav">
			<ui:menu-item text="${criterionAttrs['title']}">
				<ui:menu-source autoFetch="true"
					href="javascript:luiCriteriaTitlePopupItemClick('${criterionAttrs['channel']}', '${criterionAttrs['key']}', '!{value}');">
					<ui:source type="AjaxJson">
						{"url":"/sys/organization/sys_org_element/sysOrgElementCriteria.do?method=criteria&parentId=!{value}&orgType=3"}
					</ui:source>
				</ui:menu-source>
			</ui:menu-item>
		</ui:menu></div>
	</list:box-title>
	<%
			String lookValue = "";
			if (UserUtil.getKMSSUser() != null
					&& UserUtil.getUser().getFdParent() != null
					&& UserUtil.getUser().getFdParent().getFdParent() != null) {
				lookValue = UserUtil.getUser().getFdParent().getFdParent().getFdId();
			}
			pageContext.setAttribute("lookValue", lookValue);
	%>
	<list:box-select>
		<c:choose>
			<c:when test="${not empty criterionAttrs['cfg-if']}">
				<%--包含子节点 复选框--%>
				<div class="criterion-is-include-sub" style="z-index: 100; display: none"><label>
					<input name="t1_${criterionAttrs['key']}" type="checkbox" value="true" listview-creteria-type="dept">
					<bean:message bundle="sys-modeling-main" key="search.includeChild"/>
				</label></div>
				<list:item-select type="lui/criteria!CriterionHierarchyDatas" cfg-lookValue="${lookValue }" cfg-if="${criterionAttrs['cfg-if']}">
					<ui:source type="AjaxJson">
						{url: "/sys/organization/sys_org_element/sysOrgElementCriteria.do?method=criteria&parentId=!{value}&orgType=3&__hierarchy=true"}
					</ui:source>
				</list:item-select>
			</c:when>
			<c:otherwise>
				<%--包含子节点 复选框--%>
				<div class="criterion-is-include-sub" style="z-index: 100; display: none"><label>
					<input name="t1_${criterionAttrs['key']}" type="checkbox" value="true" listview-creteria-type="dept">
					<bean:message bundle="sys-modeling-main" key="search.includeChild"/>
				</label></div>
				<list:item-select type="lui/criteria!CriterionHierarchyDatas" cfg-lookValue="${lookValue }">
					<ui:source type="AjaxJson">
						{url: "/sys/organization/sys_org_element/sysOrgElementCriteria.do?method=criteria&parentId=!{value}&orgType=3&__hierarchy=true"}
					</ui:source>
				</list:item-select>
			</c:otherwise>
		</c:choose>
	</list:box-select>


</list:cri-criterion>

<script>
	LUI.ready(function () {
		//包含子节点 复选框 第一次显示的时候初始化位置
		let initFinish = false;
		seajs.use('lui/topic', function (topic) {
			topic.subscribe('criteria.changed', function (evt) {
				if (initFinish)
					return;
				criterionIsIncludeSubDivInit('${criterionAttrs['canMulti']}');
				initFinish = true;
			});
		});
	});
</script>