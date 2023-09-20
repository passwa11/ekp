define(function(require, exports, module) {
			var lang = require('lang!sys-ui');
			var select_panel = require('lui/criteria/select_panel');
			var render = require('lui/view/render');
			var source = require('lui/data/source');
			var $ = require('lui/jquery');
			
			var CriterionInputDatas = select_panel.CriterionInputDatas;
			
			var TextInput = CriterionInputDatas.extend({
				initProps : function($super, cfg) {
					$super(cfg);
					this.placeholder = cfg.placeholder || lang['ui.criteria.insert'];
				},
				supportMulti: function() {
					return false;
				},
				startup : function($super) {
					if (this.isStartup) {
						return;
					}
					if (!this.render) {
						this.setRender(new render.Template({
									src : require
											.resolve('./template/criterion-textinput-cell.jsp#'),
									parent : this
								}));
						this.render.startup();
					}
					if (!this.source) {
						this.setSource(new source.Static({
									datas : [{
												'placeholder' : this.placeholder
											}],
									parent : this
								}));
						if(this.source.startup)
							this.source.startup();
					}
					$super();
				},
				doRender : function($super, html) {
					$super(html);
					var self = this;
					this.element.find('.commit-action').bind('click', function(evt) {
						self.onClicked(evt);
					}).hide();
					this.element.find(':text').bind({
						'keyup' : function(evt) {
							self.onKeyup(evt);
						},
						// 解决鼠标右键粘贴不触发事件的问题
						'paste':function(evt){
							self.onPaste(evt);
						}
					});
					LUI.placeholder(this.element);
				},
				onClicked: function(evt) {
					this.addValue();
				},
				onKeyup : function(evt) {
					if (evt.keyCode != 13 && this.element.find(':text').val() != '') {
						this.element.find('.commit-action').show();
					}
					if (evt.keyCode == 13) {
						this.addValue();
					}
				},
				
				onPaste : function(evt) {
					this.element.find('.commit-action').show();
				},
				
				addValue: function() {
					var text = this.element.find(':text');
					var value = text.val();
					if (value == '') {
						this.selectedValues.removeAll();
						return;
					}
					this.selectedValues.set(value);
				},
				onChanged: function(val) {
					var sv = this.selectedValues.values;
					var text = this.element.find(':text');
					if (sv == null || sv.length == 0) {
						text.val('');
						this.element.find('.commit-action').hide();
						return;
					}
					if (text.val() != sv[0].value) {
						text.val(sv[0].value);
					}
					if (!sv[0].text) { // 修补文本
						sv[0].text = sv[0].value;
					}
					this.element.find('.commit-action').hide();
				}
			});
			
			var NumberInput = CriterionInputDatas.extend({
				supportMulti: function() {
					return false;
				},
				startup : function($super) {
					if (this.isStartup) {
						return;
					}
					if (!this.render) {
						this.setRender(new render.Template({
									src : require
											.resolve('./template/criterion-numberinput-cell.jsp#'),
									parent : this
								}));
						this.render.startup();
					}
					if (!this.source) {
						this.setSource(new source.Static({
									datas : [{min: 'MIN', max: 'MAX'}],
									parent : this
								}));
						if(this.source.startup)
							this.source.startup();
					}
					$super();
				},
				onChanged: function(val) {
					var sv = this.selectedValues.values;
					
					var min = this.element.find('[name="lui-critertion-min"]');
					var max = this.element.find('[name="lui-critertion-max"]');
					if (sv == null || sv.length == 0) {
						min.val('');
						max.val('');
						this.element.find('[data-critertion-number-min]').each(function() {
							$(this).find('a').removeClass('selected');
						});
						return;
					}
					var hasSelected = false;
					this.element.find('[data-critertion-number-min]').each(function() {
						var valElem = $(this);
						var aElem = valElem.find('a');
						if (valElem.attr('data-critertion-number-min') == sv[0].value
								&& valElem.attr('data-critertion-number-max') == sv[1].value) {
							if (!aElem.hasClass('selected')) {
								aElem.addClass('selected');
							}
							hasSelected = true;
						} else {
							aElem.removeClass('selected');
						}
						if (!sv[0].text) { // 修复选项文本
							sv[0].text = aElem.attr('title');
							sv[1].text = aElem.attr('title');
						}
					});
					if (!hasSelected) {
						min.val(sv[0].value);
						max.val(sv[1].value);
						if (!sv[0].text) { // 修复选项文本
							sv[0].text = sv[0].value;
							sv[1].text = sv[1].value;
						}
					} else {
						min.val('');
						max.val('');
					}
				},
				onSelected: function(evt) {
					var target = evt.target;
					if (target == this.element[0]) {
						return;
					}
					var self = this;
					self._withElement(target, 'data-critertion-number-min', function(valElem) {
						if (valElem.attr('data-critertion-number-min') == 'MIN'
							&& valElem.attr('data-critertion-number-max') == 'MAX') {
							return;
						}
						var aElem = valElem.find('a');
						if (!aElem.hasClass('selected')) {
							self.selectedValues.setAll([
							     {text: aElem.attr('title'), value: valElem.attr('data-critertion-number-min')},
							     {text: aElem.attr('title'), value: valElem.attr('data-critertion-number-max')}
							]);
						} else {
							self.selectedValues.removeAll();
						}
					});
				},
				doRender : function($super, html) {
					$super(html);
					var self = this;
					this.element.find('.commit-action').bind('click', function(evt) {
						self.onClicked(evt);
					}).hide();
					this.element.find('[name="lui-critertion-min"], [name="lui-critertion-max"]').bind('keyup', function(evt) {
						self.onKeyup(evt);
					});
				},
				onClicked: function(evt) {
					this.addValue();
				},
				onKeyup : function(evt) {
					if (evt.keyCode == 13) {
						this.addValue();
					} else {
						this.validateNumber();
						var min = this.element.find('[name="lui-critertion-min"]');
						var max = this.element.find('[name="lui-critertion-max"]');
						if (min.val() == '' && max.val() == '') {
							this.element.find('.commit-action').hide();
							return;
						}
						this.element.find('.commit-action').show();
					}
				},
				addValue: function() {
					var min = this.element.find('[name="lui-critertion-min"]');
					var max = this.element.find('[name="lui-critertion-max"]');
					if (!this.validateNumber()) {
						return false;
					}
					this.element.find('.commit-action').hide();
					if (min.val() == '' && max.val() == '') {
						this.selectedValues.removeAll();
						return;
					}
					this.selectedValues.setAll([
					     min.val(),
					     max.val()
					]);
				},
				validateNumber: function() {
					var min = this.element.find('[name="lui-critertion-min"]').val();
					var max = this.element.find('[name="lui-critertion-max"]').val();
					if (min == '' && max == '') {
						this.hideValMsg();
						return true;
					}
					if (!this.testNumber(min)) {
						this.showValMsg();
						return false;
					}
					if (!this.testNumber(max)) {
						this.showValMsg();
						return false;
					}
					if(min != '' && max != ''
						&& this.testNumber(min)
						&& this.testNumber(max)
						&& Number(max) < Number(min)){
						this.showValMsg(lang['ui.criteria.number.area.validate']);
						return false;
					}

					this.hideValMsg();
					return true;
				},
				testNumber: function(val) {
					if (val == '') {
						return true;
					}
					if(isNaN(val)){
						return false;
					}
					return (/^-?[0-9]\d*$/.test(val) || /^-?[0-9]\d*\.\d*|0\.\d*[0-9]\d*$/.test(val));
				},
				showValMsg: function(msg) {
					var text = msg || lang['ui.criteria.number.validate'];
					this.element.find('.lui_criteria_number_validate_container').show().find('.text').text(text);
				},
				hideValMsg: function() {
					this.element.find('.lui_criteria_number_validate_container').hide();
				}
			});
			
			//	//只在人事档案最近生日页面使用
			var CalendarMothDayInput = CriterionInputDatas.extend({
					initProps : function($super, cfg) {
						$super(cfg);
						this.placeholder = cfg.placeholder;
					},
					supportMulti: function() {
						return false;
					},
					startup : function($super) {
						if (this.isStartup) {
							return;
						}
						if (!this.render) {
							this.setRender(new render.Template({
										src : require
												.resolve('./template/criterion-date.jsp#'),
										parent : this
									}));
							this.render.startup();
						}
						if (!this.source) {
							this.setSource(new source.Static({
										datas : [{
													'placeholder' : this.placeholder
												}],
										parent : this
									}));
							if(this.source.startup)
								this.source.startup();
						}
						$super();
					},
					doRender : function($super, html) {
						$super(html);
						var self = this;
						this.element.find('.commit-action').bind('click', function(evt) {
							self.onClicked(evt);
						})
						
						this.element.find('.date-select').bind({
							'change' : function(evt) {
							self.checked(evt);
							}
						});
						LUI.placeholder(this.element);
						var date=self.getDefaultDate();
						
						if(typeof(date[0]) !="undefined"){
							var startDate=date[0];
							var endDate=date[1];
							var strs= new Array(); //定义一数组 
							strs=startDate.split("-"); //字符分割 
							$("#startMonth").val(strs[0]);
							$("#startDay").val(strs[1]);
							
							strs=endDate.split("-");
							$("#endMonth").val(strs[0]);
							$("#endDay").val(strs[1]);
						}
						
					},
					
					checked: function(evt){
						this.hideValMsg();
						this.element.find('.commit-action').show();
						
					},
					onClicked: function(evt) {
						if(this.validate()){
							this.addValue();
							return true;
						}
						return false;
					},
					
					validate: function(){
						var startMonth=$("#startMonth option:selected").val(); 
						var startDay=$("#startDay option:selected").val();
						
						var endMonth=$("#endMonth option:selected").val(); 
						var endDay=$("#endDay option:selected").val(); 		
						
						var startNum =parseInt(startMonth+startDay);
						var endNum =parseInt(endMonth+endDay);
						if(startNum>endNum){
							this.showValMsg();
							return false;
						}
						this.hideValMsg();
						return true;
					},
					
					addValue: function() {
						var startMonth=$("#startMonth option:selected").val(); 
						var startDay=$("#startDay option:selected").val();
						
						var endMonth=$("#endMonth option:selected").val(); 
						var endDay=$("#endDay option:selected").val(); 		
						
						var startDate=startMonth+"-"+startDay;
						var endDate=endMonth+"-"+endDay;
						
						if (startDate == '' && endDate=='') {
							this.selectedValues.removeAll();
							return;
						}
						this.selectedValues.setAll([startDate,endDate]);
					},
					
					showValMsg: function() {
						var text = lang['ui.criteria.calendar.time.area.validate'];
						this.element.find('.lui_criteria_number_validate_container').show().find('.text').text(text);
					},
					hideValMsg: function() {
						this.element.find('.lui_criteria_number_validate_container').hide();
					},
					
					getDefaultDate: function(){
						var name,value; 
						var hash = '';
						try{
							hash = top.location.hash; //取得整个地址栏
						}catch(e){
							hash = location.hash;
						}
						hash = decodeURIComponent(hash);
						var arr = hash.split("fdBirthdayOfYear:"); //各个参数放到数组里
						var startDate = arr[1];
						if(typeof(startDate) != "undefined"){
							var temp = startDate.split(";");
							startDate = temp[0];
							temp = arr[2].split(/(?:[&|;]+)/);
							var endDate = temp[0];
							var strs= new Array(startDate,endDate);
							return strs;
						}
					}
					
				});
			//薪酬管理
			var CalendarYearMonthInput = CriterionInputDatas.extend({
					initProps : function($super, cfg) {
						$super(cfg);
						this.placeholder = cfg.placeholder;
					},
					supportMulti: function() {
						return false;
					},
					startup : function($super) {
						if (this.isStartup) {
							return;
						}
						if (!this.render) {
							this.setRender(new render.Template({
										src : require
												.resolve('./template/criterion-year-month.jsp#'),
										parent : this
									}));
							this.render.startup();
						}
						if (!this.source) {
							this.setSource(new source.Static({
										datas : [{
													'placeholder' : this.placeholder
												}],
										parent : this
									}));
							if(this.source.startup)
								this.source.startup();
						}
						$super();
					},
					doRender : function($super, html) {
						$super(html);
						var self = this;
						this.element.find('.commit-action').bind('click', function(evt) {
							self.onClicked(evt);
						})
						
						this.element.find('.date-select').bind({
							'change' : function(evt) {
							self.checked(evt);
							}
						});
						LUI.placeholder(this.element);
						var date=self.getDefaultDate();
						
						if(typeof(date) !="undefined"){
							var startDate=date[0];
							var strs= new Array(); //定义一数组 
							strs=startDate.split("-"); //字符分割 
							$("#startYear").val(strs[0]);
							$("#startMonth").val(strs[1]);
						}
					},
					
					checked: function(evt){
						this.hideValMsg();
						this.element.find('.commit-action').show();
						
					},
					onClicked: function(evt) {
						if(this.validate()){
							this.addValue();
							return true;
						}
						return false;
					},
					
					validate: function(){
						this.hideValMsg();
						return true;
					},
					
					addValue: function() {
						var startMonth=$("#startMonth option:selected").val(); 
						var startYear=$("#startYear option:selected").val();
						
						var startDate=startYear+"-"+startMonth;
						
						if (startDate == '') {
							this.selectedValues.removeAll();
							return;
						}
						this.selectedValues.setAll([startDate]);
					},
					
					showValMsg: function() {
						var text = lang['ui.criteria.calendar.time.area.validate'];
						this.element.find('.lui_criteria_number_validate_container').show().find('.text').text(text);
					},
					hideValMsg: function() {
						this.element.find('.lui_criteria_number_validate_container').hide();
					},
					
					getDefaultDate: function(){
						var name,value; 
						var hash = '';
						try{
							hash = top.location.hash; //取得整个地址栏
						}catch(e){
							hash = location.hash;
						}
						hash = decodeURIComponent(hash);
						var arr = hash.split("fdTaxMonth:"); //各个参数放到数组里
						var startDate = arr[1];
						if(typeof(startDate) != "undefined"){
							var temp = startDate.split(";");
							startDate = temp[0];
							var strs= new Array(startDate);
							return strs;
						}
					}
				});

			
			exports.TextInput = TextInput;
			exports.NumberInput = NumberInput;
			//只在人事档案最近生日页面使用
			exports.CalendarMothDayInput =CalendarMothDayInput;
			exports.CalendarYearMonthInput=CalendarYearMonthInput;
});