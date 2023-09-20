<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.landray.kmss.util.ResourceUtil"%>
<%@page import="com.landray.kmss.util.DateUtil"%>
<%@page import="java.util.Date"%>
<%@ page import="java.util.List"%>
<%@page import="com.landray.kmss.util.SpringBeanUtil,com.landray.kmss.util.StringUtil"%>
<%@page import="com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService"%>
<template:include ref="default.print" sidebar="no">
<template:replace name="toolbar">
	<ui:toolbar  id="toolbar" layout="sys.ui.toolbar.float" count="8"> 
			<ui:button  id="zoomIn" text="${ lfn:message('km-imeeting:button.zoom.in') }"   onclick="ZoomFontSize(2);">
		    </ui:button>
		     <ui:button id="zoomOut" text="${ lfn:message('km-imeeting:button.zoom.out') }"   onclick="ZoomFontSize(-2);">
	 	   	</ui:button>
	  		 <ui:button text="${ lfn:message('button.print') }"   onclick="printorder();">
		    </ui:button>
		    <c:import url="/sys/common/exportButton.jsp" charEncoding="UTF-8"></c:import>
		      <ui:button text="${ lfn:message('button.close') }"   onclick="window.close();">
		    </ui:button>
	</ui:toolbar>
</template:replace>
<template:replace name="content">
<script language="JavaScript">
seajs.use(['theme!form']);
function outputMHT() {
	seajs.use(['lui/export/export'],function(exp) {
		exp.exportMht({
			title:'${kmImeetingSummaryForm.fdName}'
		});
	});
}
function outputPDF() {
	seajs.use(['lui/jquery','lui/export/export'],function($,exp) {
		var pdfHead = $("<tr id='pdfHead'/>").css({"padding-bottom":"10px"});
		pdfHead.append($("<td colspan='4' />").html($(".print_title_header").html()));
		$("#Table_Main tr:first").before(pdfHead);
		exp.exportPdf($("#printTable")[0],{
			title: '${kmImeetingSummaryForm.fdName}',
			callback: function() {
				$("#pdfHead").remove();
			}
		});
	});
}
</script>
<c:set var="p_defconfig" value="${p_defconfig}" scope="request"/>
<script>
Com_IncludeFile("jquery.js|dialog.js|doclist.js");
</script>
<script>
seajs.use(['lui/dialog','lui/jquery','km/imeeting/resource/js/dateUtil'],function(dialog,$,dateUtil){
	//初始化会议历时
	if('${kmImeetingSummaryForm.fdHoldDuration}'){
		//将小时分解成时分
		var timeObj=dateUtil.splitTime({"ms":"${kmImeetingSummaryForm.fdHoldDuration}"});
		$('#fdHoldDurationHour').html(timeObj.hour);
		$('#fdHoldDurationMin').html(timeObj.minute);
		if(timeObj.minute){
			$('#fdHoldDurationMinSpan').show();
		}else{
			$('#fdHoldDurationMinSpan').hide();
		}
	}
});
function AbsPosition(node, stopNode) {
	var x = y = 0;
	for (var pNode = node; pNode != null && pNode !== stopNode; pNode = pNode.offsetParent) {
		x += pNode.offsetLeft - pNode.scrollLeft; y += pNode.offsetTop - pNode.scrollTop;
	}
	x = x + document.body.scrollLeft;
	y = y + document.body.scrollTop;
	return {'x':x, 'y':y};
};
function MousePosition(event) {
	if(event.pageX || event.pageY) return {x:event.pageX, y:event.pageY};
	return {
		x:event.clientX + document.body.scrollLeft - document.body.clientLeft,
		y:event.clientY + document.body.scrollTop  - document.body.clientTop
	};
};
function SetPageBreakLine(tr) {
	var pos = AbsPosition(tr);
	var line = document.createElement('div');
	line.className = 'page_line';
	line.style.top = pos.y + 'px';
	line.style.left = '0px';
	line.id = 'line_' + tr.uniqueID;
	document.body.appendChild(line);
}
function RemovePageBreakLine(tr) {
	var line = document.getElementById('line_' + tr.uniqueID);
	if (line != null)
		document.body.removeChild(line);
	line = null;
}
var flag = 0;
function ZoomFontSize(size) {
	//当字体缩小到一定程度时，缩小字体按钮变灰不可点击
	if(flag>=0||flag==-2){
		flag = flag+size;
	}
	if(flag<0){
		$("#zoomOut").prop("disabled",true);
		$("#zoomOut").addClass("status_disabled");
	}else{
		$("#zoomOut").prop("disabled",false);
		$("#zoomOut").removeClass("status_disabled");
	} 
	var root = document.getElementById("printTable");
	var i = 0;
	for (i = 0; i < root.childNodes.length; i++) {
		SetZoomFontSize(root.childNodes[i], size);
	}
	var tag_fontsize;
	if(root.currentStyle){
	    tag_fontsize = root.currentStyle.fontSize;  
	}  
	else{  
	    tag_fontsize = getComputedStyle(root, null).fontSize;  
	} 
	root.style.fontSize = parseInt(tag_fontsize) + size + 'px';
}
function SetZoomFontSize(dom, size) {
	if (dom.nodeType == 1 && dom.tagName != 'OBJECT' && dom.tagName != 'SCRIPT' && dom.tagName != 'STYLE' && dom.tagName != 'HTML') {
		for (var i = 0; i < dom.childNodes.length; i ++) {
			SetZoomFontSize(dom.childNodes[i], size);
		}
		var tag_fontsize;
		if(dom.currentStyle){  
		    tag_fontsize = dom.currentStyle.fontSize;  
		}  
		else{  
		    tag_fontsize = getComputedStyle(dom, null).fontSize;  
		} 
		dom.style.fontSize = parseInt(tag_fontsize) + size + 'px';
	}
}
function ClearDomWidth(dom) {
	if (dom != null && dom.nodeType == 1 && dom.tagName != 'OBJECT' && dom.tagName != 'SCRIPT' && dom.tagName != 'STYLE' && dom.tagName != 'HTML') {
		//修改打印布局为 百分比布局 #曹映辉 2014.8.7
			/****
			var w = dom.getAttribute("width");
			if (w != '100%')
				dom.removeAttribute("width");
			w = dom.style.width;
			if (w != '100%')
				dom.style.removeAttribute("width");
			****/
			if (dom.style.whiteSpace == 'nowrap') {
				dom.style.whiteSpace = 'normal';
			}
			if (dom.style.display == 'inline') {
				dom.style.wordBreak  = 'break-all';
				dom.style.wordWrap  = 'break-word';
			}
		ClearDomsWidth(dom);
	}
}
function ClearDomsWidth(root) {
	for (var i = 0; i < root.childNodes.length; i ++) {
		ClearDomWidth(root.childNodes[i]);
	}
}
function printView() {
	try {
		//PageSetup_temp();
		//PageSetup_Null();
		document.getElementById('WebBrowser').ExecWB(7,1);
	    //PageSetup_Default();
	} catch (e) {
		alert("<bean:message key="button.printPreview.error" bundle="km-imeeting"/>");
	}
}
function expandXformTab(){
	var xformArea = $("#_xform_detail");
	if(xformArea.length>0){
		var tabs = $("#_xform_detail table.tb_label");
		if(tabs.length>0){
			for(var i=0; i<tabs.length; i++){
				var id = $(tabs[i]).prop("id");
				if(id==null || id=='') continue;
				$(tabs[i]).toggleClass("tb_normal");
				tabs[i].deleteRow(0);
				var tmpFun = function(idx,trObj){
					var trObj = $(trObj);
					//trObj.children("td").css({"padding":"0px","margin":"0px"});
					var tmpTitleTr = $("<tr class='tr_normal_title'>");
					var tempTd = $('<td align="left">');
					tempTd.html(trObj.attr("LKS_LabelName"));
					tempTd.appendTo(tmpTitleTr);
					trObj.before(tmpTitleTr);
				};
				var trArr = $("#"+id+" >tbody > tr[LKS_LabelName]");
				if(trArr.length<1){
					trArr = $("#"+id+" > tr[LKS_LabelName]");
				}
				trArr.each(tmpFun).show();
			}
		}
	}
}
Com_AddEventListener(window, "load", function() {
	ClearDomWidth(document.getElementById("info_content"));
	expandXformTab();
	//清除链接样式
	$('#_xform_detail a').css('text-decoration','none');
	$('a[id^=thirdCtripXformPlane_]').removeAttr('onclick');
	$('a[id^=thirdCtripXformHotel_]').removeAttr('onclick');
});
</script>
<SCRIPT language=javascript>  
var head,foot,top,bottom,left,right;  
  
  
//设置网页打印的页眉页脚和页边距  
function printorder()  
{  
	try {
        window.print();
	} catch (e) {
		alert("<bean:message key="button.printPreview.error" bundle="km-imeeting"/>");
	}
}  
</script>
<style type="text/css">
	#title {
		font-size: 22px;
		color: #000;
	}
	.tr_label_title{
		margin: 28px 0px 10px 0px;
		border-left: 3px solid #46b1fc
	}
	
	.tr_label_title .title{
		font-weight: 900;
		font-size: 16px;
		color: #000;
		text-align:left;
		margin-left: 8px;
	}
	.page_line {
		background-color: red;
		height: 1px;
		border: none;
		width: 100%;
		position: absolute;
		overflow: hidden;
	}
	a:hover{color:#333;text-decoration: none;}
	#printTable{width:980px;margin-bottom:20px;}
	
	/*--- 打印页面带二维码的标题 ---*/
	.print_title_header{ padding-bottom: 10px;border-bottom: 1px solid #dbdbdb;}
	.print_txttitle,.print_txttitle#title{ font-size: 18px; color:#333; padding:8px 0; text-align: center;}
	.printDate{ text-align: right; color:#808080;}
	
     
@media print {
	.new_page {
		page-break-before : always;
	}
	.page_line {
		display: none;
	}
	
	#printTable .tb_noborder,
	#printTable table .tb_noborder,
	#printTable .tb_noborder td {
		border: none;
	}
	#printTable .tr_label_title {
		/*font-weight: 900;*/
	}
	#printTable{width:100%;margin-bottom:0px;}
	
	/*- 打印头部 标题 -*/
	.print_title_header{border-bottom: 1px solid #000}
	.print_txttitle,.print_txttitle#title{ font-size: 20px; font-weight: normal; color:#000;}
	.printDate{color:#000;}
}
</style>
<!-- <OBJECT ID='WebBrowser' NAME="WebBrowser" WIDTH=0 HEIGHT=0 CLASSID='CLSID:8856F961-340A-11D0-A96B-00C04FD705A2'></OBJECT> -->

<html:form action="/km/imeeting/km_imeeting_summary/kmImeetingSummary.do">
<center>
<div class="print_title_header">
<p id="title" class="print_txttitle"><bean:message bundle="km-imeeting" key="table.kmImeetingSummary"/></p>
<div class="printDate">
  <bean:message bundle="km-imeeting" key="KmImeetingSummary.fdPrintDate"/>:<% out.print(DateUtil.convertDateToString(new Date(), DateUtil.TYPE_DATE, request.getLocale()));%>
</div>
</div>
<div id="printTable" style="border: none;">
<div printTr="true" style="border: none;">

<%-- 基本信息 width="650px" --%>
<div>
	<table class="tb_normal" width=100% id="Table_Main">
		<tr>
			<%-- 会议名称--%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdName"/>
			</td>
			<td width="35%">
				<xform:text property="fdName" style="width:80%" />
			</td>
			<%-- 会议类型--%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdTemplate"/>
			</td>
			<td width="35%">
				<c:out value="${kmImeetingSummaryForm.fdTemplateName}"></c:out>
			</td>
		</tr>
		<tr>
			<%-- 主持人--%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdHost"/>
			</td>
			<td width="35%">
				<c:out value="${kmImeetingSummaryForm.fdHostName} ${kmImeetingSummaryForm.fdOtherHostPerson}"></c:out>
			</td>
			<%-- 会议地点--%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdPlace"/>
			</td>
			<td width="35%">
				<c:out value="${kmImeetingSummaryForm.fdPlaceName} ${kmImeetingSummaryForm.fdOtherPlace}"></c:out>
			</td>
		</tr>
		<tr>
			<%-- 会议时间--%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-imeeting" key="kmImeetingMain.fdDate"/>
			</td>
			<td width="35%">
				<xform:datetime property="fdHoldDate" dateTimeType="datetime" style="width:36%" ></xform:datetime>~
				<xform:datetime property="fdFinishDate" dateTimeType="datetime" style="width:36%" ></xform:datetime>
			</td>
			<%--会议历时--%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-imeeting" key="kmImeetingMain.fdHoldDuration"/>
			</td>			
			<td width="35%" >
				<span id ="fdHoldDurationHour" ></span><bean:message key="date.interval.hour"/>
				<span id="fdHoldDurationMinSpan"><span id ="fdHoldDurationMin" ></span><bean:message key="date.interval.minute"/></span>
			</td>
		</tr>
		<tr>
			<%-- 计划参加人员--%>
			<td class="td_normal_title" width=15%>
		   		<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdPlanAttendPersons" />
			</td>
			<td width="85%"  colspan="3" style="word-break:break-all">
				<c:if test="${ not empty kmImeetingSummaryForm.fdPlanAttendPersonNames }">
					<div>
<%-- 						<img src="${LUI_ContextPath}/km/imeeting/resource/images/inner_person.png" /> --%>
						<span style="vertical-align: top;">
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdInnerPerson"/>：<c:out value="${kmImeetingSummaryForm.fdPlanAttendPersonNames }"></c:out>
						</span>
					</div>
				</c:if>
				<%--外部计划参与人员--%>
				<c:if test="${ not empty kmImeetingSummaryForm.fdPlanOtherAttendPerson }">
					<div>
<%-- 						<img src="${LUI_ContextPath}/km/imeeting/resource/images/other_person.png" /> --%>
						<span style="vertical-align: top;">
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdOtherPerson"/>：<c:out value="${kmImeetingSummaryForm.fdPlanOtherAttendPerson }"></c:out>
						</span>
					</div>
				</c:if>
			</td>
		</tr>
		<tr>
			<%-- 计划列席人员--%>
			<td class="td_normal_title" width=15%>
		   		<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdPlanParticipantPersons" />
			</td>
			<td width="85%"  colspan="3" style="word-break:break-all">
				<c:if test="${ not empty kmImeetingSummaryForm.fdPlanParticipantPersonNames }">
					<div>
						<span style="vertical-align: top;">
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdInnerPerson"/>：<c:out value="${kmImeetingSummaryForm.fdPlanParticipantPersonNames }"></c:out>
						</span>
					</div>
				</c:if>
				<%--外部参加人员--%>
				<c:if test="${ not empty kmImeetingSummaryForm.fdPlanOtherParticipantPersons }">
					<div>
						<img src="${LUI_ContextPath}/km/imeeting/resource/images/other_person.png" />
						<span style="vertical-align: top;">
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdOtherPerson"/>：<c:out value="${kmImeetingSummaryForm.fdPlanOtherParticipantPersons }"></c:out>
						</span>
					</div>
				</c:if>
			</td>
		</tr>
		<tr>
			<!-- 实际与会人员 -->
			<td class="td_normal_title" width=15%>
			   <bean:message bundle="km-imeeting" key="kmImeetingSummary.fdActualAttendPersons" />
			</td>
			<td width="85%"  colspan="3" style="word-break:break-all">
				<c:if test="${ not empty kmImeetingSummaryForm.fdActualAttendPersonNames }">
					<div>
<%-- 						<img src="${LUI_ContextPath}/km/imeeting/resource/images/inner_person.png" /> --%>
						<span style="vertical-align: top;">
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdInnerPerson"/>：<c:out value="${kmImeetingSummaryForm.fdActualAttendPersonNames }"></c:out>
						</span>
					</div>
				</c:if>
				<%--外部与会人员--%>
				<c:if test="${ not empty kmImeetingSummaryForm.fdActualOtherAttendPersons }">
					<div>
<%-- 						<img src="${LUI_ContextPath}/km/imeeting/resource/images/other_person.png" /> --%>
						<span style="vertical-align: top;">
							<bean:message bundle="km-imeeting" key="kmImeetingMain.fdOtherPerson"/>：<c:out value="${kmImeetingSummaryForm.fdActualOtherAttendPersons }"></c:out>
						</span>
					</div>
				</c:if>
			</td>
		</tr>
		<tr>
			<%-- 抄送人员--%>
			<td class="td_normal_title" width=15%>
		   		<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdCopyToPersons" />
			</td>
			<td colspan="3">
				<xform:address propertyName="fdCopyToPersonNames" propertyId="fdCopyToPersonIds" style="width:92%;" textarea="true"></xform:address>
			</td>
		</tr>
		<tr>
			<%-- 编辑内容--%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-imeeting" key="kmImeetingSummary.docContent" />
			</td>
			<td width=85% colspan="3" id="contentDiv">
				<c:if test="${kmImeetingSummaryForm.fdContentType=='rtf'}">
					<xform:rtf property="docContent"></xform:rtf>
				</c:if>
				<c:if test="${kmImeetingSummaryForm.fdContentType=='word'}">
				<% 
					boolean wpsWebOffice = new Boolean(com.landray.kmss.sys.attachment.integrate.wps.util.SysAttWpsCloudUtil.isEnableWpsWebOffice());
					   if(wpsWebOffice){%>
						  <div>
								<%
									List sysAttMains = (List)pageContext.getAttribute("sysAttMains");
										if(sysAttMains==null || sysAttMains.isEmpty()){
											try{
												String _modelId = request.getParameter("fdId");
												if(StringUtil.isNotNull(_modelId)){
													ISysAttMainCoreInnerService sysAttMainService = (ISysAttMainCoreInnerService)SpringBeanUtil.getBean("sysAttMainService");
													sysAttMains = sysAttMainService.findByModelKey("com.landray.kmss.km.imeeting.model.KmImeetingSummary",_modelId,"editonline");
												}
												if(sysAttMains!=null && !sysAttMains.isEmpty()){
													com.landray.kmss.sys.attachment.model.SysAttMain sysAttMain = (com.landray.kmss.sys.attachment.model.SysAttMain)sysAttMains.get(0);
													pageContext.setAttribute("sysAttMainFdFileName",sysAttMain.getFdFileName());
													pageContext.setAttribute("fdAttMainId",sysAttMain.getFdId());
												}
												else
												{
													pageContext.setAttribute("sysAttMainFdFileName","");
												}
											}catch(Exception e){
												e.printStackTrace();
											}
										}else
										{
											com.landray.kmss.sys.attachment.model.SysAttMain sysAttMain = (com.landray.kmss.sys.attachment.model.SysAttMain)sysAttMains.get(0);
											pageContext.setAttribute("sysAttMainFdFileName",sysAttMain.getFdFileName());
										}
									%>
									<c:set var="fdAttMainId" value="${fdAttMainId}" scope="request"/>
									<%
									if(com.landray.kmss.sys.attachment.util.JgWebOffice.isExistViewPath(request))
									{
									%>
									 <c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_include_viewHtml.jsp" charEncoding="UTF-8">
										<c:param name="fdKey" value="editonline" />
										<c:param name="fdAttType" value="office" />
										<c:param name="fdModelId" value="${kmImeetingSummaryForm.fdId}" />
										<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingSummary" />
										<c:param name="formBeanName" value="kmImeetingSummaryForm" />
										<c:param name="buttonDiv" value="missiveButtonDiv" />
										<c:param name="isExpand" value="true" />
										<c:param name="showToolBar" value="false" />
									</c:import>
									<%} else{%>
									<img src="${LUI_ContextPath}/km/imeeting/resource/images/word.png" height='30' width='30' border='0' align='absmiddle' style='margin-right:3px;' />
									${sysAttMainFdFileName}
									
								<%} %>
							</div>
					<%} else if(com.landray.kmss.sys.attachment.util.JgWebOffice.isJGEnabled()){%>
						<div id="missiveButtonDiv" style="text-align:right;padding-bottom:5px">&nbsp;
			            </div>
			            <c:import url="/sys/attachment/sys_att_main/jg/sysAttMain_include_viewHtml.jsp" charEncoding="UTF-8">
							<c:param name="fdKey" value="editonline" />
							<c:param name="fdAttType" value="office" />
							<c:param name="fdModelId" value="${kmImeetingSummaryForm.fdId}" />
							<c:param name="fdModelName" value="com.landray.kmss.km.imeeting.model.KmImeetingSummary" />
							<c:param name="formBeanName" value="kmImeetingSummaryForm" />
							<c:param name="buttonDiv" value="missiveButtonDiv" />
							<c:param name="isExpand" value="true" />
							<c:param name="showToolBar" value="false" />
						</c:import>
					<%  } else { %>
							${kmImeetingSummaryForm.fdHtmlContent}
					<%} %>
				</c:if>
			</td>
		</tr>
		<tr>
	 		<%--会议组织人--%>
	 		<td class="td_normal_title" width=15%>
	 			<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdEmcee"/>
	 		</td>
	 		<td width="35%" >
	 			<c:out value="${kmImeetingSummaryForm.fdEmceeName}"></c:out>
			</td>
			<%--组织部门--%>
	 		<td class="td_normal_title" width=15%>
	 			<bean:message bundle="km-imeeting" key="kmImeetingSummary.docDept"/>
	 		</td>
	 		<td width="35%" >
	 			<c:out value="${kmImeetingSummaryForm.docDeptName}"></c:out>
			</td>
	 	</tr>
		<tr>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-imeeting" key="kmImeetingSummary.fdNotifyType" />
			</td>
			<td colspan="3">
					<c:if test="${kmImeetingSummaryForm.fdNotifyType == 'todo' }">
						<bean:message bundle="sys-notify" key="sysNotify.type.todo"/>
					</c:if>
					<c:if test="${kmImeetingSummaryForm.fdNotifyType == 'mobile' }">
						<bean:message bundle="sys-notify" key="sysNotify.type.mobile"/>
					</c:if>
					<c:if test="${kmImeetingSummaryForm.fdNotifyType == 'email' }">
						<bean:message bundle="sys-notify" key="sysNotify.type.email"/>
					</c:if>
			</td>
		</tr>
		<tr>
			<%-- 纪要录入人--%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-imeeting" key="kmImeetingSummary.docCreator"/>
			</td>
			<td width="35%">
				<html:hidden property="docCreatorId"/><html:hidden property="docCreatorName"/>
				<c:out value="${kmImeetingSummaryForm.docCreatorName }"></c:out>
			</td>
			<%-- 录入时间--%>
			<td class="td_normal_title" width=15%>
				<bean:message bundle="km-imeeting" key="kmImeetingSummary.docCreateTime"/>
			</td>
			<td width="35%">
				<html:hidden property="docCreateTime"/>
				<c:out value="${kmImeetingSummaryForm.docCreateTime }"></c:out>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/km/imeeting/km_imeeting_main/kmImeetingMain_printQrCode.jsp"%>
</div></div>
</center>
</html:form>
<c:set var="frameShowTop" scope="page" value="${(empty param.showTop) ? 'yes' : (param.showTop)}"/>
<c:if test="${frameShowTop=='yes' }">
	<ui:top id="top"></ui:top>
	<kmss:ifModuleExist path="/sys/help">
		<c:import url="/sys/help/sys_help_template/sysHelp_template_btn.jsp" charEncoding="UTF-8"></c:import>
	</kmss:ifModuleExist>
</c:if>
</template:replace>
</template:include>

