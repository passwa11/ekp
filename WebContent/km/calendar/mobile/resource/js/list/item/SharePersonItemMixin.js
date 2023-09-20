define([
    "dojo/_base/declare",
    "dojo/dom-construct",
    "dojo/dom-class",
	"dojo/dom-style",
	"dojo/dom-attr",
    "dojox/mobile/_ItemBase",
   	"mui/util",
   	"mui/calendar/CalendarUtil",
   	"mui/list/item/_ListLinkItemMixin",
   	"dojo/date",
   	"dojo/touch",
   	"dojo/query",
	"dojo/date/locale" ,
	"mui/i18n/i18n!sys-mobile",
	"mui/i18n/i18n!km-calendar:kmCalendarMain.allDay"
	], function(declare, domConstruct,domClass , domStyle , domAttr , ItemBase , util, cutil,_ListLinkItemMixin,dateUtil,touch,query,locale,msg,calendarMsg) {
	
	var item = declare("km.calendar.list.item.SharePersonItemMixin", [ItemBase, _ListLinkItemMixin], {
		
		tag:"li",
		baseClass:"muiSharePersonCard",
		
		buildRendering:function(){
			this.inherited(arguments);
			
			this.domNode = domConstruct.create(this.tag, { className : this.baseClass });
			this.buildInternalRender();
		},
		
		buildInternalRender : function() {
			
			this.contentNode = domConstruct.create("div",{className:"muiSharePersonItem"},this.domNode);
			this.contentContainer = domConstruct.create("div",{className:"muiSharePersonContainer"},this.contentNode);
			
			var checkboxNode = domConstruct.create("div",{className:"muiSelBox"},this.contentContainer);
			this.selectedNode = domConstruct.create("div",{className:"muiSelBoxMul"},checkboxNode);
			
			var iconNode = domConstruct.create("div",{className:"muiPersonIcon"},this.contentContainer);
			var src = "/sys/organization/image.jsp?orgId="+this.id+"&size=m";
			domConstruct.create("span", {style:{background:'url(' + util.formatUrl(src) +') center center no-repeat',backgroundSize:'cover',display:'inline-block'}}, iconNode);
			
			var infoNode = domConstruct.create("div",{className:"muiPersonInfo"},this.contentContainer);
			var personNode = domConstruct.create("div",{className:"muiPersonName"},infoNode);
			domConstruct.create("span",{className:"muiLabelName",innerHTML:this.name},personNode);
			domConstruct.create("div",{className:"muiPersonDept",innerHTML:this.dept},infoNode);

		},
		onSelectPerson : function() {
			if (!domClass.contains(this.selectedNode, "muiBoxSeled")) {
				domClass.add(this.selectedNode, "muiBoxSeled");
				domConstruct.empty(this.selectedNode);
				this.checkedIcon = domConstruct.create("i", {
					className : "mui mui-checked muiBoxSelected"
				}, this.selectedNode)
				domClass.add(this.domNode, "mblListItemSelected");
			} else {
				domClass.remove(this.selectedNode, "muiBoxSeled");
				domClass.remove(this.domNode, "mblListItemSelected")
				domConstruct.empty(this.selectedNode);
				
				//全选
				//TODO
				domClass.remove(query('.muiSharePersonHead .muiSelBoxMul')[0], "muiBoxSeled");
				domConstruct.destroy(query('.muiSharePersonHead .muiSelBoxMul i.muiBoxSelected')[0]);
			}
		},
		
		startup:function(){
			if(this._started){ return; }
			this.inherited(arguments);
		},
		makeTransition: function() {
			this.onSelectPerson()
		},
		_setLabelAttr: function(text){
			if(text)
				this._set("label", text);
		}
	});
	return item;
});