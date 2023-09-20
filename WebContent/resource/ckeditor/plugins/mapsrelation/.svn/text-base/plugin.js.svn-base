(function() {
	CKEDITOR.plugins.add('mapsrelation', {
		lang : 'af,ar,bg,bn,bs,ca,cs,cy,da,de,el,en,en-au,en-ca,en-gb,eo,es,et,eu,fa,fi,fo,fr,fr-ca,gl,gu,he,hi,hr,hu,id,is,it,ja,ka,km,ko,ku,lt,lv,mk,mn,ms,nb,nl,no,pl,pt,pt-br,ro,ru,si,sk,sl,sq,sr,sr-latn,sv,th,tr,ug,uk,vi,zh,zh-cn',
		init : function(editor) {
			if(!window.relationMains) {
				return;
			}
			lang = editor.lang.mapsrelation;
			var recordSec = null, recordRanges = null;
			var nid = function() {
			     return (new Date().getTime().toString(16) + Math.random().toString(16).substr(2)).substr(2, 16);
			}
			
			var isIE = function() {
				return CKEDITOR.env.ie;
			}
			
			var isEmptyObject =  function( obj ) {
			    var name;
			    for ( name in obj ) {
			        return false;
			    }
			    return true;
			}
			
			var afterRelation = function(k) {
				if(relationMains[k] && relationMains[k].relationEntrys && !isEmptyObject(relationMains[k].relationEntrys)) {
					var attributes = {
							'data-relation-link' : 'true',
							'class' : 'lui_maps_relation_link',
							'data-relation-key' : k
					};
					var style = new CKEDITOR.style({
									element : 'a',
									attributes : attributes
								});
					
					if(isIE() && recordRanges && recordRanges.length == 1) {
						editor.getSelection().selectRanges( recordRanges );
					}
					
					style.type = CKEDITOR.STYLE_INLINE;
					style.apply(editor.document);
				} else {
					if(relationMains[k]) {
						if(window.Relation_deleteRelation)
							Relation_deleteRelation(k);
					}
					recordRanges = null;
					editor.document.$
						.execCommand('unlink', false, null);
				}
			}
			if(window.____relationEvent) {
				____relationEvent.push(afterRelation);
			}
			
			
			
			var getRelationFun  = function(isAdd) {
				return function() {
					var selection = editor.getSelection(), ranges = selection
						.getRanges(true), nativeSel = selection.getNative();
					var str = ((CKEDITOR.env.ie && CKEDITOR.env.quirks) || CKEDITOR.env.ie8Compat)
							   ? nativeSel.createRange().text
							   : nativeSel.toString();
					var selem = null;
					
					if (ranges.length == 1 && !ranges[0].collapsed) {
						if(isIE()) {
							recordRanges = ranges;
						}
						var bookmark = ranges[0].createBookmark(), firstNode = bookmark.startNode, lastNode = bookmark.endNode, flag = false;
						var currentNode = firstNode;
						while (currentNode) {
							var elem = currentNode.getAscendant('a', true);
							if (!elem) {
								flag = true;
							} else {
								selem = elem;
								flag = false;
								break;
							}
							if (currentNode.equals(lastNode)) {
								currentNode = null;
								break;
							}
							currentNode = currentNode.getNextSourceNode(true);
						}
						
						if(isAdd) {
							//添加关联
							if (!selem) {
								if(window.Relation_editRelation)  {
									var k = nid();
									Relation_editRelation(k.toString());
								}
							} else {
								var k = selem.getAttribute("data-relation-key");
								if(k) {
									if(window.Relation_editRelation) {
										Relation_editRelation(k.toString());
									}
								}
							} 
						} else {
							//移除关联
							if (selem) {
								var k = selem.getAttribute("data-relation-key");
								if(k) {
									if(relationMains[k] && window.Relation_deleteRelation) {
										Relation_deleteRelation(k);
									}
									editor.document.$
										.execCommand('unlink', false, null);
								}
							}
						}
						ranges[0].moveToBookmark(bookmark);
						selection.selectRanges(ranges);
					}
				}
			}
			
			editor.addCommand('Mapsrelation', {
				exec : getRelationFun(true)
			});
			
			editor.addCommand('Unmapsrelation', {
				exec :  getRelationFun(false)
			});
			
			editor.ui.addButton('Mapsrelation', {
						label : lang.label,
						command : 'Mapsrelation',
						icon :  'Link'
					});
			
			editor.ui.addButton('Unmapsrelation', {
				label : lang.unrela,
				command : 'Unmapsrelation',
				icon : 'unLink'
			});
			
		}
	
	});
	
})();
