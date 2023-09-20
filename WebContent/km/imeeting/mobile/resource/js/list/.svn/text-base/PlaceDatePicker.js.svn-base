define(['dojo/_base/declare', 'dojo/_base/config', 'mui/form/DateTime', 'dojo/date/locale', 'dojo/topic'], 
		function(declare, config, DateTime, locale, topic) {

	return declare("km.imeeting.PlaceDatePicker", [ DateTime ], {
		
		SELECT_CALLBACK : '/km/imeeting/navitem/selected',
		
		postCreate: function() {
			var self = this;
			
			self.inherited(arguments);

			topic.subscribe(self.VALUE_CHANGE, function() {
				topic.publish(self.SELECT_CALLBACK, self, {
					value: self.value,
					text: self.value
				});
			});
		}
		
	});
		
});