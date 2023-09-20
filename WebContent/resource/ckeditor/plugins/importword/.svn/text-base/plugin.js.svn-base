(function() {
	CKEDITOR.plugins.add('importword', {
		hidpi : true,
		lang : 'en,zh,zh-cn',
		requires : 'fakeobjects',
	    init : function( editor ) {
	        // Add the link and unlink buttons.
	    	var pluginName = "importword";
	        CKEDITOR.dialog.add(pluginName, this.path + 'dialogs/importword.js');
	        editor.addCommand(pluginName, new CKEDITOR.dialogCommand(pluginName) );
	        editor.ui.addButton('Importword', {
                label : editor.lang.importword.label,
                icon: this.path + 'images/word_import.png',
                command : pluginName
            });
	    }
	});
})();