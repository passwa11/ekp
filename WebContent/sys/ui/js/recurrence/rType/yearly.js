define(function(require, exports, module) {
	
	var AbstractRType = require('./abstract');
	var lang = require('lang!sys-ui');
	
	var YearlyRType = AbstractRType.extend({
		
		rType : 'YEARLY',
		
		initProps : function($super,props){
			$super(props);
			this.addStrategy('interval',{ text : lang['ui.recurrence.yearly'] });
			this.addStrategy('endType');
		}
		
	});
	
	module.exports = YearlyRType;
	
});