define([ "dojo/_base/declare", "mui/property/filter/FilterRadio"], 
		function(declare, FilterRadio) {
			return declare("mui.property.FilterCheckBox", [ FilterRadio ], {
				multi: true
			});
		});