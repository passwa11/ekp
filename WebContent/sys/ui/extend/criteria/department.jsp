<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.util.UserUtil"%>
<list:cri-criterion
	title="${lfn:message('sys-ui:ui.criteria.dept') }" expand="false"
	key="${criterionAttrs['key']}">
	<list:box-title>
		<div class="criterion-title-popup-div"><ui:menu
			layout="sys.ui.menu.nav">
			<ui:menu-item text="${criterionAttrs['title']}">
				<ui:menu-source autoFetch="true" cfg-key="${criterionAttrs['key']}" var-key="${criterionAttrs['key']}"
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
				<list:item-select type="lui/criteria!CriterionHierarchyDatas" cfg-lookValue="${lookValue }" cfg-if="${criterionAttrs['cfg-if']}">
					<ui:source type="AjaxJson">
						{url: "/sys/organization/sys_org_element/sysOrgElementCriteria.do?method=criteria&parentId=!{value}&orgType=3&__hierarchy=true"}
					</ui:source>
				</list:item-select>
			</c:when>
			<c:otherwise>
				<list:item-select type="lui/criteria!CriterionHierarchyDatas" cfg-lookValue="${lookValue }">
					<ui:source type="AjaxJson">
						{url: "/sys/organization/sys_org_element/sysOrgElementCriteria.do?method=criteria&parentId=!{value}&orgType=3&__hierarchy=true"}
					</ui:source>
				</list:item-select>
			</c:otherwise>
		</c:choose>	
	</list:box-select>
</list:cri-criterion>