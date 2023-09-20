define([ "dojo/_base/declare",  "mui/dialog/Tip" ], 
		function(declare, Tip) {
	var claz = declare("mui.sys.evaluation.Loading", null, {
		
		loading_working: false,
		
		loading_processing: null,					
		
		loading_error: function(txt) {
    		Tip.fail({text:txt});
	    },
	    
	    loading_success: function(txt) {
    		Tip.success({text:txt});
	    },
		
		showLoading: function () {
			window._alert = window.alert;
			this.loading_processing = Tip.processing();
			if (this.loading_working)
				return false;
			this.loading_working = true;
			window.alert = this.loading_Alert;
			this.loading_processing.show();
			return true;
		},
		
		hideLoading: function () {
        	this.loading_working = false;
        	window.alert = window._alert;
        	this.loading_processing.hide(false);
		}
	});
	return claz;
});