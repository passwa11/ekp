define(["dojo/_base/declare", "dijit/_WidgetBase", "mui/util", "dojo/dom-style", "dojo/topic", "dojox/mobile/viewRegistry",
        "dojo/dom-class", "dojo/dom-construct", "dojo/request", "mui/device/adapter", "dojo/query", 'mui/dialog/Dialog', "mui/i18n/i18n!sys-organization:*"], 
	function(declare, WidgetBase, util, domStyle, topic, viewRegistry, domClass, domConstruct, request, adapter, query, Dialog, Msg) {
	var muiOrgEcoBtn = declare("mui.org.eco.btn", [WidgetBase], {
		
		deptUrl : "/sys/organization/sys_org_element_external/sysOrgElementExternalDept.do?method=add&parent=",
		
		personUrl : "/sys/organization/sys_org_element_external/sysOrgElementExternalPerson.do?method=add&parent=",
		
		postUrl : "/sys/organization/sys_org_element_external/sysOrgElementExternalPost.do?method=add&parent=",
		
		authUrl : "/sys/organization/sys_org_element_external/sysOrgElementExternalDept.do?method=getDeptAuth&fdId=",
		
	    buildRendering: function() {
	    	this.inherited(arguments);
	    	this.subscribe("/sys/org/eco/btn/setId", "setParentId");
	    	this.subscribe("/sys/org/eco/btn/hidden", "hiddenBtn");
	    	this.subscribe("/sys/org/eco/btn/show", "showBtn");
	    	this.subscribe("/sys/org/eco/btn/change", "getBtnAuth");
	    	this.setParentId(this.parentId);
	    	
	    	this.buildAddGroup();
	    	this.buildAddPost();
	    	this.buildAddPerson();
	    	
	    	var changeData = new Object();
	    	changeData.fdId = this.parentId;
	    	changeData.fdInviteUrl = "";
	    	changeData.fdOrgType = "1";
	    	this.getBtnAuth(changeData);
	    },
	    
	    startup: function() {
	    	var self = this;
	    	document.addEventListener('resume', function(e) {
	    		e.preventDefault();
	    		self._pageShow();
	    	}, false);
	    },
	    
	    buildAddGroup: function() {
	    	this.groupNode = domConstruct.create("div", {
	    		className : "muiOrgEcoAddGroupBtn",
	    		style : {
	    			'display' : 'none'
	    		}
	    	}, this.domNode);
	    	this.connect(this.groupNode, 'click', 'onClickGroup');
	    },
	    
	    buildAddPerson: function() {
	    	var single = "";
	    	if(this.fdOrgType == "1")
	    		single = "single";
	    	this.personNode = domConstruct.create("div", {
	    		className: "muiOrgEcoAddPersonBtn " + single,
	    		innerHTML: Msg['sysOrgElementExternal.add.person']
	    	}, this.domNode);
	    	this.connect(this.personNode, 'click', 'onClickPerson');
	    },
	    
	    buildAddPost: function() {
	    	var single = "";
	    	if(this.fdOrgType == "1")
	    		single = "single";
	    	this.postNode = domConstruct.create("div", {
	    		className: "muiOrgEcoAddPostBtn " + single,
	    		innerHTML: Msg['sysOrgElementExternal.add.post']
	    	}, this.domNode);
	    	this.connect(this.postNode, 'click', 'onClickPost');
	    },
	    
	    onClickGroup : function(){
	    	var url = util.formatUrl(this.deptUrl, true);
	    	adapter.open(url + this.parentId + "&fdOrgType=" + this.fdOrgType, "_blank");
	    },

		onClickPerson : function() {
			if(this.changeData && this.changeData.fdInviteUrl && this.changeData.fdInviteUrl.length > 0) {
				// 有邀请人员方式，需要使用弹窗的模式
				var html = '<ul class="add_person" data-dojo-type="sys/organization/mobile/js/eco/muiOrgEcoPersonBtn" data-dojo-props="parentId:\'' + this.parentId + '\'';
					html += ',inviteUrl:\'' + this.changeData.fdInviteUrl + '\'';
					html += '"></ul>';
				var options = {
					'title' : '',
					'element' : html,
					'scrollable' : false,
					'parseable' : true,
					'canClose' : false,
					'position' : 'bottom'
				};

				Dialog.element(options);
			} else {
				// 没有邀请人员方式，直接手动添加
				var url = util.formatUrl(this.personUrl);
				 window.location.href = url + this.parentId;
			}
			
		},
	    
	    onClickPost : function(){
	    	var url = util.formatUrl(this.postUrl);
	    	window.location.href = url + this.parentId;
	    },
	    
	    setParentId : function(id) {
	    	this.parentId = id;
	    },
	    
	    hiddenBtn : function() {
	    	domStyle.set(this.domNode, {
	    		'display' : 'none'
	    	});
	    },
	    
	    showBtn : function() {
	    	domStyle.set(this.domNode, {
	    		'display' : ''
	    	});
	    },
	    
	    getBtnAuth : function(changeData) {
	    	var self = this;
    		var url = this.authUrl + changeData.fdId;
    		var data = changeData;
	    	request.get(util.formatUrl(url), {
	            headers: {'Accept': 'application/json'},
	            handleAs: 'json'
			}).then(function(requestData) {
				self.changeBtn(data, requestData.isAuth);
			});
	    },
	    
	    changeBtn : function(changeData, isAuth) {
	    	if(isAuth) {
	    		this.changeData = changeData;
	    		domStyle.set(this.groupNode, {
	    			'display' : 'block'
	    		})
	    		var fdOrgType = changeData.fdOrgType;
	    		this.fdOrgType = fdOrgType;
	    		var label = Msg['sysOrgElementExternal.add.subDept'];
	    		if(fdOrgType == '1')
	    			label = Msg['sysOrgElementExternal.add.dept'];
	    		if(fdOrgType != '1') {
					domClass.remove(this.groupNode, 'single');
					domClass.remove(this.personNode, 'single');
					domStyle.set(this.personNode, {
		    			'display' : 'block'
		    		});
					domClass.remove(this.postNode, 'single');
					domStyle.set(this.postNode, {
		    			'display' : 'block'
		    		});
	    		} else {
	    			domClass.add(this.groupNode, 'single');
	    			domClass.add(this.personNode, 'single');
	    			domStyle.set(this.personNode, {
	    				'display' : 'none'
	    			});
	    			domClass.add(this.postNode, 'single');
	    			domStyle.set(this.postNode, {
	    				'display' : 'none'
	    			});
	    		}
	    		this.groupNode.innerHTML = label;
	    	} else {
	    		domStyle.set(this.groupNode, {
	    			'display' : 'none'
	    		});
	    		domStyle.set(this.personNode, {
	    			'display' : 'none'
	    		});
	    		domStyle.set(this.postNode, {
	    			'display' : 'none'
	    		});
	    	}
	    },
	    
	    // 仅支持钉钉上回退时刷新下列表数据
	    _pageShow : function(evt) {
	    	var listDom = query('.muiCateLists.muiAddressList.muiOrgEcoList')[0];
			var scrollView = viewRegistry.getEnclosingScrollable(listDom);
			topic.publish('/mui/list/onReload', scrollView);
    	}
	});
	return muiOrgEcoBtn;
})
