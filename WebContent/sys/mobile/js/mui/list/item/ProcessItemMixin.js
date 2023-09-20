define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/openProxyMixin",
   	"mui/i18n/i18n!sys-mobile:mui.model.fdCreateTime"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util,openProxyMixin, msg) {
	var item = declare("mui.list.item.ProcessItemMixin", [ItemBase,openProxyMixin], {
		tag:"li",
		
		baseClass:"muiProcessItem muiListItemCard",
		
		//流程简要信息
		summary:"",
		
		//创建时间
		created:"",
		
		//创建者
		creator:"",
		
		//创建人图像
		icon:"",
		
		//状态
		status:"",
		
		buildRendering:function(){
			this.inherited(arguments);
			this.contentNode = domConstruct.create(
					this.tag, {
						className : 'muiListItem'
					});
			this.buildInternalRender();
			
			domConstruct.place(this.contentNode,this.containerNode);
			
		},
		buildInternalRender : function() {
			
			var itemClass = this.href ? {}:{className:'lock'};
			this.contentNode = domConstruct.create('div', itemClass);
			
			var headNode = domConstruct.create("div",{className:"muiListItemTop"},this.contentNode); // figure
			
			// 标题
			var subject = domConstruct.create("div",{className:"muiFontSizeM muiListItemTitle",
			innerHTML: '<div class="muiListItemTitleInner">'  +this.label+ '</div>'},headNode);
			
			// 人员图标
			var personImgBoxNode = domConstruct.create("div",{className:"muiListItemPerImgBox"},headNode);
			
			//用户头像
			domConstruct.create("span", { className: "muiListItemPerImg",style:{background:'url(' + util.formatUrl(this.icon) +') center center no-repeat',
			backgroundSize:'cover',display:'inline-block'}}, personImgBoxNode);

			// 创建人
			if(this.creator){
				var creatorNode = domConstruct.create("div",{className:"muiListItemCreator muiFontSizeS muiFontColorMuted"},headNode);
				creatorNode.innerText = this.creator;
			}

			if(this.status){
				var statusNode = domConstruct.create("div",{className:"muiListItemStatus muiFontSizeS muiFontColorMuted"},headNode);
				statusNode.innerHTML = this.status;
			}
			
			//subhead
			//bottom
			var subhead = domConstruct.create("div",{className:"muiListItemBottom muiFontSizeS muiFontColorMuted"},this.contentNode);
			
			// 发起时间
			if(this.created){
				domConstruct.create("div",{className:'muiListItemExpTimeInfo',
				innerHTML:msg['mui.model.fdCreateTime']+':<span class="muiListItemExpTime"> '+this.created+'</span>'},subhead);
			}
			//信息
			if(this.summary){
				domConstruct.create("div",{className:'muiListItemMinorInfo',
				innerHTML:'<span class="muiListItemMinorItem"> '+this.summary+'</span>'},subhead);
			}
			
			if(this.href){
				this.proxyClick(this.contentNode, this.href, '_blank');
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