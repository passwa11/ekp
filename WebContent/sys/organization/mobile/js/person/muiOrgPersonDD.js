define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "mui/util",
  "dojo/dom-construct",
  "mui/device/adapter",
  "dojo/query",
  "dojo/dom-class",
  "mui/i18n/i18n!sys-organization:*"
], function(declare, WidgetBase, util, domConstruct, adapter, query, domClass, Msg) {
	var orgPersonDD = declare("mui.org.person.dd", [WidgetBase], {
		
	    buildRendering: function() {
	    	
	    	this.inherited(arguments);
	    	this.subscribe('/third/ding/ready', 'draw');
	    },
	    
	    draw : function() {
	    	
	    	var gapArr = query('.tableGap');
	    	
	    	domClass.add(gapArr[gapArr.length-1], 'gapBottom');
	    	
	    	var ldMemberDetails = domConstruct.create("div" ,{
	    		className: 'ld-member-details'
	    	}, this.domNode);
	    	
	    	var memberDetaileContact = domConstruct.create("div" ,{
	    		className: 'member-details-contact'
	    	}, ldMemberDetails);
	    	
	    	if(this.userDDId && this.corpId) {
	    		var messageItem = domConstruct.create("div" ,{
	    			className: 'item',
	    			innerHTML: '<div><div class="dingIcon fontmuis muis-message"></div></dvi>'
	    		}, memberDetaileContact);
	    		domConstruct.create("p" ,{
	    			innerHTML: Msg['sysOrgElementExternal.ding.message']
	    		}, messageItem);
	    		this.connect(messageItem, 'click', 'onClikcMessage');
	    		
	    		var callItem = domConstruct.create("div" ,{
	    			className: 'item',
	    			innerHTML: '<div><div class="dingIcon fontmuis muis-phone"></div></dvi>'
	    		}, memberDetaileContact);
	    		domConstruct.create("p" ,{
	    			innerHTML: Msg['sysOrgElementExternal.ding.call']
	    		}, callItem);
	    		this.connect(callItem, 'click', 'onClikcCall');
	    	}
	    	
	    	if(this.corpId) {
	    		var dingItem = domConstruct.create("div" ,{
	    			className: 'item',
	    			innerHTML: '<div><div class="dingIcon fontmuis muis-org-flash"></div></dvi>'
	    		}, memberDetaileContact);
	    		domConstruct.create("p" ,{
	    			innerHTML: 'Ding'
	    		}, dingItem);
	    		this.connect(dingItem, 'click', 'onClikcDing');
	    	}
	    },
	    
	    onClikcMessage : function() {
    		var options = new Object;
    		options.userId = this.userDDId;
    		options.ekpId = this.ekpId;
    		options.corpId = this.corpId;
    		adapter.openChat(options)
	    },
	    
	    onClikcCall : function() {
	    	var obj = new Array();
	    	obj.push(this.userDDId);
    		adapter.call(obj, this.corpId);
	    },
	    
	    onClikcDing : function() {
    		var options = new Object;
    		options.users = [];
			options.users.push(this.userDDId);
    		options.corpId = this.corpId;
    		adapter.createDing(options);
	    }
	});

	return orgPersonDD;
})
