define([
    "dojo/_base/declare",
	"mui/list/_TemplateItemListMixin",
	"sys/attend/mobile/resource/js/stat/AttendStatLogMixin"
	], function(declare, _TemplateItemListMixin, AttendStatLogMixin) {
	return declare("sys.attend.AttendStatLogListMixin", [_TemplateItemListMixin], {
		itemTemplateString : null,
		itemRenderer: AttendStatLogMixin,
		startup: function() {
			this.inherited(arguments);
		}
	});
});