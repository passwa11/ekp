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
   	"mui/i18n/i18n!hr-staff:hrStaffPersonExperience"
	], function(declare, array, domConstruct,domClass , domStyle , domAttr , ItemBase , util, _ListLinkItemMixin, on, query,Msg) {
	var item = declare("hr.staff.mobile.resource.js.item.HrStaffExperienceBonusMalusItemMixin", [ItemBase], {
		fdId: '',
		fdBonusMalusName: '',
		fdBonusMalusDate: '',
		fdBonusMalusType: '',
		fdMemo: '',
		buildRendering:function(){
			this.inherited(arguments);
			this.domNode.classList.add('ppc_c_border_content');
			domAttr.set(this.domNode,'data-display','none');
			var list = domConstruct.create("div",{className:'ppc_c_border_list'},this.domNode);
			var item = domConstruct.create("div",{className:'ppc_c_border_item'},list);
			var span = domConstruct.create("span",{innerHTML:this.fdBonusMalusName},item);
			var icon = domConstruct.create("i",{className:'ppc_c_detail_more-icon'},item);
			
			var content = domConstruct.create("div",{className:'ppc_c_content',style:'display:none;'},this.domNode);
			var clist0 = domConstruct.create("div",{className:'ppc_c_list'},content);
			domConstruct.create("div",{className:'ppc_c_list_head',innerHTML:Msg['hrStaffPersonExperience.bonusMalus.fdBonusMalusName']},clist0);
			domConstruct.create("div",{className:'ppc_c_list_body',innerHTML:this.fdBonusMalusName},clist0);
			var clist1 = domConstruct.create("div",{className:'ppc_c_list'},content);
			domConstruct.create("div",{className:'ppc_c_list_head',innerHTML:Msg['hrStaffPersonExperience.bonusMalus.fdBonusMalusDate']},clist1);
			domConstruct.create("div",{className:'ppc_c_list_body',innerHTML:this.fdBonusMalusDate},clist1);
			var clist2 = domConstruct.create("div",{className:'ppc_c_list'},content);
			domConstruct.create("div",{className:'ppc_c_list_head',innerHTML:Msg['hrStaffPersonExperience.fdBonusMalusType']},clist2);
			domConstruct.create("div",{className:'ppc_c_list_body',innerHTML:this.fdBonusMalusType},clist2);
			var clist3 = domConstruct.create("div",{className:'ppc_c_list'},content);
			domConstruct.create("div",{className:'ppc_c_list_head',innerHTML:Msg['hrStaffPersonExperience.fdMemo']},clist3);
			domConstruct.create("div",{className:'ppc_c_list_body',innerHTML:this.fdMemo},clist3);
			this.connect(span, "onclick", '_onClick');
			this.connect(icon,"onclick",'editTrackInfo');
		},
		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		},
		editTrackInfo:function(){
			var url = dojo.config.baseUrl+"hr/staff/hr_staff_person_experience/bonusMalus/hrStaffPersonExperienceBonusMalus.do?method=edit&&personInfoId="+this.personInfoId+"&fdId="+this.fdId;
			window.location.href=url;
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
				//domClass.remove(domNode,'down');
			}else{
				domStyle.set(content,'display','');
				//domClass.add(domNode,'down');
			}
		}
	});
	return item;
});