define(function(require, exports, module) {
	var $ = require('lui/jquery');
	var JsBarcode = require('lui/barcode/JsBarcode');
	
	var buildOptions = function(options) {
		var _options = {};
		$.extend(_options, {
			format:'CODE128'
		}, options);
		return _options;
	};
	
	function IEVersion() {
        var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
        var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器  
        var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器  
        var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
        if(isIE) {
            var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
            reIE.test(userAgent);
            var fIEVersion = parseFloat(RegExp["$1"]);
            if(fIEVersion == 7) {
                return 7;
            } else if(fIEVersion == 8) {
                return 8;
            } else if(fIEVersion == 9) {
                return 9;
            } else if(fIEVersion == 10) {
                return 10;
            } else {
                return 6;//IE版本<=7
            }   
        } else if(isEdge) {
            return 'edge';//edge
        } else if(isIE11) {
            return 11; //IE11  
        }else{
            return -1;//不是ie浏览器
        }
    }
	var ieVersion = IEVersion();
	/***************************************************************************
	 * options={
		  format: "CODE128",//选择要使用的条形码类型
		  width:3,//设置条之间的宽度
		  height:100,//高度
		  displayValue:true,//是否在条形码下方显示文字
		  text:"456",//覆盖显示的文本
		  fontOptions:"bold italic",//使文字加粗体或变斜体
		  font:"fantasy",//设置文本的字体
		  textAlign:"left",//设置文本的水平对齐方式
		  textPosition:"top",//设置文本的垂直位置
		  textMargin:5,//设置条形码和文本之间的间距
		  fontSize:15,//设置文本的大小
		  background:"#eee",//设置条形码的背景
		  lineColor:"#2196f3",//设置条和文本的颜色。
		  margin:15//设置条形码周围的空白边距
	  }
	 **************************************************************************/
	var Barcode = function(element,str,options) {
		if(ieVersion === -1 || ieVersion === 'edge' || ieVersion > 8) {
			options = buildOptions(options);
			JsBarcode(element,str,options);
		}
	}

	
	exports.Barcode = Barcode;
});