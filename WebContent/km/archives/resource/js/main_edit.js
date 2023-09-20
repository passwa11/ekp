Com_AddEventListener(window,'load',function(){
	seajs.use(['lui/jquery','lui/dialog','lui/dialog_common'], function($, dialog, dialogCommon){
		var formValiduteObj = $KMSSValidation(document.forms[editOption.formName]);
		
		createNewFormBlankCate();
		function createNewFormBlankCate(){
			var url = location.href;
			var cateId = Com_GetUrlParameter(url,'i.docTemplate');
			var method = Com_GetUrlParameter(url,'method');
			if((cateId==null || cateId=='') && method=='add' && editOption.mode.substring(0,5)=='main_'){
				url = Com_SetUrlParameter(url,'i.docTemplate',null);
				url = url+ (url.indexOf("?")>-1?"&":"?") + "i.docTemplate=!{id}" 
				if(editOption.mode=='main_scategory'){
					dialog.simpleCategoryForNewFile(editOption.templateName, url,
			    			false,null,null,null,'_self');
				}else if(editOption.mode=='main_template'){
					dialog.categoryForNewFile(editOption.templateName, url,
				                false,null,null,null,'_self');
				}else if(editOption.mode=='main_other'){
					var context = editOption.createDialogCtx;
		    		var sourceUrl = context.sourceUrl;
		    		var params={};
		    		if(context.params){
		    			for(var i=0;i<context.params.length;i++){
			    			var argu = context.params[i];
			    			for(var field in argu){
			    				var tmpFieldObj = document.getElementsByName(field);
			    				if(tmpFieldObj.length>0){
			    					params['c.' + argu[field] + '.'+field] = tmpFieldObj[0].value;
			    				}
			    			}
			    		}
		    		}
		    		dialogCommon.dialogSelectForNewFile(context.modelName, sourceUrl, params, url, null, null, '_self');
				}
			}
		}
		
		function validateOpt(cancel){
			if(formValiduteObj!=null && editOption.subjectField!=''){
				if(cancel){
					formValiduteObj.removeElements(document.forms[editOption.formName],'required');
					formValiduteObj.resetElementsValidate($("input[name='" + editOption.subjectField + "']").get(0));
				}else{
					formValiduteObj.resetElementsValidate(document.forms[editOption.formName]);
				}
			}
		} 
		
		window.dialogSelect = function(mul, key, idField, nameField){
    		var dialogCfg = editOption.dialogs[key];
    		if(dialogCfg){
    			var params={};
	    		if(dialogCfg.params){
	    			for(var i=0;i<dialogCfg.params.length;i++){
		    			var argu = dialogCfg.params[i];
		    			for(var field in argu){
		    				var tmpFieldObj = document.getElementsByName(field);
		    				if(tmpFieldObj.length>0){
		    					params['c.' + argu[field] + '.'+field] = tmpFieldObj[0].value;
		    				}
		    			}
		    		}
	    		}
    			dialogCommon.dialogSelect(dialogCfg.modelName,
    					mul,dialogCfg.sourceUrl, params, idField, nameField);
    		}
    	}
		
		window.submitForm = function(status, method, isDraft){
			if(isDraft == true){
				validateOpt(true);
			}else{
				validateOpt(false);
			}
			var action = document.forms[editOption.formName].action;
			document.forms[editOption.formName].action = Com_SetUrlParameter(action,"docStatus",status);
			Com_Submit(document.forms[editOption.formName], method);
		}
	});
});
