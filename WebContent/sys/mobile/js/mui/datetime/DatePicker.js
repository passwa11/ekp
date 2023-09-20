define([
  "dojo/_base/declare",
  "dojo/dom-attr",
  "dojo/dom-class",
  "dojo/_base/array",
  "dojo/_base/lang",
  "dojo/date/locale",
  "dojo/date/stamp",
  "dojox/mobile/SpinWheelDatePicker",
  "mui/i18n/i18n!sys-mobile:mui",
  "dojo/dom-attr"
], function(
  declare,
  domAttr,
  domClass,
  array,
  lang,
  datelocale,
  datestamp,
  SpinWheelDatePicker,
  msg,
  domAttr
) {
  var weekNames = msg["mui.datetime.week.shortName"].split(",");
  //mixin时间格式化
  var slotMixin = {
    minItems: 12,
    format: function(/*Date*/ d) {
      return datelocale.format(d, {
        datePattern: this.pattern,
        selector: "date"
      });
    },

    //更改颜色,文字显示

    setColor: function(/*String*/ value, /*Date*/ d, /*String?*/ color) {
      var weekDis = this.flag == "date";

      // 优化性能，去除js遍历，改用选择器
      var len = this.panelNodes.length;

      for (var i = 0; i < len; i++) {
        var panel = this.panelNodes[i];

        var preNodes = panel.querySelectorAll(
          ".mblSpinWheelSlotLabelBlue:not([data-mobile-val='" + value + "'])"
        );
        array.forEach(preNodes, function(preNode) {
          var curVal = domAttr.get(preNode, "data-mobile-val");

          if (preNode.innerHTML != curVal) {
            domClass.toggle(
              preNode,
              color || "mblSpinWheelSlotLabelBlue",
              false
            );
            preNode.innerHTML = curVal;
          }
        });

        var nodes = panel.querySelectorAll("[data-mobile-val='" + value + "']");
        array.forEach(
          nodes,
          function(node) {
            var tmpDate = datelocale.parse(value, {
              datePattern: this.pattern,
              selector: "date"
            });
            var tmpHtml = datelocale.format(tmpDate, {
              datePattern: this.disPattern,
              selector: "date"
            });
            tmpHtml =
              tmpHtml +
              '<span class="mblSpainWheelSlotLabelText">' +
              this.disText +
              "</span>";
            if (weekDis)
              tmpHtml =
                tmpHtml +
                '<span class="mblSpainWheelSlotLabelWeek">' +
                weekNames[d.getDay()] +
                "</span>";

            if (node.innerHTML != tmpHtml) {
              domClass.toggle(node, color || "mblSpinWheelSlotLabelBlue", true);
              node.innerHTML = tmpHtml;
            }
          },
          this
        );
      }
    }
  };

  var yearSlotMixin = lang.mixin(
    {
      flag: "year",
      initLabels: function() {
        this.labels = [];
        if (this.labelFrom !== this.labelTo) {
          var d = new Date(this.labelFrom, 0, 1);
          var i, idx;
          for (i = this.labelFrom, idx = 0; i <= this.labelTo; i++, idx++) {
            this["currYearFrom"] = i;
            d.setFullYear(i);
            this.labels.push(this.format(d));
          }
        }
      }
    },
    slotMixin
  );

  var monthSlotMixin = lang.mixin(
    {
      flag: "month",
      initLabels: function() {
        this.labels = [];
        var d = new Date(2000, 0, 16);
        for (var i = 0; i < 12; i++) {
          this["currMonthFrom"] = i;
          d.setMonth(i);
          this.labels.push(this.format(d));
        }
      }
    },
    slotMixin
  );

  var daySlotMixin = lang.mixin(
    {
      flag: "date",
      initLabels: function() {
        this.labels = [];
        var d = new Date(2000, 0, 1);
        for (var i = 1; i <= 31; i++) {
          this["currDateFrom"] = i;
          d.setDate(i);
          this.labels.push(this.format(d));
        }
      }
    },
    slotMixin
  );

  var claz = declare("mui.datetime.DatePicker", [SpinWheelDatePicker], {
    yearPattern: "yyyy",

    monthPattern: "MM",

    dayPattern: "dd",

    disYearPattern: "yyyy",

    disMonthPattern: "MM",

    disDayPattern: "dd",

    disYearText: msg["mui.datetime.year"],

    disMonthText: msg["mui.datetime.month"],

    disDayText: msg["mui.datetime.day"],

    slotProps: [{ labelFrom: 1899, labelTo: 2099 }, {}, {}],
    initSlots: function() {
      var c = this.slotClasses,
        p = this.slotProps;
      c[0] = declare(c[0], yearSlotMixin);
      c[1] = declare(c[1], monthSlotMixin);
      c[2] = declare(c[2], daySlotMixin);
      p[0].pattern = this.yearPattern;
      p[1].pattern = this.monthPattern;
      p[2].pattern = this.dayPattern;
      p[0].disPattern = this.disYearPattern;
      p[1].disPattern = this.disMonthPattern;
      p[2].disPattern = this.disDayPattern;
      p[0].disText = this.disYearText;
      p[1].disText = this.disMonthText;
      p[2].disText = this.disDayText;
      this.reorderSlots();
    },

    reorderSlots: function() {
      if (this.slotOrder.length) {
        return;
      }
      var a = datelocale
        ._parseInfo({ locale: this.lang })
        .bundle["dateFormat-short"].toLowerCase()
        .split(/[^ymd]+/, 3);
      this.slotOrder = array.map(a, function(pat) {
        return { y: 0, m: 1, d: 2 }[pat.charAt(0)];
      });

      //时间格式dd/MM/yyyy下特殊处理
      if ((dojoConfig.Date_format || "").match(/^d.*M.*y.*$/)) {
        this.slotOrder = [2, 1, 0];
      }
    },

    reset: function() {
      this.inherited(arguments);
      this._resetColors();
    },

    onYearSet: function() {
      this._resetColors();
    },

    onMonthSet: function() {
      this._resetColors();
    },

    onDaySet: function() {
      this._resetColors();
    },

    _setColorsAttr: function(/*Array*/ a) {
      var tmpDate = this.get("date");
      array.forEach(this.getSlots(), function(w, i) {
        w.setColor && w.setColor(a[i], tmpDate);
      });
    },

    _resetColors: function() {
      var v = this.get("values");
      this.set("colors", v);
    },

    _setValueAttr: function(value) {
      var _v = value;
      if (dojoConfig.locale == "en-us" && value) {
        var va = value.split("/");
        _v = va[2] + "-" + va[0] + "-" + va[1];
      }

      //时间格式dd/MM/yyyy下特殊处理
      if ((dojoConfig.Date_format || "").match(/^d.*M.*y.*$/) && value) {
        var va = value.split("/");
        _v = va[2] + "-" + va[1] + "-" + va[0];
      }

      var date = new Date();
      if (_v) {
        date = datestamp.fromISOString(_v);
      }
      this.set(
        "values",
        array.map(this.slots, function(w) {
          return w.format(date);
        })
      );
    },

    _getValueAttr: function() {
      var date = this.get("date");

      //时间格式dd/MM/yyyy下特殊处理
      if ((dojoConfig.Date_format || "").match(/^d.*M.*y.*$/)) {
        return datelocale.format(date, {
          datePattern: "dd/MM/yyyy",
          selector: "date"
        });
      }

      if (dojoConfig.locale == "en-us") {
        return datelocale.format(date, {
          datePattern: "MM/dd/yyyy",
          selector: "date"
        });
      }
      return datestamp.toISOString(date, { selector: "date" });
    }
  });
  return claz;
});
