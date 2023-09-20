/**********************************************************
功能：明细表
使用：
	
作者：傅游翔
创建时间：2010-04-12
**********************************************************/
// ======== 明细表 (开始)===========
Designer_Config.controls.detailsTable = {
	type : "detailsTable",
	inherit    : 'table',
	relatedWay : 'up',
    isDetailsTable : true,
	onDraw : _Designer_Control_DetailsTable_OnDraw,
	drawMobile : _Designer_Control_DetailsTable_DrawMobile,
	setColumnsWidth : _Designer_Control_DetailsTable_SetColumnsWidth,
	setOptRowColspan:_Designer_Control_DetailsTable_SetOptRowColSpan,
	onDrawEnd : _Designer_Control_DetailsTable_OnDrawEnd,
	onDrag : _Designer_Control_StandardTable_OnDrag, // 拖拽
	onDragMoving : _Designer_Control_DetailsTable_onDragMoving, // 拖拽移动
	onDragStop    : _Designer_Control_StandardTable_OnDragStop,     //拖拽结束
	onDragMovingChooseCell : _Designer_Control_StandardTable_OnDragMovingChooseCell, //拖拽中选中单元格
	columnCellDraw: _Designer_Control_DetailsTable_Column_Cell_Draw,
	drawXML : _Designer_Control_DetailsTable_DrawXML,
	attrs : {
		label : Designer_Config.attrs.label,
		showIndex : {
			text : Designer_Lang.controlDetailsTable_attr_showIndex,
			value: "true",
			type: 'checkbox',
			checked: true,
			show: true
		},
		multiHead : {
			text : Designer_Lang.controlDetailsTable_attr_multipleHeader,
			value: "",
			type: 'self',
			draw: _Designer_Control_DetailsTable_MultiHead_Self_Draw,
			show: true
		},
		required: {
			text: Designer_Lang.controlAttrRequired,
			value: "",
			type: 'self',
			draw: _Designer_Control_DetailsTable_required_Self_Draw,
			checked: false,
			show: true
		},
		showRow : {
			text : Designer_Lang.controlDetailsTable_attr_showRow,
			value: "1",
			type: 'text',
			show: true,
			validator: Designer_Control_Attr_showRow_Validator
		},
		showStatisticRow : {
			text : Designer_Lang.controlDetailsTable_attr_showStatisticRow,
			value: "true",
			type: 'checkbox',
			checked: true,
			show: true
		},
		showCopyOpt :{
			text : Designer_Lang.controlDetailsTable_attr_replicationRow,
			value : 'true',
			type: 'checkbox',
			checked: true,
			show: true
		},
		defaultFreezeTitle :{
			text : Designer_Lang.controlDetailsTable_attr_defaultFreezeTitle,
			value : '',
			type: 'self',
			show: true,
            onlyShowInDetailTable: true,
			draw: _Designer_Control_DetailsTable_freezeRow_Self_Draw
		},
		defaultFreezeCol :{
			text : Designer_Lang.controlDetailsTable_attr_defaultFreezeCol,
			value : '',
			type : 'select',
            onlyShowInDetailTable: true,
			opts:[{"text":Designer_Lang.relation_select_pleaseChoose,'value':'-1'},
				{"text": Designer_Lang.controlDetailsTable_attr_defaultFreezeCol_prefix + "3" + Designer_Lang.controlDetailsTable_attr_defaultFreezeCol_suffix,'value':'2'},
				{"text": Designer_Lang.controlDetailsTable_attr_defaultFreezeCol_prefix + "4" + Designer_Lang.controlDetailsTable_attr_defaultFreezeCol_suffix,'value':'3'},
				{"text": Designer_Lang.controlDetailsTable_attr_defaultFreezeCol_prefix + "5" + Designer_Lang.controlDetailsTable_attr_defaultFreezeCol_suffix,'value':'4'},
				{"text": Designer_Lang.controlDetailsTable_attr_defaultFreezeCol_prefix + "6" + Designer_Lang.controlDetailsTable_attr_defaultFreezeCol_suffix,'value':'5'}],
			show : true,
			translator:opts_common_translator,
			getVal:defaultFreezeCol_getVal
		},
		excelOper :{
			text : Designer_Lang.controlDetailsTable_attr_excel,
			value : '',
			type: 'self',
			draw: _Designer_Control_DetailsTable_ExcelOper_Self_Draw,
			show: true
		},
		importType:{
			text: Designer_Lang.controlDetailsTable_attr_importType,
			value: 'unRelevanceImport',
			type: 'radio',
			onlyShowInSeniorDetail: true,
			opts:[{text:Designer_Lang.controlDetailsTable_attr_unRelevanceImport,value:"unRelevanceImport"},
				{text:Designer_Lang.controlDetailsTable_attr_relevanceImport,value:"relevanceImport"}],
			show:false

		},
		alignment: {
			text: Designer_Lang.controlDetailsTable_attr_oprBtn,
			value: 'default',
			type: 'radio',
			opts: [{text:Designer_Lang.controlAttrAlignmentDefault,value:"default"},
			       {text:Designer_Lang.controlAttrAlignmentLeft,value:"left"},
			       {text:Designer_Lang.controlAttrAlignmentRight,value:"right"}],
			show: true
		},
		excelExport:{ //是否显示Excel导出功能
			text: Designer_Lang.controlDetailsTable_attr_excelExport,
			value: '',
			type: 'hidden',
			show: true
		},
		excelImport:{ //是否显示Excel导入功能
			text: Designer_Lang.controlDetailsTable_attr_excelImport,
			value: '',
			type: 'hidden',
			show: true
		},
		width : {
			text: Designer_Lang.controlAttrWidth,
			value: "",
			type: 'text',
			show: true,
			validator: Designer_Control_Attr_Width_Validator,
			checkout: Designer_Control_Attr_Width_Checkout
		},
		cell : {
			value : '6'
		},
		layout2col : {
			text : Designer_Lang.layout2colName,
			opts: [
			       {text:Designer_Lang.layout2colMobile, value:'mobile',onclick: '_Show_DataEntryMode(this.value);'},
			       {text:Designer_Lang.layout2colDesktop, value:'desktop',onclick: '_Show_DataEntryMode(this.value);'}
			       ],
			value: "mobile",
			type: 'radio',
			show: true,
			required: false,
			
		},
		dataEntryMode : {
			text: Designer_Lang.controlDetailsTable_attr_dataEntryMode,
			value: 'multipleRow',
			type: 'self',
			draw: _Designer_Control_DetailsTable_DataEntryMode_Self_Draw,
			opts: [{text:Designer_Lang.controlAttrDataEntryModeSingleRow,value:"singleRow"},
			       {text:Designer_Lang.controlAttrDataEntryModeMultipleRow,value:"multipleRow"},
			       ],
			show: true,
			translator:opts_common_translator
		}
	},
	domAttrs : {
		td : {
			align: {
				text: Designer_Lang.controlStandardTableDomAttrTdAlign,
				value: "center",
				type: 'radio',
				opts: [
					{text:Designer_Lang.controlStandardTableDomAttrTdAlignLeft,value:"left"}, // left
					{text:Designer_Lang.controlStandardTableDomAttrTdAlignCenter,value:"center"},
					{text:Designer_Lang.controlStandardTableDomAttrTdAlignRight,value:"right"}
				],
                styleName: "textAlign"
			},
			vAlign: {
				text: Designer_Lang.controlStandardTableDomAttrTdVAlign,
				value: "middle",
				type: 'radio',
				opts: [
					{text:Designer_Lang.controlStandardTableDomAttrTdVAlignTop,value:"top"},
					{text:Designer_Lang.controlStandardTableDomAttrTdVAlignMiddle,value:"middle"}, // middle
					{text:Designer_Lang.controlStandardTableDomAttrTdVAlignBottom,value:"bottom"}
				],
                styleName: "verticalAlign"
			},
			style_width: {
				text: Designer_Lang.controlAttrWidth,
				value: "auto",
				type: 'text'
			},
			//当form里面只有一个input type=‘text’时，ie下默认按enter即提交内容，解决方案：1.设置keypress方法；2、增加多一个input type=‘text’，设为隐藏，此处多做一个隐藏text
			hidden_text: {
				text: '',
				value: "",
				type: 'text',
				show: false
			}
		}
	},
	info : {
		name: Designer_Lang.controlDetailsTable_info_name,
		td: Designer_Lang.controlDetailsTable_info_td
	},
	resizeMode : 'no',
	insertRow: _Designer_Control_DetailsTable_noSuppotAlert,
	deleteRow: _Designer_Control_DetailsTable_noSuppotAlert,
	appendRow: _Designer_Control_DetailsTable_noSuppotAlert,
	merge: function(){
		var self = this;
		self.mergeCell(function(){
			if(_Designer_Control_Table_Merge.call(self)){
				// 如果是行合并，需要更新data-ismultiheadcol属性
				var selectedDom = self.selectedDomElement[0];
				var rows = $(selectedDom).attr("row");
				if(rows && rows.split(",").length > 1){
					_Designer_Control_DetailsTable_SetElementMultiHeadColValue(selectedDom,"true");
				}
			};
		});
	},
	split: function(){
		var self = this;
		var mergeCell = this.selectedDomElement[0];
		_Designer_Control_Table_Split.call(self,function(cells){
			// 如果是行合并，需要更新data-ismultiheadcol属性
			cells.push(mergeCell);
			for(var i = 0;i < cells.length;i++){
				var cell = cells[i];
				if(_Designer_Control_DetailsTable_IsMultiHeadTrElement($(cell).closest("tr"))){
					_Designer_Control_DetailsTable_SetElementMultiHeadColValue(cell,"true");
				}else{
					_Designer_Control_DetailsTable_SetElementMultiHeadColValue(cell,"false");
				}
			}
		});
	},
	mergeCell : _Designer_Control_DetailsTable_MergeCell,
	setColWidth: null,
	onColumnStart: null,
	onColumnDoing: null,
	onColumnEnd: null,
	onRowStart: _Designer_Control_DetailsTable_noSuppotAlert,
	onRowDoing: _Designer_Control_DetailsTable_noSuppotAlert,
	onRowEnd: _Designer_Control_DetailsTable_noSuppotAlert,
	onMouseOver: _Designer_Control_DetailsTable_DoMouseOver,
	onSelect: _Designer_Control_DetailsTable_OnSelect,
	insertColumn : _Designer_Control_DetailsTable_InsertColumn,
	_insertColumn : _Designer_Control_Table_InsertColumn,
	_appendColumn  : _Designer_Control_Table_AppendColumn,
	appendColumn  : _Designer_Control_DetailsTable_AppendColumn,
	deleteColumn : _Designer_Control_DetailsTable_DeleteColumn,
	_deleteColumn : _Designer_Control_Table_DeleteColumn,
	insertValidate: _Designer_Control_DetailsTable_InsertValidate,
	onInitialize : _Designer_Control_DetailsTable_OnInitialize,
	getRelateWay  : _Designer_Control_DetailsTable_GetRelateWay,
	destroyMessage:Designer_Lang.buttonsDeleteDetailsTable,
	initMultiHead : _Designer_Control_DetailsTable_OnInitMultiHead,
	skipChangeLogAttrs:["columnIndex"],
};

