var _currentEditDivDom ;

function xform_main_data_custom_skipPage(type){
	var $searchInput = $(".xform_main_data_custom_search_input");
	var searchVal = encodeURIComponent($searchInput.val());
	  
	//type为1是上一页，为2是下一页
	if(type == '1'){
		if(xform_main_data_custom_currentPageNum != 0){
			xform_main_data_custom_currentPageNum--;
		}
		xform_main_data_custom_search(xform_main_data_custom_currentPageNum,searchVal);
	}else if(type == '2'){
		xform_main_data_custom_currentPageNum++;
		xform_main_data_custom_search(xform_main_data_custom_currentPageNum,searchVal);
	}
}

function xform_main_data_custom_decodeHTML(str){
	if (str == null || str.length == 0)
		return "";
	return (str + '').replace(/&quot;/g, '"')
       .replace(/&gt;/g, '>')
       .replace(/&lt;/g, '<')
       .replace(/&amp;/g, '&')
       .replace(/&apos;/g,'\'');
}

function xform_main_data_custom_fillTableContent(result){
	var table = $("#TABLE_DocList_custom_table")[0];
	// 先把原有数据清空
	$(table).find("tr:not(:first)").each(function(){
		$(this).remove();
	});		
	var tbInfo = DocList_TableInfo[table.id];
	// 更新lastindex
	tbInfo.lastIndex = 1;
	var fieldNames = tbInfo.fieldNames;
	fieldNames = DocList_promiseUnique(fieldNames);
	var dataArray = result.dataArray;
	var contentHtml = "";
	if(dataArray && dataArray.length > 0){
		for(var i = 0;i < dataArray.length;i++){
			// 需要填充到行的字段和值
			var fieldValues = {};
			var data = dataArray[i];
			for(var fieldName in data){
				for(var j = 0 ;j < fieldNames.length; j++){
					var endFieldName = fieldNames[j].substring(fieldNames[j].lastIndexOf(".") + 1);
					if(endFieldName == fieldName){
						fieldValues[fieldNames[j]] = xform_main_data_custom_decodeHTML(data[fieldName]);
						break;
					}
				}
			}
			DocList_AddRow(table.id,null,fieldValues);
		}
	}
	//计算上下页的展示
	xform_main_data_custom_calculatePage(result.maxResults,dataArray.length);
}

// 计算上下翻页
function xform_main_data_custom_calculatePage(maxResults,currentDataLength){
	if(xform_main_data_custom_currentPageNum > 1){
		var num = currentDataLength + ((xform_main_data_custom_currentPageNum - 1) * _xform_main_data_custom_rowSize);
		//不是第一页，显示上一页
		$("#lastPageNum").show();
		if(maxResults > 0 && num < maxResults){				
			$("#nextPageNum").show();
		}else{
			$("#nextPageNum").hide();
		}
	}else{
		$("#lastPageNum").hide();
		if(currentDataLength == _xform_main_data_custom_rowSize && maxResults > 0 && currentDataLength < maxResults){				
			$("#nextPageNum").show();
		}else{
			$("#nextPageNum").hide();
		}
	}
}

function xform_main_data_custom_del(dom){
	if(!confirmDelete())return;
	// 获取tr
	var $tr = $(dom).closest("tr");
	var fdId = $tr.find("input[name*=fdId]").val();
	var url = Com_Parameter.ContextPath + "sys/xform/maindata/main_data_custom/sysFormMainDataCustom.do?method=delData&fdId="+ fdId;
	$.ajax({
		url: url,
		type:"GET",
		async:false,
		success:function(data){
			seajs.use(['lui/dialog'], function(dialog) {
				try{
					data = $.parseJSON(data);
				}catch(e){
					dialog.failure("删除失败，出错信息请看后台日志！");
				}
				if(data.status == '00'){
					dialog.success("删除成功！");
					DocList_DeleteRow($tr[0]);
				}else{
					dialog.failure(data.errorlog);
				}
				
			});
		}
	});
}

function xform_main_data_custom_editDiv_adjustHeight(){
	$(".xform_main_data_custom_editDiv_bg").height(document.documentElement.scrollHeight);	
}
Com_IncludeFile('dialog.js');
//打开消息自定义窗口
function lbpmCustomizeFunc(dom){
	var $tr = $(dom).closest("tr");
	var fdId=$tr.find("input[name$=fdId]").val()
	var dialog = new KMSSDialog();

	dialog.SetAfterShow(function(rtn){
		window.location.reload();
	});
	
	dialog.URL = Com_Parameter.ContextPath + "sys/xform/maindata/main_data_custom/sysFormMainDataCustomList.do?method=editMainDataCustomListById&fdId="+fdId;	
	window._rela_dialog = dialog;
	dialog.Show(window.screen.width*872/1366,window.screen.height*616/768);
}


//显示
function xform_main_data_custom_editDiv_show(dom){
	lbpmCustomizeFunc(dom);
	/*_currentEditDivDom = dom;
	$(".xform_main_data_custom_editDiv").show();
	$('body').css("overflow", "hidden");
	//构造内容
	var $contentDiv = $(".xform_main_data_custom_editDiv_content");
	// 获取tr
	var $tr = $(dom).closest("tr");
	// 设置上级
	var $select = $contentDiv.find("select[name='_editDiv_fdCascadeName']");
	if($select.length > 0){
		$($select[0]).empty();//清空
		$($select[0]).append(xform_main_data_custom_getSelectOptions());
		var fdCascadeId = $tr.find("input[name*=fdCascadeId]").val();
		$($select[0]).val(fdCascadeId);
	}
	
	//$("input[name='sysFormMainDataCustomList[0].fdValueText']").val("11");
	var valueTextHtml =$tr.find(".valueTextWrap").html();
	
	
	$contentDiv.find(".valueTextTd").html(valueTextHtml);
	$contentDiv.find("input[name='_editDiv_fdValue']").val($tr.find("input[name$=fdValue]").val());
	$contentDiv.find("input[name='_editDiv_fdOrder']").val($tr.find("input[name$=fdOrder]").val());
	//xform_main_data_custom_editDiv_adjustHeight();*/
}

