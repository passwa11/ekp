define(	["dojo/_base/declare",
		"mui/list/_TemplateItemListMixin",
		dojoConfig.baseUrl+ "sys/circulation/mobile/js/CirculationItemNewMixin.js"],
						
		function(declare, _TemplateItemListMixin, CirculationItemNewMixin) {

			return declare("sys.circulation.CirculationItemListNewMixin",
				[_TemplateItemListMixin], {

					itemRenderer : CirculationItemNewMixin
				});
		});