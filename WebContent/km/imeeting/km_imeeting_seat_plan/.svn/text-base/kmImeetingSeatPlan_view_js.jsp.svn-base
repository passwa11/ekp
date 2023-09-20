<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script>
	
	seajs.use(['lui/topic', 'lui/dialog', 'km/imeeting/resource/js/seatPlan',
	           	'km/imeeting/resource/js/seatutil',
	            'km/imeeting/resource/js/topiclist',
	            'km/imeeting/resource/js/personlist'],
				function (topic, dialog,SeatPlan,SeatUtil,TopicList,PersonList) {	

		//////////////////////////////////////////////////////////////////
		// 动态调整容器高度
		//////////////////////////////////////////////////////////////////
		setTimeout(function() {
			seat && seat.setOptions({
				maxHeight: (window.screen.height) + 'px'
			});
		}, 100);
		
		//////////////////////////////////////////////////////////////////
		// 全局变量
		//////////////////////////////////////////////////////////////////
		
		var seatData = [];//用于保存所有排位数据
		
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
		
		var cols = parseInt("${kmImeetingSeatPlanForm.fdCols}");
		var rows = parseInt("${kmImeetingSeatPlanForm.fdRows}");
		
		var templateData = '${kmImeetingSeatPlanForm.fdTemplateSeatDetail}';//初始化坐席模版设置的座位信息
		if(templateData == ''){
			templateData = [];
		}else{
			templateData = JSON.parse(templateData);
		}
		
		var isTopicPlan = '${kmImeetingSeatPlanForm.fdIsTopicPlan}';
		var TopicList;
		//当前选中议题
		var activeTopic = null;
		if(isTopicPlan == 'true'){
			//初始化议题
		    TopicList = new TopicList($('#topicTab'), [], {
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
		}
		
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
	 	}
		
		window.planPublic = function(){
	  		var res = [];
 			$.each(tmpData, function(_, d) {
 				var flag = false;
	 			$.each(publicData, function(_, p) {
	 				if(d.x == p.x && d.y == p.y){
	 					flag = true;
	 					var elementId = p.clazz.elementId;
						var elementName = p.clazz.elementName;
	 					var cla = {type:d.clazz.type,style:d.clazz.style,name:d.clazz.name,nodeType:p.clazz.nodeType,elementId:elementId,elementName:elementName};
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
 			
	  	}
		
		
		// 初始化主持人
	    var hostList = new PersonList($('#hostTab'), [], {
	    	mode: 'view'
	    });
	    
	 	// 初始化参加人员
	    var participantList = new PersonList($('#participantTab'), [], {
	    	mode: 'view'
	    });
	 	
	 	// 初始化纪要人员
	    var summaryList = new PersonList($('#summaryTab'), [], {
	    	mode: 'view'
	    });
	 	
	 	// 初始化列席人员
	    var attendList = new PersonList($('#attendTab'), [], {
	    	mode: 'view'
	    });
	 	
	 	// 初始化上会材料汇报人员
	    var reportersList = new PersonList($('#reportersTab'), [], {
	    	mode: 'view'
	    });
	 	
	 	// 初始化议题汇报人员
	    var reporterList = new PersonList($('#reporterTab'), [], {
	    	mode: 'view'
	    });
	 	
	 	// 初始化议题列席人员
	    var attendUnitList = new PersonList($('#attendUnitTab'), [], {
	    	mode: 'view'
	    });
	 	
	 	// 初始化议题旁听人员
	    var listenUnitList = new PersonList($('#listenUnitTab'), [], {
	    	mode: 'view'
	    });
	    
	    var seat = new SeatPlan($('#seat'), {
	    	data: []
	    }, {
	    	cols:cols,
	    	rows:rows,
	    	maxHeight: (window.screen.height - 256) + 'px',
	    	renderCol: function(col, year, month, date, element) {
	    		
	    	},
	    	afterRenderCol: function(col, year, month, date, element) {
	    		
	    	},
	    	
	    	beforeRenderData: function(cell, d, element) {
	    		(cell.attr('data-old') == 'true') ? '' : cell.css('background-color', 'transparent');
	    	},
	    	
	    	renderData: function(cell, d, element) {
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
	    		}else{
	    			if(d.clazz.type == "1" || d.clazz.type == "0"){
						$('<p/>').addClass("status_seat_no")
							.text(d.number).appendTo(cell);
					}
	    		}
	    	}
	    });
	    
	    //初始化议题
	 	window.initTopic = function(){
	 		 $.get('${LUI_ContextPath}/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan.do?method=getTopicData&fdImeetingMainId=${kmImeetingSeatPlanForm.fdImeetingMainId}').then(function(res) {
	 	    	var topicData = [];
	 	    	$.each(res || [], function(_, d) {
	 	    		topicData.push($.extend({},d));
	 	    	});
	 	    	TopicList.setData(topicData);
	 	    });
	 	}
	    
	 	//初始化会议相关人员
	 	window.initPerson = function(){
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
	 		$.get(url).then(function(res) {
	 			
	 			//主持人
 				$.each(res.host || [], function(_, d) {
 					hostPersonData.push($.extend({},d));
 					allPersonData.push($.extend({},d));
 		    	});
 				hostList.setData(hostPersonData);
 		    	
 				//参加人员
 				$.each(res.attend || [], function(_, d) {
 					attendPersonData.push($.extend({},d));
 					allPersonData.push($.extend({},d));
 		    	});
 				attendList.setData(attendPersonData);
 				
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
 				
 			});
	 	}
	    
	    window.init = function(){
	    	var seatDetail = '${kmImeetingSeatPlanForm.fdSeatDetail}';
	 		if(isTopicPlan == 'true'){
				if(seatDetail == ''){
					seatData = [];
				}else{
					seatData = JSON.parse(seatDetail);
				}
				initPublic();
				initTopic();
			}else{
				if(seatDetail == ''){
					tmpData = [];
				}else{
					tmpData = JSON.parse(seatDetail);
				}
				initPerson();
			}
	 	}
	    
	    window.initPublic = function(){
	 		$.each(seatData || [], function(_, s) {
	    		var res = [];
	    		$.each(s.data || [], function(_, d) {
		    		if(d.clazz.isPublic == "true"){
		    			publicData.push({
		        			x:d.x,
		        			y:d.y,
		        			number:d.number,
		        			clazz:d.clazz
		        		});
		    		}
		    		if(d.clazz.type == "0"){
		    			var cla = {type:d.clazz.type,style:d.clazz.style,name:d.clazz.name,nodeType:d.clazz.nodeType};
		    			tempSeatData.push({
        					x:d.x,
		        			y:d.y,
		        			clazz:cla
        				});
		    		}
	    		});
			});
	 	}
	    
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
   					$('.lui_seat_btn[data-elementid='+d.clazz.elementId+']').removeClass('lui_text_primary').addClass('lui_seat_person_selected');
   				}
			});
	    	
	    	seat.setData({
				data: tmpData
			});
	    }
	 	
	 	window.onload = function(){
	 		init();
	 	}
	    
	 	
	 	window.printDoc = function(){
	 		Com_OpenWindow('${KMSS_Parameter_ContextPath}km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan.do?method=print&fdId=${param.fdId}');
	 	}
		
	});

</script>
