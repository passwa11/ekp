define(["dojo/_base/declare", "dijit/_WidgetBase", "mui/util", "dojo/dom-construct", 'mui/dialog/Dialog',  "mui/i18n/i18n!sys-organization:*"], 
	function(declare, WidgetBase, util, domConstruct, Dialog, Msg) {
	var muiOrgEcoPersonBtn = declare("mui.org.eco.person.btn", [WidgetBase], {
		
		personUrl1 : "/sys/organization/sys_org_element_external/sysOrgElementExternalPerson.do?method=add&parent=",
		personUrl2 : "/sys/organization/sys_org_element_external/sysOrgElementExternalPerson.do?method=initInvitePage&fdId=",
		
	    buildRendering: function() {
	    	this.inherited(arguments);
	    	this.node1 = domConstruct.create("li", {
	    		innerHTML: Msg['sysOrgElementExternal.person.add']
	    	}, this.domNode);
	    	this.connect(this.node1, 'click', 'onClickNode1');

	    	if(this.inviteUrl && this.inviteUrl.length > 0) {
		    	this.node2 = domConstruct.create("li", {
		    		innerHTML: Msg['sysOrgElementExternal.person.invitation']
		    	}, this.domNode);
		    	this.connect(this.node2, 'click', 'onClickNode2');
	    	}
	    },
	    
	    startup: function() {
	    	var self = this;
	    	document.addEventListener('resume', function(e) {
	    		e.preventDefault();
	    		self._pageShow();
	    	}, false);
	    },
	    
	    onClickNode1: function() {
	    	var url = util.formatUrl(this.personUrl1);
			 window.location.href = url + this.parentId;
	    },
	    
	    onClickNode2: function() {
	    	 var url = util.formatUrl(this.personUrl2);
			 window.location.href = url + this.parentId;
	    }
	    
	});
	return muiOrgEcoPersonBtn;
})
