<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%
	Object tabList = request.getAttribute("tabList");
	String tabsNoShow = "false";
	if(tabList instanceof List<?>){
		tabsNoShow = "true";
		List<Map<String, String>> tabListMap = (List<Map<String, String>>) request.getAttribute("tabList");
		for(int i = 0;i < tabListMap.size();i++){
			Map<String, String> tab = tabListMap.get(i);
			String fdIsShow = tab.get("fdIsShow");
			if("1".equals(fdIsShow) || "".equals(fdIsShow)){
				tabsNoShow = "false";
				break;
			}
		}
	}
%>
<c:set var="tabsNoShow" value="<%=tabsNoShow%>" scope="request"/>
<c:set var="expand" value="${empty param.expand ? 'false' : param.expand}"/>
<c:set var="edit" value="${empty isEdit ? 'false' : isEdit}"/>
<c:forEach items="${tabList }" var="tab" >
	<c:if test="${tab.fdIsOpen == '0'}">
		<c:set var="expand" value="false"/>
	</c:if>
	<c:if test="${tab.fdIsOpen == '1'}">
		<c:set var="expand" value="true"/>
	</c:if>
	<c:choose>
	<c:when test="${tab.fdTabType == '3' and tab.fdIsShow == '1'}">
		<c:choose>
			<c:when test="${edit && param.approveModel ne 'right'}">
				<%--流程--%>
				<c:import url="/sys/workflow/import/sysWfProcess_edit.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="modelingAppModelMainForm" />
					<c:param name="fdKey" value="modelingApp" />
					<c:param name="showHistoryOpers" value="true" />
					<c:param name="isExpand" value="${expand }" />
					<c:param name="order" value="10"/>
				</c:import>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${param.approveModel eq 'right'}">
						<c:choose>
							<c:when test="${modelingAppModelMainForm.docStatus>='30' || modelingAppModelMainForm.docStatus=='00'}">
								<%-- 流程 --%>
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="modelingAppModelMainForm" />
									<c:param name="fdKey" value="modelingApp" />
									<c:param name="showHistoryOpers" value="true" />
									<c:param name="onClickSubmitButton" value="Com_Submit(document.modelingAppModelMainForm, 'publishUpdate');" />
									<c:param name="isExpand" value="${expand }" />
									<c:param name="approveType" value="right" />
									<c:param name="needInitLbpm" value="true" />
									<c:param name="order" value="10"/>
								</c:import>
							</c:when>
							<c:otherwise>
								<%-- 流程 --%>
								<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
									<c:param name="formName" value="modelingAppModelMainForm" />
									<c:param name="fdKey" value="modelingApp" />
									<c:param name="showHistoryOpers" value="true" />
									<c:param name="onClickSubmitButton" value="Com_Submit(document.modelingAppModelMainForm, 'publishUpdate');" />
									<c:param name="isExpand" value="${expand }" />
									<c:param name="approveType" value="right" />
									<c:param name="order" value="10"/>
								</c:import>
							</c:otherwise>
						</c:choose>
					</c:when>
					<c:otherwise>
						<%-- 流程 --%>
						<c:import url="/sys/workflow/import/sysWfProcess_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="modelingAppModelMainForm" />
							<c:param name="fdKey" value="modelingApp" />
							<c:param name="showHistoryOpers" value="true" />
							<c:param name="onClickSubmitButton" value="Com_Submit(document.modelingAppModelMainForm, 'publishUpdate');" />
							<c:param name="isExpand" value="${expand }" />
							<c:param name="order" value="10"/>
						</c:import>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${tab.fdTabType == '2' and tab.fdIsShow == '1'}">
		<c:choose>
			<c:when test="${edit}">
				<c:if test="${tab.IsFlow == '1'}">
					<%-- 权限 --%>
					<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="modelingAppModelMainForm" />
						<c:param name="moduleModelName" value="com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain" />
						<c:param name="expand" value="${expand }"></c:param>
						<c:param name="isModeling" value="true"></c:param>
						<c:param name="order" value="10"/>
					</c:import>
				</c:if>
				<c:if test="${tab.IsFlow == '0'}">
					<%-- 权限 --%>
					<c:import url="/sys/right/import/right_edit.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="modelingAppSimpleMainForm" />
						<c:param name="moduleModelName" value="com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain" />
						<c:param name="expand" value="${expand }"></c:param>
						<c:param name="isModeling" value="true"></c:param>
						<c:param name="order" value="10"/>
					</c:import>
				</c:if>
			</c:when>
			<c:otherwise>
				<c:if test="${tab.IsFlow == '1'}">
					<%-- 权限 --%>
					<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
						<c:param name="formName" value="modelingAppModelMainForm" />
						<c:param name="moduleModelName" value="com.landray.kmss.sys.modeling.main.model.ModelingAppModelMain" />
						<c:param name="expand" value="${expand }"></c:param>
						<c:param name="isModeling" value="true"></c:param>
						<c:param name="order" value="10"/>
					</c:import>
				</c:if>
				<c:if test="${tab.IsFlow == '0'}">
					<%-- 权限 --%>
					<c:import url="/sys/right/import/right_view.jsp" charEncoding="UTF-8">
							<c:param name="formName" value="modelingAppSimpleMainForm" />
							<c:param name="moduleModelName" value="com.landray.kmss.sys.modeling.main.model.ModelingAppSimpleMain" />
							<c:param name="expand" value="${expand }"></c:param>
							<c:param name="isModeling" value="true"></c:param>
							<c:param name="order" value="10"/>
					</c:import>
				</c:if>
			</c:otherwise>
		</c:choose>
	</c:when>
	<c:when test="${tab.fdTabType == '1' and tab.fdIsShow == '1' and edit ne true}">
		<c:choose>
			<c:when test="${tab.IsFlow == '1'}">
				<%-- 访问统计 --%>
				<c:import url="/sys/readlog/import/sysReadLog_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="modelingAppModelMainForm" />
					<c:param name="expand" value="${expand }"></c:param>
					<c:param name="order" value="10"/>
				</c:import>
			</c:when>
			<c:when test="${tab.IsFlow == '0'}">
				<%-- 访问统计 --%>
				<c:import url="/sys/readlog/import/sysReadLog_view.jsp" charEncoding="UTF-8">
					<c:param name="formName" value="modelingAppSimpleMainForm" />
					<c:param name="expand" value="${expand }"></c:param>
					<c:param name="order" value="10"/>
				</c:import>
			</c:when>
		</c:choose>
	</c:when>
	<c:when test="${tab.fdTabType == '4' and tab.fdIsShow == '1' and edit ne true}">
		<c:choose>
			<c:when test="${tab.IsFlow == '1'}">
				<%--传阅机制(传阅记录)--%>
				<c:if test="${modelingAppModelMainForm.fdCanCircularize eq 'true' }">
					<c:import url="/sys/circulation/import/sysCirculationMain_view.jsp"	charEncoding="UTF-8">
						<c:param name="formName" value="modelingAppModelMainForm" />
						<c:param name="toolbarOrder" value="2" />
						<c:param name="expand" value="${expand }"></c:param>
						<c:param name="isTab" value="true"></c:param>
						<c:param name="isNew" value="true" />
						<c:param name="order" value="10"/>
					</c:import>
				</c:if>
			</c:when>
			<c:when test="${tab.IsFlow == '0'}">
				<%--传阅机制(传阅记录)--%>
				<c:if test="${modelingAppSimpleMainForm.fdCanCircularize eq 'true' }">
					<c:import url="/sys/circulation/import/sysCirculationMain_view.jsp"	charEncoding="UTF-8">
						<c:param name="formName" value="modelingAppSimpleMainForm" />
						<c:param name="toolbarOrder" value="2" />
						<c:param name="expand" value="${expand }"></c:param>
						<c:param name="isTab" value="true"></c:param>
						<c:param name="isNew" value="true" />
						<c:param name="order" value="10"/>
					</c:import>
				</c:if>
			</c:when>
		</c:choose>
	</c:when>
	<c:when test="${tab.fdTabType == '5' and tab.fdIsShow == '1' and edit ne true}">
		<c:choose>
			<c:when test="${tab.IsFlow == '1'}">
				<%-- 沉淀记录--%>
				<kmss:ifModuleExist path="/kms/multidoc/">
					<kmss:auth requestURL="/kms/multidoc/kms_multidoc_subside/kmsMultidocSubside.do?method=fileDoc&modelName=com.landray.kmss.sys.modeling.base.model.ModelingAppModel&fdId=${param.fdId}" requestMethod="GET">
						<c:import url="/kms/multidoc/kms_multidoc_subside/subsideRecord.jsp" charEncoding="UTF-8">
							<c:param name="fdId" value="${modelingAppModelMainForm.fdId }" />
							<c:param name="expand" value="${expand }"></c:param>
							<c:param name="order" value="10"/>
						</c:import>
					</kmss:auth>
				</kmss:ifModuleExist>
			</c:when>
			<c:when test="${tab.IsFlow == '0'}">
				<%-- 沉淀记录--%>
				<kmss:ifModuleExist path="/kms/multidoc/">
					<kmss:auth requestURL="/kms/multidoc/kms_multidoc_subside/kmsMultidocSubside.do?method=fileDoc&modelName=com.landray.kmss.sys.modeling.base.model.ModelingAppModel&fdId=${param.fdId}" requestMethod="GET">
						<c:import url="/kms/multidoc/kms_multidoc_subside/subsideRecord.jsp" charEncoding="UTF-8">
							<c:param name="fdId" value="${modelingAppSimpleMainForm.fdId }" />
							<c:param name="expand" value="${expand }"></c:param>
							<c:param name="order" value="10"/>
						</c:import>
					</kmss:auth>
				</kmss:ifModuleExist>
			</c:when>
		</c:choose>
	</c:when>
	<c:when test="${(tab.fdTabType == '0' or empty tab.fdTabType) and edit ne true}">
		<ui:content title="${tab.fdTabName }" toggle="true" expand="${expand }">
			<%-- 表单 --%>
			<c:if test="${tab.fdType == '0' && not empty tab.fdModelName }">
				<iframe
					src="<c:url value="/sys/modeling/main/${tab.fdModelName }.do"/>?method=xform&fdId=${tab.fdId }"
					name="if_xform_${tab.fdId }" align="top" width="100%"
					Frameborder=No Border=0 Marginwidth=0 Marginheight=0 Scrolling=No onload="setModelingViewtabIframeHeight(this)">
				</iframe>
			</c:if>
		    <%-- 列表 --%>
			<c:if test="${tab.fdType == '1' && tab.fdListviewId ne '' }">
				<iframe
					src="<c:url value="/sys/modeling/main/listview.do"/>?method=index&listviewId=${tab.fdListviewId }&fdAppModelId=${tab.fdAppModelId }&isFlow=${tab.isFlow }&fdTabId=${tab.fdTabId }&incFdId=${tab.incFdId}"
					name="if_listview_${tab.fdListviewId }" align="top" width="100%"
					Frameborder=No Border=0 Marginwidth=0 Marginheight=0 Scrolling=No onload="setModelingViewtabIframeHeight(this)">
				</iframe>
			</c:if>
			<c:if test="${tab.fdType == '1'&& tab.fdCollectionViewId ne ''}">
				<iframe
						src="<c:url value="/sys/modeling/main/collectionView.do"/>?method=index&listviewId=${tab.fdCollectionViewId }&fdAppModelId=${tab.fdAppModelId }&isFlow=${tab.isFlow }&fdTabId=${tab.fdTabId }&incFdId=${tab.incFdId}"
						name="if_listview_${tab.fdCollectionViewId }" align="top" width="100%"
						Frameborder=No Border=0 Marginwidth=0 Marginheight=0 Scrolling=YES onload="setModelingViewtabIframeHeight(this,'600')">
				</iframe>
			</c:if>
			<%-- 链接 --%>
			<c:if test="${tab.fdType == '2' }">
				<iframe
						src="<c:url value="${tab.fdLinkParams }"/>"
						name="if_xform_${tab.fdId }" align="top" width="100%"
						Frameborder=No Border=0 Marginwidth=0 Marginheight=0 onload="setModelingViewtabIframeHeight(this,'600')">
				</iframe>
			</c:if>
		</ui:content>
	</c:when>
	</c:choose>
</c:forEach>
<script>
function setModelingViewtabIframeHeight(iframe,minHeight) {
	if (iframe) {
		var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
		if (iframeWin.document.body) {
			iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
			if (iframeWin.document.documentElement.scrollHeight ==0){
				$(iframe).css("height",minHeight);
				return;
			}
			if(minHeight && minHeight!=0){
				iframe.height = minHeight;
			}
		}
	}
}
Com_AddEventListener(window,"load",function(){
	var tabsNoShow = "${tabsNoShow}";
	if(tabsNoShow === "true"){
		$(".lui_tabpage_collapsed").trigger("click");
		$(".lui_tabpage_uncollapse").css("display","none");
	}
});
</script>