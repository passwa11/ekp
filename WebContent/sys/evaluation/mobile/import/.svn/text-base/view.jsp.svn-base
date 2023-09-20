<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationForm" %>
<%@page import="com.landray.kmss.sys.evaluation.interfaces.ISysEvaluationNotesForm" %>

<c:set var="sysEvaluationForm" value="${requestScope[param.formName]}" />
<c:set var="showOption" value="${param.showOption}" />
<c:set var="_eval_icon" value="" />
<c:set var="_eval_label" value="" />
<%
	String formName = request.getParameter("formName");
	String fdIsShow = "true";
	String _eval_count = null;
	if(StringUtil.isNotNull(formName)) {
		Object form = request.getAttribute(formName);
		ISysEvaluationForm evaForm = ((ISysEvaluationForm)form);
		fdIsShow = evaForm.getEvaluationForm().getFdIsShow();
		_eval_count = evaForm.getEvaluationForm().getFdEvaluateCount();
		if(StringUtil.isNotNull(_eval_count)) {
			_eval_count = _eval_count.replace("(", "").replace(")", "");
			if(form instanceof ISysEvaluationNotesForm) {
				//段落点评在移动端暂不考虑
				String notesCount = ((ISysEvaluationNotesForm)form).getEvaluationNotesCount();
				if(StringUtil.isNotNull(notesCount)) {
					Integer notes_c = Integer.valueOf(notesCount);
					Integer eval_c = Integer.valueOf(_eval_count);
					Integer c  = eval_c - notes_c;
					if(c == 0) {
						_eval_count = null;
					} else {
						_eval_count = c.toString();
					}
				}
			}
		}
	}
	pageContext.setAttribute("_eval_count", _eval_count);
	pageContext.setAttribute("fdIsShow", fdIsShow);
%>
<c:if test="${showOption == 'icon'}">
    <c:set var="_eval_icon" value="mui mui-eval" />
</c:if>
<c:if test="${showOption == 'label'}">
	<c:set var="_eval_label" value="${lfn:message('sys-evaluation:sysEvaluationMain.button.evaluation')}" />
</c:if>
<c:if test="${showOption == 'all' || empty showOption }">

    <c:set var="_eval_icon" value="mui mui-eval" />
    <c:if test="${ empty showOption}">
		<c:set var="_eval_label" value="${param.label}" />
	</c:if>
	<c:if test="${ showOption == 'all'}">
		<c:set var="_eval_label" value="${lfn:message('sys-evaluation:sysEvaluationMain.button.evaluation')}" />
	</c:if>
</c:if>
<c:if test="${fdIsShow == 'true'}">
	<c:if test="${empty _eval_label}">

		<c:choose>

			<c:when test="${param.docFlag}">
				<li data-dojo-type="sys/evaluation/mobile/js/TabBarButton"
					data-dojo-props='icon1:"${_eval_icon}",align:"${param.align}",
			badge:"${_eval_count}",
			href:"/sys/evaluation/mobile/index.jsp?modelName=${sysEvaluationForm.modelClass.name}&modelId=${sysEvaluationForm.fdId}&notifyOtherNameText=${param.notifyOtherNameText}&notifyOtherName=${param.notifyOtherName}&isNotify=${param.isNotify}"'></li>
			</c:when>
			<c:otherwise>
				<li data-dojo-type="mui/tabbar/TabBarButton"
					data-dojo-props='icon1:"${_eval_icon}",align:"${param.align}",
			badge:"${_eval_count}",
			href:"/sys/evaluation/mobile/index.jsp?modelName=${sysEvaluationForm.modelClass.name}&modelId=${sysEvaluationForm.fdId}&notifyOtherNameText=${param.notifyOtherNameText}&notifyOtherName=${param.notifyOtherName}&isNotify=${param.isNotify}"'></li>
			</c:otherwise>
		</c:choose>
	</c:if>
     <c:if test="${not empty _eval_label}">
     <c:if test="${not empty _eval_count}"><c:set var="_eval_label" value="${_eval_label}(${_eval_count})" /></c:if>
	<li data-dojo-type="mui/tabbar/TabBarButton"
		data-dojo-props='icon1:"",align:"${param.align}",label:"${_eval_label}",
			href:"/sys/evaluation/mobile/index.jsp?modelName=${sysEvaluationForm.modelClass.name}&modelId=${sysEvaluationForm.fdId}&isNotify=${param.isNotify}"'></li>
     </c:if>
</c:if>
