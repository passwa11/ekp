define(["dojo/_base/declare", "dijit/_WidgetBase", "dojo/dom-construct", "dojo/request", "mui/util", "mui/i18n/i18n!sys-organization:*"], 
	function(declare, WidgetBase, domConstruct, request, util, Msg) {
	var muiOrgListHeader = declare("mui.org.list.header", [WidgetBase], {
		
		url : '/sys/organization/sys_org_element_external/sysOrgElementExternal.do?method=getMobileHeaderData',
		
	    buildRendering: function() {
	    	
	    	this.inherited(arguments);
	    	
	    	var totalLi = domConstruct.create('li', null, this.domNode);
	    	
	    	this.getData(totalLi);
	    },
	    
	    getData : function(totalLi){
	    	var url = util.formatUrl(this.url);
	    	var self = this;
	    	request.get(url, {
	            headers: {'Accept': 'application/json'},
	            handleAs: 'json'
			}).then(function(data) {
				self.buildDom(totalLi, data);
			})
	    },
	    
	    buildDom : function (totalLi, data){
	    	domConstruct.create('p', {
	    		innerHTML : data.totalNum
	    	}, totalLi);
	    	
	    	domConstruct.create('span', {
	    		innerHTML : Msg['sysOrgElementExternal.person.total']
	    	}, totalLi);
	    	
	    	var addLi = domConstruct.create('li', null, this.domNode);
	    	
	    	domConstruct.create('p', {
	    		innerHTML : data.addNum
	    	}, addLi);
	    	
	    	domConstruct.create('span', {
	    		innerHTML : Msg['sysOrgElementExternal.person.yesterday']
	    	}, addLi);
	    }
	});

	return muiOrgListHeader;
})
