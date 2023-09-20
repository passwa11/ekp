define([ "dojo/_base/declare", "mui/iconUtils",
				"mui/category/CategorySelection" , "mui/util"],
		function(declare, iconUtils, CategorySelection, util) {
			var selection = declare("mui.selectdialog.DialogSelection",[ CategorySelection ],{

				detailUrl : null,
				
				buildRendering:function() {
					this.inherited(arguments);
				},
				buildIcon : function(iconNode, item) {
					iconUtils.setIcon("mui mui-organization", null, null, null,
							iconNode);
				}
			});
			return selection;
		});