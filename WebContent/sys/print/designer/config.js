sysPrintDesignerConfig={};

Com_IncludeFile("dialog.js");//依赖对话框
/**
 * 工具栏按钮配置
 */
sysPrintDesignerConfig.buttons={
		common : ['importTmp','deleteElem'],
		table : ['table','uniteCell','splitCell','insertRow','appendRow','deleteRow','insertCol','appendCol','deleteCol'],
		control : ['label','field','proccess','page','uploadimg'],
		font : ['bold', 'italic', 'underline', 'fontColor', 'fontStyle', 'fontSize', 'alignLeft', 'alignCenter', 'alignRight'],
		more: ['htmlEditor']
};



/**
 * 操作配置
 */
sysPrintDesignerConfig.operations={
	importTmp: {
		title : DesignerPrint_Lang.navImportXForm,
		run : SysPrint_Designer_OptionRun_ImportTmp,
	//	image:'toolbar_import', //自定义图标
		imgIndex : 51,
		type:'cmd'	
	},
	deleteElem: {
		imgIndex : 48,
		title : DesignerPrint_Lang.navDeleteElem,
		run : SysPrint_Designer_OptionRun_Delete,
		hotkey : 'delete',
		type:'cmd'
	},
	table : {
		imgIndex : 19,
		title : DesignerPrint_Lang.navInsertTable,
		run : function (designer) {
			designer.builder.createControl('table',designer);
		},
		type:'cmd'
	},
	insertRow:{
		imgIndex : 22,
		title:DesignerPrint_Lang.navInsertRow,
		run:function(designer){
			var selectControl=designer.builder.selectControl;
			var isSelected = designer.builder.isSelectedDom();
			if(isSelected && selectControl && selectControl.type=='table'){
				selectControl.insertRow();
			}else if(isSelected && selectControl && selectControl.type=='detailsTable'){
				alert(DesignerPrint_Lang.controlDetailsTable_noSuppotAlert);
			}else{
				alert(DesignerPrint_Lang.buttonsInsertRowAlert);
				designer.builder.$domElement.find('.table_select').removeClass("table_select");
			}
		},
		type:'cmd'
	},
	appendRow:{
		imgIndex : 23,
		title:DesignerPrint_Lang.navAppendRow,
		run:function(designer){
			var selectControl=designer.builder.selectControl;
			var isSelected = designer.builder.isSelectedDom();
			if(isSelected && selectControl && selectControl.type=='table'){
				selectControl.appendRow();
			}else if(isSelected && selectControl && selectControl.type=='detailsTable'){
				alert(DesignerPrint_Lang.controlDetailsTable_noSuppotAlert);
			}else{
				alert(DesignerPrint_Lang.buttonsAppendRowAlert);
				designer.builder.$domElement.find('.table_select').removeClass("table_select");
			}
		},
		type:'cmd'
	},
	deleteRow:{
		imgIndex : 24,
		title:DesignerPrint_Lang.navDeleteRow,
		run:function(designer){
			var selectControl=designer.builder.selectControl;
			var isSelected = designer.builder.isSelectedDom();
			if(isSelected && selectControl && selectControl.type=='table'){
				selectControl.deleteRow();
			}else if(isSelected && selectControl && selectControl.type=='detailsTable'){
				alert(DesignerPrint_Lang.controlDetailsTable_noSuppotAlert);
			}else{
				alert(DesignerPrint_Lang.buttonsDeleteRowAlert);
				designer.builder.$domElement.find('.table_select').removeClass("table_select");
			}
		},
		type:'cmd'
	},
	insertCol:{
		imgIndex : 25,
		title:DesignerPrint_Lang.navInsertCol,
		run:function(designer){
			var selectControl=designer.builder.selectControl;
			var isSelected = designer.builder.isSelectedDom();
			if(isSelected && selectControl && (selectControl.type=='table' || selectControl.type=='detailsTable')){
				selectControl.insertCol();
			}else{
				alert(DesignerPrint_Lang.buttonsInsertRowAlert);
				designer.builder.$domElement.find('.table_select').removeClass("table_select");
			}
		},
		type:'cmd'
	},
	appendCol:{
		imgIndex : 26,
		title:DesignerPrint_Lang.navAppendCol,
		run:function(designer){
			var selectControl=designer.builder.selectControl;
			var isSelected = designer.builder.isSelectedDom();
			if(isSelected && selectControl && (selectControl.type=='table'|| selectControl.type=='detailsTable')){
				selectControl.appendCol();
			}else{
				alert(DesignerPrint_Lang.buttonsInsertRowAlert);
				designer.builder.$domElement.find('.table_select').removeClass("table_select");
			}
		},
		type:'cmd'
	},
	deleteCol:{
		imgIndex : 27,
		title:DesignerPrint_Lang.navDeleteCol,
		run:function(designer){
			var selectControl=designer.builder.selectControl;
			var isSelected = designer.builder.isSelectedDom();
			if(isSelected && selectControl && (selectControl.type=='table' || selectControl.type=='detailsTable')){
				selectControl.deleteCol();
			}else{
				alert(DesignerPrint_Lang.buttonsDeleteCelAlert);
				designer.builder.$domElement.find('.table_select').removeClass("table_select");
			}
		},
		type:'cmd'
	},
	uniteCell:{
		imgIndex : 20,
		title:DesignerPrint_Lang.navUniteCell,
		run:function(designer){
			var selectControl=designer.builder.selectControl;
			var isSelected = designer.builder.isSelectedDom();
			if(isSelected && selectControl && selectControl.type=='table'){
				selectControl.uniteCell();
			}else if(isSelected && selectControl && selectControl.type=='detailsTable'){
				alert(DesignerPrint_Lang.controlDetailsTable_noSuppotAlert);
			}else{
				alert(DesignerPrint_Lang.controlTableSelectSplitCell);
				designer.builder.$domElement.find('.table_select').removeClass("table_select");
			}
		},
		type:'cmd'
	},
	splitCell:{
		imgIndex : 21,
		title:DesignerPrint_Lang.navSplitCell,
		run:function(designer){
			var selectControl=designer.builder.selectControl;
			var isSelected = designer.builder.isSelectedDom();
			if(isSelected && selectControl && selectControl.type=='table'){
				selectControl.splitCell();
			}else if(isSelected && selectControl && selectControl.type=='detailsTable'){
				alert(DesignerPrint_Lang.controlDetailsTable_noSuppotAlert);
			}else{
				alert(DesignerPrint_Lang.buttonsSplitCellAlert);
				designer.builder.$domElement.find('.table_select').removeClass("table_select");
			}
		},
		type:'cmd'
	},
	
	label : {
		imgIndex : 2,
		title : DesignerPrint_Lang.navInsertLabel,
		run : function (designer) {
			designer.toolBar.selectButton('label');
		},
		cursorImg: 'style/cursor/textLabel.cur',
		type:'cmd'
	},
	field : {
		imgIndex : 30,
		title : DesignerPrint_Lang.navInsertData,
		run : function (designer,obj){ 
			if(window.sysPrintFieldPanelInstance){
				window.sysPrintFieldPanelInstance.destroy();
				window.sysPrintFieldPanelInstance=null;
				return;
			}
			var ps = sysPrintUtil.absPosition(obj.domElement);
			var fp = new sysPrintFieldPanel();
			fp.open(designer,ps.x,ps.y + obj.domElement.offsetHeight);
			window.sysPrintFieldPanelInstance = designer.builder.sysPrintFieldPanelInstance;
		}
	},

	proccess:{
		imgIndex : 42,
		title:DesignerPrint_Lang.navInsertApprovalRecord,
		run:function(designer){
			designer.toolBar.selectButton('proccess');
		},
		cursorImg: 'style/cursor/auditshow.cur',
		type:'cmd'
	},
//	readlog:{
//		imgIndex : 47,
//		title:'插入阅读记录',
//		run:function(designer){
//			var proc=new sysPrintProcControl(designer.builder,'proccess');
//			proc.draw();
//		}
//	},
	page:{
		imgIndex : 32,
		title:DesignerPrint_Lang.navInsertPageBreak,
		run:function(designer){
			designer.toolBar.selectButton('page');
		},
		cursorImg: 'style/cursor/br.cur',
		type:'cmd'
	},
	uploadimg:{
		imgIndex : 33,
		title:DesignerPrint_Lang.navUploadImage,
		run:function(designer){
			designer.toolBar.selectButton('uploadimg');
		},
		cursorImg: 'style/cursor/uploadimg.cur',
		type:'cmd'
	},
	bold: {
		imgIndex : 7,
		icon_s:true,
		title : DesignerPrint_Lang.navBold,
		run : function (designer) {
			sysPrintFontUtil.setAttr(designer.builder,'bold');
		}
	},
	italic: {
		imgIndex : 8,
		icon_s:true,
		title : DesignerPrint_Lang.navItalic,
		run : function (designer) {
			sysPrintFontUtil.setAttr(designer.builder,'italic');
		}
	},
	underline: {
		imgIndex : 9,
		icon_s:true,
		title : DesignerPrint_Lang.navUnderline,
		run : function (designer) {
			sysPrintFontUtil.setAttr(designer.builder,'underline');
		}
	},
	fontColor : {
		imgIndex : 10,
		icon_s:true,
		title : DesignerPrint_Lang.navFontColor,
		run : function(designer,obj){
			if(printColorPanelInstance && printColorPanelInstance.domElement){
				printColorPanelInstance.toggle();
				return;
			}
			var ps = sysPrintUtil.absPosition(obj.domElement);
			printColorPanelInstance.open(
					'#000',
					function(value, designer) {
						sysPrintFontUtil.setAttr(designer.builder,'fontColor',value);
					},
					designer, 
					ps.x , ps.y + obj.domElement.offsetHeight
			);
		}
	},
	fontStyle : {
		imgIndex : 33,
		domWidth:'auto',
		title : DesignerPrint_Lang.navFontStyle,
		childElem : function(designer) {
			var rtn = document.createElement('div');
			var select = document.createElement('select');
			select.id = '_designer_font_style_';
			var fontStyle = sysPrintFontUtil.font.style;
			for (var i = 0, l = fontStyle.length; i < l; i ++) {
				select.add(new Option(fontStyle[i].text, fontStyle[i].value));
			}
			rtn.appendChild(select);
			rtn.style.padding = '2px';
			select.onchange = function() {
				sysPrintFontUtil.setAttr(designer.builder,'fontFamily',select.value);
			}
			return rtn;
		},
		onSelectControl : function(designer) {
			var select = document.getElementById('_designer_font_style_');
			if (designer.control && designer.control.getFontStyle) {
				var fontStyle = designer.control.getFontStyle();
				select.value = fontStyle ? fontStyle : '';
			} else {
				select.value = '';
			}
		}
	},
	fontSize : {
		imgIndex : 33,
		domWidth:'auto',
		title : DesignerPrint_Lang.navFontSize,
		childElem : function(designer) {
			var rtn = document.createElement('div');
			var select = document.createElement('select');
			select.id = '_designer_font_size_';
			var fontSize = sysPrintFontUtil.font.size;
			for (var i = 0, l = fontSize.length; i < l; i ++) {
				select.add(new Option(fontSize[i].text, fontSize[i].value));
			}
			rtn.appendChild(select);
			rtn.style.padding = '2px';
			select.onchange = function() {
				sysPrintFontUtil.setAttr(designer.builder,'fontSize',select.value);
			}
			return rtn;
		},
		onSelectControl : function(designer) {
			var select = document.getElementById('_designer_font_size_');
			if (designer.control && designer.control.getFontSize) {
				var fontSize = designer.control.getFontSize();
				select.value = fontSize ? fontSize : '';
			} else {
				select.value = '';
			}
		}
	},
	alignLeft : {
		imgIndex : 11,
		icon_s:true,
		title : DesignerPrint_Lang.navAlignLeft,
		run : function (designer) {
			var $selectDomArr = designer.builder.$selectDomArr;
			var selectControl = designer.builder.selectControl;
			if(!$selectDomArr || $selectDomArr.length==0){
				alert(DesignerPrint_Lang.buttonsCellAlignAlert);
				return;
			}
			if(selectControl instanceof sysPrintDesignerTableControl || selectControl instanceof sysPrintDetailsTableControl){
				$selectDomArr[0].css("text-align","left");
			}
		}
	},
	alignCenter : {
		imgIndex : 13,
		title : DesignerPrint_Lang.navAlignCenter,
		icon_s:true,
		run : function (designer) {
			var $selectDomArr = designer.builder.$selectDomArr;
			var selectControl = designer.builder.selectControl;
			if(!$selectDomArr || $selectDomArr.length==0){
				alert(DesignerPrint_Lang.buttonsCellAlignAlert);
				return;
			}
			if(selectControl instanceof sysPrintDesignerTableControl || selectControl instanceof sysPrintDetailsTableControl){
				$selectDomArr[0].css("text-align","center");
			}
		}
	},
	alignRight : {
		imgIndex : 12,
		title : DesignerPrint_Lang.navAlignRight,
		icon_s:true,
		run : function (designer) {
			var $selectDomArr = designer.builder.$selectDomArr;
			var selectControl = designer.builder.selectControl;
			if(!$selectDomArr || $selectDomArr.length==0){
				alert(DesignerPrint_Lang.buttonsCellAlignAlert);
				return;
			}
			if(selectControl instanceof sysPrintDesignerTableControl || selectControl instanceof sysPrintDetailsTableControl){
				$selectDomArr[0].css("text-align","right");
			}
		}
	},
	htmlEditor:{
		imgIndex : 44,
		title : DesignerPrint_Lang.navHTMLCode,
		run:function(designer){
		var domElement = document.createElement('div');
		domElement.className = 'print_srceditor';
		var $designPanel = $('#sysPrintdesignPanel');
		$designPanel.css('display','none');
		$designPanel.parent()[0].appendChild(domElement);
		var divHeight = $designPanel.outerHeight(false);
		with(domElement.style) {
			backgroundColor = '#FFF';
			height=divHeight +'px';
		}
		var $htmlbtn = $('<button class="btnopt">' + DesignerPrint_Lang.button_setHTML + '</button>');
		var $title = $('<button class="btnopt">' + DesignerPrint_Lang.panelCloseTitle +'</button>');
		var $titleBox = $('<div></div>')
		$titleBox.append(DesignerPrint_Lang.HTMLTemptSource).append($title).append($htmlbtn);
		$titleBox.css({'height':'20px','padding':'0px 8px'});
		domElement.appendChild($titleBox[0]);
		
		var $textarea = $('<textarea class="printscroll" style="width:100%;"></textarea>');
		$textarea.val(designer.builder.getHTML());
		$textarea.css('height',(divHeight-20-2)+'px');
		domElement.appendChild($textarea[0]);
		
		$title.on('click',function(){
			$designPanel.css('display','');
			$('.print_srceditor').remove();
		});
		$htmlbtn.on('click',function(){
			designer.builder.setHTML($textarea.val());
			$designPanel.css('display','');		
			$('.print_srceditor').remove();
		});
		}
	}
};

