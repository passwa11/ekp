define(['dojo/_base/declare','sys/attend/mobile/resource/js/stat/NavItem'], 
		function(declare,NavItem ) {

	return declare('sys.attend.mobile.NavBarMixin', [], {
		itemRenderer : NavItem,
	});
});