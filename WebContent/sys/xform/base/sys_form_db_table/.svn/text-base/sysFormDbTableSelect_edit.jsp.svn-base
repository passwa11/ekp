<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/resource/jsp/common.jsp"%>
<%@ include file="/resource/jsp/edit_top.jsp"%>

<kmss:windowTitle
	subjectKey="sys-xform:table.sysFormDbColumn.create.title"
	subject="${sysFormDbTableForm.fdName}"
	moduleKey="sys-xform:table.sysFormDbTable" />
	
	<div id="optBarDiv">
	<c:if test="${not empty reToFormUrl}">
		<input type=button value="<kmss:message key="sys-xform:sysFormDbTable.btn.reToForm" />" onclick="Com_OpenWindow('${reToFormUrl}', '_blank');"/>
	</c:if>
		<input type=button value="<kmss:message key="sys-xform:sysFormDbTable.btn.showAllFields" />" onclick="ShowXFormFields();"/>
		<%-- 保存 --%>
		<c:if test="${sysFormDbTableForm.tagerMethod=='add'}">
			<input type=button value="<bean:message key="button.submit"/>"
				onclick="submitForm('save');">
		</c:if>
		<%-- 更新 --%>
		<c:if test="${sysFormDbTableForm.tagerMethod=='edit'}">
			<input type=button value="<bean:message key="button.submit"/>"
				onclick="submitForm('update');">
		</c:if>
		
		<%-- 关闭 --%>
		<input type="button" value="<bean:message key="button.close"/>"
			onclick="Com_CloseWindow();">
	</div>
	
	<%-- 显示标题 --%>
	<p class="txttitle">
		<bean:message bundle="sys-xform" key="table.sysFormDbTable" />
	</p>

