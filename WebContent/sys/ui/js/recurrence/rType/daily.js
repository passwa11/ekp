define(function(require, exports, module) {
	
	var AbstractRType = require('./abstract');
	var lang = require('lang!sys-ui');
	
	var DailyRType = AbstractRType.extend({
		
		rType : 'DAILY',
		
		initProps : function($super,props){
			$super(props);
			this.addStrategy('interval',{ text : lang['ui.recurrence.daily'] });
			this.addStrategy('endType');
		}
		
	});
	
	module.exports = DailyRType;
	
});