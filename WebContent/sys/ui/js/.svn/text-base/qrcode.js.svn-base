define(function(require, exports, module) {
	var QRCode = require('lui/qrcode/qrcode');
	var toolbar = require('lui/toolbar');
	var lang = require('lang!sys-ui');
	var env = require('lui/util/env');
	var buildOptions = function(options) {
		var _options = {};
		$.extend(_options, {
			render : "canvas",
			width : 140,
			height : 140,
			typeNumber : -1,
			correctLevel : QRErrorCorrectLevel.L,
			background : "#ffffff",
			foreground : "#000000"
		}, options);
		return _options;
	};
	
	//更新options的url，有些场景下url带了file://http://这种格式导致失败，比如导出html
	var updateUrlBeforeInit = function(url){
		var prefix = 'file://http://';
		var filePrefix = "file://";
		if(url.indexOf(prefix) == 0)
			url = url.substring(filePrefix.length);
		return url;
	};

	var createCanvas = function(options) {
		var qrcode = new QRCode(options.typeNumber, options.correctLevel);
		qrcode.addData(options.text);
		qrcode.make();
		var canvas = document.createElement('canvas');
		canvas.width = options.width;
		canvas.height = options.height;
		var ctx = canvas.getContext('2d');

		var tileW = options.width / qrcode.getModuleCount();
		var tileH = options.height / qrcode.getModuleCount();

		for (var row = 0; row < qrcode.getModuleCount(); row++) {
			for (var col = 0; col < qrcode.getModuleCount(); col++) {
				ctx.fillStyle = qrcode.isDark(row, col) ? options.foreground
						: options.background;
				var w = (Math.ceil((col + 1) * tileW) - Math.floor(col * tileW));
				var h = (Math.ceil((row + 1) * tileW) - Math.floor(row * tileW));
				ctx.fillRect(Math.round(col * tileW), Math.round(row * tileH),
						w, h);
			}
		}
		return canvas;
	}

	var createTable = function(options) {
		var qrcode = new QRCode(options.typeNumber, options.correctLevel);
		qrcode.addData(options.text);
		qrcode.make();

		var $table = $('<table></table>').css("width", options.width + "px")
				.css("height", options.height + "px").css("border", "0px").css(
						"border-collapse", "collapse").css('background-color',
						options.background);

		var tileW = options.width / qrcode.getModuleCount();
		var tileH = options.height / qrcode.getModuleCount();

		for (var row = 0; row < qrcode.getModuleCount(); row++) {
			var $row = $('<tr></tr>').css('height', tileH + "px").appendTo(
					$table);

			for (var col = 0; col < qrcode.getModuleCount(); col++) {
				$('<td></td>').css('width', tileW + "px").css(
						'background-color',
						qrcode.isDark(row, col) ? options.foreground
								: options.background).appendTo($row);
			}
		}
		return $table;
	}

	/***************************************************************************
	 * options={render:'',text:'',element:''}
	 **************************************************************************/
	var Qrcode = function(options) {
		if(options.text){
			options.text = updateUrlBeforeInit(options.text);
		}
		options = buildOptions(options);
		var element;
		// ie8或以下使用后台逻辑生成二维码
		if(options.compatible&&
				(navigator.userAgent.indexOf("MSIE") > -1 && document.documentMode == null || document.documentMode <= 8)){
			var url = env.fn.formatUrl("/sys/ui/sys_ui_qrcode/sysUiQrcode.do?method=getQrcode&contents="+options.text);
			var element = $("<img src='"+url+"'/>");
		}else{
			if(options.render=='custom'){
				//自定义 使用后台生成二维码
				var className = options.className;
				var val =encodeURIComponent(options.text);
				var url = env.fn.formatUrl("/sys/ui/sys_ui_qrcode/sysUiQrcode.do?method=getQrcode&contents="+val);
				if(options.genWidth&&options.genHeight){
					url = url + "&width="+options.genWidth+"&height="+options.genHeight;
				}
				element = $("<img src='"+url+"' class='"+className+"' />");
			}else if (options.render == 'canvas'){				
				element = createCanvas(options);
			}
			else{				
				element = createTable(options);
			}
		}
		$(element).appendTo($(options.element));
	}

	// 特殊场景
	var QrcodeToTop = function() {
		var isBitch = navigator.userAgent.indexOf("MSIE") > -1
				&& document.documentMode == null || document.documentMode <= 8;
		var isIE10D = navigator.userAgent.indexOf("MSIE") > -1
				&& document.documentMode == null || document.documentMode <= 9;

		if (isBitch) // 直接去除对ie8浏览器的支持，防止页面未响应
			return;
		var topObj = LUI('top');
		var btn = new toolbar.Button({
			styleClass : 'com_qrcode',
			order : 0
		});
		btn.startup();
		if(topObj){
			topObj.addButton(btn);	
		}

		function _fixType(type) {
			var r = type.match(/png|jpeg|bmp|gif/)[0];
			return 'image/' + r;
		}

		function save(canvas) {
			var name = lang['ui.dialog.2Dbarcodes'], type = "png";
			if (window.navigator.msSaveBlob) {
				window.navigator.msSaveBlob(canvas[0].msToBlob(), name);
			} else {
				var imageData = canvas[0].toDataURL(type);
				imageData = imageData.replace(_fixType(type),
						'image/octet-stream');
				var save_link = document.createElementNS(
						"http://www.w3.org/1999/xhtml", "a");
				save_link.href = imageData;
				save_link.download = name;
				var ev = document.createEvent("MouseEvents");
				ev.initMouseEvent("click", true, false, window, 0, 0, 0, 0, 0,
						false, false, false, false, 0, null);
				save_link.dispatchEvent(ev);
				ev = null;
				delete save_link;
			}
		}

		// 显示
		function sho() {
			btn.containerNode.show();
			var height = '190px';
			if (isIE10D)
				height = '167px';
			btn.containerNode.animate({
				'height' : height,
				'width' : '162px'
			});
		}

		// 隐藏
		function hid() {
			if(btn.containerNode){
				btn.containerNode.animate({
					'height' : 0,
					'width' : 0
				}, function() {
					btn.containerNode.hide();
				});
			}
		}

		// 构建
		function construct() {
			var url = location.href;
			var pos = topObj.element.position();

			btn.containerNode = $('<div id='+topObj.id+'_frame'+'>').css({
				'height' : 0,
				'width' : 0,
				'background-color' : '#fff',
				'margin' : '0 auto',
				'position' : 'fixed',
				'bottom' : '179px',
				'right' : '69px',
				'overflow' : 'hidden',
				'z-index' : '999'
			}).appendTo($(document.body));

			$('<iframe scrolling="no">').css({
				position : "absolute",
				bottom : 0,
				left : 0,
				right : 0,
				top : 0,
				width : "100%",
				height : "100%",
				"z-index" : 4,
				border : 0
			}).appendTo(btn.containerNode);

			btn.contentNode = $('<div>').css({
				position : "absolute",
				top : 1,
				left : 1,
				right : 1,
				bottom : 1,
				'padding' : '10px',
				"z-index" : 5,
				'border' : '1px solid #000'
			}).appendTo(btn.containerNode);

			btn.containerNode.on({
				'mouseover' : function() {
					if (btn.timeout)
						clearTimeout(btn.timeout);
				},
				'mouseout' : function() {
					if (btn.timeout)
						clearTimeout(btn.timeout);
					btn.timeout = setTimeout(function() {
						qrcodeTriggle(false);
					}, 500);
				}
			})

			Qrcode({
				text : url,
				element : btn.contentNode,
				render : isBitch ? 'table' : 'canvas'
			});

			if (!isIE10D) {
				var button = toolbar.buildButton({
					text : lang['ui.dialog.downlaod2Dbarcodes']
				});
				button.element.css('text-align', 'center').click(function() {
					save(btn.contentNode.find('canvas'));
				});
				button.element.appendTo(btn.contentNode);
				button.draw();
			}
		}

		var qrcodeTriggle = function(show) {
			if (!show) {
				hid();
				return;
			}
			if (btn.containerNode) {
				sho();
				return;
			}
			construct();
			sho();
		};

		btn.element.on({
			mouseover : function(evt) {
				if (btn.timeout)
					clearTimeout(btn.timeout);
				btn.timeout = setTimeout(function() {
					qrcodeTriggle(true);
				}, 100);
			},
			mouseout : function() {
				if (btn.timeout)
					clearTimeout(btn.timeout);
				btn.timeout = setTimeout(function() {
					qrcodeTriggle(false);
				}, 500);
			}
		});

	}
	exports.Qrcode = Qrcode;
	exports.QrcodeToTop = QrcodeToTop;
});