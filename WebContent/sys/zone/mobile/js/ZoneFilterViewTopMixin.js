define([ "dojo/_base/declare", "dojo/topic"],
		function(declare, topic) {

			return declare("sys.zone.mobile.ZoneFilterViewTopMixin", null, {

				postCreate : function() {
					this.inherited(arguments);
					this.subscribe("/mui/search/submit",
						"_toTop");
					this.subscribe("/mui/tabfilter/dialog/submit",
						"_toTop");
				},
				
			
				_toTop : function() {
					if(this.handleToTopTopic) {
						this.handleToTopTopic(null, {
							y : 0,
							time : 0
						})
					}
				}
			});
		});
