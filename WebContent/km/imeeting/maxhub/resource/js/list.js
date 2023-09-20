require(['dojo/dom', 'dojo/dom-construct', 'dojo/dom-style', 'dojo/dom-class', 'dojo/html', 'dojo/on', 'dijit/registry',
         'mhui/device/jssdk', 'mhui/dialog/Dialog', 'dojo/date', 'dojo/date/locale', 'mui/util', 'mui/dialog/Tip', 'dojo/request',
         'dojo/ready','mhui/domain', 'dojo/topic', 'mhui/api'], 
		function(dom, domCtr, domStyle, domClass, html, on, registry, jssdk, Dialog, date, locale, util, Tip, request, ready, 
				domain, topic, api){

	window.__placeId__ = '';
	window.__placeName__ = '';
	
	// 会议预约转面对面建会议
	topic.subscribe('EVENTS_BOOKMEETING_CLICK', function(bookId) {
		createIMeeting(bookId);
	});
	
	// 面对面建会议
	window.createIMeeting = function(bookId) {
		var now = new Date();
		var format = {
				selector: 'datetime',
				datePattern: 'yyyy-MM-dd',
				timePattern: 'HH:mm'
		};
		
		api.checkPlaceFree(
			locale.format(now, format), 
    		locale.format(date.add(now, 'minute', 1), format),
    		window.__placeId__
		).then(function(res) {
			
			if(!bookId && !res) {
				Tip.fail({
        			text : '当前时间会议室已被占用！'
        		});
				return;
			}
			
			var _iframeUrl = 'edit.jsp?placeId=!{placeId}&meetingName=!{meetingName}&bookId=!{bookId}';
			
			var iframeUrl = util.urlResolver(_iframeUrl, {
				placeId: window.__placeId__,
				meetingName: __placeName__ + '临时会议',
				bookId: bookId || ''
			});
			
			var dialog = Dialog.show({
				title: bookId ? '生成会议' : '面对面建会议',
						iframe: iframeUrl,
						showClose: false,
						width: '73.75rem',
						iframeHeight: '48.75rem'
			});
			
			
			domain.subscribe('EVENTS_CANCLE',function(){
				dialog.hide();
			});
			
			domain.subscribe('EVENTS_SUBMIT',function(id){
				
				Tip.success({
					text : '会议创建成功！'
				});
				
				initMeeting(window.__placeId__, true);
				dialog.hide();
				location.href=dojoConfig.baseUrl + 'km/imeeting/km_imeeting_main/kmImeetingMain.do?method=view&fdId='+id;
			});
			
		});
	
	}
	
	// 返回
	window.goBack = function() {
		console.log('goBack');
		jssdk.goBack();
	}
	
	// 退出登陆
	window.goExit = function() {
		
		var tips = domCtr.create('div', {
			innerHTML: '是否确认登出？',
			style: 'text-align: center; padding: 6rem 12rem;'
		});
		
		Dialog.show({
			content: tips,
			title: '提示',
			showClose: false,
			buttons: [
				{
					text: '取消',
					onClick: function(dialog) {
						dialog.hide();
					}
				},
	            {
	            	text: '确定',
	            	className: 'mhuiDialogPrimaryBtn',
	            	onClick: function(dialog) {
	            		jssdk.logout();
	            		dialog.hide();
	            	}
            	}
          ]
		});
	}
	
	// 打开会议地图
	window.goMap = function() {
		location.href = 'map.jsp';
	}
	
	// 进入切换会议室页面
	window.goSetting = function() {
		jssdk.setMaxhubConfig(true);
	}
	
	// 进入设置页面
	window.goServerSetting = function(){
		jssdk.setServerConfig();
	}
	
	
	// 加载会议室会议列表
	
	var meetintTempData = [];
	
	function loadMeeting(placeId, force) {
		
		var imeetingList = registry.byId('imeetingList');
		
		var now = new Date();
		var localeFormat = {
			selector: 'date',
			datePattern: 'yyyy-MM-dd'
		};
		
		api.getMeetingList(locale.format(now, localeFormat), locale.format(date.add(now, 'day', 1), localeFormat), placeId)
			.then(function(res){
				var data = null;
				if((res || []).length < 1) {
					data = [{
						isEmpty: true
					}];
				} else {
					data = res;
				}
				
				// 校验数据是否需要重新渲染
				if(force || data.length != meetintTempData.length || 
					(meetintTempData[0] || {}).fdId != (data[0] || {}).fdId) {
					meetintTempData = data;
					imeetingList.renderList(data, true);
				}
				
			}, function(err) {
				console.error(err);
			});
				
	}
	
	// 初始化会议列表
	function initMeeting(placeId, force) {
		
		var imeetingList = registry.byId('imeetingList');
		var placeNameNode = dom.byId('placeName');
		
		api.checkPlaceStatus(placeId)
			.then(function(res) {
				
				if((res || []).length < 1) {
					window.goSetting();
					return;
				}
				
				html.set(placeNameNode, res[0].placeName);
				
				// 允许面对面建会议
				__placeId__ = placeId;
				__placeName__ = res[0].placeName;
				
				loadMeeting(placeId, force);
				
			}, function(err) {
				console.error(err);
			});
	}
	
	// 初始化逻辑
	function readyFunc(args) {
		
		/**********************
		 * 初始化日期时间
		 **********************/ 
		var currentTime = dom.byId('currentTime');
		function setCurrentTime() {
			html.set(currentTime, locale.format(new Date(), {
				formatLength: 'medium'
			}));
		}
		setCurrentTime();
		setInterval(function() {
			setCurrentTime();
		}, 1000);
		
		/**********************
		 * 初始化会议列表
		 **********************/ 
		initMeeting(args.placeId);
		
		// 定时刷新会议列表
		setInterval(function() {
			
			if(window.__placeId__) {
				initMeeting(window.__placeId__);
			}
			
		}, 4000);
	}
	
	// 初始化
	ready(function(){
		//readyFunc({placeId: '164ef90a3acc2cc2d07810f40aa977d2'});
		jssdk.ready(readyFunc);
		
		// 监听会议室更改
		jssdk.handleMeetingChange(function(data){
			initMeeting(data.placeId)
		});
		
	});
	
});