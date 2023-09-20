<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/resource/jsp/common.jsp"%>
<%@ include
	file="/kms/common/kms_common_push/kms_common_data_push_script.jsp"%>
	
<kmss:authShow roles="ROLE_KMSCOMMON_DEFAULT">
	<ui:dataview>
		<ui:source type="AjaxJson">
				{url:'${url}'}
			</ui:source>
		<ui:render type="Javascript">
		
			seajs.use(['lui/toolbar'],function(toolbar){
			
				function addButton(){
					for(var i=0;i<data.unsolveAskList.length;i++){
						var json = data.unsolveAskList[i];
						if(!json.isPushed){
							var button = toolbar.buildButton({order:'4',text:'推送到'+json.modelSubject,click:'datapush("'+json.modelName+'","'+json.fdId+'","'+json.cateModelName+'")'});
							LUI('toolbar').addButton(button);
						}
						
					}
				}
			
				if(LUI('toolbar')){
					addButton();
				}else{
					LUI.ready(function(){
						addButton(); 
					})
				}
				
			})
		</ui:render>
	</ui:dataview>
</kmss:authShow>
