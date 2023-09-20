define(['dojo/_base/declare','./AuthenticateBase','mui/util'],
		function(declare,AuthenticateBase,util) {

	return declare('mui.authenticate.AuthenticateURL', [ AuthenticateBase ], {
		
		url : '/sys/mobile/js/mui/authenticate/AuthenticateURL.jsp',
		requestURL : '',
		requestMethod : '',
		
		formatUrl : function(){
			this.url = util.setUrlParameter(this.url,'requestURL',this.requestURL);
			this.url = util.setUrlParameter(this.url,'requestMethod',this.requestMethod);
		}
		
	});
});