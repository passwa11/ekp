define(['dojo/_base/declare','mui/nav/NavItem','dojo/topic'], function(declare,NavItem,topic) {

	return declare('sys.attend.SysAttendNavItem', [NavItem], {

		defaultClickAction: function(e){
			topic.publish('sys.attend.navItem.changed',{
				value : this.value
			});
			topic.publish('/mui/list/toTop');
			return false;
		}

	});
	
	
});