<%@page import="com.landray.kmss.sys.lbpmservice.support.model.LbpmSetting"%>
<%@page import="com.landray.kmss.common.actions.RequestContext"%>
<%@page import="com.landray.kmss.common.model.IBaseModel"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="net.sf.json.JSONArray" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="com.landray.kmss.util.SpringBeanUtil" %>
<%@page import="com.landray.kmss.sys.mobile.util.MobileUtil" %>
<%@page import="com.landray.kmss.sys.lbpmservice.support.service.ILbpmPostscriptService" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ taglib uri="/WEB-INF/KmssConfig/sys/person/person.tld" prefix="person"%>
<!doctype html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit" />
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<link href="${LUI_ContextPath}/third/ding/third_ding_xform/resource/css/record.css?s_cache=${LUI_Cache}" rel="stylesheet" type="text/css" />
<link href="${LUI_ContextPath}/third/ding/third_ding_xform/resource/css/ding.css?s_cache=${LUI_Cache}" rel="stylesheet" type="text/css" />
<script type="text/javascript">
Com_IncludeFile("data.js");
Com_IncludeFile("jquery.js");
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
	ILbpmPostscriptService lbpmPostscriptService = (ILbpmPostscriptService) SpringBeanUtil.getBean("lbpmPostscriptService");
	if (auditNotes != null && auditNotes.size() > 0){
		List<String> auditNoteIds = new ArrayList<String>();
		for (int i = 0; i < auditNotes.size(); i++) {
			JSONObject obj = (JSONObject)auditNotes.get(i);
			auditNoteIds.add((String)obj.get("fdId"));
		}
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
	 var url = "sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote.do?method=findNoteByFdId&fdId=" + fdId + "&formBeanName=" + formBeanName
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
	var width = 600,height = 450;
	if (typeof seajs !== "undefined") {
		url =  "/" + url ;
		seajs.use(['lui/dialog',],function(dialog) {
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
	var url = "sys/lbpmservice/support/lbpm_audit_note/lbpmAuditNote.do?method=viewHistory&fdId=" + fdId + "&formBeanName=" + formBeanName ;
	var width = 900,height = 600;
	if (typeof seajs !== "undefined") {
		url =  "/" + url ;
		seajs.use(['lui/dialog',],function(dialog) {
			dialog.iframe(url," ",null,{"width" : width,"height" : height + 50}
			);
		});
	} else {
		url =  Com_Parameter.ContextPath + url;
		_popupWindow(url,width,height);
	}
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

function expandNodeAudit(src){
	var $auditNode = $(src).closest(".auditNodeItem");
	$auditNode.removeClass("collapse");
	if (!$auditNode.hasClass("expand")) {
		$auditNode.addClass("expand");
	}
	$auditNode.find("[name='collapseBtn']").show();
	$(src).hide();
}

function collapseNodeAudit(src){
	var $auditNode = $(src).closest(".auditNodeItem");
	$auditNode.removeClass("expand");
	if (!$auditNode.hasClass("collapse")) {
		$auditNode.addClass("collapse");
	}
	$auditNode.find("[name='expandBtn']").show();
	$(src).hide();
}
function onclickLablee(add,remove){
	var _lablee_add="#"+add+"_lablee";
	var _lablee_remove="#"+remove+"_lablee";
	var _content_add="#"+add+"_content";
	var _content_remove="#"+remove+"_content";
	$(_content_add).css('display','block');
	$(_content_remove).css('display','none');
	$(_lablee_add).children(".parentAndSubs_lablee_div_underline").addClass("parentAndSubs_lablee_div_underline_active");
	$(_lablee_remove).children(".parentAndSubs_lablee_div_underline").removeClass("parentAndSubs_lablee_div_underline_active");
}
function changeArrow(blockId,noneId){
	if(blockId=="#top_arrow"){//展开内容
		$("#parentAndSubs_content").css('display','block');
	}else{
		$("#parentAndSubs_content").css('display','none');
	}
	$(blockId).css('display','inline-block');
	$(noneId).css('display','none');
}
$(document).ready(function(){
	// 如果子标签有内容但是父标签没有则只显示子标签
	var subs = $("#subs_content table").length>0 ;
	var parent = $("#parent_content table").length>0 ;
    if(parent && !subs){
    	$("#parent_lablee").click();
    	$("#subs_lablee").remove();//移除子标签
    	$("#subs_content").remove();//移除子标签内容
    }else if(subs && !parent){
    	$("#subs_lablee").click();
    	$("#parent_lablee").remove();//移除父标签
    	$("#parent_content").remove();//移除父标签内容
    }
});

</script>
</head>
<body>
<c:set var="mainForm" value="${requestScope[formBeanName]}" scope="page" />
<div id="auditNoteTable" style="max-width:100%;overflow-y:scroll;">
	<c:if test="${not empty auditNotes}">
		<c:forEach items="${auditNotes}" var="auditNote" varStatus="vstatus">
			<c:if test="${'true' eq auditNote.showHead }">
				<div class="auditNodeItem<c:if test="${'30' eq auditNote.pass }"> pass</c:if><c:if test="${'31' eq auditNote.pass }"> reject</c:if>">
					<div class="muiLbpmserviceAuditLabelItem muiLbpmserviceAuditNormal">
						<div class="muiLbpmserviceAuditLabelItemDot muiLbpmProcessSystem<c:if test="${'2' eq auditNote.fdProcessType }"> muiLbpmProcessJoint</c:if><c:if test="${'1' eq auditNote.fdProcessType }"> muiLbpmProcessParallel</c:if>">
							<i class=""></i>
						</div>
						<div class="muiLbpmserviceAuditLabelItemHead">
							<c:if test="${'2' eq auditNote.fdProcessType }">
								<div>${auditNote.fdFactNodeName}(会审)</div>
							</c:if>
							<c:if test="${'1' eq auditNote.fdProcessType }">
								<div>${auditNote.fdFactNodeName}(并行)</div>
							</c:if>
							<div class="muiLbpmserviceAuditBtns">
								<span class="muiLbpmserviceAuditExpandBtn" name="expandBtn" onclick="expandNodeAudit(this);">
									展开
								</span>
								<span class="muiLbpmserviceAuditCollapseBtn" name="collapseBtn"  onclick="collapseNodeAudit(this);">
									收起
								</span>
							</div>
						</div>
						<c:if test="${'2' eq auditNote.fdProcessType }">
							<div class="muiLbpmserviceAuditLabelNodeInfo">
								<span>需${auditNote.handlerSize }人全部审批通过</span>
								<c:if test="${'30' eq auditNote.pass }">
									<span>(已通过)</span>
								</c:if>
								<c:if test="${'20' eq auditNote.pass }">
									<span>(审批中)</span>
								</c:if>
								<c:if test="${'31' eq auditNote.pass }">
									<span>(已驳回)</span>
								</c:if>
							</div>
						</c:if>
						<c:if test="${'1' eq auditNote.fdProcessType }">
							<div class="muiLbpmserviceAuditLabelNodeInfo">
								<span>${auditNote.handlerSize }人任意1人审批通过即可</span>
								<c:if test="${'30' eq auditNote.pass }">
									<span>(已通过)</span>
								</c:if>
								<c:if test="${'20' eq auditNote.pass }">
									<span>(审批中)</span>
								</c:if>
								<c:if test="${'31' eq auditNote.pass }">
									<span>(已驳回)</span>
								</c:if>
							</div>
						</c:if>
						<div class="muiLbpmserviceAuditNoteHandler">
							<c:if test="${not empty auditNote.historyHandlers}">
								<c:forEach items="${auditNote.historyHandlers}" var="historyHandler" varStatus="vstatus">
									<span class="muiLbpmserviceNodeHander history">
										<person:dingHeadimage size="s" personId='${historyHandler.fdId}' contextPath='true'/>
										<label>${historyHandler.fdName }</label>
									</span>
								</c:forEach>
							</c:if>
							<c:if test="${not empty auditNote.currentHandlers}">
								<c:forEach items="${auditNote.currentHandlers}" var="currentHandler" varStatus="vstatus">
									<span class="muiLbpmserviceNodeHander current">
										<person:dingHeadimage size="s" personId='${currentHandler.fdId}' contextPath='true'/>
										<label>${currentHandler.fdName }</label>
									</span>
								</c:forEach>
							</c:if>
						</div>
					</div>
			</c:if>
				
			<c:if test="${'true' eq auditNote.hasOuterBorder }">
				<div class="auditNodeItemWrap">
			</c:if>
		
			<div class="auditNodeItem ${auditNote.status}<c:if test="${auditNote.fdActionKey=='_pocess_end' }"> auditLastNodeItem</c:if>">
			<%
				JSONObject auditNote = (JSONObject) pageContext
								.getAttribute("auditNote");
			%>
			<c:if test="${auditNote.fdActionKey=='_concurrent_branch' }">
				<%
					if (auditNote.getBoolean("firstBlank")) {
				%>
				<div class="muiLbpmserviceAuditLabelItem muiLbpmserviceAuditNormal" fdExecutionId="${auditNote.fdParentExecutionId }" methodPrex="show" toggle="true">
					<div class="muiLbpmserviceAuditLabelItemDot">
						<i class="">
						</i>
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
			<c:if test="${auditNote.fdActionKey!='_concurrent_branch' }">
				<div class="muiLbpmserviceAuditLabelItem muiLbpmserviceAuditNormal" fdExecutionId="${auditNote.fdExecutionId }" fdParentExecutionId="${auditNote.fdParentExecutionId }">
					<div class="muiLbpmserviceAuditLabelItemTitle">
						<c:choose>
							<c:when test="${empty auditNote.fdHandlerImage}">
								<div class="muiLbpmserviceAuditLabelItemDot muiLbpmProcessSystem">
									<i class=""></i>
								</div>
							</c:when>
							<c:otherwise>
								<div class="muiLbpmserviceAuditLabelItemDot">
									<person:dingHeadimage size="s" personId='${auditNote.fdHandlerId}' contextPath='true'/>
								</div>
							</c:otherwise>
						</c:choose>
						<span class="muiLbpmserviceAuditLabelTitle">
							<c:choose>
							<c:when test="${'true' eq auditNote.hasOuterBorder }">
								${auditNote.fdHandlerName}
							</c:when>
							<c:otherwise>
								${auditNote.fdFactNodeName}(${auditNote.fdHandlerName})
							</c:otherwise>
							</c:choose>
						</span>
						<span class="muiLbpmserviceAuditCreateTime">${auditNote.fdCreateTime}</span>
					</div>
				</div>
				<div class="muiLbpmserviceAuditNodeItem muiLbpmserviceAuditNodeItemBorder" fdExecutionId="${auditNote.fdExecutionId }" fdParentExecutionId="${auditNote.fdParentExecutionId }">
					<div class="muiLbpmserviceAuditNodeItemContainer">
						<div class="muiLbpmserviceAuditNodeContent">
							<span class="muiLbpmserviceAuditNodeNode">
								<c:if test="${auditNote.fdIsHide=='1'}" >
									<font class="auditNote_fdIsHide_font1" style="font-style:italic"><bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdIsHide_1" /></font>
								</c:if>
								<c:if test="${auditNote.fdIsHide=='3'}" >
									<font class="auditNote_fdIsHide_font3" style="font-style:italic"><bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.fdIsHide_3" /></font>
								</c:if>
								<c:if test="${auditNote.fdIsHide=='2' && auditNote.fdActionKey != 'subFlowNode_start'}" >
									<c:if test="${not empty auditNote.fdAuditNote}">
										"${auditNote.fdAuditNote}"
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
							<div class="muiLbpmserviceAuditNodeInfo">
								<span>${auditNote.fdActionInfo}</span>
								<c:if test="${auditNote.fdIsHide=='2' && isShowApprovalTime eq 'true' && auditNote.fdCostTimeDisplayString != ''}">
									<span><bean:message bundle="sys-lbpmservice" key="lbpmProcess.auditNote.time" />${auditNote.fdCostTimeDisplayString}</span>
								</c:if>
								<c:if test="${auditNote.fdIsHide=='2' && auditNote.fdActionKey != 'subFlowNode_start'}">
									<!-- 流程附言图标 -->
									<c:if test="${auditNote.fdIsShowLbpmPostscriptIcon == '1'}">
										<a href="javascript:;" style="float:right" title="<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.postscript" />" class="postscript" onclick="Com_EventPreventDefault();_addLbpmPostscript('${auditNote.fdId}','${formBeanName}','${auditNote.fdNoteId}');">
											<bean:message bundle="sys-lbpmservice-support" key="lbpmAuditNote.list.postscript" />
										</a>
									</c:if>
									<!-- 修改审批记录图标 -->
									<c:if test="${auditNote.fdIsShowModifyIcon=='1'}">
										<a href="javascript:void(0);" style="float:right" class="auditNote_edit" title="<bean:message key="button.edit" />" onclick="Com_EventPreventDefault();_modifyAuditNote('${auditNote.fdId}','${formBeanName}');">
											<bean:message key="button.edit" />
										</a>
									</c:if>
									<!-- 查看历史记录图标 -->
									<c:if test="${auditNote.fdIsShowViewHistoryIcon=='1'}">
										<a href="javascript:void(0);" style="float:right" class="auditNote_view" title="查看历史记录" onclick="Com_EventPreventDefault();_viewHistoryAuditNote('${auditNote.fdId}','${formBeanName}');">
											<bean:message bundle="sys-lbpmservice" key="lbpmProcess.auditNote.history" />
										</a>
									</c:if>
								</c:if>
							</div>
							<!-- 流程附言 -->
							<div>
								<c:import url="/sys/lbpmservice/support/lbpm_postscript/dingSuit/lbpmPostscript_list.jsp" charEncoding="UTF-8">
							          <c:param name="formBeanName" value="${formBeanName}"/>
							          <c:param name="fdAuditNoteId" value="${auditNote.fdId}"/>
							          <c:param name="fdFactNodeId" value="${auditNote.fdFactNodeId}" />
						        </c:import>
					        </div>
						</c:if>
					</div>
				</div>
			</c:if>
			</div>
			<c:if test="${'true' eq auditNote.hasOuterBorder }">
				</div>
			</c:if>
			<c:if test="${'true' eq auditNote.showHeadEnd }">
				<c:if test="${'true' eq auditNote.showHeadEnd }">
					<c:if test="${'30' ne auditNote.nodeStatus }">
						<div class="auditNodeItemWrap">
							<div class="auditNodeItem auditNodeNext">
								<div class="muiLbpmserviceAuditNextNodeItem muiLbpmserviceAuditLabelItem muiLbpmserviceAuditNormal">
									<div class="muiLbpmserviceAuditLabelItemDot">
										<kmss:showWfPropertyValues idValue="${param.fdModelId}" var="fdCurrentHandlerId" propertyName="handlerId" extend="hidePersonal" />
										<person:dingHeadimage size="s" personId='${fdCurrentHandlerId}' contextPath='true'/>
									</div>
									<div class="muiLbpmserviceAuditLabelItemTitle">
										<span class="muiLbpmserviceAuditLabelTitle">
											<kmss:showWfPropertyValues idValue="${param.fdModelId}" propertyName="handlerName" extend="hidePersonal" />
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
						</div>
					</c:if>
				</c:if>
				</div>
			</c:if>
		</c:forEach>
		<c:if test="${param.docStatus=='20' || param.docStatus=='10' || param.docStatus=='11'}">
			<c:if test='${"true" eq showOuterNext}'>
			<div class="auditNodeItem auditNodeNext">
				<div class="muiLbpmserviceAuditNextNodeItem muiLbpmserviceAuditLabelItem muiLbpmserviceAuditNormal">
					<div class="muiLbpmserviceAuditLabelItemDot">
						<kmss:showWfPropertyValues idValue="${param.fdModelId}" var="fdCurrentHandlerId" propertyName="handlerId" extend="hidePersonal" />
						<person:dingHeadimage size="s" personId='${fdCurrentHandlerId}' contextPath='true'/>
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
		</c:if>
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
	<c:if test='<%="true".equals(SysFormDingUtil.getEnableDing()) || "true".equals(request.getParameter("ddpage"))%>'>
 <xform:isExistRelationProcesses relationType="all">
 <div style="padding-left:23px;">
 <div style="padding-right:30px;">
   <div class="parentAndSubs_all" style="width:100%; " id="parentAndSubs_all">
      <div class="parentAndSubs_lablee" style="height:47px;" id="parentAndSubs_lablee">
          <div onclick="onclickLablee('parent','subs')" class="parentAndSubs_lablee_div" id="parent_lablee">
                                         父标签
              <div class="parentAndSubs_lablee_div_underline parentAndSubs_lablee_div_underline_active"></div>   
          </div>
          <div onclick="onclickLablee('subs','parent')" class="parentAndSubs_lablee_div" id="subs_lablee">
                                        子标签
              <div class="parentAndSubs_lablee_div_underline"></div>
          </div>
          <div  style="float:right" class="parentAndSubs_lablee_div" id="subs_lablee">
                 <img id="top_arrow" onclick="changeArrow('#buttom_arrow','#top_arrow')" style="display:none;width: 20px;height: 20px;" src="${KMSS_Parameter_ContextPath}sys/lbpmservice/common/image/top_arrow.png" />                      
                 <img id="buttom_arrow" onclick="changeArrow('#top_arrow','#buttom_arrow')" style="display:inline-block;width: 20px;height: 20px;" src="${KMSS_Parameter_ContextPath}sys/lbpmservice/common/image/buttom_arrow.png" />                      
          </div>
      </div>
      <div id="parentAndSubs_content" class="parentAndSubs_content" style="display:none;">
           <div id="parent_content">
		     <xform:isExistRelationProcesses relationType="parent">
		    	<xform:showParentProcesse /> 
		     </xform:isExistRelationProcesses>          
           </div>
            <div id="subs_content" style="display:none;">
		      <xform:isExistRelationProcesses relationType="subs">
			    <xform:showSubProcesses />
		      </xform:isExistRelationProcesses>
		   </div>
      </div>
   </div>
</div>   
</div>
</xform:isExistRelationProcesses>  
</c:if> 
</div>
</body>
</html>