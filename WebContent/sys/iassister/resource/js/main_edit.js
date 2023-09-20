Com_AddEventListener(window,'load',function(){
	seajs.use(['lui/jquery','lui/dialog','lui/dialog_common','lui/util/str'], function($, dialog, dialogCommon,strutil){
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
		
		window.dialogCategory=function(modelName,idField,nameField,mulSelect){
			dialog.category(modelName, idField, nameField, mulSelect, null,
					null, null, null, null);
		};
		
		window.dialogSimpleCategory=function(modelName,idField,nameField,mulSelect){
			dialog.simpleCategory(modelName,idField,nameField,mulSelect,null,null,null,null);
		};
		
		window.dialogSelect=function(mul, key, idField, nameField,targetWin){
		    targetWin = targetWin||window;
			if(key==null||key==''){
				window.alert("请修改此段代码");
				return;
			}
			if(idField.indexOf('*')>-1 && window.DocListFunc_GetParentByTagName){
				//明细表
				dialogSelectForDetail(mul, key, idField, nameField);
			}else{
				var dialogCfg = formOption.dialogs[key];
	    		if(dialogCfg){
	    			var params='';
	    			var inputs=getDialogInputs(idField);
	    			if(inputs){
	    				for(var i=0;i<inputs.length;i++){
	    					var argu = inputs[i];
	    					var modelVal=$form(argu["value"]).val();
	    					if(modelVal==null||modelVal==''){
	    						if(argu["required"]=="true"){
	    							var errorInfo="当前对话框缺失必须传递的参数【"+argu["label"]+"】，请检查表单数据或相关配置";
	    							alert(errorInfo);
	    							return;
	    						}
	    						params+='&'+argu["key"]+'='+formInitData[argu["value"]];
	    					}else{
	    						params+='&'+argu["key"]+'='+modelVal;
	    					}
	    				}
	    			}
	    			params=encodeURI(params);
	    			targetWin['__dialog_' + idField + '_dataSource'] = function(){
                        return strutil.variableResolver(dialogCfg.sourceUrl+params ,null);
                    }
	    			dialogCommon.dialogSelect(dialogCfg.modelName,
	    					mul,dialogCfg.sourceUrl+params, null, idField, nameField,null,function(data){
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
	    							var errorInfo="当前对话框缺失必须传递的参数【"+argu["label"]+"】，请检查表单数据或相关配置";
	    							alert(errorInfo);
	    							return;
	    						}
	    						params+='&'+argu["key"]+'='+formInitData[argu["value"]];
	    					}else{
	    						params+='&'+argu["key"]+'='+modelVal;
	    					}
    					}
    					
    				}
    			}
    			params=encodeURI(params);
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
		
		function dialogSelectForDetail(mul, key, idField, nameField){
			var tr=DocListFunc_GetParentByTagName('TR');
			var tb= DocListFunc_GetParentByTagName("TABLE");
			var tbInfo = DocList_TableInfo[tb.id];
			var refIdField=idField.replace("*",tr.rowIndex-tbInfo.firstIndex); 
			var refNameField=nameField.replace("*",tr.rowIndex-tbInfo.firstIndex); 
			var dialogCfg = formOption.dialogs[key];
    		if(dialogCfg){
    			var params='';
    			var inputs=getDialogInputs(idField);
    			if(inputs){
    				for(var i=0;i<inputs.length;i++){
    					var argu = inputs[i];
    					if(argu["value"].indexOf('*')>-1){
    						//入参来自明细表
    						var modelVal=$form(argu["value"],refIdField).val();
        					if(modelVal==null||modelVal==''){
        						if(argu["required"]=="true"){
        							var errorInfo="当前对话框缺失必须传递的参数【"+argu["label"]+"】，请检查表单数据或相关配置";
	    							alert(errorInfo);
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
	    							var errorInfo="当前对话框缺失必须传递的参数【"+argu["label"]+"】，请检查表单数据或相关配置";
	    							alert(errorInfo);
	    							return;
	    						}
	    						params+='&'+argu["key"]+'='+formInitData[argu["value"]];
	    					}else{
	    						params+='&'+argu["key"]+'='+modelVal;
	    					}
    					}
    					
    				}
    			}
    			params=encodeURI(params);
    			dialogCommon.dialogSelect(dialogCfg.modelName,
    					mul,dialogCfg.sourceUrl+params, null, refIdField, refNameField,null,function(data){
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
			if(isDraft == true){
				validateOpt(true);
			}else{
				validateOpt(false);
			}
			var action = document.forms[formOption.formName].action;
			document.forms[formOption.formName].action = Com_SetUrlParameter(action,"docStatus",status);
			Com_Submit(document.forms[formOption.formName], method);
		}
		
		window.validateDetail=function(){
			var details=formOption.detailTables;
			if(details!=null){
				for(var i=0;i<details.length;i++){
					var detail=details[i];
					var message=messageInfo[detail.split("_")[2]];
					var trSize=$("#"+detail).find("tr").length;
					if(trSize<=2){
						dialog.alert("明细表【"+message+"】不能为空");
						return false;
					}else{
						var flag=true;
						$("#"+detail).find("tr").each(function(){
							var inputs=$(this).find("input[name!='DocList_Selected']");
							inputs.each(function(){
								var value=$(this).val();
								if(value!=null&&value!=''){
									flag=false;
								}
							});
						});
						if(flag){
							dialog.alert("明细表【"+message+"】不能为空");
							return false;
						}
					}
				}
			}
			return true;
		};
		
		
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
		
		function startsWith(value, prefix) {
			return value.slice(0, prefix.length) === prefix;
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
			dateDiffYear:function(beginTime,endTime){
				return Math.round((endTime.getTime()-beginTime.getTime())/1000/60/60/24/365);
			},
			dateDiffHour:function(beginTime,endTime){
				return Math.round((endTime.getTime()-beginTime.getTime())/1000/60/60);
			},
			dateMerge:function(date,time){
				return new Date(date.getTime()+time.getTime());
			},
			dateAddDay:function(date,add){
				return new Date(date.getTime()+add*24*60*60*1000);
			},
			dateAddMin:function(date,add){
				return new Date(date.getTime()+add*60*1000);
			},
			emailCheck:function(email){
				if (email == "") {
					return true;
				}
				return /^([a-z0-9A-Z]+(-|\.|_)*)+[a-z0-9A-Z]@([a-z0-9A-Z-]+\.)+[a-zA-Z]{2,}$/.test(email);
			},
			phoneCheck:function(phone){
				if (phone == "") {
					return true;
				}
				// 国内手机号可以有+86，但是后面必须是11位数字
				// 国际手机号必须要以+区号开头，后面可以是6~11位数据
				if(startsWith(phone, "+")) {
					if(startsWith(phone, "+86")) {
						return /^(\+86)(\d{11})$/.test(phone);
					} else {
						return /^(\+\d{1,5})(\d{6,11})$/.test(phone);
					}
				} else {
					// 没有带+号开头，默认是国内手机号
					return /^[1][0-9]{10}$/.test(phone);
				}
			},
			uniqueCheckDetail:function(url,property,tableId,data){
				var temp=data?data+'':'';
				var	table="TABLE_DocList_"+tableId;
				var trs=window.parent.document.getElementById(table).getElementsByClassName("docListTr");
				var num=0;
				var fdIds=[];
				for (var i = 0; i < trs.length; i++) {
					var inputs=$("input[name='"+tableId+"["+i+"]."+property+"']")[0];
					if(inputs.value==temp){
						num=num+1;
						fdIds.push($("input[name='"+tableId+"["+i+"]."+"fdId']")[0].value);
					}
				}
				if(num>1){
					return false;
				}
				
				if (temp == "") {
						return true;
					}
					var flag=false;
					 $.ajax({     
					     type:"post",   
					     url:url,     
					     data:{property:property,data:temp.replace(/(^\s*)|(\s*$)/g, "")},
					     async:false,
					     success:function(data){
					    	 if(data =='null'){
					    		 flag = true;
					    	 }else{
					    		  for(var i = 0; i < fdIds.length; i++){
					    		        if(data == fdIds[i]){
					    		        	flag = true;
					    		        }
					    		    }
					    	 }
						}    
				 });
					 return flag;
			},
			uniqueCheck:function(url,property,data){
				var temp=data?data+'':'';
				if (temp == "") {
						return true;
					}
					var fdId = Com_GetUrlParameter(location.href, "fdId");
					var flag=true;
					 $.ajax({     
					     type:"post",   
					     url:url,     
					     data:{property:property,data:temp.replace(/(^\s*)|(\s*$)/g, ""),fdId:fdId},
					     async:false,
					     success:function(data){
					    	 if(data =='false'){
					    		 flag = false;
					    	 }
						}    
				 });
					 return flag;
			},
			abs:function(x){
				return Math.abs(x);
			},
			max:function(data){
				return Math.max.apply(null,data);
			},
			min:function(data){
				return Math.min.apply(null,data);
			},
			ceil:function(x){
				return Math.ceil(x);
			},
			floor:function(x){
				return Math.floor(x);
			},
			round:function(x,n){
				return Math.round(x * Math.pow(10, n)) / Math.pow(10, n) ;
			},
			systime:function(){
				return new Date();
			},
			year:function(x){
				return x.getFullYear();
			},
			month:function(x){
				return x.getMonth()+1;
			},
			date:function(x){
				return x.getDate();
			},
			hour:function(x){
				return x.getHours();
			},
			minute:function(x){
				return x.getMinutes();
			},
			second:function(x){
				return x.getSeconds();
			},
			lower:function(x){
				return x.toLowerCase();
			},
			upper:function(x){
				return x.toUpperCase();
			},
			trim:function(x){
				return x.replace(/^\s+|\s+$/gm,'');
			},
			len:function(x){
				return x.length;
			},
			startWith:function(x,ex){
				var reg=new RegExp("^"+ex);     
				return reg.test(x); 
			},
			endWith:function(x,ex){
				var reg=new RegExp(ex+"$");     
				return reg.test(x); 
			},
			contains:function(x,ex){
				return x.indexOf(ex)!=-1;
			},
			replace:function(x,old,newStr){
				return x.replace(old,newStr);
			},
			split:function(x,ex){
				return x.split(ex);
			},
			isEmpty:function(x){
				if(typeof x == "undefined" || x == null || x == ""){
			        return true;
			    }else{
			        return false;
			    }
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
		
		function encodeHTML(str){ 
			return str.replace(/&/g,"&amp;")
				.replace(/</g,"&lt;")
				.replace(/>/g,"&gt;")
				.replace(/\"/g,"\"")
				.replace(/\'/g,"&#39;")
				.replace(/¹/g, "&sup1;")
				.replace(/²/g, "&sup2;")
				.replace(/³/g, "&sup3;");
		};
		//导出
		window.exportDocList = function(url,detail){
			var tableId="TABLE_DocList_"+detail+"_Form";
			window.export_load = dialog.loading();
 			var json = new Array();
 			var values;
 			var ths=document.getElementById(tableId).getElementsByClassName("tr_normal_title")[0].getElementsByTagName("td");
 			var thsValues=null;
 			var index=0;
 			LUI.$("input[name='DocList_Selected']:checked").each(function(j){
			 var tds = LUI.$(this).parent().parent().find(".docList");
			 var data = new Array();
			 for (var i = 1; i < ths.length-1; i++) {
				 if(LUI.$(tds[i]).children("div").length>0){
					 if(LUI.$(tds[i]).children("div").attr("_xform_type")=="radio"){
						 if(LUI.$(tds[i]).find("input:checked")[0]){
							 data.push([ths[i].innerText,encodeHTML(LUI.$(tds[i]).find("input:checked")[0].parentElement.innerText)]);
						 }else{
							 data.push([ths[i].innerText,encodeHTML("")]);
						 }
					 }else if(LUI.$(tds[i]).children("div").attr("_xform_type")=="text"){
						 data.push([ths[i].innerText,encodeHTML(LUI.$(tds[i]).find("input[type=text]")[0].value)]);
					 }else if(LUI.$(tds[i]).children("div").attr("_xform_type")=="address"){
						 if(LUI.$(tds[i]).find("input[type=text]")[0]){
							 data.push([ths[i].innerText,encodeHTML(LUI.$(tds[i]).find("input[type=text]")[0].value)]);
						 }else{
							 data.push([ths[i].innerText,encodeHTML("")]);
						 }
					 }else if(LUI.$(tds[i]).children("div").attr("_xform_type")=="textarea"){
						 data.push([ths[i].innerText,encodeHTML(LUI.$(tds[i]).find("textarea")[0].value)]);
					 }else if(LUI.$(tds[i]).children("div").attr("_xform_type")=="rtf"){
						 data.push([ths[i].innerText,encodeHTML(LUI.$(tds[i]).find("textarea")[0].value)]);
					 }else if(LUI.$(tds[i]).children("div").attr("_xform_type")=="datetime"||LUI.$(tds[i]).children("div").attr("_xform_type")=="date"||LUI.$(tds[i]).children("div").attr("_xform_type")=="time"){
						 if(LUI.$(tds[i]).find("input[type=text]")[0]){
							 data.push([ths[i].innerText,encodeHTML(LUI.$(tds[i]).find("input[type=text]")[0].value)]);
						 }else{
							 data.push([ths[i].innerText,encodeHTML("")]);
						 }
					 }else if(LUI.$(tds[i]).children("div").attr("_xform_type")=="checkbox"){
						 var checkbox =LUI.$(tds[i]).find($("input[type='checkbox']:checked"));
						 var value="";
						 checkbox.each(
					                function() {
					                	value=$(this)[0].parentElement.innerText+","+value;   
					                }
					            );
						 value = value.substring(0, value.length-1);
						 data.push([ths[i].innerText,encodeHTML(value)]);
					 }else if(LUI.$(tds[i]).find("table").length>0){
						 var table = LUI.$(tds[i]).find("table")[0];
						 var trs=LUI.$(table).find("tr")[1];
						 var value="";
						 if(LUI.$(tds[j]).find(".upload_list_filename_edit")){
							 var valtr=LUI.$(trs).find("tr");
							 for (var j = 0; j < valtr.length; j++) {
								 value=LUI.$(valtr[j]).find(".upload_list_filename_edit")[0].innerText+","+value;
							 }
						 }
						 value = value.substring(0, value.length-1);
						 data.push([ths[i].innerText,encodeHTML(value)]);
						
					 }else{
							data.push([ths[i].innerText,encodeHTML(tds[i].innerText)]);
						}
				 }else{
						data.push([ths[i].innerText,encodeHTML(tds[i].innerText)]);
					}
				}
			 json.push(["json"+j,data]);
			 index=j;
			});
 			if(json.length!=0){
 				for (var i = 1; i < ths.length-1; i++) {
 					if(thsValues==null){
 						thsValues=ths[i].innerText;
 					}else{
 						thsValues=thsValues+","+ths[i].innerText;
 					}
 			}
 			if(window.export_load!=null){
 					window.export_load.hide(); 
 			}
 			 openWindowWithPost(url,"post","json",encodeURI(LUI.stringify(json)),"ths",encodeURI(thsValues));
 			}else{
 				 if(window.export_load!=null){
  					window.export_load.hide(); 
  				}
 				dialog.alert("您没有选择需要操作的数据");
 				return;
 			}
	};
	
	function openWindowWithPost(url,name,key,value,thkey,thvalue){
	    var newWindow = window.open(name);  
	    if (!newWindow)  
	        return false;  
	    var html = "";  
	    html += "<html><head></head><body><form id='formid' method='post' action='" + url + "'>";  
	    if (key && value)  
	    {  
	       html += "<input id='"+key+"' type='hidden' name='" + key + "' value='" +value+ "'/>";
	      
	    }
	    if(thkey && thvalue){
	       html += "<input id='"+thkey+"' type='hidden' name='" + thkey + "' value='" +thvalue+ "'/>";
	    }  
	    html += "</form><script type='text/javascript'>document.getElementById('formid').submit();";  
	    html += "<\/script></body></html>".toString().replace(/^.+?\*|\\(?=\/)|\*.+?$/gi, "");   
	    newWindow.document.write(html);  
	    return newWindow; 
	};
	
	// 导入
	window.importDocList = function(dialogUrl,url,detail) {
		var tableId="TABLE_DocList_"+detail+"_Form";
		
		dialog.iframe(dialogUrl+'?tableId='+tableId+'&url='+url, 
				' ', function(data) {
		}, {
			width : 650,
			height : 470
		});
	};
	
	});
});

