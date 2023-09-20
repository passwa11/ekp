define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"dojo/topic",
   	"mui/list/item/_ListLinkItemMixin"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util, topic, _ListLinkItemMixin) {
	var item = declare("sys.ftsearch.item.SysFtsearchItemMixin", [ItemBase, _ListLinkItemMixin], {
		tag:"li",
		//简要信息
		summary:"",
		//创建时间
		created:"",
		//创建者
		creator:"",
		// 状态
		status:"",
		
		buildRendering:function(){
			this._templated = !!this.templateString;
			if (!this._templated) {
				this.domNode = this.containerNode = this.srcNodeRef
						|| domConstruct.create(this.tag, {
									className : 'muiComplexrItem'
								});
				this.contentNode = domConstruct.create(
										'div', {
											className : 'muiListItem'
										});
			}
			this.inherited(arguments);
			if (!this._templated) {
				if(this.isPersonName === "true") {
					this.buildInternalRenderPerson();
				} else {
					this.buildInternalRender();
				}
			}
			if(this.contentNode)
				domConstruct.place(this.contentNode,this.domNode);
			
		},
		
		buildInternalRenderPerson : function() {
			var personNode = domConstruct.create("div", 
					{className:"muiSearchPersonNode"}, this.contentNode);
			
			var imgNode = domConstruct.create("div", {
						 className:"muiSearchPersonImgNode"
						 }, personNode);
			var imgSpan = domConstruct.create("span", {
						className:"muiSearchPersonImg" 
					}
				    , imgNode);
			domStyle.set(imgSpan, "background-image", "url(" + util.formatUrl(this.headIcon) + ")");
			
			var rightNode = domConstruct.create("div", 
					{className:"muiSearchPersonInfo"}, personNode);
			if(this.label) {
				this.labelNode = domConstruct.create("h4",{
					className:"muiComplexrTitle muiSubject muiFontSizeM muiFontColorInfo",
					innerHTML: this.highlightHandle(this.label)},
					rightNode);
			}
			
			var infoList = domConstruct.create("ul", 
					{className:"muiSearchPersonInfoList muiListSummary muiFontSizeS"}, rightNode);
			for(var i = 1; i < 7; i ++) {
				var des = this["addField" + i + "Desc"],
				   content = this["addField" + i];
				if(des && content) {
					content = this.highlightHandle(util.formatText(content))
					domConstruct.create("li", 
							{ 
							innerHTML : "<span class='muiSearchPersonTitle'>" 
										+ util.formatText(des) + "  : </span>" + content
							}, infoList);
				}
			}
		},
		
		buildInternalRender : function() {
			var top = domConstruct.create("a",{className:"muiComplexrTop"},this.contentNode);
			var bottom = domConstruct.create("div",{className:"muiComplexrBottom muiListInfo muiFontSizeS muiFontColorMuted"},this.contentNode);
			if(this.label){
				this.labelNode = domConstruct.create("h4",{
					className:"muiComplexrTitle muiSubject muiFontSizeM muiFontColorInfo",
					innerHTML: this.highlightHandle(this.label)},
					top);
			}
			if(this.summary){
				this.summaryNode = 
					domConstruct.create("p",
						   {
						   className:"muiComplexrSummary muiListSummary muiFontSizeS",
						   innerHTML:this.highlightHandle(this.summary)
						   },top);
			}
			if(this.href){
				this.makeLinkNode(top);
			}
			if(this.creator){
				this.createdNode = domConstruct.create("div",
							{className:"muiComplexrCreator muiAuthor",
							innerHTML:this.highlightHandle(this.creator)
							},bottom);
			}
			if(this.created){
				this.createdNode = domConstruct.create("div",{className:"muiComplexrCreated",
					innerHTML:'<i class="mui mui-todo_date"></i>' + this.created},bottom);
			}
			if(this.count){
				this.countNode = domConstruct.create("div",{className:"mui mui-eyes mui-2x muiComplexrRead",innerHTML: "&nbsp;<span class='muiNumber'>"+(this.count?this.count:0)+'</span>'},bottom);
			}
			this.statusNode = domConstruct.create("div",{className:'muiComplexrStatus'},bottom);
			if(this.status){
				this.statusNode.innerHTML = this.status;
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
		

		highlightHandle : function(str) {
			if (str == null || str.length == 0)
				return "";
		 	return str.replace(/\[red\]/g, "<em class='mui-ftsearch-hightline'>")
		 					.replace(/\[\/red\]/g, "</em>");
		},
		
		_onClick: function(e){
			var self = this;
			topic.publish("/mui/searchbar/clickListItem", {
				cb : function(){
					self.makeTransition()
				}
			})
			this.inherited(arguments);
		}
	});
	return item;
});