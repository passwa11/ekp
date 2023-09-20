<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<list:cri-criterion title="${lfn:message('sys-ui:ui.criteria.simplecategory') }" expand="false" key="simpleCategory">
	<list:box-title>
		<div class="criterion-title-popup-div">
		 <ui:menu layout="sys.ui.menu.nav"> 
			<ui:menu-item text="${criterionAttrs['title']}">
				<ui:menu-source autoFetch="true"
					href="javascript:luiCriteriaTitlePopupItemClick('${criterionAttrs['channel']}', '${criterionAttrs['key']}', '!{value}');">
					<ui:source type="AjaxJson">
						{"url":"/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=criteria&modelName=${varParams['modelName']}&parentId=!{value}&key=${criterionAttrs['key']}&channel=${criteriaAttrs['channel'] }&pAdmin=!{pAdmin}&authType=2"} 
					</ui:source>
				</ui:menu-source>
			</ui:menu-item>
		</ui:menu> 
		</div>
	</list:box-title>
	
	<list:box-select>
	
	<c:choose>
		<c:when test="${not empty criterionAttrs['cfg-if']}">
			<list:item-select type="lui/criteria/select_panel!CriterionHierarchyDatas" cfg-if="${criterionAttrs['cfg-if']}">
				<ui:source type="AjaxJson">
					{url: "/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=criteria&modelName=${varParams['modelName']}&parentId=!{value}&pAdmin=!{pAdmin}&authType=2"}
				</ui:source>
			</list:item-select>
		</c:when>
		<c:otherwise>
			<list:item-select type="lui/criteria/select_panel!CriterionHierarchyDatas">
				<ui:source type="AjaxJson">
					{url: "/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=criteria&modelName=${varParams['modelName']}&parentId=!{value}&pAdmin=!{pAdmin}&authType=2"}
				</ui:source>
			</list:item-select>
		</c:otherwise>
	</c:choose>	
	
	</list:box-select>
</list:cri-criterion>