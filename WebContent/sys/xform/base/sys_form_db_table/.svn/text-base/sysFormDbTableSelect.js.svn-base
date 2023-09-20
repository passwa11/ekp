/**
表单映射JS
*/
// utils
var ColumnTableTemplate = [];

function GetElement(dom, tagName, fn) {
	var tag = $(dom).find(tagName);
	var i;
	for (i = 0; i < tag.length; i ++) {
		if (fn(tag[i])) {
			return tag[i];
		}
	}
}
function GetParent(dom, tagName) {
	
	tagName = tagName.toUpperCase();
	var p = $(dom).parent()[0];
	while( p != null && p.tagName != tagName && p.tagName != 'BODY') {
		p = p.parentNode;
	}
	return p;
}

// ========= 模板 ============

function SysFormDb_CompileTemplate(template) { 
    var TEMPLATE_START = "!{";
	var TEMPLATE_END = "/}";
	var templateC = [];
	var snippets = [];
	var current = 0;
	while (true) {
		var start = template.indexOf(TEMPLATE_START, current);
		var sBegin = start + TEMPLATE_START.length;
		var sEnd = template.indexOf(TEMPLATE_END, sBegin);

		if (sBegin >= 2 && sEnd > sBegin) {
			templateC.push(template.substring(current, start));
			var sn = template.substring(sBegin, sEnd);
			if (sn.indexOf("!") == 0) {
				sn = eval(sn.substring(1));
			} else {
				snippets.push(templateC.length);
			}
			templateC.push(sn);
		} else {
			templateC.push(template.substring(current));
			break;
		}
		current = sEnd + 2;
	}
	templateC.push(snippets);
	return templateC;
}

function SysFormDb_RunTemplate(templateC,invar) {
    var VAR = invar;

	var snippets = templateC[templateC.length - 1];

	var rs = [];
	var sIdx = 0;
	for ( var i = 0; i < templateC.length - 1; i++) {
		if (snippets[sIdx] == i) {
			//作者 曹映辉 #日期 2015年5月21日 防止safari浏览器解析解析异常
			if(templateC[i])
				templateC[i]=templateC[i].replace(/&gt;/ig,">").replace(/&lt;/ig,"<");
			rs.push(eval(templateC[i]));
			sIdx++;
		} else {
			rs.push(templateC[i]);
		}
	}

	html = rs.join("");	
	if (html.indexOf("allowEdit=\"readOnly\"") >= 0){		
		html = html.replace("allowEdit=\"readOnly\"","readOnly")
	}else if(html.indexOf("allowedit=\"readOnly\"") >= 0){
		html = html.replace("allowedit=\"readOnly\"","readOnly")
	}
	return html;
}

//========== 通用业务
function isPkRow(tr) {
	var isPK = GetElement(tr, 'input', function(input) {
		return ($(input).attr('name')  && $(input).attr('name').indexOf('fdIsPk') > -1);
	});
	return (isPK.value == 'true');
}
//liyong 用户判断自动建表时不允许取消子表关联主表的字段，isAuto只是在自动建表时才有
function isFdParent(tr){
	var select = GetElement(tr, 'input', function(input) {
		return ($(input).attr('name')  && $(input).attr('name').indexOf('fdName') > -1);
	}); 
	return (select  && select.value == "fdParent");
}

function InitMainTableColumnTable() {
	var table = document.getElementById('columnTable'), i, j, row, cell;
	for (i = table.rows.length-1; i > 1; i --) {
		row = table.rows[i];
		if (row.className = 'template' && ColumnTableTemplate.length == 0) {
			for (j = 0; j < row.cells.length; j ++) {
				ColumnTableTemplate[ColumnTableTemplate.length] = SysFormDb_CompileTemplate(row.cells[j].innerHTML);
			}
		}
		table.deleteRow(i);
	}
}

function __transToPinyin(str) {
	//中文转拼音
	var pinyin = new Pinyin();
	var afterTrans = pinyin.getFullChars(str);
	//拼音首字母小写				
	if(afterTrans && afterTrans.length >= 2){
		afterTrans = afterTrans.charAt(0).toLowerCase() + afterTrans.substr(1);
	}
	var val = 'fd_' + afterTrans;
	//转换后的长度校验，加上'fd_'不能超过30个字符
	if(val.length > 30){
		val = val.substr(0,30);
	}
	return val;
}

//#136113 表单映射增加默认选中
function FillDefaultValues(table,values) {
	var fdColumnInputs = $(table).find("[name$='fdColumn']");
	var selectedNames = [];
	for (var i = 0; i < fdColumnInputs.length; i ++) {
		var input = fdColumnInputs[i];
		var columnName = input.value;
		if ($(input).attr('name')  && $(input).attr('name').indexOf('.fdColumn') > -1) {
			var tr = GetParent(input, 'tr');
			if (input.value == '' || isPkRow(tr)) {
				continue;
			}
			var nameSelect = GetElement(tr, 'select', function(select) {
				return (select.className == 'fdNameSelect');
			});
			var dataTypeInput = GetElement(tr, 'input', function(input) {
				return ($(input).attr('name')  && $(input).attr('name').indexOf('.fdDataType') > -1);
			});
			var columnInfo = FindValueByColumn(values, columnName);
			if (columnInfo) {
				var colName = columnInfo["column"];
				var colType = columnInfo["dataType"];
				if (dataTypeInput.value === colType) {
					$(nameSelect).find("option").each(function(index, option){
						var optionValue = option.value;
						if (optionValue != "" ) {
							var labelToPinyin = __transToPinyin(option.label).toLowerCase();
							var labelNoFdToPinyin = labelToPinyin.replace(/^fd_/,"").toLowerCase();
							var columnToLowerCase = colName.toLowerCase();
							if ((columnToLowerCase === optionValue || labelToPinyin === columnToLowerCase 
										|| labelNoFdToPinyin === columnToLowerCase)
									&& selectedNames.indexOf(optionValue) === -1 
									|| (optionValue === "fdParent" && "fd_parent_id" === columnToLowerCase)) {
								selectedNames.push(optionValue);
								$(nameSelect).find("option").eq(index).prop("selected",true);
								var nameHiddenObj =  GetElement(tr, 'input', function(inputObj) {
									return (inputObj.name && inputObj.name.indexOf(".fdName") > -1);
								});
								$(nameSelect).trigger("change");
								$(nameHiddenObj).val(optionValue);
								var enableBox = GetElement(tr, 'input', function(inputObj) {
									return (inputObj.className == 'enableBox' && inputObj.type === "checkbox");
								});
								$(enableBox).click();
								return false;
							}
						}
					});
				}
			}
		}
	}
}

