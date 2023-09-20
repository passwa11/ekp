define([ 'dojo/_base/declare', 'dijit/_WidgetBase', 'dojo/dom-construct', 'mui/util', 'dojo/dom-attr','mui/i18n/i18n!sys-mportal:sysMportalPage.search'], 
		function(declare, WidgetBase, domConstruct, util, domAttr,msg) {

	var header = declare('sys.mportal.HeaderRobot', [ WidgetBase ], {

		buildRendering : function() {

			this.inherited(arguments);
			
			this.subscribe('/mui/mportal/header/logoChange', 'logoChange');
			
			this.logoDomNode = domConstruct.create('div', {
				className : 'mui_ekp_portal_log_container'
			}, this.domNode);
			
			if(this.logo){
				this.logoImg = domConstruct.create('img', {
					'src' : this.logo
				}, this.logoDomNode);
			}
			
			var box = domConstruct.create('div', {
				className : 'mui_ekp_portal_title_info_sec'
			}, this.domNode);
			
			var input = domConstruct.create('div', {
				className : 'mui_ekp_portal_title_info_sec_input'
			}, box);
			this.connect(input, 'click', 'toSearchPage');
			
			domConstruct.create('div', {
				className : 'mui_ekp_portal_title_info_sec_input_text muiFontSizeM muiFontColorMuted',
				innerHTML :  msg['sysMportalPage.search']
			}, input);
			
			domConstruct.create('i', {
				className : 'mui_ekp_portal_input_search fontmuis muis-search'
			}, box);
			
			if (this.itEnabled=='true') {

				var a = domConstruct.create('a', {
					className : 'mui_ekp_portal_input_bot'
				}, box);
				
				var robot = domConstruct.create('img', {
					src : util.formatUrl('/sys/mportal/mobile/css/imgs/search_bot.png')
				}, a);
				this.connect(robot, 'click', 'toRobotPage');
			}
		},
		
		logoChange : function(logoUrl) {

			if(logoUrl) {

				var logoUrl = util.formatUrl(logoUrl);

				if(this.logoImg){
					domAttr.set(this.logoImg,'src', logoUrl);
				} else {
					this.logoImg = domConstruct.create('img', {
						'src' : logoUrl
					}, this.logoDomNode);
				}
			}
			
		},
		
		toSearchPage : function(){
	    	
	    	if (this.searchHost != 'null' && this.searchHost != '') {
	    		
	    		location.href = this.searchHost;

	    	} else {
	    		
	    		location.href=util.formatUrl('/sys/ftsearch/mobile/index.jsp');
	    		
	    	}
	    	
	    },
		
	    toRobotPage : function(){
	    	
	    	location.href=util.formatUrl(this.itUrl,true);
			
		}
	});

	return header;
});