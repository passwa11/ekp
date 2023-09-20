(function() {
	CKEDITOR.plugins.add('material', {
		requires : 'clipboard',
		lang : 'af,ar,bg,bn,bs,ca,cs,cy,da,de,el,en,en-au,en-ca,en-gb,eo,es,et,eu,fa,fi,fo,fr,fr-ca,gl,gu,he,hi,hr,hu,id,is,it,ja,ka,km,ko,ku,lt,lv,mk,mn,ms,nb,nl,no,pl,pt,pt-br,ro,ru,si,sk,sl,sq,sr,sr-latn,sv,th,tr,ug,uk,vi,zh,zh-cn',
		hidpi : true,
		init : function(editor) {
			var config = editor.config, lang = editor.lang.keydata;
			var pluginName = "material";
			CKEDITOR.dialog.add(pluginName, this.path + 'dialogs/material.js');
			editor.addCommand(pluginName, new CKEDITOR.dialogCommand(pluginName));
	        editor.ui.addButton(pluginName,
	        {
	            label: '系统图片',
	            command: pluginName,
	            icon : this.path + 'images/material.png'
	        });
		}
	});
})();