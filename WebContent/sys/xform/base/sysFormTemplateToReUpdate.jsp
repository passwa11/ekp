<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>
<% response.setHeader("X-UA-Compatible","IE=edge"); %>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<%@ include file="/sys/ui/jsp/jshead.jsp"%>
<script type="text/javascript">
Com_IncludeFile("dialog.js");
</script>
<title><bean:message bundle="sys-xform-base"
		key="Designer_Lang.rebuildJsp" /></title>
<div id="optBarDiv">
	<input type="button" value="<bean:message key="button.close"/>"
		onclick="Com_CloseWindow();">
</div>
<p style="text-align: center;font-size: 18px;line-height: 30px;color: #3e9ece;font-weight: bold;">
	<bean:message bundle="sys-xform-base" key="Designer_Lang.rebuildJsp" />
</p>
<center>
	<table class="tb_normal" width=85%>
		<tr>
			<td class="td_normal_title" width=15%><bean:message bundle="sys-xform-base" key="Designer_Lang.rebuildJspIsAll" /></td>
			<td width="85%">
			<ui:switch id="rebuildJspAll" property="rebuildJspAll" disabledText="${ lfn:message('sys-xform-base:Designer_Lang.rebuildJspClose') }" enabledText="${ lfn:message('sys-xform-base:Designer_Lang.rebuildJspOpen') }" checked="true" onValueChange="setSwitch('rebuildJspAll');"></ui:switch>
			</td>
		</tr>
		<tr>
		<td class="td_normal_title" width=15%><bean:message bundle="sys-xform-base" key="Designer_Lang.rebuildJspIsByIds" /></td>
			<td width="85%">
			<ui:switch id="rebuildJsp" property="rebuildJsp"  disabledText="${ lfn:message('sys-xform-base:Designer_Lang.rebuildJspClose') }" enabledText="${ lfn:message('sys-xform-base:Designer_Lang.rebuildJspOpen') }" onValueChange="setSwitch('rebuildJsp');"></ui:switch>
			</td>
		</tr>
		<tr id='rebuildJspTr' style="display: none;">
			<td class="td_normal_title" width=15%><bean:message bundle="sys-xform-base" key="Designer_Lang.rebuildJspScope" /></td>
			<td width="85%">
			<!-- 被选中的模板ID集合“;”分割 -->
			<input type="hidden" name="templateId">
			<xform:textarea property="templateName" showStatus="edit" style="width:96%" htmlElementProperties="readOnly onclick='selectTempateIdDialog();'"></xform:textarea>
			<a href="javascript:void(0);" onclick="selectTempateIdDialog();"><bean:message bundle="sys-xform-base" key="Designer_Lang.attrpanelSelect" /></a>
			</td>
		</tr>
	</table>
	</br>
	<table class="tb_nobrder" width=85%>
	<tr>
		<td align="center">
			<ui:button text="${ lfn:message('button.ok') }" onclick="submitAction();"></ui:button>
		</td>
	</tr>
</table>
</center>
<%@ include file="/sys/xform/base/sysFormTemplateReUpdate.jsp"%>
<script type="text/javascript">
function selectTempateIdDialog() {
	Dialog_Tree(true,'templateId', 'templateName',';','sysFormTempateJspUpdateDataBean&parentNodeId=!{value}','<bean:message bundle="sys-xform-base" key="Designer_Lang.rebuildJspModelName" />',false);
}

function setSwitch(id){
	var selCk,needCk;
	if(id=='rebuildJspAll'){
		selCk = LUI('rebuildJspAll');
		needCk = LUI('rebuildJsp');
	}else{
		selCk = LUI('rebuildJsp');
		needCk = LUI('rebuildJspAll');
	}
	if(selCk.checkbox.is(':checked')){
		needCk.checkbox.prop('checked',false);
		needCk.setText(false);
	}else{
		needCk.checkbox.prop('checked',true);
		needCk.setText(true);
	}
	if(LUI('rebuildJspAll').checkbox.is(':checked')){
		$("#rebuildJspTr").hide();
		$("input[name='templateId']").val("");
		$("textarea[name='templateName']").val("");
	}else{
		$("#rebuildJspTr").show();
	}
}

function submitAction(){
	var vtemplateId=document.getElementsByName('templateId')[0].value;
	//alert($("input[name='templateId']").val());
	if(LUI('rebuildJspAll').checkbox.is(':checked')){
		window.DoUpdate();
	}else{
		if(vtemplateId==""){
			alert("${lfn:message('sys-xform-base:Designer_Lang.rebuildJspSelect')}");
			return;
		}else{
			var message=confirm("${lfn:message('sys-xform-base:Designer_Lang.sure_to_rebuildJsp')}"); 
			var oldscrollTop;
			if(message==true){
				$.ajax( {
					url : Com_Parameter.ContextPath+'sys/xform/sys_form_common_template/sysFormCommonTemplate.do?method=rebuildJsp&fdId='+vtemplateId,
					type : 'GET',
					success : function(data) {
						if(data == "true"){
							alert("${lfn:message('sys-xform-base:Designer_Lang.successful_operation')}"); 
							$("input[name='templateId']").val("");
							$("textarea[name='templateName']").val("");
						}else{
							alert("${lfn:message('sys-xform-base:Designer_Lang.failure_operation')}"); 
						}
					},
					beforeSend : function() {
						XForm_Loading_Msg = "${lfn:message('sys-xform-base:Designer_Lang.loading_Msg')}";
						XForm_Loading_Img = document.createElement('img');
						XForm_Loading_Img.src = Com_Parameter.ContextPath + "sys/xform/designer/style/img/_loading.gif";
						XForm_Loading_Div = document.createElement('div');
						XForm_Loading_Div.id = "XForm_Loading_Div";
						XForm_Loading_Div.style.height = "100%";
						XForm_Loading_Div.style.width = "100%";
						XForm_loading_Text = document.createElement("label");
						XForm_loading_Text.id = 'XForm_loading_Text_Label';
						XForm_loading_Text.appendChild(document.createTextNode(XForm_Loading_Msg));
						XForm_loading_Text.style.color = "#00F";
						XForm_loading_Text.style.height = "16px";
						XForm_loading_Text.style.marginTop = '20%';
						XForm_Loading_Img.style.marginTop = '20%';
						oldscrollTop = document.body.scrollTop;
						document.body.scrollTop = 0;
						document.body.style.overflow = 'hidden';
						XForm_Loading_Div.appendChild(XForm_Loading_Img);
						XForm_Loading_Div.appendChild(XForm_loading_Text);
						XForm_Loading_Div.align = "center";
						var scrollbarWidth = document.body.offsetWidth - document.body.clientWidth;
						with(document.body.appendChild(XForm_Loading_Div).style) {
							position = 'absolute'; filter = 'alpha(opacity=80)'; opacity = '0.8';
							border = '1px'; background = '#EAEFF3'; 
							top = '0'; left = '0';
							zIndex = '999';
						}
					},
					complete : function() {
						var div = document.getElementById('XForm_Loading_Div');
						if (div){
							document.body.removeChild(div);
						}
						document.body.style.overflow = 'auto';
						document.body.scrollTop = oldscrollTop;
					}
				});
			} 
		}
	}
}
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>