<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.Date"%>
<%@page import="com.landray.kmss.sys.lbpmext.authorize.forms.LbpmAuthorizeForm"%>
<%@ include file="/resource/jsp/view_top.jsp"%>
<%
	//判断开始时间是不是比当前时间大，如果是，要让初始化的时间设置为开始时间，避开校验
	LbpmAuthorizeForm lbpmAuthorizeForm = (LbpmAuthorizeForm)request.getAttribute("lbpmAuthorizeForm");
	if(lbpmAuthorizeForm != null){
		String fdStartTime = lbpmAuthorizeForm.getFdStartTime();
		long startTime = DateUtil.convertStringToDate(fdStartTime).getTime();
		long curTime = new Date().getTime();
		if(startTime > curTime){
			request.setAttribute("isShowStartTime", "true");
		}else{
			request.setAttribute("isShowStartTime", "false");
		}
	}else{
		request.setAttribute("isShowStartTime", "false");
	}
%>
<script>

function validateSubmitForm2(method){
	var fdStartTime = document.getElementsByName("fdStartTime")[0];
	var fdEndTime = document.getElementsByName("fdEndTime")[0];
	var fdCurrentDate = document.getElementsByName("fdCurrentDate")[0];
	if(fdCurrentDate.value == ""){
		alert('<bean:message key="lbpmAuthorize.stop.view.stopTime.isNull" bundle="sys-lbpmext-authorize"/>');
		return ;
	}
	if(WorkFlow_CompareDate(fdCurrentDate.value,fdStartTime.value) <0){
		alert('<bean:message key="lbpmAuthorize.stop.view.ltStartTime1" bundle="sys-lbpmext-authorize"/>');
		return;
	}　　
	if(WorkFlow_CompareDate(fdEndTime.value, fdCurrentDate.value) < 0){
		alert('<bean:message key="lbpmAuthorize.stop.view.gtEndTime" bundle="sys-lbpmext-authorize"/>');
		return;
	}　　
	var today = new Date();
	var todayStr = today.getFullYear() + "-" + (today.getMonth() + 1) + "-" + today.getDate() + " " + today.getHours() + ":" +today.getMinutes();
	if(WorkFlow_CompareDate(Authorize_formatDate(fdCurrentDate.value), todayStr) < 0){
			alert('<bean:message key="lbpmAuthorize.stop.view.ltStartTime" bundle="sys-lbpmext-authorize"/>');
			return;
	}　　
	Com_Submit(document.lbpmAuthorizeForm, method);
}
function Authorize_formatDate(date){
	var dateFormat = Data_GetResourceString("date.format.datetime");
	var formatList = dateFormat.split(/\/|-| |:/);
	var dateList = date.split(/\/|-| |:/);
	var dateDetile = {};
	for(var i=0;i<formatList.length;i++){
		dateDetile[formatList[i]] = dateList[i];
	}
	return dateDetile["yyyy"] + "-" + dateDetile["MM"] + "-" + dateDetile["dd"] + " " + dateDetile["HH"] + ":" +dateDetile["mm"];
}
//比较两个日期的大小
function WorkFlow_CompareDate(dateOne,dateTwo){ 
	dateOne = dateOne.replace(/-/g,"\/");
	dateTwo = dateTwo.replace(/-/g,"\/");
	if (Date.parse(dateOne) > Date.parse(dateTwo)){
		return 1;
	}else if(Date.parse(dateOne) == Date.parse(dateTwo)){
		return 0;
	}else{
		return -1;
	}
}

</script>
<html:form action="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do">
<div id="optBarDiv">
		<c:if test="${_gtValue eq true}">
			<kmss:auth requestURL="/sys/lbpmext/authorize/lbpm_authorize/lbpmAuthorize.do?method=stop&fdId=${param.fdId}" requestMethod="GET">
			<input type=button value="<bean:message key="button.save"/>"
				onclick="validateSubmitForm2('stop');">
			</kmss:auth>
		</c:if>
	
	<input type="button"
		value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<kmss:windowTitle 
	moduleKey="sys-lbpmext-authorize:lbpmAuthorize.stop.view.title"/>
