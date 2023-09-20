<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page errorPage="/resource/jsp/jsperror.jsp" %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script type="text/javascript">
//Com_IncludeFile("document.js", "style/"+Com_Parameter.Style+"/doc/");
Com_IncludeFile("jquery.treeTable.css","${KMSS_Parameter_ContextPath}sys/lbpmservice/resource/","css",true);
Com_IncludeFile("jquery.js");
Com_IncludeFile("data.js");
Com_IncludeFile("jquery.treeTable.js","${KMSS_Parameter_ContextPath}sys/lbpmservice/resource/","js",true);
seajs.use(['theme!form']);
function initialPage(){
	try {
		//隐藏审批时长
		var isShowApprovalTime = _lbpmIsShowApprovalTime();
		if (!isShowApprovalTime){
			$("[name*='fdCostTimeDisplayString']").hide();
			$("[name*='efficiency']").hide();
		}
		var arguObj = document.getElementById("auditNoteTable");
		if(arguObj != null && window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
			var height = arguObj.offsetHeight + 0;
			if(height>0)
				window.frameElement.style.height = height + "px";
		}
		setTimeout(initialPage, 200);
	} catch(e) {
	}
}
Com_AddEventListener(window,'load',function() {
	$("#auditNoteTable").treeTable({
		initialState:"expanded",
		treeColumn:1,
		stringExpand:"",
		stringCollapse:""
	});
	initialPage();
	setTimeout(initialPage, 600); // 可能加载附件比较慢，再次调整
	_Lbpm_SettingInfo = new KMSSData().AddBeanData("lbpmSettingInfoService").GetHashMapArray()[0]; //统一在此获取流程默认值与功能开关等配置
	function __fixLabelStyle(argus) {
		var context = document;
		if (argus && argus.row) {
			context = argus.context;
		}
		$('.auditNote',context).each(function (index, dom) {
			var html = dom.innerHTML;
			var reg = new RegExp("&nbsp;","g");
			html = html.replace(/^\s*/g, "");
			html = html.replace(reg, " ");
			html = html.replace(/\s*$/g, "");
			dom.innerHTML = html;
		});
	}
});

function _lbpmIsShowApprovalTime(){
	var isShow = true;
	if (_Lbpm_SettingInfo && 
			_Lbpm_SettingInfo.isShowApprovalTime === "false"){
		isShow = false;
	}
	return isShow;
}



/**
 * 新增附言，fdId是审批意见的id
 */
function _addLbpmPostscript(fdAuditNoteId,formBeanName,fdFactNodeId){
	 var url = "sys/lbpmservice/support/lbpm_postscript/lbpmPostscript.do?method=add&fdAuditNoteId=" + fdAuditNoteId + "&formBeanName=" + formBeanName + "&fdFactNodeId=" + fdFactNodeId;
	var param={
		AfterShow:function(rtnVal){
				if(window.showModalDialog){//IE模态窗口
					resetIFrame(200);
				}else{
					resetIFrame(200);
				}
		}, 
		win:window
	}
	
	var width = 600,height = 450;
	
	if (typeof seajs !== "undefined") {
		seajs.use(['lui/dialog',],function(dialog) {
			url =  "/" + url ;
			dialog.iframe(url," ",
					function(value) {
						param.AfterShow(value);
					}, 
					{"width" : width,"height" : height + 50}
			);
		});
	} else {
		url =  Com_Parameter.ContextPath + url;
		_popupWindow(url,width,height,param);
	}
}

/**
 * 修改审批意见点击意见，fdId是审批意见的id
 */
function _modifyAuditNote(fdId,formBeanName){
	 var url = Com_Parameter.ContextPath
	+ "sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_modifyForward.jsp?fdId=" + fdId + "&formBeanName=" + formBeanName; 
	var param={
		AfterShow:function(rtnVal){
				if(window.showModalDialog){//IE模态窗口
					resetIFrame();
				}else{
					resetIFrame();
				}
		}, 
		win:window
	}
	_popupWindow(url,800,600,param);
}

/**
 * 局部刷新审批意见iframe
 */
function resetIFrame(time){
	time = time || 0;
	setTimeout(function(){
		window.location.href=window.location.href;
		if(window.parent){
			var auditNoteIframe = window.parent.document.getElementById("auditNoteRight");
			if(auditNoteIframe && auditNoteIframe.contentWindow){
				auditNoteIframe.contentWindow.location.reload();
			}
		}
	},time);
}