<html:form action="/sys/xform/base/sys_form_db_table/sysFormDbTable.do" >
	
	<center>
	<table class="tb_normal" width="95%" id="db_table">
	<tbody>
		<tr>
			<td class="td_normal_title" width=15%><kmss:message key="sys-xform:sysFormDbTable.fdName" /></td>
			<td colspan="3"><html:text property="fdName" style="width:90%" styleClass="inputsgl" /></td>
		</tr>
		<tr>
			<td class="td_normal_title" width=15%><kmss:message key="sys-xform:sysFormDbTable.fdFormName" /></td>
			<td width=35%>
				<html:hidden property="fdFormType"/>
				<html:hidden property="fdFormId"/>
				<html:text property="fdFormName" style="width:90%" readonly="true" styleClass="inputsgl" />
			</td>
			<td class="td_normal_title" width=15%><kmss:message key="sys-xform:sysFormDbTable.fdTable" /></td>
			<td width=35%>
				<html:text property="fdTable" style="width:70%" readonly="true" styleClass="inputsgl" />
				<a href="javascript:void(0)" onclick="SelectMainTable();"><kmss:message key="dialog.selectOther" /></a>
				<a href="javascript:void(0)" onclick="ReloadMainTable();"><kmss:message key="sys-xform:sysFormDbTable.btn.reloadTable" /></a>
			</td>
		</tr>
		<tr style="display:none;">
			<td colspan="4">
			<table id="columnTable" class="tb_normal" width="100%" style="display:none;">
				<tr class="tr_normal_title">
					<td width="20px" rowspan="2"><input type="checkbox" class="mainTableSelectAll" onclick="selectAll(this)"/>  </td>
					<td width="25px" rowspan="2"><kmss:message key="sys-xform:sysFormDbTable.No" /></td>
					<td width="150px" rowspan="2"><kmss:message key="sys-xform:sysFormDbColumn.fdName" /></td>
					<td width="200px" rowspan="2"><kmss:message key="sys-xform:sysFormDbColumn.fdColumn" /></td>
					<td width="50px" rowspan="2"><kmss:message key="sys-xform:sysFormDbColumn.fdLength" /></td>
					<td width="250px" colspan="2"><kmss:message key="sys-xform:sysFormDbColumn.type" /></td>
					<td width="*" rowspan="2"><kmss:message key="sys-xform:sysFormDbColumn.info" /></td>
				</tr>
				<tr class="tr_normal_title">
					<td width=""><kmss:message key="sys-xform:sysFormDbColumn.type.db" /></td>
					<td width=""><kmss:message key="sys-xform:sysFormDbColumn.type.java" /></td>
				</tr>
				
				<tr class="template">
					<td>
						<input type="hidden" name="!{ VAR.prefix /}fdColumns[!{ VAR.index /}].fdIsEnable" value="!{ VAR.isEnable /}">
						<input type="hidden" name="!{ VAR.prefix /}fdColumns[!{ VAR.index /}].fdOrder" value="!{ VAR.order == null ? VAR.index : VAR.order /}">
						<input type="checkbox" class="enableBox" onclick="SetEnableRow(this.parentNode.parentNode);">
					</td>
					<td>!{ VAR.index + 1/}</td>
					<td>
						<input type="hidden" name="!{ VAR.prefix /}fdColumns[!{ VAR.index /}].fdName" value="!{ VAR.name /}">
						<select class="fdNameSelect" fdNameSelect="true" id="!{ VAR.prefix /}_!{ VAR.index /}_fdNameSelect">
							<option value=""><kmss:message key="sys-xform:sysFormDbTable.help.pleaseSelect" /></option>
						</select>
					</td>
					<td>
						<input type="text" style="width:90%" class="inputsgl"
							name="!{ VAR.prefix /}fdColumns[!{ VAR.index /}].fdColumn" value="!{ VAR.column ? VAR.column : VAR.tableName /}"  >
					</td>
					<td>
						<input type="text" style="width:90%" class="inputsgl" 
							name="!{ VAR.prefix /}fdColumns[!{ VAR.index /}].fdLength" value="!{ VAR.maxLength /}" readOnly >
					</td>
					<td>
						<input type="text" style="width:100px" class="inputsgl" 
							name="!{ VAR.prefix /}fdColumns[!{ VAR.index /}].fdDataType" 
							value="!{ VAR.dataType ? VAR.dataType : '<kmss:message key="sys-xform:sysFormDbColumn.fdRelType.table" />' /}" readOnly >
					</td>
					<td>
						<input type="text" style="width:200px" class="inputsgl" 
							name="!{ VAR.prefix /}fdColumns[!{ VAR.index /}].fdType" value="!{ VAR.type /}" readOnly >
					</td>
					<td>
						<input type="hidden" name="!{ VAR.prefix /}fdColumns[!{ VAR.index /}].fdIsPk" value="!{ VAR.isPk /}" >
						
						<!-- oneToMany, manyToMany, manyToOne -->
						!{ GetDesMessage(VAR) /}
						<input type="hidden" name="!{ VAR.prefix /}fdColumns[!{ VAR.index /}].fdRelation" value="!{ VAR.relation /}" >
						<input type="hidden" name="!{ VAR.prefix /}fdColumns[!{ VAR.index /}].fdTableName" value="!{ VAR.tableName /}" >
						<input type="hidden" name="!{ VAR.prefix /}fdColumns[!{ VAR.index /}].fdModelName" value="!{ VAR.modelName /}" >
						<input type="hidden" name="!{ VAR.prefix /}fdColumns[!{ VAR.index /}].fdModelText" value="!{ VAR.modelText /}" >
						<input type="hidden" name="!{ VAR.prefix /}fdColumns[!{ VAR.index /}].fdMainColumn" value="!{ VAR.mainColumn /}" >
						<input type="hidden" name="!{ VAR.prefix /}fdColumns[!{ VAR.index /}].fdElementColumn" value="!{ VAR.elementColumn /}" >
					</td>
				</tr>
			</table>
			</td>
		</tr>
		</tbody>
		<tbody id="subTable">
		<!-- 子表 -->
		<tr class="templateTitle">
			<td class="td_normal_title" width=15%>
				<input type="hidden" name="fdTables[!{ VAR.index /}].fdId" value="!{ VAR.id /}" >
				<input type="hidden" name="fdTables[!{ VAR.index /}].fdIsPublish" value="!{ VAR.isPublish /}">
				<input type="checkbox" class="tableEnableBox" onclick="SetEnableSubTable(this.parentNode.parentNode);"/>
				<kmss:message key="sys-xform:sysFormDbTable.subFormName" />
			</td>
			<td width=35%>
				<input type="hidden" name="fdTables[!{ VAR.index /}].fdName" value="!{ VAR.name /}"/>
				!{ VAR.label /}
			</td>
			<td class="td_normal_title" width=15%><kmss:message key="sys-xform:sysFormDbTable.fdTable" /></td>
			<td width=35%>
				<input type="text" name="fdTables[!{ VAR.index /}].fdTable" value="!{ VAR.fdTable /}" style="width:70%" readonly class="inputsgl" validate="checkOnly" />
				<a href="javascript:void(0)" onclick="SelectSubTable(this, '!{ VAR.index /}');"><kmss:message key="dialog.selectOther" /></a>
				<a href="javascript:void(0)" onclick="ReloadSubTable(this, '!{ VAR.index /}');"><kmss:message key="sys-xform:sysFormDbTable.btn.reloadTable" /></a>
			</td>
		</tr>
		<tr class="templateColumns">
			<td colspan="4">
			<table class="tb_normal" width="100%">
				<tr class="tr_normal_title">
					<td width="20px" rowspan="2"></td>
					<td width="25px" rowspan="2"><kmss:message key="sys-xform:sysFormDbTable.No" /></td>
					<td width="20%" rowspan="2"><kmss:message key="sys-xform:sysFormDbColumn.fdName" /></td>
					<td width="20%" rowspan="2"><kmss:message key="sys-xform:sysFormDbColumn.fdColumn" /></td>
					<td width="5%" rowspan="2"><kmss:message key="sys-xform:sysFormDbColumn.fdLength" /></td>
					<td width="25%" colspan="2"><kmss:message key="sys-xform:sysFormDbColumn.type" /></td>
					<td width="30%" rowspan="2"><kmss:message key="sys-xform:sysFormDbColumn.info" /></td>
				</tr>
				<tr class="tr_normal_title">
					<td width=""><kmss:message key="sys-xform:sysFormDbColumn.type.db" /></td>
					<td width=""><kmss:message key="sys-xform:sysFormDbColumn.type.java" /></td>
				</tr>
			</table>
			</td>
		</tr>
		</tbody>
	</table>
	</center>

	<html:hidden property="fdKey" />
	<html:hidden property="fdModelName" />
	<html:hidden property="fdTemplateModel" />
	<html:hidden property="method_GET" />
	<html:hidden property="fdId" />
