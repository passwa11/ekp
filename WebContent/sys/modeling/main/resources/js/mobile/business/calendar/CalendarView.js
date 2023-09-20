define([
  "dojo/_base/declare",
  "dojo/_base/array",
  "dojo/dom-style",
  "dojo/dom-geometry",
  "mui/calendar/CalendarView",
  "mui/calendar/CalendarContent",
  "mui/calendar/CalendarWeek",
  "sys/modeling/main/resources/js/mobile/business/calendar/CalendarBottom",
  "sys/modeling/main/resources/js/mobile/business/calendar/CalendarMonth",
  "mui/util",
  "dojox/mobile/_css3",
  "dojo/dom-class",
  'dojox/mobile/View',
], function(
  declare,
  array,
  domStyle,
  domGeometry,
  _CalendarView,
  CalendarContent,
  CalendarWeek,
  CalendarBottom,
  CalendarMonth,
  util,
  css3,
  domClass,
  View
) {
  var claz = declare("sys.modeling.main.calendar.CalendarView", [_CalendarView], {
    changeDisplay: false,
    showMode:"",//显示视图，月视图：1，日视图：0
    bottomStatus:"",//日视图下，日周切换，周：week,日：day

    startup: function() {
      this.inherited(arguments);
      this.subscribe('/mui/calendar/monthComplete','handleDisplay');
      this.subscribe("/sys/modeling/calendar/switchView","switchView");
      this.subscribe("/sys/modeling/calendar/bottomStatus","bottomStatusChange");
    },

    handleDisplay : function(){
      var self = this;
      var calendarContent,calendarWeek,calendarBottom,calendarMonth;
      array.forEach(self.getChildren(), function(child) {
        if(child.isInstanceOf(CalendarContent) ){
          calendarContent = child;
        }
        if(child.isInstanceOf(CalendarWeek) ){
          calendarWeek = child;
        }
        if(child.isInstanceOf(CalendarBottom) ){
          calendarBottom = child;
        }
        if(child.isInstanceOf(CalendarMonth) ){
          calendarMonth = child;
        }
      });

      if(self.showMode == "0"){
        if(self.defaultDisplay == 'week'){
          calendarBottom.scrollToWeek();
          //使用scale(0)‘隐藏’元素
          domStyle.set(calendarContent.domNode,css3.add({
            transform : 'scale(0)'
          }));
        }
        if( !self.changeDisplay ){
          calendarBottom.unBindEvent()
          calendarBottom._unBindEvent();
          if(self.defaultDisplay == 'week'){
            //使用scale(0)‘隐藏’元素
            domStyle.set(calendarContent.domNode,css3.add({
              height : '0'
            },{
              transform : 'scale(0)'
            }));
          }else{
            calendarBottom.bindEvent();
            calendarBottom.publishStatus(true);
          }
        }
        domStyle.set(calendarMonth.domNode,css3.add({
          display : 'none'
        }));
      }else{
        calendarBottom.unBindEvent()
        calendarBottom._unBindEvent();
        calendarBottom.publishStatus(false);
        domClass.add(calendarBottom.domNode, "calendarMonthPreview");
        domStyle.set(calendarMonth.domNode,css3.add({
          display : 'block'
        }));
      }
    },
    switchDisplay : function(){
      var self = this;
      var calendarContent,calendarWeek,calendarBottom,calendarMonth;
      array.forEach(self.getChildren(), function(child) {
        if(child.isInstanceOf(CalendarContent) ){
          calendarContent = child;
        }
        if(child.isInstanceOf(CalendarWeek) ){
          calendarWeek = child;
        }
        if(child.isInstanceOf(CalendarBottom) ){
          calendarBottom = child;
        }
        if(child.isInstanceOf(CalendarMonth) ){
          calendarMonth = child;
        }
      });

      if(self.showMode == "0"){
        domStyle.set(calendarMonth.domNode,css3.add({
          display : 'none'
        }));
        domStyle.set(calendarContent.domNode,css3.add({
          display : 'block'
        }));
        domStyle.set(calendarWeek.domNode,css3.add({
          display : 'block'
        }));
        domClass.remove(calendarBottom.domNode, "calendarMonthPreview");
        if(this.bottomStatus == "day"){
          calendarBottom.unBindEvent()
          calendarBottom._unBindEvent();
          calendarBottom.bindEvent();
          calendarBottom.publishStatus(true);
        }else if (this.bottomStatus == "week" ){
          calendarBottom.unBindEvent()
          calendarBottom._unBindEvent();
          calendarBottom._bindEvent();
          calendarBottom.publishStatus(false);
        }
        calendarBottom.disconnects();
      }else{
        domClass.add(calendarBottom.domNode, "calendarMonthPreview");
        domStyle.set(calendarWeek.domNode,css3.add({
          display : 'none'
        }));
        domStyle.set(calendarContent.domNode,css3.add({
          display : 'none'
        }));
        domStyle.set(calendarMonth.domNode,css3.add({
          display : 'block'
        }));
        domStyle.set(calendarMonth.domNode,css3.add({
          transform : 'unset'
        }));
        calendarBottom._unBindEvent();
        calendarBottom.unBindEvent();
        calendarBottom.disconnects();
        calendarBottom.publishStatus(false);
      }
    },
    switchView:function (obj,data){
      if(this.getEnclosingCalendarView(obj) != this.getEnclosingCalendarView(this))
        return;
      this.showMode = data || "0";
      this.switchDisplay();
    },
    getEnclosingCalendarView: function(widget) {
      if (widget && widget.getParent) {
        while (widget) {
          if (widget.isInstanceOf(_CalendarView)) {
            return widget
          }
          widget = widget.getParent()
        }
      }
      return null
    },
    bottomStatusChange:function (obj,data){
      if(this.getEnclosingCalendarView(obj) != this.getEnclosingCalendarView(this))
        return;
      this.bottomStatus = data.status || "";
    }

  })
  return claz
})
