define(["dojo/_base/declare","dojo/_base/lang", "dijit/_WidgetBase","dojo/dom-attr","dojo/dom-class","dojo/io-query",
        "dojo/query","dojo/touch","dojo/dom-construct","dojo/topic",  "mui/i18n/i18n!sys-modeling-main"],
        function(declare,lang,WidgetBase,domAttr,domClass,ioq,query,touch,domConstruct,topic,modelingLang){
		  return declare("sys.modeling.main.calendar.calendarHeadTip",[WidgetBase],{
			postCreate : function() {
				this.inherited(arguments);
				this.subscribe('/mui/calendar/valueChange', "changeCurrentDate");
				this.subscribe('/mui/calendar/bottomScroll', 'changeScrollIcon');
			},
			startup:function(){
				this.inherited(arguments);
				this.genBussItem();
			},
			
			genBussItem : function(date){
				domConstruct.empty(this.domNode);
				date = date || new Date();
				var weeks = [modelingLang['mui.modeling.sun'],modelingLang['mui.modeling.mon'],modelingLang['mui.modeling.tue'],modelingLang['mui.modeling.wed'],
					modelingLang['mui.modeling.thu'],modelingLang['mui.modeling.fri'],modelingLang['mui.modeling.sat']];
				var bussNode = domConstruct.create("div",{className:"muiCalendarTitle"},this.domNode);
				var leftNode = domConstruct.create("div",{className:"muiTitleLeft"},bussNode);
				var day = date.getDate();
				domConstruct.create("div",{className:"muiTitleDay",innerHTML : day>9?""+day:"0"+day},leftNode);
				var year = date.getFullYear();
				var month = date.getMonth()+1;
				domConstruct.create("div",{className:"muiTitleYear",innerHTML : year + "."+(month>9?""+month:"0"+month)},leftNode);
				var week = date.getDay();
				domConstruct.create("div",{className:"muiTitleWeek",innerHTML : weeks[week]},leftNode);

				var rightNode = domConstruct.create("div", {className : "muiTitleRight" },bussNode);
				var cancelNode = domConstruct.create("span",{className:"muiTitleCancel",innerHTML:modelingLang['mui.modeling.cancel']},rightNode);
				this.connect(cancelNode,"click","cancelSummaryPreview");
			},
			changeCurrentDate : function(evt,date){
				if(date){
					domConstruct.empty(this.domNode);
					this.genBussItem(date.currentDate);
				}
			},
			  changeScrollIcon:function (obj,evt) {
				  var dropdownShade = query(".muiDropDownShade i")[0];
				  if(evt && evt.y == 0){
					  domClass.add(dropdownShade,"rotate");
				  }else if(evt && evt.top == 0){
					  domClass.remove(dropdownShade,"rotate");
				  }
			  },
			  cancelSummaryPreview:function (e){
				  topic.publish("/sys/modeling/calendar/preview/cancel",this);
			  }
			
		});
});