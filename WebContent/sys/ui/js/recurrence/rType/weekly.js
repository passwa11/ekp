define(function(require, exports, module) {
	
	var AbstractRType = require('./abstract');
	var lang = require('lang!sys-ui');
	
	var WeeklyRType = AbstractRType.extend({
		
		rType : 'WEEKLY',
		
		initProps : function($super,props){
			$super(props);
			this.addStrategy('interval',{ text : lang['ui.recurrence.weekly'] });
			this.addStrategy('weeklyType');
			this.addStrategy('endType');
		}
		
	});
	
	module.exports = WeeklyRType;
	
});