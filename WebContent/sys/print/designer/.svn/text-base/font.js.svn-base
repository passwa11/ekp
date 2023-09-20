(function(window, undefined){
	/**
	 * 文字排版
	 */
	var fontUtil={};
    fontUtil.font={
    		style : [
    					{text:DesignerPrint_Lang.configFontSelect, value:''},
    					{text:DesignerPrint_Lang.configFontSongTi, value:DesignerPrint_Lang.configFontSongTi, style:'font-family:'+DesignerPrint_Lang.configFontSongTi},
    					{text:DesignerPrint_Lang.configFontXinSongTi, value:DesignerPrint_Lang.configFontXinSongTi, style: 'font-family:'+DesignerPrint_Lang.configFontXinSongTi},
    					{text:DesignerPrint_Lang.configFontKaiTi, value:DesignerPrint_Lang.configFontKaiTi, style: 'font-family:'+DesignerPrint_Lang.configFontKaiTi},
    					{text:DesignerPrint_Lang.configFontHeiTi, value:DesignerPrint_Lang.configFontHeiTi, style: 'font-family:'+DesignerPrint_Lang.configFontHeiTi},
    					{text:DesignerPrint_Lang.configFontYouYuan, value:DesignerPrint_Lang.configFontYouYuan, style: 'font-family:'+DesignerPrint_Lang.configFontYouYuan},
    					{text:DesignerPrint_Lang.configFontYaHei, value:DesignerPrint_Lang.configFontYaHei, style: 'font-family:'+DesignerPrint_Lang.configFontYaHei},
    					{text:DesignerPrint_Lang.configFontCourierNew, value:DesignerPrint_Lang.configFontCourierNew, style: 'font-family:\"'+DesignerPrint_Lang.configFontCourierNew+'\"'},
    					{text:DesignerPrint_Lang.configFontTimesNewRoman, value:DesignerPrint_Lang.configFontTimesNewRoman, style: 'font-family:\"'+DesignerPrint_Lang.configFontTimesNewRoman+'\"'},
    					{text:DesignerPrint_Lang.configFontTahoma, value:DesignerPrint_Lang.configFontTahoma, style: 'font-family:\"'+DesignerPrint_Lang.configFontTahoma+'\"'},
    					{text:DesignerPrint_Lang.configFontArial, value:DesignerPrint_Lang.configFontArial, style: 'font-family:\"'+DesignerPrint_Lang.configFontArial+'\"'},
    					{text:DesignerPrint_Lang.configFontVerdana, value:DesignerPrint_Lang.configFontVerdana, style: 'font-family:\"'+DesignerPrint_Lang.configFontVerdana+'\"'}
    				],
    		size : (function() {
				var ops = [];
				ops.push({text: DesignerPrint_Lang.configSizeSelect, value:''});
				for (var i = 9; i < 26; i ++) {
					ops.push({text: i + DesignerPrint_Lang.configSizePx, value: i + 'px'});
				}
				return ops;
			})()
    }; 
	 
	
	 //字体
	 fontUtil.setAttr = function(builder,type,value){
		 var $selectDom = builder.$selectDom;
		 var selectControl = builder.selectControl;
		 if(!$selectDom || !(selectControl instanceof sysPrintLabelControl)){
			 alert(DesignerPrint_Lang.buttonsTextSelectAlert);
			 return ;
		 }
		 selectControl.setAttr(builder,type,value);
	 }
	 
	 
	
	window.sysPrintFontUtil=fontUtil;//打印机制通用工具
	
})(window);