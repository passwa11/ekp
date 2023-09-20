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
   	"dojo/on"
	], function(declare, array, domConstruct,domClass , domStyle , domAttr , ItemBase , util, _ListLinkItemMixin,on) {
	var item = declare("hr.staff.mobile.resource.js.item.HrStaffPersonInfoLogItemMixin", [ItemBase], {
		fdId: '',
		fdCreateTime: '',
		fdIp: '',
		fdBrowser: '',
		fdEquipment: '',
		fdCreator: '',
		fdParaMethod: '',
		fdDetails: '',
		buildRendering:function(){
			this.inherited(arguments);
			this.domNode.classList.add('ppc_c_content');
			var list = domConstruct.create("div",{className:'ppc_c_list'},this.domNode);
			var head = domConstruct.create("div",{className:'ppc_c_list_head',innerHTML:this.fdCreateTime},list);
			var body = domConstruct.create("div",{className:'ppc_c_list_body',innerHTML:this.fdDetails},list);
		},
		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		}

	});
	return item;
});