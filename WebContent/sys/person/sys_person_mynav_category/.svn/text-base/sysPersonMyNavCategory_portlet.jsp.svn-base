<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/portal/portal.tld" prefix="portal"%>
<%@ page import="com.landray.kmss.sys.person.service.ISysPersonMyNavCategoryService,
		com.landray.kmss.util.SpringBeanUtil" %>
	<%
	if (request.getAttribute("home_navs") == null) {
		ISysPersonMyNavCategoryService service = (ISysPersonMyNavCategoryService) SpringBeanUtil.getBean("sysPersonMyNavCategoryService");
		request.setAttribute("home_navs", service.findPersonAllNav());
	}
	%>
	<c:set var="tempate_rander" scope="page">
			{$
					<ul class='lui_list_nav_list'>
			$} 
			for(var i=0;i <data.length;i++){
				var xid = encodeURIComponent(data[i].id);
				if (data[i].id == '${JsParam.nav }' || data[i].id == '${SYS_PERSON_HOME_LINK.fdId }') {
					{$
						<li class="lui_list_nav_selected">
							<a href="${ LUI_ContextPath }/sys/person/home.do?nav={%xid%}" target="_self" title="{%data[i].text%}">
								$}
								if(data[i].titleicon) {
								{$
								<i class="{%data[i].titleicon%}"></i>
								$}
								} else if(data[i].titleimg) {
								{$
								<i class="img icon"><img src="${ LUI_ContextPath }{%data[i].titleimg%}"/></i>
								$}
								}
								{$
								{%data[i].text%}
							</a>
						</li>
					$}
				} else {
					{$
						<li>
							<c:choose>
							     <c:when test="${not empty requestScope.pageTemplateId && requestScope.pageTemplateId eq 'person.home.old'}">
									<% /**------------个人门户模版（可设置宽度）在极速模式下不支持局部刷新（不支持点击左侧菜单异步加载右侧内容区的方式），仅允许以新窗口或全页面刷新的方式打开链接URL(详见:#79539)------------**/ %>
									<a
										onclick="var url='${ LUI_ContextPath }/sys/person/home.do?nav={%xid%}';url = Com_SetUrlParameter(url,'navTitle',encodeURIComponent('{%data[i].text%}'));LUI.pageQuickOpen(url,'{%data[i].target == '_blank' ? '_blank' : '_top' %}',{subscribeIframe:false});_setSelect(this);"
										href="javascript:void(0)" title="{%data[i].text%}">
										$}
										if(data[i].titleicon) {
										{$
										<i class="{%data[i].titleicon%}"></i>
										$}
										} else if(data[i].titleimg) {
										{$
										<i class="img icon"><img src="${ LUI_ContextPath }{%data[i].titleimg%}"/></i>
										$}
										}
										{$
										{%data[i].text%}
									</a>
							     </c:when>
							     <c:otherwise>
									<a 
										onclick="var url='${ LUI_ContextPath }/sys/person/home.do?nav={%xid%}';url = Com_SetUrlParameter(url,'navTitle',encodeURIComponent('{%data[i].text%}'));if(LUI.pageMode()=='quick' && {%data[i].target != '_blank'%}){ url+='&j_iframe=true&j_aside=false' }LUI.pageQuickOpen(url,'{%data[i].target == '_blank' ? '_blank' : (LUI.pageMode()=='quick' ? '_rIframe':'_top') %}',{subscribeIframe:false});_setSelect(this);"
										href="javascript:void(0)" title="{%data[i].text%}">
										$}
										if(data[i].titleicon) {
										{$
										<i class="{%data[i].titleicon%}"></i>
										$}
										} else if(data[i].titleimg) {
										{$
										<i class="img icon"><img src="${ LUI_ContextPath }{%data[i].titleimg%}"/></i>
										$}
										}
										{$
										{%data[i].text%}
									</a>
							     </c:otherwise>
							</c:choose>
						</li>
					$}
				}
			}
			{$
				</ul>
			$}
	</c:set>
	<ui:accordionpanel cfg-memoryExpand="person_my_home_nav" id="myNavCategory_accordion">
	<c:forEach items="${home_navs}" var="nav">
			<portal:portlet title="${(empty nav.fdShortName) ? (nav.fdName) : (nav.fdShortName) }">
				<c:if test="${nav.sysNavCategory != null}">
					<c:set var="nav" value="${nav.sysNavCategory }" scope="page" />
				</c:if>
				<ui:dataview>
					<ui:source type="Static">
							[<ui:trim>
							<c:forEach items="${nav.fdLinks}" var="link">
								{
									id: "${link.fdId }",
									text: "<c:out value="${link.fdName }" />",
									href: "${link.fdUrl }",
									target: "${link.fdTarget }"
									<c:if test="${not empty link.fdIcon}">
										,titleicon: '${link.fdIcon }'
									</c:if>
									<c:if test="${not empty link.fdImg}">
										,titleimg: '${link.fdImg }'
									</c:if>
								},
							</c:forEach>
							</ui:trim>]
						</ui:source>
					<ui:render type="Template">
					${tempate_rander }
					</ui:render>
				</ui:dataview>
			</portal:portlet>
	</c:forEach>
	<script>
		function _setSelect(dom){
			$("#myNavCategory_accordion .lui_list_nav_list>li").removeClass("lui_list_nav_selected");
			$(dom).closest("li").addClass("lui_list_nav_selected");
		}
	</script>
	</ui:accordionpanel>
	<%
	request.removeAttribute("home_navs");
	%>
