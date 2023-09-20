define(['dojo/_base/declare','./AuthenticateBase','mui/util'],
		function(declare,AuthenticateBase,util) {

	return declare('mui.authenticate.AuthenticateShow', [ AuthenticateBase ], {
		
		url : '/sys/mobile/js/mui/authenticate/AuthenticateShow.jsp',
		
		roles : '',
		authAreaIds : '',
		baseEmptyShow : '',
		baseOrgIds : '',
		baseOrgElements : '',
		extendOrgIds : '',
		extendOrgElements : '',
		
		formatUrl : function(){
			this.url = util.setUrlParameter(this.url,'roles',this.roles);
			this.url = util.setUrlParameter(this.url,'authAreaIds',this.authAreaIds);
			this.url = util.setUrlParameter(this.url,'baseEmptyShow',this.baseEmptyShow);
			this.url = util.setUrlParameter(this.url,'baseOrgIds',this.baseOrgIds);
			this.url = util.setUrlParameter(this.url,'baseOrgElements',this.baseOrgElements);
			this.url = util.setUrlParameter(this.url,'extendOrgIds',this.extendOrgIds);
			this.url = util.setUrlParameter(this.url,'extendOrgElements',this.extendOrgElements);
		}

	});
});