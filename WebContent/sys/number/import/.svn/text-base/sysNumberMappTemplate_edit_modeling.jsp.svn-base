<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<c:set var="mainModelForm" value="${requestScope[param.formName]}" scope="request"></c:set>
<c:choose>
	<c:when test="${param.mechanismMap == 'true'}">
		<c:set var="sysNumberMainMappForm" value="${mainModelForm.mechanismMap['SysNumberMainMapp']}" scope="request"/>
		<c:set var="sysNumberMainMappPrefix" value="mechanismMap(SysNumberMainMapp)." scope="request"/>
	</c:when>
	<c:otherwise>
		<c:set var="sysNumberMainMappForm" value="${mainModelForm.sysNumberMainMappForm}" scope="request"/>
		<c:set var="sysNumberMainMappPrefix" value="sysNumberMainMappForm." scope="request"/>
	</c:otherwise>
</c:choose>
<script type="text/javascript">
Com_IncludeFile("doclist.js|dialog.js|calendar.js|optbar.js|jquery.js|json2.js|formula.js|eventbus.js|xform.js");
</script>
<table class="tb_normal" width="100%" id="TB_Template_sysNumberMainMapp" border=1>

	<!-- 选择特定编号规则时展示 -->
	<c:set var="number_iframe" value="${KMSS_Parameter_ContextPath}sys/number/sys_number_main/sysNumberMain.do?method=add&isCustom=1&modelName=${param.modelName}&fdKey=${param.fdKey }"/>
	<c:if test="${sysNumberMainMappForm.fdNumberId!=null && fn:length(sysNumberMainMappForm.fdNumberId)>10}">
		<c:set var="number_iframe" value="${KMSS_Parameter_ContextPath}sys/number/sys_number_main/sysNumberMain.do?method=edit&isCustom=1&fdId=${sysNumberMainMappForm.fdNumberId}&modelName=${param.modelName}&fdKey=${param.fdKey }"/>
	</c:if>
	<tr  id="TR_ID_sysNumberMainMapp_showNumber">
		<td width="100%" colspan="2"  onresize="number_LoadIframe();">
			 <iframe id="iframeNumberCustomPage" src="${number_iframe}"
			 	width="100%" height="100%" scrolling="no" frameborder="0">
			 </iframe>
		</td>
	</tr>
</table>
 
<html:hidden property="${sysNumberMainMappPrefix}fdType" value="2"/>
<html:hidden property="${sysNumberMainMappPrefix}fdNumberId" value="-1"/>
<html:hidden property="${sysNumberMainMappPrefix}fdMainModelName" value="${HtmlParam.modelName}"/>
<html:hidden property="${sysNumberMainMappPrefix}fdContent"/>
<!-- 流水号补零信息 -->
<html:hidden property="${sysNumberMainMappPrefix}fdFlowContent"/>

<script type="text/javascript">

	function number_isExists(str){
		var dataObj = new Object();
		dataObj.modelName = "${param.modelName}";
		dataObj.templateId = "${param.templateId}";
		dataObj.fdContent = encodeURIComponent(str);
		dataObj.s_seq = Math.random(); 
		$.ajax({
            type: "post", 
            url: "${KMSS_Parameter_ContextPath}sys/number/sys_number_main/sysNumberMain.do?method=isExistsNumberRule&isCustom=1",
            data: dataObj, 
            dataType: "text", 
            async:false,
            contentType:"application/x-www-form-urlencoded;charset=utf-8",
            success: function (data) {
                if(data == null || data===undefined || data=='null' || data==''){
                	temp_control_commit = 1 ;
                    return;
                }
                if(confirm('<bean:message bundle="sys-number" key="sysNumber.sysConfirmInfo"/><bean:message bundle="sys-number" key="sysNumber.isContinue"/>')){
                	temp_control_commit = 1;
                }else{
                	temp_control_commit = 2;
                }
            }
		});
	}
	
	Com_Parameter.event["submit"][Com_Parameter.event["submit"].length] = function(){
		var mName = document.getElementsByName("${sysNumberMainMappPrefix}fdMainModelName")[0];
		if(mName.value == ""){
			mName.value = "${param.modelName}";
		}
		document.getElementsByName("${sysNumberMainMappPrefix}fdNumberId")[0].value = "-1";
		var frameObj = window.frames['iframeNumberCustomPage'].contentWindow?window.frames['iframeNumberCustomPage'].contentWindow:window.frames['iframeNumberCustomPage'];
		if(frameObj.document.getElementsByName("fdFlowContent").length>0){
			var fdFlowContentDom = frameObj.document.getElementsByName("fdFlowContent")[0];
			var frameFdFlowContent = fdFlowContentDom ? fdFlowContentDom.value : '';
			document.getElementsByName("${sysNumberMainMappPrefix}fdFlowContent")[0].value = frameFdFlowContent;
		}

		var iframeRuleValue = '[]';
		if(frameObj.document.getElementsByName("fdContent").length>0){
			var iframeRuleValue = frameObj.document.getElementsByName("fdContent")[0].value;
			frameObj.eleInit(iframeRuleValue);
			if(frameObj.Page_CanCommit){
				alert('<bean:message bundle="sys-number" key="sysNumber.error.OneIsNotEmpty"/>');
				return false;
			}
			document.getElementsByName("${sysNumberMainMappPrefix}fdContent")[0].value = iframeRuleValue;
		}else{
			iframeRuleValue = document.getElementsByName("${sysNumberMainMappPrefix}fdContent")[0].value ;
		}
		if(_numberSet==true){
			if(!iframeRuleValue || iframeRuleValue=='[]'){
				alert('<bean:message bundle="sys-number" key="sysNumber.error.numberIsNotEmpty"/>');
				return false;
			}
			number_isExists(iframeRuleValue);
			if(temp_control_commit==1){
				return true;
			}else{
				return false;
			}
		}else{
			document.getElementsByName("${sysNumberMainMappPrefix}fdFlowContent")[0].value="[]";
			document.getElementsByName("${sysNumberMainMappPrefix}fdContent")[0].value="[]";
		}
	 	return true;
	};
	//$(document).ready(number_showData);
</script>
