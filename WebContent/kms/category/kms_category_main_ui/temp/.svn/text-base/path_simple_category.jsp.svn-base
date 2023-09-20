<%@page import="com.landray.kmss.util.StringUtil"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.Map"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="java.util.Iterator"%>
<%@page import="net.sf.json.JSONArray"%>
<%
	Object _varParams = request.getAttribute("varParams");
	if (_varParams != null) {

		Map<String, Object> varParams = (Map) _varParams;
		
		// 扩展筛选
		Object criProps = varParams.get("criProps");
		if (criProps == null) {
			pageContext.setAttribute("criProps", "");
		} else {
			pageContext.setAttribute("criProps", criProps);
		}
				
		Object _href = varParams.get("href");
		if (_href != null) {
			String href = String.valueOf(_href);
			if(StringUtil.isNotNull(href)&&href.indexOf("!{docCategory}")>-1){
				href = href.replace("!{docCategory}", "!{value}");
			}else{
				String urlParam = null;
				Object _categoryKey = varParams.get("categoryKey");
				if (_categoryKey != null) {
					urlParam = _categoryKey + "=!{value}";
				} else {
					urlParam = "docCategory=!{value}";
				}
				href += "#" + urlParam;
				Object _extHash = varParams.get("extHash");
				if (_extHash != null) {
					String extHash = String.valueOf(_extHash);
					href += "&" + extHash;
				}
			}
			pageContext.setAttribute("href", href);
		}
		// 扩展查询字段
		Object extProps = varParams.get("extProps");
		if (extProps != null) {
			JSONObject ___obj = JSONObject.fromObject(extProps);
			Iterator it = ___obj.keys();
			JSONArray array = new JSONArray();
			while (it.hasNext()) {
				Object key = it.next();
				array.add(("qq." + key + "=" + ___obj.getString(key.toString())).toString());
			}
			String ___extProps = StringUtil.join(array, "&");
			pageContext.setAttribute("extProps", "&" + ___extProps);
		}

		Object oid = varParams.get("id");
		String id = "";
		if (oid != null) {
			id = String.valueOf(oid);
		} else {
			oid = varParams.get("vid");
			if (oid != null) {
				id = String.valueOf(oid);
			} else
				id = "simplecategoryId";
		}
		pageContext.setAttribute("__id__", id);
		
	}
%>

<c:set var="path_hrefDisable" value="false" />
<xform:editShow>
	<c:set var="path_hrefDisable" value="true" />
</xform:editShow>

<c:choose>
	<c:when test="${ path_hrefDisable == true }">
		<ui:menu layout="sys.ui.menu.nav" id="${__id__}">
			<ui:menu-item text="${ lfn:message('home.home') }"
				icon="lui_icon_s_home">
			</ui:menu-item>
			<ui:menu-item text="${varParams.moduleTitle }">
			</ui:menu-item>
			<ui:menu-item text="${varParams.LibName }">
			</ui:menu-item>
			<ui:menu-source
				autoFetch="${(empty varParams.autoFetch) ? 'true' : (varParams.autoFetch)}">
				<ui:source type="AjaxJson">
					{"url":"/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=path&modelName=${varParams.modelName }&categoryId=${varParams.categoryId}&currId=!{value}"} 
				</ui:source>
			</ui:menu-source>
		</ui:menu>
	</c:when>

	<c:otherwise>

		<c:set value="javascript:top.open('${LUI_ContextPath }/','_self')"
			var="rootUrl" />

		<c:if
			test="${not empty varParams.target && '_blank' eq varParams.target }">
			<c:set value="/" var="rootUrl" />
		</c:if>

		<ui:menu 
			layout="sys.ui.menu.nav" 
			id="${__id__}"
			cfg-router="/docCategory" >
			<ui:menu-item text="${ lfn:message('home.home') }"
				icon="lui_icon_s_home" href="${rootUrl }"
				target="${(empty varParams.target) ? '_self' : (varParams.target)}">
			</ui:menu-item>
			<ui:menu-item text="${varParams.moduleTitle }"
				href="${varParams.mainHref }"
				target="${(empty varParams.target) ? '_self' : (varParams.target)}">
			</ui:menu-item>
			<ui:menu-source
				cfg-extKey="${(empty varParams.extkey) ? '' : (varParams.extkey)}"
				autoFetch="${(empty varParams.autoFetch) ? 'true' : (varParams.autoFetch)}"
				target="${(empty varParams.target) ? '_self' : (varParams.target)}"
				href="${href}">
				<ui:source type="AjaxJson" cfg-criProps="${criProps }">
					{"url":"/sys/simplecategory/criteria/sysSimpleCategoryCriteria.do?method=path&modelName=${varParams.modelName }&categoryId=${varParams.categoryId}&currId=!{value}${extProps }&authType=2&pAdmin=!{pAdmin}"} 
				</ui:source>
			</ui:menu-source>
		</ui:menu>
	</c:otherwise>
</c:choose>
