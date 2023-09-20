<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>

<ui:button parentId="toolbar" order="2" text="${param.text}"
  onclick="subside_doc('${JsParam.fdId}');">
</ui:button>
<script>
seajs.use(["lui/dialog"], function(dialog) {
	window.subside_doc = function(id) {
		if(id == null) return;
		var url = '${LUI_ContextPath}/kms/multidoc/kms_multidoc_subside/kmsMultidocSubside.do?method=fileDoc';
		dialog.confirm('<bean:message key="confirm.subside" bundle="kms-multidoc"/>',function(value){
			if(value==true){
				var serviceName = "";
				if("${param.isFlow}" == "true"){
					serviceName = 'modelingAppModelMainOprService';
				}else if("${param.isFlow}" == "false") {
					serviceName = 'modelingAppSimpleMainOprService';
				}
				if("${JsParam.serviceName}" != ""){
					serviceName = "${JsParam.serviceName}";
				}
				var fdModelName = "com.landray.kmss.sys.modeling.base.model.ModelingAppModel";
				if("${JsParam.fdModelName}" != ""){
					fdModelName = "${JsParam.fdModelName}";
				}
				window.file_load = dialog.loading();
				$.ajax({
					url: url,
					type: 'POST',
					data:{fdId:id,serviceName:serviceName},
					dataType: 'json',
					error: function(data){
						if(window.file_load!=null){
							window.file_load.hide(); 
						}
						dialog.alert('<bean:message key="kmsMultidocSubside.warnError" bundle="kms-multidoc"/>')
					},
					success: function(data){
						if(window.file_load!=null){
							window.file_load.hide(); 
						}
						if(data.status == true){
							dialog.confirm('<bean:message key="kmsMultidocSubside.isTurnTo" bundle="kms-multidoc"/>',function(value){
								if(value == true){
									var url = "${LUI_ContextPath}/kms/multidoc/kms_multidoc_knowledge/kmsMultidocKnowledge.do?method=view&fdId="+data.id;
									window.open(url,"_blank");
								}
							});
						}else{
                            if(data.errorMessage) {
                                dialog.alert("${lfn:message('sys-modeling-main:kms.multidoc.name.isExist')}");
                                return;
                            }
							var userSettingUrl = '/sys/modeling/main/mechanism/sediment/subsideSetting.jsp?fdModelName='+fdModelName+'&fdId='+id+'&serviceName='+serviceName+'&fdmodelId='+'${param.modelId}'+"&isFlow="+'${param.isFlow}';
							dialog.iframe(userSettingUrl,"${lfn:message('kms-multidoc:table.kmsMultidocSubside')}", function(value) {
		    				//	window.location.reload(true);
		    				}, {
		    					"width" : 640,
		    					"height" : 500,
		    					params : {
		    						
		    					}
		    				});
						//	dialog.alert('<bean:message key="kmsMultidocSubside.warnCategory" bundle="kms-multidoc"/>')
						}
					}
			   });
			}
		});
	};
});
</script>