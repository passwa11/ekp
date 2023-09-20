define([ "dojo/_base/declare", "mui/form/Address", "mui/i18n/i18n!sys-organization:*"],
	function(declare, Address, Msg) {

	return declare("sys.org.eco.address", [ Address ], {
		
		jsURL : '/sys/organization/mobile/js/eco/muiOrgEcoAddressSglMixin.js!',
		
		subject : Msg['sysOrgElement.ecoDefName']

	});
});