define(["dojo/_base/declare", "dojo/_base/array", "dojo/json", "dojo/dom", "dojo/dom-construct",
        "dojo/dom-attr", "dijit/registry", "dojox/mobile/viewRegistry","dojox/mobile/ListItem", 
        "dojo/query", "dojo/NodeList-manipulate"], 
         function(declare, array, json, dom, domConstruct, domAttr, registry, viewRegistry, ListItem, query) {
	var claz = declare("sys.xform.mobile.controls.DetailTableValidator", [ ListItem ], {
		detailTableId:null,
		
		showRow:0,
		
		edit: false,//明细表整体是否可编辑
		
		curView:null,
		
		_needValidate:false,
		
		postCreate:function(){
			this.inherited(arguments);
		},
		
		startup:function(){
			this.inherited(arguments);
			this.dTableView = registry.byId('dt_wrap_' + this.detailTableId + '_view');
			this.view = registry.byId(this.curView);
			if(this.view._validation!=null){
				var self=this;
				this._needValidate = true;
				this.view._validation.addValidator('detailTable_required_' + this.detailTableId ,'{name} 中有必填项',function(){
					return self.validateDetailTable();
				});
			}

		},
		
		validateDetailTable:function(){
			if(this._needValidate){
				if(this.dTableView && this.view){
					if(this.dTableView.validate){
						if(!this.dTableView.validate()){
							return false;
						}
					}
				}
			}
			return true;
		}
	});
	return claz;
});