function defaultFreezeCol_getVal(name,attr,value,controlValue) {
	var opts = attr["opts"];
	if(undefined==opts){
    		return "";
    	}
	if(value==undefined){
		controlValue[name] = "-1";
		return "-1";
	}
	return value;
}
function _Designer_Control_DetailsTable_noSuppotAlert() {
	alert(Designer_Lang.controlDetailsTable_noSuppotAlert);
	return false;
}

Designer_Config.operations['detailsTable'] = {
		lab : "2",
		imgIndex : 15,
		title : Designer_Lang.controlDetailsTable_title,
		run : function (designer) {
			designer.toolBar.selectButton('detailsTable');
		},
		type : 'cmd',
		order: 10,
	    line_splits_end:true,
		shortcut : 'R',
		select: true,
		cursorImg: 'style/cursor/detailsTable.cur',
		isAdvanced: false
	};
Designer_Config.buttons.form.push("detailsTable");
Designer_Menus.form.menu['detailsTable'] = Designer_Config.operations['detailsTable'];

function _Show_DataEntryMode(value){
	var control = Designer.instance.attrPanel.panel.control;
	if (Designer.IsDetailsTableControl(control)) {
		var dataEntryModeObj = $("[name='dataEntryMode']",Designer.instance.attrPanel.panel.domElement).closest("tr");
		if (value === "desktop") {
			dataEntryModeObj.show();
		}
		if (value === "mobile") {
			dataEntryModeObj.hide();
		}
	}
}

