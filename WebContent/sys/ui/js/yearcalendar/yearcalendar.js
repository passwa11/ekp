define(function (require, exports, module) {

  var $ = require('lui/jquery');
  var Calendar = require('lui/yearcalendar/calendar');

  $.fn.extend({
    Calendar: Calendar.Component
  });

  var YearCalendar = function (el, options) {

    this.$el = el;

    var now = new Date();

    this.defaultOptions = {
      year: now.getUTCFullYear(),
      startDay: 1,
      lang: 'en-us',
      calendarClass: 'calendar',
      calendarHeadClass: 'calendar-head',
      calendarHeadMonthClass: 'calendar-head-month',
      calendarHeadWeekClass: 'calendar-head-week',
      calendarHeadWeekItemClass: 'calendar-head-week-item',
      calendarPageClass: 'calendar-page',
      calendarPageItemClass: 'calendar-page-item',
      renderCalendarWeekItem: function (day, dayLabel) {
        return '<span>' + dayLabel + '</span>';
      },
      renderCalendarPageItem: function (year, month, date, day) {
        return '<span>' + date + '</span>';
      },
      renderCalendarOuterPageItem: function (year, month, date, day) {
          return '<span>' + date + '</span>';
      },
      beforeCalendarPageItemClick: function (ctx, el, year, month, date, day) {
        console.log('beforePageItemClick', ctx, el, year, month, date, day);
        return true;
      },
      onCalendarPageItemClick: function (ctx, el, year, month, date, day) {
        console.log('onPageItemClick', ctx, el, year, month, date, day);
      }

    };

    this.options = $.extend({}, this.defaultOptions, options);

  };

  YearCalendar.prototype = {
    __initialize: function () {

      this.render();

    },
    clear: function () {
      this.$el.html('');
    },
    render: function () {

      var that = this;

      that.clear();

      var i, fm = $(document.createDocumentFragment());

      for (i = 1; i <= 12; i++) {

        var t = $('<div></div>');
        t.attr('data-month', i);

        t.Calendar({
          year: that.options.year,
          month: i,
          startDay: that.options.startDay,
          lang: that.options.lang,
          calendarClass: that.options.calendarClass,
          calendarHeadClass: that.options.calendarHeadClass,
          calendarHeadMonthClass: that.options.calendarHeadMonthClass,
          calendarHeadWeekClass: that.options.calendarHeadWeekClass,
          calendarHeadWeekItemClass: that.options.calendarHeadWeekItemClass,
          calendarPageClass: that.options.calendarPageClass,
          calendarPageItemClass: that.calendarPageItemClass,
          beforePageItemClick: that.options.beforeCalendarPageItemClick,
          renderWeekItem: that.options.renderCalendarWeekItem,
          renderPageItem: that.options.renderCalendarPageItem,
          renderOuterPageItem: that.options.renderCalendarOuterPageItem,
          onPageItemClick: that.options.onCalendarPageItemClick

        });

        fm.append(t);

      }

      that.$el.append(fm);

    }
  };

  module.exports = {
    Component: function (options) {

      return this.each(function (idx, el) {
        var yearCalendar = new YearCalendar($(el), options);
        yearCalendar.__initialize();
        return yearCalendar;
      });

    }
  };

});