define([
    "dojo/_base/declare",
    "dojo/_base/array",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/list/item/_ListLinkItemMixin",
   	"dojo/on",
   	"dojo/query",
   	"mui/i18n/i18n!hr-staff:hrStaffEmolumentWelfareDetalied"
	], function(declare, array, domConstruct,domClass , domStyle , domAttr , ItemBase , util, _ListLinkItemMixin,on,query,Msg) {
	var item = declare("hr.staff.mobile.resource.js.item.HrStaffSalaryAdjustRecordItemMixin", [ItemBase], {
		fdId: '',
		personInfoId: '',
		nameAccount: '',
		fdRelatedProcess: '',
		fdAdjustDate: '',
		fdBeforeEmolument: '',
		fdAdjustAmount: '',
		fdAfterEmolument: '',
		buildRendering:function(){
			this.inherited(arguments);
			this.domNode.classList.add('ppc_c_border_content');
			domAttr.set(this.domNode,'data-display','none');
			var list = domConstruct.create("div",{className:'ppc_c_border_list'},this.domNode);
			var item = domConstruct.create("div",{className:'ppc_c_border_item'},list);
			var span = domConstruct.create("span",{innerHTML:this.fdRelatedProcess},item);
			var icon = domConstruct.create("i",{className:'ppc_c_detail_more-icon'},item);
			
			var content = domConstruct.create("div",{className:'ppc_c_content',style:'display:none;'},this.domNode);
			var clist0 = domConstruct.create("div",{className:'ppc_c_list'},content);
			//domConstruct.create("div",{className:'ppc_c_list_head',innerHTML:Msg['hrStaffEmolumentWelfareDetalied.fdRelatedProcess']},clist0);
			//domConstruct.create("div",{className:'ppc_c_list_body',innerHTML:this.fdRelatedProcess},clist0);
			var clist1 = domConstruct.create("div",{className:'ppc_c_list'},content);
			domConstruct.create("div",{className:'ppc_c_list_head',innerHTML:Msg['hrStaffEmolumentWelfareDetalied.fdAdjustDate']},clist1);
			domConstruct.create("div",{className:'ppc_c_list_body',innerHTML:this.fdAdjustDate},clist1);
			var clist2 = domConstruct.create("div",{className:'ppc_c_list'},content);
			domConstruct.create("div",{className:'ppc_c_list_head',innerHTML:Msg['hrStaffEmolumentWelfareDetalied.fdBeforeEmolument']},clist2);
			domConstruct.create("div",{className:'ppc_c_list_body',innerHTML:this.fdBeforeEmolument},clist2);
			var clist3 = domConstruct.create("div",{className:'ppc_c_list'},content);
			domConstruct.create("div",{className:'ppc_c_list_head',innerHTML:Msg['hrStaffEmolumentWelfareDetalied.fdAdjustAmount']},clist3);
			domConstruct.create("div",{className:'ppc_c_list_body',innerHTML:this.fdAdjustAmount},clist3);
			var clist4 = domConstruct.create("div",{className:'ppc_c_list'},content);
			domConstruct.create("div",{className:'ppc_c_list_head',innerHTML:Msg['hrStaffEmolumentWelfareDetalied.fdAfterEmolument']},clist4);
			domConstruct.create("div",{className:'ppc_c_list_body',innerHTML:this.fdAfterEmolument},clist4);
			this.connect(icon, "onclick", '_onClick');
			this.connect(span,"onclick",'viewTrack');
		},
		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		},
		viewTrack:function(){
			window.location.href=dojo.config.baseUrl+this.fdRelatedProcess;
		},
		_onClick:function(evt){
			var domNode= evt.target;
			var domDiv = $(domNode).parents('div')[2];
			var display = domAttr.get(domDiv,'data-display'),
				newdisplay = (display == 'none' ? '' : 'none');
			domAttr.set(domDiv,'data-display',newdisplay);
			var content = query('.ppc_c_content',domDiv)[0];
			if(newdisplay == 'none'){
				domStyle.set(content,'display','none');
				domClass.remove(domNode,'down');
			}else{
				domStyle.set(content,'display','');
				domClass.add(domNode,'down');
			}
		}
	});
	return item;
});