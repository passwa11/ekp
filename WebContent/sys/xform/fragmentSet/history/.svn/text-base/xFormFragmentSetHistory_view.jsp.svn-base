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
		<input type="checkbox" onclick="Sys_Form_FragmentSet_loadHistory('${sysFormFragmentSetForm.fdId}', '${sysFormFragmentSetForm.fdKey}');"/>
		<bean:message bundle="sys-xform-fragmentSet" key="sysFormFragmentSet.changeRecord" />
	</label>
		<kmss:authShow roles="ROLE_SYSXFORM_CONTROL">
			<input class="btnopt" style="float: right;margin-right:15px;height:25px;width: 100px;background-color: #46B4E7;padding: 0 2px 5px 2px;border: none;color: #fff;line-height: 22px;" type="button" value="${lfn:message('sys-xform-base:Designer_Lang.rebuildJsp')}" onclick="Designer_OptionRun_RebuildJsp();" />
		</kmss:authShow>
	</td>
</tr>
<tr>
	<td colspan="2">
		<div id="sysFormFragmentSetChangeHistoryDiv_${sysFormFragmentSetForm.fdKey}" style="display:none"></div>
	</td>
</tr>
<!-- 片段集历史变更记录 -->
<script>
	Com_IncludeFile("data.js");
	function Sys_Form_FragmentSet_loadHistory(templateId, key){
		var div = $("#sysFormFragmentSetChangeHistoryDiv_" + key);
		if(div.is(":hidden")) {
			div.show(); // 显示
			if(div.html()) { //已经加载
				return;
			}
			div.html('<img src="' + Com_Parameter.ResPath + 'style/common/images/loading.gif" border="0" />');
			var url = "sysFormFragmentSetHistoryService&fdFragmentSetId=" + templateId+ "&key=" + key + "&isCommonTemplate=true";
			var data = new KMSSData().AddBeanData(url).GetHashMapArray();
			var html = [];
			html.push('<table class="tb_normal" width="100%" style="text-align:center;">');
			html.push('<tr class="tr_normal_title">');
			html.push('<td width="40pt" class="td_normal_title">' + '${lfn:message('sys-xform:sysFormTemplate.no')}' + '</td>');
			html.push('<td class="td_normal_title">' + '${lfn:message('sys-xform:sysFormTemplate.templateEdition')}' + '</td>');
			html.push('<td class="td_normal_title">' + '${lfn:message('sys-xform-base:sysFormModifiedLog.docCreator')}' + '</td>');
			html.push('<td class="td_normal_title">' + '${lfn:message('sys-xform:sysFormTemplate.alterorTime')}' + '</td>');
			html.push('<td class="td_normal_title">' + '${lfn:message('sys-xform:sysFormTemplate.versionType')}' + '</td>');
			html.push('</tr>');
			for(var i = 0; i < data.length; i++){
				if(data[i].fdId && data[i].fdId != ''){
					//i == 0表明该行为最新版本
					if(i == 0){
						html.push('<tr id = "' + data[i].fdId + '" versionType="new">');
					}else{
						html.push('<tr id = "' + data[i].fdId + '" >');
					}				
					html.push('<td>' + (i + 1) + '</td>');
					html.push('<td>' + 'V' + data[i].fdTemplateEdition + '</td>');			
					html.push('<td>' + data[i].fdAlterorName + '</td>');
					html.push('<td>' + data[i].fdAlterTime + '</td>');
					if(i == 0){
						html.push('<td>' + "${lfn:message('sys-xform:sysFormTemplate.currentVersion')}" + '</td>');
					}else{
						html.push('<td>' + "${lfn:message('sys-xform:sysFormTemplate.historyVersion')}" + '</td>');
					}	
					html.push('</tr>');	
				}
			}
			html.push('</table>');
			div.html(html.join(''));
			div.find('tr:gt(0)').mouseover(function(){
				$(this).addClass("sys_form_template_history_mouseover");
			}).mouseout(function(){
				$(this).removeClass("sys_form_template_history_mouseover");
			}).click(function(){
				var fdId = $(this).attr("id");
				var versionType = $(this).attr("versionType");
				Com_OpenWindow(Com_Parameter.ContextPath+'sys/xform/fragmentSet/history/xFormFragmentSetHistory.do?method=viewHistory&fdId=' + fdId + '&fdModelName=${sysFormFragmentSetForm.fdModelName}&fdMainModelName=${param.fdMainModelName}&versionType=' + versionType);
			});
		} else {
			div.hide(); // 隐藏
		}
	}
	
	function Designer_OptionRun_RebuildJsp(){
		var message=confirm("${lfn:message('sys-xform-base:Designer_Lang.sure_to_rebuildJsp')}"); 
		var oldscrollTop;
		if(message==true){
			$.ajax( {
				url : Com_Parameter.ContextPath+'sys/xform/sys_form_common_template/sysFormCommonTemplate.do?method=rebuildJsp&fdId=${sysFormFragmentSetForm.fdId}',
				type : 'GET',
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