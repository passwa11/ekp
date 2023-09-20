<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.landray.kmss.sys.print.forms.SysPrintTemplateForm,com.landray.kmss.util.EnumerationTypeUtil"%>
<%@page import="com.landray.kmss.sys.xform.XFormConstant"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="templateForm" value="${requestScope[param.formName]}" />
<c:set var="sysPrintTemplateForm" value="${templateForm.sysPrintTemplateForm}" />
<c:set var="sysPrintPrefix" value="sysPrintTemplateForm.${param.fdKey}." />
<link href="${KMSS_Parameter_ContextPath}sys/print/designer/style/designer.css" type="text/css" rel="stylesheet" />
<link href="${KMSS_Parameter_ContextPath}resource/style/default/doc/document.css" type="text/css" rel="stylesheet" />

<!-- 设计器区域 -->
<c:if test="${param.useLabel != 'false'}">
	<tr id="sysPrint_tab" LKS_LabelName="<kmss:message key="${(not empty param.messageKey) ? (param.messageKey) : 'sys-print:table.sysPrint.title'}" />" style="display:none" LKS_LabelEnable="${JsParam.enable eq 'false' ? 'false' : 'true'}">
		<td>
</c:if>
<%
 	SysPrintTemplateForm printForm = (SysPrintTemplateForm)pageContext.getAttribute("sysPrintTemplateForm");
	String printMode = printForm.getFdPrintMode();
	String fdPrintModeLabel = EnumerationTypeUtil.getColumnEnumsLabel("fdPrint_mode","1");
%>
<div id="DIV_SubPrint_View" style="width:12.7%;height:100%;float:left;display:none;border:1px #d2d2d2 solid;">
	<div style="height:32px;border-bottom:1px solid #d2d2d2;">
		<table class="subTable" style="width:100%">
			<tr>
				<td style="width: 38%;padding:8px">
					<div style="font-size:12px;font-weight:bold;"><kmss:message key="sys-print:sysPrint.multiprint_configuration" /></div>
				</td>
			</tr>
		</table>
	</div>
	<div id="SubFormDiv" style="overflow-x:hidden;overflow-y:auto;text-align: center;"> 
		<%@include file="/sys/print/include/sysSubPrintTemplate_view.jsp"%>
	</div>
</div>
<div id="DIV_SubPrintTep_View">
<table class="tb_normal" width=100% style="border-top:0;">
	<tr>
		<c:if test="${'true' eq param._isShowName}">
			<c:if test="${fn:length(sysPrintTemplateForm.fdSubTemplates) <= 0 }">
				<td class='td_normal_title' width=15%>
					<kmss:message key="sys-print:sysPrintTemplate.fdName"></kmss:message>
				</td>
				<td width=25%>
					<xform:text value='${sysPrintTemplateForm.fdName }' property="sysPrintTemplateForm.fdName" showStatus='view' style='width:90%'></xform:text>
				</td>
			</c:if>
		</c:if>
		<td class="td_normal_title" width=15%>
			<kmss:message key="sys-print:table.sysPrint.fdPrintMode" />
		</td>
		<td width=25% id="tr_fdPrintMode">
		 	<xform:select property="sysPrintTemplateForm.fdPrintMode"  showPleaseSelect="false" showStatus="view" onValueChange="onFdPrintModeChange">
				<xform:enumsDataSource enumsType="fdPrint_mode" />
			</xform:select>
		</td>
		<td class="td_normal_title" width=15% style='<c:if test="${'true' eq param._isHideDefaultSetting}">display:none</c:if>'>
			<c:choose>
				<c:when test="${empty param.usePrintTemplate }">
					<kmss:message key="sys-print:table.sysPrint.config" />
				</c:when>
				<c:otherwise>
					${param.usePrintTemplate }
				</c:otherwise>
			</c:choose>
		</td>
		<td style='<c:if test="${'true' eq param._isHideDefaultSetting}">display:none</c:if>'>
		 	<xform:checkbox property="sysPrintTemplateForm.fdPrintTemplate">
			   	<xform:simpleDataSource value="true"><kmss:message key="sys-print:table.sysPrint.fdPrintTemplate" />
			   	</xform:simpleDataSource>
			</xform:checkbox>
		</td>
	</tr>
	<c:if test="${sysPrintTemplateForm.fdPrintMode=='2'}">
		<tr id="tr_word_printEdit">
			<td class="td_normal_title" width=15%>
				<kmss:message key="sys-print:table.sysPrint.fdPrintEdit" />
			</td>
			<td colspan="3">
			 	<xform:checkbox property="sysPrintTemplateForm.fdPrintEdit" showStatus="view">
				   	<xform:simpleDataSource value="true"><kmss:message key="message.yes" />
				   	</xform:simpleDataSource>
				</xform:checkbox>
			</td>
		</tr>
	</c:if>

	<c:if test="${sysPrintTemplateForm.fdPrintMode!='2'}">
		<tr style='<c:if test="${'true' eq param._isHideDesc}">display:none</c:if>'>
			<td class="td_normal_title" width=15%>
				<kmss:message key="sys-print:table.sysPrint.description" />
			</td>
			<td colspan="3">
			 	<kmss:message key="sys-print:table.sysPrint.description.txt" />
			</td>
		</tr>
	</c:if>
	
