define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "dojo/request",
  "mui/util",
  "dojo/dom-construct",
  "sys/organization/mobile/js/inner/muiOrgInnerAddressMixin",
  "mui/i18n/i18n!sys-organization:*"
], function(declare, WidgetBase, request, util, domConstruct, Address, Msg) {
	var orgIndexItem = declare("mui.org.index.card", [WidgetBase, Address], {
		
		url: "/sys/organization/sys_org_element_external/sysOrgElementExternal.do?method=getMobileIndexHeaderData",
		
		showSelect : function() {
			
		},
		
	    buildRendering: function() {
	    	
	    	this.inherited(arguments);
	    	
	    	this.domNode = domConstruct.create('div');
	    	
	    	this.getData();
	    },
	    
	    getData: function() {
	    	
	    	var url = util.formatUrl(this.url);
	    	var self = this;
	    	request.get(url, {
	            headers: {'Accept': 'application/json'},
	            handleAs: 'json'
			}).then(function(data) {
				self.build(data);
			});
	    },
	    
	    build: function(data) {
	    	this.buildLeftNode(data);
	    	this.buildRightNode(data);
	    },
	    
	    buildLeftNode: function(data) {
	    	
	    	var muiSysOrgEcoIndexCardLeft = domConstruct.create("div" ,{
	    		className: 'muiSysOrgEcoIndexCardLeft'
	    	}, this.domNode);
	    	
	    	domConstruct.create("div" ,{
	    		className: 'num',
	    		innerHTML: data.ecoTotal + '<span>人</span>'
	    	}, muiSysOrgEcoIndexCardLeft);
	    	
	    	var btn = domConstruct.create("div" ,{
	    		className: 'btn',
	    		innerHTML: Msg['sysOrgEco.name'] + '<i class="fontmuis muis-to-right"></i>'
	    	}, muiSysOrgEcoIndexCardLeft);
	    	
	    	this.connect(muiSysOrgEcoIndexCardLeft, 'click', 'leftClick');

	    },
	    
	    buildRightNode: function(data) {
	    	var muiSysOrgEcoIndexCardRight = domConstruct.create("div" ,{
	    		className: 'muiSysOrgEcoIndexCardLeft'
	    	}, this.domNode);
	    	
	    	domConstruct.create("div" ,{
	    		className: 'num',
	    		innerHTML: data.total + '<span>人</span>'
	    	}, muiSysOrgEcoIndexCardRight);
	    	
	    	var btn = domConstruct.create("div" ,{
	    		className: 'btn',
	    		innerHTML: Msg['sysOrg.address.tree.new'] + '<i class="fontmuis muis-to-right"></i>'
	    	}, muiSysOrgEcoIndexCardRight);
	    	
	    	this.connect(muiSysOrgEcoIndexCardRight, 'click', 'rightClick');
	    },
	    
	    leftClick : function () {
	    	window.location.href = util.formatUrl('/sys/organization/mobile/sys_org_eco/list.jsp');
	    },
	    
	    rightClick : function () {
	    	this._selectCate();
	    }
	});

	return orgIndexItem;
})
