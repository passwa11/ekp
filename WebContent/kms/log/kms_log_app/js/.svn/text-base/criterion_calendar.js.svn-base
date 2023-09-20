define(function(require, exports, module) {
	var lang = require('lang!kms-log');
	var select_panel = require('lui/criteria/select_panel');
	var init_date = require('lui/criteria/criterion_calendar_init_date');
	var render = require('lui/view/render');
	var source = require('lui/data/source');
	var $ = require('lui/jquery');
	
	var CriterionInputDatas = select_panel.CriterionInputDatas;
	
	//日期格式校验
	function dateValidate(v) {
		var res = false;
		var regDate = /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/;
		if (Com_Parameter['Lang'] != null && (Com_Parameter['Lang'] != 'zh-cn' && Com_Parameter['Lang'] != 'zh-hk' && Com_Parameter['Lang'] != 'ja-jp')) {
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
			this.renderUrl = "./../tmpl/criterion-new-calendar-cell.jsp#";
			this.isDate = false;
			this.isTime = false;
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
			if(this.isDate) {
				
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
			} else {
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
					return false;
				}
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
			$("."+self.className).find("li").bind({click: function() {
				if(this.className.indexOf(self.LI_CLASS) == -1){
					var $container = $('.' + self.VALIDATE_CONTAINER_CLASS);
					$container.hide();
				}
			}});
			$inputs.bind({blur: function() {
				self.validate(this);
			}});
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
			// res == 1: endTime<startTime
			// res == 2: startTime > current - 30
			// res == 3: endTime > current
			var res = 0,
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
					startTime = Number(new Date(startTime));
					endTime = Number(new Date(endTime));
					var now8time = new Date(new Date(new Date(new Date(new Date().setHours(8)).setMinutes(0)).setSeconds(0))).setMilliseconds(0);
					var timeBefore30 = new Date(now8time).setMonth((new Date().getMonth()-1));
					res = 4;
					if(endTime < startTime) {
						res = 1;
					}
					if(startTime < timeBefore30) {
						res = 2
					} 
					if(endTime > new Date().getTime()) {
						res = 3;
					}
				}
			}
			return res;
		},
		validate : function(target) {
			var res = this.executeValidate($(target).val()), 
				name = $(target).attr(this.INPUT_NAME), 
				// 日期控件提示信息
				$validate = this.element.find('[' + this.VALIDATE_NAME + '="' + name + '"]'), 
				$container = this.element.find('.' + this.VALIDATE_CONTAINER_CLASS),
				// 日期区域提示信息
				$area_container = this.element.find('.' + this.VALIDATE_CONTAINER_CLASS + '.area'),
				$area_validate = $area_container.find('.lui_criteria_date_validate');
			if (!$(target).val()) {
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
				$validate.css('visibility', 'hidden')
				res = this.validateArea();
				if(res != 4) {
					if(res == 1) {
						$area_container.find('.text').html(lang['kmsLogApp.recent.prompt3']);
						$area_validate.css({'visibility':'visible', 'width': 450});
					}
					if(res == 2) {
						$area_container.find('.text').html(lang['kmsLogApp.recent.prompt2']);
						$area_validate.css({'visibility':'visible', 'width': 322});
					}
					if(res == 3) {
						$area_container.find('.text').html(lang['kmsLogApp.recent.prompt1']);
						$area_validate.css({'visibility':'visible', 'width': 300});
					}
					$("."+this.BTN_CLASS).hide();
				} else {
					$area_validate.css('visibility', 'hidden');
					$area_container.hide();
					$("."+this.BTN_CLASS).show();
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

	var CriterionDateDatas = CriterionCalendarDatas.extend({
		initProps : function($super, cfg) {
			$super(cfg);
			this.type = "selectDate";
			this.isDate = true;
			
		},
		executeValidate : dateValidate,
		validateMsg : function() {
			return lang['kmsLogApp.recent.prompt4'];
		}
	});
	
	exports.CriterionDateDatas = CriterionDateDatas;
});