function _popupWindow(url,width,height,param){
	var left = (screen.width-width)/2;
	var top = (screen.height-height)/2;
	if(window.showModalDialog){
		var winStyle = "resizable:1;scroll:1;dialogwidth:"+width+"px;dialogheight:"+height+"px;dialogleft:"+left+";dialogtop:"+top;
		var rtnVal=window.showModalDialog(url, param, winStyle);
		if(param.AfterShow){
			param.AfterShow(rtnVal);
		}
	}else{
		var winStyle = "resizable=1,scrollbars=1,width="+width+",height="+height+",left="+left+",top="+top+",dependent=yes,alwaysRaised=1";
		Com_Parameter.Dialog = param;
		var tmpwin=window.open(url, "_blank", winStyle);
		if(tmpwin){
			tmpwin.onbeforeunload=function(){
				if(param.AfterShow && !param.AfterShow._isShow) {
					param.AfterShow._isShow = true;
					param.AfterShow(tmpwin.returnValue);
				}   
			};
		}		
	}
}

/**
 * 查看历史审批记录
 */
function _viewHistoryAuditNote(fdId,formBeanName){
	var url = Com_Parameter.ContextPath
	+ "sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote_viewHistoryForward.jsp?fdId=" + fdId + "&formBeanName=" + formBeanName ;
	var param={
	AfterShow:function(rtnVal){
			if(window.showModalDialog){//IE模态窗口
				//resetIFrame();
			}else{
				//resetIFrame();
			}
		}
	}
	_popupWindow(url,800,600,param);
}

