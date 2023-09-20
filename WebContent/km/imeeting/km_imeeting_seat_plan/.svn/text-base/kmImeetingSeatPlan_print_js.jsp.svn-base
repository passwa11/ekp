<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/sys/ui/jsp/common.jsp"%>

<script>
	
	seajs.use(['lui/topic', 'lui/dialog', 'km/imeeting/resource/js/seatPlan',
	           	'km/imeeting/resource/js/seatutil',
	            'km/imeeting/resource/js/topiclist'],
				function (topic, dialog,SeatPlan,SeatUtil,TopicList) {	

		//////////////////////////////////////////////////////////////////
		// 动态调整容器高度
		//////////////////////////////////////////////////////////////////
		var seatData = [];//用于保存所有排位数据
		
		var tmpData = [];// 用于保存当前排位信息
		
		var subject = "${kmImeetingSeatPlanForm.docSubject}";
		
		var cols = parseInt("${kmImeetingSeatPlanForm.fdCols}");
		var rows = parseInt("${kmImeetingSeatPlanForm.fdRows}");
		
		var isTopicPlan = '${kmImeetingSeatPlanForm.fdIsTopicPlan}';
		var TopicList;
	    
	    var options = {
		    	mode: "print",
		    	cols:cols,
		    	rows:rows,
		    	
		    	renderCol: function(col, year, month, date, element) {
		    		
		    	},
		    	afterRenderCol: function(col, year, month, date, element) {
		    		
		    	},
		    	
		    	beforeRenderData: function(cell, d, element) {
		    		(cell.attr('data-old') == 'true') ? '' : cell.css('background-color', 'transparent');
		    	},
		    	
		    	renderData: function(cell, d, element) {
					cell.addClass(d.clazz.style);
					var type = d.clazz.type;
					switch(type){
						case '3':
							var mask = $('<span/>').addClass("lui_seat_table_text").appendTo(cell);
							$('<span/>').addClass("mask_name").text('演讲台').appendTo(mask);
							break;
						case '41':
						case '42':
						case '43':
						case '44':
							var mask = $('<span/>').addClass("lui_seat_table_text").appendTo(cell);
							$('<span/>').addClass("mask_name").text('屏幕').appendTo(mask);
							break;
						case '51':
						case '52':
						case '53':
						case '54':
							var mask = $('<span/>').addClass("lui_seat_table_text").appendTo(cell);
							$('<span/>').addClass("mask_name").text('门口').appendTo(mask);
							break;
					}
					if(d.clazz.type == "2"){
						if(d.name){
							var mask = $('<span/>').addClass("lui_seat_table_text").appendTo(cell);
							$('<span/>').addClass("mask_name").text(d.name).appendTo(mask);
						}else{
							var mask = $('<span/>').addClass("lui_seat_table_text").appendTo(cell);
							$('<span/>').addClass("mask_name").text('占位').appendTo(mask);
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
	    	};
	    
	    //初始化议题
	 	window.initTopic = function(){
	 		$.get('${LUI_ContextPath}/km/imeeting/km_imeeting_seat_plan/kmImeetingSeatPlan.do?method=getTopicData&fdImeetingMainId=${kmImeetingSeatPlanForm.fdImeetingMainId}').then(function(res) {
	 			for(var i = 0;i< res.length;i++){
	 	    		var topic = res[i];
	 	    		$.each(seatData || [], function(_, s) {
	 	    			if(topic.topicId == s.fdId){
 		    				var seat = new SeatPlan($('#seat_'+(i+1)), {
 						    	data: []
 						    }, options);
	 						seat.setData({
	 							data: s.data
	 						});
	 						$('#plan_subject_'+(i+1)).text(subject+'('+topic.topicSubject+')');
	 	    			}
	 				});
	 	    	}
	 	    	
	 	    });
	 	}
	    
	 	window.init = function(){
	 		if(isTopicPlan == 'true'){
				seatData = '${kmImeetingSeatPlanForm.fdSeatDetail}';
				if(seatData == ''){
					seatData = [];
				}else{
					seatData = JSON.parse(seatData);
				}
				initTopic();
			}else{
				tmpData = '${kmImeetingSeatPlanForm.fdSeatDetail}';
				if(tmpData == ''){
					tmpData = [];
				}else{
					tmpData = JSON.parse(tmpData);
				}
				var seat = new SeatPlan($('#seat'), {
				    	data: []
				    }, options);
				seat.setData({
					data: tmpData
				});
			}
	 	}
	 	
	 	window.onload = function(){
	 		init();
	 	}
	    
	 	window.printDoc = function(){
	 		window.print();
	 	}
		
	});

</script>
