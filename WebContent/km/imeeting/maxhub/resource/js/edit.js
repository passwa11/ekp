require(['dojo/_base/lang', 'dojo/dom', 'dojo/dom-construct', 'dojo/dom-attr', 'dojo/dom-class', 'dojo/html', 'dojo/on', 'dojo/topic', 'dijit/registry', 'dojo/_base/array',
         'mhui/device/jssdk', 'dojo/query', 'mui/qrcode/QRcode','dojo/ready','mui/dialog/Tip',"mui/util", 'dojo/_base/xhr', 'dojo/request',
         'mhui/domain', 'dojo/date/locale', 'dojo/date', 'dojo/promise/all', 'mhui/api'], 
		function(lang, dom, domCtr, domAttr, domClass, html, on, topic, registry, array, jssdk, query, qrcode, ready,
				Tip, util, xhr, req, domain, locale, date, promiseAll, api){

	window.validate = function() {
		var newForm = registry.byId('newForm');
		if(newForm.validate()){
			return true;
		}
	}
		
	
	window.genQRCode = function(){
		var scanUrl = location.origin + dojoConfig.baseUrl + "km/imeeting/km_imeeting_main/kmImeetingMain.do?method=mhuAdd&key=mhuKmImeeting&fdId="+fdId+"&t=" + new Date().getTime();
		domCtr.empty("qrcode");
		
		var obj = qrcode.make({
			url : scanUrl,
			width: 500,
			height: 500
			
		});
		var qrcodeMain = query('#qrcode');
		domCtr.place(obj.domNode,qrcodeMain[0],'first'); 
	}
	
	window.submit=function(){
		var domList = document.getElementsByClassName("mhuiAddressItemName");
		if(domList.length>0){
			var domList = document.getElementsByClassName("mhuiAddressItemName");
			var res = [].slice.call(domList);
			var ids="";
			for(var item in res) {
				if(ids==""){
					ids = res[item].getAttribute('data-dojo-id');
				}else{
					ids += ";"+res[item].getAttribute('data-dojo-id');
				}
			}
			document.getElementById("attendIds").value=ids;
	
			// 会议预约转会议不校验时间
			if(bookId) {
				
	        	var processTip = Tip.tip({				
	        		icon: "mui mui-loading",
	        		time: -1, 
	        		cover: true,
	        		text: "会议创建中"
	        	});
	        	
	        	xhr.post({
	        		url: document.saveForm.action,
	        		form: document.saveForm,
	        		load: function(res) {
	        			processTip.hide();
	        			domain.publish('EVENTS_SUBMIT', fdId);
	        		},
	        		error: function(err){
	        			processTip.hide();
	        			domain.publish('EVENTS_SUBMIT_END', '会议创建失败！');
	        		}
	        	});
				
			} else {
				
				var durationWidget = registry.byId('duration');
				var now = new Date();
				var format = {
						selector: 'datetime',
						datePattern: 'yyyy-MM-dd',
						timePattern: 'HH:mm'
				};
				
		        api.checkPlaceFree(
		    		locale.format(now, format), 
		    		locale.format(date.add(now, 'minute', parseInt(parseFloat(durationWidget.value) * 60)), format),
		    		placeId
		        ).then(function(free){
		        	
		        	if(!free) {
		        		Tip.fail({
		        			text : '当前时间段会议室已被占用！'
		        		});
		        		return;
		        	}
		        	
		        	var processTip = Tip.tip({				
		        		icon: "mui mui-loading",
		        		time: -1, 
		        		cover: true,
		        		text: "会议创建中"
		        	});
		        	
		        	xhr.post({
		        		url: document.saveForm.action,
		        		form: document.saveForm,
		        		load: function(res) {
		        			processTip.hide();
		        			domain.publish('EVENTS_SUBMIT', fdId);
		        		},
		        		error: function(err){
		        			processTip.hide();
		        			domain.publish('EVENTS_SUBMIT_END', '会议创建失败！');
		        		}
		        	});
		        	
		        });
			}
		}else{
			Tip.fail({
				text : '参加人员不能为空！'
			});
		}
	}
	
	function changeStep(index) {
		
		var editBase = dom.byId('editBase');
		var editQrcode = dom.byId('editQrcode');
		
		if(index == 0) {
			domClass.add(editQrcode, 'mhui-hidden');
			domClass.remove(editBase, 'mhui-hidden');
		} else {
			domClass.add(editBase, 'mhui-hidden');
			domClass.remove(editQrcode, 'mhui-hidden');
		}
		
		topic.publish('EVENT_STEPNAV_CHANGE', {
			index: index,
			key: 'stepNav'
		});
		
		array.forEach(query('.mhuiDialogBtn') || [], function(btn) {
			
			var step = domAttr.get(btn, 'data-step');
			
			if(parseInt(step) == index) {
				domClass.remove(btn, 'mhui-hidden');
			} else {
				domClass.add(btn, 'mhui-hidden');
			}
			
		});
		
	}
	
	window.next=function(){

		if(validate()){
			
			if(bookId) {
				changeStep(1);
			} else {
				
				var durationWidget = registry.byId('duration');
				
				var now = new Date();
				var format = {
						selector: 'datetime',
						datePattern: 'yyyy-MM-dd',
						timePattern: 'HH:mm'
				};
				
				promiseAll([
		            api.checkPlaceFree(
	            		locale.format(now, format), 
	            		locale.format(date.add(now, 'minute', parseInt(parseFloat(durationWidget.value) * 60)), format),
	            		placeId
		            ).then(function(res){
		            	return res;
		            }),
		            api.checkPlaceStatus(
	            		placeId
		            ).then(function(res){
		            	return res[0]
		            })
	            ]).then(function(results){
	            	if((results || []).length < 2) {
	            		Tip.fail({
	            			text : '校验失败！'
	            		});
	            	} else if(!results[0]) {
	            		Tip.fail({
	            			text : '当前时间段会议室已被占用！'
	            		});
	            	} else if(results[1].fdUserTime && parseFloat(durationWidget.value) > results[1].fdUserTime) {
	            		Tip.fail({
	            			text : results[1].placeName + '当前会议室最大使用时长为' + results[1].fdUserTime + '小时，当前会议用时超过了该会议室最大使用时长！'
	            		});
	            	} else {
	            		changeStep(1);
	            	}

	            });
				
			}
			
		}else{
			Tip.fail({
				text : '请填写所有参数！'
			});
		}
		
	}
	
	window.prev=function(){
		changeStep(0);
	}
	
	window.dialogCancle=function(){
		domain.publish('EVENTS_CANCLE');
	}
	
	ready(function() {
		
		// 生成二维码
		genQRCode();
		
		// 获取扫码人数
		var attendNumsNode = dom.byId('attendNums');
		setInterval(function(){
			registry.byId("attenPersons").reload(function(l) {
				html.set(attendNumsNode, '总人数：' + l);
			});
		}, 3000);
		
		// 会议时间计算校验
		if(!bookId) {
			
			var duration = registry.byId("duration");
			
			var now = new Date();
			var localeFormat = {
				selector: 'date',
				datePattern: 'yyyy-MM-dd'
			};
			
			api.getMeetingList(locale.format(now, localeFormat), locale.format(date.add(now, 'day', 1), localeFormat), placeId)
				.then(function(res){
					res = res || [];
					
					var flag = false;
					var space = 0;
					
					var i = 0, l = res.length;
					for(i; i < l; i++) {
	
						var start = new Date(res[i].start);
						var end = new Date(res[i].end);
						
						if(now < start) {
							
							flag = true;
							space = date.difference(now, start, 'second');
							
							break;
						} else if(now >= end) {
							if(res[i+1]) {
								var _start = new Date(res[i+1].start);
								if(now < _start) {
									flag = true;
									space = date.difference(now, _start, 'second');
								}
							}
							
							break;
						}
						
					}
					
					if(flag && space > 0 && duration) {
						
						var options = [];
						var tt = space / 3600;
						
						if(tt < 0.5) {
							duration.value = '';
							Tip.fail({
								text: '距离下个会议不足0.5小时，禁止新建会议！'
							});
						} else {
							i = 0; l = (duration.options || []).length;
							for(i; i < l; i++) {
								var opt = duration.options[i];
								var t = parseFloat(opt.value) * 3600;
								if(t <= space) {
									options.push(opt);
								} 
							}
						}
						
						duration.renderOptions(options);
						
					} else {
						duration && duration.renderOptions(duration.options);
					}
					
				});
		}
	});
	
});