</script>
</head>
<body>
<c:set var="mainForm" value="${requestScope[formBeanName]}" scope="page" />
<style>
.tb_normal .td_normal_title {
	text-align: center;
}
#auditNoteTable .collapsed:hover{
	background-color: #fff!important;
}
</style>
<html:form action="/sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote.do">
	<table class="tb_normal" width="100%" id="auditNoteTable" >
		<tr class="tr_normal">
			<td width="12%" class="td_normal_title">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.date" />
			</td>
			<td width="15%" class="td_normal_title">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.fdFactNodeName" />
			</td>
			<td width="13%" class="td_normal_title">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.fdHandlerId" />
			</td>
			<td width="13%" class="td_normal_title">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.fdActionKey" />
			</td>
			<td width="46%" class="td_normal_title">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.fdAuditNote" />
			</td>
			<%--  <c:if test="${requestScope['isPrivileger'] == true }">
			<td width="20%" class="td_normal_title">
				<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.fdActionInfo" />
			</td>
			</c:if>--%>
		</tr>
		<c:if test="${isEmptyList}">
			<tr>
				<td align="center" colspan="9"><bean:message bundle="sys-lbpmservice-support" key="lbpmProcess.no.relevant.records" /></td>
			</tr>
		</c:if>
		<c:forEach items="${auditNotes}" var="lbpmAuditNote" varStatus="vStatus">
			<tr 
				<c:if test="${not empty lbpmAuditNote.fdExecutionId}">
					<c:if test="${lbpmAuditNote.fdActionKey == '_concurrent_branch'}">
						id="${lbpmAuditNote.fdExecutionId}" class="
						<c:if test="${empty rootExecutionId}">
							<c:set var="rootExecutionId" value="${lbpmAuditNote.fdParentExecutionId}" />
						</c:if>
						<c:if test="${lbpmAuditNote.fdParentExecutionId != rootExecutionId}">
							child-of-${lbpmAuditNote.fdParentExecutionId}
						</c:if>
						<c:if test="${not empty rootSubExecutionId &&  lbpmAuditNote.fdParentExecutionId == rootSubExecutionId}">
							child-of-${rootSubId}
						</c:if>
						<c:if test="${true eq lbpmAuditNote.distributeNote || true eq lbpmAuditNote.recoverNote}">
							child-of-${lbpmAuditNote.fdParentExecutionId}
							<c:set var="rootExecutionId" value="" />
						</c:if>
						"
					</c:if>
					<c:if test="${lbpmAuditNote.fdActionKey == 'distributeNote' || lbpmAuditNote.fdActionKey == 'recoverNote'}">
						id="${lbpmAuditNote.fdExecutionId}"
					</c:if>
					<c:if test="${lbpmAuditNote.fdActionKey != '_concurrent_branch' && lbpmAuditNote.fdActionKey != 'distributeNote' && lbpmAuditNote.fdActionKey != 'recoverNote'}">
						<c:choose>
							<c:when test="${lbpmAuditNote.fdActionKey == '_vote_start'}">
								id="${lbpmAuditNote.fdId}"
									<c:set var="rootVoteId" value="${lbpmAuditNote.fdId}" />
									<c:set var="rootVoteExecutionId" value="${lbpmAuditNote.fdExecutionId}" />
								<c:if test="${not empty lbpmAuditNote.fdParentExecutionId}">
									class="child-of-${lbpmAuditNote.fdExecutionId}"
								</c:if>
							</c:when>
							<c:when test="${lbpmAuditNote.fdActionKey == '_vote_end' || lbpmAuditNote.fdActionKey == '_pocess_end'}">
								id="${lbpmAuditNote.fdId}"
								<c:if test="${not empty lbpmAuditNote.fdParentExecutionId}">
									class="child-of-${lbpmAuditNote.fdExecutionId}"
								</c:if>
							</c:when>
							<c:when test="${lbpmAuditNote.fdActionKey == 'subFlowNode_start'}">
								id="${lbpmAuditNote.fdId}"
									<c:set var="rootSubId" value="${lbpmAuditNote.fdId}" />
									<c:set var="rootSubExecutionId" value="${lbpmAuditNote.fdExecutionId}" />
								<c:if test="${not empty lbpmAuditNote.fdParentExecutionId}">
									class="child-of-${lbpmAuditNote.fdExecutionId}"
								</c:if>
							</c:when>
							<c:otherwise>
								id="${lbpmAuditNote.fdId}" class="
								<c:if test="${lbpmAuditNote.fdExecutionId == rootVoteExecutionId}">
									child-of-${rootVoteId}
								</c:if>
								<c:if test="${not empty rootSubExecutionId &&  lbpmAuditNote.fdParentExecutionId == rootSubExecutionId}">
										child-of-${rootSubId}
								</c:if>
								<c:if test="${not empty lbpmAuditNote.fdParentExecutionId}">
									child-of-${lbpmAuditNote.fdExecutionId}
								</c:if>
								"
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:if>
				>
				<td>
					<kmss:showDate type="datetime" value="${lbpmAuditNote.fdCreateTime}"/>
				</td>
				<td style="padding-left:14px;">
					<c:out value="${lbpmAuditNote.fdFactNodeName}" />
				</td>
				<td style="word-wrap: break-word;word-break: break-all;">
					<span title='<c:out value="${lbpmAuditNote.detailHandlerName}" />'>
						<c:out value="${lbpmAuditNote.handlerName}" escapeXml="false"/>
						<c:if test="${lbpmAuditNote.fdActionKey == 'share_handler_pass' || lbpmAuditNote.fdActionKey == 'share_handler_refuse'}">
							(分享人)
						</c:if>
					</span>
				</td>
				<td style="word-wrap: break-word;word-break: break-all;">
					<c:if test="${lbpmAuditNote.fdActionKey != 'subFlowNode_start'}">
						<c:out value="${lbpmAuditNote.fdActionInfo}" />
					</c:if>
				</td>
				<td>
					<c:if test="${lbpmAuditNote.fdActionKey != 'subFlowNode_start'}">
						<table class="tb_noborder" width="100%">
						<c:if test="${lbpmAuditNote.fdIsHide=='2'}">
								<tr>
									<td style="word-wrap: break-word;word-break: break-word;">
									<c:choose>
										<c:when test="${lbpmAuditNote.fdActionKey == 'share_handler_pass' || lbpmAuditNote.fdActionKey == 'share_handler_refuse'}">
											<c:if test="${lbpmAuditNote.fdIsShowViewHistoryIcon=='1'}">
												<strong><label class="auditNote" style="white-space:pre-wrap">${lbpmAuditNote.fdAuditNote}</label></strong>
											</c:if>
											<c:if test="${lbpmAuditNote.fdIsShowViewHistoryIcon=='0'}">
													<label class="auditNote" style="white-space:pre-wrap">${lbpmAuditNote.fdAuditNote}</label>
											</c:if>
										</c:when>
										<c:otherwise>
											<c:if test="${lbpmAuditNote.fdIsShowViewHistoryIcon=='1'}">
												<strong><label class="auditNote" style="white-space:pre-wrap"><c:out value="${lbpmAuditNote.fdAuditNote}" /></label></strong>
											</c:if>
											<c:if test="${lbpmAuditNote.fdIsShowViewHistoryIcon=='0'}">
												<label class="auditNote" style="white-space:pre-wrap"><c:out value="${lbpmAuditNote.fdAuditNote}" /></label>
											</c:if>
										</c:otherwise>
									</c:choose>
									</td>
								</tr>
								<tr>
									<td colspan="2" style="word-wrap: break-word;word-break: break-all;">
										<c:if test="${not empty lbpmAuditNote.fdExtNote}">
											<c:out value="${lbpmAuditNote.fdExtNote}" escapeXml="false"/>
										</c:if>
									</td>
								</tr>
								<c:if test="${not empty lbpmAuditNote.auditNoteListsJsps4Pc}">
								<tr>
									<td colspan="2">
								<c:forEach items="${lbpmAuditNote.auditNoteListsJsps4Pc}" var="auditNoteListsJsp" varStatus="vstatus">
									<c:import url="${auditNoteListsJsp}" charEncoding="UTF-8">
										<c:param name="auditNoteFdId" value="${lbpmAuditNote.fdId}" />
										<c:param name="modelName" value="${lbpmAuditNote.fdProcess.fdModelName}" />
										<c:param name="modelId" value="${lbpmAuditNote.fdProcess.fdModelId}" />
										<c:param name="formName" value="${formBeanName}"/>
										<c:param name="curHanderId" value="${lbpmAuditNote.fdHandler.fdId}"/>
									</c:import>
								</c:forEach>
									</td>
								</tr>
								</c:if>
								<c:if test="${not empty mainForm.attachmentForms[lbpmAuditNote.fdId] and not empty mainForm.attachmentForms[lbpmAuditNote.fdId].attachments}">
								<tr>
									<td colspan="2">
								        <c:choose>
											<c:when test="${param.approveType eq 'right'}">
												<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
										          <c:param name="formBeanName" value="${formBeanName}"/>
										          <c:param name="fdKey" value="${lbpmAuditNote.fdId}"/>
										          <c:param name="fdModelId" value="${lbpmAuditNote.fdProcess.fdModelId}"/>
										          <c:param name="fdModelName" value="${lbpmAuditNote.fdProcess.fdModelName}"/>
										          <%-- 修改附件模板类型，让审批意见附件功能支持全部下载功能 2017-9-21 王祥 --%>
										          <%-- <c:param name="fdViewType" value="simple" /> --%>
										          <c:param name="fdViewType" value="byte" />
										          <c:param name="fdForceDisabledOpt" value="edit" />
										          <c:param name="fdLabel" value="${lfn:message('sys-lbpmservice:lbpmProcess.attachment.auditnote')}" />
												  <c:param name="fdGroup" value="lbpm" />
												  <c:param name="fdGroupName" value="${lfn:message('sys-lbpmservice:lbpmProcess.attachment.group')}" />
										       	  <c:param name="isShowDownloadCount" value="false" />
										        </c:import>
											</c:when>
											<c:otherwise>
												<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
										          <c:param name="formBeanName" value="${formBeanName}"/>
										          <c:param name="fdKey" value="${lbpmAuditNote.fdId}"/>
										          <c:param name="fdModelId" value="${lbpmAuditNote.fdProcess.fdModelId}"/>
										          <c:param name="fdModelName" value="${lbpmAuditNote.fdProcess.fdModelName}"/>
										          <%-- 修改附件模板类型，让审批意见附件功能支持全部下载功能 2017-9-21 王祥 --%>
										          <%-- <c:param name="fdViewType" value="simple" /> --%>
										          <c:param name="fdViewType" value="byte" />
										          <c:param name="fdForceDisabledOpt" value="edit" />
										          <c:param name="fdLabel" value="${lfn:message('sys-lbpmservice:lbpmProcess.attachment.auditnote')}" />
												  <c:param name="fdGroup" value="lbpm" />
												  <c:param name="fdGroupName" value="${lfn:message('sys-lbpmservice:lbpmProcess.attachment.group')}" />
										       	</c:import>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
								</c:if>
								<c:if test="${not empty lbpmAuditNote.auditNoteFrom && fn:length(requestScope.lbpmPostscriptMap[lbpmAuditNote.fdId]) > 0}">
								<tr>
									<td colspan="2" align="right" style="color:#999;">
										<kmss:showText value="${lbpmAuditNote.auditNoteFrom}" />
									</td>
								</tr>
								</c:if>
								<tr>
									<td colspan="2" align="right" style="color:#999;padding-top: 10px;">
										<c:if test="${not empty lbpmAuditNote.fdCostTimeDisplayString}">
											<c:if test="${lbpmAuditNote.fdActionKey!='_pocess_end'}">
												<span name="fdCostTimeDisplayString${vStatus.index}" style="float:left;">
													<span>
														<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.fdCostTime" />:
													</span>
													<span>
														<kmss:showText value="${lbpmAuditNote.fdCostTimeDisplayString}" />
													</span>
												</span>
											</c:if> 
										</c:if>
										<span>
											<!-- 查看历史记录图标 -->
											<c:if test="${lbpmAuditNote.fdIsShowViewHistoryIcon=='1'}">
												<a href="javascript:;" style="float:right;margin-right:10px;" class="com_btn_link" onclick="Com_EventPreventDefault();_viewHistoryAuditNote('${lbpmAuditNote.fdId}','${formBeanName}');">
													<img alt="" src="${KMSS_Parameter_ContextPath}sys/lbpmservice/support/lbpm_audit_note/css/images/icon-viewHistoryLbpmAuditNote.png" title='<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.viewHistory" />'>
												</a>
											</c:if>
											<!-- 修改审批记录图标 -->
											<c:if test="${lbpmAuditNote.fdIsShowModifyIcon=='1'}">
												<a href="javascript:;" style="float:right;margin-right:10px;" class="com_btn_link" onclick="Com_EventPreventDefault();_modifyAuditNote('${lbpmAuditNote.fdId}','${formBeanName}');">
													<img alt="" src="${KMSS_Parameter_ContextPath}sys/lbpmservice/support/lbpm_audit_note/css/images/icon-updateLbpmAuditNote.png" title='<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.update" />'>
												</a>
											</c:if>
											<!-- 流程附言图标 -->
											<c:if test="${lbpmAuditNote.fdIsShowLbpmPostscriptIcon == '1'}">
												<a href="javascript:;" style="float:right;margin-right:10px;" class="com_btn_link" onclick="Com_EventPreventDefault();_addLbpmPostscript('${lbpmAuditNote.fdId}','${formBeanName}','${lbpmAuditNote.fdFactNodeId}');">
													<img alt="" src="${KMSS_Parameter_ContextPath}sys/lbpmservice/support/lbpm_audit_note/css/images/icon-lbpmPostscript.png" title='<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.postscript" />'>
												</a>
											</c:if>
										</span>
									</td>
								</tr>
								<!-- 流程附言 -->
								<tr>
									<td colspan="3" class="lbpmPostscript" style="padding-top:10px;">
										<c:import url="/sys/lbpmservice/support/lbpm_postscript/lbpmPostscript_list.jsp" charEncoding="UTF-8">
									          <c:param name="formBeanName" value="${formBeanName}"/>
									          <c:param name="fdAuditNoteId" value="${lbpmAuditNote.fdId}"/>
									          <c:param name="fdFactNodeId" value="${lbpmAuditNote.fdFactNodeId}" />
									          <c:param name="approveType" value="${param.approveType}" />
								        </c:import>
									</td>
								</tr>
						</c:if>
						<c:if test="${lbpmAuditNote.fdIsHide=='1'}" >
							<tr><td colspan="2"><font style="font-style:italic"><bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdIsHide_1" /></font></td></tr>
						</c:if>
						<c:if test="${lbpmAuditNote.fdIsHide=='3'}" >
							<tr><td colspan="2"><font style="font-style:italic"><bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdIsHide_3" /></font></td></tr>
						</c:if>
						<c:if test="${not empty lbpmAuditNote.auditNoteFrom && !(fn:length(requestScope.lbpmPostscriptMap[lbpmAuditNote.fdId]) > 0 && lbpmAuditNote.fdIsHide=='2')}">
						<tr>
							<td colspan="2" align="right" style="color:#999;">
								<kmss:showText value="${lbpmAuditNote.auditNoteFrom}" />
							</td>
						</tr>
						</c:if>
						</table>
					</c:if>
				</td>
			<%--<c:if test="${requestScope['isPrivileger'] == true }">
				<td>
					<c:out value="${lbpmAuditNote.fdActionName}" />
				</td>
			</c:if>--%>
			</tr>
		</c:forEach>
			<!-- 解决老版本数据没有结束节点，导致审批意见加载时无法显示流程总耗时和排名的问题，现在将总耗时和排名整合直接发送至前端 2017-7-21 王祥 -->
			<c:if test="${processStatus=='30'}">
				<tr name="efficiency">
					<td colspan="5" align="right" style="color:#999;">
								<img src="../../resource/images/efficiency.png">&nbsp;<kmss:showText value="${efficiency}" />
					</td>
				</tr>
			</c:if> 	
	</table>
</html:form>
</body>
</html>