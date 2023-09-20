<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>
<template:include ref="default.simple" sidebar="no">
<template:replace name="body">
<link rel="Stylesheet" href="${LUI_ContextPath}/sys/circulation/resource/css/circulate.css?s_cache=${MUI_Cache}" />
<script type="text/javascript">
seajs.use(['theme!form']);
Com_Parameter.CloseInfo="<bean:message key="message.closeWindow"/>";
Com_IncludeFile("validation.jsp|validation.js|plugin.js|eventbus.js|xform.js", null, "js");
</script>	
	<c:if test="${existOpinion eq 'true'}">
	<div class="opinionContainer" id="opinionForm">
		<html:form action="/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do">
			<div  width=100%>
			 <table class="tb_simple" width=100%>
			 	<tr>
			 		<td>
			 			<html:hidden property="fdId" />
						<html:hidden property="sysCirculationMainId" />
						<html:hidden property="fdBelongPersonId" />
						<xform:textarea property="docContent" style="height:100px;width:96%" showStatus="edit" required="${required}" subject="${ lfn:message('sys-circulation:sysCirculationMain.fdOpinion') }"></xform:textarea>
			 		</td>
			 	</tr>
			 	<tr>
			 		<td>
			 			<div>
							<div class="lui_form_subhead">
								<img src="${KMSS_Parameter_ContextPath}sys/attachment/view/img/attachment.png">&nbsp;<bean:message bundle="sys-circulation" key="sysCirculationMain.attachment" />
							</div>
							<c:import url="/sys/attachment/sys_att_main/sysAttMain_edit.jsp" charEncoding="UTF-8">
								<c:param name="fdKey" value="attachment" />
								<c:param name="fdMulti" value="true" />
								<c:param name="fdModelId" value="${sysCirculationOpinionForm.fdId }" />
								<c:param name="addToPreview" value="false" />
								<c:param name="fdModelName" value="com.landray.kmss.sys.circulation.model.SysCirculationOpinion" />
							</c:import>
						</div>
			 		</td>
			 	</tr>
			 	<html:hidden property="method_GET" />
			 </table>
			</div>
			<script language="JavaScript">
				var validation = $KMSSValidation(document.forms['sysCirculationOpinionForm']);
			</script>
			<div style="padding-top: 15px; text-align:right;padding-right:10px;">
				<ui:button text="${ lfn:message('button.submit') }" id="submit"  onclick="submitForm();">
				</ui:button>
		    </div>
		</html:form>
		<script>
			seajs.use(['lui/jquery','lui/dialog','lui/topic'], function($, dialog , topic) {
				window.submitForm = function(){
					if(validation.validate()){
						if(document.getElementsByName("docContent")[0].value == ""){
							document.getElementsByName("docContent")[0].value = '<bean:message bundle="sys-circulation" key="sysCirculationOpinion.read" />';
						}
						Com_Submit(document.forms['sysCirculationOpinionForm'], 'updateOpinion');
						/*
						var url='<c:url value="/sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=updateOpinion"/>';
						var fdId = document.getElementsByName("fdId")[0].value;
						var docContent = document.getElementsByName("docContent")[0].value;
						if(docContent == ""){
							docContent = '<bean:message bundle="sys-circulation" key="sysCirculationOpinion.read" />';
						}
						$.ajax({
							url: url,
							type: 'POST',
							data:$.param({"fdId":fdId,docContent:docContent},true),
							error: function(data){
								if(window.file_load!=null){
									window.file_load.hide(); 
								}
								dialog.result(data.responseJSON);
							},
							success: function(data){
								var results =  eval("("+data+")");
								if(results['repeat'] == 'true'){
									dialog.alert('您已填写过意见!');
								}else{
									if(results['flag'] == 'true'){
										$("#opinionForm").remove();
										LUI('opinionList').source.get();
									}
								}
							}
					   });
					 */
					}
				}
			});
			</script>
	</div>
	<c:if test="${'1' eq sysCirculationMain.fdRegular}">
		<div class="nextInfoContainer">
			<div class="label_title"> 
		       <div class="title"><bean:message bundle="sys-circulation" key="sysCirculationMain.next" /></div>
		    </div>
			<div class="nextInfo"><bean:message bundle="sys-circulation" key="sysCirculationMain.next" />:${not empty nextPerson?nextPerson:'传阅结束'}</div>
			<div class="nextInfoTips"><bean:message bundle="sys-circulation" key="sysCirculationMain.tip" /></div>
		</div>	
	</c:if>
	<ui:iframe src="${KMSS_Parameter_ContextPath}sys/circulation/sys_circulation_opinion/sysCirculationOpinion.do?method=listOpinion&fdMainId=${sysCirculationMain.fdId}&isOpinion=true">
	</ui:iframe>
	</c:if>
	</template:replace>
</template:include>
