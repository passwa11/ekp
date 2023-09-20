define(	["dojo/_base/declare",
		"mui/list/_TemplateItemListMixin",
		dojoConfig.baseUrl+ "sys/circulation/mobile/js/CirculationOpinionItemMixin.js",
		"sys/circulation/mobile/js/CirculationOpinionItemNewVersionMixin"],
						
		function(declare, _TemplateItemListMixin, CirculationOpinionItemMixin,newMixin) {

			return declare("sys.circulation.CirculationOpinionItemListMixin",
				[_TemplateItemListMixin], {

					itemRenderer : CirculationOpinionItemMixin,
					
					buildRendering : function() {
						this.inherited(arguments);
						if(this.isNewVersion=="true"){
							this.itemRenderer = newMixin;
						}
					}
				});
		});