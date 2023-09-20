
Designer_Config.operations['excel']={
		imgIndex : 43,
		title:Designer_Lang.controlOperation_excel,
		run : function (designer) {
			window.form_impt_import();
		},
		type : 'cmd',
		order: 999,
		select: true,
		isAdvanced: true
};

Designer_Config.buttons.tool.push('excel');