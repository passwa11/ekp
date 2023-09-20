	
	function downTemplate(){
		var hasSuper = $("[name='isCascade']:checked").val();
		var url = Com_Parameter.ContextPath + "sys/xform/maindata/main_data_custom/sysFormMainDataCustom.do?method=downloadTemplate&hasSuper=" + hasSuper;	
		Com_OpenWindow(url,"_parent");
	}
	
	seajs.use(['lui/dialog'], function(dialog) {
		// excel导入
		window.importExcel = function(flag){
			// 为空判断
			var file = document.getElementsByName("uploadFile");
			if(file[0].value==null || file[0].value.length==0){
				seajs.use(['lui/dialog'],function(dialog){
					dialog.alert("上传附件不能为空！");
				});
				return false;
			}
			if(file[0].value.indexOf(".xls") == -1){
					alert("文件格式不正确，请选择正确的Excel文件(后缀名为.xls或者.xlsx)!");
					return false;
			}
			// 判断是否有上级
			var hasSuper = $("[name='isCascade']:checked").val();
			if(hasSuper == 'true'){
				if($("input[name='cascadeCustomId']").val() == ''){
					alert("请先选择级联对象！");
					return;
				}
			}
			$(".xform_main_data_custom_errorlog").html('');
			// flag:0---新增，flag：1----覆盖
			var url = Com_Parameter.ContextPath + "sys/xform/maindata/main_data_custom/sysFormMainDataCustom.do?method=loadExcel&hasSuper="+ hasSuper +"&flag=" + flag;
			var form = document.getElementsByName("sysFormMainDataCustomForm")[0];
			var tempTarget = form.target;
			form.target = "file_frame";
			form.action = url;
			window.customImport_load = dialog.loading();
			form.submit();
			// 复原target
			form.target = tempTarget;
		}
		
		window.importDataToTable = function(result,flag){
			if(result){
				result = eval(result);
				if(result.status && result.status == '01'){
					dialog.failure(result.errorlog);
				}else{
					var datas = result.datas;
					if(datas && datas.length > 0){
						// 获取当前显示的table
						var table = xform_main_data_custom_getCurrentTable();
						if(table){
							// 获取name
							var tbObj = DocList_TableInfo[table.id];
							var fieldNames = tbObj.fieldNames;
							fieldNames = DocList_promiseUnique(fieldNames);
							// flag:0---新增，flag：1----覆盖
							if(flag && flag == '1'){
								// 先把原有数据清空
								$(table).find("tr[invalidrow!='true']").each(function(){
									DocList_DeleteRow_ClearLast(this);
								});			
							}
							// 遍历导入	
							// 报错信息
							var errorLog = [];
							for(var i = 0;i < datas.length;i++){
								var data = datas[i];
								// 需要填充到行的字段和值
								var fieldValues = {};
								// 上级的值
								var selectOption = {};
								var isError = false;
								// 遍历每行的字段
								for(var fieldName in data){
									// 只要存在错误就跳出
									if(isError == true){
										break;
									}						
									// 如果是上级，则直接取出来						
									if(fieldName == 'fdCascade'){
										selectOption.fdCascade = data['fdCascade'];
										// 判断是否有该实际值，不存在则跳出
										if(judgeFdCascadeMatch(data['fdCascade']) == false){
											// 存储错误信息
											errorLog.push({'errorVal':data['fdCascade'],'index':i+2});	
											isError = true;
										}
										continue;
									}
									for(var j = 0 ;j < fieldNames.length; j++){
										var endFieldName = fieldNames[j].substring(fieldNames[j].lastIndexOf(".") + 1);
										if(endFieldName == fieldName){
											fieldValues[fieldNames[j]] = data[fieldName];
											break;
										}
									}
								}
								if(isError == true){
									continue;
								}
								var row = DocList_AddRow(table.id,null,fieldValues);
								$(row).find(".xform_main_data_custom_super select").append(xform_main_data_custom_buildSelectBycascade(selectOption));
							}
							if(window.customImport_load != null){
								window.customImport_load.hide();
							}
							if(errorLog.length > 0){
								// 构建错误信息的dom内容
								var html = [];
								html.push("<span>导入异常：</span></br>");
								for(var i = 0;i < errorLog.length;i++){
									var error = errorLog[i];
									html.push("<span>");
									html.push("找不到第"+ errorLog[i].index +"行对应的实际值：" + errorLog[i].errorVal);
									html.push("</span>");
									html.push("</br>");
								}
								$(".xform_main_data_custom_errorlog").html(html.join(''));
							}else{
								dialog.success("导入数据成功！");
							}
							
						}
					}	
				}
			}
			
			if(window.customImport_load!=null){
				window.customImport_load.hide();
			}
		}
	});
	
	// 校验导入的实际值是否存在
	function judgeFdCascadeMatch(val){
		var datas = xform_main_data_custom_parameter.cascadeCustomDict;
		if(datas){
			for(var i = 0;i < datas.length;i++){
				var data = datas[i];
				if(data.value == val){
					return true;
				}
			}	
		}
		return false;
	}
	
	function xform_main_data_custom_getCurrentTable(){
		var table = $(".xform_maindata_datalist:visible");
		if(table && table.length > 0){
			return table[0];
		}else{
			return ;
		}
	}
	
	function xform_main_data_addrow(){
		// 如果选择了级联，判断是否选择了表
		var isCascade = $("[name='isCascade']:checked").val();
		if(isCascade == 'true'){
			if($("input[name='cascadeCustomId']").val() == ''){
				alert(Data_GetResourceString("sys-xform-maindata:sysFormMainDataCustom.SelectCascadeFirst"));
				return;
			}
		}
		var row = DocList_AddRow();
		// 如果是级联，则实时构造上级的下拉框内容
		if(isCascade == 'true'){
			$(row).find(".xform_main_data_custom_super select").append(xform_main_data_custom_buildSelectBycascade());
		}
	}
	
	
	
	//是否是级联，级联则显示含有上级的table
	function xform_main_data_custom_showCascadeSelect(dom,isCascade){
		var value;
		if(isCascade && isCascade != ''){
			value = isCascade;
		}else{
			value = $(dom).val();
		}
		//value 为true则有级联，为false则不是级联
		if(value){
			if(value == 'true'){
				$("#TABLE_DocList_hasSuper").show();
				$("#TABLE_DocList_noSuper").hide();
				$("#TABLE_DocList_noSuper").find("tr[kmss_iscontentrow='1']").remove();
				$("input[name='cascadeCustomSubject']").attr('validate','required');
				if($("#xform_main_data_custom_cascadeCustomWrap").find(".txtstrong").length === 0){
					$("#xform_main_data_custom_cascadeCustomWrap").append("<span class='txtstrong'>*</span>");
				}
				//显示级联对象选择
				$("#xform_main_data_custom_cascadeCustomWrap").css('display','inline-block');
				this.table_validate('TABLE_DocList_noSuper','');
				this.table_validate('TABLE_DocList_hasSuper','required');

			}else if(value == 'false'){
				$("#TABLE_DocList_hasSuper").hide();
				$("#TABLE_DocList_hasSuper").find("tr[kmss_iscontentrow='1']").remove();
				$("#TABLE_DocList_noSuper").show();
				$("input[name='cascadeCustomSubject']").attr('validate','');
				$("#xform_main_data_custom_cascadeCustomWrap").children("span[class='txtstrong']").remove();
				$("#xform_main_data_custom_cascadeCustomWrap").closest("td").children(".validation-advice").remove();
				$("#xform_main_data_custom_cascadeCustomWrap").hide();
				this.table_validate('TABLE_DocList_hasSuper','');
				this.table_validate('TABLE_DocList_noSuper','required');
			}
		}
	}

	//自定义类别，有无级联按钮切换对是否必填进行处理
	function table_validate(id,validate){
		var row = $("#"+id +">tbody>tr").nextAll();
		for(var i = 0;i < row.length;i++){
			$(row[i]).find("input[name$='fdValueText']").attr('validate',validate);
			$(row[i]).find("input[name$='fdValue']").attr('validate',validate);
		}
	}
	function xform_main_data_custom_buildSelectBycascade(option){
		if(xform_main_data_custom_parameter.cascadeCustomDict && xform_main_data_custom_parameter.cascadeCustomDict != ''){
			var datas = xform_main_data_custom_parameter.cascadeCustomDict;			
			var html = [];
			var hasSelectVal = false;
			if(option && option.fdCascade != ''){
				hasSelectVal = true;
			}
			for(var i = 0;i < datas.length;i++){
				var data = datas[i];
				var buf = [];
				buf.push("<option value='"+ data.fdId +"' data-fdvalue='"+ data.value +"' ");
				// 按实际值来匹配
				if(hasSelectVal == true && option.fdCascade == data.value){
					buf.push("selected ");
				}
				buf.push(">");
				buf.push(data.text);
				buf.push("</option>");
				html.push(buf.join(''));
			}
			return html.join('');
		}
	}
	
	