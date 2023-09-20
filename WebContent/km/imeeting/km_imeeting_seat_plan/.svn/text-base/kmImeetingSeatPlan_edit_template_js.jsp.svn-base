<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script>
	
	seajs.use([ 'lui/topic', 'lui/dialog','km/imeeting/resource/js/seatPlan', 
	            'km/imeeting/resource/js/seatutil',
	            'km/imeeting/resource/js/tempSeatlist',
	            'km/imeeting/resource/js/personlist',
	            'km/imeeting/resource/js/topiclist'],
			function (topic, dialog,SeatPlan,SeatUtil,TempSeatList,PersonList,TopicList) {	
		
		//////////////////////////////////////////////////////////////////
		// 全局变量
		//////////////////////////////////////////////////////////////////
		
		var fdMainId = "${kmImeetingSeatPlanForm.fdImeetingMainId}";//会议ID
		
		var seatData = [];//用于保存所有排位数据
		
		var planSeatCount = 0;//已安排座位数
		
		var cols = parseInt("${kmImeetingSeatPlanForm.fdCols}");
		var rows = parseInt("${kmImeetingSeatPlanForm.fdRows}");
		
		var templateData = '${kmImeetingSeatPlanForm.fdTemplateSeatDetail}';//初始化坐席模版设置的座位信息
		if(templateData == ''){
			templateData = [];
		}else{
			templateData = JSON.parse(templateData);
		}
		
		var publicData = [];//用于保存共有人员信息
		
		var tempSeatData = [];//用于保存临时座位
		
		var tmpData = [];// 用于保存当前排位信息
		
		var allPersonData = [];//所有人员信息
		
		var hostPersonData = [];//主持人信息
		
		var attendPersonData = [];// 参加人员信息
		
		var summaryPersonData = [];// 纪要人员信息
		
		var participantPersonData = [];//列席人员信息
		
		var reportersData = [];//上会材料汇报人信息
		
		var reporterData = [];//议题汇报人信息
		
		var attendUnitData = [];//议题列席人员信息
		
		var listenUnitData = [];//其它人员信息
		
		var planElement = new Array();//已安排人员数组
		
		var showPlanFlag = false;//仅显示未排位人员
		
		var showAttendFlag = false;//仅显示回执且参加人员
	   
		//////////////////////////////////////////////////////////////////
		// 动态调整容器高度
		//////////////////////////////////////////////////////////////////
		setTimeout(function() {
			seat && seat.setOptions({
				maxHeight: (window.screen.height) + 'px',
				maxWidth: (window.screen.width-300) + 'px'
			});
		}, 500);
		//////////////////////////////////////////////////////////////////
		// 主逻辑
		//////////////////////////////////////////////////////////////////
		
		//当前选中议题
		var activeTopic = null;
		
		//当前选中元素
		var activeClass = null;
		// 初始化临时座席元素
	    var templateSeatData = [{type:"0",style:"status_temporary",name:'临时座位',nodeType:"1"}];
	    var tempSeatList = new TempSeatList($('#tempSeatTab'), [], {
	    	mode: 'edit',
	    	onSelect: function(clazz) {
			    activeClass = clazz;
			}
	    });
	    tempSeatList.setData(templateSeatData); 
	    
	 	// 初始化主持人
	    var HostList = new PersonList($('#hostTab'), [], {
			onSelect: function(clazz) {
			    activeClass = clazz;
			}
	    });
	    
	 	// 初始化参加人员
	    var participantList = new PersonList($('#participantTab'), [], {
			onSelect: function(clazz) {
			    activeClass = clazz;
			}
	    });
	 	
	 	// 初始化纪要人员
	    var summaryList = new PersonList($('#summaryTab'), [], {
			onSelect: function(clazz) {
			    activeClass = clazz;
			}
	    });
	 	
	 	// 初始化列席人员
	    var AttendList = new PersonList($('#attendTab'), [], {
			onSelect: function(clazz) {
			    activeClass = clazz;
			}
	    });
	 	
	 	// 初始化上会材料汇报人员
	    var reportersList = new PersonList($('#reportersTab'), [], {
			onSelect: function(clazz) {
			    activeClass = clazz;
			}
	    });
	 	
	 	// 初始化议题汇报人员
	    var reporterList = new PersonList($('#reporterTab'), [], {
			onSelect: function(clazz) {
			    activeClass = clazz;
			}
	    });
	 	
	 	// 初始化议题列席人员
	    var attendUnitList = new PersonList($('#attendUnitTab'), [], {
			onSelect: function(clazz) {
			    activeClass = clazz;
			}
	    });
	 	
	 	// 初始化议题旁听人员
	    var listenUnitList = new PersonList($('#listenUnitTab'), [], {
			onSelect: function(clazz) {
			    activeClass = clazz;
			}
	    });
	    
	  	//初始化议题
	    var TopicList = new TopicList($('#topicTab'), [], {
	    	mode:'edit',
			onSelect: function(clazz) {
				activeTopic = clazz;
				var flag = false;
			   	$.each(seatData, function(_, d) {
	   				if(d.fdId == activeTopic.topicId){
	   					flag = true;
	   					tmpData = d.data;
	   				}
   				});
			   	if(!flag){
			   		tmpData = templateData;
			   	}
			   	
			   	addTempSeatData();
			   	
			   	planPublic();//回显共有人员
			}
	    });
	  	
	    // 初始化坐席画布
	    var seat = new SeatPlan($('#seat'), {
	    	data: []
	    }, {
	    	cols:cols,
	    	rows:rows,
			mode: 'edit',
	    	renderCol: function(col, year, month, date) {
	    		
	    	},
	    	
	    	afterRenderCol: function(col, year, month, date) {
	    		
	    	},
	    	
	    	beforeRenderData: function(cell, d) {
	    		cell.css('background-color', 'transparent');
	    	},
	    	
	    	renderData: function(cell, d) {
	    		cell.addClass(d.clazz.style);
	    		if(d.clazz.type == "2"){
	    			if(d.name){
						var mask = $('<span/>').addClass("lui_seat_table_text").appendTo(cell);
						$('<div/>').addClass("mask_name").text(d.name).appendTo(mask);
					}else{
						var mask = $('<span/>').addClass("lui_seat_table_text").appendTo(cell);
						$('<div/>').addClass("mask_name").text('占位').appendTo(mask);
					}
				}
	    		if(d.clazz.nodeType == "2"){
	    			var mask = $('<span/>').addClass("lui_seat_table_mask").appendTo(cell);
	    			if(d.clazz.type == "1" || d.clazz.type == "0"){
						$('<span/>').addClass("mask_number").text(d.number).appendTo(mask);
	    			}
	    			
	    			$('<span/>').addClass("mask_name")
						.text(d.clazz.elementName).appendTo(mask);
					
	    			var delNode = $('<span/>').addClass("mask_close").appendTo(mask);
	    			delNode.on('click',function(e){
	    				e.stopPropagation();
	    				window.delData(d);
	    			});
	    			
	    		}else{
	    			if(d.clazz.type == "1" || d.clazz.type == "0"){
						$('<p/>').addClass("status_seat_no")
							.text(d.number).appendTo(cell);
					}
	    		}
	    	},
	    	
	    	selectRange: function(data) {
	    		if(!activeClass) {
	        		dialog.alert('请选择会议室元素或人员！');
	        		return;
	        	}
	    		var i = 0, l = data.length, t;
	    		
	    		var res = [];
	    		var nodeType = activeClass.nodeType;
	    		for(i = 0; i < l; i++) {
	        		
	        		t = data[i];
	        		if(nodeType == "1"){
	        			var _flag = false;
	    				$.each(tmpData || [], function(_, t) {
	    					$.each(data || [], function(_, d) {
	    						if(d.x == t.x && d.y == t.y){
	    							if(t.clazz.type == activeClass.type){
	    								_flag = true;
	    							}
	    						}
	    					});
	    				});
	    				if(_flag){
	    					return;
	    				}
	        			var flag = false;
	        			$.each(tmpData || [], function(_, d) {
	        				if(d.x == t.x && d.y == t.y && d.clazz.type != "0"){
	        					dialog.alert('请选择空白位置！');
	        					flag = true;
	        				}
	        			});
	        			if(!flag){
	        				res.push({
    		        			x:t.x,
    		        			y:t.y,
    		        			clazz:activeClass
    		        		});
	        				tempSeatData.push({
	        					x:t.x,
    		        			y:t.y,
    		        			clazz:activeClass
	        				});
	        			}
	        		}else if(nodeType == "2"){
	        			var _flag = false;
	    				$.each(tmpData || [], function(_, t) {
	    					$.each(data || [], function(_, d) {
	    						if(d.x == t.x && d.y == t.y){
	    							if(t.clazz.elementId){
	    								_flag = true;
	    							}
	    						}
	    					});
	    				});
	    				if(_flag){
	    					dialog.alert('请选择未安排的座位！');
	    					return;
	    				}
	        			$.each(tmpData, function(_, d) {
	        				if(d.x == t.x && d.y == t.y ){
	        					if(d.clazz.type == "1" || d.clazz.type == "0"){
	        						var isPublic = activeClass.isPublic;
	        						var elementId = activeClass.elementId;
	        						var elementName = activeClass.elementName;
	        						var cla = {type:d.clazz.type,style:d.clazz.style,name:d.clazz.name,nodeType:nodeType,elementId:elementId,elementName:elementName,isPublic:isPublic};
		        					res.push({
		    		        			x:t.x,
		    		        			y:t.y,
		    		        			number:d.number,
		    		        			clazz:cla
		    		        		});
		        					window.unActivePerson(elementId);
		        					planElement.push(elementId);
		        					planSeatCount++;
		        					if("true" == isPublic){
		        						publicData.push({
			    		        			x:t.x,
			    		        			y:t.y,
			    		        			number:d.number,
			    		        			clazz:cla
			    		        		});
		        					}
	        					}else{
	        						dialog.alert('请选择正确的座位！');
	        						res.push($.extend({}, d));
	        					}
	        				}else{
	        					res.push($.extend({}, d));
	        				}
	        			});
	        		}
	        	}
	    		if(nodeType == "1"){
	    			tmpData = SeatUtil.resolveData(tmpData.concat(res));
	    		}else if(nodeType == "2"){
	    			tmpData = res;
	    		}
	    		seat.setData({
					data: tmpData
				},true);
	    		
	    		if(activeTopic){
	    			window.saveTopicData();
	    		}else{
	    			window.saveMainData();
	    		}
	    		checkIsPlan();//该议题参会人员是否已经全部安排座位
			},
			contextMenuTitle: function(cell, data) {
				var menuTitle = '暂无数据';
				var i = 0, l = tmpData.length;
				for(i; i < l; i++) {
					var t = tmpData[i];
					if(t.x == data.x && t.y == data.y) {
						switch(t.clazz.type) {
						case '2':
							menuTitle = t.name ? t.name : '占位';
							break;
						case '0':
						case '1':
						case '3':
						case '41':
						case '42':
						case '43':
						case '44':
						case '51':
						case '52':
						case '53':
						case '54':
							menuTitle = t.clazz.name;
							break;
						default:
							break;
						}
					}
				}
				
				return menuTitle;
				
			},
			contextMenuItems: [
		    	{ 
		    		text: '删除',
		    		click: function(cell,data) {

		    			var res = [], 
		    				i = 0, l = tmpData.length;
		    			for(i; i < l; i++) {
		    				var t = tmpData[i];
		    				if(t.x == data.x && t.y == data.y) {
		    					res.push(t);
		    				}
		    			}
		    			
		    			tmpData = SeatUtil.resolveData(tmpData.concat(res));
		    			
		    			seat.setData({
		    				data: tmpData
		    			});
		    			
		    			tempSeatData = SeatUtil.resolveData(tempSeatData.concat(res));
		    			
		    			var sData = [];
		    	    	$.each(seatData || [], function(_, s) {
		    	    		var sres = [];
		    	    		$.each(s.data || [], function(_, d) {
		    	    			if(d.x != data.x || d.y != data.y ){
		    		    			sres.push($.extend({}, d));
		    					}
		    	    		});
		    	    		sData.push({
		    	    			fdId:s.fdId,
		    	    			data:sres
		    	    		});
		    			});
		    	    	seatData = sData;
		    			
		    		},
		    		visible: function(data) {
		    			var i = 0, l = tmpData.length;
		    			for(i; i < l; i++) {
							var t = tmpData[i];
							if(t.x == data.x && t.y == data.y) {
								if(t.clazz.type == '0'){
									if(!t.clazz.elementId){
										return true;
									}
								}
							}
						}
		    			return false;
		    		}
		    	}
		    ]
	    });
	    
	    window.getSeatCount = function(data){
	    	var seatCount = 0;
			$.each(data || [], function(_, d) {
				if(d.clazz.type == "1" || d.clazz.type == "0"){
					seatCount++;
				}
			});
			return seatCount;
	    };
	    
	    window.saveTopicData = function(){
	    	var resData = [];
			var flag = false;
			$.each(seatData, function(_, d) {
				if(d.fdId == activeTopic.topicId){
					flag = true;
					resData.push({
						fdId:activeTopic.topicId,
	    				data:tmpData
					});
				}else{
					resData.push($.extend({}, d));
				}
			});
			if(!flag){
				resData.push({
					fdId:activeTopic.topicId,
    				data:tmpData
    			});
			}
			seatData = resData;
	    };
	    
	    window.saveMainData = function(){
	    	var resData = [];
			var flag = false;
			$.each(seatData, function(_, d) {
				if(d.fdId == fdMainId){
					flag = true;
					resData.push({
						fdId:fdMainId,
	    				data:tmpData
					});
				}else{
					resData.push($.extend({}, d));
				}
			});
			if(!flag){
				resData.push({
    				fdId:fdMainId,
    				data:tmpData
    			});
			}
			seatData = resData;
	    };
	    
	    
	    window.delData = function(data){
	    	window.activePerson(data.clazz.elementId);
	    	window.removePlanElement(data.clazz.elementId);//从已安排人员中移除当前人员
	    	var isPublic = data.clazz.isPublic;
	    	if("true" == isPublic){
	    		window.removePublic(data);//移除已安排的共有人员
	    	}
	    	var res = [];
	    	$.each(tmpData || [], function(_, d) {
	    		if(d.x == data.x && d.y == data.y ){
					var cla = {type:d.clazz.type,style:d.clazz.style,name:d.clazz.name,nodeType:"1"};
    				res.push({
		        		x:data.x,
		        		y:data.y,
		        		number:data.number,
		        		clazz:cla
		        	});
    				planSeatCount--;
				}else{
					res.push($.extend({}, d));
				}
			});
	    	tmpData = res;
	    	seat.setData({
				data: tmpData
			});
	    	
	    	if(activeTopic){
    			window.saveTopicData();
    		}else{
    			window.saveMainData();
    		}
	    	
	    	checkIsPlan();//该议题参会人员是否已经全部安排座位
	    };
	    
	    window.removePublic = function(data){
	    	var pres = [];
	    	$.each(publicData || [], function(_, p) {
	    		if(p.x != data.x || p.y != data.y ){
	    			pres.push($.extend({}, p));
				}
			});
	    	publicData = pres;
	    	
	    	var sData = [];
	    	$.each(seatData || [], function(_, s) {
	    		var res = [];
	    		$.each(s.data || [], function(_, d) {
	    			if(d.x != data.x || d.y != data.y ){
		    			res.push($.extend({}, d));
					}else{
						var cla = {type:d.clazz.type,style:d.clazz.style,name:d.clazz.name,nodeType:"1"};
	    				res.push({
			        		x:d.x,
			        		y:d.y,
			        		number:d.number,
			        		clazz:cla
			        	});
					}
	    		});
	    		sData.push({
	    			fdId:s.fdId,
	    			data:res
	    		});
			});
	    	seatData = sData;
	    };
	    
	    window.removePlanElement = function(elementId){
	    	var index = $.inArray(elementId,planElement);//兼容IE8
			if (index > -1) {
				planElement.splice(index, 1);
			}
	    };
	    
	    window.unActivePerson = function(elementId){
	    	$('.lui_seat_btn[data-elementid='+elementId+']').removeClass('lui_text_primary').addClass('lui_seat_person_selected');
	    	if(showPlanFlag == true){
	    		$('.lui_seat_btn[data-elementid='+elementId+']').addClass('unshow');
	    	}
	    	activeClass = null;
	    };
	    
	    window.activePerson = function(elementId){
	    	$('.lui_seat_btn[data-elementid='+elementId+']').removeClass('lui_seat_person_selected');
	    	$('.lui_seat_btn[data-elementid='+elementId+']').removeClass('unshow');
	    };
	    
	 	//议题开启按钮切换
	 	window.topicPlanChange = function(){
	 		var val = $('[name="fdIsTopicPlan"]').val();
			if(val == "true"){
	 			initTopic();
	 		}else{
	 			TopicList.reset();
	 			activeTopic = null;
	 			var flag = false;
			   	$.each(seatData, function(_, d) {
	   				if(d.fdId == fdMainId){
	   					flag = true;
	   					tmpData = d.data;
	   				}
   				});
			   	if(!flag){
			   		tmpData = templateData;
			   	}
			   	
			   	addTempSeatData();
			   	
			   	planPublic();//回显共有人员
	 		}
	 	};
	 	
	 	window.addTempSeatData = function(){
	 		var res = [];
	 		$.each(tempSeatData, function(_, t) {
	 			var flag = false;
	 			$.each(tmpData, function(_, d) {
	 				if(d.x == t.x && d.y == t.y){
	 					flag = true;
	 				}
	 			});
	 			if(!flag){
	 				res.push($.extend({}, t));
	 			}
			});
	 		tmpData = SeatUtil.resolveData(tmpData.concat(res));
	 	};
	 	
	 	window.planPublic = function(){
	  		var res = [];
 			$.each(tmpData, function(_, d) {
 				var flag = false;
	 			$.each(publicData, function(_, p) {
	 				if(d.x == p.x && d.y == p.y){
	 					flag = true;
	 					var elementId = p.clazz.elementId;
						var elementName = p.clazz.elementName;
						var isPublic = p.clazz.isPublic;
	 					var cla = {type:d.clazz.type,style:d.clazz.style,name:d.clazz.name,nodeType:p.clazz.nodeType,elementId:elementId,elementName:elementName,isPublic:isPublic};
    					res.push({
		        			x:d.x,
		        			y:d.y,
		        			number:d.number,
		        			clazz:cla
		        		});
	 				}
   				});
	 			if(!flag){
	 				res.push($.extend({}, d));
	 			}
 			});
 			tmpData = res;
 			
			initPerson();
 			
 			if(activeTopic){
    			window.saveTopicData();
    		}else{
    			window.saveMainData();
    		}
 			
	  	};
	 	
	 	window.showPlanChange = function(){
	 		var val = $('[name="showPlan"]').val();
	 		if(val == 'true'){
	 			showPlanFlag = false;
	 			$('.lui_seat_person_selected').removeClass('unshow');
	 		}else{
	 			showPlanFlag = true;
	 			$('.lui_seat_person_selected').addClass('unshow');
	 		}
	 	};
	 	
	 	window.feedbackChange = function(){
	 		var val = $('[name="fdFeedback"]').val();
	 		if(val == 'true'){
	 			showAttendFlag = false;
	 		}else{
	 			showAttendFlag = true;
	 		}
	 		initPerson();
	 	};
	 	
	 	
	 	//初始化会议相关人员
	 	window.initPerson = function(){
	 		planElement.splice(0, planElement.length);//清空已安排人员
	 		planSeatCount = 0;//重置已安排座位数
	 		allPersonData = [];//重置人员信息
	 		hostPersonData = [];
	 		attendPersonData = [];
	 		summaryPersonData = [];
	 		participantPersonData = [];
	 		reportersData = [];
	 		reporterData = [];
	 		attendUnitData = [];
	 		listenUnitData = [];
	 		var url = "${LUI_ContextPath}/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan.do?method=getPersonData&fdImeetingMainId=${kmImeetingSeatPlanForm.fdImeetingMainId}";
 			if(activeTopic){
 				url += "&fdTopicId="+activeTopic.topicId;
 			}
 			if(showAttendFlag == true){
 				url += "&isFeedback=true";
 			}
	 		$.get(url).then(function(res) {
	 			
	 			//主持人
 				$.each(res.host || [], function(_, d) {
 					hostPersonData.push($.extend({},d));
 					allPersonData.push($.extend({},d));
 		    	});
 				HostList.setData(hostPersonData);
 		    	
 				//参加人员
 				$.each(res.attend || [], function(_, d) {
 					attendPersonData.push($.extend({},d));
 					allPersonData.push($.extend({},d));
 		    	});
 				AttendList.setData(attendPersonData);
 				
 				//纪要人员
 				$.each(res.summary || [], function(_, d) {
 					summaryPersonData.push($.extend({},d));
 					allPersonData.push($.extend({},d));
 		    	});
 				summaryList.setData(summaryPersonData);
 				if(summaryPersonData.length <= 0){
 					$("#summaryWrap").hide();
 				}else{
 					$("#summaryWrap").show();
 				}
 				
 				//列席人员
 				$.each(res.participant || [], function(_, d) {
 					participantPersonData.push($.extend({},d));
 					allPersonData.push($.extend({},d));
 		    	});
 				participantList.setData(participantPersonData);
 				if(participantPersonData.length <= 0){
 					$("#participantWrap").hide();
 				}else{
 					$("#participantWrap").show();
 				}
 				
 				//上会材料汇报人员
 				$.each(res.reporters || [], function(_, d) {
 					reportersData.push($.extend({},d));
 					allPersonData.push($.extend({},d));
 		    	});
 				reportersList.setData(reportersData);
 				if(reportersData.length <= 0){
 					$("#reportersWrap").hide();
 				}else{
 					$("#reportersWrap").show();
 				}
 				
 				//议题汇报人员
 				$.each(res.topicReporter || [], function(_, d) {
 					reporterData.push($.extend({},d));
 					allPersonData.push($.extend({},d));
 		    	});
 				reporterList.setData(reporterData);
 				if(reporterData.length <= 0){
 					$("#reporterWrap").hide();
 				}else{
 					$("#reporterWrap").show();
 				}
 				
 				//议题列席人员
 				$.each(res.attendUnit || [], function(_, d) {
 					attendUnitData.push($.extend({},d));
 					allPersonData.push($.extend({},d));
 		    	});
 				attendUnitList.setData(attendUnitData);
 				if(attendUnitData.length <= 0){
 					$("#attendUnitWrap").hide();
 				}else{
 					$("#attendUnitWrap").show();
 				}
 				
 				//议题旁听人员
 				$.each(res.listenUnit || [], function(_, d) {
 					listenUnitData.push($.extend({},d));
 					allPersonData.push($.extend({},d));
 		    	});
 				listenUnitList.setData(listenUnitData);
 				if(listenUnitData.length <= 0){
 					$("#listenUnitWrap").hide();
 				}else{
 					$("#listenUnitWrap").show();
 				}
 				
 				removePerson();
 		    	
 		    	checkIsPlan();//该议题参会人员是否已经全部安排座位
 			});
	 	};
	 	
	 	//该议题参会人员是否已经全部安排座位
	 	window.checkIsPlan = function(){
	 		var isTopicPlan = $('[name="fdIsTopicPlan"]').val();// 是否开启议题排位
	    	if(isTopicPlan == 'true'){
	    		var flag = true;
	    		$.each(allPersonData || [], function(_, p) {
	    			var index = planElement.indexOf(p.elementId);
					if(index == -1){
						flag = false;
					}
	    		});
    			if(activeTopic != null){
    				TopicList.setIsPlan(activeTopic.topicId,flag);
    			}
	    	}
	 	};
	 	
	 	//初始化议题
	 	window.initTopic = function(){
	 		 $.get('${LUI_ContextPath}/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan.do?method=getTopicData&fdImeetingMainId=${kmImeetingSeatPlanForm.fdImeetingMainId}').then(function(res) {
	 	    	var topicData = [];
	 	    	$.each(res || [], function(_, d) {
	 	    		topicData.push($.extend({},d));
	 	    	});
	 	    	TopicList.setData(topicData);
	 	    });
	 	};
	 	
	 	window.init = function(){
	 		var isTopicPlan = $('[name="fdIsTopicPlan"]').val();// 是否开启议题排位
			if(isTopicPlan == 'true'){
				seatData = '${kmImeetingSeatPlanForm.fdSeatDetail}';
				if(seatData == ''){
					seatData = [];
				}else{
					seatData = JSON.parse(seatData);
				}
				initPublic();
				initTopic();
			}else{
				var method = "${kmImeetingSeatPlanForm.method_GET}";
				if("edit" == method){
					tmpData = '${kmImeetingSeatPlanForm.fdSeatDetail}';
					if(tmpData == ''){
						tmpData = [];
					}else{
						tmpData = JSON.parse(tmpData);
					}
				}else{
					tmpData = templateData;
				}
		 		initPerson();
			}
	 	};
	 	
	 	window.initPublic = function(){
	 		$.each(seatData || [], function(_, s) {
	    		var res = [];
	    		$.each(s.data || [], function(_, d) {
		    		if(d.clazz.isPublic == "true"){
		    			var p_flag = false;
		    			$.each(publicData|| [], function(_, p) {
		    				if(d.x == p.x && d.y == p.y){
		    					p_flag = true;
		    				}
		    			});
		    			if(!p_flag){
		    				publicData.push({
			        			x:d.x,
			        			y:d.y,
			        			number:d.number,
			        			clazz:d.clazz
			        		});
		    			}
		    		}
		    		if(d.clazz.type == "0"){
		    			var t_flag = false;
		    			$.each(tempSeatData|| [], function(_, t) {
		    				if(d.x == t.x && d.y == t.y){
		    					t_flag = true;
		    				}
		    			});
		    			if(!t_flag){
		    				var cla = {type:d.clazz.type,style:d.clazz.style,name:d.clazz.name,nodeType:d.clazz.nodeType};
			    			tempSeatData.push({
	        					x:d.x,
			        			y:d.y,
			        			clazz:cla
	        				});
		    			}
		    		}
	    		});
			});
	 	};
	 	
	 	window.removePerson = function(){
	    	var res = [];
	    	$.each(tmpData || [], function(_, t) {
	    		if(t.clazz.elementId){
	    			var flag = false;
	    			$.each(allPersonData || [], function(_, d) {
						if(t.clazz.elementId == d.elementId){
							flag = true;
						}
					});
	    			if(!flag){
		    			var cla = {type:t.clazz.type,style:t.clazz.style,name:t.clazz.name,nodeType:"1"};
	    				res.push({
			        		x:t.x,
			        		y:t.y,
			        		number:t.number,
			        		clazz:cla
			        	});
		    		}else{
		    			res.push($.extend({}, t));
		    		}
	    		}else{
	    			res.push($.extend({}, t));
	    		}
			});
	    	tmpData = res;
	    	
	    	$.each(tmpData, function(_, d) {
   				if(d.clazz.elementId){
   					window.unActivePerson(d.clazz.elementId);
   					planElement.push(d.clazz.elementId);
   					planSeatCount++;
   				}
			});
	    	
	    	seat.setData({
				data: tmpData
			});
	    };
	 	
	 	window.onload = function(){
	 		init();
	 	};
	 	
	 	//一键排位
	 	window.autoPlan = function(type){
	 		var elementData = getPersonData(type);
	 		var planCount = elementData.length;
	 		
			$.each(elementData, function(_, p) {
				var elementId = p.elementId;
				var index = $.inArray(elementId,planElement);//兼容IE8
				if(index != -1){
					planCount--;
				}
			});
						
	 		var sCount = getSeatCount(tmpData);
	 		var count =  planCount - (sCount - planSeatCount);
	 		if(count > 0){
	 			dialog.confirm('座位数小于待安排人数(缺'+ count +'座)!<br>可通过添加临时坐席给人员安排位置。', function(check) {
		 			if(check){
		 				plan(elementData);
		 			}
	 			});
	 			
	 		}else{
	 			plan(elementData);
	 		}
	 	};
	 	
	 	
	 	window.plan = function(elementData){
	 		var res = [];
			$.each(tmpData, function(_, d) {
				if(d.clazz.type == "1" || d.clazz.type == "0"){
					if(!d.clazz.elementId){
						var flag = true;
						var elementId = "";
						var elementName = "";
						var isPublic ;
						$.each(elementData, function(_, p) {
							isPublic = p.isPublic;
							elementId = p.elementId;
							elementName = p.elementName;
							var ind =$.inArray(elementId,planElement);//兼容IE8
							if(ind == -1){
								flag = false;
								return false;
							}
						});
						if(!flag){
							var cla = {type:d.clazz.type,style:d.clazz.style,name:d.clazz.name,nodeType:"2",elementId:elementId,elementName:elementName,isPublic:isPublic};
		   					res.push({
			        			x:d.x,
			        			y:d.y,
			        			number:d.number,
			        			clazz:cla
			        		});
		   					window.unActivePerson(elementId);
		   					planElement.push(elementId);
		   					planSeatCount++;
		   					if("true" == isPublic){
        						publicData.push({
	    		        			x:d.x,
	    		        			y:d.y,
	    		        			number:d.number,
	    		        			clazz:cla
	    		        		});
        					}
						}else{
							res.push($.extend({}, d));
						}
							
					}else{
						res.push($.extend({}, d));
					}
				}else{
					res.push($.extend({}, d));
				}
	 			
	 		});
			tmpData = res;
			
	 		seat.setData({
				data: tmpData
			});
	 		
	 		if(activeTopic){
    			window.saveTopicData();
    		}else{
    			window.saveMainData();
    		}
	 		
	 		checkIsPlan();//该议题参会人员是否已经全部安排座位
	 	};
	 	
	 	window.getPersonData = function(type){
	 		var elementData = [];
	 		switch(type){
		 		case 0:
		 			elementData = allPersonData;
		 			break;
		 		case 1:
		 			elementData = hostPersonData;
		 			break;
		 		case 2:
		 			elementData = attendPersonData;
		 			break;
		 		case 3:
		 			elementData = summaryPersonData;
		 			break;
		 		case 4:
		 			elementData = participantPersonData;
		 			break;
		 		case 5:
		 			elementData = reportersData;
		 			break;
		 		case 6:
		 			elementData = reporterData;
		 			break;
		 		case 7:
		 			elementData = attendUnitData;
		 			break;
		 		case 8:
		 			elementData = listenUnitData;
		 			break;
		 		default:
		 			break;
 			}
	 		elementData=getUniqueArray(elementData);
	 		return elementData;
	 	};
		
		//数组去重
		window.getUniqueArray=function(arr){
			var uniqueArr=[];
			for(var i=0;i<arr.length;i++){
				var index=getIndexInPersonData(arr[i],uniqueArr);
				if(index==-1){
					uniqueArr.push(arr[i]);
				}
			}
			return uniqueArr;
		}
		
		window.getIndexInPersonData=function(person,personArr){
			var index=-1;
			for(var i=0;i<personArr.length;i++){
				if(person.elementId==personArr[i].elementId){
					index=i;
					break;
				}
			}
			return index;
		}
	 	
	 	//重置
	 	window.resetPerson = function(type){
	 		var elementData = getPersonData(type);
	 		var res = [];
	 		
	 		$.each(tmpData, function(_, d) {
	 			var flag = false;
	 			$.each(elementData, function(_, e) {
	 				if(d.clazz.elementId == e.elementId){
	 					flag = true;
	 					window.activePerson(d.clazz.elementId);
	 			    	window.removePlanElement(d.clazz.elementId);//从已安排人员中移除当前人员
						var cla = {type:d.clazz.type,style:d.clazz.style,name:d.clazz.name,nodeType:"1"};
	    				res.push({
			        		x:d.x,
			        		y:d.y,
			        		number:d.number,
			        		clazz:cla
			        	});
	    				planSeatCount--;
	 				}
	 			});
	 			if(!flag){
	 				res.push($.extend({}, d));
	 			}
	 		});
	 		
	 		var pres = [];
	    	$.each(publicData || [], function(_, p) {
	    		var flag = false;
	    		$.each(elementData, function(_, e) {
	 				if(p.clazz.elementId == e.elementId){
	 					flag = true;
	 				}
	    		});
	    		if(!flag){
	    			pres.push($.extend({}, p));
	 			}
			});
	    	publicData = pres;
	    	
	    	var sData = [];
	    	$.each(seatData || [], function(_, s) {
	    		var res = [];
	    		$.each(s.data || [], function(_, d) {
	    			var flag = false;
		    		$.each(elementData, function(_, e) {
		 				if(d.clazz.elementId == e.elementId){
		 					flag = true;
		 					var cla = {type:d.clazz.type,style:d.clazz.style,name:d.clazz.name,nodeType:"1"};
		    				res.push({
				        		x:d.x,
				        		y:d.y,
				        		number:d.number,
				        		clazz:cla
				        	});
		 				}
		    		});
		    		if(!flag){
		    			res.push($.extend({}, d));
		 			}
	    		});
	    		sData.push({
	    			fdId:s.fdId,
	    			data:res
	    		});
			});
	    	seatData = sData;
	    	
	 		tmpData = res;
	    	seat.setData({
				data: tmpData
			});
	    	
	    	if(activeTopic){
    			window.saveTopicData();
    		}else{
    			window.saveMainData();
    		}
	 		checkIsPlan();//该议题参会人员是否已经全部安排座位
	 	};
		
	    // 保存数据
		window.submit = function(method) {
			var data = "";
			var val = $('[name="fdIsTopicPlan"]').val();
			if(val == "true"){
				data = JSON.stringify(seatData);
			}else{
				data = JSON.stringify(tmpData);
			}
			$('[name="fdSeatDetail"]').val(data);
			Com_Submit(document.kmImeetingSeatPlanForm, method);
		};
	    
	});

</script>