<p class="txttitle"><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.stop.view.title"/></p>
<center>
<table id="Label_Tabel" width="95%">
	<tr LKS_LabelName="<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorize.baseInfo"/>">
		<td>
			<table class="tb_normal" width=100%>
					<html:hidden name="lbpmAuthorizeForm" property="fdId"/>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdAuthorizeType"/>
					</td><td width=35%>
						<sunbor:enumsShow value="${lbpmAuthorizeForm.fdAuthorizeType}" enumsType="lbpmAuthorize_authorizeType" />
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdAuthorizer"/>
					</td><td width=35%>
						${lbpmAuthorizeForm.fdAuthorizerName }
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorizeItem.fdAuthorizeOrgId"/>
					</td><td width=35%>
						${lbpmAuthorizeForm.fdLbpmAuthorizeItemNames}
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdAuthorizedPerson"/>
					</td><td width=35%>
						${lbpmAuthorizeForm.fdAuthorizedPersonName}
					</td>
				</tr>
				<tr>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.lbpmAuthorizeScope"/>
					</td><td width=85% colspan=3>
						<textarea style="width:95%" readonly>${lbpmAuthorizeForm.fdScopeFormAuthorizeCateShowtexts}</textarea>
					</td>
				</tr>
				<c:if test="${lbpmAuthorizeForm.fdAuthorizeType != 1}">
					<tr id="processTypeRow">
						<td class="td_normal_title" width=15%>
							<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorizationTitle"/>
						</td><td width=85% colspan=3>
							<input type="hidden" name="fdStartTime" id="fdStartTime" value="${lbpmAuthorizeForm.fdStartTime}">
							<input type="hidden" name="fdEndTime" id="fdEndTime" value="${lbpmAuthorizeForm.fdEndTime}">
							<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorizationTitle.from"/>${lbpmAuthorizeForm.fdStartTime}
							<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.authorizationTitle.to"/>${lbpmAuthorizeForm.fdEndTime}

							&nbsp;&nbsp;&nbsp;
							<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.stop.view.set"/>
							<c:if test="${isShowStartTime == 'true' }">
								<xform:datetime property="fdCurrentDate" dateTimeType="datetime" value="${lbpmAuthorizeForm.fdStartTime}" showStatus="edit" required="true" style="width:150px;">
								</xform:datetime>
							</c:if>
							<c:if test="${isShowStartTime == 'false' }">
								<xform:datetime property="fdCurrentDate" dateTimeType="datetime" showStatus="edit" required="true" style="width:150px;">
								</xform:datetime>
							</c:if>
							&nbsp;&nbsp;
							<!-- 已经勾选到期自动回收的，不允许取消，避免多次终止时定时任务已经启动后，再取消时导致取消失败，未勾选的可以勾选 -->
							<c:if test="${lbpmAuthorizeForm.fdAuthorizeType==2}">
								<input type="checkbox" name="fdExpireRecover" value="true"
								<c:if test="${lbpmAuthorizeForm.fdExpireRecover=='true'}">
								checked="checked" onclick="return false;"
								</c:if>
								><bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdExpireRecover"/>
							</c:if>
						</td>
					</tr>
				</c:if>
				<tr>
					<td class="td_normal_title" width=15%> 
						<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdCreator"/>
					</td><td width=35%>
						${lbpmAuthorizeForm.fdCreatorName} 
					</td>
					<td class="td_normal_title" width=15%>
						<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.fdCreateTime"/>
					</td><td width=35%>
						${lbpmAuthorizeForm.fdCreateTime} 
					</td>
				</tr>
			
				<c:if test="${lbpmAuthorizeForm.fdStoppedFlag eq '1' }">
				<!--
				<tr>
					<td class="td_normal_title" width=15%> 
						<bean:message  bundle="sys-lbpmext-authorize" key="lbpmAuthorize.stop.label"/>
					</td>
					<td width=85% colspan="3">
						<span style="color:red">
							<kmss:message key="sys-lbpmext-authorize:lbpmAuthorize.stop.info" arg0="${lbpmAuthorizeForm.fdStoppedPersonName}" arg1="${lbpmAuthorizeForm.fdStoppedDate}"/>
						</span>
					</td>
				</tr>
				-->
				</c:if>
			</table>
		</td>
	</tr>
	<tr LKS_LabelName="<bean:message bundle="sys-lbpmext-authorize" key="lbpmAuthorize.logInfo"/>">
		<td>
			<iframe name="IFRAME" src='<c:url value="/sys/lbpmext/authorize/log/index.jsp?fdId=${lbpmAuthorizeForm.fdId}" />' frameBorder=0 width="100%"> 
			</iframe>
		</td>
	</tr>
</table>
</center>
		<html:hidden property="fdId"/>
		<html:hidden property="fdAuthorizerId"/>
		<html:hidden property="fdAuthorizedPersonId"/>
		
		<html:hidden property="method_GET"/>
</html:form>
<%@ include file="/resource/jsp/view_down.jsp"%>