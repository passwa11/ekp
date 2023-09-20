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
		//文档二维码
		var docQRCodes = $(document).find('div[flagid="docQRCode"]');
		if(docQRCodes && docQRCodes.length>0){
			buildControl(docQRCodes);
		}
	})

	/**
	*二维码控件对象
	*/
	function qrCodeControl(dom){
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
		//宽度
		this.width = obj.attr("_width");
		//是否显示下载按钮
		this.isShowDownLoadButton = obj.attr("isShowDownLoadButton");
		//内容
		this.content = obj.attr("content");
		if(this.content ){
			this.content = htmlUncodeByRegExpInCode(this.content);
		}
		//绑定文本
		this.title = obj.attr("title");
		if(this.dom.id=='docQRCode'){
			this.mold = "11";
			this.valType = "11";
			this.height = 100;
			this.width = 100;
		}
		//初始化函数1
		this.init = init;
		//下载 
		this.save = save;
		this.init();
	}

	function init(){
		$(this.dom).css("width",this.width);
		$(this.dom).css("height",this.height);
		this.detailQrCode = document.createElement("div");
		$(this.detailQrCode).addClass("muiQrCode");
		$(this.dom).append(this.detailQrCode);
		var self = this;
		//调用接口生成二维码
		seajs.use(['lui/qrcode'],$.proxy(generateQrCode,self));
	}
	
	function generateQrCode(qrcode){
		var url = this.content;
		if(this.mold === urlLinkType){ //超链接url
			if (this.valType === docUrl){//文档链接
				url = location.href;
				url = url.replace("method=print","method=view");
			}
			qrcode.Qrcode({
				text :url,
				element : this.detailQrCode,
				render :  'canvas',
				height : this.height,
				width : this.width
			});
			
			
		}else if (this.mold === contentType){//文本内容
			
		}else if (this.mold === cardType){//名片类型
			
		}else{
			
		}
	}
	
	function save() {
	}
	
	function _fixType(type) {
		var r = type.match(/png|jpeg|bmp|gif/)[0];
		return 'image/' + r;
	}

	function buildControl(qrCodes){
		if (qrCodes instanceof jQuery){
			for (var i = 0; i < qrCodes.length; i++){
				if(qrCodes[i] && qrCodes[i].nodeType){
					var control = new qrCodeControl(qrCodes[i]);
					xformQRCodeControls[control.id] = control;
					var isPrint = $(control.dom).attr("isprint");
					if (isPrint === "add" &&  $(control.dom).attr("valType") === docUrl){
						$(control["dom"]).hide();
					}
				}
			}
		}
	}
	function htmlUncodeByRegExpInCode(str){
		return str.replace(/&#039;/g,"'")
			.replace(/&#034;/g,'"')
			.replace(/&lt;/g,"<")
			.replace(/&gt;/g,">")
			.replace(/&amp;/g,"&");
	}
})(window);