</html:form>
<script>Com_IncludeFile("dialog.js|data.js");</script>
<script src="<c:url value="/sys/xform/base/sys_form_db_table/sysFormDbTableSelect.js" />"></script>
<script src="<c:url value="/sys/xform/base/resource/js/pinYin.js" />"></script>
<script>
function selectAll(obj){ 
	var tr = GetParent(obj, 'table');	 
	inputs = $(tr).find('select');
	for (i = 0; i < inputs.length; i ++) {
			var row = GetParent(inputs[i], 'tr');
			var checkboxs = GetElement(row, 'input', function(input) {
				return ($(input).attr('type') == 'checkbox');
			}); 
			checkboxs.checked = obj.checked; 
			SetEnableRow(row);		 
	}
}
function submitForm(method){
	Com_Submit(document.sysFormDbTableForm, method);
}
// 消息
function GetDesMessage(_VAR) {
	if (_VAR.isPk == 'true') {
		return GetRelationMessage('isPk');
	}
	if (_VAR.relation != null && _VAR.relation != '') {
		if (_VAR.relation == 'manyToOne') {
			if (_VAR.modelName != null && _VAR.modelName != '') {
				return GetRelationMessage('manyToOne_sys', _VAR.modelText);
			}
			return GetRelationMessage('manyToOne_table', _VAR.tableName);
		}
		return GetRelationMessage(_VAR.relation, _VAR.tableName);
	}
	return '';
}
function GetRelationMessage(key, text) {
	if (key == null || key == '') return "";
	return GetRelationMessage.msg[key].replace('{0}' , text);
}
GetRelationMessage.msg = {
		'isPk' : '<kmss:message key="sys-xform:sysFormDbColumn.fdIsPk" />',
		'oneToMany' : '<kmss:message key="sys-xform:sysFormDbColumn.relation.oneToMany" />',
		'manyToMany' : '<kmss:message key="sys-xform:sysFormDbColumn.relation.manyToMany" />',
		'manyToOne' : '<kmss:message key="sys-xform:sysFormDbColumn.relation.manyToOne_sys" />',
		'manyToOne_sys' : '<kmss:message key="sys-xform:sysFormDbColumn.relation.manyToOne_sys" />',
		'manyToOne_table' : '<kmss:message key="sys-xform:sysFormDbColumn.relation.manyToOne_table" />'
};

// 子表模板
var TableTemplate = {templateTitle:null,templateColumns:null};
// 创建子表
function BuildSubTableItems(subField) {
	 var tbody = document.getElementById('subTable'), i, templateTitle, templateColumns, cell, j, index = 0;

	 for (i = tbody.rows.length-1; i >= 0; i --) {
		row = tbody.rows[i];
		if (row.className == 'templateTitle' && TableTemplate.templateTitle == null) {
			templateTitle = [];
			for (j = 0; j < row.cells.length; j ++) {
				templateTitle[templateTitle.length] = SysFormDb_CompileTemplate(row.cells[j].innerHTML);
			}
			TableTemplate.templateTitle = templateTitle;
		}
		else
		if (row.className == 'templateColumns' && TableTemplate.templateColumns == null) {
			templateColumns = [];
			for (j = 0; j < row.cells.length; j ++) {
				templateColumns[templateColumns.length] = SysFormDb_CompileTemplate(row.cells[j].innerHTML);
			}
			TableTemplate.templateColumns = templateColumns;
		}
		tbody.deleteRow(i);
	}
	for (i = 0; i < subField.length; i ++) {
		if (subField[i].children == null) {
			continue;
		}
		var tr = tbody.insertRow(-1);
		for (j = 0; j < TableTemplate.templateTitle.length; j ++) {
			cell = tr.insertCell(-1);
			subField[i].index = index;
			html = SysFormDb_RunTemplate(TableTemplate.templateTitle[j], subField[i]);
			cell.innerHTML = html;
		}
		tr = tbody.insertRow(-1);
		tr.style.display = 'none';
		cell = tr.insertCell(-1);
		cell.colSpan = 4;
		for (j = 0; j < TableTemplate.templateColumns.length; j ++) {
			html = SysFormDb_RunTemplate(TableTemplate.templateColumns[j]);
			cell.innerHTML = html;
		}
		index ++;
		SetEnableSubTable(tr.previousSibling);
	}
}
function DeleteSubTableColumns(tr) {
	var table = tr.getElementsByTagName('table')[0];
	for (i = table.rows.length-1; i > 1; i --) {
		table.deleteRow(i);
	}
	return table;
}
/** 子表字段生成 */
function BuildSubTableColumns(values, dom) {
	var tr = GetParent(dom, 'tr'), i, html, j, row, cell;
	if (!ValHasPk(values)) {
		var fdTable =  GetElement(tr, 'input', function(input) {
			return (input.name != null && input.name.indexOf('.fdTable') > -1);
		});
		fdTable.value = '';
		DeleteSubTableColumns(tr.nextSibling);
		tr.nextSibling.style.display = 'none';
		return;
	}
	var fdName = GetElement(tr, 'input', function(input) {
		return (input.name != null && input.name.indexOf('.fdName') > -1);
	});
	var prefix = fdName.name.substring(0, fdName.name.indexOf('.'));
	var fields = [];
	for (i = 0; i < dictTree.length; i ++) {
		if (fdName.value == dictTree[i].name) {
			fields = dictTree[i].children;
		}
	}
	tr = tr.nextSibling;
	tr.style.display = '';
	var table = tr.getElementsByTagName('table')[0];
	var oldValues = GetOldValues(table);
	DeleteSubTableColumns(tr);
	for (i = 0; i < values.length; i ++) {
		tr = table.insertRow(-1);
		values[i].index = i;
		for (j = 0; j < ColumnTableTemplate.length; j ++) {
			cell = tr.insertCell(-1);
			values[i].prefix = prefix + '.';
			html = SysFormDb_RunTemplate(ColumnTableTemplate[j], values[i]);
			cell.innerHTML = html;
		}
		FillXFormOptions(tr ,fields);
		SetEnableRow(tr, values[i].isEnable);
	}
	FillDefaultValues(table,values)
	FillOldValues(table, oldValues);
}

