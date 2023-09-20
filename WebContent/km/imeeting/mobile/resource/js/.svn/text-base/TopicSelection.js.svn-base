define([ 'dojo/_base/declare', 'mui/iconUtils', 'dojo/_base/array', 'mui/category/CategorySelection', 'dojo/topic', 'dojo/_base/lang'],
	function(declare, iconUtils, array, CategorySelection, topic, lang) {
		var selection = declare('km.imeeting.mobile.js.TopicSelection',[ CategorySelection ],{
	
			_initSelection:function(){
				var ctx = this;
				
				topic.publish('km/imeeting/selectedtopic/get', function(selectedCerts) {
					array.forEach(selectedCerts || [],function(item){
						ctx._addSelItme(ctx, lang.mixin(item, {
							label: item.title
						}));
					});
				})
				
			},
	
			buildIcon : function(iconNode, item) {
				iconUtils.setIcon('mui mui-file-text', null, null, null,
					iconNode);
			},
			
			_calcCurSel:function(){
				return this.cateSelArr;
			}
		});
		return selection;
	}
);