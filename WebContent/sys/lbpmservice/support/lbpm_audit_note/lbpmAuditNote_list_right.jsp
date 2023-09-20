<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting"%>
<%@page import="com.landray.kmss.common.actions.RequestContext"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@page import="com.landray.kmss.sys.lbpmservice.support.service.ILbpmPostscriptService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<link href="${LUI_ContextPath}/sys/lbpmservice/support/lbpm_audit_note/css/lbpmAuditNote.css?s_cache=${LUI_Cache}" rel="stylesheet" type="text/css" />
<script type="text/javascript">
Com_IncludeFile("data.js");
Com_IncludeFile("jquery.js");
seajs.use(['theme!form']);
</script>
<%
	com.landray.kmss.sys.lbpmservice.support.service.spring.LbpmAuditNoteLoader auditNoteLoader = (com.landray.kmss.sys.lbpmservice.support.service.spring.LbpmAuditNoteLoader) SpringBeanUtil
			.getBean("lbpmAuditNoteLoader");
	
	JSONArray auditNotes = auditNoteLoader.notes4mobile(auditNoteLoader
		.notesFilter(auditNoteLoader
				.listNote(new RequestContext(
						request))), new RequestContext(request));
	pageContext.setAttribute("auditNotes",auditNotes);
	LbpmSetting lbpmSetting = new LbpmSetting();
	pageContext.setAttribute("isShowApprovalTime",lbpmSetting.getIsShowApprovalTime());
	
	if (auditNotes != null && auditNotes.size() > 0){
		List<String> auditNoteIds = new ArrayList<String>();
		for (int i = 0; i < auditNotes.size(); i++) {
			JSONObject obj = (JSONObject)auditNotes.get(i);
			auditNoteIds.add((String)obj.get("fdId"));
		}
		ILbpmPostscriptService lbpmPostscriptService = (ILbpmPostscriptService) SpringBeanUtil.getBean("lbpmPostscriptService");
		Map<String,List<?>> postscriptMap = lbpmPostscriptService.findMapByNoteIds(auditNoteIds);
		request.setAttribute("lbpmPostscriptMap",postscriptMap);
	}
	