function ReloadSubTable(dom, index) {
	var fdTable = document.getElementsByName('fdTables[' + index + '].fdTable')[0];
	if (fdTable.value != '') {
		ReLoadColumns(fdTable.value, function(data) {
				if (data == null) return;
				var values = data.GetHashMapArray();
				BuildSubTableColumns(values, dom);
			}
		);
	}
}

//选择子表
function SelectSubTable(dom, index) {//sysFormDbListService实际返回空
	Dialog_List(false, 'fdTables[' + index + '].fdTable', null, null, 'sysFormDbListService', 
			function(data) {LoadColumns(data, function(data) {
				if (data == null) return;
				var fdTable = document.getElementsByName('fdTables[' + index + '].fdTable')[0];
				new Reminder(fdTable).hide();
				var values = data.GetHashMapArray();
				BuildSubTableColumns(values, dom);}
			);},
			'sysFormDbSearchService&keyword=!{keyword}',
			false, true, 
			'<kmss:message key="sys-xform:sysFormDbTable.selectTable" />');
}

// 所有添加列方法
function FillXFormOptions(tr, fields) {
	var select = GetElement(tr, 'select', function(select) {
		return (select.className == 'fdNameSelect');
	});
	var fdName = GetElement(tr, 'input', function(input) {
		return (input.name != null && input.name.indexOf('.fdName') > -1);
	});
	if (isPkRow(tr)) {
		var p = select.parentNode;
		p.removeChild(select);
		fdName.value = 'fdId';
		p.appendChild(document.createTextNode("ID"));
		return;
	}
	for (var i = 0; i < fields.length; i ++) {
		if (fields[i].children != null) {
			continue;
		}
		var opt = new Option(fields[i].label, fields[i].name);
		select.options.add(opt);
		if (fdName.value == opt.value) {
			opt.selected = true;
		}
	}
	select.onchange = function() {
		var tr = GetParent(this, 'tr');
		var fdName = GetElement(tr, 'input', function(input) {
			return (input.name != null && input.name.indexOf('.fdName') > -1);
		});
		var fdColumn = GetElement(tr, 'input', function(input) {
			return (input.name != null && input.name.substring(input.name.lastIndexOf('.fdColumn'))== '.fdColumn');
		});
		
		if (this.value != '') {
			new Reminder(this).hide();
			var selects = GetParent(this, 'table').getElementsByTagName('select');
			for (var i = 0; i < selects.length; i ++) { // 唯一性校验
				if (selects[i] !== this && this.value == selects[i].value) {
					alert(this.options[this.selectedIndex].text + "<kmss:message key="sys-xform:sysFormDbTable.alert.hasSelectedField"/>");
					this.value  = '';
					break;
				}
			}
		}
		fdName.value = this.value;
		var originalFdColumnVal = fdColumn.value;
		if (this.value == 'fdParent') {
			var tbRow = GetParent(GetParent(tr, 'table'), 'tr');
			var tbName = GetElement(tbRow.previousSibling, 'input', function(input) {
				return (input.name != null && input.name.indexOf('.fdName') > -1);
			});
			new Reminder(tbName).hide();
			return;
		}
		//=====
		if(this.value == "") {
			return;
		}
		var index = /fdColumns\[(\d+)\]/.exec($(fdName).attr("name"))[1];
		$(tr).empty();
		var xColumn;
		var subTable = $(tr).closest("[id='subTable']");
		if (subTable.length > 0) {
			var name = $(fdName).attr("name");
			var tablePrefix = name.substring(0,name.indexOf("."));
			var fdTable = GetElement(subTable[0], 'input', function(input) {
				return (input.name != null && input.name.indexOf(tablePrefix+'.fdName') > -1);
			});
			for (i = 0; i < dictTree.length; i ++) {
				if (fdTable.value == dictTree[i].name) {
					var children = dictTree[i].children;
					for (j = 0; j < children.length; j++) {
						if (this.value === children[j].name) {
							xColumn = children[j].xColumn;
							xColumn = JSON.parse(xColumn);
							xColumn.index = parseInt(index);
							xColumn.maxLength = xColumn.len;
							xColumn.prefix = tablePrefix + '.';
						}
					}
				}
			}
		} else {
			for (i = 0; i < dictTree.length; i ++) {
				if (this.value == dictTree[i].name) {
					xColumn = dictTree[i].xColumn;
					xColumn = JSON.parse(xColumn);
					xColumn.index = parseInt(index);
					xColumn.maxLength = xColumn.len;
				}
			}
		}
		
		for (j = 0; j < ColumnTableTemplate.length; j ++) {
			cell = tr.insertCell(-1);
			html = SysFormDb_RunTemplate(ColumnTableTemplate[j], xColumn);
			cell.innerHTML = html;
		}
		var fdName = GetElement(tr, 'input', function(input) {
			return (input.name != null && input.name.indexOf('.fdName') > -1);
		});
		var fdColumn = GetElement(tr, 'input', function(input) {
			return (input.name != null && input.name.substring(input.name.lastIndexOf('.fdColumn'))== '.fdColumn');
		});
		fdName.value = xColumn.name;
		fdColumn.value  = originalFdColumnVal;
		FillXFormOptions(tr, fields);
		//=====
	};
}
function DeleteMainTableColumns(table) {
	for (var i = table.rows.length-1; i > 1; i --) {
		table.deleteRow(i);
	}
}
/** 创建表格 */
function BuildMainTableDataToColumnTable(values) {
	var table = document.getElementById('columnTable'), i, html, j, row, cell;
	if (!ValHasPk(values)) {
		document.getElementsByName('fdTable')[0].value = '';
		DeleteMainTableColumns(table);
		table.style.display = 'none';
		table.parentNode.parentNode.style.display = 'none';
		return;
	}
	table.style.display = '';
	table.parentNode.parentNode.style.display = '';
	var oldValues = GetOldValues(table);
	
	DeleteMainTableColumns(table);
	for (i = 0; i < values.length; i ++) {
		var tr = table.insertRow(-1);
		values[i].index = i;
		for (j = 0; j < ColumnTableTemplate.length; j ++) {
			cell = tr.insertCell(-1);
			html = SysFormDb_RunTemplate(ColumnTableTemplate[j], values[i]);
			cell.innerHTML = html;
		}
		FillXFormOptions(tr ,dictTree);
		SetEnableRow(tr, values[i].isEnable);
	}
	FillDefaultValues(table,values);
	FillOldValues(table, oldValues);
}

