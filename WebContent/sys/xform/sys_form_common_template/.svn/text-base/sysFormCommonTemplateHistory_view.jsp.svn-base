<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<style>
.sys_form_template_history_mouseover{
	background-color: #F0F0F0;
	cursor:pointer;
}
</style>
<tr>
	<td class="td_normal_title" colspan="2" style="position:relative">
	<label>
		<input type="checkbox" onclick="Sys_Form_Template_loadHistory('${sysFormCommonTemplateForm.fdId}', '${sysFormCommonTemplateForm.fdKey}');"/>
		${lfn:message('sys-xform:sysFormTemplate.template.change.log')}
	</label>
		<kmss:authShow roles="ROLE_SYSXFORM_CONTROL">
			<input class="btnopt" style="float: right;margin-right:15px;height:25px;width: 100px;background-color: #46B4E7;padding: 0 2px 5px 2px;border: none;color: #fff;line-height: 22px;" type="button" value="${lfn:message('sys-xform-base:Designer_Lang.rebuildJsp')}" onclick="Designer_OptionRun_RebuildJsp();" />
		</kmss:authShow>
	</td>
</tr>
<tr>
	<td colspan="2">
		<div id="sysFormTemplateChangeHistoryDiv_${sysFormCommonTemplateForm.fdKey}" style="display:none"></div>
	</td>