</table>
<c:if test="${sysPrintTemplateForm.fdPrintMode!='2'}">
	<div id="sysPrintdesignPanel">
		<table class="tb_normal" width="100%">
			<tr>
				<td id="sysPrintdesignPanelTd">${sysPrintTemplateForm.fdTmpXml}</td>
			</tr>
		</table>
	</div>
	<div id="sysPrintEditionPanel">
		<table class="tb_normal" width="100%">
			<tr style="background: #f6f6f6;">
				<td style="position: relative;">
				<label>
					<input type="checkbox" onclick="sysPrintLoadHistory();"/>
						<bean:message  bundle="sys-print" key="sysPrintTemplate.changeRecord"/>
				</label>
				<div class="sysPrint_addEdition_btn" style="right: 0%;border: none;margin-top: 1px">
					<input class="btnopt" style="height:25px;width: 100px;padding: 0 2px 5px 2px;border: none;color: #fff;line-height: 22px;" type="button" value="生成历史版本" onclick="rebuildPrintJsp();" />
					
				</div>
				</td>
			</tr>
			<tr>
				<td>
					<div id="sysPrintTemplateChangeHistoryDiv" style="display:none"></div>
					<div id="sysPrintPageOperation" style="display:none">
						<ul class="sysPrint_history_pageUl">
							<li style="float:left;" id="lastPrintPageNum" onclick="sys_xform_history_skipPage('1');">
								<bean:message  bundle="sys-xform" key="sysFormTemplate.previousPage"/>
							</li>
							<li id="nextPrintPageNum" onclick="sys_xform_history_skipPage('2');">
								<bean:message  bundle="sys-xform" key="sysFormTemplate.nextPage"/>
							</li>
						</ul>
					</div>
				</td>
			</tr>
		</table>
	</div>
</c:if>
<input type="hidden" name="sysPrintTemplateForm.fdTmpXml"  />
</div>
<c:if test="${param.useLabel != 'false'}">
	</tr>
		</td>
