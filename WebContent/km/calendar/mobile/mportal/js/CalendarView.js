define([
  "dojo/_base/declare",
  "dojo/_base/array",
  "dojo/dom-style",
  "dojo/dom-geometry",
  "mui/calendar/CalendarView",
  "mui/calendar/CalendarContent",
  "mui/calendar/CalendarWeek",
  "mui/calendar/CalendarBottom",
  "mui/util"
], function(
  declare,
  array,
  domStyle,
  domGeometry,
  _CalendarView,
  CalendarContent,
  CalendarWeek,
  CalendarBottom,
  util
) {
  var claz = declare("mui.calendar.CalendarView", [_CalendarView], {
    changeDisplay: false,

    buildRendering: function() {
      this.inherited(arguments)
      var self = this
      util.loadCSS("/km/calendar/mobile/resource/css/list.css")
      util.loadCSS("/sys/mobile/css/themes/default/calendar.css", function() {
         self.handleDisplay()
      })
    },

    startup: function() {
      this.inherited(arguments)
      this.subscribe("/mui/list/loaded", "resizeView")
    },

    resize: function() {
      this.inherited(arguments)
      this._resizeView()
    },

    resizeView: function(widget) {
      var self = this
      if (widget) {
        var view = this.getEnclosingCalendarView(widget)
        if (view && view == this) {
          this._resizeView()
        }
      }
    },

    _resizeView: function() {
      var height = 0,
        self = this
      array.forEach(this.getChildren(), function(child) {
        if (
          self.defaultDisplay == "week" &&
          child.isInstanceOf(CalendarContent)
        ) {
          return
        }
        if (
          self.defaultDisplay == "month" &&
          child.isInstanceOf(CalendarWeek)
        ) {
          return
        }
        height += domGeometry.getMarginSize(child.domNode).h
      })
      domStyle.set(this.domNode, "height", height + "px")
    },

    getEnclosingCalendarView: function(widget) {
      if (widget && widget.getParent) {
        widget = widget.getParent()
        while (widget) {
          if (widget.isInstanceOf(_CalendarView)) {
            return widget
          }
          widget = widget.getParent()
        }
      }
      return null
    }
  })
  return claz
})
