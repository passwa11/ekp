Com_AddEventListener(window,'load',function(){
	seajs.use(['lui/jquery','lui/dialog','fssc/common/resource/js/dialog_common','lui/util/str','lang!fssc-budget','lui/util/env'], function($, dialog, dialogCommon,strutil,lang,env){
		var formValiduteObj = $KMSSValidation(document.forms[formOption.formName]);
		createNewFormBlankCate();
		function createNewFormBlankCate(){
			var url = location.href;
			var cateId = Com_GetUrlParameter(url,'i.docTemplate');
			var method = Com_GetUrlParameter(url,'method');
			if((cateId==null || cateId=='') && method=='add' && formOption.mode.substring(0,5)=='main_'){
				url = Com_SetUrlParameter(url,'i.docTemplate',null);
				url = url+ (url.indexOf("?")>-1?"&":"?") + "i.docTemplate=!{id}" 
				if(formOption.mode=='main_scategory'){
					dialog.simpleCategoryForNewFile(formOption.templateName, url,
			    			false,null,null,null,'_self');
				}else if(formOption.mode=='main_template'){
					dialog.categoryForNewFile(formOption.templateName, url,
				                false,null,null,null,'_self');
				}else if(formOption.mode=='main_other'){
					var context = formOption.createDialogCtx;
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
			if(formValiduteObj!=null && formOption.subjectField!=''){
				if(cancel){
					formValiduteObj.removeElements(document.forms[formOption.formName],'required');
					formValiduteObj.resetElementsValidate($("input[name='" + formOption.subjectField + "']").get(0));
				}else{
					formValiduteObj.resetElementsValidate(document.forms[formOption.formName]);
				}
			}
		} 
		
		window.dialogSelect=function(mul, key, idField, nameField,action,extendParam){
		    var targetWin = targetWin||window;
			if(key==null||key==''){
				window.alert("请修改此段代码");
				return;
			}
			if((idField.indexOf('*')>-1||idField.indexOf('[')>-1) && window.DocListFunc_GetParentByTagName){
				//明细表
				dialogSelectForDetail(mul, key, idField, nameField,action,extendParam);
			}else{
				var dialogCfg = formOption.dialogs[key];
	    		if(dialogCfg){
	    			var params='';
	    			if(extendParam){
	    				for(var i in extendParam){
	    					params+='&'+i+"="+extendParam[i];
	    				}
	    			}
	    			var inputs=getDialogInputs(idField);
	    			if(inputs){
	    				for(var i=0;i<inputs.length;i++){
	    					var argu = inputs[i];
	    					var modelVal=$form(argu["value"]).val();
	    					if(modelVal==null||modelVal==''){
	    						if(argu["required"]=="true"){
	    							alert("对话框参数未配置");
	    							return;
	    						}
	    						params+='&'+argu["key"]+'='+formInitData[argu["value"]];
	    					}else{
	    						params+='&'+argu["key"]+'='+modelVal;
	    					}
	    				}
	    			}
	    			var type=getParamValueByName(dialogCfg.sourceUrl,"type");
	    			if("byScheme"==type){//根据预算方案对应的使用公司过滤所选公司
	    				params+='&fdBudgetSchemeId='+$("[name='fdBudgetSchemeId']").val();
	    			}
	    			targetWin['__dialog_' + idField + '_dataSource'] = function(){
                        return strutil.variableResolver(dialogCfg.sourceUrl+params ,null);
                    }
	    			dialogCommon.dialogSelect(dialogCfg.modelName,
	    					mul,dialogCfg.sourceUrl+params, null, idField, nameField,null,function(data){
	    				if(action){
	    					action(data);
	    				}
	    				var outputs=getDialogOutputs(idField);
	    				if(outputs){
							if(data.length==1){
								for(var i=0;i<outputs.length;i++){
									var output=outputs[i];
									$form(output["value"]).val(data[0][output["key"]]);
	        					}
							}
	    				}
	    			});
	    		}
			}
    	}
		
		window.batchAddRow=function(key,idField,nameField,displayProp,optTB){
			if(optTB==null)
				optTB = DocListFunc_GetParentByTagName("TABLE");
			else if(typeof(optTB)=="string")
				optTB = document.getElementById(optTB);
			var dialogCfg = formOption.dialogs[key];
    		if(dialogCfg){
    			var params='';
    			var inputs=getDialogInputs(idField);
    			if(inputs){
    				for(var i=0;i<inputs.length;i++){
    					var argu = inputs[i];
    					if(argu["value"].indexOf('*')>-1){
    						//入参来自明细表,不处理
    					}else{
    						//入参来自主表
    						var modelVal=$form(argu["value"]).val();
	    					if(modelVal==null||modelVal==''){
	    						if(argu["required"]=="true"){
	    							alert("对话框参数未配置");
	    							return;
	    						}
	    						params+='&'+argu["key"]+'='+formInitData[argu["value"]];
	    					}else{
	    						params+='&'+argu["key"]+'='+modelVal;
	    					}
    					}
    					
    				}
    			}
    			dialogCommon.dialogSelect(dialogCfg.modelName,
    					true,dialogCfg.sourceUrl+params, null, null, null,null,function(data){
    				if(data.length>0){
    					var outputs=getDialogOutputs(idField);
    					for(var i=0;i<data.length;i++){
            				var newRow=DocList_AddRow(optTB);
    						var tbInfo = DocList_TableInfo[optTB.id];
        					var refIdField=idField.replace("*",newRow.rowIndex-tbInfo.firstIndex); 
        					//id,name
        					$form(idField,refIdField).val(data[i]['fdId']);
        					$form(nameField,refIdField).val(data[i][displayProp]);
        					//出参
        					if(outputs){
            					for(var j=0;j<outputs.length;j++){
    								var output=outputs[j];
    								$form(output["value"],refIdField).val(data[i][output["key"]]);
            					}
        					}
    					}
    				}
    			});
    		}
		};
		
		function dialogSelectForDetail(mul, key, idField, nameField,action,extendParam){
			var tr=DocListFunc_GetParentByTagName('TR');
			var tb= DocListFunc_GetParentByTagName("TABLE");
			var tbInfo = DocList_TableInfo[tb.id];
			var refIdField=idField.replace("*",tr.rowIndex-tbInfo.firstIndex); 
			var refNameField=nameField.replace("*",tr.rowIndex-tbInfo.firstIndex); 
			var dialogCfg = formOption.dialogs[key];
    		if(dialogCfg){
    			var params='';
    			if(extendParam){
    				for(var i in extendParam){
    					params+='&'+i+"="+extendParam[i];
    				}
    			}
    			var inputs=getDialogInputs(idField);
    			if(inputs){
    				for(var i=0;i<inputs.length;i++){
    					var argu = inputs[i];
    					if(argu["value"].indexOf('*')>-1){
    						//入参来自明细表
    						var modelVal=$form(argu["value"],refIdField).val();
        					if(modelVal==null||modelVal==''){
        						if(argu["required"]=="true"){
        							alert("对话框参数未配置");
        							return;
        						}
        					}else{
        						params+='&'+argu["key"]+'='+modelVal;
        					}
    					}else{
    						//入参来自主表
    						var modelVal=$form(argu["value"]).val();
	    					if(modelVal==null||modelVal==''){
	    						if(argu["required"]=="true"){
	    							alert("对话框参数未配置");
	    							return;
	    						}
	    						params+='&'+argu["key"]+'='+formInitData[argu["value"]];
	    					}else{
	    						params+='&'+argu["key"]+'='+modelVal;
	    					}
    					}
    					
    				}
    			}
    			dialogCommon.dialogSelect(dialogCfg.modelName,
    					mul,dialogCfg.sourceUrl+params, null, refIdField, refNameField,null,function(data){
    				if(action){
    					if(data){
    						data.field=refIdField;
    					}
    					action(data);
    				}
    				var outputs=getDialogOutputs(idField);
    				if(outputs){
						if(data.length==1){
							for(var i=0;i<outputs.length;i++){
								var output=outputs[i];
								$form(output["value"],refIdField).val(data[0][output["key"]]);
        					}
						}
    				}
    			});
    		}
		}
		
		function getDialogInputs(idField){
			var dialogLinks=formOption.dialogLinks;
			if(dialogLinks==null||dialogLinks.length==0){
				return null;
			}
			for(var i=0;i<dialogLinks.length;i++){
				var dialogLink=dialogLinks[i];
				if(idField==dialogLink.idField){
					return dialogLink.inputs;
				}
			}
			return null;
		};
		
		function getDialogOutputs(idField){
			var dialogLinks=formOption.dialogLinks;
			if(dialogLinks==null||dialogLinks.length==0){
				return null;
			}
			for(var i=0;i<dialogLinks.length;i++){
				var dialogLink=dialogLinks[i];
				if(idField==dialogLink.idField){
					return dialogLink.outputs;
				}
			}
			return null;
		};
		
		window.submitForm = function(status, method, isDraft){
			$("input[name='IsDraft']").val(isDraft);
			if(isDraft == true){
				validateOpt(true);
			}else{
				validateOpt(false);
			}
			$("[name=docStatus]").val(status);
			var action = document.forms[formOption.formName].action;
			document.forms[formOption.formName].action = Com_SetUrlParameter(action,"docStatus",status);
			Com_Submit(document.forms[formOption.formName], method);
		}
		
		//对话框联动清空值
		bindDialogLink();
		function bindDialogLink(){
			var dialogLinks=formOption.dialogLinks;
			if(dialogLinks==null||dialogLinks.length==0){
				return null;
			}
			for(var i=0;i<dialogLinks.length;i++){
				var sourceFields=[];
				var targetFields=[];
				var dialogLink=dialogLinks[i];
				for(var j=0;j<dialogLink.inputs.length;j++){
					var input=dialogLink.inputs[j];
					sourceFields.push(input['value']);
				}
				for(var k=0;k<dialogLink.outputs.length;k++){
					var output=dialogLink.outputs[k];
					targetFields.push(output['value']);
				}
				targetFields.push(dialogLink.idField);
				targetFields.push(dialogLink.nameField);
				$form.bind({
					field:sourceFields,
					targetFields : targetFields,
					onValueChange:function(event){
						var targetFields = event.listener.targetFields;
						for(var i=0;i<targetFields.length;i++){
							$form(targetFields[i], event.field).val(''); 
						}
					}
				});
			}
		}

		// 函数注册
		formOption.fn = {
			sum : function(data, detailTable, func){
				var size = formOption.fn.count(data, detailTable);
				var result = 0;
				for(var i=0; i<size; i++){
					result += func(data, i);
				}
				return result;
			},
			avg : function(data, detailTable, func){
				var size = formOption.fn.count(data, detailTable);
				if(size==0){
					return 0;
				}
				return formOption.fn.sum(data, detailTable, func)*1.0/size;
			},
			count : function(data, detailTable){
				for(var o in data){
					var _detailTable = getDetailTable(o);
					if(_detailTable==detailTable && $.isArray(data[o])){
						return data[o].length;
					}
				}
				return 0;
			},
			dateDiffMin:function(beginTime,endTime){
				return Math.round((endTime.getTime()-beginTime.getTime())/1000/60);
			},
			dateDiffDay:function(beginTime,endTime){
				return Math.round((endTime.getTime()-beginTime.getTime())/1000/60/60/24);
			},
			dateMerge:function(date,time){
				return new Date(date.getTime()+time.getTime());
			},
			dateAddDay:function(date,add){
				return new Date(date.getTime()+add*24*60*60*1000);
			},
			dateAddMin:function(date,add){
				return new Date(date.getTime()+add*60*1000);
			}
		};

		function getDetailTable(field){
			var index = field.indexOf('[');
			if(index==-1){
				return null;
			}
			return field.substring(0, index);
		}
		// key:detailTableId, value:[attrLink]
		var detailTableAttrLinks = {};
		bindAttrLinks();
		function bindAttrLinks(){
			if(formOption.attrLinks==null || formOption.attrLinks.length==0){
				return;
			}
			for(var i=0;i<formOption.attrLinks.length;i++){
				var attrLink = formOption.attrLinks[i];
				var conditions = attrLink.conditions;
				var inputs = [];
				var detailTables = [];
				for(var j=0; j<conditions.length; j++){
					var condition = conditions[j];
					if(condition.inputs){
						for(var k=0; k<condition.inputs.length; k++){
							if($.inArray(condition.inputs[k], inputs)==-1){
								inputs.push(condition.inputs[k]);
								var detailTable = getDetailTable(condition.inputs[k]);
								if(detailTable && $.inArray(detailTable, detailTables)==-1){
									detailTables.push(detailTable);
								}
							}
						}
					}
				}
				attrLink.inputs = inputs;
				// 明细表事件
				for(var j=0; j<detailTables.length; j++){
					var detailTableAttrLink = detailTableAttrLinks['TABLE_DocList_'+detailTables[j]];
					if(detailTableAttrLink==null){
						detailTableAttrLink = detailTableAttrLinks['TABLE_DocList_'+detailTables[j]] = [];
					}
					detailTableAttrLink.push(attrLink);
				}
				// 表单事件
				$form.bind({
					field : inputs,
					attrLink : attrLink,
					onValueChange:function(event){
						var eventDetail = getDetailTable(event.field);
						if(eventDetail){
							var detailTable = getDetailTable(event.listener.attrLink.outputs[0]);
							if(eventDetail==detailTable){
								eventDetail = event.field.substring(eventDetail.length+1, event.field.indexOf(']'));
							}else{
								eventDetail = null;
							}
						}
						fireAttrLink(event.listener.attrLink, eventDetail);
					}
				});
				// 初次加载
				fireAttrLink(attrLink);
			}
		}
		function fireAttrLink(attrLink, fieldIndex){
			var detailTable = getDetailTable(attrLink.outputs[0]);
			var data = {};
			if(detailTable){
				// 明细表
				if(fieldIndex==null){
					// 不带下标，分开计算
					var size = $form(attrLink.outputs[0]).size();
					for(var i=0; i<size; i++){
						execCondition(i);
					}
				}else{
					// 带下标
					execCondition(fieldIndex);
				}
			}else{
				// 非明细表
				execCondition();
			}
			function execCondition(fieldIndex){
				for(var i=0; i<attrLink.conditions.length; i++){
					var condition = attrLink.conditions[i];
					var result = execLinkValue(data, condition.inputs, condition.value, fieldIndex);
					if(result==true){
						for(var j=0; j<attrLink.outputs.length; j++){
							if(fieldIndex!=null){
								$form(attrLink.outputs[j].replace('*', fieldIndex)).editLevel(condition.editLevel);
							}else{
								$form(attrLink.outputs[j]).editLevel(condition.editLevel);
							}
						}
						return;
					}
				}
			}
		}

		function execLinkValue(data, inputs, func, fieldIndex){
			if(inputs){
				for(var i=0; i<inputs.length; i++){
					var input = inputs[i];
					if(data[input]==null || fieldIndex!=null && input.indexOf('*')>-1){
						// 加载数据
						var value = null;
						if(input.indexOf('*')>-1){
							if(fieldIndex!=null){
								value = $form(input.replace('*', fieldIndex)).val();
							}else{
								value = $form(input).val();
							}
						}else{
							value = $form(input).val();
							if(value==null && formInitData!=null){
								value = formInitData[input];
							}
						}
						value = $form.format(value, formOption.dataType[input]);
						if($.isArray(value)){
							for(var j=0; j<value.length; j++){
								if(value[j]==null){
									return null;
								}
							}
						}else{
							if(value==null){
								return null;
							}
						}
						data[input] = value;
					}
				}
			}
			return func(data);
		}
		
		var detailTableOptionLinks={};
		bindOptionLinks();
		function bindOptionLinks(){
			if(formOption.optionLinks==null||formOption.optionLinks.length==0){
				return;
			}
			for(var i=0;i<formOption.optionLinks.length;i++){
				var optionLink = formOption.optionLinks[i];
				var conditions = optionLink.conditions;
				var inputs = [];
				var detailTables = [];
				for(var j=0; j<conditions.length; j++){
					var condition = conditions[j];
					if(condition.inputs){
						for(var k=0; k<condition.inputs.length; k++){
							if($.inArray(condition.inputs[k], inputs)==-1){
								inputs.push(condition.inputs[k]);
								var detailTable = getDetailTable(condition.inputs[k]);
								if(detailTable && $.inArray(detailTable, detailTables)==-1){
									detailTables.push(detailTable);
								}
							}
						}
					}
				}
				optionLink.inputs = inputs;
				// 明细表事件
				for(var j=0; j<detailTables.length; j++){
					var detailTableOptionLink = detailTableOptionLinks['TABLE_DocList_'+detailTables[j]];
					if(detailTableOptionLink==null){
						detailTableOptionLink = detailTableOptionLinks['TABLE_DocList_'+detailTables[j]] = [];
					}
					detailTableOptionLink.push(optionLink);
				}
				// 表单事件
				$form.bind({
					field : inputs,
					optionLink : optionLink,
					onValueChange:function(event){
						var eventDetail = getDetailTable(event.field);
						if(eventDetail){
							var detailTable = getDetailTable(event.listener.optionLink.output);
							if(eventDetail==detailTable){
								eventDetail = event.field.substring(eventDetail.length+1, event.field.indexOf(']'));
							}else{
								eventDetail = null;
							}
						}
						fireOptionLink(event.listener.optionLink, eventDetail);
					}
				});
				// 初次加载
				fireOptionLink(optionLink);
			}
		}
		
		function fireOptionLink(optionLink, fieldIndex){
			var detailTable = getDetailTable(optionLink.output);
			var data = {};
			if(detailTable){
				// 明细表
				if(fieldIndex==null){
					// 不带下标，分开计算
					var size = $form(optionLink.output).size();
					for(var i=0; i<size; i++){
						execCondition(i);
					}
				}else{
					// 带下标
					execCondition(fieldIndex);
				}
			}else{
				// 非明细表
				execCondition();
			}
			function execCondition(fieldIndex){
				for(var i=0; i<optionLink.conditions.length; i++){
					var condition = optionLink.conditions[i];
					var result = execLinkValue(data, condition.inputs, condition.value, fieldIndex);
					if(result==true){
						var optionVal=condition.optionValue;
						if(fieldIndex!=null){
							if('radio'==optionLink.displayType){
								var oldVal=$('input[name="'+optionLink.output.replace('*', fieldIndex)+'"]:checked').val();
								$('input[name="'+optionLink.output.replace('*', fieldIndex)+'"]').parent().hide();
								$('input[name="'+optionLink.output.replace('*', fieldIndex)+'"]').removeAttr('checked');
								for(var j=0;j<optionVal.length;j++){
									if(optionVal[j].value==oldVal){
										$('input[name="'+optionLink.output.replace('*', fieldIndex)+'"][value="'+optionVal[j].value+'"]').prop('checked',true);
									}
									$('input[name="'+optionLink.output.replace('*', fieldIndex)+'"][value="'+optionVal[j].value+'"]').parent().show();
								}
							}else if('select'==optionLink.displayType){
								var oldVal=$('select[name="'+optionLink.output.replace('*', fieldIndex)+'"] option:selected').val();
								$('select[name="'+optionLink.output.replace('*', fieldIndex)+'"] option').each(function(){
									if($(this).val().length>0){
										$(this).attr('selected',false);
										$(this).hide();
									}
								});
								for(var j=0;j<optionVal.length;j++){
									if(optionVal[j].value==oldVal){
										$('select[name="'+optionLink.output.replace('*', fieldIndex)+'"] option[value="'+optionVal[j].value+'"]').prop('selected',true);
									}
									$('select[name="'+optionLink.output.replace('*', fieldIndex)+'"] option[value="'+optionVal[j].value+'"]').show();
								}
							}
						}else{
							if('radio'==optionLink.displayType){
								var oldVal=$('input[name="'+optionLink.output+'"]:checked').val();
								$('input[name="'+optionLink.output+'"]').parent().hide();
								$('input[name="'+optionLink.output+'"]').removeAttr('checked');
								for(var j=0;j<optionVal.length;j++){
									if(optionVal[j].value==oldVal){
										$('input[name="'+optionLink.output+'"][value="'+optionVal[j].value+'"]').prop('checked',true);
									}
									$('input[name="'+optionLink.output+'"][value="'+optionVal[j].value+'"]').parent().show();
								}
							}else if('select'==optionLink.displayType){
								var oldVal=$('select[name="'+optionLink.output+'"] option:selected').val();
								$('select[name="'+optionLink.output+'"] option').each(function(){
									if($(this).val().length>0){
										$(this).attr('selected',false);
										$(this).hide();
									}
								});
								for(var j=0;j<optionVal.length;j++){
									if(optionVal[j].value==oldVal){
										$('select[name="'+optionLink.output+'"] option[value="'+optionVal[j].value+'"]').prop('selected',true);
									}
									$('select[name="'+optionLink.output+'"] option[value="'+optionVal[j].value+'"]').show();
								}
							}
						}
						return;
					}
				}
			}
		}
		
		
		var detailTableValueLinks = {};
		bindValueLinks();
		function bindValueLinks(){
			if(formOption.valueLinks==null || formOption.valueLinks.length==0){
				return;
			}
			for(var i=0;i<formOption.valueLinks.length;i++){
				var valueLink = formOption.valueLinks[i];
				var inputs = valueLink.inputs;
				var detailTables = [];
				if(inputs){
					for(var j=0; j<inputs.length; j++){
						var input = inputs[j];
						var detailTable = getDetailTable(input);
						if(detailTable && $.inArray(detailTable, detailTables)==-1){
							detailTables.push(detailTable);
						}
					}
				}
				// 明细表事件
				for(var j=0; j<detailTables.length; j++){
					var detailTableValueLink = detailTableValueLinks['TABLE_DocList_'+detailTables[j]];
					if(detailTableValueLink==null){
						detailTableValueLink = detailTableValueLinks['TABLE_DocList_'+detailTables[j]] = [];
					}
					detailTableValueLink.push(valueLink);
				}
				// 表单事件
				$form.bind({
					field : inputs,
					valueLink : valueLink,
					onValueChange:function(event){
						var eventDetail = getDetailTable(event.field);
						if(eventDetail){
							var detailTable = getDetailTable(event.listener.valueLink.output);
							if(eventDetail==detailTable){
								eventDetail = event.field.substring(eventDetail.length+1, event.field.indexOf(']'));
							}else{
								eventDetail = null;
							}
						}
						fireValueLink(event.listener.valueLink, eventDetail);
					}
				});
			}
		}
		function fireValueLink(valueLink, fieldIndex){
			var detailTable = getDetailTable(valueLink.output);
			var data = {};
			if(detailTable){
				// 明细表
				if(fieldIndex==null){
					// 不带下标，分开计算
					var size = $form(valueLink.output).size();
					for(var i=0; i<size; i++){
						execValue(i);
					}
				}else{
					// 带下标
					execValue(fieldIndex);
				}
			}else{
				// 非明细表
				execValue();
			}
			function execValue(fieldIndex){
				var result = execLinkValue(data, valueLink.inputs, valueLink.value, fieldIndex);
				if(result!=null){
					result=$form.str(result,formOption.dataType[valueLink.output]);
					if(fieldIndex!=null){
						$form(valueLink.output.replace('*', fieldIndex)).val(result);
					}else{
						$form(valueLink.output).val(result);
					}
				}
			}
		}
		
		bindDetailTable();
		function bindDetailTable(){
			if(window.DocList_Info==null){
				return;
			}
			for(var i=0; i<DocList_Info.length; i++){
				var tb = document.getElementById(DocList_Info[i]);
				if(tb==null){
					continue;
				}
				$(tb).bind({
					'table-add':function(e, row){
						var tbId = e.target.id;
						var fieldIndex = row.rowIndex - DocList_TableInfo[tbId].firstIndex;
						if(detailTableAttrLinks[tbId]){
							for(var i=0; i<detailTableAttrLinks[tbId].length; i++){
								fireAttrLink(detailTableAttrLinks[tbId][i], fieldIndex);
							}
						}
						if(detailTableValueLinks[tbId]){
							for(var i=0; i<detailTableValueLinks[tbId].length; i++){
								fireValueLink(detailTableValueLinks[tbId][i], fieldIndex);
							}
						}
						if(detailTableOptionLinks[tbId]){
							for(var i=0; i<detailTableOptionLinks[tbId].length; i++){
								fireOptionLink(detailTableOptionLinks[tbId][i], fieldIndex);
							}
						}
					},
					'table-delete':function(e){
						var tbId = e.target.id;
						setTimeout(function(){
							if(detailTableAttrLinks[tbId]){
								for(var i=0; i<detailTableAttrLinks[tbId].length; i++){
									var attrLink = detailTableAttrLinks[tbId][i];
									if(getDetailTable(attrLink.outputs[0])==null){
										fireAttrLink(attrLink);
									}
								}
							}
							if(detailTableValueLinks[tbId]){
								for(var i=0; i<detailTableValueLinks[tbId].length; i++){
									var valueLink = detailTableValueLinks[tbId][i];
									if(getDetailTable(valueLink.output)==null){
										fireValueLink(valueLink);
									}
								}
							}
							if(detailTableOptionLinks[tbId]){
								for(var i=0; i<detailTableOptionLinks[tbId].length; i++){
									var optionLink = detailTableOptionLinks[tbId][i];
									if(getDetailTable(optionLink.output)==null){
										fireOptionLink(optionLink);
									}
								}
							}
						}, 100);
					}
				});
			}
		}
		
		addLinkValidates();
		function addLinkValidates(){
			var linkValidates=formOption.linkValidates;
			if(linkValidates!=null&&linkValidates.length>0){
				var _validation = $KMSSValidation();
				for(var i=0;i<linkValidates.length;i++){
					var iden=linkValidates[i].iden;
					var message=linkValidates[i].message;
					_validation.addValidator(iden,message,function(v,e,o){
						var linkValidate=getLinkValidateByIden(o.iden+'(iden)');
						if(linkValidate==null){
							return true;
						}
						var func=linkValidate.value;
						var inputs=linkValidate.inputs;
						var outputs=linkValidate.outputs;
						var data={};
						var detailTable = getDetailTable(outputs[0]);
						var eventDetail = getDetailTable(e.name);
						if(eventDetail){
							if(eventDetail==detailTable){
								eventDetail = e.name.substring(eventDetail.length+1,e.name.indexOf(']'));
							}else{
								eventDetail = null;
							}
						}
						if(detailTable){
							// 明细表
							if(eventDetail==null){
								// 不带下标，分开计算
								var size = $form(outputs[0]).size();
								for(var i=0; i<size; i++){
									var result=execLinkValue(data,inputs,func,i);
									return result==null?true:result;
								}
							}else{
								// 带下标
								var result=execLinkValue(data,inputs,func,eventDetail);
								return result==null?true:result;
							}
						}else{
							// 非明细表
							var result=execLinkValue(data,inputs,func);
							return result==null?true:result;
						}
					});
				}
			}
		}
		
		function getLinkValidateByIden(iden){
			var linkValidates=formOption.linkValidates;
			if(linkValidates!=null||linkValidates.length>0){
				for(var i=0;i<linkValidates.length;i++){
					if(iden==linkValidates[i].iden){
						return linkValidates[i];
					}
				}
			}
			return null;
		}
		
		//获取链接中的参数名及参数值
		function getParamValueByName(url,name){
			var params = url.substr(1).split("&"), paramsObject = {};
			for (var i = 0; i < params.length; i++) {
				if (!params[i])
					continue;
				var a = params[i].split("=");
				if(a[0] == name){
					return decodeURIComponent(a[1]);
				}
			}
			return "";
		}
		
		//获取当前时间，格式YYYY-MM-DD
		function getNowFormatDate() {
		    var date = new Date();
		    var seperator1 = "-";
		    var year = date.getFullYear();
		    var month = date.getMonth() + 1;
		    var strDate = date.getDate();
		    if (month >= 1 && month <= 9) {
		        month = "0" + month;
		    }
		    if (strDate >= 0 && strDate <= 9) {
		        strDate = "0" + strDate;
		    }
		    var currentdate = year + seperator1 + month + seperator1 + strDate;
		    return currentdate;
		}

		//比较日期大小
		function compareDate(date1, date2) {
		    var date1 = new Date(date1);
		    var date2 = new Date(date2);
		    if (date1.getTime() > date2.getTime()) {
		        return true;
		    } else {
		        return false;
		    }
		}
		//获取借出方调整金额累计，出现借出维度完全一致，需要累加借出金额
		function getLendAcount(index){
			var fdLendAmount=$("input[name='fdDetailList_Form["+index+"].fdMoney']").val();
			var lendObjList=$("[name*='fdDetailList_Form["+index+"].fdLend']");
			var thisKey="";  //当前行维度值
			for(var i=0;i<lendObjList.length;i++){
				var value=lendObjList[i].value;
				if(value){
					thisKey=thisKey+value;
				}
			}
			//需要统计的行
			for(var n=0;n<index;n++){
				var lendObjList=$("[name*='fdDetailList_Form["+n+"].fdLend']");
				var preKey="";  //当前行之前行的维度值
				for(var i=0;i<lendObjList.length;i++){
					var value=lendObjList[i].value;
					if(value){
						preKey=preKey+value;
					}
				}
				if(thisKey==preKey){
					var fdPreAmount=$("input[name='fdDetailList_Form["+n+"].fdMoney']").val();
					if(fdPreAmount){
						fdLendAmount=fdLendAmount*1.00+fdPreAmount*1.00;
					}
				}
			}
			return formatFloat(fdLendAmount,2);
		}

		function checkLendMoneyFun(value,object){
			//获取校验对象在表格中的行序号
			var index = object.name.substring(object.name.indexOf("[")+1,object.name.indexOf("]"));
			var lendObjList=$("[name*='fdDetailList_Form["+index+"].fdLend']");
			var fdSchemeId=$("input[name='fdBudgetSchemeId']").val();
			var params = {};
			params["fdBudgetSchemeId"]=fdSchemeId;
			params["fdCompanyId"]=$("[name='fdCompanyId']").val();
			params["fdModelId"]=$("[name='fdId']").val();
			for(var i=0;i<lendObjList.length;i++){
				var property=lendObjList[i].name.substring(lendObjList[i].name.indexOf(".")+1,lendObjList[i].name.length);
				var value=lendObjList[i].value;
				property=property.replace("Lend","");  //将对应的参数替换Lend
				params[property]=value;
			}
			$.ajax({
				type: 'post',
				url: env.fn.formatUrl("/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do?method=checkLendMoney"),
				async:false,
				data : {hashMapArray:JSON.stringify(params)},
				success: function(result){
					if(result){
						var result=JSON.parse(result);
						var minPeriod=result["minPeriod"];
						if(minPeriod){//具体最小期间预算
							var fdCanUseAmount=minPeriod["fdCanUseAmount"];
							$("input[name*='fdDetailList_Form["+index+"].fdLendCanUseMoney']").val(fdCanUseAmount);
						}else{//无预算
							$("input[name*='fdDetailList_Form["+index+"].fdLendCanUseMoney']").val(0);
						}
					}
				},
				error:function(jqXHR, textStatus, e){
					console.log(e);
				}
			});
		}

		
		var validations = {
				'checkLendMoney': { // 校验借出预算可使用金额是否足够
					error: lang["message.fsscBudgetAdjust.fdLendMoney.error"],
					test: function(value, object) {
						var validateFlag=true;
						if($("input[name='fdSchemeType']").val()=='2'){ //预算追加不校验
							return true;
						}
						if($("input[name='docStatus']").val()=='10'&&$("input[name='IsDraft']").val()){
							return true;
						}
						 //获取校验对象在表格中的行序号
						var index = object.name.substring(object.name.indexOf("[")+1,object.name.indexOf("]"));
						var lendObjList=$("[name*='fdDetailList_Form["+index+"].fdLend']");
						var fdSchemeId=$("input[name='fdBudgetSchemeId']").val();
						var params = {};
						params["fdBudgetSchemeId"]=fdSchemeId;
						params["fdCompanyId"]=$("[name='fdCompanyId']").val();
						params["fdModelId"]=$("[name='fdId']").val();
						for(var i=0;i<lendObjList.length;i++){
							var property=lendObjList[i].name.substring(lendObjList[i].name.indexOf(".")+1,lendObjList[i].name.length);
							var value=lendObjList[i].value;
							property=property.replace("Lend","");  //将对应的参数替换Lend
							params[property]=value;
						}
						var that=this;
						$.ajax({
							  type: 'post',
							  url: env.fn.formatUrl("/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do?method=checkLendMoney"),
							  async:false,
							  data : {hashMapArray:JSON.stringify(params)},
							  success: function(result){
								  var fdCanUseAmount=0.0;
								  if(result){
									  var result=JSON.parse(result);
									  var minPeriod=result["minPeriod"];
									  if(minPeriod){//具体最小期间预算
										  var fdCanUseAmount=minPeriod["fdCanUseAmount"];
										  var fdLendAmount=getLendAcount(index);
										  $("input[name*='fdDetailList_Form["+index+"].fdLendCanUseMoney']").val(fdCanUseAmount);

										  if(fdCanUseAmount>=fdLendAmount){
											  validateFlag= true;
										  }else{
											  validateFlag= false;
										  } 
									  }else{//无预算
										  validateFlag=false;
									  }
									  var allPeriod=result["allPeriod"];  //全部的预算
									  if(allPeriod){
										  $("input[name='fdDetailList_Form["+index+"].fdBudgetInfo']").val(JSON.stringify(allPeriod).replace(/\"/g,"'"));
									  }
									}else{
										validateFlag=false;
									}
								  if(!validateFlag){
									//获取当前校验器对象
								    var validator = that.getValidator("checkLendMoney");
								    //替换资源文件中提示信息的预留文本
								    validator.error = validator.error.replace("\{fdLendMoney\}",fdCanUseAmount);
								    //校验完成后还原提示信息
								    that.options.afterElementValidate = function(){
									    validator.error=validator.error.replace(fdCanUseAmount,"\{fdLendMoney\}");
									}
								  }
							  },
							  error:function(jqXHR, textStatus, e){
								  console.log(e);
							  }
							});
						return validateFlag;
					}
				},
				'checkBorrowMoney': { // 校验借入成本中心是否存在预算，不然不允许追加或者调整。且借入金额如果为负数，金额绝对值不允许超过可使用额，否则借入可使用直接为负数
					error: lang["message.fsscBudgetAdjust.fdBorrowMoney.error"],
					test: function(value, object) {
						if($("input[name='docStatus']").val()=='10'&&$("input[name='IsDraft']").val()){
							return true;
						}
						var validateFlag=true;
						//获取校验对象在表格中的行序号
						var index = object.name.substring(object.name.indexOf("[")+1,object.name.indexOf("]"));
						var borrowObjList=$("[name*='fdDetailList_Form["+index+"].fdBorrow']");
						var fdMoney=$("input[name*='fdDetailList_Form["+index+"].fdMoney']").val();
						var fdSchemeId=$("input[name='fdBudgetSchemeId']").val();
						var params = {};
						params["fdBudgetSchemeId"]=fdSchemeId;
						params["fdCompanyId"]=$("[name='fdCompanyId']").val();
						for(var i=0;i<borrowObjList.length;i++){
							var property=borrowObjList[i].name.substring(borrowObjList[i].name.indexOf(".")+1,borrowObjList[i].name.length);
							var value=borrowObjList[i].value;
							property=property.replace("Borrow","");  //将对应的参数替换Lend
							params[property]=value;
						}
						var that=this;
						$.ajax({
							type: 'post',
							url: env.fn.formatUrl("/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do?method=checkBorrowMoney"),
							async:false,
							data : {hashMapArray:JSON.stringify(params)},
							success: function(result){
								var result=JSON.parse(result);
								var fdCanUseAmount=result["canUse"];
								$("input[name*='fdDetailList_Form["+index+"].fdBorrowCanUseMoney']").val(fdCanUseAmount);
								var notEmpty=result["result"];
								if(notEmpty){
									var fdSchemeType=$("[name='fdSchemeType']").val();  //预算调整
									if(fdSchemeType=='1'&&fdMoney<0){//预算调整时，金额不允许
										validateFlag= false;
										//获取当前校验器对象
									    var validator = that.getValidator("checkBorrowMoney");
										//替换资源文件中提示信息的预留文本
									    validator.error = lang["message.fsscBudgetAdjust.fdBorrowMoney.noFushu"];
									    //校验完成后还原提示信息
									    that.options.afterElementValidate = function(){
										    validator.error=lang["message.fsscBudgetAdjust.fdBorrowMoney.error"];
										}
									}else if(fdMoney<0&&Math.abs(fdMoney)>fdCanUseAmount){
										validateFlag= false;
										//获取当前校验器对象
									    var validator = that.getValidator("checkBorrowMoney");
										//替换资源文件中提示信息的预留文本
									    validator.error = lang["message.fsscBudgetAdjust.fdBorrowMoney.fuhsu"];
									    //校验完成后还原提示信息
									    that.options.afterElementValidate = function(){
										    validator.error=lang["message.fsscBudgetAdjust.fdBorrowMoney.error"];
										}
									}else{
										validateFlag= true;
									}
								}else{
									validateFlag=false;
								}
							},
							error:function(jqXHR, textStatus, e){
								console.log(e);
							}
						});
						return validateFlag;
					}
				},
				'validateSameDimension': { // 校验借入维度是否和借出维度完全一致
					error: lang["message.fsscBudgetAdjust.fdDimension.error"],
					test: function(value, object) {
						if($("input[name='fdSchemeType']").val()=='2'){ //预算追加不校验
							return true;
						}
						return checkSameDimension(value,object);  //该方法实现fsscBudgetAdjust.js
					}
				},
				'checkAdjust': { // 校验预算数据查看页面调整预算，金额不能大当前预算的可使用金额
					error: lang["message.fsscBudgetAdjust.fdAdjustMoney.error"],
					test: function(value, object) {
						var validateFlag=true;
						if(value>0){ //调增不做校验
							return true;
						}
						 //获取校验对象在表格中的行序号
						var params = {};
						params["fdBudgetId"]=getParamValueByName(window.location.href,"fdId");
						params["fdAdjustMoney"]=value;
						var that=this;
						$.ajax({
							  type: 'post',
							  url: env.fn.formatUrl("/fssc/budget/fssc_budget_adjust_main/fsscBudgetAdjustMain.do?method=checkAdjust"),
							  async:false,
							  data : {hashMapArray:JSON.stringify(params)},
							  success: function(result){
								  var fdCanUseAmount=0.0;
								  if(result){
									  if(result=='true'){
										  validateFlag= true;
									  }else{
										  validateFlag= false;
									  }
									}else{
										validateFlag=false;
									}
								/*  if(!validateFlag){
									//获取当前校验器对象
								    var validator = that.getValidator("checkAdjust");
								    //替换资源文件中提示信息的预留文本
								    validator.error = validator.error.replace("\{fdMoney\}",fdCanUseAmount);
								    //校验完成后还原提示信息
								    that.options.afterElementValidate = function(){
									    validator.error=validator.error.replace(fdCanUseAmount,"\{fdLendMoney\}");
									}
								  }*/
							  },
							  error:function(jqXHR, textStatus, e){
								  console.log(e);
							  }
							});
						return validateFlag;
					}
				},
				'checkCurrentDate': { // 校验日期不能小于当前日期
					error: lang["message.budget.must.lt.current"],
					test: function(value, object) {
						var validateFlag=true;
						var fdEnableDate=$("input[name='fdEnableDate']").val();
						var currentDate=getNowFormatDate();
						if(compareDate(currentDate,fdEnableDate)){
							validateFlag=false;
						}
						return validateFlag;
					}
				},
			};
		$KMSSValidation().addValidators(validations);
	});
});

