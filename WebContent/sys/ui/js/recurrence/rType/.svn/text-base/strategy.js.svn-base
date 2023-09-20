/**
 * 策略
 */
define(function(require, exports, module) {
	
	var base = require('lui/base');
	var layout = require('lui/view/layout');
	var env = require('lui/util/env');
	
	var uuid = 0;
	
	var BaseStrategy = base.Base.extend({
		initProps : function(props){
			props = props || {};
			this.owner = props.owner;
			this.tmpParams = props.tmpParams || {}; //模板参数
			this.$container = props.$container;
		},
		//生成节点,支持异步;目前暂时只支持生成TR
		element : function(){
			this.$element = null;
			if(this.html){
				this.$element = $(this.html);
				this.addValidation();
				return this.$element;
			}else if(this.templateURI){
				var defer = $.Deferred();
				var _layout = new layout.Template({
					src : require.resolve(this.templateURI + '#'),
					param : $.extend({
						cid : this.owner.cid
					},this.tmpParams),
					parent : this
				});
				_layout.startup();
				_layout.get(this,$.proxy(function(obj){
					this.$element = $(obj);
					this.addValidation(obj);
					defer.resolve(this.$element);
				},this));
				return defer.promise();
			}
		},
		//根据值设置控件
		setValue : function(value){},
		//获取值
		getValue : function(){},
		//重置组件
		reset : function(){},
		//追加校验
		addValidation : function(){},
		getValidationName : function(){
			return 'RecurrenceValidation' + uuid++;
		}
	});
	
	//官方策略组
	var strategies = { };
	
	//重复频率策略
	strategies['interval'] = BaseStrategy.extend({
		templateURI : '../tmpl/interval.jsp',
		initProps : function($super,props){
			$super(props);
			this.tmpParams.text = this.tmpParams.text || '日';
		},
		setValue : function(value){
			var intervalReg = /INTERVAL=(\d+)/;
			if(intervalReg.test(value)){
				var interval = RegExp.$1;
				var $rTypeInterval = $('.rTypeInterval',this.$element);
				$rTypeInterval.val(interval);
			}
		},
		getValue : function(){
			var $rTypeInterval = $('.rTypeInterval',this.$element);
			return {
				interval : $rTypeInterval.val()
			};
		},
		reset : function(){
			var $rTypeInterval = $('.rTypeInterval',this.$element);
			$rTypeInterval.val('1');
		}
	});
	
	//周重复策略
	strategies['weeklyType'] = BaseStrategy.extend({
		templateURI : '../tmpl/weeklyType.jsp',
		setValue : function(value){
			var weekReg = /BYDAY=([^;]+)/;
			if(weekReg.test(value)){
				var weekArray = RegExp.$1.split(',');
				var $weeklyType = $('.weeklyType',this.$element);
				$weeklyType.prop('checked',false);
				for(var i = 0; i < weekArray.length; i++){
					$weeklyType.filter('[value="'+ weekArray[i] +'"]').prop('checked',true);
				}
			}
		},
		getValue : function(){
			return {
				byday : this.getWeekValue()
			}
		},
		getWeekValue : function(){
			var $weeklyType = $('.weeklyType',this.$element);
				weekValue = [];
			for(var i = 0; i < $weeklyType.length; i++){
				if($weeklyType.eq(i).prop('checked')){
					weekValue.push($weeklyType.eq(i).val());
				}
			}
			return weekValue.join(',');
		},
		addValidation : function(){
			if(!$KMSSValidation){
				return;
			}
			var self = this,
				validation = $KMSSValidation(),
				validationName = this.getValidationName(),
				$weeklyType = $('.weeklyType',this.$element);
			validation.addValidator(validationName,'重复时间不能为空',function(v, e, o){
				if(self.$element.filter(':hidden').length){
					return true;
				}
				return $weeklyType.filter(':checked').length;
			});
			//bad hack,校验在一个隐藏input上
			var validationElement = $('.weeklyTypeValidation',this.$element);
			validation.addElements(validationElement[0],validationName);
			$weeklyType.on('change',function(){
				validation.validateElement(validationElement[0]);
			});
		},
		reset : function(){
			var $weeklyType = $('.weeklyType',this.$element);
			var $validationElement = $('.weeklyTypeValidation',this.$element);
			$weeklyType.prop('checked',false);
			$weeklyType.eq(0).prop('checked',true);
			if($KMSSValidation){
				var validation = $KMSSValidation();
				setTimeout(function(){
					validation.validateElement($validationElement[0]);
				},1);
			}
		}
	});
	
	//月重复策略
	var weekDays = ['SU','MO','TU','WE','TH','FR','SA'];
	strategies['monthlyType'] = BaseStrategy.extend({
		templateURI : '../tmpl/monthlyType.jsp',
		setValue : function(value){
			var monthReg = /BYDAY=([^;]+)/;
			if(monthReg.test(value)){
				var $monthlyType = $('.monthlyType',this.$element);
				$monthlyType.prop('checked',false);
				$monthlyType.filter('[value="week"]').prop('checked',true);
			}
		},
		getValue : function(){
			var $monthlyType = $('.monthlyType',this.$element);
			if($monthlyType.filter('[value="week"]').prop('checked')){
				var owner = this.owner,
					weekNumber = Math.ceil( owner.baseDate.getDate() / 7),
					weekDay = weekDays[owner.baseDate.getDay()];
				return {
					byday : weekNumber + '' + weekDay
				}
			}
			return {};
		},
		reset : function(){
			var $monthlyType = $('.monthlyType',this.$element);
			$monthlyType.val('month');
		}
	});
	
	//结束条件策略
	strategies['endType'] = BaseStrategy.extend({
		templateURI : '../tmpl/endType.jsp',
		setValue : function(value){
			var $endType = $('.endType',this.$element),
				endTypeCountReg = /COUNT=(\d+)/,
				untilCountReg = /UNTIL=(\d+)/;
			if(endTypeCountReg.test(value)){
				var count  = RegExp.$1;
				var $endTypeCount = $('.endTypeCount',this.$element);
				$endTypeCount.val(count);
				var countEndType = $endType.filter(function(index, endTypeItem){
					return $(endTypeItem).val() == 'count';
				});
				countEndType.prop('checked',true)
			}
			if(untilCountReg.test(value)){
				var dateStr = RegExp.$1,
					date = new Date(dateStr.substring(0,4), parseInt(dateStr.substring(4,6)) -1, parseInt(dateStr.substring(6)) ),
					$untilNode = $('.endTypeSelectDate',this.$element);
				$untilNode.val(date.format(Com_Parameter.Date_format));
				var untilEndType = $endType.filter(function(index, endTypeItem){
					return $(endTypeItem).val() == 'until';
				});
				untilEndType.prop('checked',true)
			}
		},
		getValue : function(){
			var recurrenceObj = {},
				$endType = $('.endType',this.$element);
			var countEndType = $endType.filter(function(index, endTypeItem){
				return $(endTypeItem).val() == 'count';
			}),
				utilEndType = $endType.filter(function(index, endTypeItem){
				return $(endTypeItem).val() == 'until';
			});
			if(countEndType.prop('checked')){
				var $endTypeCount = $('.endTypeCount',this.$element);
				recurrenceObj['count'] = $endTypeCount.val() || '1';
			}
			if(utilEndType.prop('checked')){
				var $untilNode = $('.endTypeSelectDate',this.$element);
				var untilDate = formatDate($untilNode.val(),Com_Parameter.Date_format);
				recurrenceObj['until'] = this.formatDateStr(untilDate);
			}
			return recurrenceObj;
		},
		formatDateStr : function(date){
			return date.getFullYear() 
					+ (date.getMonth() < 9 ? '0' + (date.getMonth()+1) : ''+ (date.getMonth()+1) ) 
					+ (date.getDate() <= 9 ? '0' + date.getDate() : ''+ date.getDate() ) ;
		},
		addValidation : function(){
			if(!$KMSSValidation){
				return;
			}
			var self = this,
				validation = $KMSSValidation(),
				$endType = $('.endType',this.$element);
			//校验次数
			var validationName4Count = this.getValidationName();	
			validation.addValidator(validationName4Count,'发生次数不能为空且必须为正整数',function(v, e, o){
				var countEndType = $endType.filter(function(index, endTypeItem){
					return $(endTypeItem).val() == 'count';
				});
				if(self.$element.filter(':hidden').length || !countEndType.prop('checked') ){
					return true;
				}
				return $('.endTypeCount',this.$element).val();
			});
			validation.addElements($('.endTypeCount',this.$element)[0],validationName4Count);
			
			//校验直到
			validationName2Until = this.getValidationName();
			validation.addValidator(validationName2Until,'直到时间不能为空',function(v, e, o){
				var utilEndType = $endType.filter(function(index, endTypeItem){
					return $(endTypeItem).val() == 'until';
				});
				if(self.$element.filter(':hidden').length || !utilEndType.prop('checked') ){
					return true;
				}
				return $('.endTypeSelectDate',this.$element).val();
			});
			validation.addElements($('.endTypeSelectDate',this.$element)[0],validationName2Until);
						
			// 校验时间不能小于结束时间
			if(this.owner && this.owner.config && this.owner.config.finishDate) {
				var finishDate = this.owner.config.finishDate;
				validationName2Finish = this.getValidationName();
				validation.addValidator(validationName2Finish,'直到时间不能小于结束时间',function(v, e, o){
					var utilEndType = $endType.filter(function(index, endTypeItem){
						return $(endTypeItem).val() == 'until';
					});
					if(self.$element.filter(':hidden').length || !utilEndType.prop('checked') ){
						return true;
					}
					// 获取结束时间
					var _finishDate = $("input[name=" + finishDate + "]").val();
					// 判断时间是否合法
					var start = env.fn.parseDate(_finishDate, Com_Parameter.Date_format);
					var end = env.fn.parseDate(v, Com_Parameter.Date_format);
					return start.getTime() < end.getTime();
				});
				validation.addElements($('.endTypeSelectDate',this.$element)[0],validationName2Finish);
			}
		},
		reset : function(){
			var $endType = $('.endType',this.$element);
			var $endTypeCount = $('.endTypeCount',this.$element);
			var $untilNode = $('.endTypeSelectDate',this.$element);
			$endType.prop('checked',false);
			$endType.eq(0).prop('checked',true);
			$endTypeCount.val('');
			$untilNode.val('');
			if($KMSSValidation){
				var validation = $KMSSValidation();
				setTimeout(function(){
					validation.validateElement($endTypeCount[0]);
					validation.validateElement($untilNode[0]);
				},1);
			}
		}
	});
	
	//添加第三方策略
	var addStrategy = function(name, strategy){
		strategies[name] = strategy;
	};
	
	exports.BaseStrategy = BaseStrategy;
	exports.strategies = strategies;
	exports.addStrategy = addStrategy;
});
