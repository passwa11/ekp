(function() {

	CKEDITOR.plugins.add('wikilink', {
		lang : 'af,ar,bg,bn,bs,ca,cs,cy,da,de,el,en,en-au,en-ca,en-gb,eo,es,et,eu,fa,fi,fo,fr,fr-ca,gl,gu,he,hi,hr,hu,id,is,it,ja,ka,km,ko,ku,lt,lv,mk,mn,ms,nb,nl,no,pl,pt,pt-br,ro,ru,si,sk,sl,sq,sr,sr-latn,sv,th,tr,ug,uk,vi,zh,zh-cn',
		init : function(editor) {
			lang = editor.lang.wikilink;
			editor.addCommand('Wikilink', {
				exec : function() {
					var selection = editor.getSelection(), ranges = selection
							.getRanges(true), nativeSel = selection.getNative();
					var str = ((CKEDITOR.env.ie && CKEDITOR.env.quirks) || CKEDITOR.env.ie8Compat)
							   ? nativeSel.createRange().text
							   : nativeSel.toString();
					if (ranges.length == 1 && !ranges[0].collapsed) {
						var bookmark = ranges[0].createBookmark(), firstNode = bookmark.startNode, lastNode = bookmark.endNode, flag = false;
						var currentNode = firstNode;
						while (currentNode) {
							var elem = currentNode.getAscendant('a', true);
							if (!elem) {
								flag = true;
							} else {
								flag = false;
								break;
							}
							if (currentNode.equals(lastNode)) {
								currentNode = null;
								break;
							}
							currentNode = currentNode.getNextSourceNode(true);
						}
						if (flag) {
							var attributes = {
								'data-wiki-link' : 'true',
								'href':'',
								'class' : 'lui_wiki_link'
							};
							var style = new CKEDITOR.style({
										element : 'a',
										attributes : attributes
									});
							style.type = CKEDITOR.STYLE_INLINE;
							style.apply(editor.document);
						} else {
							editor.document.$
									.execCommand('unlink', false, null);
						}
						ranges[0].moveToBookmark(bookmark);
						selection.selectRanges(ranges);

					}
				}
			});
			editor.ui.addButton('Wikilink', {
						label : lang.label,
						command : 'Wikilink',
						icon : this.path + 'images/wiki.gif'
					});
		}
	});

})();
