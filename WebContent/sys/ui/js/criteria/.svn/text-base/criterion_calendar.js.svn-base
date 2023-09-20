define(function(require, exports, module) {
	var lang = require('lang!sys-ui');
	var select_panel = require('lui/criteria/select_panel');
	var init_date = require('lui/criteria/criterion_calendar_init_date');
	var render = require('lui/view/render');
	var source = require('lui/data/source');
	var $ = require('lui/jquery');
	var dialog = require('lui/dialog');
	
	var CriterionInputDatas = select_panel.CriterionInputDatas;
	
	
	//时间格式校验
	function timeValidate(v) {
		var res = false;
		var regTime = /^[0-2][\d]:[0-5][\d]$/;
		if (v) {
			res = regTime.test(v);
		}
		return res;
	}
	//日期格式校验
	function dateValidate(v) {
		var res = false;
		var regDate = /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/;
		if (Com_Parameter['Lang'] != null && (Com_Parameter['Lang'] != 'zh-cn' && Com_Parameter['Lang'] != 'zh-hk' && Com_Parameter['Lang'] != 'ja-jp')) {
			//regDate = /^(((0[13578]|1[02])\/(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)\/(0[1-9]|[12][0-9]|30))|(02\/(0[1-9]|[1][0-9]|2[0-9])))\/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})$/;
			if('dd/MM/yyyy'==Com_Parameter.Date_format){  
				regDate = /^(((0[1-9]|[12][0-9]|3[01])\/(0[13578]|1[02]))|((0[1-9]|[12][0-9]|30)\/(0[469]|11))|((0[1-9]|[1][0-9]|2[0-9])\/02))\/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})$/;
			}else{
				regDate = /^(((0[13578]|1[02])\/(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)\/(0[1-9]|[12][0-9]|30))|(02\/(0[1-9]|[1][0-9]|2[0-9])))\/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})$/;
			}
		}
		if (v) {
			res = regDate.test(v);
		}
		return res;
	}
	//日期时间格式校验
	function dateTimeValidate(v) {
		var res = false;
		var regDate = /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/;
		if (Com_Parameter['Lang'] != null && (Com_Parameter['Lang'] != 'zh-cn' && Com_Parameter['Lang'] != 'zh-hk' && Com_Parameter['Lang'] != 'ja-jp')) {
			//regDate = /^(((0[13578]|1[02])\/(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)\/(0[1-9]|[12][0-9]|30))|(02\/(0[1-9]|[1][0-9]|2[0-9])))\/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})$/;
			if('dd/MM/yyyy'==Com_Parameter.Date_format){  
				regDate = /^(((0[1-9]|[12][0-9]|3[01])\/(0[13578]|1[02]))|((0[1-9]|[12][0-9]|30)\/(0[469]|11))|((0[1-9]|[1][0-9]|2[0-9])\/02))\/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})$/;
			}else{
				regDate = /^(((0[13578]|1[02])\/(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)\/(0[1-9]|[12][0-9]|30))|(02\/(0[1-9]|[1][0-9]|2[0-9])))\/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})$/;
			}
		}
		var regTime = /^[0-2][\d]:[0-5][\d]$/;
		if (v) {
			var dateAry = v.split(/\s+/);
			if (dateAry.length == 2) {
				res = regDate.test(dateAry[0]) && regTime.test(dateAry[1]);
			}
		}
		return res;
	}
	
	var CriterionCalendarDatas = CriterionInputDatas.extend({

		className : "criterion-calendar",

		initProps : function($super, cfg) {
			this.INPUT_NAME = "data-lui-date-input-name";
			this.VALIDATE_NAME = "data-lui-date-input-validate";
			this.VALIDATE_CONTAINER_CLASS = "lui_criteria_date_validate_container";
			this.INPUT_PLACEHOLDER = "placeholder";
			this.SELECT_CLASS = "lui_criteria_date_select";
			this.LI_CLASS = "lui_criteria_date_li";
			this.BTN_CLASS = "commit-action";
			this.type = "";
			this.renderUrl = "./template/criterion-new-calendar-cell.jsp#";
			this.isDate = false;
			this.isTime = false;
			this.isDateTime = false;
			$super(cfg);
		},
		startup : function($super) {
			if (this.isStartup) {
				return;
			}
			if (!this.render) {
				this.render = new render.Template({
					src : require
							.resolve(this.renderUrl),
					parent : this
				});
				this.render.startup();
			}
			if (!this.source) {
				// 待修改
				var data = {
					datas : this._getData(),
					parent : this
				};
				this.source = new source.Static(data);
				if (this.source.startup)
					this.source.startup();
			}
			
			// #34212 日期区间选择控件易用性优化，只对日期控件做处理，时间控件理论上不受影响
			//this.isDate = this.type == "selectDate";
			if(this.isDate || this.isDateTime) {
				
				// 快速选择项目
//				this.TYPE_ALL = "all"; // 全部
//				this.TYPE_TODAY = "today"; // 今天
//				this.TYPE_WEEK = "week"; // 本周
//				this.TYPE_MONTH = "month"; // 本月
//				this.TYPE_QUARTER = "quarter"; // 本季
//				this.TYPE_YEAR = "year"; // 本年
//				this.TYPE_LAST_MONTH = "last_month"; // 上一月
//				this.TYPE_LAST_YEAR = "last_year"; // 上一年
//				this.TYPE_CUSTOM = "custom"; // 自定义
				
				this.TYPE_WEEK = "1";
				this.TYPE_MONTH = "2";
				this.TYPE_3MONTH = "3";
				this.TYPE_HALFYEAR = "4";
				this.TYPE_YEAR = "5";
				this.TYPE_ALL = "6";
				
				// 初始化所有日期区域
				this.allDates = {};
				// 全部
				this.allDates[this.TYPE_ALL] = [];
//				// 今天
//				this.allDates[this.TYPE_TODAY] = [init_date.getToday(), init_date.getToday()];
//				// 本周
//				this.allDates[this.TYPE_WEEK] = [init_date.getWeekStartDate(), init_date.getWeekEndDate()];
//				// 本月
//				this.allDates[this.TYPE_MONTH] = [init_date.getMonthStartDate(), init_date.getMonthEndDate()];
//				// 本季
//				this.allDates[this.TYPE_QUARTER] = [init_date.getQuarterStartDate(), init_date.getQuarterEndDate()];
//				// 本年
//				this.allDates[this.TYPE_YEAR] = [init_date.getYearStartDate(), init_date.getYearEndDate()];
//				// 上一月
//				this.allDates[this.TYPE_LAST_MONTH] = [init_date.getLastMonthStartDate(), init_date.getLastMonthEndDate()];
//				// 上一年
//				this.allDates[this.TYPE_LAST_YEAR] = [init_date.getLastYearStartDate(), init_date.getLastYearEndDate()];
				
				this.allDates[this.TYPE_WEEK] = [init_date.getLastWeek(), init_date.getToday()];
				
				this.allDates[this.TYPE_MONTH] = [init_date.getLastMonth(), init_date.getToday()];
				
				this.allDates[this.TYPE_3MONTH] = [init_date.getLast3Month(), init_date.getToday()];
				
				this.allDates[this.TYPE_HALFYEAR] = [init_date.getLastHalfYear(), init_date.getToday()];
				
				this.allDates[this.TYPE_YEAR] = [init_date.getLastYear(), init_date.getToday()];
			}
			var as =  this.element.find("a");
			$super();
		},
		_getData : function() {
			return [{
				'name' : ['date', 'from', this.cid].join('-'),
				'placeholder' : seajs.data.env.pattern.date

			}, {
				'name' : ['date', 'to', this.cid].join('-'),
				'placeholder' : seajs.data.env.pattern.date
			}];
		},
		doRender : function($super, html) {
			$super(html);
			this.element.find("." + this.BTN_CLASS).hide();
			LUI.placeholder(this.element);
			this.bindValidate();
			
			if(this.isDate) {
				var self = this;	
				
				if(this.defaultValue!=null){
					var as =  this.element.find("a");
					for(var i=0;i<as.length;i++){
						var parent = $(as[i]);
						if(this.defaultValue != parent.attr('data-lui-val')){
							if (parent.hasClass('selected')){
									parent.removeClass('selected');
							}
						}else{
							if (!parent.hasClass('selected')){
								parent.addClass('selected');
							}
							self.selectedValues.defaultValueDatas(self.allDates[parent.attr('data-lui-val')]);
						}
					}
				}
				
				
				
				// 隐藏“不限”按钮
				//self.element.find("a.selected").hide();
				// 默认隐藏日期区域
				var date_li = self.element.find("." + self.LI_CLASS);
				//date_li.hide();
				
				// 绑定下拉框选择事件
//				this.element.find("select.selected").on("change", function() {
//					if("custom" == this.value) {
//						//date_li.show(); // 显示自定义日期选择框
//						self.clearValidate();
//					} else if(this.value.length > 0) {
//						//date_li.hide(); // 隐藏自定义日期选择框
//						// 获取内置日期数据
//						self.selectedValues.setAll(self.allDates[this.value]);
//					}
//				});
				
				this.allActionArea = this.element.find("li[name='chekbox']");
				this.allActionArea.bind('click', function(evt) {
					// 样式修改
					var as =  self.element.find("a");
					var type;
					var parent = $(evt.target);
					while (parent.length > 0) {
						if (parent[0].tagName == 'A') {
							
							type = parent.attr('data-lui-val');
							if (!parent.hasClass('selected')){
								parent.addClass('selected');
								
								self.selectedValues.setAll(self.allDates[parent.attr('data-lui-val')]);
							}else{
								if("all"!=type)
									parent.removeClass('selected');
								self.clearAll();
							}
							break;
						}
						parent = parent.parent();
					}
					
					if(type!=""){
						for(var i=0;i<as.length;i++){
							var parent = $(as[i]);
							if(type != parent.attr('data-lui-val')){
								if (parent.hasClass('selected')){
										parent.removeClass('selected');
								}
							}
						}
					}
					
				});
			} else if(this.isTime) {
				var self = this;	
				this.allActionArea = this.element.find("li[name='chekbox']");
				this.allActionArea.bind('click', function(evt) {
					// 样式修改
					var as =  self.element.find("a");
					var type;
					var parent = $(evt.target);
					while (parent.length > 0) {
						if (parent[0].tagName == 'A') {
							
							type = parent.attr('data-lui-val');
							if (!parent.hasClass('selected')){
								parent.addClass('selected');
								
								self.selectedValues.setAll(self.allDates[parent.attr('data-lui-val')]);
							}else{
								if("all"!=type)
									parent.removeClass('selected');
								self.clearAll();
							}
							break;
						}
						parent = parent.parent();
					}
					
					if(type!=""){
						for(var i=0;i<as.length;i++){
							var parent = $(as[i]);
							if(type != parent.attr('data-lui-val')){
								if (parent.hasClass('selected')){
										parent.removeClass('selected');
								}
							}
						}
					}
					
				});
			} else if(this.isDateTime){
				var self = this;	
				if(this.defaultValue!=null){
					var as =  this.element.find("a");
					for(var i=0;i<as.length;i++){
						var parent = $(as[i]);
						if(this.defaultValue != parent.attr('data-lui-val')){
							if (parent.hasClass('selected')){
									parent.removeClass('selected');
							}
						}else{
							if (!parent.hasClass('selected')){
								parent.addClass('selected');
							}
							
							var intArr = self.allDates[parent.attr('data-lui-val')];
							var newArr = [];
							newArr.push(intArr[0]+" 00:00");
							newArr.push(intArr[1]+" 23:59");
							
							self.selectedValues.defaultValueDatas(newArr);
						}
					}
				}
				
				this.allActionArea = this.element.find("li[name='chekbox']");
				this.allActionArea.bind('click', function(evt) {
					// 样式修改
					var as =  self.element.find("a");
					var type;
					var parent = $(evt.target);
					while (parent.length > 0) {
						if (parent[0].tagName == 'A') {
							
							type = parent.attr('data-lui-val');
							if (!parent.hasClass('selected')){
								parent.addClass('selected');
								 
								var intArr = self.allDates[parent.attr('data-lui-val')];
								var newArr = [];
								if(intArr && intArr.length == 2) {
									newArr.push(intArr[0]+" 00:00");
									newArr.push(intArr[1]+" 23:59");
								}
								self.selectedValues.setAll(newArr);
							}else{
								if("all"!=type)
									parent.removeClass('selected');
								self.clearAll();
							}
							break;
						}
						parent = parent.parent();
					}
					
					if(type!=""){
						for(var i=0;i<as.length;i++){
							var parent = $(as[i]);
							if(type != parent.attr('data-lui-val')){
								if (parent.hasClass('selected')){
										parent.removeClass('selected');
								}
							}
						}
					}
					
				});
			}
		},

		sychOkBtnShow : function() {
			var __show = false;
			var btn = this.element.find("." + this.BTN_CLASS);
			var values = this.selectedValues.values;
			var valuesEmpty = values == null || values.length == 0;
			this.element.find('[' + this.INPUT_NAME + ']').each(function(i) {
				if ((valuesEmpty && this.value != '')
						|| (!valuesEmpty && values[i].value != this.value)) {
					__show = true;
					// btn.show();
					return false;
				}
					// btn.hide();
			});
			// 判断是否通过校验
			this.element.find('.' + this.VALIDATE_CONTAINER_CLASS).each(
					function(i) {
						if ($(this).css('display') == 'block') {
							__show = false;
							return false;
						}
					});
			if (__show)
				btn.show()
			else
				btn.hide();
			
			if(this.isDate) {
				// 获取已选择的日期区域，修改下拉框选项
				var inputValues = [];
				this.element.find("[" + this.INPUT_NAME + "]").each(function() {
					inputValues.push(this.value);
				});
				var _type = this.selectTypeByDate(inputValues);
				// 初始化下拉框的选项
				//this.element.find("select.selected").val(_type);
				
				var as =  this.element.find("a");
				for(var i=0;i<as.length;i++){
					var parent = $(as[i]);
					if(_type != parent.attr('data-lui-val')){
						if (parent.hasClass('selected')){
								parent.removeClass('selected');
						}
					}else{
						parent.addClass('selected');
					}
				}
			}
		},
		// 根据已填充好的日期查询下拉框的类型
		selectTypeByDate : function(inputValues) {
			if(inputValues.length == 2 && inputValues[0].length < 1 && inputValues[1].length < 1)
				return this.TYPE_ALL;
			for(var type in this.allDates) {
				if(this.allDates[type][0] == inputValues[0] && this.allDates[type][1] == inputValues[1]){
					return type;
				}
			}
			
			// 除了上面的类型，剩下的都属于自定义
			this.element.find("." + this.LI_CLASS).show();
			return this.TYPE_CUSTOM;
		},
		onSelected : function(evt) {
			// 修复#154939:时间筛选组件结束日期早于开始日期时。
			$area_container = this.element.find('.' + this.VALIDATE_CONTAINER_CLASS + '.area');
			$area_validate = $area_container.find('.lui_criteria_date_validate');
			$area_validate.css('visibility', 'hidden');
			var target = evt.target;
			if (target == this.element[0]) {
				return;
			}
			var $target = $(target);
			// 日期选择
			if ($target.hasClass(this.SELECT_CLASS)
					|| $target.attr('type') == 'text') {
				// 待修改
				var parent = $target.parent();
				var $input = parent.children("[" + this.INPUT_NAME + "]");
				var self = this;
				var btn = this.element.find("." + this.BTN_CLASS);
				window[this.type].call(parent
								.find("." + this.SELECT_CLASS + "")[0],
						window.event || evt, $input.attr(this.INPUT_NAME),
						null, function(field) {
							self.validate($target);
							self.sychOkBtnShow();
						});
			}
			// 筛选确定
			else if ($target.hasClass(this.BTN_CLASS)) {
				var inputValues = [];
				this.element.find("[" + this.INPUT_NAME + "]").each(function() {
							inputValues.push(this.value);
						});
				this.selectedValues.setAll(inputValues);
			}
		},
		buildIntTime:function(fdTime){
			var timestamp = 0;
			try{
				timestamp = new Date(fdTime).getTime();
			}catch(e){}
			return timestamp;
		},
		onChanged : function(val) {
			this.element.find('[' + this.INPUT_NAME + ']').each(function(i) {
						if (val.values != null && val.values.length < i + 1) {
							$(this).val('');
						} else {
							if (!val.values[i].text) { // 修正显示信息
								val.values[i].text = val.values[i].value;
							}
							$(this).val(val.values[i].value);
						}
					});
			this.switchAllActionAreaSelected();
			this.sychOkBtnShow();
		},
		clearAll : function($super) {
			var values = this.selectedValues.values;
			if (values == null || values.length == 0) {
				this.onChanged({
							values : []
						});
				this.element.find("." + this.BTN_CLASS).hide();
				this.clearValidate();
			}
			$super();
		},
		// 文本框输入日期格式校验
		bindValidate : function() {
			var self = this;
			var $inputs = this.element.find('[' + this.INPUT_NAME + ']');
			$inputs.bind('blur', function() {
						self.validate(this);
					});
			//$inputs.bind('focus', function() {
			//	$(this).attr('placeholder', seajs.data.env.pattern.date);
			//});
		},
		clearValidate : function() {
			var $container = this.element.find('.'
					+ this.VALIDATE_CONTAINER_CLASS), $validates = this.element
					.find('[' + this.VALIDATE_NAME + ']');
			$container.hide();
			$validates.css('visibility', 'hidden');
		},
		validateArea : function() {
			// 校验区域是否合法
			var res = true,
				inputValues = [];
			this.element.find("[" + this.INPUT_NAME + "]").each(function() {
				inputValues.push(this.value);
			});
			if(inputValues.length == 2) {
				var startTime = inputValues[0];
				var endTime = inputValues[1];
				if(startTime != '' && endTime != ''){
					startTime = this.buildIntTime(startTime);
					endTime = this.buildIntTime(endTime);
					
					if(Number(endTime) < Number(startTime)) {
						res = false;
					}
				}
			}
			return res;
		},
		validate : function(target) {
			//#111656 校验器有时候失效问题
			var _target = $(target).hasClass('lui_criteria_date_select') ? $(target).prev() : target;
			var res = this.executeValidate($(_target).val()), 
				name = $(_target).attr(this.INPUT_NAME), 
				// 日期控件提示信息
				$validate = this.element.find('[' + this.VALIDATE_NAME + '="' + name + '"]'), 
				$container = this.element.find('.' + this.VALIDATE_CONTAINER_CLASS),
				// 日期区域提示信息
				$area_container = this.element.find('.' + this.VALIDATE_CONTAINER_CLASS + '.area'),
				$area_validate = $area_container.find('.lui_criteria_date_validate');
			if (!$(_target).val()) {
				$container.hide();
				return false;
			}
			if (!res) {
				var msg = this.validateMsg();
				$validate.find('.text').html(msg);
				$container.show();
				$validate.css('visibility', 'visible');
				
				// 如果日期格式非法，则不显示区域校验信息
				$area_validate.css('visibility', 'hidden');
				$area_container.hide();
			} else {
				$validate.css('visibility', 'hidden');
				
				res = this.validateArea();
				if(!res) {
					$area_container.find('.text').html(lang['ui.criteria.calendar.time.area.validate']);
					$area_validate.css({'visibility':'visible', 'width': 450});
				} else {
					$area_validate.css('visibility', 'hidden');
					$area_container.hide();
				}
			}

			var __hasVisibility = false;
			this.element.find('[' + this.VALIDATE_NAME + ']').each(function() {
						if ($(this).css('visibility') == 'visible') {
							__hasVisibility = true;
							return false;
						}
					});
			if (!__hasVisibility)
				$container.hide();
			
			// 如果区域校验失败，需要显示校验信息
			if($area_validate.css('visibility') == 'visible')
				$area_container.show();
			
			return res;
		},
		// 执行校验
		executeValidate : function(v) {
			return true;
		},
		// 校验失败返回信息
		validateMsg : function() {}
	});

	var CriterionTimeDatas = CriterionCalendarDatas.extend({
		initProps : function($super, cfg) {
			$super(cfg);
			this.type = "selectTime";
			this.isTime = true;
			this.renderUrl = "./template/criterion-new-calendar-time-cell.jsp#";
			//this.SELECT_CLASS = "lui_criteria_time_select";
		},
		executeValidate : timeValidate,
		validateMsg : function() {
			return lang['ui.criteria.calendar.time.validate'];
		}
	});

	var CriterionDateDatas = CriterionCalendarDatas.extend({
		initProps : function($super, cfg) {
			$super(cfg);
			this.type = "selectDate";
			this.isDate = true;
		},
		executeValidate : dateValidate,
		validateMsg : function() {
			return lang['ui.criteria.calendar.date.validate'];
		}
	});

	var CriterionDateTimeDatas = CriterionCalendarDatas.extend({
		initProps : function($super, cfg) {
			$super(cfg);
			this.type = "selectDateTime";
			this.isDateTime = true;
		},
		executeValidate : dateTimeValidate,
		validateMsg : function() {
			return lang['ui.criteria.calendar.datetime.validate'];
		}
	});
	
	/*****单个日期选择控件******/
	var CriterionSingleCalendarDatas = CriterionCalendarDatas.extend({
		initProps : function($super, cfg) {
			$super(cfg);
			this.renderUrl = "./template/criterion-calendar-single.jsp#";
		},
		_getData : function() {
			return [{
				'name' : ['date', 'single', this.cid].join('-'),
				'placeholder' : seajs.data.env.pattern.date

			}];
		}
	});
	
	////////////////////
	var CriterionSingleTimeDatas = CriterionSingleCalendarDatas.extend({
		initProps : function($super, cfg) {
			$super(cfg);
			this.type = "selectTime";
		},
		executeValidate : timeValidate,
		validateMsg : function() {
			return lang['ui.criteria.calendar.datetime.validate'];
		}
	});

	var CriterionSingleDateDatas = CriterionSingleCalendarDatas.extend({
		initProps : function($super, cfg) {
			$super(cfg);
			this.type = "selectDate";
		},
		executeValidate : dateValidate,
		validateMsg : function() {
			return lang['ui.criteria.calendar.date.validate'];
		}
	});
	
	var CriterionSingleDateTimeDatas = CriterionSingleCalendarDatas.extend({
		initProps : function($super, cfg) {
			$super(cfg);
			this.type = "selectDateTime";
		},
		executeValidate : dateTimeValidate,
		validateMsg : function() {
			return lang['ui.criteria.calendar.datetime.validate'];
		}
	});
	/////////////////////
	
	exports.CriterionTimeDatas = CriterionTimeDatas;
	exports.CriterionDateDatas = CriterionDateDatas;
	exports.CriterionDateTimeDatas = CriterionDateTimeDatas;
	//
	exports.CriterionSingleTimeDatas = CriterionSingleTimeDatas;
	exports.CriterionSingleDateDatas = CriterionSingleDateDatas;
	exports.CriterionSingleDateTimeDatas = CriterionSingleDateTimeDatas;
});