define("km/imeeting/mobile/resource/js/list/PlaceBar", ['dojo/_base/declare', 
                "./PlaceBarStore",
                "mui/util"], function(declare, TimeNavBarStore, util) {
	
		return declare('km.imeeting.PlaceBar', [TimeNavBarStore], {

			startup : function() {
				if (this._started)
					return;
				this.inherited(arguments);
			}
		});
});
