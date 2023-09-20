define([
  "dojo/_base/declare",
  "dojox/mobile/_ItemBase",
  "dojo/dom-construct",
  "dojo/dom-class",
  "dojo/dom-style",
  "dojo/date",
  "dojo/date/locale",
  "sys/mportal/mobile/OpenProxyMixin"
], function(
  declare,
  _ItemBase,
  domConstruct,
  domClass,
  domStyle,
  dateutil,
  locale,
  OpenProxyMixin
) {
  var item = declare(
    "sys.mportal.CalendarItemMixin",
    [_ItemBase, OpenProxyMixin],
    {
      baseClass: "muiPortalCalendarItem",

      buildRendering: function() {
        this.inherited(arguments)
        var linkdom = domConstruct.create(
          "a",
          {className: "muiPortalCalendarLink"},
          this.domNode
        )
        if (this.headerTitle) {
          domClass.add(this.domNode, "muiPortalCalendarFirstDateDom")
          var dObj = locale.parse(this.headerTitle, {
              selector: "time",
              timePattern: dojoConfig.Date_format
            }),
            dStr = null
          if (dateutil.compare(dObj, new Date(), "date") == 0) {
            dStr = "今日"
          } else {
            var tp = dojoConfig.locale == "zh-cn" ? "MM月dd日" : "MM-dd",
              dStr = locale.format(dObj, {selector: "time", timePattern: tp})
          }
          var headerdom = domConstruct.create(
              "div",
              {className: "muiPortalCalendarHeaderTitle"},
              linkdom
            ),
            headerTextdom = domConstruct.create(
              "span",
              {className: "muiPortalCalendarHeaderTitleText", innerHTML: dStr},
              headerdom
            )
        }
        var datedom = domConstruct.create(
          "div",
          {className: "muiPortalCalendarDate"},
          linkdom
        )
        footerdom = domConstruct.create(
          "div",
          {className: "muiPortalCalendarFooter"},
          linkdom
        )
        if (this.href) {
          this.proxyClick(linkdom, this.href, "_blank")
        }
        var _date = null
        if (this.allDay) {
          _date = "全天"
          domClass.add(this.domNode, "muiPortalCalendarAllday")
        } else {
          var format = dojoConfig.DateTime_format,
            _start = locale.parse(this.start, {
              selector: "time",
              timePattern: format
            }),
            _end = locale.parse(this.end, {
              selector: "time",
              timePattern: format
            })
          _start = locale.format(_start, {
            selector: "time",
            timePattern: dojoConfig.Time_format
          })
          _end = locale.format(_end, {
            selector: "time",
            timePattern: dojoConfig.Time_format
          })
          _date = _start + " ~ " + _end
        }
        datedom.innerHTML = _date
        var labelNode = null
        if (this.labelName) {
          labelNode = domConstruct.create(
            "span",
            {innerHTML: this.labelName, className: "muiPortalCalendarLabel"},
            footerdom
          )
        } else {
          labelNode = domConstruct.create(
            "span",
            {innerHTML: "我的日历", className: "muiPortalCalendarLabel"},
            footerdom
          )
        }
        domStyle.set(labelNode, "color", this.color)
        domConstruct.create(
          "span",
          {innerHTML: this.title, className: "muiPortalCalendarSubject"},
          footerdom
        )
      },

      _setLabelAttr: function(label) {
        if (label) this._set("label", label)
      }
    }
  )

  return item
})
