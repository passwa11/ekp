define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	
   	"sys/mportal/mobile/OpenProxyMixin" 
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util, OpenProxyMixin) {
	var item = declare("sys.mportal.item.MixContentBItemMixin", [ItemBase, OpenProxyMixin], {
		
		tag:"li",
		
		//简要信息
		summary:null,
		
		//创建时间
		created:null,
		
		//创建者
		creator:null,
		
		//状态信息
		status:null,
		
		thumbs:null,
		
		buildRendering:function(){
			this.domNode = this.containerNode = this.srcNodeRef
				       || domConstruct.create(this.tag, {className : 'mui_ekp_portal_collaborate_item'});
			this.inherited(arguments);
			if (!this.templateString){
				this.contentNode = domConstruct.create(
						'div', {className : 'mui_ekp_portal_collaborate_list'});
				this.proxyClick(this.contentNode, this.href, '_blank');
				this.buildInternalRender();
				domConstruct.place(this.contentNode,this.domNode);
			}
		},

		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},
	
		buildInternalRender : function() {
			if(this.lock){
				var _lock = domConstruct.toDom("<div class='icoLock'><i class='mui mui-todo_lock'></i></div>");
				domConstruct.place(_lock, this.contentNode);
			}
			var top = domConstruct.create("div",{className:"mui_ekp_portal_collaborate_top"},this.contentNode);
			this.buildTopRender(top);
			var center = domConstruct.create("div",{className:"muiMixContentMiddle"},this.contentNode);
			this.buildCenterRender(center);
			var bottom = domConstruct.create("div",{className:"mui_ekp_portal_collaborate_bottom muiFontSizeS"},this.contentNode);
			this.buildBottomRender(bottom);
		},
		
		buildTopRender:function(top){
			if(this.icon){
				var topCreate = domConstruct.create("div",{className:"mui_ekp_portal_collaborate_icon"}, top);
				this.imgNode = domConstruct.create("img", { className: "mui_ekp_portal_collaborate_img",src:this.icon}, topCreate);
			}
			var topEmpInfo = domConstruct.create("div",{className:"mui_ekp_portal_collaborate_emp muiFontSizeS"}, topCreate);
			if(this.creator){
				this.CreatorNode=domConstruct.create("span",{className:"mui_ekp_portal_collaborate_creator",
					innerHTML: this.creator},topEmpInfo);
			}
			if(this.deptName){
				this.CreatorNode=domConstruct.create("span",{className:"mui_ekp_portal_collaborate_dept",
					innerHTML: this.deptName},topEmpInfo);
			}
			var titleCreate = domConstruct.create("div",{className:"mui_ekp_portal_collaborate_create"},top);

			if(this.label){
				var title = this.label;
				if(this.status){
					title = this.status + title;
				}
				domConstruct.create("div",{className:"mui_ekp_portal_collaborate_title muiFontSizeM", innerHTML:title},titleCreate);
			}
		},
		
		buildCenterRender:function(center){
			if(this.summary){
				domConstruct.create("p",{className:"muiMixContentSummary muiListSummary",innerHTML:this.summary},center);
			}
			var thum = domConstruct.create("p",{className:"muiMixThumb"},center);
			if(this.thumbs){
				var thumbDom = domConstruct.create("div",{className:"muiListThumb"},thum);
				var tmpThumbs = this.thumbs.split("|");
				var width = 100/tmpThumbs.length;
				for ( var i = 0; i < tmpThumbs.length; i++) {
					domConstruct.create('p', {
						style : 'background-image: url(' + util.formatUrl(util.setUrlParameter(tmpThumbs[i],"picthumb","small"))
								+ ');width:'+width+'%'
					}, thumbDom);
				}
			}
		},
		
		buildBottomRender:function(bottom){
		},
		
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		},
		
		hrefTarget: '_self',
		
		_selClass: "mblListItemSelected",
		
		_setSelectedAttr: function(selected){
			this.inherited(arguments);
			domClass.toggle(this.domNode, this._selClass, selected);
		}
	});
	return item;
});