</c:if>
<script type="text/javascript">
	function sysPrintOnLoad(){
		var fdPrintMode = "${sysPrintTemplateForm.fdPrintMode}";
		if(!fdPrintMode){
			var v = "<%=fdPrintModeLabel%>";
			$('#tr_fdPrintMode').html(v);
		}
		//多标签切换时件
		var table = document.getElementById("Label_Tabel");
		if(table!=null && window.Doc_AddLabelSwitchEvent){
			Doc_AddLabelSwitchEvent(table, "SubPrint_OnLabelSwitch_${JsParam.fdKey}");
		} 
	}
	//记录当前最新的版本号
	var sys_print_currentVersion = 0;

	//记录当前的页码
	var sys_print_history_currentPageNum = 1;
	var sys_print_history_rowSize = 8;

	var sys_print_history_maxResults = 0;
	function sysPrintLoadHistory(){
		var div = $("#sysPrintTemplateChangeHistoryDiv");
		var pageNum = $("#sysPrintPageOperation");
		if(div.is(":hidden")) {
			div.show(); // 显示
			pageNum.show();
			if(div.html()) { //已经加载
				return;
			}
			sysPrintEditionListReLoad(div);
			
		} else {
			div.hide(); // 隐藏
			pageNum.hide();
		}
	}
	function sysPrintEditionListReLoad(div){
		div.html('<img src="' + Com_Parameter.ResPath + 'style/common/images/loading.gif" border="0" />');
		var url = "sysPrintTemplateHistoryService&fdModelId=${JsParam.fdId}&rowsize=" + sys_print_currentVersion + "&pageno=" + sys_print_history_currentPageNum;
		//兼容多表单
		if(xform_subform_fdMode=="<%=XFormConstant.TEMPLATE_SUBFORM%>" || xform_subform_fdMode=="<%=XFormConstant.TEMPLATE_DEFINE%>"){
			var tr = $("#TABLE_PRINT").find("tr[ischecked='true']");
			url += ("&fdTemplateId="+tr.find("input[name='MyfdId']").val());
		}else{
			url += ("&fdTemplateId=${sysPrintTemplateForm.fdId}");
		}
		var kmssdata = new KMSSData();
		if(xform_subform_fdMode=="<%=XFormConstant.TEMPLATE_SUBFORM%>" || xform_subform_fdMode=="<%=XFormConstant.TEMPLATE_DEFINE%>"){
			kmssdata.UseCache = false;
		}
		var data = kmssdata.AddBeanData(url).GetHashMapArray();
		var html = getPrintTableHtml(data,'true');		
		div.html(html.join(''));
		addPrintOperation(div);
		//翻页
		if(sys_print_history_currentPageNum > 1){
			var num = data.length + ((sys_print_history_currentPageNum-1) * sys_print_history_rowSize);
			//不是第一页，显示上一页
			$("#lastPrintPageNum").show();
			if(sys_print_history_maxResults > 0 && num < sys_print_history_maxResults){				
				$("#nextPrintPageNum").show();
			}else{
				$("#nextPrintPageNum").hide();
			}
		}else{
			$("#lastPrintPageNum").hide();
			if(sys_print_history_maxResults > 0 && data.length < sys_print_history_maxResults){	
				$("#nextPrintPageNum").show();
			}else{
				$("#nextPrintPageNum").hide();
			}
		}
	}
	function addPrintOperation(div){
		div.find('tr:gt(0)').mouseover(function(){
			$(this).addClass("sys_form_template_history_mouseover");
		}).mouseout(function(){
			$(this).removeClass("sys_form_template_history_mouseover");
		}).click(function(){
			var fdId = $(this).attr("id");
			var versionType = $(this).attr("versionType");
			Com_OpenWindow(Com_Parameter.ContextPath+'sys/print/sys_print_template_history/sysPrintTemplateHistory.do?method=view&fdId=' + fdId + '&fdModelTemplateId=${JsParam.fdModelTemplateId}&fdMainModelName=${JsParam.modelName}&fdKey=${JsParam.fdKey}&versionType=' + versionType);
		});
	}

	function getPrintTableHtml(data,notSearch){
		var html = [];
		html.push('<table class="tb_normal" width="100%" style="text-align:center;">');
		html.push('<tr class="tr_normal_title">');
		html.push('<td width="40pt" class="td_normal_title">' + '<bean:message  bundle="sys-xform" key="sysFormTemplate.no"/>' + '</td>');
		html.push('<td class="td_normal_title">' + '<bean:message  bundle="sys-xform" key="sysFormTemplate.templateEdition"/>' + '</td>');
		html.push('<td class="td_normal_title">' + '<bean:message  bundle="sys-print" key="sysPrintTemplate.fdFormMode"/>' + '</td>');
		html.push('<td class="td_normal_title">' + '<bean:message  bundle="sys-xform" key="sysFormTemplate.alteror"/>' + '</td>');
		html.push('<td class="td_normal_title">' + '<bean:message  bundle="sys-xform" key="sysFormTemplate.alterorTime"/>' + '</td>');		
		html.push('<td class="td_normal_title">' + '<bean:message  bundle="sys-xform" key="sysFormTemplate.versionType"/>' + '</td>');
		//html.push('<td class="td_normal_title">' + '变更原因' + '</td>');
		html.push('</tr>');
		for(var i = 0; i < data.length; i++){
			if(data[i].maxResults){
				sys_print_history_maxResults = data[i].maxResults;
				//赋值完，删除记录
				data.splice(i,1);
				i--;
				continue;
			}
			//i == 0表明该行为最新版本
			if(i == 0){
				//notSearch搜索框的标志位，如果是搜索框触发的，不需要更新最新版本号
				if(notSearch && notSearch == 'true'){
					if(parseInt(sys_print_currentVersion) < parseInt(data[i].fdTemplateEdition)){
						sys_print_currentVersion = data[i].fdTemplateEdition;
					}	
				}
				if(parseInt(sys_print_currentVersion) == parseInt(data[i].fdTemplateEdition)){
					html.push('<tr id = "' + data[i].fdId + '" versionType="new">');
				}else{
					html.push('<tr id = "' + data[i].fdId + '" >');
				}
				//html.push('<tr id = "' + data[i].fdId + '" versionType="new">');
			}else{
				html.push('<tr id = "' + data[i].fdId + '" >');
			}				
			html.push('<td>' + (i + 1) + '</td>');
			html.push('<td>' + 'V' + data[i].fdTemplateEdition + '</td>');
			var commonTemplateTxt = '<bean:message  bundle="sys-print" key="sysPrintTemplate.fdFormMode.value2"/>';
			var formTemplateTxt = '<bean:message  bundle="sys-print" key="sysPrintTemplate.fdFormMode.value1"/>';
			var subTemplateTxt = '多表单模式';
			var rtfTxt = '富文本模式';
			var wordTxt = 'word模式';
			var formPrintModeTxt = '';
			if(data[i].fdFormMode==2){
				formPrintModeTxt = commonTemplateTxt;
			}else if(data[i].fdFormMode==1){
				formPrintModeTxt = rtfTxt;
			}else if(data[i].fdFormMode==3){
				formPrintModeTxt = formTemplateTxt;
			}else if(data[i].fdFormMode==4){
				formPrintModeTxt = subTemplateTxt;
			}else if(data[i].fdFormMode==5){
				formPrintModeTxt = wordTxt;
			}
			
			html.push('<td>' + formPrintModeTxt + '</td>');
			html.push('<td>' + data[i].fdAlterorName + '</td>');
			html.push('<td>' + data[i].fdAlterTime + '</td>');
			if(parseInt(data[i].fdTemplateEdition) < parseInt(sys_print_currentVersion)){
				html.push('<td>' + '<bean:message  bundle="sys-xform" key="sysFormTemplate.historyVersion"/>' + '</td>');
			}else{
				html.push('<td>' + '<bean:message  bundle="sys-xform" key="sysFormTemplate.currentVersion"/>' + '</td>');
			}
			//html.push('<td>' + data[i].fdChangeAsNewReason + '</td>');
			html.push('</tr>');
		}
		html.push('</table>');
		return html;
	};
	//生成历史版本
	function rebuildPrintJsp(){
		var dialog = new KMSSDialog(false);
		dialog.BindingField(null, null, null, null);
		var modelName = "${JsParam.templateModelName}";
		dialog.SetAfterShow(function(rtnVal){
			if(rtnVal == null) {
				return;
			}
			var fdFormTemplateId = rtnVal.data[0].id;//表单模板id
			Com_OpenWindow('${KMSS_Parameter_ContextPath}sys/print/sys_print_template_history/sysPrintTemplateHistory.do?method=add&fdFormTemplateId=' + fdFormTemplateId+'&modelName=${HtmlParam.modelName}&fdKey=${HtmlParam.fdKey }&templateModelName=${HtmlParam.templateModelName}&fdModelId=${HtmlParam.fdId}','_blank');
		});
		dialog.URL = Com_Parameter.ContextPath + "sys/print/sys_print_template_history/formTemplate_select.jsp?fdModelName=${JsParam.templateModelName}&fdModelId=${JsParam.fdId}&fdKey=${JsParam.fdKey}&fdType=";
		dialog.Show(710, 550);
		
	}
	Com_AddEventListener(window, 'load', sysPrintOnLoad);
</script>