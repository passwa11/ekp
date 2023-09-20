define(function(require, exports, module) {
	
	var AbstractRType = require('./abstract');
	var lang = require('lang!sys-ui');
	
	var MonthlyRType = AbstractRType.extend({
		
		rType : 'MONTHLY',
		
		initProps : function($super,props){
			$super(props);
			this.addStrategy('interval',{ text : lang['ui.recurrence.monthly'] });
			this.addStrategy('monthlyType');
			this.addStrategy('endType');
		}
		
	});
	
	module.exports = MonthlyRType;
	
});