define(['dojo/_base/declare','mui/nav/NavItem','dojo/topic'], function(declare,NavItem,topic) {

	return declare('sys.attend.SignStatNavItem', [NavItem], {

		defaultClickAction: function(e){
			topic.publish('sys.attend.signStat.navItem.changed',{
				value : this.value
			});
			topic.publish('/mui/list/toTop');
			return false;
		}

	});
	
	
});