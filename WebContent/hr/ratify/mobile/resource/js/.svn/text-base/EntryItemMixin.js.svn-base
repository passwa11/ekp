define([
    "dojo/_base/declare",
    "dojo/_base/array",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/list/item/_ListLinkItemMixin"
	], function(declare, array, domConstruct,domClass , domStyle , domAttr , ItemBase , util, _ListLinkItemMixin) {
	var item = declare("hr.ratify.mobile.EntryItemMixin", [ItemBase, _ListLinkItemMixin], {
		tag:"li",
		
		fdId: "",
		
		//头像
		headIcon:"",
		//姓名
		name: "",
		//是否扫码登记
		fdQRStatus: "",
		//录入时间
		creatTime: "",
		//预计入职时间
		fdPlanEntryTime: "",
		
		buildRendering:function(){
			this._templated = !!this.templateString;
			if (!this._templated) {
				this.domNode = this.containerNode = this.srcNodeRef
						|| domConstruct.create(this.tag, {
									className : 'muiCertificateItem'
								});
				this.contentNode = domConstruct.create(
										'div', {
											className : 'muiListItem'
										});
			}
			this.inherited(arguments);
			if (!this._templated)
				this.buildInternalRender();
			if(this.contentNode)
				domConstruct.place(this.contentNode,this.domNode);
			
		},
		buildInternalRender : function() {
			var wrap = domConstruct.create("a",{className:"muiCertificateWrap"},this.contentNode);
			
			this.href = 'hr/staff/hr_staff_entry/hrStaffEntry.do?method=view&fdId=' + this.fdId;
			this.makeLinkNode(wrap);
			
			var main = domConstruct.create("div",{className:"muiCertificateMain"},wrap);
			
			var left = domConstruct.create("div",{className:"muiCertificateMain_sec muiCertificateMain_sec_left"},main);
			
			var right = domConstruct.create("div",{className:"muiCertificateMain_sec muiCertificateMain_sec_right"},main);
			
			var top = domConstruct.create("div",{className:"muiCertificateMain_sec_left_top"},left);
			var bottom = domConstruct.create("div",{className:"muiCertificateMain_sec_left_bottom"},left);
						
			
			/************* 待入职列表  *************/
			if(this.status){
				var sign = domConstruct.create("span",{className:"muiCertificateSign",
					innerHTML:this.status},top);
				
				if(this.statusValue != ''){
					domAttr.set(sign, 'data-status', this.statusValue);
				} else {
					domAttr.set(sign, 'data-status', -1);
				}
				
			}
			if(this.name){
				domConstruct.create("span",{className:"muiCertificateTitle",innerHTML:this.name},top);
			}
			
			//显示头像
			var icon = "km/certificate/mobile/resource/images/defaulthead.jpg";
			var headIcon = domConstruct.create("span",{
				className:"muiCertificateItem_icon muiCertificateItem_icon_1" 
			},left);
			domStyle.set(headIcon, 'background-image', 'url(' + location.origin + dojoConfig.baseUrl + icon + ')');
				
			if(!this.fdQRStatus){
				this.certIcon = "km/certificate/mobile/resource/images/defaulthead.jpg";
			}
			if(this.creatTime){
				var borrowIcon = domConstruct.create("span",{
					className:"muiCertificateItem_icon muiCertificateItem_icon_2" 
				},right);
				
				domStyle.set(borrowIcon, 'background-image', 'url(' + location.origin + this.docCreateTime + ')');
			}
			if(this.fdPlanEntryTime){
				this.href = '/hr/staff/hr_staff_entry/hrStaffEntry.do?method=view&fdId=' + this.fdId;
				this.makeLinkNode(wrap);
				
				domConstruct.create("div",{className:"muiProcessCreator muiAuthor",innerHTML:this.fdPlanEntryTime},right);
			}

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