// 隐藏加载进度条
function xform_clearIndicatorDiv(obj,win){
	win = win|| window;
	win.document.body.removeChild(obj.newMaskDiv);
	win.document.body.removeChild(obj.infoDiv);
}

// 显示加载进度条
function xform_openIndicatorDiv(win,_id) {
	  win = win|| this;
	  var m = "mask_div";  
	 
	  // 新激活图层  
	  var newDiv = win.document.createElement("div");  
	  newDiv.id = _id;  
	  newDiv.style.position = "absolute";  
	  newDiv.style.zIndex = "9999";  
	  newDiv.style.top = (parseInt($(win).height() - 100)) / 2 + "px"; // 屏幕居中  
	  newDiv.style.left = (parseInt($(win).width() - 300)) / 2 + "px"; // 屏幕居中  
	  newDiv.style.background = "#FFFFFF";
	  newDiv.style.filter = "alpha(opacity=60)";
	  newDiv.style.opacity = "0.60";
	  newDiv.style.border = "0px solid #860001";  
	  newDiv.style.padding = "5px";  
	  newDiv.innerHTML = "<span style='font-size:12px;font-weight:bolder'><img  width=160px height=20px  src='"+Com_Parameter.ContextPath+"sys/lbpm/flowchart/images/indicator_long.gif'></img><br/>系统正在加载...</span>";
	  win.document.body.appendChild(newDiv);  
	  // mask图层  
	  var newMask = win.document.createElement("div");  
	  newMask.id = m;  
	  newMask.style.position = "absolute";  
	  newMask.style.zIndex = "1";  
	  newMask.style.width = $(win).width() + "px";  
	  newMask.style.height = $(win).height() + "px";  
	  newMask.style.top = "0px";  
	  newMask.style.left = "0px";  
	  newMask.style.background = "#FFFFFF";
	  newMask.style.filter = "alpha(opacity=60)";
	  newMask.style.opacity = "0.60";
	  win.document.body.appendChild(newMask);
  return  {newMaskDiv:newMask,infoDiv:newDiv};
}

function ReLoadColumns(table, callback) {
	var kmssData = new KMSSData();
	kmssData.SendToBean('sysFormDbColumnListService&table=' + table, callback);
}

