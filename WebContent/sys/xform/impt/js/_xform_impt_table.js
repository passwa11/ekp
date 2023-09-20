/**********************************************************
功能：导入时，通用前端处理
**********************************************************/
__xform_impt_parser.table = {
		callback:function(data,context){
			var controls = data.controlers;
			var _maxWidth = data.maxWidth;
			var _maxRow = data.maxRow;
			var _maxColumn = data.maxColumn;
			
			var tabControl = null;
			var initControlSetting = function(control){
				control.attrs.label.text = data.name;//未生效
				control.attrs.cell.style = " ";//清空表格的样式
				tabControl = control;
			};
			var getControl = function(cid){
				for(var idx in controls){
					if(controls[idx].id==cid){
						return controls[idx];
					}
				}
			};
			Designer.instance.builder.createControl('standardTable',context.parent,initControlSetting);
			var tableVar = $(tabControl.options.domElement).html($(data.layoutHtml).html());
			//tableVar.css({"table-layout":"fixed"});
			var rows = tableVar.get(0).rows;
			var tmpRows = new Array();
			for(var i=0;i<rows.length;i++){
				var cells = rows[i].cells;
				var tmpCells = new Array();
				for ( var j = 0; j < cells.length; j++) {
					tmpCells.push(cells[j]);
					var tmpCell = $(cells[j]);
					var ctrlId = tmpCell.html("&nbsp;").attr("_wgtId");
					tmpCell.removeAttr("_wgtId");
					var oneCellW = tmpCell.attr("_width");
					if(oneCellW!=''){
						tmpCell.css({"width":''+Math.ceil((parseInt(oneCellW,10)/_maxWidth)*100)+'%'});
					}
					tmpCell.removeAttr("_width");
					var ctrlObj = getControl(ctrlId);
					if(__xform_impt_parser[ctrlObj.type].callback){
						__xform_impt_parser[ctrlObj.type].callback(ctrlObj,{parent:cells[j]});
					}else{
						if(window.console){
							window.console.error("暂不支持控件" + ctrlObj.type);
						}
					}
				}
				tmpRows.push(tmpCells);
			}
			var column = this.getColumns(rows);
			
			tabControl.options.tmpRows = tmpRows;
			tabControl.options.column = column;
			tabControl.attrs.cell.value = _maxColumn;
			tabControl.attrs.row.value = _maxRow;
		},
		
		getColumns:function(rows){
			debugger;
			if(rows.length == 0){
				return [];
			}
			var column = [];
			var colCount = 0, row, cell, cellColumn;
			//获取最大列数和对应行
			var maxColCount = 0;
			var maxColRow;
			for(var i=0; i<rows.length; i++){
				var temp = rows[i];
				if(maxColCount < temp.cells.length){
					maxColCount = temp.cells.length;
					maxColRow = temp;
				}
			}
			for(var i=0; i<maxColCount; i++){
				cell = maxColRow.cells[i];
				cellColumn = cell.getAttribute('column').split(',');
				for (var j = 0; j < cellColumn.length; j++)
					column.push([]);
			}
			var rowCount = rows.length;
			//遍历单元格，放入相应列组中
			for (var i = 0; i < rowCount; i++) {
				row = rows[i];
				colCount = row.cells.length;
				for (var j = 0; j < colCount; j++) {
					cell = row.cells[j];
					cellColumn = cell.getAttribute('column').split(',');
					column[cellColumn[0]].push(cell);
					var cellWidth = _Designer_Control_Table_GetCellWidth(cell);
					cell.setAttribute('width', cellWidth);
				}
			}
			//返回列对象集
			return column;
		}
};
