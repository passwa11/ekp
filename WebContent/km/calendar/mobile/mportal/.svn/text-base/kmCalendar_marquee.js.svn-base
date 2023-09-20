define(["mui/util"], function(util) {
  function portletLoad(params, load) {
    var rowsize = util.getUrlParameter(params, "rowsize")

    var html =
      '<div class="muiPortalCalendarMarquee"' +
      'data-dojo-type="km/calendar/mobile/mportal/js/JsonStoreCalendarMarquee"' +
      'data-dojo-mixins="km/calendar/mobile/mportal/js/CalendarMarqueeMixin"' +
      "data-dojo-props=\"url:'/km/calendar/km_calendar_main/kmCalendarMain.do?method=data&fdStart=!{start}&fdEnd=!{end}&rowsize=!{rowsize}',scopeType:'today'\">" +
      "</div>"

    html = util.urlResolver(html, {
      rowsize: rowsize,
      start: "!{start}",
      end: "!{end}"
    })

    load({
      html: html,
      cssUrls: ["/km/calendar/mobile/resource/css/calendarMarquee.css"]
    })
  }

  return {
    load: function(params, require, load) {
      portletLoad(params, load)
    },
    dependences: [
      "/km/calendar/mobile/mportal/js/JsonStoreCalendarMarquee.js",
      "/km/calendar/mobile/mportal/js/CalendarMarqueeMixin.js",
      "/sys/mobile/js/mui/calendar/CalendarUtil.js"
    ]
  }
})
