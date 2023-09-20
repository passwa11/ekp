define([
    	'dojo/_base/declare', 
    	"dijit/_WidgetBase",
    	'mui/util',
    	"dojo/request",
    	"dojo/_base/array",
    	"dojo/dom-construct",
    	"dojo/_base/window"
    	], function(declare,_WidgetBase, 
    		util,  request, array,
    		domConstruct, win) {
    	
    	return declare("sys.person.mobile.js.SysPersonalLinks", [_WidgetBase], {
    		
    		buildRendering : function() {
				this.inherited(arguments); 
				this.source();
			},
			
			url : "/sys/person/sys_person_mlink_category/sysPersonMlinkCategory.do?method=configs",
			
			baseClass:'muiPersonListBox',
			
    		source : function() {
				var _url = util.formatUrl(this.url);
				var promise = request.get(_url, {
					handleAs : 'json'
				});
				var self = this;
				promise.response.then(function(data) {
					if(data && data.data.length > 0)
						self.render(data.data);
				});
			},
			
			
			render : function(data) {
				array.forEach(data, function(item) {
					if(item && item.length > 0) {
						var self = this;
						var ul = domConstruct.create("ul" , {
							className : "muiPersonListLink"
						});
						array.forEach(item, function(obj) {
							var linkNode =  domConstruct.create("li" , {
								className : "muiPersonLinkItem",
								innerHTML : "<a href='javascript:void(0)' class='muiFontSizeM muiFontColorInfo' >" +
											"<span class='mui  " +  obj.icon + " muiPersonIcon'></span>" +
											 util.formatText(obj.name)
											 +"</a>"
							}, ul);
							
							self.connect(linkNode, "click", function() {
								win.global.open(util.formatUrl(obj.url), "_self");
							});
						});
						domConstruct.place(ul,this.domNode, "last");
					}
				}, this);
			}
    	});
});
    	