/**
 * 菜单
 */
sysPrintDesignerConfig.menus = {
	add : {
		title : DesignerPrint_Lang.menusAdd,
		type : 'menu',
		menu : {
			label : sysPrintDesignerConfig.operations.label,
			proccess : sysPrintDesignerConfig.operations.proccess,
			page:sysPrintDesignerConfig.operations.page
		}
	},
	line1 : {type:'line'},
//	attribute : Designer_Config.operations.attribute,
	deleteElem: sysPrintDesignerConfig.operations.deleteElem,

	table: {
		title : DesignerPrint_Lang.menusTable,
		type : 'menu',
		validate : function(designer) {
			return true;
//			return designer.control && designer.control.options.domElement&&designer.control.options.domElement.tagName == 'TABLE';
		},
		menu : {
			insertRow : sysPrintDesignerConfig.operations.insertRow,
			appendRow : sysPrintDesignerConfig.operations.appendRow,
			deleteRow: sysPrintDesignerConfig.operations.deleteRow,
			insertCol: sysPrintDesignerConfig.operations.insertCol,
			appendCol : sysPrintDesignerConfig.operations.appendCol,
			deleteCol : sysPrintDesignerConfig.operations.deleteCol,
			uniteCell: sysPrintDesignerConfig.operations.uniteCell,
			splitCell: sysPrintDesignerConfig.operations.splitCell
		}
	}
};