function ReloadMainTable(td) {
	var fdTable = document.getElementsByName('fdTable')[0];
	if (fdTable.value == '') {
		return;
	}
	var xform_div=xform_openIndicatorDiv();
	ReLoadColumns(fdTable.value, function(data) {
		if (data == null) return;
		var values = data.GetHashMapArray();
		BuildMainTableDataToColumnTable(values);
		xform_clearIndicatorDiv(xform_div);
	});
}
function BuildMainColumns(data) {
	if (data == null) return;
	var values = data.GetHashMapArray();
	BuildMainTableDataToColumnTable(values);
}
/** 加载表字段 */
function LoadColumns(data, callback) {
	if (data == null) return;
	var fdTable = document.getElementsByName('fdTable')[0];
	new Reminder(fdTable).hide();
	var values = data.GetHashMapArray();
	var kmssData = new KMSSData();
	var xform_div=xform_openIndicatorDiv();
	kmssData.SendToBean('sysFormDbColumnListService&table=' + values[0].id, function(data){
		callback(data);	
		xform_clearIndicatorDiv(xform_div);
	});
}
/** 选择主表 */
function SelectMainTable() {//sysFormDbListService实际返回空
	Dialog_List(false, 'fdTable', null, null, 'sysFormDbListService',
			function(data) {LoadColumns(data, BuildMainColumns);},
			'sysFormDbSearchService&keyword=!{keyword}',
			false, true,
			'<kmss:message key="sys-xform:sysFormDbTable.selectTable" />', null, 520);
}
// 原配置数据
var initValues = new Object();
<c:if test="${not empty sysFormDbTableForm.fdColumns}">
initValues.columns = [];
<c:forEach items="${sysFormDbTableForm.fdColumns}" var="column">
initValues.columns.push({
	id:"${column.fdId}",
	name:"<c:out value="${column.fdName}"/>",
	column:"<c:out value="${column.fdColumn}"/>",
	dataType:"<c:out value="${column.fdDataType}"/>",
	type:"<c:out value="${column.fdType}"/>",
	relation:"<c:out value="${column.fdRelation}"/>",
	tableName:"<c:out value="${column.fdTableName}"/>",
	modelName:"<c:out value="${column.fdModelName}"/>",
	modelText:"<c:out value="${column.fdModelText}"/>",
	mainColumn:"<c:out value="${column.fdMainColumn}"/>",
	elementColumn:"<c:out value="${column.fdElementColumn}"/>",
	maxLength:"<c:out value="${column.fdLength}"/>",
	isPk:'<c:out value="${column.fdIsPk}"/>',
	isForm:'<c:out value="${column.fdIsForm}"/>',
	isEnable:'<c:out value="${column.fdIsEnable}"/>',
	order : '${column.fdOrder}'
});
</c:forEach>
</c:if>
<c:if test="${not empty sysFormDbTableForm.fdTables}">
initValues.tables = {};
<c:forEach items="${sysFormDbTableForm.fdTables}" var="table">
initValues.tables['${table.fdName}'] = {};
initValues.tables['${table.fdName}'].id = "${table.fdId}";
initValues.tables['${table.fdName}'].isPublish = "${table.fdIsPublish}";
initValues.tables['${table.fdName}'].table = "${table.fdTable}";
initValues.tables['${table.fdName}'].columns = [];
<c:forEach items="${table.fdColumns}" var="column">
initValues.tables['${table.fdName}'].columns.push({
	id:"${column.fdId}",
	name:"<c:out value="${column.fdName}"/>",
	column:"<c:out value="${column.fdColumn}"/>",
	dataType:"<c:out value="${column.fdDataType}"/>",
	type:"<c:out value="${column.fdType}"/>",
	relation:"<c:out value="${column.fdRelation}"/>",
	tableName:"<c:out value="${column.fdTableName}"/>",
	modelName:"<c:out value="${column.fdModelName}"/>",
	modelText:"<c:out value="${column.fdModelText}"/>",
	mainColumn:"<c:out value="${column.fdMainColumn}"/>",
	elementColumn:"<c:out value="${column.fdElementColumn}"/>",
	maxLength:"<c:out value="${column.fdLength}"/>",
	isPk:'<c:out value="${column.fdIsPk}"/>',
	isForm:'<c:out value="${column.fdIsForm}"/>',
	isEnable:'<c:out value="${column.fdIsEnable}"/>',
	order : '${column.fdOrder}'
});
</c:forEach>
</c:forEach>
</c:if>
// ======== 表单属性
var dictTree = [];
var xformFields = [];
function GetFormExtDictObj(tempType, tempId) {
	return new KMSSData().AddBeanData("sysFormDictVarTree&tempType="+tempType+"&tempId="+tempId + "&isIgnoreStore=true").GetHashMapArray();
}
function ShowXFormFields() {
	DialogParams = {formFiels:xformFields,title:'<kmss:message key="sys-xform:sysFormDbFormFields.title" />'};
	var left = (screen.width-500)/2;
	var top = (screen.height-400)/2;
	window.open('<c:url value="/sys/xform/base/sys_form_db_table/sysFormDbFormFields_view.jsp"/>', '_blank', 'resizable=1,scrollbars=1,height=400,width=500,left='+left+',top='+top);
}
// 表单属性初始化
function InitXFormFields() {
	var formId = document.getElementsByName('fdFormId')[0];
	var formType = document.getElementsByName('fdFormType')[0];
	if (formId.value == '' || formType.value == '') return;
	
	var array = GetFormExtDictObj(formType.value, formId.value);
	xformFields = array;
	for (var i = 0; i < xformFields.length; i ++) {
		var field = xformFields[i];
		var name = field.name;
		if (name.indexOf('.') > 0) {
			var label = field.label;
			var type = field.type;
			var preName = name.substring(0, name.indexOf('.'));
			var subName = name.substring(name.indexOf('.') + 1, name.length);
			var subLabel = label.substring(label.indexOf('.') + 1, label.length);
			var subType = type.substring(0, type.length - 2);
			for (var j = 0; j < xformFields.length; j ++) {
				if (preName == xformFields[j].name) {
					if (xformFields[j].children == null) {
						xformFields[j].children = [];
						xformFields[j].children.push({name:"fdParent", label:"<kmss:message key="sys-xform:sysFormDbColumn.fdParent.parent"/>"});
					}
					xformFields[j].children.push({name:subName, label:subLabel, type: subType,xColumn: field.xColumn});
				}
			}
		} else {
			dictTree.push(field);
		}
	}
}
Com_AddEventListener(window, 'load', function() {
	var xform_div=xform_openIndicatorDiv();
	InitXFormFields(); // 首先初始化表单属性
	InitMainTableColumnTable(); // 初始化模板
	var kmssData = new KMSSData();
	BuildSubTableItems(dictTree); // 初始化可选择直接配置子表
	if (initValues.tables != null) {
		var inputs = document.getElementsByTagName('input');
		for (var i = 0; i < inputs.length; i ++) {
			var input = inputs[i];
			if (input.type == 'hidden' && input.name != null){
				var name = input.name;
				if (name.indexOf('fdTables') > -1 && name.indexOf('.fdName') > -1) {
					if (initValues.tables[input.value] != null) {
						var tableValue = initValues.tables[input.value];
						var tr = GetParent(input, 'tr');
						var fdIsPublish = GetElement(tr, 'input', function(put) {return (put.name.indexOf('.fdIsPublish') > -1)});
						fdIsPublish.value = tableValue.isPublish;
						var checkbox = GetElement(tr, 'input', function(put) {return (put.className.indexOf('tableEnableBox') > -1)});
						checkbox.checked = tableValue.isPublish == 'true';
						var tableName = GetElement(tr, 'input', function(put) {return (put.name.indexOf('.fdTable') > -1)});
						tableName.value = tableValue.table;
						var fdId = GetElement(tr, 'input', function(put) {return (put.name.indexOf('.fdId') > -1)});
						fdId.value = tableValue.id;
						SetEnableSubTable(tr);
						if (tableValue.columns.length > 0)
							BuildSubTableColumns(tableValue.columns, input);
					}
				}
			}
		}
	}
	if (initValues.columns != null)
		BuildMainTableDataToColumnTable(initValues.columns);
	xform_clearIndicatorDiv(xform_div);
	Com_Parameter.event["submit"].push(ValidateForm);
});

