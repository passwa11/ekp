/**
 * 
 */
function Designer_Mobile_OpenAddRowPanel(designer, obj) {
	var mobileDesigner = MobileDesigner.instance;
	var designer = mobileDesigner.getMobileDesigner();
	if (typeof designer.builder.mobileDragBox === "undefined") {
		alert("请先选中行!");
		return;
	}
	var currentSelectedRow = designer.builder.mobileDragBox.attached;
	if (!currentSelectedRow || currentSelectedRow.length === 0) {
		alert("请先选中行!");
		return;
	}
	var mobileIFrame = getMobileIFrame();
	var ps = mobileIFrame.Designer.absPosition(obj.domElement);
	Designer = mobileIFrame.Designer;
	Designer_AttrPanel.addRowPanelInit();
	if (Designer_AttrPanel.addRowPanel.isClose) {
		Designer_AttrPanel.addRowPanel.open(
				function() {},
				designer.mobileDesigner, 
				ps.x ,
				ps.y
		);	
	} else {
		Designer_AttrPanel.addRowPanel .close();
	}
	
}

Designer_AttrPanel.addRowPanelInit = function() {
	if (Designer_AttrPanel.addRowPanel == null) {
		Designer_AttrPanel.addRowPanel = new Designer_AttrPanel.AddRow_Panel();
	}
}

Designer_AttrPanel.addRowPanelOpen = function(event) {
	var obj = event.target ? event.target : event.srcElement;
	var ps = Designer.absPosition(obj);
	Designer_AttrPanel.addRowPanelInit();
	Designer_AttrPanel.addRowPanelPanel.open(
			Designer_AttrPanel.autoFormatPanelCallBack,
			obj.previousSibling, ps.x , ps.y + obj.offsetHeight
	);
}
function Designer_AttrPanel_AddRowPanelClose() {
	if (Designer_AttrPanel.addRowPanel) {
		Designer_AttrPanel.addRowPanel.close();
	}
}
function Designer_AttrPanel_AddRowPanelSubmit() {
	if (Designer_AttrPanel.addRowPanel) {
		Designer_AttrPanel.addRowPanel.submit();
	}
}

Designer_AttrPanel.AddRow_Panel = function() {
	var self = this;
	self.domElement = document.createElement('div');
	$(self.domElement).addClass("addRow container")
	document.body.appendChild(self.domElement);
	//创建内容
	var html = "";
	html += "<table class='panel'>";
	html += '<tr class="panel_tr"><td><span class="panel_title">行数: </span></td>' + 
		'<td><input type="text" name="row" value="1"></td></tr>'+
		'<tr class="panel_tr"><td><span class="panel_title">列数: </span></td>' + 
		'<td><span class="radio"><label><input type="radio" name="column" value="1" checked>'+'一列'+'</input></label></span>' +
		'<span class="radio"><label><input type="radio" name="column" value="2">'+'两列'+'</input></label></span></td></tr>';
	html += '</table>';
	html += '<div class="btns" style="text-align:center;margin-top:5px">';
	html += '<a class="addRowBtn" href="javascript:void(0)" onclick="Designer_AttrPanel_AddRowPanelSubmit()">'+Data_GetResourceString("sys-xform-base:autoformat.button.ok")+'</a>';
	html += '<a class="addRowBtn" href="javascript:void(0)" onclick="Designer_AttrPanel_AddRowPanelClose()">'+Data_GetResourceString("sys-xform-base:autoformat.button.cancel")+'</a>';
	html += '</div>';
	$(self.domElement).html(html);
}

