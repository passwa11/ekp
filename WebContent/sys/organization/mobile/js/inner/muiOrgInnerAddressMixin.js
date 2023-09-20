define([ "dojo/_base/declare", "mui/form/Address"],
	function(declare, Address) {

	return declare("sys.org.inner.address", [ Address ], {
		
		jsURL : '/sys/organization/mobile/js/inner/muiOrgInnerAddressSglMixin.js!',
		
		subject : '内部组织'

	});
});