define(["dojo/_base/declare", "dojo/_base/array", "dojo/json", "dojo/dom", "dojo/dom-construct", "dojo/dom-attr", "dijit/registry", "dojox/mobile/viewRegistry",
         "dojox/mobile/ListItem", "dojo/query", "dojo/NodeList-manipulate"], 
         function(declare, array, json, dom, domConstruct, domAttr, registry, viewRegistry, ListItem, query) {
	var claz = declare("sys.xform.mobile.controls.DetailTableItem", [ ListItem ], {
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
			this.dTableView = dom.byId('dt_wrap_' + this.detailTableId + '_view');
//			this.view = registry.byId(this.curView);
//			if(this.view.domNode.parentNode != this.dTableView.parentNode){//未初始化时
//				var contentDom = query("[KMSS_IsContentRow='1']",this.dTableView);
//				if(this.showRow>0 || contentDom.length>0){//当明细表初始需要默认新增一行时，或有明细表内某个修改权限时才校验
//					if(this.view._validation!=null){
//						var self=this;
//						this._needValidate = true;
//						this.view._validation.addValidator('detailTable_' + this.detailTableId ,'{name} 中有必填项',function(){
//							return self.validateDetailTable();
//						});
//					}
//				}
//			}
		},
		
		validateDetailTable:function(){
			if(this._needValidate){
				if(this.dTableView && this.view){
					if(this.view.domNode.parentNode != this.dTableView.parentNode){//未初始化时才校验
						var tabInfo = window.DocList_TableInfo?window.DocList_TableInfo['TABLE_DL_' + this.detailTableId]:null;
						var refrenceDoms=[];
						if(tabInfo){
							refrenceDoms = query("[KMSS_IsContentRow='1']",tabInfo.DOMElement);
							if(refrenceDoms.length>0){
								for (var i = 0; i < refrenceDoms.length; i++) {
									if(this.checkRequired(refrenceDoms[i].innerHTML)){
										return false;
									}
								}
							}else{
								for (var i = 0; i < tabInfo.cells.length; i++) {
									var tmlStr = tabInfo.cells[i].innerHTML;
									if(this.checkRequired(tmlStr)){
										return false;
									}
								}
							}
						}else{
							refrenceDoms = query("[KMSS_IsContentRow='1']",this.dTableView);//如果有数据，只校验数据，不校验模板
							if(refrenceDoms.length==0){
								refrenceDoms = query("[KMSS_IsReferRow='1']",this.dTableView);
							}
							if(refrenceDoms.length>0){
								for (var i = 0; i < refrenceDoms.length; i++) {
									if(this.checkRequired(refrenceDoms[i].innerHTML)){
										return false;
									}
								}
							}
						}
					}
				}
			}
			return true;
		},
		
		checkRequired:function(htmlStr){//是否校验成功
			var rtn=false;
			if(htmlStr.indexOf("required")>-1){
				var tmpDiv = domConstruct.create("div",{innerHTML:htmlStr});
				rtn = array.some(query("[data-dojo-props]",tmpDiv),function(tmpDom){
					var props=domAttr.get(tmpDom,"data-dojo-props");
					var propObj = json.parse("{" + props + "}");
					if(propObj["required"]==true){
						if(propObj["value"]!=null && propObj["value"]!="") return false;
						if(propObj['curIds']!=null && propObj['curIds']!='') return false;
						return true;
					} 
					return false;
				});
			}
			return rtn;
		}
	});
	return claz;
});