function ValidateForm() {
	var isOk = true;
	var fdName = document.getElementsByName('fdName')[0];
	if (Com_Trim(fdName.value) == '') {
		new Reminder(fdName, '<kmss:message key="sys-xform:sysFormDbTable.alert.nameNotNull"/>').show();
		Com_AddEventListener(fdName, 'blur', ValFdName);
		isOk = false;
	} else {
		Com_RemoveEventListener(fdName, 'blur', ValFdName);
	}
	// 校验所有有效行，是否已经选择
	var selects = document.getElementsByTagName('select');
	var i = 0;
	for (i = 0; i < selects.length; i ++ ){
		var select = selects[i];
		if (select.getAttribute('fdNameSelect') == 'true') {
			if ($(select).closest("tr").find(".enableBox").is(":checked") && !select.disabled && select.value == '') {
				new Reminder(select, '<kmss:message key="sys-xform:sysFormDbTable.alert.selectField"/>').show();
				isOk = false;
				break;
			}
		}
	}
	// 子表有效情况下，是否选择表
	var subTable = document.getElementById('subTable');
	var inputs = subTable.getElementsByTagName('input');
	var hasSubTable = false;
	for (i = 0; i < inputs.length; i ++ ){
		var input = inputs[i];
		if (input.type == 'checkbox' && input.className == 'tableEnableBox') {
			if (input.checked) {
				hasSubTable = true;
				var row = GetParent(input, 'tr');
				var tableName = GetElement(row, 'input', function(input) {
					return (input.name != null && input.name.indexOf('.fdTable') > -1);
				});
				if (tableName.value == '') {
					new Reminder(tableName, '<kmss:message key="sys-xform:sysFormDbTable.alert.tableNotNull"/>').show();
					isOk = false;
					continue;
				}
				var tRow = row.nextSibling;
				// 校验是否有选择fdParent
				var subSelects = tRow.getElementsByTagName('select');
				var hasFdParent = false;
				for (var n = 0; n < subSelects.length; n ++) {
					var ss = subSelects[n];
					if (ss.value == 'fdParent') {
						hasFdParent = true;
						break;
					}
				}
				if (!hasFdParent) {
					var fdName = GetElement(row, 'input', function(input) {
						return (input.name != null && input.name.indexOf('.fdName') > -1);
					});
					new Reminder(fdName, '<kmss:message key="sys-xform:sysFormDbTable.alert.pleaseSelectParent"/>').show();
					isOk = false;
				}
			}
		}
	}
	// 主表映射配置，如果无子表情况下，是必填
	//if (!hasSubTable) {
		var fdTable = document.getElementsByName('fdTable')[0];
		if (Com_Trim(fdTable.value) == '') {
			new Reminder(fdTable, '<kmss:message key="sys-xform:sysFormDbTable.alert.tableNotNull"/>').show();
			isOk = false;
		}
	//}
	return isOk;
}
function ValFdName() {
	var fdName = document.getElementsByName('fdName')[0];
	if (Com_Trim(fdName.value) != '') {
		new Reminder(fdName).hide();
	}
}
function ValHasPk(columns) {
	var has = false;
	for (var i = 0; i < columns.length; i ++) {
		if (columns[i].isPk == 'true') {
			has = true;
			break;
		}
	}
	if (!has) {
		alert('<kmss:message key="sys-xform:sysFormDbTable.alert.idNotNull"/>');
	}
	return has;
}

