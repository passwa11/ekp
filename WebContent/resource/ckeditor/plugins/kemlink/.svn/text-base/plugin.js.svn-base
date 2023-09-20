(function() {
	function use(url, isCss) {
		if (Com_ArrayGetIndex(Com_Parameter.JsFileList, url) == -1) {
			Com_Parameter.JsFileList[Com_Parameter.JsFileList.length] = url;
			var node = document.createElement(isCss ? 'link' : 'script'), head = document
					.getElementsByTagName("head")[0];
			if (isCss) {
				node.rel = "stylesheet"
				node.href = url
			} else {
				node.src = url
			}
			head.appendChild(node);
		}
	}
	/* kemlink 是原子知识用的，这里判断一下，维基新增页面请求数量太多，IE8报错“储存空间不足”*/
	if (window.location.href.indexOf("/kms/kem") > -1){
	use(Com_Parameter.ContextPath
			+ 'resource/ckeditor/plugins/kemlink/dialogs/style/kemlink.css',
			true);
	}
	
	
	
	var pluginName = "kemlink";
	CKEDITOR.plugins
			.add(pluginName,
					{
						requires : 'dialog',
						lang : 'af,ar,bg,bn,bs,ca,cs,cy,da,de,el,en,en-au,en-ca,en-gb,eo,es,et,eu,fa,fi,fo,fr,fr-ca,gl,gu,he,hi,hr,hu,id,is,it,ja,ka,km,ko,ku,lt,lv,mk,mn,ms,nb,nl,no,pl,pt,pt-br,ro,ru,si,sk,sl,sq,sr,sr-latn,sv,th,tr,ug,uk,vi,zh,zh-cn', // %REMOVE_LINE_CORE%
						icons : pluginName,
						hidpi : true,
						init : function(editor) {
							editor.addCommand(pluginName,new CKEDITOR.dialogCommand(pluginName));
							editor.ui.addButton
									&& editor.ui.addButton(pluginName, {
										icon : this.path + 'dialogs/style/images/wiki.gif',
										label : editor.lang.kemlink.icon,
										command : pluginName,
										toolbar : 'insert,50'
									});
							/* kemlink 是原子知识用的，这里判断一下，维基新增页面请求数量太多，IE8报错“储存空间不足”*/
							if (window.location.href.indexOf("/kms/kem") > -1){
							CKEDITOR.dialog.add(pluginName, this.path
									+ 'dialogs/kemlink.js');
							CKEDITOR.dialog.add(pluginName, this.path
									+ 'dialogs/style/kemlink.css');
							}
						}
					});
})();
