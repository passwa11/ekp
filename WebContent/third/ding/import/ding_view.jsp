<%@ page import="com.landray.kmss.third.ding.model.DingConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*,
	com.landray.kmss.util.*,
	com.landray.kmss.common.forms.ExtendForm,
	com.landray.kmss.third.ding.action.DingJsapiAction" %>
<%@ include file="/resource/jsp/common.jsp"%>
<%if("true".equals(DingConfig.newInstance().getDingEnabled())){%>
<c:set var="mainModelForm" value="${requestScope[param.formName]}" scope="request"/>

		<%
		//在钉钉的特殊处理 add by wubing date 2015-09-05
		if(isDingInEnter(request.getHeader("user-agent"))){
			ExtendForm efm = (ExtendForm)request.getAttribute("mainModelForm");
			//会议管理新增流程结束后会议发起人、主持人和组织者可以发送钉通知
			String fdEmceeId = (String)request.getParameter("fdEmceeId");
			String fdHostId = (String)request.getParameter("fdHostId");
			String fdAttendPersonIds = (String)request.getParameter("fdAttendPersonIds");
			String fdParticipantPersonIds = (String)request.getParameter("fdParticipantPersonIds");
			String isMeeting = (String)request.getParameter("isMeeting");
			String fdFinishDate = (String)request.getParameter("fdFinishDate");
			//是否已召开
			Boolean isEnd = false;
			Date now = new Date();
			Date finish = DateUtil.convertStringToDate(fdFinishDate);
			if (StringUtil.isNotNull(fdFinishDate) && now.getTime() > finish.getTime()) {
				isEnd = true;
			}
			String docCreatorId = DingJsapiAction.getDocCreatorId(efm.getFdId(),efm.getModelClass().getName());
			Boolean flag = docCreatorId.equals(UserUtil.getUser().getFdId()) || UserUtil.getUser().getFdId().equals(fdEmceeId) || UserUtil.getUser().getFdId().equals(fdHostId);
			if("true".equals(isMeeting) && flag && !isEnd){
				fdAttendPersonIds = fdAttendPersonIds + ";" + fdEmceeId + ";" + fdHostId;
		%>
		<c:if test="${(mainModelForm.docStatus>='20' && mainModelForm.docStatus<'30') || mainModelForm.docStatus == '11'}">
		<div class="muiDing"><i class="mui mui-ding" onclick="dingMsg()"></i></div>
			<script type="text/javascript">
				function dingMsg(){
					var url = "<c:url value="/third/ding/jsapi/dingmsg.jsp" />";
					url = Com_SetUrlParameter(url, "fdModelId", "${mainModelForm.fdId}");
					url = Com_SetUrlParameter(url, "fdModelName", "${mainModelForm.modelClass.name}");
					window.open(url, "_self")
				}
			</script>
		</c:if>
		<!-- 会议管理流程结束后后台无处理人信息，这里手动将参会人员信息传递过去 -->
		<c:if test="${mainModelForm.docStatus=='30' }">
		<div class="muiDing"><i class="mui mui-ding" onclick="dingMsg()"></i></div>
			<script type="text/javascript">
				function dingMsg(){
					var url = "<c:url value="/third/ding/jsapi/dingmsg.jsp" />";
					var fdAttendPersonIds = "<%=fdAttendPersonIds%>";
					var fdParticipantPersonIds = "<%=fdParticipantPersonIds%>";
					url = Com_SetUrlParameter(url, "fdModelId", "${mainModelForm.fdId}");
					url = Com_SetUrlParameter(url, "fdModelName", "${mainModelForm.modelClass.name}");
					url = Com_SetUrlParameter(url, "fdAttendPersonIds", fdAttendPersonIds);
					url = Com_SetUrlParameter(url, "fdParticipantPersonIds", fdParticipantPersonIds);
					window.open(url, "_self")
				}
			</script>
		</c:if>
		<%}else if(docCreatorId.equals(UserUtil.getUser().getFdId())){
			%>
			<c:if test="${(mainModelForm.docStatus>='20' && mainModelForm.docStatus<'30') || mainModelForm.docStatus == '11'}">
				<div class="muiDing"><i class="mui mui-ding" onclick="dingMsg()"></i></div>
					<script type="text/javascript">
						function dingMsg(){
							var url = "<c:url value="/third/ding/jsapi/dingmsg.jsp" />";
							url = Com_SetUrlParameter(url, "fdModelId", "${mainModelForm.fdId}");
							url = Com_SetUrlParameter(url, "fdModelName", "${mainModelForm.modelClass.name}");
							window.open(url, "_self")
						}
					</script>
			</c:if>
		<% 
		}
		}%>

<%!
public boolean isDingInEnter(String useragent){
	if(useragent.toLowerCase().indexOf("dingtalk")!=-1){
		return true;
	}
	return false;
}

public String getEncodeString(String str)throws Exception{
	return java.net.URLEncoder.encode(str,"UTF-8");
}

%>
<%}%>