$(function(){
	
	$form.regist({
		support : function(target){
			return target.type=='relationChoose';
		},
		readOnly : function(target, value){
			var element = target.display || target.element;
			var operationDiv = target.root.find(".relationChoose_operation");
			if(value == null){
				if(operationDiv.length > 0){
					return !operationDiv.is(':visible');
				}
				return;
			}
			if(operationDiv.length > 0){
				if(value){
					operationDiv.hide();
					target.root.find(".relationChoose_textShow").removeClass("relationChoose_textShow_Edit");
				}else{
					operationDiv.css("display","table-cell");
					target.root.find(".relationChoose_textShow").addClass("relationChoose_textShow_Edit");
				}
			}else{
				return false;
			}
		},
		// 必填标签兄弟节点
		// 让显示值的input也是指向select
		getRequiredFlagPreElement : function(target){
			var rsDom = target.display || target.element;
			if(target.root){
				var property = target.root.attr('property');
				rsDom = target.root.find("[name='"+property+"']");
			}
			return rsDom;
		}
	});

    relation_choose_load_ready();

	//明细表内控件 有明细表的 table-add 事件触发初始化
	$(document).on('table-add','table[showStatisticRow]',function(e,row){
		$(row).find("div[mytype='relation_choose']").each(function(i,obj){
			Relation_Choose_addInputControl(obj);
			Relation_Choose_Copy(obj);
		});
	});
});

/** 监听高级明细表 */
$(document).on("detailsTable-init", function(e, tbObj){
    relation_choose_load_ready(tbObj);
})

function relation_choose_load_ready(tbObj) {
    var context = tbObj || document;
    $("xformflag[flagtype='xform_relation_choose']", context).each(function(i,xformflag){
        var $dom = $(xformflag).find("div[mytype='relation_choose']");
        if($dom.length > 0){
            // 查看页面没有div，不需要监听
            Relation_Choose_addInputControl($dom[0]);
            Relation_choose_initShowTextBlock($(xformflag),'edit');
			Relation_choose_initShowTextBlock_nocopy($(xformflag),'edit');
        }else{
            Relation_choose_initShowTextBlock($(xformflag),'view');
			Relation_choose_initShowTextBlock_nocopy($(xformflag),'view');
        }
    });
}

function Relation_Choose_Copy(obj) {
	var $dom = $(obj);
	var showStatus;
	if($dom.length > 0){ 
		showStatus = 'edit';
	} else {
		showStatus = 'view';
	}
	var xformflag = $(obj).closest("xformflag");
	Relation_choose_initShowTextBlock(xformflag,showStatus);	
}

function Relation_Choose_addInputControl(obj){
	var controlId = $(obj).closest("xformflag").attr("flagid");
	// 如果是明细表的模板行，直接忽略
	if(controlId.indexOf("!{index}")>=0){
		return;
	}
	var $divField = $(obj).closest("xformflag").find(".relationChoose_textShow");
	var text = $("[name='extendDataFormInfo.value(" + controlId + "_text)']" ).val();
//	$divField.text(text);
	var inputDomIds = $(obj).attr("inputDomIds");
	var outFormIds = getOutParamfieldIdForm($(obj).attr("outputParams"));
	if(inputDomIds && inputDomIds != ''){
		var idsArray = inputDomIds.split(";");
		var isIndetails = false;
		var detailFromId;
		var rowIndex;
		if(/\.(\d+)\./g.test(controlId)){
			rowIndex = controlId.match(/\.(\d+)\./g);
			rowIndex = rowIndex ? rowIndex : [];
			//当前弹出框控件的明细表ID
			detailFromId = controlId.split(".")[0];
			isIndetails = true;
		}
		for(var i = 0;i < idsArray.length;i++){
			var id = idsArray[i];
			if(!id){
				continue;
			}
			//若传出和传入有相同的字段，则不绑定值改变清空事件
			if($.inArray(id, outFormIds)<0){
				// 输入控件ID在明细表内
				if(id.indexOf(".") > -1){
					// 只处理选择框控件在明细表内
					if(isIndetails){
						// 只处理选择框控件和输入框控件在同表的情况
						if(id.indexOf(detailFromId) > -1){
							id = id.replace(".",rowIndex[0]);
						}else{
							alert(XformObject_Lang.relationChoose_msg);
							return;
						}
					}else{
						alert(XformObject_Lang.relationChoose_msg2);
						return;
					}
				}
				if(/-fd(\w+)/g.test(id)){
					id = id.match(/(\S+)-/g)[0].replace("-","");
				}
				var bindStr = document.getElementById(id)?"#" + id:'[name*="' + id + '"]';
				$(bindStr).on('change',function(){
					Relation_Choose_clearValue(obj);
				});
			}
		}
	}
}

