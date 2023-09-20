define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/list/item/_ListLinkItemMixin",
   	"dojo/on"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util, _ListLinkItemMixin, on) {
	var item = declare("sys.maindata.MainDataItemMixin", [ItemBase, _ListLinkItemMixin], {
		
		tag:"li",
		
		hrefTarget : "_top",
		
		buildRendering:function(){
			this._templated = !!this.templateString;
			if (!this._templated) {
				this.domNode = this.containerNode = this.srcNodeRef
						|| domConstruct.create(this.tag, {
									className : 'muiMainDataItem'
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
			var top = domConstruct.create("a",{className:"muiMainDataLink"},this.contentNode);
			this.labelNode = domConstruct.create("h4",{className:"muiMainDataSubject muiSubject",innerHTML:this.subject },top);
			if(this.url){
				this.href = this.url;
				this.makeLinkNode(top);
			}
			var bottom = domConstruct.create("div",{className:"muiListInfo"},top);
			for(var i = 0; i < 3; i++){
				var column = this['column'+ (i + 1)];
				if(column){
					domConstruct.create("div",{innerHTML:column},bottom);
				}
			}
		},
		
		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},
	
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		},
		
		makeLinkNode: function(linkNode) {
			linkNode.href = this.makeUrl();
			linkNode.target = "_top";
			on(linkNode, 'click', function(event) {
				event.preventDefault();
				return false;
			});
		}
		
	});
	return item;
});