define([ "dojo/_base/declare", "dojo/dom-construct", "dojo/dom-class",
		"dojo/dom-style", "dojo/dom-attr", "dojox/mobile/_ItemBase",
		"mui/util", "mui/rating/Rating","mui/i18n/i18n!sys-circulation:sysCirculationMain.mobile" ], function(declare, domConstruct,
		domClass, domStyle, domAttr, ItemBase, util, Praise,Msg) {
	var item = declare("sys.circulation.CirculationOpinionItemOptMixin", [ ItemBase ], {
		
		tag : "table",
		
		fdId:"",
		
		fdOrder:"",
		
		fdBelongPerson:"",
		
		fdBelongPersonDept:"",
		
		buildRendering : function() {
			this._templated = !!this.templateString;
			if (!this._templated) {
				this.domNode = this.containerNode = this.srcNodeRef
						|| domConstruct.create(this.tag, {
							className : 'muiSimple'
						});
			}
			this.inherited(arguments);
			if (!this._templated)
				this.buildInternalRender();
		},
		buildInternalRender : function() {
			
			this.titleHeadNode = domConstruct.create("tr", {
				className : "titleHead"
			}, this.containerNode);
			
			var optTd = domConstruct.create("td", {
				
			}, this.titleHeadNode);
			this.selectArea = domConstruct.create('div', {
				'className' : 'muiCateSelArea'
			}, optTd);//用于占位
			 this.selectNode = domConstruct.create('div', {
				 'value':this.fdId,
				 'className' : 'muiCateSel muiCateSelMul'
			}, this.selectArea);
			 
			this.checkedIcon= domConstruct.create('i', {
					'className' : 'mui mui-checked muiCateSelected'
			}, this.selectNode);
			
			domStyle.set(this.checkedIcon,'display','none');
			
			domConstruct.create("td", {
				colspan:"2",
				width:"88%",
				innerHTML :this.fdBelongPersonDept+"-"+this.fdBelongPerson
			}, this.titleHeadNode);
			
			this.connect(this.titleHeadNode, "click", "_selectData");
		},
		_selectData:function(){
			var status = domStyle.get(this.checkedIcon,'display');
			if(status == 'none'){
				domStyle.set(this.checkedIcon,'display','');
				if(!domClass.contains(this.selectNode, 'muiCateSeled')) {
					domClass.add(this.selectNode,"muiCateSeled");
				}
			}else{
				 domClass.remove(this.selectNode,"muiCateSeled");
				 domStyle.set(this.checkedIcon,'display','none');
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