function getOutParamfieldIdForm(outputParams){
	var outFormIds = [];
	if(outputParams){
		var outputsJSON = JSON.parse(outputParams.replace(/quot;/g, "\""));
		for(var out in outputsJSON){
			var fieldIdFormTemp=outputsJSON[out].fieldIdForm;
			if(fieldIdFormTemp){
				outFormIds.push(fieldIdFormTemp);
			}
		}
	}
	return outFormIds;
}

function Relation_Choose_Run(obj){
	var myid = relation_getParentXformFlagIdHasPre(obj);
	if(/\((\w+\.\d+.\w+)\)/.test(myid)){
		var resArray = /\((\w+\.\d+.\w+)\)/.exec(myid);
		if(resArray.length > 0){
			myid = resArray[1];
		}
	}else if(/\(\w+\)/.test(myid)){
		var resArray = /\((\w+)\)/.exec(myid);
		if(resArray.length > 0){
			myid = resArray[1];
		}
	}
	myid = myid.replace(/\./g,'_');
	//正在加载时,不需要再次触发
	if(document.getElementById("spinner_img_"+myid)){
		return;
	}
	// execRelationEvent方法在relation_event_run里面 by zhugr 2017-08-10
	execRelationEvent($(obj),$(obj).attr("inputParams"),$(obj).attr("outputParams"),$(obj).attr("outerSearchParams"),$(obj).attr("params"),myid,$(obj),Realtion_Choose_BuildShow);
}

function Relation_Choose_FormatSelectedDatas(checkedRows, rowsContext) {
	var selectedDatas = [];
	for (var i = 0; i < checkedRows.length; i++) {
		var checkedRow = checkedRows[i];
		var itemInfo = {};
		var itemVals = [];
		for (var j = 0; j < checkedRow.length; j++) {
			itemVals.push(checkedRow[j]);
		}
		itemInfo["itemVals"] = itemVals;
		if (rowsContext) {
			var rowContext = Relation_Choose_FindRowContext(checkedRow, rowsContext.context);
			if (rowContext) {
				$.extend(itemInfo, rowContext);
			}
		} else {
			itemInfo.currentRecordId = checkedRow.currentRecordId;
		}
		selectedDatas.push(itemInfo);
	}
	return selectedDatas;
}

function Relation_Choose_FindRowContext(curRow, rowsContext){
	for (var i = 0; i < rowsContext.length; i++) {
		var context = rowsContext[i];
		var fdId = context.fdId || context.currentRecordId;
		if (curRow.currentRecordId === fdId) {
			return context;
		}
	}
	return;
}

