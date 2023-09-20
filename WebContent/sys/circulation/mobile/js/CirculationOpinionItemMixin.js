define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
		"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
		"mui/util", "mui/rating/Rating","mui/i18n/i18n!sys-circulation:sysCirculationMain.mobile" ], function(declare, domConstruct,
		domClass, domStyle, domAttr, ItemBase, util, Praise,Msg) {
	var item = declare("sys.circulation.CirculationOpinionItemMixin", [ ItemBase ], {
		
		tag : "table",
		
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
							className : 'muiSimple cirRecord'
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
			var titleHeadNode = domConstruct.create("tr", {
				className : "titleHead",
				onclick:"detail_expandRow(this);"
			}, this.containerNode);
			domConstruct.create("td", {
				colspan:"2",
				width:"88%",
				innerHTML : "<div class='tditem'><span class='item-order'>"+order +"</span><span class='item-text'>"+ this.fdBelongPersonDept+"-"+this.fdBelongPerson+"</span><span class='item-status status-"+this.docStatusVal+"'>"+this.docStatus+"</span></div>"
			}, titleHeadNode);
			
			domConstruct.create("td", {
				className : "displayOpt displayDown"
			}, titleHeadNode);
			
			var contentHeadNode1 = domConstruct.create("tr", {
				className : "contentTr"
			}, this.containerNode);
			domConstruct.create("td", {
				className : "muiTitle",
				innerHTML : "提醒次数"
			}, contentHeadNode1);
			domConstruct.create("td", {
				colspan:"2",
				innerHTML : this.fdRemindCount
			}, contentHeadNode1);
			
			var contentHeadNode2 = domConstruct.create("tr", {
				className : "contentTr"
			}, this.containerNode);
			domConstruct.create("td", {
				className : "muiTitle",
				innerHTML : "阅读时间"
			}, contentHeadNode2);
			domConstruct.create("td", {
				colspan:"2",
				innerHTML : this.fdReadTime
			}, contentHeadNode2);
			if(this.docStatus == "11" || this.docStatus == "12"){
				var contentHeadNode3 = domConstruct.create("tr", {
					className : "contentTr"
				}, this.containerNode);
				domConstruct.create("td", {
					className : "muiTitle",
					innerHTML : "撤回时间"
				}, contentHeadNode3);
				domConstruct.create("td", {
					colspan:"2",
					innerHTML : this.fdRecallTime
				}, contentHeadNode3);
			}
			var contentHeadNode4 = domConstruct.create("tr", {
				className : "contentTr"
			}, this.containerNode);
			domConstruct.create("td", {
				className : "muiTitle",
				innerHTML : "意见"
			}, contentHeadNode4);
			domConstruct.create("td", {
				colspan:"2",
				innerHTML : this.docContent
			}, contentHeadNode4);
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