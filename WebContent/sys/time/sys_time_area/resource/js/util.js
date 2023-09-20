define(function(require, exports, module) {
	
	var $ = require('lui/jquery');
	
	// 日期类型权重顺序
	var TYPE_WEIGHT = [
	  1, // 工作日
	  4, // 法定节日
	  2, // 假期
	  5, // 法定节日补班
	  3 // 补班
	];

	function resolveData(data, checkNull, checkWeight, checkSame) {
		
		var _symbol = 0;
		
		if(!data || data.length < 1) {
			return [];
		}
		
		var min = null;
		var max = null;
		
		// 取时间范围
		$.each(data, function(_, d) {
			var startDate = d.startDate;
			var endDate = d.endDate;
			if(min == null || startDate < min) {
				min = startDate;
			}
			if(max == null || endDate > max) {
				max = endDate;
			}
		});
		
		// 生成数据缓存对象（日刻度）
		var tempData = {};
		var i = min.getTime();
		var j = max.getTime();
		for(i; i <= j; i += 86400000) {
			var t = new Date(i);
			var year = t.getFullYear();
			var month = t.getMonth() + 1;
			var date = t.getDate();
			if(!tempData[year]) {
				tempData[year] = {};
			}
			if(!tempData[year][month]) {
				tempData[year][month] = {};
			}
			if(!tempData[year][month][date]) {
				tempData[year][month][date] = null;
			}
		}

		// 合并数据
		$.each(data, function(_, d) {
			var startDate = d.startDate.getTime();
			var endDate = d.endDate.getTime();
			
			for(startDate; startDate <= endDate; startDate += 86400000) {
				
				var t = new Date(startDate);
				var year = t.getFullYear();
				var month = t.getMonth() + 1;
				var date = t.getDate();
				
				try {
					
					if(checkWeight) {
						if($.inArray(d.type, TYPE_WEIGHT) >= $.inArray(tempData[year][month][date].type || 0, TYPE_WEIGHT)) {
							tempData[year][month][date] = $.extend(true, {}, d, {
								fdId: ('item-' + _symbol++),
								startDate: new Date(year, month - 1, date),
								endDate: new Date(year, month - 1, date)
							});
						}
					} else {
						
						//相同数据置为null
						if(checkSame) {
								
							var _d = tempData[year][month][date];
							
							var flag = false;
							if(_d) {
								if(d.type == _d.type) {
									
									switch(d.type) {
										case 1:
										case 3:
											if(d.clazz.fdId == _d.clazz.fdId) {
												flag = true;
											}
										case 2:
											if(d.name == _d.name) {
												flag = true;
											}
										default: break;
									}
									
								}
							}
							
							if(flag) {
								tempData[year][month][date] = null;
							} else {
								tempData[year][month][date] = $.extend(true, {}, d, {
									fdId: ('item-' + _symbol++),
									startDate: new Date(year, month - 1, date),
									endDate: new Date(year, month - 1, date)
								});
							}
							
						} else {
							tempData[year][month][date] = $.extend(true, {}, d, {
								fdId: ('item-' + _symbol++),
								startDate: new Date(year, month - 1, date),
								endDate: new Date(year, month - 1, date)
							});
						}
						
					}
					
				} catch(e) {
					tempData[year][month][date] = $.extend(true, {}, d, {
						fdId: ('item-' + _symbol++),
						startDate: new Date(year, month - 1, date),
						endDate: new Date(year, month - 1, date)
					});
				}
			}
			
		});
		var resData = [];
		for(var year in tempData) {
			for(var month in tempData[year]) {
				for(var date in tempData[year][month]) {
					if(checkNull) {
						if(tempData[year][month][date]) {
							resData.push(tempData[year][month][date]);
						}
					} else {
						resData.push(tempData[year][month][date]);
					}
				}
			}
		}
		
		$.each(resData || [], function(_, d) {
			
			//工作日判断是否在所选星期之外
			if(d.type == 1) {

				var day = d.startDate.getDay();
				if(day < d.fromWeek - 1 || day > d.toWeek - 1) {
					d.invalid = true; //数据无效标记
				}
				
			}
			
		});
		
		return resData;
	}
	
	function resolveDataInMonth(data, checkNull, checkWeight, checkSame) {
		
		if(!data || data.length < 1) {
			return [];
		}
		
		var t = {};
		$.each(data || [], function(_, d) {
			var _d = t[d.date.getTime() + (d.elementId || '')];
			
			if(checkSame && _d) {
				if(_d.type == d.type) {
					
					var flag = false;
					
					switch(_d.type) {
						case 1: 
						case 3: 
							if(d.clazz.fdId == _d.clazz.fdId) {
								flag = true;
							}
							break;
						case 2: 
							if(d.name == _d.name) {
								
								flag = true;
							}
							break;
						default: break;
					}
					
					if(flag) {
						t[d.date.getTime() + (d.elementId || '')] = null;
					} else {
						t[d.date.getTime() + (d.elementId || '')] = d;
					}
				} else {
					if(checkWeight && $.inArray(d.type, TYPE_WEIGHT) >= $.inArray(_d.type, TYPE_WEIGHT)) {
						t[d.date.getTime() + (d.elementId || '')] = d;
					} else {
						t[d.date.getTime() + (d.elementId || '')] = d;
					}
				}
				
			} else {
				t[d.date.getTime() + (d.elementId || '')] = d;
			}
		});
		
		var res = [];
		for(var k in t) {
			if(checkNull) {
				
				var _t = t[k];
				if(!_t) {
					continue;
				}
				
				if(_t.type == 1 && _t.fromWeek && _t.toWeek) {
					var _day = _t.date.getDay() + 1;
					if(_day >= _t.fromWeek && _day <= _t.toWeek) {
						res.push(_t);
					}
				} else {
					res.push(_t);
				}
			} else {
				res.push(t[k]);
			}
		}
		return res;
	}
	
	exports.resolveData = resolveData;
	exports.resolveDataInMonth = resolveDataInMonth;
	
});