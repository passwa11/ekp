<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="/resource/jsp/jsperror.jsp"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ page import="com.landray.kmss.sys.xform.XFormConstant"%>
<tr id="isLbpmCustomizeContentFormTr">
	<td width="100px"><kmss:message key="FlowChartObject.Lang.Node.Default.approval" bundle="sys-lbpmservice" /></td>
	<td colspan="3">
		<label>
			<input name="ext_isLbpmCustomizeContent" type="radio" value="0" checked>
			<kmss:message key="FlowChartObject.Lang.Node.Default.approval.use.global" bundle="sys-lbpmservice" />
		</label>
		<label>
			<input name="ext_isLbpmCustomizeContent" type="radio" value="1" >
			<kmss:message key="FlowChartObject.Lang.Node.Default.approval.custom" bundle="sys-lbpmservice" />
		</label>&nbsp;
		
		<input name="ext_lbpmCustomizeContentJson" id="lbpmCustomizeContentJson" type="hidden">
		<input name="ext_lbpmCustomizeValidateContentJson" id="lbpmCustomizeValidateContentJson" type="hidden">
		<a style="cursor:pointer;" href="#" id="update_set" class="btn_b" title="修改设置" onclick="lbpmCustomizeFunc(this);"><span><em>修改设置</em></span></a>
	</td>
</tr>

<script>
$(document).ready(function(){
	var extAttributes=AttributeObject.NodeData.extAttributes;
	
	var isLbpmCustomizeContentRadio="";
	if(extAttributes){
		for(var i=0;i<extAttributes.length;i++){
			if(extAttributes[i].name=="isLbpmCustomizeContent"){
				isLbpmCustomizeContentRadio=extAttributes[i].value;
			}
		}	
	}
	
	
	
	if(isLbpmCustomizeContentRadio=="1"){
		$("#update_set").show();//显示div
	}else{
		$("#update_set").hide();//隐藏div
		//兼容历史数据，清除全局配置下的校验
		if(extAttributes){
			for(var i=0;i<extAttributes.length;i++){
				if(extAttributes[i].name=="lbpmCustomizeValidateContentJson"){
					extAttributes[i].value = "";
				}
			}
		}
		$("#lbpmCustomizeValidateContentJson").val("");
	}
	
	$('input[type=radio][name=ext_isLbpmCustomizeContent]').change(function() {
		console.log("processType:"+this.value);
		if(this.value=="1"){
			$("#update_set").show();//显示div
		}else{
			$("#lbpmCustomizeContentJson").val("");
			$("#lbpmCustomizeValidateContentJson").val("");
			$("#update_set").hide();//隐藏div
		}
	});
	
});


//打开消息自定义窗口
function lbpmCustomizeFunc(dom){
	
	var dialog = new KMSSDialog();

	dialog.lbpmCustomizeParameter = {
			dataInfo : $("#lbpmCustomizeContentJson").val(),
			dataInfoValidate : $("#lbpmCustomizeValidateContentJson").val(),
			returnType: "String"
	};

	dialog.SetAfterShow(function(rtn){
		
		if(rtn && rtn.data&& rtn.data[0]){
			console.log("sdf"+rtn.data[0].jsonCustomize);
			$("#lbpmCustomizeContentJson").val(rtn.data[0].jsonCustomize);
			$("#lbpmCustomizeValidateContentJson").val(rtn.data[1].jsonCustomizeValidate);

		}
	});
		
	if(AttributeObject.NodeObject.Type=="signNode"){
		dialog.URL = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpm_usage/lbpmCustomizeUsageContentSign_edit.jsp";	
	}else if(AttributeObject.NodeObject.Type=="reviewNode" || AttributeObject.NodeObject.Type=="robtodoNode"){
		dialog.URL = Com_Parameter.ContextPath + "sys/lbpmservice/support/lbpm_usage/lbpmCustomizeUsageContent_edit.jsp";	
	}
	
	dialog.Show(window.screen.width*872/1366,window.screen.height*616/768);
}





</script>

