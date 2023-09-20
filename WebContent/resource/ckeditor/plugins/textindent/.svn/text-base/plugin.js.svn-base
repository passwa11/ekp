(function() {

	CKEDITOR.plugins
			.add(
					'textindent',
					{
						lang : 'en,zh,zh-cn',

						init : function(editor) {

							var genericDefinition = CKEDITOR.plugins.textindent.genericDefinition;

							editor.addCommand('textindent',
									new genericDefinition(true));

							if (editor.ui.addButton) {
								editor.ui.addButton('Textindent', {
									label : editor.lang.textindent.indent,
									command : 'textindent',
									icon : this.path + '/images/textindent.png'
								});
							}

						}
					});

	CKEDITOR.plugins.textindent = {
		genericDefinition : function(istextindent) {

		}
	};

	CKEDITOR.plugins.textindent.genericDefinition.prototype = {

		context : 'p',

		exec : function(editor) {

			var selection = editor.getSelection(), enterMode = editor.config.enterMode;

			if (!selection)
				return;

			var bookmarks = selection.createBookmarks(), ranges = selection
					.getRanges();

			for (var i = ranges.length - 1; i >= 0; i--) {

				iterator = ranges[i].createIterator();
				iterator.enlargeBr = enterMode != CKEDITOR.ENTER_BR;

				while ((block = iterator
						.getNextParagraph(enterMode == CKEDITOR.ENTER_P ? 'p'
								: 'div'))) {

					if (block.isReadOnly())
						continue;

					if (block.getStyle('text-indent') == '2em') {
						block.removeStyle('text-indent');
						continue;
					}

					block.setStyle('text-indent', '2em');

				}

			}

			editor.focus();
			editor.forceNextSelectionCheck();
			selection.selectBookmarks(bookmarks);
		}
	};

})();