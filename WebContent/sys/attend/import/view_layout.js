seajs.use(['lui/jquery','lui/topic','lui/qrcode','lang!sys-attend'],function($,topic,QRCode,Msg){
	
	/**
	 * 状态说明:
	 * 	0 : 未开始
	 *  1 : 进行中
	 *  2 : 已结束
	 */
	var urlcategory = env.fn.formatUrl('/sys/attend/sys_attend_category/sysAttendCategory.do?method=viewdata&appId=') + window.__fdModelId4sysAttend__;
	var urlstat = env.fn.formatUrl('/sys/attend/sys_attend_category/sysAttendCategory.do?method=stat&appId=') + window.__fdModelId4sysAttend__;
	var promise1 = $.ajax({
			url : urlcategory
		}),
		promise2 = $.ajax({
			url : urlstat
		});
	window.genQRCode = function(QRCode,url,$element,status){
		var t = new Date().getTime();
		$.ajax({
			url:env.fn.formatUrl('/sys/attend/sys_attend_main/sysAttendMain.do?method=getDbTimeMillis&forward=lui-source'),
			type:"POST",
			dataType:"json",
			async:false,
			success:function(data){
				if (data.nowTime) {
					t = data.nowTime;
				}
			}
		});
		var scanUrl = url+"&t=" + t;
		var qrCodeUpdateTime = parseInt(Com_GetUrlParameter(url, 'qrCodeTime')) * 1000;
		$element.empty();
		var canvas= document.createElement('canvas');
		if(typeof canvas.getContext === "function") {
			QRCode.Qrcode({
				text : scanUrl,
				element : $element[0],
				render : 'canvas'
			});			
		}else{
			$($element[0]).html("<div style='display: inline-block;text-align: left;margin-left: 10px;margin-right: 10px;'>"+
					Msg['sysAttendCategory.Qrcode.topic']+"</div>");
		}
		if(status=='1'){
			window.setTimeout(function(){
				genQRCode(QRCode,url,$element,status);
	 		},qrCodeUpdateTime || 60000);
		}
	};
	
	function _fixType(type) {
			var r = type.match(/png|jpeg|bmp|gif/)[0];
			return 'image/' + r;
	};
	window.downloadQRCode = function(){
		var type = "png", 
			name = Msg['sysAttendCategory.importView.signQrcode'] + '.' + type,
			canvas = $('.lui_attendStat_body canvas');
		if (window.navigator.msSaveBlob) {
			window.navigator.msSaveBlob(canvas[0].msToBlob(), name);
		} else {
			var imageData = canvas[0].toDataURL(type);
			imageData = imageData.replace(_fixType(type),
					'image/octet-stream');
			var save_link = document.createElementNS(
					"http://www.w3.org/1999/xhtml", "a");
			save_link.href = imageData;
			save_link.download = name;
			var ev = document.createEvent("MouseEvents");
			ev.initMouseEvent("click", true, false, window, 0, 0, 0, 0, 0,
					false, false, false, false, 0, null);
			save_link.dispatchEvent(ev);
			ev = null;
			delete save_link;
		}
	};

	$.when(promise1,promise2).done(function(categories,stats){
		categories = categories[0];
		stats = stats[0];
		
		topic.publish('sys.attend.category.load.complete',categories);
		
		if(categories.length==0)
			return;
		for(var k = 0 ; k < categories.length;k++){
			category = categories[k];
			stat = stats[k];
			var attendContainer = $('<div/>').addClass('lui_attend_container'),
			timeContainer = $('<div/>').addClass('lui_attendTime_container').appendTo(attendContainer),
			bottomContainer = $('<div/>').addClass('lui_attendBottom_container').appendTo(attendContainer);
		
			//时间
			var startTimeContainer = $('<div/>').addClass('lui_startTime_container lui_attendTime_item').appendTo(timeContainer),
				dateContainer = $('<div/>').addClass('lui_date_container lui_attendTime_item').appendTo(timeContainer),
				endTimeContainer = $('<div/>').addClass('lui_endTime_container lui_attendTime_item').appendTo(timeContainer);
				
			startTimeContainer.append($('<span/>').addClass('time').html(category.fdStartTime));
			startTimeContainer.append($('<span/>').addClass('txt').html(Msg['sysAttendCategory.importView.fdStartTime']));
			endTimeContainer.append($('<span/>').addClass('time').html(category.fdEndTime));
			endTimeContainer.append($('<span/>').addClass('txt').html(Msg['sysAttendCategory.importView.fdEndTime']));
			if(category.fdTimes && category.fdTimes[0] && category.fdTimes[0].indexOf("00:00") == -1){
				dateContainer.html(category.fdTimes[0]+" "+ Msg['sysAttendCategoryRule.lateTime.text']);
			}
			//签到状态
			var status = category.fdStatus,
				sobj = { '0' : 'unStart' , '1' : 'doing' , '2' : 'finish' },
				now = new Date().format(Data_GetResourceString("date.format.datetime")),
				nowDate = now.split(' ')[0],
				nowTime = now.split(' ')[1];
			
			if(compareDate(category.fdTimes[0], nowDate , category.fdStartTime , nowTime) > 0 && status != '2' ){//未开始
				status = '0';
			}else if(compareDate(nowDate,category.fdTimes[0],nowTime,category.fdEndTime) > 0 || status == '2'){//已结束
				status = '2';
			}
			var statusTxt = status=='0' ? Msg['sysAttend.status.nostart']:(status=='2' ? Msg['sysAttend.status.end']:Msg['sysAttend.status.holding']);
			var statusId = "#sysAttendView_title_"+ window.__fdModelId4sysAttend__ + "_" + category.fdId + " .lui_attendCategoryStatus";
			$(statusId).addClass(sobj[status]);
			$(statusId).html(statusTxt);

			attendContainer.addClass(sobj[status]);
			//统计
			var statContainer = $('<div/>').addClass('lui_attendBottom_item').appendTo(bottomContainer);
			var statFooter = $('<h2/>').addClass('lui_attendBottom_item_h').html(Msg['sysAttendCategory.importView.shouldSign'].replace('%count%', stat.count)).appendTo(statContainer);
			statContainer.append($('<i/>').addClass('lui_attendBottom_item_tag').text(statusTxt));
			var statBody = $('<div/>').addClass('lui_attendStat_body').appendTo(statContainer),
				statList = $('<ul>').addClass('lui_attendStat_list').appendTo(statBody);
			var doContent = $('<div/>').addClass('content').appendTo($('<li/>').appendTo(statList));
			doContent.append($('<span/>').addClass('num').html(stat.attendcount));
			doContent.append($('<span/>').addClass('txt').html(Msg['sysAttendCategory.importView.signed']));
			var undoContent = $('<div/>').addClass('content').appendTo($('<li/>').addClass('unattend').appendTo(statList));
			undoContent.append($('<span/>').addClass('num').html(stat.unattendcount));
			undoContent.append($('<span/>').addClass('txt').html(Msg['sysAttendCategory.importView.missed']));
			var signedInfo = '&nbsp;&nbsp;';
			if(category.fdInTime>0){
				signedInfo = '<span>'+ Msg['sysAttendCategory.importView.normal.count'].replace('%count%', stat.normalcount) 
							+ '</span><span>'+ Msg['sysAttendCategory.importView.late.count'].replace('%count%', stat.latecount) + '</span>';
			}
			var statFooter = $('<div/>').addClass('lui_attendStat_footer').html(signedInfo).appendTo(statContainer);
			
			//二维码
			var qrcodeContainer = $('<div/>').addClass('lui_attendBottom_item').appendTo(bottomContainer);
			qrcodeContainer.append($('<h2/>').addClass('lui_attendBottom_item_h').html(Msg['sysAttendCategory.importView.signQrcode']));
			var qrcodeBody = $('<div/>').addClass('lui_attendStat_body').appendTo(qrcodeContainer);
			
			genQRCode(QRCode,category.fdQRCodeUrl,qrcodeBody,status);
			
			$('<div/>').addClass('lui_attendStat_download').html('<span class="lui_attendStat_download_btn"><a href="javascript:void(0)" onclick="window.downloadQRCode();">' + Msg['sysAttendCategory.importView.download'] + '</a></span>').appendTo(qrcodeContainer);
			var qrcodeFooter = $('<div/>').addClass('lui_attendStat_footer').html('&nbsp;&nbsp;').appendTo(qrcodeContainer);
			
			var elementId = '#sysAttendView_' + window.__fdModelId4sysAttend__ + "_" + category.fdId;
				element = $(elementId);
			layout.dom = attendContainer.prependTo(element);
			done();
			
		 }
	});
});