// 确定
function xform_main_data_custom_editDiv_OK(){
	//保存到数据库
	//回填
	var dom = _currentEditDivDom;
	var $contentDiv = $(".xform_main_data_custom_editDiv_content");
	// 获取tr
	var $tr = $(dom).closest("tr");
	// 设置上级
	var $select = $contentDiv.find("select[name='_editDiv_fdCascadeName']");
	var selectVal = "";
	if($select.length > 0){
		selectVal = $select.val();
		$tr.find("input[name*=fdCascadeId]").val(selectVal);
		$tr.find("input[name*=fdCascadeName]").val($select.find("option:selected").text());
	}
	var fdValueText = $contentDiv.find("input[name='_editDiv_fdValueText']").val();
	$tr.find("input[name$=fdValueText]").val(fdValueText);
	var fdValue = $contentDiv.find("input[name='_editDiv_fdValue']").val();
	$tr.find("input[name$=fdValue]").val(fdValue);
	var fdOrder = $contentDiv.find("input[name='_editDiv_fdOrder']").val();
	var val = /^-?\d+$/.test(fdOrder);
	if($.trim(fdOrder) !== "" && !val){
		return ;
	}
	$tr.find("input[name$=fdOrder]").val(fdOrder);
	var dataObject = {};
	dataObject.fdId = $tr.find("input[name*=fdId]").val();
	dataObject.fdCascadeId = selectVal;
	dataObject.fdValueText = fdValueText;
	dataObject.fdValue = fdValue;
	dataObject.fdOrder = fdOrder;
	var url = Com_Parameter.ContextPath + "sys/xform/maindata/main_data_custom/sysFormMainDataCustom.do?method=saveEditData";
	$.ajax({
		url: url,
		type:"POST",
		data:{'data':JSON.stringify(dataObject)},
		async:false,
		success:function(data){
			
		}
	});
	xform_main_data_custom_editDiv_hide();
}

// 隐藏
function xform_main_data_custom_editDiv_hide(){
	$(".xform_main_data_custom_editDiv").hide();
	$('body').css("overflow", "auto");
}



/*废弃*/
function importDataToTable(datas,flag){
	if(datas && datas.length > 0){
		// 获取table
		var table = $("#TABLE_DocList_custom_table")[0];
		if(table){
			if(flag && flag == '1'){
				// 先把原有数据清空
				$(table).find("tr:not(:first)").each(function(){
					$(this).remove();
				});		
				var tbInfo = DocList_TableInfo[table.id];
				// 更新lastindex
				tbInfo.lastIndex = 1;
			}
			return dealWithDatas(datas,table);
		}
	}	
}

// 处理数据 废弃
function dealWithDatas(datas,table){
	var errorLog = [];
	var tbObj = DocList_TableInfo[table.id];
	var fieldNames = tbObj.fieldNames;
	fieldNames = DocList_promiseUnique(fieldNames);
	var multiFieldAttr = "fdCascade";
	for(var i = 0;i < datas.length;i++){
		var data = datas[i];
		// 需要填充到行的字段和值
		var fieldValues = {};
		var isError = false;
		// 遍历每行的字段
		for(var fieldName in data){
			var isFdCascade = false;//上级标志位
			var fdCascadeDataJson;//用于保存对应上级的信息，包括val、text、fdId，以免后面有需要重新查
			// 如果是上级，则直接取出来						
			if(fieldName == multiFieldAttr){
				// 判断是否有该实际值，不存在则跳出
				var rs = judgeFdCascadeMatch(data[fieldName]);
				if(rs.isMatch == false){
					// 存储错误信息
					errorLog.push({'errorVal':data[fieldName],'index':i+2});
					// 只要出错即跳出该循环
					isError = true;
					break;
				}
				isFdCascade = true;
				fdCascadeDataJson = rs;
			}
			for(var j = 0 ;j < fieldNames.length; j++){
				var endFieldName = fieldNames[j].substring(fieldNames[j].lastIndexOf(".") + 1);
				// 有上级
				if(isFdCascade == true){
					if((fieldName + "Id") == endFieldName){
						//填充id
						fieldValues[fieldNames[j]] = fdCascadeDataJson.fdId;
					}else if((fieldName + "Name") == endFieldName){
						//填充name
						fieldValues[fieldNames[j]] = fdCascadeDataJson.fdCascadeName;
					}
				}else if(endFieldName == fieldName){
					fieldValues[fieldNames[j]] = data[fieldName];
					break;
				}
			}
		}
		if(isError == true){
			continue;
		}
		DocList_AddRow(table.id,null,fieldValues);
	}
	return errorLog;
}

// 校验导入的实际值是否存在 废弃
function judgeFdCascadeMatch(val){
	var datas = xform_main_data_custom_parameter.cascadeCustomDict;
	var result = {'isMatch':false,'fdCascadeId':'','fdCascadeName':'',fdId:''};
	if(datas){
		for(var i = 0;i < datas.length;i++){
			var data = datas[i];
			if(data.value == val){
				result.isMatch = true;
				result.fdCascadeId = val;
				result.fdCascadeName = data.text;
				result.fdId = data.fdId;
			}
		}	
	}
	
	return result;
}

	
