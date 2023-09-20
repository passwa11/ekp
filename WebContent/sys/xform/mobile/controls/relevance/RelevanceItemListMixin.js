define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"sys/xform/mobile/controls/relevance/RelevanceItemMixin"
	], function(declare, _TemplateItemMixin, RelevanceItemMixin) {
	
	return declare("sys.xform.mobile.controls.relevance.RelevanceItemListMixin", [_TemplateItemMixin], {
		
		itemRenderer : RelevanceItemMixin,
		
		_createItemProperties: function(/*Object*/item) {
			var props = this.inherited(arguments);
			props['key'] = this.key;
			props['isMul'] = this.isMul;
			return props;
		}
	});
});