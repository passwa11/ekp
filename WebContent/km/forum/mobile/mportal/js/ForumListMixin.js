define([ "dojo/_base/declare", "mui/list/_TemplateItemListMixin",
		"km/forum/mobile/resource/js/ForumTopicItemMportalMixin", "dojo/_base/array",'mui/util' ], function(declare,
		_TemplateItemListMixin, ForumTopicItemMportalMixin, array,util) {

	return declare("km.forum.mportal.ForumListMixin", [ _TemplateItemListMixin ], {
		
		type : 'latest',
		
		itemRenderer : ForumTopicItemMportalMixin,
		
		buildRendering : function() {
			this.inherited(arguments);
			switch(this.type){
				case 'latest' :
					break;
				case 'hot' :
					this.url = util.setUrlParameter(this.url,'q.fdOther','hot');
					break;
				case 'pinked' :
					this.url = util.setUrlParameter(this.url,'q.fdOther','pink');
					break;
				case 'my' :
					this.url = util.setUrlParameter(this.url,'q.myTopic','create');
					break;
				default:
					break;
			}
		}
		
	});
});