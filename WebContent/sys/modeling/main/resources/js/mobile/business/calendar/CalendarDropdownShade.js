define(["dojo/_base/declare","dojo/_base/lang", "dijit/_WidgetBase","dojo/dom-attr","dojo/dom-class","dojo/io-query",
        "dojo/query","dojo/touch","dojo/dom-construct"],
        function(declare,lang,WidgetBase,domAttr,domClass,ioq,query,touch,domConstruct){
		  return declare("sys.modeling.main.calendar.calendarDropdownShade",[WidgetBase],{
			postCreate : function() {
				this.inherited(arguments);
				this.subscribe('/mui/calendar/bottomScroll', 'changeScrollIcon');
			},
			startup:function(){
				this.inherited(arguments);
				this.buildNode();
			},
			  buildNode:function(){
				  domConstruct.empty(this.domNode);
				  var shadeNode = domConstruct.create("div",{className:"muiDropDownShade"},this.domNode);
				  this.triangleNode = domConstruct.create("i",{className:"rotate"},shadeNode);
				  this.connect(this.triangleNode,touch.press,"nodeClick");
			  },
			  changeScrollIcon:function (obj,evt) {
				  if (evt && !isNaN(evt.y) && !isNaN(evt.top)){
					  if(evt.y == 0){
						  domClass.add(this.triangleNode,"rotate");
					  }else if(evt.top == 0){
						  domClass.remove(this.triangleNode,"rotate");
					  }
				  }
			  },
			  nodeClick :function (e) {
				  e.preventDefault();
				  e.stopPropagation();
				  if(e.target.classList.contains("rotate")){
					  this.getParent().scrollToWeek();
				  }else{
					  this.getParent().scrollToMonth();
				  }
			  }
		});
});