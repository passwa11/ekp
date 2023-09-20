define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class","dojo/query",
		"dojo/dom-style", "dojox/mobile/_ItemBase",
		"mui/i18n/i18n!sys-circulation:sysCirculationMain.mobile" ], 
		function(declare, domConstruct, domClass, query, domStyle, ItemBase, Msg) {
	var item = declare("sys.circulation.CirculationOpinionItemMixin", [ ItemBase ], {
		
		tag : "ul",
		
		fdCirculationTime:"",
		
		fdOrder:"",
		
		fdBelongPerson:"",
		
		fdBelongPersonDept:"",
		
		docStatus:"",
		
		fdReadTime:"",
		
		fdWriteTime:"",
		
		fdRemindCount:"",
		
		fdRecallTime:"",
		
		docContent:"",
		
		fdSeries:"",
		
		buildRendering : function() {
			this._templated = !!this.templateString;
			if (!this._templated) {
				this.domNode = this.containerNode = this.srcNodeRef
						|| domConstruct.create(this.tag, {
							className : 'muiCirculationOpinionItem'
						});
			}
			this.inherited(arguments);
			if (!this._templated)
				this.buildInternalRender();
		},
		buildInternalRender : function() {
			var order=this.fdOrder;
			if(parseInt(this.fdOrder)< 10 ){
				order = "0"+order;
			}
			var itemNode = domConstruct.create("div", {
				className : "muiCirOpTitle",
				innerHTML : "<span class='item-order'>"+order +"</span><span class='item-text'>"+ (this.fdBelongPersonDept?this.fdBelongPersonDept+"-":"")+this.fdBelongPerson+"</span><span class='item-status status-"+this.docStatusVal+"'>"+this.docStatus+"</span><span class='displayOpt displayDown'></span>"
			}, this.containerNode);
			this.infoNode = domConstruct.create("div", {
				className : "muiCirInfo"
			}, this.containerNode);
			var remindTimesNode = domConstruct.create("div", {
				className : "muiRemindTimes",
				innerHTML : "<span>"+ Msg['sysCirculationMain.mobile.remindTimes']+"："+this.fdRemindCount+"</span>"
			}, this.infoNode);
			if(this.docStatusVal!="10"){
				var readTimeNode = domConstruct.create("span", {
					className : "muiReadTime",
					innerHTML : Msg['sysCirculationMain.mobile.readTime']+"："+this.fdReadTime
				}, remindTimesNode);
				var titleHeadNode = domConstruct.create("div", {
					innerHTML : (this.docContent ? this.docContent : Msg['sysCirculationMain.mobile.noOpinion']),
					className : (this.docContent ? "circulationOpinion opinion" : "opinion"),
				}, this.infoNode);
			}
			var self = this;
			this.defer(function(){
				self.connect(query(".displayOpt",itemNode)[0], 'onclick', self.displayOpt);
			},0);
		},

		displayOpt: function(evt) {
			evt.preventDefault();
			evt.stopPropagation();
			//避免ios kk 双击
			var nowTime = new Date().getTime();
	        var clickTime = this.cbtime;
	        if (clickTime != "undefined" && nowTime - clickTime < 500) {
	           return false;
	        }
	        this.cbtime = nowTime;
	        if(domClass.contains(evt.target, "displayDown")){
	        	domClass.add(evt.target,"displayUp");
	        	domClass.remove(evt.target,"displayDown");
	        	domStyle.set(this.infoNode,{
					'display': 'none'
				})
	        }else{
	        	domClass.add(evt.target,"displayDown");
	        	domClass.remove(evt.target,"displayUp");
	        	domStyle.set(this.infoNode,{
					'display': ''
				})
	        }
		},
		
		startup : function() {
			if (this._started) {
				return;
			}
			this.inherited(arguments);
		},

		_setLabelAttr : function(text) {
			if (text)
				this._set("label", text);
		}
	});
	return item;
});