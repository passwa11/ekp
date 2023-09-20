define(function (require, exports, module) {

  var $ = require("lui/jquery");

  var LABEL_MONTHS = [
                      {'en-us': 'January', 'zh-cn': '一月', 'zh-hk': '一月', 'ja-jp': '一月'},
                      {'en-us': 'February', 'zh-cn': '二月', 'zh-hk': '二月', 'ja-jp': '二月'},
                      {'en-us': 'March', 'zh-cn': '三月', 'zh-hk': '三月', 'ja-jp': '三月'},
                      {'en-us': 'April', 'zh-cn': '四月', 'zh-hk': '四月', 'ja-jp': '四月'},
                      {'en-us': 'May', 'zh-cn': '五月', 'zh-hk': '五月', 'ja-jp': '五月'},
                      {'en-us': 'June', 'zh-cn': '六月', 'zh-hk': '六月', 'ja-jp': '六月'},
                      {'en-us': 'July', 'zh-cn': '七月', 'zh-hk': '七月', 'ja-jp': '七月'},
                      {'en-us': 'August', 'zh-cn': '八月', 'zh-hk': '八月', 'ja-jp': '八月'},
                      {'en-us': 'September', 'zh-cn': '九月', 'zh-hk': '九月', 'ja-jp': '九月'},
                      {'en-us': 'October', 'zh-cn': '十月', 'zh-hk': '十月', 'ja-jp': '十月'},
                      {'en-us': 'November', 'zh-cn': '十一月', 'zh-hk': '十一月', 'ja-jp': '十一月'},
                      {'en-us': 'December', 'zh-cn': '十二月', 'zh-hk': '十二月', 'ja-jp': '十二月'}
	];
  var LABEL_WEEKS = [
                 	{'en-us': 'Sun', 'zh-cn': '日', 'zh-hk': '日', 'ja-jp': '日'}, 
                 	{'en-us': 'Mon', 'zh-cn': '一', 'zh-hk': '一', 'ja-jp': '月'}, 
                 	{'en-us': 'Tue', 'zh-cn': '二', 'zh-hk': '二', 'ja-jp': '火'}, 
                 	{'en-us': 'Wed', 'zh-cn': '三', 'zh-hk': '三', 'ja-jp': '水'}, 
                 	{'en-us': 'Thu', 'zh-cn': '四', 'zh-hk': '四', 'ja-jp': '木'}, 
                 	{'en-us': 'Fri', 'zh-cn': '五', 'zh-hk': '五', 'ja-jp': '金'}, 
                 	{'en-us': 'Sat', 'zh-cn': '六', 'zh-hk': '六', 'ja-jp': '土'}
                   ];

  var CALENDAR_PAGE_ITEM_WIDTH_PRECENT = 14.28571;


  function formatInt(i) {

    return i < 10 ? '0' + i : '' + i;

  }

  function getDays(year, month) {

    var t;

    switch (month) {
      case 1:
      case 3:
      case 5:
      case 7:
      case 8:
      case 10:
      case 12:
        t = 31;
        break;
      case 4:
      case 6:
      case 9:
      case 11:
        t = 30;
        break;
      case 2:
        t = (year % 4 == 0 || year % 400 == 0) ? 29 : 28;
        break;
      default:
        t = 31;
        break;

    }

    return t;

  }

  var CalendarPage = function (el, options) {
    this.$el = el;

    var now = new Date();

    this.defaultOptions = {
      year: now.getUTCFullYear(),
      month: now.getUTCMonth() + 1,
      startDay: 1,
      lang: 'en-us',
      calendarClass: 'calendar',
      calendarHeadClass: 'calendar-head',
      calendarHeadMonthClass: 'calendar-head-month',
      calendarHeadWeekClass: 'calendar-head-week',
      calendarHeadWeekItemClass: 'calendar-head-week-item',
      calendarPageClass: 'calendar-page',
      calendarPageItemClass: 'calendar-page-item',
      renderWeekItem: function (day, dayLabel) {
        return '<span>' + dayLabel + '</span>';
      },
      renderPageItem: function (year, month, date, day) {
        return '<span>' + date + '</span>';
      },
      renderOuterPageItem: function (year, month, date, day) {
        return '<span>' + date + '</span>';
      },
      beforePageItemClick: function (ctx, el, year, month, date, day) {
        console.log('beforePageItemClick', ctx, el, year, month, date, day);
        return true;
      },
      onPageItemClick: function (ctx, el, year, month, date, day) {
        console.log('onPageItemClick', ctx, el, year, month, date, day);
      }
    };

    this.options = $.extend({}, this.defaultOptions, options);

  };

  CalendarPage.prototype = {
    __initialize: function () {

      if (!this.$el.hasClass(this.options.calendarClass)) {
        this.$el.addClass(this.options.calendarClass);
      }

      this.render();

    },
    clean: function () {
      this.$el.html('');
    },
    renderCalendarHead: function () {

      var that = this;

      var calendarHead = $('<div class="' + that.options.calendarHeadClass + '"></div>');
      var calendarHeadMonth = $('<div class="' + that.options.calendarHeadMonthClass + '">' + (LABEL_MONTHS[that.options.month - 1][that.options.lang] || LABEL_MONTHS[that.options.month - 1]['en-us']) + '</div>');
      calendarHead.append(calendarHeadMonth);

      var calendarHeadWeek = $('<div class="' + that.options.calendarHeadWeekClass + '"></div>');
      
      for(var i = 0; i < 7; i++){
    	  
    	  var calendarHeadWeekItem = $('<div class="' + that.options.calendarHeadWeekItemClass + '"></div>');
    	  
    	  var idx = (i + that.options.startDay) % 7;
    	  var item = LABEL_WEEKS[idx][that.options.lang] || LABEL_WEEKS[idx]['en-us'];
    	  
    	  calendarHeadWeekItem.append(that.options.renderWeekItem && that.options.renderWeekItem(idx, item));
    	  calendarHeadWeek.append(calendarHeadWeekItem);
    	  
      }

      calendarHead.append(calendarHeadWeek);

      that.$el.append(calendarHead);

    },
    renderCalendarPage: function () {

      var that = this;

      var i, days = getDays(that.options.year, that.options.month);
      var t = new Date(that.options.year + '/' + formatInt(that.options.month) + '/01').getDay();
      var $fm = $(document.createDocumentFragment());

      ///////////////////////////////////
      // 补充上个月日期
      ///////////////////////////////////
      var lYear = that.options.year;
      var lMonth = that.options.month;
      
      if(lMonth - 1 <= 0){
    	  lYear -=1 ;
    	  lMonth = 12;
      }else{
    	  lMonth--;
      }
      
      var lDays = getDays(lYear, lMonth);
      var _i, _j = 0, _t = (t + 7 - that.options.startDay) % 7;
      
      
      for(_i = _t; _i > 0; _i--){
    	  
          var calendarPageItem = $(
                  '<div class="' + that.options.calendarPageItemClass + '" ' +
                  'data-year="' + lYear + '" ' +
                  'data-month="' + lMonth + '" ' +
                  'data-date="' + (lDays - _i + 1) + '" ' +
                  'data-day="' + ((that.options.startDay +  _j) % 7) + '" ' +
                  'data-type="pre-month">' +
                  '</div>'
                );
          
          calendarPageItem.append(that.options.renderOuterPageItem(lYear, lMonth, (lDays - _i + 1), ((that.options.startDay +  _j) % 7)));
          
          _j++;

          $fm.append(calendarPageItem);
    	  
    	  
      }
      
      ///////////////////////////////////
      

      for (i = 1; i <= days; i++) {

        var calendarPageItem = $(
          '<div class="' + that.options.calendarPageItemClass + '" ' +
          'data-year="' + that.options.year + '" ' +
          'data-month="' + that.options.month + '" ' +
          'data-date="' + i + '" ' +
          'data-day="' + t + '">' +
          '</div>'
        );

        calendarPageItem.append((that.options.renderPageItem && that.options.renderPageItem(
          that.options.year,
          that.options.month,
          i,
          t
        )));

//        if (i == 1) {
//          calendarPageItem.css('margin-left', CALENDAR_PAGE_ITEM_WIDTH_PRECENT * ((t + 7 - that.options.startDay) % 7) + '%');
//        }

        t = (t + 1) % 7;

        $fm.append(calendarPageItem);

      }
      
      
      ///////////////////////////////////
      // 补充下个月日期
      ///////////////////////////////////
      var nYear = that.options.year;
      var nMonth = that.options.month;
      
      if(nMonth + 1 > 12){
    	  nYear +=1 ;
    	  nMonth = 1;
      }else{
    	  nMonth++;
      }
      
      var k, nDays = 42 - _t - days;
      
      for(k = 1; k <= nDays; k++){
    	  
          var calendarPageItem = $(
                  '<div class="' + that.options.calendarPageItemClass + '" ' +
                  'data-year="' + nYear + '" ' +
                  'data-month="' + nMonth + '" ' +
                  'data-date="' + k + '" ' +
                  'data-day="' + t + '" ' +
                  'data-type="next-month">' +
                  '</div>'
                );
          
          calendarPageItem.append(that.options.renderOuterPageItem(nYear, nMonth, k, t));
          
          t = (t + 1) % 7;

          $fm.append(calendarPageItem);
    	  
      }
      
      ///////////////////////////////////
      
      var calendarPage = $('<div class="' + that.options.calendarPageClass + '"></div>');
      calendarPage.append($fm);
      calendarPage.on('click', '.' + that.options.calendarPageItemClass, function () {

        var year = parseInt($(this).attr('data-year'));
        var month = parseInt($(this).attr('data-month'));
        var date = parseInt($(this).attr('data-date'));
        var day = parseInt($(this).attr('data-day'));

        if (that.options.beforePageItemClick && that.options.beforePageItemClick(that, this, year, month, date, day)) {
          that.options.onPageItemClick && that.options.onPageItemClick(that, this, year, month, date, day);
        }

      });
      that.$el.append(calendarPage);

    },
    render: function () {
      this.clean();
      this.renderCalendarHead();
      this.renderCalendarPage();
    }

  };

  module.exports = {
    Component: function (options) {

      return this.each(function (idx, el) {
        var calendar = new CalendarPage($(el), options);
        calendar.__initialize();
        return calendar;
      });

    },
    formatInt: formatInt,
    getDays: getDays,
    LABEL_MONTHS: LABEL_MONTHS,
    LABEL_WEEKS: LABEL_WEEKS,
    CALENDAR_PAGE_ITEM_WIDTH_PRECENT: CALENDAR_PAGE_ITEM_WIDTH_PRECENT
  };

});