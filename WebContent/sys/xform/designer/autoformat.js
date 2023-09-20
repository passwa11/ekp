/**********************************************************
功能：表单一键排版
使用：
	用于表单一键排版，包括最外层表格宽度，列宽度和控件宽度
作者：苏运彬
**********************************************************/
Designer_Config.operations['autoformat'] = {
	lab : "1",
	imgIndex : 23,
	title : Designer_Lang.autoFormat,//提示语
	icon_s:true,//小图标class
	order: 22,
	line_splits_end:false,//后分割线
	select: false,
	run : Designer_OptionRun_OpenAutoFormatPanel
};

function Designer_OptionRun_OpenAutoFormatPanel(designer, obj) {
	if (designer.control == null) {
		Designer_OptionRun_Alert(Designer_Lang.buttonsOpenFontColorAlert);
		return;
	}
	var ps = Designer.absPosition(obj.domElement);
	Designer_AttrPanel.autoFormatPanelInit();
	Designer_AttrPanel.autoFormatPanel.open(
			function() {},
			designer, 
			ps.x , ps.y + obj.domElement.offsetHeight
	);	
	
}

Designer_Config.buttons.head.push("autoformat");


/*一键排版面板*/
Designer_AttrPanel.autoFormatPanel = null;
Designer_AttrPanel.autoFormatPanelInit = function() {
	if (Designer_AttrPanel.autoFormatPanel == null) {
		Designer_AttrPanel.autoFormatPanel = new Designer_AttrPanel.AutoFormat_Panel();
	}
}
Designer_AttrPanel.autoFormatPanelOpen = function(event) {
	var obj = event.target ? event.target : event.srcElement;
	var ps = Designer.absPosition(obj);
	Designer_AttrPanel.autoFormatPanelInit();
	Designer_AttrPanel.autoFormatPanel.open(
			Designer_AttrPanel.autoFormatPanelCallBack,
			obj.previousSibling, ps.x , ps.y + obj.offsetHeight
	);
}
Designer_AttrPanel.autoFormatPanelClose = function() {
	if (Designer_AttrPanel.autoFormatPanel) {
		Designer_AttrPanel.autoFormatPanel.close();
	}
}
Designer_AttrPanel.autoFormatPanelSubmit = function() {
	if (Designer_AttrPanel.autoFormatPanel) {
		Designer_AttrPanel.autoFormatPanel.submit();
	}
}
Designer_AttrPanel.switchType = function(obj,name){
	var value = obj.value;
	if(value == 'fixed'){
		//固定值，出现输入框
		$("[name='"+name+"']").show();
	}else{
		$("[name='"+name+"']").hide();
	}
}
Designer_AttrPanel.autoFormatPanelCallBack = function() {

}
Designer_AttrPanel.AutoFormat_Panel = function() {
	var self = this;
	self.domElement = document.createElement('div');
	$(self.domElement).addClass("autoformat container")
//	$(self.domElement).css({
//		'position' : 'fixed',
//		'display' : 'none',
//		'z-index': '200',
//		'background-color': '#ECECEC',
//		'border': 'solid 1px #f0c49B',
//		'padding':'10px 20px'
//	})
	document.body.appendChild(self.domElement);
	//创建内容
	var html = "";
	html += "<table class='panel'>";
	html += '<tr class="panel_tr"><td><input type="checkbox" name="autoformat" value="cellWidth"></td>'+
		'<td><span>'+Data_GetResourceString("sys-xform-base:autoformat.setting.cell")+':</span></td><td><span class="default"><input type="radio" name="cellWidth" checked value="average" onclick="Designer_AttrPanel.switchType(this,\'cellWidthNum\')">'+Data_GetResourceString("sys-xform-base:autoformat.type.average")+'</input></span>'+
		'<span class="radio"><input type="radio" name="cellWidth" value="fixed" onclick="Designer_AttrPanel.switchType(this,\'cellWidthNum\')">'+Data_GetResourceString("sys-xform-base:autoformat.type.fixed")+'</input></span><input type="text" name="cellWidthNum" class="input-num"></td>'+
		'<td><a href="javascript:void(0)" class="tip" title="'+Data_GetResourceString("sys-xform-base:autoformat.cell.tip")+'">!</a></td></tr>';
	html += '<tr class="panel_tr"><td><input type="checkbox" name="autoformat" value="tableWidth"></td>'+
		'<td><span>'+Data_GetResourceString("sys-xform-base:autoformat.setting.table")+':</span></td><td><span class="default"><input type="radio" name="tableWidth" value="adapt" checked onclick="Designer_AttrPanel.switchType(this,\'tableWidthNum\')">'+Data_GetResourceString("sys-xform-base:autoformat.type.adapt")+'</input></span>'+
		'<span class="radio"><input type="radio" name="tableWidth" value="fixed" onclick="Designer_AttrPanel.switchType(this,\'tableWidthNum\')">'+Data_GetResourceString("sys-xform-base:autoformat.type.fixed")+'</input></span><input type="text" name="tableWidthNum" class="input-num"></td>'+
		'<td><a href="javascript:void(0)" class="tip" title="'+Data_GetResourceString("sys-xform-base:autoformat.table.tip")+'">!</a></td></tr>';
	html += '<tr class="panel_tr"><td><input type="checkbox" name="autoformat" value="controlWidth"></td>'+
		'<td><span>'+Data_GetResourceString("sys-xform-base:autoformat.setting.control")+':</span></td><td><span class="default"><input type="radio" name="controlWidth" value="adapt" checked onclick="Designer_AttrPanel.switchType(this,\'controlWidthNum\')">'+Data_GetResourceString("sys-xform-base:autoformat.type.adapt")+'</input></span>'+
		'<span class="radio"><input type="radio" name="controlWidth" value="fixed" onclick="Designer_AttrPanel.switchType(this,\'controlWidthNum\')">'+Data_GetResourceString("sys-xform-base:autoformat.type.fixed")+'</input></span><input type="text" name="controlWidthNum" class="input-num"/>'+
		'</td><td><a href="javascript:void(0)" class="tip" title="'+Data_GetResourceString("sys-xform-base:autoformat.control.tip")+'">!</a></td></tr>';
	html += '<tr class="panel_tr"><td><input type="checkbox" name="autoformat" value="rowHeight"></td>'+
		'<td><span>'+Data_GetResourceString("sys-xform-base:autoformat.setting.rowHeight")+':</span></td><td><span class="default"><input type="radio" name="rowHeight" value="adapt" checked onclick="Designer_AttrPanel.switchType(this,\'rowHeightNum\')">'+Data_GetResourceString("sys-xform-base:autoformat.type.adapt")+'</input></span>'+
		'<span class="radio"><input type="radio" name="rowHeight" value="fixed" onclick="Designer_AttrPanel.switchType(this,\'rowHeightNum\')">'+Data_GetResourceString("sys-xform-base:autoformat.type.fixed.row")+'</input></span><input type="text" name="rowHeightNum" class="input-num"/>'+
		'</td><td><a href="javascript:void(0)" class="tip" title="'+Data_GetResourceString("sys-xform-base:autoformat.rowHeight.tip")+'">!</a></td></tr>';
	html += '</table>';
	html += '<div class="btns" style="text-align:center;margin-top:10px">';
	html += '<a class="btn" href="javascript:void(0)" onclick="Designer_AttrPanel.autoFormatPanelSubmit()">'+Data_GetResourceString("sys-xform-base:autoformat.button.ok")+'</a>';
	html += '<a class="btn" href="javascript:void(0)" onclick="Designer_AttrPanel.autoFormatPanelClose()">'+Data_GetResourceString("sys-xform-base:autoformat.button.cancel")+'</a>';
	html += '</div>';
	$(self.domElement).html(html);
}
Designer_AttrPanel.AutoFormat_Panel.prototype = {
	isClose : true,
	callback : function(){},
	open: function(fn, arg, x, y) {
		var self = this;
		if(!self.isClose){
			return;
		}
		//修改默认值
		$("[name='cellWidth'][value='average']").attr("checked","checked");
		$("[name='cellWidth'][value='average']").prop("checked","checked");
		$("[name='cellWidthNum']").hide();
		$("[name='cellWidthNum']").val("");
		$("[name='tableWidth'][value='adapt']").attr("checked","checked");
		$("[name='tableWidth'][value='adapt']").prop("checked","checked");
		$("[name='tableWidthNum']").hide();
		$("[name='tableWidthNum']").val("");
		$("[name='controlWidth'][value='adapt']").attr("checked","checked");
		$("[name='controlWidth'][value='adapt']").prop("checked","checked");
		$("[name='controlWidthNum']").hide();
		$("[name='controlWidthNum']").val("");
		$("[name='rowHeight'][value='adapt']").attr("checked","checked");
		$("[name='rowHeight'][value='adapt']").prop("checked","checked");
		$("[name='rowHeightNum']").hide();
		$("[name='rowHeightNum']").val("");
		// 修正 x 轴超出问题
		var p_size = 20;
		var right_x_pos = x + (self.domElement.offsetWidth||436);
		if (right_x_pos > Designer.getDocumentAttr("offsetWidth") - p_size) {
			x = Designer.getDocumentAttr("offsetWidth") - (self.domElement.offsetWidth||436) - p_size;
		}
		// 定位
		var left = x ? (x+21.67+(self.domElement.offsetWidth||436)-Designer.getDocumentAttr("scrollLeft")>Designer.getDocumentAttr("clientWidth")?Designer.getDocumentAttr("clientWidth")-(self.domElement.offsetWidth||436)-21.67:x+21.67-Designer.getDocumentAttr("scrollLeft")) : 0 + 'px';
		var top = y ? (y+self.domElement.offsetHeight-Designer.getDocumentAttr("scrollTop")>Designer.getDocumentAttr("clientHeight")?Designer.getDocumentAttr("clientHeight")-self.domElement.offsetHeight:y-Designer.getDocumentAttr("scrollTop")) : 0 + 'px';
		$(self.domElement).css({'left':left,'top':top});
		$(self.domElement).show();
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
		var checkBoxs = [];
		$("input[name='autoformat']:checked").each(function(){
			var value = $(this).val();
			checkBoxs.push(value);
        });
		//校验
		if(this.validate(checkBoxs)){
			//设置宽度从大元素到小元素
			if(checkBoxs.indexOf('tableWidth') != -1){//处理表格宽
				this.setTableWidth(checkBoxs);
			}
			if(checkBoxs.indexOf('cellWidth') != -1){//处理列宽
				this.setCellWidth(checkBoxs);
			}
			if(checkBoxs.indexOf('controlWidth') != -1){//处理控件框
				this.setControlWidth(checkBoxs);
			}
			//设置行高
			if(checkBoxs.indexOf('rowHeight') != -1){//处理行高
				this.setRowHeight(checkBoxs);
			}
			this.close();
		}
		
	},
	validate:function(checkBoxs){
		var type = $("input[name='cellWidth']:checked").val();
		if(checkBoxs.indexOf('cellWidth') != -1 && type === 'fixed'){
			var widthNum = $("input[name='cellWidthNum']").val();
			var result = this.validateTip(widthNum,Data_GetResourceString("sys-xform-base:autoformat.validate.cell"));
			if(!result)
				return false;
		}
		type = $("input[name='tableWidth']:checked").val();
		if(checkBoxs.indexOf('tableWidth') != -1 && type === 'fixed'){//固定值
			var widthNum = $("input[name='tableWidthNum']").val();
			var result = this.validateTip(widthNum,Data_GetResourceString("sys-xform-base:autoformat.validate.table"));
			if(!result)
				return false;
		}
		
		type = $("input[name='controlWidth']:checked").val();
		if(checkBoxs.indexOf('controlWidth') != -1 && type === 'fixed'){//控件固定值
			var widthNum = $("input[name='controlWidthNum']").val();
			var result = this.validateTip(widthNum,Data_GetResourceString("sys-xform-base:autoformat.validate.control"));
			if(!result)
				return false;
		}

		//行高校验
		type = $("input[name='rowHeight']:checked").val();
		if(checkBoxs.indexOf('rowHeight') != -1 && type === 'fixed'){//控件固定值
			var rowHeightNum = $("input[name='rowHeightNum']").val();
			var result = this.validateTip(rowHeightNum,Data_GetResourceString("sys-xform-base:autoformat.validate.rowHeight"),true);
			if(!result)
				return false;
		}
		return true;
	},
	validateTip:function(num,tip,notPercen){
		try{
			if(!num || num == '0' || (notPercen && num.indexOf("%") != -1)){
				alert(tip);
				return false;
			}

			if(!notPercen && num.indexOf("%") != -1 && num.indexOf("%") == num.length -1){
				num = num.substring(0,num.indexOf("%"));
			}else if(num.indexOf("px") != -1 && num.indexOf("px") == num.length - 2){
				num = num.substring(0,num.indexOf("px"));
			}
			if(!(/^[1-9]\d*$/.test(num)) || isNaN(parseInt(num))){
				alert(tip);
				return false;
			}
		}catch(e){
			alert(tip);
			return false;
		}
		return true;
	},
	setCellWidth:function(checkBoxs){
		//列宽配置
		var type = $("input[name='cellWidth']:checked").val();
		var $designPanel = $("#designPanel");//表单领域
		var firstTable = $designPanel.find("[fd_type='standardTable']")[0];//主表
		var tableWidthNum = $(firstTable).width();
		var obj = this.getMaxColumnNum(firstTable);
		var tdNum = obj.maxNum;
		var trObj = obj.tr;
		if(type === 'average'){//均分
			if(tdNum != 0){
				var averWidth = tableWidthNum/tdNum;
				var remainder = tableWidthNum%tdNum;
				var trs = $(firstTable).children("tbody").children("tr");
				for(var i=0; i<trs.length; i++){
					var tr = trs[i];
					var tds = $(tr).children("td");
					if(tds.length == tdNum){
						for(var j=0; j<tds.length; j++){//最后一个列自动
							var td = tds[j];
							if(j == tds.length-1){
								$(td).attr("width",parseInt(averWidth+remainder)+"");
								$(td).css("width",parseInt(averWidth+remainder)+"px");
								$(td).attr("style_width",parseInt(averWidth+remainder));
							}else{
								$(td).attr("width",parseInt(averWidth)+"");
								$(td).css("width",averWidth+"px");
								$(td).attr("style_width",parseInt(averWidth));
							}
						}
					}
				}
			}
		}else{//固定值
			var widthNum = $("input[name='cellWidthNum']").val();
			var isPercen = false;
			if(widthNum.indexOf("%") != -1){
				//百分比
				isPercen = true;
			}else{
				widthNum = parseInt(widthNum);
			}
			if(tdNum != 0){//列宽一定设置，无论表格怎么样
				var trs = $(firstTable).children("tbody").children("tr");
				for(var i=0; i<trs.length; i++){
					var tr = trs[i];
					var tds = $(tr).children("td");
					if(tds.length == tdNum){
						//修改
						for(var j=0; j<tds.length; j++){//最后一个列自动
							var td = tds[j];
							if(!isPercen && j == tds.length-1 && tableWidthNum > widthNum * tdNum){
								widthNum = tableWidthNum - widthNum*(tdNum-1);
							}
							$(td).attr("width",widthNum+"");
							$(td).attr("style_width",widthNum);
							if(isPercen){
								$(td).css("width",widthNum);
							}else{
								$(td).css("width",widthNum+"px");
							}
						}
					}
				}
			}
		}
		return true;
	},
	setTableWidth:function(checkBoxs){
		//表格宽度配置
		var type = $("input[name='tableWidth']:checked").val();
		var $designPanel = $("#designPanel");//表单领域
		var firstTable = $designPanel.find("[fd_type='standardTable']")[0];//主表
		var obj = this.getMaxColumnNum(firstTable);
		var tdNum = obj.maxNum;
		var trObj = obj.tr;
		var isChange = true;
		var widthNum;
		if(type === 'adapt'){//自适应
			widthNum = "98%";
		}else{//固定值
			widthNum = $("input[name='tableWidthNum']").val();
			var isPercen = false;
			if(widthNum.indexOf("%") != -1){
				//百分比
				isPercen = true;
			}else{
				widthNum = parseInt(widthNum);
			}
			//比较表格和列宽
			if(checkBoxs.indexOf('cellWidth') != -1){
				var type = $("input[name='cellWidth']:checked").val();
				if(type === 'fixed'){
					if(isPercen){
						isChange = true;
					}else{
						var cellWidthNum = $("input[name='cellWidthNum']").val();
						if(cellWidthNum.indexOf("%") != -1){
							//列宽设置百分比，不需要进行比较
							isChange = true;
						}else{
							cellWidthNum = parseInt(cellWidthNum);
							if(widthNum > cellWidthNum * tdNum){//设置的表格宽度小于设置列的总宽度，不设置表格
								isChange = true;
							}else{
								isChange = false;
							}
						}
					}
				}
			}
		}
		if(isChange){
			//设置表格
			var table = Designer.instance.builder.getControlByDomElement(firstTable);
			table.options.values["width"] = widthNum;
			Designer.instance.builder.setProperty(table);
		}
		return true;
	},
	setControlWidth:function(checkBoxs){
		//获取所有的元素id
		var $designPanel = $("#designPanel");//表单领域
		var firstTable = $designPanel.find("[fd_type='standardTable']")[0];//主表
		//获取非表格控件
		var controlDoms = $(firstTable).find("[fd_type]");
		var newControlDoms = [];
		for(var i=0; i<controlDoms.length; i++){
			var controlDom = controlDoms[i];
			if($(controlDom).attr("fd_type") === "standardTable" || $(controlDom).attr("fd_type") === 'textLabel'){
				continue;//内部表格和label不处理
			}
			newControlDoms.push(controlDom);
		}
		//控件宽度配置
		var type = $("input[name='controlWidth']:checked").val();
		var widthNum;
		if(type === 'adapt'){//控件自适应
			widthNum = "98%";
		}else{//控件固定值
			widthNum = $("input[name='controlWidthNum']").val();
			if(widthNum.indexOf("%") == -1){
				widthNum = parseInt(widthNum);
			}
		}
		for(var j=0; j<newControlDoms.length; j++){
			var controlDom = newControlDoms[j];
			if(($(controlDom).attr("fd_type") === "voteNode" || $(controlDom).attr("fd_type") === "uploadimg" || $(controlDom).attr("fd_type") === "docimg") && (widthNum+"").indexOf("%") != -1){
				continue;
			}
			var fdVals = $(controlDom).attr("fd_values");
			fdVals = eval('('+fdVals+')');
			var control = Designer.instance.builder.getControlByDomElement($("#"+fdVals.id)[0]);
			control.options.values["width"] = widthNum;
			Designer.instance.builder.setProperty(control);
			Designer.instance.builder.serializeControl(control);
		}
		return true;
	},
	setRowHeight : function(checkBoxs){
		var $designPanel = $("#designPanel");//表单领域
		var firstTable = $designPanel.find("[fd_type='standardTable']")[0];//主表

		if(!firstTable)
			return false;

		var type = $("input[name='rowHeight']:checked").val();
		var rowHeightNum;
		if(type === 'adapt') {//自适应
			rowHeightNum = "auto";
		}else{//固定值
			rowHeightNum = $("input[name='rowHeightNum']").val();
			if(rowHeightNum.indexOf("px") == -1){
				rowHeightNum = rowHeightNum + "px";
			}
		}
		for(var i=0; i<firstTable.rows.length; i++){
			var row = firstTable.rows[i];
			$(row).attr("height",rowHeightNum);
			$(row).css("height",rowHeightNum);
		}
		return true;
	},
	close : function() {
		var self = this;
		$(self.domElement).hide();
		this.isClose = true;
	}
}