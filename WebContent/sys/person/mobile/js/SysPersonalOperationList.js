define([
    	'dojo/_base/declare', 
    	"dijit/_WidgetBase",
    	'mui/util',
    	"dojo/_base/array",
    	"dojo/dom-construct",
    	"dojo/_base/window",
    	"mui/i18n/i18n!sys-person:*"
    	], function(declare,_WidgetBase, 
    		util, array,
    		domConstruct, win, msg) {
    	
    	return declare("sys.person.mobile.js.SysPersonalOperationList", [_WidgetBase], {
    		
    		baseClass : "muiSubscribeBox",
    		
    		buildRendering : function() {
				this.inherited(arguments); 
				var msgBox = domConstruct.create("div" , {
					className : "muiMsgBox"
				}, this.domNode);
				var linkNode = domConstruct.create("a" , {
					href : "javascript:;",
					className : 'muiFontSizeM muiFontColorInfo',
					innerHTML : "<span class='mui mui-person-msg muiPersonIcon magenta'></span>" + msg['sysPersonMlink.myMsg'],
				},  msgBox);
				
				this.connect(linkNode, "click", function() {
					win.global.open(util.formatUrl("/sys/notify/mobile/index.jsp"), "_self");
				});
				
				this.buildList();
			},
			
			data : [],
			
			
			buildList : function() {
				if(this.data && this.data.length > 0) {
					var ulNode = domConstruct.create("ul", {
						className : "muiOperationList"
					}, this.domNode);
					array.forEach(this.data, function(item) {
							var li = domConstruct.create("li");
							var link = domConstruct.create("a", {
								href: "javascript:;",
								className : 'muiFontSizeS muiFontColorMuted'
							}, li);
							
							var iconBox = domConstruct.create("div", {
								className: "iconbox",
								innerHTML : "<span class='mui " + item.icon +"'></span>"
							}, link);
							
							if(item.num) {
								domConstruct.create("span", {
									className: "num",
									innerHTML : item.num 
								}, iconBox);
							}
							domConstruct.create("div", {
								innerHTML : item.text
							}, link);
							
//							array.forEach(item, function(obj) {
//								var linkNode =  domConstruct.create("li" , {
//									className : "muiPersonLinkItem",
//									innerHTML : "<a href='javascript:void(0)' >" +
//												"<span class='mui  " +  obj.icon + " muiPersonIcon'></span>" +
//												 util.formatText(obj.name)
//												 +"</a>"
//								}, ul);
//								
//								self.connect(linkNode, "click", function() {
//									win.global.open(util.formatUrl(obj.url), "_self");
//								});
//							});
							domConstruct.place(li,ulNode, "last");
							
							this.connect(link, "click", function() {
								win.global.open(util.formatUrl(item.url), "_self");
							});
					}, this);
				}
			}
    	});
});
    	