function SysPrint_Designer_OptionRun_Delete(designer){
	var selCtrl = designer.builder.selectControl;
	
	if(!selCtrl){
		alert(DesignerPrint_Lang.controlDeleteAlert);
		return;
	}
	if(selCtrl instanceof sysPrintDesignerTableControl){
		if(!confirm(DesignerPrint_Lang.controlDeleteTableAlert))
			return;
		
	}else if(selCtrl instanceof sysPrintDetailsTableControl){
		if(!confirm(DesignerPrint_Lang.controlDeleteDetailTableAlert))
			return;
		
	}else{
		if(!confirm(DesignerPrint_Lang.controlDeleteSelControlAlert))
			return;
	}
	
	var currentNode = selCtrl.$domElement[0];
	var parentNode = currentNode.parentNode;
	
	designer.builder.selectControl.remove();
	designer.builder.selectControl=null;
	designer.builder.clearSeclectedCtrl();
	
	//若控件原所在的单元格没有内容，则添加上空格(&nbsp;)
	if (parentNode && parentNode.innerHTML == '') 
		parentNode.innerHTML = '&nbsp;';
	
	//清除数据面板样式
	var delId = selCtrl.$domElement.attr('id');
	if(delId){
		if(selCtrl instanceof sysPrintDetailsTableControl){
			$('#sysPrintdesignPanel .field_panel_normal').each(function(){
				var $item = $(this);
				if($item.attr('name').indexOf(delId) > -1){
					$item.removeClass('field_panel_choosed');
				}
			});
		}else{
			var name = delId.replace('.','\\.');
			var $item = $('#sysPrintdesignPanel .panel_main_tree_box').find('div[name="' + name+'"]');
			$item.removeClass('field_panel_choosed');
		}
	}
    SysPrint_Designer_ReloadControlDivHtml(designer);
}

function SysPrint_Designer_ReloadControlDivHtml(designer) {
    if(IS_PRINT_SUB_TEMPLATE || PRINT_OPER_TYPE !='templateHistory' && (sysPrintButtons.getXFormMode(designer.fdKey)==PRINT_XFORM_TEMPLATE_SUBFORM||sysPrintButtons.getXFormMode(designer.fdKey)==PRINT_XFORM_TEMPLATE_DEFINE)){
        var height = $("#SubForm_Print_table").outerHeight(false)-5;
        $("#DIV_SubForm_Print").css("height",height);
        $("#SubPrintDiv").css("height",(height-20-37)*0.35);
        $("#SubPrintControlsDiv").css("height",(height-20-37)*0.65);
        //清空控件div中信息
        $("#SubPrintControlsDiv").html("");
        if(needLoad_subprint){
            SubPrint_Load();
        }
    }
}

function SysPrint_Designer_OptionRun_ImportTmp(designer){
	sysPrintButtons.importXFormTemp(designer,true);
    SysPrint_Designer_ReloadControlDivHtml(designer);
}