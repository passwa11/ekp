define(	["dojo/_base/declare",
		"mui/list/_TemplateItemListMixin",
		dojoConfig.baseUrl+ "sys/circulation/mobile/js/CirculationOpinionItemOptMixin.js"],
						
		function(declare, _TemplateItemListMixin, CirculationOpinionItemOptMixin) {

			return declare("sys.circulation.CirculationOpinionItemListOptMixin",
				[_TemplateItemListMixin], {

					itemRenderer : CirculationOpinionItemOptMixin
				});
		});