require(['dojo/dom', 'dojo/dom-construct', 'dojo/dom-style', 'dojo/dom-class', 'dojo/dom-attr', 'dojo/html', 'dojo/on', 
         'dijit/registry', 'mhui/device/jssdk', 'mhui/dialog/Dialog', 'mhui/util', 'mui/dialog/Tip', 'dojo/request',
         'dojo/ready','mhui/domain', 'dojo/topic', 'mhui/api', 'dojo/_base/lang', 'dojo/date/locale', 'dojo/date'], 
		function(dom, domCtr, domStyle, domClass, domAttr, html, on, registry, jssdk, Dialog, util, Tip, request, ready, 
				domain, topic, api, lang, locale, date){
	
	// 进入切换会议室页面
	window.goBack = function() {
		jssdk.goBack();
	}

	// 搜索会议
	window.searchMeeting = function(value) {
		
	}
	
	// 重置筛选项
	window.resetMeetingFilter = function(pos) {
		var meetingFilter = null;
		if(pos == 'left') {
			meetingFilter = registry.byId('meetingLeftFilter');
		} else {
			meetingFilter = registry.byId('meetingRightFilter');
		}
		meetingFilter.reset();
	}
	
	// 侧边栏展开收缩
	window.toggleSidebar = function(pos) {
		
		var leftSidebarNode = dom.byId('meetingMapLeftSideBar');
		var rightSidebarNode = dom.byId('meetingMapRightSideBar');
		
		var meetingMapListNode = dom.byId('meetingMapList');
		var _pos = domAttr.get(meetingMapListNode, 'data-sidebar');
		if(_pos == pos) {
			domAttr.remove(meetingMapListNode, 'data-sidebar');
		} else {
			domAttr.set(meetingMapListNode, 'data-sidebar', pos);
		}
		
		if(pos == 'left') {

			if(domClass.contains(leftSidebarNode, 'active')) {
				domClass.remove(leftSidebarNode, 'active');
			} else {
				domClass.add(leftSidebarNode, 'active');
			}
			
			if(domClass.contains(rightSidebarNode, 'active')) {
				domClass.remove(rightSidebarNode, 'active');
			}
			
		} else {
			
			if(domClass.contains(rightSidebarNode, 'active')) {
				domClass.remove(rightSidebarNode, 'active');
			} else {
				domClass.add(rightSidebarNode, 'active');
			}
			
			if(domClass.contains(leftSidebarNode, 'active')) {
				domClass.remove(leftSidebarNode, 'active');
			}
		}
		
		
	}
	
	// 初始化逻辑
	function readyFunc(args) {
		
		// 监听筛选器
		var meetingMapList = registry.byId('meetingMapList');
		topic.subscribe('EVENT_FORM_VALUECHANGE', function(payload) {
			
			topic.publish('EVENT_FORM_CHANGEVALUE', payload);
			
			var name = payload.name;
			var value = payload.value;
			
			if(name == 'fdHoldDate') {
				var format = {
						selector: 'date',
						datePattern: 'yyyy-MM-dd'
				}
				var formatString = 'q.fdHoldDate={from}&q.fdHoldDate={to}';
				switch(value) {
					case '1': 
						var today = locale.format(util.today(), format);
						value = lang.replace(formatString, {
							from: today,
							to: today
						});
						break;
					case'2': 
						var thisWeek = util.thisWeek();
						value = lang.replace(formatString, {
							from: locale.format(thisWeek.from, format),
							to: locale.format(thisWeek.to, format)
						});
						
						break;
					case '3': 
						var thisMonth = util.thisMonth();
						value = lang.replace(formatString, {
							from: locale.format(thisMonth.from, format),
							to: locale.format(thisMonth.to, format)
						});
						break;
					case '4': 
						var thisQuarter = util.thisQuarter();
						value = lang.replace(formatString, {
							from: locale.format(thisQuarter.from, format),
							to: locale.format(thisQuarter.to, format)
						});
						break;
					case '5': 
						var y = util.today().getFullYear();
						value = lang.replace(formatString, {
							from: y + '-01-01',
							to: y + '-12-31'
						});
						break;
					case '6': 
						var today = util.today();
						var y = today.getFullYear();
						var m = today.getMonth();
						var lastMonthStart = new Date(y + '-' + util.zeroPad(m, 2) + '-01');
						var lastMonthEnd = date.add(lastMonthStart, 'day', date.getDaysInMonth(lastMonthStart) - 1);
						
						value = lang.replace(formatString, {
							from: locale.format(lastMonthStart, format),
							to: locale.format(lastMonthEnd, format)
						});
						
						break;
					case '7': 
						var today = util.today();
						var y = today.getFullYear() - 1;

						value = lang.replace(formatString, {
							from: y + '-01-01',
							to: y + '-12-31'
						});
						
						break;
					default: 
						value = '';
						break;
				}
			}
			
			var query = {};
			query[name] = value;
			meetingMapList.reload(query);
			
		});
		
		
		

	}
	
	// 初始化
	ready(function(){
		readyFunc({});
		//jssdk.ready(readyFunc);
		
	});
	
});