</tr>
<script>
	Com_IncludeFile("data.js");
	function Sys_Form_Template_loadHistory(templateId, key){
		var div = $("#sysFormTemplateChangeHistoryDiv_" + key);
		if(div.is(":hidden")) {
			div.show(); // 显示
			if(div.html()) { //已经加载
				return;
			}
			div.html('<img src="' + Com_Parameter.ResPath + 'style/common/images/loading.gif" border="0" />');
			var url = "sysFormTemplateHistoryService&templateId=" + templateId+ "&key=" + key + "&isCommonTemplate=true";
			var data = new KMSSData().AddBeanData(url).GetHashMapArray();
			var html = [];
			html.push('<table class="tb_normal" width="100%" style="text-align:center;">');
			html.push('<tr class="tr_normal_title">');
			html.push('<td width="40pt" class="td_normal_title">' + "${lfn:message('sys-xform:sysFormTemplate.no')}" + '</td>');
			html.push('<td class="td_normal_title">' +"${lfn:message('sys-xform:sysFormTemplate.templateEdition')}" + '</td>');
			html.push('<td class="td_normal_title">' +"${lfn:message('sys-xform:sysFormTemplate.alteror')}" + '</td>');
			html.push('<td class="td_normal_title">' +"${lfn:message('sys-xform:sysFormTemplate.alterorTime')}" + '</td>');		
			html.push('<td class="td_normal_title">' + "${lfn:message('sys-xform:sysFormTemplate.versionType')}" + '</td>');
			html.push('<td class="td_normal_title">' + '${lfn:message('sys-xform:sysFormTemplate.version.modification.detailed.log')}' + '</td>');
			html.push('</tr>');
			for(var i = 0; i < data.length; i++){
				if(data[i].fdId && data[i].fdId != ''){
					//i == 0表明该行为最新版本
					if(i == 0){
						html.push('<tr type="version" id = "' + data[i].fdId + '" versionType="new">');
					}else{
						html.push('<tr type="version" id = "' + data[i].fdId + '" >');
					}				
					html.push('<td>' + (i + 1) + '</td>');
					html.push('<td>' + 'V' + data[i].fdTemplateEdition + '</td>');			
					html.push('<td>' + data[i].fdAlterorName + '</td>');
					html.push('<td>' + data[i].fdAlterTime + '</td>');
					if(i == 0){
						html.push('<td>' + "${lfn:message('sys-xform:sysFormTemplate.currentVersion')}" + '</td>');
					}else{
						html.push('<td>' + "${lfn:message('sys-xform:sysFormTemplate.historyVersion')}"+ '</td>');
					}
					html.push('<td>');
					html.push(getMLogTableHtml(data[i].mLogs));
					html.push('</td>');
					html.push('</tr>');	
				}
			}
			html.push('</table>');
			div.html(html.join(''));
			div.find('tr[type="version"]').mouseover(function(){
				$(this).addClass("sys_form_template_history_mouseover");
			}).mouseout(function(){
				$(this).removeClass("sys_form_template_history_mouseover");
			}).click(function(){
				var fdId = $(this).attr("id");
				var versionType = $(this).attr("versionType");
				Com_OpenWindow(Com_Parameter.ContextPath+'sys/xform/sys_form_common_template_history/sysFormCommonTemplateHistory.do?method=viewHistory&fdId=' + fdId + '&fdModelName=${sysFormCommonTemplateForm.fdModelName}&fdMainModelName=${param.fdMainModelName}&versionType=' + versionType);
			});
		} else {
			div.hide(); // 隐藏
		}
	}
	
	// 变更日志
	function getMLogTableHtml(data,notSearch){
		var html = [];
		if (data) {
			data = JSON.parse(data);
			if(data && data.length > 0) {
				html.push('<table class="tb_normal" width="100%" style="text-align:center;">');
				html.push('<tr class="tr_normal_title">');
				html.push('<td width="40pt" class="td_normal_title">' + '${lfn:message('sys-xform:sysFormTemplate.no')}' + '</td>');
				html.push('<td class="td_normal_title">' + '${lfn:message('sys-xform:sysFormTemplate.alteror')}' + '</td>');
				html.push('<td class="td_normal_title">' + '${lfn:message('sys-xform:sysFormTemplate.alterorTime')}' + '</td>');
				html.push('<td class="td_normal_title">' + '${lfn:message('sys-xform:sysFormTemplate.update.detail')}' + '</td>');
				html.push('</tr>');
				for (var i = 0; i < data.length; i++){
					html.push('<tr id = "' + data[i].fdId + '" >');
					html.push('<td>' + (i + 1) + '</td>');
					html.push('<td>' + data[i].fdAlterorName + '</td>');
					html.push('<td>' + data[i].fdAlterTime + '</td>');
					html.push('<td>' + '<a style="color: #47b5ea;border-bottom:1px solid #47b5ea;" mlogId="' + data[i].fdId + '" href="javascript:void(0);" onclick="viewLog(this)">${lfn:message('sys-xform:sysFormTemplate.view')}</a>' + '</td>');
					html.push('</tr>');
				}
				html.push('</table>');
			}
		}
		return html.join("");
	}
	
	/** 查看变更日志详情 */
	function viewLog(src) {
		Com_EventStopPropagation();
		var mlogId = $(src).attr("mlogId");
		Com_OpenWindow(Com_Parameter.ContextPath+'sys/xform/base/sysFormModifiedLogAction.do?method=view&fdId=' + mlogId);
		return false;
	}
	
	function Designer_OptionRun_RebuildJsp(){
		var message=confirm("${lfn:message('sys-xform-base:Designer_Lang.sure_to_rebuildJsp')}"); 
		var oldscrollTop;
		if(message==true){
			$.ajax( {
				url : Com_Parameter.ContextPath+'sys/xform/sys_form_common_template/sysFormCommonTemplate.do?method=rebuildJsp&fdId=${sysFormCommonTemplateForm.fdId}',
				type : 'post',
				success : function(data) {
					if(data == "true"){
						alert("${lfn:message('sys-xform-base:Designer_Lang.successful_operation')}"); 
					}else{
						alert("${lfn:message('sys-xform-base:Designer_Lang.failure_operation')}"); 
					}
				},
				beforeSend : function() {
					var XForm_Loading_Msg = "${lfn:message('sys-xform-base:Designer_Lang.loading_Msg')}";
					var XForm_Loading_Img = document.createElement('img');
					XForm_Loading_Img.src = Com_Parameter.ContextPath + "sys/xform/designer/style/img/_loading.gif";
					var XForm_Loading_Div = document.createElement('div');
					XForm_Loading_Div.id = "loading_div";
					XForm_Loading_Div.style.height = "100%";
					XForm_Loading_Div.style.width = "100%";
					var XForm_loading_Text = document.createElement("label");
					XForm_loading_Text.id = 'XForm_loading_Text_Label';
					XForm_loading_Text.appendChild(document.createTextNode(XForm_Loading_Msg));
					XForm_loading_Text.style.color = "#00F";
					XForm_loading_Text.style.height = "16px";
					XForm_loading_Text.style.marginTop = '20%';
					XForm_Loading_Img.style.marginTop = '20%';
					oldscrollTop = document.documentElement.scrollTop||document.body.scrollTop;
					document.documentElement.scrollTop=document.body.scrollTop = 0;
					document.body.style.overflow = 'hidden';
					XForm_Loading_Div.appendChild(XForm_Loading_Img);
					XForm_Loading_Div.appendChild(XForm_loading_Text);
					XForm_Loading_Div.align = "center";
					with(document.body.appendChild(XForm_Loading_Div).style) {
						position = 'absolute'; filter = 'alpha(opacity=80)'; opacity = '0.8';
						border = '1px'; background = '#EAEFF3'; 
						top = '0'; left = '0';
						zIndex = '999';
					}
				},
				complete : function() {
					var div = document.getElementById('loading_div');
					if (div){
						document.body.removeChild(div);
					}
					document.body.style.overflow = 'auto';
					document.documentElement.scrollTop=document.body.scrollTop = oldscrollTop;
				}
			});
		} 
	}
</script>