// 初始化绘制
function _Designer_Control_DetailsTable_OnDraw(parentNode, childNode) {
	if(this.options.values.cell == null){
		this.options.values.cell=this.attrs.cell.value?this.attrs.cell.value : 5;
	}
	var cells = {row:4, cell:this.options.values.cell}, domElement, row, cell;
	this.options.domElement = document.createElement('table');
	domElement = this.options.domElement;
	this.options.values._label_bind = 'false'; // 不需要绑定
	if(!this.options.values.label){
	    if (this.type === "seniorDetailsTable") {
            this.options.values.label = this.info.name + _Designer_Index_Object.advancedDetailsTableLabel ++;
        } else {
            this.options.values.label = this.info.name + _Designer_Index_Object.label ++;
        }
	}
	domElement.setAttribute('label', this.options.values.label);

	if(this.options.values.id == null) {
        this.options.values.id = "fd_" + Designer.generateID();
        //高级明细表记录最原始id
        if (this.type === "seniorDetailsTable") {
            this.options.values.originId = Designer.generateID();
            domElement.setAttribute('originId', this.options.values.originId);
        }
    }

	domElement.setAttribute('id', this.options.values.id);
	domElement.setAttribute('formDesign', 'landray');
	domElement.setAttribute('align', 'center');
	domElement.className = 'tb_normal';
	domElement.style.width = '100%';
	if (this.parent)
		domElement.setAttribute('tableName', _Get_Designer_Control_TableName(this.parent));
	parentNode.appendChild(this.options.domElement);
	for (var i = 0; i < cells.row; i ++) {
		row = domElement.insertRow(-1);
		if ( i == 0) {row.className = 'tr_normal_title'; $(row).attr("type","titleRow");$(row).height(33);}
		else if (i == 1) {$(row).attr("type","templateRow");}
		else if (i == 2) {$(row).attr("type","statisticRow");}
		else if (i == 3) {$(row).attr("type","optRow");$(row).addClass('tr_normal_opt'); }
		for (var j = 0; j < cells.cell; j ++) {
			cell = row.insertCell(-1);
			cell.setAttribute('row', '' + i);            //记录行数(多值，以逗号分割，有严格顺序)
			cell.setAttribute('column', '' + j);         //记录列数(多值，以逗号分割，有严格顺序)
			cell.setAttribute('align', 'center');
			if(i == 0){
				cell.className = 'td_normal_title';
			}
			if(j == 0){
				cell.style.width = '15px';
				cell.setAttribute("colType", "selectCol");
				if(i == 1){
					cell.innerHTML = '<input type="checkbox" name="DocList_Selected" />';
				}
				if(i == 3){
					cell.style.width = '';
					cell.setAttribute("colType", "optCol");
					cell.setAttribute("colSpan",cells.cell);
					cell.innerHTML = '<label attach="' + this.options.values.id + '" style="display:none;">&nbsp;</label>';
					var bgmUrlStyle = "style='display:inline-block;width:20px;height:20px;background:url(style/img/normal_opt_sprite.png) no-repeat 0 0;";
					var add = 'style/img/icon_add.png';
					var batchDelete = 'style/img/icon_add.png';
					var up = 'style/img/icon_up.png';
					var down = 'style/img/icon_down.png';
					cell.innerHTML +=
						"<div name='tr_normal_opt_content' style='WIDTH: 100%; POSITION: relative; color:#fff;white-space: nowrap;'>" 
							+"<div name='tr_normal_opt_l' style='POSITION: absolute; LEFT: 0px; TOP: 0px'>"
							+"<div style='display:inline-block; line-height: 20px; vertical-align: top;'><input style='position: relative; top: 6px;' type=\"checkbox\" onclick='return false;'/><span style=\"display:inline-block;position: relative;top:2px;margin-left:6px;\">"+Designer_Lang.controlDetailsTable_selectAllRow+"</span></div>"
							+ '<span style="margin-left:15px; display:inline-block; line-height: 20px;"><span ' + bgmUrlStyle + 'background-position: 0 0;position: relative;top:2px;\'"></span><span style="display:inline-block;vertical-align: text-bottom;margin-left:6px;">'+Designer_Lang.controlDetailsTable_deleteRow+'</span></span>'
							+"</div>"
							+"<div name='tr_normal_opt_c'>"
							+ '<span style="display:inline-block; line-height: 20px;"><span ' + bgmUrlStyle + 'background-position: -20px 0;position: relative;top:2px;\' title="'+Designer_Lang.controlDetailsTable_addRow+'"></span><span style="display:inline-block;vertical-align: text-bottom;margin-left:6px;">'+Designer_Lang.controlDetailsTable_addRow+'</span></span>'
							+ '<span style="margin-left:15px; display:inline-block; line-height: 20px;"><span ' + bgmUrlStyle + 'background-position: -40px 0;position: relative;top:2px;\' title="'+Designer_Lang.controlDetailsTable_moveUp+'"></span><span style="display:inline-block;vertical-align: text-bottom;margin-left:6px;">'+Designer_Lang.controlDetailsTable_moveUp+'</span></span>'
							+ '<span style="margin-left:15px; display:inline-block; line-height: 20px;"><span ' + bgmUrlStyle + 'background-position: -60px 0;position: relative;top:2px;\' title="'+Designer_Lang.controlDetailsTable_moveDown+'"></span><span style="display:inline-block;vertical-align: text-bottom;margin-left:6px;">'+Designer_Lang.controlDetailsTable_moveDown+'</span></span>'
							+"</div>"
							+"<div name='tr_normal_opt_r' style='POSITION: absolute; RIGHT: 0px; TOP: 0px'>"
							+ '<span name="excelExport" style="display:inline-block; line-height: 20px;"><span ' + bgmUrlStyle + 'background-position: -100px 0; margin-right:6px;position: relative;top:2px;\'"></span><span style="display:inline-block;vertical-align: text-bottom;">'+Designer_Lang.controlDetailsTable_excelExport+'</span></span>'
							+ '<span name="excelImport" style="margin-left:15px; display:inline-block; line-height: 20px;"><span ' + bgmUrlStyle + 'background-position: -80px 0; margin-right:6px;position: relative;top:2px;\'"></span><span style="display:inline-block;vertical-align: text-bottom;">'+Designer_Lang.controlDetailsTable_excelImport+'</span></span>'
							+"</div>"
						+"</div>";
				}
			}else if (j == 1) {
				//cell.setAttribute('align', 'center');
				cell.style.width = '25px';
				if (i == 0) {
					cell.setAttribute("colType", "noTitle");
					cell.innerHTML = '<label attach="' + this.options.values.id + '">'+Designer_Lang.controlDetailsTable_seqNo+'</label>';
				}
				else if (i == 1) {
					cell.setAttribute("colType", "noTemplate");
					cell.innerHTML = '<label attach="' + this.options.values.id + '">'+i+'</label>';
				}
				else if (i == 2) {
					cell.setAttribute("colType", "noFoot");
					cell.innerHTML = '<label attach="' + this.options.values.id + '">&nbsp;</label>';
				}
				else if (i == 3){
					$(cell).remove();
				}
			} else if (j + 1 == cells.cell) {
				cell.style.width = '48px';
				if (i == 0) {
					cell.setAttribute('colType', 'blankTitleCol');
				}
				else if (i == 1) {
					cell.setAttribute('colType', 'copyCol');
					var copy = 'style/img/icon_copy.png';
					var del = 'style/img/icon_del.png';
					cell.innerHTML = '<nobr><img class="copyIcon" src="' + copy + '" title='+Designer_Lang.controlDetailsTable_copyRow+'>&nbsp;&nbsp;' 
							+ '<img class="delIcon" src="' + del + '" title='+Designer_Lang.controlDetailsTable_delRow+'>&nbsp;&nbsp;';
				}
				else if (i == 2) {
					cell.setAttribute('colType', 'emptyCell');
					cell.innerHTML = '<label attach="' + this.options.values.id + '">&nbsp;</label>';
				}
				else if (i == 3){
					$(cell).remove();
				}
			} else {
				if (i == 2) {
					this.columnCellDraw(cell);
				}
				else if (i == 3){
					$(cell).remove();
				}
			}
		}
	}
	this.setColumnsWidth();
}

