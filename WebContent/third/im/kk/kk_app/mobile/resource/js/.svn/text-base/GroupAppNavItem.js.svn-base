define(['dojo/_base/declare','mui/nav/NavItem','dojo/topic'], function(declare,NavItem,topic) {

	return declare('third.kk.GroupAppNavItem', [NavItem], {

		defaultClickAction: function(e){
			topic.publish('/third/kk/groupApp/navItem/changed',{
				value : this.value,
				href:this.href
			});
			topic.publish('/mui/list/toTop');
			return false;
		}

	});
	
	
});