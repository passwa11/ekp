define(["dojo/_base/declare","dojo/date/locale","mui/i18n/i18n!sys-mobile","mui/util", "mui/calendar/termCalendar"],function(declare,locale,msg,util,termCalendar){
	var clz=declare("mui.calendar.CalendarUtil", null, {
		
		//日期转字符串
		formatDate:function(/*Date*/date,timePattern){
			var patterns=[dojoConfig.Date_format,dojoConfig.DateTime_format,dojoConfig.Time_format];
			if(timePattern){
				patterns.unshift(timePattern);
			}
			try{
				for(var i in patterns){
					var _pattern=patterns[i];
					var _date=locale.format(date,{selector : 'time',timePattern : _pattern});
					if(_date!=null){
						return _date;
					}
				}
			}catch(e){
				return null;
			}
			return null;
		},
		
		//字符串转日期
		parseDate:function(/*String*/date,timePattern){
			var patterns=[dojoConfig.Date_format,dojoConfig.DateTime_format,dojoConfig.Time_format];
			if(timePattern){
				patterns.unshift(timePattern);
			}
			try{
				for(var i in patterns){
					var _pattern=patterns[i];
					var _date=locale.parse(date,{selector : 'time',timePattern : _pattern});
					if(_date!=null){
						return _date;
					}
				}
			}catch(e){
				return null;
			}
			return null;
		},
		
		// 查询排班，return:1(休),2(班)
		checkHPDate : function (date) {

			var url = util.formatUrl('/sys/time/sys_time_area/sysTimeArea.do?method=getHPDay&s_ajax=true');
			var self = this;
			
			if(!this.hpData){
				require(['dojo/request'], function(request) {
					
					request.get(url, {
			            handleAs: "json",
			            sync: true
			        }).then(function(data){
			        	self.hpData = data;
			        }, function(err){
			        	self.hpData = [];
			        });
				});
			}

			var result = '';
			
			for(var i = 0; i < this.hpData.length; i++) {
				var hp = this.hpData[i];
				var _date = new Date(hp.date.replace(/\-/g, '/'));
				if(date.getFullYear() != _date.getFullYear() || 
						date.getMonth() != _date.getMonth() || 
						date.getDate() != _date.getDate()){
					continue;
				}
				
				if(hp.type == '1')
					result = '1';
				else
					result = '2';

				break;
			}
			return result;
		},
		
		/**
		 * 判断当前日期农历和假日
		 **/
		// 入口，有假期返回假期，没有假期返回农历
		checkLunarAndHoliday : function (refDate) {
			// 中文环境下显示农历
			if(!(dojoConfig['locale']!=null && (dojoConfig['locale']=='zh-cn' || dojoConfig['locale']=='zh-hk'))){
				return {value:''}
			}

			var LunarMonth = ['正月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '冬月', '腊月'];
			var LunarDate = ['日', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十'];
			var LunarOdate = ['初', '十', '廿', '卅', '　'];
			var solarFestivals = ["0101 元旦", "0214 情人节", "0308 妇女节", "0312 植树节", "0401 愚人节", "0501 劳动节", "0504 青年节", "0512 护士节", "0601 儿童节", "0701 建党节", "0801 建军节", "0910 教师节", "1001 国庆节", "1031 万圣节","1225 圣诞节"];
			var lunarFestivals = ["0101 春节", "0115 元宵节", "0505 端午节", "0707 七夕", "0715 中元节", "0815 中秋节", "0909 重阳节", "1208 腊八节", "1224 小年", "1230 除夕"];
			var weekFestivals=["0520 母亲节","0630 父亲节","1144 感恩节"];
			
			var SY = refDate.getFullYear();
			var SM = refDate.getMonth();
			var SD =  refDate.getDate();
			var SW=refDate.getDay();//星期几
			var SN=Math.ceil((SD+6-SW)/7);//第几周
			var cDay = function(m, d){
				var s = "";
				if(parseInt(d)==1){
					s = LunarMonth[parseInt(m)-1];
				}else{
					switch (parseInt(d)) {
						case 10: s += '初十'; break;
						case 20: s += '二十'; break;
						case 30: s += '三十'; break;
						default: s += LunarOdate[Math.floor(d / 10)]; s += LunarDate[Math.round(d % 10)];
					}
				}
				return (s);
			};
		    var lDObj = new this.Lunar(refDate);
		    var solarTerm = '', solarFestival = '', lunarFestival = '',sFtv = '',sFtvType = '', tmp1, tmp2;
		    // 先拿阳历节日，没有拿农历（情人节和春节重叠显示春节），拿月周节，拿节气
		    for (i in solarFestivals){										//阳历节日
	            if (solarFestivals[i].match(/^(\d{2})(\d{2})([\s\*])(.+)$/)) {
	                tmp1 = Number(RegExp.$1) - (SM + 1);
	                tmp2 = Number(RegExp.$2) - SD;
	                if (tmp1 == 0 && tmp2 == 0) 
	                	solarFestival = RegExp.$4;
	            }
	        }
		    
		    if(solarFestival == '' || solarFestival == '情人节') { // 2010年的春节和情人节重叠，显示春节
		    	if(!lDObj.isLeap){
		    		for (i in lunarFestivals){//农历节日
		    			if (lunarFestivals[i].match(/^(\d{2})(.{2})([\s\*])(.+)$/)) {
		    				
		    				var lm = parseInt(lDObj.month);
		    				var ld = parseInt(lDObj.day);
		    				
		    				//除夕一般是在春节前一天（腊月最后一天），可能是腊月三十或者二九，因此通过判断月份改变时当做三十处理
		    				if(lm == 12 && ld == 29) {
		    					var _refDate = new Date(refDate.getTime());
		    					_refDate.setDate(_refDate.getDate() + 1);
		    					var _lDObj = new this.Lunar(_refDate);
		    					if(_lDObj.month == 1) {
		    						ld = 30;
		    					}
		    				}
		    				
		    				tmp1 = Number(RegExp.$1) - lm;
		    				tmp2 = Number(RegExp.$2) - ld;
		    				if (tmp1 == 0 && tmp2 == 0 ) {
		    					lunarFestival = RegExp.$4;
		    				}
		    			}
		    		}
		    	}
		    }
		    
		    if(solarFestival == '' && lunarFestival == '') {
		    	for(i in weekFestivals){//月周节日
		    		if(weekFestivals[i].match(/^(\d{2})(\d)(\d)([\s\*])(.+)$/)){
		    			if(Number(RegExp.$1)==(SM+1)) {
		    				var firstWeek=new Date(SY,SM,1).getDay();
		    				tmp1=Number(RegExp.$2);
		    				tmp2=Number(RegExp.$3);  
		    				if(((firstWeek>tmp2)?7:0) + 7*(tmp1-1) + tmp2 -firstWeek+1==SD)
		    					solarFestival = RegExp.$5;
		    				//this[((this.firstWeek>tmp2)?7:0) + 7*(tmp1-1) + tmp2 - this.firstWeek].solarFestival += RegExp.$5 + ' '  
		    			}  
		    		}  
		    	}
		    }

		    if (solarFestival == '' && lunarFestival == '') {//阴历节气						
		    	var t = termCalendar.solar2lunar(SY, SM + 1, SD);
		    	if(t && t.Term){            		
		    		sFtv = t.Term;
		    		sFtvType = 'lunarFestival';
		    	}else{
		    		sFtv = '';
		    		sFtvType = '';
		    	}
		    }else {
		    	sFtv = solarFestival;
		    	sFtvType = 'solarFestival';
		    }
		    
		    if (lunarFestival != '' && (solarFestival != '国庆节')) { // 2020年的国庆和中秋会重叠，显示国庆节
		    	sFtv = lunarFestival;
		    	sFtvType = 'lunarFestival';
		    }

		    if(sFtv!=""){
				return {
					value: sFtv,
					type: sFtvType
				};
			}else{
				return {
					value: cDay(lDObj.month,lDObj.day)
				}
			}
		},
		
		Lunar : function(refDate) {
			
			var lunarInfo = [0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2, 0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6, 0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0];
			
			var _leapMonth = function (y) { 				
				return (lunarInfo[y - 1900] & 0xf);
			};
			var _monthDays = function monthDays(y, m) { 
				return ((lunarInfo[y - 1900] & (0x10000 >> m)) ? 30 : 29); 
			};
			var _leapDays = function(y) {					
			    if (_leapMonth(y)) 
			    	return ((lunarInfo[y - 1900] & 0x10000) ? 30 : 29);
			    else 
			    	return (0);
			};
			var _lYearDays = function(y) {
			    var i, sum = 348;
			    for (i = 0x8000; i > 0x8; i >>= 1){ 
			    	sum += (lunarInfo[y - 1900] & i) ? 1 : 0;
			    }
			    return (sum + _leapDays(y));
			};
			var i, leap = 0, temp = 0;
		    var baseDate = new Date(1900, 0, 31);
		    var offset = (refDate - baseDate.getTime())/86400000;
		    this.dayCyl = offset + 40;
		    this.monCyl = 14;
		    for (i = 1900; i < 2050 && offset > 0; i++) {
		        temp = _lYearDays(i);
		        offset -= temp;
		        this.monCyl += 12;
		    }
		    if (offset < 0) {
		        offset += temp;
		        i--;
		        this.monCyl -= 12;
		    }
		    this.year = i;
		    this.yearCyl = i - 1864;
		    leap =_leapMonth(i);
		    this.isLeap = false;
		    for (i = 1; i < 13 && offset > 0; i++) {
		        if (leap > 0 && i == (leap + 1) && this.isLeap == false){ 
		        	--i; this.isLeap = true; temp = _leapDays(this.year); 
		        }else{ 
		        	temp = _monthDays(this.year, i); 
		        }
		        if (this.isLeap == true && i == (leap + 1)) 
		        	this.isLeap = false;
		        offset -= temp;
		        if (this.isLeap == false) 
		        	this.monCyl++;
		    }
		    if (offset == 0 && leap > 0 && i == leap + 1){
		        if (this.isLeap){ 
		        	this.isLeap = false; 
		        }
		        else{
		        	this.isLeap = true; --i; --this.monCyl; 
		        }
		    }
		    if (offset < 0) { 
		    	offset += temp; --i; --this.monCyl; 
		    }
		    this.month = i;
		    this.day = offset + 1;
		}
		
	});
	
	return new clz();
	
});