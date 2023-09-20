define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "mui/calendar/base/CalendarBase",
  "mui/calendar/CalendarUtil",
  "dojo/date",
  "dojo/date/locale",
  "dojo/topic",
  "mui/calendar/_HeaderExternalViewMixin",
  "dojo/dom-style",
  "dojo/dom-class",
  "dojo/dom-construct",
  "mui/i18n/i18n!sys-mobile:mui",
  "mui/util"
], function(
  declare,
  _WidgetBase,
  CalendarBase,
  calendarUtil,
  dateClaz,
  locale,
  topic,
  _HeaderExternalViewMixin,
  domStyle,
  domClass,
  domConstruct,
  msg,
  util
) {
  var claz = declare(
    "mui.calendar.CalendarHeader",
    [_WidgetBase, CalendarBase, _HeaderExternalViewMixin],
    {
      status: true,

      leftNode: null,
      rightNode: null,

      // 上个月节点
      preNode: null,
      preNodeHandler: null,

      // 下个月节点
      nextNode: null,
      nextNodeHandler: null,

      // 当前月节点
      thisNode: null,
      thisNodeHandler: null,

      // 今天节点
      todayNode: null,
      todayNodeHandler: null,

      // 左边区域按钮
      left: null,
      // 右边区域按钮
      right: null,
      //是否该附件发布事件，避免其他组件串扰
      isThis: false,

      showDatePicker: true,

      _setLeftAttr: function(left) {
        this.left = left
      },

      _setRightAttr: function(right) {
        this.right = right
      },

      templateString: '<div class="mui_ekp_portal_date_title">'
    	  			+ '		<span data-dojo-attach-point="thisNode" class="muiFontColorInfo muiFontSizeXXXL"></span>'
    	  			+ '		<ul class="mui_ekp_portal_date_btn">'
    	  			+ '			<li data-dojo-attach-point="preNode">'
    	  			+ '				<span><i class="fontmuis muis-to-left muiFontSizeS"></i></span>'
    	  			+ '			</li>'
    	  			+ '			<li class="mui_ekp_portal_date_btn_left" data-dojo-attach-point="nextNode">'
    	  			+ '				<span><i class="fontmuis muis-to-right muiFontSizeS"></i></span>'
    	  			+ '			</li>'
    	  			+ '			<li data-dojo-attach-point="leftNode">'
    	  			+ '				<span></span>'
    	  			+ '			</li>'
    	  			+ '			<li data-dojo-attach-point="rightNode">'
    	  			+ '				<span></span>'
    	  			+ '			</li>'
    	  			+ '			<li class="mui_ekp_portal_date_btn_today"  data-dojo-attach-point="todayNode">'
    	  			+ '				<span class="fontmuis muis-today muiFontSizeS"></span>'
    	  			+ '			</li>'
    	  			+ '		</ul>'
    	  			+ '		<a class="muiFontColorMuted muiFontSizeS" href="javascript:;" style="display:none" data-dojo-attach-point="moreNode">'+msg["mui.button.small.more"]+'<i class="mui_ekp_portal_more_arrow fontmuis muis-to-right muiFontSizeXS"></i></a>'
    	  			+ '</div>',
          
      buildRendering: function() {
    	this.templateString = this.templateString;
        this.inherited(arguments)
        this.currentDateDom = domConstruct.create("input", {
        	name: "currentDate", 
        	type: "hidden"
        }, this.domNode)
        this.bindEvent()
        this.subscribe(this.VALUE_CHANGE, "nodeChange")
        this.subscribe(this.VALUE_CHANGE, "todayNodeChange")
        this.subscribe("/mui/form/datetime/change", "datePickerChange")
        this.subscribe("/mui/calendar/bottomStatus", "statusChange")
        if(this.moreUrl){
        	domStyle.set(this.moreNode, 'display', 'block');
        	this.connect(this.moreNode, 'click', function(){
        		window.location = util.formatUrl(this.moreUrl);
        	});
        }
      },

      startup: function() {
        this.inherited(arguments)
        if (this.right) {
          domClass.add(this.rightNode, this.right.icon || "mui mui-listView")
        } else {
          domStyle.set(this.rightNode, "display", "none")
        }
        if (this.left) {
          domClass.add(this.leftNode, this.left.icon || "mui-group")
        } else {
          domStyle.set(this.leftNode, "display", "none")
        }

        topic.publish("/mui/calendar/headerComplete", this, {
          height: this.domNode.offsetHeight
        })
      },

      resize: function() {
        topic.publish("/mui/calendar/headerComplete", this, {
          height: this.domNode.offsetHeight
        })
      },

      _setCurrentDateAttr: function(date, fire) {
        this.inherited(arguments)
        this.currentDateDom.value = calendarUtil.formatDate(date)
      },

      todayNodeChange: function(obj) {
        if (
          this.getEnclosingCalendarView(obj) !==
          this.getEnclosingCalendarView(this)
        )
          return
        if (!dateClaz.compare(new Date(), this.currentDate, "date"))
          domStyle.set(this.todayNode, {
            display: "none"
          })
        else
          domStyle.set(this.todayNode, {
            display: "inline-block"
          })
      },

      nodeChange: function(fire) {
        if (
          this.getEnclosingCalendarView(fire) !==
          this.getEnclosingCalendarView(this)
        )
          return
        this.stuff(this.currentDate, fire)
      },

      statusChange: function(widget, args) {
        if (
          this.getEnclosingCalendarView(widget) !==
          this.getEnclosingCalendarView(this)
        )
          return
        this.status = args.status
        this.bindEvent()
        this.stuff(this.currentDate)
      },

      datePickerChange: function(widget) {
        if (!this.isThis)
          if (
            this.getEnclosingCalendarView(widget) !==
            this.getEnclosingCalendarView(this)
          )
            return
        this.currentDateDom.value = widget.value
        var date = calendarUtil.parseDate(widget.value)
        this.set("currentDate", date)
      },

      bindEvent: function() {
        if (this.preNodeHandler) {
          this.disconnect(this.preNodeHandler)
        }
        if (this.nextNodeHandler) {
          this.disconnect(this.nextNodeHandler)
        }
        if (this.status) {
          this.preNodeHandler = this.connect(this.preNode, "click", "preMonth")
          this.nextNodeHandler = this.connect(this.nextNode, "click", "nextMonth")
        } else {
          this.preNodeHandler = this.connect(this.preNode, "click", "preWeek")
          this.nextNodeHandler = this.connect(this.nextNode, "click", "nextWeek")
        }
        if (!this.todayNodeHandler)
          this.todayNodeHandler = this.connect(this.todayNode, "click", "toToday")
        if (!this.thisNodeHandler && this.showDatePicker) {
          this.thisNodeHandler = this.connect(this.thisNode, "click", "toCustomDate")
        }
      },
      
      toToday: function() {
        this.set("currentDate", new Date(), true)
      },

      toCustomDate: function(event) {
        domClass.add(this.thisNode, "selected")
        this.defer(function() {
          require(["mui/form/DateTime"], function(DateTime) {
            DateTime.selectDate(event, "currentDate")
              if(this.thisNode) {
                  domClass.remove(this.thisNode, "selected")
              }
          })
        }, 1)
        this.isThis = true
      },

      preMonth: function() {
        this.set("currentDate", dateClaz.add(this.currentDate, "month", -1), false, true)
      },

      nextMonth: function() {
        this.set("currentDate", dateClaz.add(this.currentDate, "month", 1), false, true)
      },

      preWeek: function() {
        this.set("currentDate", dateClaz.add(this.currentDate, "week", -1), false, true)
      },

      nextWeek: function() {
        this.set("currentDate", dateClaz.add(this.currentDate, "week", 1), false, true)
      },

      // 填充头部年月份信息
      stuff: function(_date, fire) {
          if(_date) {
              var dataStr = calendarUtil.formatDate(_date);
              var datePattern = 'y-MM';
              if (dataStr.indexOf('/') > -1) {
                  datePattern = 'y/MM';
              }
              var text = locale.format(_date, {
                  selector: "date",
                  datePattern: datePattern
              });
              this._setText(this.thisNode, text)
          }
      }
    }
  )
  return claz
})
