define([ "dojo/_base/declare", "dojox/mobile/ScrollableView", "dojo/topic","dojo/dom-class", "mui/list/_ViewScrollEventPublisherMixin" ],
		function(declare, ScrollableView, topic,domClass, _ViewScrollEventPublisherMixin) {
			var claz = declare("mui.calendar.CalendarListScrollableView",
					[ ScrollableView, _ViewScrollEventPublisherMixin ], {
						scrollBar : false,
						weight : 0,
						type : '/mui/calendar/listScrollableTop',

						buildRendering : function() {
							this.inherited(arguments);
							this.subscribe('/mui/calendar/bottomStatus',
									'statusChange');
						},

						disableTouchScroll : true,

						adjustDestination : function(to) {
							if (to.y >= 0)
								this.defer(function() {
									topic.publish(this.type, this, to);
								}, 1000);
							else
								topic.publish(this.type, this, to);
							return true;
						},
						statusChange : function(obj, evt) {
							if(this.getEnclosingCalendarView(obj) != this.getEnclosingCalendarView(this))
								return;
							this.disableTouchScroll = evt.status;
						},
						
						getEnclosingCalendarView : function(widget){
							if(widget && widget.getParent){
								widget = widget.getParent();
								while(widget){
									if(domClass.contains(widget.domNode,'muiCalendarScrollableView')){
										return widget;
									}
									widget = widget.getParent();
								}
							}
							return null;
						},
						
						startup : function(){
							this.inherited(arguments);
							this.subscribe("/mui/calendar/valueChange", function(){
								topic.publish("/mui/list/toTop", this, {y : 0});
							});
						}
						
					});
			return claz;
		});