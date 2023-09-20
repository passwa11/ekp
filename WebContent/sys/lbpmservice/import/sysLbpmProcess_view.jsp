<%@page import="com.landray.kmss.sys.lbpmservice.interfaces.ISysLbpmMainFormAdapter"%>
<%@page import="com.landray.kmss.sys.lbpmservice.interfaces.LbpmProcessForm"%>
<%@page import="java.util.Map"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessLimitTimeOperationLogService"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSettingDefault"%>
<%@page import="com.landray.kmss.util.ResourceUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="sysWfBusinessForm" value="${requestScope[param.formName]}" />
<c:set var="lbpmProcessForm" value="${sysWfBusinessForm.sysWfBusinessForm.internalForm}" />
<c:set var="resize_prefix" value="_" scope="request" />

<c:if test="${param.approveType ne 'right' || (param.approveType eq 'right' && (param.approvePosition eq 'right' || (param.approvePosition ne 'right' && param.needInitLbpm eq 'true')))}">
	<%-- 群协作 --%>
	<kmss:ifModuleExist path="/km/cogroup/">
		<c:import url="/km/cogroup/km_cogroup_main/view.jsp" charEncoding="UTF-8">
			<c:param name="modelName" value="${sysWfBusinessForm.modelClass.name}"></c:param>
		</c:import>
	</kmss:ifModuleExist>
</c:if>

