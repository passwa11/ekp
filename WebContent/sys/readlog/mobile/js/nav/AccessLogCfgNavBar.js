define(['dojo/_base/declare', 
        "mui/nav/NavBarStore",
        "mui/util",
        "sys/readlog/mobile/js/nav/AccessLogNavItem"], function(declare, NavBarStore, util, AccessLogNavItem) {
	
		return declare('sys.readlog.mobile.js.nav.AccessLogCfgNavBar', [NavBarStore], {

			url : "/sys/readlog/sys_read_log/sysReadLog.do?method=dataNav&fdModelName=!{modelName}&fdModelId=!{modelId}",

			modelName: null,
			
			modelId:null,
			
			itemRenderer:AccessLogNavItem,
			
			startup : function() {
				if (this._started)
					return;
				
				this.url = util.urlResolver(this.url,this);
				
				this.inherited(arguments);
			}
		});
});