//数据录入属性栏
function _Designer_Control_DetailsTable_DataEntryMode_Self_Draw(name, attr, value, form, attrs, values,control) {
	var buff = [];
	if (attr.opts) {
		for (var i = 0; i < attr.opts.length; i ++) {
			var opt = attr.opts[i];
			buff.push('<label isfor="true"><input type="radio" name="', name, '"');
			if (opt.value == value) {
				buff.push(' checked="checked" ');
			} else if ((value == null || value == '') && attr.value == opt.value) {
				buff.push(' checked="checked" ');
			}
			if (opt.onclick != null) {
				buff.push(' onclick="' + opt.onclick + '"');
			}
			buff.push(' value="', opt.value, '">', opt.text, '</label><br>');
		}
	}
	if (attr.required == true) {
		buff.push('<span class="txtstrong">*</span>');
	}
	var isShow = (!values.layout2col || values.layout2col === "mobile") ? false : true;
	return ('<tr style="display:' + (isShow ? "" : "none") + ';"><td width="25%" class="panel_td_title">' + attr.text + '</td><td>' + buff.join("") + '</td></tr>');
}


//自定义Excel操作属性栏
function _Designer_Control_DetailsTable_ExcelOper_Self_Draw(name, attr, value, form, attrs, values,control){
	//excelImport:Excel导入属性，excelExport:Excel导出属性 
	var html = '';
	html += "<label isfor='true'><input type='checkbox' value='1'";
	var excelExport = control.options.values.excelExport || 'false';
	var excelImport = control.options.values.excelImport || 'false';
	if(excelImport == 'true'){
		html += " checked='checked'";		
	}
	html += " onclick='_Designer_Control_DetailsTable_ExcelOper_setExcelOper(this);'/>"+Designer_Lang.controlDetailsTable_attr_excelImport+"</label></br>";
	html += "<label isfor='true'><input type='checkbox' value='2'";
	if(excelExport == 'true'){
		html += " checked='checked'";		
	}
	html += " onclick='_Designer_Control_DetailsTable_ExcelOper_setExcelOper(this);'/>"+Designer_Lang.controlDetailsTable_attr_excelExport+"</label>";
	//延时赋值
	setTimeout(function(){
		if($("input[name='excelExport']").length > 0){
			$("input[name='excelExport']")[0].value = excelExport;
	    }
		if($("input[name='excelImport']").length > 0){
			$("input[name='excelImport']")[0].value = excelImport;
	    }
	},0);
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

//设置Excel隐藏属性
function _Designer_Control_DetailsTable_ExcelOper_setExcelOper(dom){
	if(dom && dom.value){
		// 1为Excel导入，2为Excel导出
		if(dom.value == '1'){
			if($("input[name='excelImport']").length > 0){
				$("input[name='excelImport']")[0].value = dom.checked ? 'true':'false';
		    }
			// 高级明细表勾选了excel导入，导入模式才显示
			var $nameObj = $("[name='importType']");
			if($nameObj.length>0){
				var tr = $nameObj.closest("tr");
				if(dom.checked){
					tr.show();
				}else{
					tr.hide();
				}
			}

		}else if(dom.value == '2'){
			if($("input[name='excelExport']").length > 0){
				$("input[name='excelExport']")[0].value = dom.checked ? 'true':'false';
		    }
		}
	}
}

// 设置宽度属性
function _Designer_Control_DetailsTable_SetColumnsWidth() {
	var domElement = this.options.domElement;
	var columns = domElement.rows[0].cells.length - 2;
	var width = 'auto';//100 / columns + '%';
	var columnsWidth = ['10px'];
	for (var i = 0, l = columns; i < l; i ++) {
		//domElement.rows[0].cells[i + 1].width = width;
		//columnsWidth.push(width);
	}
	//columnsWidth.push('10px');
	//domElement.columnsWidth = columnsWidth.join(';');
	domElement = null;
}

//设置操作栏列数
function _Designer_Control_DetailsTable_SetOptRowColSpan(){
	var domElement = this.options.domElement;
	var templateRow = $(domElement).find("[type='templateRow']");
	var columns = templateRow[0].cells.length;
	var optRow = domElement.rows[domElement.rows.length - 1];
	if(optRow.getAttribute('type') == 'optRow'){
		optRow.cells[0].setAttribute('colSpan',columns);
	}
}

function _Designer_Control_DetailsTable_OnInitialize() {
	var table = this.options.domElement, column = [];
	var rowCount = table.rows.length, colCount = 0, row, cell, cellColumn;
	//计算列总数
	row = table.rows[0], colCount = row.cells.length;
	for (var i = 0; i < colCount; i++) {
		cell = row.cells[i];
		rowCount = column.length;
		cellColumn = cell.getAttribute('column').split(',');
		for (var j = 0; j < cellColumn.length; j++)
			column.push([]);
		column[rowCount].push(cell);
	}
	//遍历单元格，放入相应列组中
	rowCount = table.rows.length;
	for (var i = 1; i < rowCount; i++) {
		row = table.rows[i];
		colCount = row.cells.length;
		for (var j = 0; j < colCount; j++) {
			cell = row.cells[j];
			cellColumn = cell.getAttribute('column').split(',');
			column[cellColumn[0]].push(cell);
		}
	}
	//记录列对象集
	this.options.column = column;
}

// 添加列
function _Designer_Control_DetailsTable_InsertColumn() {
	this._insertColumn(null,function(cells){
		var multiCells = [];
		for(var i = 0;i < cells.length;i++){
			var $tr = $(cells[i]).closest("tr");
			if(_Designer_Control_DetailsTable_IsMultiHeadTrElement($tr[0])){
				_Designer_Control_DetailsTable_SetElementMultiHeadColValue(cells[i],"true");
			}else if($tr.is("[type='titleRow']")){
				_Designer_Control_DetailsTable_SetElementMultiHeadColValue(cells[i],"false");
			}
		}
	});
	this.setColumnsWidth();
	this.setOptRowColspan();
}

// 删除列
function _Designer_Control_DetailsTable_DeleteColumn() {
	this._deleteColumn();
	this.setColumnsWidth();
	this.setOptRowColspan();
}

// 追加列
function _Designer_Control_DetailsTable_AppendColumn() {
	var row = this.options.domElement.rows[0], colCount = row.cells.length;
	this._insertColumn(row.cells[colCount - 1],function(cells){
		var multiCells = [];
		for(var i = 0;i < cells.length;i++){
			var $tr = $(cells[i]).closest("tr");
			if(_Designer_Control_DetailsTable_IsMultiHeadTrElement($tr[0])){
				_Designer_Control_DetailsTable_SetElementMultiHeadColValue(cells[i],"true");
			}else if($tr.is("[type='titleRow']")){
				_Designer_Control_DetailsTable_SetElementMultiHeadColValue(cells[i],"false");
			}
		}
	});
	this.setColumnsWidth();
	this.setOptRowColspan();
}

// 选中显示
function _Designer_Control_DetailsTable_OnSelect(event) {
	//this.options.showAttr = 'default';
	var currElement = event.srcElement || event.target;
	//若选中的不是单元格，则退出
	if (!Designer.checkTagName(currElement, 'td')) {
		this.options.values.columnIndex = null;
		return;
	}
	//this.options.values.columnIndex = currElement.cellIndex;
	//if (currElement.cellIndex == 0) return;
	if ((currElement.colType != null && currElement.colType != '')) return;
	if (this.options._selectTable) {this.onUnLock(); return;}
	//this.options.showAttr = currElement.parentNode.rowIndex == 2 ? 'all' : 'default';
	//var table = currElement.parentNode.parentNode.parentNode;
	//var dataCell = table.rows[2].cells[currElement.cellIndex];
	//var input = dataCell.getElementsByTagName('input')[0];
	//this.options.values.selectedValue = input.selectedValue;
	//this.options.values.showText = input.showText;

	this.selectedDomElement = [];
	this.chooseCell(currElement, true);
}

function _Designer_Control_DetailsTable_DoMouseOver() {

}

// 插入校验
function _Designer_Control_DetailsTable_InsertValidate(cell, control) {
	var tdCell = cell;
	if(cell.tagName.toLowerCase != 'td'){
		tdCell = $(cell).closest("td")[0];
	}
	if (tdCell.colType != null && tdCell.colType != '') {
		return false;
	}
	if (control.implementDetailsTable != true) {
	    if  (this.isAdvancedDetailsTable) {
            alert(Designer_Lang.controlSeniorDetailsTable_notSupportControl);
        } else {
            alert(Designer_Lang.controlDetailsTable_notSupportControl);
        }
		return false;
	}
	return true;
}

//数据字段生成
function _Designer_Control_DetailsTable_DrawXML() {
	var values = this.options.values;
	var rowDom;
	var otherXml = [];
	var buf = ['<extendSubTableProperty '];
	buf.push('name="',values.id, '" ');
	buf.push('label="', values.label, '" ');
	// 控件类型
	buf.push('businessType="', this.type, '" ');
	//高级明细表记录最原始id
    if (values.originId && this.type === "seniorDetailsTable") {
        var customElementProperties = {};
        customElementProperties.originId = values.originId;
        buf.push('customElementProperties="', Designer.HtmlEscape(JSON.stringify(customElementProperties)), '" ');
    }
	buf.push('>\r\n');
	buf.push('<idProperty><generator type="assigned" /></idProperty>\r\n');
	if (this.children.length > 0) {
		var xmls = [];
		var rowDomTemp;
		this.children = this.children.sort(Designer.SortControl);
		for (var i = 0, l = this.children.length; i < l; i ++) {
			var c = this.children[i];
			if (c.drawXML) {
				var xml = c.drawXML();
				if (xml != null) {
					rowDom = Designer_Control_DetailsTable_GetParentDom('tr', c.options.domElement);
					if ($(rowDom).attr("type") == 'templateRow' || $(c.options.domElement).closest("tr[type='templateRow']").length>0) {
						xmls.push(xml, '\r\n');
						rowDomTemp = rowDom;
					} else {
						//要将非模板行和明细表关联
						try{
							var index = xml.indexOf("name") - 1;
							var leftStr = xml.substring(0, index);
							var rightStr = xml.substring(index);
							
							var cellDom = Designer_Control_DetailsTable_GetParentDom('td', c.options.domElement);
							var cellIndex = cellDom.cellIndex;
							var fdValues = $(rowDomTemp).find("td").eq(cellIndex).find("[fd_values]").attr("fd_values");
							var id = eval('('+fdValues+')').id;
							
							leftStr += ' relationTable="'+values.id + '.' + id + '"';
							var oxml = leftStr + rightStr;
							if(oxml){
								otherXml.push(oxml);
							}else{
								otherXml.push(xml);
							}
						}catch(e){
							otherXml.push(xml);
						}
					}
				}
			}
		}
		buf.push( xmls.join('') );
	}
	buf.push('</extendSubTableProperty>\r\n');
	return buf.join('') + otherXml.join('');
}

/**
 * 合并单元格
 * @returns
 */
function _Designer_Control_DetailsTable_MergeCell(fn){
	var self = this;
	var multiHead = self.options.values.multiHead;
	if(multiHead && multiHead == 'true'){
		var dom = self.options.domElement;
		// 找到原本的标题行
		var selectedDomArr = self.selectedDomElement;
		var isAllMultiHead = true;
		var count = 0;
		for(var i = 0;i < selectedDomArr.length;i++){
			var selectedDom = selectedDomArr[i];
			if(_Designer_Control_DetailsTable_IsNotMoveCol(selectedDom) || 
					!_Designer_Control_DetailsTable_IsMultiHeadElement($(selectedDom).closest("tr"))){
				isAllMultiHead = false;
				break;
			}
			// 多表头的最后一行标题行，不允许列合并，可以行合并
			if($(selectedDom).attr("data-ismultiheadcol") == "true"){
				count++;	
			}
			if(count > 1){
				alert("第二行表头不允许列合并！");
				return;
			}
		}
		if(isAllMultiHead){
			if(fn){
				fn();
			}
		}else{
			alert("只支持多表头元素的拆分和合并！");
		}
	}else{
		_Designer_Control_DetailsTable_noSuppotAlert();
	}
}

/**
 * 删除多表头行
 * @param row 多表头行
 * @param titleRow 标题行
 * @param control
 * @returns
 */
function _Designer_Control_DetailsTable_DeleteMultiHeadTr(row,titleRow,control){
	// 把合并的行拆分到标题行
	var columns = control.options.column;
	var tds = $(row).find("td");
	for(var i = 0;i < tds.length;i++){
		var td = tds[i];
		// 列索引
		var colIndex = $(td).attr("column").split(",")[0];
		var rowArr = $(td).attr("row").split(",");
		// 只要存在合并行，都需要同步新增td
		if(rowArr.length > 1){
			$(td).removeAttr("rowspan");
			// 新td
			var titleTd = td.cloneNode(true);
			var rowIndex;
			var titleTds = $(titleRow).find("td");
			// 设置同行的row，插入元素
			if(titleTds.length >= parseInt(colIndex) + 1){
				var a = titleTds[colIndex];
				rowIndex = $(a).attr("row").split(",")[0];
				$(a).before(titleTd);
			}else{
				var a = titleTds[titleTds.length - 1];
				rowIndex = $(a).attr("row").split(",")[0];
				$(a).after(titleTd);
			}
			$(titleTd).attr("row", rowIndex);
			for(var j = 0;j < columns[i].length;j++){
				if(columns[i][j] == td){
					// 插入新元素
					columns[i].splice(j + 1,0,titleTd);
					break;
				}
			}	
		}
	}
	var index = $(row).index();
	for(var i = 0;i < columns.length;i++){
		for(var j = 0;j < columns[i].length;j++){
			var rowIndex = $(columns[i][j]).attr("row")[0];
			// 删除原来的单元格
			if(rowIndex == index){
				columns[i].splice(index,1);					
			}
		}
	}
	$(row).remove();
}

/**
 *	插入多表头行 
 * @param row 标题行
 * @param control
 * @returns
 */
function _Designer_Control_DetailsTable_InsertMultiHeadTr(row,control){
	// 在最上面添加一行
	var tr0 = row.cloneNode(false);
	_Designer_Control_DetailsTable_SetElementMultiHeadValue(tr0);
	$(row).before(tr0);
	// 需要更新列对象
	var columns = control.options.column;
	var tds = $(row).find("td");
	for(var i = 0;i < tds.length;i++){
		var td = tds[i];
		var td0 = td.cloneNode(false);
		_Designer_Control_DetailsTable_SetElementMultiHeadColValue(td0,"false");
		if(_Designer_Control_DetailsTable_IsNotMoveCol(td)){
			td0 = td.cloneNode(true);
			for(var j = 0;j < columns[i].length;j++){
				if(columns[i][j] == td){
					// 删除元素
					columns[i].splice(j,1);
					break;
				}
			}	
			$(td).remove();
			$(td0).attr("rowspan","2");
			
			var _rowNo = $(td0).attr("row").split(",");
			_rowNo.push(parseInt(_rowNo[_rowNo.length - 1]) + 1);
			$(td0).attr("row", _rowNo.join(','));
		}
		$(tr0).append(td0);
		columns[i].unshift(td0);	
	}
}

/**
 * td更新属性row
 * @param $area 更新范围
 * @param offset
 * @returns
 */
function _Designer_Control_DetailsTable_UpdateTdAttr($area,offset){
	var tds = $area.find("td[row]");
	for(var i = 0;i < tds.length;i++){
		var td = tds[i];
		var rowArr = $(td).attr("row").split(",");
		var newIndex = [];
		for(var j = 0;j < rowArr.length;j++){
			newIndex.push(parseInt(rowArr[j]) + offset);
		}
		$(td).attr("row",newIndex.join(','));
	}
}

/**
 * 多表头初始化
 * @param dom
 * @param values
 * @returns
 */
function _Designer_Control_DetailsTable_OnInitMultiHead(dom,values){
	var multiHead = values.multiHead;
	if(multiHead && multiHead == 'true'){
		var titleRow = $(dom).find("tr[type='titleRow']");
		if(titleRow.length == 1){
			// 插入多表头行
			_Designer_Control_DetailsTable_InsertMultiHeadTr(titleRow[0],this);
			// 更新属性
			_Designer_Control_DetailsTable_UpdateTdAttr($(dom).find("tr:not(:first)"),1);
			// 把标题行和表格都添加上多表头属性
			_Designer_Control_DetailsTable_SetMultiHead(dom,titleRow,"true");
		}
	}else{
		var trs = $(dom).find("tr");
		// 找到原本的标题行
		var titleTr = $(dom).find("tr[type='titleRow'][data-ismultiheadtr='true']");
		if(titleTr.length > 0){
			for(var i = 0;i < trs.length;i++){
				var tr = trs[i];
				// 处理多表头的行
				if(_Designer_Control_DetailsTable_IsMultiHeadElement(tr) && tr != titleTr[0]){
					if(titleTr.length > 0){
						// 删除多表头行
						_Designer_Control_DetailsTable_DeleteMultiHeadTr(tr,titleTr[0],this);
						// 更新属性
						_Designer_Control_DetailsTable_UpdateTdAttr($(dom),-1);	
					}
				}
			}
		}
		// 把标题行和表格都删除上多表头属性
		_Designer_Control_DetailsTable_SetMultiHead(dom,titleTr,"false");
	}
}

/**
 * 把标题行和表格都设置多表头属性为value
 * @param dom
 * @param titleTr
 * @param value
 * @returns
 */
function _Designer_Control_DetailsTable_SetMultiHead(dom, titleTr, value){
	// 把标题行和表格都设置多表头属性为value
	_Designer_Control_DetailsTable_SetElementMultiHeadValue(dom,value);
	_Designer_Control_DetailsTable_SetElementMultiHeadValue(titleTr,value);
	$(titleTr).find("td:not([coltype])").each(function(index,e){
		_Designer_Control_DetailsTable_SetElementMultiHeadColValue(e,value);
	});
	$(titleTr).attr("data-ismultiheadtr",value);
}

/**
 * 判断元素是否属性多表头元素
 * @param tr
 * @returns
 */
function _Designer_Control_DetailsTable_IsMultiHeadElement(elem){
	var flag = false;
	if(elem && $(elem).is("[data-multihead='true']")){
		flag = true;
	}
	return flag;
}

/**
 * 设置元素为多表头元素
 * @param elem
 * @returns
 */
function _Designer_Control_DetailsTable_SetElementMultiHeadValue(elem , b){
	var f = "true";
	if(b){
		f = b;
	}
	if(elem){
		$(elem).attr("data-multihead",f);
	}
	return elem;
}

/**
 * 设置元素为多表头列元素
 * @param elem
 * @param b
 * @returns
 */
function _Designer_Control_DetailsTable_SetElementMultiHeadColValue(elem , b){
	var f = "true";
	if(b){
		f = b;
	}
	if(elem){
		$(elem).attr("data-ismultiheadcol",f);
	}
	return elem;
}

/**
 * 判断元素是否为多表头标题行元素
 * @param tr
 * @returns
 */
function _Designer_Control_DetailsTable_IsMultiHeadTrElement(elem){
	var flag = false;
	if(elem && $(elem).is("[data-ismultiheadtr='true']")){
		flag = true;
	}
	return flag;
}

// 绘制结束调用方法
function _Designer_Control_DetailsTable_OnDrawEnd() {
	var values = this.options.values;
	var domElement = this.options.domElement;
	domElement.label = values.label;
	domElement.setAttribute('id', values.id);
	// 处理多表头
	this.initMultiHead(domElement,values);
	
	//序号强制不换行
	$(domElement).find("td[coltype='noTitle']").css("white-space","nowrap");
	
	this.options.values.width=this.options.values.width?this.options.values.width:'100%';
	
	domElement.width=this.options.values.width;
	domElement.style.width=this.options.values.width;
	$(domElement).attr("width",this.options.values.width);
	$(domElement).css("width",this.options.values.width);

	if (this.parent)
		domElement.setAttribute('tableName', _Get_Designer_Control_TableName(this.parent));

	if (values) {
		var rows = domElement.rows;
		if (values.showIndex == 'false') {
			domElement.setAttribute('showIndex', 'false');
			for (var i = 0; i < rows.length; i ++) {
				if(rows[i].getAttribute('type') == 'optRow')
					continue;
				//隐藏序号列
				$(rows[i]).find('td[colType="noTitle"],td[colType="noTemplate"],td[colType="noFoot"]').hide();
			}
		} else {
			domElement.setAttribute('showIndex', 'true');
			for (var i = 0; i < rows.length; i ++) {
				if(rows[i].getAttribute('type') == 'optRow')
					continue;
				//显示序号列
				if (rows[i].cells[1]) {
					rows[i].cells[1].style.display = '';//旧模板在新需求下（增加复选框列），导致序列号后面那列不小心被隐藏了，这里增加兼容代码把不小心被隐藏的列显示回出来
				}
				$(rows[i]).find('td[colType="noTitle"],td[colType="noTemplate"],td[colType="noFoot"]').show();
			}
		}
		if (values.showRow == null) {
			values.showRow = 1;
		}
		
		
		var alignment = Designer_Config.controls.detailsTable.attrs.alignment.value;
		//#55955 明细表出滚动条的情况下，建议底部操作布局优化
		if (values.alignment){
			alignment = values.alignment;
			domElement.setAttribute("aligment",alignment);
			var row = "";
			for (var i = 0; i < rows.length; i ++) {
				if(rows[i].getAttribute('type') == 'optRow'){
					row = rows[i];
				}
			}
			if(alignment === "right"){
				$(row).find("div[name='tr_normal_opt_content']").css("text-align","right");
			}
			if (alignment === "left"){
				$(row).find("div[name='tr_normal_opt_content']").css("text-align","left");
			}
			if (alignment === "left" || alignment === "right"){
				$(row).find("div[name='tr_normal_opt_l']").css("display","inline-block");
				$(row).find("div[name='tr_normal_opt_c']").css("display","inline-block");
				$(row).find("div[name='tr_normal_opt_r']").css("display","inline-block");
				$(row).find("div[name='tr_normal_opt_l']").css("position","");
				$(row).find("div[name='tr_normal_opt_c']").css("position","");
				$(row).find("div[name='tr_normal_opt_r']").css("position","");
				$(row).find("div[name='tr_normal_opt_l']").css("margin-right","30px");
				$(row).find("div[name='tr_normal_opt_c']").css("margin-right","30px");
			}
			if(alignment === "default"){//还原默认样式
				$(row).find("div[name='tr_normal_opt_content']").css("text-align","");
				$(row).find("div[name='tr_normal_opt_l']").css("position","absolute");
				$(row).find("div[name='tr_normal_opt_r']").css("position","absolute");
				$(row).find("div[name='tr_normal_opt_l']").css("margin-right","0");
				$(row).find("div[name='tr_normal_opt_c']").css("margin-right","0");
			}
		}
		
		
		if (values.showRow) {
			// 初始行数
			var rowSize = parseInt(values.showRow);
			if (isNaN(rowSize) || rowSize < 0) {
				rowSize = 0;
			}
			domElement.setAttribute('showRow', '' + rowSize);
			// 是否显示统计行
			domElement.setAttribute('showStatisticRow', values.showStatisticRow);
			if(values.showStatisticRow === 'false'){
				$('tr[type="statisticRow"]',$(domElement)).hide();
			}else{
				$('tr[type="statisticRow"]',$(domElement)).show();
			}
			// 是否显示复制行功能
			domElement.setAttribute('showCopyOpt', values.showCopyOpt);
			if(values.showCopyOpt === 'false'){
				$('img.copyIcon',$(domElement)).hide();
			}else{
				$('img.copyIcon',$(domElement)).show();
			}
		}
		domElement.setAttribute("dataEntryMode",values.dataEntryMode || "multipleRow");
		domElement.setAttribute('required', values.required);
		domElement.setAttribute('excelExport', values.excelExport);
		domElement.setAttribute('excelImport', values.excelImport);
		if(values.excelExport && values.excelExport == 'true'){			
			$('span[name="excelExport"]',$(domElement)).show();
		}else{
			$('span[name="excelExport"]',$(domElement)).hide();
		}
		if(values.excelImport && values.excelImport == 'true'){			
			$('span[name="excelImport"]',$(domElement)).show();
		}else{
			$('span[name="excelImport"]',$(domElement)).hide();
		}
		domElement.setAttribute('defaultFreezeTitle', values.defaultFreezeTitle);
		domElement.setAttribute('defaultFreezeCol', values.defaultFreezeCol);
	}
}

// 列单元绘制
function _Designer_Control_DetailsTable_Column_Cell_Draw(cell, selectedValue, showText) {
	var html = '&nbsp;';
	//var html = '<input type="hidden" selectedValue="' + (selectedValue ? selectedValue : "none");
	//if (showText) {
	//	html += '" showText="' + showText;
	//}
	//html += '" >';
	//if (showText) {
	//	html += '<label attach="' + this.options.values.id + '">' + showText + '</label>';
	//}
	cell.innerHTML = html;
}

function Designer_Control_DetailsTable_GetParentDom(tagName, dom) {
	var parent = dom;
	while((parent = parent.parentNode) != null) {
		if (Designer.checkTagName(parent, tagName)) {
			return parent;
		}
	}
	return parent;
}

function _Designer_Control_DetailsTable_GetRelateWay(control) {
	var tr = control.options.domElement;
	tr = Designer_Control_DetailsTable_GetParentDom('tr', tr);
	if (control.isTextLabel) {
		if ($(tr).attr("type")&& $(tr).attr("type")== 'titleRow') {
			return 'up';
		}
		return 'self';
	}
	if ($(tr).attr("type")&& $(tr).attr("type") == 'templateRow') {
		return 'up';
	}
	return 'self';
}

function _Designer_Control_DetailsTable_required_Self_Draw(name, attr, value, form, attrs, values,control){
	var html = '';
	html += "<label isfor='true'><input type='checkbox' name='required' value='1'";
	var required = control.options.values.required || 'false';
	if(required == 'true'){
		html += " checked='checked'";		
	}
	html += " onclick='_Designer_Control_DetailsTable_required_setValue(this);'/>" + Designer_Lang.controlDetailsTable_attr_required + "</label>";
	//延时赋值
	setTimeout(function(){
		if(document.getElementsByName("required")[0]){
	    	document.getElementsByName("required")[0].value = required;
	    }
	},0);
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

function _Designer_Control_DetailsTable_MultiHead_Self_Draw(name, attr, value, form, attrs, values,control){
	var html = '';
	html += "<label isfor='true'><input type='checkbox' name='multiHead' ";
	var multiHead = control.options.values.multiHead || 'false';
	if(multiHead == 'true'){
		html += " checked='checked'";
	}
	html += " value = '" + multiHead + "'";
	html += " onclick='_Designer_Control_DetailsTable_MultiHead_setValue(this);'/></label>";
	
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

function _Designer_Control_DetailsTable_freezeRow_Self_Draw(name, attr, value, form, attrs, values,control){
	var html = '';
	html += "<label isfor='true'><input type='checkbox' name='defaultFreezeTitle' value='1'";
	var required = control.options.values.defaultFreezeTitle || 'false';
	if(required == 'true'){
		html += " checked='checked'";
	}
	html += " onclick='_Designer_Control_DetailsTable_freezeRow_setValue(this);'/>" + Designer_Lang.controlDetailsTable_attr_freezeEnable + "</label>";
	return Designer_AttrPanel.wrapTitle(name, attr, value, html);
}

function _Designer_Control_DetailsTable_MultiHead_setValue(dom){
	if(document.getElementsByName("multiHead")[0]){
    	document.getElementsByName("multiHead")[0].value = dom.checked ? 'true':'false';
    }
}

function _Designer_Control_DetailsTable_required_setValue(dom){	
	if(document.getElementsByName("required")[0]){
    	document.getElementsByName("required")[0].value = dom.checked ? 'true':'false';
    }	
}

function _Designer_Control_DetailsTable_freezeRow_setValue(dom){
	if(document.getElementsByName("defaultFreezeTitle")[0]){
		document.getElementsByName("defaultFreezeTitle")[0].value = dom.checked ? 'true':'false';
	}
}

function Designer_Control_Attr_showRow_Validator(elem, name, attr, value, values){
	var required = values.required;
	if(required && required == 'true'){
		if(parseInt(value) < 1){
			alert(Designer_Lang.controlDetailsTable_attr_requiredValidate);
			return false;
		}
	}
	return true;
}

/**
 * 拖拽
 * @param event
 * @returns
 */
function _Designer_Control_DetailsTable_onDragMoving(event){
	if(Designer.UserAgent == 'msie') {
		this.options.domElement.setCapture();
		event.cancelBubble = true;
	} else {
		event.preventDefault();
		event.stopPropagation();
	}
	window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
	//控件锁定，则不能移动
	if (event.ctrlKey) return;
	
	if (this.dragAction.onMove)
		this.dragAction.onMove(event, this);
	// 如果是多表头行，支持滑动选择
	var isMoving = false;
	var selectedDomArr = this.selectedDomElement;
	if(selectedDomArr.length == 0){
		// 支持通过optRow行进行拖拽
		isMoving = true;
	}
	for(var i = 0;i < selectedDomArr.length;i++){
		var selectDom = selectedDomArr[i];
		if(_Designer_Control_DetailsTable_IsNotMoveCol(selectDom)){
			continue;
		}
		if(!_Designer_Control_DetailsTable_IsMultiHeadElement($(selectDom).closest("tr"))){
			isMoving = true;
			break;
		}
	}
	if(isMoving){
		this.onMoving(event);
	}else{
		this.onDragMovingChooseCell(event);
	}
}

/**
 * 是否是静态单元格
 * @param td
 * @returns
 */
function _Designer_Control_DetailsTable_IsNotMoveCol(td){
	return $(td).is("[coltype='selectCol']") || $(td).is("[coltype='noTitle']") || $(td).is("[coltype='blankTitleCol']");
}

// ======== 明细表 (结束)===========