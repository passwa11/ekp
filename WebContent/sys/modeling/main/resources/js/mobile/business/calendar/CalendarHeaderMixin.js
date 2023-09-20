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
  "mui/util",
  "mui/i18n/i18n!sys-modeling-main"
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
  util,
  modelingLang
) {
  var claz = declare(
    "sys.modeling.main.calendar.CalendarHeaderMixin",
    [_WidgetBase, CalendarBase, _HeaderExternalViewMixin],
    {

        // 日视图节点
        dayNode: null,
        dayNodeHandler: null,
        // 月视图节点
        monthNode: null,
        monthNodeHandler: null,
        showMode:null,//默认显示视图，月视图：1，日视图：0
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
    	  			+ '		</ul>'
    	  			+ '		<a class="muiFontColorMuted muiFontSizeS" href="javascript:;" style="display:none" data-dojo-attach-point="moreNode">'+msg["mui.button.small.more"]+'<i class="mui_ekp_portal_more_arrow fontmuis muis-to-right muiFontSizeXS"></i></a>'
                      + '		<ul class="mui_ekp_portal_date_btn muiViewSwitch">'
                      + '			<li class="mui_ekp_portal_date_btn_today"  data-dojo-attach-point="todayNode">'
                      + '				<span class="fontmuis muis-today muiFontSizeS"></span>'
                      + '			</li>'
                      + '			<li class="mui_ekp_portal_date_btn_day"  data-dojo-attach-point="dayNode">'
                      + '				<span class="">'+modelingLang["mui.modeling.day1"]+'</span>'
                      + '			</li>'
                      + '			<li class="mui_ekp_portal_date_btn_month"  data-dojo-attach-point="monthNode">'
                      + '				<span class="">'+modelingLang["mui.modeling.month1"]+'</span>'
                      + '			</li>'
                      + '		</ul>'
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
        this.bindSwitchEvent();
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
        if(this.dayNode && this.showMode == "0"){
            domClass.add(this.dayNode,"active");
        }
          if(this.monthNode && this.showMode == "1"){
              domClass.add(this.monthNode,"active");
          }

        topic.publish("/mui/calendar/headerComplete", this, {
          height: this.domNode.offsetHeight
        })
      },
        bindEvent: function() {
            if (this.preNodeHandler) {
                this.disconnect(this.preNodeHandler)
            }
            if (this.nextNodeHandler) {
                this.disconnect(this.nextNodeHandler)
            }
            if (this.status || this.showMode == "1") {
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
        bindSwitchEvent:function (){
            if (!this.dayNodeHandler)
                this.dayNodeHandler = this.connect(this.dayNode, "click", "switchDayView");
            if (!this.monthNodeHandler)
                this.monthNodeHandler = this.connect(this.monthNode, "click", "switchMonthView");
        },
        switchDayView:function (){
            if(this.dayNode){
                domClass.add(this.dayNode,"active");
            }
            if(this.monthNode){
                domClass.remove(this.monthNode,"active");
            }
            this.showMode = "0";
            topic.publish("/sys/modeling/calendar/switchView",this,"0");
        },
        switchMonthView:function (){
            if(this.dayNode){
                domClass.remove(this.dayNode,"active");
            }
            if(this.monthNode){
                domClass.add(this.monthNode,"active");
            }
            this.showMode = "1";
            topic.publish("/sys/modeling/calendar/switchView",this,"1");
        }
    }
  )
  return claz
})
