define("mui/nav/StaticNavBar", [
                "./NavBar", 
                "dojo/_base/declare",
				"./_StaticNavBarMixin"
                ], function(NavBar, declare,
				StaticNavBarMixin) {
	return declare('mui.nav.NavBarStore', [NavBar, StaticNavBarMixin], {
		height:'4.4rem'
	});
	
});