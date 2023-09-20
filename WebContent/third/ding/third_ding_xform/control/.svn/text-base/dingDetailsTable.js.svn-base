/*******************************************************************************
 * 功能：钉钉定制的明细表，和标准明细表相比少了很多功能 描述：1.没有标题行和统计行 2.属性面板只有文字描述功能
 * 
 * 该控件仅开发做钉钉套件时方便使用，使用时需要把isShow设置为true
 * 
 ******************************************************************************/
// ======== 明细表 (开始)===========
(function(win) {
	Com_IncludeFile("dingDetailsTable.css", Com_Parameter.ContextPath + "third/ding/third_ding_xform/control/css/","css",true);
	win.Designer_Config.controls.dingDetailsTable = {
		type : "dingDetailsTable",
		inherit       : 'base',                                //继承对象(必须)
		container     : true,                                  //容器(必须)
		onDraw : _Designer_Control_DingDetailsTable_OnDraw,
		onDrawEnd : Ding_DrawEnd,
		drawXML : DingDetailsTable_DrawXML,
		
		onInitialize  : doInitialize,  //控件初始化事件
		onMouseOver   : _Designer_Control_Table_DoMouseOver,   //控件Over事件(参数：mousePosition>鼠标绝对位置)
		onColumnStart : _Designer_Control_Table_DoColumnStart, //开始调整列宽
		onColumnDoing : _Designer_Control_Table_DoColumnDoing,	//调整列宽相关
		onColumnEnd   : _Designer_Control_Table_DoColumnEnd,	//调整列宽相关
		colDistance   : _Designer_Control_Table_ColDistance,	//调整列宽相关
		cellDistance  : _Designer_Control_Table_CellDistance,	//调整列宽相关
		setColWidth   : _Designer_Control_Table_SetColumnWidth,	//调整列宽相关
		onDrag : _Designer_Control_StandardTable_OnDrag, // 拖拽
		onSelect      : _Designer_Control_Table_DoSelect,	// 选择单元格
		chooseCell    : _Designer_Control_Table_ChooseCell,    //选中单元格
		merge         : _Designer_Control_Table_Merge,         //合并单元格
		split         : _Designer_Control_Table_Split,         //拆分单元格
		onLock        : _Designer_Control_Table_DoLock,
		onUnLock      : _Designer_Control_Table_DoUnLock,
		getTagName : Ding_DetailsTable_GetTagName,	// 支持单元格插入
		insertRow     : Ding_InserRow,     //插入行
		appendRow     : Ding_AppendRow,     //追加行
		insertColumn  : _Designer_Control_Table_InsertColumn,  //插入列
		appendColumn  : _Designer_Control_Table_AppendColumn,  //追加列
		deleteRow     : _Designer_Control_Table_DeleteRow,     //删除行
		deleteColumn  : _Designer_Control_Table_DeleteColumn,  //删除列
		_getControlsByCell : _Designer_Control_Table_GetControlsByCell, //获得单元格包含的控件集
		_resetCoordin : _Designer_Control_Table_ResetCoordinate,//更新坐标位置
		_getPosByRow  : _Designer_Control_Table_GetPosByRow,   //获得所在行的位置，根据坐标
		_isInSameRow  : _Designer_Control_Table_IsInSameRow,   //判断选中的单元格是否在同一行里
		_isInSameColumn  : _Designer_Control_Table_IsInSameColumn, // 判断选中的单元格是否在同一列里
		getColumnSize : _Designer_Control_Table_GetColumnSize, //获得当前表格列数
		_dealRowSpanCells : _Designer_Control_Table_DealRowSpanCells, // 处理跨行单元格
		
		attrs : {
			label : Designer_Config.attrs.label
		},
		domAttrs : {
			td : {
				align : {
					text : Designer_Lang.controlStandardTableDomAttrTdAlign,
					value : "center",
					type : 'radio',
					opts : [
							{
								text : Designer_Lang.controlStandardTableDomAttrTdAlignLeft,
								value : "left"
							}, // left
							{
								text : Designer_Lang.controlStandardTableDomAttrTdAlignCenter,
								value : "center"
							},
							{
								text : Designer_Lang.controlStandardTableDomAttrTdAlignRight,
								value : "right"
							} ]
				},
				vAlign : {
					text : Designer_Lang.controlStandardTableDomAttrTdVAlign,
					value : "middle",
					type : 'radio',
					opts : [
							{
								text : Designer_Lang.controlStandardTableDomAttrTdVAlignTop,
								value : "top"
							},
							{
								text : Designer_Lang.controlStandardTableDomAttrTdVAlignMiddle,
								value : "middle"
							}, // middle
							{
								text : Designer_Lang.controlStandardTableDomAttrTdVAlignBottom,
								value : "bottom"
							} ]
				},
				style_width : {
					text : Designer_Lang.controlAttrWidth,
					value : "auto",
					type : 'text'
				},
				// 当form里面只有一个input
				// type=‘text’时，ie下默认按enter即提交内容，解决方案：1.设置keypress方法；2、增加多一个input
				// type=‘text’，设为隐藏，此处多做一个隐藏text
				hidden_text : {
					text : '',
					value : "",
					type : 'text',
					show : false
				}
			}
		},
		info : {
			name : "钉钉明细表",
			td : Designer_Lang.controlDetailsTable_info_td
		},
		resizeMode : 'no',
	};

	win.Designer_Config.operations['dingDetailsTable'] = {
		lab : "2",
		imgIndex : 15,
		title : "钉钉明细表",
		run : function(designer) {
			designer.toolBar.selectButton('dingDetailsTable');
		},
		type : 'cmd',
		order : 10,
		shortcut : 'R',
		select : true,
		cursorImg : 'style/cursor/detailsTable.cur',
		isAdvanced : false,
		isShow: function(){
			return false;
		}
	};
	win.Designer_Config.buttons.tool.push("dingDetailsTable");
	win.Designer_Menus.tool.menu['dingDetailsTable'] = Designer_Config.operations['dingDetailsTable'];
	
	function doInitialize(){
		this.options.tableElement = $(this.options.domElement).find("table.ding_detailstable_inner_table")[0];
		_Designer_Control_Table_DoInitialize.call(this);
		// 表格不需要ID
		$(this.options.tableElement).removeAttr("id");
	}

	/** **************** ↓↓↓↓↓↓↓↓画明细表表格↓↓↓↓↓↓↓↓ **************** */
	// 初始化绘制
	function _Designer_Control_DingDetailsTable_OnDraw(parentNode, childNode) {
		var domElement;
		this.options.domElement = document.createElement('div');
		domElement = this.options.domElement;
		this.options.values._label_bind = 'false'; // 不需要绑定
		if (!this.options.values.label) {
			this.options.values.label = this.info.name
					+ _Designer_Index_Object.label++;
		}
		domElement.setAttribute('label', this.options.values.label);

		if (this.options.values.id == null) {
			this.options.values.id = "fd_" + Designer.generateID();
		}
		domElement.setAttribute('id', this.options.values.id);
		domElement.setAttribute('formDesign', 'landray');
		domElement.setAttribute('align', 'center');
		domElement.className = 'tb_normal';
		domElement.style.width = '97%';
		if (this.parent) {
			domElement.setAttribute('tableName',
					_Get_Designer_Control_TableName(this.parent));
		}
		parentNode.appendChild(this.options.domElement);
		var tableContentHtml = "";
		tableContentHtml += getDetailsTableHtml(this.options.values.id);

		$(domElement).append(tableContentHtml);
	}

	function getDetailsTableHtml(tableId) {
		var html = "";
		var cellLength = 4;
		// "ding_detailstable_row"该class名不能更改，后台解析会使用它来定位模板行div
		// tr[type='templateRow']该属性目前仅用于designer.getObj方法
		html += "<div class='ding_detailstable_wrap'><table class='ding_detailstable_content' width='100%'><tr KMSS_IsReferRow='1' class='ding_detailstable_row'><td>"
				+ "<div class='ding_detailstable_desc'><span class='ding_detailstable_desc_txt'>明细</span><span class='ding_detailstable_del'>删除</span></div>"
				+ "<div class='ding_detailstable_table'>"
				+ "<table class='tb_normal ding_detailstable_inner_table' width='100%'><tbody><tr type='templateRow'>";
		for (var i = 0; i < cellLength; i++) {
			html += "<td row='0' column='" + i + "' ";
			if (i % 2 == 0) {
				//html += "class='td_normal_title'";
				html += " style='width:15%;'";
			} else {
				html += " style='width:35%;'";
			}
			html += ">&nbsp;</td>";
		}
		html += "</tr></tbody></table>" + "</div></td></tr></table>"
				+ "<div class='ding_detailstable_opt'><div class='ding_detailstable_add'>添加明细</div></div>" + "</div>";
		return html;
	}
	
	function Ding_DrawEnd(){
		$(this.options.domElement).attr("id", this.options.values.id);
		$(this.options.domElement).find(".ding_detailstable_content").attr("id","TABLE_DL_" + this.options.values.id);
		$(this.options.domElement).attr("label", this.options.values.label);
	}

	/** **************** ↑↑↑↑↑↑↑↑画明细表表格↑↑↑↑↑↑↑↑ **************** */

	/** **************** ↓↓↓↓↓↓↓↓行操作↓↓↓↓↓↓↓↓ **************** */
	function Ding_DetailsTable_GetTagName(){
		return "table";
	}
	
	// 插入行
	function Ding_InserRow(){
		var newRow = _Designer_Control_Table_InsertRow.call(this);
		$(newRow).attr("type","templateRow");
	}
	
	function Ding_AppendRow(){
		var newRow = _Designer_Control_Table_AppendRow.call(this);
		$(newRow).attr("type","templateRow");
	}
	/** **************** ↑↑↑↑↑↑↑↑行操作↑↑↑↑↑↑↑↑ **************** */

	// 数据字段生成
	function DingDetailsTable_DrawXML() {
		var values = this.options.values;
		var rowDom;
		var otherXml = [];
		var buf = [ '<extendSubTableProperty ' ];
		buf.push('name="', values.id, '" ');
		buf.push('label="', values.label, '" ');
		// 控件类型
		buf.push('businessType="', this.type, '" ');
		buf.push('>\r\n');
		buf.push('<idProperty><generator type="assigned" /></idProperty>\r\n');
		if (this.children.length > 0) {
			var xmls = [];
			this.children = this.children.sort(Designer.SortControl);
			for (var i = 0, l = this.children.length; i < l; i++) {
				var c = this.children[i];
				if (c.drawXML) {
					var xml = c.drawXML();
					if (xml != null) {
						rowDom = Designer_Control_DetailsTable_GetParentDom(
								'tr', c.options.domElement);
						if ($(rowDom).attr("type") == 'templateRow') {
							xmls.push(xml, '\r\n');
						}
					}
				}
			}
			buf.push(xmls.join(''));
		}
		buf.push('</extendSubTableProperty>\r\n');
		return buf.join('') + otherXml.join('');
	}

})(window);
// ======== 明细表 (结束)===========