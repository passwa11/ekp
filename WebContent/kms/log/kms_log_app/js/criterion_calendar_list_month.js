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
			this.renderUrl = "./../tmpl/criterion-new-calendar-cell-list-month.jsp#";
			this.isDate = false;
			this.isTime = false;
			this. _DATA_KEY = "0"
			$super(cfg);
		},
		startup : function($super) {
			if (this.isStartup) {
				return;
			}
			
			// 初始化所有日期区域
			this.allDates = {};
			// 全部
			var now = new Date();
			var month = now.getMonth() + 1;
			if(month < 10){
				month = "0"+month;
			}
			var yearMonth =  now.getFullYear()+"-" + month;
			this.allDates[this._DATA_KEY] = [yearMonth];
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
			$super();
		},
		_getData : function() {
			return [{
				'name' : ['date', 'from', this.cid].join('-'),
				'placeholder' : 'yyyy-MM'

			}, {
				'name' : ['date', 'to', this.cid].join('-'),
				'placeholder' :  'yyyy-MM'
			}];
		},
		doRender : function($super, html) {
			$super(html);
			this.element.find("." + this.BTN_CLASS).hide();
			LUI.placeholder(this.element);
			var self = this;
			self.selectedValues.setAll(self.allDates[self._DATA_KEY]);
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
						}else{
							if("all"!=type)
								parent.removeClass('selected');
//							self.clearAll();
						}
						break;
					}
					parent = parent.parent();
				}
				if(type!=""){
					var date = self.allDates[self._DATA_KEY][0];
					var dates = date.split('-');
					var year = dates[0];
					var month = dates[1];
					if(month.charAt(0) == '0'){
						month = parseInt(month[1]);
					}
					//上一月
					if("1" == type){
						date = new Date(year, month -1-1,1);
					}else if("2" == type){
						//下一月
						date = new Date(year, month,1);
					}
					var month = date.getMonth() + 1;
					if(month < 10){
						month = "0"+month;
					}
					var yearMonth =  date.getFullYear()+"-" + month;
					self.allDates[self._DATA_KEY] = [yearMonth];
					self.selectedValues.setAll(self.allDates[self._DATA_KEY]);
				}
				
			});
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
			return lang['kmsLogApp.recent.prompt4'];
		}
	});
	
	exports.CriterionDateDatas = CriterionDateDatas;
});