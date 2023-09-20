define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util","mui/history/listener",
   	"mui/calendar/CalendarUtil",
   	"mui/dialog/Tip",
   	"dojo/query",
   	"dojo/date",
	"dojo/date/locale" ,
	"dojo/_base/lang",
	"dojo/on","dojo/touch",
	'dojo/parser',"dojo/_base/array","dojox/mobile/TransitionEvent",
	"mui/i18n/i18n!sys-modeling-main"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util, listener,cutil,Tip,query,dateUtil,locale,
			lang,on,touch,parser,array,TransitionEvent,modelingLang) {
	
	var item = declare("sys.modeling.main.calendar.item.GroupItemMixin", [ItemBase], {
		
		tag: 'li',
		
		baseClass: 'muiCalendarItem',
		
		buildRendering:function(){
			this.inherited(arguments);
			this.domNode = this.containerNode= this.srcNodeRef || domConstruct.create(this.tag,{className:this.baseClass});
			this.buildItem();
		},
		
		buildItem : function () {
			var itemContent = domConstruct.create("div",{className : "muiItemContent"},this.domNode);
			var topNode = domConstruct.create("div",{className : "muiContentTop"},itemContent);
			domConstruct.create("div",{className : "muiContentLine",style : "background-color:"+this.color.backgroundColor},topNode);
			domConstruct.create("div", {className : "muiContentSubject",innerHTML :this.title||modelingLang['mui.modeling.no.content'],style : "color:"+this.color.fontColor}, topNode);
			var leftNode = domConstruct.create("div",{className : "muiContentLeft"},itemContent);
			var summaryContent = domConstruct.create("ul", {className : "muiContentTable" },leftNode);
			if(this.summary && this.summary.length>0){
				for (var i = 0; i < this.summary.length; i++) {
					if (this.summary[i].value){
						var summaryItem = domConstruct.create("li",{className : ""},summaryContent);
						domConstruct.create("div",{className:"muiSummaryTitle",innerHTML:this.summary[i].label},summaryItem);
						domConstruct.create("div",{className:"muiSummaryContent",innerHTML:this.summary[i].value},summaryItem);
					}
				}
			}
			var rightNode = domConstruct.create("div",{className : "muiContentRight"},this.domNode);
			var linkNode = domConstruct.create("i",{className : "muiContentLink"},rightNode);
			this.connect(linkNode, touch.press, '_onLinkClick');
		},


		_onLinkClick : function(){
			if(this.href){
				location.href = util.formatUrl(this.href);
			}
		},
		
		startup : function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},
	
		_setLabelAttr : function(text){
			if(text)
				this._set("label", text);
		},
		
		//url加入当前选中日期参数
		makeUrl : function(){
			var url=util.formatUrl(this.href);
			return url;
		}
		
		
	});
	return item;
});