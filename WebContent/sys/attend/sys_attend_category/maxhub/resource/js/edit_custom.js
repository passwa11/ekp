require(['dojo/_base/xhr', 'dojo/domReady', 'dojo/dom', 'dojo/html', 'dojo/date', 'dojo/date/locale'], 
		function(xhr, ready, dom, html, date, locale){
	
	ready(function() {
		
		window.submitForm = function(onSuccess, onFail) {
			
			xhr.post({
				url: document.sysAttendCategoryForm.action + '?method=saveadd',
				form: document.sysAttendCategoryForm,
				load: onSuccess || function(res) {
					console.log(res);
				},
				error: onFail || function(err){
					console.error(err);
				}
			});
			
		}
		
		// 初始化签到信息
		var sysAttendLateTimeNode = dom.byId('sysAttendLateTime');

		var inTime = new Date('2018/01/01 ' + __sysAttendInTime__);
		var lateTime = date.add(inTime, 'minute', parseInt(__sysAttendLateTime__));
		
		html.set(sysAttendLateTimeNode, locale.format(lateTime, {
			selector: 'time',
			timePattern: 'HH:mm'
		}));
	});
	
});