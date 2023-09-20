define([
    	'dojo/_base/declare', 
    	"dijit/_WidgetBase",
    	'mui/util',
    	"dojo/_base/window"
    	], function(declare,_WidgetBase, 
    		util, win) {
    	
    	return declare("sys.person.mobile.js.SysPersonalHeaderLinkMixin", null, {
    			
    		personId : "",
    		
    		_onClick : function() {
    			win.global.open(util.formatUrl("/sys/zone/sys_zone_personInfo/sysZonePersonInfo.do?method=view&fdId=" + this.personId), "_self");
			}
    	});
});
    	