<c:if test="${sysWfBusinessForm.sysWfBusinessForm!=null}">
	<%
		LbpmSettingDefault settingDefault = new LbpmSettingDefault();
		pageContext.setAttribute("approveModel", settingDefault.getApproveModel());
		String title = ResourceUtil.getString("sys-lbpmservice:lbpm.tab.label");
		if("right".equals(request.getParameter("approvePosition"))){
			title = ResourceUtil.getString("sys-lbpmservice:lbpmview.approve.model.dialog.view");
			pageContext.setAttribute("titleicon", "lui-fm-icon-3");
			pageContext.setAttribute("tabcontentId", "_right");
		}else if(StringUtil.isNotNull(request.getParameter("titleicon"))){
			pageContext.setAttribute("titleicon", request.getParameter("titleicon"));
		}
		pageContext.setAttribute("title", title);

		//限时办理，获取开关
		LbpmSetting lbpmSettingInfo = new LbpmSetting();
		String isShowLimitTimeOperation = lbpmSettingInfo.getIsShowLimitTimeOperation();
		request.setAttribute("showLimitTimeOperation", isShowLimitTimeOperation);

		if("true".equals(isShowLimitTimeOperation)){
			//限时办理，获取剩余时间
			ILbpmProcessLimitTimeOperationLogService operationLogService =
			(ILbpmProcessLimitTimeOperationLogService)SpringBeanUtil.getBean("lbpmProcessLimitTimeOperationLogService");
			String fdCurNodeXML = null;
			String fdProcessId = null;
			if(pageContext.getAttribute("sysWfBusinessForm") instanceof ISysLbpmMainFormAdapter){
				ISysLbpmMainFormAdapter adapter = (ISysLbpmMainFormAdapter)pageContext.getAttribute("sysWfBusinessForm");
				fdCurNodeXML = adapter.getSysWfBusinessForm().getFdCurNodeXML();
				fdProcessId = adapter.getSysWfBusinessForm().getFdProcessId();
			}
			if(StringUtil.isNotNull(fdCurNodeXML) && StringUtil.isNotNull(fdProcessId)){
				Map<String, Object> result = operationLogService.getRemainingTime(fdProcessId, fdCurNodeXML);
				Integer status = (Integer)result.get("status");

				if(status == 0){
					//表示已经超时
					request.setAttribute("timeoutDay", result.get("day"));
					request.setAttribute("timeoutHour",result.get("hour"));
					request.setAttribute("timeoutMinute",result.get("minute"));
					request.setAttribute("timeoutSecond", result.get("second"));
					request.setAttribute("limitTotalTime", result.get("total"));
					request.setAttribute("isTimeout", "true");
					request.setAttribute("showTimeType","timeout");//表示超时时间
				}else if(status == 1){
					//存在限时并未超过
					request.setAttribute("limitDay", result.get("day"));
					request.setAttribute("limitHour",result.get("hour"));
					request.setAttribute("limitMinute",result.get("minute"));
					request.setAttribute("limitSecond", result.get("second"));
					request.setAttribute("limitTotalTime", result.get("total"));
					request.setAttribute("isTimeout", "false");
					request.setAttribute("showTimeType", "limit");//表示限时时间
				}else if(status == -1){
					//不存在限时，不显示
					request.setAttribute("showLimitTimeOperation", "false");
				}
			}else{
				request.setAttribute("showLimitTimeOperation", "false");
			}
		}
	%>
	<script>
		var limitTotalTime = '${requestScope.limitTotalTime}';//提供在流程初始化时使用
		var isTimeoutTotal = '${requestScope.isTimeout}';
	</script>
	<c:if test="${param.isSimpleWorkflow != 'true' }">
	<c:choose>
		<c:when test="${empty param.isExpand and (sysWfBusinessForm.docStatus == '20' or sysWfBusinessForm.docStatus == '11')}">
		    <c:set var="expand" value="true"></c:set>
		</c:when>
		<c:otherwise>
		  <c:set var="expand" value="${param.isExpand}"></c:set>
		</c:otherwise>
	</c:choose>
	<c:if test="${sysWfBusinessForm.docStatus == '10' && param.approveType eq 'right' && LUI_ContextPath!=null && param.approvePosition eq 'right'}">
	    <ui:event event="layoutDone">
	    	$("i.lui-fm-icon-3").closest(".lui_tabpanel_vertical_icon_navs_item_l").hide();
	    </ui:event>
	</c:if>
	<c:set var="order" value="${empty param.order ? '10' : param.order}"/>
	<c:set var="disable" value="${empty param.disable ? 'false' : param.disable}"/>
	<ui:content id="process_review_tabcontent${tabcontentId}"
		title="${title}"
		expand="${expand}" titleicon="${titleicon}" cfg-order="${order}" cfg-disable="${disable}">
	<ui:event event="show">
		if (this.isBindOnResize) {
			return;
		}
		var element = this.element;
		function onResize() {
			element.find("*[_onresize]").each(function(){
				var elem = $(this);
				var funStr = elem.attr("_onresize");
				var show = elem.closest('tr').is(":visible");
				var init = elem.attr("data-init-resize");
				if(funStr!=null && funStr!="" && show && init == null){
					elem.attr("data-init-resize", 'true');
					var tmpFunc = new Function(funStr);
					tmpFunc.call();
				}
			});
		}
		this.isBindOnResize = true;
		onResize();
		element.click(onResize);
	</ui:event>
	<c:choose>
		<c:when test="${lbpmProcessForm.fdTemplateType=='4'}">
			<c:choose>
				<c:when test="${param.approveType eq 'right' && LUI_ContextPath!=null}">
					<c:choose>
						<c:when test="${param.approvePosition eq 'right'}">
							<c:import url="/sys/lbpmservice/common/process_view_freeFlow_right.jsp" charEncoding="UTF-8"></c:import>
						</c:when>
						<c:otherwise>
							<c:import url="/sys/lbpmservice/common/process_view_freeFlow_left.jsp" charEncoding="UTF-8"></c:import>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:import url="/sys/lbpmservice/common/process_view_freeFlow.jsp" charEncoding="UTF-8"></c:import>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:when test="${param.approveType eq 'right' && LUI_ContextPath!=null}">
			<!-- 参数approveType=='right'表示为右侧审批模式，参数approvePosition=='right'表示为右边部分，为空或其他
			为左边部分，流程初始化默认在右边，若只引左边不引右边，则需另加参数needInitLbpm=='true'来初始化 -->
			<c:choose>
				<c:when test="${param.approvePosition eq 'right'}">
					<c:import url="/sys/lbpmservice/common/process_view_right.jsp" charEncoding="UTF-8"></c:import>
				</c:when>
				<c:otherwise>
					<c:import url="/sys/lbpmservice/common/process_view_left.jsp" charEncoding="UTF-8"></c:import>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:when test="${(approveModel eq 'dialog' || approveModel eq 'tiled') && LUI_ContextPath!=null}">
			<c:import url="/sys/lbpmservice/common/process_view_dialog.jsp" charEncoding="UTF-8"></c:import>
		</c:when>
		<c:otherwise>
			<c:import url="/sys/lbpmservice/common/process_view.jsp" charEncoding="UTF-8"></c:import>
		</c:otherwise>
	</c:choose>
	</ui:content>
	</c:if>

	<c:if test="${param.isSimpleWorkflow == 'true' }">
	<ui:event event="show">
		if (this.isBindOnResize) {
			return;
		}
		var element = this.element;
		function onResize() {
			element.find("*[_onresize]").each(function(){
				var elem = $(this);
				var funStr = elem.attr("_onresize");
				var show = elem.closest('tr').is(":visible");
				var init = elem.attr("data-init-resize");
				if(funStr!=null && funStr!="" && show && init == null){
					elem.attr("data-init-resize", 'true');
					var tmpFunc = new Function(funStr);
					tmpFunc.call();
				}
			});
		}
		this.isBindOnResize = true;
		onResize();
		element.click(onResize);
	</ui:event>
	<c:choose>
		<c:when test="${lbpmProcessForm.fdTemplateType=='4'}">
			<c:choose>
				<c:when test="${param.approveType eq 'right' && LUI_ContextPath!=null}">
					<c:choose>
						<c:when test="${param.approvePosition eq 'right'}">
							<c:import url="/sys/lbpmservice/common/process_view_freeFlow_right.jsp" charEncoding="UTF-8"></c:import>
						</c:when>
						<c:otherwise>
							<c:import url="/sys/lbpmservice/common/process_view_freeFlow_left.jsp" charEncoding="UTF-8"></c:import>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:import url="/sys/lbpmservice/common/process_view_freeFlow.jsp" charEncoding="UTF-8"></c:import>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:when test="${param.approveType eq 'right' && LUI_ContextPath!=null}">
			<c:choose>
				<c:when test="${param.approvePosition eq 'right'}">
					<c:import url="/sys/lbpmservice/common/process_view_right.jsp" charEncoding="UTF-8"></c:import>
				</c:when>
				<c:otherwise>
					<c:import url="/sys/lbpmservice/common/process_view_left.jsp" charEncoding="UTF-8"></c:import>
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:when test="${(approveModel eq 'dialog' || approveModel eq 'tiled') && LUI_ContextPath!=null}">
			<c:import url="/sys/lbpmservice/common/process_view_dialog.jsp" charEncoding="UTF-8"></c:import>
		</c:when>
		<c:otherwise>
			<c:import url="/sys/lbpmservice/common/process_view.jsp" charEncoding="UTF-8"></c:import>
			<%-- <%@ include file="../common/process_view.jsp"%> --%>
		</c:otherwise>
	</c:choose>
	</c:if>
</c:if>