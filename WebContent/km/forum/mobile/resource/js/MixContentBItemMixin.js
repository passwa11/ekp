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
				       || domConstruct.create(this.tag, {className : 'muiMixContentBItem'});
			this.inherited(arguments);
			if (!this.templateString){
				this.contentNode = domConstruct.create(
						'a', {className : 'muiListItem',href : 'javascript:;'});
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
			var top = domConstruct.create("div",{className:"mui_portal_km_forum_top"},this.contentNode);
			this.buildTopRender(top);
			if(this.label){
				var title = this.label;
//				if(this.status){
//					title =  title+this.status;
//				}
				var titleNode = domConstruct.create("div",{className:"mui_ekp_portal_km_forum_title",innerHTML:title},this.contentNode);
			//	domConstruct.create("h4",{className:"mui_ekp_portal_km_forum_title", innerHTML:title},titleNode);
			}
			var center = domConstruct.create("div",{className:"mui_ekp_portal_km_forum_middle"},this.contentNode);
			this.buildCenterRender(center);
			var bottom = domConstruct.create("div",{className:"mui_ekp_portal_km_forum_muiMixContentBottom"},this.contentNode);
			this.buildBottomRender(bottom);
		},
		
		buildTopRender:function(top){
			if(this.icon){
				var iconContainerNode = domConstruct.create('div',{ className:'mui_ekp_portal_km_forum_icon_container' },top);
				var personHeadIconNode = domConstruct.create('div',{ className:'mui_ekp_portal_km_forum_headicon' },iconContainerNode);
				domConstruct.create('img',{ src:util.formatUrl(this.icon), className:'mui_ekp_portal_km_forum_headicon_img' },personHeadIconNode);
			}
			var topCreate = domConstruct.create("div",{className:"muiMixContentCreate"},top);
			if(this.creator){
				this.CreatorNode=domConstruct.create("div",{className:"mui_ekp_portal_km_forum_header_base",
					innerHTML:"<span class='mui_ekp_portal_km_forum_creator'>" + this.creator + "</span>" + this.status},topCreate);
			}
		},
		
		buildCenterRender:function(center){
			if(this.summary){
				domConstruct.create("p",{className:"muiMixContentSummary muiListSummary",innerHTML:this.summary},center);
			}
			var thum = domConstruct.create("p",{className:"muiMixThumb"},center);
			if(this.thumbs){
				var thumbDom = domConstruct.create("div",{className:"mui_ekp_portal_km_forum_Thumb"},thum);
				var tmpThumbs = this.thumbs.split("|");
				var width = 100/tmpThumbs.length;
				var height = 100;
				if(tmpThumbs.length>=3){
					height = 80;
					width = 32;
					domConstruct.create('p', {
						className : "mui_portal_km_forum_image3",
						style : 'background-image: url(' + util.formatUrl(util.setUrlParameter(tmpThumbs[0],"picthumb","small"))
								+ ')'
					}, thumbDom);
					domConstruct.create('p', {
						className : "mui_portal_km_forum_image3_mid",
						style : 'background-image: url(' + util.formatUrl(util.setUrlParameter(tmpThumbs[1],"picthumb","small"))
								+ ')'
					}, thumbDom);
					domConstruct.create('p', {
						className : "mui_portal_km_forum_image3",
						style : 'background-image: url(' + util.formatUrl(util.setUrlParameter(tmpThumbs[2],"picthumb","small"))
								+ ')'
					}, thumbDom);
				};
				if(tmpThumbs.length==2){
					height = 100;
					width = 48;
					domConstruct.create('p', {
						className : "mui_portal_km_forum_image2_right",
						style : 'background-image: url(' + util.formatUrl(util.setUrlParameter(tmpThumbs[0],"picthumb","small"))
								+ ')'
					}, thumbDom);
					domConstruct.create('p', {
						className : "mui_portal_km_forum_image2_left",
						style : 'background-image: url(' + util.formatUrl(util.setUrlParameter(tmpThumbs[1],"picthumb","small"))
								+ ')'
					}, thumbDom);
				};
				if(tmpThumbs.length<=1){
					height = 150;
					domConstruct.create('p', {
						className : "mui_portal_km_forum_image1",
						style : 'background-image: url(' + util.formatUrl(util.setUrlParameter(tmpThumbs[0],"picthumb","small"))
								+ ')'
					}, thumbDom);
				};
//				for ( var i = 0; i < tmpThumbs.length; i++) {
//					domConstruct.create('p', {
//						style : 'background-image: url(' + util.formatUrl(util.setUrlParameter(tmpThumbs[i],"picthumb","small"))
//								+ ');width:'+width+'%' + ';height: '+height+'px;'
//					}, thumbDom);
//				}
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