function FindValueByColumn(values, columnName){
	if (values.length > 0 && columnName)
	for (var i = 0; i < values.length; i++) {
		var colVal = values[i];
		if (colVal["column"] === columnName) {
			return colVal
		}
	}
	return null;
}

// 还原装填旧数据
function FillOldValues(table, oldValues) {
	if (oldValues != null && oldValues.length > 0) {
		var inputs = $(table).find('input');
		for (var i = 0; i < inputs.length; i ++) {
			var input = inputs[i];
			if ($(input).attr('name')  && $(input).attr('name').indexOf('.fdColumn') > -1) {
				var tr = GetParent(input, 'tr');
				if (input.value == '' || isPkRow(tr)) {
					continue;
				}
				for (var j = 0; j < oldValues.length; j ++) {
					if (oldValues[j].column == input.value) {
						var select = GetElement(tr, 'select', function(select) {
							return (select.className == 'fdNameSelect');
						});
						select.value = oldValues[j].name;
						var fdName = GetElement(tr, 'input', function(input) {
							return ($(input).attr('name')  && $(input).attr('name').indexOf('fdName') > -1);
						});
						fdName.value = oldValues[j].name;
						var checkbox = GetElement(tr, 'input', function(input) {
							return (input.type == 'checkbox' && input.className == 'enableBox');
						});
						checkbox.checked = true;
						SetEnableRow(tr);
					}
				}
			}
		}
	}
}
// 获取行当前配置
function GetRowValue(dom) {
	var tr = dom.tagName == 'TR' ? dom : GetParent(dom, 'tr');
	var fdName = GetElement(tr, 'input', function(input) {
		return ($(input).attr('name')  && $(input).attr('name').indexOf('fdName') > -1);
	});
	var fdColumn = GetElement(tr, 'input', function(input) {
		return ($(input).attr('name')  && $(input).attr('name').indexOf('.fdColumn', $(input).attr('name').indexOf('.fdColumns') + 1) > -1);
	});
	return {name: fdName.value, column: fdColumn.value};
}
// 获取当前表格配置
function GetOldValues(table) {
	var oldValues = [], i, input;
	var inputs = $(table).find('input');
	for (i = 0; i < inputs.length; i ++) {
		input = inputs[i];
		if (input.type == 'checkbox' && input.className == 'enableBox') {
			if (input.checked) {
				oldValues.push(GetRowValue(input));
			}
		}
	}
	return oldValues;
}

//设置行是否有效
function SetEnableRow(tr, enable) {
	var select;
	var checkbox = GetElement(tr, 'input', function(input) {
		return (input.className == 'enableBox');
	});
	var fdIsEnable = GetElement(tr, 'input', function(input) {
		return ($(input).attr('name')  && $(input).attr('name').indexOf('.fdIsEnable') > -1);
	});
	if (isPkRow(tr) || isFdParent(tr) || enable == 'true') {
		checkbox.checked = true;
	}
	
	select = GetElement(tr, 'select', function(select) {
		return (select.fdNameSelect == 'true');
	});
	if (select != null) {
		select.disabled = !checkbox.checked;
		if (select.disabled) {
			new Reminder(select).hide();
		}
	}
	fdIsEnable.value = checkbox.checked;
}
// 设置子表是否有效
function SetEnableSubTable(tr) {
	var inputs, i, input, fdTable, links;
	var checkbox = GetElement(tr, 'input', function(input) {
		return (input.className == 'tableEnableBox');
	});
	var fdIsPublish = GetElement(tr, 'input', function(input) {
		return ($(input).attr('name')  && $(input).attr('name').indexOf('.fdIsPublish') > -1);
	});
	fdIsPublish.value = checkbox.checked;
	links = $(tr).find('a');
	for (i = 0; i < links.length; i ++) {
		links[i].style.display = checkbox.checked ? '' : 'none';
	}
	fdTable =  GetElement(tr, 'input', function(input) {
		return ($(input).attr('name')  && $(input).attr('name').indexOf('.fdTable') > -1);
	});
	tr = tr.nextSibling;
	tr.style.display = (fdTable.value == '' || !checkbox.checked) ? 'none' : '';

	inputs = $(tr).find('input');
	for (i = 0; i < inputs.length; i ++) {
		inputs[i].disabled = !checkbox.checked;
	}
	inputs = $(tr).find('select');
	for (i = 0; i < inputs.length; i ++) {
		if (!checkbox.checked)
			inputs[i].disabled = true;
		else {
			var row = GetParent(inputs[i], 'tr');
			SetEnableRow(row);
		}
	}
}