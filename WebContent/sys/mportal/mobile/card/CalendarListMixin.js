define([
  "dojo/_base/declare",
  "mui/list/_TemplateItemListMixin",
  "./item/CalendarItemMixin",
  "dojo/_base/lang",
  "dojo/date",
  "mui/calendar/CalendarUtil"
], function(
  declare,
  _TemplateItemListMixin,
  CalendarItemMixin,
  lang,
  dateClz,
  cUtil
) {
  return declare("sys.mportal.CalendarListMixin", [_TemplateItemListMixin], {
    rowsize: 15,

    itemRenderer: CalendarItemMixin,

    generateList: function(items) {
      if (isNaN(parseInt(this.rowsize))) {
        this.rowsize = Number.MAX_VALUE
      }
      items = items || []
      if (items.length > this.rowsize) {
        var index = this.rowsize - items.length
        items.splice(index)
      }
      var tmpItems = []
      for (var i = 0; i < items.length; i++) {
        var item = items[i],
          start = cUtil.parseDate(item.start),
          end = cUtil.parseDate(item.end),
          diff = dateClz.difference(start, end, "day") + 1 //多少天
        for (var j = 0; j < diff; j++) {
          var tmpItem = lang.clone(item)
          if (j == 0 && j < diff - 1) {
            //跨天的第一天
            var __start = cUtil.parseDate(
                tmpItem.start,
                dojoConfig.Date_format
              ),
              __end =
                cUtil.formatDate(__start, dojoConfig.Date_format) + " 23:59"
            tmpItem.end = __end
          } else if (j != 0 && j == diff - 1) {
            //跨天的最后一天
            var __start = cUtil.parseDate(tmpItem.start, dojoConfig.Date_format)
            __start.setDate(__start.getDate() + j)
            __start =
              cUtil.formatDate(__start, dojoConfig.Date_format) + " 00:00"
            tmpItem.start = __start
          } else if (diff > 1) {
            //跨天的中间天数
            var __start = cUtil.parseDate(tmpItem.start, dojoConfig.Date_format)
            __start.setDate(__start.getDate() + j)
            var __end =
              cUtil.formatDate(__start, dojoConfig.Date_format) + " 23:59"
            __start =
              cUtil.formatDate(__start, dojoConfig.Date_format) + " 00:00"
            tmpItem.start = __start
            tmpItem.end = __end
          }
          var f1 =
              dateClz.compare(
                cUtil.parseDate(this.end),
                cUtil.parseDate(tmpItem.start)
              ) > 0
                ? true
                : false,
            f2 =
              dateClz.compare(
                cUtil.parseDate(tmpItem.end),
                cUtil.parseDate(this.start)
              ) >= 0
                ? true
                : false
          if (f1 && f2) tmpItems.push(tmpItem)
        }
      }
      tmpItems = this._sortItems(tmpItems)
      items = tmpItems
      this.inherited(arguments)
    },

    _sortItems: function(items) {
      items.sort(function(a, b) {
        var astart = cUtil.parseDate(a.start, dojoConfig.DateTime_format),
          bstart = cUtil.parseDate(b.start, dojoConfig.DateTime_format)
        return astart.getTime() - bstart.getTime()
      })
      var dateCache = {}
      for (var i = 0; i < items.length; i++) {
        var startStr = cUtil.formatDate(
          cUtil.parseDate(items[i].start, dojoConfig.Date_format),
          dojoConfig.Date_format
        )
        if (!dateCache[startStr]) {
          items[i].headerTitle = startStr
          dateCache[startStr] = true
        }
      }
      return items
    },

    _createItemProperties: function(/*Object*/ item) {
      var props = this.inherited(arguments)
      props["_parent"] = this
      props["id"] = null
      return props
    }
  })
})
