require(['dojo/dom', 'dojo/dom-construct', 'dojo/dom-style', 'dojo/dom-class', 'dojo/html', 'dojo/on', 'dijit/registry',
         'mhui/device/jssdk', 'mhui/dialog/Dialog', 'dojo/date', 'dojo/date/locale', 'mui/util', 'mui/dialog/Tip', 'dojo/request',
         'dojo/ready','mhui/domain', 'dojo/topic', 'mhui/api'], 
		function(dom, domCtr, domStyle, domClass, html, on, registry, jssdk, Dialog, date, locale, util, Tip, request, ready, 
				domain, topic, api){

	window.__placeId__ = '';
	window.__placeName__ = '';
		
	// 会议列表
	window.goMettingList = function() {
		location.href=dojoConfig.baseUrl + 'km/imeeting/maxhub/list.jsp';
	}
	
	// 面对面建会议
	window.createMeeting = function(bookId) {
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
	
	// 初始化会议列表
	function initMeeting(placeId, force) {
		
		var placeNameNode = dom.byId('placeName');
		var btnNewMeeting = dom.byId('btnNewMeeting');
		
		api.checkPlaceStatus(placeId)
			.then(function(res) {
				
				if((res || []).length < 1) {
					btnNewMeeting && domClass.add(btnNewMeeting, 'mhui-hidden');
					window.goSetting();
					return;
				}
				
				html.set(placeNameNode, res[0].placeName);
				
				// 允许面对面建会议
				__placeId__ = placeId;
				__placeName__ = res[0].placeName;
				
				btnNewMeeting && domClass.remove(btnNewMeeting, 'mhui-hidden');
				
				
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