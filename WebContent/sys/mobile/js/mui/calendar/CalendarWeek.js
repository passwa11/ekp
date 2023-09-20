define(
		[ "dojo/_base/declare", "dijit/_WidgetBase",
				"mui/calendar/base/CalendarScrollable", "dojo/date",
				"dojo/date/locale", "dojo/string", "dojo/topic",
				"dojo/dom-construct", "dojo/dom-style", "dojo/_base/array",
				"dojo/_base/lang", "dojo/dom-class", "dojo/query",
				"dojo/dom-attr", "mui/calendar/CalendarUtil",
				"dojo/request","dojo/dom-geometry"],
		function(declare, _WidgetBase, CalendarScrollable, dateClaz,
				locale, string, topic, domConstruct, domStyle, array, lang,
				domClass, query, domAttr, calendarUtil,request,domGeometry) {
			var claz = declare(
					"mui.calendar.CalendarWeek",
					[ _WidgetBase, CalendarScrollable ],
					{

						// 容器节点
						tableNode : null,
						// 日期信息节点
						dateNode : null,
						weekNode : null,

						templateString : '<div class="mui_ekp_portal_date muiCalendarTable muiCalendarWeekTable" data-dojo-attach-point="tableNode">'
										+'	<ul class="mui_ekp_portal_date_week schedule">'
										+'		${!weekHtml}'
										+'	</ul>'
										+'	<ul class="mui_ekp_portal_date_days schedule" data-dojo-attach-point="dateNode" class="">'
										+'		${!dateHtml}'
										+'	</ul>'
										+'</div>',
						
						thTemplate : '<li class="muiFontSizeS"><span>${d}</span></li>',

						trTemplate : '${d}${d}${d}${d}${d}${d}${d}',

						tdTemplate : '<li class="muiFontSizeM">'
									+'	<div class="mui_ekp_portal_data_bg">'
									+'		<span class="mui_ekp_portal_date_num" data-dojo-attach-point="datesNode"></span>'
									+'	</div>'
									+'</li>',
						
						postMixInProperties : function(){
							this.inherited(arguments);
							this.datesNode = [];
						},

						buildRendering : function() {
							this.dayNames = locale.getNames('days', 'narrow',
									'standAlone');

							var i = 7, d = this.thTemplate;
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
																// return
																// this.dayNames[i++
																// % 7];
															}));

							// 构建内容
							var r = string.substitute(this.trTemplate, {
								d : this.tdTemplate
							});
							this.dateHtml = r;

							this.inherited(arguments);

							this.subscribe(this.VALUE_CHANGE, 'valueChange');
							this.subscribe(this.NOTIFY, 'processEvent');
							this.subscribe('/mui/calendar/bottomStatus', 'processEventOfWeek');
							this.subscribe('/mui/calendar/bottomScroll', 'translate');
							
							this.stuffDate();
							this.bindEvent();
						},

						translate : function(obj, evt) {
							if( this.getEnclosingCalendarView(this) !== this.getEnclosingCalendarView(obj))
								return;
							if (!evt)
								return;
							var top = evt.top;
							this.scrollTo({
								y : '-' + top
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
							query('.journey_status.journey',this.domNode).remove();// 清空小红点
							for (var i = 0; i < this.datesNode.length; i++) {
								var key = domAttr.get(this.datesNode[i], "date");
								if (this.haveEvent && this.haveEvent[key]) {
									domConstruct.create("i", {
										className : "journey_status journey"
									}, this.datesNode[i].parentNode);
								}
							}
						},

						processEventOfWeek : function(obj, evt) {
							if( this.getEnclosingCalendarView(this) !== this.getEnclosingCalendarView(obj))
								return;
							// status=false:周模式
							if (!evt || !evt.status)
								this.processEvent(null,this);
						},

						valueChange : function(obj, data) {
							if(this.getEnclosingCalendarView(obj) != this.getEnclosingCalendarView(this))
								return;

							this.stuffDate();
							this.processEvent(null,this);
						},
						
						// 填充日期前执行
						beforeStuffDate: function(domNode){ },

						stuffDate : function() {
							
							var ctx = this;
							
							ctx.beforeStuffDate && ctx.beforeStuffDate(ctx.domNode,query,request);
							if(this.currentDate) {
								var date = this.currentDate.getDate();
								var today = new Date();
								var day = (this.currentDate.getDay() - this.firstDayInWeek + 7) % 7;

								array.forEach(this.datesNode, function (node, idx) {

									domClass.remove(node.parentNode.parentNode, 'active');
									domClass.remove(node.parentNode, 'today');

									var className = 'mui_ekp_portal_date_num muiFontSizeL';
									var d = new Date(this.currentDate);
									d.setDate(date - day + idx);

									//选中日
									if (day == idx) {
										domClass.add(node.parentNode.parentNode, 'active');
									}
									//今天
									if (!dateClaz.compare(d, today, "date")) {
										domClass.add(node.parentNode, 'today');
									}

									var diff = dateClaz.difference(this.currentDate, d, 'month');
									//上个月
									if (diff < 0) {
										className += ' outweek';
									}
									//下个月
									if (diff > 0) {
										className += ' outweek';
									}
									//周六、日和不在本周颜色一致
									if (d.getDay() == 0 || d.getDay() == 6) {
										domClass.add(node.parentNode, 'outweek');
									}

									this._setText(node, d.getDate());
									node.className = className;
									domAttr.set(node, 'date', locale.format(d, {
											selector: 'time',
											timePattern: 'yyyy-MM-dd'
										})
									);

									ctx.stuffDateNode && ctx.stuffDateNode(d, node);

									// 班和休
									var type = calendarUtil.checkHPDate(new Date(domAttr.get(node, 'date')));
									if (query('.mui_ekp_portal_date_lunar', node).length == 0)
										if (type != '')
											domConstruct.create('i', {
												className: 'work_condition ' + (type == 1 ? 'rest' : 'work'),
												innerHTML: (type == '1' ? '休' : '班')
											}, node);

									var cal = calendarUtil.checkLunarAndHoliday(new Date(domAttr.get(node, 'date')));
									var lunar = cal.value;
									if (query('.mui_ekp_portal_date_lunar', node.parentNode).length == 0)
										domConstruct.create('div', {
											className: 'mui_ekp_portal_date_lunar muiFontSizeXS',
											innerHTML: lunar
										}, node.parentNode);
									else
										node.parentNode.children[1].innerHTML = lunar;

								}, this);
							}
						},
						
						// 处理填充日期条目
						stuffDateNode: function(date, dateNode){ },

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
							if(domClass.contains(target, 'mui_ekp_portal_date_lunar') || domClass.contains(target, 'journey_status'))
								target = target.parentNode.children[0];
							while (target) {
								if (domClass.contains(target, 'mui_ekp_portal_date_num')) {
									this.triggleSelected(target);
									break;
								}
								target = target.parentNode;
							}
						},

						triggleSelected : function(node) {
							var dates = this.datesNode;
							array.forEach(array.filter(dates, function(item) {
								return domClass.contains(item.parentNode.parentNode, 'active');
							}), function(item, index) {
								domClass.toggle(item.parentNode.parentNode, 'active', false);
							}, this);
							array.map(dates, function(item) {
								if (item == node) {
									var date = new Date(this.currentDate);
									if (domClass.contains(node, 'outweek')){
										// 上个月
										date = dateClaz.add(date, 'month',-1);
									}else if (domClass.contains(node,'outweek')){
										// 下个月
										date = dateClaz.add(date, 'month',1);
									}
									
									this.set('currentDate', new Date(domAttr.get(node, 'date')), false);
									domClass.toggle(item.parentNode.parentNode, 'active', true);
								}
							}, this);
						},

						resize : function() {
							/*if(this.domNode.offsetHeight){
								topic.publish('/mui/calendar/weekComplete', this, {
									height : this.domNode.offsetHeight
								});
							}else{
								setTimeout(function(){
									topic.publish('/mui/calendar/weekComplete', this, {
										height : document.querySelector('.muiCalendarWeekTable').offsetHeight
									});
								},10)
							}*/
							var self = this;
							setTimeout(function(){
								topic.publish('/mui/calendar/weekComplete', self, {
									height : domGeometry.getMarginBox(self.domNode).h
								});
							},100)
						}

					});
			return claz;
		});