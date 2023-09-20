
(function() {
	
	CKEDITOR.plugins.add('autoformat', {
		lang : 'af,ar,bg,bn,bs,ca,cs,cy,da,de,el,en,en-au,en-ca,en-gb,eo,es,et,eu,fa,fi,fo,fr,fr-ca,gl,gu,he,hi,hr,hu,id,is,it,ja,ka,km,ko,ku,lt,lv,mk,mn,ms,nb,nl,no,pl,pt,pt-br,ro,ru,si,sk,sl,sq,sr,sr-latn,sv,th,tr,ug,uk,vi,zh,zh-cn', // %REMOVE_LINE_CORE%
		init : function(editor) {

			var pluginName = "autoformat"; 
			
			new CKEDITOR.dialogCommand( pluginName );
			editor.addCommand( pluginName, new CKEDITOR.dialogCommand( pluginName ) );

			
			editor.ui.addButton(pluginName, {
				 label: editor.lang.autoformat? editor.lang.autoformat.immediately:"一键排版",
				 command: pluginName,
				 icon : this.path + 'icons/yjpb.png'
			});
			
			
			
			//注册我们的myButton.js
			CKEDITOR.dialog.add( pluginName, this.path + 'dialogs/autoformat.js' );
			
			
		}
	});
	
	
})();