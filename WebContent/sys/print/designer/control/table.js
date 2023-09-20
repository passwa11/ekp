(function(window, undefined){
	
	/**
	 * 表格控件
	 */
	var tableControl=sysPrintDesignerControl.extend({
		
		container:true, //容器
		
		$moveDom:null,
		$selectDom:null,
		dash:{dashDom:'',isMouseDown:'',actionType:''},//拖拽相关信息
		
		//exports
		insertRow:insertRow,
		appendRow:appendRow,
		deleteRow:deleteRow,
		insertCol:insertCol,
		appendCol:appendCol,
		deleteCol:deleteCol,
		splitCell:splitCell,//拆分单元格
		uniteCell:uniteCell,//合并单元格
		onInitialize:onInitialize,//记录列集合
		
		_resetCoordin:_resetCoordin,//更新坐标位置
		_dealRowSpanCells:_dealRowSpanCells,//处理跨行单元格
		_getColumnSize:_getColumnSize,//获取最大列数
		_isInSameRow:_isInSameRow,//判断选中的单元格是否在同一行里
		_isInSameColumn:_isInSameColumn,//判断选中的单元格是否在同一列里
		_getPosByRow:_getPosByRow,//获得所在行的位置，根据坐标

		_bindEvent:_bindEvent,//重新声明事件
		_unbindEvent:_unbindEvent,//解绑事件
		setTextAlign:setTextAlign,
		//表格拖拽
		_mousemove:_mousemove,//鼠标移动事件
		_dashMouseDown:_dashMouseDown, //单元格拖拽事件
		_getDashDomWidthDatas:_getDashDomWidthDatas,//拖拽前单元格相关信息
		_getDashDomCellIndex:_getDashDomCellIndex,//获取拖拽单元格下标
		_getCellBycolumnIndex:_getCellBycolumnIndex,//根据下标获取对应单元格
		_getOldRowCellWidthObj:_getOldRowCellWidthObj,//获取单元格宽度相关信息
		
		_onSelectControl:_onSelectControl,
		//单元格属性
		domAttrs : {
			td:{
				style_textAlign: {
					text: DesignerPrint_Lang.attrpanelTableDomAttrTdAlign,
					value: "left",
					type: 'radio',
					opts: [
						{text:DesignerPrint_Lang.attrpanelTableDomAttrTdAlignLeft,value:"left"}, // left
						{text:DesignerPrint_Lang.attrpanelTableDomAttrAlignCenter,value:"center"},
						{text:DesignerPrint_Lang.attrpanelTableDomAttrTdAlignRight,value:"right"}
					]
				},
				style_verticalAlign: {
					text: DesignerPrint_Lang.attrpanelTableDomAttrTdVAlign,
					value: "middle",
					type: 'radio',
					opts: [
						{text:DesignerPrint_Lang.attrpanelTableDomAttrTdVAlignTop,value:"top"},
						{text:DesignerPrint_Lang.attrpanelTableDomAttrTdVAlignMiddle,value:"middle"}, // middle
						{text:DesignerPrint_Lang.attrpanelTableDomAttrTdVAlignBottom,value:"bottom"}
					]
				},
				className: {
					text: DesignerPrint_Lang.attrpanelTableDomAttrTdClassName,
					value: "tb_normal",
					type: 'radio',
					opts: [
						{text:DesignerPrint_Lang.attrpanelTableDomAttrTdClassNameNormal,value:"tb_normal"}, // tb_normal
						{text:DesignerPrint_Lang.attrpanelTableDomAttrTdClassNameTitle,value:"td_normal_title"}
					]
				},
				style_width: {
					text: DesignerPrint_Lang.attrpanelTableDomAttrTdWidth,
					value: "auto",
					type: 'text'
				}
			},
			info:{
				name:DesignerPrint_Lang.attrpanelTableDomAttrTable,
				td:DesignerPrint_Lang.attrpanelTableDomAttrTableCell
			}
		},
		
		//实现渲染接口
		render:function(config){
			if(config && config.renderLazy) return;
			var id=sysPrintUtil.generateID();
			var tableHTML='<table printcontrol="true" fd_type="table" align="center" class="tb_normal" style="width: 98%;" id="'+id+'"><tbody>';
			if(!config)
				config={};
			if(!config.rows) 
				config.rows=4;
			if(!config.cols)
				config.cols=4;
			for(var i=0;i<config.rows;i++){
				tableHTML+='<tr>';
				for(var j=0;j<config.cols;j++){
					if(j==0){
						tableHTML+='<td row="'+i+'" column="'+j+'" class="td_normal_title"  width="15%">&nbsp;</td>';
					}
					if(j==1){
						tableHTML+='<td row="'+i+'" column="'+j+'"  width="35%">&nbsp;</td>';
					}
					if(j==2){
						tableHTML+='<td row="'+i+'" column="'+j+'" class="td_normal_title"  width="15%">&nbsp;</td>';
					}
					if(j==3){
						tableHTML+='<td row="'+i+'" column="'+j+'"  width="35%">&nbsp;</td>';
					}
				}
				tableHTML+='</tr>';	
			}
			tableHTML+='</tbody></table>';
			this.$domElement=$(tableHTML);
			
			//记录列集合
			var self = this;
			setTimeout(function(){
				self.onInitialize();
			},1)
			
		},
		//实现事件接口
		bind:function(){
			var self=this;
			
			this.addListener('mouseup',function(event){
				self.builder.onDrawMouseUp(event);
				
				//结束宽度调整 
				if(self.dash.isMouseDown){
					self.dash.isMouseDown = false;
					$(self.dash.dashDom).addClass('sysprint_cursor_d').removeClass('sysprint_cursor_e sysprint_cursor_n');
					$(self.dash.dashDom).css("cursor","");
					self.dash.oldCellsWidthObj=null;
				}
			});
			//移动事件
			this.addListener('mousemove',function(event){
				if(self.builder.owner.toolBarAction){
					return;
				}
				self._mousemove(event,self);
			});
			
			//鼠标点击
			this.addListener('mousedown',function(event){
				var button = sysPrintUtil.eventButton(event);
				if(button==1){//左键
					self._onSelectControl();
					
					if(self instanceof sysPrintDesignerTableControl){
						//拖拽
						self._dashMouseDown(event);
						//debugger;
						if(self.dash.actionType){
							return;
						}
						if(event.ctrlKey==true){
							//多选
							var $curObj = $(event.target);
							var curSelDoms = self.builder.$selectDomArr;
							if(curSelDoms && curSelDoms.length>0){
								for(var i in curSelDoms){
									var isExist = false;
									if(curSelDoms[i][0]===$curObj[0]){
										isExist = true;
										$curObj.removeClass('table_select');
										self.builder.delSelDom($curObj);
										return ;
									}
								}
								
								if(!isExist){
									self.builder.addSelDom($curObj);
									$curObj.addClass("table_select");
								}
								
							}else{
								self.builder.addSelDom($curObj);
								$curObj.addClass("table_select");
							}
							
						}else{
							self.builder.clearSeclectedCtrl();
							//设置新对象
							self.$selectDom=$(event.target);
							if(self.$selectDom){
								self.builder.setSelectDom(self.$selectDom);
								self.$selectDom.addClass("table_select");
								self.builder.selectControl=self;
							}
						}
					}
				}else if(button==2){
					
				}
				
			});

			//添加放置结束事件
			this.addListener('dropStop',function(event,ui){
				if(self instanceof sysPrintDesignerTableControl){
					//分页符拖拽时不允许添加到table首行
					if(ui.draggable.attr('fd_type')=='page'){
						var ctrl = sysPrintUtil.getPrintDesignElement(event.target);
						var p = $(ctrl).parent();
						if(p[0].id=='sys_print_designer_draw'){
							var rows = $(event.target).attr('row').split(',');
							if(rows[0]=='0') return;
						}
					}
					if(event.target.innerHTML=='&nbsp;'){
						$(event.target).empty();
					}
					$(event.target).append(ui.draggable);
					//brcontrol控件拖拽
					if(ui.draggable.attr('fd_type')=='brcontrol'){
						var id = "brcontrol-"+ui.draggable.attr('id');
						$(event.target).append($('#'+id));
					}
					ui.draggable.css({
						position:'static',left:'0px',top:'0px'
					});
					setTimeout(function(){
						ui.draggable.css({
							position:'relative',left:'0px',top:'0px'
						});
					},350)
					
				}
			});
			
			this.$domElement.dblclick(function(event){
				var target = event.target;
				var tagName = target.tagName.toLowerCase();
				if(tagName!='td'){
					return;
				}
	 			//设置当前ctrl数据
	 			var ctrl = self.builder.selectControl;
	 			ctrl.options.showDomAttr=true;
	 			self.builder.attrPanelShow();
	 		});
		}
		
		
	});
	 //记录列
	 function onInitialize() {
		var table = this.$domElement[0], column = [];
		var rowCount = table.rows.length, colCount = 0, row, cell, cellColumn;
		//计算列总数
		if(!table.rows[0])
			return;
		row = table.rows[0], colCount = row.cells.length;
		
		for (var i = 0; i < colCount; i++) {
			cell = row.cells[i];
			rowCount = column.length;
			cellColumn = cell.getAttribute('column').split(',');
			for (var j = 0; j < cellColumn.length; j++)
				column.push([]);
			column[rowCount].push(cell);
			
			var _width = $(cell).attr('width');
			if(_width && _width.indexOf('%')>-1){
				if(_width=='15%'){
					var offsetWidth = cell.offsetWidth==0 ? 207:cell.offsetWidth-3;
					cell.setAttribute('width', offsetWidth);
				}else{
					var offsetWidth = cell.offsetWidth==0 ? 482:(cell.offsetWidth-(sysPrintUtil.UserAgent()=='msie'? 6:7));
					cell.setAttribute('width', offsetWidth);
				}
			}
			
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
				var _width = $(cell).attr('width');
				if(_width && _width.indexOf('%')>-1){
					if(_width=='15%'){
						var offsetWidth = cell.offsetWidth==0 ? 207:cell.offsetWidth-3;
						cell.setAttribute('width', offsetWidth);
					}else{
						var offsetWidth = cell.offsetWidth==0 ? 482:(cell.offsetWidth-(sysPrintUtil.UserAgent()=='msie'? 6:7));
						cell.setAttribute('width', offsetWidth);
					}
				}
			}
		}
		//记录列对象集
		this.options.column = column;
	}
	 
	 //删除表格行
	 function deleteRow(){
		 if (!confirm("确认删除行及行中包含的控件？")) {
			return;
		}
		var isInSameColumn = false;
		var selectedDomElement = this.builder.$selectDomArr;
		if (selectedDomElement.length < 1) {
			alert("请选中一个单元格！"); return;
		} else if (!this._isInSameRow(selectedDomElement) 
				&& !(isInSameColumn = this._isInSameColumn(selectedDomElement))) {
			alert("请选中同一行或同一列单元格！"); return;
		}
		//是否跨列
		var isSpanCell = selectedDomElement[0][0].rowSpan > 1;
		//获得单元格相关信息
		var table = this.$domElement[0], currCell = selectedDomElement[0][0], row, cell;
		var rowAttr = currCell.getAttribute('row').split(','), rowIndex = parseInt(rowAttr[0]);
		//开始到当前单元格所在行，若发现包含当前行信息的单元格则更新rowspan
		for (var i = 0; i < rowIndex; i++) {
			row = table.rows[i];
			for (var j = row.cells.length - 1; j >= 0; j--) {
				cell = row.cells[j];
				rowAttr = cell.getAttribute('row').split(',');
				if (parseInt(rowAttr[rowAttr.length - 1]) >= rowIndex) {
					rowAttr.pop();
					if (rowAttr.length == 1)
						cell.removeAttribute('rowSpan');
					else
						cell.setAttribute('rowSpan', '' + rowAttr.length);
					cell.setAttribute('row', rowAttr.join(','));
				}
			}
		}
		//当前行若有列合并单元格，则删除当前行的内容，保留其他合并内容
		var remains = [], insertIndex = -1, deleteControls = [];
		row = table.rows[rowIndex];
		for (var i = row.cells.length - 1; i >= 0; i--) {
			cell = row.cells[i];
			//删除包含的控件
			$(cell).empty();

			rowAttr = cell.getAttribute('row').split(',');
			if (rowAttr.length > 1) {
				rowAttr.pop();
				if (rowAttr.length == 1)
					cell.removeAttribute('rowSpan');
				else
					cell.setAttribute('rowSpan', '' + rowAttr.length);
				cell.setAttribute('row', rowAttr.join(','));
				remains.push(cell);
			}
		}
		//调整删除行后面的所有单元格的坐标属性
		this._resetCoordin(rowIndex + 1, -1, true, -1);
		//寻找下一行的位置，然后插入当前行的列合并单元格
		row = table.rows[rowIndex + 1];
		for (var i = remains.length - 1; i >= 0; i--) {
			rowAttr = remains[i].getAttribute('column').split(',');
			//寻找插入点
			insertIndex = this._getPosByRow(row, parseInt(rowAttr[0]), 'column');
			//若没找到，则认为是行尾
			if (insertIndex == -1)
				row.appendChild(remains[i]);
			else
				row.insertBefore(remains[i], row.cells[insertIndex]);
		}
		//从列集合中删除当前单元格
		row = table.rows[rowIndex];
		var columns = this.options.column;
		for (var i = 0; i < row.cells.length; i ++) {
			var cell = row.cells[i];
			var col = columns[cell.getAttribute('column').split(',')[0]];
			for (var j = 0; j < col.length; j ++) {
				if (cell === col[j]) {
					col.splice(j, 1);
					break;
				}
			}
		}
		//删除当前行
		table.deleteRow(rowIndex);
		//清空选中
		if (!isSpanCell && !isInSameColumn){ 
			this.builder.$selectDomArr = [];
			this.builder.$selectDom=null;
		}
	 }
	 
	 //插入行
	 function insertRow(){
		if(this.builder.$selectDomArr.length != 1){
			 alert('请选中一个单元格');
			 return;
		}
		this._unbindEvent();
		//获得单元格相关信息
		var selectedDomElement = this.builder.$selectDomArr[0];
		var currCell = selectedDomElement[0], rows = currCell.getAttribute('row').split(',');
		var oldRows = currCell.parentNode.cells, cellCount = oldRows.length;
		var columns = this.options.column,oldCell, newCell, className, cloneColumns;
		//调整插入点后面的所有单元格的坐标属性
		this._resetCoordin(rows[0], -1, true);
		//找到有跨行合并的单元格
		this._dealRowSpanCells(currCell.parentNode, function(cell) {
			cell.rowSpan = cell.rowSpan + 1;
			var _rowNo = cell.getAttribute('row').split(',');
			_rowNo.push(parseInt(_rowNo[_rowNo.length - 1]) + 1);
			cell.setAttribute('row', _rowNo.join(','));
		});
		//克隆选中单元格所在的行
		var newRow = this.$domElement[0].insertRow(rows[0]);
		for (var i = 0; i < cellCount; i++) {
			oldCell = oldRows[i];
			//克隆单元格
			newCell = oldCell.cloneNode(false);
			if (oldCell.rowSpan > 1) {newCell.rowSpan = 1;}
			newRow.appendChild(newCell);
			//设置相关属性
			newCell.setAttribute('row', rows[0]);
			newCell.innerHTML = '&nbsp;';
			//修正className
			className = $(oldCell).hasClass('table_select');
			if (className) {
				$(newCell).removeClass('table_select');
			}
			 
			//添加到列数组中
			cloneColumns = oldCell.getAttribute('column').split(',');
			columns[cloneColumns[0]].push(newCell);
		}
		 //事件绑定
		 this._bindEvent();
	 }
	 //追加行
	 function appendRow(){
		 this._unbindEvent();
		 var table = this.$domElement[0];
		 var oldRow = table.rows[table.rows.length -1];
		 var oldCells = new Array();
		 for (var i = 0, l = oldRow.cells.length; i < l; i ++) {
			oldCells.push(oldRow.cells[i]);
		 }
		 var columns = this.options.column;
		 var rows = oldCells[0].getAttribute('row').split(',');
		 var cloneColumns = null;
		 var oldCell = null;
		 //找到有跨行合并的单元格
		 this._dealRowSpanCells(oldRow, function(cell) {
			oldCells.push(cell);
		 });
		 oldCells.sort(function(cell_1, cell_2) {
			var c1 = parseInt(cell_1.getAttribute('column').split(',')[0]);
			var c2 = parseInt(cell_2.getAttribute('column').split(',')[0]);
			return (c1 - c2);
		 });
		 var cellCount = oldCells.length; // 包含找到的合并单元格
		 var newRow = table.insertRow(-1);
		 for (var i = 0; i < cellCount; i++) {
			oldCell = oldCells[i];
			//克隆单元格
			newCell = oldCell.cloneNode(false);
			if (oldCell.rowSpan > 1) {newCell.rowSpan = 1;}
			newRow.appendChild(newCell);
			//设置相关属性
			newCell.setAttribute('row', '' + newRow.rowIndex);
			newCell.innerHTML = '&nbsp;';
			//修正className
			className = $(oldCell).hasClass('table_select');
			if (className) {
				$(newCell).removeClass('table_select');
			}
			//添加到列数组中
			cloneColumns = oldCell.getAttribute('column').split(',');
			columns[cloneColumns[0]].push(newCell);
		  }
		 //事件
		 this._bindEvent();
	 }
	 
	 
	 //删除表格列
	 function deleteCol(){
		if (!confirm(DesignerPrint_Lang.buttonsDeleteColConfirm)) {
			return;
		}
		var isInSameRow = false;
		var selectedDomElement = this.builder.$selectDomArr;
		if (selectedDomElement.length < 1) {
			alert(DesignerPrint_Lang.buttonsDeleteCellSelectAlert); 
			return;
		} else if (!this._isInSameColumn(selectedDomElement)
				&& !(isInSameRow = this._isInSameRow(selectedDomElement))) {
			alert(DesignerPrint_Lang.buttonsDeleteCellSelectSameRowAlert); 
			return;
		}
		//是否跨列
		var isSpanCell = selectedDomElement[0][0].colSpan > 1;
		//获得单元格相关信息
		var columns = this.options.column, currCell = selectedDomElement[0][0], column, cell;
		var columnAttr = currCell.getAttribute('column').split(','), columnIndex = parseInt(columnAttr[0]);
		//开始到当前单元格所在列，若发现包含当前列信息的单元格则更新colspan
		for (var i = 0; i < columnIndex; i++) {
			column = columns[i];
			for (var j = column.length - 1; j >= 0; j--) {
				cell = column[j];
				columnAttr = cell.getAttribute('column').split(',');
				if (parseInt(columnAttr[columnAttr.length - 1]) >= columnIndex) {
					columnAttr.pop();
					if (columnAttr.length == 1)
						cell.removeAttribute('colSpan');
					else
						cell.setAttribute('colSpan', '' + columnAttr.length);
					cell.setAttribute('column', columnAttr.join(','));
				}
			}
		}
		//调整删除列后面的所有单元格的坐标属性
		this._resetCoordin(columnIndex + 1, -1, false, -1);
		//当前行若有行合并单元格，则删除当前列的内容，保留其他合并内容
		var newColumn = [], deleteControls = [];
		column = columns[columnIndex];
		for (var i = column.length - 1; i >= 0; i--) {
			cell = column[i];
			//删除包含的控件
			$(cell).empty();
			
			columnAttr = cell.getAttribute('column').split(',');
			if (columnAttr.length > 1) {
				columnAttr.pop();
				if (columnAttr.length == 1)
					cell.removeAttribute('colSpan');
				else
					cell.setAttribute('colSpan', '' + columnAttr.length);
				cell.setAttribute('column', columnAttr.join(','));
				newColumn.push(cell);
			} else cell.parentNode.removeChild(cell);
		}
		//更新列数组
		column = columns[columnIndex + 1];
		if (column) {
			newColumn = newColumn.concat(column);
			columns.splice(columnIndex, 2, newColumn);
		} else {
			columns.splice(columnIndex, 1);
		}
		//清空选中
		if (!isSpanCell && !isInSameRow){
			this.builder.$selectDomArr=[];
			this.builder.$selectDom=null;
		}
	 }
	 
	 //插入列
	 function insertCol(currCell){
		if(this.builder.$selectDomArr.length != 1){
			alert(DesignerPrint_Lang.buttonsDeleteCellSelectAlert);
			return;
		}
		this._unbindEvent();
		//获得单元格相关信息
		var table = this.$domElement[0], count = table.rows.length, currCell = (currCell == null ? this.builder.$selectDomArr[0][0] : currCell);
		var columnAttr = currCell.getAttribute('column').split(','), colIndex = parseInt(columnAttr[0]);
		var columns = this.options.column, column = columns[colIndex], rows = [], newColumn = [];
		
		var row, cell, rowAttr, newCell;
		//调整插入点后面的所有单元格的坐标属性
		this._resetCoordin(colIndex, -1, false);
		//记录行
		for (var i = 0; i < count; i++) {
			rows.push(table.rows[i]);
		}
		//插入列，除了合并单元格横跨的情况
		for (var i = column.length - 1; i >= 0; i--) {
			rowAttr = column[i].getAttribute('row').split(',');
			row = table.rows[rowAttr[0]];
			for (var j = 0; j < row.cells.length; j++) {
				cell = row.cells[j];
				if (cell == column[i]) {
					newCell = row.insertCell(j);
					//设置坐标相关属性
					newCell.setAttribute('row', rowAttr[0]);
					newCell.setAttribute('column', '' + colIndex);
					newCell.innerHTML = '&nbsp;';
					//添加到列数组中
					newColumn.push(newCell);
					//从行数组中删除当前行记录
					sysPrintUtil.spliceArray(rows, row);
					break;
				}
			}
			//当前单元格是列合并单元格时
			for (var j = rowAttr.length - 1; j > 0; j--) {
				row = table.rows[rowAttr[j]];
				//插入单元格
				newCell = row.insertCell(this._getPosByRow(row, colIndex, 'column'));
				//设置坐标相关属性
				newCell.setAttribute('row', rowAttr[j]);
				newCell.setAttribute('column', '' + colIndex);
				newCell.innerHTML = '&nbsp;';
				//添加到列数组中
				newColumn.push(newCell);
				//从行数组中删除当前行记录
				sysPrintUtil.spliceArray(rows, row);
			}
		}
		columns.splice(colIndex, 0, newColumn);
		//更新合并单元格横跨的colspan和column属性
		var lastColumn = -1;
		for (var i = rows.length - 1; i >= 0; i--) {
			row = rows[i];
			for (var j = 0; j < row.cells.length; j++) {
				cell = row.cells[j];
				columnAttr = cell.getAttribute('column').split(',');
				lastColumn = parseInt(columnAttr[columnAttr.length - 1]);
				if ((parseInt(columnAttr[0]) <= colIndex) && (lastColumn >= colIndex)) {
					columnAttr.push('' + (lastColumn + 1));
					cell.setAttribute('colSpan', '' + (columnAttr.length));
					cell.setAttribute('column', columnAttr.join(','));
					break;
				}
			}
		}
		 //事件
		this._bindEvent();
	 }
	 //追加列
	 function appendCol(){
		this._unbindEvent();
		var table = this.$domElement[0];
		var rows = table.rows;
		var newColumn = [], columnSize = 0, row;
		row = rows[1];
		for (var i = 0; i < row.cells.length; i ++) {
			var cell = row.cells[i];
			columnSize += (cell.colSpan && cell.colSpan > 1 ? cell.colSpan : 1);
		}
		for (var i = rows.length - 1; i >= 0; i--) {
			row = rows[i];
			var newCell = row.insertCell(-1);
			newCell.setAttribute('row', '' + i);
			newCell.setAttribute('column', '' + columnSize);
			newCell.innerHTML = '&nbsp;';
			newColumn.push(newCell);
		}
		this.options.column.push(newColumn);

		 //事件
		this._bindEvent();
	 }
	 //拆分单元格
	 function splitCell(){
		var newCell, row, newColumns;
		if (this.builder.$selectDomArr.length > 1) {
			alert(DesignerPrint_Lang.buttonsUniteCellSelectAlert); 
			return;
		}
		this._unbindEvent();
		//获得单元格相关信息
		var cell = this.builder.$selectDomArr[0][0];
		if (cell.colSpan == null || cell.rowSpan == null) return;
		//记录需拆分行和列信息
		var rows = cell.getAttribute('row').split(',');
		var columns = cell.getAttribute('column').split(',');
		//创建相应分拆的单元格
		var table = this.$domElement[0];
		var columnCount = columns.length;
		for (var i = 1; i < columnCount; i++) {
			newCell = table.rows[rows[0]].insertCell(cell.cellIndex + i);
			newCell.setAttribute('row', rows[0]);
			newCell.setAttribute('column', columns[i]);
			newCell.innerHTML = '&nbsp;';
			this.options.column[columns[i]].push(newCell);
		}
		//当前单元格保留成一个无法拆分的单元格
		cell.removeAttribute('colSpan');
		cell.removeAttribute('rowSpan');
		cell.setAttribute('row', rows[0]);
		cell.setAttribute('column', columns[0]);
		//与拆分单元格不同行的其他位置
		var prevColCount = parseInt(columns[0]);
		var rowCount = rows.length, insertIndex = 0;
		for (var i = 1; i < rowCount; i++) {
			row = table.rows[rows[i]];
			if (prevColCount != 0) insertIndex = this._getPosByRow(row, prevColCount, 'column');
			//若没找到插入点，则认为是行尾
			insertIndex = (insertIndex == -1) ? row.cells.length : insertIndex;
			for (var j = 0; j < columnCount; j++) {
				cell = row.insertCell(insertIndex + j);
				cell.setAttribute('row', rows[i]);
				cell.setAttribute('column', columns[j]);
				cell.innerHTML = '&nbsp;';
				this.options.column[columns[j]].push(cell);
			}
		}
		//
		this._bindEvent();
	 }
	 
	 //合并单元格
	 function uniteCell(){
		var selectedDomElement = this.builder.$selectDomArr;
	 	var rowMin = rowMax = columnMin = columnMax = -1, total = 0;
		var colspan = rowspan = 0, cell, rows, columns, cOwner, children, childCount;
		for (var i = selectedDomElement.length - 1; i >= 0; i--) {
			cell = selectedDomElement[i][0];
			rows = cell.getAttribute('row').split(',');
			columns = cell.getAttribute('column').split(',');
			//行的最小值和最大值
			rowMax = Math.max(rowMax, parseInt(rows[rows.length - 1]));
			rowMin = (rowMin == -1) ? parseInt(rows[0]) : Math.min(rowMin, parseInt(rows[0]));
			//列的最小值和最大值
			columnMax = Math.max(columnMax, parseInt(columns[columns.length - 1]));
			columnMin = (columnMin == -1) ? parseInt(columns[0]) : Math.min(columnMin, parseInt(columns[0]));
			//记录最小位置的单元格
			if (rowMin == parseInt(rows[0]) && columnMin == parseInt(columns[0])) cOwner = cell;
			//记录占用单元格数量
			total += (rows.length * columns.length);
		}
		//校验选中的单元格是否能合并(必须挨在一起成矩形)
		rowspan = rowMax - rowMin + 1;
		colspan = columnMax - columnMin + 1;
		if (colspan * rowspan != total) return;
		//合并
		if (colspan > 1) {
			cOwner.setAttribute('colSpan', '' + colspan);
			cOwner.removeAttribute('width');
			cOwner.style.width = "auto";
		}
		if (rowspan > 1) {
			cOwner.setAttribute('rowSpan', '' + rowspan);
			cOwner.removeAttribute('height');
			cOwner.style.height = "auto";
		}
		//删除其他单元格
		for (var i = selectedDomElement.length - 1; i >= 0; i--) {
			cell = selectedDomElement[i][0];
			if (cell && cell != cOwner) {
				children = cell.childNodes;
				for (var j = children.length - 1; j >= 0; j--) {
					if (children[j].nodeType != 3) cOwner.appendChild(children[j]);
				}
				//从列数组中删除
				columns = cell.getAttribute('column').split(',');
				//Designer.spliceArray(this.options.column[columns[0]], cell);
				//删除单元格
				cell.parentNode.removeChild(cell);
			}
		}
		//更新属性
		rows = []; columns = [];
		for (var i = rowMin; i <= rowMax; i++) {
			rows.push('' + i);
		}
		for (var i = columnMin; i <= columnMax; i++) {
			columns.push('' + i);
		}
		cOwner.setAttribute('row', rows.join(','));
		cOwner.setAttribute('column', columns.join(','));
		//更新选中元素
		this.builder.$selectDomArr = [$(cOwner)];
	 }
	 
	 //返回最大列数
	 function ___getMaxColumns(tableDom){
		 var size=0;
		 if($(tableDom).find('tr').size()>0){
			 var trDom=$(tableDom).find('tr').eq(0);
			 $(trDom).find('td').each(function(){
				 size+=1;
				 if($(this).prop('colSpan')!=null&& $(this).prop('colSpan')!=''){
					 size+=parseInt($(this).prop('colSpan'))-1;
				 }
			 });
		 }
		 return size;
	 }
	 function _getColumnSize() {
		var cols = 0;
		var cells = this.$domElement[0].rows[0].cells;
		for (var i = 0, l = cells.length; i < l; i++) {   
			cols += cells[i].colSpan;
		}
		return cols;
	}
	 
	 //对齐方式
	 function setTextAlign(){
		 if(this.$selectDom==null){
			 return ;
		 }
		 
	 }
	 
	 
	 //
	 function _dealRowSpanCells(currRow, doFun) {
			var cellCount = currRow.cells.length;
			//找到有跨行合并的单元格
			var columnSize = this._getColumnSize();
			if (columnSize > cellCount) {
				//修复 多浏览器下合并行删除行问题
				var _row = $(currRow).prev()[0];
				var _currMaxColSize = cellCount;
				var _rowIndex = 2; // 从2开始
				while(_row != null) {
					if (_row.cells.length > _currMaxColSize) {
						for (var i = 0, l = _row.cells.length; i < l; i ++) {
							if (_row.cells[i].rowSpan >= _rowIndex) { // 跨行单元格
								doFun(_row.cells[i]);
							}
						}
					}
					if (_row.cells.length == columnSize) {
						break;
					}
					_currMaxColSize = _row.cells.length;
					_row = $(_row).prev()[0];
					_rowIndex ++;
				}
			}
	}
	//
	 /*函数功能：更新指定行或列的坐标属性 参数：index>开始行(列)数 endIndex>结束行(列)数 isRow>行或列 offset>调整偏移*/
	 function _resetCoordin(startIndex, endIndex, isRow, offset) {
	 	var _startIndex = startIndex || 0, _endIndex = endIndex || -1, _offset = offset || 1;
	 	if (isRow) {
	 		var table = this.$domElement[0], rows, rowAttr;
	 		_endIndex = (_endIndex == -1 || _endIndex > table.rows.length) ? table.rows.length : (_endIndex + 1);
	 		for (var i = _startIndex; i < _endIndex; i++) {
	 			rows = table.rows[i];
	 			for (var j = rows.cells.length - 1; j >= 0; j--) {
	 				rowAttr = rows.cells[j].getAttribute('row').split(',');
	 				for (var k = rowAttr.length - 1; k >= 0; k--) {
	 					rowAttr[k] = parseInt(rowAttr[k]) + _offset;
	 				}
	 				rows.cells[j].setAttribute('row', rowAttr.join(','));
	 			}
	 		}
	 	} else {
	 		var columns = this.options.column, column, cell, columnAttr;
	 		_endIndex = (_endIndex == -1 || _endIndex > columns.length) ? columns.length : (_endIndex + 1);
	 		for (var i = _startIndex; i < _endIndex; i++) {
	 			column = columns[i];
	 			for (var j = column.length - 1; j >= 0; j--) {
	 				cell = column[j];
	 				columnAttr = cell.getAttribute('column').split(',');
	 				for (var k = columnAttr.length - 1; k >= 0; k--) {
	 					columnAttr[k] = parseInt(columnAttr[k]) + _offset;
	 				}
	 				cell.setAttribute('column', columnAttr.join(','));
	 			}
	 		}
	 	}
	 }
	 
	 function _isInSameRow(cells) {
		if (cells.length < 2) return true;
		var rowIndex = cells[0][0].parentNode.rowIndex;
		for (var i = 1, l = cells.length; i < l; i ++) {
			if (rowIndex != cells[i][0].parentNode.rowIndex) {
				return false;
			}
		}
		return true;
	}
	function _isInSameColumn(cells) {
		if (cells.length < 2) return true;
		var cellIndex = cells[0][0].cellIndex;
		for (var i = 1, l = cells.length; i < l; i ++) {
			if (cellIndex != cells[i][0].cellIndex) {
				return false;
			}
		}
		return true;
	}
	function _getPosByRow(row, colIndex, coordinateType) {
		var _colIndex = colIndex || 0, _cType = coordinateType || 'row';
		if (!row) return -1;

		var cells = row.cells, cellCount = cells.length, attrs;
		for (var i = 0; i < cellCount; i++) {
			attrs = cells[i].getAttribute(_cType).split(',');
			if (parseInt(attrs[0]) >= _colIndex) return i;
		}
		return -1;
	};
	function _bindEvent(){
//		 this.$domElement.find('td').unbind('mouseenter').unbind('mouseleave');
		 var self = this;
//		 self.$domElement.find('td').on({
//				mouseenter:function(){
//					self.$moveDom=$(this);
//				},
//				mouseleave:function(){
//					if($(this)==self.$moveDom){
//						self.$moveDom=null;
//					}
//				}
//			});

//		 self.$domElement.find('td').each(function(){
//				$(this).hover(function(event){
//					self.$moveDom=$(this);
//				},function(){
//					if($(this)==self.$moveDom){
//						self.$moveDom=null;
//					}
//				});
//			});
		 //2016.1.28 重新定义事件，防止ie5模式下事件不生效
		 this.$domElement.find('td').droppable({
				drop:function(event, ui){
					self.fireListener('dropStop',event,ui);
				},
				scope:'control'
			});
	}
	
	function _unbindEvent(){
		//清除droppableg事件，防止ie5模式下不生效
		this.$domElement.find('td').droppable('destroy');
	}
	
	function _dashMouseDown(eventObj){
		//设置拖拽信息
		var dashDom = this.dash.dashDom=eventObj.target;
		this.dash.isMouseDown = true;
		if (eventObj.offsetX > dashDom.offsetWidth  - 10) {
			//下个节点
			var $nextTd = $(dashDom).next();
			if($nextTd.length==0){//最后节点不允许拖拽
				return;
			}
			this.dash.actionType = "horizontal";
			dashDom.oldX = eventObj.clientX || eventObj.originalEvent.clientX; 
//			dashDom.oldWidth = $(dashDom).width();
//			this.dash.oldNextTdWidth = $nextTd.width();
			
			//保存此时的table对象数据
			this.dash.oldCellsWidthObj = this._getDashDomWidthDatas();
		}else if(eventObj.offsetY>dashDom.offsetHeight - 10){
			this.dash.actionType = "vertical";
			dashDom.oldY = eventObj.clientY || eventObj.originalEvent.clientY; 
			dashDom.oldHeight = dashDom.offsetHeight; 
		}else{
			this.dash.actionType = "";
		}
	}
	
	function _mousemove(event,self){
		var dashDom = self.dash.dashDom;
		var curObj = event.target;
		if(curObj.nodeName.toUpperCase()!='TD'){
			return;
		}
		//明细表无此操作
		var ctrlDom = sysPrintUtil.getPrintDesignElement(curObj);
		if(ctrlDom && $(ctrlDom).attr('fd_type')=='detailsTable'){
			return;
		}
		if(!self.dash.isMouseDown){
			if (event.offsetX > curObj.offsetWidth-5){
				if($(curObj).next().length==0){//最后节点不允许拖拽
					return;
				}
				$(curObj).addClass('sysprint_cursor_e').removeClass('sysprint_cursor_d sysprint_cursor_n'); 
				$(curObj).css("cursor","");
				self.dash.actionType = "horizontal"; 
			}else if(event.offsetY >curObj.offsetHeight-5){
				$(curObj).addClass('sysprint_cursor_n').removeClass('sysprint_cursor_e sysprint_cursor_d'); 
				$(curObj).css("cursor","");
				self.dash.actionType = "vertical";
			}else{ 
				$(curObj).addClass('sysprint_cursor_d').removeClass('sysprint_cursor_e sysprint_cursor_n');
				$(curObj).css("cursor","");
				self.dash.actionType = "";
			}
			return;
		}

		//取出暂存的Table Cell 
		if (!dashDom){
			if(window.console)
				console.log("tTD is null"); 
			return;
		}
		//调整宽度 
		if (self.dash.isMouseDown) {
				if(self.dash.actionType=='horizontal'){
					var distanceX = (event.clientX ||event.originalEvent.clientX) - dashDom.oldX;
					if(Math.abs(distanceX) <=2){
						//认为拖动>2时有效
						return;
					}
					$(curObj).addClass('sysprint_cursor_e').removeClass('sysprint_cursor_d sysprint_cursor_n');
					//当前dashdom单元格的cellIndex
					var dashDomIndex = self._getDashDomCellIndex();
					if (true){ 
			    		var table = self.$domElement[0];
			    		if(ctrlDom !=table){
			    			//嵌套表格时
			    			return;
			    		}
						for (var j = 0; j < table.rows.length; j++) {
							var cells = table.rows[j].cells;
							if(dashDomIndex >cells.length-1){
								continue;
							}
							//计算每个cell的宽度
							var oldRowCellWidthObj = self._getOldRowCellWidthObj(j);
							var newWidth = oldRowCellWidthObj.curCellWidth + distanceX;
							//下个cell的宽度
							var oldNextCellWidth = oldRowCellWidthObj.nextCellWidth;
							var nextCellWidth1 = oldNextCellWidth -distanceX;
							var nextCellWidth = oldRowCellWidthObj.curnextWidth -newWidth;
						//	window.console.log("newWidth:" + newWidth +";nextCellWidth:"+nextCellWidth);
							if(newWidth < 0 || nextCellWidth < 0){
								continue;
							}
							var _rowCurCell = this._getCellBycolumnIndex(cells,dashDomIndex);
							if(!_rowCurCell){
								continue;
							}
							var $_rowCurCell = $(_rowCurCell);
							if(parseInt(_rowCurCell.colSpan) > 1){
								_rowCurCell.style.width = 'auto';
								$(_rowCurCell).addClass('sysprint_cursor_e').removeClass('sysprint_cursor_d sysprint_cursor_n');
								$(_rowCurCell).css("cursor","");
								$(_rowCurCell).removeAttr('width');
								
							}else{
								_rowCurCell.style.width = newWidth +"px";
								$_rowCurCell.addClass('sysprint_cursor_e').removeClass('sysprint_cursor_d sysprint_cursor_n');
								$_rowCurCell.css("cursor","");
							}
							//window.console.log("aa:" + nextCellWidth1 +";nextCellWidth:"+nextCellWidth);
							//下个cell宽度调整
							var nextCellDom = this._getCellBycolumnIndex(cells,dashDomIndex+1);
							if(!nextCellDom){
								continue;
							}
							var $nextCell = $_rowCurCell.next();
							if(!$nextCell || $nextCell.length==0 || nextCellDom !=$nextCell[0]){
								continue;
							}
							
							if(parseInt(nextCellDom.colSpan) > 1){
							   nextCellDom.style.width='auto';
							   $nextCell.addClass('sysprint_cursor_e').removeClass('sysprint_cursor_d sysprint_cursor_n');
							   $nextCell.css("cursor","");
							   $nextCell.removeAttr('width');
							}else{
							   nextCellDom.style.width=nextCellWidth+"px";
							   $nextCell.addClass('sysprint_cursor_e').removeClass('sysprint_cursor_d sysprint_cursor_n');
							   $nextCell.css("cursor","");
							   $nextCell.removeAttr('width');
							}
						} 
			    	}else{
			    		if(window.console)
			    			console.log("_mousedown.... no horizontal"); 
			    	}
				}else if(self.dash.actionType=='vertical'){
					var distanceY = (event.clientY ||event.originalEvent.clientY) - dashDom.oldY;
					if($(curObj).parent().index()==self.$domElement[0].rows.length-1){
					}else{
						if(Math.abs(distanceY) <=2){
							//认为拖动>2时有效
							return;
						}
					}
					$(curObj).css("cursor","");
					$(curObj).addClass('sysprint_cursor_n').removeClass('sysprint_cursor_e sysprint_cursor_d');
					
					var newHeight = dashDom.oldHeight + ((event.clientY ||event.originalEvent.clientY) - dashDom.oldY);
					var row = dashDom.parentElement;
					for (var i = 0;i < row.cells.length;i++) {
						 var cell = row.cells[i];
						 cell.style.height=newHeight + 'px';
					}
				}else{
					if(window.console)
					console.log("no reset....");
				}
		}
	}
	
	
	function _getCellBycolumnIndex(cells,columnIndex){
		//debugger;
		for(var i = 0 ;i < cells.length;i++){
			var column = cells[i].getAttribute('column').split(',');
			for(var index in column){
				if(column[index]==columnIndex){
					return cells[i];
				}
			}
		}
		return null;
	}
	function _getOldRowCellWidthObj(rowIndex){
		var rowCells = this.dash.oldCellsWidthObj;
		var rowCellWidth = rowCells[rowIndex];
		return rowCellWidth;
	}
	//获取行对应位置单元格的宽
	function _getDashDomWidthDatas(){
		//debugger;
		var table = this.$domElement[0];
		var dashDom = this.dash.dashDom;
		//当前dashdom单元格的cellIndex
		var dashDomIndex = this._getDashDomCellIndex();
		var rowCells = [];
		for (var j = 0; j < table.rows.length; j++) {
			var cells = table.rows[j].cells;
			var _rowCurCell = this._getCellBycolumnIndex(cells,dashDomIndex);
			var $_rowCurCell = $(_rowCurCell);
			var $nextCell = $_rowCurCell.next();
			var rowCellWidth = {'curCellWidth':$_rowCurCell.outerWidth(),'nextCellWidth':$nextCell.outerWidth(),'curnextWidth':$_rowCurCell.outerWidth()+$nextCell.outerWidth()};
			rowCells.push(rowCellWidth);
		}
		//debugger;
		return rowCells;
	}
	
	function _getDashDomCellIndex(){
		//debugger;
		var dashDom = this.dash.dashDom;
		var dashDomIndex = dashDom.getAttribute('column');
		var _column = [];
		if(parseInt(dashDom.colSpan) > 1){
			_column = dashDom.getAttribute('column').split(",");
			dashDomIndex = parseInt(_column[_column.length-1]);
		}
		dashDomIndex = parseInt(dashDomIndex);
		return dashDomIndex;
	}
	function _onSelectControl() {
 		//恢复默认值
		var select = document.getElementById('_designer_font_style_');
		select.value = '';
		select = document.getElementById('_designer_font_size_');
		select.value = '';
	}
	
	 
	window.sysPrintDesignerTableControl=tableControl;
	
})(window);