// 通过选框选择选项的时候，参数为$dom,result；只有一条数据或者直接多行返回时，参数为$dom,null,data
function Realtion_Choose_BuildShow($dom,result,data){
	var $xformflag = $dom.closest("xformflag"); 
	var $div = $xformflag.find(".relationChoose_textShow");
	var parentClass = $div.attr("parentClass");
	// checkedRows被选择选项，有选中的值，有选中的选项的索引checkedIndex
	if($div && $div.length > 0){
		$div.empty();
		var html = "";
		// 以页面上是否存在 dataurl 元素来判断是否是历史数据
		var $inputDataFdId = Relation_Choose_FindNameEndByStr($xformflag,'dataFdId');
		var $inputSeletedDatas = Relation_Choose_FindNameEndByStr($xformflag,'selectedDatas');
		/************************封装一层*********************************/
		var rowsContext = {};// {islink,context}
		rowsContext.islink = false;
		rowsContext.context = [];
		if(result && result.checkedRows && result.checkedRows.length > 0 && result.objData.data.hasRowsContext && $inputDataFdId.length > 0){
			// 最新版本
			rowsContext.islink = true;
			for(var i  = 0;i < result.checkedRows.length;i++){
				var rows = result.checkedRows[i];
				//var context = result.objData.data.rowsContext[rows.checkedIndex];
				var context = result.rowsContext[i] || result.objData.data.rowsContext[rows.checkedIndex];
				if(context && context.fdId && context.fdSubject){
					rowsContext.context.push(context);
				}else if (context && context.currentRecordId){
					rowsContext.context.push(context);
				}
			}
		}else if(data){
			rowsContext.islink = true;
			for(var i  = 0;i < data.rowsContext.length;i++){
				var context = data.rowsContext[i];
				if(context && context.fdId && context.fdSubject){
					rowsContext.context.push(context);
				}
			}
		}
		/************************封装一层*********************************/
		if(rowsContext.islink && rowsContext.context.length > 0 && rowsContext.context[0].hasUrl){
			//代码同上
			var dataFdId = '';
			var dataModelName = '';
			var dataSourceId = '';
			for(var i  = 0;i < rowsContext.context.length;i++){
				var context = rowsContext.context[i];
				if(context && context.fdId && context.fdSubject){
					// 系统内数据
					var url = Relation_Choose_BuildActionUrl(context.fdId,context.fdModelName);
					html += Relation_Choose_GetUrlHTML(url,context.fdSubject);
					dataFdId += context.fdId + ';';
					dataModelName = context.fdModelName;
					dataSourceId = context.fdSourceId;
				}else if (context && context.currentRecordId){
					var url = Relation_Choose_BuildActionUrl(context.currentRecordId,context.fdModelName);
					html += Relation_Choose_GetUrlHTML(url,context.fdSubject);
					dataFdId += context.currentRecordId + ';';
					dataModelName = context.fdModelName;
					dataSourceId = context.fdSourceId;
				}
			}
			$inputDataFdId.val(Relation_Choose_FilterLastSemicolon(dataFdId));
            var checkedRowsStr ="";
            if (result && result.checkedRows) {
                checkedRowsStr = JSON.stringify(Relation_Choose_FormatSelectedDatas(result.checkedRows, rowsContext));
            } else {
                checkedRowsStr = JSON.stringify(Relation_Choose_FormatSelectedDatas(data.rows, rowsContext));
            }
			checkedRowsStr = checkedRowsStr.replace(/\"/g, "quot;");
			$inputSeletedDatas.val(checkedRowsStr);
			Relation_Choose_FindNameEndByStr($xformflag,'dataModelName').val(dataModelName);
			Relation_Choose_FindNameEndByStr($xformflag,'dataSourceId').val(dataSourceId);
		}else{
			// 没有上下文，按原来的展示
			var textId = relation_getTextNameByControl($dom[0]);
			if (result && result.checkedRows) { //弹窗返回
				if (result.objData.paramsJSON.hasRowIdentity) {
					var checkedRowsStr = JSON.stringify(Relation_Choose_FormatSelectedDatas(result.checkedRows));
					checkedRowsStr = checkedRowsStr.replace(/\"/g, "quot;");
					$inputSeletedDatas.val(checkedRowsStr);
				}
			} else { //直接返回
				if (data && data.rows) {
					if (data.hasRowIdentity) {
						var checkedRowsStr = JSON.stringify(Relation_Choose_FormatSelectedDatas(data.rows));
						checkedRowsStr = checkedRowsStr.replace(/\"/g, "quot;");
						$inputSeletedDatas.val(checkedRowsStr);
					}
				}
			}
			html = Relation_Choose_GetNoUrlHTML($("input[name*='"+ textId +"']").val());
		}
		
		$div.append(html);
		$div.find(".com_btn_link").addClass(parentClass);
	}
	//执行一次校验
	var myid = $dom.attr("myid");
	var validation = $GetFormValidation(document.forms[0]);
	if(myid && validation && $("input[name='"+myid+"']").length > 0){
		validation.validateElement($("input[name='"+myid+"']")[0]);
	}
}

function Relation_Choose_BuildActionUrl(fdId , fdModelName){
	return Com_Parameter.ContextPath + "sys/xform/maindata/main_data_show/sysFormMainDataShow.do?method=cardInfo&fdId="+ fdId +"&modelName=" + fdModelName;
}

// 清除最后一个分号
function Relation_Choose_FilterLastSemicolon(str){
	var reg=/;$/gi; 
	str = str.replace(reg , "");
	return str
}

// 有链接的HTML
function Relation_Choose_GetUrlHTML(url,subject){
	var html;
	html = "<a class='com_btn_link' href='javascript:void(0)' ajax-href='"+ url +"' onmouseover='if(window.LUI && window.LUI.maindata) window.LUI.maindata(event,this);' >"+ subject +"</a>";
	return html;
}

// 没链接的HTML，即原来的样子
function Relation_Choose_GetNoUrlHTML(text){
	var html;
	html = text;
	return html;
}

// 查找以参数结尾的元素,默认最后带右括号
function Relation_Choose_FindNameEndByStr($area,str){
	return $area.find("input[name$='_"+ str +")']");
}

//清空
function Relation_Choose_clearValue(dom){
	if(dom == null){
		return;
	}
	var xformflag = $(dom).closest("xformflag");
	if(xformflag){
		//清空选择框的值的同时，清空输出控件的值
		Relation_Choose_clearOutputControlsValue(xformflag.find("[mytype='relation_choose']")[0]);
		//执行一次校验
		var myid = xformflag.attr("property");
		var validation = $GetFormValidation(document.forms[0]);
		if(myid && validation && $("input[name='"+myid+"']").length > 0){
			validation.validateElement($("input[name='"+myid+"']")[0]);
		}
	}
}

// 清空输出控件的值，原理就是把空字符串传到输出控件里面
function Relation_Choose_clearOutputControlsValue(obj){
	var outputParams = $(obj).attr("outputParams");
	if(outputParams && outputParams != ''){
		var params = $(obj).attr("params");
		// 选择框控件在明细表里面
		var controlInDetail = false;
		var bindName = $(obj).attr("name");
		if(!bindName){
			bindName = relation_getParentXformFlagIdHasPre($(obj)); 
		}
		if(/\.(\d+)\./g.test(bindName)){
			controlInDetail = true;
		}
		var outputsJSON = JSON.parse(outputParams.replace(/quot;/g, "\""));
		var rows = [];
		var data = {};
		data.headers = [];
		rows[0] = [];
		for(var out in outputsJSON){
			var fieldId = outputsJSON[out].fieldIdForm;
			// 选择框在明细表外，输出控件在明细表内的时候，先不处理同步清空
			if(controlInDetail == false && fieldId.indexOf('.') > -1){
				continue;
			}
			data.headers.push({'fieldIdForm':outputsJSON[out].fieldIdForm});
			rows[0].push("");
		}
		setValueByDataRows(rows,data,$(obj));
		var $div = $(obj).closest("xformflag").find(".relationChoose_textShow");
		if($div.length > 0){
			$div.empty();
		}
		//清空dataFdId的值
		$(obj).closest("xformflag").find("[name*='_dataFdId']").val("");
		//清空selectedDatas的值
		$(obj).closest("xformflag").find("[name*='_selectedDatas']").val("");
	}
}

// 初始化展示块
function Relation_choose_initShowTextBlock($xformflag,type){
	var $div = $xformflag.find(".relationChoose_textShow");
	var parentClass = $div.attr("parentClass");
	var dataFdId = Relation_Choose_FindNameEndByStr($xformflag,'dataFdId');
	var html = '';
	if(dataFdId.length > 0){
		if(dataFdId.val() && dataFdId.val() != ''){
			var dataFdIdArr = dataFdId.val().split(";");
			// text以分号分隔，可能还有风险
			var textArr = Relation_Choose_FindNameEndByStr($xformflag,'text').val().split(";");
			var dataModelName = Relation_Choose_FindNameEndByStr($xformflag,'dataModelName').val();
			for(var i = 0;i < dataFdIdArr.length;i++){
				//#65607 选择框，数据字典配置链接时，但该字段为空，查看页面为undefined，请屏蔽
				if (typeof textArr[i] === "undefined"){
					continue;
				}
				html += Relation_Choose_GetUrlHTML(Relation_Choose_BuildActionUrl(dataFdIdArr[i],dataModelName),textArr[i]);
			}
			if(type && type == 'edit'){
				$div.addClass("relationChoose_textShow_Edit");
			}
			$div.append(html);
			$div.find(".com_btn_link").addClass(parentClass);
			return;
		}
	}
	
	var text = $xformflag.find("input[name$='_text)']").val();
	html = Relation_Choose_GetNoUrlHTML(text);
	if(type && type == 'edit'){
		$div.addClass("relationChoose_textShow_Edit");
	}
	
	$div.append(html);
}

// 明细表copy行时，不在调用宽度重置方法
function Relation_choose_initShowTextBlock_nocopy($xformflag,type){
	var $content = $xformflag.find(".relationChoose_content");
	if ($content[0]){
		var content = $content[0];
		var width = $content.attr("cfgWidth") || content.style.width;
		var cfgWidth = width;
		width = parseInt(width);
		if (width === 0) {
			$xformflag.find(".relationChoose_textShow").css("display","none");
		} else {
			var relatinoTextObj = $(".relationChoose_textShow",content);
			if (type === "edit" && content.style.width && content.style.width.indexOf("px") > -1) {
				var width = $content.width();
				var outerWidth = parseInt(width) + 56 + "px";
				var innerWidth = parseInt(width) + "px";
				relatinoTextObj.css("min-width","0px");
				relatinoTextObj.css("width",innerWidth);
				$content.css("width", outerWidth);
			}
			if (type === "view" && content.style.width && content.style.width.indexOf("px") > -1) {
				relatinoTextObj.css("min-width","0px");
			}
		}
		if (cfgWidth && cfgWidth.indexOf("%") > -1) {
			$content.parent().css("width", cfgWidth);
			$content.parent().css("display", "inline-table");
			$content.attr("cfgWidth", cfgWidth);
			$content.css("width", "100%");
		}
	}
}