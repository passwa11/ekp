define(["dojo/_base/declare", "dijit/_WidgetBase","dojo/dom-construct", "dojo/dom-style", "dojo/topic", "dojo/query", "dojox/mobile/viewRegistry", "mui/i18n/i18n!sys-organization:*"], 
	function(declare, WidgetBase, domConstruct, domStyle, topic, query, viewRegistry, Msg) {
	var muiOrgEcoAdminListSearch = declare("mui.org.eco.admin.list.search", [WidgetBase], {
		
	    buildRendering: function() {
	    	
	    	this.inherited(arguments);
	    	
	    	var sysOrgEcoSearchBox = domConstruct.create('div', {
				className : 'sysOrgEcoSearchBox',
			}, this.domNode);
	    	
	    	domConstruct.create('i', {
				className : 'fontmuis muis-search',
			}, sysOrgEcoSearchBox);
	    	
	    	this.input = domConstruct.create('input', {
	    		className : 'sysOrgEcoSearchInput',
	    		type : 'text',
	    		placeholder : Msg['sysOrgElementExternal.searchInput']
	    	}, sysOrgEcoSearchBox);
	    	this.connect(this.input, "keypress", "_onChange")
	    	
	    	this.searchClose = domConstruct.create('i', {
				className : 'searchClose fontmuis muis-epid-close',
			}, sysOrgEcoSearchBox);
	    	this.connect(this.searchClose, 'click', '_searchClose');
	    	
	    },
	    
	    _onChange : function(event) {
	    	if(event.keyCode == '13') {
	    		var key = this.input.value;
	    		if(key != "") {
	    			topic.publish('/mui/org/eco/admin/list/changeUrl', key);
	    			
	    			var listDom = query('.mblEdgeToEdgeList.sysOrgEcoSearchListItem')[0];
	    			var scrollView = viewRegistry.getEnclosingScrollable(listDom);
	    			topic.publish('/mui/list/onReload', scrollView);
	    			domStyle.set(this.searchClose, 'display', 'block');
	    		}
	    	} 
	    },
	    
	    _searchClose : function() {
	    	this.input.value = '';
	    	domStyle.set(this.searchClose, 'display', 'none');
	    	
	    	topic.publish('/mui/org/eco/admin/list/changeUrl', null, true);
	    	
	    	var listDom = query('.mblEdgeToEdgeList.sysOrgEcoSearchListItem')[0];
			var scrollView = viewRegistry.getEnclosingScrollable(listDom);
			topic.publish('/mui/list/onReload', scrollView);
	    }
	    
	});
	return muiOrgEcoAdminListSearch;
})
