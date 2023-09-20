define(
		[ "dojo/_base/declare", "dijit/_WidgetBase",
				"mui/calendar/base/CalendarBase",
				"mui/calendar/_ContentEventMixin", "dojo/date",
				"dojo/date/locale", "dojo/string", "dojo/_base/lang",
				"dojo/_base/array", "dojo/topic", "dojo/dom-class",
				"dojo/dom-style", "dojox/mobile/_css3", "dojo/dom-construct",
				"dojo/query", "dojo/dom-attr", "mui/calendar/CalendarUtil",
				"dojo/request","dojo/dom-geometry"],
		function(declare, _WidgetBase, CalendarBase,_ContentEventMixin, dateClaz,
				locale, string, lang, array, topic, domClass, domStyle, css3,
				domConstruct, query, domAttr, calendarUtil,request,domGeometry) {
			var claz = declare(
					"sys.modeling.main.calendar.CalendarMonth",
					[ _WidgetBase, CalendarBase ],
					{

						// 容器节点
						tableNode : null,
						// 日期信息节点
						dateNode : null,

						templateString : '<div class="mui_ekp_portal_date muiCalendarMonth" data-dojo-attach-point="tableNode">'
										+'	<ul class="mui_ekp_portal_date_week schedule">'
										+'		${!weekHtml}'
										+'	</ul>'
										+'	<ul class="mui_ekp_portal_date_days schedule" style="display:table">'
										+'		${!dateHtml}'
										+'	</ul>'
										+'</div>',
										
						thTemplate : '<li class="muiFontSizeS"><span>${d}</span></li>',
						tdTemplate : '<li class="muiFontSizeM">'
									+'	<div class="mui_ekp_portal_data_bg">'
									+'		<span class="mui_ekp_portal_date_num" data-dojo-attach-point="datesNode"></span>'
									+'	</div>'
									+'</li>',
						trTemplate : '${d}${d}${d}${d}${d}${d}${d}',
						
						postMixInProperties : function(){
							this.inherited(arguments);
							this.datesNode = [];
						},

						buildRendering : function() {
							this.dayNames = locale.getNames('days', 'narrow',
									'standAlone');
							this._buildTable();
							this.inherited(arguments);
							this.stuffDate();
							this.bindEvent();
							this.subscribe(this.VALUE_CHANGE, 'valueChange');
							this.subscribe(this.MONTH_CHANGE, 'monthChange');
							this.subscribe(this.NOTIFY, 'processEvent');
							// this.subscribe('/mui/calendar/bottomStatus',
							// 		'processEventOfMonth');
							// this.subscribe('/mui/calendar/bottomScroll',
							// 		'scale');
						},

						scale : function(obj, evt) {
							if(this.getEnclosingCalendarView(obj) != this.getEnclosingCalendarView(this))
								return;
							if (!evt)
								return;
							domStyle.set(this.domNode, css3.add(css3.add({}, {transform : 'scale(' + (1 - Math.abs(evt.y) / (this.domNode.offsetHeight * 3)) + ')' })));
						},

						monthChange : function(obj) {
							if(this.getEnclosingCalendarView(obj) != this.getEnclosingCalendarView(this))
								return;
							else
								this.stuffDate();
							topic.publish(this.DATA_CHANGE, this, {
								lastDate : this.lastDate,
								startDate : this.startDate,
								currentDate : this.currentDate,
								endDate : this.endDate
							});
						},

						// 哪天有日程(key,value形式...key=日期,value=true|false是否存在日程)
						haveEvent : null,
						processEvent : function(haveEvent,obj) {
							if( this.getEnclosingCalendarView(this) !== this.getEnclosingCalendarView(obj))
								return;
							if (haveEvent) {
								this.haveEvent = haveEvent;
							}
							query('.mui_ekp_portal_date_summary',this.domNode).remove();// 清空摘要
							query('.mui_ekp_portal_date_summary_count',this.domNode).remove();// 清空摘要
							for (var i = 0; i < this.datesNode.length; i++) {
								var key = domAttr.get(this.datesNode[i], "date");
								if(dojoConfig.locale == 'en-us' && key){
									key = key.split('-');
									if(dojoConfig.Date_format == 'dd/MM/yyyy')
										key = key[2] + '/' + key[1] + '/' + key[0];
									else
										key = key[1] + '/' + key[2] + '/' + key[0];
								}
								if (this.haveEvent && this.haveEvent[key]) {
									var datas = obj.__caches[key];

									if(datas){
										var length = datas.length>3?3:datas.length;
										for (var j = 0; j < length; j++) {
											domConstruct.create("div", {
												className : "mui_ekp_portal_date_summary muiFontSizeXS",
												innerHTML:datas[j].title||"暂无内容"
											}, this.datesNode[i].parentNode);
										}
										if(datas.length>3){
											domConstruct.create("div", {
												className : "mui_ekp_portal_date_summary_count muiFontSizeXS",
												innerHTML:"共"+datas.length +"项"
											}, this.datesNode[i].parentNode);
										}
									}
								}
							}
						},

						processEventOfMonth : function(obj, evt) {
							if(this.getEnclosingCalendarView(obj) != this.getEnclosingCalendarView(this))
								return;
							// status=true:月模式
							if (evt && evt.status)
								this.processEvent(null,this);
						},
							
						valueChange : function(obj, currentDate){
							if(this.getEnclosingCalendarView(obj) != this.getEnclosingCalendarView(this))
								return;
							var dates = this.datesNode;
							array.forEach(array.filter(dates, function(item) {
								return domClass.contains(item.parentNode.parentNode, 'active');
							}), function(item, index) {
								domClass.toggle(item.parentNode.parentNode, 'active', false);
							}, this);
							
							array.map(dates, function(item) {
								var _c=locale.format(this.currentDate,{
									selector : 'time',
									timePattern : 'yyyy-MM-dd'
								})
								if(_c == domAttr.get(item,'date') ){
									domClass.toggle(item.parentNode.parentNode, 'active', true);
								}
							}, this);
						},
						
						bindEvent : function() {

							for (var i = 0; i < this.datesNode.length; i++) {
								this.connect(this.datesNode[i].parentNode.parentNode, 'click', 'onDateClick');
							}
						},

						onDateClick : function(evt) {
							evt.stopPropagation();
							var target = evt.target;
							if(domClass.contains(target, 'muiFontSizeM'))
								target = target.children[0].children[0];
							if(domClass.contains(target, 'mui_ekp_portal_data_bg'))
								target = target.children[0];
							if(domClass.contains(target, 'mui_ekp_portal_date_lunar')
								|| domClass.contains(target, 'mui_ekp_portal_date_summary')
								|| domClass.contains(target, 'mui_ekp_portal_date_summary_count'))
								target = target.parentNode.children[0];
							while (target) {
								if (domClass.contains(target, 'mui_ekp_portal_date_num')) {
									this.triggleSelected(target)
									break;
								}
								target = target.parentNode;
							}
						},

						lastSelected : function(node) {
							var date = new Date(domAttr.get(node, 'date'));
							this.set('currentDate', date, false, true);
						},

						nextSelected : function(node) {
							var date = new Date(domAttr.get(node, 'date'));
							this.set('currentDate', date, false, true);
						},

						currSelected : function(node) {
							var dates = this.datesNode;
							topic.publish("/sys/modeling/calendar/preview/view",this);
							array.forEach(array.filter(dates, function(item) {
								return domClass.contains(item.parentNode.parentNode, 'active');
							}), function(item, index) {
								domClass.toggle(item.parentNode.parentNode, 'active', false);
							}, this);

							array.map(dates, function(item) {
								if (item == node) {
									var date = new Date(domAttr.get(node, 'date'));
									this.set('currentDate', date, false, true);
									domClass.toggle(item.parentNode.parentNode, 'active', true);
								}
							}, this);
						},

						triggleSelected : function(node) {
							if (domClass.contains(node, 'muiCalendarDatePre'))
								// 上个月
								this.lastSelected(node);
							else if (domClass.contains(node, 'muiCalendarDateNext'))
								// 下个月
								this.nextSelected(node);
							else
								// 当前月
								this.currSelected(node);
						},
						
						// 填充日期前执行
						beforeStuffDate: function(domNode){ },

						// 填充日期
						stuffDate : function() {

							var ctx = this;
							
							ctx.beforeStuffDate && ctx.beforeStuffDate(ctx.domNode,query,request);
							
							var month = new Date(this.currentDate), today = new Date();
							month.setDate(1);
							// 本月第一天星期几
							var firstDay = (month.getDay() - this.firstDayInWeek + 7) % 7;

							var daysInPreviousMonth = dateClaz.getDaysInMonth(dateClaz.add(month, "month", -1));
							// 本月多少天
							var daysInMonth = dateClaz.getDaysInMonth(month);

							array.forEach(this.datesNode, function(node, idx) {
								
								domClass.remove(node.parentNode.parentNode, 'active');
								domClass.remove(node.parentNode, 'today');
								domClass.remove(node.parentNode, 'outweek');

								var number, className = 'mui_ekp_portal_date_num muiFontSizeL ', sym;
								var date = new Date(month);
								if (idx < firstDay) {// 前
									number = daysInPreviousMonth - firstDay + idx + 1;
									domClass.add(node.parentNode, 'outweek');
									className += 'muiCalendarDatePre';
									sym = 'Pre';
								} else if (idx >= (firstDay + daysInMonth)) {// 下
									number = idx - firstDay - daysInMonth + 1;
									domClass.add(node.parentNode, 'outweek');
									className += 'muiCalendarDateNext';
									sym = 'Next'
								} else {// 当前
									number = idx - firstDay + 1;
									sym = 'Curr';
								}
								

								if (sym == 'Pre')
									date = dateClaz.add(date, "month", -1);
								if (sym == 'Next')
									date = dateClaz.add(date, "month", 1);
								date.setDate(number);
								
								if (idx == 0) {
									this.startDate = date;
								}
								if (idx == this.datesNode.length - 1) {
									this.endDate = date;
								}

								if (!dateClaz.compare(date, today, "date")) {
									className = "muiCalendarCurrentDate " + className;
								}

								if (!dateClaz.compare(date, this.currentDate, "date")) {
									domClass.add(node.parentNode.parentNode, 'active');
								}
								
								if (!dateClaz.compare(date, today, "date")) {
									domClass.add(node.parentNode, 'today');
								}
								
								//周六、日字体显示为灰色
								if(date.getDay()==0 || date.getDay()==6){
									domClass.add(node.parentNode, 'outweek');
								}
								
								this._setText(node, number);
								node.className = className;
								domAttr.set(node, 'date', locale.format(date,{
										selector : 'time',
										timePattern : 'yyyy-MM-dd'
									})
								);
								
								ctx.stuffDateNode && ctx.stuffDateNode(date, node);
								
								var type = calendarUtil.checkHPDate(new Date(domAttr.get(node, 'date')));
								if(query('.mui_ekp_portal_date_lunar', node).length == 0)
									if(type != '')
										domConstruct.create('i', {
											className : 'work_condition ' + (type==1?'rest':'work'),
											innerHTML : (type=='1'?'休':'班')
										}, node);

								var cal = calendarUtil.checkLunarAndHoliday(new Date(domAttr.get(node, 'date')));
								var lunar = cal.value;
								if(query('.mui_ekp_portal_date_lunar', node.parentNode).length == 0)
									domConstruct.create('div', {
										className : 'mui_ekp_portal_date_lunar muiFontSizeXS',
										innerHTML : lunar
									}, node.parentNode);
								else
									node.parentNode.children[1].innerHTML = lunar;
								
							}, this);
						},
						
						// 处理填充日期条目
						stuffDateNode: function(date, dateNode){ },
						
						stuffDateOneMonth:function(){
							
						},

						// 构建表格
						_buildTable : function() {
							var d = this.thTemplate, i = 7;
							// 构建日历星期表头
							this.weekHtml = string
									.substitute(
											[ d, d, d, d, d, d, d ].join(''),
											{
												d : ""
											},
											lang
													.hitch(
															this,
															function() {
																var r = this.dayNames[(i + this.firstDayInWeek) % 7]
																i++;
																return r;
															}));

							// 构建内容
							var r = string.substitute(this.trTemplate, {
								d : this.tdTemplate
							});
							this.dateHtml = [ r, r, r, r, r, r ].join("");
						},

						resize : function() {
							var self = this;
							var tabBarNode = query(".modeling_list_bottom_tab_bar")[0];
							var tabBarHeight = 0;
							if(tabBarNode){
								tabBarHeight = tabBarNode.clientHeight;
							}
							var h =
								this.getScreenHeight() -
								tabBarHeight -100;//100为头部页签+日历头部的高度

							domStyle.set(this.domNode, "height", h + "px")
							setTimeout(function(){
								topic.publish('/mui/calendar/monthComplete', self, {
									height : domGeometry.getMarginBox(self.domNode).h
								});
							},100)
						},
						
						reload : function(){
							topic.publish(this.DATA_CHANGE, this, {
								lastDate : this.lastDate||new Date(),
								startDate : this.startDate||new Date(),
								currentDate : this.currentDate||new Date(),
								endDate : this.endDate
							});
						},
						
						startup:function(){
							this.inherited(arguments);
							topic.publish(this.DATA_CHANGE, this, {
								lastDate : this.lastDate||new Date(),
								startDate : this.startDate||new Date(),
								currentDate : this.currentDate||new Date(),
								endDate : this.endDate
							});
						}
						
					});
			return claz;
		});