//全局变量，字段名称的存储
var tbColumnFiledNames = [];

/* 把name转换为拼音
* 
* validateStr:被校验的id
* validateName:被校验的id,当表单新增控件的时候，validateStr是一个随机值
* isReadOnly:是否需要只读，只读的不用处理
*/
function transToPinYin(validateStr,validateName,isReadOnly){
	var result = validateStr;
	if(!isReadOnly){
		if(validateStr && validateStr != null && validateName){
			//找到当前行的文本名
			var str = findBeTranslatedLabel(validateStr,dictTree);
			//表单控件新增的时候，validateStr是后台随机生成的，故肯定找不到对应的文本名，此时str为空
			if(typeof(str) == 'undefined'){
				//通过控件fdid找
				str = findBeTranslatedLabel(validateName,dictTree);
				validateStr = validateName;
				result = validateStr;
			}
			// 清除空格
			str = str.replace(/\s/ig, '');
			//校验是否需要替换
			if(isTransToPinYin(validateStr)){			
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
				result = val;
			}
		}
		//校验是否存在同名字段，存在则在后面加上‘_vx‘
		result = aferValidateIsSame(result,tbColumnFiledNames);		
		if(typeof(result) == 'object'){
			//result = result.value + '_v' + result.version;
			result = result.value;
		}else{
			tbColumnFiledNames.push(result);
		}
	}	
	return result;
}

//处理相同字段名的情况
function aferValidateIsSame(val,columnNamesArray){
	var result = val;
	for(var i = 0; i < columnNamesArray.length; i++){
		//如果存在同名的，则修改当前字段名
		if(typeof(columnNamesArray[i]) == 'string'){
			//第一次匹配
			if(columnNamesArray[i] == val){
				result = {'value':val,'version':1};
				//把同名的字段改为对象
				columnNamesArray[i] = result;
				break;
			}			
		}else if(typeof(columnNamesArray[i]) == 'object'){
			if(columnNamesArray[i].value == val){
				//版本号+1
				result = {'value':val,'version':( ++columnNamesArray[i].version )};
				break;
			}
		}
	}
	return result;
}

//校验当前字段名是否需要转换为拼音
function isTransToPinYin(str){
	var isTrans = false;
	//如果字符串不是以fd_开头或者能够后面的字符不能被解析成16位进制，就表示已被修改过，以修改的为准
	if(str.indexOf('fd_') == 0){
		var partStr = str.substr(3);
		var result = partStr.match(/^[0-9a-fA-F]{1,}$/);
		if(result){
			isTrans = true;
		}else{
			isTrans = false;
		}
	}else{
		isTrans = false;
	}
	return isTrans;
}

//匹配当前行的name
function findBeTranslatedLabel(name,fields){	
	for (var i = 0; i < fields.length; i ++) {
		//明细表的递归
		if (fields[i].children != null) {
			var detailsVal = findBeTranslatedLabel(name,fields[i].children);
			//只有有值才返回
			if(detailsVal && detailsVal != null){
				return detailsVal;
			}
		}else{
			//匹配当前行
			if(fields[i].name == name){				
				return fields[i].label;
			}
		}	
	}
}
//中文转拼音 end
var _$validation = $KMSSValidation(document.forms['sysFormDbTableForm']);
if(_$validation){
	_$validation.addValidator('checkOnly',
		"<bean:message bundle='sys-xform' key='sysFormDbTable.subTable.only'/>",
		function(v,e,o){
			var bool = true;
			$("#subTable [name$='.fdTable']").each(function(){
				if(v && this!=e && $(this).val()==v && $(this).closest("tr").find(".tableEnableBox").is(':checked') && $(e).closest("tr").find(".tableEnableBox").is(':checked')){
					bool = false;
					return false;
				}
			})
			return bool;
		}
	); 
}
</script>
<%@ include file="/resource/jsp/edit_down.jsp"%>