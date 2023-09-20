define(["dojo/_base/declare", "dijit/_WidgetBase", "dojo/dom-construct", "dojo/request", "dojo/topic",
        "mui/util", "mui/form/Address", 'mui/dialog/Confirm', 'mui/dialog/Alert', "mui/util", "mui/i18n/i18n!sys-organization:*"], 
	function(declare, WidgetBase, domConstruct, request, topic, util, Address, Confirm, Alert, util, Msg) {
	var muiOrgEcoCard = declare("mui.org.eco.card", [WidgetBase, Address], {
		
		viewUrl : '/sys/organization/sys_org_element_external/sysOrgElementExternalDept.do?method=view&fdId=',
		
		authUrl : "/sys/organization/sys_org_element_external/sysOrgElementExternalDept.do?method=getDeptAuth&fdId=",
		
		_cateDialogPrefix: "__muiOrgEcoCardCate__",
		
		idField : "__muiOrgEcoCardField__",
		
		key: "muiOrgEcoCardKey",
		
		type: ORG_TYPE_PERSON,
		
		isMul: true,
		
	    buildRendering: function() {
	    	
	    	this.inherited(arguments);
	    	
	    	this.domNode = domConstruct.create('div');
	    	
	    	this.buildNode();
	    	
	    	this.subscribe("/sys/org/eco/card/reload", "reload");
	    },
	    
	    startup : function() {
	    	topic.publish("/mui/org/eco/admin/list/sendCardData");
	    },
	    
	    buildNode : function (){
	    	
	    	this.muiOrgEcoCard = domConstruct.create("div", {
	    		className: "muiOrgEcoCard"
	    	}, this.domNode);
	    	
	    	
	    	var muiOrgEcoCardfdName =  domConstruct.create("div", {
	    		className : "muiOrgEcoCardfdName",
	    	}, this.muiOrgEcoCard);
	    	
	    	this.fdName = domConstruct.create("div", {
	    		style : {
	    			'float' : 'left'
	    		},
	    	}, muiOrgEcoCardfdName);
	    	
	    	this.arrow = domConstruct.create("i", {
	    		className : "muiOrgEcoCardArrow fontmuis muis-to-right",
	    	}, muiOrgEcoCardfdName);
	    	
	    	this.connect(muiOrgEcoCardfdName, 'click', 'onClick');
	    	
	    	var muiOrgEcoCardfdName =  domConstruct.create("div", {
	    		className : "muiOrgEcoCardfdNum clearfix",
	    	}, this.muiOrgEcoCard);
	    	
	    	domConstruct.create("span", {
	    		innerHTML : Msg['sysOrgOrg.fdNo']
	    	}, muiOrgEcoCardfdName);
	    	
	    	this.fdNum = domConstruct.create("span", {
	    	}, muiOrgEcoCardfdName);
	    	
	    	var muiOrgEcoCardfdName =  domConstruct.create("div", {
	    		className : "muiOrgEcoCardfdAdmin",
	    	}, this.muiOrgEcoCard);
	    	
	    	domConstruct.create("span", {
	    		innerHTML : Msg['sysOrgElementExternal.authElementAdmin']
	    	}, muiOrgEcoCardfdName);
	    	
	    	this.fdAdmin = domConstruct.create("span", {
	    	}, muiOrgEcoCardfdName);
	    },
	    
	    reloadAdmin: function(adminNames) {
	    	if(adminNames) {
    	    	this.fdAdmin.innerHTML = adminNames;
	    	} 
	    },
	    
	    reload : function (data) {
	    	if(this.fdId != data.fdId) 
		    	if(data && data.isShow){
		    		this.fdId = data.fdId;
		    		this.fdName.innerHTML = data.label;
		    		this.fdNum.innerHTML = data.fdNo;
		    		if(data.fdAdmin) {
		    			this.fdAdmin.innerHTML = data.fdAdmin;
		    		} else {
		    			this.getBtnAuth(this.fdId);
		    		}
		    		this.muiOrgEcoCard.style.display = 'block';
		    	} else {
		    		this.muiOrgEcoCard.style.display = 'none';
		    		this.fdId = data.fdId;
		    	}
	    },
	    
	    getBtnAuth : function(fdId) {
	    	var self = this;
    		var url = this.authUrl + fdId;
	    	request.get(util.formatUrl(url), {
	            headers: {'Accept': 'application/json'},
	            handleAs: 'json'
			}).then(function(data) {
				self.fdAdmin.innerHTML = "";
				if(data.isAuth) {
					var addAdmin = domConstruct.create("span", {
						className : 'addAdmin',
						innerHTML : Msg['sysOrgElementExternal.authElementAdmin.select']
					}, self.fdAdmin);
					self.connect(addAdmin, 'click', 'addAdmin');
				}
			});
	    },
	    
	    onClick : function() {
	    	
	    	var url = this.viewUrl;
	    	
	    	window.location.href = util.formatUrl(url + this.fdId);
	    	
	    },
	    
	    addAdmin : function() {
	    	this._selectCate();
	    },
	    
	    returnDialog: function(srcObj , evt) {
	    	var arr = srcObj.cateSelArr;
	    	var curIds = "";
	    	var curNames = "";
	    	for(var i = 0; i < arr.length; i++) {
	    		if(i == arr.length - 1) {
	    			curIds += arr[i].fdId;
	    			curNames += arr[i].label;
	    		} else {
	    			curIds += arr[i].fdId + ";";
	    			curNames += arr[i].label + ";";
	    		}
	    	}
	    	var self = this;
	    	var url = "/sys/organization/sys_org_element_external/sysOrgElementExternalDept.do?method=editAdmin&ids="+curIds+"&id="+this.fdId;
			var callback = function(bool){
		    	if(bool){
		    		request.get(util.formatUrl(url), {
			            headers: {'Accept': 'application/json'},
			            handleAs: 'json'
					}).then(function() {
						var callback = function(){
							self.__doCloseDialog();
							self.reloadAdmin(curNames);
					    }
						Alert(Msg['sysOrgElementExternal.success'], null, callback);
					});
		    	}
		    }  
			Confirm(Msg['sysOrgElementExternal.comfirm'], null, callback);
	    }
	});
	return muiOrgEcoCard;
})
