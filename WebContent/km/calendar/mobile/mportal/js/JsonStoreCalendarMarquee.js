define([
    "dojo/_base/declare",
    "dojo/request",
    "mui/util",
    'mui/calendar/CalendarUtil',
    "dijit/_WidgetBase"
	], function(declare, request, util, calendarUtil, WidgetBase) {
	
	return declare("sys.mportal.JsonStoreCardMarquee", [WidgetBase], {
		
		url: '', // 获取日程信息列表的URL地址
		
		scopeType : 'today', // 日程过滤时间范围 ( today:今天 、 week:本周 、less3:最近三天 、less7:最近一周 、more3:未来三天 、more7:未来一周 )
		
		start: '', // 日程过滤起始时间 
		
		end: '',   // 日程过滤结束时间
		
		startup : function() {
			var self = this;
			// 防止组件重复构建
			if(this._started){return;} 
			this.inherited(arguments);
			
			// 设置日程查询过滤的时间范围
			this._setFilterDateRange();

			// 发送请求获取日程信息数据列表
			var requestUrl = util.formatUrl(util.urlResolver(this.url, this));
			var promise = request.get(requestUrl, {
				headers : {
					'Accept' : 'application/json'
				},
				handleAs : 'json'
			});
			promise.response.then(function(result) {
				if (!result || !result.data) return;
				var dataList = result.data;
				self.generateMarquee(dataList);
			});
		},
		
		/**
		* 设置日程查询过滤的时间范围（起始时间【start】和结束时间【end】）
		* @return
		*/  		
		_setFilterDateRange : function(){
			var start = this._clearTime(new Date());
			var	end = new Date(start.getTime() + 24 * 3600 * 1000);
			if(this.scopeType && this.scopeType != 'today'){
				var now = this._clearTime(new Date());
				var nowTime = now.getTime();
				switch(this.scopeType){
				    case 'week':  // 本周
				        var weekFirstDay = new Date( now - (now.getDay()-1) * 86400000);    // 本周第一天 
				        var weekLastDay = new Date( (weekFirstDay/1000 + 6*86400) * 1000);  // 本周最后一天 
				    	start = weekFirstDay;
				    	end = new Date(weekLastDay.getTime() + 24 * 3600 * 1000);
                        break;   
					case 'less3': // 最近三天
						start = new Date(nowTime - 24 *  3600 * 1000 * 2 );
						break;
					case 'less7': // 最近一周 
						start = new Date(nowTime - 24 *  3600 * 1000 * 6 );
						break;
					case 'more3': // 未来三天
						end =  new Date(nowTime + 24 *  3600 * 1000 * 3 );
						break;
					case 'more7': // 未来一周
						end = new Date(nowTime + 24 *  3600 * 1000 * 7);
						break;
					default :
						break;
				}
			}
			this.start = calendarUtil.formatDate(this._clearTime(start));
			this.end = calendarUtil.formatDate(this._clearTime(end));
		},
	
		_clearTime : function(date){
			date.setHours(0);
			date.setMinutes(0);
			date.setSeconds(0);
			return date;
		}
		
	});
});