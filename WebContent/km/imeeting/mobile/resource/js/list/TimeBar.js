define("km/imeeting/mobile/resource/js/list/TimeBar", ['dojo/_base/declare', 
				"km/imeeting/mobile/resource/js/list/TimeNavBar",
                "mui/util"], function(declare, TimeNavBar, util) {
	
		return declare('km.imeeting.TimeBar', [TimeNavBar], {

			startup : function() {
				if (this._started)
					return;
				this.inherited(arguments);
			}
		});
});
