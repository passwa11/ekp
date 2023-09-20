// JavaScript Document
define(function(require, exports, module) {
	var dialog = require("lui/dialog");
	var $ = require("lui/jquery");
	var env = require("lui/util/env");
	var lang = require('lang!');
	var dom = require('sys/organization/resource/js/dom-to-image.js');
	var saver = require('sys/organization/resource/js/FileSaver.js');
	var jsPdf = require('lui/export/jsPdf.debug.js');

	
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
	
	// 是否为火狐浏览器
	function ISFirefox(){
		return navigator.userAgent.indexOf('Firefox')>=0;
	}
	
	// 是否为Safari浏览器
	function ISSafari(){
		return navigator.userAgent.indexOf("Safari")!=-1 && navigator.userAgent.indexOf("Version")!=-1;
	}
	
	var exportPdf = function(container,title) {
		logging("exportPdf begin");
		if(IEVersion() !== -1) {
			if(window.export_load!=null){
				window.export_load.hide(); 
			}
			dialog.alert(notsupportIE);
			return false;
		}
		
		if(ISFirefox()){
			if(window.export_load!=null){
				window.export_load.hide(); 
			}
			dialog.alert(notsupportFirefox);
			return false;
		}
		
		if(ISSafari()){
			if(window.export_load!=null){
				window.export_load.hide(); 
			}
			dialog.alert(notsupportSafari);
			return false;
		}
		
		var $container = $(container);
		$container.css({ left: "0px", top: "0px" });
		
		logging("exportPdf toCanvas begin");
		domtoimage.toCanvas(container).then(function (canvas) {
			
			var contentWidth = canvas.width;
	        var contentHeight = canvas.height;
            //一页pdf显示html页面生成的canvas高度;
            var pageHeight = contentWidth / 592.28 * 841.89;
            //未生成pdf的html页面高度
            var leftHeight = contentHeight;
            //pdf页面偏移
            var position = 0;
            //a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
            var imgWidth = 595.28;
            var imgHeight = 592.28/contentWidth * contentHeight;
          
            // 重新确认页面尺寸
            if(contentWidth < imgWidth){
            	imgWidth = contentWidth;
            	imgHeight = contentHeight;
            }
            
            logging("exportPdf toDataURL begin");
            var pageData = canvas.toDataURL('image/jpg', 1.0);
            logging("exportPdf toDataURL end");
            
            logging("exportPdf createPDF begin");
            var pdf = jspdf.jsPDF('', 'pt', 'a4');
            //pdf.setLineWidth();//设置线宽 
            //有两个高度需要区分，一个是html页面的实际高度，和生成pdf的页面高度(841.89)
            //当内容未超过pdf一页显示的范围，无需分页
            if (leftHeight < pageHeight) {
                pdf.addImage(pageData, 'JPG', 9, 0, imgWidth, imgHeight );
            } else {
            	while(leftHeight > 0) {
                    pdf.addImage(pageData, 'JPG', 9, position, imgWidth, imgHeight);
                    leftHeight -= pageHeight;
                    position -= 841.89;
                    //避免添加空白页
                    if(leftHeight > 0) {
                        pdf.addPage();
                    }
                }
            }
            logging("exportPdf createPDF end");
            
            if(window.export_load!=null){
    			window.export_load.hide(); 
    		}
            
            pdf.save(title);
            logging("exportPdf toCanvas end");
	    });
		logging("exportPdf end");
	};
	
	var exportImage = function(container,name) {
		logging("exportImage begin");
		if(IEVersion() !== -1) {
			if(window.export_load!=null){
				window.export_load.hide(); 
			}
			dialog.alert(notsupportIE);
			return false;
		}
		
		if(ISFirefox()){
			if(window.export_load!=null){
				window.export_load.hide(); 
			}
			dialog.alert(notsupportFirefox);
			return false;
		}
		
		if(ISSafari()){
			if(window.export_load!=null){
				window.export_load.hide(); 
			}
			dialog.alert(notsupportSafari);
			return false;
		}
		
		var $container = $(container);
		$container.css({ left: "0px", top: "0px" });
		
		logging("exportImage doToBlob");
		domtoimage.toBlob(container).then(function (imageData) {
			if(window.export_load!=null){
				window.export_load.hide(); 
			}
			
			logging("exportImage doSaveAsBegin");
			window.saveAs(imageData, name);
			logging("exportImage doSaveAsEnd");
	    });
		logging("exportImage end");
	};
	
	var logging = function(msg){
		if(window.console){
			window.console.log('==>Time:',new Date().getTime(),',Message:',msg);
		}
	};
	
	module.exports.exportPdf = exportPdf;
	module.exports.exportImage = exportImage;
	module.exports.logging = logging;
});