Designer_AttrPanel.AddRow_Panel.prototype = {
		isClose : true,
		callback : function(){},
		open: function(fn, arg, x, y) {
			var self = this;
			if(!self.isClose){
				return;
			}
			var width = $(self.domElement).width() + 40; //40为左右边距和
			// 定位
			var left = x - width + 'px';
			var top = y + 28 + 'px';
			$(self.domElement).css({'left':left,'top':top});
			$(self.domElement).css("display","inline-block");
			self.isClose = false;
			self.callback = fn;
			self._arg = arg;
		},
		getMaxColumnNum:function(table){
			var trs = $(table).children("tbody").children("tr");
			var obj = {};
			var maxNum = 0;
			var trObj;
			for(var i=0; i<trs.length; i++){
				var tr = trs[i];
				var tds = $(tr).children("td");
				if(tds.length > maxNum){
					maxNum = tds.length;
					trObj = tr;
				}
			}
			obj.maxNum = maxNum;
			obj.tr = trObj;
			return obj;
		},
		submit:function(){
			var obj = {};
			var rowNum = $("input[name='row']").val();
			obj.rowNum = rowNum;
			var columnNum = $("input[name='column']:checked").val();
			obj.columnNum = columnNum;
			//校验
			if(this.validate(obj)){
				this.addRow(obj);
				this.close();
			}
			
		},
		validate:function(obj){
			if(!obj.rowNum || obj.rowNum == '0'){
				alert('请输入正确的行数!');
				return false;
			}
			if(!obj.columnNum || obj.columnNum == '0'){
				alert('请输入正确的列数!');
				return false;
			}
			if(!/^[0-9]*[1-9][0-9]*$/.test(obj.rowNum) || !/^\d+$/.test(obj.columnNum)){
				 alert("行数和列数必须正整数!");
				return false
			}
			try{
				obj.rowNum = parseInt(obj.rowNum);
			}catch(e){
				alert("请输入正确的行数!");
				return false;
			}
			try{
				obj.columnNum = parseInt(obj.columnNum);
			}catch(e){
				alert("请输入正确的列数!");
				return false;
			}
			return true;
		},
		addRow : function(obj){
			Designer_Mobile_AddRow(obj);
		},
		close : function() {
			var self = this;
			$(self.domElement).hide();
			this.isClose = true;
		}
}

function Designer_Mobile_AddRow(obj){
	var mobileDesigner = MobileDesigner.instance;
	var designer = mobileDesigner.getMobileDesigner();
	var currentSelectedRow = designer.builder.mobileDragBox.attached;
	var currentRow;
	if (!currentSelectedRow || currentSelectedRow.length === 0) {
		alert("请先选中行!");
	}
	var tableControl = mobileDesigner.findTableControl(currentSelectedRow);
	if (tableControl == null) {
		alert("请选创建表格!");
		return;
	} else {
		var rowNum = obj.rowNum;
		var columnNum = obj.columnNum;
		designer.builder.resizeDashBox.hide();
		for (var i = 0; i < rowNum; i++) {
			var table = tableControl.options.domElement;
			var rowIndex = $($(currentSelectedRow).find("td")[0]).attr("row");
			//调整插入点后面的所有单元格的坐标属性
			tableControl._resetCoordin(rowIndex, -1, true);
			table.insertRow(rowIndex);
			currentRow = table.rows[parseInt(rowIndex)];
			$(currentRow).addClass("mobileTr");
			for (var j = 0; j < columnNum; j++) {
				cell = currentRow.insertCell(-1);
				cell.setAttribute('row', '' + rowIndex);            //记录行数(多值，以逗号分割，有严格顺序)
				cell.setAttribute('column', '' + j); 
				if(j == 0) {
					cell.setAttribute('class', 'muiTitle');
				}//记录列数(多值，以逗号分割，有严格顺序)
				if (columnNum === 1) {
					cell.setAttribute('colspan', '2');    
				}
				cell.innerHTML = '&nbsp;';
			}
			// 新增行时保证样式统一，对齐
			// $(currentRow).find(".muiTitle").removeClass("muiTitle");
			mobileDesigner.attach(currentRow);
		}
	}
	return currentRow;
}

function Designer_Mobile_RefreshRowIndex(table) {
	var rows = table.rows;
	for (var i = 0; i < rows.length; i++) {
		var row = rows[i];
		var cells = row.cells;
		for (var j = 0; j < cells.length; j++) {
			var cell = cells[j];
			cell.setAttribute('row', '' + i);           
		}
	}
}