define([
  "dojo/_base/declare",
  "dijit/_WidgetBase",
  "mui/calendar/base/CalendarScrollable",
  "dojo/date",
  "dojo/date/locale",
  "dojo/dom-construct",
  "dojo/dom-style",
  "dojo/query",
  "mui/calendar/_BottomEventMixin",
  "dojo/ready",
  "mui/i18n/i18n!sys-mobile:mui",
  "dojo/dom-geometry"
], function(
  declare,
  _WidgetBase,
  CalendarScrollable,
  dateClaz,
  locale,
  domConstruct,
  domStyle,
  query,
  _BottomEventMixin,
  ready,
  msg,
  domGeometry
) {
  var claz = declare(
    "mui.calendar.CalendarBottom",
    [_WidgetBase, CalendarScrollable, _BottomEventMixin],
    {
      templateString: '<div class="">'
    	  			+ '		<div class="muiCalendarOpt" data-dojo-attach-point="optNode">'
    	  			+ '			<div class="muiCalendarBottomLeft" data-dojo-attach-point="dateNode"></div>'
    	  			+ '			<div class="muiCalendarBottomCenter">'
    	  			+ '				<div data-dojo-attach-point="y2mNode"></div>'
    	  			+ '				<div class="muiCalendarBottomInfo">'
    	  			+ '					<div data-dojo-attach-point="weekNode"></div>'
    	  			+ '					<div data-dojo-attach-point="compNode"></div>'
    	  			+ '				</div>'
    	  			+ '			</div>'
    	  			+ '			<div class="muiCalendarBottomRight" data-dojo-attach-point="eventNode"></div>'
    	  			+ '		</div>'
    	  			+ '</div>',
    	  					
      optNode: null,
      showOptNode: true,

      dateNode: null,

      y2mNode: null,

      weekNode: null,

      compNode: null,

      reparent: function() {
        this.tmplNode = domConstruct.create("div")
        var i, idx, len, c
        var domNode = this.srcNodeRef
        for (i = 0, idx = 0, len = domNode.childNodes.length; i < len; i++) {
          c = domNode.childNodes[idx]
          domConstruct.place(c, this.tmplNode, "last")
        }
      },
      
      buildAddNode : function () {
    	
    	  var addNodeContainer = domConstruct.create('div', {
    		  className : 'mui_ekp_portal_date_addNode_contariner'
    	  }, this.domNode);
    	  
    	  var left = domConstruct.create('div', {
    		  className : 'mui_ekp_portal_date_addNode_left',
    		  innerHTML : '<span class="muiFontSizeM">'+msg["mui.button.schedule"]+'</span>'
    	  }, addNodeContainer);
    	  
    	  var right = domConstruct.create('div', {
    		  className : 'mui_ekp_portal_date_addNode_right'
    	  }, addNodeContainer);

    	  var button = domConstruct.create('div', {
    		  className : 'addNodeButton muiFontColorMuted',
    		  innerHTML : '<i class="mui_ekp_portal_date_addNode_icon fontmuis muis-new muiFontSizeXS"></i><span class="muiFontSizeS">'+msg["mui.button.add"]+'</span>'
    	  }, right);
    	  
    	  this.connect(button, 'click', 'eventClick');
    	  
      },

      buildRendering: function() {
        this.reparent()
        this.inherited(arguments)
        if(this.isPortal)
        	this.buildAddNode();
        domConstruct.place(this.tmplNode, this.domNode, "last")
        this.subscribe(this.VALUE_CHANGE, "nodeChange")
        this.subscribe("/mui/calendar/contentComplete", "contentComplete")
        this.subscribe("/mui/calendar/weekComplete", "weekComplete")
        this.subscribe("/mui/calendar/headerComplete", "headerComplete")
        this.subscribe("/mui/calendar/viewComplete", "viewComplete")
        var self = this
        var calendarBottom
        if (!this.showOptNode) {
          domStyle.set(this.optNode, "display", "none")
        }

        ready(function() {
          self.resize()
        })
        domStyle.set(this.domNode, "background", "#fff")
      },

      resize: function() {
        var viewNode = query(".mblView", this.domNode)[0]
        var optHeight = 0;
		if(this.optNode) {
			optHeight = this.optNode.offsetHeight;
		}
        var h =
          this.getScreenHeight() -
          optHeight -
          //this.optNode.offsetHeight -
          this.headerHeight -
          this.weekHeight -
          this.fixedHeaderHeight -
          this.fixedFooterHeight
        domStyle.set(viewNode, "height", h + "px")
      },

      contentComplete: function(obj, evt) {
    	  if (this.contentHeight == 0) {
    		  this.contentHeight = document.querySelectorAll(".muiCalendarTable ")[1].offsetHeight
    	  } else {
    		  this.contentHeight = evt.height
    	  }
    	  this.resize()
      },

      viewComplete: function(obj, evt) {
        this.fixedHeaderHeight = evt.fixedHeaderHeight
        this.fixedFooterHeight = evt.fixedFooterHeight
      },
      weekComplete: function(obj, evt) {
        if (this.weekHeight == 0) {
          this.weekHeight = evt.height
        } else {
          //this.weekHeight = document.querySelector(".muiCalendarWeekTable").offsetHeight
          this.weekHeight = domGeometry.getMarginBox(obj.domNode).h;
        }
        this.resize()
      },

      headerComplete: function(obj, evt) {
        //							debugger;
        this.headerHeight = evt.height
      },

      nodeChange: function(evt) {return
        if (
          this.getEnclosingCalendarView(evt) !=
          this.getEnclosingCalendarView(this)
        )
          return
        if (!evt) return
        var date = evt.currentDate,
          dayHTML = date.getDate() > 9 ? date.getDate() : "0" + date.getDate(),
          monthHTML =
            date.getMonth() + 1 > 9
              ? date.getMonth() + 1
              : "0" + (date.getMonth() + 1)
        this.dateNode.innerHTML = dayHTML
        this.y2mNode.innerHTML = date.getFullYear() + "." + monthHTML
        this.dayNames = locale.getNames("days", "wide", "standAlone")
        this.weekNode.innerHTML = this.dayNames[date.getDay()]
        var comp = dateClaz.difference(date, null, "day"),
          absComp = Math.abs(comp)
        var _blank = dojoConfig.locale == "en-us" ? " " : ""
        if (comp > 0)
          absComp +=
            _blank + msg["mui.calendar.day"] + _blank + msg["mui.calendar.ago"]
        else if (comp == 0) absComp = msg["mui.calendar.today"]
        else
          absComp +=
            _blank +
            msg["mui.calendar.day"] +
            _blank +
            msg["mui.calendar.after"]
        this.compNode.innerHTML = absComp
      }
    }
  )
  return claz
})
