<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<%
	Object _varParams = request.getAttribute("varParams");
	if (_varParams != null) {
		Map<String, Object> varParams = (Map) _varParams;
		// 扩展查询字段
		Object extProps = varParams.get("extProps");
		if (extProps != null) {
			JSONObject ___obj = JSONObject.fromObject(extProps);
			Iterator it = ___obj.keys();
			JSONArray array = new JSONArray();
			while (it.hasNext()) {
				Object key = it.next();
				array.add(("qq." + key + "="
						+ ___obj.getString(key.toString())).toString());
			}
			String ___extProps = StringUtil.join(array, "&");
			pageContext.setAttribute("extProps", "&" + ___extProps);
		}

	}
%>

<list:cri-criterion title="${lfn:message('sys-ui:ui.criteria.category') }" expand="true" key="docCatergroyMain">
	<list:box-title>
		<div class="criterion-title-popup-div">
		 <ui:menu layout="sys.ui.menu.nav"> 
			<ui:menu-item text="${criterionAttrs['title']}">
				<ui:menu-source autoFetch="true" 
					href="javascript:luiCriteriaTitlePopupItemClick('${criterionAttrs['channel']}', '${criterionAttrs['key']}', '!{value}');">
					<ui:source type="AjaxJson">
						{"url":"/sys/category/criteria/sysCategoryCriteria.do?method=criteria&showTemp=${varParams['showTemp']}&modelName=${varParams['modelName']}&parentId=!{value}&nodeType=!{nodeType}&pAdmin=!{pAdmin}&key=${criterionAttrs['key']}&channel=${criteriaAttrs['channel'] }&authType=2${extProps}"} 
					</ui:source>
				</ui:menu-source>
			</ui:menu-item>
		</ui:menu>
		</div>
	</list:box-title>
	<list:box-select>
		<list:item-select type="lui/criteria!CriterionHierarchyDatas">
			<ui:source type="AjaxJson">
				{url: "/sys/category/criteria/sysCategoryCriteria.do?method=criteria&showTemp=${varParams['showTemp']}&modelName=${varParams['modelName']}&parentId=!{value}&pAdmin=!{pAdmin}&nodeType=!{nodeType}&authType=2${extProps}"}
			</ui:source>
		</list:item-select>
	</list:box-select>
</list:cri-criterion>