define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/openProxyMixin"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util,openProxyMixin) {
	var item = declare("mui.list.item.TemplateItemMixin", [ItemBase,openProxyMixin], {
		
		tag:"div",
		
		baseClass:"muiTemplateCard",
		
		//名称
		label:"",
		//备注
		fdDesc:"",
		//文案
		optTxt:"",
		//操作链接
		href: "",
		
		buildRendering:function(){
			this.inherited(arguments);
			this.domNode = domConstruct.create(this.tag, { className : this.baseClass });
			this.buildInternalRender();
		},
		
		buildInternalRender : function() {
			//标题
			this.contentNode = domConstruct.create("div",{className:""},this.domNode);
			this.titleNode = domConstruct.create("p",{
				className : "",
				innerHTML : this.label
			},this.contentNode);
			if(this.fdDesc){
				domConstruct.create("span",{className:"", innerHTML:this.fdDesc},this.contentNode);
			}
			// 绑定点击事件
			if(this.href){
				//操作
				this.optNode = domConstruct.create("div",{className:"btn", innerHTML:this.optTxt},this.domNode);
				this.proxyClick(this.optNode, this.href, '_blank');
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