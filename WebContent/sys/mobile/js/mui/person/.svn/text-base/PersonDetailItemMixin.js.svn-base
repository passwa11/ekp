define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/list/item/_ListLinkItemMixin",
	"dojo/date/locale",
	"mui/device/adapter"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util, _ListLinkItemMixin,locale,adapter) {
	
	var item = declare("mui.person.PersonDetailItemMixin", [ItemBase, _ListLinkItemMixin], {
		tag:"li",
		
		baseClass:"muiListItem muiPerson",
		
		href:'',
		
		buildRendering:function(){
			this.inherited(arguments);
			
			this.domNode = this.containerNode= this.srcNodeRef
				|| domConstruct.create(this.tag,{className:this.baseClass});
			
			//左侧头像
			var leftBar=domConstruct.create("div",{className:"muiPersonIcon"},this.containerNode);
			//domConstruct.create("img", { className: "muiPersonIconImg",src:this.src}, leftBar);
			var url = util.formatUrl(this.src);
			domConstruct.create("span", {className: "muiPersonIconImg",style:{background:'url(' + url +') center center no-repeat',backgroundSize:'cover',display:'inline-block'}}, leftBar);
			//右侧内容 
			var rightContent=domConstruct.create("div",{className:"muiPersonInfo"},this.containerNode);
			domConstruct.create("div", { className: "muiPersonName",innerHTML:this.name}, rightContent);
			if(this.fdParentName){
				domConstruct.create("div", { className: "muiParentNames",innerHTML:this.fdParentName}, rightContent);
			}
			
			//查看用户详情
			var self = this;
			this.connect(this.domNode,'click',function(){
				//查询用户 #169760
				var userId = "";
				//头像链接src中自带用户的fdId
				if(!!self.src && self.src.indexOf(self.fdModelId) > -1){
					userId = self.fdModelId;
				} else {
					userId = self.fdId;
				}
				// #49451与60570兼容处理
				adapter.openUserCard({ ekpId : userId });
			});
			
		},
		
		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},
	
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		},
		
		makeUrl:function(){
			if(!this.href){
				return '';
			}
			return this.inherited(arguments);
		}
	});
	return item;
});