%>
<script type="text/javascript">
var LbpmAuditNoteList = ${auditNotes};
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
function resetIFrame(){
	setTimeout(function(){
		window.location.href=window.location.href;
		if(window.parent){
			var auditNoteIframe = window.parent.document.getElementById("historyInfoTableIframe");
			if(auditNoteIframe && auditNoteIframe.contentWindow){
				auditNoteIframe.contentWindow.location.reload();
			}
		}
	},0);
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

function initialPage(){
	try {
		var arguObj = document.getElementById("auditNoteTable");
		if(arguObj != null && window.frameElement!=null && window.frameElement.tagName=="IFRAME"){
			var height = arguObj.offsetHeight + 30;
			if(height>0)
				window.frameElement.style.height = height + "px";
		}
		setTimeout(initialPage, 200);
	} catch(e) {
	}
}

$(function(){
	initialPage();
	$(".muiLbpmserviceAuditLabelItem[toggle='true'] .muiLbpmserviceAuditLabelTitle i").click(function(){
		var $item = $(this).closest(".muiLbpmserviceAuditLabelItem[toggle='true']");
		var methodPrex = $item.attr("methodPrex");
		$item.find(".muiLbpmserviceAuditLabelItemTitle").toggleClass("hide",methodPrex=='show');
		$(".muiLbpmserviceAuditBranchItem[fdParentExecutionId='"+$item.attr("fdExecutionId")+"']").trigger('click',{"methodPrex":methodPrex,"target":$item[0]});
		$item.attr("methodPrex",methodPrex=='show' ? 'hide' : 'show');
		if(methodPrex=='show'){
			$item.find(".muiLbpmserviceAuditFill").show();
		}else{
			$item.find(".muiLbpmserviceAuditFill").hide();
		}
	});
	
	$(".muiLbpmserviceAuditBranchItem").click(function(event,data){
		var target = (data && data.target) || event.currentTarget;
		var prex = (data && data.methodPrex) || $(this).attr("methodPrex");
		if(target != this){
			if(prex=='show'){
				$(this).css('height','0').css('display','none');
			}else{
				$(this).css('height','auto').css('display','block');
			}
		}
		$(this).find(".muiLbpmserviceAuditBranchTitle").toggleClass("hide",prex=='show');
		if(prex=='show'){
			$(".muiLbpmserviceAuditLabelItem[fdExecutionId='"+$(this).attr("fdExecutionId")+"']").hide();
			$(".muiLbpmserviceAuditNodeItem[fdExecutionId='"+$(this).attr("fdExecutionId")+"']").hide();
		}else{
			$(".muiLbpmserviceAuditLabelItem[fdExecutionId='"+$(this).attr("fdExecutionId")+"']").show();
			$(".muiLbpmserviceAuditNodeItem[fdExecutionId='"+$(this).attr("fdExecutionId")+"']").show();
		}
		$(this).attr("methodPrex",prex=='show' ? 'hide' : 'show');
	});
});

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

</script>
</head>
<body>
<c:set var="mainForm" value="${requestScope[formBeanName]}" scope="page" />
<div id="auditNoteTable">
	<c:if test="${not empty auditNotes}">
		<c:forEach items="${auditNotes}" var="auditNote" varStatus="vstatus">
			<div class="auditNodeItem">
			<%
				JSONObject auditNote = (JSONObject) pageContext
								.getAttribute("auditNote");
			%>
			<c:if test="${auditNote.fdActionKey=='_concurrent_branch' || auditNote.fdActionKey == 'distributeNote' || auditNote.fdActionKey == 'recoverNote'}">
				<%
					if (auditNote.getBoolean("firstBlank")) {
				%>
				<div class="muiLbpmserviceAuditLabelItem muiLbpmserviceAuditNormal" fdExecutionId="${auditNote.fdParentExecutionId }" methodPrex="show" toggle="true">
					<div class="muiLbpmserviceAuditLabelItemDot">
						<i class=""></i>
					</div>
					<div class="muiLbpmserviceAuditLabelItemTitle muiLbpmserviceAuditLabelTitle">
						<bean:message bundle="sys-lbpmservice" key="mui.lbpmservice.flowchart.process.branch"/>
						<i></i>
					</div>
					<div class="muiLbpmserviceAuditFill" style="display:none;"></div>
				</div>
				<%
					}
				%>
				<div class="muiLbpmserviceAuditBranchItem" style="height: auto; display: block;" fdExecutionId="${auditNote.fdExecutionId }" fdParentExecutionId="${auditNote.fdParentExecutionId }" methodPrex="show">
					<div class="muiLbpmserviceAuditBranchContent">
					<span class="muiLbpmserviceAuditBranchTitle">
						${auditNote.fdFactNodeName}
						<i></i>
					</span>
					</div>
				</div>
			</c:if>
			<c:if test="${auditNote.fdActionKey!='_concurrent_branch' && auditNote.fdActionKey != 'distributeNote' && auditNote.fdActionKey != 'recoverNote'}">
				<%
					if (auditNote.getBoolean("firstNode")) {
				%>
					<div class="muiLbpmserviceAuditLabelItem muiLbpmserviceAuditNormal" fdExecutionId="${auditNote.fdExecutionId }" fdParentExecutionId="${auditNote.fdParentExecutionId }">
						<div class="muiLbpmserviceAuditLabelItemDot">
							<i class=""></i>
						</div>
						<div class="muiLbpmserviceAuditLabelItemTitle">
							<span class="muiLbpmserviceAuditLabelTitle">${auditNote.fdHandlerName}(${auditNote.fdFactNodeName})</span>
							<span class="muiLbpmserviceAuditCreateTime">${auditNote.fdCreateTime}</span>
						</div>
					</div>
				<%
					}else{
				%>
					<div class="muiLbpmserviceAuditLabelItem muiLbpmserviceAuditNormal" fdExecutionId="${auditNote.fdExecutionId }" fdParentExecutionId="${auditNote.fdParentExecutionId }">
						<div class="muiLbpmserviceAuditLabelItemTitle">
							<span class="muiLbpmserviceAuditLabelTitle">${auditNote.fdHandlerName}(${auditNote.fdFactNodeName})</span>
							<span class="muiLbpmserviceAuditCreateTime">${auditNote.fdCreateTime}</span>
						</div>
					</div>
				<%
					} 
				%>
				<div class="muiLbpmserviceAuditNodeItem muiLbpmserviceAuditNodeItemBorder" fdExecutionId="${auditNote.fdExecutionId }" fdParentExecutionId="${auditNote.fdParentExecutionId }">
					<div class="muiLbpmserviceAuditNodeItemContainer">
						<div class="muiLbpmserviceAuditNodeContent">
							<span class="muiLbpmserviceAuditNodeNode">
							<c:if test="${auditNote.fdActionKey != 'subFlowNode_start'}" >
								<c:if test="${auditNote.fdIsHide=='1'}" >
									<font style="font-style:italic"><bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdIsHide_1" /></font>
								</c:if>
								<c:if test="${auditNote.fdIsHide=='3'}" >
									<font style="font-style:italic"><bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdIsHide_3" /></font>
								</c:if>
								<c:if test="${auditNote.fdIsHide=='2' && auditNote.fdActionKey != 'subFlowNode_start'}" >
									${auditNote.fdAuditNote}
								</c:if>
							</c:if>
							</span>
						</div>
						<c:if test="${auditNote.fdIsHide eq '2'}">
							<c:forEach items="${auditNote.auditNoteListsJsps4Pc}" var="auditNoteListsJsp" varStatus="vstatus">
								<c:import url="${auditNoteListsJsp}" charEncoding="UTF-8">
									<c:param name="auditNoteFdId" value="${auditNote.fdId}" />
									<c:param name="modelName" value="${param.fdModelName}" />
									<c:param name="modelId" value="${param.fdModelId}" />
									<c:param name="formName" value="${formBeanName}"/>
									<c:param name="curHanderId" value="${auditNote.fdHandlerId}"/>
								</c:import>
							</c:forEach>
							<c:if test="${not empty mainForm.attachmentForms[auditNote.fdId] and not empty mainForm.attachmentForms[auditNote.fdId].attachments}">
								<c:import url="/sys/attachment/sys_att_main/sysAttMain_view.jsp" charEncoding="UTF-8">
						          <c:param name="formBeanName" value="${formBeanName}"/>
						          <c:param name="fdKey" value="${auditNote.fdId}"/>
						          <c:param name="fdModelId" value="${param.fdModelId}"/>
						          <c:param name="fdModelName" value="${param.fdModelName}"/>
						          <c:param name="fdViewType" value="simple" />
						          <c:param name="fdForceDisabledOpt" value="edit" />
						          <c:param name="isShowDownloadCount" value="false" />
						        </c:import>
							</c:if>
							<!-- 流程附言 -->
							<div>
								<c:import url="/sys/lbpmservice/support/lbpm_postscript/lbpmPostscript_list_right.jsp" charEncoding="UTF-8">
							          <c:param name="formBeanName" value="${formBeanName}"/>
							          <c:param name="fdAuditNoteId" value="${auditNote.fdId}"/>
							          <c:param name="fdFactNodeId" value="${auditNote.fdFactNodeId}" />
						        </c:import>
					        </div>
						</c:if>
						<div class="muiLbpmserviceAuditNodeInfo">
							<c:if test="${auditNote.fdIsHide=='2' && isShowApprovalTime eq 'true' && auditNote.fdCostTimeDisplayString != ''}">
								<span><bean:message bundle="sys-lbpmservice" key="lbpmProcess.auditNote.time" />${auditNote.fdCostTimeDisplayString}</span>
							</c:if>
							<c:if test="${auditNote.fdActionKey != 'subFlowNode_start'}" >
								<span>${auditNote.fdActionInfo}</span>
							</c:if>
							<c:if test="${auditNote.fdIsHide=='2' && auditNote.fdActionKey != 'subFlowNode_start'}">
								<!-- 流程附言图标 -->
								<c:if test="${auditNote.fdIsShowLbpmPostscriptIcon == '1'}">
									<a href="javascript:;" style="" class="com_btn_link" onclick="Com_EventPreventDefault();_addLbpmPostscript('${auditNote.fdId}','${formBeanName}','${auditNote.fdNoteId}');">
										<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.postscript" />
									</a>
								</c:if>
								<!-- 修改审批记录图标 -->
								<c:if test="${auditNote.fdIsShowModifyIcon=='1'}">
									<a href="javascript:void(0);" class="com_btn_link" onclick="Com_EventPreventDefault();_modifyAuditNote('${auditNote.fdId}','${formBeanName}');">
										<bean:message key="button.edit" />
									</a>
								</c:if>
								<!-- 查看历史记录图标 -->
								<c:if test="${auditNote.fdIsShowViewHistoryIcon=='1'}">
									<a href="javascript:void(0);" class="com_btn_link" onclick="Com_EventPreventDefault();_viewHistoryAuditNote('${auditNote.fdId}','${formBeanName}');">
										<bean:message bundle="sys-lbpmservice" key="lbpmProcess.auditNote.history" />
									</a>
								</c:if>
							</c:if>
						</div>
					</div>
				</div>
			</c:if>
			</div>
		</c:forEach>
		<div class="auditNodeNext">
			<div class="muiLbpmserviceAuditNextNodeItem muiLbpmserviceAuditLabelItem muiLbpmserviceAuditNormal">
				<div class="muiLbpmserviceAuditLabelItemDot">
					<i class=""></i>
				</div>
				<div class="muiLbpmserviceAuditLabelItemTitle">
					<span class="muiLbpmserviceAuditLabelTitle">
						<kmss:showWfPropertyValues idValue="${param.fdModelId}" propertyName="handerNameDetail" extend="hidePersonal" />
					</span>
				</div>
			</div>
			<div class="muiLbpmserviceAuditNextNodeItem muiLbpmserviceAuditNodeItem muiLbpmserviceAuditNodeItemBorder">
				<div class="muiLbpmserviceAuditNodeItemContainer">
					<div class="muiLbpmserviceAuditNodeContent">
						<span class="muiLbpmserviceAuditNodeNode">
							
						</span>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${empty auditNotes}">
		<div class="lui_prompt_container lui_prompt_vertical">
            <div class="lui_prompt_frame">
                <div class="lui_prompt_container">
                    <div class="lui_prompt_content_error lui_prompt_content_noData"></div>
                    <div class="lui_prompt_content_right">
                        <div class="lui_msgtitle"><bean:message bundle="sys-lbpmservice" key="lbpmProcess.right.msgtitle" /></div>
                        <p class="lui_prompt_content_timer"><bean:message bundle="sys-lbpmservice" key="lbpmProcess.right.timer" /></p>
                        <div class="lui_msgcontent">
                            <span><bean:message bundle="sys-lbpmservice" key="lbpmProcess.right.reason" /></span>
                            <div class="lui_msgtip">
                                <ul>
                                    <li><bean:message bundle="sys-lbpmservice" key="lbpmProcess.right.noPower" /></li>
                                    <li><bean:message bundle="sys-lbpmservice" key="lbpmProcess.right.noRecord" /></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
	</c:if>
</div>
</body>
</html>