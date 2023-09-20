/*
* @Author: liwenchang
* @Date:   2018-08-27 22:04:29
* @Last Modified by:   liwenchang
* @Last Modified time: 2018-08-31 08:58:14
*/
//二维码控件执行脚本
(function(window){
	var win = window,
	document = win.document,
	type = 'qrCode',
	//二维码类型
	urlLinkType = "11",//11:超链接url 
	contentType = "12",//12. 文本内容
	cardType = "13",//13.名片类型
	//值
	docUrl = "11",//11.文档链接
	customUrl = "12";//12自定义
	
	//所有二维码控件对象,key是id,value是控件对象
	var xformQRCodeControls = {};
	$(function(){
		//获取明细表外的二维码控件
		var qrCodes = $(document).find("[fd_type='" + type + "']:not([id*='!{index}'])");
		buildControl(qrCodes);
		//获取明细表内的二维码控件
		$(document).on("table-add",function(event,source){
			tableAdd(event,source);
		});
	})

	/**
	*二维码控件对象
	*/
	function qrCodeControl(dom,isHidden){
		var obj = $(dom);
		//控件属性所在dom元素
		this.dom = dom;
		//id
		this.id = obj.attr("id");
		//type 
		this.type = "qrCode";
		//类型
		this.mold = obj.attr("mold");
		//值
		this.valType = obj.attr("valType");
		//高度
		this.height = obj.attr("_height");
		if (this.height.indexOf("%") > 0) {
			var $parent = $(this.dom).parent("xformflag").parent();
			this.height = $parent.height() * parseFloat(this.height)/100 || 140;
		}
	
		//宽度
		this.width = obj.attr("_width");
		if (this.width.indexOf("%") > 0) {
			var $parent = $(this.dom).parent("xformflag").parent();
			this.width = $parent.width() * parseFloat(this.width)/100;
		}
		//是否显示下载按钮
		this.isShowDownLoadButton = obj.attr("isShowDownLoadButton");
		//内容
		this.content = obj.attr("content");
		//绑定文本
		this.title = obj.attr("title");
		//初始化函数
		this.init = init;
		//下载 
		this.save = save;
		
		this.buildExample = buildExample;
		if (!isHidden) {
			this.init();
		}
	}

	function init(){
		$(this.dom).css("width",this.width);
		$(this.dom).css("height",this.height);
		this.detailQrCode = document.createElement("div");
//		$(this.detailQrCode).addClass("detailQrCode");
		$(this.dom).append(this.detailQrCode);
		var self = this;
		//调用接口生成二维码
		seajs.use(['lui/qrcode'],$.proxy(generateQrCode,self));
		//显示下载按钮
		if (this.isShowDownLoadButton == "true"){
			this.downLoadDom = document.createElement("span");
			$(this.dom).append(this.downLoadDom);
			$(this.downLoadDom).css("width",this.width);
			$(this.downLoadDom).css("display","inline-block");
			$(this.downLoadDom).append(XformObject_Lang.controlQrCodeDownLoad);
			$(this.downLoadDom).css("text-align","center");
			$(this.downLoadDom).css("background-color","#47b5e6");
			$(this.downLoadDom).css("color","#fff");
			$(this.downLoadDom).css("cursor","pointer");
			//绑定点击事件
			$(this.downLoadDom).on("click",$.proxy(save,self))
		}
		
	}
	
	function buildExample(){
		var exampleHtml = "<div class='qrCodeExample'>";
		exampleHtml += "<div class='qrCodeExample_img'></div>";
		exampleHtml += "<div class='qrCodeExample_text'><span>" + XformObject_Lang.controlQrCodeExample + "</span></div>";
		exampleHtml += "<div class='qrCodeExample_tip'><span>" + XformObject_Lang.controlQrCodeExample_tip + "</span></div>";
		exampleHtml += "</div>";
		$(this.dom).append($(exampleHtml));
	}
	
	function generateQrCode(qrcode){
		var url = this.content;
		if(this.mold === urlLinkType){ //超链接url
			if (this.valType === docUrl){//文档链接
				url = location.href;
				var method = Com_GetUrlParameter(url,"method");
				if (method && (method.indexOf("edit") != -1 || method.indexOf("print") != -1)){
					url = url.replace("method=" + method,"method=view");
				}
			}
			
			qrcode.Qrcode({
				text :url,
				element : this.detailQrCode,
				render :  'canvas',
				height : this.height,
				width : this.width//compatible : true
			});
			
			
		}else if (this.mold === contentType){//文本内容
			
		}else if (this.mold === cardType){//名片类型
			
		}else{
			
		}
		//兼容ie8
		/*var isBitch = navigator.userAgent.indexOf("MSIE") > -1
					&& document.documentMode == null 
					|| document.documentMode <= 8;
		if (isBitch){
			var img = $(this.dom).find("img");
			img.css("width",this.width);
			img.css("height",this.height);
		}*/
	}
	
	function save() {
		var canvas = $(this.detailQrCode).find("canvas");
		var name = this.title + ".png", type = "png";
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
	
	function _fixType(type) {
		var r = type.match(/png|jpeg|bmp|gif/)[0];
		return 'image/' + r;
	}

	/**
	 * 明细表行增加事件,初始化的时候也会触发此事件
	 * @param event
	 * @param source
	 * @returns
	 */
	function tableAdd(event,source){
		//获取添加行内的二维码控件
		var qrCodes = $(source).find("[fd_type='" + type + "']");
		buildControl(qrCodes);
	}

	function buildControl(qrCodes){
		if (qrCodes instanceof jQuery){
			for (var i = 0; i < qrCodes.length; i++){
				if(qrCodes[i] && qrCodes[i].nodeType){
					var isPrint = $(qrCodes[i]).attr("isprint");
					var isHidden = isPrint === "add" &&  $(qrCodes[i]).attr("valType") === docUrl;
					var control = new qrCodeControl(qrCodes[i],isHidden);
					xformQRCodeControls[control.id] = control;
					if (isHidden){
//						$(control["dom"]).hide();
						$(control["dom"]).css("position","relative");
						$(control["dom"]).css("width","300px");
						control.buildExample();
					}
				}
			}
		}
	}
	
	function htmlUnEscape (s){
		if (s == null || s ==' ') return '';
		s = s.replace(/&amp;/g, "&");
		s = s.replace(/&quot;/g, "\"");
		s = s.replace(/&lt;/g, "<");
		return s.replace(/&gt;/g, ">");
	};
})(window);