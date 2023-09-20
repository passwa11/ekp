define([ "dojo/_base/declare", 
         "dojo/dom-construct", 
         "dojo/dom-prop", 
         "mui/util",
         "dijit/registry", 
         "mui/form/_FormBase", 
         "dojo/query","dojo/dom", 
         "dojo/dom-class",
         "dojo/dom-attr",
         "dojo/dom-style",
         "dojo/on",
         "dojo/_base/lang",
         "dojo/topic",
         "mui/i18n/i18n!sys-xform-base",
         "mui/dialog/Dialog",
         ], function(declare, domConstruct, domProp, util, registry,
        		 _FormBase, query,dom,domClass,domAttr,domStyle,on,lang,topic,msg,Dialog) {
	var claz = declare("sys.xform.mobile.controls.QrCode", [ _FormBase], {
		
		content : "",
		
		type : 'qrCode',
		
		//二维码类型
		urlLinkType : "11",	//11:超链接url 
		
		contentType : "12",	//12. 文本内容
		
		cardType : "13",	//13.名片类型
		
		//值
		docUrl : "11",	//11.文档链接
		
		
		customUrl : "12",	//12自定义
		
		buildRendering : function() {
			this.inherited(arguments);
		},
		
		postCreate: function() {
			this.inherited(arguments);
		},
		
		startup:function(){
			this.inherited(arguments);
			var method = util.getUrlParameter(location.href,"method");
			if (!(method === "add" &&  this.valType === this.docUrl)){
				this.generateQrCode();
			} else {
				this.buildExample();
			}
		},
		
		buildExample:function(){
//			var exampleHtml = "<div class='muiQrCodeExample'>";
//			exampleHtml += "<div class='muiQrCodeExample_img'></div>";
//			exampleHtml += "<div class='muiQrCodeExample_text'><span>" + msg["mui.qRCode.example"] + "</span></div>";
//			exampleHtml += "<div class='muiQrCodeExample_tip'><span>" + msg["mui.qRCode.example_tip"] + "</span></div>";
//			exampleHtml += "</div>";
			var exampleHtml = '<div style="'+(this.orient == 'horizontal' ? 'float:right;' : '')+'color:#C4C6CF;line-height: 3rem;">'+msg["mui.qRCode.exCreate"]+'</div>'
			var element = domConstruct.toDom(exampleHtml);
			domConstruct.place(element,this.domNode,"last");
		},
		
		generateQrCode: function (qrcode){
			var url = this.content;
			if(this.mold === this.urlLinkType){ //超链接url
				if (this.valType === this.docUrl){//文档链接
					url = location.href;
					var method = util.getUrlParameter(location.href,"method");
					if (method && method.indexOf("edit") != -1){
						url = url.replace("method=" + method,"method=view");
					}
				}
				var options = {
					text :url,
					element : this.detailQrCode,
					height : '100px',
					width : '100px',//compatible : true
					title : this.subject
				};
				this.doGenerateQrCode(options);
				
			}else if (this.mold === this.contentType){//文本内容
				
			}else if (this.mold === this.cardType){//名片类型
				
			}else{
				
			}
		},
		
		doGenerateQrCode : function(options){
			var url = this.formatUrl("/sys/ui/sys_ui_qrcode/sysUiQrcode.do?method=getQrcode&contents="+options.text);
			var html = "<img style='width:" + options.width + ";height:" + options.height + ";" + (this.orient == 'horizontal' ? 'float:right;' : '') + "' src='"+url+"' title='"+(options.title?options.title:'')+"'/>";
			var element = domConstruct.toDom(html);
			this.connect(element,"click","_ShowQrCode");
			domConstruct.place(element,this.domNode,"last");
		},
		
		formatUrl : function(url) {

			if (url == null) {
				return "";
			}
			
			var targetUrl = url;

			if (url.substring(0, 1) == '/') {
				targetUrl = Com_Parameter.ContextPath + url.substring(1);
			}
			return targetUrl;
		},
		
		_setRequiredAttr : function(value) {
			
		},
		
		_getRequiredAttr:function(){
			return false;
		},
		
		_ShowQrCode : function(evt){
			if(!evt.target.src){return}
			var sub = evt.target.title;
			if(evt.target.title == ''){
				sub = query(evt.target).parents("tr").children(".muiTitle").children("label").text();
			}
			var contentNode = domConstruct.create('div', {
				style:{
					'padding': '3rem'
				}
			});
			domConstruct.create('div',{
				innerHTML : '<div><img style="max-width:100%" src="'+evt.target.src+'"></div>'
			},contentNode)
			if(sub != ''){
				domConstruct.create('div',{
					innerHTML : '<div style="width: 100%;height: 4rem;font-size: 12px;font-size: var(--muiFontSizeL);padding-top: 3rem;">'+sub+'</div>'
				},contentNode)
			}
			var options = {
				title : null,
				element : contentNode,
				scrollable : false,
				parseable : false, 
				canClose : true,
				callback : null
			}
			Dialog.element(options);
		}
		
	});
	return claz;
});